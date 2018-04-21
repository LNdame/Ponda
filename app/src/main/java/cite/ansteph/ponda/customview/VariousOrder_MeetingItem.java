package cite.ansteph.ponda.customview;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.inqbarna.tablefixheaders.TableFixHeaders;

import java.util.ArrayList;
import java.util.HashMap;

import cite.ansteph.ponda.R;
import cite.ansteph.ponda.adapter.AttendingRecyclerAdapter;
import cite.ansteph.ponda.adapter.tableadapter.MatrixTableAdapter;
import cite.ansteph.ponda.adapter.tableadapter.VariationOrderRecAdapter;

import cite.ansteph.ponda.api.ContentType;
import cite.ansteph.ponda.api.columns.MeetingItemColumns;
import cite.ansteph.ponda.api.columns.PaymentCertificateColumns;
import cite.ansteph.ponda.api.columns.VariationOrderColumns;
import cite.ansteph.ponda.helper.VariationRecyclerItemTouchHelper;
import cite.ansteph.ponda.model.Attendee;
import cite.ansteph.ponda.model.Meeting;
import cite.ansteph.ponda.model.MeetingItem;
import cite.ansteph.ponda.model.MeetingSubItem;
import cite.ansteph.ponda.model.PaymentCertificate;
import cite.ansteph.ponda.model.VariationOrder;

/**
 * Created by loicstephan on 2018/03/05.
 */

public class VariousOrder_MeetingItem extends LinearLayout implements VariationRecyclerItemTouchHelper.RecyclerItemTouchHelperListener {

    final static String TAG   = VariousOrder_MeetingItem.class.getSimpleName();

    LinearLayout containerLyt;

    ImageButton btnOpenSub, btnCloseSub;

    TextView txtItemNumber, txtItemTitle;

    LinearLayout lytSubItemMeeting;//subItemContainer

    NestedScrollView lytSubItemContainer;

    TableFixHeaders variousOrderTable;//FixHeaders;

    RecyclerView variationRecyclerView;
    HashMap<Integer, VariationOrder> variationAdded;

    VariationOrderRecAdapter mVariationAdapter;
    private ArrayList<VariationOrder> mVariationOrderPool;

    Button btnAddRow;

    Button btnAddSub;

    int positionCount;

    private Context mContext;
    private MeetingItem meetingItem;
    private Meeting mMeeting;

    EditText edtvarOrder,edtMotivation,edtApproved,edtOmit,edtAdd,edtBalance;
    private ArrayList<SubMeeting_Item> SubItemLayoutList;
    int subMeetingItemCount=0;

    public VariousOrder_MeetingItem(Context context) {
        super(context);
        mContext = context;
        initViews( context);
    }

    public VariousOrder_MeetingItem(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        //  initViews( context, attrs);
    }

    public VariousOrder_MeetingItem(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }



