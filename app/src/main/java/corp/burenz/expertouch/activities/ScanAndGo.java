package corp.burenz.expertouch.activities;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.PointF;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dlazaro66.qrcodereaderview.QRCodeReaderView;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;

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

import corp.burenz.expertouch.util.PointsOverlayView;

public class ScanAndGo extends Activity implements QRCodeReaderView.OnQRCodeReadListener {

    private TextView resultTextView;
    private QRCodeReaderView qrCodeReaderView;
    private PointsOverlayView pointsOverlayView;
    private LinearLayout bottomStyleSheetQR;
    static int doneScanning = 1;
    private TextView companyRecievedFromQR, friendlyQRTV;
    private String finalURL;
    Button subscribeToCompanyBT;
    TextView friendlyStoreMessageTV;
    String channelId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.scan_and_go);
        Typeface logoTypeface               = Typeface.createFromAsset(ScanAndGo.this.getAssets(), "fonts/forte.ttf");

        friendlyStoreMessageTV              = (TextView)            findViewById(R.id.friendlyStoreMessageTV);
        TextView mainAppDemoQR              = (TextView)            findViewById(R.id.mainAppDemoQR);
        final LinearLayout scannerLine      = (LinearLayout)        findViewById(R.id.scanner_line);
        bottomStyleSheetQR                  = (LinearLayout)        findViewById(R.id.bottomStyleSheetQR);
        resultTextView                      = (TextView)            findViewById(R.id.extracted);
        qrCodeReaderView                    = (QRCodeReaderView)    findViewById(R.id.qrdecoderview);
        pointsOverlayView                   = (PointsOverlayView)   findViewById(R.id.points_overlay_view);
        companyRecievedFromQR               = (TextView)            findViewById(R.id.companyRecievedFromQR);
        friendlyQRTV                        = (TextView)            findViewById(R.id.friendlyQRTV);
        subscribeToCompanyBT                = (Button)              findViewById(R.id.subsCribeToCompanyBT);
        animateScanner(scannerLine);






        qrCodeReaderView.setOnQRCodeReadListener(this);
        mainAppDemoQR.setTypeface(logoTypeface);

        // Use this function to enable/disable decoding
        qrCodeReaderView.setQRDecodingEnabled(true);

        // Use this function to change the autofocus interval (default is 5 secs)
        qrCodeReaderView.setAutofocusInterval(2000L);

        // Use this function to enable/disable Torch
//        qrCodeReaderView.setTorchEnabled(true);

        // Use this function to set front camera preview
