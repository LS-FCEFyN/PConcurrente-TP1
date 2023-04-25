package ctmtypes;

/**
 * This class represents a thread-safe custom image,
 * it only tracks the number
 * of improvements made to a mock image and whether if it has been adjusted
 * or not.
 */
public class CustomImage {

    /**
     * The number of improvements made to the image.
     */
    private int improvements;

    /**
     * Whether if the image has been resized or not.
     */
    private boolean adjusted;


    /**
     * Constructs a new CustomImage object
     */
    public CustomImage() {
        this.improvements = 0;
        this.adjusted = false;
    }

    /**
     * @return true if the image has been resized, false if it hasn't been
     * resized
     */
    public synchronized boolean isAdjusted() {
        return adjusted;
    }

    /**
     * Set the value of adjusted to true once the image has been resized
     */
    public synchronized boolean adjust() {
        return !isAdjusted() && (adjusted = true);
    }

    /**
     * Returns the number of improvements made to the image.
     *
     * @return the number of improvements.
     */
    public synchronized int getImprovements() {
        return improvements;
    }

    /**
     * Sets the number of improvements made to the image.
     */
    public synchronized void setImprovements() {
        this.improvements += 1;
    }
}
