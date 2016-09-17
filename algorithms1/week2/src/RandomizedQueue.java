import java.util.Iterator;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] data = (Item[]) new Object[1];
    private int size = 0;

    /**
     * Construct an empty randomized queue
     */
    public RandomizedQueue() {
    }

    /**
     * is the queue empty?
     * 
     * @return
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * return the number of items on the queue
     * 
     * @return
     */
    public int size() {
        return size;
    }

    /**
     * @param size - new size of data array
     */
    private void resize(int newSize) {
        Item[] resizedData = (Item[]) new Object[newSize];
        for (int i = 0; i < data.length; i++) {
            resizedData[i] = data[i];
        }
        data = resizedData;
    }

    /**
     * add the item
     * 
     * @param item
     */
    public void enqueue(Item item) {
        if (item == null) {
            throw new java.lang.NullPointerException();
        }
        if (size == data.length) {
            resize(data.length * 2);
        }
        data[size++] = item;
    }

    /**
     * remove and return a random item
     * 
     * @return
     */
    public Item dequeue() {
        if (size == 0) {
            throw new java.util.NoSuchElementException(
                    "ERROR: attempt to remove first item from an empty queue");
        }
        int i = StdRandom.uniform(0, size);
        Item removedItem = data[i];
        data[i] = data[--size];
        data[size] = null;
        return removedItem;
    }

    /**
     * Return (but do not remove) a random item.
     * 
     * @return random sampled item
     */
    public Item sample() {
        if (size == 0) {
            throw new java.util.NoSuchElementException(
                    "ERROR: attempt to remove first item from an empty queue");
        }
        return data[StdRandom.uniform(0, size)];
    }

    /**
     * ListIterator - iterator implementation.
     * 
     * @author John Sissler
     *
     */
    private class RandomizedIterator implements Iterator<Item> {
        /**
         * ramdomizedData - data copied and randomized.
         */
        private Item[] randomizedData;

        /**
         * cursor - current index into randomized data.
         */
        private int cursor = 0;

        /**
         * Create new RandomizedIterator.
         */
        RandomizedIterator() {
            randomizedData = (Item[]) new Object[size];
            for (int i = 0; i < size; i++) {
                randomizedData[i] = data[i];
            }
            if (size > 0) {
                StdRandom.shuffle(randomizedData, 0, size - 1);
            }
        }

        /**
         * hasNext - any items left to iterate?
         * 
         * @return - if any more items left to iterate.
         */
        public boolean hasNext() {
            return cursor < randomizedData.length;
        }

        /**
         * next - return next Item.
         * 
         * @return - next Item.
         */
        public Item next() {
            if (cursor >= size) {
                throw new java.util.NoSuchElementException(
                        "ERROR: attempt to iterate at end of queue");
            }
            return randomizedData[cursor++];
        }

        /**
         * remove - not implemented.
         */
        public void remove() {
            throw new java.lang.UnsupportedOperationException(
                    "ERROR: remove not supported");
        }
    }

    /**
     * Return an independent iterator over items in random order.
     * 
     * @return - new RandomizedIterator.
     */
    public Iterator<Item> iterator() {
        return new RandomizedIterator();
    }

    public static void main(String[] args) {
        RandomizedQueue<Integer> queue = new RandomizedQueue<Integer>();
        for (int i = 0; i < 10000; i++) {
            queue.enqueue(i);
            StdOut.println("i=" + i + " size=" + queue.size() + " sample="
                    + queue.sample());
        }
        for (Integer i : queue) {
            StdOut.println(i);
        }
        for (int i = 0; i < 3; i++) {
            StdOut.println("deque=" + queue.dequeue());
        }
    }
}
