package cite.ansteph.ponda.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;

import cite.ansteph.ponda.R;
import cite.ansteph.ponda.views.meeting.MeetingHistory;

/**
 * Created by Wendy on 2018/02/22.
 */

public class MeetingHistoryRecyclerAdapter extends RecyclerView.Adapter<MeetingHistoryRecyclerAdapter.ViewHolder> {

    public ArrayList<MeetingHistory> mMeetingHistory;
    Context mContext;

    public MeetingHistoryRecyclerAdapter(ArrayList<MeetingHistory> meetingsList, MeetingHistory mContext) {
    }

    public MeetingHistoryRecyclerAdapter(ArrayList<MeetingHistory> mMeetingHistory, Context mContext) {
        this.mMeetingHistory = mMeetingHistory;
        this.mContext = mContext;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.meetingitem_list_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder,  final int position) {
        holder.mTextView.setText(mMeetingHistory.get(position).projectManagersRef());
        //   holder.mIcon.setLetter(mBookList.get(position).getTitle());

        holder.mTextView.setText(String.valueOf(mMeetingHistory.get(position)));

        //holder.mTextView.setText(String.valueOf(mMeetingHistory.get(position).getContactPerson(contactPerson))  );



        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("item", "clicked");
                Intent i = new Intent(mContext, MeetingHistory.class);
                i.putExtra("meeting", (Serializable) mMeetingHistory.get(position));
                mContext.startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mMeetingHistory.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{


        public final View mView;
        public final TextView mTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            this.mView = itemView;

            mTextView = (TextView) itemView.findViewById(android.R.id.text1);
            mView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getLayoutPosition();
            // recyclerViewClickListener.onRecyclerViewListItemClicked(v, position);


        }
    }

}
