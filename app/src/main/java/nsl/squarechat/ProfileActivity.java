package nsl.squarechat;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;


public class ProfileActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        final TextView name = (TextView) findViewById(R.id.name);
        name.setText("Say hi to "+Core.ChattingTo.getUsername());

        final ImageView favorite = (ImageView) findViewById(R.id.favorite);
        final ImageView avatar = (ImageView) findViewById(R.id.avatar);
        ParseQuery<Avatar> innerQuery = new ParseQuery<Avatar>("Avatar");
        innerQuery.whereEqualTo("user", Core.ChattingTo);
        innerQuery.include("user");

        innerQuery.findInBackground(new FindCallback<Avatar>() {
            @Override
            public void done(List<Avatar> parseUsers, com.parse.ParseException e) {
                if (parseUsers.size() == 0) {
                    Toast.makeText(getApplicationContext(),
                            "Couldn't find user"
                            , Toast.LENGTH_LONG).show();
                } else {
                    byte [] encodeByte= Base64.decode(parseUsers.get(0).getFavorite(), Base64.DEFAULT);
                    Bitmap bitmap= BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
                    favorite.setImageBitmap(bitmap);

                    byte [] encodeByte2 = Base64.decode(parseUsers.get(0).getAvatar(), Base64.DEFAULT);
                    Bitmap bitmap2= BitmapFactory.decodeByteArray(encodeByte2, 0, encodeByte2.length);
                    avatar.setImageBitmap(bitmap2);
                }
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_profile, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
