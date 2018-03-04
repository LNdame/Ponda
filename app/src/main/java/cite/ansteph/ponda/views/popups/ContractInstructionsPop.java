package cite.ansteph.ponda.views.popups;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.TextView;

import cite.ansteph.ponda.R;

/**
 * Created by Nana Kwame Boateng on 2018/03/04.
 */

public class ContractInstructionsPop extends Activity{
    TextView tvCancel, tvSave;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pop_contract_instructions);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int) (width * .8), (int) (height * .8));

        tvSave = (TextView) findViewById(R.id.tvsave);
        tvCancel = (TextView) findViewById(R.id.tvcancel);

        tvSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
