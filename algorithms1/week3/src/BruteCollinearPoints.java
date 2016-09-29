import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class BruteCollinearPoints {
    private class ComparableLineSegment implements Comparable<ComparableLineSegment> {
        private final Point p;
        private final Point q;

        public ComparableLineSegment(Point p, Point q) {
            if (p == null || q == null) {
                throw new NullPointerException();
            }
            this.p = p;
            this.q = q;
        }

        public int compareTo(ComparableLineSegment that) {
            double slopeDifference = p.slopeTo(q) - that.p.slopeTo(that.q);
            if (slopeDifference < 0)
                return -1;
            else if (slopeDifference > 0)
                return 1;
            else {
                if (q != that.q)
                    return q.compareTo(that.q);
                if (p != that.p)
                    return p.compareTo(that.p);
                return 0;
            }
        }

        public LineSegment asLineSegment() {
            return new LineSegment(p, q);
        }

        public String toString() {
            return asLineSegment().toString() + " (" + p.slopeTo(q) + ")";
        }

        public boolean isSubSegment(ComparableLineSegment s) {
            return p.slopeTo(q) == s.p.slopeTo(s.q) && q == s.q && p.compareTo(s.p) > 0;
        }
    }

    private final Point[] points;
    private final LineSegment[] segments;

    /**
     * Finds all line segments containing 4 points.
     * 
     * @param points
     */
    public BruteCollinearPoints(Point[] pointsArg) {
        if (pointsArg == null)
            throw new java.lang.NullPointerException();

        points = Arrays.copyOf(pointsArg, pointsArg.length);

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

        List<ComparableLineSegment> comparableSegments = new ArrayList<ComparableLineSegment>();

        for (int i = 0; i <= points.length - 4; i++) {
            for (int j = i + 1; j < points.length; j++) {
                for (int k = j + 1; k < points.length; k++) {
                    for (int l = k + 1; l < points.length; l++) {
                        final Point p = points[i];
                        final Point q = points[j];
                        final Point r = points[k];
                        final Point s = points[l];

                        if (p.slopeTo(q) == q.slopeTo(r) && q.slopeTo(r) == r.slopeTo(s)) {
                            ComparableLineSegment segment = new ComparableLineSegment(p, s);
                            int index = Collections.binarySearch(comparableSegments, segment);
                            if (index < 0)
                                comparableSegments.add(-index - 1, segment);
                        }
                    }
                }
            }
        }

        List<LineSegment> segmentList = new ArrayList<LineSegment>();
        ComparableLineSegment lastSegment = null;

        for (ComparableLineSegment segment : comparableSegments) {
            if (lastSegment == null || !segment.isSubSegment(lastSegment))
                segmentList.add(segment.asLineSegment());
            lastSegment = segment;
        }

        segments = segmentList.toArray(new LineSegment[segmentList.size()]);
    }

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
     * @return number of segments.
     */
    public int numberOfSegments() {
        return segments.length;
    }

    /**
     * @return the line segments
     */
    public LineSegment[] segments() {
        return Arrays.copyOf(segments, segments.length);
    }
}
