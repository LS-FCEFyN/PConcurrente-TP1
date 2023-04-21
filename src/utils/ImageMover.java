package utils;

import ctmtypes.ImageContainer;
import ctmtypes.ImageContainerIterator;

import java.util.concurrent.Callable;

/**
 * This ImageMover class is responsible for moving all images who have
 * already been processed (.i.e have had their brightness and resolution
 * modified) from one source to a destination
 */
public class ImageMover implements Callable<Integer> {

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

    // TODO write proper documentation
    @Override
    public Integer call() {
        final int[] adjusted = {0};
        ImageContainerIterator iterator = new ImageContainerIterator(src);

        iterator.forEachRemaining(image -> {if(image.isAdjusted() && dest.add(image)){
            adjusted[0]++;}});

        return adjusted[0];
    }

}
