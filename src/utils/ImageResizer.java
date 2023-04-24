package utils;

import ctmtypes.CustomImage;
import ctmtypes.ImageContainer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * This ImageResizer class is responsible for resizing the images from one
 * container, images will only be resized once their brightness has been
 * adjusted trice.
 */
public class ImageResizer implements Callable<AtomicInteger> {

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
    public AtomicInteger call() {
        final ImageContainer images = new ImageContainer();

        images.addAll(container);

        Collections.shuffle(images);

        AtomicInteger adjusted = new AtomicInteger();

        // While all the images in the original container are not adjusted
        // Manipulate the images stream as follows
        while(!images.stream().allMatch(CustomImage::isAdjusted)) {
            images.stream().
                    // Filter through all the images and return
                    // those that are both not yet adjusted and have had
                    // their brightness adjusted thrice
                    filter(image -> !image.isAdjusted() &&
                            image.getImprovements() == 3).
                    forEach(image -> adjusted.addAndGet(image.adjust() ? 1 :
                            0));
            images.addAll(container);

            Collections.shuffle(images);

            try {
                Thread.sleep(78);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        }

        return adjusted;
    }
}
