package com.example.junit.exercise4;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

class BankAccountTest {

    // Test fixture: shared object recreated before each test
    private BankAccount account;

    // ── Lifecycle Methods ────────────────────────────────────────

    @BeforeAll
    static void initAll() {
        System.out.println("[BeforeAll]  One-time setup — runs before any test");
    }

    @AfterAll
    static void tearDownAll() {
        System.out.println("[AfterAll]   One-time teardown — runs after all tests");
    }

    @BeforeEach
    void setUp() {
        // ARRANGE (shared): a fresh BankAccount before each test
        account = new BankAccount("Abhirami S", 1000.0);
        System.out.println("[BeforeEach] Fresh BankAccount created, balance = 1000.0");
    }

    @AfterEach
    void tearDown() {
        System.out.println("[AfterEach]  Test complete, account balance was: " + account.getBalance());
    }

    // ── Test Methods (each follows AAA) ─────────────────────────

    @Test
    @DisplayName("Deposit increases balance by the deposited amount")
    void testDeposit() {
        // ARRANGE
        double depositAmount = 500.0;

        // ACT
        account.deposit(depositAmount);

        // ASSERT
        assertEquals(1500.0, account.getBalance(),
                     "Balance after deposit should be 1500.0");
    }

    @Test
    @DisplayName("Withdrawal reduces balance by the withdrawn amount")
    void testWithdraw() {
        // ARRANGE
        double withdrawAmount = 300.0;

        // ACT
        account.withdraw(withdrawAmount);

        // ASSERT
        assertEquals(700.0, account.getBalance(),
                     "Balance after withdrawal should be 700.0");
    }

    @Test
    @DisplayName("Withdrawing more than balance throws IllegalStateException")
    void testWithdrawInsufficientFunds() {
        // ARRANGE
        double excessAmount = 5000.0;   // more than the 1000.0 balance

        // ACT + ASSERT
        IllegalStateException ex = assertThrows(
            IllegalStateException.class,
            () -> account.withdraw(excessAmount)
        );
        assertEquals("Insufficient funds", ex.getMessage());
        // Balance must remain unchanged after a failed withdrawal
        assertEquals(1000.0, account.getBalance(),
                     "Balance should not change after a failed withdrawal");
    }

    @Test
    @DisplayName("Depositing a negative amount throws IllegalArgumentException")
    void testDepositNegativeAmount() {
        // ARRANGE
        double badAmount = -200.0;

        // ACT + ASSERT
        assertThrows(IllegalArgumentException.class,
                     () -> account.deposit(badAmount),
                     "Depositing a negative amount must throw IllegalArgumentException");
    }

    @Test
    @DisplayName("Multiple deposits and withdrawals produce correct final balance")
    void testMultipleOperations() {
        // ARRANGE – account starts at 1000 (set by @BeforeEach)

        // ACT
        account.deposit(500.0);    // 1500
        account.withdraw(200.0);   // 1300
        account.deposit(100.0);    // 1400
        account.withdraw(400.0);   // 1000

        // ASSERT
        assertEquals(1000.0, account.getBalance(),
                     "Final balance should be 1000.0 after net-zero operations");
    }

    @Test
    @DisplayName("Account holder name is stored correctly")
    void testAccountHolder() {
        // ARRANGE – (done in @BeforeEach)

        // ACT
        String name = account.getAccountHolder();

        // ASSERT
        assertNotNull(name,                  "Account holder name must not be null");
        assertEquals("Abhirami S", name,    "Account holder name should match");
    }
}
