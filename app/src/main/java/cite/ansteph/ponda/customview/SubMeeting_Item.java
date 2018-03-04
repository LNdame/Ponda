package cite.ansteph.ponda.customview;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import cite.ansteph.ponda.R;
import cite.ansteph.ponda.model.MeetingSubItem;

/**
 * Created by loicstephan on 2018/03/01.
 */

public class SubMeeting_Item extends LinearLayout{

   TextView txtSubNumber;
   EditText edtSubItemContent, edtSubOwner;

   MeetingSubItem meetingSubItem;
   LinearLayout containerLyt;

    public SubMeeting_Item(Context context) {
        super(context);
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





}
