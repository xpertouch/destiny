package corp.burenz.expertouch.map_utils;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

import corp.burenz.expertouch.R;

public class SearchPlaces extends AppCompatActivity {


    private static final String LOG_TAG = "Places";

    private static final String PLACES_API_BASE = "https://maps.googleapis.com/maps/api/place";
    private static final String TYPE_AUTOCOMPLETE = "/autocomplete";
    private static final String OUT_JSON = "/json";
    SearchLocationAdapter searchLocationAdapter;

    //------------ make your specific key ------------
    private static final String API_KEY = "AIzaSyC-pygGcIpSzAGs6BqPrUa20jyPyjWPud0";

    AutoCompleteTextView autoCompView;
    RecyclerView myCustomRV;

    DataFromPlaces dataFromPlaces;


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.fadein_scan, R.anim.fadeout_scan);


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_places);


        autoCompView = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView);
        autoCompView.setDropDownHeight(0);

        myCustomRV = (RecyclerView) findViewById(R.id.myCustomRV);
        myCustomRV.setLayoutManager(new LinearLayoutManager(this));

//        new GooglePlacesAutocompleteAdapter(this, R.layout.list_item);
        autoCompView.setAdapter(new GooglePlacesAutocompleteAdapter(this, R.layout.list_item));


        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(autoCompView, InputMethodManager.SHOW_IMPLICIT);

    }






    public static DataFromPlaces autocomplete(String input) {

        DataFromPlaces dataFromPlaces = new DataFromPlaces();


        ArrayList<String> resultList = null;

        Log.e("called","called auto complete  with "+input);

        HttpURLConnection conn = null;
        StringBuilder jsonResults = new StringBuilder();
        try {
            StringBuilder sb = new StringBuilder(PLACES_API_BASE + TYPE_AUTOCOMPLETE + OUT_JSON);
            sb.append("?key=" + API_KEY);
            sb.append("&components=country:in");
            sb.append("&input=" + URLEncoder.encode(input, "utf8"));

            URL url = new URL(sb.toString());

            System.out.println("URL: "+url);
            conn = (HttpURLConnection) url.openConnection();
            InputStreamReader in = new InputStreamReader(conn.getInputStream());

            // Load the results into a StringBuilder
            int read;
            char[] buff = new char[1024];
            while ((read = in.read(buff)) != -1) {
                jsonResults.append(buff, 0, read);
            }
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Error processing Places API URL", e);
            return new DataFromPlaces();
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error connecting to Places API", e);
            return new DataFromPlaces();
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }

        try {

            // Create a JSON object hierarchy from the results
            JSONObject jsonObj = new JSONObject(jsonResults.toString());
            JSONArray predsJsonArray = jsonObj.getJSONArray("predictions");

            // Extract the Place descriptions from the results
            resultList = new ArrayList<String>(predsJsonArray.length());
            for (int i = 0; i < predsJsonArray.length(); i++) {
                System.out.println(predsJsonArray.getJSONObject(i).getString("description"));

                System.out.println("============================================================");
                resultList.add(predsJsonArray.getJSONObject(i).getString("description"));
                dataFromPlaces.addPlace(predsJsonArray.getJSONObject(i).getString("place_id"),predsJsonArray.getJSONObject(i).getJSONObject("structured_formatting").getString("main_text"),predsJsonArray.getJSONObject(i).getString("description"));
            }
        } catch (JSONException e) {
            Log.e(LOG_TAG, "Cannot process JSON results", e);
        }

        return dataFromPlaces;
    }



    class GooglePlacesAutocompleteAdapter extends ArrayAdapter implements Filterable {
        private ArrayList<String> resultList;
        private int itemLayout;
        private Context mContext;
/*

        public GooglePlacesAutocompleteAdapter(Context context, int textViewResourceId) {
            super(context, textViewResourceId);
        }
*/

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

            if (convertView == null) {
                convertView = LayoutInflater.from(parent.getContext())
                        .inflate(itemLayout, parent, false);
            }

            TextView strName = (TextView) convertView.findViewById(R.id.cityTitleLV);
//            strName.setText(resultList.get(position));
            return convertView;

        }

        public GooglePlacesAutocompleteAdapter(Context context, int resource) {
            super(context, resource);
            mContext = context;
            itemLayout = resource;
        }

        @Override
        public int getCount() {
            return resultList.size();
        }

        @Override
        public String getItem(int index) {
            return resultList.get(index);
        }

        @Override
        public Filter getFilter() {
            Filter filter = new Filter() {
                @Override
                protected FilterResults performFiltering(CharSequence constraint) {
                    FilterResults filterResults = new FilterResults();
                    if (constraint != null) {

//                        Toast.makeText(mContext, "Started process " , Toast.LENGTH_SHORT).show();
                        // Retrieve the autocomplete results.

                        dataFromPlaces = autocomplete(constraint.toString());

                        resultList = dataFromPlaces.getPlaceAddress();
                        // Assign the data to the FilterResults
                        filterResults.values = resultList;
                        filterResults.count = resultList.size();


                    }




                    return filterResults;
                }

                @Override
                protected void publishResults(CharSequence constraint, FilterResults results) {
                    if (results != null && results.count > 0) {
                        searchLocationAdapter = new SearchLocationAdapter(dataFromPlaces,SearchPlaces.this);
                        searchLocationAdapter.notifyDataSetChanged();
                        myCustomRV.setAdapter(searchLocationAdapter);

                        notifyDataSetChanged();
                    } else {
                        searchLocationAdapter = new SearchLocationAdapter(new DataFromPlaces(),SearchPlaces.this);
                        searchLocationAdapter.notifyDataSetChanged();
                        myCustomRV.setAdapter(searchLocationAdapter);

                        notifyDataSetInvalidated();
                    }
                }
            };
            return filter;
        }
    }





}
