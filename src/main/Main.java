package main;

public class Main {

    private static final int WIDTH = 600;
    private static final int HEIGHT = 400;
    private static final long PERIOD = 50;

    public static void main(String[] args) {
        DrawPanel drawPanel = new DrawPanel(WIDTH, HEIGHT);
        Spinner spinner = new Spinner(drawPanel, PERIOD);
        ControlPanel controlPanel = new ControlPanel(drawPanel, spinner);
        new MainWindow("LabWork5", WIDTH, HEIGHT, drawPanel, controlPanel);
        new Thread(spinner).run();
    }
}
