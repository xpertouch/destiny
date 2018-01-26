package corp.burenz.expertouch.util;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

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
import corp.burenz.expertouch.activities.RateExpertActivity;
import corp.burenz.expertouch.adapters.ExpertReviewAdapter;
import corp.burenz.expertouch.butter.GuestInformation;
import corp.burenz.expertouch.butter.MySharedConfig;

/**
 * Created by buren on 19/1/18.
 */

public class ExpertReviews extends AppCompatActivity{

    private Context mContext;

    TextView        overallRatingsTV, ratingsAndReviewsTV, fiveStarCountTV, fourStarCountTV, threeStarCountTV, twoStarCountTV, oneStarCountTV;
    ProgressBar     fiveStarProgressBar, fourStarProgressBar, threeStarProgressBar, twoStarProgressBar, oneStarProgressBar;
    CardView        expertReviewCardHolder;
    RecyclerView    threeReviewsRecyclerView;
    Button          rateAndReviewExpert;


    @Override
    protected void onResume() {
        super.onResume();

        new GetIndividualRatings().execute("12");
        new GetExpertReviews().execute("12");

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.review_layout);
        mContext = this;
        inflate_views();

        rateAndReviewExpert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ExpertReviews.this, RateExpertActivity.class));
            }
        });

        new GetIndividualRatings().execute("12");
        new GetExpertReviews().execute("12");





    }



    void inflate_views(){

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






    }

    class IsReviewedByMe         extends AsyncTask< String, String, String >{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            doToast("inside the on Pre - Execute Method ");

        }



        @Override
        protected String doInBackground(String... strings) {

            String urlToHit = mContext.getString(R.string.host) + "workshop/expert_reviews.php";
            StringBuilder stringBuilder = new StringBuilder();

            URL url;
            HttpURLConnection httpURLConnection;

            try {
                url = new URL(urlToHit+ "?functionname="+URLEncoder.encode("is_reviewed_by_me","UTF-8")+ "&user_phone"+URLEncoder.encode(mContext.getSharedPreferences(MySharedConfig.GuestPrefs.LOCAL_APP_DATA,0).getString("userEmail","null"),"UTF-8"));
                httpURLConnection = (HttpURLConnection) url.openConnection();

                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
                String line = "";
                while ( (line = bufferedReader.readLine()) != null ){
                    stringBuilder.append(line + "\n");
                }


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


            return stringBuilder.toString();
        }



        @Override
        protected void onPostExecute(String responseFromServer) {
            super.onPostExecute(responseFromServer);

            switch (responseFromServer){

                        case "0":
                            /*show the bar that allows it to submit a review to the expert*/
                            break;

                        case "1":
                            /*hide the bar and show his own review that he has posted already*/
                            break;

            }


            doToast("Just completed the on Post Execute");

        }
    }

    class MakeAReview            extends AsyncTask< String, String, String >{


        @Override
        protected String doInBackground(String... strings) {

            String user_id       = new GuestInformation(mContext).getGuestNumber();
            String expert_id     = strings[0];
            String stars_given   = strings[1];
            String review_body   = strings[2];



            String urlToHit = mContext.getString(R.string.host) + "workshop/expert_reviews.php";
            StringBuilder  stringBuilder = new StringBuilder();

            URL url;
            HttpURLConnection httpURLConnection;

            try {
                url = new URL(urlToHit+"?functionname="+URLEncoder.encode("make_review", "UTF-8")+ "&user_phone="+URLEncoder.encode(user_id, "UTF-8")
                        +"&expert_id="+URLEncoder.encode(expert_id, "UTF-8")+"&stars_given="+URLEncoder.encode(stars_given, "UTF-8")
                        +"&review_body=" + URLEncoder.encode(review_body,"UTF-8"));
                httpURLConnection = (HttpURLConnection) url.openConnection();

                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
                String line = "";
                while ( (line = bufferedReader.readLine()) != null ){
                    stringBuilder.append(line + "\n");
                }


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


            return stringBuilder.toString();

        }


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            doToast("inside make review on pre execute");

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            doToast("inside on post execute of make _ review");

            /*update the new ratings now */
        }


    }

    private class GetIndividualRatings   extends AsyncTask< String, String, String >{
        RatingsObtained ratingsObtained;


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
            String urlTohit             = "http://192.168.43.190/ver1.1/workshop/expert_review.php";
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


                ratingsObtained = new RatingsObtained(jsonObject.getString("overall_stars"),jsonObject.getString("five_stars"),jsonObject.getString("four_stars"),
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

//            expertReviewCardHolder.setVisibility(View.GONE);
            if(ratingsObtained == null){ return;}

            overallRatingsTV    .setText(ratingsObtained.get_overall_stars());
            ratingsAndReviewsTV .setText("Some Ratings and reviews");
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


    public class GetExpertReviews       extends AsyncTask< String, String, String>{

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


            String urlTohit             = "http://192.168.43.190/ver1.1/workshop/expert_review.php";
            StringBuilder stringBuilder = new StringBuilder();

            URL url;
            HttpURLConnection httpURLConnection;


            try {
                url = new URL(urlTohit + "?functionname=" + URLEncoder.encode("get_three", "UTF-8") + "&expert_id=" + URLEncoder.encode(strings[0],"UTF-8"));
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

    private void doToast(String s){
        Toast.makeText(mContext, s, Toast.LENGTH_SHORT).show();

    }

}
