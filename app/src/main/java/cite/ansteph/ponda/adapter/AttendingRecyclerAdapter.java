package cite.ansteph.ponda.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import cite.ansteph.ponda.R;
import cite.ansteph.ponda.model.Attendee;

/**
 * Created by loicstephan on 2018/03/04.
 */

public class AttendingRecyclerAdapter extends RecyclerView.Adapter<AttendingRecyclerAdapter.AttViewHolder>{

    private ArrayList<Attendee> attendees;

    Context mContext;

    public AttendingRecyclerAdapter(ArrayList<Attendee> attendees, Context mContext) {
        this.attendees = attendees;
        this.mContext = mContext;
    }

    @Override
    public AttViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.attendeeing_item, parent, false);

        return new AttViewHolder(view);

    }

    @Override
    public void onBindViewHolder(AttViewHolder holder, int position) {
        holder .txtAttendeeitem.setText(attendees.get(position).getFirstname() +" "+attendees.get(position).getSurname());


        final View itemView = holder.itemView;
    }

    @Override
    public int getItemCount() {
        return attendees.size();
    }

    public void removeItem(int position) {
        attendees.remove(position);
        // notify the item removed by position
        // to perform recycler view delete animations
        // NOTE: don't call notifyDataSetChanged()
        notifyItemRemoved(position);
    }

    public void restoreItem(Attendee item, int position) {
        attendees.add(position, item);
        // notify item added by position
        notifyItemInserted(position);
    }


    public class AttViewHolder extends RecyclerView.ViewHolder {


        public RelativeLayout viewBackground, viewForeground;

        public final TextView txtAttendeeitem;
        public final ImageView imgAttendeeSelect;

        public AttViewHolder(View view) {
            super(view);
            this.txtAttendeeitem =(TextView) itemView.findViewById(R.id.txtAttendeeitem);
            this.imgAttendeeSelect = (ImageView)itemView.findViewById(R.id.imgAttendeeSelect) ;

            viewBackground = view.findViewById(R.id.view_background);
            viewForeground = view.findViewById(R.id.view_foreground);
        }
    }

}
