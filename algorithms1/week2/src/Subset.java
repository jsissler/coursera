import java.util.Iterator;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

/**
 * Subset - See http://coursera.cs.princeton.edu/algs4/assignments/queues.html
 */

/**
 * @author John Sissler
 *
 */
public class Subset {

    /**
     * @param args - k samples to print out.
     */
    public static void main(String[] args) {
        if (args.length != 1) {
            throw new java.lang.IllegalArgumentException("usage: Subset k");
        }
        int k = Integer.parseInt(args[0]);
        String[] strings = StdIn.readAllStrings();
        RandomizedQueue<String> queue = new RandomizedQueue<String>();
        for (String s : strings) {
            queue.enqueue(s);
        }
        Iterator<String> it = queue.iterator();
        for (int i = 0; i < k; i++) {
            StdOut.println(it.next());
        }
    }
}
