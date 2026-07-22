package com.cognizant.ormlearn.service;

import com.cognizant.ormlearn.model.Country;
import com.cognizant.ormlearn.repository.CountryRepository;
import com.cognizant.ormlearn.service.exception.CountryNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * CountryService – business-logic layer for Country operations.
 *
 * Exercises covered:
 *   Hands-on 1 (doc 1) MANDATORY  – getAllCountries() : Quick Example
 *   Hands-on 5 (doc 1) Additional – Full CRUD (add, update, delete, find by code)
 *   Hands-on 6 (doc 1) Additional – findCountryByCode() with CountryNotFoundException
 *   Hands-on 7 (doc 1) Additional – addCountry()
 *   Hands-on 8 (doc 1) Additional – updateCountry()
 *   Hands-on 9 (doc 1) Additional – deleteCountry()
 *   Hands-on 1 (doc 2) Additional – searchByName(), searchByNameSorted(), searchByPrefix()
 *
 * @Transactional note (from exercise):
 *   Spring creates a Hibernate Session, begins a transaction, runs the method,
 *   then commits and closes the session automatically.
 *   Without @Transactional, lazy-loaded relationships would throw
 *   LazyInitializationException because the session closes before access.
 */
@Service
public class CountryService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CountryService.class);

    @Autowired
    private CountryRepository countryRepository;

    // ── Hands-on 1 (doc 1): MANDATORY – Quick Example ────────────────────────

    /**
     * Returns all countries from the database.
     * countryRepository.findAll() is provided by JpaRepository — no SQL needed.
     */
    @Transactional
    public List<Country> getAllCountries() {
        LOGGER.info("Start getAllCountries");
        List<Country> countries = countryRepository.findAll();
        LOGGER.debug("countries={}", countries);
        LOGGER.info("End getAllCountries");
        return countries;
    }

    // ── Hands-on 6 (doc 1): Find country by code ─────────────────────────────

    /**
     * Finds a single country by its 2-letter code.
     * findById() returns an Optional — we check if present and throw
     * CountryNotFoundException if the code does not exist.
     */
    @Transactional
    public Country findCountryByCode(String countryCode) throws CountryNotFoundException {
        LOGGER.info("Start findCountryByCode: {}", countryCode);
        Optional<Country> result = countryRepository.findById(countryCode);
        if (!result.isPresent()) {
            throw new CountryNotFoundException(countryCode);
        }
        Country country = result.get();
        LOGGER.debug("Country:{}", country);
        LOGGER.info("End findCountryByCode");
        return country;
    }

    // ── Hands-on 7 (doc 1): Add a new country ────────────────────────────────

    /**
     * Saves a new country to the database.
     * save() performs INSERT when entity has no existing ID in DB.
     */
    @Transactional
    public void addCountry(Country country) {
        LOGGER.info("Start addCountry: {}", country);
        countryRepository.save(country);
        LOGGER.info("End addCountry – country added successfully");
    }

    // ── Hands-on 8 (doc 1): Update a country ─────────────────────────────────

    /**
     * Updates the name of an existing country identified by code.
     * Pattern: fetch → modify → save (Hibernate detects the change and UPDATEs).
     */
    @Transactional
    public void updateCountry(String code, String newName) throws CountryNotFoundException {
        LOGGER.info("Start updateCountry: code={}, newName={}", code, newName);
        Country country = findCountryByCode(code);
        country.setName(newName);
        countryRepository.save(country);
        LOGGER.info("End updateCountry – country updated successfully");
    }

    // ── Hands-on 9 (doc 1): Delete a country ─────────────────────────────────

    /**
     * Deletes a country by code.
     * deleteById() issues a DELETE FROM country WHERE co_code = ?
     */
    @Transactional
    public void deleteCountry(String code) {
        LOGGER.info("Start deleteCountry: code={}", code);
        countryRepository.deleteById(code);
        LOGGER.info("End deleteCountry – country deleted successfully");
    }

    // ── Hands-on 1 (doc 2): Query Methods ────────────────────────────────────

    /**
     * Search countries by partial name (case-insensitive).
     * Demo: searching "ou" returns Bouvet Island, Djibouti, Guadeloupe, etc.
     */
    @Transactional
    public List<Country> searchByName(String text) {
        LOGGER.info("Start searchByName: text={}", text);
        List<Country> results = countryRepository.findByNameContainingIgnoreCase(text);
        LOGGER.debug("Results: {}", results);
        return results;
    }

    /**
     * Same search but results sorted alphabetically.
     */
    @Transactional
    public List<Country> searchByNameSorted(String text) {
        LOGGER.info("Start searchByNameSorted: text={}", text);
        return countryRepository.findByNameContainingIgnoreCaseOrderByNameAsc(text);
    }

    /**
     * Countries starting with a given letter/prefix.
     * Demo: "Z" returns Zambia, Zimbabwe.
     */
    @Transactional
    public List<Country> searchByPrefix(String prefix) {
        LOGGER.info("Start searchByPrefix: prefix={}", prefix);
        return countryRepository.findByNameStartingWith(prefix);
    }
}
