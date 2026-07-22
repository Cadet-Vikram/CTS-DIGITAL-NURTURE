package com.cognizant.springlearn.security;

import com.cognizant.springlearn.controller.AuthenticationController;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.ArrayList;

/**
 * JwtAuthorizationFilter – doc5: Validates JWT Bearer token on every incoming request.
 *
 * Extends BasicAuthenticationFilter so it plugs into the Spring Security filter chain.
 *
 * Flow for each request:
 *   1. doFilterInternal() reads the Authorization header
 *   2. If it starts with "Bearer " → getAuthentication() validates the JWT
 *   3. If JWT is valid → sets authenticated user in SecurityContextHolder
 *   4. If no Bearer token → passes request to next filter (Basic auth kicks in)
 *
 * Note (Spring Security 6 vs 5 / Jakarta vs Javax):
 *   The exercise uses javax.servlet.* (Spring Security 5 / Spring Boot 2.x).
 *   Updated here to jakarta.servlet.* for Spring Security 6 / Spring Boot 3.x.
 */
public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    private static final Logger LOGGER = LoggerFactory.getLogger(JwtAuthorizationFilter.class);

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
        LOGGER.info("Start JwtAuthorizationFilter constructor");
        LOGGER.debug("authenticationManager={}", authenticationManager);
    }

    /**
     * Intercepts every HTTP request.
     * If Authorization header has "Bearer <token>", validates the JWT.
     * Otherwise delegates to the next filter (Basic auth).
     */
    @Override
    protected void doFilterInternal(HttpServletRequest req,
                                    HttpServletResponse res,
                                    FilterChain chain) throws IOException, ServletException {
        LOGGER.info("Start doFilterInternal");

        String header = req.getHeader("Authorization");
        LOGGER.debug("Authorization header: {}", header);

        // No Bearer token → pass to next filter (BasicAuth will handle it)
        if (header == null || !header.startsWith("Bearer ")) {
            chain.doFilter(req, res);
            return;
        }

        // Validate the JWT and set authentication in context
        UsernamePasswordAuthenticationToken authentication = getAuthentication(req);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        chain.doFilter(req, res);
        LOGGER.info("End doFilterInternal");
    }

    /**
     * Parses and validates the JWT from the Authorization header.
     *
     * jjwt 0.9.x API (exercise): Jwts.parser().setSigningKey("secretkey")
     * jjwt 0.11.5 API (updated): Jwts.parserBuilder().setSigningKey(Keys.hmacShaKeyFor(...))
     *
     * @return authenticated token if JWT is valid, null otherwise
     */
    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        String header = request.getHeader("Authorization");

        if (header != null) {
            try {
                Key key = Keys.hmacShaKeyFor(
                    AuthenticationController.SECRET_KEY.getBytes(StandardCharsets.UTF_8));

                Jws<Claims> jws = Jwts.parserBuilder()
                        .setSigningKey(key)
                        .build()
                        .parseClaimsJws(header.replace("Bearer ", ""));

                String user = jws.getBody().getSubject();
                LOGGER.debug("JWT validated, user={}", user);

                if (user != null) {
                    // Authentication token with empty authorities list (roles come from JWT claims in production)
                    return new UsernamePasswordAuthenticationToken(user, null, new ArrayList<>());
                }
            } catch (JwtException ex) {
                LOGGER.warn("JWT validation failed: {}", ex.getMessage());
                return null;
            }
        }
        return null;
    }
}
