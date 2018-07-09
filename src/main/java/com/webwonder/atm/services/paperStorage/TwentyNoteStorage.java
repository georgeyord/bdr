package com.webwonder.atm.services.paperStorage;

import com.webwonder.atm.enums.NoteType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class TwentyNoteStorage extends BaseNoteStorage {
    public static final NoteType TYPE = NoteType.TWENTY_NOTE;

    @Autowired
    public TwentyNoteStorage(
        @Value("${atm.notes.twenty.total}") final Integer total) {
        super(total);
    }
}
