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
import cite.ansteph.ponda.views.lmeeting.ViewMeetingHistory;
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
        clientID = mMeetingHistory.get(position).getClientId();
        projectID = mMeetingHistory.get(position).getProjectId();

        client = getClient(clientID);
        project = getProject(projectID);

        holder.client.setText(client);
        holder.meetingDate.setText(String.valueOf(mMeetingHistory.get(position).getMeetingDate()));
        holder.project.setText(project  );

      holder.mView.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
                Log.d("item", "clicked");
               Intent i = new Intent(mContext, ViewMeetingHistory.class);
               i.putExtra("meeting",  mMeetingHistory.get(position));
                mContext.startActivity(i);
           }
       });

    }

    private String getProject(Integer projectID) {
        String project_name = "";
        for(Project P: mProjectList){
            if(P.getId() == projectID ){
                project_name = P.getName();

            }
        }
        return project_name;
    }

    private String getClient(Integer clientID) {
        String client_name = "";
        for(Client C: mClientList){
            if(C.getId() == clientID ){
                client_name = C.getName();

            }
        }
        return client_name;

    }

    @Override
    public int getItemCount() {
        return mMeetingHistory.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{


        public final View mView;
        public final TextView client, meetingDate, project;

        public ViewHolder(View itemView) {
            super(itemView);
            this.mView = itemView;

            client = (TextView) itemView.findViewById(R.id.client);
            meetingDate = (TextView) itemView.findViewById(R.id.meetingDate);
            project = (TextView) itemView.findViewById(R.id.project);
            mView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getLayoutPosition();
            // recyclerViewClickListener.onRecyclerViewListItemClicked(v, position);


        }
    }

}
