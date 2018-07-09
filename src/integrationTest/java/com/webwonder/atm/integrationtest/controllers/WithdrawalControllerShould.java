package com.webwonder.atm.integrationtest.controllers;

import com.webwonder.atm.controllers.WithdrawalController;
import com.webwonder.atm.enums.NoteType;
import com.webwonder.atm.services.NotesStorageService;
import com.webwonder.atm.services.WithdrawalService;
import com.webwonder.atm.services.paperStorage.FiftyNoteStorage;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("integrationtest")
public class WithdrawalControllerShould {

    @Autowired
    WithdrawalService withdrawalService;

    @Autowired
    NotesStorageService notesStorageService;

    MockMvc mockMvc;

    @Before
    public void setUp() {
        WithdrawalController controller = new WithdrawalController(withdrawalService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

        notesStorageService.resetStorage(NoteType.FIFTY_NOTE, new FiftyNoteStorage(10));
        notesStorageService.resetStorage(NoteType.TWENTY_NOTE, new FiftyNoteStorage(10));
    }

    @Test
    public void respondWithABreakdownWhenTheRequestAmountIsValid() throws Exception {
        mockMvc.perform(post(WithdrawalController.WITHDRAW_ACTION)
            .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
            .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
            .content(new String("290")))
            .andExpect(status().is2xxSuccessful())
            .andExpect(jsonPath("$.breakdown.FIFTY_NOTE").value(5))
            .andExpect(jsonPath("$.breakdown.TWENTY_NOTE").value(2));
    }

    @Test
    public void respondWith400WhenTheRequestAmountIsOverTheStorageCapacity() throws Exception {
        mockMvc.perform(post(WithdrawalController.WITHDRAW_ACTION)
            .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
            .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
            .content("1000"))
            .andExpect(status().is4xxClientError())
            .andExpect(content().string(""));
    }

    @Test
    public void respondWithABreakdownWithTwentysWhenTheRequestAmountIsValidAndFiftysAreAlreadyTaken() throws Exception {
        mockMvc.perform(post(WithdrawalController.WITHDRAW_ACTION)
            .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
            .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
            .content(new String("500")))
            .andExpect(status().is2xxSuccessful())
            .andExpect(jsonPath("$.breakdown.FIFTY_NOTE").value(10));

        mockMvc.perform(post(WithdrawalController.WITHDRAW_ACTION)
            .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
            .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
            .content(new String("200")))
            .andExpect(status().is2xxSuccessful())
            .andExpect(jsonPath("$.breakdown.TWENTY_NOTE").value(10));
    }
}