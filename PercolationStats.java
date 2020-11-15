/* *****************************************************************************
 *  Name:              Roman Zaiats
 *  Coursera User ID:  b38ab3d10530110bb13487a0a9254f25
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private final double[] percolationRates;
    private final int experimentsCount;

    public PercolationStats(int gridSize, int experimentsCount) {
        if (gridSize <= 0 || experimentsCount <= 0) {
            throw new IllegalArgumentException();
        }

        this.experimentsCount = experimentsCount;
        percolationRates = new double[experimentsCount];

        for (int i = 0; i < experimentsCount; i++) {
            Percolation percolation = new Percolation(gridSize);

            while (!percolation.percolates()) {
                int row = StdRandom.uniform(1, gridSize + 1);
                int column = StdRandom.uniform(1, gridSize + 1);

                if (!percolation.isOpen(row, column)) {
                    percolation.open(row, column);
                }
            }

            percolationRates[i] = (double) percolation.numberOfOpenSites() / gridSize / gridSize;
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(percolationRates);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.mean(percolationRates);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean() - ((1.96 * stddev()) / Math.sqrt(experimentsCount));

    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean() + ((1.96 * stddev()) / Math.sqrt(experimentsCount));

    }

    // test client (see below)
    public static void main(String[] args) {
        if (args.length < 2) {
            throw new IllegalArgumentException();
        }

        int gridSize = Integer.parseInt(args[0]);
        int retries = Integer.parseInt(args[1]);

        long start = System.currentTimeMillis();
        PercolationStats percolationStats = new PercolationStats(gridSize, retries);

        long finish = System.currentTimeMillis();

        System.out.println(finish - start);

        String confidence = percolationStats.confidenceLo() + ", " + percolationStats
                .confidenceHi();
        StdOut.println("mean                    = " + percolationStats.mean());
        StdOut.println("stddev                  = " + percolationStats.stddev());
        StdOut.println("95% confidence interval = " + confidence);
    }
}
