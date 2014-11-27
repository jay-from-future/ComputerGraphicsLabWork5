package main;

import interfaces.ControlPanelSpinnerListener;
import interfaces.RotateListener;

public class Spinner implements Runnable, ControlPanelSpinnerListener {

    private boolean isRunning;
    private boolean isPaused;
    private RotateListener rotateListener;
    private long period;

    public Spinner(RotateListener rotateListener, long period) {
        this.rotateListener = rotateListener;
        this.period = period;
        isRunning = true;
        isPaused = false;
    }

    @Override
    public void run() {
        int count = 0;
        while (isRunning) {
            if (!isPaused) {
                if (count < 25) {
                    rotateListener.yRotate(5);
                    count++;
                } else if (count < 50) {
                    rotateListener.xRotate(5);
                    count++;
                } else {
                    count = 0;
                }
            }
            try {
                Thread.sleep(period);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void pauseOrRestart() {
        isPaused = !isPaused;
    }
}
