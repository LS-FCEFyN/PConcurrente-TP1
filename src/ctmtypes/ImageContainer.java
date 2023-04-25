package ctmtypes;

import java.util.ArrayList;
import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * This class represents a thread-safe container for CustomImage objects
 * and provides methods for accessing and manipulating them.
 */
public class ImageContainer extends ArrayList<CustomImage> {

    /**
     * The historic size of this ImageContainer.
     */
    private Integer historicSize;

    /**
     * Constructs an empty ImageContainer with an initial historic size of zero.
     */
    public ImageContainer() {
        super();
        this.historicSize = 0;
    }

    /**
     * Adds a CustomImage object to the container.
     *
     * @param image the CustomImage object to add.
     */
    @Override
    public synchronized boolean add(final CustomImage image) {
        if (historicSize < 100) {
            historicSize++;
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

    @Override
    public synchronized void forEach(final Consumer<? super CustomImage> action) {
        super.forEach(action);
    }

    public synchronized int getHistoricSize() {
        return historicSize;
    }


}
