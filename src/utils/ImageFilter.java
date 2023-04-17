package utils;

import ctmtypes.CustomImage;

import java.awt.*;
import java.awt.image.BufferedImage;
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
     * Generates a histogram equalization map for the given input image.
     *
     * @param inputImage the input image to generate the histogram equalization
     *                   map for
     * @return an array representing the histogram equalization map
     */
    private int[] generateMap(final BufferedImage inputImage) {
        final BufferedImage hsbImage = new BufferedImage(
                inputImage.getWidth(),
                inputImage.getHeight(),
                BufferedImage.TYPE_INT_RGB);

        for (int x = 0; x < inputImage.getWidth(); x++) {
            for (int y = 0; y < inputImage.getHeight(); y++) {
                final int rgb = inputImage.getRGB(x, y);

                final float[] hsb = Color.RGBtoHSB(
                        (rgb >> 16) & 0xFF,
                        (rgb >> 8) & 0xFF,
                        (rgb) & 0xFF,
                        null
                );

                hsbImage.setRGB(x, y, Color.HSBtoRGB(hsb[0], hsb[1], hsb[2]));
            }
        }

        float[] brightnessValues = new float[256];
        for (int x = 0; x < hsbImage.getWidth(); x++) {
            for (int y = 0; y < hsbImage.getHeight(); y++) {
                final int rgb = hsbImage.getRGB(x, y);
                final float[] hsb = Color.RGBtoHSB((rgb >> 16) & 0xFF,
                        (rgb >> 8) & 0xFF,
                        (rgb) & 0xFF,
                        null);
                final int brightnessIndex = (int) (hsb[2] * 255);
                brightnessValues[brightnessIndex]++;
            }
        }

        final int numPixels = hsbImage.getWidth() * hsbImage.getHeight();

        float[] cdf = new float[256];
        cdf[0] = brightnessValues[0] / (float) numPixels;
        for (int i = 1; i < 256; i++) {
            cdf[i] = cdf[i - 1] + brightnessValues[i] / (float) numPixels;
        }

        for (int i = 0; i < 256; i++) {
            cdf[i] /= cdf[255];
        }

        int[] map = new int[256];
        for (int i = 0; i < 256; i++) {
            map[i] = Math.round(cdf[i] * 255);
        }

        return map;

    }

    /**
     * Applies histogram equalization to the given input image.
     *
     * @param inputImage the input image to apply histogram equalization to
     * @return a new image with histogram equalization applied
     */
    private BufferedImage applyHistogramEqualization(final BufferedImage
                                                             inputImage) {
        final int[] map = generateMap(inputImage);

        final BufferedImage outputImage = new BufferedImage(
                inputImage.getWidth(),
                inputImage.getHeight(),
                BufferedImage.TYPE_INT_RGB);

        for (int x = 0; x < inputImage.getWidth(); x++) {
            for (int y = 0; y < inputImage.getHeight(); y++) {

                final int rgb = inputImage.getRGB(x, y);

                float[] hsb = Color.RGBtoHSB(
                        (rgb >> 16) & 0xFF,
                        (rgb >> 8) & 0xFF,
                        (rgb) & 0xFF,
                        null
                );

                final int newBrightness = map[(int) (hsb[2] * 255)];
                hsb[2] = newBrightness / 255.0f;

                outputImage.setRGB(x, y,
                        Color.HSBtoRGB(hsb[0], hsb[1], hsb[2]));
            }
        }

        return outputImage;
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
        final List<CustomImage> images =
                new ArrayList<>(container.getContainer());

        Collections.shuffle(images);

        // TODO Fix synchronization
        for (final CustomImage image : images) {
            synchronized (image) {
                image.setImprovements(
                        applyHistogramEqualization(image.getImage()));
            }
        }
    }

}