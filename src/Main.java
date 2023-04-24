import ctmtypes.CustomImage;
import ctmtypes.ImageContainer;

import utils.*;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;


public class Main {
    public static void main(String[] args) {

        final ImageContainer cont = new ImageContainer();
        final ImageContainer processedImages = new ImageContainer();

        /*------ Create a ThreadLogger to log information every 500ms ------*/

        final ThreadLogger logger = new ThreadLogger(cont, processedImages);
        final ScheduledExecutorService loggerExecutor =
                Executors.newSingleThreadScheduledExecutor();

        loggerExecutor.scheduleAtFixedRate(logger, 0, 500,
                TimeUnit.MILLISECONDS);

        /*------ Create a ThreadLogger to log information every 500ms ------*/


        /*------ FIRST PROCESS ------*/

        ExecutorService executorService = Executors.newFixedThreadPool(2,
                new NamedThreadFactory("ImageLoader"));

        executorService.execute(new ImageLoader(cont));
        executorService.execute(new ImageLoader(cont));

        executorService.shutdown();

        /*------ FIRST PROCESS ------*/

        /*------ SECOND & THIRD PROCESS ------*/
        ExecutorService imageFilterService = Executors.newFixedThreadPool(3,
                new NamedThreadFactory("ImageFilter"));

        imageFilterService.execute(new ImageFilter(cont));
        imageFilterService.execute(new ImageFilter(cont));
        imageFilterService.execute(new ImageFilter(cont));

        imageFilterService.shutdown();

        ExecutorService imageResizerService = Executors.newFixedThreadPool(3,
                new NamedThreadFactory("ImageResizer"));

        Future<AtomicInteger> adjustedBy1 =
                imageResizerService.submit(new ImageResizer(cont));
        Future<AtomicInteger> adjustedBy2 =
                imageResizerService.submit(new ImageResizer(cont));
        Future<AtomicInteger> adjustedBy3 =
                imageResizerService.submit(new ImageResizer(cont));

        imageResizerService.shutdown();

        ExecutorService imageMoverService = Executors.newFixedThreadPool(2,
                new NamedThreadFactory("ImageMover"));

        Future<AtomicInteger> movedBy1 = imageMoverService.submit(new ImageMover(cont,
                processedImages));
        Future<AtomicInteger> movedBy2 = imageMoverService.submit(new ImageMover(cont,
                processedImages));

        imageMoverService.shutdown();

        try {
            final boolean terminated =
                    executorService.awaitTermination(Integer.MAX_VALUE,
                            TimeUnit.MICROSECONDS);
            if (terminated) {
                System.out.println("ImageLoader threads completed successfully");
                System.out.println("Total images loaded: " + cont.getHistoricSize());
            } else {
                System.out.println("ImageLoader threads interrupted or timed " +
                        "out");
            }
        } catch (InterruptedException e) {
            // handle interruption
        }

        try {
            final boolean terminated = imageFilterService.awaitTermination(Integer.MAX_VALUE,
                    TimeUnit.MILLISECONDS);
            if (terminated) {
                System.out.println("ImageFilter threads completed successfully");
            } else {
                System.out.println("ImageFilter threads interrupted or timed " +
                        "out");
            }
        } catch (InterruptedException e) {
            // handle interruption
        }

        try {
            imageResizerService.awaitTermination(Integer.MAX_VALUE,
                    TimeUnit.MILLISECONDS);
            if (adjustedBy1.get() != null && adjustedBy2.get() != null && adjustedBy3.get() != null) {
                System.out.println("ImageResizer threads completed successfully");
                System.out.println("Images adjusted by thread ImageResizer-1: " + adjustedBy1.get());
                System.out.println("Images adjusted by thread ImageResizer-2: " + adjustedBy2.get());
                System.out.println("Images adjusted by thread ImageResizer-3: " + adjustedBy3.get());
            }
        } catch (InterruptedException e) {
            // handle interruption
        } catch (ExecutionException e) {
            // handle exception thrown by task
        }

        try {
            imageMoverService.awaitTermination(Integer.MAX_VALUE,
                    TimeUnit.MILLISECONDS);
            if (movedBy1.get() != null && movedBy2.get() != null) {
                System.out.println("ImageMover threads completed successfully");
                System.out.println("Images moved by thread ImageMover-1: " + movedBy1.get());
                System.out.println("Images moved by thread ImageMover-2: " + movedBy2.get());
            }
        } catch (InterruptedException e) {
            // handle interruption
        } catch (ExecutionException e) {
            // handle exception thrown by task
        }

        /*------ FOURTH PROCESS ------*/

        try {
            if (loggerExecutor.awaitTermination(500, TimeUnit.MILLISECONDS)) {
                System.out.println("ThreadLogger thread completed successfully");
            } else {
                loggerExecutor.shutdownNow();
            }
        } catch (InterruptedException e) {
            loggerExecutor.shutdownNow();
        }

        System.out.println("Main thread completed");

        System.out.println(processedImages.size());

    }
}