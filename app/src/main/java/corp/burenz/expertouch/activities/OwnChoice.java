package corp.burenz.expertouch.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
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

public class OwnChoice extends AppCompatActivity implements View.OnClickListener {



    LinearLayout notVerifiedLayout;
    EditText otpAgain;
    ImageButton otpAgainSubmitButton;
    InputMethodManager im;
    private String otpEntered,originalOTP;
    TextView resendHelperV;
    TextView changeEmailAddress;

    SharedPreferences.Editor editor;
    TextView userName,userEmail;
    LinearLayout hireButton;
    TextView getHiredButton;
    LinearLayout verifiedLayout;
    ViewFlipper codeAccepted,registerImageFlipper,registerTextFlipper,subtitleRegisterFlipper;
    LinearLayout resendLayout,resendCode;

    String TAG = "OTP";

    SharedPreferences userData;
    String LOCAL_APP_DATA = "userInformation";


    @Override
    protected void onResume() {
        super.onResume();

        userData = getSharedPreferences(LOCAL_APP_DATA,0);
        userName.setText(userData.getString("userName","Username"));
        userEmail.setText(userData.getString("userEmail","example@host.com"));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_own_choice);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        userData = OwnChoice.this.getSharedPreferences(LOCAL_APP_DATA,0);

        originalOTP = "9789";

        setupVariables();

