package ctmtypes;

import java.util.Iterator;
import java.util.function.Consumer;

public class ImageContainerIterator implements Iterator<CustomImage> {


    private final ImageContainer container;
    private int index;

    public ImageContainerIterator(ImageContainer container) {
        this.container = container;
        this.index = 0;
    }

    @Override
    public synchronized boolean hasNext() {
        return index < container.size();
    }

    @Override
    public synchronized CustomImage next() {
        if (index >= container.size()) {
            throw new IndexOutOfBoundsException("No more elements in container.");
        }
        CustomImage image = container.get(index);
        index++;
        return image;
    }

    @Override
    public void remove() {
        if (index == 0) {
            throw new IllegalStateException("Next has not been called yet.");
        }
        container.remove(index - 1);
        index--;
    }

    @Override
    public void forEachRemaining(Consumer<? super CustomImage> consumer) {
        Iterator.super.forEachRemaining(consumer);
    }
}
