
SET SERVEROUTPUT ON;


CREATE OR REPLACE PROCEDURE ProcessMonthlyInterest AS

    v_accounts_updated NUMBER := 0;
    v_interest_amount  NUMBER;

    -- Cursor for all savings accounts
    CURSOR cur_savings IS
        SELECT AccountID, Balance
        FROM   Accounts
        WHERE  AccountType = 'Savings'
        FOR UPDATE OF Balance, LastModified;   -- lock rows for safe update

BEGIN
    DBMS_OUTPUT.PUT_LINE('==============================================');
    DBMS_OUTPUT.PUT_LINE('SCENARIO 1: ProcessMonthlyInterest');
    DBMS_OUTPUT.PUT_LINE('==============================================');

    FOR rec IN cur_savings LOOP

        v_interest_amount := rec.Balance * 0.01;   -- 1% interest

        UPDATE Accounts
        SET    Balance      = Balance + v_interest_amount,
               LastModified = SYSDATE
        WHERE  CURRENT OF cur_savings;             -- update the locked row

        v_accounts_updated := v_accounts_updated + 1;

        DBMS_OUTPUT.PUT_LINE(
            'Account ID: '        || rec.AccountID          ||
            ' | Previous Balance: $' || rec.Balance         ||
            ' | Interest: $'      || ROUND(v_interest_amount, 2) ||
            ' | New Balance: $'   || ROUND(rec.Balance + v_interest_amount, 2)
        );

    END LOOP;

    COMMIT;
    DBMS_OUTPUT.PUT_LINE('----------------------------------------------');
    DBMS_OUTPUT.PUT_LINE('Monthly interest processed for '
                          || v_accounts_updated || ' savings account(s).');
    DBMS_OUTPUT.PUT_LINE('Scenario 1 complete.');
    DBMS_OUTPUT.PUT_LINE('');

EXCEPTION
    WHEN OTHERS THEN
        ROLLBACK;
        DBMS_OUTPUT.PUT_LINE('ERROR in ProcessMonthlyInterest: ' || SQLERRM);
        RAISE;   -- re-raise so the caller also sees the error
END ProcessMonthlyInterest;
/

-- Execute and verify
BEGIN
    ProcessMonthlyInterest;
END;
/

SELECT AccountID, AccountType, Balance AS UpdatedBalance, LastModified
FROM   Accounts
WHERE  AccountType = 'Savings';



CREATE OR REPLACE PROCEDURE UpdateEmployeeBonus (
    p_department    IN VARCHAR2,
    p_bonus_percent IN NUMBER
) AS

    v_rows_updated NUMBER := 0;
    v_bonus_amount NUMBER;

    -- Cursor to show individual impact before bulk update
    CURSOR cur_employees IS
        SELECT EmployeeID, Name, Salary
        FROM   Employees
        WHERE  UPPER(Department) = UPPER(p_department);

BEGIN
    DBMS_OUTPUT.PUT_LINE('==============================================');
    DBMS_OUTPUT.PUT_LINE('SCENARIO 2: UpdateEmployeeBonus');
    DBMS_OUTPUT.PUT_LINE('Department : ' || p_department);
    DBMS_OUTPUT.PUT_LINE('Bonus %    : ' || p_bonus_percent || '%');
    DBMS_OUTPUT.PUT_LINE('==============================================');

    -- Validate bonus percentage
    IF p_bonus_percent <= 0 THEN
        DBMS_OUTPUT.PUT_LINE('ERROR: Bonus percentage must be greater than 0.');
        RETURN;
    END IF;

    -- Show per-employee breakdown before updating
    FOR rec IN cur_employees LOOP
        v_bonus_amount := rec.Salary * (p_bonus_percent / 100);
        DBMS_OUTPUT.PUT_LINE(
            'Employee: '       || rec.Name                                  ||
            ' | Current Salary: $' || rec.Salary                           ||
            ' | Bonus: $'      || ROUND(v_bonus_amount, 2)                  ||
            ' | New Salary: $' || ROUND(rec.Salary + v_bonus_amount, 2)
        );
    END LOOP;

    -- Bulk update salaries
    UPDATE Employees
    SET    Salary = Salary + (Salary * p_bonus_percent / 100)
    WHERE  UPPER(Department) = UPPER(p_department);

    v_rows_updated := SQL%ROWCOUNT;

    IF v_rows_updated = 0 THEN
        DBMS_OUTPUT.PUT_LINE(
            'WARNING: No employees found in department "' || p_department || '".'
        );
    ELSE
        COMMIT;
        DBMS_OUTPUT.PUT_LINE('----------------------------------------------');
        DBMS_OUTPUT.PUT_LINE(
            'Bonus applied to ' || v_rows_updated || ' employee(s) in '
            || p_department || ' department.'
        );
    END IF;

    DBMS_OUTPUT.PUT_LINE('Scenario 2 complete.');
    DBMS_OUTPUT.PUT_LINE('');

