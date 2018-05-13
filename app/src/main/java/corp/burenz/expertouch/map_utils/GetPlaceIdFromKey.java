package corp.burenz.expertouch.map_utils;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by buren on 2/5/18.
 */

public class GetPlaceIdFromKey extends AsyncTask<String, String, String>{

    private LatLng latLng;
    private Context mContext;


    public GetPlaceIdFromKey(LatLng latLng, Context mContext) {
        this.latLng= latLng;
        this.mContext = mContext;
    }

    @Override
    protected String doInBackground(String... strings) {

        String urlToHit = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=" + latLng.latitude + "," + latLng.longitude + "&radius=1&sensor=true&types=all&key=AIzaSyBuVzsoP0X5Tp_7t9HwgYtnNotioUGPb3Q";
        StringBuilder stringBuilder = new StringBuilder();
        Log.e("hyhyh",urlToHit);

        HttpURLConnection httpURLConnection;
        URL url;

        try {
            url = new URL(urlToHit);
            httpURLConnection = (HttpURLConnection) url.openConnection();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));

            String line = "";

            while ((line = bufferedReader.readLine()) != null){
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
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        Log.e("response",s);

        try {
            JSONObject jsonObject = new JSONObject(s);
            JSONArray jsonArray = jsonObject.getJSONArray("results");
            JSONObject checkInside;

            for (int i = 0; i < jsonArray.length(); i++) {

                checkInside = jsonArray.getJSONObject(i).getJSONObject("geometry").getJSONObject("location");

                if (checkInside.getString("lat").equals(String.valueOf(latLng.latitude)) && checkInside.getString("lng").equals(String.valueOf(latLng.longitude))){
                    Toast.makeText(mContext, "Found Match", Toast.LENGTH_SHORT).show();
                    Toast.makeText(mContext, jsonArray.getJSONObject(i).getString("name") + " " + jsonArray.getJSONObject(i).getString("place_id"), Toast.LENGTH_SHORT).show();
                    Log.e("place_id",jsonArray.getJSONObject(i).getString("place_id"));

                    break;
                }

            }

        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(mContext, "Not Getting appropriate json " + e.toString(), Toast.LENGTH_SHORT).show();
        }


    }
}
