package com.cognizant.springlearn;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Country – plain POJO loaded from country.xml via Spring IoC container.
 *
 * doc1 HOL4: Demonstrates Spring XML bean instantiation and setter injection.
 *   Constructor log shows WHEN Spring creates the object.
 *   Setter logs show WHEN Spring injects each property.
 *
 * doc4: @NotNull and @Size added for input validation on POST /countries.
 *   @Valid in CountryController triggers these constraints.
 *   Violations are caught by GlobalExceptionHandler → 400 Bad Request.
 */
public class Country {

    private static final Logger LOGGER = LoggerFactory.getLogger(Country.class);

    // doc4: Validation annotations
    @NotNull(message = "Country code must not be null")
    @Size(min = 2, max = 2, message = "Country code should be 2 characters")
    private String code;

    @NotNull(message = "Country name must not be null")
    private String name;

    /** doc1 HOL4: Log here to observe when Spring creates the bean */
    public Country() {
        LOGGER.debug("Inside Country Constructor.");
    }

    public String getCode() {
        LOGGER.debug("Inside getCode. code={}", code);
        return code;
    }

    public void setCode(String code) {
        LOGGER.debug("Inside setCode. code={}", code);
        this.code = code;
    }

    public String getName() {
        LOGGER.debug("Inside getName. name={}", name);
        return name;
    }

    public void setName(String name) {
        LOGGER.debug("Inside setName. name={}", name);
        this.name = name;
    }

    @Override
    public String toString() {
        return "Country{code='" + code + "', name='" + name + "'}";
    }
}
