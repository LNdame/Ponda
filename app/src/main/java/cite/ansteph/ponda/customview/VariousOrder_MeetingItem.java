package cite.ansteph.ponda.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.AttributeSet;
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

import cite.ansteph.ponda.helper.VariationRecyclerItemTouchHelper;
import cite.ansteph.ponda.model.Attendee;
import cite.ansteph.ponda.model.Meeting;
import cite.ansteph.ponda.model.MeetingItem;
import cite.ansteph.ponda.model.MeetingSubItem;
import cite.ansteph.ponda.model.VariationOrder;

/**
 * Created by loicstephan on 2018/03/05.
 */

public class VariousOrder_MeetingItem extends LinearLayout implements VariationRecyclerItemTouchHelper.RecyclerItemTouchHelperListener {

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


                mVariationOrderPool.add(varOrder);
                // refreshing recycler view
                mVariationAdapter.notifyDataSetChanged();
                // String ct = "(" + (3 - mPrefCount) + " more)";
                //txtPrefCount.setText(ct);
            }
        });

        setupTable();
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


    void setupTable()
    {
        MatrixTableAdapter<String> matrixTableAdapter = new MatrixTableAdapter<String>(mContext, new String[][] {
                {
                        "Header 1",
                        "Header 2",
                        "Header 3",
                        "Header 4",
                        "Header 5",
                        "Header 6" },
                {
                        "Lorem",
                        "sed",
                        "do",
                        "eiusmod",
                        "tempor",
                        "incididunt" },
                {
                        "ipsum",
                        "irure",
                        "occaecat",
                        "enim",
                        "laborum",
                        "reprehenderit" },
                {
                        "dolor",
                        "fugiat",
                        "nulla",
                        "reprehenderit",
                        "laborum",
                        "consequat" },
                {
                        "sit",
                        "consequat",
                        "laborum",
                        "fugiat",
                        "eiusmod",
                        "enim" },
                {
                        "amet",
                        "nulla",
                        "Excepteur",
                        "voluptate",
                        "occaecat",
                        "et" },
                {
                        "consectetur",
                        "occaecat",
                        "fugiat",
                        "dolore",
                        "consequat",
                        "eiusmod" },
                {
                        "adipisicing",
                        "fugiat",
                        "Excepteur",
                        "occaecat",
                        "fugiat",
                        "laborum" },
                {
                        "elit",
                        "voluptate",
                        "reprehenderit",
                        "Excepteur",
                        "fugiat",
                        "nulla" },
        });
        variousOrderTable.setAdapter(matrixTableAdapter);
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
        }
    }

    public Meeting getmMeeting() {
        return mMeeting;
    }

    public void setmMeeting(Meeting mMeeting) {
        this.mMeeting = mMeeting;
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
}
