package com.webwonder.atm.controllers;

import com.webwonder.atm.services.NotesStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(
    produces = MediaType.APPLICATION_JSON_UTF8_VALUE
)
public class StorageStatusController {

    public static final String STATUS_ACTION = "/status";

    private NotesStorageService notesStorageService;

    @Autowired
    public StorageStatusController(NotesStorageService notesStorageService) {
        this.notesStorageService = notesStorageService;
    }

    @RequestMapping(method = RequestMethod.GET, value = STATUS_ACTION)
    @ResponseBody
    public ResponseEntity<NotesStorageService> withdrawAction() {
        return ResponseEntity.ok().body(notesStorageService);
    }
}
