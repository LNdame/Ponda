package cite.ansteph.ponda.views.lmeeting;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;

import cite.ansteph.ponda.R;
import cite.ansteph.ponda.customview.Attendee_SubMeeting_Item;
import cite.ansteph.ponda.customview.Meeting_Item;
import cite.ansteph.ponda.model.Meeting;
import cite.ansteph.ponda.views.lmeeting.datetimepicker.RecordDatePickerFragment;
import cite.ansteph.ponda.views.lmeeting.datetimepicker.RecordTimePickerFragment;

public class StartMeeting extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    LinearLayout meetItemContainer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_meeting);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        meetItemContainer = (LinearLayout) findViewById(R.id.meetingitem_container);

        addAttMeetingItem();

    }



    public void onStartDateClicked(View view)
    {
        DialogFragment nf = new RecordDatePickerFragment();
        nf.show(getSupportFragmentManager(), "Start Date");
    }

    public void onStartTimeClicked(View view)
    {
        DialogFragment nf = new RecordTimePickerFragment();
        nf.show(getSupportFragmentManager(),"Start Time");
    }

    public void onSaveClicked(View view)
    {
        addMeetItem(5);
    }


    void addAttMeetingItem()
    {
        Attendee_SubMeeting_Item attendee_subMeeting_item = new Attendee_SubMeeting_Item(this);
        meetItemContainer.addView(attendee_subMeeting_item);
    }

    private void addMeetItem(int count)
    {

        for(int i =0; i<count; i++)
        {
            Meeting_Item meeting_item = new  Meeting_Item(this);

            meetItemContainer.addView(meeting_item);



        }



    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.start_meeting, menu);
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
