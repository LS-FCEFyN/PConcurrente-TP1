package ctmtypes;

/**
 * This class represents a thread-safe custom image,
 * it only tracks the number
 * of improvements made to a simulated image.
 */
public class CustomImage {

    /**
     * The number of improvements made to the image.
     */
    private int improvements;

    /**
     * Constructs a new CustomImage object
     */
    public CustomImage() {
        this.improvements = 0;
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
