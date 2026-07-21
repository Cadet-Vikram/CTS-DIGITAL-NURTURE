package com.finance;

public class Main {

    public static void main(String[] args) {

        System.out.println("=================================================");
        System.out.println("       FINANCIAL FORECASTING TOOL");
        System.out.println("=================================================\n");

        // ── Scenario 1: Basic forecast ───────────────────────────────────────
        double initialInvestment = 10_000.0;  // ₹10,000 initial investment
        double annualGrowthRate  = 0.08;      // 8% annual growth rate
        int    years             = 5;

        System.out.printf("Initial Investment : ₹%.2f%n", initialInvestment);
        System.out.printf("Annual Growth Rate : %.0f%%%n", annualGrowthRate * 100);
        System.out.printf("Investment Period  : %d years%n%n", years);

        System.out.println("-------------------------------------------------");
        System.out.println("Approach 1 – Plain Recursion");
        System.out.println("-------------------------------------------------");

        long start = System.nanoTime();
        double resultRecursive = FinancialForecasting.calculateFutureValueRecursive(
                initialInvestment, annualGrowthRate, years);
        long timeRecursive = System.nanoTime() - start;

        System.out.printf("  Future Value after %d years : ₹%.2f%n", years, resultRecursive);
        System.out.println("  Time taken               : " + timeRecursive + " ns");
        System.out.println("  Time Complexity          : O(n)");
        System.out.println("  Space Complexity         : O(n) – call stack\n");

        System.out.println("-------------------------------------------------");
        System.out.println("Approach 2 – Memoized Recursion");
        System.out.println("-------------------------------------------------");

        FinancialForecasting.clearMemo();
        start = System.nanoTime();
        double resultMemoized = FinancialForecasting.calculateFutureValueMemoized(
                initialInvestment, annualGrowthRate, years);
        long timeMemoized = System.nanoTime() - start;

        System.out.printf("  Future Value after %d years : ₹%.2f%n", years, resultMemoized);
        System.out.println("  Time taken               : " + timeMemoized + " ns");
        System.out.println("  Time Complexity          : O(n) first call, O(1) repeated calls");
        System.out.println("  Space Complexity         : O(n) – HashMap cache\n");

        System.out.println("-------------------------------------------------");
        System.out.println("Approach 3 – Iterative");
        System.out.println("-------------------------------------------------");

        start = System.nanoTime();
        double resultIterative = FinancialForecasting.calculateFutureValueIterative(
                initialInvestment, annualGrowthRate, years);
        long timeIterative = System.nanoTime() - start;

        System.out.printf("  Future Value after %d years : ₹%.2f%n", years, resultIterative);
        System.out.println("  Time taken               : " + timeIterative + " ns");
        System.out.println("  Time Complexity          : O(n)");
        System.out.println("  Space Complexity         : O(1) – most efficient\n");

        // ── Scenario 2: Year-by-year growth table ────────────────────────────
        System.out.println("=================================================");
        System.out.println("  YEAR-BY-YEAR GROWTH TABLE (Recursive)");
        System.out.println("=================================================");
        System.out.printf("%-6s  %-20s  %-15s%n", "Year", "Future Value (₹)", "Growth (₹)");
        System.out.println("------  --------------------  ---------------");

        double prev = initialInvestment;
        for (int y = 1; y <= years; y++) {
            double fv = FinancialForecasting.calculateFutureValueRecursive(
                    initialInvestment, annualGrowthRate, y);
            System.out.printf("  %-4d  ₹%-19.2f  +₹%.2f%n", y, fv, fv - prev);
            prev = fv;
        }

        // ── Scenario 3: Show memoization benefit on repeated queries ─────────
        System.out.println("\n=================================================");
        System.out.println("  MEMOIZATION BENEFIT – Repeated Lookups");
        System.out.println("=================================================");

        FinancialForecasting.clearMemo();
        int largeN = 30;

        // First call: populates the cache
        start = System.nanoTime();
        FinancialForecasting.calculateFutureValueMemoized(initialInvestment, annualGrowthRate, largeN);
        long firstCallTime = System.nanoTime() - start;

        // Second call for same period: O(1) lookup
        start = System.nanoTime();
        FinancialForecasting.calculateFutureValueMemoized(initialInvestment, annualGrowthRate, largeN);
        long secondCallTime = System.nanoTime() - start;

        System.out.println("Query: periods = " + largeN);
        System.out.println("  1st call (cold cache) : " + firstCallTime  + " ns  – computed recursively");
        System.out.println("  2nd call (warm cache) : " + secondCallTime + " ns  – returned from HashMap");
        System.out.println("  Speedup               : ~" + (firstCallTime / Math.max(secondCallTime, 1)) + "x faster");

        // ── Final Analysis ────────────────────────────────────────────────────
        System.out.println("\n=================================================");
        System.out.println("  ANALYSIS & RECOMMENDATION");
        System.out.println("=================================================");
        System.out.println("Approach          | Time       | Space | Best For");
        System.out.println("------------------|------------|-------|--------------------");
        System.out.println("Plain Recursion   | O(n)       | O(n)  | Simple, small n");
        System.out.println("Memoized Recursion| O(n)/O(1)  | O(n)  | Repeated queries");
        System.out.println("Iterative         | O(n)       | O(1)  | Production use");
        System.out.println();
        System.out.println("RECOMMENDATION:");
        System.out.println("  Use the iterative approach for production. It avoids call-stack");
        System.out.println("  limits (no StackOverflowError for large n) and uses O(1) space.");
        System.out.println("  Use memoization when the same period is queried many times");
        System.out.println("  (e.g., dashboard that refreshes forecast frequently).");
    }
}
