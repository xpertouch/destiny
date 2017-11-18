package corp.burenz.expertouch.fragments.favorites;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Arrays;
import java.util.Collections;

import corp.burenz.expertouch.R;
import corp.burenz.expertouch.adapters.ExpertFavAdapter;
import corp.burenz.expertouch.databases.Favourites;

/**
 * Created by xperTouch on 8/15/2016.
 */
public class ExpertsFavFragment  extends Fragment{

    RecyclerView expertsFavR;
    RecyclerView.Adapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


     View v = inflater.inflate(R.layout.experts_fav_fragment,container,false);

            Favourites  favourites = new Favourites(getActivity());
            favourites.writer();

            String []expertNames  =   favourites.getExpertName().split(" \n");

            String []expertIds  =   reverse3(favourites.getExpertId().split(" \n"));
            String []expertExperiences =  favourites.getExpertExperience().split(" \n");
            String []expertSkills =  favourites.getExpertSkills().split(" \n");
            String []expertStatus = favourites.getExpertStatus().split(" \n");
            String []expertExpertise = favourites.getExpertExpertise().split(" \n");
            String []expertPic= favourites.getExpertPic().split(" \n");



            favourites.getExpertCount();
            favourites.close();

        expertsFavR = (RecyclerView) v.findViewById(R.id.expertsFavR);
        expertsFavR.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new ExpertFavAdapter(getContext(),expertIds,expertNames,expertExpertise,expertExperiences,expertStatus,expertSkills,expertPic);

        expertsFavR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.notifyDataSetChanged();

            }
        });


        expertsFavR.setAdapter(adapter);
        return v;

    }

    public String[] reverse3(String[] nums) {
        String[] reversed = new String[nums.length];
        for (int i=0; i<nums.length; i++) {

            reversed[i] = nums[nums.length - 1 - i];

        }
        return reversed;
    }

}
