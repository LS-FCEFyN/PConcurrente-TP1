package utils;

import ctmtypes.CustomImage;
import ctmtypes.ImageContainer;

import java.util.Collections;
import java.util.concurrent.Callable;

/**
 * This ImageResizer class is responsible for resizing the images from one
 * container, images will only be resized once their brightness has been
 * adjusted trice.
 */
public class ImageResizer implements Callable<Integer> {

    /**
     * The ImageContainer instance to whose images are to be resized.
     */
    private final ImageContainer container;

    /**
     * Constructs an instance of ImageResizer class.
     *
     * @param container an instance of ImageContainer with images to be resized.
     */
    public ImageResizer(final ImageContainer container) {
        this.container = container;
    }

    // TODO Do not throw raw exception types and add proper documentation

    @Override
    public Integer call() {
        final ImageContainer images = new ImageContainer();

        images.addAll(container);

        Collections.shuffle(images);

        boolean allAdjusted = false;

        int adjusted = 0;

        while (!allAdjusted) {
            allAdjusted = true;
            for (final CustomImage image : images) {
                if (!image.isAdjusted() && image.getImprovements() == 3) {
                    adjusted += image.adjust() ? 1 : 0;
                    try {
                        Thread.sleep(110);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                if (!image.isAdjusted()) {
                    allAdjusted = false;
                }
            }
        }
        return adjusted;
    }
}
