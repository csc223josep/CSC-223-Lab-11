/**
 * Solves the 0/1 Knapsack problem using dynamic programming.
 * 
 * Authors: Dan O'Brien, Joseph Kabesha, Isaiah Ayres
 */
public class Knapsack {

    // A method to find the maximum of two integers
    static int max(int a, int b) {
        return (a > b) ? a : b;
    }

    // Returns maximum value that can be put in a knapsack of capacity W
    static int knapsack(int W, int[] wt, int[] val, int n, boolean[] selectedItems) {
        int i, w;
        // Create a 2D array to store the maximum value that can be put in the knapsack
        int[][] K = new int[n + 1][W + 1];

        // Build table K[][] in bottom-up manner
        for (i = 0; i <= n; i++) {
            for (w = 0; w <= W; w++) {
                if (i == 0 || w == 0)
                    K[i][w] = 0; // Base case: no items or no capacity
                else if (wt[i - 1] <= w)
                    // If the current item can fit in the knapsack, consider including it
                    K[i][w] = max(val[i - 1] + K[i - 1][w - wt[i - 1]], K[i - 1][w]);
                else
                    // If the current item cannot fit, skip it
                    K[i][w] = K[i - 1][w];
            }
        }

        // Backtrack to find the selected items
        int res = K[n][W];
        w = W;
        for (i = n; i > 0 && res > 0; i--) {
            if (res != K[i - 1][w]) {
                // This item is included in the knapsack
                selectedItems[i - 1] = true;
                res -= val[i - 1];
                w -= wt[i - 1];
            }
        }
        
        // Return the maximum value that can be put in the knapsack
        return K[n][W];
    }

    public static void main(String[] args) {
        int[] val = {60, 100, 120, 160, 50, 110, 150, 200};  // Values of items
        int[] wt = {10, 20, 30, 40, 10, 25, 35, 45}; // Weights of items
        int W = 120;           // Maximum weight capacity of knapsack
        int n = val.length;    // Number of items

        // Print the values and their respective weights
        System.out.println("Items:");
        for (int j = 0; j < n; j++) {
            System.out.println("Item " + (j + 1) + ": Weight = " + wt[j] + " units, Value = " + val[j] + " units");
        }

        // Array to store which items were selected
        boolean[] selectedItems = new boolean[n];

        // Find the maximum value that can be put in the knapsack and print the selected items
        int maxValue = knapsack(W, wt, val, n, selectedItems);
        System.out.println("\nMaximum value that can be put in a knapsack of capacity W = " +
                           W + " is " + maxValue);

        // Print the selected items
        System.out.println("Selected items:");
        for (int j = 0; j < n; j++) {
            if (selectedItems[j]) {
                System.out.println("Item " + (j + 1));
            }
        }
    }
}