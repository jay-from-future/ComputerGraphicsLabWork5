package main;

import util.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Model {

    public static void determineVisibility(List<Line<Point3D>> cubeLines,
                                           List<util.Rectangle> rectangles) {
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
                for (Rectangle r : rectangles) {
                    if (r.contains(l)) {
                        r.setVisible(true);
                    }
                }
            }
        }
    }
}