package corp.burenz.expertouch.activities;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

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
import corp.burenz.expertouch.adapters.ExpertReviewAdapter;
import corp.burenz.expertouch.butter.GuestInformation;
import corp.burenz.expertouch.butter.MySharedConfig;

/**
 * Created by buren on 26/1/18.
 */

public class AllExpertReviews extends AppCompatActivity {

    AllExpertReviews.GetIndividualRatings.RatingsObtained ratingsObtained;

    Context             mContext;
    TextView            overallRatingsTV, ratingsAndReviewsTV, fiveStarCountTV, fourStarCountTV, threeStarCountTV, twoStarCountTV, oneStarCountTV;
    ProgressBar         fiveStarProgressBar, fourStarProgressBar, threeStarProgressBar, twoStarProgressBar, oneStarProgressBar;
    CardView            expertReviewCardHolder,noRatingsYetCardView;
    RecyclerView        threeReviewsRecyclerView;
    Button              rateAndReviewExpert,showMeAllReviews,rateTheExpertButton;
    LinearLayout        showAllReviewsHolder;
    String              review_body,review_stars;
    String              expert_identity;




    @Override
    protected void onResume() {
        super.onResume();
        if (expert_identity != null){  try{ new AllExpertReviews.GetIndividualRatings().execute(expert_identity); }catch (Exception e){e.printStackTrace();}}
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.all_expert_reviews);



        inflate_views();
        onClickListeners();


        try{ expert_identity = getIntent().getExtras().getString("expertIdentity"); }catch (Exception e){e.printStackTrace();}

