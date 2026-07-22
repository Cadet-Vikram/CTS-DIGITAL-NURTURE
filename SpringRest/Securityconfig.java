package com.cognizant.springlearn.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

/**
 * SecurityConfig – doc5: Spring Security configuration with JWT filter.
 *
 * Spring Security 6 (Spring Boot 3.x) changes from the exercise code:
 *   Exercise (Spring Security 5.x):                This implementation (6.x):
 *   ─ extends WebSecurityConfigurerAdapter          ─ uses SecurityFilterChain @Bean
 *   ─ protected void configure(HttpSecurity)        ─ SecurityFilterChain filterChain(HttpSecurity)
 *   ─ protected void configure(AuthMgrBuilder)      ─ UserDetailsService + PasswordEncoder @Beans
 *   ─ httpSecurity.authorizeRequests()              ─ .authorizeHttpRequests()
 *   ─ .antMatchers("/url")                          ─ .requestMatchers("/url")
 *
 * Users defined (in-memory for demo purposes):
 *   user:pwd  → role USER  (can access /authenticate, /countries, /employees, etc.)
 *   admin:pwd → role ADMIN (can access /authenticate)
 *
 * Security rules:
 *   /authenticate  → accessible by USER and ADMIN (Basic auth to get JWT)
 *   any other URL  → requires valid JWT Bearer token OR valid Basic credentials
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(SecurityConfig.class);

    /**
     * doc5: Define in-memory users with BCrypt-encoded passwords and roles.
     *
     * In production: implement UserDetailsService against a database.
     * The exercise explicitly uses in-memory for learning purposes.
     */
    @Bean
    public UserDetailsService userDetailsService() {
        LOGGER.info("Start userDetailsService");
        return new InMemoryUserDetailsManager(
            User.withUsername("user")
                .password(passwordEncoder().encode("pwd"))
                .roles("USER")
                .build(),
            User.withUsername("admin")
                .password(passwordEncoder().encode("pwd"))
                .roles("ADMIN")
                .build()
        );
    }

    /** doc5: BCrypt password encoder – required for in-memory user setup */
    @Bean
    public PasswordEncoder passwordEncoder() {
        LOGGER.info("Start passwordEncoder");
        return new BCryptPasswordEncoder();
    }

    /**
     * doc5: AuthenticationManager wires UserDetailsService + PasswordEncoder.
     * Required by JwtAuthorizationFilter constructor.
     */
    @Bean
    public AuthenticationManager authenticationManager() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService());
        provider.setPasswordEncoder(passwordEncoder());
        return new ProviderManager(provider);
    }

    /**
     * doc5: Security filter chain.
     *
     * Rule 1: /authenticate – accessible by USER or ADMIN (Basic auth to obtain JWT)
     * Rule 2: All other URLs – must provide JWT Bearer token (or Basic auth)
     *
     * addFilter(JwtAuthorizationFilter) – plugs in our JWT validator before
     * the standard BasicAuthenticationFilter in the chain.
     *
     * csrf().disable() – REST APIs are stateless; CSRF protection is for session-based apps.
     * httpBasic() – required to allow curl -u user:pwd ... for /authenticate.
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        LOGGER.info("Start filterChain configuration");

        http
            .csrf(csrf -> csrf.disable())
            .httpBasic(Customizer.withDefaults())
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/authenticate").hasAnyRole("USER", "ADMIN")
                .requestMatchers("/h2-console/**").permitAll()  // dev convenience
                .anyRequest().authenticated()
            )
            .addFilter(new JwtAuthorizationFilter(authenticationManager()))
            // Allow H2 console frames (dev only)
            .headers(headers -> headers.frameOptions(fo -> fo.disable()));

        LOGGER.info("End filterChain configuration");
        return http.build();
    }
}
