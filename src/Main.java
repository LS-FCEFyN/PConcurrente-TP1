import ctmtypes.CustomImage;
import ctmtypes.ImageContainer;
import utils.ImageFilter;
import utils.ImageLoader;
import utils.ImageMover;
import utils.ImageResizer;

import java.util.concurrent.*;

public class Main {
    public static void main(String[] args) {

        final ImageContainer cont = new ImageContainer();
        final ImageContainer processedImages = new ImageContainer();

        /*------ FIRST PROCESS ------*/

        runner(new ImageLoader(cont),
                new ImageLoader(cont));

        System.out.println("Loaded " + cont.getSize() + " images into the " +
                "container");

        /*------ FIRST PROCESS ------*/

        runner(new ImageFilter(cont), new ImageFilter(cont),
                new ImageFilter(cont), new ImageResizer(cont),
                new ImageResizer(cont), new ImageResizer(cont),
                new ImageMover(cont, processedImages), new ImageMover(cont, processedImages));

        int tmp = 0;

        for(CustomImage image : processedImages.getContainer()) {
            tmp += image.getImprovements();
        }

        System.out.println("Total improvements: " + tmp);

        tmp = 0;

        for(CustomImage image : processedImages.getContainer()) {
            tmp += image.isAdjusted() ? 1 : 0;
        }

        System.out.println("Resized " + tmp + " images");


        System.out.println("Original container size after processes two, " +
                "three and  four: " + cont.getSize());

        System.out.println("Final container size: " + processedImages.getSize());

    }

    /**
     * Executes a collection of Runnable tasks in parallel using an
     * ExecutorService with a fixed thread pool.
     * This method takes a variable number of objects as arguments,
     * each of which should be an instance of Runnable.
     * Each Runnable is executed in its own thread, with the number of threads
     * equal to the number of arguments passed to the method.
     * The method blocks until all tasks have completed execution.
     *
     * @param args variable number of objects that implement the Runnable
     *             interface
     * @throws RuntimeException if an error occurs while executing a task
     */
    public static void runner(final Runnable... args) {
        final ExecutorService executor =
                Executors.newFixedThreadPool(args.length);

        for (final Runnable arg : args) {
                executor.execute(arg);
        }

        executor.shutdown();

        try {
            final boolean terminated =
                    executor.awaitTermination(Integer.MAX_VALUE,
                            TimeUnit.MICROSECONDS);
            if (terminated) {
                System.out.println("Executor terminated normally");
            } else {
                System.out.println("Executor was interrupted or timed out");
            }
        } catch (InterruptedException e) {
            // handle interruption
        }
    }

}
