package main;

import interfaces.ControlPanelListener;
import interfaces.RotateListener;
import util.Point3D;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class ControlPanel extends JPanel {

    private static final boolean DEBUG_MODE = true;

    private static final String DEFAULT_ROTATION_STR = "Установить углы вращения по умолчанию";
    private static final String VISIBLE_LINE_STR = "Выявление видимых греней и ребер";

    // количество вершин куба
    private static final int POINT_COUNT = 8;

    // начальные значения координат вершин куба
    private double[] xValues = {0, 0, 100, 100, 0, 0, 100, 100};
    private double[] yValues = {0, 0, 0, 0, 100, 100, 100, 100};
    private double[] zValues = {0, 100, 100, 0, 0, 100, 100, 0};

    private ControlPanelListener controlPanelListener;

    private JCheckBox isBaseLineVisible;

    public ControlPanel(ControlPanelListener controlPanelListener) {

        this.controlPanelListener = controlPanelListener;

        JPanel buttonPanel = new JPanel();

        ButtonListener buttonListener = new ButtonListener();
        CheckBoxListener checkBoxListener = new CheckBoxListener();

        JButton setDefaultRotationButton = new JButton(DEFAULT_ROTATION_STR);
        setDefaultRotationButton.addActionListener(buttonListener);

        isBaseLineVisible = new JCheckBox(VISIBLE_LINE_STR, true);
        isBaseLineVisible.addActionListener(checkBoxListener);

        buttonPanel.add(setDefaultRotationButton);
        buttonPanel.add(isBaseLineVisible);

        add(buttonPanel);
        sendCubePoints();
    }

    private void sendCubePoints() {
        List<Point3D> cubePoints = new ArrayList<Point3D>();
        for (int i = 0; i < POINT_COUNT; i++) {
            cubePoints.add(new Point3D(xValues[i], yValues[i], zValues[i]));
        }

        if (DEBUG_MODE) {
            System.out.println("sendCubePoints()");
            for (Point3D p : cubePoints) {
                System.out.println(p);
            }
        }

        controlPanelListener.setBasePoints(cubePoints);
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
                controlPanelListener.setBaseLineVisible(isBaseLineVisible.isSelected());
            }
        }
    }
}

