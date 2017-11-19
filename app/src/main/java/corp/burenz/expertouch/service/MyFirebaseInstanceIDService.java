package corp.burenz.expertouch.service;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.google.firebase.messaging.FirebaseMessaging;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import corp.burenz.expertouch.R;
import corp.burenz.expertouch.util.Config;


/**
 * Created by Ravi Tamada on 08/08/16.
 * www.androidhive.info
 */
public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {
    private static final String TAG = MyFirebaseInstanceIDService.class.getSimpleName();
    String NARRATION = "narrate";

    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        // Saving reg id to shared preferences
        storeRegIdInPref(refreshedToken);

        // sending reg id to your server
        sendRegistrationToServer(refreshedToken);



        // Notify UI that registration has completed, so the progress indicator can be hidden.
        Intent registrationComplete = new Intent(Config.REGISTRATION_COMPLETE);
        registrationComplete.putExtra("token", refreshedToken);
        LocalBroadcastManager.getInstance(this).sendBroadcast(registrationComplete);
    }

    private void sendRegistrationToServer(final String token) {
        // sending gcm token to server
        Log.e(TAG, "sendRegistrationToServer: " + token);
        FirebaseMessaging.getInstance().subscribeToTopic("news");
    }

    private void storeRegIdInPref(String token) {
        SharedPreferences pref = getApplicationContext().getSharedPreferences(Config.SHARED_PREF, 0);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("regId", token);
        editor.commit();

    }


    class UpdatFCMToken extends AsyncTask<String, String, String>{


        @Override
        protected String doInBackground(String... strings) {

            String urlToHit = getString(R.string.host) + "/registerations/update_fcm.php";
            URL url;

            StringBuilder stringBuilder         = new StringBuilder();
            HttpURLConnection httpURLConnection;


            try {

                url = new URL(urlToHit + "?fcm_id=" + URLEncoder.encode(strings[0],"UTF-8") + "&userPhone="+strings[1]);
                httpURLConnection = (HttpURLConnection) url.openConnection();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));

                String line = "";
                while ( (line = bufferedReader.readLine()) != null  ){
                    stringBuilder.append(line + "\n");
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


            return stringBuilder.toString();


        }


        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);


            SharedPreferences sharedPreferences = getSharedPreferences(NARRATION, 0);
            SharedPreferences.Editor editor = sharedPreferences.edit();

            if(s.contains("success")){
                editor.putBoolean("token",true);
                editor.apply();
            }else{
                editor.putBoolean("token",false);
                editor.apply();
            }




        }
    }



}

