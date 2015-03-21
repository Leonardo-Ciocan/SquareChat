package nsl.squarechat;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.text.ParseException;
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
            View rootView = inflater.inflate(R.layout.fragment_conversations, container, false);
            toolbar = (Toolbar) rootView.findViewById(R.id.toolbar);
            ((ActionBarActivity)getActivity()).setSupportActionBar(toolbar);
            return rootView;
        }
    }
}
