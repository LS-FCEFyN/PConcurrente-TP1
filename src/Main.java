import utils.ImageContainer;
import utils.ImageFilter;
import utils.ImageLoader;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.*;

public class Main {
    public static void main(String[] args) {

        final ImageContainer cont = new ImageContainer();

        /*------ FIRST PROCESS ------*/

        ConcurrentMap<String, Boolean> imgPaths = new ConcurrentHashMap<>();

        runner(new ImageLoader(cont, "./input", imgPaths),
                new ImageLoader(cont, "./input", imgPaths));

        /*------ Dereference the objects to encourage GC ------*/
        imgPaths = null;
        /*------ Dereference the objects to encourage GC ------*/

        /*------ FIRST PROCESS ------*/

        /*------ SECOND PROCESS ------*/

        runner(new ImageFilter(cont), new ImageFilter(cont),
                new ImageFilter(cont));

        /*------ SECOND PROCESS ------*/

        final JFrame frame2 = new JFrame("Image Display");
        frame2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        final JLabel label2 =
                new JLabel(new ImageIcon(cont.getContainer().get(0).
                getImage().
                getScaledInstance(800, 600, Image.SCALE_SMOOTH)));

        frame2.getContentPane().add(label2, BorderLayout.CENTER);

        // Display the frame
        frame2.pack();
        frame2.setVisible(true);

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
                //System.out.println("Executor terminated normally");
            } else {
                //System.out.println("Executor was interrupted or timed out");
            }
        } catch (InterruptedException e) {
            // handle interruption
        }
    }

}
