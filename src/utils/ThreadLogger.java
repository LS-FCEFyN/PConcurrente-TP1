package utils;

import ctmtypes.CustomImage;
import ctmtypes.ImageContainer;

import java.io.IOException;
import java.util.Map;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ThreadLogger implements Runnable {

    private final Logger logger;
    private final ImageContainer cont;
    private final ImageContainer processedImages;

    public ThreadLogger(final ImageContainer cont,
                        final ImageContainer processedImages) {

        this.logger = Logger.getLogger(ThreadLogger.class.getName());

        logger.setUseParentHandlers(false);

        try {
            final FileHandler fileHandler = new FileHandler("log.html");
            fileHandler.setFormatter(new Formatter() {
                @Override
                public String format(java.util.logging.LogRecord record) {
                    return record.getMessage() + "\n";
                }
            });
            logger.addHandler(fileHandler);
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
        int numFullyImproved =
                (int) cont.stream().filter(image ->
                        image.getImprovements() % 3 == 0
                        && image.getImprovements() != 0).count();
        /* Amount of images whose resolution has been adjusted */
        int numAdjusted =
                (int) cont.stream().filter(image -> image.isAdjusted()).count();
        /* Amount of images that have been transferred to the final container */
        final int numFinished = processedImages.size();

        final StringBuilder sb = new StringBuilder();
        sb.append("<table>");
        sb.append("<tr><th>Metrics</th><th>Values</th></tr>");
        sb.append("<tr><td>Images Inserted</td><td>").append(numInserted).append("</td></tr>");
        sb.append("<tr><td>Fully improved images</td><td>").append(numFullyImproved).append("</td></tr>");
        sb.append("<tr><td>Adjusted images</td><td>").append(numAdjusted).append("</td></tr>");
        sb.append("<tr><td>Finished images</td><td>").append(numFinished).append("</td></tr>");

        // Get all threads and their states
        final Map<Thread, StackTraceElement[]> threadMap = Thread.getAllStackTraces();
        sb.append("<tr><td colspan=\"2\"><strong>Threads:</strong></td></tr>");
        for (final Thread thread : threadMap.keySet()) {
            if (thread.getThreadGroup().equals(Thread.currentThread().
                    getThreadGroup()) && !thread.getName().
                    equals("pool-1-thread-1") && !thread.getName().
                    equals("Monitor Ctrl-Break") && !thread.getName().
                    equals("main")) {
                sb.append("<tr><td>").append(thread.getName()).
                        append("</td><td>").append(thread.getState()).
                        append("</td></tr>");
            }
        }
        sb.append("</table>");


        logger.log(Level.INFO, sb.toString());
    }

}
