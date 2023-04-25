package utils;

import ctmtypes.CustomImage;
import ctmtypes.ImageContainer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

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

    @Override
    public AtomicInteger call() {
        final ImageContainer images = new ImageContainer();

        // Variables inside a lambda must be final or effectively final
        final AtomicInteger adjusted = new AtomicInteger();

        // While all the images in the original container are not adjusted
        // Manipulate the images stream as follows
        while (!container.stream().allMatch(CustomImage::isAdjusted)) {
            images.addAll(container.stream().
                    filter(image -> !image.isAdjusted() &&
                            image.getImprovements() == 3 ).
                    collect(Collectors.toCollection(ArrayList::new)));
            Collections.shuffle(images);
            images.forEach(image -> adjusted.addAndGet(image.adjust() ? 1 :
                            0));
            images.clear();

            try {
                Thread.sleep(78);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        }

        return adjusted;
    }
}
