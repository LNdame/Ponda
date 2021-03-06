package cite.ansteph.ponda.views.meeting;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
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
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import cite.ansteph.ponda.R;
import cite.ansteph.ponda.adapter.CustomClientListAdapter;
import cite.ansteph.ponda.adapter.CustomProjectListAdapter;
import cite.ansteph.ponda.adapter.MeetingItemRecyclerAdapter;
import cite.ansteph.ponda.api.ContentType;
import cite.ansteph.ponda.api.columns.ClientColumns;
import cite.ansteph.ponda.api.columns.MeetingColumns;
import cite.ansteph.ponda.api.columns.ProjectColumns;
import cite.ansteph.ponda.model.Client;
import cite.ansteph.ponda.model.Meeting;
import cite.ansteph.ponda.model.MeetingItem;
import cite.ansteph.ponda.model.Project;
import cite.ansteph.ponda.views.attendee.AttendeeList;
import cite.ansteph.ponda.views.client.ClientList;
import cite.ansteph.ponda.views.lmeeting.StartMeeting;
import cite.ansteph.ponda.views.project.ProjectList;

public class NewMeeting extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    int mYear, mMonth, mDay;
    String selectedMonth, selectedDay;
    Spinner clientSpinner, projectSpinner;
    ArrayList<Client> mClientList;
    ArrayList<Project> mProjectList;
    ArrayList<MeetingItem> mMeetingItem;
    CustomClientListAdapter mClientAdapter;
    CustomProjectListAdapter mProjectAdapter;
    LinearLayout cNewMeeting, cMeetingFragment, popAttendance;
    RecyclerView meetingItemRecyclerView;
    MeetingItemRecyclerAdapter mMeetingItemAdapter;
    Integer selectedClient, selectedProject;
    Context mContext;
    DrawerLayout drawerLayout;


    Meeting mMeetingAdd;
    TextView tvStartTime, tvDate;
    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_meeting);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
       // setSupportActionBar(toolbar);


        Bundle bundle = getIntent().getExtras();

//        if(bundle!= null)
//        {
//            //mMeetingAdd =(Meeting) bundle.getSerializable("meeting");
////            fillFields();
//        }else{
            mMeetingAdd = new Meeting();
        //}

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //saving the current client
                //step 1 one get the values
                prepareSaving();

                //step 2 insert or update : if getId !=0 null update else insert

//                if(mMeetingAdd.getId()!=0) {
//                    int done =  updateClient(mMeetingAdd);
//                    if(done==1)
//                        startActivity(new Intent(getApplicationContext(), MeetingList.class));
//                }else{
                    int done =  insertNewMeeting(mMeetingAdd);
                    if(done==1)
                    {
                        cMeetingFragment.setVisibility(View.VISIBLE);
                        cNewMeeting.setVisibility(View.INVISIBLE);


                    }
                    else{
                        Snackbar.make(view, "Not added", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }
//                        startActivity(new Intent(getApplicationContext(), MeetingList.class));
                //}
            }
        });

//        int selected_background = android.R.color.holo_green_dark;
////                editor.putInt("background_resource", selected_background);
////                editor.apply();
//
//        backgroundSet(selected_background);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        tvDate = (TextView) findViewById(R.id.tvdate);

        tvStartTime = (TextView) findViewById(R.id.tvstarttime);
        cNewMeeting = (LinearLayout) findViewById(R.id.cNewMeeting);
        cMeetingFragment = (LinearLayout) findViewById(R.id.cMeetingFragment);



        Date now = new Date();
        SimpleDateFormat dateFormatter, timeFormatter;

        timeFormatter = new SimpleDateFormat("HH:mm");
        dateFormatter = new SimpleDateFormat("MM-dd-yyyy");

        tvDate.setText(dateFormatter.format(now));
        tvStartTime.setText(timeFormatter.format(now));


        tvDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO Auto-generated method stub
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

