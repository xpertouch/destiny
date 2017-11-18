package corp.burenz.expertouch.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import corp.burenz.expertouch.R;

public class UpdateApp extends AppCompatActivity implements View.OnClickListener{

    SharedPreferences updateInfo;
    String UPDATE_INFO = "updateInfo";
    SharedPreferences.Editor editor;
    ImageView goBackUpdates;
    TextView checkForUpadtes;
    ViewFlipper progressFlipper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_app);

        updateInfo = getSharedPreferences(UPDATE_INFO,0);
        editor = updateInfo.edit();



        checkForUpadtes = (TextView) findViewById(R.id.checkForUpdates);
        progressFlipper = (ViewFlipper) findViewById(R.id.progressFlipper);
        goBackUpdates = (ImageView) findViewById(R.id.goBackUpdates);


        goBackUpdates.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdateApp.this.finish();
            }
        });


        checkForUpadtes.setOnClickListener(this);


        if (updateInfo.getBoolean("updateAvailable",false) ) {

            checkForUpadtes.setText("Update Available");

        }





    }


    @Override
    protected void onResume() {
        super.onResume();

        updateInfo = getSharedPreferences(UPDATE_INFO,0);
        if (updateInfo.getBoolean("updateAvailable",false) ) {

            checkForUpadtes.setText("Update Available");

        }
    }

    @Override
    public void onClick(View v) {



        switch (v.getId()){








            case R.id.checkForUpdates:


                if (checkForUpadtes.getText().toString().equals("Update Available")){
                    Uri uri = Uri.parse("market://details?id="+getPackageName());
                    startActivity(new Intent(Intent.ACTION_VIEW,uri));
                    return;
                }
                else{

                    new GetCurrentVersion().execute();

                }


                break;









        }





















    }



    class GetCurrentVersion extends AsyncTask< String , String, String >{

        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        StringBuilder builder = new StringBuilder();
        BufferedReader bufferedReader;



        @Override
        protected String doInBackground(String... params) {

            nameValuePairs.add(new BasicNameValuePair("version","silicon"));

            try {
                HttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost(getString(R.string.host)+"/jobs/app_version.php");

                HttpResponse httpResponse =  (HttpResponse) httpClient.execute(httpPost);

                HttpEntity httpEntity = (HttpEntity) httpResponse.getEntity();



                bufferedReader = new BufferedReader(new InputStreamReader(httpEntity.getContent()));
                String str = "";

                while (  (str = bufferedReader.readLine())  != null ){

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
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            SharedPreferences.Editor editor;
            updateInfo = getSharedPreferences(UPDATE_INFO,0);
            editor = updateInfo.edit();


            if (s.equals("1")){
                editor.putBoolean("updateAvailable",true);
                editor.apply();
                checkForUpadtes.setText("Update Available");
                progressFlipper.showNext();
            }else {
                Toast.makeText(UpdateApp.this, "You are currently Using the latest Version", Toast.LENGTH_SHORT).show();
                progressFlipper.showNext();
            }

        }


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressFlipper.showNext();
        }
    }



//    class GetCurrentVersion extends AsyncTask< String , String, String >{
//
//        JSONObject jsonObject;
//        JSONArray jsonArray;
//
//
//        StringBuilder builder = new StringBuilder();
//        BufferedReader bufferedReader;
//
//
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//
//            progressFlipper.showNext();
//
//        }
//
//        @Override
//        protected void onPostExecute(String s) {
//            super.onPostExecute(s);
//
//            progressFlipper.showNext();
//
//
//        }
//
//        @Override
//        protected String doInBackground(String... params) {
//
//
//
//                try {
//                    HttpClient httpClient = new DefaultHttpClient();
//                    HttpPost httpPost = new HttpPost("check_app_version.php");
//
//                    HttpResponse httpResponse =  (HttpResponse) httpClient.execute(httpPost);
//
//                    HttpEntity httpEntity = (HttpEntity) httpResponse.getEntity();
//
//
//
//                    bufferedReader = new BufferedReader(new InputStreamReader(httpEntity.getContent()));
//                    String str = "";
//
//                    while (  (str = bufferedReader.readLine())  != null ){
//
//                        builder.append(str);
//
//                    }
//
//                    jsonArray = new JSONArray(builder.toString());
//                    jsonObject = new JSONObject();
//
//
//
//                    jsonObject= jsonArray.getJSONObject(0);
//
//                    updateInfo = getSharedPreferences(APP_VERSION,0);
//
//                    if (!updateInfo.getString("updateInfo","0").equals(currentupdateInfo)){
//
//                        editor = updateInfo.edit();
//                        editor.putBoolean("newVersionFound",true);
//                        editor.apply();
//                        checkForUpadtes.setText("New version Found");
//                        updateFound.setVisibility(View.VISIBLE);
//
//                    }
//
//
//
//
//
//
//
//                } catch (ClientProtocolException e) {
//                    e.printStackTrace();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                } catch (Exception e){
//
//                }
//
//
//            return null;
//
//
//
//        }
//
//
//
//    }




}
