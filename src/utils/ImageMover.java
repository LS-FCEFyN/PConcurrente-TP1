package utils;

import ctmtypes.ImageContainer;

/**
 * This ImageMover class is responsible for moving all images who have
 * already been processed (.i.e have had their brightness and resolution
 * modified) from one source to a destination
 */
public class ImageMover implements Runnable{

    /**
     * Source container.
     */
    private final ImageContainer src;

    /**
     * Final container.
     */
    private final ImageContainer dest;

    /**
     * Constructs an instance of ImageMover class.
     *
     * @param src an instance of ImageContainer to move images from.
     *
     * @param dest an instance of ImageContainer to move images to.
     */
    public ImageMover(final ImageContainer src, final ImageContainer dest) {
        this.src = src;
        this.dest = dest;
    }

    // TODO Do not throw raw exception types and add proper documentation
    @Override
    public void run() {
        while (!src.isEmpty()) {
            src.removeIf(image -> image.isAdjusted() && (dest.add(image) || true));
            try {
                Thread.sleep(64);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