// Launch Date Picker Dialog
                DatePickerDialog dpd = new DatePickerDialog(NewMeeting.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // Display Selected date in textbox

                                if (year < mYear)
                                    view.updateDate(mYear,mMonth,mDay);

                                if (monthOfYear < mMonth && year == mYear)
                                    view.updateDate(mYear,mMonth,mDay);

                                if (dayOfMonth < mDay && year == mYear && monthOfYear == mMonth)
                                    view.updateDate(mYear,mMonth,mDay);

//                                if(dayOfMonth < 10)
                                {
                                    selectedDay = "0" + dayOfMonth;
                                }
//                                if(monthOfYear < 10){
//                                    selectedMonth = "0" + monthOfYear;
//                                }
                                SimpleDateFormat dateFormatter;
                                dateFormatter = new SimpleDateFormat("MM-dd-yyyy");

                                Calendar calander = Calendar.getInstance();
                                calander.setTimeInMillis(0);
                                calander.set(year, monthOfYear, dayOfMonth, 0, 0, 0);

                                tvDate.setText(dateFormatter.format(calander.getTime()));
//                                SimpleDateFormat monthFormat;
//                                monthFormat = new SimpleDateFormat("mm");
////                                tvDate.setText(selectedDay + "-"
//                                        + monthFormat.format(selectedMonth + 1) + "-" + year);

                            }
                        }, mYear, mMonth, mDay);
                dpd.getDatePicker().setMinDate(System.currentTimeMillis());
                dpd.show();
            }
        });

        tvStartTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //DialogFragment newFragment = new TimePickerFragment();
               // newFragment.show(getFragmentManager(),"TimePicker");

                // TODO Auto-generated method stub
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(NewMeeting.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {

                        String hours, minutes;
                        if(hourOfDay < 10){
                            hours = "0" + hourOfDay;
                        }
                        else{
                            hours = String.valueOf(hourOfDay);
                        }
                        if(minute < 10){
                            minutes = "0" + minute;
                        }
                        else{
                            minutes = String.valueOf(minute);
                        }
                        tvStartTime.setText( hours + ":" + minutes);
                    }
                }, hour, minute, true);//Yes 24 hour time
//                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });


        clientSpinner = (Spinner) findViewById(R.id.spclient);
        mClientList = populateList();
        mClientAdapter = new CustomClientListAdapter(mClientList, this);
        clientSpinner.setAdapter(mClientAdapter);

        clientSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                // TODO Auto-generated method stub
                Client selected = (Client) parent.getItemAtPosition(position);

                selectedClient = selected.getId();

                //PositionDisplay.setText("Item Position is = " + Hold );

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub

            }
        });


        projectSpinner = (Spinner) findViewById(R.id.spproject);
        mProjectList = populateProjectList();
        mProjectAdapter = new CustomProjectListAdapter(mProjectList, this);
        projectSpinner.setAdapter(mProjectAdapter);

        projectSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                // TODO Auto-generated method stub
                Project selected = (Project) parent.getItemAtPosition(position);
                selectedProject = selected.getId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub

            }
        });

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        meetingItemRecyclerView = (RecyclerView) findViewById(R.id.recyclerview);

        mMeetingItem= populateItems();
        mMeetingItemAdapter = new MeetingItemRecyclerAdapter(mMeetingItem, this);
        meetingItemRecyclerView.setLayoutManager(mLayoutManager);
        meetingItemRecyclerView.setAdapter(mMeetingItemAdapter);






    }

