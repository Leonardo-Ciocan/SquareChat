package nsl.squarechat;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

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
            convertView = View.inflate(getContext() , R.layout.conversation_item , parent);
        }

        TextView tv = (TextView) convertView.findViewById(R.id.friend);
        tv.setText(getItem(position).getUsername());



        return convertView;
    }
}
