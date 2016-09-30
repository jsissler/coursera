import java.util.Arrays;

import edu.princeton.cs.algs4.StdOut;

public class Board {
    final private int[][] tiles;
    final private int n;

    private static int[][] clone(int[][] tiles) {
        int[][] clonedTiles = new int[tiles.length][];
        for (int i = 0; i < tiles.length; i++)
            clonedTiles[i] = tiles[i].clone();
        return clonedTiles;
    }

    // construct a board from an n-by-n array of blocks
    // (where blocks[i][j] = block in row i, column j)
    public Board(int[][] blocks) {
        tiles = clone(blocks);
        n = tiles.length;
        StdOut.println("Board ctor: \n" + this);
    }

    public int dimension() {
        return n;
    }

    // number of blocks out of place
    public int hamming() {
        return 0;
    }

    // sum of Manhattan distances between blocks and goal
    public int manhattan() {
        return 0;
    }

    // is this board the goal board?
    public boolean isGoal() {
        return false;
    }

    // a board that is obtained by exchanging any pair of blocks
    public Board twin() {
        StdOut.printf("twin called on %s\n", this);
        int[][] twinTiles = clone(tiles);
        for (int row = 0; row < n - 1; row++) {
            for (int col = 0; col < n - 1; col++) {
                if (twinTiles[row][col] != 0 && twinTiles[row + 1][col + 1] != 0) {
                    int tmp = twinTiles[row][col];
                    twinTiles[row][col] = twinTiles[row + 1][col + 1];
                    twinTiles[row + 1][col + 1] = tmp;
                    return new Board(twinTiles);
                }
            }
        }
        return null;
    }

    // does this board equal y?
    public boolean equals(Object y) {
        return tiles.equals(((Board) y).tiles);
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        return null;
    }

    // string representation of this board (in the output format specified
    // below)
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(n + "\n");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                s.append(String.format("%2d ", tiles[i][j]));
            }
            s.append("\n");
        }
        return s.toString();
    }

    // unit tests (not graded)
    public static void main(String[] args) {
        int[][] tiles1 = { { 0, 1, 3 }, { 4, 2, 5 }, { 7, 8, 6 } };
        Board b1 = new Board(tiles1);
        int[][] tiles2 = { { 0, 3, 1 }, { 4, 2, 5 }, { 7, 8, 6 } };
        Board b2 = new Board(tiles2);
        StdOut.printf("%s == %s? %s\n", b1, b2, b1 == b2);
        Board t1 = b1.twin();
        StdOut.printf("b1=%s\n", b1);
        StdOut.printf("t1=%s\n", t1);
    }
}