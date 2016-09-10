import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

/**
 * Perform trials independent experiments on an n-by-n grid.
 * 
 * @author John Sissler
 *
 */
public class PercolationStats {
    /**
     * Percolation threshold for each trial.
     */
    private double[] collectedPercolationThresholds;
    /**
     * Constant used to calculate confidence interval.
     */
    private final double confidenceIntervalConstant = 1.96;

    /**
     * Constructor.
     * 
     * @param n size of grid n x n
     * @param trials number of runs.
     */
    public PercolationStats(final int n, final int trials) {
        if (n <= 0 || trials <= 0) {
            throw new java.lang.IllegalArgumentException(
                    "grid size and number of trials must be greater than 0");
        }
        collectedPercolationThresholds = new double[trials];
        for (int trial = 0; trial < trials; trial++) {
            Percolation p = new Percolation(n);
            int opened = 0;
            
            while (!p.percolates()) {
                int i = StdRandom.uniform(1, n + 1);
                int j = StdRandom.uniform(1, n + 1);

                while (p.isOpen(i, j)) {
                    i = StdRandom.uniform(1, n + 1);
                    j = StdRandom.uniform(1, n + 1);
                }

                p.open(i, j);
                opened++;
            }
            
            collectedPercolationThresholds[trial] = (double) opened
                    / (double) (n * n);
        }
    }
    /**
     * Sample mean of percolation threshold.
     * 
     * @return calculated mean
     */
    public final double mean() {
        return StdStats.mean(collectedPercolationThresholds);
    }

    /**
     * Sample standard deviation of percolation threshold.
     * 
     * @return standard deviation of pt
     */
    public final double stddev() {
        return StdStats.stddev(collectedPercolationThresholds);
    }

    /**
     * Low endpoint of 95% confidence interval.
     * 
     * @return low endpoint
     */
    public final double confidenceLo() {
        return mean() - confidenceIntervalConstant * stddev()
                / Math.sqrt(collectedPercolationThresholds.length);
    }

    /**
     * High endpoint of 95% confidence interval.
     * 
     * @return high endpoint
     */
    public final double confidenceHi() {
        return mean() + confidenceIntervalConstant * stddev()
                / Math.sqrt(collectedPercolationThresholds.length);
    }

    /**
     * Test driver.
     * 
     * @param args grid size and num trials
     */
    public static void main(final String[] args) {
        if (args.length != 2) {
            throw new java.lang.IllegalArgumentException(
                    "usage: PercolationStats n trials");
        }
        int n = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);
        PercolationStats ps = new PercolationStats(n, trials);
        StdOut.printf("%-24s = %.15f\n", "mean", ps.mean());
        StdOut.printf("%-24s = %.15f\n", "stddev", ps.stddev());
        StdOut.printf("%-24s = %.15f, %.15f\n", "95% confidence interval",
                ps.confidenceLo(), ps.confidenceHi());
    }

}
