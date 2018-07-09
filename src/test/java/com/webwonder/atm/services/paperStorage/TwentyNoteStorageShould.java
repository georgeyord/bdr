package com.webwonder.atm.services.paperStorage;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class TwentyNoteStorageShould{
    TwentyNoteStorage twentyNoteStorage;

    @Test
    public void captureAllNotesIfThereAreAvailable() {
        twentyNoteStorage = new TwentyNoteStorage(5);
        Integer captured = twentyNoteStorage.captureMaximum(4);

        assertThat(captured).isEqualTo(4);
        assertThat(twentyNoteStorage.getCaptured()).isEqualTo(4);
        assertThat(twentyNoteStorage.getTotal()).isEqualTo(5);
    }

    @Test
    public void captureSomeNotesIfThereAreSomeAvailable() {
        twentyNoteStorage = new TwentyNoteStorage(3);
        Integer captured = twentyNoteStorage.captureMaximum(4);

        assertThat(captured).isEqualTo(3);
        assertThat(twentyNoteStorage.getCaptured()).isEqualTo(3);
        assertThat(twentyNoteStorage.getTotal()).isEqualTo(3);
    }

    @Test
    public void captureAllNotesIfThereAreAvailableAndComplete() {
        twentyNoteStorage = new TwentyNoteStorage(5);
        Integer captured = twentyNoteStorage.captureMaximum(4);

        twentyNoteStorage.complete(captured);

        assertThat(captured).isEqualTo(4);
        assertThat(twentyNoteStorage.getCaptured()).isEqualTo(0);
        assertThat(twentyNoteStorage.getTotal()).isEqualTo(1);
    }

    @Test
    public void captureASomeNotesIfThereAreSomeAvailableAndComplete() {
        twentyNoteStorage = new TwentyNoteStorage(3);
        Integer captured = twentyNoteStorage.captureMaximum(4);

        twentyNoteStorage.complete(captured);

        assertThat(captured).isEqualTo(3);
        assertThat(twentyNoteStorage.getCaptured()).isEqualTo(0);
        assertThat(twentyNoteStorage.getTotal()).isEqualTo(0);
    }

}