package cite.ansteph.ponda.views.attendee;

import android.content.ContentValues;
import android.content.Intent;
import android.database.SQLException;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

import cite.ansteph.ponda.R;
import cite.ansteph.ponda.api.ContentType;
import cite.ansteph.ponda.api.columns.AttendeeColumns;
import cite.ansteph.ponda.api.columns.ClientColumns;
import cite.ansteph.ponda.model.Attendee;

public class EditAttendee extends AppCompatActivity {

    Attendee mAttendeeEdited;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_attendee);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        Bundle bundle = getIntent().getExtras();

        if(bundle!= null)
        {
            mAttendeeEdited =(Attendee) bundle.getSerializable("attendee");
            fillFields();
        }else{
            mAttendeeEdited = new Attendee();
        }




        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //saving the current attendee
                //step 1 one get the values
                prepareSaving();

                //step 2 insert or update : if getId !=0 null update else insert

                if(mAttendeeEdited.getId()!=0) {
                    int done =    updateAttendee(mAttendeeEdited);
                    if(done==1)
                        startActivity(new Intent(getApplicationContext(), AttendeeList.class));
                }else{
                    int done =  insertAttendee(mAttendeeEdited);
                    if(done==1)
                        startActivity(new Intent(getApplicationContext(), AttendeeList.class));
                }



            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private int insertAttendee(Attendee aAttendee)
    {
        try {
            ContentValues values = new ContentValues();

            values.put(AttendeeColumns.FIRSTNAME,aAttendee.getFirstname()) ;
            values.put(AttendeeColumns.SURNAME ,aAttendee.getSurname()) ;
            values.put(AttendeeColumns.ORGANSATION,aAttendee.getOrganisation()) ;
            values.put(AttendeeColumns.TELEPHONE,aAttendee.getTelephone()) ;
            values.put(AttendeeColumns.CELLPHONE,aAttendee.getCellphone()) ;
            values.put(AttendeeColumns.FAX,aAttendee.getFax()) ;
            values.put(AttendeeColumns.EMAIL,aAttendee.getEmail()) ;


            getContentResolver().insert(ContentType.ATTENDEE_CONTENT_URI, values);


            return 1;

        }catch (SQLException e)
        {
            e.printStackTrace();

            return 0;
        }
    }

    private int updateAttendee(Attendee aAttendee)
    {
        String client_id = String.valueOf( aAttendee.getId());

        try {
            ContentValues values = new ContentValues();

            values.put(AttendeeColumns.FIRSTNAME,aAttendee.getFirstname()) ;
            values.put(AttendeeColumns.SURNAME ,aAttendee.getSurname()) ;
            values.put(AttendeeColumns.ORGANSATION,aAttendee.getOrganisation()) ;
            values.put(AttendeeColumns.TELEPHONE,aAttendee.getTelephone()) ;
            values.put(AttendeeColumns.CELLPHONE,aAttendee.getCellphone()) ;
            values.put(AttendeeColumns.FAX,aAttendee.getFax()) ;
            values.put(AttendeeColumns.EMAIL,aAttendee.getEmail()) ;


            getContentResolver().update(ContentType.ATTENDEE_CONTENT_URI, values, ClientColumns._ID+" =?", new String[]{client_id});


            return 1;

        }catch (SQLException e)
        {
            e.printStackTrace();

            return 0;
        }
    }


    void prepareSaving()
    {

        //retrieving the corresponding value

        mAttendeeEdited.setFirstname( ((EditText)findViewById(R.id.edtname)).getText().toString()  );
        mAttendeeEdited.setSurname( ((EditText)findViewById(R.id.edtsurnname)).getText().toString()  );
        mAttendeeEdited.setOrganisation( ((EditText)findViewById(R.id.edtorganisation)).getText().toString()  );
        mAttendeeEdited.setTelephone( ((EditText)findViewById(R.id.edttelephone)).getText().toString()  );
        mAttendeeEdited.setCellphone( ((EditText)findViewById(R.id.edtcellphone)).getText().toString()  );
        mAttendeeEdited.setFax( ((EditText)findViewById(R.id.edtfax)).getText().toString()  );
        mAttendeeEdited.setEmail( ((EditText)findViewById(R.id.edtemail)).getText().toString()  );

    }



    void fillFields()
    {
        //filling the corresponding value

        ((EditText)findViewById(R.id.edtname)).setText(mAttendeeEdited.getFirstname());
        ((EditText)findViewById(R.id.edtsurnname)).setText(mAttendeeEdited.getSurname());
        ((EditText)findViewById(R.id.edtorganisation)).setText(mAttendeeEdited.getTelephone());
        ((EditText)findViewById(R.id.edttelephone)).setText(mAttendeeEdited.getTelephone());
        ((EditText)findViewById(R.id.edtcellphone)).setText(mAttendeeEdited.getCellphone());
        ((EditText)findViewById(R.id.edtfax)).setText(mAttendeeEdited.getFax());
        ((EditText)findViewById(R.id.edtemail)).setText(mAttendeeEdited.getEmail());


    }





}