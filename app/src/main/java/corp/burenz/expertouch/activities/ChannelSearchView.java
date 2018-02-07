package corp.burenz.expertouch.activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.widget.LinearLayout;
import android.widget.Toast;

import android.animation.Animator;

import android.support.v7.widget.DefaultItemAnimator;

import com.google.firebase.messaging.FirebaseMessaging;

import org.cryse.widget.persistentsearch.DefaultVoiceRecognizerDelegate;
import org.cryse.widget.persistentsearch.PersistentSearchView;
import org.cryse.widget.persistentsearch.SearchItem;
import org.cryse.widget.persistentsearch.SearchSuggestionsBuilder;
import org.cryse.widget.persistentsearch.VoiceRecognitionDelegate;
import org.cryse.widget.persistentsearch.SearchItem;
import org.cryse.widget.persistentsearch.SearchSuggestionsBuilder;
import org.cryse.widget.persistentsearch.PersistentSearchView.SearchListener;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import corp.burenz.expertouch.R;
import corp.burenz.expertouch.adapters.ChannelAdaper;
import corp.burenz.expertouch.butter.GuestInformation;
import corp.burenz.expertouch.databases.SearchHistory;
import corp.burenz.expertouch.util.ChannelHolder;
import corp.burenz.expertouch.util.SampleSuggestionsBuilder;
import corp.burenz.expertouch.util.SimpleAnimationListener;

public class ChannelSearchView extends AppCompatActivity {

    ArrayList<String>           companyBannerArrayList,companyTitleArrayList, companyAdddressArrayList,companyAboutArrayList, isSubscribedByMeArrayList, companyIdArrayList;
    ArrayList<String>           companyBannerArrayListS,companyTitleArrayListS, companyAdddressArrayListS,companyAboutArrayListS, isSubscribedByMeArrayListS, companyIdArrayListS;
    ChannelAdaper               channelAdaper;
    ArrayList<ChannelHolder>    channelHolderList;
    RecyclerView                recyclerView;
    PersistentSearchView        persistentSearchView;
    private static final int    VOICE_RECOGNITION_REQUEST_CODE = 1023;
    String                      insideQueryString = "";

    private LinearLayout        mSearchTintView;

    LinearLayout showProgressGettingListLinearLayout;


    void addTemporarly(){


        companyTitleArrayList.add("Demo title 1");
        companyAdddressArrayList.add("demo address");
        companyAboutArrayList.add("demo company about");
        isSubscribedByMeArrayList.add("0");
        companyIdArrayList.add("12");

        companyTitleArrayList.add("Demo title 2");
        companyAdddressArrayList.add("demo address");
        companyAboutArrayList.add("demo company about");
        isSubscribedByMeArrayList.add("01");
        companyIdArrayList.add("12");


        companyTitleArrayList.add("Demo title 3");
        companyAdddressArrayList.add("demo address");
        companyAboutArrayList.add("demo company about");
        isSubscribedByMeArrayList.add("0");
        companyIdArrayList.add("12");


        companyTitleArrayList.add("Demo title 4");
        companyAdddressArrayList.add("demo address");
        companyAboutArrayList.add("demo company about");
        isSubscribedByMeArrayList.add("0");
        companyIdArrayList.add("12");


        companyTitleArrayList.add("Demo title 5");
        companyAdddressArrayList.add("demo address");
        companyAboutArrayList.add("demo company about");
        isSubscribedByMeArrayList.add("0");
        companyIdArrayList.add("12");


        companyTitleArrayList.add("Demo title 6");
        companyAdddressArrayList.add("demo address");
        companyAboutArrayList.add("demo company about");
        isSubscribedByMeArrayList.add("1");
        companyIdArrayList.add("12");


        companyTitleArrayList.add("Demo title 7");
        companyAdddressArrayList.add("demo address");
        companyAboutArrayList.add("demo company about");
        isSubscribedByMeArrayList.add("0");
        companyIdArrayList.add("12");




        companyTitleArrayList.add("Demo title 8");
        companyAdddressArrayList.add("demo address");
        companyAboutArrayList.add("demo company about");
        isSubscribedByMeArrayList.add("0");
        companyIdArrayList.add("12");

        companyTitleArrayList.add("Demo title 9");
        companyAdddressArrayList.add("demo address");
        companyAboutArrayList.add("demo company about");
        isSubscribedByMeArrayList.add("1");
        companyIdArrayList.add("12");

        companyTitleArrayList.add("Demo title 10");
        companyAdddressArrayList.add("demo address");
        companyAboutArrayList.add("demo company about");
        isSubscribedByMeArrayList.add("1");
        companyIdArrayList.add("12");

        companyTitleArrayList.add("Demo title 11");
        companyAdddressArrayList.add("demo address");
        companyAboutArrayList.add("demo company about");
        isSubscribedByMeArrayList.add("0");
        companyIdArrayList.add("12");

        companyTitleArrayList.add("Demo title 12");
        companyAdddressArrayList.add("demo address");
        companyAboutArrayList.add("demo company about");
        isSubscribedByMeArrayList.add("0");
        companyIdArrayList.add("12");

    }
    void doToast(String s){
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }


