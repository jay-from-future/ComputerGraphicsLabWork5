package xyz.jayfromfuture;

import xyz.jayfromfuture.interfaces.ControlPanelSpinnerListener;
import xyz.jayfromfuture.interfaces.RotateListener;

public class Spinner implements Runnable, ControlPanelSpinnerListener {

    private boolean isRunning;
    private final long period;
    private final RotateListener rotateListener;
    private boolean isPaused;

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
                isRunning = false;
            }
        }
    }

    @Override
    public void pauseOrRestart() {
        isPaused = !isPaused;
    }
}
