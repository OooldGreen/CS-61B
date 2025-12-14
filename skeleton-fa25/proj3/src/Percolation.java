import edu.princeton.cs.algs4.WeightedQuickUnionUF;
import java.lang.IllegalArgumentException;

public class Percolation {
    int N;
    // number of open sites
    int numOfOpenSites;
    int [][] grid;
    int top;
    int bottom;
    WeightedQuickUnionUF uf1;
    WeightedQuickUnionUF uf2;

    public Percolation(int N) {
        if (N <= 0) {
            throw new IllegalArgumentException("N should more than 0, " + N);
        }
        this.N = N;
        grid = new int [N][N];
        numOfOpenSites = 0;
        uf1 = new WeightedQuickUnionUF(N * N + 2);
        uf2 = new WeightedQuickUnionUF(N * N + 1);
        top = N * N;
        bottom = N * N + 1;
    }

    public void open(int row, int col) {
        validateInput(row, col);
        // examine repeating open
        if(grid[row][col] != 1) {
            grid[row][col] = 1;
            numOfOpenSites += 1;
        }
        connecting(row, col);
    }

    public boolean isOpen(int row, int col) {
        validateInput(row, col);
        return grid[row][col] == 1;
    }

    public boolean isFull(int row, int col) {
        validateInput(row, col);
        return uf2.connected(top, xyTo1D(row, col));
    }

    public int numberOfOpenSites() {
        return numOfOpenSites;
    }

    public boolean percolates() {
        return uf1.connected(top, bottom);
    }

    private int xyTo1D (int row, int col) {
        return N * row + col;
    }

    // examine corner cases
    private void validateInput(int a, int b) {
        if (a >= N || b >= N || a < 0 || b < 0) {
            throw new IndexOutOfBoundsException();
        }
    }

    // principles of connecting
    private void connecting(int row, int col) {
        int p = xyTo1D(row, col);
        if (row == 0) {
            uf1.union(top, p);
            uf2.union(top, p);
        }

        if(row == N - 1) {
            uf1.union(p, bottom);
        }

        // if this neighbor is open, union these two grids
        if (checkNeighbors(row + 1, col)) {
            uf1.union(p, xyTo1D(row + 1, col));
            uf2.union(p, xyTo1D(row + 1, col));
        }
        if (checkNeighbors(row - 1, col)) {
            uf1.union(p, xyTo1D(row - 1, col));
            uf2.union(p, xyTo1D(row - 1, col));
        }
        if (checkNeighbors(row, col + 1)) {
            uf1.union(p, xyTo1D(row, col + 1));
            uf2.union(p, xyTo1D(row, col + 1));
        }
        if (checkNeighbors(row, col - 1)) {
            uf1.union(p, xyTo1D(row, col - 1));
            uf2.union(p, xyTo1D(row, col - 1));
        }
    }

    // check neighbors are open or not
    private boolean checkNeighbors(int row, int col) {
        return row < N && row >= 0 && col < N && col >= 0 && isOpen(row, col);
    }
}