    private void createHistorys() {
        List<SearchItem> mHistorySuggestions = new ArrayList<SearchItem>();
        SearchItem item1 = new SearchItem(
                "Isaac Newton",
                "Isaac Newton",
                SearchItem.TYPE_SEARCH_ITEM_HISTORY
        );
        mHistorySuggestions.add(item1);
        SearchItem item2 = new SearchItem(
                "Albert Einstein",
                "Albert Einstein",
                SearchItem.TYPE_SEARCH_ITEM_HISTORY
        );
        mHistorySuggestions.add(item2);
        SearchItem item3 = new SearchItem(
                "John von Neumann",
                "John von Neumann",
                SearchItem.TYPE_SEARCH_ITEM_HISTORY
        );
        mHistorySuggestions.add(item3);
        SearchItem item4 = new SearchItem(
                "Alan Mathison Turing",
                "Alan Mathison Turing",
                SearchItem.TYPE_SEARCH_ITEM_HISTORY
        );
        mHistorySuggestions.add(item4);
    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.works_space_scroll);


//        FirebaseMessaging.getInstance().subscribeToTopic("/topics/news");

//        FirebaseMessaging.getInstance().subscribeToTopic("nono");



        recyclerView = (RecyclerView) findViewById(R.id.channelListRecyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        channelHolderList                    = new ArrayList<>();
        companyTitleArrayList                = new ArrayList<>();
        companyAdddressArrayList             = new ArrayList<>();
        companyAboutArrayList                = new ArrayList<>();

        isSubscribedByMeArrayList            = new ArrayList<>();
        companyIdArrayList                   = new ArrayList<>();
        companyBannerArrayList               = new ArrayList<>();

        companyTitleArrayListS               = new ArrayList<>();
        companyAdddressArrayListS            = new ArrayList<>();
        companyAboutArrayListS               = new ArrayList<>();

        isSubscribedByMeArrayListS           = new ArrayList<>();
        companyIdArrayListS                  = new ArrayList<>();


//        addTemporarly();


        persistentSearchView = (PersistentSearchView) findViewById(R.id.searchview);
        mSearchTintView = (LinearLayout) findViewById(R.id.view_search_tint);
        showProgressGettingListLinearLayout = (LinearLayout) findViewById(R.id.show_progress_getting_channel);

        new GETChannelList().execute("all");



        assert persistentSearchView != null;
















        VoiceRecognitionDelegate delegate = new DefaultVoiceRecognizerDelegate(this, VOICE_RECOGNITION_REQUEST_CODE);
        if(delegate.isVoiceRecognitionAvailable()) {
            persistentSearchView.setVoiceRecognitionDelegate(delegate);
        }


        persistentSearchView.setHomeButtonListener(new PersistentSearchView.HomeButtonListener() {

            @Override
            public void onHomeButtonClick() {
                //Hamburger has been clicked
                finish();
            }

        });


        mSearchTintView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                persistentSearchView.cancelEditing();
            }
        });

        persistentSearchView.setSearchListener(new SearchListener() {

            @Override
            public void onSearchCleared() {

            }

            @Override
            public void onSearchTermChanged(String term) {

                insideQueryString = term;

                new GETChannelSugesstionList().execute(term.toLowerCase());


//                if(term.length() == 0){ new GETChannelList().execute("all");  }


           /*     if(companyTitleArrayList.size() == 0){doToast("Cannot Search in an empty List");return;}*/
               /* companyTitleArrayListS       .clear();
                companyAdddressArrayListS    .clear();
                companyAboutArrayListS       .clear();
                isSubscribedByMeArrayListS   .clear();
                companyIdArrayListS          .clear();

                for (int i = 0; i < companyTitleArrayList.size(); i++){

                    if(   companyTitleArrayList.get(i).toLowerCase().contains(term.toLowerCase()) || companyAdddressArrayList.get(i).toLowerCase().contains(term.toLowerCase())    ){

                        companyTitleArrayListS          .add(companyTitleArrayList.get(i));
                        companyAdddressArrayListS       .add(companyAdddressArrayList.get(i));
                        companyAboutArrayListS          .add(companyAboutArrayList.get(i));
                        isSubscribedByMeArrayListS      .add(isSubscribedByMeArrayList.get(i));
                        companyIdArrayListS             .add(companyIdArrayList.get(i));

                    }




                }

                channelAdaper = new ChannelAdaper(ChannelSearchView.this,new ChannelHolder(companyBannerArrayListS,companyTitleArrayListS, companyAdddressArrayListS,companyAboutArrayListS, isSubscribedByMeArrayListS, companyIdArrayListS));
                recyclerView.setAdapter(channelAdaper);
                channelAdaper.notifyDataSetChanged();
                Log.e("searchVM","onSearchTermChanged term =  " + term);
                persistentSearchView.setSuggestionBuilder(new SampleSuggestionsBuilder(ChannelSearchView.this,companyTitleArrayList));*/


            }

            @Override
            public void onSearch(String query) {

                Log.e("searchVM","onSearch query=" + query);

                SearchHistory searchHistory = new SearchHistory(ChannelSearchView.this);
                searchHistory.writer();

                if (!searchHistory.getHistoryStringArray().contains(query)){ searchHistory.createHistory(query.toLowerCase().trim());}

                searchHistory.close();

                new GETChannelList().execute("like",query.toLowerCase());




            }

            @Override
            public void onSearchEditOpened() {

                Log.e("searchVM","onSearchEdit Opened");

                mSearchTintView.setVisibility(View.VISIBLE);
                mSearchTintView
                        .animate()
                        .alpha(1.0f)
                        .setDuration(300)
                        .setListener(new SimpleAnimationListener())
                        .start();

            }

            @Override
            public void onSearchEditClosed() {
                Log.e("searchVM","onSearchEdit Closed");


                mSearchTintView
                        .animate()
                        .alpha(0.0f)
                        .setDuration(300)
                        .setListener(new SimpleAnimationListener() {
                            @Override
                            public void onAnimationEnd(Animator animation) {
                                super.onAnimationEnd(animation);
                                mSearchTintView.setVisibility(View.GONE);
                            }
                        })
                        .start();

                if(insideQueryString.length() == 0){
                    Log.e("Channel","No text in the query");
                    new GETChannelList().execute("all");
                }


            }

            @Override
            public boolean onSearchEditBackPressed() {

                Log.e("searchVM","onSearchBacEditPressed called");
                return false;

            }

            @Override
            public void onSearchExit() {


            }
        });

        persistentSearchView.setSuggestionBuilder(  new SampleSuggestionsBuilder(this)  );


    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == VOICE_RECOGNITION_REQUEST_CODE && resultCode == RESULT_OK) {
            ArrayList<String> matches = data
                    .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            persistentSearchView.populateEditText(matches);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onBackPressed() {
        if(persistentSearchView.isEditing()) {
            persistentSearchView.cancelEditing();
        } else {
            super.onBackPressed();
        }
    }



    private class GETChannelList   extends AsyncTask< String, String , String  >      {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.e("channel_l","Inside GETChannelList onPre");

            companyTitleArrayList       .clear();
            companyAdddressArrayList    .clear();
            companyAboutArrayList       .clear();
            isSubscribedByMeArrayList   .clear();
            companyIdArrayList          .clear();
            companyBannerArrayList      .clear();

            Log.e("channel","Size in preex fixture " + companyTitleArrayList.size());


                /*set a progress abr visibilty*/


            showProgressGettingListLinearLayout
                    .animate()
                    .setDuration(300)
                    .setListener(new SimpleAnimationListener(){

                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            showProgressGettingListLinearLayout.setVisibility(View.VISIBLE);

                        }
                    });


        }


        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
