package ctmtypes;

import java.util.ArrayList;
import java.util.function.Predicate;

/**
 * This class represents a thread-safe container for CustomImage objects
 * and provides methods for accessing and manipulating them.
 */
public class ImageContainer extends ArrayList<CustomImage>{

    /**
     * Adds a CustomImage object to the container.
     *
     * @param image the CustomImage object to add.
     */
    @Override
    public synchronized boolean add(final CustomImage image) {
        return size() < 100 && super.add(image);
    }


    @Override
    public synchronized boolean remove(final Object image) {
        return !isEmpty() && super.remove(image);
    }

    @Override
    public synchronized boolean removeIf(final Predicate<? super CustomImage> filter) {
        return super.removeIf(filter);
    }

}
