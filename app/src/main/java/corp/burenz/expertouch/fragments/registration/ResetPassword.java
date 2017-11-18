package corp.burenz.expertouch.fragments.registration;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
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

/**
 * Created by xperTouch on 8/4/2016.
 */
public class ResetPassword extends Fragment {



    Button proceedButton;
    EditText userEmail,enterOTP,enterPassword;
    ViewFlipper editTextFeilds,bottomButtonsFlipper;
    ViewFlipper otpSentFlipper,passwordChangeProgress;
    LinearLayout stepOne,stepTwo,stepThree;
    TextView stepTitleV,stepSubtitleV;
    Button submitOTP,changePass;
    String otpRecieved;
    Animation slide;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v  = inflater.inflate(R.layout.reset_password,container,false);



        editTextFeilds = (ViewFlipper) v.findViewById(R.id.editTextsFeilds);
        bottomButtonsFlipper = (ViewFlipper) v.findViewById(R.id.bottomButtonsFlipper);
        proceedButton  = (Button) v.findViewById(R.id.proceedButton);
        userEmail = (EditText) v.findViewById(R.id.resetEmail);
        otpSentFlipper = (ViewFlipper) v.findViewById(R.id.otpSentFlipper);

        stepOne = (LinearLayout) v.findViewById(R.id.stepOne);
        stepTwo = (LinearLayout) v.findViewById(R.id.stepTwo);
        stepThree = (LinearLayout) v.findViewById(R.id.stepThree);

        stepTitleV = (TextView) v.findViewById(R.id.stepTitileV);
        stepSubtitleV = (TextView) v.findViewById(R.id.stepSubtitleV);
        submitOTP = (Button)v.findViewById(R.id.submitOTP);

        enterOTP = (EditText)v.findViewById(R.id.enterOTP);
        enterPassword = (EditText) v.findViewById(R.id.enterPassword);


        passwordChangeProgress = (ViewFlipper) v.findViewById(R.id.passwordChangeProgress);
        changePass = (Button) v.findViewById(R.id.changePass);

        slide = AnimationUtils.loadAnimation(getActivity(),R.anim.md_styled_slide_up_slow);



        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                stepOne.startAnimation(slide);

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        stepOne.setVisibility(View.VISIBLE);
                    }
                },300);


            }
        },300);



        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                stepTwo.startAnimation(slide);


                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        stepTwo.setVisibility(View.VISIBLE);
                    }
                },300);

            }
        },300);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                stepThree.startAnimation(slide);


                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        stepThree.setVisibility(View.VISIBLE);
                    }
                },300);


            }
        },300);




        changePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // validate

                if (enterPassword.getText().toString().length() >7 ){

                    new UpdatePassword().execute();

                }else{
                    Toast.makeText(getActivity(), "Password length must be at least 8 characters long", Toast.LENGTH_SHORT).show();
                }

            }
        });


        proceedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (userEmail.getText().toString().length() == 10){
                    if (userEmail.getText().toString().contains(" ")){
                        Toast.makeText(getActivity(), "Phone Number must not contain spaces", Toast.LENGTH_SHORT).show();
                    }else{

                        new CheckAvailability().execute();
                    }
                }else{
                    Toast.makeText(getActivity(), "Please Enter 10 digits exactly", Toast.LENGTH_SHORT).show();
                }




            }
        });

        submitOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



            }
        });


        submitOTP.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                String otp = enterOTP.getText().toString();
                if (otp.equals(otpRecieved)){

                    stepOne.setBackgroundColor(Color.TRANSPARENT);
                    stepTwo.setBackgroundColor(Color.TRANSPARENT);
                    stepThree.setBackgroundColor(Color.argb(198,0,0,0));


                    editTextFeilds.showNext();
                    bottomButtonsFlipper.showNext();

                    stepTitleV.setText("Step 3");
                    stepSubtitleV.setText("Enter New Password For "+userEmail.getText().toString());

                }else {
                    Snackbar.make(v,"OTP Doesn't Match",Snackbar.LENGTH_LONG).show();
                }





            }
        });


        return v;

    }


    class CheckAvailability extends AsyncTask< String, String, String> {


        StringBuilder builder = new StringBuilder();
        BufferedReader bufferedReader;

        List<NameValuePair> nameValuePairs = new ArrayList<>();


        @Override
        protected String doInBackground(String... params) {



            nameValuePairs.add(new BasicNameValuePair("userPhone",userEmail.getText().toString()));

            try {


                HttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost =  new HttpPost(getString(R.string.host)+"/registrations/reset_password.php");
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

            otpSentFlipper.showNext();

        }


        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            
            if (s.equals("1")){

                Toast.makeText(getActivity(), "This Phone Number is Not Registered With Us", Toast.LENGTH_SHORT).show();
                otpSentFlipper.showNext();

            }else if (s.length() == 4){

                otpRecieved = s;
                Toast.makeText(getActivity(), "Verification Code is Sent to your Phone Number", Toast.LENGTH_SHORT).show();
                otpSentFlipper.showNext();
                editTextFeilds.showNext();
                bottomButtonsFlipper.showNext();
                stepTitleV.setText("Step 2");
                stepSubtitleV.setText("Enter Verification code sent to " + userEmail.getText().toString());

                stepOne.setBackgroundColor(Color.TRANSPARENT);
                stepTwo.setBackgroundColor(Color.argb(198,0,0,0));


            }


            else if (s.equals("die")) {

                Toast.makeText(getActivity(), "Server Not Responding, please try again later", Toast.LENGTH_SHORT).show();
                otpSentFlipper.showNext();

            }else {

                Toast.makeText(getActivity(), "Connection Slow, couldn't Connect To Server, Please Try Again" +s, Toast.LENGTH_SHORT).show();
                otpSentFlipper.showNext();

            }



        }



    }



    class UpdatePassword extends AsyncTask< String, String, String > {


        StringBuilder builder  = new StringBuilder();
        BufferedReader bufferedReader;

        List<NameValuePair> updatePassword = new ArrayList<>();


        protected String doInBackground(String... paramas){

            updatePassword.add(new BasicNameValuePair("userPhone",userEmail.getText().toString()));
            updatePassword.add(new BasicNameValuePair("newPassword",enterPassword.getText().toString()));


            try{


                HttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost(getString(R.string.host)+"/registrations/new_password.php");
                httpPost.setEntity(new UrlEncodedFormEntity(updatePassword));
                HttpResponse  httpResponse = (HttpResponse) httpClient.execute(httpPost);

                HttpEntity httpEntity = (HttpEntity) httpResponse.getEntity();


                bufferedReader = new BufferedReader(new InputStreamReader(httpEntity.getContent()));
                String str = "";

                while( (str = bufferedReader.readLine()) != null ){

                    builder.append(str);
                }







            }catch(Exception e ){

            }




            return builder.toString();



        }




        protected void onPostExecute( String s ){
            super.onPostExecute(s);


            if ( s.equals("0") ) {

                Toast.makeText(getActivity(),"Connection Slow Please Try again",Toast.LENGTH_LONG).show();

            }else if ( s.equals("1") ) {

                Toast.makeText(getActivity()," Password Changed Successfully ",Toast.LENGTH_LONG).show();
                passwordChangeProgress.showNext();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_server_frame_layout,new LoginFragment()).commit();

            }






        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            passwordChangeProgress.showNext();

        }
    }


}
