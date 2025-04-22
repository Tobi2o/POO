package lab4;

/**
 * The Int class serves as a wrapper for the primitive int value.
 * It allows operations such as modifying the encapsulated value,
 * which is not possible with the standard Java API's Integer class.
 */
public class Int {

    private int value;

    /**
     * Constructor initializing the Int object with a specified value.
     *
     * @param value the integer value to be encapsulated.
     */
    public Int(int value) {
        this.value = value;
    }

    /**
     * Retrieves the integer value currently encapsulated in this Int object.
     *
     * @return the encapsulated integer value.
     */
    public int getValue() {
        return value;
    }

    /**
     * Sets a new value for the integer encapsulated in this Int object.
     *
     * @param value the new value to set.
     */
    public void setValue(int value) {
        this.value = value;
    }

    /**
     * Returns a String representation of the encapsulated value.
     *
     * @return the String representation of the encapsulated value.
     */
    @Override
    public String toString() {
        return Integer.toString(value);
    }

    /**
     * Swaps values between this Int object and another specified Int object.
     *
     * @param other the other Int object with which to swap values.
     */
    public void swapValues(Int other) {
        int temp = this.value;
        this.value = other.value;
        other.value = temp;
    }
}

