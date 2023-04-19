package ctmtypes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * This class represents a thread-safe container for CustomImage objects
 * and provides methods for accessing and manipulating them.
 */
public class ImageContainer {
    /**
     * The list of CustomImage objects stored in the container.
     */
    private final List<CustomImage> container;

    /**
     * Constructs a new empty ImageContainer.
     */
    public ImageContainer() {
        container = Collections.synchronizedList(new ArrayList<>());
    }

    /**
     * Returns the list of CustomImage objects stored in the container.
     *
     * @return the list of CustomImage objects.
     */
    public synchronized List<CustomImage> getContainer() {
        return container;
    }

    /**
     * Returns the amount of objects stored in the container.
     *
     * @return the amount of CustomImage objects.
     */
    public synchronized int getSize() {
        return container.size();
    }

    /**
     * Adds a CustomImage object to the container.
     *
     * @param image the CustomImage object to add.
     */
    public synchronized void addImage(final CustomImage image) {
        container.add(image);
    }

    /**
     * Removes a CustomImage object from the container.
     *
     * @param image the CustomImage object to remove.
     * @return true if the object was removed, false otherwise.
     */
    public synchronized boolean removeImage(final CustomImage image) {
        return container.remove(image);
    }
}
