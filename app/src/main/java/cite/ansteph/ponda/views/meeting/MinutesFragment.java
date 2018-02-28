package cite.ansteph.ponda.views.meeting;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cite.ansteph.ponda.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class MinutesFragment extends Fragment {


    public MinutesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_minutes, container, false);
    }

}
