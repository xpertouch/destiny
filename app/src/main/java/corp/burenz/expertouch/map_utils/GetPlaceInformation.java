package corp.burenz.expertouch.map_utils;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by buren on 22/4/18.
 */

public class GetPlaceInformation extends AsyncTask<String, String, String> {

    private Context mContext;
    private String placeId;
    GoogleMap mMap;

    double currentLatitude,currentLongitude;

    private ImageView   subscribeToNotificationsIV,unsubscribeToNotificationsIV;
    private Button      subscribeToPostsBTN, unSubscribeToPostsBTN;
    private TextView    placeTitleMapsTV, placeStarsTextTV;
    private RatingBar   placeStarsRB;
    private ProgressBar progressBarSubs;
    private LinearLayout explainPLaceLL;
    ImageView isVerifiedLV;
    TextView takeMeToMaps;



    public GetPlaceInformation(TextView takeMeToMaps, Context mContext, String placeId, GoogleMap mMap, ImageView subscribeToNotificationsIV, ImageView unsubscribeToNotificationsIV, Button subscribeToPostsBTN, Button unSubscribeToPostsBTN, TextView placeTitleMapsTV, TextView placeStarsTextTV, RatingBar placeStarsRB, ProgressBar progressBarSubs, LinearLayout explainPLaceLL, ImageView isVerifiedLV) {
        this.mContext = mContext;
        this.placeId = placeId;
        this.mMap = mMap;
        this.subscribeToNotificationsIV = subscribeToNotificationsIV;
        this.unsubscribeToNotificationsIV = unsubscribeToNotificationsIV;
        this.subscribeToPostsBTN = subscribeToPostsBTN;
        this.unSubscribeToPostsBTN = unSubscribeToPostsBTN;
        this.placeTitleMapsTV = placeTitleMapsTV;
        this.placeStarsTextTV = placeStarsTextTV;
        this.placeStarsRB = placeStarsRB;
        this.progressBarSubs = progressBarSubs;
        this.explainPLaceLL = explainPLaceLL;
        this.isVerifiedLV = isVerifiedLV;
        this.takeMeToMaps = takeMeToMaps;
    }


    //    public GetPlaceInformation(Context mContext, String placeId, GoogleMap mMap) {
//        this.mContext = mContext;
//        this.placeId = placeId;
//        this.mMap = mMap;

//    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();



    }

    @Override
    protected String doInBackground(String... strings) {

        String          urlToHit        = "https://maps.googleapis.com/maps/api/place/details/json?place_id=" + placeId
                + "&sensor=true&key=AIzaSyC-pygGcIpSzAGs6BqPrUa20jyPyjWPud0";

        Log.e("urlTohit", urlToHit);
        StringBuilder   stringBuilder   = new StringBuilder();

        HttpURLConnection httpURLConnection;
        URL url;

        try {
            url = new URL(urlToHit);
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
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        Log.e("----- response -----",s);
        if (s == null) return;


        try {
            JSONObject jsonObject = new JSONObject(s);

            currentLongitude = Double.valueOf(jsonObject.getJSONObject("result").getJSONObject("geometry").getJSONObject("location").getString("lng"));
            currentLatitude = Double.valueOf(jsonObject.getJSONObject("result").getJSONObject("geometry").getJSONObject("location").getString("lat"));

            Log.e("----- response ---->",jsonObject.getJSONObject("result").getJSONObject("geometry").getJSONObject("location").getString("lat"));
            Log.e("----- response ---->"," " +currentLatitude);


            subscribeToPostsBTN.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    try{new MapsSubscriptionUtils(subscribeToNotificationsIV, unsubscribeToNotificationsIV, unSubscribeToPostsBTN, subscribeToPostsBTN, "sub", returnStoreKey(currentLatitude, currentLongitude)).execute();                    }catch (Exception e){
                        e.printStackTrace();
                    }


                }
            });

            unSubscribeToPostsBTN.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try{new MapsSubscriptionUtils(subscribeToNotificationsIV, unsubscribeToNotificationsIV, unSubscribeToPostsBTN, subscribeToPostsBTN, "unsub", returnStoreKey(currentLatitude, currentLongitude)).execute();                    }catch (Exception e){
                        e.printStackTrace();
                    }


                }
            });

            subscribeToNotificationsIV.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    try{
                        new EnableNotifications(subscribeToNotificationsIV, unsubscribeToNotificationsIV, "sub_n",returnStoreKey(currentLatitude, currentLongitude)).execute();
                    /*simply call firebase class*/

                    }catch (Exception e){
                        e.printStackTrace();
                    }

                }
            });

            unsubscribeToNotificationsIV.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                /*simply call firebase unsub class*/
                    try{
                        new EnableNotifications(subscribeToNotificationsIV, unsubscribeToNotificationsIV, "unsub_n",returnStoreKey(currentLatitude, currentLongitude)).execute();
                    /*simply call firebase class*/

                    }catch (Exception e){
                        e.printStackTrace();
                    }

                }
            });

            //            Toast.makeText(mContext, "" + jsonObject.getJSONObject("result").getString("formatted_phone_number"), Toast.LENGTH_SHORT).show();
