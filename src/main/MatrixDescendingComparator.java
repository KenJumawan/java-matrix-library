package edu.commonwealthu.mrodriguez.cmsc230.matrixlab;

import java.util.Comparator;

public class MatrixDescendingComparator implements Comparator<Matrix> {
    private static MatrixDescendingComparator instance = new MatrixDescendingComparator();

    private MatrixDescendingComparator() {
        // No-op (singleton)
    }

    /**
     * @return singleton instance for reuse
     */
    public static MatrixDescendingComparator getInstance() {
        if (instance == null) {
            instance = new MatrixDescendingComparator();
        }
        return instance;
    }

    /**
     * Reverse of natural ordering.
     * If natural order is ascending by magnitude, this is descending by magnitude.
     *
     * @param m first matrix
     * @param n second matrix
     * @return negative if m should come BEFORE n in descending order
     *         (i.e., m has GREATER magnitude)
     * @throws NullPointerException if either argument is null
     */
    public int compare(Matrix m, Matrix n) {
        // Keep it simple and directly reverse the natural order.
        return -m.compareTo(n);
    }
}
