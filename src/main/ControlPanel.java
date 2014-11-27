package main;

import interfaces.ControlPanelListener;
import interfaces.RotateListener;
import util.Line;
import util.Point3D;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class ControlPanel extends JPanel {

    private static final String DEFAULT_ROTATION_STR = "Установить углы вращения по умолчанию";
    private static final String VISIBLE_LINE_STR = "Выявление видимых греней и ребер";

    // количество вершин куба
    private static final int POINT_COUNT = 8;

    // начальные значения координат вершин куба
    private double[] xValues = {-50, -50, 50, 50, -50, -50, 50, 50};
    private double[] yValues = {50, 50, 50, 50, -50, -50, -50, -50};
    private double[] zValues = {-50, 50, 50, -50, -50, 50, 50, -50};

    private ControlPanelListener controlPanelListener;

    private JCheckBox isVisible;

    public ControlPanel(ControlPanelListener controlPanelListener) {

        this.controlPanelListener = controlPanelListener;

        JPanel buttonPanel = new JPanel();

        ButtonListener buttonListener = new ButtonListener();
        CheckBoxListener checkBoxListener = new CheckBoxListener();

        JButton setDefaultRotationButton = new JButton(DEFAULT_ROTATION_STR);
        setDefaultRotationButton.addActionListener(buttonListener);

        isVisible = new JCheckBox(VISIBLE_LINE_STR, false);
        isVisible.addActionListener(checkBoxListener);

        buttonPanel.add(setDefaultRotationButton);
        buttonPanel.add(isVisible);

        add(buttonPanel);
        sendCubeLines();
    }

    private void sendCubeLines() {
        List<Point3D> cubePoints = new ArrayList<Point3D>();
        for (int i = 0; i < POINT_COUNT; i++) {
            cubePoints.add(new Point3D(xValues[i], yValues[i], zValues[i]));
        }

        List<Line<Point3D>> cubeLines = new ArrayList<Line<Point3D>>();

        cubeLines.add(new Line<Point3D>(cubePoints.get(0), cubePoints.get(1)));
        cubeLines.add(new Line<Point3D>(cubePoints.get(1), cubePoints.get(2)));
        cubeLines.add(new Line<Point3D>(cubePoints.get(2), cubePoints.get(3)));

        cubeLines.add(new Line<Point3D>(cubePoints.get(3), cubePoints.get(0)));

        cubeLines.add(new Line<Point3D>(cubePoints.get(4), cubePoints.get(5)));
        cubeLines.add(new Line<Point3D>(cubePoints.get(5), cubePoints.get(6)));
        cubeLines.add(new Line<Point3D>(cubePoints.get(6), cubePoints.get(7)));

        cubeLines.add(new Line<Point3D>(cubePoints.get(7), cubePoints.get(4)));

        cubeLines.add(new Line<Point3D>(cubePoints.get(0), cubePoints.get(4)));
        cubeLines.add(new Line<Point3D>(cubePoints.get(1), cubePoints.get(5)));
        cubeLines.add(new Line<Point3D>(cubePoints.get(2), cubePoints.get(6)));
        cubeLines.add(new Line<Point3D>(cubePoints.get(3), cubePoints.get(7)));

        controlPanelListener.setCubeLines(cubeLines);
    }

    class ButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getActionCommand().equals(ControlPanel.DEFAULT_ROTATION_STR)) {
                ((RotateListener) controlPanelListener).setDefaultRotation();
            }
        }
    }

    class CheckBoxListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getActionCommand().equals(ControlPanel.VISIBLE_LINE_STR)) {
                controlPanelListener.setVisibility(!isVisible.isSelected());
            }
        }
    }
}