//            Toast.makeText(mContext, "" + jsonObject.getJSONObject("result").getJSONObject("geometry").getJSONObject("location").getString("lat"), Toast.LENGTH_SHORT).show();
            Log.d("latlong","Here is lat : " + jsonObject.getJSONObject("result").getJSONObject("geometry").getJSONObject("location").getString("lat"));
            Log.d("latlong","Here is rating: " + jsonObject.getJSONObject("result").getString("rating"));


            try {
                setCustomLatLong(new LatLng(Float.valueOf(jsonObject.getJSONObject("result").getJSONObject("geometry").getJSONObject("location").getString("lat")) , Float.valueOf(jsonObject.getJSONObject("result").getJSONObject("geometry").getJSONObject("location").getString("lng"))),jsonObject.getJSONObject("result").getString("name"),jsonObject.getJSONObject("result").getString("rating"), jsonObject.getJSONObject("result").getString("formatted_address"));

            }catch (Exception e){
                Toast.makeText(mContext, "inside get places", Toast.LENGTH_SHORT).show();
            }


        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(mContext, "Exception in json " + e.toString(), Toast.LENGTH_SHORT).show();
        }


        takeMeToMaps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.e("response","LAT ->" +currentLatitude + "LNG ->" + currentLongitude);
                new GetPlaceIdFromKey(new LatLng(currentLatitude,currentLongitude),mContext).execute();
                Toast.makeText(mContext, "inside onPreTakeMeToMaps", Toast.LENGTH_SHORT).show();
            }
        });



    }


    void setCustomLatLong(final LatLng latLng , String name, String rating, String formattedAddress){

        Marker currentLocationmMarker;
        final MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title(name);
        placeTitleMapsTV.setText(name);

        placeStarsTextTV.setText(rating);
        placeStarsRB.setRating(Float.valueOf(rating));
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE));
        mMap.setBuildingsEnabled(true);

        currentLocationmMarker = mMap.addMarker(markerOptions);
        currentLocationmMarker.setSnippet(formattedAddress);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
//                mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
//                mMap.animateCamera(CameraUpdateFactory.zoomBy(12  ));
                // Construct a CameraPosition focusing on Mountain View and animate the camera to that position.
                CameraPosition cameraPosition = new CameraPosition.Builder()
                        .target(latLng)      // Sets the center of the map to Mountain View
                        .zoom(17)                   // Sets the zoom
                        .bearing(90)                // Sets the orientation of the camera to east
                        .tilt(30)                   // Sets the tilt of the camera to 30 degrees
                        .build();                   // Creates a CameraPosition from the builder
                mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
            }
        },200);


        new CheckMySubscription(isVerifiedLV,progressBarSubs,returnStoreKey(currentLatitude,currentLongitude),mContext, subscribeToNotificationsIV, unsubscribeToNotificationsIV, unSubscribeToPostsBTN, subscribeToPostsBTN).execute();
        explainPLaceLL.setVisibility(View.VISIBLE);


    }

    String returnStoreKey( double lat, double longitude ){
        return Double.toString(lat).replace(".","d") + "xf" + Double.toString(longitude).replace(".","d");
    }



}
