package corp.burenz.expertouch.map_utils;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

/**
 * @author Priyanka
 */

public class GetNearbyPlacesData extends AsyncTask<Object, String, String> {

    private String googlePlacesData;
    private GoogleMap mMap;
    String url;
    private LinearLayout gettingPlacesPLL;
    private Context mContext;

    public GetNearbyPlacesData(LinearLayout gettingPlacesPLL, Context mContext){
        this.gettingPlacesPLL = gettingPlacesPLL;
        this.mContext         = mContext;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        gettingPlacesPLL.setVisibility(View.VISIBLE);
   /*     gettingPlacesPLL.startAnimation(AnimationUtils.loadAnimation( mContext, R.anim. fadein_scan));
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                gettingPlacesPLL.setVisibility(View.VISIBLE);
            }
        },500);*/
    }

    @Override
    protected String doInBackground(Object... objects){
        mMap = (GoogleMap)objects[0];
        url = (String)objects[1];

        DownloadURL downloadURL = new DownloadURL();
        try {
            googlePlacesData = downloadURL.readUrl(url);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return googlePlacesData;
    }

    @Override
    protected void onPostExecute(String s){

        List<HashMap <String, String> > nearbyPlaceList;
        DataParser parser = new DataParser();
        nearbyPlaceList = parser.parse(s);
        Log.d("nearbyplacesdata","called parse method " + s);
        showNearbyPlaces(nearbyPlaceList);



        gettingPlacesPLL.setVisibility(View.GONE);
/*        gettingPlacesPLL.startAnimation(AnimationUtils.loadAnimation( mContext, R.anim.fadeout_scan));
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                gettingPlacesPLL.setVisibility(View.GONE);
            }
        },500);*/
    }

    private void showNearbyPlaces(List<HashMap<String, String>> nearbyPlaceList)
    {
        if (nearbyPlaceList.size() == 0){
            Toast.makeText(mContext, "No Nearby Places", Toast.LENGTH_SHORT).show();
            return;
        }


        Log.d("bugbug" ,"size of nearby places " + nearbyPlaceList.size());

        for(int i = 0; i < nearbyPlaceList.size(); i++)
        {
            MarkerOptions markerOptions = new MarkerOptions();
            HashMap<String, String> googlePlace = nearbyPlaceList.get(i);

            String placeName = googlePlace.get("place_name");
            String vicinity = googlePlace.get("vicinity");



//            String reference = googlePlace.get("reference");
            double lat = 13;
            double lng = 77;
            try{
                lat = Double.parseDouble( googlePlace.get("lat"));
                lng = Double.parseDouble( googlePlace.get("lng"));

            }catch (Exception e){Log.d("bugbug" ,"position " + i + e.toString());}


            LatLng latLng = new LatLng( lat, lng);
            markerOptions.position(latLng);
            markerOptions.title(placeName);
            markerOptions.snippet(vicinity);
            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));

            if ( googlePlace.get("rating") != null ){
                String ratings =  googlePlace.get("rating");
                
                mMap.addMarker(markerOptions).setTag(ratings);
            }else {
//                Toast.makeText(mContext, "here it was", Toast.LENGTH_SHORT).show();
                mMap.addMarker(markerOptions).setTag("5");
            }


            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
            mMap.animateCamera(CameraUpdateFactory.zoomTo(10));

        }
    }
}
