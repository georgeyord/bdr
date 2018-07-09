package com.webwonder.atm.services;

import com.webwonder.atm.exceptions.InsufficientResources;
import com.webwonder.atm.models.Withdrawal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WithdrawalService {

    private NotesStorageService notesStorageService;
    private WithdrawalChain withdrawalChain;

    @Autowired
    public WithdrawalService(WithdrawalChain withdrawalChain, NotesStorageService notesStorageService) {
        this.withdrawalChain = withdrawalChain;
        this.notesStorageService = notesStorageService;
    }

    public Withdrawal run(Integer amount) throws InsufficientResources {
        Withdrawal withdrawal = new Withdrawal();
        try {
            withdrawal = withdrawalChain.run(amount, withdrawal);
        } catch (InsufficientResources insufficientResources) {
            notesStorageService.abort(withdrawal);
            throw insufficientResources;
        }

        notesStorageService.complete(withdrawal);
        return withdrawal;
    }
}
