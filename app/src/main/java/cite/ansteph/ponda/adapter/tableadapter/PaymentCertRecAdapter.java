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
import cite.ansteph.ponda.model.PaymentCertificate;
import cite.ansteph.ponda.model.VariationOrder;

/**
 * Created by loicstephan on 2018/04/01.
 */

public class PaymentCertRecAdapter extends RecyclerView.Adapter<PaymentCertRecAdapter.PayViewHolder>{


    private ArrayList<PaymentCertificate> PaymentCertificates;

    Context mContext;

    public PaymentCertRecAdapter(ArrayList<PaymentCertificate> paymentCertificates, Context mContext) {
        PaymentCertificates = paymentCertificates;
        this.mContext = mContext;
    }

    @Override
    public PaymentCertRecAdapter.PayViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.payment_cert_line_item, parent, false);

        return new PaymentCertRecAdapter.PayViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PaymentCertRecAdapter.PayViewHolder holder, int position) {

        holder.txtlnPayCert.setText(PaymentCertificates.get(position).getPaymentcertificate());
        holder.txtlnIssueDate.setText(PaymentCertificates.get(position).getIssueDate());
        holder.txtlnPaid.setText(PaymentCertificates.get(position).getPaid());

        holder.txtlnDateDue.setText(String.valueOf(PaymentCertificates.get(position).getDateDue()));
        holder.txtlnDayLate.setText(String.valueOf(PaymentCertificates.get(position).getDayLate()));
        holder.txtlnCopy.setText(String.valueOf(PaymentCertificates.get(position).getSignedCopy()));
        holder.txtlnAmount.setText(String.valueOf(PaymentCertificates.get(position).getAmount()));


        final View itemView = holder.itemView;
    }

    @Override
    public int getItemCount() {
        return PaymentCertificates.size();
    }


    public void removeItem(int position) {
        PaymentCertificates.remove(position);
        // notify the item removed by position
        // to perform recycler view delete animations
        // NOTE: don't call notifyDataSetChanged()
        notifyItemRemoved(position);
    }

    public void restoreItem(PaymentCertificate item, int position) {
        PaymentCertificates.add(position, item);
        // notify item added by position
        notifyItemInserted(position);
    }


    public class PayViewHolder extends RecyclerView.ViewHolder {


        public RelativeLayout viewBackground, viewForeground;

        public final TextView txtlnPayCert;
        public final TextView txtlnIssueDate;
        public final TextView txtlnPaid;
        public final TextView txtlnDateDue;
        public final TextView txtlnDayLate;
        public final TextView txtlnCopy;
        public final TextView txtlnAmount;


        public final ImageView imgAttendeeSelect;

        public PayViewHolder(View view) {
            super(view);
            txtlnPayCert =(TextView) itemView.findViewById(R.id.lnPayCert);
            txtlnIssueDate=(TextView) itemView.findViewById(R.id.lnIssueDate);
            txtlnPaid=(TextView) itemView.findViewById(R.id.lnPaid);
            txtlnDateDue=(TextView) itemView.findViewById(R.id.lnDateDue);
            txtlnDayLate=(TextView) itemView.findViewById(R.id.lnDayLate);
            txtlnCopy=(TextView) itemView.findViewById(R.id.lnCopy);
            txtlnAmount=(TextView) itemView.findViewById(R.id.lnAmount);

            this.imgAttendeeSelect = (ImageView)itemView.findViewById(R.id.imgAttendeeSelect) ;

            viewBackground = view.findViewById(R.id.view_background);
            viewForeground = view.findViewById(R.id.view_foreground);
        }
    }

}
