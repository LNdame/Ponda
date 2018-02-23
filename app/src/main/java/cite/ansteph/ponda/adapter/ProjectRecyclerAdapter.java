package cite.ansteph.ponda.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import cite.ansteph.ponda.R;
import cite.ansteph.ponda.model.Client;
import cite.ansteph.ponda.model.Project;
import cite.ansteph.ponda.views.project.EditProject;

/**
 * Created by loicstephan on 2018/02/16.
 */

public class ProjectRecyclerAdapter extends RecyclerView.Adapter<ProjectRecyclerAdapter.ViewHolder> {

    ArrayList<Project> mProjects;
    Context mContext;

    public ProjectRecyclerAdapter(Activity activity, ArrayList<Client> mClientlist) {
    }

    public ProjectRecyclerAdapter(ArrayList<Project> mProjects, Context mContext) {
        this.mProjects = mProjects;
        this.mContext = mContext;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.project_list_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder,  final int position) {
        holder.mTextView.setText(mProjects.get(position).getName());
        //   holder.mIcon.setLetter(mBookList.get(position).getTitle());

        holder.mAddText.setText(String.valueOf(mProjects.get(position).getProjManName())  );

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("item", "clicked");
                Intent i = new Intent(mContext, EditProject.class);
                i.putExtra("project", mProjects.get(position));
                mContext.startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() { return mProjects.size();}

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{


        public final View mView;
        public final ImageView mIcon;
        public final TextView mTextView;
        public final TextView mAddText;

        public ViewHolder(View itemView) {
            super(itemView);
            this.mView = itemView;

            mIcon = (ImageView) itemView.findViewById(R.id.imgicon);
            mTextView = (TextView) itemView.findViewById(android.R.id.text1);
            mAddText = (TextView) itemView.findViewById(R.id.text_message_email);
            mView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getLayoutPosition();
            // recyclerViewClickListener.onRecyclerViewListItemClicked(v, position);


        }

    }

}
