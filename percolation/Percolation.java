/* *****************************************************************************
 *  Name:              Zihao
 *  Coursera User ID:  123456
 *  Last modified:     9/8/2020
 **************************************************************************** */

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private final int n;
    private boolean[] opensite;
    private final WeightedQuickUnionUF grid;
    private final WeightedQuickUnionUF fullconnection;
    private final int top;
    private final int bottom;
    private int numopen;
    

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        this.n = n;
        if (n <= 0) {
            throw new IllegalArgumentException("N must be a positive int");
        }
        opensite = new boolean[n * n];
        grid = new WeightedQuickUnionUF(n * n + 2);
        fullconnection = new WeightedQuickUnionUF(n * n + 1);
        top = getsingleindex(n, n) + 1;
        bottom = getsingleindex(n, n) + 2;
        numopen = 0;
    }

    private int getsingleindex(int row, int col) {
        isOutbond(row, col);
        return (n * (row - 1) + col - 1);
    }

    private boolean isValid(int row, int col) {
        return row > 0 && col > 0 && col <= n && row <= n;
    }

    private void isOutbond(int row, int col) {
        if (!isValid(row, col)) {
            throw new IllegalArgumentException("Values are out of bounds");
        }
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (isOpen(row, col)) {
            return;
        }
        int index = getsingleindex(row, col);
        opensite[index] = true;

        // if node is in top row
        if (row == 1) {
            grid.union(index, top);
            fullconnection.union(index, top);
        }
        // if node is in bottom row
        if (row == n) {
            grid.union(index, bottom);
        }
        // union above node if above node is open
        if (isValid(row - 1, col) && isOpen(row - 1, col)) {
            grid.union(index, getsingleindex(row - 1, col));
            fullconnection.union(index, getsingleindex(row - 1, col));
        }
        // union left node if left node is open
        if (isValid(row, col - 1) && isOpen(row, col - 1)) {
            grid.union(index, getsingleindex(row, col - 1));
            fullconnection.union(index, getsingleindex(row, col - 1));
        }
        // union above right if right node is open
        if (isValid(row, col + 1) && isOpen(row, col + 1)) {
            grid.union(index, getsingleindex(row, col + 1));
            fullconnection.union(index, getsingleindex(row, col + 1));
        }
        // union below node if below node is open
        if (isValid(row + 1, col) && isOpen(row + 1, col)) {
            grid.union(index, getsingleindex(row + 1, col));
            fullconnection.union(index, getsingleindex(row + 1, col));
        }
        numopen++;
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        return opensite[getsingleindex(row, col)];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        return fullconnection.find(getsingleindex(row, col)) == fullconnection.find(top);
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return numopen;
    }

    // does the system percolate?
    public boolean percolates() {
        return grid.find(top) == grid.find(bottom);
    }

    // test client (optional)
    // public static void main(String[] args)

}
