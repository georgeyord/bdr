package com.webwonder.atm.controllers;

import com.webwonder.atm.exceptions.InsufficientResources;
import com.webwonder.atm.models.Withdrawal;
import com.webwonder.atm.services.WithdrawalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(
    consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
    produces = MediaType.APPLICATION_JSON_UTF8_VALUE
)
public class WithdrawalController {
    public static final String WITHDRAW_ACTION = "/withdraw";

    private WithdrawalService withdrawalService;

    @Autowired
    public WithdrawalController(WithdrawalService withdrawalService) {
        this.withdrawalService = withdrawalService;
    }

    @RequestMapping(method = RequestMethod.POST, value = WITHDRAW_ACTION)
    @ResponseBody
    public ResponseEntity<Withdrawal> withdrawAction(@RequestBody Integer amount) {
        try {
            Withdrawal withdrawal = withdrawalService.run(amount);
            return ResponseEntity.ok().body(withdrawal);
        } catch (InsufficientResources insufficientResources) {
            return ResponseEntity.badRequest().build();
        }
    }
}
