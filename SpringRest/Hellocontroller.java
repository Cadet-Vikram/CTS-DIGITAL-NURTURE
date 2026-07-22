package com.cognizant.springlearn.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * HelloController – doc2 MANDATORY: Hello World RESTful Web Service.
 *
 * Method : GET
 * URL    : /hello
 * Returns: plain text "Hello World!!"
 *
 * Test:
 *   Browser : http://localhost:8083/hello
 *   curl    : curl -s -u user:pwd http://localhost:8083/hello
 */
@RestController
public class HelloController {

    private static final Logger LOGGER = LoggerFactory.getLogger(HelloController.class);

    /**
     * doc2: @RestController + @GetMapping combination means:
     *   - Spring registers this as a bean
     *   - GET /hello requests are dispatched here by DispatcherServlet
     *   - Return value is written directly to HTTP response body (not a view name)
     */
    @GetMapping("/hello")
    public String sayHello() {
        LOGGER.info("Start sayHello");
        String response = "Hello World!!";
        LOGGER.debug("response={}", response);
        LOGGER.info("End sayHello");
        return response;
    }
}