//    private void backgroundSet(int selected_background) {
//        SharedPreferences sharedPref = getSharedPreferences("my_prefs", NewMeeting.MODE_PRIVATE);
//        SharedPreferences.Editor editor = sharedPref.edit();
//        editor.putInt("background_resource", selected_background);
//        editor.commit();
//    }


    private ArrayList<Project> populateProjectList() {
        ContentResolver resolver = getContentResolver();

        Cursor cursor = resolver.query(ContentType.PROJECT_CONTENT_URI, ProjectColumns.PROJECTION, null, null,null);
        ArrayList<Project> activities = new ArrayList<>();


        if(cursor.moveToFirst()){
            do{
                Project activity = new Project(
                        ((cursor.getString(0))!=null ? Integer.parseInt(cursor.getString(0)):0),
                        (cursor.getString(cursor.getColumnIndex(ProjectColumns.NAME))),
                        (cursor.getInt(cursor.getColumnIndex(ProjectColumns.CLIENT_ID))),
                        (cursor.getString(cursor.getColumnIndex(ProjectColumns.START_DATE))),
                        (cursor.getString(cursor.getColumnIndex(ProjectColumns.END_DATE))),
                        (cursor.getString(cursor.getColumnIndex(ProjectColumns.PROJ_MAN_NAME)))
                        //(cursor.getString(cursor.getColumnIndex(ClientColumns.EMAIL)))
                );

                activities.add(activity);

            }while(cursor.moveToNext());
        }



        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }

        return activities;
    }

    private ArrayList<MeetingItem> populateItems() {
        ArrayList<MeetingItem>  items = new ArrayList<>();


        items.add(new MeetingItem(1,1,"Attendance Register"));
        items.add(new MeetingItem(2,2,"Apologies"));
        items.add(new MeetingItem(3,3,"Minutes of Previous Meeting"));
        items.add(new MeetingItem(4,4,"Matters Arising"));
        items.add(new MeetingItem(5,5,"Contract Details"));
        items.add(new MeetingItem(6,6,"Programme"));
        items.add(new MeetingItem(7,7,"Delays"));
        items.add(new MeetingItem(8,8,"Cash Flow"));
        items.add(new MeetingItem(9,9,"Payment Certificates"));
        items.add(new MeetingItem(10,10,"Progress"));
        return  items;
    }

    private void prepareSaving() {
        //retrieving the corresponding value
//        //Spinner clientSpinner = (Spinner)findViewById(R.id.spclient);
//        String client = String.valueOf(clientSpinner);
//
//        //Spinner projSpinner = (Spinner)findViewById(R.id.spproject);
//        projectSpinner.getSelectedItem().toString();
//        String project = String.valueOf(projectSpinner);

        mMeetingAdd.setProjectManagersRef( ((EditText)findViewById(R.id.edtprojectmgrref)).getText().toString()  );
        mMeetingAdd.setClientId(selectedClient);
        mMeetingAdd.setProjectId(selectedProject);
        mMeetingAdd.setSite( ((EditText)findViewById(R.id.edtsite)).getText().toString()  );
        mMeetingAdd.setMeetingDate( ((TextView)findViewById(R.id.tvdate)).getText().toString()  );
        mMeetingAdd.setStartTime( ((TextView)findViewById(R.id.tvstarttime)).getText().toString()  );

    }

    private void fillFields() {
//        ((Spinner)findViewById(R.id.spclient)).setText(mMeetingAdd.getProjectManagersRef());
//        ((Spinner)findViewById(R.id.spproject)).setText(mMeetingAdd.getSite());
//        ((EditText)findViewById(R.id.edtprojectmgrref)).setText(mMeetingAdd.getProjectManagersRef());
//        ((EditText)findViewById(R.id.edtsite)).setText(mMeetingAdd.getSite());
//        ((TextView)findViewById(R.id.tvdate)).setText(mMeetingAdd.getMeetingDate());
//        ((TextView)findViewById(R.id.tvstarttime)).setText(mMeetingAdd.getStartTime());

    }

    private int insertNewMeeting(Meeting aMeeting) {
        try {
            ContentValues values = new ContentValues();

            values.put(MeetingColumns.PROJECT_MANAGER_REF,aMeeting.getProjectManagersRef()) ;
            values.put(MeetingColumns.CLIENT_ID,aMeeting.getClientId()) ;
            values.put(MeetingColumns.PROJECT_ID ,aMeeting.getProjectId()) ;
            values.put(MeetingColumns.SITE,aMeeting.getSite()) ;
            values.put(MeetingColumns.START_DATE,aMeeting.getMeetingDate()) ;
            values.put(MeetingColumns.START_TIME,aMeeting.getStartTime()) ;

            getContentResolver().insert(ContentType.MEETING_CONTENT_URI, values);


            return 1;

        }catch (SQLException e)
        {
            e.printStackTrace();

            return 0;
        }
    }


//    private int updateClient(Meeting aMeeting) {
//        String client_id = String.valueOf( aMeeting.getId());
//
////        try {
////            ContentValues values = new ContentValues();
////
////            values.put(MeetingColumns.NAME,aMeeting.getName()) ;
////            values.put(MeetingColumns.WEBSITE_URL ,aMeeting.getWebsiteUrl()) ;
////            values.put(MeetingColumns.TELEPHONE,aMeeting.getTelephone()) ;
////            values.put(MeetingColumns.CONTACT_PERSON,aMeeting.getContactPerson()) ;
////            values.put(MeetingColumns.CONTACT_PHONE,aMeeting.getContactPhone()) ;
////            values.put(MeetingColumns.EMAIL,aMeeting.getEmail()) ;
////
////
////            getContentResolver().update(ContentType.CLIENT_CONTENT_URI, values, MeetingColumns._ID+" =?", new String[]{client_id});
////
////
////            return 1;
////
////        }catch (SQLException e)
////        {
////            e.printStackTrace();
////
////            return 0;
////        }
   // }

    private ArrayList<Client> populateList() {
        ContentResolver resolver = getContentResolver();

        Cursor cursor = resolver.query(ContentType.CLIENT_CONTENT_URI, ClientColumns.PROJECTION, null, null,null);
        ArrayList<Client> activities = new ArrayList<>();

        if(cursor.moveToFirst()){
            do{
                Client activity = new Client(
                        ((cursor.getString(0))!=null ? Integer.parseInt(cursor.getString(0)):0),
                        (cursor.getString(cursor.getColumnIndex(ClientColumns.NAME)))
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
        getMenuInflater().inflate(R.menu.new_meeting, menu);
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
}