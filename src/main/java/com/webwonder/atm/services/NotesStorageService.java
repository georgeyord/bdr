package com.webwonder.atm.services;

import com.webwonder.atm.enums.NoteType;
import com.webwonder.atm.models.Withdrawal;
import com.webwonder.atm.services.paperStorage.BaseNoteStorage;
import com.webwonder.atm.services.paperStorage.FiftyNoteStorage;
import com.webwonder.atm.services.paperStorage.TwentyNoteStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class NotesStorageService {
    private Map<NoteType, BaseNoteStorage> storages = new HashMap<>();

    @Autowired
    public NotesStorageService(FiftyNoteStorage fiftyNoteStorage, TwentyNoteStorage twentyNoteStorage) {
        storages.put(FiftyNoteStorage.TYPE, fiftyNoteStorage);
        storages.put(TwentyNoteStorage.TYPE, twentyNoteStorage);
    }

    public Map<NoteType, BaseNoteStorage> getStorages() {
        return storages;
    }

    public BaseNoteStorage getStorage(NoteType type) {
        return storages.get(type);
    }

    public void resetStorage(NoteType type, BaseNoteStorage storage) {
        storages.put(type, storage);
    }

    public Integer captureMaximum(NoteType type, Integer count) {
        return storages.get(type).captureMaximum(count);
    }

    public void complete(Withdrawal withdrawal) {
        storages.entrySet().stream()
            .filter(entry -> withdrawal.getCount(entry.getKey()) != null)
            .forEach(entry -> entry.getValue().complete(withdrawal.getCount(entry.getKey())));
    }

    public void abort(Withdrawal withdrawal) {
        storages.entrySet().stream()
            .filter(entry -> withdrawal.getCount(entry.getKey()) != null)
            .forEach(entry -> entry.getValue().abort(withdrawal.getCount(entry.getKey())));
    }
}
