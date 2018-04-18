package cite.ansteph.ponda.customview;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import cite.ansteph.ponda.R;
import cite.ansteph.ponda.api.ContentType;
import cite.ansteph.ponda.api.columns.MeetingItemColumns;
import cite.ansteph.ponda.model.Meeting;
import cite.ansteph.ponda.model.MeetingItem;
import cite.ansteph.ponda.model.MeetingSubItem;

/**
 * Created by loicstephan on 2018/03/01.
 */

public class Meeting_Item extends LinearLayout {
    final static String TAG   = Meeting_Item.class.getSimpleName();
    LinearLayout containerLyt;

    ImageButton btnOpenSub, btnCloseSub;

    TextView txtItemNumber, txtItemTitle, txtdate;

    LinearLayout lytSubItemMeeting;//subItemContainer

    NestedScrollView lytSubItemContainer;
    Button btnAddSub;

    private String itemNumber, itemTitle;

    private Context mContext;

    private ArrayList<MeetingSubItem> meetingSubItems;

    private ArrayList<SubMeeting_Item> SubItemLayoutList;
    int subMeetingItemCount=0;

    private MeetingItem meetingItem;
    private Meeting mMeeting;



    public Meeting_Item(Context context) {
        super(context);
        mContext = context;
        initViews( context);
    }

    public Meeting_Item(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initViews( context, attrs);
    }

    public Meeting_Item(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }



    public void initViews(final Context context)
    {
        LayoutInflater.from(context).inflate(R.layout.meeting_item_layout, this);
        containerLyt = (LinearLayout) findViewById(R.id.meetingLytContainer);

        lytSubItemContainer =(NestedScrollView) findViewById(R.id.subItemContainer);

        lytSubItemMeeting = (LinearLayout) findViewById(R.id.subItemMeetingLyt);

        txtItemNumber= (TextView) findViewById(R.id.txtItemNumber);
        txtItemTitle= (TextView) findViewById(R.id.txtItemTitle);


        btnOpenSub = (ImageButton) findViewById(R.id.imgbtnOpenSub);
        btnCloseSub= (ImageButton) findViewById(R.id.imgbtnCloseSub);

        txtdate = (TextView) findViewById(R.id.txtdate);


        btnCloseSub.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                lytSubItemContainer.setVisibility(GONE);
            }
        });


        btnOpenSub.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                lytSubItemContainer.setVisibility(VISIBLE);
            }
        });

        SubItemLayoutList = new ArrayList<>();

        btnAddSub = (Button) findViewById(R.id.btnAddSub) ;
        final SubMeeting_Item[] subItem = {null};



        btnAddSub.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                SubMeeting_Item subMeeting_item = new SubMeeting_Item(context);
                subMeeting_item.setmMeeting(getMeetingItem().getMeeting());
                subMeeting_item.setMeetingItem(getMeetingItem());


                MeetingSubItem meetingSubItem = new MeetingSubItem(getMeetingItem().getMeeting().getId(),
                        getMeetingItem().getId(),"",String.valueOf(subMeetingItemCount+1), "","" );
                meetingSubItem.setMeeting(getMeetingItem().getMeeting());
                meetingSubItem.setMeetingItem(getMeetingItem());

                subMeeting_item.setMeetingSubItem(meetingSubItem);

                SubItemLayoutList.add(subMeeting_item);
                //subItem[0] = new SubMeeting_Item(context);

                lytSubItemMeeting.addView(SubItemLayoutList.get(subMeetingItemCount));
                subMeetingItemCount ++;
            }
        });

//        txtdate.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                DialogFragment nf = new RecordTimePickerFragment();
//                nf.show(appCompatActivity.getSupportFragmentManager(), "Start Date");
//
//
//            }
//        });

        //SubMeeting_Item subItem = new SubMeeting_Item(context);
        //lytSubItemMeeting.addView(subItem);




    }



    private void initViews(Context context , AttributeSet attrs)
    {

    }


    public MeetingItem getMeetingItem() {
        return meetingItem;
    }

    public void setMeetingItem(MeetingItem meetingItem) {
        this.meetingItem = meetingItem;
        if(meetingItem!=null)
        {
            txtItemNumber.setText(meetingItem.getPosition());
            txtItemTitle.setText(meetingItem.getItemName());

            int i = insertMeetingItem(meetingItem);
            if (i==1)
                getLastMeetingItemID();

        }
    }


    public Meeting getmMeeting() {
        return mMeeting;
    }

    public void setmMeeting(Meeting mMeeting) {
        this.mMeeting = mMeeting;
    }

    public void UpdatePositions(){
//        SubMeeting_Item subMeeting_item = new SubMeeting_Item(mContext);
//        subMeeting_item.setmMeeting(getMeetingItem().getMeeting());
//        subMeeting_item.setMeetingItem(getMeetingItem());
//
//
//        MeetingSubItem meetingSubItem = new MeetingSubItem(getMeetingItem().getMeeting().getId(),
//                getMeetingItem().getId(),"",String.valueOf(subMeetingItemCount+1) );
//        meetingSubItem.setMeeting(getMeetingItem().getMeeting());
//        meetingSubItem.setMeetingItem(getMeetingItem());
//
//        subMeeting_item.setMeetingSubItem(meetingSubItem);
//
//        SubItemLayoutList.add(subMeeting_item);
//        //subItem[0] = new SubMeeting_Item(context);
//
//        lytSubItemMeeting.addView(SubItemLayoutList.get(subMeetingItemCount));
//        //subMeetingItemCount ++;
//
//        for (int i = 0; i<subMeetingItemCount; i++){
//
//        }
    }


    public int insertMeetingItem(MeetingItem aMeetItem){

        try {
            ContentValues values = new ContentValues();

            values.put(MeetingItemColumns.MEETING_ID,aMeetItem.getMeeting().getId()) ;
            values.put(MeetingItemColumns.ITEM_NAME ,aMeetItem.getItemName()) ;
            values.put(MeetingItemColumns.POSITION,aMeetItem.getPosition()) ;

            mContext. getContentResolver().insert(ContentType.MEETINGITEM_CONTENT_URI, values);


            return 1;

        }catch (Exception e)
        {
            e.printStackTrace();

            return 0;
        }

    }



    public int getLastMeetingItemID(){

        String [] columns  = new String []{MeetingItemColumns._ID};
        ContentResolver resolver =  mContext. getContentResolver();
        Cursor cursor = resolver.query(ContentType.MEETINGITEM_CONTENT_URI,columns,null,null, MeetingItemColumns._ID+" DESC LIMIT 1");

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



    public int updateMeetingItem(MeetingItem aMeetItem)
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
