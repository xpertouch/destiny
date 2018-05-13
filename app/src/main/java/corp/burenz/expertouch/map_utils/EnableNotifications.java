package corp.burenz.expertouch.map_utils;

import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by buren on 27/4/18.
 */

public class EnableNotifications extends AsyncTask<String , String, String> {

    static String SUBSCRIBE = "sub_n";
    static String UN_SUBSCRIBE = "unsub_n";

    private ImageView subscribeToNotificationsIV, unsubscribeToNotificationsIV;

    private String from;
    private String storeId;


    public EnableNotifications(ImageView subscribeToNotificationsIV, ImageView unsubscribeToNotificationsIV, String from, String storeId) {
        this.subscribeToNotificationsIV = subscribeToNotificationsIV;
        this.unsubscribeToNotificationsIV = unsubscribeToNotificationsIV;
        this.from = from;
        this.storeId = storeId;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        subscribeToNotificationsIV.setEnabled(false);
        unsubscribeToNotificationsIV.setEnabled((false));

        if (from.equals(UN_SUBSCRIBE)){


            subscribeToNotificationsIV.setVisibility(View.VISIBLE);
            unsubscribeToNotificationsIV.setVisibility(View.GONE);

        }else {

            subscribeToNotificationsIV.setVisibility(View.GONE);
            unsubscribeToNotificationsIV.setVisibility(View.VISIBLE);

        }


    }

    @Override
    protected String doInBackground(String... strings) {
        String          urlToHit            =   "http://1clickaway.in/ver1.1/workshop/subscription_utils.php";

        StringBuilder   stringBuilder   = new StringBuilder();

        HttpURLConnection httpURLConnection;
        URL url;

        try {


            switch (from){

                case "sub":
                    url = new URL(urlToHit + "?functionname=" + URLEncoder.encode(from, "UTF-8") + "&user_phone="+ URLEncoder.encode("9797080059", "UTF-8") +"&channel_id=" + URLEncoder.encode(storeId,"UTF-8") );
                    Log.e("channel",urlToHit + "?functionname=" + URLEncoder.encode(from, "UTF-8") + "&user_phone="+ URLEncoder.encode("9797080059", "UTF-8") +"&channel_id=" + URLEncoder.encode(storeId,"UTF-8")  );
                    break;

                case "unsub":
                    url = new URL(urlToHit + "?functionname=" + URLEncoder.encode(from, "UTF-8") + "&user_phone="+ URLEncoder.encode("9797080059", "UTF-8") +"&channel_id=" + URLEncoder.encode(storeId,"UTF-8") );
                    Log.e("channel",urlToHit + "?functionname=" + URLEncoder.encode(from, "UTF-8") + "&user_phone="+ URLEncoder.encode("9797080059", "UTF-8") +"&channel_id=" + URLEncoder.encode(storeId,"UTF-8") );
                    break;

                default:
                    url = new URL(urlToHit + "?functionname=" + URLEncoder.encode(from, "UTF-8") + "&user_phone="+ URLEncoder.encode("9797080059", "UTF-8") +"&channel_id=" + URLEncoder.encode(storeId,"UTF-8") );

            }

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
        }catch (Exception e ){
            Log.e("bugHere","" + e.toString());
        }

        return stringBuilder.toString();
    }


    @Override
    protected void onPostExecute(String fromServer) {
        super.onPostExecute(fromServer);


        subscribeToNotificationsIV.setEnabled(true);
        unsubscribeToNotificationsIV.setEnabled((true));

//        fromServer = "success";

        if (!fromServer.contains("success")){

            if (from.equals(UN_SUBSCRIBE)){


                subscribeToNotificationsIV.setVisibility(View.GONE);
                unsubscribeToNotificationsIV.setVisibility(View.VISIBLE);


            }else {

                subscribeToNotificationsIV.setVisibility(View.VISIBLE);
                unsubscribeToNotificationsIV.setVisibility(View.GONE);


            }


        }



    }



}

