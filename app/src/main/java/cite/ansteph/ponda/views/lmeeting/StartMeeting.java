package cite.ansteph.ponda.views.lmeeting;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

import cite.ansteph.ponda.R;
import cite.ansteph.ponda.adapter.CustomClientListAdapter;
import cite.ansteph.ponda.adapter.CustomProjectListAdapter;
import cite.ansteph.ponda.api.ContentType;
import cite.ansteph.ponda.api.columns.ClientColumns;
import cite.ansteph.ponda.api.columns.MeetingColumns;
import cite.ansteph.ponda.api.columns.ProjectColumns;
import cite.ansteph.ponda.customview.Attendee_SubMeeting_Item;
import cite.ansteph.ponda.customview.Meeting_Item;
import cite.ansteph.ponda.customview.PaymentCert_MeetingItem;
import cite.ansteph.ponda.customview.VariousOrder_MeetingItem;
import cite.ansteph.ponda.model.Client;
import cite.ansteph.ponda.model.Meeting;
import cite.ansteph.ponda.model.MeetingItem;
import cite.ansteph.ponda.model.Project;
import cite.ansteph.ponda.template.MeetingTemplate;
import cite.ansteph.ponda.views.attendee.AttendeeList;
import cite.ansteph.ponda.views.client.ClientList;
import cite.ansteph.ponda.views.lmeeting.datetimepicker.RecordDatePickerFragment;
import cite.ansteph.ponda.views.lmeeting.datetimepicker.RecordTimePickerFragment;
import cite.ansteph.ponda.views.meeting.History;
import cite.ansteph.ponda.views.meeting.MeetingHistory;
import cite.ansteph.ponda.views.project.ProjectList;

