package corp.burenz.expertouch.fragments.more;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import corp.burenz.expertouch.R;
import corp.burenz.expertouch.adapters.FullDiscriptionAdapter;

/**
 * Created by xperTouch on 7/12/2016.
 */
public class Education extends Fragment {


    RecyclerView educationRV;
    RecyclerView.Adapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View  v = inflater.inflate(R.layout.education_layout,container,false);

        setupVariables(v);

        String[] keysForMore = {
                "Detail #1",
                "Detail #2",
                "Detail #3",
                "Detail #4"

        };
        String[] valuesForMore = {
                "Discription #1",
                "Discription #2",
                "Discription #3",
                "Discription #4"

        };


        //get keysForMore and their Values Either from server or put keysForMore Yourself and get Values From Server

        adapter = new FullDiscriptionAdapter(keysForMore,valuesForMore);
        educationRV.setAdapter(adapter);

        return v;

    }




    void setupVariables(View v){

        educationRV = (RecyclerView)v.findViewById(R.id.educationRV);
        educationRV.setLayoutManager(new LinearLayoutManager(v.getContext()));





    }
}
