import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class BruteCollinearPoints {
    final Point[] points;
    List<LineSegment> segments = new ArrayList<LineSegment>();

    public static void main(String[] args) {
        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }

    /**
     * Finds all line segments containing 4 points.
     * 
     * @param points
     */
    public BruteCollinearPoints(Point[] points) {
        if (points == null)
            throw new java.lang.NullPointerException();

        for (Point point : points) {
            if (point == null) {
                throw new java.lang.NullPointerException();
            }
        }

        this.points = points;
        Arrays.sort(this.points);
        Point last = null;

        for (Point point : this.points) {
            if (last != null && point.compareTo(last) == 0) {
                throw new java.lang.IllegalArgumentException();
            }
            last = point;
        }

        for (int i = 0; i <= points.length - 4; i++) {
            for (int j = i + 1; j < points.length; j++) {
                for (int k = j + 1; k < points.length; k++) {
                    for (int l = k + 1; l < points.length; l++) {
                        final Point p = points[i];
                        final Point q = points[j];
                        final Point r = points[k];
                        final Point s = points[l];

                        if (p.slopeTo(q) == q.slopeTo(r)
                                && q.slopeTo(r) == r.slopeTo(s)) {
                            StdOut.printf("FINAL ADD p=%s q=%s p.slopeTo(q)=%.10f\n", p, q, p.slopeTo(q));
                            segments.add(new LineSegment(p, s));
                        }
                    }
                }
            }
        }
    }

    /**
     * @return number of segments.
     */
    public int numberOfSegments() {
        return segments.size();
    }

    /**
     * @return the line segments
     */
    public LineSegment[] segments() {
        return segments.toArray(new LineSegment[segments.size()]);
    }
}
