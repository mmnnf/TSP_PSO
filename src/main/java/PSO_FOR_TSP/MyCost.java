package PSO_FOR_TSP;
import java.util.Arrays;
import java.util.Comparator;

public class MyCost {

    // Solution class to hold tour and length of the tour
    public static class Solution {
        public int[] tour;
        public double L;
    }

    // Cost function to calculate the cost of the tour (TSP problem)
    public static Solution myCost(TSPModel model, double[] x) {
        // Sort the tour based on x values and store indices
        int[] tour = argsort(x);

        double[][] D = model.D;  // Distance matrix
        int n = model.n;         // Number of cities
        double L = 0;            // Total length of the tour

        // Calculate the total tour length
        for (int k = 0; k < n-2; k++) {
            int i = tour[k];
            int j;
            if (k < n - 1) {
                j = tour[k + 1];  // Next city in the tour
            } else {
                j = tour[0];      // Return to the starting city
            }
            L += D[i][j];  // Add distance between city i and city j
        }

        // Return the total cost and the solution (tour and total length)
        Solution sol = new Solution();
        sol.tour = tour;
        sol.L = L;
        return sol;
    }

    // Helper function to sort the indices of array x in ascending order
    public static int[] argsort(double[] x) {
        Integer[] indices = new Integer[x.length];
        for (int i = 0; i < x.length; i++) {
            indices[i] = i;
        }
        Arrays.sort(indices, Comparator.comparingDouble(a -> x[a]));
        return Arrays.stream(indices).mapToInt(Integer::intValue).toArray();
    }

    public static void main(String[] args) {
        // Example model creation
        TSPModel model = new TSPModel();
        model.createModel();

        // Example position array
        double[] x = {0.5, 0.8, 0.3, 0.9, 0.4};

        // Calculate the cost and tour
        Solution result = myCost(model, x);

        System.out.println("Tour length (Cost): " + result.tour);
    }

    // Placeholder for creating the TSP model
//    public static Model createModel() {
//        Model model = new Model();
//        model.n = 5;  // Number of cities
//        model.D = new double[][]{
//                {0, 10, 15, 20, 25},
//                {10, 0, 35, 25, 30},
//                {15, 35, 0, 30, 40},
//                {20, 25, 30, 0, 50},
//                {25, 30, 40, 50, 0}
//        };
//        return model;
//    }
//
//    // Model class to represent TSP
//    static class Model {
//        public int n; // Number of cities
//        public double[][] D; // Distance matrix
//    }
}