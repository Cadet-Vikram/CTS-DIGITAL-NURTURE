-- ============================================================
-- SCHEMA SETUP – Cognizant DN 5.0 | PL/SQL Exercises
-- Run this script first before any exercise script.
-- Oracle SQL / PL/SQL (tested on Oracle 19c+)
-- ============================================================

-- ── Drop tables if they already exist (clean re-run) ─────────
BEGIN
    EXECUTE IMMEDIATE 'DROP TABLE Transactions CASCADE CONSTRAINTS';
EXCEPTION WHEN OTHERS THEN NULL;
END;
/
BEGIN
    EXECUTE IMMEDIATE 'DROP TABLE Loans CASCADE CONSTRAINTS';
EXCEPTION WHEN OTHERS THEN NULL;
END;
/
BEGIN
    EXECUTE IMMEDIATE 'DROP TABLE Accounts CASCADE CONSTRAINTS';
EXCEPTION WHEN OTHERS THEN NULL;
END;
/
BEGIN
    EXECUTE IMMEDIATE 'DROP TABLE Customers CASCADE CONSTRAINTS';
EXCEPTION WHEN OTHERS THEN NULL;
END;
/
BEGIN
    EXECUTE IMMEDIATE 'DROP TABLE Employees CASCADE CONSTRAINTS';
EXCEPTION WHEN OTHERS THEN NULL;
END;
/
BEGIN
    EXECUTE IMMEDIATE 'DROP TABLE AuditLog CASCADE CONSTRAINTS';
EXCEPTION WHEN OTHERS THEN NULL;
END;
/

-- ── Create Tables ─────────────────────────────────────────────
CREATE TABLE Customers (
    CustomerID   NUMBER PRIMARY KEY,
    Name         VARCHAR2(100),
    DOB          DATE,
    Balance      NUMBER,
    LastModified DATE,
    IsVIP        VARCHAR2(5) DEFAULT 'FALSE'   -- added for Exercise 1 Scenario 2
);

CREATE TABLE Accounts (
    AccountID    NUMBER PRIMARY KEY,
    CustomerID   NUMBER,
    AccountType  VARCHAR2(20),
    Balance      NUMBER,
    LastModified DATE,
    FOREIGN KEY (CustomerID) REFERENCES Customers(CustomerID)
);

CREATE TABLE Transactions (
    TransactionID   NUMBER PRIMARY KEY,
    AccountID       NUMBER,
    TransactionDate DATE,
    Amount          NUMBER,
    TransactionType VARCHAR2(10),
    FOREIGN KEY (AccountID) REFERENCES Accounts(AccountID)
);

CREATE TABLE Loans (
    LoanID       NUMBER PRIMARY KEY,
    CustomerID   NUMBER,
    LoanAmount   NUMBER,
    InterestRate NUMBER,
    StartDate    DATE,
    EndDate      DATE,
    FOREIGN KEY (CustomerID) REFERENCES Customers(CustomerID)
);

CREATE TABLE Employees (
    EmployeeID NUMBER PRIMARY KEY,
    Name       VARCHAR2(100),
    Position   VARCHAR2(50),
    Salary     NUMBER,
    Department VARCHAR2(50),
    HireDate   DATE
);

-- AuditLog table (used by Exercise 5 Trigger, included here for completeness)
CREATE TABLE AuditLog (
    LogID           NUMBER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    TransactionID   NUMBER,
    AccountID       NUMBER,
    Amount          NUMBER,
    TransactionType VARCHAR2(10),
    LogDate         DATE DEFAULT SYSDATE
);

-- ── Sample Data ───────────────────────────────────────────────
-- Customers: mix of ages and balances to trigger all scenarios
INSERT INTO Customers (CustomerID, Name, DOB, Balance, LastModified)
VALUES (1, 'John Doe',      TO_DATE('1985-05-15','YYYY-MM-DD'),  1000,  SYSDATE);

INSERT INTO Customers (CustomerID, Name, DOB, Balance, LastModified)
VALUES (2, 'Jane Smith',    TO_DATE('1990-07-20','YYYY-MM-DD'),  1500,  SYSDATE);

-- Senior customers (age > 60) to trigger Scenario 1 of Exercise 1
INSERT INTO Customers (CustomerID, Name, DOB, Balance, LastModified)
VALUES (3, 'Robert Brown',  TO_DATE('1955-03-10','YYYY-MM-DD'),  5000,  SYSDATE);

