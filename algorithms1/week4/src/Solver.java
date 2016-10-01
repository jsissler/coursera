import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

public class Solver {
    private class SearchNode implements Comparable<SearchNode> {
        private final Board board;
        private int moves;
        private final SearchNode previous;
        private int priority = -1;

        public SearchNode(Board board, int moves, SearchNode previous) {
            this.board = board;
            this.moves = moves;
            this.previous = previous;
        }

        private int priority() {
            if (priority == -1)
                priority = moves + board.manhattan();
            return priority;
        }

        public int compareTo(SearchNode arg0) {
            if (priority() < arg0.priority())
                return -1;
            if (priority() > arg0.priority())
                return 1;
            return 0;
        }
    }

    private List<Board> solution = new ArrayList<Board>();

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null)
            throw new NullPointerException();

        MinPQ<SearchNode> pq = new MinPQ<SearchNode>();
        pq.insert(new SearchNode(initial, 0, null));
        SearchNode min = pq.delMin();

        MinPQ<SearchNode> pqt = new MinPQ<SearchNode>();
        pqt.insert(new SearchNode(initial.twin(), 0, null));
        SearchNode mint = pqt.delMin();

        int iterations = 0, maxIterations = 1000;

        while (!min.board.isGoal() && !mint.board.isGoal() && iterations++ < maxIterations) {
            min.moves++;
            for (Board neighbor : min.board.neighbors()) {
                if (min.previous != null && neighbor.equals(min.previous.board))
                    continue;
                SearchNode minNeighbor = new SearchNode(neighbor, min.moves, min);
                pq.insert(minNeighbor);
            }
            min = pq.delMin();

            mint.moves++;
            for (Board neighbor : mint.board.neighbors()) {
                if (mint.previous != null && neighbor.equals(mint.previous.board))
                    continue;
                SearchNode mintNeighbor = new SearchNode(neighbor, mint.moves, mint);
                pqt.insert(mintNeighbor);
            }
            mint = pqt.delMin();
        }

        if (min.board.isGoal()) {
            Stack<Board> stack = new Stack<Board>();
            while (min != null) {
                stack.push(min.board);
                min = min.previous;
            }
            while (!stack.isEmpty())
                solution.add(stack.pop());
        }
    }

    // is the initial board solvable?
    public boolean isSolvable() {
        return !solution.isEmpty();
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        return isSolvable() ? solution.size() - 1 : -1;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        return isSolvable() ? solution : null;
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