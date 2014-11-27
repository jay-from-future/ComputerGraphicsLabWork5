package util;

public class PointUtil {

    public static double distanceBetweenPoints(Point3D p1, Point3D p2) {
        double dist = Math.sqrt(Math.pow((p1.getX() - p2.getX()), 2) + Math.pow((p1.getX() - p2.getX()), 2) +
                Math.pow((p1.getX() - p2.getX()), 2));
        return dist;
    }
}
