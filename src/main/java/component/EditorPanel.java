package component;

import controller.GlobalCoordinateMode;
import controller.LocalCoordinateMode;
import controller.ModeController;
import model.*;
import util.Colors;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class EditorPanel extends JPanel {

    private List<Drawable> drawableList;

    private ModeController[] modes;

    public final static int LOCAL_MODE = 0;
    public final static int GLOBAL_MODE = 1;

    private int currMode;

    public EditorPanel() {
        this.drawableList = new ArrayList<Drawable>();
        MouseHandler handler = new MouseHandler();

        modes = new ModeController[2];
        modes[LOCAL_MODE] = new LocalCoordinateMode(drawableList);
        modes[GLOBAL_MODE] = new GlobalCoordinateMode(drawableList);
        currMode = LOCAL_MODE;

        addMouseListener(handler);
        addMouseMotionListener(handler);
        addMouseWheelListener(handler);
    }

    public List<Drawable> getDrawableList() {
        return drawableList;
    }

    public void setDrawableList(List<Drawable> drawableList) {
        this.drawableList = drawableList;
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D graphics2D = (Graphics2D) g;
        for (Drawable d : drawableList) {
            if (d.getDrawableState() == DrawableState.selected) {
                graphics2D.setColor(Colors.SELECTED);
            } else if (d.getDrawableState() == DrawableState.normal) {
                graphics2D.setColor(Colors.NORMAL);
            } else {
                graphics2D.setColor(Colors.PRE_SELECTED);
            }
            d.draw(graphics2D);
        }
    }


    public int getCurrMode() {
        return currMode;
    }

    public void setCurrMode(int currMode) {
        this.currMode = currMode;
    }

    private class MouseHandler extends MouseAdapter {

        @Override
        public void mouseMoved(final MouseEvent e) {
            modes[currMode].mouseMoved(e);
            repaint();
        }

        @Override
        public void mouseClicked(final MouseEvent e) {
            modes[currMode].mouseClicked(e);
            repaint();
        }

        @Override
        public void mouseWheelMoved(final MouseWheelEvent e) {
            modes[currMode].mouseWheelMoved(e);
            repaint();
        }

        @Override
        public void mousePressed(final MouseEvent e) {
            modes[currMode].mousePressed(e);
            repaint();
        }

        @Override
        public void mouseDragged(final MouseEvent e) {
            modes[currMode].mouseDragged(e);
            repaint();
        }

        @Override
        public void mouseReleased(final MouseEvent e) {
            modes[currMode].mouseReleased(e);
            repaint();
        }

    }


}
