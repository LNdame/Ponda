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
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.inqbarna.tablefixheaders.TableFixHeaders;
import com.inqbarna.tablefixheaders.adapters.BaseTableAdapter;

import java.util.ArrayList;
import java.util.HashMap;

import cite.ansteph.ponda.R;
import cite.ansteph.ponda.adapter.tableadapter.MatrixTableAdapter;
import cite.ansteph.ponda.adapter.tableadapter.PaymentCertRecAdapter;
import cite.ansteph.ponda.adapter.tableadapter.PaymentCertTableAdapter;
import cite.ansteph.ponda.adapter.tableadapter.VariationOrderRecAdapter;
import cite.ansteph.ponda.api.ContentType;
import cite.ansteph.ponda.api.columns.MeetingSubItemColumns;
import cite.ansteph.ponda.api.columns.PaymentCertificateColumns;
import cite.ansteph.ponda.helper.PaymentCertRecyclerItemTouchHelper;
import cite.ansteph.ponda.helper.VariationRecyclerItemTouchHelper;
import cite.ansteph.ponda.model.Meeting;
import cite.ansteph.ponda.model.MeetingItem;
import cite.ansteph.ponda.model.MeetingSubItem;
import cite.ansteph.ponda.model.PaymentCertificate;
import cite.ansteph.ponda.model.VariationOrder;

/**
 * Created by loicstephan on 2018/03/05.
 */

public class PaymentCert_MeetingItem  extends LinearLayout implements PaymentCertRecyclerItemTouchHelper.RecyclerItemTouchHelperListener{

    final static String TAG   = PaymentCert_MeetingItem.class.getSimpleName();

    LinearLayout containerLyt;

    ImageButton btnOpenSub, btnCloseSub;

    TextView txtItemNumber, txtItemTitle;

    LinearLayout lytSubItemMeeting;//subItemContainer

    NestedScrollView lytSubItemContainer;

    TableFixHeaders mPaymentCertTable;//FixHeaders;
    PaymentCertTableAdapter tblAdapter;

    ArrayList<PaymentCertificate> mPaymentCertificates ;



    RecyclerView PayCertRecyclerView;
    HashMap<Integer, PaymentCertificate> PayCertAdded;

    PaymentCertRecAdapter mPayCertAdapter;
  //  private ArrayList<VariationOrder> mVariationOrderPool;

    int positionCount;

    Button btnAddRow;
    private Context mContext;
    private MeetingItem meetingItem;
    private Meeting mMeeting;


    EditText edtPayment,edtIssueDate,edtPaid,edtDateDue,edtDayLate,edtSigned,edtAmount;

    public PaymentCert_MeetingItem(Context context) {
        super(context);
        mContext = context;
        initViews( context);
    }

