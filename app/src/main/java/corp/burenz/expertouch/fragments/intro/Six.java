package corp.burenz.expertouch.fragments.intro;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import corp.burenz.expertouch.R;
import corp.burenz.expertouch.activities.Registrations;

/**
 * Created by xperTouch on 12/11/2016.
 */
public class Six extends Fragment {



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.intro_six,container,false);
        Button letSGetGoing;

        letSGetGoing = (Button) v.findViewById(R.id.letsGetGoing);


        letSGetGoing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), Registrations.class));
                getActivity().finish();
            }
        });


        return v;


    }
}
