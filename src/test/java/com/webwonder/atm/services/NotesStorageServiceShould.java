package com.webwonder.atm.services;

import com.webwonder.atm.enums.NoteType;
import com.webwonder.atm.models.Withdrawal;
import com.webwonder.atm.services.paperStorage.BaseNoteStorage;
import com.webwonder.atm.services.paperStorage.FiftyNoteStorage;
import com.webwonder.atm.services.paperStorage.TwentyNoteStorage;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class NotesStorageServiceShould {

    NotesStorageService notesStorageService;

    @Mock
    FiftyNoteStorage fiftyNoteStorage;
    @Mock
    TwentyNoteStorage twentyNoteStorage;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        notesStorageService = new NotesStorageService(fiftyNoteStorage, twentyNoteStorage);
    }

    @Test
    public void beAbleToGetTheCurrentStateOfNoteStorages() {
        Map<NoteType, BaseNoteStorage> storages = notesStorageService.getStorages();

        assertThat(storages).isNotEmpty();
    }

    @Test
    public void beAbleToGetTheCurrentStateOfTwentyNoteStorage() {
        when(twentyNoteStorage.getTotal()).thenReturn(2);
        when(twentyNoteStorage.getCaptured()).thenReturn(0);

        BaseNoteStorage storage = notesStorageService.getStorage(NoteType.TWENTY_NOTE);

        assertThat(storage).isNotNull();
        assertThat(storage.getTotal()).isEqualTo(2);
        assertThat(storage.getCaptured()).isEqualTo(0);
    }

    @Test
    public void beAbleToGetTheCurrentStateOfFiftyNoteStorage() {
        when(fiftyNoteStorage.getTotal()).thenReturn(2);
        when(fiftyNoteStorage.getCaptured()).thenReturn(0);

        BaseNoteStorage storage = notesStorageService.getStorage(NoteType.FIFTY_NOTE);

        assertThat(storage).isNotNull();
        assertThat(storage.getTotal()).isEqualTo(2);
        assertThat(storage.getCaptured()).isEqualTo(0);
    }

    @Test
    public void beAbleToCaptureAllNotesForASpecificNoteType() {
        when(fiftyNoteStorage.captureMaximum(anyInt())).thenReturn(4);

        Integer captured = notesStorageService.captureMaximum(NoteType.FIFTY_NOTE, 4);

        assertThat(captured).isEqualTo(4);
    }

    @Test
    public void beAbleToCaptureAValidAmountOfNotesForASpecificNoteType() {
        when(fiftyNoteStorage.captureMaximum(anyInt())).thenReturn(2);

        Integer captured = notesStorageService.captureMaximum(NoteType.FIFTY_NOTE, 4);

        assertThat(captured).isEqualTo(2);
    }

    @Test
    public void beAbleToCompleteATransaction() {
        Withdrawal withdrawal = new Withdrawal();
        withdrawal.add(NoteType.FIFTY_NOTE, 4);
        notesStorageService.complete(withdrawal);

        verify(fiftyNoteStorage).complete(4);
        verify(twentyNoteStorage, never()).complete(anyInt());
    }

    @Test
    public void beAbleToAbortATransaction() {
        Withdrawal withdrawal = new Withdrawal();
        withdrawal.add(NoteType.FIFTY_NOTE, 4);
        notesStorageService.abort(withdrawal);

        verify(fiftyNoteStorage).abort(4);
        verify(twentyNoteStorage, never()).complete(anyInt());
    }

    @Test
    public void beAbleToResetAStorage() {
        notesStorageService.resetStorage(NoteType.FIFTY_NOTE, new FiftyNoteStorage(100));

        BaseNoteStorage storage = notesStorageService.getStorage(NoteType.FIFTY_NOTE);
        assertThat(storage.getTotal()).isEqualTo(100);
    }
}