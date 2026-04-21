package edu.commonwealthu.mrodriguez.cmsc230.matrixlab;


public class Matrix implements Comparable<Matrix> {
    // Dimensions
    private final int rows;
    private final int cols;

    private final double[][] data;

    /**
     * @param rows number of rows (>= 1)
     * @param cols number of columns (>= 1)
     * @throws IllegalArgumentException if rows or cols < 1
     */
    public Matrix(int rows, int cols) {
        if (rows < 1 || cols < 1) {
            throw new IllegalArgumentException("rows and cols must both be >= 1");
        }
        this.rows = rows;
        this.cols = cols;
        this.data = new double[rows][cols];
    }

    /**
     * Construct a matrix by deep-copying a rectangular 2D array.
     *
     * @param that rectangular array
     * @throws IllegalArgumentException if array is null or empty
     */
    public Matrix(double[][] that) {
        if (that == null || that.length == 0 || that[0] == null || that[0].length == 0) {
            throw new IllegalArgumentException("input must be non-null");
        }
        int r = that.length;
        int c = that[0].length;
        for (int i = 1; i < r; i++) {
            if (that[i] == null || that[i].length != c) {
                throw new IllegalArgumentException("input must be rectangular (all rows same length)");
            }
        }
        this.rows = r;
        this.cols = c;
        this.data = new double[rows][cols];
        // Deep copy cell-by-cell (spec requires copy, not adoption)
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                this.data[i][j] = that[i][j];
            }
        }
    }

    /**
     * @param that matrix to copy (non-null)
     * @throws NullPointerException if {@code that} is null
     */
    public Matrix(Matrix that) {
        if (that == null) {
            throw new NullPointerException("matrix to copy is null");
        }
        this.rows = that.rows;
        this.cols = that.cols;
        this.data = new double[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                this.data[i][j] = that.data[i][j];
            }
        }
    }

    /**
     * Write a single value to (row, col).
     *
     * @param row zero-based row index
     * @param col zero-based column index
     * @param value value to store
     * @throws IndexOutOfBoundsException if indices are out of range
     */
    public void setEntry(int row, int col, double value) {
        checkBounds(row, col);
        data[row][col] = value;
    }

    /**
     * Element-wise sum: this + that (same shape).
     *
     * @param that other matrix
     * @return new matrix equal to the element-wise sum
     * @throws DimensionMismatchException if dimensions differ
     */
    public Matrix add(Matrix that) throws DimensionMismatchException {
        requireSameShape(that, "add");
        Matrix out = new Matrix(rows, cols);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                out.data[i][j] = this.data[i][j] + that.data[i][j];
            }
        }
        return out;
    }

    /**
     * Element-wise difference: this - that (same shape).
     *
     * @param that other matrix
     * @return new matrix equal to the element-wise difference
     * @throws DimensionMismatchException if dimensions differ
     */
    public Matrix subtract(Matrix that) throws DimensionMismatchException {
        requireSameShape(that, "subtract");
        Matrix out = new Matrix(rows, cols);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                out.data[i][j] = this.data[i][j] - that.data[i][j];
            }
        }
        return out;
    }

    /**
     * @param that right-hand matrix
     * @return product matrix
     * @throws DimensionMismatchException if this.cols != that.rows
     */
    public Matrix multiply(Matrix that) throws DimensionMismatchException {
        if (that == null) {
            throw new NullPointerException("that is null");
        }
        if (this.cols != that.rows) {
            throw new DimensionMismatchException(
                    "Cannot multiply: this.cols=" + this.cols + " != that.rows=" + that.rows
            );
        }
        Matrix out = new Matrix(this.rows, that.cols);
        // Classic triple loop; simple and clear
        for (int i = 0; i < this.rows; i++) {
            for (int k = 0; k < this.cols; k++) {
                double a = this.data[i][k];
                for (int j = 0; j < that.cols; j++) {
                    out.data[i][j] += a * that.data[k][j];
                }
            }
        }
        return out;
    }

    /**
     * @return a new matrix that is the transpose of this matrix
     */
    public Matrix transpose() {
        Matrix t = new Matrix(cols, rows);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                t.data[j][i] = this.data[i][j];
            }
        }
        return t;
    }

    /**
     * @param other any object
     * @return true if other is a Matrix with same size and all equal entries; false otherwise
     */
    public boolean equals(Object other) {
        if (this == other) return true;
        if (!(other instanceof Matrix)) return false;
        Matrix that = (Matrix) other;
        if (this.rows != that.rows || this.cols != that.cols) return false;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (Double.compare(this.data[i][j], that.data[i][j]) != 0) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Magnitude = sum of absolute values of all cells.
     * @return magnitude of this matrix
     */
    public double magnitude() {
        double sum = 0.0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                sum += Math.abs(data[i][j]);
            }
        }
        return sum;
    }

    /**
     * Natural ordering: ascending
     * @param other other matrix (non-null)
     * @return negative if this < other; zero if equal; positive if this > other
     * @throws NullPointerException if other is null
     */
    @Override
    public int compareTo(Matrix other) {
        if (other == null) throw new NullPointerException("other is null");
        return Double.compare(this.magnitude(), other.magnitude());
    }

    /**
     * Pretty print for quick debugging—rows on separate lines.
     */
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Matrix(").append(rows).append("x").append(cols).append(")\n");
        for (int i = 0; i < rows; i++) {
            sb.append("[ ");
            for (int j = 0; j < cols; j++) {
                sb.append(String.format("%8.3f", data[i][j]));
                if (j + 1 < cols) sb.append(' ');
            }
            sb.append(" ]\n");
        }
        return sb.toString();
    }


    // private helpers
    /** Bounds check for (row, col). */
    private void checkBounds(int row, int col) {
        if (row < 0 || row >= rows || col < 0 || col >= cols) {
            throw new IndexOutOfBoundsException(
                    "Index (" + row + "," + col + ") out of bounds for " + rows + "x" + cols
            );
        }
    }

    /** Ensure shapes match for element-wise operations. */
    private void requireSameShape(Matrix that, String opName) throws DimensionMismatchException {
        if (that == null) {
            throw new NullPointerException("that is null");
        }
        if (this.rows != that.rows || this.cols != that.cols) {
            throw new DimensionMismatchException(
                    "Cannot " + opName + ": shapes differ (this=" + rows + "x" + cols +
                            ", that=" + that.rows + "x" + that.cols + ")"
            );
        }
    }
    public static void main(String[] args) throws Exception {
            Matrix A = new Matrix(new double[][]{{1, 2}, {3, 4}});
            Matrix B = new Matrix(new double[][]{{5, 6}, {7, 8}});
            System.out.println("A:\n" + A);
            System.out.println("B:\n" + B);
            System.out.println("A+B:\n" + A.add(B));
            System.out.println("A-B:\n" + A.subtract(B));
            System.out.println("A^T:\n" + A.transpose());
            System.out.println("mag(A): " + A.magnitude());
    }
}

