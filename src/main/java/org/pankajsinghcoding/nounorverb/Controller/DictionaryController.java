package org.pankajsinghcoding.nounorverb.Controller;

import org.pankajsinghcoding.nounorverb.Service.DictionaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DictionaryController {

    @Autowired
    private DictionaryService dictionaryService;

    @GetMapping("/api/meaning")
    public ResponseEntity<?> getMeaning(
            @RequestParam String word,
            @RequestParam String partOfSpeech) {
        return dictionaryService.getMeaning(word, partOfSpeech);
    }
}
