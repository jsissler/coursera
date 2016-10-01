import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.princeton.cs.algs4.StdOut;

public class Board {
    private static final Map<Integer, Board> GOALCACHE = new HashMap<Integer, Board>();

    private final int[][] tiles;
    private final int n;

    // construct a board from an n-by-n array of blocks
    // (where blocks[i][j] = block in row i, column j)
    public Board(int[][] blocks) {
        tiles = clone(blocks);
        n = tiles.length;
    }

    private static int[][] clone(int[][] tiles) {
        int[][] clonedTiles = new int[tiles.length][];
        for (int i = 0; i < tiles.length; i++)
            clonedTiles[i] = tiles[i].clone();
        return clonedTiles;
    }

    private static Board getGoalBoard(int n) {
        if (!GOALCACHE.containsKey(n)) {
            int[][] goalTiles = new int[n][n];
            for (int row = 0; row < n; row++)
                for (int col = 0; col < n; col++)
                    goalTiles[row][col] = row * n + col + 1;
            goalTiles[n - 1][n - 1] = 0;
            Board goalBoard = new Board(goalTiles);
            GOALCACHE.put(n, goalBoard);
        }
        return GOALCACHE.get(n);
    }

    public int dimension() {
        return n;
    }

    // number of blocks out of place
    public int hamming() {
        Board goalBoard = getGoalBoard(n);
        int sum = 0;
        for (int row = 0; row < n; row++)
            for (int col = 0; col < n; col++)
                if (tiles[row][col] != 0 && tiles[row][col] != goalBoard.tiles[row][col])
                    sum++;
        return sum;
    }

    // sum of Manhattan distances between blocks and goal
    public int manhattan() {
        int sum = 0;
        for (int row = 0; row < n; row++) {
            for (int col = 0; col < n; col++) {
                int value = tiles[row][col];
                if (value-- != 0)
                    sum += Math.abs(row - (value / n)) + Math.abs(col - (value % n));
            }
        }
        return sum;
    }

    // is this board the goal board?
    public boolean isGoal() {
        return this.equals(getGoalBoard(n));
    }

    // a board that is obtained by exchanging any pair of blocks
    public Board twin() {
        int[][] twinTiles = clone(tiles);
        for (int row = 0; row < n; row++) {
            for (int col = 0; col < n - 1; col++) {
                if (twinTiles[row][col] != 0 && twinTiles[row][col + 1] != 0) {
                    int tmp = twinTiles[row][col];
                    twinTiles[row][col] = twinTiles[row][col + 1];
                    twinTiles[row][col + 1] = tmp;
                    return new Board(twinTiles);
                }
            }
        }
        return null;
    }

    // does this board equal y?
    public boolean equals(Object y) {
        if (y == null || y.getClass() != Board.class)
            return false;
        Board that = (Board) y;
        if (n != that.n)
            return false;
        for (int row = 0; row < n; row++)
            for (int col = 0; col < n; col++)
                if (tiles[row][col] != that.tiles[row][col])
                    return false;
        return true;
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        List<Board> l = new ArrayList<Board>();
        for (int row = 0; row < n; row++) {
            for (int col = 0; col < n; col++) {
                if (tiles[row][col] == 0) {
                    if (row > 0) {
                        Board exchUp = new Board(tiles);
                        exchUp.tiles[row][col] = exchUp.tiles[row - 1][col];
                        exchUp.tiles[row - 1][col] = 0;
                        l.add(exchUp);
                    }
                    if (row < n - 1) {
                        Board exchDown = new Board(tiles);
                        exchDown.tiles[row][col] = exchDown.tiles[row + 1][col];
                        exchDown.tiles[row + 1][col] = 0;
                        l.add(exchDown);
                    }
                    if (col > 0) {
                        Board exchLeft = new Board(tiles);
                        exchLeft.tiles[row][col] = exchLeft.tiles[row][col - 1];
                        exchLeft.tiles[row][col - 1] = 0;
                        l.add(exchLeft);
                    }
                    if (col < n - 1) {
                        Board exchRight = new Board(tiles);
                        exchRight.tiles[row][col] = exchRight.tiles[row][col + 1];
                        exchRight.tiles[row][col + 1] = 0;
                        l.add(exchRight);
                    }
                    return l;
                }
            }
        }
        return l;
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
        StdOut.printf("t1 goal? %s\n", t1.isGoal());
        int[][] tiles3 = { { 8, 1, 3 }, { 4, 0, 2 }, { 7, 6, 5 } };
        Board b3 = new Board(tiles3);
        StdOut.printf("b3=%s\nmanhattan=%d hamming=%d", b3, b3.manhattan(), b3.hamming());
        Board b4 = new Board(new int[][] { { 8, 1, 3 }, { 4, 2, 0 }, { 7, 6, 5 } });
        StdOut.printf("b4=%s\nneighbors:\n", b4);
        for (Board neighbor : b4.neighbors())
            StdOut.println(neighbor);
    }
}