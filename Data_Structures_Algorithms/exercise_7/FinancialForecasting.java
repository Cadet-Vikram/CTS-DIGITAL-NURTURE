package com.finance;

import java.util.HashMap;
import java.util.Map;


public class FinancialForecasting {

    public static double calculateFutureValueRecursive(double initialValue,
                                                       double growthRate,
                                                       int periods) {
        // Base case: no periods left → return the current value as-is
        if (periods == 0) {
            return initialValue;
        }

        // Recursive case: apply one period of growth and recurse
        return calculateFutureValueRecursive(initialValue, growthRate, periods - 1)
                * (1 + growthRate);
    }


    private static final Map<Integer, Double> memo = new HashMap<>();


    public static double calculateFutureValueMemoized(double initialValue,
                                                      double growthRate,
                                                      int periods) {
        // Base case
        if (periods == 0) {
            return initialValue;
        }

        // Cache hit – return stored result immediately (O(1))
        if (memo.containsKey(periods)) {
            return memo.get(periods);
        }

        // Cache miss – compute, store, then return
        double result = calculateFutureValueMemoized(initialValue, growthRate, periods - 1)
                * (1 + growthRate);
        memo.put(periods, result);
        return result;
    }


    public static void clearMemo() {
        memo.clear();
    }

    public static double calculateFutureValueIterative(double initialValue,
                                                       double growthRate,
                                                       int periods) {
        double value = initialValue;
        for (int i = 1; i <= periods; i++) {
            value *= (1 + growthRate);
        }
        return value;
    }
}
