package utils;

import ctmtypes.CustomImage;
import ctmtypes.ImageContainer;

import java.io.IOException;
import java.util.Map;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;


public class ThreadLogger implements Runnable {

    private final Logger logger;
    private final ImageContainer cont;
    private final ImageContainer processedImages;

    public ThreadLogger(final ImageContainer cont,
                        final ImageContainer processedImages) {

        this.logger = Logger.getLogger(ThreadLogger.class.getName());

        try {
            final FileHandler fileHandler = new FileHandler("log.txt");
            logger.addHandler(fileHandler);
            final SimpleFormatter formatter = new SimpleFormatter();
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error creating log file", e);
        }

        this.cont = cont;
        this.processedImages = processedImages;
    }

    @Override
    public void run() {
        final int numInserted = cont.size();
        /* Amount of images whose brightness has been adjusted trice */
        int numFullyImproved = 0;
        /* Amount of images whose resolution has been adjusted */
        int numAdjusted = 0;
        /* Amount of images that have been transferred to the final container */
        final int numFinished = processedImages.size();

        for (final CustomImage image : cont) {
            numFullyImproved += image.getImprovements() % 3 == 0 && image.getImprovements() != 0
                    ? 1 : 0;
            numAdjusted += image.isAdjusted() ? 1 : 0;
        }

        final StringBuilder sb = new StringBuilder();
        sb.append("Images inserted: ").append(numInserted).append("\n");
        sb.append("Fully improved images: ").append(numFullyImproved).append("\n");
        sb.append("Adjusted images: ").append(numAdjusted).append("\n");
        sb.append("Finished images: ").append(numFinished).append("\n");


        // Get all threads and their states
        final Map<Thread, StackTraceElement[]> threadMap =
                Thread.getAllStackTraces();
        for (final Thread thread : threadMap.keySet()) {
            sb.append(thread.getName()).append(": ").append(thread.getState()).
                    append("\n");
        }

        logger.log(Level.INFO, sb.toString());

    }
}
