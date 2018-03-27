package corp.burenz.expertouch.activities;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import net.glxn.qrgen.android.QRCode;

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

/**
 * Created by buren on 3/3/18.
 */

public class MyCompanyQR extends AppCompatActivity {

    ImageView companyQRIV;
    TextView storeNameTV,offerFromStore;
    TextView clickAwayHereTV,clickAwayHereTV2, scanAndSubscribeTV;
    Typeface typeface;

    String storeName;
    String storeID;




    LinearLayout upperKNowHowLL, lowerKnowHowLL;


    @Override
    public void onBackPressed() {


        if (upperKNowHowLL.getVisibility() == View.VISIBLE){

            animateBottomKnowHow(0);
        }else {
            super.onBackPressed();
            overridePendingTransition(R.anim.fadein_scan,R.anim.fadeout_scan);
        }

    }

    private void  animateBottomKnowHow(final int doWhat){



        if (doWhat == 0){

            upperKNowHowLL.startAnimation(AnimationUtils.loadAnimation(MyCompanyQR.this, R.anim.fadeout_scan));
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                    upperKNowHowLL.setVisibility(View.INVISIBLE);

                }
            },500);

            lowerKnowHowLL.startAnimation(AnimationUtils.loadAnimation(MyCompanyQR.this, R.anim.design_bottom_sheet_slide_out_scan));
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    lowerKnowHowLL.setVisibility(View.INVISIBLE);
                }
            },500);


        }else {

            upperKNowHowLL.startAnimation(AnimationUtils.loadAnimation(MyCompanyQR.this, R.anim.fadein_scan));
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                    upperKNowHowLL.setVisibility(View.VISIBLE);

                }
            },500);

            lowerKnowHowLL.startAnimation(AnimationUtils.loadAnimation(MyCompanyQR.this, R.anim.design_bottom_sheet_slide_in_scan));
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    lowerKnowHowLL.setVisibility(View.VISIBLE);
                }
            },500);



        }










    }

    private void inflateLayouts(){

        companyQRIV         = (ImageView)   findViewById(R.id.companyQR);
        storeNameTV         = (TextView)    findViewById(R.id.storeNameTV);
        offerFromStore      = (TextView)    findViewById(R.id.offersFromStore);

        upperKNowHowLL      = (LinearLayout) findViewById(R.id.upperLayerKnowHowLL);
        lowerKnowHowLL      = (LinearLayout) findViewById(R.id.bottomlayerKnowHowLL);


        clickAwayHereTV     = (TextView) findViewById(R.id.clickAwayHereTV);
        clickAwayHereTV2    = (TextView) findViewById(R.id.clickAwayHereTV2);
        scanAndSubscribeTV  = (TextView) findViewById(R.id.scanAndSubscribeTV);

        typeface = Typeface.createFromAsset(this.getAssets(), "fonts/forte.ttf");
        clickAwayHereTV.setTypeface(typeface);
        clickAwayHereTV2.setTypeface(typeface);
        storeNameTV.setTypeface(typeface);
        scanAndSubscribeTV.setTypeface(Typeface.createFromAsset(this.getAssets(), "fonts/roboto.ttf"));
        offerFromStore.setTypeface(Typeface.createFromAsset(this.getAssets(), "fonts/roboto.ttf"));

        storeNameTV.setTextColor(getResources().getColor(R.color.vectorColor));


    }



    private void generateTheQR(){




        String meCardContent    = "http://1clickaway.in/ver1.1/workshop/subscription_utils.php?functionname=sub&channel_id=" + storeID + "betacutoff"+ storeName;
        Bitmap bitMap = Bitmap.createScaledBitmap(QRCode.from(meCardContent).bitmap(),1000,1000,false);

        clickAwayHereTV.setTextSize(30);

        clickAwayHereTV.setText(storeName);

        offerFromStore.setTextColor(Color.BLACK);

        String theOfferLetter  =  " \" Now don't miss any offers from <b>" + storeName +  ". </b>Subscribe to get push notifications on offers. \" ";
        offerFromStore.setTextSize(22);
        offerFromStore.setText(Html.fromHtml(theOfferLetter) );
        companyQRIV.getLayoutParams().width = 1000;
        companyQRIV.getLayoutParams().height = 1000;
        companyQRIV.setAdjustViewBounds(true);
        companyQRIV.setImageBitmap(bitMap);






    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.qr_my_company);

        inflateLayouts();

        try {
            new GETCompanyInformation().execute(new GuestInformation(MyCompanyQR.this).getGuestNumber());
        }catch (Exception e){ e.printStackTrace();
            Toast.makeText(this, "Having trouble connecting to the internet", Toast.LENGTH_SHORT).show(); finish(); }
    }



    private class GETCompanyInformation extends AsyncTask<String , String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();


//            Toast.makeText(MyCompanyQR.this, "getting information about the company", Toast.LENGTH_SHORT).show();
            /*this is where we tell the client that he can now generate the QR*/
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            if (storeID != null && companyQRIV != null){
                generateTheQR();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

//                        animateBottomKnowHow(1);

                    }
                },5000);
            }else {
                Toast.makeText(MyCompanyQR.this, "We are having trouble connecting to the internet", Toast.LENGTH_SHORT).show();
            }



        }

        @Override
        protected String doInBackground(String... strings) {

            String urlToHit = "http://1clickaway.in/ver1.1/workshop/get_company_info_for_qr.php";
            StringBuilder stringBuilder = new StringBuilder();


            URL url;
            HttpURLConnection httpURLConnection;


            try {
                url = new URL(urlToHit + "?assoc_no=" + URLEncoder.encode(strings[0],"UTF-8"));
                httpURLConnection = (HttpURLConnection) url.openConnection();

                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));

                String line = "";
                while ((line  = bufferedReader.readLine()) != null){
                    stringBuilder.append(line).append("\n");
                }


                JSONArray jsonArray = new JSONArray(stringBuilder.toString());
                JSONObject jsonObject = jsonArray.getJSONObject(0);

                storeName = jsonObject.getString("companyTitle");
                storeID   = jsonObject.getString("companyID");


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



    }
}
