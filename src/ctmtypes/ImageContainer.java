package ctmtypes;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;

/**
 * This class represents a thread-safe container for CustomImage objects
 * and provides methods for accessing and manipulating them.
 */
public class ImageContainer extends ArrayList<CustomImage>{

    private AtomicInteger historicSize;

    public ImageContainer() {
        super();
        this.historicSize = new AtomicInteger();
    }

    /**
     * Adds a CustomImage object to the container.
     *
     * @param image the CustomImage object to add.
     */
    @Override
    public synchronized boolean add(final CustomImage image) {
        if(historicSize.get() < 100) {
            historicSize.getAndIncrement();
            return super.add(image);
        }
        return false;
    }


    @Override
    public synchronized boolean remove(final Object image) {
        return super.remove(image);
    }

    @Override
    public synchronized boolean removeIf(final Predicate<? super CustomImage> filter) {
        return super.removeIf(filter);
    }

    public synchronized int getHistoricSize(){
        return historicSize.get();
    }


}
