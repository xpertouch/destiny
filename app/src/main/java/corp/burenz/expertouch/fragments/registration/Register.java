package corp.burenz.expertouch.fragments.registration;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import corp.burenz.expertouch.R;
import corp.burenz.expertouch.activities.Jobs;
import corp.burenz.expertouch.activities.TermsOfService;

/**
 * Created by Developer on 6/29/2016.
 */
public class Register extends Fragment implements View.OnClickListener {


    String LOCAL_APP_DATA = "userInformation";
    SharedPreferences userData;
    SharedPreferences.Editor editor;

    LinearLayout getRegistered;
    ViewFlipper trickRegisterFlipper,feildRegisterFlipper;

    String TAG = "OTP";
    TextView enterEmail,enterName,enterState,enterpassword,otpTextView,titleOne,titleTwo,titleThree,titleFour;
    Button firstButton,stateButton,secondButton,thirdButton,skip,submitOtp;
    EditText emailEditText,nameEditText,passwordEditText,otpEditText;
    ViewFlipper imageViewGuideLine,textViewGuideLine,titleFlipper,editTextGuideLine,buttonGuideLine,sendingOTPFlipper;
    String verifyOtp;
    TextView registreLogo;
    Spinner registerStateSpinner;

    Animation animation,bottomAnimation;
    String userEmail,userState,userName,password,otp;
    Typeface subtitleTypeface;
    ArrayList<String> statesArray = new ArrayList<String>();


    Button lookBackOne,lookBackTwo,lookBackThree;

    InputMethodManager im;
    TextView sendingOTP;

