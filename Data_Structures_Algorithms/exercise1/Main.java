package com.ecommerce;

public class Main {

    public static void main(String[] args) {

        Product[] unsortedCatalogue = {
            new Product(104, "Wireless Mouse",         "Electronics"),
            new Product(201, "Running Shoes",          "Footwear"),
            new Product(015, "Yoga Mat",               "Sports"),
            new Product(333, "Office Chair",           "Furniture"),
            new Product(050, "Coffee Maker",           "Appliances"),
            new Product(178, "Bluetooth Headphones",   "Electronics"),
            new Product(092, "Leather Wallet",         "Accessories"),
            new Product(410, "Notebook",               "Stationery"),
            new Product(067, "Sunscreen SPF 50",       "Beauty"),
            new Product(255, "Kitchen Knife Set",      "Cookware")
        };

        Product[] sortedCatalogue = {
            new Product(015, "Yoga Mat",               "Sports"),
            new Product(050, "Coffee Maker",           "Appliances"),
            new Product(067, "Sunscreen SPF 50",       "Beauty"),
            new Product(092, "Leather Wallet",         "Accessories"),
            new Product(104, "Wireless Mouse",         "Electronics"),
            new Product(178, "Bluetooth Headphones",   "Electronics"),
            new Product(201, "Running Shoes",          "Footwear"),
            new Product(255, "Kitchen Knife Set",      "Cookware"),
            new Product(333, "Office Chair",           "Furniture"),
            new Product(410, "Notebook",               "Stationery")
        };

        System.out.println("=================================================");
        System.out.println("  E-COMMERCE PLATFORM – SEARCH FUNCTION DEMO");
        System.out.println("=================================================\n");

        int searchId = 178;   // Bluetooth Headphones
        System.out.println("Searching for productId = " + searchId);
        System.out.println("-------------------------------------------------");

        // Linear Search
        long startTime = System.nanoTime();
        int linearResult = SearchAlgorithms.linearSearch(unsortedCatalogue, searchId);
        long linearTime  = System.nanoTime() - startTime;

        if (linearResult != -1) {
            System.out.println("[Linear Search]  FOUND at index " + linearResult
                    + " → " + unsortedCatalogue[linearResult]);
        } else {
            System.out.println("[Linear Search]  Product NOT FOUND");
        }
        System.out.println("  Time taken: " + linearTime + " ns");

        // Binary Search
        startTime = System.nanoTime();
        int binaryResult = SearchAlgorithms.binarySearch(sortedCatalogue, searchId);
        long binaryTime  = System.nanoTime() - startTime;

        if (binaryResult != -1) {
            System.out.println("[Binary Search]  FOUND at index " + binaryResult
                    + " → " + sortedCatalogue[binaryResult]);
        } else {
            System.out.println("[Binary Search]  Product NOT FOUND");
        }
        System.out.println("  Time taken: " + binaryTime + " ns\n");

        // ── Test 2: Search for a product that does NOT exist ─────────────────
        int missingId = 999;
        System.out.println("Searching for productId = " + missingId + " (does not exist)");
        System.out.println("-------------------------------------------------");

        linearResult = SearchAlgorithms.linearSearch(unsortedCatalogue, missingId);
        System.out.println("[Linear Search]  "
                + (linearResult == -1 ? "Product NOT FOUND (scanned all " + unsortedCatalogue.length + " items)" : "Found at " + linearResult));

        binaryResult = SearchAlgorithms.binarySearch(sortedCatalogue, missingId);
        System.out.println("[Binary Search]  "
                + (binaryResult == -1 ? "Product NOT FOUND (only " + (int)(Math.log(sortedCatalogue.length)/Math.log(2)) + " comparisons needed)" : "Found at " + binaryResult));

        // ── Analysis Summary ─────────────────────────────────────────────────
        System.out.println("\n=================================================");
        System.out.println("  ANALYSIS SUMMARY");
        System.out.println("=================================================");
        System.out.println("Algorithm       | Best  | Average | Worst  | Sorted?");
        System.out.println("----------------|-------|---------|--------|--------");
        System.out.println("Linear Search   | O(1)  | O(n)    | O(n)   | No");
        System.out.println("Binary Search   | O(1)  | O(log n)| O(log n)| YES");
        System.out.println();
        System.out.println("For a catalogue of 1,000,000 products:");
        System.out.println("  Linear Search (worst case) → 1,000,000 comparisons");
        System.out.println("  Binary Search (worst case) →        20 comparisons  (log₂ 1M ≈ 20)");
        System.out.println();
        System.out.println("RECOMMENDATION:");
        System.out.println("  Use Binary Search for the e-commerce platform.");
        System.out.println("  Products are indexed and sorted by productId at upload time,");
        System.out.println("  so the O(log n) lookup cost is paid only once per search query.");
    }
}
