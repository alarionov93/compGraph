package model;

import util.Constants;
import util.Sequence;

import java.awt.*;
import java.io.Serializable;
import java.util.Random;

public class Segment implements Drawable, Serializable {

    private Point p1;
    private Point p2;
    private DrawableState state;
    private int id;
    private float opacity = 1;

    public Segment(Point p1, Point p2) {
        this.p1 = p1;
        this.p2 = p2;
        state = DrawableState.normal;
        id = -1;
    }

    public Segment(double maxX, double maxY){
        double randX = new Random().nextDouble();
        double randY = new Random().nextDouble();
        p1 = new Point(randX*maxX, randY*maxY);
        randX = new Random().nextDouble();
        randY = new Random().nextDouble();
        p2 = new Point(randX*maxX, randY*maxY);
        state = DrawableState.normal;
        id = Sequence.intsance().getNext();
    }

    public Segment(Segment segment){
        p1 = new Point(segment.p1);
        p2 = new Point(segment.p2);
        state = DrawableState.normal;
        id = segment.id;
        opacity = segment.opacity;
    }

    public boolean isBelong(Point p){
        if(p.dist(p1)+p.dist(p2) <= length()+ Constants.EPS
                && p.dist(p1)+p.dist(p2) >= length() - Constants.EPS)
            return true;
        else return false;
    }

    public double length(){
        return p1.dist(p2);
    }

    public Point getP1() {
        return p1;
    }

    public void setP1(Point p1) {
        this.p1 = p1;
    }

    public Point getP2() {
        return p2;
    }

    public void setP2(Point p2) {
        this.p2 = p2;
    }

    public float getOpacity() {
        return opacity;
    }

    public void setOpacity(float opacity) {
        this.opacity = opacity;
    }

    public void draw(Graphics2D g) {
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity));
        g.drawLine((int)p1.getX(), (int)p1.getY(), (int)p2.getX(), (int)p2.getY());
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
        Point p1 = new Point(getP1());
        Point p2 = new Point(getP2());
        p1.shift(-mousePoint.getX(), -mousePoint.getY());
        p2.shift(-mousePoint.getX(), -mousePoint.getY());

        double A = p1.getY() - p2.getY();
        double B = p2.getX() - p1.getX();
        double C = p1.getX()*p2.getY() - p2.getX()*p1.getY();

        double x0 = -A*C/(A*A+B*B),  y0 = -B*C/(A*A+B*B);
        Segment tmp = new Segment(p1,p2);
        if (C*C > sens*sens*(A*A+B*B)+Constants.EPS){
            return false;
        }
        else if (Math.abs (C*C - sens*sens*(A*A+B*B)) < Constants.EPS) {
            if(tmp.isBelong(new Point(x0,y0))) {
                return true;
            }
            else return false;
        }
        else {
            double D = sens*sens - C*C/(A*A+B*B);
            double mult = Math.sqrt (D / (A*A+B*B));
            double ax,ay,bx,by;
            ax = x0 + B * mult;
            bx = x0 - B * mult;
            ay = y0 - A * mult;
            by = y0 + A * mult;
            if(tmp.isBelong(new Point(ax,ay))
                    || tmp.isBelong(new Point(bx,by)))
                return true;
            else return false;
        }
    }

    @Override
    public void scaleSelf(double scale) {
        double offsetX = (p1.getX()+p2.getX())/2;
        double offsetY = (p1.getY()+p2.getY())/2;
        p1.shift(-offsetX, -offsetY);
        p2.shift(-offsetX, -offsetY);
        scalePoints(scale);
        p1.shift(offsetX, offsetY);
        p2.shift(offsetX, offsetY);
    }

    private void scalePoints(double scale){
        p1.scale(scale);
        p2.scale(scale);
    }

    @Override
    public void rotateSelf(double angle) {
        double offsetX = (p1.getX()+p2.getX())/2;
        double offsetY = (p1.getY()+p2.getY())/2;
        p1.shift(-offsetX, -offsetY);
        p2.shift(-offsetX, -offsetY);
        rotatePoints(angle);
        p1.shift(offsetX, offsetY);
        p2.shift(offsetX, offsetY);
    }

    private void rotatePoints(double angle){
        p1.rotate(angle);
        p2.rotate(angle);
    }

    @Override
    public void shift(Point p) {
        p1.shift(p.getX(),p.getY());
        p2.shift(p.getX(),p.getY());
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
        if(p1.compareTo(p2)==1)
            return p1;
        else return p2;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public Drawable morf(double t, Drawable d) {
        Segment s = (Segment) d;
//        Segment s2 = new Segment(p1.morf(t,s.p1), p2.morf(t,s.p2));
        Segment s2 = new Segment(p1.morf(t,s.p1), p2.morf(t,s.p2));
        s2.id = s.id;
        return s2;
    }

    @Override
    public Drawable morf(double t) {
        Segment segment = new Segment(this);
        segment.setOpacity((float)t);
        return  segment;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Segment segment = (Segment) o;

        return id == segment.id;

    }

    @Override
    public int hashCode() {
        return id;
    }
}
