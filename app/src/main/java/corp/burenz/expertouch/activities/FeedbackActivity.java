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
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

import corp.burenz.expertouch.R;


public class FeedbackActivity extends AppCompatActivity {
	EditText myFeedback;
	SharedPreferences userData;
	String LOCAL_APP_DATA = "userInformation";
	Button sendFeedback;
	String MY_FEEDBACK;
	ViewFlipper bottomProgressFlipper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        myFeedback = (EditText) findViewById(R.id.myFeedback);
        bottomProgressFlipper = (ViewFlipper) findViewById(R.id.bottomProgressFeedback);
		sendFeedback = (Button)findViewById(R.id.sendFeedbackButton);

		sendFeedback.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

            if (myFeedback.getText().toString().length() > 5){
                new SendFeeback().execute();
            }else {
                Toast.makeText(FeedbackActivity.this, "Please mention at least 6 Characters", Toast.LENGTH_SHORT).show();
            }



            }
		});


    }




    class SendFeeback extends AsyncTask<String,String,String> {

					StringBuilder builder = new StringBuilder();
	    			ArrayList<NameValuePair> nameValuePair = new ArrayList<>();BufferedReader reader;

    		@Override
    		protected void onPreExecute(){
    				super.onPreExecute();

    		   MY_FEEDBACK = myFeedback.getText().toString();
				bottomProgressFlipper.showNext();

    		} 

    			@Override
    			protected String doInBackground(String... params){
    			
    			
    					userData = getSharedPreferences(LOCAL_APP_DATA,0);
        nameValuePair.add(new BasicNameValuePair("userPhone",userData.getString("userEmail","noemail@gmailcom")));
        nameValuePair.add(new BasicNameValuePair("feedback",MY_FEEDBACK));
    			


    			try{


    				HttpClient httpClient = new DefaultHttpClient();
    				HttpPost httpPost = new HttpPost(getString(R.string.host)+"/other/feedback.php");
    				httpPost.setEntity(new UrlEncodedFormEntity(nameValuePair));
    				HttpResponse httpResponse = (HttpResponse) httpClient.execute(httpPost);
    				HttpEntity httpEntity = httpResponse.getEntity();
    				String str = "";
    				reader = new BufferedReader(new InputStreamReader(httpEntity.getContent()));

    					while( (str = reader.readLine() ) != null ){
    						builder.append(str);			
    					}


    			}catch(Exception e){
    							e.printStackTrace();
    			}
    				



				return builder.toString();

    			} 	





				@Override
				protected void onPostExecute(String s){
						super.onPostExecute(s);

					Log.e("outcome",s);

					if (s.contains("1")){
								bottomProgressFlipper.showNext();
								Toast.makeText(FeedbackActivity.this, "Feedback sent Successfully", Toast.LENGTH_SHORT).show();
								finish();
								// feedback sent here

							}else {
								bottomProgressFlipper.showNext();
								Toast.makeText(FeedbackActivity.this, "Connection Slow", Toast.LENGTH_SHORT).show();

								// feedback Sending failed
							}
						
				}
    			}





public void sendFeedback(View v){


	new SendFeeback();

}





    }









