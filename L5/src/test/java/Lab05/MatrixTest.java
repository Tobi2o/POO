package Lab05;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;



public class MatrixTest {

    /**
     * Tests the basic matrix operations: addition, subtraction, and multiplication.
     */
    @Test
    public void testNominalCase() {
        // Initialize matrices for testing
        Matrix m1 = new Matrix(3, 4, 5, new int[][]{
                {1, 3, 1, 1},
                {3, 2, 4, 2},
                {1, 0, 1, 0}
        });

        Matrix m2 = new Matrix(3, 5, 5, new int[][]{
                {1, 4, 2, 3, 2},
                {0, 1, 0, 4, 2},
                {0, 0, 2, 0, 2}
        });

        Matrix resultAdd = new Matrix(3, 5, 5, new int[][]{
                {2, 2, 3, 4, 2},
                {3, 3, 4, 1, 2},
                {1, 0, 3, 0, 2}
        });


        // Expected results
        Matrix resultSub = new Matrix(3, 5, 5, new int[][]{
                {0, 4, 4, 3, 3},
                {3, 1, 4, 3, 3},
                {1, 0, 4, 0, 3}
        });

        Matrix resultMult = new Matrix(3, 5, 5, new int[][]{
                {1, 2, 2, 3, 0},
                {0, 2, 0, 3, 0},
                {0, 0, 2, 0, 0}
        });

        // Assertions
        assertEquals(resultAdd.toString(), m1.add(m2).toString());
        assertEquals(resultSub.toString(), m1.sub(m2).toString());
        assertEquals(resultMult.toString(), m1.product(m2).toString());
    }

    /**
     * Tests the chaining of matrix operations.
     */
    @Test
    public void testChainOperations() {
        // Initialize matrices for testing
        Matrix m1 = new Matrix(3, 3, 10, new int[][]{
                {1, 1, 1},
                {2, 2, 2},
                {3, 3, 3}
        });

        Matrix m2 = new Matrix(3, 5, 10, new int[][]{
                {1, 1, 1, 1, 1},
                {1, 1, 1, 1, 1},
                {1, 1, 1, 1, 1}
        });

        // Expected results
        Matrix result = new Matrix(3, 5, 10, new int[][]{
                {3, 3, 3, 2, 2},
                {4, 4, 4, 2, 2},
                {5, 5, 5, 2, 2}
        });

        m1 = m1.add(m2).add(m2);

        assertEquals(result.toString(), m1.toString());
    }

    /**
     * Tests the addition of matrices with different modulo.
     */
    @Test
    public void testAdditionWithDifferentModulus() {
        assertThrows(IllegalArgumentException.class, () ->
                new Matrix(3, 4, 5, null).add(new Matrix(3, 4, 6, null)));
    }

    /**
     * Tests the subtraction of matrices with different modulo.
     */
    @Test
    public void testSubtractionWithDifferentModulus() {
        assertThrows(IllegalArgumentException.class, () ->
                new Matrix(1, 4, 1, null).sub(new Matrix(10, 11, 6, null)));
    }

    /**
     * Tests the multiplication of matrices with different moduli.
     */
    @Test
    public void testMultiplicationWithDifferentModulus() {
        assertThrows(IllegalArgumentException.class, () ->
                new Matrix(100, 200, 1000, null).product(new Matrix(66, 1, 1001, null)));
    }

    /**
     * Tests operations on empty matrices.
     */
    @Test
    public void testOperationsOnEmptyMatrices() {
        Matrix m1 = new Matrix(0, 0, 5, null);
        Matrix m2 = new Matrix(0, 0, 5, null);

        assertEquals(m1.toString(), m1.add(m2).toString());
        assertEquals(m1.toString(), m1.sub(m2).toString());
        assertEquals(m1.toString(), m1.product(m2).toString());
    }

    /**
     * Tests matrix constructor with invalid parameters.
     */
    @Test
    public void testMatrixConstructorWithInvalidParams() {
        assertThrows(IllegalArgumentException.class, () ->
                new Matrix(-1, 4, 5, null)); // negative rows
        assertThrows(IllegalArgumentException.class, () ->
                new Matrix(3, -4, 5, null)); // negative cols
        assertThrows(IllegalArgumentException.class, () ->
                new Matrix(3, 4, -5, null)); // negative modulus
    }
}

