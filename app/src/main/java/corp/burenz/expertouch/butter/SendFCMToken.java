package corp.burenz.expertouch.butter;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import corp.burenz.expertouch.R;
import corp.burenz.expertouch.util.Config;

/**
 * Created by buren on 19/11/17.
 */

public class SendFCMToken extends AsyncTask<String, String, String> {

    private Context context;

    public SendFCMToken(Context context){

        this.context        = context;

    }


    @Override
    protected String doInBackground(String... strings) {

        String NARRATION            = "narrate";
        String urlToHit             = context.getString(R.string.host) + "/registrations/update_fcm.php";
        StringBuilder stringBuilder = new StringBuilder();

        URL url;
        HttpURLConnection httpURLConnection;

        try {

            url = new URL( urlToHit + "?fcm_id=" + getUserToken() + "&userPhone=" + getUserPhoneNumber() );
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
//        Toast.makeText(context, "from server " + s, Toast.LENGTH_SHORT).show();

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
        return  sharedPreferences.getString("userEmail","0");

    }



    public String getUserToken(){
        SharedPreferences sharedPreferences = context
                .getSharedPreferences(Config.SHARED_PREF,0);
        return  sharedPreferences.getString("regId","no_token");

    }








}
