package com.cognizant.springlearn;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.http.converter.HttpMessageNotReadableException;

import java.util.*;
import java.util.stream.Collectors;

/**
 * GlobalExceptionHandler – doc4: Centralized validation and error handling.
 *
 * @ControllerAdvice intercepts exceptions thrown by ANY controller in the app.
 * Extends ResponseEntityExceptionHandler to override Spring MVC's built-in handlers.
 *
 * Handles:
 *   1. MethodArgumentNotValidException – @Valid validation failures on @RequestBody
 *      Triggered by: POST /countries with invalid code (e.g. single char)
 *      Response: HTTP 400 with list of field error messages
 *
 *   2. HttpMessageNotReadableException – malformed JSON / wrong field types
 *      Triggered by: sending a string for a numeric id field
 *      Response: HTTP 400 with field name in error message
 */
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * doc4: Handles @Valid validation failures (MethodArgumentNotValidException).
     *
     * Triggered when @RequestBody fails any constraint defined on the bean
     * (e.g. @NotNull, @Size, @Min).
     *
     * Response format:
     * {
     *   "timestamp": "...",
     *   "status": 400,
     *   "errors": ["Country code should be 2 characters", ...]
     * }
     */
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request) {

        LOGGER.info("Start handleMethodArgumentNotValid");

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", new Date());
        body.put("status", status.value());

        // Collect all validation error messages from BindingResult
        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(fieldError -> fieldError.getDefaultMessage())
                .collect(Collectors.toList());

        body.put("errors", errors);

        LOGGER.debug("Validation errors: {}", errors);
        LOGGER.info("End handleMethodArgumentNotValid");

        return new ResponseEntity<>(body, headers, status);
    }

    /**
     * doc4: Handles malformed JSON / type mismatch errors.
     *
     * Triggered when JSON has an incorrect type for a field
     * (e.g. sending a String for an Integer id field).
     *
     * Response format:
     * {
     *   "timestamp": "...",
     *   "status": 400,
     *   "error": "Bad Request",
     *   "message": "Incorrect format for field 'id'"
     * }
     */
    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(
            HttpMessageNotReadableException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request) {

        LOGGER.info("Start handleHttpMessageNotReadable");

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", new Date());
        body.put("status", status.value());
        body.put("error", "Bad Request");

        if (ex.getCause() instanceof InvalidFormatException cause) {
            cause.getPath().forEach(ref ->
                body.put("message", "Incorrect format for field '" + ref.getFieldName() + "'")
            );
        }

        LOGGER.debug("Message not readable: {}", ex.getMessage());
        LOGGER.info("End handleHttpMessageNotReadable");

        return new ResponseEntity<>(body, headers, status);
    }
}