    public PaymentCert_MeetingItem(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public PaymentCert_MeetingItem(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void initViews(Context context)
    {
        LayoutInflater.from(context).inflate(R.layout.payment_cert_meeting_item, this);
        containerLyt = (LinearLayout) findViewById(R.id.meetingLytContainer);

        lytSubItemContainer =(NestedScrollView) findViewById(R.id.subItemContainer);

        lytSubItemMeeting = (LinearLayout) findViewById(R.id.subItemMeetingLyt);

        txtItemNumber= (TextView) findViewById(R.id.txtItemNumber);
        txtItemTitle= (TextView) findViewById(R.id.txtItemTitle);

        edtPayment=(EditText) findViewById(R.id.edtPayment) ;
                edtIssueDate=(EditText) findViewById(R.id.edtIssueDate) ;
                edtPaid=(EditText) findViewById(R.id.edtPaid) ;
                edtDateDue=(EditText) findViewById(R.id.edtDateDue) ;
                edtDayLate=(EditText) findViewById(R.id.edtDayLate) ;
                edtSigned=(EditText) findViewById(R.id.edtSigned) ;
                edtAmount=(EditText) findViewById(R.id.edtAmount) ;


        btnOpenSub = (ImageButton) findViewById(R.id.imgbtnOpenSub);
        btnCloseSub= (ImageButton) findViewById(R.id.imgbtnCloseSub);
        btnAddRow = (Button) findViewById(R.id.btnAddtblRow);


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

        mPaymentCertificates = new ArrayList<>();

        PayCertAdded= new HashMap<>();

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
        PayCertRecyclerView = (RecyclerView) findViewById(R.id.payCertrecyclerview);
        PayCertRecyclerView.setLayoutManager(mLayoutManager);

        mPayCertAdapter = new PaymentCertRecAdapter(mPaymentCertificates, context);
        PayCertRecyclerView.setAdapter(mPayCertAdapter);


        //Implement swipe remove

        // adding item touch helper
        // only ItemTouchHelper.LEFT added to detect Right to Left swipe
        // if you want both Right -> Left and Left -> Right
        // add pass ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT as param
        ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new PaymentCertRecyclerItemTouchHelper(0, ItemTouchHelper.LEFT, this);
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(PayCertRecyclerView);


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
        new ItemTouchHelper(itemTouchHelperCallback1).attachToRecyclerView(PayCertRecyclerView);


       // paymentCertificates.add(new cite.ansteph.ponda.model.PaymentCertificate("Nexus One", "HTC", "Gingerbread", "10", "512 MB", "3.7\"", "512"));
       // paymentCertificates.add(new cite.ansteph.ponda.model.PaymentCertificate("Nexus S", "Samsung", "Gingerbread", "10", "16 GB", "4\"", "512 MB"));
        //paymentCertificates.add(new cite.ansteph.ponda.model.PaymentCertificate("Galaxy Nexus (16 GB)", "Samsung", "Ice cream Sandwich", "15", "16 GB", "4.65\"", "1 GB"));


        mPaymentCertTable =  (TableFixHeaders) findViewById(R.id.table);

        tblAdapter = new PaymentCertTableAdapter(context,mPaymentCertificates);
        mPaymentCertTable.setAdapter(tblAdapter);
       // setupTable();


        btnAddRow.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                PaymentCertificate pay = new PaymentCertificate();
                pay.setPaymentcertificate(edtPayment.getText().toString());
                pay.setIssueDate(edtIssueDate.getText().toString());
                pay.setPaid(edtPaid.getText().toString());
                pay.setDateDue(edtDateDue.getText().toString());
                pay.setDayLate(edtDayLate.getText().toString());
                pay.setSignedCopy(edtSigned.getText().toString());
                pay.setAmountString(edtAmount.getText().toString());

                //  mPrefCount++;
                PayCertAdded.put(positionCount, pay);


                mPaymentCertificates.add(pay);
                // refreshing recycler view
                mPayCertAdapter.notifyDataSetChanged();
                // String ct = "(" + (3 - mPrefCount) + " more)";


                //redrawTable(pay);
            }
        });

    }



    void redrawTable(PaymentCertificate pay)
    {



       String payNo= edtPayment.getText().toString();
        String issudate=edtIssueDate.getText().toString();
        String f3=edtPaid.getText().toString();
        String f4=edtDateDue.getText().toString();
        String f5=edtDayLate.getText().toString();
        String f6=edtSigned.getText().toString();
        String f7=edtAmount.getText().toString();

        mPaymentCertificates.add(new cite.ansteph.ponda.model.PaymentCertificate(payNo, issudate, f3, f4, f5, f6, f6));
      // mPaymentCertificates.add(pay);

        tblAdapter = new PaymentCertTableAdapter(mContext,mPaymentCertificates);
        mPaymentCertTable.setAdapter(tblAdapter);
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
        mPaymentCertTable.setAdapter(matrixTableAdapter);
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



    public int insertPaymentCertificate(PaymentCertificate aPaymentCertificate){

        try {
            ContentValues values = new ContentValues();


            values.put(PaymentCertificateColumns.PAYMENTCERTIFICATE,aPaymentCertificate.getPaymentcertificate()) ;
            values.put(PaymentCertificateColumns.ISSUE_DATE,aPaymentCertificate.getIssueDate()) ;
            values.put(PaymentCertificateColumns.PAID ,aPaymentCertificate.getPaid()) ;
            values.put(PaymentCertificateColumns.DATE_DUE ,aPaymentCertificate.getDateDue()) ;
            values.put(PaymentCertificateColumns.DAY_LATE ,aPaymentCertificate.getDayLate()) ;
            values.put(PaymentCertificateColumns.SIGNED_COPY ,aPaymentCertificate.getSignedCopy()) ;
            values.put(PaymentCertificateColumns.AMOUNT ,aPaymentCertificate.getAmount()) ;

           // values.put(PaymentCertificateColumns.PROJECT_ID, aPaymentCertificate.getMeetingItem().getId()) ;
            values.put(PaymentCertificateColumns.MEETING_ID,mMeeting.getId()) ;
            values.put(PaymentCertificateColumns.MEETINGITEM_ID,meetingItem.getId()) ;
            //values.put(PaymentCertificateColumns.MEETINGSUBITEM_ID,aPaymentCertificate.getMeetingItem().getId()) ;

            mContext. getContentResolver().insert(ContentType.PAYMENTCERTIFICATE_CONTENT_URI, values);
            return 1;

        }catch (Exception e)
        {
            e.printStackTrace();

            return 0;
        }

    }



    public void DeletePaymentCertificate(PaymentCertificate aPaymentCertificate){

    }



    /*String PAYMENTCERTIFICATE  = "paymentcertificate";
    String ISSUE_DATE  = "issue_date";
    String PAID = "paid";
    String DATE_DUE = "date_due";
    String DAY_LATE = "day_late";
    String SIGNED_COPY = "signed_copy";
    String AMOUNT = "amount";
    String PROJECT_ID = "project_id";

    String MEETING_ID = "meeting_id";
    String MEETINGITEM_ID  = "meetingitem_id";
    String MEETINGSUBITEM_ID  = "meetingsubitem_id";*/


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



    public int updateMeetingSubItem(PaymentCertificate aPaymentCertificate)
    {
        //String meet_id = String.valueOf( aMeetSubItem.getId());

        try {
            ContentValues values = new ContentValues();



            values.put(PaymentCertificateColumns.PAYMENTCERTIFICATE,aPaymentCertificate.getPaymentcertificate()) ;
            values.put(PaymentCertificateColumns.ISSUE_DATE,aPaymentCertificate.getIssueDate()) ;
            values.put(PaymentCertificateColumns.PAID ,aPaymentCertificate.getPaid()) ;
            values.put(PaymentCertificateColumns.DATE_DUE ,aPaymentCertificate.getDateDue()) ;
            values.put(PaymentCertificateColumns.DAY_LATE ,aPaymentCertificate.getDayLate()) ;
            values.put(PaymentCertificateColumns.SIGNED_COPY ,aPaymentCertificate.getSignedCopy()) ;
            values.put(PaymentCertificateColumns.AMOUNT ,aPaymentCertificate.getAmount()) ;

            // values.put(PaymentCertificateColumns.PROJECT_ID, aPaymentCertificate.getMeetingItem().getId()) ;
            values.put(PaymentCertificateColumns.MEETING_ID,mMeeting.getId()) ;
            values.put(PaymentCertificateColumns.MEETINGITEM_ID,meetingItem.getId()) ;


            mContext.getContentResolver().update(ContentType.MEETINGSUBITEM_CONTENT_URI, values, MeetingSubItemColumns._ID+" =?", new String[]{""});


            return 1;

        }catch (Exception e)
        {
            e.printStackTrace();

            return 0;
        }

    }


    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {

    }
}