//        qrCodeReaderView.setFrontCamera();

        // Use this function to set back camera preview
        qrCodeReaderView.setBackCamera();





        subscribeToCompanyBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (subscribeToCompanyBT.getText().toString().contains("Unsubscribe")){
                    try{  new SubscribeFromQR(finalURL.trim().split("betacutoff")[0], "unsub",channelId).execute();
                        FirebaseMessaging.getInstance().unsubscribeFromTopic(channelId);  }catch (Exception e ){e.printStackTrace();}


                }else{
                    try{  new SubscribeFromQR(finalURL.trim().split("betacutoff")[0], "sub",channelId).execute(); FirebaseMessaging.getInstance().subscribeToTopic(channelId);  }catch (Exception e ){e.printStackTrace();}
                }




            }
        });



    }

    // Called when a QR is decoded
    // "text" : the text encoded in QR
    // "points" : points where QR control points are placed in View


    @Override
    public void onBackPressed() {
//        super.onBackPressed();

        if (bottomStyleSheetQR.getVisibility() != View.VISIBLE){
            super.onBackPressed();
            return;
        }


        bottomStyleSheetQR.startAnimation(AnimationUtils.loadAnimation(ScanAndGo.this,R.anim.design_bottom_sheet_slide_out_scan));
        setFriendlyQRVisibility(0);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                bottomStyleSheetQR.setVisibility(View.GONE);
                doneScanning = 1;
            }
        },500);

    }

    @Override
    public void onQRCodeRead(String text, PointF[] points) {
       pointsOverlayView.setVisibility(View.VISIBLE);
        pointsOverlayView.setPoints(points);


        if (doneScanning != 1){return;}
        doneScanning = 2;
        if (text.contains("1clickaway.in") && text.contains("channel_id")){

            channelId  = String.valueOf(text.charAt(88)) ;
           new GetCurrentStatus().execute(channelId);

            finalURL = text;
            try {
                companyRecievedFromQR.setText(text.split("betacutoff")[1]);
                friendlyStoreMessageTV.setText("Awesome! Now don't miss anything from " + text.split("betacutoff")[1]  +  " by getting push notifications on sales and offers");

            }catch (Exception e){companyRecievedFromQR.setText("Store identified");}



            MediaPlayer.create(ScanAndGo.this,R.raw.scan).start();
            setFriendlyQRVisibility(0);
            bottomStyleSheetQR.startAnimation(AnimationUtils.loadAnimation(ScanAndGo.this,R.anim.design_bottom_sheet_slide_in_scan));
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    bottomStyleSheetQR.setVisibility(View.VISIBLE);
                }
            },500);
        /*disblint the second animation*/

        }else {

            setFriendlyQRVisibility(1);


        }






    }


    private void setFriendlyQRVisibility(final int visibility){


        friendlyQRTV.startAnimation(AnimationUtils.loadAnimation(ScanAndGo.this,R.anim.design_bottom_sheet_slide_out_scan));
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                if (visibility == 0){ friendlyQRTV.setVisibility(View.GONE); }else{ friendlyQRTV.setVisibility(View.VISIBLE);}
                friendlyQRTV.setText("No Store or company found in the QR.");
            }
        },500);



    }

    @Override
    protected void onResume() {
        super.onResume();
        qrCodeReaderView.startCamera();
        doneScanning = 1;

    }

    @Override
    protected void onPause() {
        super.onPause();
        qrCodeReaderView.stopCamera();
        doneScanning = 1;
    }



    private  void animateScanner(final LinearLayout scannerLine){


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                scannerLine.startAnimation(AnimationUtils.loadAnimation(ScanAndGo.this,R.anim.design_bottom_sheet_slide_out_scan));
                animateScanner(scannerLine);
                pointsOverlayView.setVisibility(View.GONE);
            }
        },1500);


    }


    private class SubscribeFromQR extends AsyncTask<String, String, String> {


        private Context mContext;
        private String urlFromQRCode;
        String finalURL;
        String flag;
        String channelId;

        SubscribeFromQR( String urlFromQRCode, String flag , String channelId){
            mContext            = ScanAndGo.this;
            this.urlFromQRCode  = urlFromQRCode;
            this.flag           = flag;
            this.channelId      = channelId;
        }



        @Override
        protected String doInBackground(String... strings) {

            String phoneNumber  = "9797080059";

            StringBuilder   stringBuilder       =    new StringBuilder();

//            https://api.qrserver.com/v1/create-qr-code/?color=000000&bgcolor=FFFFFF&data=http%3A%2F%2F1clickaway.in%2Fver1.1%2Fworkshop%2Fsubscription_utils.php%3Ffunctionname%3Dsub%26channel_id&qzone=1&margin=0&size=400x400&ecc=L&format=jpeg&download=1


            HttpURLConnection httpURLConnection ;
            URL url;
            String          urlToHit;


            try {

                if (flag.equals("sub")){
                    urlToHit            =    urlFromQRCode + "&user_phone="+URLEncoder.encode(phoneNumber,"UTF-8");

                }else {
                    urlToHit            =    "http://1clickaway.in/ver1.1/workshop/subscription_utils.php?functionname=unsub&channel_id=" + channelId + "" + "&user_phone="+URLEncoder.encode(phoneNumber,"UTF-8");

                }


                Log.w("urtToHit",urlToHit);
//                Toast.makeText(mContext, "" + urlToHit, Toast.LENGTH_SHORT).show();


                finalURL        = urlToHit;
                url = new URL(urlToHit );
                httpURLConnection =  (HttpURLConnection)  url.openConnection();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
                String line = "";

                while(  (line  =    bufferedReader.readLine()) != null   ){
                    stringBuilder.append(line).append("\n");
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
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            if (flag.equals("sub")){

                if (s.contains("success")){
                    subscribeToCompanyBT.setText("Unsubscribe");


                }else {
                    Toast.makeText(mContext, "We are having trouble connecting to the internet. please try again later", Toast.LENGTH_SHORT).show();
                }

            }else {

                if (s.contains("success")){
                    subscribeToCompanyBT.setText("Subscribe");


                }else {
                    Toast.makeText(mContext, "We are having trouble connecting to the internet. please try again later", Toast.LENGTH_SHORT).show();
                }


            }


            if (finalURL!= null)
                resultTextView.setText(finalURL);





        }
    }

    private class GetCurrentStatus extends AsyncTask<String, String, String>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            subscribeToCompanyBT.setText("Subscribe");
        }

        @Override
        protected String doInBackground(String... strings) {


            String          urlToHit = "http://1clickaway.in/ver1.1/workshop/subscription_utils.php";
            StringBuilder   stringBuilder = new StringBuilder();

            HttpURLConnection httpURLConnection;
            URL               url;


            try {
                url                 =  new URL(urlToHit  + "?functionname=" + URLEncoder.encode("s_status","UTF-8") + "&channel_id="+URLEncoder.encode(strings[0],"UTF-8") + "&user_phone="+URLEncoder.encode("9797080059","UTF-8"));
                Log.e("qr_code",urlToHit  + "?functionname=" + URLEncoder.encode("s_status","UTF-8") + "&channel_id="+URLEncoder.encode(strings[0],"UTF-8") + "&user_phone="+URLEncoder.encode("9797080059","UTF-8"));
                httpURLConnection   =  (HttpURLConnection) url.openConnection();

                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
                String line = "";

                while ( (line = bufferedReader.readLine()) != null ){
                    stringBuilder.append(line).append("\n");
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return stringBuilder.toString();


        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (s.contains("subscribed")){subscribeToCompanyBT.setText("Unsubscribe");}else {subscribeToCompanyBT.setText("Subscribe");}
            Log.e("server out ", s);

        }


    }






}