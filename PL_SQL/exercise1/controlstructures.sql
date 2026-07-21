
SET SERVEROUTPUT ON;


DECLARE
    -- Cursor to fetch all customers with their DOB
    CURSOR cur_customers IS
        SELECT CustomerID, Name, DOB
        FROM   Customers;

    v_age         NUMBER;
    v_rows_updated NUMBER := 0;

BEGIN
    DBMS_OUTPUT.PUT_LINE('==============================================');
    DBMS_OUTPUT.PUT_LINE('SCENARIO 1: Senior Customer Loan Discount');
    DBMS_OUTPUT.PUT_LINE('==============================================');

    FOR rec IN cur_customers LOOP

        -- Calculate age in years using MONTHS_BETWEEN
        v_age := FLOOR(MONTHS_BETWEEN(SYSDATE, rec.DOB) / 12);

        IF v_age > 60 THEN
            -- Apply 1% discount to all loans belonging to this customer
            UPDATE Loans
            SET    InterestRate = InterestRate - 1
            WHERE  CustomerID   = rec.CustomerID
              AND  InterestRate  > 1;  -- guard: prevent negative rates

            v_rows_updated := v_rows_updated + SQL%ROWCOUNT;

            DBMS_OUTPUT.PUT_LINE(
                'Customer: ' || rec.Name ||
                ' | Age: '   || v_age    ||
                ' | 1% discount applied to loan interest rate.'
            );
        ELSE
            DBMS_OUTPUT.PUT_LINE(
                'Customer: ' || rec.Name ||
                ' | Age: '   || v_age    ||
                ' | No discount (age <= 60).'
            );
        END IF;

    END LOOP;

    COMMIT;
    DBMS_OUTPUT.PUT_LINE('----------------------------------------------');
    DBMS_OUTPUT.PUT_LINE('Total loan records updated: ' || v_rows_updated);
    DBMS_OUTPUT.PUT_LINE('Scenario 1 complete.');
    DBMS_OUTPUT.PUT_LINE('');

EXCEPTION
    WHEN OTHERS THEN
        ROLLBACK;
        DBMS_OUTPUT.PUT_LINE('ERROR in Scenario 1: ' || SQLERRM);
END;
/

-- Verify the updated loan interest rates
SELECT l.LoanID, c.Name, c.DOB,
       FLOOR(MONTHS_BETWEEN(SYSDATE, c.DOB)/12) AS Age,
       l.InterestRate AS UpdatedInterestRate
FROM   Loans l
JOIN   Customers c ON l.CustomerID = c.CustomerID
ORDER  BY l.LoanID;



DECLARE
    CURSOR cur_customers IS
        SELECT CustomerID, Name, Balance
        FROM   Customers;

    v_vip_count NUMBER := 0;

BEGIN
    DBMS_OUTPUT.PUT_LINE('==============================================');
    DBMS_OUTPUT.PUT_LINE('SCENARIO 2: VIP Status Assignment');
    DBMS_OUTPUT.PUT_LINE('==============================================');

    FOR rec IN cur_customers LOOP

        IF rec.Balance > 10000 THEN
            UPDATE Customers
            SET    IsVIP = 'TRUE'
            WHERE  CustomerID = rec.CustomerID;

            v_vip_count := v_vip_count + 1;

            DBMS_OUTPUT.PUT_LINE(
                'Customer: ' || rec.Name        ||
                ' | Balance: $' || rec.Balance  ||
                ' | Status: Promoted to VIP ✓'
            );
        ELSE
            DBMS_OUTPUT.PUT_LINE(
                'Customer: ' || rec.Name        ||
                ' | Balance: $' || rec.Balance  ||
                ' | Status: Not VIP (balance <= $10,000)'
            );
        END IF;

    END LOOP;

    COMMIT;
    DBMS_OUTPUT.PUT_LINE('----------------------------------------------');
    DBMS_OUTPUT.PUT_LINE('Total customers promoted to VIP: ' || v_vip_count);
    DBMS_OUTPUT.PUT_LINE('Scenario 2 complete.');
    DBMS_OUTPUT.PUT_LINE('');

EXCEPTION
    WHEN OTHERS THEN
        ROLLBACK;
        DBMS_OUTPUT.PUT_LINE('ERROR in Scenario 2: ' || SQLERRM);
END;
/

-- Verify VIP flags
SELECT CustomerID, Name, Balance, IsVIP
FROM   Customers
ORDER  BY CustomerID;


DECLARE
    -- Cursor: loans ending within the next 30 days
    CURSOR cur_due_loans IS
        SELECT l.LoanID,
               l.CustomerID,
               c.Name        AS CustomerName,
               l.LoanAmount,
               l.EndDate,
               (l.EndDate - SYSDATE) AS DaysRemaining
        FROM   Loans     l
        JOIN   Customers c ON l.CustomerID = c.CustomerID
        WHERE  l.EndDate BETWEEN SYSDATE AND SYSDATE + 30
        ORDER  BY l.EndDate;

    v_reminder_count NUMBER := 0;

BEGIN
    DBMS_OUTPUT.PUT_LINE('==============================================');
    DBMS_OUTPUT.PUT_LINE('SCENARIO 3: Loan Due Reminders (Next 30 Days)');
    DBMS_OUTPUT.PUT_LINE('==============================================');

    FOR rec IN cur_due_loans LOOP

        v_reminder_count := v_reminder_count + 1;

        DBMS_OUTPUT.PUT_LINE(
            'REMINDER → Customer: '  || rec.CustomerName                       ||
            ' | Loan ID: '           || rec.LoanID                             ||
            ' | Amount: $'           || rec.LoanAmount                         ||
            ' | Due Date: '          || TO_CHAR(rec.EndDate, 'DD-MON-YYYY')    ||
            ' | Days Left: '         || FLOOR(rec.DaysRemaining)
        );

    END LOOP;

    IF v_reminder_count = 0 THEN
        DBMS_OUTPUT.PUT_LINE('No loans due in the next 30 days.');
    ELSE
        DBMS_OUTPUT.PUT_LINE('----------------------------------------------');
        DBMS_OUTPUT.PUT_LINE('Total reminders sent: ' || v_reminder_count);
    END IF;

    DBMS_OUTPUT.PUT_LINE('Scenario 3 complete.');

EXCEPTION
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('ERROR in Scenario 3: ' || SQLERRM);
END;
/
