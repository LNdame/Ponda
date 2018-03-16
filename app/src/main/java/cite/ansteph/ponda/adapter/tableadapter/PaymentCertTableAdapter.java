package cite.ansteph.ponda.adapter.tableadapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.inqbarna.tablefixheaders.adapters.BaseTableAdapter;

import java.util.ArrayList;

import cite.ansteph.ponda.R;
import cite.ansteph.ponda.model.PaymentCertificate;

/**
 * Created by loicstephan on 2018/03/14.
 */

public class PaymentCertTableAdapter  extends BaseTableAdapter {


    Context mContext;

    ArrayList<PaymentCertificate> paymentCertificates;

    private final PaymentCertFamily family[];
    private final String headers[] = {
            "Payment Certificate No",
            "Issue Date",
            "Paid",
            "Date Due",
            "Days Late",
            "Signed Copy on File",
            "Amount"
    };

    private final int[] widths = {
            120,
            100,
            140,
            60,
            70,
            120,
            60,
    };
    private final float density;

    public PaymentCertTableAdapter(Context mContext, ArrayList<PaymentCertificate> paymentCertificates) {
        this.mContext = mContext;
        this.paymentCertificates = paymentCertificates;
        family = new PaymentCertFamily[]{new PaymentCertFamily("Payment Certificates")};
        density = mContext.getResources().getDisplayMetrics().density;

        loadPayCertinFamily();


    }

    public PaymentCertTableAdapter(Context context) {
        mContext= context;
        family = new PaymentCertFamily[]{new PaymentCertFamily("Payment Certificates")};
        density = context.getResources().getDisplayMetrics().density;

        family[0].list.add(new PaymentCertificate("Nexus One", "HTC", "Gingerbread", "10", "512 MB", "3.7\"", "512"));
        family[0].list.add(new PaymentCertificate("Nexus S", "Samsung", "Gingerbread", "10", "16 GB", "4\"", "512 MB"));
        family[0].list.add(new PaymentCertificate("Galaxy Nexus (16 GB)", "Samsung", "Ice cream Sandwich", "15", "16 GB", "4.65\"", "1 GB"));
        family[0].list.add(new PaymentCertificate("Galaxy Nexus (32 GB)", "Samsung", "Ice cream Sandwich", "15", "32 GB", "4.65\"", "1 GB"));
        family[0].list.add(new PaymentCertificate("Nexus 4 (8 GB)", "LG", "Jelly Bean", "17", "8 GB", "4.7\"", "2 GB"));
        family[0].list.add(new PaymentCertificate("Nexus 4 (16 GB)", "LG", "Jelly Bean", "17", "16 GB", "4.7\"", "2 GB"));
    }



    void loadPayCertinFamily()
    {
        for( PaymentCertificate pc: paymentCertificates)
        {
            family[0].list.add(pc);
        }
    }


    private View getFirstHeader(int row, int column, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_table_header_first, parent, false);
        }
        ((TextView) convertView.findViewById(android.R.id.text1)).setText(headers[0]);
        return convertView;
    }

    private View getHeader(int row, int column, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_table_header, parent, false);
        }
        ((TextView) convertView.findViewById(android.R.id.text1)).setText(headers[column + 1]);
        return convertView;
    }

    private View getFirstBody(int row, int column, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_table_first, parent, false);
        }
        convertView.setBackgroundResource(row % 2 == 0 ? R.drawable.bg_table_color1 : R.drawable.bg_table_color2);
        ((TextView) convertView.findViewById(android.R.id.text1)).setText(getDevice(row).data[column + 1]);
        return convertView;
    }

    private View getBody(int row, int column, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_table, parent, false);
        }
        convertView.setBackgroundResource(row % 2 == 0 ? R.drawable.bg_table_color1 : R.drawable.bg_table_color2);
        ((TextView) convertView.findViewById(android.R.id.text1)).setText(getDevice(row).data[column + 1]);
        return convertView;
    }

    private View getFamilyView(int row, int column, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_table_family, parent, false);
        }
        final String string;
        if (column == -1) {
            string = getFamily(row).name;
        } else {
            string = "";
        }
        ((TextView) convertView.findViewById(android.R.id.text1)).setText(string);
        return convertView;
    }



    @Override
    public int getRowCount() {
        return paymentCertificates.size()+1;
    }

    @Override
    public int getColumnCount() {
        return headers.length-1;
    }


    @Override
    public View getView(int row, int column, View convertView, ViewGroup parent) {
        final View view;
        switch (getItemViewType(row, column)) {
            case 0:
                view = getFirstHeader(row, column, convertView, parent);
                break;
            case 1:
                view = getHeader(row, column, convertView, parent);
                break;
            case 2:
                view = getFirstBody(row, column, convertView, parent);
                break;
            case 3:
                view = getBody(row, column, convertView, parent);
                break;
            case 4:
                view = getFamilyView(row, column, convertView, parent);
                break;
            default:
                throw new RuntimeException("wtf?");
        }
        return view;
    }

    @Override
    public int getWidth(int column) {
        return Math.round(widths[column + 1] * density);
    }

    @Override
    public int getHeight(int row) {
        final int height;
        if (row == -1) {
            height = 35;
        } else if (isFamily(row)) {
            height = 5;
        } else {
            height = 45;
        }
        return Math.round(height * density);
    }

    private boolean isFamily(int row) {
        int fam = 0;
        while (row > 0) {
            row -= family[fam].size() + 1;
            fam++;
        }
        return  row == 0;
    }

    private PaymentCertFamily getFamily(int row) {
        int fam = 0;
        //while (row >= 0) {
        //     row -= family[fam].size() + 1;
        //    fam++;
        // }
        return family[0];
    }

    private PaymentCertificate getDevice(int row) {
        int fam = 0;
        while (row >= 0) {
            row -= family[fam].size() + 1;
            fam++;
        }
        fam--;
        return family[fam].get(row + family[fam].size());
    }


    @Override
    public int getItemViewType(int row, int column) {
        final int itemViewType;
        if (row == -1 && column == -1) {
            itemViewType = 0;
        } else if (row == -1) {
            itemViewType = 1;
        } else if (isFamily(row)) {
            itemViewType = 4;
        } else if (column == -1) {
            itemViewType = 2;
        } else {
            itemViewType = 3;
        }
        return itemViewType;
    }

    @Override
    public int getViewTypeCount() {
        return 5;
    }



    public class PaymentCertFamily{
        private final String name;
        private final ArrayList<PaymentCertificate> list;



        public PaymentCertFamily(String name) {
            this.name = name;
            this.list = new ArrayList<>();
        }

        public int size() {return list.size();}
        public PaymentCertificate get(int i){ return  list.get(i);}
    }
}
