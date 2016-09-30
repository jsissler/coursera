import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

public class Solver {
    private int moves = 0;
    private List<Board> solution = new ArrayList<Board>();
    
    private class ManhattanPriority implements Comparator<Board> {
        public int compare(Board b1, Board b2) {
            StdOut.printf("cpmpare b1=\n%s\nb2=\n%s\n", b1, b2);
            return b1.manhattan() - b2.manhattan();
        }
    }

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        MinPQ<Board> pq = new MinPQ<Board>(new ManhattanPriority());
        pq.insert(initial);
        Board min = pq.delMin();
        do {
            solution.add(min);
            if (!min.isGoal()) {
                for (Board neighbor : min.neighbors())
                    pq.insert(neighbor);
            }
            min = pq.delMin();
            moves++;
            break;
        } while (!min.isGoal());
    }

    // is the initial board solvable?
    public boolean isSolvable() {
        return true;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        return moves;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        return solution;
    }

    // solve a slider puzzle (given below)
    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);
        
        ////
        initial = new Board(new int[][] {{1, 2}, {3, 0}});
        StdOut.printf("initial=%s goal? %s\n", initial, initial.isGoal());

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
}