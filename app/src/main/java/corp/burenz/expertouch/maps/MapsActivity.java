package corp.burenz.expertouch.maps;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.BounceInterpolator;
import android.view.animation.Interpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;



import java.util.List;
import java.util.Locale;

import corp.burenz.expertouch.R;
import corp.burenz.expertouch.map_utils.CheckMySubscription;
import corp.burenz.expertouch.map_utils.EnableNotifications;
import corp.burenz.expertouch.map_utils.GetNearbyPlacesData;
import corp.burenz.expertouch.map_utils.GetPlaceIdFromKey;
import corp.burenz.expertouch.map_utils.GetPlaceInformation;
import corp.burenz.expertouch.map_utils.MapsSubscriptionUtils;
import corp.burenz.expertouch.map_utils.MyPannelModel;
import corp.burenz.expertouch.map_utils.SearchPlaces;
import eminayar.com.cardhelper.CardClickListener;
import eminayar.com.cardhelper.HelperCardsLayout;
import eminayar.com.cardhelper.models.CardItem;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {


    private GoogleMap mMap;
    private GoogleApiClient client;
    private LocationRequest locationRequest;
    private Location lastlocation;
    private Marker currentLocationmMarker;
    public static final int REQUEST_LOCATION_CODE = 99;
    int PROXIMITY_RADIUS = 10000;
    LinearLayout gettingPlacesPLL;
    double latitude,longitude;
    LinearLayout explainPLaceLL;

    ImageView subscribeToNotificationsIV,unsubscribeToNotificationsIV;
    Button      subscribeToPostsBTN, unSubscribeToPostsBTN;
    TextView placeTitleMapsTV, placeStarsTextTV;
    RatingBar placeStarsRB;
    ProgressBar progressBarSubs;
    ArrayList<CardItem> cardItems;
    HelperCardsLayout layout;

    ImageView isVerifiedLV;
    TextView takeMeToMaps;

    double currentLatitude,currentLongitude;


    private void getMyLocation() {
        LatLng latLng = new LatLng(latitude, longitude);

        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title("You are here");
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
        mMap.addMarker(markerOptions);


        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 18);
        mMap.animateCamera(cameraUpdate);

    }


    @Override
    protected void onResume() {
        super.onResume();

        if ( !getSharedPreferences("maps",0).getString("place_id","none") .equals("none") ){

            try {

                new GetPlaceInformation(takeMeToMaps,MapsActivity.this, getSharedPreferences("maps",0).getString("place_id","none"),mMap, subscribeToNotificationsIV,unsubscribeToNotificationsIV, subscribeToPostsBTN, unSubscribeToPostsBTN, placeTitleMapsTV, placeStarsTextTV, placeStarsRB, progressBarSubs, explainPLaceLL, isVerifiedLV).execute();

            }catch (Exception e){
//                Toast.makeText(this, "ss", Toast.LENGTH_SHORT).show();
            }

        }


    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

            checkLocationPermission();}
        setContentView(R.layout.activity_maps);

        getSharedPreferences("maps",0).edit().putString("place_id","none").commit();

        inflateBottomSheet();

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        findViewById(R.id.getMyLocation).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getMyLocation();
            }
        });

        findViewById(R.id.getThisPlaceIB).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MapsActivity.this, SearchPlaces.class));
                overridePendingTransition(R.anim.fadein_scan,R.anim.fadeout_scan);
            }
        });




        /* PlaceAutocompleteFragment places= (PlaceAutocompleteFragment)
                getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);

        AutocompleteFilter typeFilter = new AutocompleteFilter.Builder()
                .setCountry("IN")
                .setTypeFilter(AutocompleteFilter.TYPE_FILTER_ADDRESS)
                .build();

        places.setFilter(typeFilter);


        places.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {

                Toast.makeText(getApplicationContext(),place.getName(),Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(Status status) {

                Toast.makeText(getApplicationContext(),status.toString(),Toast.LENGTH_SHORT).show();

            }
        });
*/
/*
        Button btnShow = (Button) findViewById(R.id.btn_show_dialog);

        btnShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BottomSheetDialog bottomSheetDialog = new BottomSheetDialog();
                bottomSheetDialog.show(getSupportFragmentManager(), bottomSheetDialog.getTag());
            }
        });
*/

        addCardsToUberLayout(layout);

    }

    void getTheNearest(String place){
        Object dataTransfer[] = new Object[2];
        GetNearbyPlacesData getNearbyPlacesData = new GetNearbyPlacesData(gettingPlacesPLL, MapsActivity.this);
        mMap.clear();
        String url = getUrl(latitude, longitude, place);
        dataTransfer[0] = mMap;
        dataTransfer[1] = url;
        getNearbyPlacesData.execute(dataTransfer);

        Toast.makeText(MapsActivity.this, "Showing Nearby " + place, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch(requestCode)
        {
            case REQUEST_LOCATION_CODE:
                if(grantResults.length >0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    if(ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION) !=  PackageManager.PERMISSION_GRANTED)
                    {
                        if(client == null)
                        {
                            bulidGoogleApiClient();
                        }
                        mMap.setMyLocationEnabled(true);
                    }
                }
                else
                {
                    Toast.makeText(this,"Permission Denied" , Toast.LENGTH_LONG).show();
                }
        }
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            bulidGoogleApiClient();
            mMap.setMyLocationEnabled(false);
            mMap.getUiSettings().setMyLocationButtonEnabled(false);
        }