INSERT INTO Customers (CustomerID, Name, DOB, Balance, LastModified)
VALUES (4, 'Mary Wilson',   TO_DATE('1950-11-22','YYYY-MM-DD'),  8000,  SYSDATE);

-- High-balance customers (balance > 10000) to trigger VIP flag
INSERT INTO Customers (CustomerID, Name, DOB, Balance, LastModified)
VALUES (5, 'Alice Green',   TO_DATE('1978-08-05','YYYY-MM-DD'), 15000,  SYSDATE);

INSERT INTO Customers (CustomerID, Name, DOB, Balance, LastModified)
VALUES (6, 'Charlie Black', TO_DATE('1965-01-30','YYYY-MM-DD'), 25000,  SYSDATE);

-- Accounts
INSERT INTO Accounts (AccountID, CustomerID, AccountType, Balance, LastModified)
VALUES (1, 1, 'Savings',  1000,  SYSDATE);
INSERT INTO Accounts (AccountID, CustomerID, AccountType, Balance, LastModified)
VALUES (2, 2, 'Checking', 1500,  SYSDATE);
INSERT INTO Accounts (AccountID, CustomerID, AccountType, Balance, LastModified)
VALUES (3, 3, 'Savings',  5000,  SYSDATE);
INSERT INTO Accounts (AccountID, CustomerID, AccountType, Balance, LastModified)
VALUES (4, 4, 'Savings',  8000,  SYSDATE);
INSERT INTO Accounts (AccountID, CustomerID, AccountType, Balance, LastModified)
VALUES (5, 5, 'Savings',  15000, SYSDATE);
INSERT INTO Accounts (AccountID, CustomerID, AccountType, Balance, LastModified)
VALUES (6, 6, 'Checking', 25000, SYSDATE);

-- Transactions
INSERT INTO Transactions (TransactionID, AccountID, TransactionDate, Amount, TransactionType)
VALUES (1, 1, SYSDATE,  200, 'Deposit');
INSERT INTO Transactions (TransactionID, AccountID, TransactionDate, Amount, TransactionType)
VALUES (2, 2, SYSDATE,  300, 'Withdrawal');
INSERT INTO Transactions (TransactionID, AccountID, TransactionDate, Amount, TransactionType)
VALUES (3, 3, SYSDATE, 1000, 'Deposit');

-- Loans: include one due within 30 days to trigger Scenario 3 of Exercise 1
INSERT INTO Loans (LoanID, CustomerID, LoanAmount, InterestRate, StartDate, EndDate)
VALUES (1, 1, 5000, 5.0, SYSDATE, ADD_MONTHS(SYSDATE, 60));

INSERT INTO Loans (LoanID, CustomerID, LoanAmount, InterestRate, StartDate, EndDate)
VALUES (2, 3, 8000, 6.5, TO_DATE('2020-01-01','YYYY-MM-DD'), SYSDATE + 10); -- due in 10 days

INSERT INTO Loans (LoanID, CustomerID, LoanAmount, InterestRate, StartDate, EndDate)
VALUES (3, 4, 3000, 7.0, TO_DATE('2021-06-01','YYYY-MM-DD'), SYSDATE + 25); -- due in 25 days

-- Employees
INSERT INTO Employees (EmployeeID, Name, Position, Salary, Department, HireDate)
VALUES (1, 'Alice Johnson', 'Manager',   70000, 'HR', TO_DATE('2015-06-15','YYYY-MM-DD'));
INSERT INTO Employees (EmployeeID, Name, Position, Salary, Department, HireDate)
VALUES (2, 'Bob Brown',     'Developer', 60000, 'IT', TO_DATE('2017-03-20','YYYY-MM-DD'));
INSERT INTO Employees (EmployeeID, Name, Position, Salary, Department, HireDate)
VALUES (3, 'Carol Davis',   'Analyst',   55000, 'IT', TO_DATE('2019-09-01','YYYY-MM-DD'));
INSERT INTO Employees (EmployeeID, Name, Position, Salary, Department, HireDate)
VALUES (4, 'David Lee',     'Recruiter', 50000, 'HR', TO_DATE('2020-01-15','YYYY-MM-DD'));

COMMIT;

SELECT 'Schema created and sample data inserted successfully.' AS Status FROM DUAL;
