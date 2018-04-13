package corp.burenz.expertouch.util;

import android.content.Context;
import android.os.AsyncTask;
import android.text.Html;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import corp.burenz.expertouch.butter.GuestInformation;

/**
 * Created by buren on 9/4/18.
 */

public class GetSubscriberCount extends AsyncTask<String, String, String> {


    private TextView textViewReference;
    private Context mContext;

    public GetSubscriberCount(Context mContext, TextView textViewReference) {
        this.textViewReference = textViewReference;
        this.mContext          = mContext;
    }

    @Override
    protected String doInBackground(String... strings) {

        String urlToHit = "http://1clickaway.in/ver1.1/workshop/subscription_utils.php";
        StringBuilder stringBuilder = new StringBuilder();

        URL url;
        HttpURLConnection httpURLConnection;
/*?functionname=count&user_phone=9797080059*/

        try {
            url = new URL(urlToHit + "?functionname="+ URLEncoder.encode("count","UTF-8") +
            "&user_phone=" + new GuestInformation(mContext).getGuestNumber());
            Log.e("bugHere",urlToHit + "?functionname="+ URLEncoder.encode("count","UTF-8") +
                    "&user_phone=" + new GuestInformation(mContext).getGuestNumber());

            httpURLConnection = (HttpURLConnection) url.openConnection();

            String line = "";
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));

            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line).append("\n");
            }


            Log.e("bugHere","stringbuilder here" +stringBuilder.toString());

        } catch (MalformedURLException e) {
            e.printStackTrace();
            Log.e("bugHere","malformed" +e.toString());
        } catch (IOException e) {
            Log.e("bugHere","Io Exception" +e.toString());
            e.printStackTrace();
        }catch (Exception e){
            Log.e("bugHere","Majopur Exception" +e.toString());
        }

        return stringBuilder.toString();

    }


    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        Log.e("bugHere","from server "+ s);
        if (s != null) {
            textViewReference.setText(Html.fromHtml("You have <font color=\"red\">"  + s + " </font>subscribers at the moment"));
            Log.e("countFromServer",s);
        }else{
            Log.e("countFromServer","Null here");
        }

    }

}