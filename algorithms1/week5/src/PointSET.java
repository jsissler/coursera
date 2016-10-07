import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

public class PointSET {
    private Set<Point2D> points = new TreeSet<Point2D>();

    // construct an empty set of points
    public PointSET() {
    }

    // is the set empty?
    public boolean isEmpty() {
        return points.isEmpty();
    }

    // number of points in the set
    public int size() {
        return points.size();
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        if (p == null)
            throw new NullPointerException();
        points.add(p);
    }

    // does the set contain point p?
    public boolean contains(Point2D p) {
        if (p == null)
            throw new NullPointerException();
        return points.contains(p);
    }

    // draw all points to standard draw
    public void draw() {
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 1);
        StdDraw.setYscale(0, 1);
        for (Point2D point : points)
            point.draw();
        StdDraw.show();
    }

    // all points that are inside the rectangle
    public Iterable<Point2D> range(RectHV rect) {
        List<Point2D> inside = new ArrayList<Point2D>();
        for (Point2D point : points) {
            if (rect.contains(point))
                inside.add(point);
        }
        return inside;
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        if (p == null)
            throw new NullPointerException();
        Point2D nearest = null;
        for (Point2D point : points) {
            if (nearest == null || point.distanceSquaredTo(p) < nearest.distanceSquaredTo(p))
                nearest = point;
        }
        return nearest;
    }
    
    // unit testing of the methods (optional)
    public static void main(String[] args) {
        PointSET points = new PointSET();
        In in = new In(args[0]);
        double[] coordinates = in.readAllDoubles();
        assert ((coordinates.length % 2) == 0);
        for (int i = 0; i < coordinates.length;) {
            Point2D point = new Point2D(coordinates[i++], coordinates[i++]);
            points.insert(point);
        }
        // points.draw();
        RectHV rect = new RectHV(0.125, 0.25, 0.3, 0.6);
        rect.draw();
        for (Point2D point : points.range(rect))
            point.draw();
    }
}
