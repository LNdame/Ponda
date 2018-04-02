package cite.ansteph.ponda.adapter.tableadapter;

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
import cite.ansteph.ponda.adapter.AttendingRecyclerAdapter;
import cite.ansteph.ponda.model.VariationOrder;

/**
 * Created by loicstephan on 2018/03/31.
 */

public class VariationOrderRecAdapter extends RecyclerView.Adapter<VariationOrderRecAdapter.VarViewHolder>{


    private ArrayList<VariationOrder> variationOrders;

    Context mContext;

    public VariationOrderRecAdapter(ArrayList<VariationOrder> variationOrders, Context mContext) {
        this.variationOrders = variationOrders;
        this.mContext = mContext;
    }

    @Override
    public VarViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.variation_order_line_item, parent, false);

        return new VarViewHolder(view);
    }

    @Override
    public void onBindViewHolder(VarViewHolder holder, int position) {

        holder.txtlnVarOrder.setText(variationOrders.get(position).getVariationOrder());
        holder.txtlnMotivation.setText(variationOrders.get(position).getMotivation());
        holder.txtlnApproved.setText(variationOrders.get(position).getApproved());

        holder.txtlnOmit.setText(String.valueOf(variationOrders.get(position).getOmit()));
        holder.txtlnAdd.setText(String.valueOf(variationOrders.get(position).getAdd()));
        holder.txtlnBalance.setText(String.valueOf(variationOrders.get(position).getBalance()));

        final View itemView = holder.itemView;
    }

    @Override
    public int getItemCount() {
        return variationOrders.size();
    }


    public void removeItem(int position) {
        variationOrders.remove(position);
        // notify the item removed by position
        // to perform recycler view delete animations
        // NOTE: don't call notifyDataSetChanged()
        notifyItemRemoved(position);
    }

    public void restoreItem(VariationOrder item, int position) {
        variationOrders.add(position, item);
        // notify item added by position
        notifyItemInserted(position);
    }


    public class VarViewHolder extends RecyclerView.ViewHolder {


        public RelativeLayout viewBackground, viewForeground;

        public final TextView txtlnVarOrder;
        public final TextView txtlnMotivation;
        public final TextView txtlnApproved;
        public final TextView txtlnOmit;
        public final TextView txtlnAdd;
        public final TextView txtlnBalance;


        public final ImageView imgAttendeeSelect;

        public VarViewHolder(View view) {
            super(view);
            txtlnVarOrder =(TextView) itemView.findViewById(R.id.lnVarOrder);
            txtlnMotivation=(TextView) itemView.findViewById(R.id.lnMotivation);
            txtlnApproved=(TextView) itemView.findViewById(R.id.lnApproved);
            txtlnOmit=(TextView) itemView.findViewById(R.id.lnOmit);
            txtlnAdd=(TextView) itemView.findViewById(R.id.lnAdd);
            txtlnBalance=(TextView) itemView.findViewById(R.id.lnBalance);


            this.imgAttendeeSelect = (ImageView)itemView.findViewById(R.id.imgAttendeeSelect) ;

            viewBackground = view.findViewById(R.id.view_background);
            viewForeground = view.findViewById(R.id.view_foreground);
        }
    }

}
