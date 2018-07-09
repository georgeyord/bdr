package com.webwonder.atm.services.paperStorage;

import com.webwonder.atm.enums.NoteType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class FiftyNoteStorage extends BaseNoteStorage {
    public static final NoteType TYPE = NoteType.FIFTY_NOTE;

    @Autowired
    public FiftyNoteStorage(
        @Value("${atm.notes.fifty.total}") final Integer total) {
        super(total);
    }
}
