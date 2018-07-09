package com.webwonder.atm.integrationtest.controllers;

import com.webwonder.atm.controllers.StorageStatusController;
import com.webwonder.atm.enums.NoteType;
import com.webwonder.atm.models.Withdrawal;
import com.webwonder.atm.services.NotesStorageService;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("integrationtest")
public class StorageStatusControllerShould {

    @Autowired
    NotesStorageService notesStorageService;

    MockMvc mockMvc;

    @Before
    public void setUp() {
        StorageStatusController storageStatusController = new StorageStatusController(notesStorageService);
        mockMvc = MockMvcBuilders.standaloneSetup(storageStatusController).build();
    }

    @Test
    public void respondWithAStatus() throws Exception {
        mockMvc.perform(get(StorageStatusController.STATUS_ACTION)
            .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
            .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(status().is2xxSuccessful())
            .andExpect(jsonPath("$.storages.FIFTY_NOTE.total").value(10))
            .andExpect(jsonPath("$.storages.TWENTY_NOTE.total").value(10));
    }


    @Test
    public void respondWithAStatusAfterSomeWithdrawalsHaveTakenPlace() throws Exception {
        Withdrawal withdrawal = new Withdrawal();
        withdrawal.add(NoteType.FIFTY_NOTE, 6);
        withdrawal.add(NoteType.TWENTY_NOTE, 3);
        notesStorageService.captureMaximum(NoteType.FIFTY_NOTE,6);
        notesStorageService.captureMaximum(NoteType.TWENTY_NOTE, 3);
        notesStorageService.complete(withdrawal);

        mockMvc.perform(get(StorageStatusController.STATUS_ACTION)
            .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
            .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(status().is2xxSuccessful())
            .andExpect(jsonPath("$.storages.FIFTY_NOTE.total").value(4))
            .andExpect(jsonPath("$.storages.TWENTY_NOTE.total").value(7));
    }
}