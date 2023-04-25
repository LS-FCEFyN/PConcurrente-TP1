package utils;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * NamedThreadFactory is a thread factory that creates new threads with a given name prefix.
 * Each new thread name will be suffixed with a unique integer count.
 */
public class NamedThreadFactory implements ThreadFactory {

    /**
     * The prefix to use for the name of new threads.
     */
    private final String namePrefix;

    /**
     * The integer count used to suffix the name of new threads.
     */
    private final AtomicInteger count = new AtomicInteger(1);

    /**
     * Constructs a new NamedThreadFactory with the given name prefix.
     *
     * @param namePrefix the prefix to use for the name of new threads.
     */
    public NamedThreadFactory(final String namePrefix) {
        this.namePrefix = namePrefix;
    }

    /**
     * Creates a new thread with the given Runnable and a unique name based on the name prefix
     * and an incrementing integer count.
     *
     * @param r the Runnable to be executed by the new thread.
     * @return the new thread with a unique name.
     */
    @Override
    public Thread newThread(final Runnable r) {
        return new Thread(r, namePrefix + "-" + count.getAndIncrement());
    }
}