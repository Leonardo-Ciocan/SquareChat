package nsl.squarechat;

import android.app.ActionBar;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class EmojiAdapter extends ArrayAdapter<String> {
    public EmojiAdapter(Context context, int resource, List<String> objects) {
        super(context, resource, objects);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = View.inflate(getContext() , R.layout.emoji_item , null);
            //convertView = new ImageView(getContext());
        }


        String img = MainActivity.sharedPref.getString(getItem(position), "");
        byte[] imageAsBytes = Base64.decode(img.getBytes() , Base64.DEFAULT);

        Bitmap b = BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length);
        ((ImageView)convertView.findViewById(R.id.emoji)).setImageBitmap(b);
        return convertView;
    }
}
