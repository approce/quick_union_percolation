import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Random;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private static final int INIT_ARRAY_SIZE = 16;

    private Item[] array;
    private int actualSize;
    private int arraySize = INIT_ARRAY_SIZE;

    public RandomizedQueue() {
        array = createArrayOfItems(arraySize);
    }

    public boolean isEmpty() {
        return actualSize == 0;
    }

    public int size() {
        return actualSize;
    }

    public void enqueue(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }

        if (actualSize == arraySize) {
            arraySize *= 2;
            resizeArray();
        }
        if (actualSize > INIT_ARRAY_SIZE && arraySize / actualSize > 4) {
            arraySize /= 2;
            resizeArray();
        }

        array[actualSize++] = item;
    }

    public Item dequeue() {
        validateQueueNotEmpty();

        int randomIndex = getRandomIndex();

        Item item = array[randomIndex];

        array[randomIndex] = array[--actualSize];

        return item;
    }

    public Item sample() {
        validateQueueNotEmpty();
        int randomIndex = getRandomIndex();

        return array[randomIndex];
    }

    private void resizeArray() {
        Item[] newArray = createArrayOfItems(arraySize);

        System.arraycopy(array, 0, newArray, 0, array.length);

        array = newArray;
    }

    private Item[] createArrayOfItems(int size) {
        return (Item[]) new Object[size];
    }

    private int getRandomIndex() {
        return new Random().nextInt(actualSize);
    }

    private void validateQueueNotEmpty() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
    }

    public Iterator<Item> iterator() {
        Item[] iteratorArray = createArrayOfItems(actualSize);
        System.arraycopy(array, 0, iteratorArray, 0, actualSize);

        return new ItemIterator(iteratorArray);
    }

    private class ItemIterator implements Iterator<Item> {

        private final Item[] iteratorArray;
        private int index;

        public ItemIterator(Item[] iteratorArray) {
            this.iteratorArray = iteratorArray;
        }

        @Override
        public boolean hasNext() {
            return iteratorArray.length - index > 1;
        }

        @Override
        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            return iteratorArray[index++];
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    public static void main(String[] args) {
        RandomizedQueue<Integer> integers = new RandomizedQueue<>();

        for (int i = 0; i < 65; i++) {
            integers.enqueue(i);

        }

        integers.iterator();

        System.out.println(integers.dequeue());
    }

}
