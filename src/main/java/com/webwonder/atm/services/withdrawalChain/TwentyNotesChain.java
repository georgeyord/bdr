package com.webwonder.atm.services.withdrawalChain;

import com.webwonder.atm.enums.NoteType;
import com.webwonder.atm.services.NotesStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TwentyNotesChain extends BaseNotesChain {
    protected static final NoteType TYPE = NoteType.TWENTY_NOTE;
    protected static final int VALUE = 20;

    public TwentyNotesChain() {
    }

    @Autowired
    public TwentyNotesChain(NotesStorageService notesStorageService) {
        super(notesStorageService);
    }

    @Override
    public NoteType getType() {
        return TYPE;
    }

    @Override
    public Integer getValue() {
        return VALUE;
    }
}
