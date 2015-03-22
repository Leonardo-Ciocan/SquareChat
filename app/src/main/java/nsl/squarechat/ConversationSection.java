package nsl.squarechat;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.util.Base64;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.io.ByteArrayOutputStream;

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
    void init(final Bitmap img , int side){
        View view = inflate(getContext(),R.layout.section,this);
        ImageView imgView = (ImageView) view.findViewById(R.id.img);
        imgView.setImageBitmap(img);
        CardView imgHolder = (CardView) view.findViewById(R.id.imgHolder);
        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams)imgHolder.getLayoutParams();
        params.gravity = Gravity.CENTER_VERTICAL | ((side == ME) ? Gravity.RIGHT : Gravity.LEFT);
        setOnLongClickListener(new OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setPositiveButton("Make emoji",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                        img.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                        byte[] byteArray = byteArrayOutputStream.toByteArray();

                        String encoded = Base64.encodeToString(byteArray, Base64.DEFAULT);
                        int count = MainActivity.sharedPref.getInt("emojiCount",0);
                        MainActivity.sharedPref.edit().putString("emoji" +count,encoded).commit();
                        MainActivity.sharedPref.edit().putInt("emojiCount" ,count+1).commit();

                    }
                });
                builder.create().show();
                return false;
            }
        });
    }

}
