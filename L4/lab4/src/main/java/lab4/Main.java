package lab4;
import java.util.*;

/**
 * Main class containing methods for array manipulation and sorting.
 */
public class Main {

    // Constant characters for specific ASCII symbols.
    private static final char ZERO_ASCII = '0', MINUS_ASCII = '-', PLUS_ASCII = '+';


    /**
     * Swaps the elements at the specified positions in the provided array.
     *
     * @param array The array in which to swap elements.
     * @param idx The index of the first element to be swapped.
     * @param idx2 The index of the second element to be swapped.
     */
    public static void swapElements(Int[] array, int idx, int idx2) {
        try{
            Int temp = array[idx];
            array[idx] = array[idx2];
            array[idx2] = temp;
        }catch(ArrayIndexOutOfBoundsException e) {
            System.out.println("Erreur lors de l'échange d'éléments aux indices " + idx + " et " + idx2 + ": "
                    + e.getMessage());
        }
    }

    /**
     * Swaps the values of two Int objects.
     *
     * @param first The first Int object.
     * @param second The second Int object.
     */
    public static void swapObjectValues(Int first, Int second) {
        int temp = first.getValue();
        first.setValue(second.getValue());
        second.setValue(temp);
    }


    public static void main(String[] args) {

        if (args.length == 0) return;

        Int[] table = new Int[args.length];
        Int iniVal = new Int(0);
        Arrays.fill(table,iniVal);

        try{
            //Parse each string of the array
            for (int i = 0; i < args.length; i++) {
                Int temp = new Int(stringToInt(args[i]));
                table[i] = temp;
            }
        }catch(Exception e)
        {
            System.out.println("Error : " + e);
            return;
        }

        System.out.println("=== Before Sorting ===\n");
        for (int i = 0; i < table.length; ++i) {
            System.out.println("Index " + i + ": " + table[i]);
        }

        System.out.println("\n");

        quickSort(table, 0, table.length - 1);

        System.out.println("=== After Sorting ===\n");

        for (int i = 0; i < table.length; ++i) {
            System.out.println("Index " + i + ": " + table[i]);
        }
    }

    /**
     * Performs a quicksort on the specified array of Int objects.
     *
     * @param array The array to be sorted.
     * @param lowerBound The start index from where to begin the sorting.
     * @param upperBound The end index where the sorting should stop.
     */
    public static void quickSort(Int[] array, int lowerBound, int upperBound){
        if(lowerBound < upperBound){
            // partition
            int pivot = partition(array, lowerBound, upperBound);

            quickSort(array, lowerBound, pivot - 1);
            quickSort(array, pivot + 1, upperBound);

        }
    }

    /**
     * Partitions the given segment of the array so elements lower than the pivot are on the left, and elements greater than the pivot are on the right.
     *
     * @param array The array to partition.
     * @param lowerBound The lower bound of the segment to partition.
     * @param upperBound The upper bound of the segment to partition.
     * @return The final position of the pivot after partitioning.
     */
    public static int partition(Int[] array, int lowerBound, int upperBound)
    {
        // pivot is the upperBound instead of a random pivot
        int pivot = array[upperBound].getValue();
        int i = lowerBound - 1;

        for (int j = lowerBound; j < upperBound; j++) {
            // compare with pivot
            if (array[j].getValue() <= pivot) {
                i++;
                swapElements(array, i, j);
            }
        }

        swapElements(array, i+1, upperBound);
        return i + 1;
    }

    /**
     * Converts a string representation of a number into an integer, handling optional '+' and '-' signs at the beginning.
     *
     * @param currentString The string to convert.
     * @return The integer value represented by the string.
     */
    public static int stringToInt(String currentString) {

        int result = 0;

        char firstChar = currentString.charAt(0);

        boolean isNegative = firstChar == MINUS_ASCII;

        if (firstChar == MINUS_ASCII || firstChar == PLUS_ASCII)
            currentString = currentString.substring(1);


        for (int j = currentString.length() - 1, k = 0; j >= 0; j--, k++) {
            char currentChar = currentString.charAt(k);
            result += (currentChar - ZERO_ASCII) * (int)Math.pow(10, j);
        }

        return isNegative ? -result : result;
    }
}




