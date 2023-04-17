package ctmtypes;

import java.awt.image.BufferedImage;

/**
 * This class represents a custom image,
 * which is a wrapper for a BufferedImage object and tracks the number
 * of improvements made to the image.
 */
public class CustomImage {
    /**
     * The underlying BufferedImage object.
     */
    private BufferedImage image;

    /**
     * The number of improvements made to the image.
     */
    private int improvements;

    /**
     * Constructs a new CustomImage object with the given BufferedImage.
     *
     * @param image the BufferedImage to wrap.
     */
    public CustomImage(final BufferedImage image) {
        this.image = image;
        this.improvements = 0;
    }


    /**
     * Returns the underlying BufferedImage object.
     *
     * @return the BufferedImage object.
     */
    public BufferedImage getImage() {
        return image;
    }

    /**
     * Returns the number of improvements made to the image.
     *
     * @return the number of improvements.
     */
    public int getImprovements() {
        return improvements;
    }

    /**
     * Sets the number of improvements made to the image.
     *
     * @param improvements the number of improvements.
     */
    public void setImprovements(final BufferedImage improved) {
        this.improvements += 1;
        this.image = improved;
    }
}
