package controller;

import model.Drawable;
import model.DrawableState;
import util.Constants;

import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.util.List;

public class GlobalCoordinateMode extends ModeController {

    public GlobalCoordinateMode(List<Drawable> drawables) {
        super(drawables);
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        if (e.isControlDown()) {
            if (e.getWheelRotation() < 0)
                rotate(Constants.ROTATE_ANGLE);
            else rotate(-Constants.ROTATE_ANGLE);
        } else {
            if (e.getWheelRotation() < 0)
                scale(Constants.SCALE_STEP + 1);
            else scale(1 - Constants.SCALE_STEP);
        }
    }

    private void rotate(double angle) {
        for (Drawable d : drawables) {
            if (d.getDrawableState() == DrawableState.selected) {
                d.rotateGlobal(angle);
            }
        }
    }

    private void scale(double scale) {
        for (Drawable d : drawables) {
            if (d.getDrawableState() == DrawableState.selected) {
                d.scaleGlobal(scale);
            }
        }
    }

}