EXCEPTION
    WHEN OTHERS THEN
        ROLLBACK;
        DBMS_OUTPUT.PUT_LINE('ERROR in UpdateEmployeeBonus: ' || SQLERRM);
        RAISE;
END UpdateEmployeeBonus;
/

-- Execute: give the IT department a 10% bonus
BEGIN
    UpdateEmployeeBonus('IT', 10);
END;
/

-- Execute: give the HR department a 5% bonus
BEGIN
    UpdateEmployeeBonus('HR', 5);
END;
/

-- Verify updated salaries
SELECT EmployeeID, Name, Department, Salary AS UpdatedSalary
FROM   Employees
ORDER  BY Department, EmployeeID;


CREATE OR REPLACE PROCEDURE TransferFunds (
    p_from_account IN NUMBER,
    p_to_account   IN NUMBER,
    p_amount       IN NUMBER
) AS

    v_from_balance  NUMBER;
    v_to_balance    NUMBER;
    v_from_exists   NUMBER;
    v_to_exists     NUMBER;
    v_new_txn_id    NUMBER;

    -- Custom exceptions
    e_insufficient_funds  EXCEPTION;
    e_account_not_found   EXCEPTION;
    e_invalid_amount      EXCEPTION;
    PRAGMA EXCEPTION_INIT(e_insufficient_funds, -20001);
    PRAGMA EXCEPTION_INIT(e_account_not_found,  -20002);
    PRAGMA EXCEPTION_INIT(e_invalid_amount,     -20003);

