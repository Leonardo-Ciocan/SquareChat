package nsl.squarechat;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.getpebble.android.kit.PebbleKit;
import com.parse.FindCallback;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;


public class ConversationsActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversations);
        ConversationsActivity.PlaceholderFragment fragment  = new PlaceholderFragment();
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, fragment)
                    .commit();
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_conversations, menu);
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

        if(id == R.id.add){
            final EditText name = new EditText(this);

            name.setHint("Username");

            new AlertDialog.Builder(this)
                    .setTitle("Who do you want to chat with?")
                    .setView(name)
                    .setPositiveButton("Square it", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            ParseQuery<ParseUser> innerQuery = ParseUser.getQuery();
                            innerQuery.whereEqualTo("username", name.getText().toString());

                            //ParseQuery query = new ParseQuery("ClassA");
                            //query.whereMatchesQuery("pointer-column-to-classB", innerQuery);
                            innerQuery.findInBackground(new FindCallback<ParseUser>() {
                                @Override
                                public void done(List<ParseUser> parseUsers, com.parse.ParseException e) {
                                    if (parseUsers.size() == 0) {
                                        Toast.makeText(getApplicationContext(),
                                                "Couldn't find user"
                                                , Toast.LENGTH_LONG).show();
                                    } else {
                                        Core.ChattingTo = parseUsers.get(0);
                                        Intent i = new Intent(ConversationsActivity.this , MainActivity.class);
                                        startActivity(i);
                                    }
                                }
                            });
                        }
                    })
                    .setNegativeButton("Cancel", null)
                    .show();
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        public Toolbar toolbar;
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            final View rootView = inflater.inflate(R.layout.fragment_conversations, container, false);
            toolbar = (Toolbar) rootView.findViewById(R.id.toolbar);
            ((ActionBarActivity)getActivity()).setSupportActionBar(toolbar);


            ParseQuery<Message> query = new ParseQuery<Message>("Message");
            query.whereEqualTo("from",ParseUser.getCurrentUser());

            ParseQuery<Message> query2 = new ParseQuery<Message>("Message");
            query2.whereEqualTo("to",ParseUser.getCurrentUser());

            ArrayList<ParseQuery<Message>> queries = new ArrayList<>();
            queries.add(query);
            queries.add(query2);
            ParseQuery<Message> joint = ParseQuery.or(queries);
            joint.include("pointer to _user table");

            final ArrayList<ParseUser> chattingTo = new ArrayList<>();

            final ConversationAdapter adapter = new ConversationAdapter(getActivity() , R.layout.conversation_item, chattingTo);

            joint.findInBackground(new FindCallback<Message>() {
                @Override
                public void done(List<Message> messages, com.parse.ParseException e) {
                    if(messages == null)return;
                    Message lastMessage = null;
                    for(Message m : messages){
                        if(!m.getFrom().getObjectId().equals(ParseUser.getCurrentUser().getObjectId()))
                            if(!chattingTo.contains(m.getFrom())) {
                               lastMessage = m;
                                chattingTo.add(m.getFrom());
                            }
                        if(!m.getTo().getObjectId().equals(ParseUser.getCurrentUser().getObjectId()))
                            if(!chattingTo.contains(m.getTo())){

                                chattingTo.add(m.getTo());

                            }

                        adapter.notifyDataSetChanged();
                   }
                    notification(lastMessage,rootView);
                }
            });

            ListView ls = (ListView) rootView.findViewById(R.id.conversations);
            ls.setAdapter(adapter);

            ls.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent i = new Intent(getActivity() , MainActivity.class);
                    Core.ChattingTo = adapter.getItem(position);
                    startActivity(i);
                }
            });

            return rootView;
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static void notification(Message m, View view)    {
        NotificationManager mNotificationManager = (NotificationManager) view.getContext().getSystemService(Context.NOTIFICATION_SERVICE);
// Sets an ID for the notification, so it can be updated
        int notifyID = 1;
        NotificationCompat.Builder mNotifyBuilder = new NotificationCompat.Builder(view.getContext())
                .setContentTitle("New Message")
                .setContentText("You've received new messages.");
        // Because the ID remains unchanged, the existing notification is
        // updated.
        mNotificationManager.notify(
                notifyID,
                mNotifyBuilder.build());
    }
    public static Bitmap StringToBitMap(String encodedString){
        try{
            byte [] encodeByte= Base64.decode(encodedString, Base64.DEFAULT);
            Bitmap bitmap= BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        }catch(Exception e){
            e.getMessage();
            return null;
        }
    }
}
