package utils;

import ctmtypes.CustomImage;
import ctmtypes.ImageContainer;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Stream;

/**
 * The ImageLoader class is responsible for loading image files from the input
 * path and storing them in the provided
 * ImageContainer instance.
 */
public class ImageLoader implements Runnable {

    /**
     * The ImageContainer instance to store the loaded images.
     */
    private final ImageContainer container;

    /**
     * The path to the directory containing image files.
     */
    private final Path inputPath;

    /**
     * A set of paths of already loaded images.
     */
    private final ConcurrentMap<String, Boolean> loadedImagePaths;

    /**
     * Constructs an instance of ImageLoader class.
     *
     * @param container an instance of ImageContainer to store the loaded
     *                  images.
     * @param inputPath a string representing the input path where image files
     *                  are located.
     * @param imgPaths  a set of strings representing the paths of already
     *                  loaded images.
     */
    public ImageLoader(final ImageContainer container, final String inputPath,
                       final ConcurrentMap<String, Boolean> imgPaths) {
        this.container = container;
        this.inputPath = Paths.get(inputPath);
        this.loadedImagePaths = imgPaths;
    }

    /**
     * Loads image files from the input path and stores them in the
     * ImageContainer instance.
     */
    @Override
    public void run() {
        /* Try-with-resources block ensures that the Stream is closed after use,
         * even if an exception is thrown.
         */
        try (Stream<Path> paths = Files.list(inputPath)) {
            paths
                    /* Filter out any files that are not PNG images. */
                    .filter(path -> path.toString().endsWith(".png"))
                    /* Only load images that have not already been loaded and
                     * added to the loadedImagePaths set.
                     */.filter(path -> loadedImagePaths.putIfAbsent(path.
                    toString(), Boolean.TRUE) == null)
                    /* For each image file, create a CustomImage instance and
                     * add it to the ImageContainer.
                     */.forEach(path -> {
                try {
                    /* Add the CustomImage instance to the
                     * ImageContainer.
                     */

                    container.addImage(new
                            CustomImage(ImageIO.read(path.toFile())));

                } catch (IOException e) {
                    /* Handle exception accordingly */
                }
            });
        } catch (IOException e) {
            /* Handle exception accordingly */
        }
    }
}

