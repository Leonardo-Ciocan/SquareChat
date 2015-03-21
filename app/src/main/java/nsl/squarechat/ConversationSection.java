package nsl.squarechat;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class ConversationSection extends RelativeLayout {
    public ConversationSection(Context context) {
            super(context);
            }

    public ConversationSection(Context context, AttributeSet attrs) {
            super(context, attrs);
            }

    public ConversationSection(Context context, AttributeSet attrs, int defStyleAttr) {
            super(context, attrs, defStyleAttr);
            }

    public static final int ME = 0;
    public static final int OTHER = 1;
    void init(Bitmap img , int side){
        View view = inflate(getContext(),R.layout.section,this);
        ImageView imgView = (ImageView) view.findViewById(R.id.img);
        imgView.setImageBitmap(img);
        CardView imgHolder = (CardView) view.findViewById(R.id.imgHolder);
        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams)imgHolder.getLayoutParams();
        params.gravity = Gravity.CENTER_VERTICAL | ((side == ME) ? Gravity.RIGHT : Gravity.LEFT);

    }

}
