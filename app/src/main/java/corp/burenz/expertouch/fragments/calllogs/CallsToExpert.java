package corp.burenz.expertouch.fragments.calllogs;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import corp.burenz.expertouch.R;
import corp.burenz.expertouch.adapters.CallsToAdapter;
import corp.burenz.expertouch.databases.CallLogs;

/**
 * Created by xperTouch on 8/16/2016.
 */

public class CallsToExpert extends Fragment {


    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    LinearLayout clearExpertLogs;
    LinearLayout expertCallFeild;
    LinearLayout noExpertCalls;
    LinearLayout sureToClearExpert;
    TextView yesClearExperts,noLetBeExperts;

    void sureToClear(View v){

        clearExpertLogs.setClickable(false);
        sureToClearExpert.startAnimation(AnimationUtils.loadAnimation(getContext(),R.anim.md_styled_slide_up_slow));
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                sureToClearExpert.setVisibility(View.VISIBLE);
            }
        },500);




        yesClearExperts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sureToClearExpert.startAnimation(AnimationUtils.loadAnimation(getContext(),R.anim.md_styled_slide_down_slow));

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

        try {
            CallLogs callLogs  = new CallLogs(getActivity());
            callLogs.writer();
            callLogs.clearExpertLogs();
            callLogs.close();
        }catch (Exception e){
            e.printStackTrace();
        }

                        expertCallFeild.startAnimation(AnimationUtils.loadAnimation(getContext(),R.anim.fab_close));

                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {

                                expertCallFeild.setVisibility(View.INVISIBLE);
                                noExpertCalls.startAnimation(AnimationUtils.loadAnimation(getContext(),R.anim.card_animation));
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {

                                        noExpertCalls.setVisibility(View.VISIBLE);
                                    }
                                },500);
                            }
                        },500);

                        sureToClearExpert.setVisibility(View.INVISIBLE);

                    }
                },500);


            }
        });




        noLetBeExperts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                clearExpertLogs.setClickable(true);
                sureToClearExpert.startAnimation(AnimationUtils.loadAnimation(getContext(),R.anim.md_styled_slide_down_slow));
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        sureToClearExpert.setVisibility(View.INVISIBLE);

                    }
                },500);



            }
        });


    }





    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        View v = inflater.inflate(R.layout.calls_to_experts,container,false);


        clearExpertLogs = (LinearLayout) v.findViewById(R.id.clearExpertLogs);
        noExpertCalls = (LinearLayout) v.findViewById(R.id.noExpertCalls);
        recyclerView = (RecyclerView) v.findViewById(R.id.callsToExpertsRV);
        expertCallFeild = (LinearLayout) v.findViewById(R.id.expertCallFeild);
        yesClearExperts = (TextView) v.findViewById(R.id.yesClearExpert);
        noLetBeExperts = (TextView) v.findViewById(R.id.letBeExperts);
        sureToClearExpert = (LinearLayout)v.findViewById(R.id.sureToClearExpert);


        final CallLogs callLogs  = new CallLogs(getActivity());
        callLogs.writer();

        String[] callTitles = callLogs.getCallTitles().split(" \n");
        String[] callDates = callLogs.getCallDates().split(" \n");
        String[] callPhones = callLogs.getCallPhone().split(" \n");

        if(callTitles[0].equals("")) {

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    noExpertCalls.startAnimation(AnimationUtils.loadAnimation(getContext(),R.anim.card_animation));

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                         noExpertCalls.setVisibility(View.VISIBLE);
                        }
                    },500);


                }
            },500);
            recyclerView.setVisibility(View.GONE);
        }else{

            noExpertCalls.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
        }
        callLogs.close();


        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        adapter = new CallsToAdapter(getActivity(),"expert",callTitles,callDates,callPhones);
        recyclerView.setAdapter(adapter);



        clearExpertLogs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sureToClear(v);

            }
        });


        return v;


    }
}
