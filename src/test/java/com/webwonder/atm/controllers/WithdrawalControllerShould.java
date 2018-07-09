package com.webwonder.atm.controllers;

import com.webwonder.atm.exceptions.InsufficientResources;
import com.webwonder.atm.models.Withdrawal;
import com.webwonder.atm.services.WithdrawalService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class WithdrawalControllerShould {

    WithdrawalController withdrawalController;
    @Mock
    WithdrawalService withdrawalService;
    @Mock
    Withdrawal withdrawal;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        withdrawalController = new WithdrawalController(withdrawalService);
    }

    @Test
    public void alwaysCallWithdrawService() throws InsufficientResources {
        when(withdrawalService.run(anyInt())).thenReturn(withdrawal);

        withdrawalController.withdrawAction(250);

        verify(withdrawalService).run(anyInt());
    }

    @Test
    public void returnAValidBreakdownIfWithdrawServiceResponseIsValid() throws InsufficientResources {
        when(withdrawalService.run(anyInt())).thenReturn(withdrawal);

        ResponseEntity<Withdrawal> response = withdrawalController.withdrawAction(250);

        assert (response.getStatusCode()).is2xxSuccessful();
        assertEquals(withdrawal, response.getBody());
    }

    @Test
    public void returnA4xxErrorIfWithdrawServiceRespondsWithError() throws InsufficientResources {
        doThrow(new InsufficientResources()).when(withdrawalService).run(anyInt());

        ResponseEntity<Withdrawal> response = withdrawalController.withdrawAction(250);

        assert (response.getStatusCode()).is4xxClientError();
    }
}