//            doToast(" Get Channel List on Post " +s);

            /*check the length an hide if 0*/


            Log.e("channel","Size before fixture " + companyTitleArrayList.size());

            channelAdaper = new ChannelAdaper(ChannelSearchView.this, new ChannelHolder(companyBannerArrayList, companyTitleArrayList, companyAdddressArrayList, companyAboutArrayList, isSubscribedByMeArrayList, companyIdArrayList));
            recyclerView.setAdapter(channelAdaper);

            channelAdaper.notifyDataSetChanged();


            /*set the adapter for the main recycler view without any chnage in suggestions*/
            /*this clas is called in onSearch and onCreate*/ /* show a progress and then hides it */
            /*if the query length is 0 then  its hould call all the channelm name*/

            showProgressGettingListLinearLayout
                    .animate()
                    .setDuration(300)
                    .setListener(new SimpleAnimationListener(){

                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            showProgressGettingListLinearLayout.setVisibility(View.GONE);

                        }
                    });


        }

        @Override
        protected String doInBackground(String... strings) {

            String urlToHit             = "http://192.168.43.190/ver1.1/workshop/subscription_utils.php";
            StringBuilder stringBulder  = new StringBuilder();


            Log.e("channel_l","Inside GETChannelList Getting "+strings[0]);

            HttpURLConnection httpUrlConnection;
            URL url;

            try {

                switch (strings[0]) {

                    case "all":
                        url = new URL(urlToHit + "?functionname=" + URLEncoder.encode(strings[0], "UTF-8") + "&user_phone=" + URLEncoder.encode(new GuestInformation(ChannelSearchView.this).getGuestNumber(), "UTF-8"));
                        Log.e("channel",urlToHit + "?functionname=" + URLEncoder.encode(strings[0], "UTF-8") + "&user_phone=" + URLEncoder.encode(new GuestInformation(ChannelSearchView.this).getGuestNumber(), "UTF-8") );
                        break;

                    case "like":
                        url = new URL(urlToHit + "?functionname=" + URLEncoder.encode(strings[0], "UTF-8") + "&user_phone=" + URLEncoder.encode(new GuestInformation(ChannelSearchView.this).getGuestNumber(), "UTF-8") + "&like_k=" + URLEncoder.encode(strings[1], "UTF-8"));
                        Log.e("channel",urlToHit + "?functionname=" + URLEncoder.encode(strings[0], "UTF-8") + "&user_phone=" + URLEncoder.encode(new GuestInformation(ChannelSearchView.this).getGuestNumber(), "UTF-8") + "&like_k=" + URLEncoder.encode(strings[1], "UTF-8"));
                        break;

                    default:
                        url = new URL(urlToHit + "?functionname=" + URLEncoder.encode(strings[0], "UTF-8") + "&user_phone=" + URLEncoder.encode(new GuestInformation(ChannelSearchView.this).getGuestNumber(), "UTF-8"));


                }

                httpUrlConnection = (HttpURLConnection) url.openConnection();
                String line = "";
                BufferedReader bufferReader = new BufferedReader(new InputStreamReader(httpUrlConnection.getInputStream()));
                while ((line = bufferReader.readLine()) != null) {
                    stringBulder.append(line).append("\n");
                }

                JSONObject jsonObject;
                JSONArray jsonArray = new JSONArray(stringBulder.toString());

                for (int i = 0; i < jsonArray.length(); i++) {

                    jsonObject = jsonArray.getJSONObject(i);

                    companyTitleArrayList       .add(jsonObject.getString("company_title"));
                    companyAdddressArrayList    .add(jsonObject.getString("company_city"));
                    companyAboutArrayList       .add(jsonObject.getString("company_discription"));
                    isSubscribedByMeArrayList   .add(jsonObject.getString("is_subscribed"));
                    companyIdArrayList          .add(jsonObject.getString("company_id"));
                    companyBannerArrayList      .add(jsonObject.getString("company_banner"));

                }


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return stringBulder.toString();

        }



    }

    private class GETChannelSugesstionList extends AsyncTask<String, String, String> {

        ArrayList<String> companyTitleArrayListSuggestions;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            companyTitleArrayListSuggestions = new ArrayList<>();
            companyTitleArrayListSuggestions.clear();


            /*set a progress abr visibilty*/

        }


        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