public class StartMeeting extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    final static String TAG   = StartMeeting.class.getSimpleName();

    Spinner mSpinClient, mSpinProject;
    ArrayList<Client> mClientList;
    ArrayList<Project> mProjectList;
    ArrayList<MeetingItem> mMeetingItem;
    CustomClientListAdapter mClientAdapter;
    CustomProjectListAdapter mProjectAdapter;
    int selectedClientID, selectedProjectID;

    int mLastInserted;

    LinearLayout meetItemContainer;
    Meeting mMeetingAdd;
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

       // addAttMeetingItem();

        //load the spinner
        mSpinClient = (Spinner)findViewById(R.id.spclient);
        mClientList = retrieveClients();
        mClientAdapter = new CustomClientListAdapter(mClientList, this);
        mSpinClient.setAdapter(mClientAdapter);


        mSpinProject = (Spinner)findViewById(R.id.spproject);
        //mProjectList =setupList();// retrieveProjects();
        mProjectList = retrieveProjects();
        mProjectAdapter = new CustomProjectListAdapter(mProjectList, this);
        mSpinProject.setAdapter(mProjectAdapter);

        //end load the spinner

        mSpinClient.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Client selected = (Client) parent.getItemAtPosition(position);

                selectedClientID = selected.getId();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        mSpinProject.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Project selected = (Project) parent.getItemAtPosition(position);

                selectedProjectID = selected.getId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }


    ArrayList<Project> setupList()
    {
        ArrayList<Project>  arrayL = new ArrayList<>();

      // arrayL.add(new Project (1,1, "Tony Company","John Black"));
       //arrayL.add(new Project (2, 2,"Goodwill Construction","Pete Jameson"));
//        arrayL.add(new Project (3,3,"Turin Plet Arch","Jason Jack "));



        // String duration, String task_date, String start, String end, String project, String description, String realduration, String task_break) {
        return  arrayL;
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
        prepareSaving();
        insertNewMeeting(mMeetingAdd);

        getLastMeetingID();

        mMeetingAdd.setId(mLastInserted);

        addMeetItem(5);
    }


    void addAttMeetingItem()
    {
        Attendee_SubMeeting_Item attendee_subMeeting_item = new Attendee_SubMeeting_Item(this);
        VariousOrder_MeetingItem variousOrder_meetingItem = new VariousOrder_MeetingItem(this);
        PaymentCert_MeetingItem paymentCert_meetingItem = new PaymentCert_MeetingItem(this);
        meetItemContainer.addView(paymentCert_meetingItem);
        meetItemContainer.addView(variousOrder_meetingItem);
        meetItemContainer.addView(attendee_subMeeting_item);
    }

    private void addMeetItem(int count)
    {

        for(int i =0; i<MeetingTemplate.Template.length; i++)
        {
            LinearLayout meeting_item = null;

            int templateType= MeetingTemplate.TemplateTypeOrder[i];

            switch (templateType)
            {
                case MeetingTemplate.TemplateType.COMMON_ITEM : meeting_item = new Meeting_Item(this);
                    ((Meeting_Item)meeting_item).setMeetingItem(new MeetingItem(MeetingTemplate.Template[i],String.valueOf(i+1), mMeetingAdd));  break;

                case MeetingTemplate.TemplateType.ATTENDEE_ITEM :meeting_item = new Attendee_SubMeeting_Item(this) ;
                ((Attendee_SubMeeting_Item)meeting_item).setMeetingItem(new MeetingItem(MeetingTemplate.Template[i],String.valueOf(i+1), mMeetingAdd ));  break;


                case MeetingTemplate.TemplateType.PAYMENT_CERTIFICATE_ITEM :meeting_item = new PaymentCert_MeetingItem(this) ;
                ((PaymentCert_MeetingItem)meeting_item).setMeetingItem(new MeetingItem(MeetingTemplate.Template[i],String.valueOf(i+1), mMeetingAdd ));  break;

                case MeetingTemplate.TemplateType.VARIOUS_ORDER_ITEM : meeting_item = new VariousOrder_MeetingItem(this) ;
                ((VariousOrder_MeetingItem)meeting_item).setMeetingItem(new MeetingItem(MeetingTemplate.Template[i],String.valueOf(i+1), mMeetingAdd ));  break;

                default:meeting_item = new Meeting_Item(this);
                    ((Meeting_Item)meeting_item).setMeetingItem(new MeetingItem(MeetingTemplate.Template[i],String.valueOf(i+1), mMeetingAdd ));  break;


            }


            meetItemContainer.addView(meeting_item);



        }



    }

    private void prepareSaving() {

        mMeetingAdd = new Meeting();

        mMeetingAdd.setProjectManagersRef( ((EditText)findViewById(R.id.edtprojectmgrref)).getText().toString()  );
        mMeetingAdd.setClientId(selectedClientID);
        mMeetingAdd.setProjectId(selectedProjectID);
        mMeetingAdd.setSite( ((EditText)findViewById(R.id.edtsite)).getText().toString()  );
        mMeetingAdd.setMeetingDate( ((TextView)findViewById(R.id.txtstartdateday)).getText().toString()  );
        mMeetingAdd.setStartTime( ((TextView)findViewById(R.id.txtstartdatetime)).getText().toString()  );
        mMeetingAdd.setVenue( ((TextView)findViewById(R.id.edtVenue)).getText().toString()  );

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
            values.put(MeetingColumns.VENUE,aMeeting.getVenue()) ;

            getContentResolver().insert(ContentType.MEETING_CONTENT_URI, values);


            return 1;

        }catch (SQLException e)
        {
            e.printStackTrace();

            return 0;
        }
    }


    /*
     * get the last save Audit iD
     */

    public int getLastMeetingID(){

        String [] columns  = new String []{MeetingColumns._ID};
        ContentResolver resolver = getContentResolver();
        Cursor cursor = resolver.query(ContentType.MEETING_CONTENT_URI,columns,null,null, MeetingColumns._ID+" DESC LIMIT 1");

        int lastId =0;

        if(cursor !=null && cursor.moveToFirst())
        {
            lastId =(cursor.getString(0))!=null ? Integer.parseInt(cursor.getString(0)):0;
            mLastInserted = lastId;
            //mGlobalRetainer.get_grCurrentAudit().set_id(lastId);
            Log.d(TAG, String.valueOf(lastId) );
        }
        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }

        return lastId;
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

        if (id == R.id.nav_meet_hist) {
            // Handle the camera action
            startActivity(new Intent(getApplicationContext(), History.class));
        } else if (id == R.id.nav_new_meeting) {
         //   startActivity(new Intent(getApplicationContext(), StartMeeting.class));
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




    private ArrayList<Client> retrieveClients() {
        ContentResolver resolver = getContentResolver();

        Cursor cursor = resolver.query(ContentType.CLIENT_CONTENT_URI, ClientColumns.PROJECTION, null, null,null);
        ArrayList<Client> clients = new ArrayList<>();

        if(cursor.moveToFirst()){
            do{
                Client item = new Client(
                        ((cursor.getString(0))!=null ? Integer.parseInt(cursor.getString(0)):0),
                        (cursor.getString(cursor.getColumnIndex(ClientColumns.NAME))),
                        (cursor.getString(cursor.getColumnIndex(ClientColumns.WEBSITE_URL))),
                        (cursor.getString(cursor.getColumnIndex(ClientColumns.TELEPHONE))),
                        (cursor.getString(cursor.getColumnIndex(ClientColumns.CONTACT_PERSON))),
                        (cursor.getString(cursor.getColumnIndex(ClientColumns.CONTACT_PHONE))),
                        (cursor.getString(cursor.getColumnIndex(ClientColumns.EMAIL)))
                );

                clients.add(item);

            }while(cursor.moveToNext());
        }

        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }

        return clients;
    }


    private ArrayList<Project> retrieveProjects() {
        ContentResolver resolver = getContentResolver();

        Cursor cursor = resolver.query(ContentType.PROJECT_CONTENT_URI, ProjectColumns.PROJECTION, null, null,null);
        ArrayList<Project> projects = new ArrayList<>();

        if(cursor.moveToFirst()){
            do{
                Project item = new Project(
                        ((cursor.getString(0))!=null ? Integer.parseInt(cursor.getString(0)):0),
                        (cursor.getString(cursor.getColumnIndex(ProjectColumns.NAME))),
                        (cursor.getInt(cursor.getColumnIndex(ProjectColumns.CLIENT_ID))),
                        (cursor.getString(cursor.getColumnIndex(ProjectColumns.START_DATE))),
                        (cursor.getString(cursor.getColumnIndex(ProjectColumns.END_DATE))),
                        (cursor.getString(cursor.getColumnIndex(ProjectColumns.PROJ_MAN_NAME)))
                );

                projects.add(item);

            }while(cursor.moveToNext());
        }

        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }

        return projects;
    }

}
