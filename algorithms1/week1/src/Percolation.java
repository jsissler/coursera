import edu.princeton.cs.algs4.WeightedQuickUnionUF;

/**
 * Class Percolation. Uses a union-find implementation to maintain a N x N grid
 * of sites which post configuration may exhibit the property of percolation, or
 * one or more path connecting top row and bottom row of grid.
 * 
 * This class features a small hard-coded main test driver but is also
 * instantiated by class PercolationStats and PercolationVisualizer which have
 * their own main test drivers.
 * 
 * @author John Sissler
 *
 */
public class Percolation {
	/**
	 * n - grid size, N x N
	 */
	private final int n;
	/**
	 * virtualTopRow - virtual row representing top of grid.
	 */
	private final int virtualTopRow;
	/**
	 * virtualBottomRow - virtual row representing bottom of grid.
	 */
	private final int virtualBottomRow;
	/**
	 * TOP_ROW - constant for readability, top row index 1.
	 */
	private final int TOP_ROW = 1;
	/**
	 * BOTTOM_ROW - constant for readability, bottom grid row index n.
	 */
	private final int BOTTOM_ROW;

	/**
	 * ufWithTopBottom - Union-find implementation with both virtual top and
	 * bottom used to determine percolation state.
	 */
	private final WeightedQuickUnionUF ufWithTopBottom;

	/**
	 * ufWithTop - Union-find implementation with just virtual top used to
	 * determine isFull state avoiding back wash when percolated state causes
	 * all bottom-connected sites to appear full.
	 */
	private final WeightedQuickUnionUF ufWithTop;

	/**
	 * isOpen - Array tracking if site is open or blocked.
	 */
	private final boolean[] isOpen;

	/**
	 * Create n-by-n grid, with all sites blocked.
	 * 
	 * @param gridSize size of grid
	 */
	public Percolation(final int gridSize) {
		if (gridSize <= 0) {
			throw new java.lang.IllegalArgumentException(
			        "n must be greater than 0");
		}
		BOTTOM_ROW = n = gridSize;
		virtualTopRow = n * n;
		virtualBottomRow = n * n + 1;
		ufWithTopBottom = new WeightedQuickUnionUF(n * n + 2);
		ufWithTop = new WeightedQuickUnionUF(n * n + 1);
		isOpen = new boolean[n * n];
	}

	/**
	 * Validate row col params.
	 * 
	 * @param row row 1-n
	 * @param column col 1-n
	 */
	private void checkRowColIndices(final int row, final int column) {
		if (row < TOP_ROW || row > BOTTOM_ROW || column < TOP_ROW
		        || column > BOTTOM_ROW) {
			throw new java.lang.IndexOutOfBoundsException(
			        "grid indices must be between 1 and n");
		}
	}

	/**
	 * Compute 1-dim index of grid row and col.
	 * 
	 * @param row row 1-n
	 * @param column col 1-n
	 * @return 1-dim index
	 */
	private int gridIndex(final int row, final int column) {
		return (row - 1) * n + (column - 1);
	}

	/**
	 * Open site (row i, column j) if it is not.
	 * 
	 * @param row row 1-n
	 * @param column col 1-n
	 */
	public final void open(final int row, final int column) {
		checkRowColIndices(row, column);

		if (!isOpen(row, column)) {
			int gridIndex = gridIndex(row, column);

			isOpen[gridIndex] = true;

			if (row == 1) {
				ufWithTopBottom.union(gridIndex, virtualTopRow);
				ufWithTop.union(gridIndex, virtualTopRow);
			} else if (isOpen(row - 1, column)) {
				ufWithTopBottom.union(gridIndex, gridIndex(row - 1, column));
				ufWithTop.union(gridIndex, gridIndex(row - 1, column));
			}

			if (row == n) {
				ufWithTopBottom.union(gridIndex, virtualBottomRow);
			} else if (isOpen(row + 1, column)) {
				ufWithTopBottom.union(gridIndex, gridIndex(row + 1, column));
				ufWithTop.union(gridIndex, gridIndex(row + 1, column));
			}

			if (column > 1 && isOpen(row, column - 1)) {
				ufWithTopBottom.union(gridIndex, gridIndex(row, column - 1));
				ufWithTop.union(gridIndex, gridIndex(row, column - 1));
			}

			if (column < n && isOpen(row, column + 1)) {
				ufWithTopBottom.union(gridIndex, gridIndex(row, column + 1));
				ufWithTop.union(gridIndex, gridIndex(row, column + 1));
			}
		}
	}

	/**
	 * Is site (row i, column j) open?
	 * 
	 * @param i row 1-n
	 * @param j col 1-n
	 * @return if site is open.
	 */
	public final boolean isOpen(final int i, final int j) {
		checkRowColIndices(i, j);
		return isOpen[gridIndex(i, j)];
	}

	/**
	 * Is site (row i, column j) full?
	 * 
	 * @param i row 1-n
	 * @param j col 1-n
	 * @return if site is open and connected to top row
	 */
	public final boolean isFull(final int i, final int j) {
		checkRowColIndices(i, j);
		return isOpen(i, j)
		        && ufWithTop.connected(gridIndex(i, j), virtualTopRow);
	}

	/**
	 * Does the system percolate?
	 * 
	 * @return true if percolates
	 */
	public final boolean percolates() {
		return ufWithTopBottom.connected(TOP_ROW, BOTTOM_ROW);
	}

	/**
	 * Test driver.
	 * 
	 * @param args Unused
	 */
	public static void main(final String[] args) {
		int n = 5;
		Percolation p = new Percolation(n);
		p.open(1, 1);
		p.open(2, 1);
		p.open(3, 1);
		p.open(4, 1);
		PercolationVisualizer.draw(p, n);
	}

}
