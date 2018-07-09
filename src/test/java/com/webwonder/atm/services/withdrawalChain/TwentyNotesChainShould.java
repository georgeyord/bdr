package com.webwonder.atm.services.withdrawalChain;

import com.webwonder.atm.enums.NoteType;
import com.webwonder.atm.exceptions.InsufficientResources;
import com.webwonder.atm.models.Withdrawal;
import com.webwonder.atm.services.NotesStorageService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.entry;
import static org.mockito.Mockito.when;

public class TwentyNotesChainShould {

    @Mock
    NotesStorageService notesStorageService;
    @Mock
    Withdrawal expectedWithdrawal;

    private TwentyNotesChain twentyNotesChain;
    private Withdrawal withdrawal;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        withdrawal = new Withdrawal();
        twentyNotesChain = new TwentyNotesChain(notesStorageService);
    }

    @Test
    public void addFiveTwentyNotesToWithdrawWhenAmountAskedIs100AndPaperStorageServiceHasCapacityInTwentys() throws InsufficientResources {
        when(notesStorageService.captureMaximum(NoteType.TWENTY_NOTE, 5)).thenReturn(5);

        Withdrawal actual = twentyNotesChain.run(100, withdrawal);

        assertThat(actual.getBreakdown()).contains(entry(NoteType.TWENTY_NOTE, 5));
        assertThat(actual.getBreakdown()).doesNotContainKeys(NoteType.FIFTY_NOTE);
    }

    @Test(expected = InsufficientResources.class)
    public void addFourTwentyNotesToWithdrawAndCallTwentyNoteChainWhenAmountAskedIs300AndPaperStorageServiceHasCapacityOfFourInTwentys() throws InsufficientResources {
        when(notesStorageService.captureMaximum(NoteType.FIFTY_NOTE, 5)).thenReturn(4);

        twentyNotesChain.run(100, withdrawal);
    }
}