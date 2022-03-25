package xyz.jayfromfuture.interfaces;

import xyz.jayfromfuture.util.Line;
import xyz.jayfromfuture.util.Point3D;

import java.util.List;

public interface ControlPanelListener {

    void setCubeLines(List<Line<Point3D>> cubeLines);

    void setVisibility(boolean visible);

    void setColored(boolean colored);
}
