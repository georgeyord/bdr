package com.webwonder.atm.services;

import com.webwonder.atm.exceptions.InsufficientResources;
import com.webwonder.atm.models.Withdrawal;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class WithdrawalServiceShould {

    @Mock
    WithdrawalChain withdrawalChain;
    @Mock
    NotesStorageService notesStorageService;
    @Mock
    Withdrawal expectedWithdrawal;

    WithdrawalService withdrawalService;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        withdrawalService = new WithdrawalService(withdrawalChain, notesStorageService);
    }

    @Test
    public void allwaysRunChain() throws InsufficientResources {
        withdrawalService.run(250);

        verify(withdrawalChain).run(anyInt(), any(Withdrawal.class));
    }

    @Test
    public void completeNotesWithdrawalWhenChainRunsSuccessfully() throws InsufficientResources {
        when(withdrawalChain.run(anyInt(), any(Withdrawal.class))).thenReturn(expectedWithdrawal);

        withdrawalService.run(250);

        verify(notesStorageService).complete(expectedWithdrawal);
    }

    @Test(expected = InsufficientResources.class)
    public void abortNotesWithdrawalWhenChainIsNotSuccesfull() throws InsufficientResources {
        doThrow(new InsufficientResources()).when(withdrawalChain).run(anyInt(), any(Withdrawal.class));

        withdrawalService.run(250);

        verify(notesStorageService).abort(expectedWithdrawal);

    }
}