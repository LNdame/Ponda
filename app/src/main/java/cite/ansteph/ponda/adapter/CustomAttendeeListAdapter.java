package cite.ansteph.ponda.adapter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckedTextView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import cite.ansteph.ponda.R;
import cite.ansteph.ponda.model.Attendee;
import cite.ansteph.ponda.model.Client;

/**
 * Created by Wendy Luthuli on 2018/03/03.
 */

public class CustomAttendeeListAdapter extends BaseAdapter{



    Context context;

    ArrayList<Attendee> mAttendeeList;

    public CustomAttendeeListAdapter(ArrayList<Attendee> mAttendeeList, Context context) {
        this.mAttendeeList = mAttendeeList;
        this.context = context;

    }

    @Override
    public int getCount() {
        return mAttendeeList.size();
    }

    @Override
    public Object getItem(int position) {
        return mAttendeeList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.check_attendee_list_item,parent,false);
        CheckedTextView name = (CheckedTextView) convertView.findViewById(R.id.ctAttendeeName);
        name.setText(mAttendeeList.get(position).getFirstname() + " " + mAttendeeList.get(position).getSurname());

        return convertView;
    }
}