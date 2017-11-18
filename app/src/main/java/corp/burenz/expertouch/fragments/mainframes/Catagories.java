package corp.burenz.expertouch.fragments.mainframes;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import corp.burenz.expertouch.R;
import corp.burenz.expertouch.adapters.CataAdapter;

/**
 * Created by Developer on 6/30/2016.
 */
public class Catagories extends Fragment {

    RecyclerView cataRecyclerView;
    RecyclerView.Adapter cataRecyclerViewAdapter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.catagories,container,false);

        String[] titles = {
                "Doctors"
        };

        String[] subTitles = {
                "Hire Doctors for your Family"
        };

        setUpVariables(v);

        cataRecyclerViewAdapter = new CataAdapter(getActivity());
        cataRecyclerView.setAdapter(cataRecyclerViewAdapter);

        return v;




    }



    void setUpVariables(View v){
        cataRecyclerView = (RecyclerView) v.findViewById(R.id.cataRecyclerView);
        cataRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }
}
