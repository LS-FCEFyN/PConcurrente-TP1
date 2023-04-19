package utils;

import ctmtypes.CustomImage;
import ctmtypes.ImageContainer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ImageResizer implements Runnable {
    private final ImageContainer container;

    public ImageResizer(final ImageContainer container) {
        this.container = container;
    }

    @Override
    public void run() {
        final List<CustomImage> images = new ArrayList<>(container.getContainer());

        Collections.shuffle(images);

        boolean allAdjusted = false;
        while (!allAdjusted) {
            allAdjusted = true;
            for (final CustomImage image : images) {
                if (!image.isAdjusted() && image.getImprovements() == 3) {
                    image.adjust();
                }
                if (!image.isAdjusted()) {
                    allAdjusted = false;
                }
            }
        }
    }
}
