package cite.ansteph.ponda.views.lmeeting;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
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
import cite.ansteph.ponda.api.columns.ApologyColumns;
import cite.ansteph.ponda.api.columns.AttendanceColumns;
import cite.ansteph.ponda.api.columns.AttendeeColumns;
import cite.ansteph.ponda.api.columns.ClientColumns;
import cite.ansteph.ponda.api.columns.MeetingColumns;
import cite.ansteph.ponda.api.columns.MeetingItemColumns;
import cite.ansteph.ponda.api.columns.MeetingSubItemColumns;
import cite.ansteph.ponda.api.columns.PaymentCertificateColumns;
import cite.ansteph.ponda.api.columns.ProjectColumns;
import cite.ansteph.ponda.api.columns.VariationOrderColumns;
import cite.ansteph.ponda.customview.Attendee_SubMeeting_Item;
import cite.ansteph.ponda.customview.Meeting_Item;
import cite.ansteph.ponda.customview.PaymentCert_MeetingItem;
import cite.ansteph.ponda.customview.VariousOrder_MeetingItem;
import cite.ansteph.ponda.model.Attendee;
import cite.ansteph.ponda.model.Client;
import cite.ansteph.ponda.model.Meeting;
import cite.ansteph.ponda.model.MeetingItem;
import cite.ansteph.ponda.model.MeetingSubItem;
import cite.ansteph.ponda.model.PaymentCertificate;
import cite.ansteph.ponda.model.Project;
import cite.ansteph.ponda.model.VariationOrder;
import cite.ansteph.ponda.template.MeetingTemplate;
import cite.ansteph.ponda.views.lmeeting.datetimepicker.RecordDatePickerFragment;
import cite.ansteph.ponda.views.lmeeting.datetimepicker.RecordTimePickerFragment;

public class ViewMeetingHistory extends AppCompatActivity {


    final static String TAG   = StartMeeting.class.getSimpleName();

    Spinner mSpinClient, mSpinProject;
    ArrayList<Client> mClientList;
    ArrayList<Attendee> mAttendeeList;
    ArrayList<Project> mProjectList;

    ArrayList<PaymentCertificate> mPaymentCertificates;
    ArrayList<VariationOrder> mVariationOrders;


    ArrayList<MeetingItem> mMeetingItem;
    CustomClientListAdapter mClientAdapter;
    CustomProjectListAdapter mProjectAdapter;
    int selectedClientID, selectedProjectID;

    int mLastInserted;

