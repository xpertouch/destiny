package corp.burenz.expertouch.activities;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class AddMissingExpertise extends AppCompatActivity {


    EditText addCatagoryED,addtypeED;
    Button submitExpertiseLL;
    String addCatagory,addtype;

    ViewFlipper addMissingFlipper;

    SharedPreferences userData;
    String LOCAL_APP_DATA = "userInformation";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_missing_expertise);
        addCatagoryED       = (EditText)    findViewById(R.id.addCatagoryED);
        addtypeED           = (EditText)    findViewById(R.id.addTypeED);
        submitExpertiseLL   = (Button)      findViewById(R.id.submitExpertiseLL);
        addMissingFlipper   = (ViewFlipper) findViewById(R.id.addMissingFlipper);

        submitExpertiseLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addCatagory = addCatagoryED.getText().toString();
                addtype     = addtypeED.getText().toString();

                new  AddExpertise().execute();

            }
        });


    }










class AddExpertise extends AsyncTask<String,String,String>{


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        addMissingFlipper.showNext();
    }
    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        Log.e("outcome",s);

        if (s.equals("1")){
            addMissingFlipper.showNext();
            Toast.makeText(AddMissingExpertise.this, "Thank You For your Time, We would include the said Expertise in the Next Version", Toast.LENGTH_SHORT).show();
            finish();
        }else{
            // not received here
            addMissingFlipper.showNext();
            Toast.makeText(AddMissingExpertise.this, "We couldn't establish a secure connection with the server, Please try again Later", Toast.LENGTH_SHORT).show();
        }

    }
    @Override
    protected String doInBackground(String... params) {


        StringBuilder builder = new StringBuilder();
        BufferedReader bufferedReader;
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        userData = getSharedPreferences(LOCAL_APP_DATA,0);
        nameValuePairs.add(new BasicNameValuePair("userPhone",userData.getString("userEmail","noemail@gmailcom")));
        nameValuePairs.add(new BasicNameValuePair("category",addCatagory));
        nameValuePairs.add(new BasicNameValuePair("subCategory",addtype));


        try{


            HttpClient httpClient = new DefaultHttpClient();
            HttpPost  httpPost = new HttpPost(getString(R.string.host)+"/other/add_missing.php");
            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            HttpResponse httpResponse = (HttpResponse) httpClient.execute(httpPost);
            HttpEntity httpEntity = httpResponse.getEntity();

            bufferedReader = new BufferedReader(new InputStreamReader(httpEntity.getContent()));
            String str = "";
            while ( (str =  bufferedReader.readLine()) != null  ){
                builder.append(str);
            }

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e){

        }


        return builder.toString();

    }
    }



    public void sendMissing(View v){

        new AddExpertise().execute();

    }




}



