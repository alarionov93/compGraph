package model;

import java.awt.*;

public interface Drawable {

    void draw(Graphics2D g);
    DrawableState getDrawableState();
    void setDrawableState(DrawableState drawableState);
    boolean isMouseNear(Point mousePoint, double sens);
    void scaleSelf(double scale);
    void rotateSelf(double angle);
    void scaleGlobal(double scale);
    void rotateGlobal(double angle);
    void shift(Point p);
    Point farrestPoint();
    int getId();
    Drawable morf(double t, Drawable d);
    Drawable morf(double t);
}
