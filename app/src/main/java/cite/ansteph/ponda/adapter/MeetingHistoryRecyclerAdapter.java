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
import cite.ansteph.ponda.model.Client;
import cite.ansteph.ponda.model.Meeting;
import cite.ansteph.ponda.model.Project;
import cite.ansteph.ponda.views.meeting.MeetingHistory;

/**
 * Created by Wendy on 2018/02/22.
 */

public class MeetingHistoryRecyclerAdapter extends RecyclerView.Adapter<MeetingHistoryRecyclerAdapter.ViewHolder> {

    public ArrayList<Meeting> mMeetingHistory;
    Context mContext;
    String client, project;
    Integer clientID, projectID;
    public ArrayList<Client> mClientList;
    public ArrayList<Project> mProjectList;


  /*  public MeetingHistoryRecyclerAdapter(ArrayList<Meeting> meetingsList, Context mContext) {
    }*/

    public MeetingHistoryRecyclerAdapter(ArrayList<Meeting> mMeetingHistory, ArrayList<Client> mClientList, ArrayList<Project> mProjectList, Context mContext) {
        this.mMeetingHistory = mMeetingHistory;
        this.mClientList = mClientList;
        this.mProjectList = mProjectList;
        this.mContext = mContext;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.meetingitem_list_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder,  final int position) {
        //holder.mTextView.setText(mMeetingHistory.get(position).getClientId());
        //holder.mTextView.setText(String.valueOf(mMeetingHistory.get(position).getClientId()));
        clientID = mMeetingHistory.get(position).getClientId();
        projectID = mMeetingHistory.get(position).getProjectId();

        client = mClientList.get(clientID).getName();
        project = mProjectList.get(projectID).getName();

        holder.mTextView.setText(client);
        holder.mTextView1.setText(String.valueOf(mMeetingHistory.get(position).getMeetingDate()));
        holder.mTextView2.setText(project  );

//        holder.mView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Log.d("item", "clicked");
//                Intent i = new Intent(mContext, MeetingHistory.class);
//                i.putExtra("meeting", (Serializable) mMeetingHistory.get(position));
//                mContext.startActivity(i);
//            }
//        });

    }

    @Override
    public int getItemCount() {
        return mMeetingHistory.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{


        public final View mView;
        public final TextView mTextView, mTextView1, mTextView2;

        public ViewHolder(View itemView) {
            super(itemView);
            this.mView = itemView;

            mTextView = (TextView) itemView.findViewById(R.id.client);
            mTextView1 = (TextView) itemView.findViewById(R.id.meetingDate);
            mTextView2 = (TextView) itemView.findViewById(R.id.project);
            mView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getLayoutPosition();
            // recyclerViewClickListener.onRecyclerViewListItemClicked(v, position);


        }
    }

}
