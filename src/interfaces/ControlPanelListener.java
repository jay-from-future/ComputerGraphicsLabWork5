package interfaces;

import util.Line;
import util.Point3D;

import java.util.List;

public interface ControlPanelListener {

    void setCubeLines(List<Line<Point3D>> cubeLines);

    void setVisibility(boolean visible);

    void setColored(boolean colored);
}
