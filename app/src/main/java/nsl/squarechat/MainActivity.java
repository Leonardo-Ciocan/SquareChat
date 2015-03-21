package nsl.squarechat;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.SeekBar;

import com.melnykov.fab.FloatingActionButton;
import com.parse.Parse;
import com.r0adkll.slidr.Slidr;

import java.util.logging.Handler;


public class MainActivity extends ActionBarActivity {

    private ActionBarDrawerToggle toggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Slidr.attach(this);


        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        toolbar.setTitle("Chatting with X");

        setSupportActionBar(toolbar);
        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        toggle = new ActionBarDrawerToggle(MainActivity.this, drawerLayout, R.string.abc_action_mode_done, R.string.abc_action_mode_done) {
            @Override
            public void onDrawerClosed(View drawerView) {

                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };
        toggle.setDrawerIndicatorEnabled(true);
        drawerLayout.setDrawerListener(toggle);
        
        final SquareView currentSquare = (SquareView) findViewById(R.id.currentSquare);

        final LinearLayout holder = (LinearLayout) findViewById(R.id.holder);
        final FloatingActionButton send = (FloatingActionButton) findViewById(R.id.send);

        SeekBar thickness = (SeekBar) findViewById(R.id.thickness);
        thickness.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                currentSquare.Thickness = progress / 100.0f * 6f;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bitmap b = Bitmap.createBitmap(
                        currentSquare.getLayoutParams().width,
                        currentSquare.getLayoutParams().height,
                        Bitmap.Config.ARGB_8888);
                Canvas c = new Canvas(b);
                //v.layout(v.getLeft(), v.getTop(), v.getRight(), v.getBottom());
                currentSquare.draw(c);
                ConversationSection section = new ConversationSection(MainActivity.this);
                section.init(b,ConversationSection.ME);

                holder.addView(section);
                Thread thread = new Thread() {
                    @Override
                    public void run() {
                        try {
                            sleep(100);
                            ((ScrollView) holder.getParent()).fullScroll(ScrollView.FOCUS_DOWN);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                };

                thread.start();
                    currentSquare.lines.clear();
                    currentSquare.invalidate();
                }
            });

        LinearLayout colors = (LinearLayout) findViewById(R.id.colorHolder);
        for(int x =0; x< colors.getChildCount();x++){
            colors.getChildAt(x).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    currentSquare.color =((ColorDrawable) v.getBackground()).getColor();
                }
            });
        }
        onPostCreate(null);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (toggle.onOptionsItemSelected(item))
            return true;
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        if(toggle!=null)toggle.syncState();
    }
}
