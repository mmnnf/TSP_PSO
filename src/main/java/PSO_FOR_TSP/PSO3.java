package PSO_FOR_TSP;


import java.util.Arrays;
import java.util.Random;

import static PSO_FOR_TSP.PlotSolution.plotSolution;


public class PSO3 {

    // Particle class to represent each particle in the swarm
    static class Particle {
        double[] position;
        double[] velocity;
        double[] bestPosition;
        double bestCost;
        MyCost.Solution bestSolution;

        public Particle(int dimensions) {
            position = new double[dimensions];
            velocity = new double[dimensions];
            bestPosition = new double[dimensions];
            bestCost = Double.POSITIVE_INFINITY;
        }
    }

//    static class Solution {
//        int[] tour;
//        double length;
//    }

    // Cost function for TSP
    public static MyCost.Solution costFunction(TSPModel  model, double[] position) {
        MyCost cost=new MyCost();
//        myCost(model)
        return MyCost.myCost(model,position);

    }

    public static double[] myCost(Model model, double[] x) {
        // Your cost calculation logic for TSP
        // Example code to create the solution based on the x values
        int[] tour = Arrays.stream(x).boxed().sorted().mapToInt(Double::intValue).toArray();
        double cost = 0;
        for (int i = 0; i < tour.length - 1; i++) {
            cost += model.D[tour[i]][tour[i + 1]];
        }
        return new double[]{cost};
    }

    public static void main(String[] args) {
        // Model creation
        TSPModel model = new TSPModel();
        model.createModel();  // Create your TSP model here

        int nVar = model.n; // Number of variables
        double varMin = 0;
        double varMax = 1;
        int maxIt = 1000;  // Maximum iterations
        int nPop = 1000;  // Population size
        double c1 = 0;    // Personal learning coefficient
        double c2 = 1;    // Global learning coefficient
        double w = 1;     // Inertia weight
        double wdamp = 0.999; // Damping ratio for inertia weight
        double cost=1000000000;
        int[] costL={};
        // Initialization
        Particle[] particles = new Particle[nPop];
        Particle gBest = new Particle(nVar);
        gBest.bestCost = Double.POSITIVE_INFINITY;

        Random random = new Random();
        for (int i = 0; i < nPop; i++) {
            particles[i] = new Particle(nVar);
            for (int j = 0; j < nVar; j++) {
                particles[i].position[j] = varMin + (varMax - varMin) * random.nextDouble();
                particles[i].velocity[j] = 0;
            }
            MyCost.Solution costSol = costFunction(model, particles[i].position);
            particles[i].bestCost = costSol.L;
            particles[i].bestPosition = Arrays.copyOf(particles[i].position, nVar);

            if (particles[i].bestCost < gBest.bestCost) {
                gBest.bestCost = particles[i].bestCost;
                gBest.bestPosition = Arrays.copyOf(particles[i].bestPosition, nVar);
            }
        }

        // Main PSO loop
        for (int it = 0; it < maxIt; it++) {
            for (int i = 0; i < nPop; i++) {
                // Update velocity
                for (int j = 0; j < nVar; j++) {
                    particles[i].velocity[j] = w * particles[i].velocity[j] +
                            c1 * random.nextDouble() * (particles[i].bestPosition[j] - particles[i].position[j]) +
                            c2 * random.nextDouble() * (gBest.bestPosition[j] - particles[i].position[j]);
                }

                // Update position
                for (int j = 0; j < nVar; j++) {
                    particles[i].position[j] += particles[i].velocity[j];
                }

                // Evaluate cost
                MyCost.Solution costSol = costFunction(model, particles[i].position);
                cost = costSol.L;
                costL=costSol.tour;
                // Update personal best
                if (cost < particles[i].bestCost) {
                    particles[i].bestCost = cost;
                    particles[i].bestPosition = Arrays.copyOf(particles[i].position, nVar);
                }

                // Update global best
                if (particles[i].bestCost < gBest.bestCost) {
                    gBest.bestCost = particles[i].bestCost;
                    gBest.bestPosition = Arrays.copyOf(particles[i].bestPosition, nVar);
                }
            }
//            //mutation
//            Doreversion d=new Doreversion();
//            double [] newsolpos=d.doreversion( gBest.bestPosition);
//            if(costFunction(model,newsolpos).L<gBest.bestCost)
//            {
//                System.out.println(costFunction(model,newsolpos).L+"+++++"+gBest.bestCost);
//                gBest.position=newsolpos;
//                gBest.bestCost=costFunction(model,newsolpos).L;
//                System.out.println("var");
//            }
            // Update inertia weight
            w *= wdamp;

            // Display the best cost of the current iteration
            System.out.println("Iteration " + it + ": Best Cost = " + gBest.bestCost);

            // Optionally plot or visualize the solution here

        }
//        for (int i = 0; i < costL.length; i++) {
//            System.out.println("cost  "+costL[i]);
//        }

        plotSolution(costL, model);
        // Display final results
        System.out.println("Best solution found:");
        System.out.println("Cost: " + gBest.bestCost);
        System.out.println("Position: " + Arrays.toString(gBest.bestPosition));
        for (int i = 0; i < costL.length; i++) {
            System.out.print(costL[i]+" , ");
        }
        System.out.println();
    }

    // Placeholder for creating the TSP model
    public static Model createModel() {
        Model model = new Model();
        model.n = 5;  // Example, number of cities
        model.D = new double[][]{{0, 10, 15, 20, 25}, {10, 0, 35, 25, 30}, {15, 35, 0, 30, 40}, {20, 25, 30, 0, 50}, {25, 30, 40, 50, 0}};
        return model;
    }

    // Model class to represent TSP
    static class Model {
        public int n; // Number of cities
        public double[][] D; // Distance matrix
    }
}