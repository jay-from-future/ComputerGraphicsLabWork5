package xyz.jayfromfuture.util;

public class PointLight {

    private final Point3D lightPoint;

    public PointLight(Point3D lightPoint) {
        this.lightPoint = lightPoint;
    }

    public double getLightToPoint(Point3D point) {
        return PointUtil.distanceBetweenPoints(lightPoint, point);
    }
}
