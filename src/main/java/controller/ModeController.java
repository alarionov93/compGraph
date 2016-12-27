package controller;

import model.Drawable;
import model.DrawableState;
import model.Point;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.util.List;

public abstract class ModeController {
    protected List<Drawable> drawables;

    private static final double SENS = 5.0;

    private Point prevPoint = null;
    private boolean isSelectedShift = false;

    public ModeController(List<Drawable> drawables) {
        this.drawables = drawables;
    }

    abstract public void mouseWheelMoved(MouseWheelEvent e);

    public void mouseMoved(MouseEvent e){
        boolean flag = false;
        for (Drawable d : drawables) {
            if (d.isMouseNear(new Point(e.getX(), e.getY()), SENS)
                    && d.getDrawableState() != DrawableState.selected && !flag) {
                d.setDrawableState(DrawableState.preSelected);
                flag = true;
            } else if (d.getDrawableState() != DrawableState.selected) {
                d.setDrawableState(DrawableState.normal);
            }
        }
    }

    public void mousePressed(MouseEvent e){
        if (SwingUtilities.isLeftMouseButton(e)) {
            prevPoint = new Point(e.getX(), e.getY());
            boolean flag = false;
            for (Drawable d : drawables){
                if (d.getDrawableState() == DrawableState.preSelected
                        && d.isMouseNear(prevPoint, SENS)) {
                    flag = true;
                    isSelectedShift = false;
                    break;
                }
                if (d.getDrawableState() == DrawableState.selected
                        && d.isMouseNear(prevPoint, SENS)) {
                    flag = true;
                    isSelectedShift = true;
                    break;
                }
            }
            if(!flag)
                prevPoint = null;
        }
    }

    public void mouseClicked(MouseEvent e){
        if (!SwingUtilities.isLeftMouseButton(e))
            return;
        for (Drawable d : drawables) {
            if (d.getDrawableState() == DrawableState.preSelected)
                d.setDrawableState(DrawableState.selected);
            else if (d.getDrawableState() == DrawableState.selected)
                d.setDrawableState(DrawableState.normal);
        }
    }

    public void mouseDragged(MouseEvent e){
        if (SwingUtilities.isLeftMouseButton(e) && prevPoint != null) {
            Point curr = new Point(e.getX(), e.getY());
            shift(new Point(curr.getX() - prevPoint.getX(), curr.getY() - prevPoint.getY()));
            prevPoint = curr;
        }
    }

    public void mouseReleased(MouseEvent e){
        if (SwingUtilities.isLeftMouseButton(e))
            prevPoint = null;
    }

    public void setDrawableList(List<Drawable> drawables){
        this.drawables = drawables;
    }

    public List<Drawable> getDrawables(){
        return drawables;
    }

    private void shift(Point p) {
        for (Drawable d : drawables) {
            if (d.getDrawableState() == DrawableState.preSelected && !isSelectedShift) {
                d.shift(p);
                break;
            }
            else if(d.getDrawableState() == DrawableState.selected && isSelectedShift){
                d.shift(p);
                break;
            }
        }
    }
}
