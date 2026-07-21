package com.example.logging.exercise1;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggingExample {

    // SLF4J logger — bound to this class at class-load time
    private static final Logger logger = LoggerFactory.getLogger(LoggingExample.class);

    public static void main(String[] args) {

        logger.info("Application started — demonstrating SLF4J log levels");

        // ── ERROR: serious failures that require immediate attention ──
        logger.error("This is an ERROR message — e.g., database connection failed");

        // ── WARN: unexpected situations that are not fatal ────────────
        logger.warn("This is a WARN message  — e.g., retry attempt 2 of 3");

        // ── INFO: normal operational messages ─────────────────────────
        logger.info("This is an INFO message  — e.g., service initialised");

        // ── DEBUG: detailed diagnostic info (suppressed in production) ─
        logger.debug("This is a DEBUG message — e.g., method parameters");

        // ── TRACE: finest-grained detail ─────────────────────────────
        logger.trace("This is a TRACE message — e.g., entering a loop");

        logger.info("Application finished — check console and app.log");
    }
}
