package org.pankajsinghcoding.nounorverb.ControllerTest;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.pankajsinghcoding.nounorverb.Controller.DictionaryController;
import org.pankajsinghcoding.nounorverb.Service.DictionaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(DictionaryController.class)
public class DictionaryControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DictionaryService dictionaryService;

    @Test
    @WithMockUser(username = "user", password = "password", roles = "USER")
    public void testGetMeaning() throws Exception {
        String word = "example";
        String partOfSpeech = "noun";
        List<String> meanings = List.of("a thing characteristic of its kind or illustrating a general rule.");

        ResponseEntity<List<String>> responseEntity = new ResponseEntity<>(meanings, HttpStatus.OK);

        Mockito.<ResponseEntity<List<String>>>when(dictionaryService.getMeaning(word, partOfSpeech))
                .thenReturn(responseEntity);

        mockMvc.perform(get("/api/meaning")
                        .param("word", word)
                        .param("partOfSpeech", partOfSpeech))
                .andExpect(status().isOk())
                .andExpect(content().json("[\"a thing characteristic of its kind or illustrating a general rule.\"]"));
    }
}