    TextView registerTrickTitle,registerTrickSubtitle;
    Button iacceptRegister;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.register_fragment_layout,container,false);
        setUpVariables(v);
        getRegistered.startAnimation(AnimationUtils.loadAnimation(getActivity(),R.anim.abc_fade_in));

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
            getRegistered.setVisibility(View.VISIBLE);
            }
        },300);


        animation = AnimationUtils.loadAnimation(getActivity(),R.anim.card_animation);
        bottomAnimation = AnimationUtils.loadAnimation(getActivity(),R.anim.abc_fade_in);
        registreLogo = (TextView) v.findViewById(R.id.registerLogo);

        //trickRegisterFlipper.showNext();

        im = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);

        subtitleTypeface = Typeface.createFromAsset(getContext().getAssets(), "fonts/subtitle.otf");
        Typeface  titleTypeface = Typeface.createFromAsset(getContext().getAssets(), "fonts/forte.ttf");

        registreLogo.setTypeface(titleTypeface);
        userData = getActivity().getSharedPreferences(LOCAL_APP_DATA, Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = userData.edit();

        editor.putString("OTP","9789");

        editor.commit();

        setTexts();

        return v;


    }

    public void setTexts(){

        userData = getActivity().getSharedPreferences(LOCAL_APP_DATA,0);
        if (!userData.getString("userEmail","Enter Phone Number").equals("Enter Phone Number")){
        emailEditText.setText(userData.getString("userEmail","Enter Phone Number"));
        }else {
        emailEditText.setHint(userData.getString("userEmail","Enter Phone Number"));
        }


        if (!userData.getString("userName","Enter Name").equals("Enter Name")){
            nameEditText.setText(userData.getString("userName","Enter Name"));
        }else {
            nameEditText.setHint(userData.getString("userName","Enter Name"));
        }


    }


    void setUpVariables(View v){

        // View Flippers

        imageViewGuideLine = (ViewFlipper) v.findViewById(R.id.imageViewGuideLine);
        imageViewGuideLine.setInAnimation(getActivity(),R.anim.fab_open);
        textViewGuideLine = (ViewFlipper) v.findViewById(R.id.textViewGuideLine);
        editTextGuideLine = (ViewFlipper) v.findViewById(R.id.editTextGuideLine);
        buttonGuideLine = (ViewFlipper) v.findViewById(R.id.buttonGuideLine);
        titleFlipper = (ViewFlipper)v.findViewById(R.id.titleFlipper);

        trickRegisterFlipper = (ViewFlipper) v.findViewById(R.id.trickRegisterFlipper);
        feildRegisterFlipper = (ViewFlipper) v.findViewById(R.id.feildsRegisterFlipper);


        lookBackOne = (Button)v.findViewById(R.id.lookBackOne);
        lookBackTwo = (Button)v.findViewById(R.id.lookBackTwo);
        lookBackThree = (Button)v.findViewById(R.id.lookBackThree);
        iacceptRegister = (Button) v.findViewById(R.id.iacceptRegister);

        sendingOTPFlipper = (ViewFlipper) v.findViewById(R.id.sendingOTPFlipper);
        sendingOTP = (TextView)v.findViewById(R.id.sendingOTP);



        registerTrickTitle = (TextView) v.findViewById(R.id.registerTrickTitle);
        registerTrickSubtitle = (TextView) v.findViewById(R.id.registerTrickSubtitle);



        registerTrickSubtitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (registerTrickSubtitle.getText().toString().contains("Terms and Conditions")){
                    startActivity(new Intent(getActivity(), TermsOfService.class));

                }



            }
        });


        // Relative Lauyouts

        getRegistered = (LinearLayout) v.findViewById(R.id.getRegister);


        // TextViews

        enterName = (TextView)v.findViewById(R.id.nameTextView);
        enterState = (TextView)v.findViewById(R.id.stateTextView);
        enterEmail = (TextView)v.findViewById(R.id.emailTextView);
        enterpassword = (TextView)v.findViewById(R.id.passwordTextView);
        otpTextView = (TextView)v.findViewById(R.id.otpTextView);
        titleOne = (TextView)v.findViewById(R.id.titleOne);
        titleTwo = (TextView)v.findViewById(R.id.titleTwo);
        titleThree = (TextView)v.findViewById(R.id.titleThree);
        titleFour = (TextView)v.findViewById(R.id.titleFour);


        // editTexts


        statesArray.add("Select Your State");
        statesArray.add("Andhra Pradesh");
        statesArray.add("Arunachal Pradesh");
        statesArray.add("Assam");
        statesArray.add("Bihar");
        statesArray.add("Chandigarh");
        statesArray.add("Chhattisgarh");
        statesArray.add("Dadra and Nagar Haveli");
        statesArray.add("Daman and Diu");
        statesArray.add("Delhi");
        statesArray.add("Goa");
        statesArray.add("Gujarat");
        statesArray.add("Haryana");
        statesArray.add("Himachal Pradesh");
        statesArray.add("Jammu and Kashmir");
        statesArray.add("Jharkhand");
        statesArray.add("Karnataka");
        statesArray.add("Kerala");
        statesArray.add("Lakshadweep");
        statesArray.add("Madhya Pradesh");
        statesArray.add("Maharashtra");
        statesArray.add("Manipur");
        statesArray.add("Meghalaya");
        statesArray.add("Mizoram");
        statesArray.add("Nagaland");
        statesArray.add("Odisha");
        statesArray.add("Puducherry");
        statesArray.add("Punjab");
        statesArray.add("Rajasthan");
        statesArray.add("Sikkim");
        statesArray.add("Tamil Nadu");
        statesArray.add("Telengana");
        statesArray.add("Tripura");
        statesArray.add("Uttar Pradesh");
        statesArray.add("Uttarakhand");
        statesArray.add("West Bengal");


        emailEditText = (EditText)v.findViewById(R.id.emailEditText);

        registerStateSpinner = (Spinner)v.findViewById(R.id.registerStateSpinner);
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(getActivity(),R.layout.spinner_adapter,R.id.spinnerText,statesArray);
        registerStateSpinner.setAdapter(adapter1);
        otpEditText = (EditText)v.findViewById(R.id.otpEditText);
        nameEditText = (EditText)v.findViewById(R.id.nameEditText);
        passwordEditText = (EditText)v.findViewById(R.id.passwordEditText);

        //buttons


        submitOtp = (Button) v.findViewById(R.id.submitOtp);
        skip = (Button) v.findViewById(R.id.skipOtp);
        firstButton = (Button) v.findViewById(R.id.emailNextButton);
        stateButton = (Button)v.findViewById(R.id.stateNextButton);
        secondButton = (Button) v.findViewById(R.id.nameNextButton);
        thirdButton = (Button) v.findViewById(R.id.passwordNextButton);

        // OnClick litners

        submitOtp.setOnClickListener(this);
        submitOtp.setOnClickListener(this);
        firstButton.setOnClickListener(this);
        stateButton.setOnClickListener(this);
        secondButton.setOnClickListener(this);
        thirdButton.setOnClickListener(this);
        skip.setOnClickListener(this);
        lookBackOne.setOnClickListener(this);
        lookBackTwo.setOnClickListener(this);
        lookBackThree.setOnClickListener(this);
        iacceptRegister.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        userData = getActivity().getSharedPreferences(LOCAL_APP_DATA, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor= userData.edit();

        switch (v.getId()){



            case R.id.iacceptRegister:
                trickRegisterFlipper.showNext();
                feildRegisterFlipper.showNext();
                buttonGuideLine.showNext();
                registreLogo.startAnimation(AnimationUtils.loadAnimation(getContext(),R.anim.fab_close));
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        registreLogo.setVisibility(View.GONE);
                    }
                },300);
                break;


            case R.id.submitOtp:
               // OTP is saved somewhere but retrieved here

                verifyOtp = userData.getString("OTP","9999");
                String otpEntered = otpEditText.getText().toString();

                if (otpEditText.getText().toString().length()!= 4){

                        // INCORRECR OTP
                        Toast.makeText(getActivity(), "Plese Enter a 4 digit OTP", Toast.LENGTH_SHORT).show();
                        editor.putBoolean("VERIFIED",false);
                        editor.commit();
                }else if (otpEntered.equals(verifyOtp)){

                    // ACCESS GRANTED

                    editor.putString("userEmail",userEmail);
                    editor.putString("userState",userState);
                    editor.putString("userName",userName);
                    editor.putString("userPassword",password);
                    editor.putBoolean("VERIFIED",true);
                    editor.putBoolean("LOGEDOUT",false);
                    editor.commit();
                    new RegisterUser().execute();

                }else if (otpEntered.equals("9999")){
                    // PROCESSING
                    Toast.makeText(getActivity(), "OTP is on its Way, Please Wait for a moment", Toast.LENGTH_SHORT).show();
                }

                else  {

                        Toast.makeText(getActivity(), "OTP Doesn't Match, you can skip it and verify later", Toast.LENGTH_SHORT).show();
                    }

                    break;

            case R.id.skipOtp:
                // Access Denied , Verification Postponed
                Toast.makeText(getContext(), "Verification Postponed, You can verify later", Toast.LENGTH_SHORT).show();
                editor.putString("userEmail",userEmail);
                editor.putString("userState",userState);
                editor.putString("userName",userName);
                editor.putString("userPassword",password);
                editor.putBoolean("VERIFIED",false);
                editor.putBoolean("SKIPPEDVERI",true);
                editor.putBoolean("LOGEDOUT",false);
                editor.commit();

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        startActivity(new Intent(getActivity(), Jobs.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                        getActivity().finish();

                    }
                },200);
                break;

            case R.id.emailNextButton:
                // No Validation Right Now

                if ( emailEditText.getText().toString().length() == 10 ){


                    if (emailEditText.getText().toString().contains(" ")){
                        Toast.makeText(getActivity(), "Phone number must not contains any spaces", Toast.LENGTH_SHORT).show();
                    }else {

                        userEmail = emailEditText.getText().toString();
                        editor.putString("userEmail",userEmail);
                        editor.apply();
                        im.hideSoftInputFromWindow(emailEditText.getWindowToken(),0);
                        new CheckAvailability().execute();


                    }

                }else {
                    Toast.makeText(getActivity(), "Please Enter a valid Phone Number ", Toast.LENGTH_SHORT).show();
                }

                break;

            case R.id.stateNextButton:



                if (registerStateSpinner.getSelectedItem().toString().equals("Select Your State")){
                    Toast.makeText(getActivity(), "Please Select a Valid State", Toast.LENGTH_SHORT).show();
                }else {
                    imageViewGuideLine.showNext();
                    textViewGuideLine.showNext();
                    editTextGuideLine.showNext();
                    titleFlipper.showNext();
                    buttonGuideLine.showNext();
                    userState = registerStateSpinner.getSelectedItem().toString();
                    editor.putString("userState",userState);
                    editor.apply();
                    enterName.setText("Alright Sparky, Verification Code is on its way to : " + userEmail);
                }


                break;



            case R.id.nameNextButton:

                if (nameEditText.getText().toString().length() < 4){
                    Toast.makeText(getActivity(), "Username must be at least 4 Characters Long", Toast.LENGTH_SHORT).show();

                }else {
                    imageViewGuideLine.showNext();
                    textViewGuideLine.showNext();
                    editTextGuideLine.showNext();
                    titleFlipper.showNext();
                    buttonGuideLine.showNext();

                    userName = nameEditText.getText().toString();
                    editor.putString("userName",userName);
                    editor.apply();

                    enterpassword.setText("Time to get Secure "+ userName +", pick a secure password for your Account" );
                    break;

                }


            case R.id.passwordNextButton:
                if (passwordEditText.getText().toString().length() < 8){
                    Toast.makeText(getActivity(), "Password must be at least 8 Characters Long", Toast.LENGTH_SHORT).show();
                }else{
                    imageViewGuideLine.showNext();
                    textViewGuideLine.showNext();
                    editTextGuideLine.showNext();
                    buttonGuideLine.showNext();
                    titleFlipper.showNext();
                    password = passwordEditText.getText().toString();
                    editor.putString("userPassword",password);
                    editor.apply();
                    otpTextView.setText("Verification Code was sent to " + userEmail +""  );
                }
                break;


            case R.id.lookBackOne:
                lookBack();
                break;


            case R.id.lookBackTwo:
                lookBack();
                break;


            case R.id.lookBackThree:
                lookBack();
                break;

        }





    }

    public  void lookBack(){

        imageViewGuideLine.showPrevious();
        editTextGuideLine.showPrevious();
        titleFlipper.showPrevious();
        textViewGuideLine.showPrevious();
        buttonGuideLine.showPrevious();


    }

     class RegisterUser extends  AsyncTask< String, String, String> {

        StringBuilder builder = new StringBuilder();
        BufferedReader bufferedReader;

         List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();



        @Override
        protected String doInBackground(String... params) {



            try {


                nameValuePairs.add(new BasicNameValuePair("userPhone",userData.getString("userEmail","noEmailFound")));
                nameValuePairs.add(new BasicNameValuePair("userState",userData.getString("userState","Jammu and Kashmir")));
                nameValuePairs.add(new BasicNameValuePair("userName",userData.getString("userName","Guest")));
                nameValuePairs.add(new BasicNameValuePair("userPassword",userData.getString("userPassword","noPasswordFound")));

                Log.e(TAG, "userEmail = " +userData.getString("userEmail","noEmailFound") );
                Log.e(TAG, "userState = " +userData.getString("userState","Jammu and Kashmir"));
                Log.e(TAG, "userName= " +userData.getString("userName","noEmailFound"));
                Log.e(TAG, "userPassword = " +userData.getString("userPassword","noEmailFound") );


                HttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost(getString(R.string.host)+"/registrations/register_user.php");
                httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                HttpResponse httpResponse = httpClient.execute(httpPost);
                HttpEntity httpEntity = (HttpEntity) httpResponse.getEntity();

                bufferedReader = new BufferedReader(new InputStreamReader(httpEntity.getContent()));
                String str = "";

                while ( (str = bufferedReader.readLine()) != null ){
                    builder.append(str);
                }


            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e){


            }



            return  builder.toString();

        }



         @Override
         protected void onPreExecute() {
             super.onPreExecute();

             trickRegisterFlipper.showPrevious();
             feildRegisterFlipper.showNext();
             buttonGuideLine.showNext();
             registerTrickTitle.setText("Hang On");
             registerTrickSubtitle.setText("Your account is successfully verified please wait for a moment while we are update your account to our Community");

         }

         @Override
         protected void onPostExecute(String s) {
             super.onPostExecute(s);

             if(s.equals("1")){
                 startActivity(new Intent(getActivity(), Jobs.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                 getActivity().finish();

             }else{

                 Toast.makeText(getActivity(), "We Couldn't Register your Account, Please Try again" + s, Toast.LENGTH_SHORT).show();
                 trickRegisterFlipper.showNext();
                 feildRegisterFlipper.showPrevious();
                 buttonGuideLine.showPrevious();

             }





         }



    }
     class CheckAvailability extends AsyncTask< String, String, String>{


        StringBuilder builder = new StringBuilder();
        BufferedReader bufferedReader;

        List<NameValuePair> nameValuePairs = new ArrayList<>();


        @Override
        protected String doInBackground(String... params) {

            nameValuePairs.add(new BasicNameValuePair("userPhone",userEmail));

                try {


                    HttpClient httpClient = new DefaultHttpClient();
                    HttpPost httpPost =  new HttpPost(getString(R.string.host)+"/registrations/generate_random_new.php");
                    httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                    Log.e(TAG,"Send Random Code To : " + userEmail);
                    HttpResponse httpResponse = (HttpResponse) httpClient.execute(httpPost);
                    HttpEntity httpEntity = httpResponse.getEntity();

                    bufferedReader = new BufferedReader(new InputStreamReader(httpEntity.getContent()));
                    String str = "";

                    while ( (str = bufferedReader.readLine()) != null ){

                        builder.append(str);
                    }


                } catch (ClientProtocolException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (Exception e){

                }


            return builder.toString();
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            sendingOTP.setText("Sending Verification Code to " + emailEditText.getText().toString());
            sendingOTPFlipper.showNext();



        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
             sendingOTPFlipper.showNext();

            if (s.equals("1")){

                Toast.makeText(getActivity(), "This Phone Number is already Registered With Us", Toast.LENGTH_SHORT).show();






            }else if (s.length() == 4){

                imageViewGuideLine.showNext();
                textViewGuideLine.showNext();
                editTextGuideLine.showNext();
                titleFlipper.showNext();
                buttonGuideLine.showNext();
                enterState.setText("Great Going Sparky, Please Select Your Current State");
                editor = userData.edit();
                editor.putString("OTP",s);
                editor.apply();


            }


            else if (s.equals("die")) {


                Toast.makeText(getActivity(), "Couldn't Connect To server Right Now" , Toast.LENGTH_SHORT).show();

            }else {

                Toast.makeText(getActivity(), "Network failure, Please try again " , Toast.LENGTH_SHORT).show();
            }

        }
    }






}
