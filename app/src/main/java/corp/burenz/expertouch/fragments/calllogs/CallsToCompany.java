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
public class CallsToCompany extends Fragment {


    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    LinearLayout noCallsToCompany;
    LinearLayout clearCompanyLogs;
    LinearLayout sureToClearCompany;
    TextView yesClearCompany,noLetBe;
    LinearLayout companyCallFeild;






    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.calls_to_company,container,false);


        noCallsToCompany = (LinearLayout) v.findViewById(R.id.noCompanyCalls);
        recyclerView = (RecyclerView) v.findViewById(R.id.callsToCompanyRV);
        clearCompanyLogs = (LinearLayout) v.findViewById(R.id.clearCompanyLogs);
        sureToClearCompany = (LinearLayout) v.findViewById(R.id.sureToClearCompany);
        yesClearCompany = (TextView)  v.findViewById(R.id.yesClearCompany);
        noLetBe = (TextView) v.findViewById(R.id.noLetBe);
        companyCallFeild = (LinearLayout) v.findViewById(R.id.companyCallFeild);



        try{

        CallLogs callLogs  = new CallLogs(getActivity());
        callLogs.writer();

        String[] callTitles = callLogs.getCompanyCallTitles().split(" \n");
        String[] callDates = callLogs.getCompanyCallDates().split(" \n");
        String[] callPhones = callLogs.getCompanyCallPhones().split(" \n");

        if (callTitles[0].equals("")){
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    noCallsToCompany.startAnimation(AnimationUtils.loadAnimation(getContext(),R.anim.card_animation));

                  new Handler().postDelayed(new Runnable() {
                      @Override
                      public void run() {

                          noCallsToCompany.setVisibility(View.VISIBLE);

                      }
                  },500);
                }
            },500);
            recyclerView.setVisibility(View.GONE);
        }else{

            noCallsToCompany.setVisibility(View.INVISIBLE);
            recyclerView.setVisibility(View.VISIBLE);
        }
        adapter = new CallsToAdapter(getActivity(),"company",callTitles,callDates,callPhones);

        callLogs.close();

        }
        catch (Exception e){

        }



        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);



        clearCompanyLogs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            sureToClear(v);

            }
        });

        return v;
    }



    void sureToClear(View v){



        clearCompanyLogs.setClickable(false);
        sureToClearCompany.startAnimation(AnimationUtils.loadAnimation(getContext(),R.anim.md_styled_slide_up_slow));


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                sureToClearCompany.setVisibility(View.VISIBLE);

            }
        },500);



        yesClearCompany.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sureToClearCompany.startAnimation(AnimationUtils.loadAnimation(getContext(),R.anim.md_styled_slide_down_slow));
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        try {
                            CallLogs callLogs  = new CallLogs(getActivity());
                            callLogs.writer();
                            callLogs.clearCompanyLogs();
                            callLogs.close();
                        }catch (Exception e){
                            e.printStackTrace();
                        }


                        sureToClearCompany.setVisibility(View.INVISIBLE);
                        companyCallFeild.startAnimation(AnimationUtils.loadAnimation(getContext(),R.anim.fab_close));
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                companyCallFeild.setVisibility(View.INVISIBLE);
                            }
                        },500);
                        noCallsToCompany.startAnimation(AnimationUtils.loadAnimation(getContext(),R.anim.card_animation));
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                               noCallsToCompany.setVisibility(View.VISIBLE);
                            }
                        },500);
                    }


                },500);

            }
        });



        noLetBe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                clearCompanyLogs.setClickable(true);
                sureToClearCompany.startAnimation(AnimationUtils.loadAnimation(getContext(),R.anim.md_styled_slide_down_slow));
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        sureToClearCompany.setVisibility(View.INVISIBLE);

                    }
                },500);

            }
        });


    }

}
