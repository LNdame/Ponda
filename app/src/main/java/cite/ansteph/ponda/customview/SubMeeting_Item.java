package cite.ansteph.ponda.customview;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

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

   MeetingSubItem meetingSubItem;
    MeetingItem meetingItem;
    private Meeting mMeeting;
   LinearLayout containerLyt;



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


    public  void initViews(Context context)
    {
        LayoutInflater.from(context).inflate(R.layout.subitem_layout, this);
        containerLyt = (LinearLayout) findViewById(R.id.submeetingLytContainer);


        txtSubNumber = (TextView) findViewById(R.id.txtSubNumber);
         edtSubItemContent= (EditText) findViewById(R.id.txtSubItemContent);
         edtSubOwner= (EditText) findViewById(R.id.edtSubOwner);
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



    public int updateClient(MeetingItem aMeetItem)
    {
        String meet_id = String.valueOf( aMeetItem.getId());

        try {
            ContentValues values = new ContentValues();


            values.put(MeetingItemColumns.MEETING_ID,aMeetItem.getMeeting().getId()) ;
            values.put(MeetingItemColumns.ITEM_NAME ,aMeetItem.getItemName()) ;
            values.put(MeetingItemColumns.POSITION,aMeetItem.getPosition()) ;


            mContext.getContentResolver().update(ContentType.MEETINGITEM_CONTENT_URI, values, MeetingItemColumns._ID+" =?", new String[]{meet_id});


            return 1;

        }catch (Exception e)
        {
            e.printStackTrace();

            return 0;
        }

    }








}
