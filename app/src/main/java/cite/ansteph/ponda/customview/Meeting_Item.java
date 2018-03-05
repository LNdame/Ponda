package cite.ansteph.ponda.customview;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import cite.ansteph.ponda.R;
import cite.ansteph.ponda.model.MeetingItem;
import cite.ansteph.ponda.model.MeetingSubItem;

/**
 * Created by loicstephan on 2018/03/01.
 */

public class Meeting_Item extends LinearLayout {

    LinearLayout containerLyt;

    ImageButton btnOpenSub, btnCloseSub;

    TextView txtItemNumber, txtItemTitle;

    LinearLayout lytSubItemMeeting;//subItemContainer

    NestedScrollView lytSubItemContainer;
    Button btnAddSub;

    private String itemNumber, itemTitle;

    private Context mContext;

    private ArrayList<MeetingSubItem> meetingSubItems;

    private MeetingItem meetingItem;

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



    public void initViews(Context context)
    {
        LayoutInflater.from(context).inflate(R.layout.meeting_item_layout, this);
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


        btnAddSub = (Button) findViewById(R.id.btnAddSub) ;
       final SubMeeting_Item subItem = new SubMeeting_Item(context);

        btnAddSub.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                lytSubItemMeeting.addView(subItem);
            }
        });

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
        }
    }
}
