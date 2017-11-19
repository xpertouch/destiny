package corp.burenz.expertouch.butter;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by buren on 19/11/17.
 */

public class SendFCMToken extends AsyncTask<String, String, String> {

    private String  urlToHit;
    private Context context;

    public SendFCMToken(String urlToHit,  Context context){

        this.urlToHit       = urlToHit;
        this.context        = context;

    }


    @Override
    protected String doInBackground(String... strings) {

        String NARRATION = "narrate";
        String urlToHit             = this.urlToHit;
        StringBuilder stringBuilder = new StringBuilder();

        URL url;
        HttpURLConnection httpURLConnection;

        try {

            url = new URL( this.urlToHit + "?fcm_id=" + urlToHit + "&userPhone=" + getUserPhoneNumber() );
            httpURLConnection = (HttpURLConnection) url.openConnection();

            BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(httpURLConnection.getInputStream()));
            String line = "";

            while (   (line = bufferedReader.readLine()) != null ){

                stringBuilder.append(line + "\n");
            }



        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e){
            e.printStackTrace();
        }


        return stringBuilder.toString();


    }



    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        String NARRATION = "narrate";

        SharedPreferences sharedPreferences = context.getSharedPreferences(NARRATION, 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        if(s.contains("success")){
            editor.putBoolean("token",true);
            editor.apply();
        }else{
            editor.putBoolean("token",false);
            editor.apply();
        }


    }

    public String getUserPhoneNumber(){

        String LOCAL_APP_DATA = "userInformation";
        SharedPreferences sharedPreferences = context.getSharedPreferences(LOCAL_APP_DATA,0);
        return  sharedPreferences.getString("userPhone","0");

    }



}
