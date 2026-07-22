package com.cognizant.springlearn;

import com.cognizant.springlearn.controller.CountryController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * SpringLearnApplicationTests – doc2: MockMVC tests for Country REST service.
 *
 * @SpringBootTest        – loads the full application context
 * @AutoConfigureMockMvc  – auto-configures MockMvc (no server needed)
 *
 * @WithMockUser – bypasses Spring Security for the test (the exercise
 * originally tested without security; added here since doc5 security is active).
 *
 * Run with: mvn test
 *       or: right-click > Run As > JUnit Test
 */
@SpringBootTest
@AutoConfigureMockMvc
class SpringLearnApplicationTests {

    /** doc2: Autowire CountryController to verify it loads correctly */
    @Autowired
    private CountryController countryController;

    /** doc2: Inject MockMvc for simulating HTTP requests without a server */
    @Autowired
    private MockMvc mvc;

    // ── Test 1: Verify CountryController is loaded ────────────────────────────
    /**
     * doc2: contextLoads() – checks if CountryController bean is created.
     * If Spring context fails or CountryController is not found, this test fails.
     */
    @Test
    void contextLoads() {
        assertNotNull(countryController,
                "CountryController should be loaded by the Spring context");
    }

    // ── Test 2: GET /countries – all countries ────────────────────────────────
    /**
     * doc2: Tests getAllCountries() endpoint.
     *
     * Verifies:
     *   - HTTP 200 OK
     *   - Response is a JSON array
     *   - First element has a "code" field
     */
    @Test
    @WithMockUser(username = "user", roles = "USER")
    void testGetAllCountries() throws Exception {
        ResultActions actions = mvc.perform(get("/countries"));
        actions.andExpect(status().isOk());
        actions.andExpect(jsonPath("$").isArray());
        actions.andExpect(jsonPath("$[0].code").exists());
    }

    // ── Test 3: GET /countries/in – specific country ──────────────────────────
    /**
     * doc2 MANDATORY MockMVC test: testGetCountry()
     *
     * Progressively built up in the exercise as shown in the doc:
     *   Step 1: mvc.perform(get("/countries/in"))
     *   Step 2: .andExpect(status().isOk())
     *   Step 3: .andExpect(jsonPath("$.code").exists())
     *   Step 4: .andExpect(jsonPath("$.code").value("IN"))
     *   Step 5: .andExpect(jsonPath("$.name").value("India"))
     */
    @Test
    @WithMockUser(username = "user", roles = "USER")
    void testGetCountry() throws Exception {
        ResultActions actions = mvc.perform(get("/countries/in"));

        actions.andExpect(status().isOk());
        actions.andExpect(jsonPath("$.code").exists());
        actions.andExpect(jsonPath("$.code").value("IN"));
        actions.andExpect(jsonPath("$.name").exists());
        actions.andExpect(jsonPath("$.name").value("India"));
    }

    // ── Test 4: GET /countries/az – country not found → 404 ──────────────────
    /**
     * doc2: testGetCountryException()
     *
     * Verifies that requesting a non-existent country code returns HTTP 404.
     * CountryNotFoundException is annotated with @ResponseStatus(NOT_FOUND).
     */
    @Test
    @WithMockUser(username = "user", roles = "USER")
    void testGetCountryException() throws Exception {
        ResultActions actions = mvc.perform(get("/countries/az"));
        actions.andExpect(status().isNotFound());
    }

    // ── Test 5: GET /hello ────────────────────────────────────────────────────
    @Test
    @WithMockUser
    void testHelloWorld() throws Exception {
        ResultActions actions = mvc.perform(get("/hello"));
        actions.andExpect(status().isOk());
        actions.andExpect(content().string("Hello World!!"));
    }
}
