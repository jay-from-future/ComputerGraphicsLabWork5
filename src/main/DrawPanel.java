package main;

import Jama.Matrix;
import interfaces.ControlPanelListener;
import interfaces.RotateListener;
import util.*;
import util.Rectangle;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.NoninvertibleTransformException;
import java.util.ArrayList;
import java.util.List;

public class DrawPanel extends JPanel implements RotateListener, ControlPanelListener {

    private final static int AXIS_LENGTH = 50;
    private final static Color BACKGROUND_COLOR = Color.BLACK;
    private final static Color MAIN_COLOR = Color.WHITE;


    private int width;
    private int height;

    private double alpha = 0;
    private double beta = 0;

    private Matrix rotationMatrix;
    private Matrix defaultRoatationMatrix;

    private List<Line<Point3D>> cubeLines;

    private boolean isAllLineVisible = true;
    private boolean isColored = false;

    public DrawPanel(int width, int height) {
        DrawPanelMouseListener drawPanelMouseListener = new DrawPanelMouseListener(this, width, height);
        addMouseListener(drawPanelMouseListener);
        addMouseMotionListener(drawPanelMouseListener);
        this.width = width;
        this.height = height;
        updateRotationMatrix();
        defaultRoatationMatrix = rotationMatrix;
    }

    @Override
    public void paint(Graphics g) {

        // заливка фона
        g.setColor(BACKGROUND_COLOR);
        g.fillRect(0, 0, width, height);

        // смещение цетра координатных осей на центр панели
        Graphics2D g2d = (Graphics2D) g;
        AffineTransform offsetToRightDownCorner = new AffineTransform();
        offsetToRightDownCorner.translate(width - AXIS_LENGTH, (height - 200) - AXIS_LENGTH);
        g2d.transform(offsetToRightDownCorner);


        // рисуем координатные оси
        Point3D zeroPoint = new Point3D(0, 0, 0);
        Point3D xAxis = new Point3D(AXIS_LENGTH, 0, 0);
        Point3D yAxis = new Point3D(0, -AXIS_LENGTH, 0);
        Point3D zAxis = new Point3D(0, 0, AXIS_LENGTH);
        drawAxes(g, zeroPoint, xAxis, yAxis, zAxis);

        try {
            g2d.transform(offsetToRightDownCorner.createInverse());
        } catch (NoninvertibleTransformException e) {
            e.printStackTrace();
        }

        AffineTransform offsetToCenter = new AffineTransform();
        offsetToCenter.translate(width / 2, ((height - 100) / 2));
        g2d.transform(offsetToCenter);



        if (isAllLineVisible) {
            // рисуем куб без обработки видимости ребер
            if (cubeLines != null) {
                g.setColor(MAIN_COLOR);
                for (Line<Point3D> l : cubeLines) {
                    Point2D start = RotationUtil.orthogonalProjection(RotationUtil.convert(l.getStart(), rotationMatrix));
                    Point2D end = RotationUtil.orthogonalProjection(RotationUtil.convert(l.getEnd(), rotationMatrix));
                    drawLine(g, start, end);
                }
            }
        } else {
            // отображение только видимых ребер
            List<Line<Point3D>> cubeLinesAfterRotation = new ArrayList<Line<Point3D>>();
            for (Line<Point3D> l : cubeLines) {
                Point3D start = RotationUtil.convert(l.getStart(), rotationMatrix);
                Point3D end = RotationUtil.convert(l.getEnd(), rotationMatrix);
                cubeLinesAfterRotation.add(new Line<Point3D>(start, end));
            }

            List<Rectangle> rectangles = new ArrayList<Rectangle>();

            Line<Point3D> l1 = cubeLinesAfterRotation.get(0);
            Line<Point3D> l2 = cubeLinesAfterRotation.get(1);
            Line<Point3D> l3 = cubeLinesAfterRotation.get(2);
            Line<Point3D> l4 = cubeLinesAfterRotation.get(3);
            Line<Point3D> l5 = cubeLinesAfterRotation.get(4);
            Line<Point3D> l6 = cubeLinesAfterRotation.get(5);
            Line<Point3D> l7 = cubeLinesAfterRotation.get(6);
            Line<Point3D> l8 = cubeLinesAfterRotation.get(7);
            Line<Point3D> l9 = cubeLinesAfterRotation.get(8);
            Line<Point3D> l10 = cubeLinesAfterRotation.get(9);
            Line<Point3D> l11 = cubeLinesAfterRotation.get(10);
            Line<Point3D> l12 = cubeLinesAfterRotation.get(11);

            rectangles.add(new Rectangle(Color.RED, l1, l2, l3, l4));
            rectangles.add(new Rectangle(Color.BLUE, l5, l6, l7, l8));
            rectangles.add(new Rectangle(Color.GREEN, l1, l10, l5, l9));
            rectangles.add(new Rectangle(Color.LIGHT_GRAY, l2, l11, l6, l10));
            rectangles.add(new Rectangle(Color.CYAN, l3, l12, l7, l11));
            rectangles.add(new Rectangle(Color.YELLOW, l4, l9, l8, l12));

            Model.determineVisibility(cubeLinesAfterRotation, rectangles);

            List<Rectangle> visibleRectangles = new ArrayList<Rectangle>();
            for (Rectangle r : rectangles) {
                if (r.isVisible()) {
                    visibleRectangles.add(r);
                }
            }

            for (Rectangle r : visibleRectangles) {
                List<Point3D> points = r.getPoints();
                if (isColored) {
                    int pointsSize = points.size();
                    int[] xPoints = new int[pointsSize];
                    int[] yPoints = new int[pointsSize];

                    Point2D currPoint;
                    for (int i = 0; i < pointsSize; i++) {
                        currPoint = RotationUtil.orthogonalProjection(points.get(i));
                        xPoints[i] = (int) currPoint.getX();
                        yPoints[i] = (int) currPoint.getY();
                    }

                    g.setColor(r.getColor());
                    g.fillPolygon(xPoints, yPoints, pointsSize);

                } else {
                    g.setColor(MAIN_COLOR);
                    drawLine(g, points.get(0), points.get(1));
                    drawLine(g, points.get(1), points.get(2));
                    drawLine(g, points.get(2), points.get(3));
                    drawLine(g, points.get(3), points.get(0));
                }
            }
        }
    }

