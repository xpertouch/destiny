package corp.burenz.expertouch.activities;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RatingBar;
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

import corp.burenz.expertouch.R;
import corp.burenz.expertouch.butter.GuestInformation;
import corp.burenz.expertouch.butter.MySharedConfig;

/**
 * Created by buren on 26/1/18.
 */

public class RateExpertActivity extends AppCompatActivity {


    LinearLayout    reviewExpertStars, reviewExpertText, progressReview;
    RatingBar       ratingBarForExpert;
    Button          cancelMyReview, submitMyReview;
    Float           ratingsSet;
    EditText        my_review_text;
    String          review_body,review_stars, expertIdentity;





    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inverse_layout);

        inflateViews();


        Bundle extrasFromReviews    = getIntent().getExtras();

        if(extrasFromReviews != null){


             try{
                 expertIdentity  = extrasFromReviews.getString("expertIdentity");

                 review_body     = extrasFromReviews.getString("r_body");
                 review_stars    = extrasFromReviews.getString("r_stars");


             }   catch (Exception e){
                 e.printStackTrace();

             }finally {

                 if (review_body != null && review_stars != null ) {
                     ratingsSet              = Float.valueOf(review_stars);
                     ratingBarForExpert     .setRating(Float.valueOf(review_stars));
                     my_review_text         .setText(review_body);
                     reviewExpertText       .setVisibility(View.VISIBLE);

                 }else {

                     try {new IsReviewedByMe().execute(expertIdentity); }catch (Exception e){e.printStackTrace();}

                 }

             }



        }else{

            try {new IsReviewedByMe().execute(expertIdentity); }catch (Exception e){e.printStackTrace();}

        }




        cancelMyReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RateExpertActivity.this.finish();
            }
        });

        submitMyReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(ratingsSet == null){
                    Toast.makeText(RateExpertActivity.this, "Please Rate the expert before submitting", Toast.LENGTH_SHORT).show();
                }else if( ratingsSet == 0.0 || ratingsSet == 0.5 ){
                    Toast.makeText(RateExpertActivity.this, "Minimum ratings should be at least one star!", Toast.LENGTH_SHORT).show();
                } else {

                    Log.e("raitngs","ratings given to expert " + ratingsSet + " review discription " + my_review_text.getText().toString());

                    new MakeAReview().execute(expertIdentity,ratingsSet.toString(),my_review_text.getText().toString());

                }


            }
        });


        ratingBarForExpert.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                ratingsSet = v;
                reviewExpertText.setVisibility(View.VISIBLE);
            }
        });






    }


    private void inflateViews(){

        reviewExpertStars   = (LinearLayout)    findViewById(R.id.review_expert_stars);
        reviewExpertText    = (LinearLayout)    findViewById(R.id.review_expert_text);
        ratingBarForExpert  = (RatingBar)       findViewById(R.id.ratings_bar_for_expert);
        cancelMyReview      = (Button)          findViewById(R.id.cancel_my_expert_review_button);
        submitMyReview      = (Button)          findViewById(R.id.submit_my_expert_review_button);
        my_review_text      = (EditText)        findViewById(R.id.my_review_edit_text);
        progressReview      = (LinearLayout)    findViewById(R.id.progress_layout_submit_review);

    }


    private class MakeAReview  extends AsyncTask< String, String, String > {


        @Override
        protected String doInBackground(String... strings) {

            String user_id       = new GuestInformation(RateExpertActivity.this).getGuestNumber();
            String expert_id     = strings[0];
            String stars_given   = strings[1];
            String review_body   = strings[2];



            String urlToHit = RateExpertActivity.this.getString(R.string.host) + "/workshop/expert_review.php";
            StringBuilder  stringBuilder = new StringBuilder();

            URL url;
            HttpURLConnection httpURLConnection;

            try {
                url = new URL(urlToHit+"?functionname="+ URLEncoder.encode("make_review", "UTF-8")+ "&user_phone="+URLEncoder.encode(user_id, "UTF-8")
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

            progressReview.setVisibility(View.VISIBLE);

            /*show the progress*/


        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
//            Toast.makeText(RateExpertActivity.this, s, Toast.LENGTH_SHORT).show();
            /*update the new ratings now */
            progressReview.setVisibility(View.GONE);

            RateExpertActivity.this.finish();

            /**/
        }


    }

    private class IsReviewedByMe extends AsyncTask< String, String, String >{

        String review_body,review_stars;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }



        @Override
        protected String doInBackground(String... strings) {

            String urlToHit = RateExpertActivity.this.getString(R.string.host) + "/workshop/expert_review.php";
            StringBuilder stringBuilder = new StringBuilder();

            URL url;
            HttpURLConnection httpURLConnection;

            try {
                url = new URL(urlToHit+ "?functionname="+URLEncoder.encode("is_reviewed_by_me","UTF-8")+ "&expert_id=" + URLEncoder.encode(strings[0],"UTF-8") +  "&user_phone="+URLEncoder.encode(RateExpertActivity.this.getSharedPreferences(MySharedConfig.GuestPrefs.LOCAL_APP_DATA,0).getString("userEmail","null"),"UTF-8"));
                Log.e("reviews",urlToHit+ "?functionname="+URLEncoder.encode("is_reviewed_by_me","UTF-8")+ "&expert_id=" + URLEncoder.encode(strings[0],"UTF-8") +  "&user_phone"+URLEncoder.encode(RateExpertActivity.this.getSharedPreferences(MySharedConfig.GuestPrefs.LOCAL_APP_DATA,0).getString("userEmail","null"),"UTF-8"));
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

            if (!responseFromServer.equals("0") ){  Log.e("reviews","inside yes");  if (review_body != null && review_stars != null ){  Log.e("reviews","inside yes yes " + review_stars); ratingBarForExpert.setRating(Float.valueOf(review_stars)); my_review_text.setText(review_body);    }       }



        }
    }


}