//            doToast( "Channel Suggestions " + s);

//            persistentSearchView.clearSuggestions();
            persistentSearchView.setSuggestionBuilder(new SampleSuggestionsBuilder(ChannelSearchView.this, companyTitleArrayListSuggestions));

        }

        @Override
        protected String doInBackground(String... strings) {


            String urlToHit = "http://192.168.43.190/ver1.1/workshop/subscription_utils.php";
            StringBuilder stringBulder = new StringBuilder();

            HttpURLConnection httpUrlConnection;
            URL url;


            try {

                url = new URL(urlToHit + "?functionname=" + URLEncoder.encode("suggest", "UTF-8") + "&term=" + URLEncoder.encode(strings[0], "UTF-8"));
                Log.e("channel",urlToHit + "?functionname=" + URLEncoder.encode("suggest", "UTF-8") + "&term=" + URLEncoder.encode(strings[0], "UTF-8"));
                httpUrlConnection = (HttpURLConnection) url.openConnection();
                String line = "";
                BufferedReader bufferReader = new BufferedReader(new InputStreamReader(httpUrlConnection.getInputStream()));
                while ((line = bufferReader.readLine()) != null) {
                    stringBulder.append(line).append("\n");

                }
                JSONArray jsonArray = new JSONArray(stringBulder.toString());

                for (int i = 0; i < jsonArray.length(); i++) {

                    companyTitleArrayListSuggestions.add(jsonArray.getJSONObject(i).getString("company_title"));

                }


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return stringBulder.toString();

        }


    }



}
