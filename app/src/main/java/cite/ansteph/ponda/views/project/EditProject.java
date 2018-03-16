package cite.ansteph.ponda.views.project;

import android.app.DatePickerDialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import cite.ansteph.ponda.adapter.CustomClientListAdapter;
import cite.ansteph.ponda.R;
import cite.ansteph.ponda.api.ContentType;
import cite.ansteph.ponda.api.columns.ClientColumns;
import cite.ansteph.ponda.api.columns.ProjectColumns;
import cite.ansteph.ponda.model.Client;
import cite.ansteph.ponda.model.Project;

public class EditProject extends AppCompatActivity implements NavigationView.OnCreateContextMenuListener  {

    Spinner mSpinClient;
    ArrayList <Client> mClientlist;
    CustomClientListAdapter customClientListAdapter;
    Integer selectedClient;
    int mYear, mMonth, mDay;
    int selectedClientID;
    int mLastInserted;
    String seletedMonth, selectedDay;
    LinearLayout projItemContainer;
    Project mProjectEdited;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_project);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Start Date
        //final TextView pickStartDate = (TextView)findViewById(R.id.edtstartdate);
        final TextView startDate = (TextView) findViewById(R.id.start_date);

        //End Date
        //final TextView pickEndDate = (TextView)findViewById(R.id.edtenddate);
        final TextView endDate = (TextView) findViewById(R.id.end_date);

        final Calendar myCalendar = Calendar.getInstance();

        Date now = new Date();
        SimpleDateFormat dateFormatter;
        dateFormatter = new SimpleDateFormat("MM-dd-yyyy");
        startDate.setText(dateFormatter.format(now));
            endDate.setText(dateFormatter.format(now));

        endDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                // Launch Date Picker Dialog
                DatePickerDialog dpd = new DatePickerDialog(EditProject.this,
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

                                String monthcon = (mMonth<10)? "0"+String.valueOf(mMonth+1):String.valueOf(mMonth+1);
                                String daycon =  (dayOfMonth<10)? "0"+String.valueOf(dayOfMonth):String.valueOf(dayOfMonth);


                                endDate.setText(daycon + "-"
                                        + monthcon + "-" + year);

                            }
                        }, mYear, mMonth, mDay);
                dpd.getDatePicker().setMinDate(System.currentTimeMillis());
                dpd.show();
            }
        });



        startDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                // Launch Date Picker Dialog
                DatePickerDialog dpd = new DatePickerDialog(EditProject.this,
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

                                String monthcon = (mMonth<10)? "0"+String.valueOf(mMonth+1):String.valueOf(mMonth+1);
                                String daycon =  (dayOfMonth<10)? "0"+String.valueOf(dayOfMonth):String.valueOf(dayOfMonth);

                                startDate.setText(daycon + "-"
                                        + monthcon + "-" + year);

                            }
                        }, mYear, mMonth, mDay);
                dpd.getDatePicker().setMinDate(System.currentTimeMillis());
                dpd.show();

            }
        });

        Bundle bundle = getIntent().getExtras();

        if(bundle!= null)
        {
            mProjectEdited =(Project) bundle.getSerializable("project");
            fillFields();
        }else{
            mProjectEdited = new Project();
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                prepareSaving();

                //step 2 insert or update : if getId !=0 null update else insert

                if(mProjectEdited.getId()!=0) {
                    int done =  updateProject(mProjectEdited);
                    if(done==1)
                        startActivity(new Intent(getApplicationContext(), ProjectList.class));
                }else{
                    int done =  insertProject(mProjectEdited);
                    if(done==1)
                        startActivity(new Intent(getApplicationContext(), ProjectList.class));
                }

               /* Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mSpinClient = (Spinner)findViewById(R.id.edtcleintid);
        mClientlist = populatelist();

        customClientListAdapter = new CustomClientListAdapter(mClientlist, this);

        mSpinClient.setAdapter(customClientListAdapter);

        mSpinClient.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                // TODO Auto-generated method stub
                Client selected = (Client) parent.getItemAtPosition(position);

                selectedClient = selected.getId();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub

            }
        });
    }

    private ArrayList<Client> populatelist() {
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


    void prepareSaving()
    {
        //retrieving the corresponding value
        mProjectEdited.setName( ((EditText)findViewById(R.id.edtname)).getText().toString()  );
        mProjectEdited.setClientId(selectedClient);
        mProjectEdited.setProjManName( ((EditText)findViewById(R.id.edtprojectmanname)).getText().toString()  );
    }

    void fillFields()
    {
        //filling the corresponding value
        ((EditText)findViewById(R.id.edtname)).setText(mProjectEdited.getName());
        ((EditText)findViewById(R.id.edtprojectmanname)).setText(mProjectEdited.getProjManName());
    }

    public int insertProject(Project aProject){

        try {
            ContentValues values = new ContentValues();

            values.put(ProjectColumns.NAME,aProject.getName()) ;
            values.put(ProjectColumns.CLIENT_ID ,aProject.getClientId()) ;
            values.put(ProjectColumns.START_DATE,aProject.getStartDate()) ;
            values.put(ProjectColumns.END_DATE,aProject.getEndDate()) ;
            values.put(ProjectColumns.PROJ_MAN_NAME,aProject.getProjManName()) ;

            getContentResolver().insert(ContentType.PROJECT_CONTENT_URI, values);

            return 1;

        }catch (SQLException e)
        {
            e.printStackTrace();

            return 0;
        }
    }

    public int updateProject(Project aProject)
    {
        String project_id = String.valueOf( aProject.getId());

        try {
            ContentValues values = new ContentValues();

            values.put(ProjectColumns.NAME,aProject.getName()) ;
            values.put(ProjectColumns.CLIENT_ID ,aProject.getClientId()) ;
            values.put(ProjectColumns.START_DATE,aProject.getStartDate()) ;
            values.put(ProjectColumns.END_DATE,aProject.getEndDate()) ;
            values.put(ProjectColumns.PROJ_MAN_NAME,aProject.getProjManName()) ;

            getContentResolver().update(ContentType.PROJECT_CONTENT_URI, values, ProjectColumns._ID+" =?", new String[]{project_id});

            return 1;

        }catch (SQLException e)
        {
            e.printStackTrace();

            return 0;
        }
    }
}