//        setCustomLatLong();
    }


    protected synchronized void bulidGoogleApiClient() {
        client = new GoogleApiClient.Builder(this).addConnectionCallbacks(this).addOnConnectionFailedListener(this).addApi(LocationServices.API).build();
        client.connect();

    }

    @Override
    public void onLocationChanged(Location location) {

        latitude = location.getLatitude();
        longitude = location.getLongitude();
        lastlocation = location;
        if(currentLocationmMarker != null)
        {
            currentLocationmMarker.remove();

        }
        Log.d("lat = ",""+latitude);
        LatLng latLng = new LatLng(location.getLatitude() , location.getLongitude());
        final MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title("You are here");
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));

        mMap.setBuildingsEnabled(true);

        currentLocationmMarker = mMap.addMarker(markerOptions);
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
//        mMap.animateCamera(CameraUpdateFactory.zoomBy(15  ));

//        9513313181

        final CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(latLng)      // Sets the center of the map to Mountain View
                .zoom(17)                   // Sets the zoom
                .bearing(90)                // Sets the orientation of the camera to east
                .tilt(30)                   // Sets the tilt of the camera to 30 degrees
                .build();                   // Creates a CameraPosition from the builder
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

            }
        },500);


        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(final Marker marker) {

                try{
//                    Toast.makeText(MapsActivity.this,  marker.getTag() + "  " + marker.getTitle(), Toast.LENGTH_SHORT).show();
                    try {

                        setBottomSheet(new MyPannelModel(marker.getTitle(),marker.getTag().toString(),marker.getTitle(),Double.toString(marker.getPosition().latitude)));
                        new CheckMySubscription(isVerifiedLV,progressBarSubs,returnStoreKey(marker.getPosition().latitude,marker.getPosition().longitude),MapsActivity.this, subscribeToNotificationsIV, unsubscribeToNotificationsIV, unSubscribeToPostsBTN, subscribeToPostsBTN).execute();
//                        new GetPlaceInformation(MapsActivity.this,marker.getTag().toString()).execute();

                        currentLatitude     = marker.getPosition().latitude;
                        currentLongitude    = marker.getPosition().longitude;


                        takeMeToMaps.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                new GetPlaceIdFromKey(new LatLng(marker.getPosition().latitude,marker.getPosition().longitude),MapsActivity.this).execute();
                                Log.e("response","LAT " + marker.getPosition().latitude + " LNG " + marker.getPosition().longitude);

                                    /*
                                String label = marker.getTitle();
                                String uriBegin = String.format(Locale.ENGLISH, "geo:%f,%f", currentLatitude,currentLongitude);
                                String query = String.format(Locale.ENGLISH, "" + label + " " + marker.getSnippet());
                                String encodedQuery = Uri.encode( query  );
                                String uriString = uriBegin + "?q=" + encodedQuery;
                                Uri uri = Uri.parse( uriString );
                                Intent intent = new Intent(android.content.Intent.ACTION_VIEW, uri );
                                intent.setPackage("com.google.android.apps.maps");
                                startActivity( intent );
*/

                              /*  String labelS = "Cinnamon & Toast";
                                String urii = String.format(Locale.ENGLISH, "geo:%f,%f", marker.getPosition().latitude, marker.getPosition().longitude);
                                Log.e("address",urii);
                                Intent intentt = new Intent(Intent.ACTION_VIEW, Uri.parse(urii));
                                intentt.setPackage("com.google.android.apps.maps");
                                startActivity(intentt);*/

                            }
                        });

                        layout.setVisibility(View.GONE);
                        explainPLaceLL.setVisibility(View.VISIBLE);



                    }catch (Exception e){
                        Toast.makeText(MapsActivity.this, "Exception " + e.toString(), Toast.LENGTH_SHORT).show();
                    }

                    /*https://maps.googleapis.com/maps/api/place/details/json?place_id=ChIJ2YT8sRsVrjsR2R1sQJUJ3Sc&sensor=true&key=AIzaSyBLEPBRfw7sMb73Mr88L91Jqh3tuE4mKsE*/
                }catch (Exception e){

                }
                return false;


            }
        });

        if(client != null)
        {
            LocationServices.FusedLocationApi.removeLocationUpdates(client,this);
        }
    }

    public void onClick(View v)
    {
        Object dataTransfer[] = new Object[2];
        GetNearbyPlacesData getNearbyPlacesData = new GetNearbyPlacesData(gettingPlacesPLL, MapsActivity.this);

        switch(v.getId())
        {
            case R.id.B_search:
                EditText tf_location =  findViewById(R.id.TF_location);
                String location = tf_location.getText().toString();
                List<Address> addressList;


                if(!location.equals(""))
                {
                    Geocoder geocoder = new Geocoder(this);

                    try {
                        addressList = geocoder.getFromLocationName(location, 5);

                        if(addressList != null)
                        {
                            for(int i = 0;i<addressList.size();i++) {
                                LatLng latLng = new LatLng(addressList.get(i).getLatitude() , addressList.get(i).getLongitude());
                                MarkerOptions markerOptions = new MarkerOptions();
                                markerOptions.position(latLng);
//                                markerOptions.title(location);
//                                currentLocationmMarker.setTag("Tag for" + location);
                                mMap.addMarker(markerOptions);
                                mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                                mMap.animateCamera(CameraUpdateFactory.zoomTo(10));
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case R.id.B_hopistals:
                mMap.clear();
                String hospital = "hospital";
                String url = getUrl(latitude, longitude, hospital);
                dataTransfer[0] = mMap;
                dataTransfer[1] = url;

                getNearbyPlacesData.execute(dataTransfer);
                Toast.makeText(MapsActivity.this, "Showing Nearby Hospitals", Toast.LENGTH_SHORT).show();
                break;


            case R.id.B_schools:
                mMap.clear();
                String school = "school";
                url = getUrl(latitude, longitude, school);
                dataTransfer[0] = mMap;
                dataTransfer[1] = url;

                getNearbyPlacesData.execute(dataTransfer);
                Toast.makeText(MapsActivity.this, "Showing Nearby Schools", Toast.LENGTH_SHORT).show();
                break;


            case R.id.B_restaurants:

                mMap.clear();
                String resturant = "restuarant";
                url = getUrl(latitude, longitude, resturant);
                dataTransfer[0] = mMap;
                dataTransfer[1] = url;

                getNearbyPlacesData.execute(dataTransfer);
                Toast.makeText(MapsActivity.this, "Showing Nearby Restaurants", Toast.LENGTH_SHORT).show();
                break;
            case R.id.B_to:
        }
    }


    private String getUrl(double latitude , double longitude , String nearbyPlace) {

        StringBuilder googlePlaceUrl = new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");
        googlePlaceUrl.append("location="+latitude+","+longitude);
        googlePlaceUrl.append("&radius="+PROXIMITY_RADIUS);
        googlePlaceUrl.append("&types="+nearbyPlace);
        googlePlaceUrl.append("&sensor=true");
        googlePlaceUrl.append("&key="+"AIzaSyBuVzsoP0X5Tp_7t9HwgYtnNotioUGPb3Q");

        Log.d("MapsActivity", "url = "+googlePlaceUrl.toString());

        return googlePlaceUrl.toString();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

        locationRequest = new LocationRequest();
        locationRequest.setInterval(100);
        locationRequest.setFastestInterval(1000);
        locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);


        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION ) == PackageManager.PERMISSION_GRANTED)
        {
            LocationServices.FusedLocationApi.requestLocationUpdates(client, locationRequest, this);
        }
    }


    public boolean checkLocationPermission() {
        if(ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION)  != PackageManager.PERMISSION_GRANTED )
        {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.ACCESS_FINE_LOCATION))
            {
                ActivityCompat.requestPermissions(this,new String[] {Manifest.permission.ACCESS_FINE_LOCATION },REQUEST_LOCATION_CODE);
            }
            else
            {
                ActivityCompat.requestPermissions(this,new String[] {Manifest.permission.ACCESS_FINE_LOCATION },REQUEST_LOCATION_CODE);
            }
            return false;

        }
        else
            return true;
    }


    @Override
    public void onConnectionSuspended(int i) {
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
    }

    /*used to set title and stars on bottom*/
    void setBottomSheet(MyPannelModel myPannelModel ){

        placeTitleMapsTV.setText(myPannelModel.getPlaceTitle());
        placeStarsTextTV.setText(myPannelModel.getPlaceStars());
        placeStarsRB.setStepSize(0.5f);
        placeStarsRB.setRating(Float.parseFloat(myPannelModel.getPlaceStars()));


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
    }

    /*add cards to uber layout*/
    void addCardsToUberLayout(final HelperCardsLayout layout ){
        cardItems = new ArrayList<>();
        cardItems.add(new CardItem("Swipe up to choose category",
                "Description this can be some long text, " + "layout" + "will scale itself"));
        cardItems.add(new CardItem("Cafes",
                "Tap the marker and subscribe to get push notifications on offers",R.drawable.ic_restaurant_menu_black_24dp));
        cardItems.add(new CardItem("Restaurants",
                "Tap the marker and subscribe to get push notifications on offers",R.drawable.ic_restaurant_menu_black_24dp));
        cardItems.add(new CardItem("Shopping",
                "Tap the marker and subscribe to get push notifications on offers",R.drawable.ic_restaurant_menu_black_24dp));
        cardItems.add(new CardItem("Hotels",
                "Tap the marker and subscribe to get push notifications on offers",R.drawable.ic_restaurant_menu_black_24dp));
        cardItems.add(new CardItem("Travel",
                "Tap the marker and subscribe to get push notifications on offers",R.drawable.ic_restaurant_menu_black_24dp));
        cardItems.add(new CardItem("Stores",
                "Tap the marker and subscribe to get push notifications on offers",R.drawable.ic_restaurant_menu_black_24dp));
        cardItems.add(new CardItem("Bakeries",
                "Tap the marker and subscribe to get push notifications on offers",R.drawable.ic_restaurant_menu_black_24dp));

        cardItems.add(new CardItem("Universities / Colleges",
                "Tap the marker and subscribe to get push notifications on offers",R.drawable.ic_restaurant_menu_black_24dp));
        cardItems.add(new CardItem("Schools",
                "Tap the marker and subscribe to get push notifications on offers",R.drawable.ic_restaurant_menu_black_24dp));
        cardItems.add(new CardItem("Libraries",
                "Tap the marker and subscribe to get push notifications on offers",R.drawable.ic_restaurant_menu_black_24dp));
        cardItems.add(new CardItem("Fire Stations",
                "Tap the marker and subscribe to get push notifications on offers",R.drawable.ic_restaurant_menu_black_24dp));
        cardItems.add(new CardItem("Police Stations",
                "Tap the marker and subscribe to get push notifications on offers",R.drawable.ic_restaurant_menu_black_24dp));
        cardItems.add(new CardItem("Hospitals",
                "Tap the marker and subscribe to get push notifications on offers",R.drawable.ic_restaurant_menu_black_24dp));
        cardItems.add(new CardItem("Doctors / Clinics",
                "Tap the marker and subscribe to get push notifications on offers",R.drawable.ic_restaurant_menu_black_24dp));
        cardItems.add(new CardItem("Medical Stores",
                "Tap the marker and subscribe to get push notifications on offers",R.drawable.ic_restaurant_menu_black_24dp));
        cardItems.add(new CardItem("Banks",
                "Tap the marker and subscribe to get push notifications on offers",R.drawable.ic_restaurant_menu_black_24dp));
        cardItems.add(new CardItem("Movie Theaters",
                "Tap the marker and subscribe to get push notifications on offers",R.drawable.ic_restaurant_menu_black_24dp));
        cardItems.add(new CardItem("Filling Stations",
                "Tap the marker and subscribe to get push notifications on offers",R.drawable.ic_restaurant_menu_black_24dp));
        cardItems.add(new CardItem("Airports",
                "Tap the marker and subscribe to get push notifications on offers",R.drawable.ic_restaurant_menu_black_24dp));
        cardItems.add(new CardItem("Post Office",
                "Tap the marker and subscribe to get push notifications on offers",R.drawable.ic_restaurant_menu_black_24dp));
        cardItems.add(new CardItem("Car Repairs",
                "Tap the marker and subscribe to get push notifications on offers",R.drawable.ic_restaurant_menu_black_24dp));
        cardItems.add(new CardItem("Everything else",
                "Tap the marker and subscribe to get push notifications on offers",R.drawable.ic_restaurant_menu_black_24dp));

        layout.setItems(cardItems);


        layout.setOnCardClickListener(new CardClickListener() {
            @Override
            public void onCardClicked(int pos, CardItem cardItem) {

                String places[]  = {"","cafe", "restaurant||food", "shopping_mall", "room", "travel_agency", "clothing_store", "bakery", "college","school","library", "fire_station", "police","hospital",
                        "doctor", "pharmacy", "movie_theater","gas_station",
                        "airport", "post_office","car_repair","gym" };
                switch (pos){
                    case 0:
                        break;

                    case 1:
                        getTheNearest(places[pos]);
                        layout.canScrollToTop();
                        break;

                    case 2:
                        getTheNearest(places[pos]);
                        layout.canScrollToTop();
                        break;

                    case 3:
                        getTheNearest(places[pos]);
                        layout.canScrollToTop();
                        break;

                    case 4:
                        getTheNearest(places[pos]);
                        layout.canScrollToTop();
                        break;

                    case 5:
                        getTheNearest(places[pos]);
                        layout.canScrollToTop();
                        break;

                    case 6:
                        getTheNearest(places[pos]);
                        layout.canScrollToTop();
                        break;

                    case 7:
                        getTheNearest(places[pos]);
                        layout.canScrollToTop();
                        break;

                    case 8:
                        getTheNearest(places[pos]);
                        layout.canScrollToTop();
                        break;

                    case 9:
                        getTheNearest(places[pos]);
                        layout.canScrollToTop();
                        break;

                    case 10:
                        getTheNearest(places[pos]);
                        layout.canScrollToTop();
                        break;

                    case 11:
                        getTheNearest(places[pos]);
                        layout.canScrollToTop();
                        break;

                    case 12:
                        getTheNearest(places[pos]);
                        layout.canScrollToTop();
                        break;

                    case 13:
                        getTheNearest(places[pos]);
                        layout.canScrollToTop();
                        break;

                    case 14:
                        getTheNearest(places[pos]);
                        layout.canScrollToTop();
                        break;

                    case 15:
                        getTheNearest(places[pos]);
                        layout.canScrollToTop();
                        break;

                    case 16:
                        getTheNearest(places[pos]);
                        layout.canScrollToTop();
                        break;

                    case 17:
                        getTheNearest(places[pos]);
                        layout.canScrollToTop();
                        break;

                    case 18:
                        getTheNearest(places[pos]);
                        layout.canScrollToTop();
                        break;

                    case 19:
                        getTheNearest(places[pos]);
                        layout.canScrollToTop();
                        break;

                    case 20:
                        getTheNearest(places[pos]);
                        layout.canScrollToTop();
                        break;

                    case 21:
                        getTheNearest(places[pos]);
                        layout.canScrollToTop();
                        break;

                    case 22:
                        getTheNearest(places[pos]);
                        layout.canScrollToTop();
                        break;


                }




            }
        });

    }

    @Override
    public void onBackPressed() {
        layout.canScrollToTop();

        if (explainPLaceLL.getVisibility() == View.VISIBLE){
            explainPLaceLL.setVisibility(View.GONE);
            layout.setVisibility(View.VISIBLE);
            return;
        }

        if (!layout.canScrollToTop()){
            overridePendingTransition(R.anim.fadein_scan, R.anim.fadeout_scan);
        }


    }

    void inflateBottomSheet(){
        takeMeToMaps = (TextView) findViewById(R.id.takeMetoMaps);


        isVerifiedLV        =    (ImageView) findViewById(R.id.isVerifiedLL);

        placeTitleMapsTV    =   (TextView) findViewById(R.id.placeTitleMaps);
        placeStarsTextTV    =   (TextView) findViewById(R.id.placeStarsText);
        placeStarsRB        =   (RatingBar) findViewById(R.id.placeStarsRB);

        subscribeToNotificationsIV  =   (ImageView) findViewById(R.id.subscribeToNotifications);
        unsubscribeToNotificationsIV=   (ImageView) findViewById(R.id.unsubscribeToNotifications);

        subscribeToPostsBTN         =   (Button)    findViewById(R.id.subscribeToPosts);
        unSubscribeToPostsBTN       =   (Button)    findViewById(R.id.unSubscribeToPosts);

        progressBarSubs             =   (ProgressBar) findViewById(R.id.progressBarSubs);

        explainPLaceLL = findViewById(R.id.explainPlaceLL);
        gettingPlacesPLL = (LinearLayout) findViewById(R.id.gettingPlacesPLL);

        layout = (HelperCardsLayout) findViewById(R.id.cardHelper);


    }



    String returnStoreKey( double lat, double longitude ){
        return Double.toString(lat).replace(".","d") + "xf" + Double.toString(longitude).replace(".","d");
    }





    void setCustomLatLong(final LatLng latLng ){

        Marker currentLocationmMarker;


//        ChIJ2YT8sRsVrjsR2R1sQJUJ3Sc

        final MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title("Custom Title");
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE));
        mMap.setBuildingsEnabled(true);

        currentLocationmMarker = mMap.addMarker(markerOptions);
        currentLocationmMarker.setSnippet("custom snippet");
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
        },1000);

    }

   /* private Runnable mAnimation;
    private static class BounceAnimation implements Runnable {

        private final long mStart, mDuration;
        private final Interpolator mInterpolator;
        private final Marker mMarker;
        private final Handler mHandler;

        private BounceAnimation(long start, long duration, Marker marker, Handler handler) {
            mStart = start;
            mDuration = duration;
            mMarker = marker;
            mHandler = handler;
            mInterpolator = new BounceInterpolator();
        }

        @Override
        public void run() {
            long elapsed = SystemClock.uptimeMillis() - mStart;
            float t = Math.max(1 - mInterpolator.getInterpolation((float) elapsed / mDuration), 0f);
            mMarker.setAnchor(0.5f, 1.0f + 1.2f * t);

            if (t > 0.0) {
                // Post again 16ms later.
                mHandler.postDelayed(this, 16L);
            }
        }
    }
*/

}