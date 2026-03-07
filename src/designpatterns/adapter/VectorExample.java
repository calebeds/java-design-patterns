package designpatterns.adapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class Point {
    private int x, y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }


    @Override
    public String toString() {
        return "Point{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}

class Line {
    private Point start, end;

    public Line(Point start, Point end) {
        this.start = start;
        this.end = end;
    }

    public Point getStart() {
        return start;
    }

    public void setStart(Point start) {
        this.start = start;
    }

    public Point getEnd() {
        return end;
    }

    public void setEnd(Point end) {
        this.end = end;
    }

    @Override
    public String toString() {
        return "Line{" +
                "start=" + start +
                ", end=" + end +
                '}';
    }
}

class VectorObject extends ArrayList<Line> {}

class VectorRectangle extends VectorObject {
    public VectorRectangle(int x, int y, int width, int height) {
        add(new Line(new Point(x, y), new Point(x + width, y)));
        add(new Line(new Point(x + width, y), new Point(x + width, y + height)));
        add(new Line(new Point(x, y), new Point(x, y + height)));
        add(new Line(new Point(x, y + height), new Point(x + width, y + height)));
    }
}

class LineToPointAdapter extends ArrayList<Point> {
    private static int count = 0;

    public LineToPointAdapter(Line line) {
        System.out.printf("%d: Generating point for line [%d,%d]-[%d,%d] (no caching)%n", ++count, line.getStart().getX(), line.getStart().getY(), line.getEnd().getX(), line.getEnd().getY());

        int left = Math.min(line.getStart().getX(), line.getEnd().getX());
        int right = Math.max(line.getStart().getX(), line.getEnd().getX());
        int top = Math.min(line.getStart().getY(), line.getEnd().getY());
        int bottom = Math.max(line.getStart().getY(), line.getEnd().getY());
        int dx = right - left;
        int dy = line.getEnd().getY() - line.getStart().getY();

        if(dx == 0) {
            for(int y = top; y <= bottom; ++y) {
                add(new Point(left, y));
            }
        } else if(dy == 0) {
            for(int x = left; x <= right; ++x) {
                add(new Point(x, top));
            }
        }
    }

}

public class VectorExample {
    private final static List<VectorObject> vectorObjects = new ArrayList<>(Arrays.asList(
            new VectorRectangle(1, 1, 10, 10),
            new VectorRectangle(3, 3, 6, 6)
    ));

    public static void drawPoint(Point p) {
        System.out.println(".");
    }

    private static void draw() {
        for(VectorObject vectorObject: vectorObjects) {
            for(Line line: vectorObject) {
                LineToPointAdapter adapter = new LineToPointAdapter(line);
                adapter.forEach(VectorExample::drawPoint);
            }
        }
    }

    public static void main(String[] args) {
        draw();
    }
}
