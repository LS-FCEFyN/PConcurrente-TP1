package utils;

import ctmtypes.ImageContainer;

public class ImageMover implements Runnable{

    /**
     * Source container.
     */
    private final ImageContainer src;

    /**
     * Final container.
     */
    private final ImageContainer dest;

    public ImageMover(final ImageContainer src, final ImageContainer dest) {
        this.src = src;
        this.dest = dest;
    }


    @Override
    public void run() {
        src.getContainer().removeIf(image -> {
            if (image.isAdjusted()) {
                dest.addImage(image);
                return true;
            } else {
                return false;
            }
        });
    }
}
