package com.cognizant.account.controller;

import com.cognizant.account.model.Account;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * AccountController – doc2 MANDATORY: Account microservice REST endpoint.
 *
 * Method  : GET
 * Endpoint: /accounts/{number}
 * Response: Account JSON (dummy, no backend connectivity)
 *
 * Test:
 *   Direct : curl http://localhost:8080/accounts/00987987973432
 *   Via GW : curl http://localhost:9090/account-service/accounts/00987987973432
 */
@RestController
public class AccountController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AccountController.class);

    /**
     * Returns dummy account details for the given account number.
     * In a real system this would query a database via a service layer.
     *
     * doc2: "Just a dummy response without any backend connectivity."
     */
    @GetMapping("/accounts/{number}")
    public Account getAccountDetails(@PathVariable String number) {
        LOGGER.info("Start getAccountDetails: number={}", number);

        // Dummy response — matches the exercise specification exactly
        Account account = new Account(number, "savings", 234343);

        LOGGER.debug("Returning account: {}", account.getNumber());
        LOGGER.info("End getAccountDetails");
        return account;
    }
}
