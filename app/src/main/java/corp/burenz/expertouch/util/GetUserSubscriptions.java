package corp.burenz.expertouch.util;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessaging;

import org.apache.http.protocol.HTTP;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

import corp.burenz.expertouch.R;
import corp.burenz.expertouch.butter.GuestInformation;

/**
 * Created by buren on 1/2/18.
 */

public class GetUserSubscriptions  extends AsyncTask<String, String, String>{

    private     String subOrUnsub;
    private     ArrayList<String> subscriptionIDsArray;
    private     Context mContext;


    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        subscriptionIDsArray = new ArrayList<>();


    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

    }

    @Override
    protected String doInBackground(String... strings) {

        String          urlToHit        = mContext.getString(R.string.host) + "/workshop/get_my_subscriptions.php";
        StringBuilder   stringBuilder   = new StringBuilder();

        HttpURLConnection httpURLConnection;
        URL url;

        /* http://1clickaway.in/ver1.1/workshop/get_my_subscriptions.php?phone_number=9797080059 */
        try {

            url = new URL(urlToHit + "?phone_number=" + new GuestInformation(mContext).getGuestNumber());

            Log.e("fromURL",urlToHit + "?phone_number=" + new GuestInformation(mContext).getGuestNumber());
            httpURLConnection = (HttpURLConnection) url.openConnection();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));

            String line = "";
            while ( (line = bufferedReader.readLine()) != null ){
                stringBuilder.append(line).append("\n");
            }

            JSONArray jsonArray  = new JSONArray(stringBuilder.toString());


            for (int i = 0; i < jsonArray.length(); i++){
                subscriptionIDsArray.add(jsonArray.getJSONObject(i).getString("id"));

            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return stringBuilder.toString();

    }


}
