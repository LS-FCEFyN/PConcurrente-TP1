package utils;

import java.util.ArrayList;
import ctmtypes.CustomImage;
import java.util.List;

/**
 * This class represents a container for CustomImage objects
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
        container = new ArrayList<>();
    }

    /**
     * Returns the list of CustomImage objects stored in the container.
     *
     * @return the list of CustomImage objects.
     */
    public List<CustomImage> getContainer() {
        return container;
    }

}

