package nsl.squarechat;

import android.graphics.Color;
import android.graphics.PointF;

import java.util.ArrayList;

public class Line {
    ArrayList<PointF> points = new ArrayList<>();
    int color;
    float width = 1f;

    public Line(float t,int c){
        width = t;
        color = c;
    }

    public ArrayList<PointF> getPoints() {
        return points;
    }

    public void setPoints(ArrayList<PointF> points) {
        this.points = points;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }
}
