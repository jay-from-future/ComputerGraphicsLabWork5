package util;

public class PointLight {

    private Point3D lightPoint;
    private int intensity;

    public PointLight(Point3D lightPoint, int intensity) {
        this.lightPoint = lightPoint;
        this.intensity = intensity;
    }

    public double getLightToPoint(Point3D point) {
        double dist = PointUtil.distanceBetweenPoints(lightPoint, point);

//        System.out.println("dist " + dist);

//        return (int) (Double.valueOf(intensity) / Math.pow(dist, 2));
        return dist;
    }
}
