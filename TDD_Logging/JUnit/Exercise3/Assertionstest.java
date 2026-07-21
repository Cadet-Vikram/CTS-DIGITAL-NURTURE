package com.example.junit.exercise3;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class AssertionsTest {

    private final MathUtils mathUtils = new MathUtils();

    // ── assertEquals / assertNotEquals ───────────────────────────
    @Test
    void testAssertEquals() {
        assertEquals(5, 2 + 3,          "2+3 should be 5");
        assertEquals(10, mathUtils.absolute(-10), "|-10| should be 10");
    }

    @Test
    void testAssertNotEquals() {
        assertNotEquals(0, 2 + 3, "2+3 should not be 0");
    }

    // ── assertTrue / assertFalse ──────────────────────────────────
    @Test
    void testAssertTrueAndFalse() {
        assertTrue(5 > 3,   "5 should be greater than 3");
        assertFalse(5 < 3,  "5 should not be less than 3");

        // Real usage: boolean result of a method
        assertTrue(mathUtils.absolute(-7) > 0, "Absolute value should be positive");
    }

    // ── assertNull / assertNotNull ────────────────────────────────
    @Test
    void testAssertNullAndNotNull() {
        assertNull(mathUtils.nullableSquare(0),
                   "nullableSquare(0) should return null");

        assertNotNull(mathUtils.nullableSquare(5),
                      "nullableSquare(5) should return a non-null Integer");

        assertNotNull(new Object(), "A newly created object should not be null");
    }

    // ── assertArrayEquals ─────────────────────────────────────────
    @Test
    void testAssertArrayEquals() {
        int[] expected = {0, 1, 1, 2, 3};
        int[] actual   = mathUtils.fibonacci(5);
        assertArrayEquals(expected, actual,
                          "First 5 Fibonacci numbers should be 0,1,1,2,3");
    }

    // ── assertThrows ──────────────────────────────────────────────
    @Test
    void testAssertThrows() {
        IllegalArgumentException ex = assertThrows(
            IllegalArgumentException.class,
            () -> mathUtils.squareRoot(-1),
            "squareRoot of negative number should throw IllegalArgumentException"
        );
        assertTrue(ex.getMessage().contains("negative"),
                   "Exception message should mention 'negative'");
    }

    // ── assertAll (grouped assertions — all run even if one fails) ─
    @Test
    void testAssertAll() {
        assertAll("MathUtils grouped assertions",
            () -> assertEquals(9,    mathUtils.absolute(-9)),
            () -> assertEquals(25,   mathUtils.nullableSquare(5)),
            () -> assertNull(        mathUtils.nullableSquare(0)),
            () -> assertEquals(2.0,  mathUtils.squareRoot(4), 0.0001)
        );
    }

    // ── Inline arithmetic assertions (matches exercise solution code) ─
    @Test
    void testInlineAssertions() {
        assertEquals(5,  2 + 3);
        assertTrue(5   > 3);
        assertFalse(5  < 3);
        assertNull(null);
        assertNotNull(new Object());
    }
}
