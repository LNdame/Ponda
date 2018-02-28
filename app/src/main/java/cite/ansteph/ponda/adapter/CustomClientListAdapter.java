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

/**
 * Created by Nana Kwame Boateng on 2018/02/21.
 */

public class CustomClientListAdapter extends BaseAdapter{



    Context context;

    ArrayList<Client> mClientList;

    public CustomClientListAdapter(ArrayList<Client> mClientList, Context context) {
        this.mClientList = mClientList;
        this.context = context;

    }

    @Override
    public int getCount() {
        return mClientList.size();
    }

    @Override
    public Object getItem(int position) {
        return mClientList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.spin_client_list_item,parent,false);
        ImageView icon = (ImageView) convertView.findViewById(R.id.imgicon);
        // txtcontractName
        TextView name = (TextView) convertView.findViewById(R.id.tvClientName);
        name.setText(mClientList.get(position).getName());

        return convertView;
    }
}