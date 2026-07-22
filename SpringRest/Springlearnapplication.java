package com.cognizant.springlearn;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * SpringLearnApplication – doc1 MANDATORY entry point.
 *
 * @SpringBootApplication combines:
 *   @Configuration        – marks this as a source of bean definitions
 *   @EnableAutoConfiguration – auto-configures Spring based on classpath JARs
 *   @ComponentScan        – scans com.cognizant.springlearn for @Controller, @Service etc.
 *
 * SpringApplication.run() starts the embedded Tomcat server and initialises the context.
 *
 * Additional demonstration methods called from main():
 *   displayDate()      – doc1 HOL2: Load SimpleDateFormat from Spring XML
 *   displayCountry()   – doc1 HOL4: Load Country bean from Spring XML (setter injection)
 *   displayCountries() – doc1 HOL6: Load list of countries from Spring XML
 */
@SpringBootApplication
public class SpringLearnApplication {

    private static final Logger LOGGER = LoggerFactory.getLogger(SpringLearnApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(SpringLearnApplication.class, args);
        LOGGER.info("Inside main – Spring Boot application started on port 8083");

        // doc1 HOL2: Load date format bean from XML
        displayDate();

        // doc1 HOL4: Load single country bean from XML
        displayCountry();

        // doc1 HOL5: Demonstrates singleton vs prototype scope
        displayCountryScopes();

        // doc1 HOL6: Load list of countries from XML
        displayCountries();
    }

    // ── doc1 HOL2: Load SimpleDateFormat from date-format.xml ────────────────
    static void displayDate() {
        LOGGER.info("Start displayDate");

        ApplicationContext context = new ClassPathXmlApplicationContext("date-format.xml");
        SimpleDateFormat format = context.getBean("dateFormat", SimpleDateFormat.class);

        try {
            Date date = format.parse("31/12/2018");
            LOGGER.debug("Parsed date: {}", date);
            System.out.println("Parsed date: " + date);
        } catch (ParseException e) {
            LOGGER.error("Date parsing failed: {}", e.getMessage());
        }

        LOGGER.info("End displayDate");
    }

    // ── doc1 HOL4: Load Country bean from country.xml ────────────────────────
    static void displayCountry() {
        LOGGER.info("Start displayCountry");

        ApplicationContext context = new ClassPathXmlApplicationContext("country.xml");

        // getBean() retrieves the singleton/prototype bean by ID
        Country country = context.getBean("country", Country.class);
        LOGGER.debug("Country: {}", country.toString());

        LOGGER.info("End displayCountry");
    }

    // ── doc1 HOL5: Singleton vs Prototype scope demonstration ─────────────────
    static void displayCountryScopes() {
        LOGGER.info("Start displayCountryScopes");

        ApplicationContext context = new ClassPathXmlApplicationContext("country.xml");

        // With scope="prototype": constructor is called TWICE (two instances created)
        // With scope="singleton": constructor is called ONCE (same instance returned)
        Country country1 = context.getBean("country", Country.class);
        Country country2 = context.getBean("country", Country.class);

        LOGGER.debug("Same instance? {}", (country1 == country2));
        // scope="prototype" → false (different objects)
        // scope="singleton" → true (same object)

        LOGGER.info("End displayCountryScopes");
    }

    // ── doc1 HOL6: Load list of countries from country.xml ───────────────────
    static void displayCountries() {
        LOGGER.info("Start displayCountries");

        ApplicationContext context = new ClassPathXmlApplicationContext("country.xml");
        List<Country> countries = (List<Country>) context.getBean("countryList", ArrayList.class);

        LOGGER.debug("Countries loaded: {}", countries);
        countries.forEach(c -> LOGGER.debug("  {}", c));

        LOGGER.info("End displayCountries");
    }
}
