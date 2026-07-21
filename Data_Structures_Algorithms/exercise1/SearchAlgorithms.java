package com.ecommerce;


public class SearchAlgorithms {

    public static int linearSearch(Product[] products, int targetId) {
        for (int i = 0; i < products.length; i++) {
            if (products[i].getProductId() == targetId) {
                return i;          // found – return index immediately
            }
        }
        return -1;                 // not found
    }

    public static int binarySearch(Product[] products, int targetId) {
        int low  = 0;
        int high = products.length - 1;

        while (low <= high) {
            int mid = low + (high - low) / 2;   // avoids integer overflow
            int midId = products[mid].getProductId();

            if (midId == targetId) {
                return mid;                      // found
            } else if (midId < targetId) {
                low = mid + 1;                   // target is in the right half
            } else {
                high = mid - 1;                  // target is in the left half
            }
        }
        return -1;                               // not found
    }
}