        inflate_views();
        onClickListeners();
        try {new GetIndividualRatings().execute(expert_identity);} catch (Exception e){e.printStackTrace();}


    }

    private void    onClickListeners(){
        rateAndReviewExpert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, RateExpertActivity.class);
                try{
                    intent.putExtra("expertIdentity",expert_identity);
                    intent.putExtra("r_body",review_body);
                    intent.putExtra("r_stars",review_stars);
                }catch (Exception e){
                    e.printStackTrace();
                }
                startActivity(intent);

            }
        });

        showMeAllReviews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(mContext, AllExpertReviews.class);


                try{
                    intent.putExtra("expertIdentity",expert_identity);
                }catch (Exception e){
                    e.printStackTrace();
                }
                startActivity(intent);

            }
        });


        rateTheExpertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(mContext, RateExpertActivity.class);


                try{
                    intent.putExtra("expertIdentity",expert_identity);
                }catch (Exception e){
                    e.printStackTrace();
                }
                startActivity(intent);


            }
        });


    }
    private void    inflate_views(){
        mContext = this;

        overallRatingsTV        = (TextView) findViewById(R.id.overall_stars_tv);
        ratingsAndReviewsTV     = (TextView) findViewById(R.id.ratings_and_reviews_tv);
        fiveStarCountTV         = (TextView) findViewById(R.id.five_stars_count_tv);
        fourStarCountTV         = (TextView) findViewById(R.id.four_stars_count_tv);
        threeStarCountTV        = (TextView) findViewById(R.id.three_stars_count_tv);
        twoStarCountTV          = (TextView) findViewById(R.id.two_stars_count_tv);
        oneStarCountTV          = (TextView) findViewById(R.id.one_stars_count_tv);

        fiveStarProgressBar     = (ProgressBar) findViewById(R.id.five_star_progress);
        fourStarProgressBar     = (ProgressBar) findViewById(R.id.four_star_progress);
        threeStarProgressBar    = (ProgressBar) findViewById(R.id.three_star_progress);
        twoStarProgressBar      = (ProgressBar) findViewById(R.id.two_star_progress);
        oneStarProgressBar      = (ProgressBar) findViewById(R.id.one_star_progress);

        expertReviewCardHolder  = (CardView)    findViewById(R.id.expert_reviews_card_view);
        threeReviewsRecyclerView= (RecyclerView)findViewById(R.id.three_reviws_recyler);
        rateAndReviewExpert     = (Button) findViewById(R.id.rate_and_write_button);
        showMeAllReviews        = (Button) findViewById(R.id.show_all_reviews);
        noRatingsYetCardView    = (CardView)findViewById(R.id.no_ratings_yet);
        showAllReviewsHolder    = (LinearLayout) findViewById(R.id.all_ratings_holder_view);
        rateTheExpertButton     = (Button) findViewById(R.id.rate_the_expert_button);
        rateAndReviewExpert     .setVisibility(View.GONE);
        showAllReviewsHolder    .setVisibility(View.GONE);






    }
    private class   IsReviewedByMe extends AsyncTask< String, String, String > {





        @Override
        protected String doInBackground(String... strings) {

            String urlToHit = mContext.getString(R.string.host) + "/workshop/expert_review.php";
            StringBuilder stringBuilder = new StringBuilder();

            URL url;
            HttpURLConnection httpURLConnection;

            try {
                url = new URL(urlToHit+ "?functionname="+ URLEncoder.encode("is_reviewed_by_me","UTF-8")+ "&expert_id=" + URLEncoder.encode(strings[0],"UTF-8") +  "&user_phone="+URLEncoder.encode(mContext.getSharedPreferences(MySharedConfig.GuestPrefs.LOCAL_APP_DATA,0).getString("userEmail","null"),"UTF-8"));
                Log.e("reviews",urlToHit+ "?functionname="+URLEncoder.encode("is_reviewed_by_me","UTF-8")+ "&expert_id=" + URLEncoder.encode(strings[0],"UTF-8") +  "&user_phone="+URLEncoder.encode(mContext.getSharedPreferences(MySharedConfig.GuestPrefs.LOCAL_APP_DATA,0).getString("userEmail","null"),"UTF-8"));
                httpURLConnection = (HttpURLConnection) url.openConnection();

                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
                String line = "";
                while ( (line = bufferedReader.readLine()) != null ){
                    stringBuilder.append(line + "\n");
                }

                JSONArray jsonArray = new JSONArray(stringBuilder.toString());
                JSONObject jsonObject = jsonArray.getJSONObject(0);

                review_body     = jsonObject.getString("r_body");
                review_stars    = jsonObject.getString("r_stars");


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }


            return stringBuilder.toString();
        }



        @Override
        protected void onPostExecute(String responseFromServer) {
            super.onPostExecute(responseFromServer);

            if (responseFromServer.trim().length() < 3  ){
                Log.e("reviews","inside true" + responseFromServer.trim().length());

            }else {
                Log.e("reviews","inside false" + responseFromServer.trim().length());

                rateAndReviewExpert.setText("EDIT MY REVIEW");

            }



        }
    }
    private class   GetIndividualRatings   extends AsyncTask< String, String, String >{


        private class RatingsObtained {



            private String overall_stars, five_stars, four_stars, three_stars, two_stars, one_stars,total_stars;

            RatingsObtained(String overall_stars, String five_stars, String four_stars, String three_stars, String two_stars, String one_stars, String total_stars){

                this.five_stars     = five_stars;
                this.four_stars     = four_stars;
                this.three_stars    = three_stars;
                this.two_stars      = two_stars;
                this.one_stars      = one_stars;
                this.overall_stars  = overall_stars;
                this.total_stars    = total_stars;


            }


            String get_overall_stars(){ return overall_stars; }

            String getFive_stars() {
                return five_stars;
            }


            String getFour_stars() {
                return four_stars;
            }


            String getThree_stars() {
                return three_stars;
            }


            String getTwo_stars() {
                return two_stars;
            }


            String getOne_stars() {
                return one_stars;
            }

            String getTotal_stars() {
                return total_stars;
            }


        }
        ArrayList<String> ratingList = new ArrayList<>();

        @Override
        protected String doInBackground(String... strings) {

            /*pass the expert id in the 0th position in the execution*/
            String urlTohit             = mContext.getString(R.string.host) + "/workshop/expert_review.php";
            StringBuilder stringBuilder = new StringBuilder();
            URL url;
            HttpURLConnection httpURLConnection;


            try {
                url = new URL(urlTohit + "?functionname=" + URLEncoder.encode("get_indi", "UTF-8")+ "&expert_id=" + URLEncoder.encode(strings[0], "UTF-8"));
                Log.e("e_review",urlTohit + "?functionname=" + URLEncoder.encode("get_indi", "UTF-8")+ "&expert_id=" + URLEncoder.encode(strings[0], "UTF-8"));

                httpURLConnection = (HttpURLConnection) url.openConnection();


                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
                String line = "";

                while ( (line = bufferedReader.readLine()) != null ){
                    stringBuilder.append(line).append("\n");
                }

                JSONArray jsonArray = new JSONArray(stringBuilder.toString());
                JSONObject jsonObject;

                jsonObject = jsonArray.getJSONObject(0);


                ratingsObtained = new GetIndividualRatings.RatingsObtained(jsonObject.getString("overall_stars"),jsonObject.getString("five_stars"),jsonObject.getString("four_stars"),
                        jsonObject.getString("three_stars"),jsonObject.getString("two_stars"),jsonObject.getString("one_stars"),jsonObject.getString("total_stars"));






            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }catch (Exception e){
                e.printStackTrace();
//                doToast("weee");
            }


            return stringBuilder.toString();
        }


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.e("reviews", "Inside get reviews on pre execute");
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.e("reviews", "inside post execute get indi reviews "+ s);



            if(ratingsObtained == null){
                expertReviewCardHolder.setVisibility(View.GONE);
                noRatingsYetCardView.setVisibility(View.VISIBLE);
                return;
            }else {
                if( Integer.parseInt(ratingsObtained.getTotal_stars()) == 0 ){noRatingsYetCardView.setVisibility(View.VISIBLE); return;}

                try{    new GetExpertReviews().execute(expert_identity);    }catch (Exception e){e.printStackTrace();}
                try{    new IsReviewedByMe().execute(expert_identity);      }catch (Exception e){e.printStackTrace();}

                expertReviewCardHolder.setVisibility(View.VISIBLE);
                noRatingsYetCardView.setVisibility(View.GONE);
            }

/*
            if( Integer.parseInt(ratingsObtained.getTotal_stars()) > 3 ){showAllReviewsHolder.setVisibility(View.VISIBLE);}else {showAllReviewsHolder.setVisibility(View.GONE);}
*/

            overallRatingsTV    .setText(ratingsObtained.get_overall_stars());
            ratingsAndReviewsTV .setText( "Reviewed by " + ratingsObtained.getTotal_stars() + " user(s)");
            ratingsAndReviewsTV .setTextSize(12);
            fiveStarCountTV     .setText(ratingsObtained.getFive_stars());
            fourStarCountTV     .setText(ratingsObtained.getFour_stars());
            threeStarCountTV    .setText(ratingsObtained.getThree_stars());
            twoStarCountTV      .setText(ratingsObtained.getTwo_stars());
            oneStarCountTV      .setText(ratingsObtained.getOne_stars());

            fiveStarProgressBar .setProgress((int) (pareInt(ratingsObtained.getFive_stars())     / pareInt(ratingsObtained.getTotal_stars())  * 100));
            fourStarProgressBar .setProgress((int) (pareInt(ratingsObtained.getFour_stars())     / pareInt(ratingsObtained.getTotal_stars())  * 100));
            threeStarProgressBar.setProgress((int) (pareInt(ratingsObtained.getThree_stars())    / pareInt(ratingsObtained.getTotal_stars())  * 100));
            twoStarProgressBar  .setProgress((int) (pareInt(ratingsObtained.getTwo_stars())      / pareInt(ratingsObtained.getTotal_stars())  * 100));
            oneStarProgressBar  .setProgress((int) (pareInt(ratingsObtained.getOne_stars())      / pareInt(ratingsObtained.getTotal_stars())  * 100));




        }

        Float pareInt( String s ){
            return Float.valueOf(s);


        }

    }
    private class   GetExpertReviews       extends AsyncTask< String, String, String>{

        ArrayList<String> review_user_pic, review_user_name,review_date , review_stars , review_body, review_user_title;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            review_user_pic     = new ArrayList<>();
            review_user_name    = new ArrayList<>();
            review_date         = new ArrayList<>();
            review_stars        = new ArrayList<>();
            review_body         = new ArrayList<>();
            review_user_title   = new ArrayList<>();

            threeReviewsRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
//            doToast(s);


            ExpertReviewAdapter expertReviewAdapter = new ExpertReviewAdapter(mContext,review_user_pic,review_user_name,review_date,review_stars,review_body,review_user_title);
            try{ threeReviewsRecyclerView.setAdapter(expertReviewAdapter);}   catch (Exception e){
                Log.e("reviews","Exceptionraised in reviews " + e.toString());
            }
            /*initialize the adapter*/


        }


        @Override
        protected String doInBackground(String... strings) {


            String urlTohit             = mContext.getString(R.string.host) + "/workshop/expert_review.php";
            StringBuilder stringBuilder = new StringBuilder();

            URL url;
            HttpURLConnection httpURLConnection;


            try {
                url = new URL(urlTohit + "?functionname=" + URLEncoder.encode("get_reviews", "UTF-8") + "&expert_id=" + URLEncoder.encode(strings[0],"UTF-8"));
                httpURLConnection = (HttpURLConnection) url.openConnection();

                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
                String line = "";

                while (  (line = bufferedReader.readLine()) != null ){
                    stringBuilder.append(line).append("\n");
                }

                JSONArray jsonArray = new JSONArray(stringBuilder.toString());
                JSONObject      jsonObject;

                for ( int i = 0; i <= jsonArray.length(); i++ ){

                    grabReviewsIntoArrays(jsonArray.getJSONObject(i), review_user_pic, review_user_name,review_date, review_stars, review_body, review_user_title);

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


            return stringBuilder.toString();



        }

        void grabReviewsIntoArrays(JSONObject jsonObject, ArrayList<String> review_user_pic, ArrayList<String> review_user_name,ArrayList<String> review_date , ArrayList<String> review_stars , ArrayList<String> review_body, ArrayList<String> review_user_title) throws JSONException {

            review_user_pic     .add(jsonObject.getString("user_pic"));
            review_user_name    .add(jsonObject.getString("user_name"));
            review_date         .add(jsonObject.getString("r_date"));
            review_stars        .add(jsonObject.getString("r_stars"));
            review_body         .add(jsonObject.getString("r_body"));
            review_user_title   .add(jsonObject.getString("r_title"));

        }


    }

}
