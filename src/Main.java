import ctmtypes.ImageContainer;

import utils.*;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


public class Main {
    public static void main(String[] args) {

        final ImageContainer cont = new ImageContainer();
        final ImageContainer processedImages = new ImageContainer();


        // Create a ThreadLogger to log information every 500ms
        final ThreadLogger logger = new ThreadLogger(cont, processedImages);
        final ScheduledExecutorService loggerExecutor = Executors.newSingleThreadScheduledExecutor();
        loggerExecutor.scheduleAtFixedRate(logger, 0, 500, TimeUnit.MILLISECONDS);

        /*------ FIRST PROCESS ------*/

        runner( new ImageLoader(cont),
                new ImageLoader(cont));

        /*------ FIRST PROCESS ------*/

        /*------ SECOND & THIRD PROCESS ------*/

        runner( new ImageFilter(cont),
                new ImageFilter(cont),
                new ImageFilter(cont));

        runner(
                new ImageResizer(cont),
                new ImageResizer(cont),
                new ImageResizer(cont));

        /*------ SECOND & THIRD PROCESS ------*/

        /*------ FOURTH PROCESS ------*/

        runner( new ImageMover(cont, processedImages),
                new ImageMover(cont, processedImages));

        /*------ FOURTH PROCESS ------*/

        loggerExecutor.shutdown();

    }

    /**
     * Executes a collection of Runnable tasks in parallel.
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
