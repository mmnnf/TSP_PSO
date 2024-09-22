package PSO_FOR_TSP;

public class TSPModel {

    public int n;          // Number of points (cities)
    public double[][] D;   // Distance matrix
    public double[] x;        // x-coordinates of cities
    public double[] y;        // y-coordinates of cities

    // Method to create the model
    public  void createModel() {
        //TSPModel model = new TSPModel();

        // x and y coordinates
        this.x = new double[]{57, 96, 79, 40, 93, 60, 58, 82, 30, 53, 86, 16, 60, 43, 69, 91, 23, 60, 86, 15};
        this.y = new double[]{86, 59, 53, 6, 74, 23, 70, 6, 35, 17, 48, 87, 37, 31, 45, 26, 17, 75, 65, 42};
        this.n = this.x.length;

        // Create the distance matrix D
        this.D = new double[this.n][this.n];
        for (int i = 0; i < this.n - 1; i++) {
            for (int j = i; j < this.n; j++) {
                this.D[i][j] = Math.sqrt(Math.pow(this.x[i] - this.x[j], 2) + Math.pow(this.y[i] - this.y[j], 2));
                this.D[j][i] = this.D[i][j];  // Symmetric matrix
            }
        }

      //  return model;
    }

    // Example usage
    public static void main(String[] args) {
        TSPModel model = new TSPModel();
        model.createModel();

        // Print the distance matrix
        for (int i = 0; i < model.n; i++) {
            for (int j = 0; j < model.n; j++) {
                System.out.printf("%.2f ", model.D[i][j]);
            }
            System.out.println();
        }
    }
}