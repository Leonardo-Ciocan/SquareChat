package nsl.squarechat;

import android.graphics.Color;
import android.graphics.PointF;

import java.util.ArrayList;

public class Line {
    ArrayList<PointF> points = new ArrayList<>();
    Color color;
    float width;

    public ArrayList<PointF> getPoints() {
        return points;
    }

    public void setPoints(ArrayList<PointF> points) {
        this.points = points;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }
}
