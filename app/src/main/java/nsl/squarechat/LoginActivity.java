package nsl.squarechat;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import java.io.ByteArrayOutputStream;


public class LoginActivity extends ActionBarActivity {

    private EditText name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Enable Local Datastore.
        ParseObject.registerSubclass(Message.class);
        ParseObject.registerSubclass(Avatar.class);
        Parse.enableLocalDatastore(this);
        Parse.initialize(this, "HnSKRXnV2GNcrchc8QF3tSqs42rNMJRl4SRoCbch",
                "xmIe34IZjp2mbWZ3QZySfoYd5F2KB1OYvT1QLszy");


        final EditText username = (EditText) findViewById(R.id.username);
        final EditText password = (EditText) findViewById(R.id.password);


        if(ParseUser.getCurrentUser()!=null){
            Intent i = new Intent(LoginActivity.this , ConversationsActivity.class);
            startActivity(i);
        }
        Button login = (Button) findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParseUser.logInInBackground(username.getText().toString(), password.getText().toString()
                        , new LogInCallback() {
                    public void done(ParseUser user, com.parse.ParseException e) {
                        if (user != null) {
                            Intent i = new Intent(LoginActivity.this , ConversationsActivity.class);
                            startActivity(i);
                        } else {
                            Toast.makeText(getApplicationContext(),
                                    "There was an error logging in.",
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });

        final SquareView squareView = (SquareView) findViewById(R.id.avatar);
        Button signup = (Button) findViewById(R.id.signup);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(squareView.getVisibility() == View.VISIBLE){
                    final ParseUser user = new ParseUser();
                    user.setUsername(username.getText().toString());
                    user.setPassword(password.getText().toString());

                    user.signUpInBackground(new SignUpCallback() {
                        public void done(com.parse.ParseException e) {
                            if (e == null) {

                                Avatar avatar = new Avatar();
                                Bitmap b = Bitmap.createBitmap(
                                        squareView.getLayoutParams().width,
                                        squareView.getLayoutParams().height,
                                        Bitmap.Config.ARGB_8888);
                                Canvas c = new Canvas(b);
                                squareView.draw(c);
                                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                                b.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                                byte[] byteArray = byteArrayOutputStream.toByteArray();

                                String encoded = Base64.encodeToString(byteArray, Base64.DEFAULT);
                                avatar.setAvatar(encoded);
                                avatar.setUser(user);
                                avatar.saveInBackground();
                                Intent i = new Intent(LoginActivity.this , ConversationsActivity.class);
                                startActivity(i);
                            } else {
                                Toast.makeText(getApplicationContext(),
                                        "There was an error signing up."
                                        , Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
                else {
                    squareView.setVisibility(View.VISIBLE);
                    TextView  labelavatar = (TextView) findViewById(R.id.labelAvatar);
                    labelavatar.setVisibility(View.VISIBLE);
                }
            }
        });


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
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
