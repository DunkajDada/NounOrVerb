package org.pankajsinghcoding.nounorverb.Service;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class DictionaryService {

    private final RestTemplate restTemplate = new RestTemplate();

    public ResponseEntity<List<String>> getMeaning(String word, String partOfSpeech) {
        String apiUrl = "https://api.dictionaryapi.dev/api/v2/entries/en/" + word;
        ResponseEntity<String> response = restTemplate.getForEntity(apiUrl, String.class);

        if (response.getStatusCode().is2xxSuccessful()) {
            JSONArray jsonResponse = new JSONArray(response.getBody());
            List<String> result = new ArrayList<>();

            for (int i = 0; i < jsonResponse.length(); i++) {
                JSONObject entry = jsonResponse.getJSONObject(i);
                JSONArray meanings = entry.getJSONArray("meanings");

                for (int j = 0; j < meanings.length(); j++) {
                    JSONObject meaning = meanings.getJSONObject(j);
                    if (meaning.getString("partOfSpeech").equalsIgnoreCase(partOfSpeech)) {
                        JSONArray definitions = meaning.getJSONArray("definitions");
                        for (int k = 0; k < definitions.length(); k++) {
                            result.add(definitions.getJSONObject(k).getString("definition"));
                        }
                    }
                }
            }

            if (result.isEmpty()) {
                return ResponseEntity.status(404).body(Collections.singletonList("No meanings found for the specified part of speech."));
            }

            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.status(response.getStatusCode()).body(Collections.singletonList("Error retrieving meaning."));
        }
    }
}

