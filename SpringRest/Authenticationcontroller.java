package com.cognizant.springlearn.controller;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * AuthenticationController – doc5 MANDATORY: Create authentication service returning JWT.
 *
 * JWT Process Flow:
 *   1. Client sends username:password as Base64-encoded Authorization: Basic header
 *   2. authenticate() decodes the credentials and extracts the username
 *   3. generateJwt() creates a signed JWT with the username as subject
 *   4. JWT is returned to the client in {"token": "..."} response
 *   5. Client uses the JWT in subsequent requests: Authorization: Bearer <token>
 *
 * Test:
 *   Step 1 - Get token:
 *     curl -s -u user:pwd http://localhost:8083/authenticate
 *
 *   Step 2 - Use token:
 *     curl -s -H "Authorization: Bearer <token>" http://localhost:8083/countries
 */
@RestController
public class AuthenticationController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationController.class);

    /**
     * Secret key for HMAC-SHA256 signing.
     * Must be at least 256 bits (32 bytes) for HS256.
     * In production: store in environment variable / Vault, never in code.
     */
    static final String SECRET_KEY = "cognizant-spring-learn-jwt-secret-key-256bit";

    /**
     * doc5 MANDATORY: GET /authenticate
     *
     * @param authHeader – Spring reads "Authorization" from HTTP header automatically.
     *                     Value format: "Basic <Base64(username:password)>"
     * @return Map with key "token" containing the generated JWT
     */
    @GetMapping("/authenticate")
    public Map<String, String> authenticate(
            @RequestHeader("Authorization") String authHeader) {

        LOGGER.info("Start authenticate");
        LOGGER.debug("authHeader={}", authHeader);

        // Decode credentials and extract username
        String user = getUser(authHeader);
        LOGGER.debug("Authenticated user: {}", user);

        // Generate JWT for the authenticated user
        String token = generateJwt(user);
        LOGGER.debug("Generated token: {}", token);

        Map<String, String> response = new HashMap<>();
        response.put("token", token);

        LOGGER.info("End authenticate");
        return response;
    }

    /**
     * doc5: Decodes the Base64 Authorization header to extract the username.
     *
     * Input : "Basic dXNlcjpwd2Q="
     * Decode: "user:pwd"
     * Return: "user"
     */
    private String getUser(String authHeader) {
        LOGGER.info("Start getUser");

        // Extract Base64 portion after "Basic "
        String encodedCredentials = authHeader.substring("Basic ".length()).trim();
        LOGGER.debug("encodedCredentials={}", encodedCredentials);

        // Decode Base64 → "username:password"
        byte[] decodedBytes = Base64.getDecoder().decode(encodedCredentials);
        String decodedCredentials = new String(decodedBytes);
        LOGGER.debug("decodedCredentials={}", decodedCredentials);

        // Extract username (text before the colon)
        String user = decodedCredentials.substring(0, decodedCredentials.indexOf(":"));
        LOGGER.debug("user={}", user);

        LOGGER.info("End getUser");
        return user;
    }

    /**
     * doc5: Generates a signed JWT token for the given username.
     *
     * JWT structure:
     *   Header  : {"alg":"HS256"}
     *   Payload : {"sub":"user","iat":...,"exp":...}
     *   Signature: HMAC-SHA256(header + "." + payload, secretKey)
     *
     * Token expiry: 20 minutes (1200000 ms) from issue time.
     *
     * Note: Exercise uses jjwt 0.9.0 API (signWith(algo, "secretkey")).
     *       Updated here for jjwt 0.11.5 (signWith(Key) with Keys.hmacShaKeyFor).
     */
    private String generateJwt(String user) {
        LOGGER.info("Start generateJwt: user={}", user);

        Key key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));

        String token = Jwts.builder()
                .setSubject(user)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1_200_000)) // 20 min
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        LOGGER.debug("token={}", token);
        LOGGER.info("End generateJwt");
        return token;
    }
}
