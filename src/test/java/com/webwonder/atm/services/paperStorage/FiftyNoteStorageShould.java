package com.webwonder.atm.services.paperStorage;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;


public class FiftyNoteStorageShould {
    FiftyNoteStorage fiftyNoteStorage;

    @Test
    public void captureAllNotesIfThereAreAvailable() {
        fiftyNoteStorage = new FiftyNoteStorage(5);
        Integer captured = fiftyNoteStorage.captureMaximum(4);

        assertThat(captured).isEqualTo(4);
        assertThat(fiftyNoteStorage.getCaptured()).isEqualTo(4);
        assertThat(fiftyNoteStorage.getTotal()).isEqualTo(5);
    }

    @Test
    public void captureSomeNotesIfThereAreSomeAvailable() {
        fiftyNoteStorage = new FiftyNoteStorage(3);
        Integer captured = fiftyNoteStorage.captureMaximum(4);

        assertThat(captured).isEqualTo(3);
        assertThat(fiftyNoteStorage.getCaptured()).isEqualTo(3);
        assertThat(fiftyNoteStorage.getTotal()).isEqualTo(3);
    }

    @Test
    public void captureAllNotesIfThereAreAvailableAndComplete() {
        fiftyNoteStorage = new FiftyNoteStorage(5);
        Integer captured = fiftyNoteStorage.captureMaximum(4);

        fiftyNoteStorage.complete(captured);

        assertThat(captured).isEqualTo(4);
        assertThat(fiftyNoteStorage.getCaptured()).isEqualTo(0);
        assertThat(fiftyNoteStorage.getTotal()).isEqualTo(1);
    }

    @Test
    public void captureASomeNotesIfThereAreSomeAvailableAndComplete() {
        fiftyNoteStorage = new FiftyNoteStorage(3);
        Integer captured = fiftyNoteStorage.captureMaximum(4);

        fiftyNoteStorage.complete(captured);

        assertThat(captured).isEqualTo(3);
        assertThat(fiftyNoteStorage.getCaptured()).isEqualTo(0);
        assertThat(fiftyNoteStorage.getTotal()).isEqualTo(0);
    }

    @Test
    public void captureAllNotesIfThereAreAvailableAndAbort() {
        fiftyNoteStorage = new FiftyNoteStorage(5);
        Integer captured = fiftyNoteStorage.captureMaximum(4);

        fiftyNoteStorage.abort(captured);

        assertThat(captured).isEqualTo(4);
        assertThat(fiftyNoteStorage.getCaptured()).isEqualTo(0);
        assertThat(fiftyNoteStorage.getTotal()).isEqualTo(5);
    }
}