package PSO_FOR_TSP;

import java.util.Arrays;
import java.util.Random;

public class Doreversion {

    public  double[] doreversion(double[] x) {
        // Sort the indices of x in ascending order to get the tour
        int[] tour = argsort(x);
        int n = tour.length;

        // Create a copy of the tour
        int[] newTour = Arrays.copyOf(tour, n);

        // Randomly select two distinct indices i1 and i2
        Random random = new Random();
        int i1 = random.nextInt(n);
        int i2 = random.nextInt(n);

        // Ensure i1 is smaller than i2
        if (i1 > i2) {
            int temp = i1;
            i1 = i2;
            i2 = temp;
        }

        // Reverse the portion of the tour between i1 and i2
        for (int i = i1, j = i2; i < j; i++, j--) {
            int temp = newTour[i];
            newTour[i] = newTour[j];
            newTour[j] = temp;
        }

        // Create a new x based on the new tour
        double[] newx = new double[x.length];
        for (int i = 0; i < n; i++) {
            newx[newTour[i]] = x[tour[i]];
        }

        return newx;
    }

    // Helper function to sort the indices of array x in ascending order
    public static int[] argsort(double[] x) {
        Integer[] indices = new Integer[x.length];
        for (int i = 0; i < x.length; i++) {
            indices[i] = i;
        }
        Arrays.sort(indices, (a, b) -> Double.compare(x[a], x[b]));
        return Arrays.stream(indices).mapToInt(Integer::intValue).toArray();
    }

    // Example usage
    public static void main(String[] args) {
        // Example x values
        double[] x = {5,8, 3, 9, 4};


        Doreversion d=new Doreversion();
        // Apply doreversion
        double[] newx = d.doreversion(x);

        // Print the result
        System.out.println("Original x: " + Arrays.toString(x));
        System.out.println("New x: " + Arrays.toString(newx));
    }
}