        OwnChoice.this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);


        verificationCheck();

        userName.setText(userData.getString("userName","Username"));
        userEmail.setText(userData.getString("userEmail","example@host.com"));

      }

    void verificationCheck(){


        userData = OwnChoice.this.getSharedPreferences(LOCAL_APP_DATA,0);

        if (userData.getBoolean("VERIFIED",false)){


            //check whether he has registered or not
             notVerifiedLayout.setClickable(false);
             verifiedLayout.setVisibility(View.VISIBLE);
             registerImageFlipper.setFlipInterval(2960);
             registerTextFlipper.setFlipInterval(2980);
             subtitleRegisterFlipper.setFlipInterval(3000);

             registerImageFlipper.startFlipping();
             registerTextFlipper.startFlipping();
             subtitleRegisterFlipper.startFlipping();

            notVerifiedLayout.setVisibility(View.GONE);

        }else{
            verifiedLayout.setClickable(false);
            notVerifiedLayout.setVisibility(View.VISIBLE);

        }

    }
    void setupVariables(){

        notVerifiedLayout = (LinearLayout) findViewById(R.id.notVerifiedLayout);
        otpAgainSubmitButton = (ImageButton)findViewById(R.id.submitOtp);
        verifiedLayout = (LinearLayout)findViewById(R.id.verifiedLayout);
        userName = (TextView)findViewById(R.id.userName);
        userEmail = (TextView)findViewById(R.id.userEmail);
        otpAgain = (EditText)findViewById(R.id.otpEntered);
        hireButton  = (LinearLayout)findViewById(R.id.hireLayout);
        getHiredButton = (TextView) findViewById(R.id.getHiredTextView);
        resendLayout = (LinearLayout)findViewById(R.id.resendLayout);
        resendCode = (LinearLayout)findViewById(R.id.resendCode);
        codeAccepted = (ViewFlipper)findViewById(R.id.codeAccepted);
        im = (InputMethodManager)OwnChoice.this.getSystemService(INPUT_METHOD_SERVICE);
        changeEmailAddress = (TextView) findViewById(R.id.changeEmailAddress);
        registerImageFlipper = (ViewFlipper) findViewById(R.id.registerImagezFlipper);
        registerTextFlipper = (ViewFlipper) findViewById(R.id.registerTextFlipper);
        subtitleRegisterFlipper = (ViewFlipper) findViewById(R.id.subtitleRegisterFlipper);

        resendHelperV = (TextView) findViewById(R.id.resendHelperV);

        otpAgainSubmitButton.setOnClickListener(this);
        hireButton.setOnClickListener(this);
        changeEmailAddress.setOnClickListener(this);
        getHiredButton.setOnClickListener(this);
        resendCode.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {


        switch (v.getId()){

            case R.id.changeEmailAddress:
                startActivity(new Intent(OwnChoice.this,SettingsActivity.class));
                break;


            case R.id.submitOtp:

                userData = OwnChoice.this.getSharedPreferences(LOCAL_APP_DATA,0);
                editor = userData.edit();

                otpEntered =  otpAgain.getText().toString();
                originalOTP = userData.getString("OTP","null");



                if (otpAgain.getText().toString().length()!= 4){

                    Snackbar.make(v,"Enter Exactly 4 digits",Snackbar.LENGTH_LONG).show();


                } else if (otpEntered.equals(originalOTP)){
                    new RegisterUser().execute();

                }else {

                    Snackbar.make(v,"OTP Doesn't Match",Snackbar.LENGTH_LONG).show();

                    resendLayout.setVisibility(View.VISIBLE);
                    im.hideSoftInputFromWindow(otpAgain.getWindowToken(),0);

                }


                break;


            case R.id.hireLayout:
                startActivity(new Intent(OwnChoice.this,RegisterCompany.class));
                finish();
                break;
            case R.id.getHiredTextView:
                startActivity(new Intent(OwnChoice.this,XpertRegistration.class));
                finish();
                break;


            case R.id.resendCode:
                ConnectivityManager connectivityManager = (ConnectivityManager)OwnChoice.this.getSystemService(CONNECTIVITY_SERVICE);
                NetworkInfo  networkInfo = connectivityManager.getActiveNetworkInfo();
                
                // REPLACE CODE HERE WHEN INTERNET RESUMES


                if (networkInfo != null  && networkInfo.isConnected() ){
                    new CheckAvailability().execute();



                }else {
                    Snackbar.make(v,"No Internet Connection",Snackbar.LENGTH_LONG).show();
                }


                break;



        }






    }



    class RegisterUser extends AsyncTask< String, String, String> {


        StringBuilder builder = new StringBuilder();
        BufferedReader bufferedReader;

        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            codeAccepted.showNext();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            if(s.equals("1")){
                editor.putBoolean("VERIFIED",true);
                editor.commit();
                codeAccepted.showNext();
                notVerifiedLayout.setClickable(false);
                verifiedLayout.setClickable(true);
                im.hideSoftInputFromWindow(otpAgain.getWindowToken(),0);

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {


                        notVerifiedLayout.startAnimation(AnimationUtils.loadAnimation(OwnChoice.this,R.anim.fab_close));
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                notVerifiedLayout.setVisibility(View.GONE);
                                notVerifiedLayout.setClickable(false);
                                verifiedLayout.startAnimation(AnimationUtils.loadAnimation(OwnChoice.this,R.anim.md_styled_slide_up_slow));
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        verifiedLayout.setVisibility(View.VISIBLE);
                                    }
                                },300);

                            }
                        },300);



                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                registerImageFlipper.setFlipInterval(3000);
                                registerTextFlipper.setFlipInterval(3000);
                                subtitleRegisterFlipper.setFlipInterval(3000);
                                registerImageFlipper.startFlipping();
                                registerTextFlipper.startFlipping();
                                subtitleRegisterFlipper.startFlipping();
                            }
                        },200);


                    }
                },500);


            }else{
                Toast.makeText(OwnChoice.this, "We are having trouble connecting to Internet, Please Try again", Toast.LENGTH_SHORT).show();
                codeAccepted.showNext();

            }


        }

        @Override
        protected String doInBackground(String... params) {



            nameValuePairs.add(new BasicNameValuePair("userPhone",userData.getString("userEmail","noEmailFound")));
            nameValuePairs.add(new BasicNameValuePair("userState",userData.getString("userState","noStateFound")));
            nameValuePairs.add(new BasicNameValuePair("userName",userData.getString("userName","noNameFound")));
            nameValuePairs.add(new BasicNameValuePair("userPassword",userData.getString("userPassword","noPasswordFound")));

            Log.e(TAG, "userName = " +userData.getString("userEmail","noEmailFound") );
            Log.e(TAG, "userState = " +userData.getString("userState","noEmailFound") );
            Log.e(TAG, "userName= " +userData.getString("userName","noEmailFound") );
            Log.e(TAG, "userPassword = " +userData.getString("userPassword","noEmailFound") );


            try {


                HttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost(getString(R.string.host)+"/registrations/register_user.php");

                httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                HttpResponse httpResponse = (HttpResponse)httpClient.execute(httpPost);
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
            } catch (Exception e){}



            return  builder.toString();
        }


    }

    class CheckAvailability extends AsyncTask< String, String, String>{


        StringBuilder builder = new StringBuilder();
        BufferedReader bufferedReader;

        List<NameValuePair> nameValuePairs = new ArrayList<>();


        @Override
        protected String doInBackground(String... params) {

            nameValuePairs.add(new BasicNameValuePair("userPhone",userData.getString("userEmail","userEmail")));
            Log.e(TAG,userData.getString("userEmail","userEmail"));
            try {


                HttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost =  new HttpPost(getString(R.string.host)+"/registrations/generate_random.php");
                httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

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

            resendCode.startAnimation(AnimationUtils.loadAnimation(OwnChoice.this,R.anim.fab_close));
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    resendCode.setVisibility(View.INVISIBLE);

                }
            },300);


        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            if (s.equals("1")){

                Toast.makeText(OwnChoice.this, "This email is already Registered With Us", Toast.LENGTH_SHORT).show();

            }else if (s.length() == 4){

                Snackbar.make(getCurrentFocus(),"OTP was sent Successfully",Snackbar.LENGTH_SHORT).show();
                editor = userData.edit();
                editor.putString("OTP",s);
                editor.apply();

            }


            else if (s.equals("die")) {


                Toast.makeText(OwnChoice.this, "Couldn't Connect to Our Server", Toast.LENGTH_SHORT).show();

            }else {

                Toast.makeText(OwnChoice.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
            }


            resendCode.startAnimation(AnimationUtils.loadAnimation(OwnChoice.this,R.anim.card_animation));
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    resendCode.setVisibility(View.VISIBLE);

                }
            },300);


        }
    }



}
