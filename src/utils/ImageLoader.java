package utils;

import ctmtypes.CustomImage;
import ctmtypes.ImageContainer;

/**
 * The ImageLoader class is responsible for storing CustomImage objects in
 * the provided ImageContainer instance.
 */
public class ImageLoader implements Runnable {

    /**
     * The ImageContainer instance to store the loaded images.
     */
    private final ImageContainer container;

    /**
     * Constructs an instance of ImageLoader class.
     *
     * @param container an instance of ImageContainer to store the loaded
     *                  images.
     */
    public ImageLoader(final ImageContainer container) {
        this.container = container;
    }

    // TODO Do not throw raw exception types
    /**
     * Loads image files from the input path and stores them in the
     * ImageContainer instance.
     */
    @Override
    public void run() {
        while (container.add(new CustomImage())) {
            try {
                Thread.sleep(44);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}

