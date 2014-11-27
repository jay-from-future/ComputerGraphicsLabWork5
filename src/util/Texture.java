package util;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Texture extends BufferedImage {

    private static final int WIDTH = 100;
    private static final int HEIGHT = 100;
    private static final int IMAGE_TYPE = BufferedImage.TYPE_INT_RGB;

    private Color baseColor;
    private Graphics graphics;
    private PointLight pointLight;

    public Texture(Color baseColor) {
        super(WIDTH, HEIGHT, IMAGE_TYPE);
        this.baseColor = baseColor;
        graphics = getGraphics();
    }

    public void setPointLight(PointLight pointLight) {
        this.pointLight = pointLight;
    }

    private void repaint(Rectangle rectangle) {

        List<Point3D> points = rectangle.getPoints();

        Point3D p0 = points.get(0);
        Point3D p1 = points.get(1);
        Point3D p2 = points.get(2);
        Point3D p3 = points.get(3);

        List<Point3D> basePoints = Arrays.asList(p0, p1, p2, p3);

        double p0light = pointLight.getLightToPoint(p0);
        double p1light = pointLight.getLightToPoint(p1);
        double p2light = pointLight.getLightToPoint(p2);
        double p3light = pointLight.getLightToPoint(p3);

        double[] lights = {p0light, p1light, p2light, p3light};
        Arrays.sort(lights);

        Map<Double, Point3D> pointsLight = new HashMap<Double, Point3D>();
        pointsLight.put(p0light, p0);
        pointsLight.put(p1light, p1);
        pointsLight.put(p2light, p2);
        pointsLight.put(p3light, p3);

        for (int i = 0; i < lights.length; i++) {
//            int i = basePoints.indexOf(pointsLight.get(lights[0]));

        }


        // 0 - 0, 0
        // 1 - 50, 0
        // 2 - 0, 50
        // 3 - 50, 50


        graphics.setColor(baseColor);
        GradientPaint gp = new GradientPaint((float) p0.getX(), (float) p0.getY(), baseColor, (float) p3.getX(), (float) p3.getY(), Color.BLACK);
        ((Graphics2D) graphics).setPaint(gp);
        graphics.fillRect(0, 0, WIDTH, HEIGHT);
    }

    public TexturePaint getTexturePaint(Polygon polygon, Rectangle rectangle) {
        repaint(rectangle);
        TexturePaint texturePaint = new TexturePaint(this, polygon.getBounds2D());
        return texturePaint;
    }
}
