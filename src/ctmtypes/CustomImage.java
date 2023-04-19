package ctmtypes;

/**
 * This class represents a thread-safe custom image,
 * it only tracks the number
 * of improvements made to a mock image.
 */
public class CustomImage {

    /**
     * The number of improvements made to the image.
     */
    private int improvements;

    private boolean adjusted;


    /**
     * Constructs a new CustomImage object
     */
    public CustomImage() {
        this.improvements = 0;
        this.adjusted = false;
    }

    public synchronized boolean isAdjusted() {
        return adjusted;
    }

    public synchronized void adjust () {
        adjusted = true;
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
