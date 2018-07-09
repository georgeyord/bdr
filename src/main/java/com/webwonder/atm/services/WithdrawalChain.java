package com.webwonder.atm.services;

import com.webwonder.atm.exceptions.InsufficientResources;
import com.webwonder.atm.models.Withdrawal;
import com.webwonder.atm.services.withdrawalChain.FiftyNotesChain;
import com.webwonder.atm.services.withdrawalChain.TwentyNotesChain;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WithdrawalChain {

    private FiftyNotesChain fiftyNotesChain;

    @Autowired
    public WithdrawalChain(FiftyNotesChain fiftyNotesChain, TwentyNotesChain twentyNotesChain) {
        this.fiftyNotesChain = fiftyNotesChain;
        this.fiftyNotesChain.setNextChain(twentyNotesChain);
    }

    public Withdrawal run(int amount, Withdrawal withdrawal) throws InsufficientResources {
        return fiftyNotesChain.run(amount, withdrawal);
    }
}
