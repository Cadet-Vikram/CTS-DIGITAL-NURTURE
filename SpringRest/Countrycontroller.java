package com.cognizant.springlearn.controller;

import com.cognizant.springlearn.Country;
import com.cognizant.springlearn.service.CountryService;
import com.cognizant.springlearn.service.exception.CountryNotFoundException;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * CountryController – covers mandatory doc2 + doc4 exercises.
 *
 * doc2 MANDATORY endpoints:
 *   GET  /country          → getCountryIndia()    (returns India from XML)
 *   GET  /countries        → getAllCountries()
 *   GET  /countries/{code} → getCountry(code)     (case-insensitive, throws 404 if not found)
 *
 * doc4 endpoints:
 *   POST /countries        → addCountry()          (@RequestBody @Valid + GlobalExceptionHandler)
 *   PUT  /countries        → updateCountry()
 *
 * URL convention (doc4): @RequestMapping("/countries") at class level so all
 * resource-specific URLs share the same base path.
 *
 * Test with curl:
 *   curl -s -u user:pwd http://localhost:8083/countries
 *   curl -s -u user:pwd http://localhost:8083/countries/in
 *   curl -s -u user:pwd -X POST -H 'Content-Type: application/json' \
 *        -d '{"code":"AU","name":"Australia"}' http://localhost:8083/countries
 */
@RestController
@RequestMapping("/countries")
public class CountryController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CountryController.class);

    public CountryController() {
        LOGGER.debug("Inside CountryController Constructor");
    }

    @Autowired
    private CountryService countryService;

    // ── doc2 MANDATORY: /country (singular) → returns India ──────────────────
    /**
     * Returns India country loaded from country.xml.
     * Note: @GetMapping here is on /country NOT /countries because
     * the class-level @RequestMapping is /countries and we need a
     * separate /country endpoint to match the exercise + MockMVC test.
     */
    @GetMapping("/india")
    public Country getCountryIndia() {
        LOGGER.info("Start getCountryIndia");
        Country country = countryService.getCountryIndia();
        LOGGER.debug("country={}", country);
        LOGGER.info("End getCountryIndia");
        return country;
    }

    // ── doc2 MANDATORY: GET /countries → all countries ────────────────────────
    @GetMapping
    public List<Country> getAllCountries() {
        LOGGER.info("Start getAllCountries");
        List<Country> countries = countryService.getAllCountries();
        LOGGER.debug("countries={}", countries);
        LOGGER.info("End getAllCountries");
        return countries;
    }

    // ── doc2 MANDATORY: GET /countries/{code} → country by code ──────────────
    /**
     * @PathVariable extracts {code} from the URL path.
     * CountryService.getCountry() does case-insensitive matching.
     * If not found, CountryNotFoundException is thrown → HTTP 404 (via @ResponseStatus).
     *
     * Sample: GET /countries/in  → {"code":"IN","name":"India"}
     *         GET /countries/az  → 404 Country not found
     */
    @GetMapping("/{code}")
    public Country getCountry(@PathVariable String code) throws CountryNotFoundException {
        LOGGER.info("Start getCountry: code={}", code);
        Country country = countryService.getCountry(code);
        LOGGER.debug("country={}", country);
        LOGGER.info("End getCountry");
        return country;
    }

    // ── doc4: POST /countries → add a country ─────────────────────────────────
    /**
     * @RequestBody maps the JSON request body to a Country bean.
     * @Valid triggers @NotNull and @Size validation on the Country bean.
     * If validation fails, GlobalExceptionHandler returns HTTP 400.
     *
     * curl -s -u user:pwd -X POST -H 'Content-Type: application/json' \
     *      -d '{"code":"AU","name":"Australia"}' http://localhost:8083/countries
     */
    @PostMapping
    public Country addCountry(@RequestBody @Valid Country country) {
        LOGGER.info("Start addCountry");
        LOGGER.debug("country={}", country);
        // In a real application: countryService.addCountry(country)
        // Here we just echo back the received country (demo)
        LOGGER.info("End addCountry");
        return country;
    }

    // ── doc4: PUT /countries → update a country ───────────────────────────────
    @PutMapping
    public Country updateCountry(@RequestBody @Valid Country country) {
        LOGGER.info("Start updateCountry");
        LOGGER.debug("country={}", country);
        LOGGER.info("End updateCountry");
        return country;
    }

    // ── doc4: DELETE /countries/{code} ────────────────────────────────────────
    @DeleteMapping("/{code}")
    public void deleteCountry(@PathVariable String code) {
        LOGGER.info("Start deleteCountry: code={}", code);
        // In a real application: countryService.deleteCountry(code)
        LOGGER.info("End deleteCountry");
    }
}
