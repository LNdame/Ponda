package cite.ansteph.ponda.views.client;

import android.content.ContentValues;
import android.content.Intent;
import android.database.SQLException;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

import cite.ansteph.ponda.R;
import cite.ansteph.ponda.api.ContentType;
import cite.ansteph.ponda.api.columns.ClientColumns;
import cite.ansteph.ponda.model.Client;

public class EditClient extends AppCompatActivity {

    Client mClientEdited;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_client);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Bundle bundle = getIntent().getExtras();

        if(bundle!= null)
        {
            mClientEdited =(Client) bundle.getSerializable("client");
            fillFields();
        }else{
            mClientEdited = new Client();
        }




        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //saving the current client
                //step 1 one get the values
                prepareSaving();

                //step 2 insert or update : if getId !=0 null update else insert

                if(mClientEdited.getId()!=0) {
                 int done =    updateClient(mClientEdited);
                 if(done==1)
                    startActivity(new Intent(getApplicationContext(), ClientList.class));
                }else{
                    int done =  insertClient(mClientEdited);
                    if(done==1)
                    startActivity(new Intent(getApplicationContext(), ClientList.class));
                }



               /* Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }



    void prepareSaving()
    {

        //retrieving the corresponding value

        mClientEdited.setName( ((EditText)findViewById(R.id.edtname)).getText().toString()  );
        mClientEdited.setWebsiteUrl( ((EditText)findViewById(R.id.edtweburl)).getText().toString()  );
        mClientEdited.setTelephone( ((EditText)findViewById(R.id.edttelephone)).getText().toString()  );
        mClientEdited.setContactPerson( ((EditText)findViewById(R.id.edtcontactname)).getText().toString()  );
        mClientEdited.setContactPhone( ((EditText)findViewById(R.id.edtcontacttel)).getText().toString()  );
        mClientEdited.setEmail( ((EditText)findViewById(R.id.edtemail)).getText().toString()  );

    }



    void fillFields()
    {
        //filling the corresponding value

        ((EditText)findViewById(R.id.edtname)).setText(mClientEdited.getName());
        ((EditText)findViewById(R.id.edtweburl)).setText(mClientEdited.getWebsiteUrl());
        ((EditText)findViewById(R.id.edttelephone)).setText(mClientEdited.getTelephone());
        ((EditText)findViewById(R.id.edtcontactname)).setText(mClientEdited.getContactPerson());
        ((EditText)findViewById(R.id.edtcontacttel)).setText(mClientEdited.getContactPhone());
        ((EditText)findViewById(R.id.edtemail)).setText(mClientEdited.getEmail());


    }





    public int insertClient(Client aClient){

        try {
            ContentValues values = new ContentValues();

            values.put(ClientColumns.NAME,aClient.getName()) ;
            values.put(ClientColumns.WEBSITE_URL ,aClient.getWebsiteUrl()) ;
            values.put(ClientColumns.TELEPHONE,aClient.getTelephone()) ;
            values.put(ClientColumns.CONTACT_PERSON,aClient.getContactPerson()) ;
            values.put(ClientColumns.CONTACT_PHONE,aClient.getContactPhone()) ;
            values.put(ClientColumns.EMAIL,aClient.getEmail()) ;


            getContentResolver().insert(ContentType.CLIENT_CONTENT_URI, values);


            return 1;

        }catch (SQLException e)
        {
            e.printStackTrace();

            return 0;
        }

    }



    public int updateClient(Client aClient)
    {
        String client_id = String.valueOf( aClient.getId());

        try {
            ContentValues values = new ContentValues();

            values.put(ClientColumns.NAME,aClient.getName()) ;
            values.put(ClientColumns.WEBSITE_URL ,aClient.getWebsiteUrl()) ;
            values.put(ClientColumns.TELEPHONE,aClient.getTelephone()) ;
            values.put(ClientColumns.CONTACT_PERSON,aClient.getContactPerson()) ;
            values.put(ClientColumns.CONTACT_PHONE,aClient.getContactPhone()) ;
            values.put(ClientColumns.EMAIL,aClient.getEmail()) ;


            getContentResolver().update(ContentType.CLIENT_CONTENT_URI, values, ClientColumns._ID+" =?", new String[]{client_id});


            return 1;

        }catch (SQLException e)
        {
            e.printStackTrace();

            return 0;
        }

    }



}
