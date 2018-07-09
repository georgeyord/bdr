package com.webwonder.atm.services;

import com.webwonder.atm.exceptions.InsufficientResources;
import com.webwonder.atm.models.Withdrawal;
import com.webwonder.atm.services.withdrawalChain.FiftyNotesChain;
import com.webwonder.atm.services.withdrawalChain.TwentyNotesChain;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class WithdrawalChainShould {

    @Mock
    FiftyNotesChain fiftyNotesChain;
    @Mock
    TwentyNotesChain twentyNotesChain;
    @Mock
    Withdrawal withdrawal;

    WithdrawalChain withdrawalChain;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        withdrawalChain = new WithdrawalChain(fiftyNotesChain, twentyNotesChain);
    }

    @Test
    public void alwaysRunFirstChain() throws InsufficientResources {
        withdrawalChain.run(250, withdrawal);

        verify(fiftyNotesChain).run(anyInt(), any(Withdrawal.class));
    }

    @Test
    public void respondWithAValidWithdrawIfChainResponseIsValid() throws InsufficientResources {
        when(fiftyNotesChain.run(anyInt(), any(Withdrawal.class))).thenReturn(withdrawal);

        Withdrawal actual = withdrawalChain.run(250, withdrawal);

        assertThat(actual).isEqualTo(withdrawal);
    }

    @Test(expected = InsufficientResources.class)
    public void respondWithAnExceptionIfThereAreNoResources() throws InsufficientResources {
        doThrow(new InsufficientResources()).when(fiftyNotesChain).run(anyInt(), any(Withdrawal.class));

        withdrawalChain.run(250, withdrawal);
    }

}