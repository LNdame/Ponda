package cite.ansteph.ponda.customview;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.inqbarna.tablefixheaders.TableFixHeaders;
import com.inqbarna.tablefixheaders.adapters.BaseTableAdapter;

import java.util.ArrayList;

import cite.ansteph.ponda.R;
import cite.ansteph.ponda.adapter.tableadapter.MatrixTableAdapter;
import cite.ansteph.ponda.adapter.tableadapter.PaymentCertTableAdapter;
import cite.ansteph.ponda.model.Meeting;
import cite.ansteph.ponda.model.MeetingItem;
import cite.ansteph.ponda.model.PaymentCertificate;

/**
 * Created by loicstephan on 2018/03/05.
 */

public class PaymentCert_MeetingItem  extends LinearLayout{

    LinearLayout containerLyt;

    ImageButton btnOpenSub, btnCloseSub;

    TextView txtItemNumber, txtItemTitle;

    LinearLayout lytSubItemMeeting;//subItemContainer

    NestedScrollView lytSubItemContainer;

    TableFixHeaders mPaymentCertTable;//FixHeaders;

    ArrayList<PaymentCertificate> mPaymentCertificates ;
   PaymentCertTableAdapter tblAdapter;

    Button btnAddRow;
    private Context mContext;
    private MeetingItem meetingItem;
    private Meeting mMeeting;

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

                redrawTable();
            }
        });

    }



    void redrawTable()
    {
        mPaymentCertificates.add(new cite.ansteph.ponda.model.PaymentCertificate("Nexus One", "HTC", "Gingerbread", "10", "512 MB", "3.7\"", "512"));

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
}
