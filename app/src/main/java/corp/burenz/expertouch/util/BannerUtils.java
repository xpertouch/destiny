package corp.burenz.expertouch.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

import corp.burenz.expertouch.R;
import corp.burenz.expertouch.butter.MySharedConfig;

/**
 * Created by buren on 14/12/17.
 */



public class BannerUtils {

    public  class IsBannerCustom extends AsyncTask<String, String, String>{

        private Context mContext;

        public IsBannerCustom (Context mContext){
            this.mContext = mContext;
            Log.e("Banner","inside isCustomBanner constructor");
        }

        @Override
        protected String doInBackground(String... strings) {

            HttpURLConnection httpURLConnection;
            URL url;


            String urlToHit             =  "http://1clickaway.in/banners/is_custom.php";
            StringBuilder stringBuilder = new StringBuilder();

            try {

                url = new URL(urlToHit);
                httpURLConnection = (HttpURLConnection) url.openConnection();

                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));



                String line = "";

                while ( (line = bufferedReader.readLine() ) != null ){
                    stringBuilder.append(line + "\n");
                }




            } catch (MalformedURLException e) {
                e.printStackTrace();
                Log.e("Banner","Exception in malform");
            } catch (IOException e) {
                e.printStackTrace();
                Log.e("Banner","Exception in IO");

            }catch ( Exception e){
                Log.e("Banner","Exception in general");

            }


            return stringBuilder.toString();
        }


        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            SharedPreferences sharedPreferences = mContext.getSharedPreferences(MySharedConfig.BannerPrefs.BANNER_PREF,0);
            SharedPreferences.Editor editor     = sharedPreferences.edit();
            Log.e("Banner","inside onPostExecute banner custom");

            Log.e("Banner","Response from server = "+s);

            if (s.contains("1")){
                editor.putBoolean(MySharedConfig.BannerPrefs.IS_CUSTOM_BOOLEAN,true);
                editor.apply();
                Log.e("Banner","invoked custom banner here");
                /*if custom banner is on then get the banner list asap */
                new BannerUtils(). new GetBannerURL(mContext).execute();

            }else if (s.contains("0")){

                editor.putBoolean(MySharedConfig.BannerPrefs.IS_CUSTOM_BOOLEAN,false);
                editor.apply();

                Log.e("Banner","deactivated banner");



            }


        }

    }

    private class GetBannerURL extends AsyncTask<String, String, String> {

        private Context mContext;
        private ArrayList<String> bannerList;

        public GetBannerURL(Context mContext) {

            this.mContext = mContext;

        }


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            bannerList = new ArrayList<>();
        }

        @Override
        protected String doInBackground(String... strings) {


            /*get the banners here*/



            HttpURLConnection httpURLConnection;
            URL url;


            StringBuilder stringBuilder = new StringBuilder();
            String urlToHit = "http://1clickaway.in/banners/get_custom_banners.php";


            try {
                url = new URL(urlToHit);
                httpURLConnection = (HttpURLConnection) url.openConnection();

                BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(httpURLConnection.getInputStream()) );

                String line = "";
                while ( (line = bufferedReader.readLine()) != null){
                    stringBuilder.append(line+"\n");
                }

                JSONObject jsonObject;
                JSONArray jsonArray = new JSONArray(stringBuilder.toString());

                for ( int i = 0; i < jsonArray.length(); i++  ){

                    jsonObject = jsonArray.getJSONObject(i);

                    bannerList.add(jsonObject.getString("banners"));

                }







            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }


            return stringBuilder.toString();

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            SharedPreferences sharedPreferences = mContext.getSharedPreferences(MySharedConfig.BannerPrefs.BANNER_PREF, 0);
            SharedPreferences.Editor editor = sharedPreferences.edit();



            try {

                if (bannerList.size() == 6) {
                    editor.putString(MySharedConfig.BannerPrefs.BANNER_URL_1, bannerList.get(0));
                    editor.putString(MySharedConfig.BannerPrefs.BANNER_URL_2, bannerList.get(1));
                    editor.putString(MySharedConfig.BannerPrefs.BANNER_URL_3, bannerList.get(2));
                    editor.putString(MySharedConfig.BannerPrefs.BANNER_URL_4, bannerList.get(3));
                    editor.putString(MySharedConfig.BannerPrefs.BANNER_URL_5, bannerList.get(4));
                    editor.putString(MySharedConfig.BannerPrefs.BANNER_URL_6, bannerList.get(5));

                    editor.apply();

                    Log.e("Banner", "applied urls");

                }

            }catch (Exception e){
                Log.e("ERROR","Exception here in Banner Utils On Post Eexcute");
            }



        }
    }

    public  class BannerMethods {
        private  Context context;


        public BannerMethods (Context context){
            this.context = context;
        }



        public  String getBanner1(){
            SharedPreferences sharedPreferences = context.getSharedPreferences(MySharedConfig.BannerPrefs.BANNER_PREF,0);
            return  sharedPreferences.getString(MySharedConfig.BannerPrefs.BANNER_URL_1,MySharedConfig.BannerPrefs.BANNER_URL_1_DEF);

        }

        public  String getBanner2(){
            SharedPreferences sharedPreferences = context.getSharedPreferences(MySharedConfig.BannerPrefs.BANNER_PREF,0);
            return  sharedPreferences.getString(MySharedConfig.BannerPrefs.BANNER_URL_2,MySharedConfig.BannerPrefs.BANNER_URL_2_DEF);

        }
        public  String getBanner3(){
            SharedPreferences sharedPreferences = context.getSharedPreferences(MySharedConfig.BannerPrefs.BANNER_PREF,0);
            return  sharedPreferences.getString(MySharedConfig.BannerPrefs.BANNER_URL_3,MySharedConfig.BannerPrefs.BANNER_URL_3_DEF);

        }
        public  String getBanner4(){
            SharedPreferences sharedPreferences = context.getSharedPreferences(MySharedConfig.BannerPrefs.BANNER_PREF,0);
            return  sharedPreferences.getString(MySharedConfig.BannerPrefs.BANNER_URL_4,MySharedConfig.BannerPrefs.BANNER_URL_4_DEF);

        }
        public String getBanner5(){
            SharedPreferences sharedPreferences = context.getSharedPreferences(MySharedConfig.BannerPrefs.BANNER_PREF,0);
            return  sharedPreferences.getString(MySharedConfig.BannerPrefs.BANNER_URL_5,MySharedConfig.BannerPrefs.BANNER_URL_5_DEF);

        }
        public String getBanner6(){
            SharedPreferences sharedPreferences = context.getSharedPreferences(MySharedConfig.BannerPrefs.BANNER_PREF,0);
            return  sharedPreferences.getString(MySharedConfig.BannerPrefs.BANNER_URL_6,MySharedConfig.BannerPrefs.BANNER_URL_6_DEF);

        }



    }


}