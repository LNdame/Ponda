package cite.ansteph.ponda.views.meeting;

import android.support.v7.widget.RecyclerView;
import android.support.v7.app.AppCompatActivity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import java.util.ArrayList;
import cite.ansteph.ponda.R;
import cite.ansteph.ponda.adapter.MeetingHistoryRecyclerAdapter;
import cite.ansteph.ponda.api.ContentType;
import cite.ansteph.ponda.api.columns.MeetingColumns;
import cite.ansteph.ponda.views.attendee.AttendeeList;
import cite.ansteph.ponda.views.client.ClientList;
import cite.ansteph.ponda.views.lmeeting.StartMeeting;
import cite.ansteph.ponda.views.project.ProjectList;

import static cite.ansteph.ponda.R.layout.activity_meeting_history;

public class MeetingHistory extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    RecyclerView meetingHistoryRecyclerView;
    ArrayList<MeetingHistory> meetingsList;
    MeetingHistoryRecyclerAdapter meetingHistoryRecyclerAdapter;

    public MeetingHistory(int mHistoryID, int clientId, int projectId, String projManagerRef) {
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(activity_meeting_history);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), NewMeeting.class));
                Snackbar.make(view, "No meeting history", Snackbar.LENGTH_LONG)
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


        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        meetingHistoryRecyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        meetingsList = retrieveMeetings();

        //meetingHistoryRecyclerAdapter = new MeetingHistoryRecyclerAdapter(meetingsList, this);
        meetingHistoryRecyclerView.setLayoutManager(layoutManager);
        meetingHistoryRecyclerView.setAdapter(meetingHistoryRecyclerAdapter);
    }

    //delete after use
    ArrayList<MeetingHistory> setupList()
    {
        ArrayList<MeetingHistory>  arrayL = new ArrayList<>();

//        arrayL.add(new MeetingHistory (1,"Tony Company","John Black"));
//        arrayL.add(new MeetingHistory (2,"Goodwill Construction","Pete Jameson"));
//        arrayL.add(new MeetingHistory (3,"Turin Plet Arch","Jason Jack "));



        // String duration, String task_date, String start, String end, String project, String description, String realduration, String task_break) {
        return  arrayL;
    }


    private ArrayList<MeetingHistory> retrieveMeetings()
    {
        ContentResolver resolver = getContentResolver();

        Cursor cursor = resolver.query(ContentType.MEETING_HISTORY_CONTENT_URI, MeetingColumns.PROJECTION, null, null,null);
        ArrayList<MeetingHistory> activities = new ArrayList<>();

        cursor = resolver.query(ContentType.MEETING_CONTENT_URI, MeetingColumns.PROJECTION, null, null, null);
        activities = new ArrayList<>();

        if(cursor.moveToFirst()){
            do{
                MeetingHistory activity = new MeetingHistory(
                        ((cursor.getString(0))!=null ? Integer.parseInt(cursor.getString(0)):0),
                        (cursor.getInt(cursor.getColumnIndex(MeetingColumns.CLIENT_ID))),
                        (cursor.getInt(cursor.getColumnIndex(MeetingColumns.PROJECT_ID))),
                        (cursor.getString(cursor.getColumnIndex(MeetingColumns.PROJECT_MANAGER_REF)))



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
        getMenuInflater().inflate(R.menu.meeting_history, menu);
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
         //   startActivity(new Intent(getApplicationContext(), MeetingHistory.class));
        } else if (id == R.id.nav_new_meeting) {
            startActivity(new Intent(getApplicationContext(), StartMeeting.class));
        } else if (id == R.id.nav_attendee) {
            startActivity(new Intent(getApplicationContext(), AttendeeList.class));
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


    public int projectManagersRef() {
        return projectManagersRef();
    }

//    public String getContactPerson() {
//        return contactPerson();
//    }

    private String contactPerson() {
        return contactPerson();
    }
}
