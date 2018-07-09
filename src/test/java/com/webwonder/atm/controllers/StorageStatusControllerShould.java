package com.webwonder.atm.controllers;

import com.webwonder.atm.services.NotesStorageService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import static org.junit.Assert.assertEquals;

public class StorageStatusControllerShould {

    @Mock
    NotesStorageService notesStorageService;

    StorageStatusController storageStatusController;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        storageStatusController = new StorageStatusController(notesStorageService);
    }

    @Test
    public void withdrawAction() {
        ResponseEntity<NotesStorageService> response = storageStatusController.withdrawAction();

        assert (response.getStatusCode()).is2xxSuccessful();
        assertEquals(notesStorageService, response.getBody());
    }
}