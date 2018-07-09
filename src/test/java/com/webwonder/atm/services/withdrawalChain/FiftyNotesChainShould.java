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

public class FiftyNotesChainShould {

    @Mock
    NotesStorageService notesStorageService;
    @Mock
    TwentyNotesChain twentyNotesChain;
    @Mock
    Withdrawal expectedWithdrawal;

    private FiftyNotesChain fiftyNotesChain;
    private Withdrawal withdrawal;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        withdrawal = new Withdrawal();
        fiftyNotesChain = new FiftyNotesChain(notesStorageService);
        fiftyNotesChain.setNextChain(twentyNotesChain);
    }

    @Test
    public void addFiveFiftyNotesToWithdrawWhenAmountAskedIs250AndPaperStorageServiceHasCapacityInFiftys() throws InsufficientResources {
        when(notesStorageService.captureMaximum(NoteType.FIFTY_NOTE, 5)).thenReturn(5);

        Withdrawal actual = fiftyNotesChain.run(250, withdrawal);

        assertThat(actual.getBreakdown()).contains(entry(NoteType.FIFTY_NOTE, 5));
        assertThat(actual.getBreakdown()).doesNotContainKeys(NoteType.TWENTY_NOTE);

        // TODO: never
//        verify(twentyNotesChain).run(any(NoteType.class),anyInt())
    }

    @Test
    public void addFiveFiftyNotesToWithdrawAndCallTwentyNoteChainWhenAmountAskedIs290AndPaperStorageServiceHasCapacityInFiftys() throws InsufficientResources {
        when(notesStorageService.captureMaximum(NoteType.FIFTY_NOTE, 5)).thenReturn(5);
        when(twentyNotesChain.run(40, withdrawal)).thenReturn(expectedWithdrawal);

        Withdrawal actual = fiftyNotesChain.run(290, withdrawal);

        assertThat(actual).isEqualTo(expectedWithdrawal);
    }

    @Test
    public void addFourFiftyNotesToWithdrawAndCallTwentyNoteChainWhenAmountAskedIs300AndPaperStorageServiceHasCapacityOfFourInFiftys() throws InsufficientResources {
        when(notesStorageService.captureMaximum(NoteType.FIFTY_NOTE, 6)).thenReturn(4);
        when(twentyNotesChain.run(100, withdrawal)).thenReturn(expectedWithdrawal);

        Withdrawal actual = fiftyNotesChain.run(300, withdrawal);

        assertThat(actual).isEqualTo(expectedWithdrawal);
    }
}