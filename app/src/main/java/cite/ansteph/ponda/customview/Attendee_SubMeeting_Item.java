package cite.ansteph.ponda.customview;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

import cite.ansteph.ponda.R;
import cite.ansteph.ponda.adapter.AttendeesPoolRecyclerAdapter;
import cite.ansteph.ponda.adapter.AttendingRecyclerAdapter;
import cite.ansteph.ponda.api.ContentType;
import cite.ansteph.ponda.api.columns.AttendeeColumns;
import cite.ansteph.ponda.api.columns.MeetingItemColumns;
import cite.ansteph.ponda.api.columns.VariationOrderColumns;
import cite.ansteph.ponda.helper.RecyclerItemTouchHelper;
import cite.ansteph.ponda.listener.RecyclerViewClickListener;
import cite.ansteph.ponda.model.Attendee;
import cite.ansteph.ponda.model.Meeting;
import cite.ansteph.ponda.model.MeetingItem;
import cite.ansteph.ponda.model.VariationOrder;
import cite.ansteph.ponda.views.lmeeting.datetimepicker.RecordTimePickerFragment;

/**
 * Created by loicstephan on 2018/03/04.
 */

public class Attendee_SubMeeting_Item  extends LinearLayout implements RecyclerViewClickListener,RecyclerItemTouchHelper.RecyclerItemTouchHelperListener {

    final static String TAG   = Attendee_SubMeeting_Item.class.getSimpleName();

    ImageButton btnOpenSub, btnCloseSub;

    TextView txtItemNumber, txtItemTitle;

    LinearLayout lytSubItemMeeting;//subItemContainer

    NestedScrollView lytSubItemContainer;
    RecyclerView attendeeRecyclerView, poolRecyclerView;

    AttendeesPoolRecyclerAdapter mAttendeesPoolAdapter;
    AttendingRecyclerAdapter mAttendingAdapter;

    private ArrayList<Attendee> mAttendeesPool;
    private ArrayList<Attendee> mAttendingPool;

    HashMap<Integer, Attendee> AttendeePresent;
    LinearLayout containerLyt;
    private Context mContext;

    private MeetingItem meetingItem;

    private Meeting mMeeting;

    public Attendee_SubMeeting_Item(Context context) {
        super(context);
        mContext = context;
        initViews( context);
    }

    public Attendee_SubMeeting_Item(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public Attendee_SubMeeting_Item(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    public  void initViews(Context context)
    {
        LayoutInflater.from(context).inflate(R.layout.attendees_meeting_item, this);
        containerLyt = (LinearLayout) findViewById(R.id.meetingLytContainer);

        lytSubItemContainer =(NestedScrollView) findViewById(R.id.subItemContainer);

        lytSubItemMeeting = (LinearLayout) findViewById(R.id.subItemMeetingLyt);

        txtItemNumber= (TextView) findViewById(R.id.txtItemNumber);
        txtItemTitle= (TextView) findViewById(R.id.txtItemTitle);


        btnOpenSub = (ImageButton) findViewById(R.id.imgbtnOpenSub);
        btnCloseSub= (ImageButton) findViewById(R.id.imgbtnCloseSub);


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


         FragmentManager fragmentManager = ((FragmentActivity) context).getSupportFragmentManager();

        AttendeePresent = new HashMap<>();
        //recycler view init
        mAttendeesPool = new ArrayList<>();
        mAttendingPool = new ArrayList<>();

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);


        attendeeRecyclerView = (RecyclerView) findViewById(R.id.attendeerecyclerview);
        poolRecyclerView = (RecyclerView) findViewById(R.id.poolrecyclerview);
        attendeeRecyclerView.setLayoutManager(mLayoutManager);
        poolRecyclerView.setLayoutManager(new LinearLayoutManager(poolRecyclerView.getContext()));

        mAttendeesPool=retrieveAttendee();

        mAttendeesPoolAdapter= new AttendeesPoolRecyclerAdapter(this,mAttendeesPool,context);
        poolRecyclerView.setAdapter(mAttendeesPoolAdapter);

        mAttendingAdapter = new AttendingRecyclerAdapter(mAttendingPool, context);
        attendeeRecyclerView.setAdapter(mAttendingAdapter);



        //Implement swipe remove

        // adding item touch helper
        // only ItemTouchHelper.LEFT added to detect Right to Left swipe
        // if you want both Right -> Left and Left -> Right
        // add pass ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT as param
        ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new RecyclerItemTouchHelper(0, ItemTouchHelper.LEFT, this);
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(attendeeRecyclerView);


        ItemTouchHelper.SimpleCallback itemTouchHelperCallback1 = new  ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT | ItemTouchHelper.UP){

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {

            }

            @Override
            public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }
        };


