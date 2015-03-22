package nsl.squarechat;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class SquareView extends View {

    public SquareView(Context context) {
        super(context);
        init();
    }

    public SquareView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SquareView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    ArrayList<Line> lines = new ArrayList<>();

    void init(){
        paint.setStrokeWidth(3.5f);
        paint.setFlags(Paint.ANTI_ALIAS_FLAG);
        paint.setAntiAlias(true);
        paint.setStrokeCap(Paint.Cap.ROUND);
    }

    public float Thickness = 2f;
    public int color = Color.BLACK;
    Paint paint = new Paint();
    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);

        for(Line l : lines){
            paint.setStrokeWidth(l.width);
            paint.setColor(l.color);
            for(int x = 0; x< l.points.size()-1;x++){
                PointF from = l.points.get(x);
                PointF to = l.points.get(x+1);
                canvas.drawLine(from.x , from.y , to.x , to.y , paint);
            }
        }
    }

    boolean down = false;
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN){
            down = true;
            lines.add(new Line(Thickness,color));
        }
        else if (event.getAction() == MotionEvent.ACTION_UP) {
            down = false;
        }

        if(down)lines.get(lines.size()-1).points.add(new PointF(event.getX(),event.getY()));

        //points.add(new PointF(event.getX(),event.getY()));


        invalidate();
        return true;
    }
}
