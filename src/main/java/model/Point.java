package model;

import java.io.Serializable;

public class Point implements Comparable<Point>,Serializable {

    private double x;
    private double y;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Point(Point p){
        x = p.x;
        y = p.y;
    }

    public void shift(double x, double y){
        this.x += x;
        this.y += y;
    }

    public double dist(Point p){
        return Math.sqrt(Math.pow(x-p.x,2)+Math.pow(y-p.y,2));
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void scale(double scale){
        x *= scale;
        y *= scale;
    }

    public void rotate(double angle){
        double newX = x*Math.cos(angle) - y*Math.sin(angle);
        double newY = x*Math.sin(angle) + y*Math.cos(angle);
        x=newX;
        y=newY;
    }

    @Override
    public int compareTo(Point o) {
        if(Math.hypot(x,y)>Math.hypot(o.x, o.y))
            return 1;
        else if(Math.hypot(x,y)==Math.hypot(o.x, o.y))
            return 0;
        else return -1;
    }

    public Point morf(double t,Point p){
        double nx = x*(1-t) + p.x*t;
        double ny = y*(1-t) + p.y*t;
        return new Point(nx,ny);
    }
}
