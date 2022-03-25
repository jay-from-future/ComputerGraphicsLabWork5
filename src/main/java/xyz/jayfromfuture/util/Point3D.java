package xyz.jayfromfuture.util;

public class Point3D extends Point2D {

    private final double z;

    public Point3D(double x, double y, double z) {
        super(x, y);
        this.z = z;
    }

    public double getZ() {
        return z;
    }

    @Override
    public double[] getArray() {
        return new double[]{this.getX(), this.getY(), this.getZ()};
    }

    @Override
    public String toString() {
        return "Point3D{" +
                "x=" + getX() +
                "y=" + getY() +
                "z=" + z +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Point3D)) return false;

        Point3D point3D = (Point3D) o;

        if (Double.compare(point3D.z, z) != 0) return false;
        if (Double.compare(point3D.getX(), getX()) != 0) return false;
        if (Double.compare(point3D.getY(), getY()) != 0) return false;

        return true;
    }

    @Override
    public int hashCode() {
        long temp = Double.doubleToLongBits(z);
        return (int) (temp ^ (temp >>> 32));
    }
}
