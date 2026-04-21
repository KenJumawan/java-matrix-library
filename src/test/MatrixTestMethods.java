package edu.commonwealthu.mrodriguez.cmsc230.matrixlab.test;

import edu.commonwealthu.mrodriguez.cmsc230.matrixlab.DimensionMismatchException;
import edu.commonwealthu.mrodriguez.cmsc230.matrixlab.Matrix;

/**
 * A class containing unit test methods for Matrix.
 * Only contains a few, to serve as examples.
 */
class MatrixTestMethods {

    // A set of example matrices (add more if desired.)
    // WARNING: If you write new tests that use these, don't modify them, as that may break future tests!
    // If you want to write a test that involves modifying a matrix, make a copy!
    public static final Matrix TEST_MATRIX_2X2_1 = new Matrix(new double[][]{{1, 2}, {3, 4}});
    public static final Matrix TEST_MATRIX_2X3_1 = new Matrix(new double[][]{{1, 2}, {3, 4}, {5, 6}});
    public static final Matrix TEST_MATRIX_2X3_2 = new Matrix(new double[][]{{8, 4}, {7, 9}, {4, 2}});
    public static final Matrix TEST_MATRIX_3X2_1 = new Matrix(new double[][]{{1, 2, 3}, {4, 5, 6}});

    /**
     * An example test. Ensures the sum of two matrices is correct.
     *
     * @return true if the sum matches the expected value; false if not.
     * @throws DimensionMismatchException If Matrix.add() incorrectly throws it. (This is a test failure condition.)
     */
    public static boolean addBasicTest() throws DimensionMismatchException {
        Matrix expected = new Matrix(new double[][]{{9, 6}, {10, 13}, {9, 8}});
        Matrix actual = TEST_MATRIX_2X3_1.add(TEST_MATRIX_2X3_2);
        return expected.equals(actual);
    }

    /**
     * An example test for verifying expected exceptions.
     * When adding a 2x2 matrix to a 2x3 matrix, we expect a DimensionMismatchException.
     * This test ensures that this does, in fact, happen.
     *
     * @return true if DimensionMismatchException is correctly thrown; false otherwise.
     */
    public static boolean addDimensionMismatchTest() {
        try {
            TEST_MATRIX_2X2_1.add(TEST_MATRIX_2X3_1);
        } catch (Exception e) {
            // The expected exception was thrown. The test passed; return true.
            return e instanceof DimensionMismatchException;
        }

        // The expected exception was not thrown. The test failed; return false.
        return false;
    }

    // STUDENTS: I suggest writing more test methods here, then adding them to TestSuite.
}
