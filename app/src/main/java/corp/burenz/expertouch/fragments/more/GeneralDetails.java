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
public class GeneralDetails extends Fragment {


    RecyclerView generalRV;
    RecyclerView.Adapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View  v = inflater.inflate(R.layout.general_details,container,false);

        setupVariables(v);

        // Name, age , State , Gender, Experience

        String[] keysForMore = {
                "Detail #1",
                "Detail #2",
                "Detail #3",
                "Detail #4",
                "Detail #5",
                "Detail #7",
                "Detail #8",
                "Detail #9",
                "Detail #10"
        };
        String[] valuesForMore = {
                "Discription #1",
                "Discription #2",
                "Discription #3",
                "Discription #4",
                "Discription #5",
                "Discription #6",
                "Discription #7",
                "Discription #8",
                "Discription #9",
                "Discription #10"

        };


        //get keysForMore and their Values Either from server or put keysForMore Yourself and get Values From Server

        adapter = new FullDiscriptionAdapter(keysForMore,valuesForMore);
        generalRV.setAdapter(adapter);

        return v;

    }




    void setupVariables(View v){

        generalRV = (RecyclerView)v.findViewById(R.id.generalDetailsRV);
        generalRV.setLayoutManager(new LinearLayoutManager(v.getContext()));





    }
}
