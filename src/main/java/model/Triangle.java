package model;

import util.Sequence;

import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Triangle implements Drawable, Serializable {
    private Point p1;
    private Point p2;
    private Point p3;
    private DrawableState state;
    private int id;
    private float opacity = 0;

    public Triangle(double maxX, double maxY){
        double randX = new Random().nextDouble();
        double randY = new Random().nextDouble();
        p1 = new Point(randX*maxX, randY*maxY);
        randX = new Random().nextDouble();
        randY = new Random().nextDouble();
        p2 = new Point(randX*maxX, randY*maxY);
        randX = new Random().nextDouble();
        randY = new Random().nextDouble();
        p3 = new Point(randX*maxX, randY*maxY);
        state = DrawableState.normal;
        id = Sequence.intsance().getNext();
    }

    public Triangle(Point p1, Point p2, Point p3) {
        this.p1 = p1;
        this.p2 = p2;
        this.p3 = p3;
        state = DrawableState.normal;
    }

    public Triangle(Triangle triangle){
        p1 = new Point(triangle.p1);
        p2 = new Point(triangle.p2);
        p3 = new Point(triangle.p3);
        state = DrawableState.normal;
        id = triangle.id;
        opacity = triangle.opacity;
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
        (new Segment(p1,p2)).draw(g);
        (new Segment(p1,p3)).draw(g);
        (new Segment(p2,p3)).draw(g);
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
        Segment s1 = new Segment(p1,p2);
        if(s1.isMouseNear(mousePoint, sens))
            return true;

        Segment s2 = new Segment(p1,p3);
        if (s2.isMouseNear(mousePoint, sens))
            return true;

        Segment s3 = new Segment(p2,p3);
        return s3.isMouseNear(mousePoint, sens);
    }

    @Override
    public void scaleSelf(double scale) {
        Point center = center();
        p1.shift(-center.getX(), -center.getY());
        p2.shift(-center.getX(), -center.getY());
        p3.shift(-center.getX(), -center.getY());
        scalePoints(scale);
        p1.shift(center.getX(), center.getY());
        p2.shift(center.getX(), center.getY());
        p3.shift(center.getX(), center.getY());
    }

    @Override
    public void rotateSelf(double angle) {
        Point center = center();
        p1.shift(-center.getX(), -center.getY());
        p2.shift(-center.getX(), -center.getY());
        p3.shift(-center.getX(), -center.getY());
        rotatePoints(angle);
        p1.shift(center.getX(), center.getY());
        p2.shift(center.getX(), center.getY());
        p3.shift(center.getX(), center.getY());
    }

    private Point center(){
        double x = (p1.getX() + p2.getX() + p3.getX())/3;
        double y = (p1.getY() + p2.getY() + p3.getY())/3;
        return  new Point(x, y);
    }

    @Override
    public void shift(Point p) {
        p1.shift(p.getX(),p.getY());
        p2.shift(p.getX(),p.getY());
        p3.shift(p.getX(),p.getY());
    }
    private void rotatePoints(double angle){
        p1.rotate(angle);
        p2.rotate(angle);
        p3.rotate(angle);
    }

    private void scalePoints(double scale){
        p1.scale(scale);
        p2.scale(scale);
        p3.scale(scale);
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
        Collections.sort(points);
        return points.get(points.size()-1);
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public Drawable morf(double t, Drawable d) {
        Triangle tr = (Triangle) d;
        Triangle tr2= new Triangle(p1.morf(t, tr.p1), p2.morf(t, tr.p2), p3.morf(t, tr.p3));
        tr2.id=tr.id;
        return tr2;
    }

    @Override
    public Drawable morf(double t) {
        Triangle tr = new Triangle(this);
        tr.setOpacity((float)opacity);
        return tr;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Triangle triangle = (Triangle) o;

        return id == triangle.id;

    }

    @Override
    public int hashCode() {
        return id;
    }
}
