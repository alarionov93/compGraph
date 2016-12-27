package model;

import util.Sequence;

import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Rectangle implements Drawable, Serializable {



    private Point p1;
    private Point p2;
    private Point p3;
    private Point p4;
    private DrawableState drawableState;
    private int id;
    private float opacity=1;



    public Rectangle(double maxX, double maxY){
        double randX = new Random().nextDouble();
        double randY = new Random().nextDouble();
        Point p1 = new Point(randX*maxX, randY*maxY);
        randX = new Random().nextDouble();
        randY = new Random().nextDouble();
        Point p2 = new Point(randX*maxX, randY*maxY);
        this.p1 = p1;
        this.p2 = new Point(p2.getX(), p1.getY());
        p3 = p2;
        p4 = new Point(p1.getX(), p2.getY());
        drawableState = DrawableState.normal;
        id = Sequence.intsance().getNext();
    }

    public Rectangle(Point p1, Point p2, Point p3, Point p4){
        this.p1 = p1;
        this.p2 = p2;
        this.p3 = p3;
        this.p4 = p4;
        drawableState = DrawableState.normal;
        id = -1;
    }

    public Rectangle(Rectangle rect){
        p1 = new Point(rect.p1);
        p2 = new Point(rect.p2);
        p3 = new Point(rect.p3);
        p4 = new Point(rect.p4);
        drawableState = DrawableState.normal;
        opacity = rect.opacity;
        id = rect.id;
    }


    @Override
    public void draw(Graphics2D g) {
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity));
        (new Segment(p1,p2)).draw(g);
        (new Segment(p2,p3)).draw(g);
        (new Segment(p3,p4)).draw(g);
        (new Segment(p4,p1)).draw(g);
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1));
    }

    @Override
    public DrawableState getDrawableState() {
        return drawableState;
    }

    @Override
    public void setDrawableState(DrawableState drawableState) {
        this.drawableState = drawableState;
    }

    @Override
    public boolean isMouseNear(Point mousePoint, double sens){
        if((new Segment(p1,p2)).isMouseNear(mousePoint, sens))
            return true;
        if((new Segment(p2,p3)).isMouseNear(mousePoint, sens))
            return true;
        if((new Segment(p3,p4)).isMouseNear(mousePoint, sens))
            return true;
        return (new Segment(p4,p1)).isMouseNear(mousePoint, sens);
    }

    @Override
    public void scaleSelf(double scale) {
        Point center = center();
        p1.shift(-center.getX(), -center.getY());
        p2.shift(-center.getX(), -center.getY());
        p3.shift(-center.getX(), -center.getY());
        p4.shift(-center.getX(), -center.getY());
        scalePoints(scale);
        p1.shift(center.getX(), center.getY());
        p2.shift(center.getX(), center.getY());
        p3.shift(center.getX(), center.getY());
        p4.shift(center.getX(), center.getY());
    }

    @Override
    public void rotateSelf(double angle) {
        Point center = center();
        p1.shift(-center.getX(), -center.getY());
        p2.shift(-center.getX(), -center.getY());
        p3.shift(-center.getX(), -center.getY());
        p4.shift(-center.getX(), -center.getY());
        rotatePoints(angle);
        p1.shift(center.getX(), center.getY());
        p2.shift(center.getX(), center.getY());
        p3.shift(center.getX(), center.getY());
        p4.shift(center.getX(), center.getY());
    }

    private Point center(){
        double x = (p1.getX()+p3.getX())/2;
        double y = (p1.getY()+p3.getY())/2;
        return new Point(x, y);
    }

    private void rotatePoints(double angle){
        p1.rotate(angle);
        p2.rotate(angle);
        p3.rotate(angle);
        p4.rotate(angle);
    }

    private void scalePoints(double scale){
        p1.scale(scale);
        p2.scale(scale);
        p3.scale(scale);
        p4.scale(scale);
    }

    public float getOpacity() {
        return opacity;
    }

    public void setOpacity(float opacity) {
        this.opacity = opacity;
    }

    @Override
    public void shift(Point p) {
        p1.shift(p.getX(),p.getY());
        p2.shift(p.getX(),p.getY());
        p3.shift(p.getX(),p.getY());
        p4.shift(p.getX(),p.getY());
    }

    @Override
    public void scaleGlobal(double scale) {
        scalePoints(scale);
    }

    @Override
    public void rotateGlobal(double angle) {
        rotatePoints(angle);
    }

    @Override
    public Point farrestPoint() {
        java.util.List<Point> points = new ArrayList<Point>();
        points.add(p1);
        points.add(p2);
        points.add(p3);
        points.add(p4);
        Collections.sort(points);
        return points.get(points.size()-1);
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public Drawable morf(double t, Drawable d) {
        Rectangle r = (Rectangle) d;
        Rectangle r2 = new Rectangle(p1.morf(t,r.p1), p2.morf(t,r.p2),
                p3.morf(t,r.p3), p4.morf(t,r.p4));
        r2.id = r.id;
        return r2;
    }

    @Override
    public Drawable morf(double t) {
        Rectangle res = new Rectangle(this);
        res.setOpacity((float)t);
        return res;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Rectangle rectangle = (Rectangle) o;

        return id == rectangle.id;

    }

    @Override
    public int hashCode() {
        return id;
    }
}