    LinearLayout meetItemContainer;
    Meeting mCurrentMeetingAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_meeting_history);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        Bundle bundle = getIntent().getExtras();




        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


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

        if(bundle!= null)
        {
            mCurrentMeetingAdd  =(Meeting) bundle.getSerializable("meeting");
            displayMeetingData(mCurrentMeetingAdd);
        }else{

            finish();
        }


    }


    public void loadMeetingItem(String meetingID)
    {

        int meetingItemCounter=0;
        mMeetingItem = retrieveMeetingItem(meetingID);
        mPaymentCertificates = retrievePayCertItem(meetingID);
        mVariationOrders = retrieveVariationOrderItem(meetingID);

        for(int i = 0; i< MeetingTemplate.Template.length; i++)
        {
            LinearLayout meeting_item = null;

            int templateType= MeetingTemplate.TemplateTypeOrder[i];

            switch (templateType)
            {
                case MeetingTemplate.TemplateType.COMMON_ITEM :

                    meeting_item = new Meeting_Item(this);
                    ((Meeting_Item)meeting_item).setMeetingItem(mMeetingItem.get(meetingItemCounter)); meetingItemCounter++;  break;

                case MeetingTemplate.TemplateType.ATTENDEE_ITEM :meeting_item = new Attendee_SubMeeting_Item(this) ;
                    ((Attendee_SubMeeting_Item)meeting_item).setMeetingItem(new MeetingItem(MeetingTemplate.Template[i],String.valueOf(i+1), mCurrentMeetingAdd ));  break;


                case MeetingTemplate.TemplateType.PAYMENT_CERTIFICATE_ITEM :meeting_item = new PaymentCert_MeetingItem(this) ;
                    ((PaymentCert_MeetingItem)meeting_item).setMeetingItem(mMeetingItem.get(meetingItemCounter)); meetingItemCounter++;  break;

                case MeetingTemplate.TemplateType.VARIOUS_ORDER_ITEM : meeting_item = new VariousOrder_MeetingItem(this) ;
                    ((VariousOrder_MeetingItem)meeting_item).setMeetingItem(mMeetingItem.get(meetingItemCounter)); meetingItemCounter++;  break;

                default:meeting_item = new Meeting_Item(this);
                    ((Meeting_Item)meeting_item).setMeetingItem(mMeetingItem.get(meetingItemCounter)); meetingItemCounter++;;  break;


            }


            meetItemContainer.addView(meeting_item);



        }




    }

    private void addMeetItem(int count)
    {

        for(int i = 0; i< MeetingTemplate.Template.length; i++)
        {
            LinearLayout meeting_item = null;

            int templateType= MeetingTemplate.TemplateTypeOrder[i];

            switch (templateType)
            {
                case MeetingTemplate.TemplateType.COMMON_ITEM : meeting_item = new Meeting_Item(this);
                    ((Meeting_Item)meeting_item).setMeetingItem(new MeetingItem(MeetingTemplate.Template[i],String.valueOf(i+1), mCurrentMeetingAdd));  break;

                case MeetingTemplate.TemplateType.ATTENDEE_ITEM :meeting_item = new Attendee_SubMeeting_Item(this) ;
                    ((Attendee_SubMeeting_Item)meeting_item).setMeetingItem(new MeetingItem(MeetingTemplate.Template[i],String.valueOf(i+1), mCurrentMeetingAdd ));  break;


                case MeetingTemplate.TemplateType.PAYMENT_CERTIFICATE_ITEM :meeting_item = new PaymentCert_MeetingItem(this) ;
                    ((PaymentCert_MeetingItem)meeting_item).setMeetingItem(new MeetingItem(MeetingTemplate.Template[i],String.valueOf(i+1), mCurrentMeetingAdd ));  break;

                case MeetingTemplate.TemplateType.VARIOUS_ORDER_ITEM : meeting_item = new VariousOrder_MeetingItem(this) ;
                    ((VariousOrder_MeetingItem)meeting_item).setMeetingItem(new MeetingItem(MeetingTemplate.Template[i],String.valueOf(i+1), mCurrentMeetingAdd ));  break;

                default:meeting_item = new Meeting_Item(this);
                    ((Meeting_Item)meeting_item).setMeetingItem(new MeetingItem(MeetingTemplate.Template[i],String.valueOf(i+1), mCurrentMeetingAdd ));  break;


            }


            meetItemContainer.addView(meeting_item);



        }



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
       /* prepareSaving();
        insertNewMeeting(mCurrentMeetingAdd);

        getLastMeetingID();

        mCurrentMeetingAdd.setId(mLastInserted);

        addMeetItem(5);*/
    }


    private void displayMeetingData(Meeting meeting)
    {


        ((EditText)findViewById(R.id.edtprojectmgrref)).setText( meeting.getProjectManagersRef());
        mSpinClient.setSelection(getClientPosition(meeting.getId()), true);
       mSpinProject.setSelection(getProjectPosition(meeting.getId()), true);
        ((EditText)findViewById(R.id.edtsite)).setText(meeting.getSite());

        ((TextView)findViewById(R.id.txtstartdateday)).setText(meeting.getMeetingDate());
        ((TextView)findViewById(R.id.txtstartdatetime)).setText(meeting.getStartTime());
        ((EditText)findViewById(R.id.edtVenue)).setText(meeting.getVenue());


    }


    private void prepareSaving() {

        mCurrentMeetingAdd = new Meeting();

        mCurrentMeetingAdd.setProjectManagersRef( ((EditText)findViewById(R.id.edtprojectmgrref)).getText().toString()  );
        mCurrentMeetingAdd.setClientId(selectedClientID);
        mCurrentMeetingAdd.setProjectId(selectedProjectID);
        mCurrentMeetingAdd.setSite( ((EditText)findViewById(R.id.edtsite)).getText().toString()  );
        mCurrentMeetingAdd.setMeetingDate( ((TextView)findViewById(R.id.txtstartdateday)).getText().toString()  );
        mCurrentMeetingAdd.setStartTime( ((TextView)findViewById(R.id.txtstartdatetime)).getText().toString()  );
        mCurrentMeetingAdd.setVenue( ((EditText)findViewById(R.id.edtVenue)).getText().toString()  );

    }



    private int updateMeeting(Meeting aMeeting) {
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


/************************************************ Retrieve all meeting items ************************************************/

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


    private ArrayList<Attendee> retrieveAttendee()
    {
        ContentResolver resolver = getContentResolver();

        Cursor cursor = resolver.query(ContentType.ATTENDEE_CONTENT_URI, AttendeeColumns.PROJECTION, null, null,null);
        ArrayList<Attendee> attendees = new ArrayList<>();

        if(cursor.moveToFirst()){
            do{
                Attendee item = new Attendee(
                        ((cursor.getString(0))!=null ? Integer.parseInt(cursor.getString(0)):0),
                        (cursor.getString(cursor.getColumnIndex(AttendeeColumns.FIRSTNAME))),
                        (cursor.getString(cursor.getColumnIndex(AttendeeColumns.SURNAME))),
                        (cursor.getString(cursor.getColumnIndex(AttendeeColumns.ORGANSATION))),
                        (cursor.getString(cursor.getColumnIndex(AttendeeColumns.TELEPHONE))),
                        (cursor.getString(cursor.getColumnIndex(AttendeeColumns.CELLPHONE))),
                        (cursor.getString(cursor.getColumnIndex(AttendeeColumns.FAX))),
                        (cursor.getString(cursor.getColumnIndex(AttendeeColumns.EMAIL)))
                );
//int id, String firstname, String surname, String organisation, String telephone, String cellphone, String fax, String email
                attendees.add(item);

            }while(cursor.moveToNext());
        }

        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }

        return attendees;
    }

    private ArrayList<MeetingItem> retrieveMeetingItem(String meeting_id)
    {
        ContentResolver resolver = getContentResolver();

        Cursor cursor = resolver.query(ContentType.MEETINGITEM_CONTENT_URI, MeetingItemColumns.PROJECTION, MeetingItemColumns.MEETING_ID+" =?", new String[]{meeting_id},null);
        ArrayList<MeetingItem > elements = new ArrayList<>();

        if(cursor.moveToFirst()){
            do{
                MeetingItem item = new MeetingItem(
                        ((cursor.getString(0))!=null ? Integer.parseInt(cursor.getString(0)):0),
                        (cursor.getString(cursor.getColumnIndex(MeetingItemColumns.MEETING_ID)) !=null ? Integer.parseInt(cursor.getString(cursor.getColumnIndex(MeetingItemColumns.MEETING_ID))):0),
                        (cursor.getString(cursor.getColumnIndex(MeetingItemColumns.ITEM_NAME))),
                        (cursor.getString(cursor.getColumnIndex(MeetingItemColumns.POSITION))),
                        (cursor.getString(cursor.getColumnIndex(MeetingItemColumns.STATUS)))

                );
                elements.add(item);
//int id, int meetingId, String itemName, String position, String status
            }while(cursor.moveToNext());
        }

        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }

        return elements;
    }


    private ArrayList<MeetingSubItem> retrieveMeetingSubItem(String meetingItem_id)
    {
        ContentResolver resolver = getContentResolver();

        Cursor cursor = resolver.query(ContentType.MEETINGSUBITEM_CONTENT_URI, MeetingSubItemColumns.PROJECTION, MeetingSubItemColumns.MEETINGITEM_ID+" =?", new String[]{meetingItem_id},null);
        ArrayList<MeetingSubItem > elements = new ArrayList<>();

        if(cursor.moveToFirst()){
            do{
                MeetingSubItem item = new MeetingSubItem(
                        ((cursor.getString(0))!=null ? Integer.parseInt(cursor.getString(0)):0),
                        (cursor.getString(cursor.getColumnIndex(MeetingSubItemColumns.MEETING_ID)) !=null ? Integer.parseInt(cursor.getString(cursor.getColumnIndex(MeetingSubItemColumns.MEETING_ID))):0),
                        (cursor.getString(cursor.getColumnIndex(MeetingSubItemColumns.MEETINGITEM_ID)) !=null ? Integer.parseInt(cursor.getString(cursor.getColumnIndex(MeetingSubItemColumns.MEETINGITEM_ID))):0),

                        (cursor.getString(cursor.getColumnIndex(MeetingSubItemColumns.ITEMNOTE))),
                        (cursor.getString(cursor.getColumnIndex(MeetingSubItemColumns.POSITION))),
                        (cursor.getString(cursor.getColumnIndex(MeetingSubItemColumns.OWNER))),
                        (cursor.getString(cursor.getColumnIndex(MeetingSubItemColumns.DONE_BY_DATE)))

                );
                elements.add(item);

            }while(cursor.moveToNext());
        }

        //int id,int meetingId, int meetingItemId, String itemNote, String position, String owner, String done_by_date
        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }

        return elements;
    }




    private ArrayList<Attendee> retrieveAttendeeMeetingItem(String meeting_id)
    {
        ContentResolver resolver = getContentResolver(); //MeetingSubItemColumns._ID+" =?", new String[]{meet_id}

        String meet_id = String.valueOf(mCurrentMeetingAdd.getId()) ;

        Cursor cursor = resolver.query(ContentType.ATTENDANCE_CONTENT_URI, AttendanceColumns.PROJECTION, AttendanceColumns.MEETING_ID+" =?", new String[]{meeting_id},null);
        ArrayList< Attendee> elements = new ArrayList<>();

        if(cursor.moveToFirst()){
            do{
                int log_id =(cursor.getString(0))!=null ? Integer.parseInt(cursor.getString(0)):0;
                int attendee_id =  (cursor.getString(cursor.getColumnIndex(AttendanceColumns.ATTENDEE_ID)) !=null ? Integer.parseInt(cursor.getString(cursor.getColumnIndex(AttendanceColumns.ATTENDEE_ID))):0);

                Attendee item = getAttendeeByID(attendee_id);

                elements.add(item);

            }while(cursor.moveToNext());
        }

        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }

        return elements;
    }


    private ArrayList<Attendee> retrieveApologyMeetingItem(String meeting_id)
    {
        ContentResolver resolver = getContentResolver();

        Cursor cursor = resolver.query(ContentType.APOLOGY_CONTENT_URI, ApologyColumns.PROJECTION, ApologyColumns.MEETING_ID+" =?", new String[]{meeting_id},null);
        ArrayList< Attendee> elements = new ArrayList<>();

        if(cursor.moveToFirst()){
            do{
                int log_id =(cursor.getString(0))!=null ? Integer.parseInt(cursor.getString(0)):0;
                int attendee_id =  (cursor.getString(cursor.getColumnIndex(ApologyColumns.ATTENDEE_ID)) !=null ? Integer.parseInt(cursor.getString(cursor.getColumnIndex(ApologyColumns.ATTENDEE_ID))):0);

                Attendee item = getAttendeeByID(attendee_id);

                elements.add(item);



            }while(cursor.moveToNext());
        }

        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }

        return elements;
    }



    private ArrayList<PaymentCertificate> retrievePayCertItem(String meeting_id)
    {
        ContentResolver resolver = getContentResolver();

        Cursor cursor = resolver.query(ContentType.PAYMENTCERTIFICATE_CONTENT_URI, PaymentCertificateColumns.PROJECTION, PaymentCertificateColumns.MEETING_ID+" =?", new String[]{meeting_id},null);
        ArrayList<PaymentCertificate > elements = new ArrayList<>();

        if(cursor.moveToFirst()){
            do{
                PaymentCertificate item = new PaymentCertificate(
                        ((cursor.getString(0))!=null ? Integer.parseInt(cursor.getString(0)):0),

                        (cursor.getString(cursor.getColumnIndex(PaymentCertificateColumns.PROJECT_ID)) !=null ? Integer.parseInt(cursor.getString(cursor.getColumnIndex(PaymentCertificateColumns.PROJECT_ID))):0),
                        (cursor.getString(cursor.getColumnIndex(PaymentCertificateColumns.MEETING_ID)) !=null ? Integer.parseInt(cursor.getString(cursor.getColumnIndex(PaymentCertificateColumns.MEETING_ID))):0),
                        (cursor.getString(cursor.getColumnIndex(PaymentCertificateColumns.MEETINGITEM_ID)) !=null ? Integer.parseInt(cursor.getString(cursor.getColumnIndex(PaymentCertificateColumns.MEETINGITEM_ID))):0),
                        (cursor.getString(cursor.getColumnIndex(PaymentCertificateColumns.MEETINGSUBITEM_ID)) !=null ? Integer.parseInt(cursor.getString(cursor.getColumnIndex(PaymentCertificateColumns.MEETINGSUBITEM_ID))):0),

                        (cursor.getString(cursor.getColumnIndex(PaymentCertificateColumns.PAYMENTCERTIFICATE))),
                        (cursor.getString(cursor.getColumnIndex(PaymentCertificateColumns.ISSUE_DATE))),
                        (cursor.getString(cursor.getColumnIndex(PaymentCertificateColumns.PAID))),
                        (cursor.getString(cursor.getColumnIndex(PaymentCertificateColumns.DATE_DUE))),
                        (cursor.getString(cursor.getColumnIndex(PaymentCertificateColumns.DAY_LATE))),
                        (cursor.getString(cursor.getColumnIndex(PaymentCertificateColumns.SIGNED_COPY))),
                        (cursor.getDouble(cursor.getColumnIndex(PaymentCertificateColumns.AMOUNT)))
                );
                elements.add(item);

            }while(cursor.moveToNext());
        }
//int projectId, int meetingId, int meetingItemId, int meetingSubItemId, String paymentcertificate, String issueDate, String paid, String dateDue, String dayLate, String signedCopy, double amount
        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }

        return elements;
    }


    private ArrayList<VariationOrder> retrieveVariationOrderItem(String meeting_id)
    {
        ContentResolver resolver = getContentResolver();

        Cursor cursor = resolver.query(ContentType.VARIATIONORDER_CONTENT_URI, VariationOrderColumns.PROJECTION, VariationOrderColumns.MEETING_ID+" =?", new String[]{meeting_id},null);
        ArrayList<VariationOrder > elements = new ArrayList<>();

        if(cursor.moveToFirst()){
            do{
                VariationOrder item = new VariationOrder(
                        ((cursor.getString(0))!=null ? Integer.parseInt(cursor.getString(0)):0),

                        (cursor.getString(cursor.getColumnIndex(VariationOrderColumns.PROJECT_ID)) !=null ? Integer.parseInt(cursor.getString(cursor.getColumnIndex(VariationOrderColumns.PROJECT_ID))):0),
                        (cursor.getString(cursor.getColumnIndex(VariationOrderColumns.MEETING_ID)) !=null ? Integer.parseInt(cursor.getString(cursor.getColumnIndex(VariationOrderColumns.MEETING_ID))):0),
                        (cursor.getString(cursor.getColumnIndex(VariationOrderColumns.MEETINGITEM_ID)) !=null ? Integer.parseInt(cursor.getString(cursor.getColumnIndex(VariationOrderColumns.MEETINGITEM_ID))):0),
                        (cursor.getString(cursor.getColumnIndex(VariationOrderColumns.MEETINGSUBITEM_ID)) !=null ? Integer.parseInt(cursor.getString(cursor.getColumnIndex(VariationOrderColumns.MEETINGSUBITEM_ID))):0),

                        (cursor.getString(cursor.getColumnIndex(VariationOrderColumns.VARIATIONORDER))),
                        (cursor.getString(cursor.getColumnIndex(VariationOrderColumns.MOTIVATION))),
                        (cursor.getString(cursor.getColumnIndex(VariationOrderColumns.APPROVED))),
                        (cursor.getDouble(cursor.getColumnIndex(VariationOrderColumns.OMIT))),
                        (cursor.getDouble(cursor.getColumnIndex(VariationOrderColumns.ADD))),
                        (cursor.getDouble(cursor.getColumnIndex(VariationOrderColumns.BALANCE)))
                );
                elements.add(item);

            }while(cursor.moveToNext());
        }
        //int id, int projectId, int meetingId, int meetingItemId, int meetingSubItemId, String variationOrder, String motivation, String approved, double omit, double add, double balance

        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }

        return elements;
    }





    private Attendee getAttendeeByID(int at_id) {
        Attendee attendee = new Attendee();
        for(Attendee at: mAttendeeList){
            if(at.getId() == at_id ){
                attendee = at;

            }
        }
        return attendee;

    }



    private int getProjectPosition(Integer projectID) {
        int position = -1;

        for(int i=0;i< mProjectList.size(); i++)
        {
            if(mProjectList.get(i).getId()== projectID)
            {
                return i;
            }
        }


        return position;
    }

    private int getClientPosition(Integer clientID) {
        int position = -1;

        for(int i=0;i< mClientList.size(); i++)
        {
            if(mClientList.get(i).getId()== clientID)
            {
                return i;
            }
        }

        return position;

    }



}
