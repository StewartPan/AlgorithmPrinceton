import edu.princeton.cs.algs4.UF;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

/**
 * Created by admin on 2018/11/28.
 */

public class Percolation {

    private int size;
    private int openSite;
    private WeightedQuickUnionUF uf;
    private boolean isPercolates;
    private boolean[] connectTop;
    private boolean[] connectBot;
    private boolean[] open;

    private boolean isValid(int x, int y){
        if(x < 1 || x > size || y < 1 || y > size) return false;
        return true;
    }

    private int xyTo1D(int row, int col){
        return (row - 1) * size + col - 1;
    }

    public Percolation(int n){
        if(n <= 0) throw new java.lang.IllegalArgumentException();
        this.size = n;
        this.uf = new WeightedQuickUnionUF(n * n);
        isPercolates = false;
        this.connectTop = new boolean[n * n];
        this.connectBot = new boolean[n * n];
        this.open = new boolean[n * n];
    }

    // create n-by-n grid, with all sites blocked
    public void open(int row, int col){
        if(!isValid(row, col)) throw new java.lang.IllegalArgumentException("invalid input" + "row is " + row + "col is " + col);
        int id = xyTo1D(row, col);
        if(open[id]) return;
        open[id] = true;
        openSite++;

        boolean linkToTop = false;
        boolean linkToBot = false;

        if(row == 1) linkToTop = true;
        if(row == size) linkToBot = true;

        // connect four directions neighbors
        if(row > 1 && isOpen(row - 1, col)){
            int top = xyTo1D(row - 1, col);
            int rootTop = uf.find(top);
            linkToTop |= connectTop[rootTop];
            linkToBot |= connectBot[rootTop];
            uf.union(rootTop, id);
        }
        if(row < size && isOpen(row + 1, col)){
            int bot = xyTo1D(row + 1, col);
            int rootBot = uf.find(bot);
            linkToTop |= connectTop[rootBot];
            linkToBot |= connectBot[rootBot];
            uf.union(rootBot, id);
        }
        if(col > 1 && isOpen(row, col - 1)){
            int left = xyTo1D(row, col - 1);
            int rootLeft = uf.find(left);
            linkToTop |= connectTop[rootLeft];
            linkToBot |= connectBot[rootLeft];
            uf.union(rootLeft, id);

        }
        if(col < size && isOpen(row, col + 1)){
            int right = xyTo1D(row, col + 1);
            int rootRight = uf.find(right);
            linkToTop |= connectTop[rootRight];
            linkToBot |= connectBot[rootRight];
            uf.union(rootRight, id);
        }
        int root = uf.find(id);
        connectBot[root] = linkToBot;
        connectTop[root] = linkToTop;
        if(linkToBot && linkToTop) isPercolates = true;
    }

    // open site (row, col) if it is not open already
    public boolean isOpen(int row, int col){
        if(!isValid(row, col)) throw new java.lang.IllegalArgumentException("invalid input" + " row is " + row + " col is " + col);
        int id = xyTo1D(row, col);
        return this.open[id];
    }

    // is site (row, col) open?

    public boolean isFull(int row, int col){
        if(!isValid(row, col)) throw new java.lang.IllegalArgumentException("invalid input" + " row is " + row + " col is " + col);
        int id = xyTo1D(row, col);
        int root = uf.find(id);
        return connectTop[root];
    }

    // is site (row, col) full?

    public int numberOfOpenSites(){
        return openSite;
    }

    // number of open sites
    public boolean percolates(){
        return isPercolates;
    }

    // does the system percolate?
}
