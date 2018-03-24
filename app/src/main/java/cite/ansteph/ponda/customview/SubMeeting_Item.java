package cite.ansteph.ponda.customview;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import cite.ansteph.ponda.R;
import cite.ansteph.ponda.api.ContentType;
import cite.ansteph.ponda.api.columns.MeetingItemColumns;
import cite.ansteph.ponda.api.columns.MeetingSubItemColumns;
import cite.ansteph.ponda.model.Meeting;
import cite.ansteph.ponda.model.MeetingItem;
import cite.ansteph.ponda.model.MeetingSubItem;

/**
 * Created by loicstephan on 2018/03/01.
 */

public class SubMeeting_Item extends LinearLayout{

    final static String TAG   = SubMeeting_Item.class.getSimpleName();

   TextView txtSubNumber;
   EditText edtSubItemContent, edtSubOwner;
   ImageView btnDelete;
   MeetingSubItem meetingSubItem;
  // ArrayList<MeetingSubItem> mMeetingSubItem;
    MeetingSubItem aMeetSubItem;
    MeetingItem meetingItem;
   private Meeting mMeeting;
   LinearLayout containerLyt;
   CharSequence noteOutput="", ownerOutput="";
   int meetingID, meetingItemId;


    private Context mContext;

    public SubMeeting_Item(Context context) {
        super(context);
        mContext = context;
        initViews( context);
    }

    public SubMeeting_Item(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public SubMeeting_Item(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    public  void initViews(final Context context)
    {
        LayoutInflater.from(context).inflate(R.layout.subitem_layout, this);
        containerLyt = (LinearLayout) findViewById(R.id.submeetingLytContainer);


        txtSubNumber = (TextView) findViewById(R.id.txtSubNumber);
        edtSubItemContent= (EditText) findViewById(R.id.txtSubItemContent);
        edtSubItemContent.setHint("Note");

        edtSubOwner= (EditText) findViewById(R.id.edtSubOwner);
        edtSubOwner.setHint("Owner");

        btnDelete = (ImageView) findViewById(R.id.btnDelete);

         btnDelete.setOnClickListener(new OnClickListener() {
             @Override
             public void onClick(View view) {
                 containerLyt.removeView((View) view.getParent());
             }
         });

        edtSubItemContent.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                noteOutput =  s;
                prepareSaving();
                updateMeetingSubItem(aMeetSubItem);

//                mMeetingAdd.setId(mLastInserted);
            }
        });

        edtSubOwner.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                ownerOutput =  s;
            }
        });
    }

    private void prepareSaving() {
        aMeetSubItem = new MeetingSubItem();

        aMeetSubItem.setMeetingId(meetingID);
        aMeetSubItem.setMeetingItemId(meetingItemId);
        aMeetSubItem.setItemNote(noteOutput.toString() );
        aMeetSubItem.setPosition(((TextView)findViewById(R.id.txtSubNumber)).getText().toString()  );;
        //mMeetingSubItemAdd.setStatus();

    }


    public Meeting getmMeeting() {
        return mMeeting;
    }

    public void setmMeeting(Meeting mMeeting) {
        this.mMeeting = mMeeting;
    }


    public MeetingSubItem getMeetingSubItem() {
        return meetingSubItem;
    }

    public void setMeetingSubItem(MeetingSubItem meetingSubItem) {
        this.meetingSubItem = meetingSubItem;

        if(meetingSubItem!=null)
        {
            txtSubNumber .setText(meetingSubItem.getPosition());

            meetingItemId = meetingSubItem.getMeetingItemId();
            meetingID = meetingSubItem.getMeetingId();

            int i = insertMeetingSubItem(meetingSubItem);
            if (i==1)
                getLastMeetingItemID();

        }
    }

    public MeetingItem getMeetingItem() {
        return meetingItem;
    }

    public void setMeetingItem(MeetingItem meetingItem) {
        this.meetingItem = meetingItem;
    }





    public int insertMeetingSubItem(MeetingSubItem aMeetSubItem){

        try {
            ContentValues values = new ContentValues();

            values.put(MeetingSubItemColumns.MEETINGITEM_ID,aMeetSubItem.getMeetingItem().getId()) ;
            values.put(MeetingSubItemColumns.MEETING_ID,aMeetSubItem.getMeeting().getId()) ;
            values.put(MeetingSubItemColumns.ITEMNOTE ,aMeetSubItem.getItemNote()) ;
            values.put(MeetingSubItemColumns.POSITION,aMeetSubItem.getPosition()) ;

            mContext. getContentResolver().insert(ContentType.MEETINGSUBITEM_CONTENT_URI, values);
            return 1;

        }catch (Exception e)
        {
            e.printStackTrace();

            return 0;
        }

    }



    public int getLastMeetingItemID(){

        String [] columns  = new String []{MeetingSubItemColumns._ID};
        ContentResolver resolver =  mContext. getContentResolver();
        Cursor cursor = resolver.query(ContentType.MEETINGSUBITEM_CONTENT_URI,columns,null,null, MeetingSubItemColumns._ID+" DESC LIMIT 1");

        int lastId =0;

        if(cursor !=null && cursor.moveToFirst())
        {
            lastId =(cursor.getString(0))!=null ? Integer.parseInt(cursor.getString(0)):0;
            meetingItem.setId( lastId);
            //mGlobalRetainer.get_grCurrentAudit().set_id(lastId);
            Log.d(TAG, String.valueOf(lastId) );
        }
        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }

        return lastId;
    }



    public int updateMeetingSubItem(MeetingSubItem aMeetSubItem)
    {
        String meet_id = String.valueOf( aMeetSubItem.getId());

        try {
            ContentValues values = new ContentValues();


            values.put(MeetingSubItemColumns.MEETINGITEM_ID,aMeetSubItem.getMeetingItem().getId()) ;
            values.put(MeetingSubItemColumns.MEETING_ID,aMeetSubItem.getMeeting().getId()) ;
            values.put(MeetingSubItemColumns.ITEMNOTE ,aMeetSubItem.getItemNote()) ;
            values.put(MeetingSubItemColumns.POSITION,aMeetSubItem.getPosition()) ;


            mContext.getContentResolver().update(ContentType.MEETINGSUBITEM_CONTENT_URI, values, MeetingSubItemColumns._ID+" =?", new String[]{meet_id});


            return 1;

        }catch (Exception e)
        {
            e.printStackTrace();

            return 0;
        }

    }









}
