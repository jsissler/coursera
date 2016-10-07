import java.util.ArrayList;
import java.util.List;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

public class KdTree {
    private class Node {
        private Point2D point;
        private Node left, right;

        private Node(Point2D point) {
            this.point = point;
            left = null;
            right = null;
        }
    }

    private Node root = null;
    private int size = 0;

    // construct an empty set of points
    public KdTree() {
    }

    // is the set empty?
    public boolean isEmpty() {
        return root == null;
    }

    // number of points in the set
    public int size() {
        return size;
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        if (p == null)
            throw new java.lang.NullPointerException();
        root = insert(root, p, true);
    }

    private Node insert(Node node, Point2D p, boolean useX) {
        if (node == null) {
            node = new Node(p);
            size++;
        } else if (useX) {
            if (p.x() < node.point.x())
                node.left = insert(node.left, p, !useX);
            else if (!p.equals(node.point))
                node.right = insert(node.right, p, !useX);
        } else {
            if (p.y() < node.point.y())
                node.left = insert(node.left, p, !useX);
            else if (!p.equals(node.point))
                node.right = insert(node.right, p, !useX);
        }
        return node;
    }

    // does the set contain point p?
    public boolean contains(Point2D p) {
        if (p == null)
            throw new java.lang.NullPointerException();
        return contains(p, root, true);
    }

    private boolean contains(Point2D p, Node node, boolean useX) {
        if (node == null)
            return false;
        if (useX) {
            if (p.x() < node.point.x())
                return contains(p, node.left, !useX);
            else if (!p.equals(node.point))
                return contains(p, node.right, !useX);
            else
                return true;
        } else {
            if (p.y() < node.point.y())
                return contains(p, node.left, !useX);
            else if (!p.equals(node.point))
                return contains(p, node.right, !useX);
            else
                return true;
        }
    }

    // draw all points to standard draw
    public void draw() {
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 1);
        StdDraw.setYscale(0, 1);
        draw(root, true, new RectHV(0, 0, 1, 1));
        StdDraw.show();
    }

    private void draw(Node node, boolean useX, RectHV container) {
        if (node != null) {
            StdDraw.setPenRadius(0.01);
            StdDraw.setPenColor(StdDraw.BLACK);
            node.point.draw();
            StdDraw.setPenRadius();
            StdDraw.setPenColor(useX ? StdDraw.RED : StdDraw.BLUE);
            if (useX) {
                StdDraw.line(node.point.x(), container.ymin(), node.point.x(), container.ymax());
                draw(node.left, !useX, new RectHV(container.xmin(), container.ymin(), node.point.x(), container.ymax()));
                draw(node.right, !useX,
                        new RectHV(node.point.x(), container.ymin(), container.xmax(), container.ymax()));
            } else {
                StdDraw.line(container.xmin(), node.point.y(), container.xmax(), node.point.y());
                draw(node.left, !useX, new RectHV(container.xmin(), container.ymin(), container.xmax(), node.point.y()));
                draw(node.right, !useX,
                        new RectHV(container.xmin(), node.point.y(), container.xmax(), container.ymax()));
            }
        }
    }

    // all points that are inside the rectangle
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null)
            throw new NullPointerException();
        List<Point2D> inside = new ArrayList<Point2D>();
        range(rect, root, true, inside);
        return inside;
    }

    private void range(RectHV rect, Node node, boolean useX, List<Point2D> contains) {
        if (node != null) {
            if (rect.contains(node.point))
                contains.add(node.point);
            if (useX) {
                if (rect.xmin() < node.point.x())
                    range(rect, node.left, !useX, contains);
                if (rect.xmax() > node.point.x())
                    range(rect, node.right, !useX, contains);
            } else {
                if (rect.ymin() < node.point.y())
                    range(rect, node.left, !useX, contains);
                if (rect.ymax() > node.point.y())
                    range(rect, node.right, !useX, contains);
            }
        }
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        if (p == null)
            throw new java.lang.NullPointerException();
        if (root == null)
            return null;
        return nearest(p, root, true, root.point);
    }

    private Point2D nearest(Point2D p, Node node, boolean useX, Point2D nearest) {
        if (node == null)
            return nearest;
        if (p.distanceSquaredTo(node.point) < p.distanceSquaredTo(nearest))
            nearest = node.point;
        Point2D best = null;
        if (useX) {
            if (p.x() < node.point.x())
                best = nearest(p, node.left, !useX, nearest);
            if (p.x() >= node.point.x()
                    || (node.right != null && best != null && p.distanceSquaredTo(node.right.point) < p
                            .distanceSquaredTo(best)))
                best = nearest(p, node.right, !useX, nearest);
            return best;
        } else {
            if (p.y() < node.point.y())
                best = nearest(p, node.left, !useX, nearest);
            if (p.y() >= node.point.y()
                    || (node.right != null && best != null && p.distanceSquaredTo(node.right.point) < p
                            .distanceSquaredTo(best)))
                best = nearest(p, node.right, !useX, nearest);
        }
        return best;
    }
    // unit testing of the methods (optional)
    public static void main(String[] args) {
        KdTree points = new KdTree();
        In in = new In(args[0]);
        double[] coordinates = in.readAllDoubles();
        assert ((coordinates.length % 2) == 0);
        for (int i = 0; i < coordinates.length;) {
            Point2D point = new Point2D(coordinates[i++], coordinates[i++]);
            points.insert(point);
        }
        points.draw();
        Point2D p = new Point2D(0.81, 0.30);
        StdDraw.setPenRadius(0.02);
        StdDraw.setPenColor(StdDraw.YELLOW);
        p.draw();
        points.nearest(p).draw();
        StdDraw.show();
        // KdTree points = new KdTree();
        // points.insert(new Point2D(0.7, 0.2));
        // points.insert(new Point2D(0.5, 0.4));
        // points.insert(new Point2D(0.2, 0.3));
        // points.insert(new Point2D(0.4, 0.7));
        // points.insert(new Point2D(0.2, 0.3));
        // points.insert(new Point2D(0.9, 0.6));
        // StdOut.printf("size 5? %d contains true? %s\n", points.size(),
        // points.contains(new Point2D(0.2, 0.3)));
        // points.draw();
    }

}
