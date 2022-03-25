package xyz.jayfromfuture;

import xyz.jayfromfuture.interfaces.ControlPanelListener;
import xyz.jayfromfuture.interfaces.ControlPanelSpinnerListener;
import xyz.jayfromfuture.interfaces.RotateListener;
import xyz.jayfromfuture.util.Line;
import xyz.jayfromfuture.util.Point3D;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class ControlPanel extends JPanel {

    private static final String DEFAULT_ROTATION_STR = "Установить углы вращения по умолчанию";
    private static final String VISIBLE_LINE_STR = "Только видимые грени и ребера";
    private static final String COLORED_STR = "Раскрасить грани";
    private static final String SPINNER_STR = "Вращение куба";

    // количество вершин куба
    private static final int POINT_COUNT = 8;
    private final ControlPanelSpinnerListener spinnerListener;

    // начальные значения координат вершин куба
    private final double[] xValues = {-50, -50, 50, 50, -50, -50, 50, 50};
    private final double[] yValues = {50, 50, 50, 50, -50, -50, -50, -50};
    private final double[] zValues = {-50, 50, 50, -50, -50, 50, 50, -50};

    private final ControlPanelListener controlPanelListener;

    private final JCheckBox isVisible;
    private final JCheckBox isColored;

    public ControlPanel(ControlPanelListener controlPanelListener, ControlPanelSpinnerListener spinnerListener) {

        this.controlPanelListener = controlPanelListener;
        this.spinnerListener = spinnerListener;

        JPanel buttonPanel = new JPanel(new GridLayout(3, 1));

        ButtonListener buttonListener = new ButtonListener();
        CheckBoxListener checkBoxListener = new CheckBoxListener();

        JButton setDefaultRotationButton = new JButton(DEFAULT_ROTATION_STR);
        setDefaultRotationButton.addActionListener(buttonListener);

        isVisible = new JCheckBox(VISIBLE_LINE_STR, false);
        isVisible.addActionListener(checkBoxListener);
        isColored = new JCheckBox(COLORED_STR, false);
        isColored.addActionListener(checkBoxListener);
        JCheckBox isSpinning = new JCheckBox(SPINNER_STR, true);
        isSpinning.addActionListener(checkBoxListener);

        buttonPanel.add(setDefaultRotationButton);
        buttonPanel.add(isVisible);
        buttonPanel.add(isColored);
        buttonPanel.add(isSpinning);

        add(buttonPanel);
        sendCubeLines();
    }

    private void sendCubeLines() {
        List<Point3D> cubePoints = new ArrayList<>();
        for (int i = 0; i < POINT_COUNT; i++) {
            cubePoints.add(new Point3D(xValues[i], yValues[i], zValues[i]));
        }

        List<Line<Point3D>> cubeLines = new ArrayList<>();

        cubeLines.add(new Line<>(cubePoints.get(0), cubePoints.get(1)));
        cubeLines.add(new Line<>(cubePoints.get(1), cubePoints.get(2)));
        cubeLines.add(new Line<>(cubePoints.get(2), cubePoints.get(3)));

        cubeLines.add(new Line<>(cubePoints.get(3), cubePoints.get(0)));

        cubeLines.add(new Line<>(cubePoints.get(4), cubePoints.get(5)));
        cubeLines.add(new Line<>(cubePoints.get(5), cubePoints.get(6)));
        cubeLines.add(new Line<>(cubePoints.get(6), cubePoints.get(7)));

        cubeLines.add(new Line<>(cubePoints.get(7), cubePoints.get(4)));

        cubeLines.add(new Line<>(cubePoints.get(0), cubePoints.get(4)));
        cubeLines.add(new Line<>(cubePoints.get(1), cubePoints.get(5)));
        cubeLines.add(new Line<>(cubePoints.get(2), cubePoints.get(6)));
        cubeLines.add(new Line<>(cubePoints.get(3), cubePoints.get(7)));

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
            } else if (e.getActionCommand().equals(ControlPanel.COLORED_STR)) {
                controlPanelListener.setColored(isColored.isSelected());
            } else if (e.getActionCommand().equals(ControlPanel.SPINNER_STR)) {
                spinnerListener.pauseOrRestart();
            }
        }
    }
}

