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
import cite.ansteph.ponda.listener.RecyclerViewClickListener;
import cite.ansteph.ponda.model.Attendee;

/**
 * Created by loicstephan on 2018/03/04.
 */

public class AttendeesPoolRecyclerAdapter extends RecyclerView.Adapter<AttendeesPoolRecyclerAdapter.ViewHolder> {


    RecyclerViewClickListener recyclerViewClickListener;

    private ArrayList<Attendee> attendees;

    Context mContext;

    public AttendeesPoolRecyclerAdapter(RecyclerViewClickListener recyclerViewClickListener, ArrayList<Attendee> attendees, Context mContext) {
        this.recyclerViewClickListener = recyclerViewClickListener;
        this.attendees = attendees;
        this.mContext = mContext;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.attendee_pool_item, parent, false);

        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder .txtAttendeePoolitem.setText(attendees.get(position).getFirstname() +" "+attendees.get(position).getSurname());


        final View itemView = holder.itemView;

    }

    @Override
    public int getItemCount() {
        return attendees.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public final View mView;
        public final TextView txtAttendeePoolitem;
        public final ImageView imgAttSelect;

        // public final RadioButton imgLogo;imgBeerSelect

        public ViewHolder(View itemView) {
            super(itemView);
            this.mView = itemView;
            this.txtAttendeePoolitem =(TextView) itemView.findViewById(R.id.txtAttendeePoolitem);
            this.imgAttSelect = (ImageView)itemView.findViewById(R.id.imgAttSelect) ;
            mView.setOnClickListener(this);
        }

        @Override
        public String toString() {
            return super.toString();
        }

        @Override
        public void onClick(View v) {
            int position =getLayoutPosition();
            recyclerViewClickListener.onRecyclerViewItemClicked(v, position);
        }
    }
}
