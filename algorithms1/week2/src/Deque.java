import java.util.Iterator;

import edu.princeton.cs.algs4.StdOut;

/**
 * Deque: implement Deque using linked list.
 * 
 * @author John Sissler
 *
 * @param <Item>
 */
public class Deque<Item> implements Iterable<Item> {
    /**
     * first: reference to head of Deque list.
     */
    private Node first;
    /**
     * last: reference to tail of Deque list.
     */
    private Node last;
    /**
     * size: current size of Deque 0-n.
     */
    private int size;

    /**
     * Node: doubly-linked list node.
     * 
     * @author John Sissler
     *
     */
    private class Node {
        /**
         * item - list data item.
         */
        private Item item;

        /**
         * prev - link to node list successor.
         */
        private Node prev;

        /**
         * next - link to node list predecessor.
         */
        private Node next;

        /**
         * Node constructor.
         * 
         * @param item - node data.
         * @param prev - reference to list predecessor.
         * @param next - reference to list successor.
         */
        Node(Item item, Node prev, Node next) {
            this.item = item;
            this.prev = prev;
            this.next = next;

            if (prev != null) {
                prev.next = this;
            }

            if (next != null) {
                next.prev = this;
            }
        }
    }

    /**
     * Construct an empty deque.
     */
    public Deque() {
        first = null;
        last = null;
        size = 0;
    }

    /**
     * @return is the deque empty?
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * @return the number of items on the deque.
     */
    public int size() {
        return size;
    }

    /**
     * add the item to the front.
     * 
     * @param item
     */
    public void addFirst(Item item) {
        if (item == null) {
            throw new java.lang.NullPointerException(
                    "ERROR: attempt to add null item");
        }

        first = new Node(item, null, first);

        if (size++ == 0) {
            last = first;
        }
    }

    /**
     * Add the item to the end.
     * 
     * @param item - node data
     */
    public void addLast(Item item) {
        if (item == null) {
            throw new java.lang.NullPointerException(
                    "ERROR: attempt to add null item");
        }

        last = new Node(item, last, null);

        if (size++ == 0) {
            first = last;
        }
    }

    /**
     * remove and return the item from the front.
     * 
     * @return the item from the front
     */
    public Item removeFirst() {
        if (size == 0) {
            throw new java.util.NoSuchElementException(
                    "ERROR: attempt to remove first item from an empty deque");
        }

        Item item = first.item;

        if (size-- == 1) {
            first = null;
            last = null;
        } else {
            first.next.prev = null;
            first = first.next;
        }

        return item;
    }

    /**
     * remove and return the item from the end.
     * 
     * @return the item from the end.
     */
    public Item removeLast() {
        if (size == 0) {
            throw new java.util.NoSuchElementException(
                    "ERROR: attempt to remove first item from an empty deque");
        }

        Item item = last.item;

        if (size-- == 1) {
            last = null;
            first = null;
        } else {
            last.prev.next = null;
            last = last.prev;
        }

        return item;
    }

    public Iterator<Item> iterator() {
        return new ListIterator();
    }

    /**
     * ListIterator - iterator implementation.
     * 
     * @author John Sissler
     *
     */
    private class ListIterator implements Iterator<Item> {
        /**
         * current - pointer to current item.
         */
        private Node current = first;

        /**
         * hasNext - any items left to iterate?
         * 
         * @return - if any more items left to iterate.
         */
        public boolean hasNext() {
            return current != null;
        }

        /**
         * next - return next Item.
         * 
         * @return - next Item.
         */
        public Item next() {
            if (current == null) {
                throw new java.util.NoSuchElementException(
                        "ERROR: attempt to iterate at end of deque");
            }

            Item item = current.item;
            current = current.next;
            return item;
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
     * @param args
     */
    public static void main(String[] args) {
        Deque<Integer> deque = new Deque<Integer>();
        deque.addLast(2);
        for (Integer i : deque)
            StdOut.println(i);
        deque.addFirst(1);
        for (Integer i : deque)
            StdOut.println(i);
    }

}
