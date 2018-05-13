package corp.burenz.expertouch.map_utils;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by buren on 26/4/18.
 */

public class CheckMySubscription extends AsyncTask<String, String, String> {

    private String  storeId;
    private Context mContext;

    private     ImageView subscribeToNotificationsIV, unsubscribeToNotificationsIV;
    private     Button unSubscribeToPostsBTN, subscribeToPostsBTN;
    private     ProgressBar progressBar;

    private ImageView isVerifiedLL;

    public CheckMySubscription(ImageView isVerifiedLL, ProgressBar progressBar,String storeId, Context mContext, ImageView subscribeToNotificationsIV, ImageView unsubscribeToNotificationsIV, Button unSubscribeToPostsBTN, Button subscribeToPostsBTN) {
        this.storeId                        = storeId;
        this.mContext                       = mContext;
        this.subscribeToNotificationsIV     = subscribeToNotificationsIV;
        this.unsubscribeToNotificationsIV   = unsubscribeToNotificationsIV;
        this.unSubscribeToPostsBTN          = unSubscribeToPostsBTN;
        this.subscribeToPostsBTN            = subscribeToPostsBTN;
        this.progressBar                    = progressBar;
        this.isVerifiedLL                   = isVerifiedLL;
    }

    @Override
    protected String doInBackground(String... strings) {

        String myPhone = "9797080059";

        String urlToHit = "http://1clickaway.in/ver1.1/workshop/subscription_utils.php?functionname=assemble&user_phone=" +myPhone+ "&channel_id=" + storeId;
        StringBuilder   stringBuilder   = new StringBuilder();

        HttpURLConnection httpURLConnection;
        URL url;

        try {
            url = new URL(urlToHit);
            Log.d("checks",urlToHit);
            httpURLConnection = (HttpURLConnection) url.openConnection();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));

            String line = "";
            while ( (line = bufferedReader.readLine()) != null){
                stringBuilder.append(line).append("\n");
            }


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return stringBuilder.toString();
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressBar.setVisibility(View.VISIBLE);
        subscribeToNotificationsIV.setVisibility(View.GONE);
        unsubscribeToNotificationsIV.setVisibility(View.GONE);
        subscribeToPostsBTN.setVisibility(View.GONE);
        unSubscribeToPostsBTN.setVisibility(View.GONE);
    }


    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        progressBar.setVisibility(View.GONE);

        Log.e("channel",s);
        try {
            JSONObject jsonObject = new JSONObject(s);

            if (jsonObject.getString("subscribed").contains("false")){

                subscribeToNotificationsIV.setVisibility(View.GONE);
                unsubscribeToNotificationsIV.setVisibility(View.GONE);
                unSubscribeToPostsBTN.setVisibility(View.GONE);
                subscribeToPostsBTN.setVisibility(View.VISIBLE);

            }else if (jsonObject.getString("subscribed").contains("true")){

                if (jsonObject.getString("notify").contains("1")){
                    subscribeToNotificationsIV.setVisibility(View.GONE);
                    unsubscribeToNotificationsIV.setVisibility(View.VISIBLE);
                }else {
                    subscribeToNotificationsIV.setVisibility(View.VISIBLE);
                    unsubscribeToNotificationsIV.setVisibility(View.GONE);

                }

                unSubscribeToPostsBTN.setVisibility(View.VISIBLE);
                subscribeToPostsBTN.setVisibility(View.GONE);

            }
        /*see if he is verified*/
            if (jsonObject.getString("inline").contains("1")){
                isVerifiedLL.setVisibility(View.VISIBLE);
            }else{
                isVerifiedLL.setVisibility(View.GONE);
            }

        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("bugHere", "Exception is here " + e.toString());
        }



    }
}