BEGIN
    DBMS_OUTPUT.PUT_LINE('==============================================');
    DBMS_OUTPUT.PUT_LINE('SCENARIO 3: TransferFunds');
    DBMS_OUTPUT.PUT_LINE('From Account : ' || p_from_account);
    DBMS_OUTPUT.PUT_LINE('To Account   : ' || p_to_account);
    DBMS_OUTPUT.PUT_LINE('Amount       : $' || p_amount);
    DBMS_OUTPUT.PUT_LINE('==============================================');

    -- ── Step 1: Validate transfer amount ─────────────────────
    IF p_amount <= 0 THEN
        RAISE_APPLICATION_ERROR(-20003,
            'Transfer amount must be greater than zero.');
    END IF;

    -- ── Step 2: Check source account exists ──────────────────
    SELECT COUNT(*) INTO v_from_exists
    FROM   Accounts
    WHERE  AccountID = p_from_account;

    IF v_from_exists = 0 THEN
        RAISE_APPLICATION_ERROR(-20002,
            'Source account ' || p_from_account || ' does not exist.');
    END IF;

    -- ── Step 3: Check destination account exists ─────────────
    SELECT COUNT(*) INTO v_to_exists
    FROM   Accounts
    WHERE  AccountID = p_to_account;

    IF v_to_exists = 0 THEN
        RAISE_APPLICATION_ERROR(-20002,
            'Destination account ' || p_to_account || ' does not exist.');
    END IF;

    -- ── Step 4: Fetch source balance (with row lock) ─────────
    SELECT Balance INTO v_from_balance
    FROM   Accounts
    WHERE  AccountID = p_from_account
    FOR UPDATE;                             -- lock to prevent concurrent overdraft

    -- ── Step 5: Check sufficient balance ─────────────────────
    IF v_from_balance < p_amount THEN
        RAISE_APPLICATION_ERROR(-20001,
            'Insufficient funds. Available balance: $' || v_from_balance
            || ', Transfer amount: $' || p_amount);
    END IF;

    -- ── Step 6: Fetch destination balance (with row lock) ────
    SELECT Balance INTO v_to_balance
    FROM   Accounts
    WHERE  AccountID = p_to_account
    FOR UPDATE;

    -- ── Step 7: Deduct from source ───────────────────────────
    UPDATE Accounts
    SET    Balance      = Balance - p_amount,
           LastModified = SYSDATE
    WHERE  AccountID    = p_from_account;

    -- ── Step 8: Credit destination ───────────────────────────
    UPDATE Accounts
    SET    Balance      = Balance + p_amount,
           LastModified = SYSDATE
    WHERE  AccountID    = p_to_account;

    -- ── Step 9: Log withdrawal transaction ───────────────────
    SELECT NVL(MAX(TransactionID), 0) + 1 INTO v_new_txn_id FROM Transactions;

    INSERT INTO Transactions (TransactionID, AccountID, TransactionDate, Amount, TransactionType)
    VALUES (v_new_txn_id, p_from_account, SYSDATE, p_amount, 'Withdrawal');

    INSERT INTO Transactions (TransactionID, AccountID, TransactionDate, Amount, TransactionType)
    VALUES (v_new_txn_id + 1, p_to_account, SYSDATE, p_amount, 'Deposit');

    -- ── Step 10: Commit ──────────────────────────────────────
    COMMIT;

    DBMS_OUTPUT.PUT_LINE('Transfer successful!');
    DBMS_OUTPUT.PUT_LINE(
        'Account ' || p_from_account ||
        ' new balance: $' || (v_from_balance - p_amount)
    );
    DBMS_OUTPUT.PUT_LINE(
        'Account ' || p_to_account   ||
        ' new balance: $' || (v_to_balance + p_amount)
    );
    DBMS_OUTPUT.PUT_LINE('Scenario 3 complete.');
    DBMS_OUTPUT.PUT_LINE('');

EXCEPTION
    WHEN e_insufficient_funds THEN
        ROLLBACK;
        DBMS_OUTPUT.PUT_LINE('TRANSFER FAILED – ' || SQLERRM);
    WHEN e_account_not_found THEN
        ROLLBACK;
        DBMS_OUTPUT.PUT_LINE('TRANSFER FAILED – ' || SQLERRM);
    WHEN e_invalid_amount THEN
        ROLLBACK;
        DBMS_OUTPUT.PUT_LINE('TRANSFER FAILED – ' || SQLERRM);
    WHEN OTHERS THEN
        ROLLBACK;
        DBMS_OUTPUT.PUT_LINE('UNEXPECTED ERROR in TransferFunds: ' || SQLERRM);
        RAISE;
END TransferFunds;
/

-- ── Test Case 1: Valid transfer ───────────────────────────────
BEGIN
    TransferFunds(p_from_account => 5,    -- Alice Green (balance $15,000)
                  p_to_account   => 1,    -- John Doe
                  p_amount       => 2000);
END;
/

-- ── Test Case 2: Insufficient funds ──────────────────────────
BEGIN
    TransferFunds(p_from_account => 1,    -- John Doe (balance now $3,000)
                  p_to_account   => 2,    -- Jane Smith
                  p_amount       => 99999);
END;
/

-- ── Test Case 3: Non-existent account ────────────────────────
BEGIN
    TransferFunds(p_from_account => 999,  -- does not exist
                  p_to_account   => 1,
                  p_amount       => 500);
END;
/

-- Verify final account balances
SELECT AccountID, CustomerID, AccountType,
       Balance AS FinalBalance, LastModified
FROM   Accounts
ORDER  BY AccountID;

-- Verify transaction log
SELECT TransactionID, AccountID, TransactionDate,
       Amount, TransactionType
FROM   Transactions
ORDER  BY TransactionID;
