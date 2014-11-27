package main;

import util.Line;
import util.Point2D;
import util.Point3D;
import util.RotationUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Model {

    public static Map<Line<Point3D>, Boolean> determineVisibility(List<Line<Point3D>> cubeLines) {

        List<Rectangle> rectangles = new ArrayList<Rectangle>();

        Line<Point3D> l1 = cubeLines.get(0);
        Line<Point3D> l2 = cubeLines.get(1);
        Line<Point3D> l3 = cubeLines.get(2);
        Line<Point3D> l4 = cubeLines.get(3);
        Line<Point3D> l5 = cubeLines.get(4);
        Line<Point3D> l6 = cubeLines.get(5);
        Line<Point3D> l7 = cubeLines.get(6);
        Line<Point3D> l8 = cubeLines.get(7);
        Line<Point3D> l9 = cubeLines.get(8);
        Line<Point3D> l10 = cubeLines.get(9);
        Line<Point3D> l11 = cubeLines.get(10);
        Line<Point3D> l12 = cubeLines.get(11);

        rectangles.add(new Rectangle(l1, l2, l3, l4));
        rectangles.add(new Rectangle(l5, l6, l7, l8));
        rectangles.add(new Rectangle(l1, l10, l5, l9));
        rectangles.add(new Rectangle(l2, l11, l6, l10));
        rectangles.add(new Rectangle(l3, l12, l7, l11));
        rectangles.add(new Rectangle(l4, l9, l8, l12));

        Map<Line<Point3D>, Boolean> visibleLines = new HashMap<Line<Point3D>, Boolean>();
        Map<Line<Point3D>, Line<Point2D>> linesProjection = new HashMap<Line<Point3D>, Line<Point2D>>();
        Map<Line<Point2D>, Double> linesWithZ = new HashMap<Line<Point2D>, Double>();

        double maxZ = -Double.MAX_VALUE;
        for (Line<Point3D> l : cubeLines) {
            double startZ = l.getStart().getZ();
            double endZ = l.getEnd().getZ();
            double z = (startZ >= endZ) ? startZ : endZ;

            Point2D start = RotationUtil.orthogonalProjection(l.getStart());
            Point2D end = RotationUtil.orthogonalProjection(l.getEnd());
            Line<Point2D> projectionLine = new Line<Point2D>(start, end);

            linesProjection.put(l, projectionLine);
            linesWithZ.put(projectionLine, z);
            if (z >= maxZ) {
                maxZ = z;
            }
        }

        for (Line<Point3D> l : cubeLines) {
            if (linesWithZ.get(linesProjection.get(l)) == maxZ) {
                visibleLines.put(l, true);
                for (Rectangle r : rectangles) {
                    if (r.contains(l)) {
                        visibleLines.put(r.getLine0(), true);
                        visibleLines.put(r.getLine1(), true);
                        visibleLines.put(r.getLine2(), true);
                        visibleLines.put(r.getLine3(), true);
                    }
                }
            }
        }
        return visibleLines;
    }

    private static class Rectangle {

        private Line<Point3D> line0;
        private Line<Point3D> line1;
        private Line<Point3D> line2;
        private Line<Point3D> line3;

        private Rectangle(Line<Point3D> line0, Line<Point3D> line1, Line<Point3D> line2, Line<Point3D> line3) {
            this.line0 = line0;
            this.line1 = line1;
            this.line2 = line2;
            this.line3 = line3;
        }

        public boolean contains(Line<Point3D> line) {
            return line0.equals(line) || line1.equals(line) || line2.equals(line) || line3.equals(line);
        }

        public Line<Point3D> getLine0() {
            return line0;
        }

        public Line<Point3D> getLine1() {
            return line1;
        }

        public Line<Point3D> getLine2() {
            return line2;
        }


        public Line<Point3D> getLine3() {
            return line3;
        }
    }
}