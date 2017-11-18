package corp.burenz.expertouch.fragments.mainframes;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

import java.util.ArrayList;

import corp.burenz.expertouch.R;

/**
 * Created by Developer on 6/30/2016.
 */
public class WhatNew extends Fragment implements View.OnClickListener{

    SharedPreferences userData;
    String LOCAL_APP_DATA = "userInformation";

    // Verification Banner layout Variables
    ViewFlipper verificationBannerFlipper;
    LinearLayout verifcationBanner,activationLayout;
    ImageButton otpAgainSubmitButton;
    EditText otpAgain;
    private String otpEntered,originalOTP;
    SharedPreferences.Editor editor;
    InputMethodManager im;
    TextView activateNow;
    ImageView hideVerificationBanner;

    RecyclerView whatsNewRecycler;
    RecyclerView.Adapter whatsNewRecyclerAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.whats_new,container,false);

        setupVariables(v);

        userData = getActivity().getSharedPreferences(LOCAL_APP_DATA, Context.MODE_PRIVATE);
        editor = userData.edit();





        ArrayList<String> someString = new ArrayList<>();
        someString.add("IAC Software Soultions");
        someString.add("IBM");
        someString.add("Microsoft");
        someString.add("hp");
        someString.add("UClap");
        someString.add("TCS");

        ArrayList<String> postDate = new ArrayList<>();
        postDate.add("November 24 2016");
        postDate.add("July 24 2016");
        postDate.add("March 24 2016");
        postDate.add("April 24 2016");
        postDate.add("May 24 2016");
        postDate.add("August 24 2016");

        ArrayList<Integer> callArray = new ArrayList<>();
        callArray.add(121);
        callArray.add(121);
        callArray.add(121);
        callArray.add(121);
        callArray.add(121);
        callArray.add(121);

        ArrayList<String> emailArray = new ArrayList<>();
        emailArray.add("email@example.com");
        emailArray.add("email@example.com");
        emailArray.add("email@example.com");
        emailArray.add("email@example.com");
        emailArray.add("email@example.com");
        emailArray.add("email@example.com");

        ArrayList<String> websiteArray = new ArrayList<>();
        websiteArray.add("www.example.com");
        websiteArray.add("www.example.com");
        websiteArray.add("www.example.com");
        websiteArray.add("www.example.com");
        websiteArray.add("www.example.com");
        websiteArray.add("www.example.com");


        ArrayList<String> someSubString = new ArrayList<>();
        someSubString.add("Can someome suggest me a good interior designer, i just need it for my new office!!," +
                "Can someome suggest me a good interior designer, i just need it for my new office!!");
        someSubString.add("Can someome suggest me a good interior designer, i just need it for my new office!!," +
                "Can someome suggest me a good interior designer, i just need it for my new office!!");
        someSubString.add("Can someome suggest me a good interior designer, i just need it for my new office!!," +
                "Can someome suggest me a good interior designer, i just need it for my new office!!");
        someSubString.add("Can someome suggest me a good interior designer, i just need it for my new office!!," +
                "Can someome suggest me a good interior designer, i just need it for my new office!!");
        someSubString.add("Can someome suggest me a good interior designer, i just need it for my new office!!," +
                "Can someome suggest me a good interior designer, i just need it for my new office!!");
        someSubString.add("Can someome suggest me a good interior designer, i just need it for my new office!!," +
                "Can someome suggest me a good interior designer, i just need it for my new office!!");

      //  whatsNewRecyclerAdapter = new FeedsAdapter(getActivity(),someString,someSubString,postDate,callArray,websiteArray,emailArray);
        whatsNewRecycler.setAdapter(whatsNewRecyclerAdapter);
        editor.apply();
        return v;


    }


   void setupVariables(View v){

       verifcationBanner = (LinearLayout) v.findViewById(R.id.verificationBanner);
       verificationBannerFlipper = (ViewFlipper) v.findViewById(R.id.verificationBannerFlipper);
       activationLayout = (LinearLayout)v.findViewById(R.id.activationLayout);
       otpAgainSubmitButton = (ImageButton)v.findViewById(R.id.otpAgainSubmitButton);
       otpAgain = (EditText) v.findViewById(R.id.otpAgainEditText);
       activateNow = (TextView) v.findViewById(R.id.activateNow);
       im = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
       whatsNewRecycler = (RecyclerView)v.findViewById(R.id.whatsNewRecycler);
       whatsNewRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
       hideVerificationBanner = (ImageView)v.findViewById(R.id.hideVerificationBanner);


       verifcationBanner.setOnClickListener(this);
       activateNow.setOnClickListener(this);
       otpAgainSubmitButton.setOnClickListener(this);
       hideVerificationBanner.setOnClickListener(this);





   }



// Disabled the Activation banner For now
    void bannerScanner(){

        userData = getActivity().getSharedPreferences(LOCAL_APP_DATA, Context.MODE_PRIVATE);

        if (!userData.getBoolean("VERIFIED",false)){

            verificationBannerFlipper.setVisibility(View.VISIBLE);

        }else {
            verificationBannerFlipper.setVisibility(View.GONE);
            verifcationBanner.setVisibility(View.GONE);
            activationLayout.setVisibility(View.GONE);

        }


    }


    @Override
    public void onClick(View v) {




        switch (v.getId()){

            case R.id.activateNow:
                    verificationBannerFlipper.showNext();
                    break;


            case R.id.otpAgainSubmitButton:
                  otpEntered =  otpAgain.getText().toString();
                  originalOTP = userData.getString("OTP","null");

                if (otpAgain.getText().toString().length()!= 4){

                    otpAgain.setError("Enter 4 digits exactly");

                } else if (otpEntered.equals(originalOTP)){
                    editor.putBoolean("VERIFIED",true);
                    editor.commit();
                    im.hideSoftInputFromWindow(otpAgain.getWindowToken(),0);
                    Snackbar.make(v,"Congratulations You Account is verified",Snackbar.LENGTH_LONG).show();
                    verificationBannerFlipper.setVisibility(View.GONE);

                }else {

                    Snackbar.make(v,"OTP Doesn't Match",Snackbar.LENGTH_LONG).show();
                    verificationBannerFlipper.showNext();
                    im.hideSoftInputFromWindow(otpAgain.getWindowToken(),0);

                }

                    break;

            case R.id.hideVerificationBanner:
                verifcationBanner.setVisibility(View.GONE);
                verificationBannerFlipper.setVisibility(View.GONE);
                break;





        }



    }


}
