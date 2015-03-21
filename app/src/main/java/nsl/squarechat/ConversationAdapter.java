package nsl.squarechat;

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

public class ConversationAdapter extends ArrayAdapter<ParseUser> {
    public ConversationAdapter(Context context, int resource, List<ParseUser> objects) {
        super(context, resource, objects);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = View.inflate(getContext() , R.layout.conversation_item , null);
        }

        TextView tv = (TextView) convertView.findViewById(R.id.friend);
        try {
            tv.setText(getItem(position).fetchIfNeeded().getUsername());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        final ImageView avatar = (ImageView) convertView.findViewById(R.id.avatar);
        ParseQuery<Avatar> query = new ParseQuery<Avatar>("Avatar");
        query.whereEqualTo("user" , getItem(position));
        query.include("user");

        query.findInBackground(new FindCallback<Avatar>() {
            @Override
            public void done(List<Avatar> avatars, ParseException e) {
                if(avatars.size()>0){
                    byte[] imageAsBytes = Base64.decode(avatars.get(0).getAvatar(), Base64.DEFAULT);

                    Bitmap b = BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length);
                    avatar.setImageBitmap(b);
                }
            }
        });

        return convertView;
    }
}
