import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

/**
 * Created by admin on 2018/12/1.
 */
public class PercolationStats {

    private final int size;
    private final int trials;
    private final double mean;
    private final double stddev;

    private double[] res;

    private void test(){

    }

    public PercolationStats(int n, int trials){
        if(n < 1 || trials < 1) throw new java.lang.IllegalArgumentException();
        this.size = n;
        this.trials = trials;
        this.res = new double[trials];
        for(int i = 0; i < trials; i++){
            Percolation p = new Percolation(size);
            while(!p.percolates()){
                int row = StdRandom.uniform(size) + 1;
                int col = StdRandom.uniform(size) + 1;
                p.open(row, col);
            }

            res[i] = (double)p.numberOfOpenSites()/(size * size);
        }

        mean = StdStats.mean(res);
        stddev = StdStats.stddev(res);
    }

    public double mean(){
        return mean;
    }

    public double stddev(){
        return stddev;
    }

    public double confidenceLo(){
        return mean - 1.96 * stddev/Math.sqrt(trials);
    }

    public double confidenceHi(){
        return mean + 1.96 * stddev/Math.sqrt(trials);
    }

    public static void main(String[] args) {
        PercolationStats test = new PercolationStats(200, 100);
        System.out.println(test.mean());
        System.out.println(test.stddev());
        System.out.println(test.confidenceLo());
        System.out.println(test.confidenceHi());
    }
}
