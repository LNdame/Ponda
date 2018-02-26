package cite.ansteph.ponda.adapter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import cite.ansteph.ponda.R;
import cite.ansteph.ponda.model.Client;
import cite.ansteph.ponda.model.Project;

/**
 * Created by Nana Kwame Boateng on 2018/02/21.
 */

public class CustomProjectListAdapter extends BaseAdapter{



    Context context;

    ArrayList<Project> mProjectList;

    public CustomProjectListAdapter(ArrayList<Project> mProjectList, Context context) {
        this.mProjectList = mProjectList;
        this.context = context;

    }

    @Override
    public int getCount() {
        return mProjectList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.spin_project_list_item,parent,false);
       // ImageView icon = (ImageView) convertView.findViewById(R.id.imgicon);
        TextView name = (TextView) convertView.findViewById(R.id.tvProjectName);
        name.setText(mProjectList.get(position).getName());

        return convertView;
    }
}