        // attaching the touch helper to recycler view
        new ItemTouchHelper(itemTouchHelperCallback1).attachToRecyclerView(attendeeRecyclerView);
    }


    //delete after use
    ArrayList<Attendee> setupList()
    {
        ArrayList<Attendee>  arrayL = new ArrayList<>();

        arrayL.add(new Attendee (1,"Tony Company","John Black"));
        arrayL.add(new Attendee (2,"Goodwill Construction","Pete Jameson"));
        arrayL.add(new Attendee (3,"Turin Plet Arch","Jason Jack "));



        // String duration, String task_date, String start, String end, String project, String description, String realduration, String task_break) {
        return  arrayL;
    }




    @Override
    public void onRecyclerViewItemClicked(View v, int position) {

        boolean shouldAdd  = true;

        for(Attendee attendee: mAttendingPool) {
            if (attendee.getId()==mAttendeesPool.get(position).getId())
                shouldAdd =false;
        }
        if(shouldAdd) {

          //  mPrefCount++;
            AttendeePresent.put(position, mAttendeesPool.get(position));

            Attendee mAtt = mAttendeesPool.get(position);

            //adding to DB
            int i = insertAttendeeItem(mAttendeesPool.get(position));
            if(i==1)
                mAtt.setId(getLastAttendeeID());


            mAttendingPool.add(mAtt);
            // refreshing recycler view
            mAttendingAdapter.notifyDataSetChanged();
           // String ct = "(" + (3 - mPrefCount) + " more)";
            //txtPrefCount.setText(ct);
        }



        Toast.makeText(mContext, "Item Clicked",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {
        if(viewHolder instanceof  AttendingRecyclerAdapter.AttViewHolder)
        {
            // get the removed item name to display it in snack bar
            String name = mAttendingPool.get(viewHolder.getAdapterPosition()).getFirstname();

            // backup of removed item for undo purpose
            final Attendee deletedItem = mAttendingPool.get(viewHolder.getAdapterPosition());
            final int deletedIndex = viewHolder.getAdapterPosition();


            // remove the item from recycler view
            mAttendingAdapter.removeItem(viewHolder.getAdapterPosition());
            deleteAttendee(deletedItem);
           /* mPrefCount--;
          //  String ct = "(" +(3-mPrefCount)+" more)";
           // txtPrefCount.setText(ct);

            // showing snack bar with Undo option
            Snackbar snackbar = Snackbar
                    .make(container, name + " removed from cart!", Snackbar.LENGTH_LONG);
            snackbar.setAction("UNDO", new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    // undo is selected, restore the deleted item
                    mPrefBeerAdapter.restoreItem(deletedItem, deletedIndex);

                    mPrefCount++;
                    String ct = "(" +(3-mPrefCount)+" more)";
                    txtPrefCount.setText(ct);
                }
            });
            snackbar.setActionTextColor(Color.YELLOW);
            snackbar.show();
            */
        }
    }



    private ArrayList<Attendee> retrieveAttendee()
    {
        ContentResolver resolver =mContext. getContentResolver();

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




    public int insertAttendeeItem(Attendee attendee){

        try {
            ContentValues values = new ContentValues();

            values.put(AttendeeColumns.FIRSTNAME,attendee.getFirstname()) ;
            values.put(AttendeeColumns.SURNAME , attendee.getSurname()) ;
            values.put(AttendeeColumns.ORGANSATION, attendee.getOrganisation()) ;
            values.put(AttendeeColumns.TELEPHONE, attendee.getTelephone()) ;
            values.put(AttendeeColumns.CELLPHONE, attendee.getCellphone()) ;
            values.put(AttendeeColumns.FAX, attendee.getFax()) ;
            values.put(AttendeeColumns.EMAIL,attendee.getEmail()) ;


            mContext. getContentResolver().insert(ContentType.ATTENDEE_CONTENT_URI, values);


            return 1;

        }catch (Exception e)
        {
            e.printStackTrace();

            return 0;
        }

    }



    public int getLastAttendeeID(){

        String [] columns  = new String []{AttendeeColumns._ID};
        ContentResolver resolver =  mContext. getContentResolver();
        Cursor cursor = resolver.query(ContentType.ATTENDEE_CONTENT_URI,columns,null,null, AttendeeColumns._ID+" DESC LIMIT 1");

        int lastId =0;

        if(cursor !=null && cursor.moveToFirst())
        {
            lastId =(cursor.getString(0))!=null ? Integer.parseInt(cursor.getString(0)):0;

            //mGlobalRetainer.get_grCurrentAudit().set_id(lastId);
            Log.d(TAG, String.valueOf(lastId) );
        }
        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }

        return lastId;
    }


    public void deleteAttendee(Attendee attendee){

        String item_id = String.valueOf( attendee.getId());

        mContext. getContentResolver().delete(ContentType.ATTENDEE_CONTENT_URI, AttendeeColumns._ID+" =?", new String[]{item_id});

        Log.d(TAG, item_id+" deleted" );

    }



    //////******************** Meeting Housekeeping *******/////////////
    //this should have been overrriden from Meeting (This is lane Design////////

    //Do not forget that Variation Order is just a special meeting object (Maybe u should have use a decorator here)




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




}
