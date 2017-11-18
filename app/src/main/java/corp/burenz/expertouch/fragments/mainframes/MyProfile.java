package corp.burenz.expertouch.fragments.mainframes;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import corp.burenz.expertouch.R;
import corp.burenz.expertouch.adapters.MyProfileAdapt;

/**
 * Created by Developer on 6/30/2016.
 */
public class MyProfile extends Fragment implements View.OnClickListener{


    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    LinearLayout notVerifiedLayout;
    String otpEnteredString;
    EditText otpAgain;
    ImageButton otpAgainSubmitButton;
    InputMethodManager im;
    private String otpEntered,originalOTP;
    SharedPreferences.Editor editor;
    TextView userName,userEmail;
    Button hireButton,getHiredButton;
    LinearLayout verifiedLayout;
    ViewFlipper hireFireFlipper;
    ImageView hireSlectionButton;
    LinearLayout resendLayout,resendCode;


    SharedPreferences userData;
    String LOCAL_APP_DATA = "userInformation";


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.my_profile,container,false);

        userData = getActivity().getSharedPreferences(LOCAL_APP_DATA,0);

            originalOTP = "9789";
            setupVaraiables(v);
            getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);


           verificationCheck();








        return v;




    }



    void setupVaraiables(View v){

        recyclerView = (RecyclerView)v.findViewById(R.id.myProfileRV);
        notVerifiedLayout = (LinearLayout) v.findViewById(R.id.notVerifiedLayout);
        otpAgainSubmitButton = (ImageButton)v.findViewById(R.id.submitOtp);
        verifiedLayout = (LinearLayout)v.findViewById(R.id.verifiedLayout);
        userName = (TextView)v.findViewById(R.id.userName);
        userEmail = (TextView)v.findViewById(R.id.userEmail);
        otpAgain = (EditText)v.findViewById(R.id.otpEntered);
        hireFireFlipper = (ViewFlipper)v.findViewById(R.id.hireFireFlipper);
        hireButton  = (Button)v.findViewById(R.id.hireButton);
        getHiredButton = (Button) v.findViewById(R.id.grtHiredButton);
        hireSlectionButton = (ImageView)v.findViewById(R.id.hireSelectionButton);
        resendLayout = (LinearLayout)v.findViewById(R.id.resendLayout);
        resendCode = (LinearLayout)v.findViewById(R.id.resendCode);

        im = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);


        otpAgainSubmitButton.setOnClickListener(this);
        hireButton.setOnClickListener(this);
        getHiredButton.setOnClickListener(this);
        hireSlectionButton.setOnClickListener(this);
        resendCode.setOnClickListener(this);
    }

    void verificationCheck(){


        userData = getActivity().getSharedPreferences(LOCAL_APP_DATA,0);

        if (userData.getBoolean("VERIFIED",false)){


            //check whether he has registered or not
            verifiedLayout.setVisibility(View.VISIBLE);

            notVerifiedLayout.setVisibility(View.GONE);

        }else{
            notVerifiedLayout.setVisibility(View.VISIBLE);

            userName.setText(userData.getString("userName","Username"));
            userEmail.setText(userData.getString("userEmail","example@host.com"));

            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            adapter = new MyProfileAdapt(getContext());
            recyclerView.setAdapter(adapter);


        }

    }



    @Override
    public void onClick(View v) {


        switch (v.getId()){





            case R.id.submitOtp:

                userData = getActivity().getSharedPreferences(LOCAL_APP_DATA,0);
                editor = userData.edit();

                otpEntered =  otpAgain.getText().toString();
                originalOTP = userData.getString("OTP","null");



                if (otpAgain.getText().toString().length()!= 4){

                    Snackbar.make(v,"Enter Exactly 4 digits",Snackbar.LENGTH_LONG).show();


                } else if (otpEntered.equals(originalOTP)){
                    editor.putBoolean("VERIFIED",true);
                    editor.commit();
                    notVerifiedLayout.setVisibility(View.GONE);
                    verifiedLayout.setVisibility(View.VISIBLE);
                    im.hideSoftInputFromWindow(otpAgain.getWindowToken(),0);
                    Snackbar.make(v,"Congratulations You Account is verified",Snackbar.LENGTH_LONG).show();

                }else {

                    Snackbar.make(v,"OTP Doesn't Match",Snackbar.LENGTH_LONG).show();

                    resendLayout.setVisibility(View.VISIBLE);
                    im.hideSoftInputFromWindow(otpAgain.getWindowToken(),0);

                }


                    break;


            case R.id.hireButton:
                hireFireFlipper.showNext();
                break;
            case R.id.grtHiredButton:
                hireFireFlipper.showNext();
                break;


            case R.id.hireSelectionButton:

                // start First Time Registration
                hireFireFlipper.showNext();
                verifiedLayout.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
                break;

            case R.id.resendCode:
                ConnectivityManager connectivityManager = (ConnectivityManager)getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo  networkInfo = connectivityManager.getActiveNetworkInfo();

                // REPLACE CODE HERE WHEN INTERNET RESUMES

                if (networkInfo == null ){

                    Snackbar.make(v,"We have sent a new Code to "+ userData.getString("userEmail","your registered email"),Snackbar.LENGTH_LONG).show();
                    resendLayout.setEnabled(false);
                    CountDownTimer countDownTimer = new CountDownTimer(1000,10000) {
                        @Override
                        public void onTick(long millisUntilFinished) {
                            Toast.makeText(getActivity(), "wait"+millisUntilFinished, Toast.LENGTH_SHORT).show();

                        }

                        @Override
                        public void onFinish() {

                            resendLayout.setEnabled(true);

                        }
                    };

                    countDownTimer.start();

                }else {
                    Snackbar.make(v,"No Internet Connection",Snackbar.LENGTH_LONG).show();
                }
                break;



        }

    }
}

