package utils;

import ctmtypes.CustomImage;
import ctmtypes.ImageContainer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The ImageFilter class is responsible for concurrently manipulating the
 * brightness of all images within an ImageContainer instance.
 * The class contains various methods for manipulating image brightness.
 */
public class ImageFilter implements Runnable {

    /**
     * The ImageContainer object to be filtered.
     */
    private final ImageContainer container;

    /**
     * Constructs a new ImageFilter object with the given ImageContainer.
     *
     * @param container the ImageContainer to be filtered
     */
    public ImageFilter(final ImageContainer container) {
        this.container = container;
    }

    /**
     * Runs brightness histogram equalization on a list of CustomImage
     * objects by generating a pixel mapping based on the brightness
     * histogram of each image and applying the mapping to the image. The
     * order of the images in the list is randomized using the Collections
     * .shuffle() method before applying equalization to each image in the list.
     */
    @Override
    public void run() {
        final ImageContainer images =
                new ImageContainer();

        final List<CustomImage> improvedImages = new ArrayList<>();

        while (improvedImages.size() != 100) {
            images.addAll(container);

            Collections.shuffle(images);

            images.forEach(image -> {
                if (!improvedImages.contains(image)) {
                    image.setImprovements();
                    improvedImages.add(image);
                }
                try {
                    Thread.sleep(88);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });

        }


    }

}