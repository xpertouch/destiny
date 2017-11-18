package corp.burenz.expertouch.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
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
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import corp.burenz.expertouch.R;


public class RegisterCompany extends AppCompatActivity implements View.OnClickListener {

    String COMPANY_DETAILS = "myCompanyDetails";
    SharedPreferences myCompanyDetails;
    SharedPreferences.Editor editor;
    String LOCAL_APP_DATA = "userInformation";
    String COMPANY_NAME;
    ViewFlipper welcomeFlipper,companyButtonsFlipper,companyImagesFlipper,companyEditTextFlipper,companySubtitleFlipper;
    String companyName,companyTag,companyPhone,companyEmail,companyVisit,companyDiscription,companyState,companyCity,   companylandmark;

    ViewFlipper companyScanFlipper;

    //Tricky Layout

    ViewFlipper companyTflipper;
    Button iAgree;
    TextView companyTtitle,companyTsubtitle;


    Button companyNameB,companyTagB,companyPhoneB,companyVisitB,companyEmailB,companyStateB,companyLandmarkB,companyDiscriptionB,companyCityB;
    EditText companyNameE,companyTagE,companyPhoneE,companyVisitE,companyEmailE,companyLandmarkE,companyDiscriptionE,companyCityE;
    Spinner companyStateE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_company);

        //  RegisterCompany.this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);



        initViews();


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                companyTflipper.showNext();
            }
        },300);


        InputMethodManager im;

        im = (InputMethodManager)RegisterCompany.this.getSystemService(INPUT_METHOD_SERVICE);
        im.hideSoftInputFromWindow(companyNameE.getWindowToken(),0);

        setTexts();


    }

    public void initViews(){


        //Flippers
        welcomeFlipper = (ViewFlipper)findViewById(R.id.welcomeFlipper);
        companyButtonsFlipper = (ViewFlipper)findViewById(R.id.companyButtonsFlipper);
        companyImagesFlipper = (ViewFlipper)findViewById(R.id.companyImagesFlipper);
        companyEditTextFlipper = (ViewFlipper)findViewById(R.id.companyEditTexts);
        companySubtitleFlipper =(ViewFlipper)findViewById(R.id.companySubtitleFlipper);
        companyScanFlipper = (ViewFlipper) findViewById(R.id.companyScanFlipper);

        //Buttons
        companyNameB = (Button)findViewById(R.id.companyNameB);
        companyTagB = (Button)findViewById(R.id.companyTagB);
        companyPhoneB = (Button)findViewById(R.id.companyPhoneB);
        companyEmailB = (Button)findViewById(R.id.companyEmailB);
        companyVisitB = (Button)findViewById(R.id.companyVistB);
        companyDiscriptionB = (Button)findViewById(R.id.companyDiscriptionB);
        companyStateB = (Button)findViewById(R.id.companyStateB);
        companyCityB = (Button)findViewById(R.id.companyCtiyB);
        companyLandmarkB = (Button)findViewById(R.id.companylandmarkB);


        //Tricky Layout

        companyTflipper = (ViewFlipper) findViewById(R.id.companyTflipper);
        companyTtitle = (TextView) findViewById(R.id.companyTtitle);
        companyTsubtitle = (TextView) findViewById(R.id.companyTsubtitle);
        iAgree = (Button)findViewById(R.id.iAgree);







        companyNameE = (EditText)findViewById(R.id.companyNameE);
        companyTagE = (EditText)findViewById(R.id.companyTagE);
        companyPhoneE = (EditText)findViewById(R.id.companyOfficeE);
        companyEmailE = (EditText)findViewById(R.id.companyEmailE);
        companyVisitE = (EditText)findViewById(R.id.companyVisitE);
        companyDiscriptionE = (EditText)findViewById(R.id.companyDiscriptionE);
        companyStateE = (Spinner)findViewById(R.id.companyStateE);
        companyCityE = (EditText)findViewById(R.id.companyCityE);
        companyLandmarkE = (EditText)findViewById(R.id.companyLandmarkE);

        companyNameB.setOnClickListener(this);
        companyTagB.setOnClickListener(this);
        companyPhoneB.setOnClickListener(this);
        companyEmailB.setOnClickListener(this);
        companyVisitB.setOnClickListener(this);
        companyDiscriptionB.setOnClickListener(this);
        companyStateB.setOnClickListener(this);
        companyCityB.setOnClickListener(this);
        companyLandmarkB.setOnClickListener(this);
        iAgree.setOnClickListener(this);



    }

    public void setTexts(){

        myCompanyDetails = getSharedPreferences(COMPANY_DETAILS,0);


        if (myCompanyDetails.getString("companyName","CompanyName").equals("CompanyName")){

            companyNameE.setHint("your company title here...");
            companyTagE.setHint("your company tag line or motto...");
            companyPhoneE.setHint("1234567891");
            companyEmailE.setHint("mail@example.com");
            companyVisitE.setHint("www.abc.com");
            companyDiscriptionE.setHint("your company description here...");
            companyCityE.setHint("your company city...");
            companyLandmarkE.setHint("company landmark here...");



        }else {

            companyNameE.setText(myCompanyDetails.getString("companyName","Enter Company Name Here"));
            companyTagE.setText(myCompanyDetails.getString("companyTag","Enter Company Tag Line Here"));
            companyPhoneE.setText(myCompanyDetails.getString("companyPhone","Enter Company Phone Here"));
            companyEmailE.setText(myCompanyDetails.getString("companyEmail","Enter Company Email Here"));
            companyVisitE.setText(myCompanyDetails.getString("companyVisit","Enter Company Website Here"));
            companyDiscriptionE.setText(myCompanyDetails.getString("companyDiscription","Enter Company About Here"));
            companyCityE.setText(myCompanyDetails.getString("companyCity","Enter Company City Here"));
            companyLandmarkE.setText(myCompanyDetails.getString("companyLandmark","Enter Company landmark Here"));


        }


    }




    @Override
    public void onClick(View v) {


        switch (v.getId()){





            case R.id.iAgree:

                companyTflipper.showPrevious();
                welcomeFlipper.showNext();
                companyButtonsFlipper.showNext();
                break;


            case R.id.companyNameB:

                companyName =  companyNameE.getText().toString();

                if (companyName.length() < 4){
                    Toast.makeText(RegisterCompany.this, "Company name must be at least 4 Characters Long", Toast.LENGTH_SHORT).show();
                }else{
                    SharedPreferences myData = getSharedPreferences(LOCAL_APP_DATA,0);
                    companyState  = myData.getString("userState","Jammu and Kashmir");
                    companyVisit = "please update your website here";
                    new CheckCompany().execute();
                }

                break;

            case R.id.companyTagB:

                companyTag =  companyTagE.getText().toString();

                if (companyTag.length() > 5 ){
                    companyButtonsFlipper.showNext();
                    companyImagesFlipper.showNext();
                    companySubtitleFlipper.showNext();
                    companyEditTextFlipper.showNext();

                }else {
                    Toast.makeText(RegisterCompany.this, "Company Tag must be at least 5 Characters Long", Toast.LENGTH_SHORT).show();
                }


                break;

            case R.id.companyPhoneB:

                companyPhone = companyPhoneE.getText().toString();

                if (companyPhone.length() == 10){

                    companyButtonsFlipper.showNext();
                    companyImagesFlipper.showNext();
                    companySubtitleFlipper.showNext();
                    companyEditTextFlipper.showNext();
                }else {
                    Toast.makeText(RegisterCompany.this, "Company Phone must be exactly 10 Digits Long", Toast.LENGTH_SHORT).show();
                }


                break;

            case R.id.companyEmailB:

                companyEmail = companyEmailE.getText().toString();
                if (companyEmail.contains("@") && companyEmail.contains(".com")) {

                    if (companyEmail.contains(" ")){
                        Toast.makeText(RegisterCompany.this, "Email address must not contains any spaces", Toast.LENGTH_SHORT).show();

                    }else {

                        companyButtonsFlipper.showNext();
                        companyImagesFlipper.showNext();
                        companySubtitleFlipper.showNext();
                        companyEditTextFlipper.showNext();
                    }

                }else {
                    Toast.makeText(RegisterCompany.this, "Please Mention a valid Email Address", Toast.LENGTH_SHORT).show();

                }

                break;

            case R.id.companyVistB:

                companyVisit = companyVisitE.getText().toString();

                if (companyVisit.contains(".com") && companyVisit.contains("www.") ){

                    if (companyVisit.length() > 9){

                        companyButtonsFlipper.showNext();
                        companyImagesFlipper.showNext();
                        companySubtitleFlipper.showNext();
                        companyEditTextFlipper.showNext();

                    }else{
                        Toast.makeText(RegisterCompany.this, "Company Website Must be at least 10 Characters Long ", Toast.LENGTH_SHORT).show();
                    }

                }else {
                    Toast.makeText(RegisterCompany.this, "Please Mention a valid Web Address", Toast.LENGTH_SHORT).show();
                }

                break;

            case R.id.companyDiscriptionB:

                companyDiscription = companyDiscriptionE.getText().toString();

                if (companyDiscription.length() > 10 && companyDiscription.length() <40){

                    companyButtonsFlipper.showNext();
                    companyImagesFlipper.showNext();
                    companySubtitleFlipper.showNext();
                    companyEditTextFlipper.showNext();

                }else {
                    Toast.makeText(RegisterCompany.this, "Please describe your company in more than 10 Characters but less than 40 Characters", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.companyStateB:



                if (companyState.equals("Select Your State")){
                    Toast.makeText(RegisterCompany.this, "Please mention a Valid State", Toast.LENGTH_SHORT).show();
                }else{

                    companyButtonsFlipper.showNext();
                    companyImagesFlipper.showNext();
                    companySubtitleFlipper.showNext();
                    companyEditTextFlipper.showNext();

                }



                break;


            case R.id.companyCtiyB:

                companyCity = companyCityE.getText().toString();
                if (companyCity.length() > 10 ){
                    companyButtonsFlipper.showNext();
                    companyImagesFlipper.showNext();
                    companySubtitleFlipper.showNext();
                    companyEditTextFlipper.showNext();
                }else {
                    Toast.makeText(RegisterCompany.this, "Please Mention your Area as well ,Company City must be greater than 10 Characters Long", Toast.LENGTH_SHORT).show();
                }


                break;

            case R.id.companylandmarkB:

                companylandmark = companyLandmarkE.getText().toString();

                if (companylandmark.length() > 10){
                    new AddCompany(RegisterCompany.this).execute();
                }else{
                    Toast.makeText(RegisterCompany.this, "Company Landmark must be at least 10 Characters Long", Toast.LENGTH_SHORT).show();
                }


                break;

        }

    }



    class AddCompany extends AsyncTask< String, String, String>{


        SharedPreferences userData;


        Context context;

        StringBuilder builder = new StringBuilder();
        BufferedReader bufferedReader;
        List<NameValuePair> companyDetails = new ArrayList<>();





        public AddCompany(Context context){
            this.context = context;
        }


        @Override
        protected String doInBackground(String... params) {

            userData = getSharedPreferences(LOCAL_APP_DATA,0);


            companyDetails.add(new BasicNameValuePair("companyTitle",companyName));
            companyDetails.add(new BasicNameValuePair("companyTag",companyTag));
            companyDetails.add(new BasicNameValuePair("companyPhone",companyPhone));
            companyDetails.add(new BasicNameValuePair("companyEmail",companyEmail));
            companyDetails.add(new BasicNameValuePair("companyVisit",companyVisit));
            companyDetails.add(new BasicNameValuePair("companyDiscription",companyDiscription));
            companyDetails.add(new BasicNameValuePair("companyState",companyState));
            companyDetails.add(new BasicNameValuePair("companyLandmark",companylandmark));
            companyDetails.add(new BasicNameValuePair("companyCity",companyCity));
            companyDetails.add(new BasicNameValuePair("userPhone",userData.getString("userEmail","none")));



            myCompanyDetails = getSharedPreferences(COMPANY_DETAILS,0);
            editor = myCompanyDetails.edit();


            editor.putString("companyName",companyName);
            editor.putString("companyTag",companyTag);
            editor.putString("companyPhone",companyPhone);
            editor.putString("companyEmail",companyEmail);
            editor.putString("companyVisit",companyVisit);
            editor.putString("companyDiscription",companyDiscription);
            editor.putString("companyState",companyState);
            editor.putString("companyCity",companyCity);
            editor.putString("companyLandmark",companylandmark);
            editor.putString("companyBanner",getString(R.string.host)+"/mycompany/defaults/company_default.jpg");
            editor.apply();




            try{

                HttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost(getString(R.string.host)+"/profile/register_company.php");
                httpPost.setEntity(new UrlEncodedFormEntity(companyDetails));

                HttpResponse httpResponse = (HttpResponse)httpClient.execute(httpPost);
                HttpEntity httpEntity = (HttpEntity)httpResponse.getEntity();

                bufferedReader = new BufferedReader(new InputStreamReader(httpEntity.getContent()));
                String str = "";

                while ((str = bufferedReader.readLine()) != null){
                    builder.append(str);
                }


            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }catch (Exception e){

            }


            return builder.toString();




        }


        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            companyTtitle.setText("Hang On");
            companyTsubtitle.setText("Sit back and relax While we are setting important things for you , This process may take a while");
            companyTflipper.showNext();
            welcomeFlipper.showPrevious();
            companyButtonsFlipper.showNext();




        }




        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            SharedPreferences userData;
            String LOCAL_APP_DATA = "userInformation";

            userData = getSharedPreferences(LOCAL_APP_DATA,0);
            editor = userData.edit();

            if (s.equals("1")){
                editor.putBoolean("COMPANY",true);
                editor.commit();
                startActivity(new Intent(context,MyCompany.class));
                RegisterCompany.this.finish();
            }
            else {
                Toast.makeText(context, "We are having trouble Connecting to Internet, Please try again Later" , Toast.LENGTH_SHORT).show();
                companyTflipper.showPrevious();
                welcomeFlipper.showNext();
                companyButtonsFlipper.showPrevious();

            }


            Log.e("REGOUTPUT",s);

        }




    }



    public  void goBackBussiness(View v){

        companyButtonsFlipper.showPrevious();
        companyImagesFlipper.showPrevious();
        companyEditTextFlipper.showPrevious();
        companySubtitleFlipper.showPrevious();

    }

    class CheckCompany extends AsyncTask<String,String,String> {

        StringBuilder builder = new StringBuilder();
        BufferedReader bufferedReader;
        List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>();


        public void onPreExecute() {
            super.onPreExecute();
            companyScanFlipper.showNext();
            companyNameE.setEnabled(false);


        }


        protected String doInBackground(String... params) {


            nameValuePair.add(new BasicNameValuePair("companyTitle", companyName));

            try {

                HttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost(getString(R.string.host)+"/profile/check_name.php");
                httpPost.setEntity(new UrlEncodedFormEntity(nameValuePair));
                HttpResponse httpResponse = (HttpResponse) httpClient.execute(httpPost);
                HttpEntity httpEntity = (HttpEntity) httpResponse.getEntity();

                bufferedReader = new BufferedReader(new InputStreamReader(httpEntity.getContent()));
                String str = "";

                while ((str = bufferedReader.readLine()) != null) {
                    builder.append(str);
                }


            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {

            }


            return builder.toString();
        }


        public void onPostExecute(String s) {


            if (s.equals("1")) {

                companyNameE.startAnimation(AnimationUtils.loadAnimation(RegisterCompany.this,R.anim.shakeanim));
                Toast.makeText(RegisterCompany.this, "This Company Name is Already Registered With us", Toast.LENGTH_SHORT).show();
                companyScanFlipper.showNext();
                companyNameE.setEnabled(true);


            } else if (s.equals("0")) {


                companyButtonsFlipper.showNext();
                companyImagesFlipper.showNext();
                companySubtitleFlipper.showNext();
                companyEditTextFlipper.showNext();
                companyScanFlipper.showNext();
                companyNameE.setEnabled(true);


            } else {


                Toast.makeText(RegisterCompany.this, "We are having Trouble Connecting to Internet, please try again Later", Toast.LENGTH_SHORT).show();
                companyScanFlipper.showNext();
                companyNameE.setEnabled(true);

            }


        }



    }



}





