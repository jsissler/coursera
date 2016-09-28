import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class FastCollinearPoints {
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
        FastCollinearPoints collinear = new FastCollinearPoints(points);
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
    @SuppressWarnings("unused")
    public FastCollinearPoints(Point[] pointsArg) {
        points = pointsArg;

        if (points == null)
            throw new java.lang.NullPointerException();

        for (Point point : points) {
            if (point == null) {
                throw new java.lang.NullPointerException();
            }
        }

        Arrays.sort(points);
        Point last = null;

        for (Point point : points) {
            if (last != null && point.compareTo(last) == 0) {
                throw new java.lang.IllegalArgumentException();
            }
            last = point;
        }

        Point[] copy = Arrays.copyOf(points, points.length);

        for (int i = 0; i < points.length; i++) {
            Point p = points[i];
            Arrays.sort(copy, p.slopeOrder());
            List<Point> line = new ArrayList<Point>();
            StdOut.println("********** p=" + p);
            for (int j = 0; j < copy.length; j++) {
                Point q = copy[j];
                StdOut.printf("\tq=%s p.slopeOrder(q)=%.10f sz=%d\n", q, p.slopeTo(q), line.size());
                if (p.compareTo(q) != 0) {
                    if (line.isEmpty()) {
                        StdOut.println("EMPTY, ADD");
                        line.add(q);
                        last = q;
                    } else if (p.slopeTo(q) == p.slopeTo(last)) {
                        StdOut.println("MATCH, ADD sz=" + (line.size() + 1));
                        line.add(q);
                        last = q;
                    } else {
                        StdOut.println("NO MATCH NO SEGMENT RESET");
                        line.clear();
                        line.add(q);
                        last = q;
                    }

                    if (line.size() == 3) {
                        StdOut.println("LINE SEGMENT sz=" + (segments.size() + 1));
                        line.add(p);
                        Point[] array = line.toArray(new Point[line.size()]);
                        Arrays.sort(array);
                        LineSegment s = new LineSegment(array[0], array[array.length - 1]);
                        StdOut.println("SEGMENT: " + s);
                        segments.add(s);
                        line.clear();
                    }
                } else {
                    StdOut.println("EQUALS SKIP");
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
