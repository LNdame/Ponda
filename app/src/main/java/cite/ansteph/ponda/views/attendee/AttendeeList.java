package cite.ansteph.ponda.views.attendee;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;

import cite.ansteph.ponda.R;
import cite.ansteph.ponda.adapter.AttendeeRecyclerAdapter;
import cite.ansteph.ponda.api.ContentType;
import cite.ansteph.ponda.api.columns.AttendeeColumns;
import cite.ansteph.ponda.model.Attendee;
import cite.ansteph.ponda.views.client.ClientList;
import cite.ansteph.ponda.views.lmeeting.StartMeeting;
import cite.ansteph.ponda.views.meeting.MeetingHistory;
import cite.ansteph.ponda.views.project.ProjectList;

public class AttendeeList extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    RecyclerView attendeeRecyclerView;
    ArrayList<Attendee> mAttendeeList;
    AttendeeRecyclerAdapter mAttendeeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendee_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), EditAttendee.class));
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

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        attendeeRecyclerView = (RecyclerView) findViewById(R.id.recyclerview);

        mAttendeeList = retrieveAttendees();//setupList();// new ArrayList<>();

        mAttendeeAdapter  = new AttendeeRecyclerAdapter(mAttendeeList,  this);
        attendeeRecyclerView.setLayoutManager(mLayoutManager);
        attendeeRecyclerView.setAdapter(mAttendeeAdapter);
    }

    private ArrayList<Attendee> retrieveAttendees()
    {
        ContentResolver resolver = getContentResolver();

        Cursor cursor = resolver.query(ContentType.ATTENDEE_CONTENT_URI, AttendeeColumns.PROJECTION, null, null,null);
        ArrayList<Attendee> activities = new ArrayList<>();

        if(cursor.moveToFirst()){
            do{
                Attendee activity = new Attendee(
                        ((cursor.getString(0))!=null ? Integer.parseInt(cursor.getString(0)):0),
                        (cursor.getString(cursor.getColumnIndex(AttendeeColumns.FIRSTNAME))),
                        (cursor.getString(cursor.getColumnIndex(AttendeeColumns.SURNAME))),
                        (cursor.getString(cursor.getColumnIndex(AttendeeColumns.ORGANSATION))),
                        (cursor.getString(cursor.getColumnIndex(AttendeeColumns.TELEPHONE))),
                        (cursor.getString(cursor.getColumnIndex(AttendeeColumns.CELLPHONE))),
                        (cursor.getString(cursor.getColumnIndex(AttendeeColumns.FAX))),
                        (cursor.getString(cursor.getColumnIndex(AttendeeColumns.EMAIL)))
                );

                activities.add(activity);

            }while(cursor.moveToNext());
        }

        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }

        return activities;
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
        getMenuInflater().inflate(R.menu.attendee_list, menu);
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

        if (id == R.id.nav_meet_hist) {
            // Handle the camera action
            startActivity(new Intent(getApplicationContext(), MeetingHistory.class));
        } else if (id == R.id.nav_new_meeting) {
            startActivity(new Intent(getApplicationContext(), StartMeeting.class));
        } else if (id == R.id.nav_attendee) {
        //    startActivity(new Intent(getApplicationContext(), AttendeeList.class));
        } else if (id == R.id.nav_client) {
            startActivity(new Intent(getApplicationContext(), ClientList.class));
        } else if (id == R.id.nav_project) {
            startActivity(new Intent(getApplicationContext(), ProjectList.class));
        } else if (id == R.id.nav_share) {
            startActivity(new Intent(getApplicationContext(), StartMeeting.class));
        }else if (id == R.id.nav_about) {
            startActivity(new Intent(getApplicationContext(), StartMeeting.class));
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}