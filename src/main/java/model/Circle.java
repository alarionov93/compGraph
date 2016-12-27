package model;

import util.Sequence;

import java.awt.*;
import java.io.Serializable;
import java.util.Random;

public class Circle implements Drawable, Serializable {

    private Point c;
    private double r;
    private DrawableState state;
    private int id;
    private float opacity = 1;

    public Circle(Point c, double r) {
        this.c = c;
        this.r = r;
        state = DrawableState.normal;
        id = -1;
    }

    public Circle(double maxX, double maxY){
        double randX = new Random().nextDouble();
        double randY = new Random().nextDouble();
        Point p1 = new Point(randX*maxX, randY*maxY);
        randX = new Random().nextDouble();
        Point p2 = new Point(randX*maxX, randX*maxY);
        double x = (p1.getX()+p2.getX())/2;
        double y = (p1.getY()+p2.getY())/2;
        c = new Point(x,y);
        r = Math.abs(x-p2.getX());
        id = Sequence.intsance().getNext();
        state = DrawableState.normal;
    }

    public Circle(Circle circle){
        c=new Point(circle.c);
        r = circle.r;
        state = DrawableState.normal;
        id = circle.id;
        opacity = circle.opacity;
    }

    public float getOpacity() {
        return opacity;
    }

    public void setOpacity(float opacity) {
        this.opacity = opacity;
    }

    @Override
    public void draw(Graphics2D g) {
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity));
        g.drawOval((int)(c.getX()-r), (int)(c.getY()-r), (int)(2*r), (int)(2*r));
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1));
    }

    @Override
    public DrawableState getDrawableState() {
        return state;
    }

    @Override
    public void setDrawableState(DrawableState drawableState) {
        state = drawableState;
    }

    @Override
    public boolean isMouseNear(Point mousePoint, double sens) {
        return c.dist(mousePoint)<=sens+r && c.dist(mousePoint)>= Math.abs(sens-r);
    }

    @Override
    public void scaleSelf(double scale) {
        r*=scale;
    }

    @Override
    public void rotateSelf(double angle) {
        //типо вращаем окржность
    }

    @Override
    public void shift(Point p) {
        c.shift(p.getX(),p.getY());
    }

    @Override
    public void scaleGlobal(double scale) {
        c.scale(scale);
        scaleSelf(scale);
    }

    @Override
    public void rotateGlobal(double angle) {
        c.rotate(angle);
    }

    @Override
    public Point farrestPoint() {
        return new Point(c.getX()+r, c.getY()+r);
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public Drawable morf(double t, Drawable d) {
        Circle circle = (Circle) d;
        double r = this.r*(1-t)+circle.r*t;
        Circle cr = new Circle(c.morf(t,circle.c), r);
        cr.id = circle.id;
        return cr;
    }

    @Override
    public Drawable morf(double t) {
        Circle res = new Circle(this);
        res.setOpacity((float) t);
        return res;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Circle circle = (Circle) o;

        return id == circle.id;

    }

    @Override
    public int hashCode() {
        return id;
    }
}
