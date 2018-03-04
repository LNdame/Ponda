package cite.ansteph.ponda.views.popups;

import android.app.Activity;
import android.content.ContentResolver;
import android.database.Cursor;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import cite.ansteph.ponda.R;
import cite.ansteph.ponda.adapter.CustomAttendeeListAdapter;
import cite.ansteph.ponda.api.ContentType;
import cite.ansteph.ponda.api.columns.AttendeeColumns;
import cite.ansteph.ponda.model.Attendee;

/**
 * Created by Wendy on 2018/03/02.
 */

public class AttendancePop extends Activity{
    ArrayList<Attendee> mAttendeeList;
    CustomAttendeeListAdapter mAttendeeAdapter;
    ListView attendeeListView;
    ArrayList<String> selectedAttendee = new ArrayList<>();
    TextView tvCancel, tvSave;

//    SharedPreferences sharedPref = context.getSharedPreferences("myprefs", Context.MODE_PRIVATE);
//    int bg = sharedPref.getInt("background_resource", android.R.color.darker_gray);
//

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pop_attendance);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        getWindow().setLayout((int) (width * .8), (int) (height * .8));

        tvSave = (TextView) findViewById(R.id.tvsave);
        tvCancel = (TextView) findViewById(R.id.tvcancel);

        tvSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        //attendee pop-up
        attendeeListView = (ListView) findViewById(R.id.listview);

        mAttendeeList = populateAttendees();
        mAttendeeAdapter = new CustomAttendeeListAdapter(mAttendeeList, this);
        attendeeListView.setAdapter(mAttendeeAdapter);
        attendeeListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);




        attendeeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                // TODO Auto-generated method stub
                String selected = ((TextView)view).getText().toString();
                if(selectedAttendee.contains(selected)){
                    selectedAttendee.remove(selected);
                }
                else{
                    selectedAttendee.add(selected);
                }
            }

        });

    }
    private ArrayList<Attendee> populateAttendees() {
        ContentResolver resolver = getContentResolver();

        Cursor cursor = resolver.query(ContentType.ATTENDEE_CONTENT_URI, AttendeeColumns.PROJECTION, null, null,null);
        ArrayList<Attendee> activities = new ArrayList<>();

        if(cursor.moveToFirst()){
            do{
                Attendee activity = new Attendee(
                        ((cursor.getString(0))!=null ? Integer.parseInt(cursor.getString(0)):0),
                        (cursor.getString(cursor.getColumnIndex(AttendeeColumns.FIRSTNAME))),
                        (cursor.getString(cursor.getColumnIndex(AttendeeColumns.SURNAME)))

                );

                activities.add(activity);

            }while(cursor.moveToNext());
        }

        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }

        return activities;
    }
}
