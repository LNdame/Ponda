package cite.ansteph.ponda.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import cite.ansteph.ponda.R;
import cite.ansteph.ponda.model.MeetingItem;

/**
 * Created by Wendy on 2018/02/22.
 */

public class MeetingItemRecyclerAdapter extends RecyclerView.Adapter<MeetingItemRecyclerAdapter.ViewHolder> {

    public ArrayList<MeetingItem> mMeetingItem;
    Context mContext;

    public MeetingItemRecyclerAdapter() {
    }

    public MeetingItemRecyclerAdapter(ArrayList<MeetingItem> mMeetingItem, Context mContext) {
        this.mMeetingItem = mMeetingItem;
        this.mContext = mContext;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.meetingitem_list_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder,  final int position) {
        holder.mTextView.setText(mMeetingItem.get(position).getItemName());
        //   holder.mIcon.setLetter(mBookList.get(position).getTitle());


        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Log.d("item", "clicked");
//                Intent i = new Intent(mContext, EditClient.class);
//                i.putExtra("meeting", mMeetingItem.get(position));
//                mContext.startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mMeetingItem.size();
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