    public void initViews(final Context context)
    {
        LayoutInflater.from(context).inflate(R.layout.various_order_item, this);
        containerLyt = (LinearLayout) findViewById(R.id.meetingLytContainer);

        lytSubItemContainer =(NestedScrollView) findViewById(R.id.subItemContainer);

        lytSubItemMeeting = (LinearLayout) findViewById(R.id.subItemMeetingLyt);

        SubItemLayoutList = new ArrayList<>();

        txtItemNumber= (TextView) findViewById(R.id.txtItemNumber);
        txtItemTitle= (TextView) findViewById(R.id.txtItemTitle);


        btnOpenSub = (ImageButton) findViewById(R.id.imgbtnOpenSub);
        btnCloseSub= (ImageButton) findViewById(R.id.imgbtnCloseSub);

        variousOrderTable =  (TableFixHeaders) findViewById(R.id.table);

        edtvarOrder = (EditText) findViewById(R.id.edtvarOrder );
        edtMotivation= (EditText) findViewById(R.id.edtMotivation );
        edtApproved= (EditText) findViewById(R.id.edtApproved );
        edtOmit= (EditText) findViewById(R.id.edtOmit );
        edtAdd= (EditText) findViewById(R.id.edtAdd );
        edtBalance= (EditText) findViewById(R.id.edtBalance );;

        mVariationOrderPool = new ArrayList<>();
        variationAdded= new HashMap<>();

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
        variationRecyclerView = (RecyclerView) findViewById(R.id.varOrderrecyclerview);
        variationRecyclerView.setLayoutManager(mLayoutManager);


        positionCount= 0;
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


        btnAddRow = (Button) findViewById(R.id.btnAddVar);

        btnAddRow.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                VariationOrder varOrder = new VariationOrder();


                String varOrd1=  edtvarOrder.getText().toString();
                String varOrd2=  edtMotivation.getText().toString();
                String varOrd3=  edtApproved.getText().toString();
                String varOrd4=  edtOmit.getText().toString();
                String varOrd5=  edtAdd.getText().toString();
                String varOrd6=  edtBalance.getText().toString();

                // varOrder.set
                varOrder.setVariationOrder(varOrd1);
                varOrder.setMotivation(varOrd2);
                varOrder.setApproved(varOrd3);
                varOrder.setOmit(Double.valueOf(varOrd4));
                varOrder.setAdd(Double.valueOf(varOrd5));
                varOrder.setBalance(Double.valueOf(varOrd6));

                //  mPrefCount++;
                variationAdded.put(positionCount, varOrder);

                int i = insertVariationOrder(varOrder);
                if(i==1)
                    varOrder.setId(getLastVarOrderItemID());



                mVariationOrderPool.add(varOrder);
                // refreshing recycler view
                mVariationAdapter.notifyDataSetChanged();
                // String ct = "(" + (3 - mPrefCount) + " more)";
                //txtPrefCount.setText(ct);
            }
        });


        btnAddSub = (Button) findViewById(R.id.btnAddSub) ;

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



        mVariationAdapter = new VariationOrderRecAdapter(mVariationOrderPool, context);
        variationRecyclerView.setAdapter(mVariationAdapter);

        //Implement swipe remove

        // adding item touch helper
        // only ItemTouchHelper.LEFT added to detect Right to Left swipe
        // if you want both Right -> Left and Left -> Right
        // add pass ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT as param
        ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new VariationRecyclerItemTouchHelper(0, ItemTouchHelper.LEFT, this);
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(variationRecyclerView);


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
        new ItemTouchHelper(itemTouchHelperCallback1).attachToRecyclerView(variationRecyclerView);


    }








    public int insertVariationOrder(VariationOrder aVariationOrder){

        try {
            ContentValues values = new ContentValues();


            values.put(VariationOrderColumns.VARIATIONORDER, aVariationOrder.getVariationOrder()) ;
            values.put(VariationOrderColumns.MOTIVATION, aVariationOrder.getMotivation()) ;
            values.put(VariationOrderColumns.APPROVED , aVariationOrder.getApproved()) ;
            values.put(VariationOrderColumns.OMIT , aVariationOrder.getOmit()) ;
            values.put(VariationOrderColumns.ADD , aVariationOrder.getAdd()) ;
            values.put(VariationOrderColumns.BALANCE , aVariationOrder.getBalance()) ;
            //values.put(VariationOrderColumns.AMOUNT ,aPaymentCertificate.getAmount()) ;

            values.put(VariationOrderColumns.PROJECT_ID, meetingItem.getMeeting().getProjectId()) ; // ***reactivate when meeting has a proper proejct id
            values.put(VariationOrderColumns.MEETING_ID,meetingItem.getMeeting().getId()) ;
            values.put(VariationOrderColumns.MEETINGITEM_ID,meetingItem.getId()) ;
            //values.put(PaymentCertificateColumns.MEETINGSUBITEM_ID,aPaymentCertificate.getMeetingItem().getId()) ;

            mContext. getContentResolver().insert(ContentType.VARIATIONORDER_CONTENT_URI, values);



            return 1;

        }catch (Exception e)
        {
            e.printStackTrace();

            return 0;
        }

    }



    public void deleteVariationOrder(VariationOrder aVariationOrder){

        String item_id = String.valueOf( aVariationOrder.getId());

        mContext. getContentResolver().delete(ContentType.VARIATIONORDER_CONTENT_URI, VariationOrderColumns._ID+" =?", new String[]{item_id});

        Log.d(TAG, item_id+" deleted" );

    }



    /* String VARIATIONORDER  = "variationorder";
    String MOTIVATION  = "motivation";
    String APPROVED  = "approved";
    String OMIT  = "omit";
    String ADD = "add";
    String BALANCE = "balance";

    String PROJECT_ID = "project_id";

    String MEETING_ID = "meeting_id";
    String MEETINGITEM_ID  = "meetingitem_id";
    String MEETINGSUBITEM_ID  = "meetingsubitem_id";;*/


    public int getLastVarOrderItemID(){

        String [] columns  = new String []{VariationOrderColumns._ID};
        ContentResolver resolver =  mContext. getContentResolver();
        Cursor cursor = resolver.query(ContentType.VARIATIONORDER_CONTENT_URI,columns,null,null, VariationOrderColumns._ID+" DESC LIMIT 1");

        int lastId =0;

        if(cursor !=null && cursor.moveToFirst())
        {
            lastId =(cursor.getString(0))!=null ? Integer.parseInt(cursor.getString(0)):0;
            // meetingItem.setId( lastId); in this case should be the last payment certificate row
            //mGlobalRetainer.get_grCurrentAudit().set_id(lastId);
            Log.d(TAG, String.valueOf(lastId) );
        }
        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }

        return lastId;
    }




    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {
        if(viewHolder instanceof VariationOrderRecAdapter.VarViewHolder)
        {
            // get the removed item name to display it in snack bar
            String name = mVariationOrderPool.get(viewHolder.getAdapterPosition()).getVariationOrder();

            // backup of removed item for undo purpose
            final VariationOrder deletedItem = mVariationOrderPool.get(viewHolder.getAdapterPosition());
            final int deletedIndex = viewHolder.getAdapterPosition();


            // remove the item from recycler view
            mVariationAdapter.removeItem(viewHolder.getAdapterPosition());

            deleteVariationOrder(deletedItem);
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
