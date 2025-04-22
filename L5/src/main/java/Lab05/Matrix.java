package Lab05;
import java.util.Objects;
import java.util.function.BiFunction;
public class Matrix
{
    //region private attributes
    private final int[][] data; // The matrix data.
    private final int rows; // Number of rows in the matrix.
    private final int cols; // Number of columns in the matrix.
    private final int mod; // The modulus for matrix operations.
    //endregion private attributes

    //region Methods
    /**
     * Converts the matrix to a string representation.
     * @return String representation of the matrix.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        try{
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    sb.append(data[i][j]);
                    if (j < cols - 1) sb.append(" ");
                }
                sb.append("\n");
            }
        }catch(RuntimeException e){
             System.out.println("Runtime Exception.\n" + e);
        }
        return sb.toString().trim();
    }

    /**
     * Constructor for creating a matrix.
     * @param rows Number of rows in the matrix.
     * @param cols Number of columns in the matrix.
     * @param mod Modulus for the matrix operations.
     * @param data 2D array containing the data of the matrix.
     * @throws IllegalArgumentException If rows, columns, modulus are not positive, or if the data dimensions don't match rows and columns.
     */
    public Matrix(int rows, int cols, int mod, int[][] data){

        // Checks if the rows, columns, and modulus are positive
        if(rows < 0 || cols < 0 || mod < 0 ){
            throw new IllegalArgumentException("Rows, columns, and modulus must be positive.");
        }
        // Checks if the data is null, if it is not null, it checks if the number of rows and columns match the data
        if( data!= null && (data[0].length != cols || data.length != rows)){
            throw new IllegalArgumentException("The number of rows and columns must match the data.");
        }

        this.rows = rows;
        this.cols = cols;
        this.mod = mod;

        // Assigns the data to null if it is null, otherwise it assigns the data to the data passed in
        this.data = Objects.requireNonNullElseGet(data, () -> new int[rows][cols]);

        try{
            // fill matrix with random values
            for(int i = 0; i < rows; ++i){
                for(int j = 0; j < cols; ++j){
                    if(data!=null){
                        this.data[i][j] = Math.floorMod(data[i][j], mod);
                    }
                    else{
                        this.data[i][j] = 0;
                    }
                }
            }
        }catch(RuntimeException e){
            System.out.println("Runtime Exception.\n" + e);
        }

    }



    /**
     * Creates a matrix with random values.
     * @param rows Number of rows in the matrix.
     * @param cols Number of columns in the matrix.
     * @param mod Modulus for the matrix operations.
     * @return Matrix with random values.
     */
    public static Matrix random(int rows, int cols, int mod){
        int[][] data = new int[rows][cols];

        try{
            for(int i = 0; i < rows; ++i){
                for(int j = 0; j < cols; ++j){
                    data[i][j] =  (int)(Math.random()*mod);
                }
            }
        }catch (RuntimeException e) {
            System.out.println("Runtime Exception.\n" + e);
        }
        return new Matrix(rows, cols, mod, data);
    }


    /**
     * Operates on two matrices using a provided operation.
     * @param other The other matrix to operate on.
     * @param op A BiFunction that specifies the operation to perform.
     * @return Resultant matrix after the operation.
     * @throws IllegalArgumentException If the moduli of the matrices are not the same.
     */

    public Matrix operate(Matrix other, BiFunction<Integer, Integer, Integer> op){
        if(this.mod != other.mod){
            throw new IllegalArgumentException("Matrices must have the same modulus.");
        }
        int maxRows = Math.max(this.rows, other.rows);
        int maxCols = Math.max(this.cols, other.cols);
        Matrix result = new Matrix(maxRows,maxCols, this.mod, null);

        try{
            for(int i = 0; i < maxRows; ++i){
                for(int j = 0; j < maxCols; ++j){

                    int a = (i < this.rows && j < this.cols) ? this.data[i][j] : 0;
                    int b = (i < other.rows && j < other.cols) ? other.data[i][j] : 0;

                    result.data[i][j] = op.apply(a, b); // apply lambda function
                }
            }
        }catch(RuntimeException e){
            System.out.println("Runtime Exception.\n" + e);
        }

        return result;
    }

    /**
     * Adds another matrix to the current matrix.
     * @param other The other matrix to add.
     * @return Resultant matrix after the addition.
     */

    public Matrix add(Matrix other){
        return operate(other, (a, b) -> Math.floorMod(a + b, mod));
    }

    /**
     * Subtracts another matrix from the current matrix.
     * @param other The matrix to subtract.
     * @return Resultant matrix after the subtraction.
     */
    public Matrix sub(Matrix other){
        return operate(other, (a, b) -> Math.floorMod(a - b, mod));
    }

    /**
     * Multiplies the current matrix by another matrix.
     * @param other The matrix to multiply with.
     * @return Resultant matrix after multiplication.
     */
    public Matrix product(Matrix other){
        return operate(other, (a, b) -> Math.floorMod(a * b, mod));
    }

    //endregion Methods

    //region Main
    public static void main( String[] args )
    {
        Matrix rm1 = Matrix.random(3,3,10);
        System.out.println("First Matrix\n");
        System.out.println(rm1 + "\n");
        Matrix m2 = new Matrix(3, 4, 10, new int[][]{
                {1, 3, 1, 1},
                {3, 2, 4, 2},
                {1, 0, 1, 0}
        });
        System.out.println("Second Matrix\n");
        System.out.println(m2 + "\n");

        rm1.add(m2);
        System.out.println("First + Second\n");
        System.out.println(rm1);
    }
    //endregion Main
}