    @Override
    public void xRotate(double alpha) {
        this.alpha = alpha;
        updateXRotationMatrix();
        repaint();
    }

    @Override
    public void yRotate(double beta) {
        this.beta = beta;
        updateYRotationMatrix();
        repaint();
    }

    @Override
    public void setDefaultRotation() {
        this.alpha = 0;
        this.beta = 0;
        setDefaultRotationMatrix();
        repaint();
    }

    private void setDefaultRotationMatrix() {
        rotationMatrix = RotationUtil.getBaseRotation();
    }

    private void updateRotationMatrix() {
        rotationMatrix = RotationUtil.getBaseRotation();
    }

    private void updateXRotationMatrix() {
        rotationMatrix = RotationUtil.getXRotationMatrix(rotationMatrix, alpha);
    }

    private void updateYRotationMatrix() {
        rotationMatrix = RotationUtil.getYRotationMatrix(rotationMatrix, beta);
    }

    private void drawLine(Graphics g, Point2D p1, Point2D p2) {
        int x1 = (int) p1.getX();
        int y1 = (int) p1.getY();
        int x2 = (int) p2.getX();
        int y2 = (int) p2.getY();

        g.drawLine(x1, y1, x2, y2);
    }

    private void drawString(Graphics g, Point2D p, String str) {
        int x = (int) p.getX();
        int y = (int) p.getY();
        g.drawString(str, x, y);
    }

    private void drawAxes(Graphics g, Point3D zeroPoint, Point3D xAxis, Point3D yAxis, Point3D zAxis) {
        xAxis = RotationUtil.convert(xAxis, defaultRoatationMatrix);
        yAxis = RotationUtil.convert(yAxis, defaultRoatationMatrix);
        zAxis = RotationUtil.convert(zAxis, defaultRoatationMatrix);

        Point2D zeroPoint2D = RotationUtil.orthogonalProjection(zeroPoint);
        Point2D xAxis2D = RotationUtil.orthogonalProjection(xAxis);
        Point2D yAxis2D = RotationUtil.orthogonalProjection(yAxis);
        Point2D zAxis2D = RotationUtil.orthogonalProjection(zAxis);

        g.setColor(Color.RED);
        drawLine(g, zeroPoint2D, xAxis2D);
        drawString(g, xAxis2D, "X");

        g.setColor(Color.GREEN);
        drawLine(g, zeroPoint2D, yAxis2D);
        drawString(g, yAxis2D, "Y");

        g.setColor(Color.BLUE);
        drawLine(g, zeroPoint2D, zAxis2D);
        drawString(g, zAxis2D, "Z");
    }


    @Override
    public void setCubeLines(List<Line<Point3D>> cubeLines) {
        this.cubeLines = cubeLines;
    }

    @Override
    public void setVisibility(boolean visible) {
        this.isAllLineVisible = visible;
        repaint();
    }

    @Override
    public void setColored(boolean colored) {
        this.isColored = colored;
        repaint();
    }
}

class DrawPanelMouseListener extends MouseAdapter {

    private RotateListener rotateListener;

    private int maxX;
    private int maxY;

    private int startX;
    private int startY;

    private int endX;
    private int endY;

    DrawPanelMouseListener(RotateListener rotateListener, int maxX, int maxY) {
        this.rotateListener = rotateListener;
        this.maxX = maxX;
        this.maxY = maxY;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        startX = e.getX();
        startY = e.getY();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        endX = e.getX();
        endY = e.getY();
        rotate();
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        endX = e.getX();
        endY = e.getY();
        rotate();
        startX = endX;
        startY = endY;
    }

    private void rotate() {

        double alpha;
        double beta;

        double x_delta = startX - endX;
        double y_delta = endY - startY;

        beta = (x_delta / maxX) * 90;
        rotateListener.yRotate(beta);
        alpha = (y_delta / maxY) * 90;
        rotateListener.xRotate(alpha);
    }
}
