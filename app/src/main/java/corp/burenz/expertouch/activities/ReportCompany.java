package corp.burenz.expertouch.activities;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
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
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import corp.burenz.expertouch.R;

public class ReportCompany extends AppCompatActivity {


    View myView;
    ViewFlipper companyListFlipper,sendReportFlipper;
    Button submitCompanyReportB;
    Spinner companyListS;
    EditText companyMessageE,adminMessageE;
    String REPORT_REASON,ADMIN_MESSAGE,COMPANY,USER_EMAIL;
    SharedPreferences userData;
    String LOCAL_APP_DATA = "userInformation";
    ArrayAdapter<String> typeAdapter;
    ArrayList<String> companyNames;
    LinearLayout noConnectionView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_company);


        userData = getSharedPreferences(LOCAL_APP_DATA,0);
        USER_EMAIL =  userData.getString("userEmail","noEmail@gmail.com");
        setViews();



        new GetCompanyList().execute();

        submitCompanyReportB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myView = v;

                if (companyListS.getSelectedItem().toString().equals("Select Company")){
                    Snackbar.make(myView,"Please Select a Valid Company",Snackbar.LENGTH_SHORT).show();
                }else{
                    new SendReport().execute();
                }


            }
        });









    }



    void setViews(){

        companyListFlipper = (ViewFlipper) findViewById(R.id.companyListFlipper);
        sendReportFlipper = (ViewFlipper) findViewById(R.id.sendReportFlipper);
        submitCompanyReportB = (Button) findViewById(R.id.submitCompanyReport);
        companyListS = (Spinner) findViewById(R.id.companyListS);
        companyMessageE = (EditText) findViewById(R.id.companyMessageE);
        adminMessageE = (EditText) findViewById(R.id.adminMesssageE);
        noConnectionView = (LinearLayout) findViewById(R.id.noConnectionView);



    }




    class GetCompanyList extends AsyncTask<String,String,String>{



        StringBuilder builder = new StringBuilder();
        List<NameValuePair> nameValuePairs  = new ArrayList<NameValuePair>();
        BufferedReader bufferedReader;
        JSONObject jsonObject;
        JSONArray jsonArray;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            companyNames = new ArrayList<>();


        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            if(checkConnection()){
                if(companyNames.size()>0){
                    typeAdapter = new ArrayAdapter<String>(ReportCompany.this, R.layout.spinner_adapter_black, R.id.spinnerTextBlack,companyNames);
                    companyListS.setAdapter(typeAdapter);
                    companyListFlipper.showNext();

                }else if (companyNames.size() == 0) {
                    Toast.makeText(ReportCompany.this, "No Public Companies found", Toast.LENGTH_SHORT).show();

                }

            }else {

                noConnectionView.setVisibility(View.VISIBLE);
            }




        }


        boolean checkConnection(){

           ConnectivityManager connectivityManager = (ConnectivityManager)  ReportCompany.this.getSystemService(CONNECTIVITY_SERVICE);
           NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

            if(networkInfo !=null && networkInfo.isConnected()){
                return true;

            } else {
                return false;
            }

        }

        @Override
        protected String doInBackground(String... params) {

            userData = getSharedPreferences(LOCAL_APP_DATA,0);

            try{


                HttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost(getString(R.string.host)+"/mycompany/get_company_list.php");
                httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                HttpResponse httpResponse = (HttpResponse) httpClient.execute(httpPost);

                HttpEntity httpEntity = (HttpEntity)httpResponse.getEntity();


                bufferedReader = new BufferedReader(new InputStreamReader(httpEntity.getContent()));
                String str = "";


                while( (str = bufferedReader.readLine()) != null ){
                    builder.append(str);
                }




                jsonObject = new JSONObject();
                jsonArray = new JSONArray(builder.toString());

                int length = jsonArray.length();

                companyNames.add("Select Company");
                for (int i = 0; i < length; i++){

                    jsonObject = jsonArray.getJSONObject(i);
                    companyNames.add(jsonObject.getString("companyTitle"));

                }




            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e){
                e.printStackTrace();
            }


            return builder.toString();


        }
    }



    class SendReport extends AsyncTask<String,String,String>{

        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        BufferedReader bufferedReader;
        StringBuilder builder = new StringBuilder();


        @Override
        protected String doInBackground(String... params) {




            nameValuePairs.add(new BasicNameValuePair("userPhone",USER_EMAIL));
            nameValuePairs.add(new BasicNameValuePair("companyTitle",COMPANY));
            nameValuePairs.add(new BasicNameValuePair("reportReason",REPORT_REASON));
            nameValuePairs.add(new BasicNameValuePair("adminMessage",ADMIN_MESSAGE));

            try{

                HttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost(getString(R.string.host)+"/mycompany/report_mech.php");
                httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                HttpResponse httpResponse = (HttpResponse) httpClient.execute(httpPost);
                HttpEntity httpEntity = (HttpEntity) httpResponse.getEntity();
                bufferedReader = new BufferedReader(new InputStreamReader(httpEntity.getContent()));
                String str = "";

                while ( (str = bufferedReader.readLine()) != null ){
                    builder.append(str);
                }


            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


            return builder.toString();



        }


        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            sendReportFlipper.showNext();
            COMPANY = companyListS.getSelectedItem().toString();
            REPORT_REASON = companyMessageE.getText().toString();
            ADMIN_MESSAGE = adminMessageE.getText().toString();


        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);


            if (s.contains("1")){
                // done here
                Snackbar snackbar = Snackbar.make(myView, "Report Submitted Successfully", Snackbar.LENGTH_LONG)
                        .setAction("Action", null);
                snackbar.show();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        finish();
                    }
                },1000);
            }else if (s.contains("0")){
                //already recieved a request from user
                Snackbar snackbar = Snackbar.make(myView, "Report Already Submitted", Snackbar.LENGTH_LONG)
                        .setAction("Action", null);
                View sbView = snackbar.getView();
                TextView tv = (TextView)sbView.findViewById(android.support.design.R.id.snackbar_text);
                tv.setGravity(Gravity.CENTER);
                tv.setTextColor(Color.WHITE);
                sbView.setBackgroundColor(Color.RED);
                snackbar.show();

            }else {
                // network Error


                Snackbar snackbar = Snackbar.make(myView, "Poor Connection,Please Try again", Snackbar.LENGTH_LONG);
                View sbView = snackbar.getView();
                TextView tv = (TextView)sbView.findViewById(android.support.design.R.id.snackbar_text);
                tv.setGravity(Gravity.CENTER);
                tv.setTextColor(Color.WHITE);
                sbView.setBackgroundColor(Color.RED);
                snackbar.show();
                noConnectionView.setVisibility(View.VISIBLE);




            }




            sendReportFlipper.showNext();


        }
    }



}

