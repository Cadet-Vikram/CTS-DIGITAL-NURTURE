package com.cognizant.loan.controller;

import com.cognizant.loan.model.Loan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * LoanController – doc2 MANDATORY: Loan microservice REST endpoint.
 *
 * Method  : GET
 * Endpoint: /loans/{number}
 * Response: Loan JSON (dummy, no backend connectivity)
 *
 * Test:
 *   Direct : curl http://localhost:8081/loans/H00987987972342
 *   Via GW : curl http://localhost:9090/loan-service/loans/H00987987972342
 */
@RestController
public class LoanController {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoanController.class);

    @GetMapping("/loans/{number}")
    public Loan getLoanDetails(@PathVariable String number) {
        LOGGER.info("Start getLoanDetails: number={}", number);

        // doc2: "Just a dummy response without any backend connectivity."
        Loan loan = new Loan(number, "car", 400000, 3258, 18);

        LOGGER.debug("Returning loan: {}", loan.getNumber());
        LOGGER.info("End getLoanDetails");
        return loan;
    }
}
