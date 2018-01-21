package corp.burenz.expertouch.util;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
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
import corp.burenz.expertouch.adapters.ExpertReviewAdapter;
import corp.burenz.expertouch.butter.GuestInformation;
import corp.burenz.expertouch.butter.MySharedConfig;

/**
 * Created by buren on 19/1/18.
 */

public class ExpertReviews {

    private Context mContext;

    public       ExpertReviews(Context mContext){
        this.mContext = mContext;

    }

    public class IsReviewedByMe         extends AsyncTask< String, String, String >{

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
                url = new URL(urlToHit+ "?functionname="+URLEncoder.encode("is_reviewed","UTF-8")+ "&my_number"+URLEncoder.encode(mContext.getSharedPreferences(MySharedConfig.GuestPrefs.LOCAL_APP_DATA,0).getString("userEmail","null"),"UTF-8"));
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

                        case "-1":
                            /*show the bar that allows it to submit a review to the expert*/
                            break;

                        case "1":
                            /*hide the bar and show his own review that he has posted already*/
                            break;

            }


            doToast("Just completed the on Post Execute");

        }
    }

    public class MakeAReview            extends AsyncTask< String, String, String >{


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
                url = new URL(urlToHit+"?functionname="+URLEncoder.encode("make_review", "UTF-8")+ "&user_id="+URLEncoder.encode(user_id, "UTF-8")
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

    public class GetIndividualRatings   extends AsyncTask< String, String, String >{

        private class RatingsObtained {




            private String five_stars, four_stars, three_stars, two_stars, one_stars;

            RatingsObtained(String five_stars, String four_stars, String three_stars, String two_stars, String one_stars){

                this.five_stars     = five_stars;
                this.four_stars     = four_stars;
                this.three_stars    = three_stars;
                this.two_stars      = two_stars;
                this.one_stars      = one_stars;
            }

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



        }
        ArrayList<String> ratingList = new ArrayList<>();

        @Override
        protected String doInBackground(String... strings) {
            /*pass the expert id in the 0th position in the execution*/

            String urlTohit = mContext.getString(R.string.app_name) +  "workshop/expert_reviews.php";
            StringBuilder stringBuilder = new StringBuilder();

            URL url;
            HttpURLConnection httpURLConnection;


            try {
                url = new URL(urlTohit + "?functionname=" + URLEncoder.encode("get_indi", "UTF-8")+ "&expert_id=" + URLEncoder.encode(strings[0], "UTF-8"));

                httpURLConnection = (HttpURLConnection) url.openConnection();


                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
                String line = "";

                while ( (line = bufferedReader.readLine()) != null ){
                    stringBuilder.append(line).append("\n");
                }

                JSONArray jsonArray = new JSONArray(stringBuilder.toString());
                JSONObject jsonObject;

                jsonObject = jsonArray.getJSONObject(0);
                RatingsObtained ratingsObtained = new RatingsObtained(jsonObject.getString("five_stars"),jsonObject.getString("four_stars"),
                        jsonObject.getString("three_stars"),jsonObject.getString("two_stars"),jsonObject.getString("one_stars"));

                ratingList.add(ratingsObtained.getFive_stars());
                ratingList.add(ratingsObtained.getFour_stars());
                ratingList.add(ratingsObtained.getThree_stars());
                ratingList.add(ratingsObtained.getTwo_stars());
                ratingList.add(ratingsObtained.getOne_stars());




            } catch (IOException | JSONException e) {
                e.printStackTrace();
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
            Log.e("reviews", "inside post execute get indi reviews ");

        }
    }

    private void doToast(String s){
        Toast.makeText(mContext, s, Toast.LENGTH_SHORT).show();

    }


    public class GetExpertReviews  extends AsyncTask< String, String, String>{

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
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            ExpertReviewAdapter expertReviewAdapter = new ExpertReviewAdapter(mContext,review_user_pic,review_user_name,review_date,review_stars,review_body,review_user_title);

            /*initialize the adapter*/
        }


        @Override
        protected String doInBackground(String... strings) {


            String urlTohit             = mContext.getString(R.string.host) + "workshop/expert_reviews.php";
            StringBuilder stringBuilder = new StringBuilder();

            URL url;
            HttpURLConnection httpURLConnection;


            try {
                url = new URL(urlTohit + "?functionname=" + URLEncoder.encode("get_3", "UTF-8"));
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

            review_user_pic     .add(jsonObject.getString("review_user_pic"));
            review_user_name    .add(jsonObject.getString("review_user_name"));
            review_date         .add(jsonObject.getString("review_date"));
            review_stars        .add(jsonObject.getString("review_stars"));
            review_body         .add(jsonObject.getString("review_body"));
            review_user_title   .add(jsonObject.getString("review_user_title"));

        }


    }

}
