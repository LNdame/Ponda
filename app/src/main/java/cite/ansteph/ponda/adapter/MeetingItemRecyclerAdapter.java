package cite.ansteph.ponda.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import cite.ansteph.ponda.R;
import cite.ansteph.ponda.model.MeetingItem;
import cite.ansteph.ponda.views.popups.ApologiesPop;
import cite.ansteph.ponda.views.popups.AttendancePop;
import cite.ansteph.ponda.views.popups.ContractDetailsPop;
import cite.ansteph.ponda.views.popups.MattersArisingPop;
import cite.ansteph.ponda.views.popups.PreviousMinutesPop;
import cite.ansteph.ponda.views.popups.ProgrammePop;

/**
 * Created by Wendy on 2018/02/22.
 */

public class MeetingItemRecyclerAdapter extends RecyclerView.Adapter<MeetingItemRecyclerAdapter.ViewHolder> {

    public ArrayList<MeetingItem> mMeetingItem;
    Context mContext;
    Intent i;


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


        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("item", "clicked");

                int itemID = mMeetingItem.get(position).getMeetingId();

                if(itemID == 1){

                    i = new Intent(mContext, AttendancePop.class);
                    i.putExtra("meetingitem", mMeetingItem.get(position));

                }
                else if(itemID == 2 ){
                    i = new Intent(mContext, ApologiesPop.class);
                }
                else if(itemID == 3 ){

                    i = new Intent(mContext, PreviousMinutesPop.class);

                }
                else if(itemID == 4 ){
                    i = new Intent(mContext, MattersArisingPop.class);

                }
                else if(itemID == 5 ){
                    i = new Intent(mContext, ContractDetailsPop.class);

                }
                else if(itemID == 6 ){
                    i = new Intent(mContext, ProgrammePop.class);

                }

                i.putExtra("meetingitem", mMeetingItem.get(position));
                mContext.startActivity(i);

                //i.putExtra("meetingitem", mMeetingItem.get(position));
                //mContext.startActivity(i);
//                AlertDialog dialog =  mBuilder.create();
//                dialog.setTitle(mMeetingItem.get(position).getItemName());
//                dialog.show();
//
//
//                DisplayMetrics displayMetrics = new DisplayMetrics();
//                ((NewMeeting)mContext).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
//                int displayWidth = displayMetrics.widthPixels;
//                int displayHeight = displayMetrics.heightPixels;
//                // Initialize a new window manager layout parameters
//                WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
//                // Copy the alert dialog window attributes to new layout parameter instance
//                layoutParams.copyFrom(dialog.getWindow().getAttributes());
//
//                // Set alert dialog width and height equal to screen size %
//                int dialogWindowWidth = (int) (displayWidth * 0.9);
//                int dialogWindowHeight = (int) (displayHeight * 0.95);
//
//                // Set the width and height for the layout parameters
//                layoutParams.width = dialogWindowWidth;
//                layoutParams.height = dialogWindowHeight;
//
//                // Apply the newly created layout parameters to the alert dialog window
//                dialog.getWindow().setAttributes(layoutParams);
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
