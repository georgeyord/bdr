package com.webwonder.atm.services.withdrawalChain;

import com.webwonder.atm.enums.NoteType;
import com.webwonder.atm.exceptions.InsufficientResources;
import com.webwonder.atm.models.Withdrawal;
import com.webwonder.atm.services.NotesStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public abstract class BaseNotesChain {
    protected NotesStorageService notesStorageService;
    protected BaseNotesChain nextChain;

    public BaseNotesChain() {
    }

    @Autowired
    public BaseNotesChain(NotesStorageService notesStorageService) {
        this.notesStorageService = notesStorageService;
    }

    public Withdrawal run(Integer amount, Withdrawal withdrawal) throws InsufficientResources {
        Integer count = Math.round(amount / getValue());
        Integer captured = notesStorageService.captureMaximum(getType(), count);

        if (captured > 0) {
            withdrawal.add(getType(), captured);
            amount -= captured * getValue();
        }

        if (amount == 0) {
            return withdrawal;
        }

        if (nextChain != null) {
            return nextChain.run(amount, withdrawal);
        }

        throw new InsufficientResources();
    }

    protected abstract NoteType getType();

    protected abstract Integer getValue();

    public void setNextChain(BaseNotesChain nextChain) {
        this.nextChain = nextChain;
    }
}
