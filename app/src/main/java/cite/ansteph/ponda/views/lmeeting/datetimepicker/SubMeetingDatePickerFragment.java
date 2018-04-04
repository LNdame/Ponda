package cite.ansteph.ponda.views.lmeeting.datetimepicker;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;
import android.widget.TextView;

import java.util.Calendar;

import cite.ansteph.ponda.R;


/**
 * Created by loicstephan on 2018/01/25.
 */

public class SubMeetingDatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {



    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //Use the current date as default date in picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        return new DatePickerDialog(getActivity(), this, year, month,day);
    }


    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {

        TextView txtdate = (TextView) getActivity().findViewById(R.id.txtdate);


        String monthcon = (month<10)? "0"+String.valueOf(month+1):String.valueOf(month+1);
        String daycon =  (dayOfMonth<10)? "0"+String.valueOf(dayOfMonth):String.valueOf(dayOfMonth);

        txtdate.setText(String.valueOf(year) +"-"+ monthcon +"-"+  daycon);
    }
}
