package main;

public class Main {

    private static final int WIDTH = 600;
    private static final int HEIGHT = 400;

    public static void main(String[] args) {
        DrawPanel drawPanel = new DrawPanel(WIDTH, HEIGHT);
        ControlPanel controlPanel = new ControlPanel(drawPanel);
        new MainWindow("LabWork5", WIDTH, HEIGHT, drawPanel, controlPanel);
    }
}
