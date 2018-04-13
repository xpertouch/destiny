package corp.burenz.expertouch.activities;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
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
import java.util.Calendar;
import java.util.List;

import corp.burenz.expertouch.R;
import corp.burenz.expertouch.adapters.BucketCompanyAdapter;
import corp.burenz.expertouch.adapters.PostsCompany;

import corp.burenz.expertouch.butter.GuestInformation;
import corp.burenz.expertouch.databases.CallLogs;
import corp.burenz.expertouch.util.CallPermissions;
import corp.burenz.expertouch.util.MySingleton;

public class CompanyProfile extends AppCompatActivity implements View.OnClickListener {

    String companyTagLineS, companyAboutS, addVisitS, companyLandmarkS, companyCityS, companyEmailS, companyOppsiteS, companyPhoneS, companyBannerS, companyTitleS, companyStateS,isSubscrribed;

    RecyclerView            companyPostsRv;
    RecyclerView.Adapter    companyPostsAdapter;

    ArrayList<String>       postDateArrayJ,jobPostIdArray, jobInfoArray;

    ArrayList<String>       saleTitleArray, saleDiscriptionArray, postDateArrayB, saleID;

    NetworkImageView        companyPicture;
    RecyclerView            companyBucket;
    RecyclerView.Adapter    bucketAdapter;
    RelativeLayout          bucketProgress;

    TextView                companyName, companyTag, companyAbout, addressLine1, addressOpp, companyState;
    ImageButton             callCompany;
    ImageButton             visitCompany;
    ImageButton             mailCompany;
    LinearLayout            shareDetails;
    TextView                callTV, shareTV, mailTV, visitTV, adddressTV, postsTV;
    TextView                noPostsTitle, noPostsSubtitle, noBucketTitle, noBucketSubtitle;


    String tag, address, Opp, email, call;

    SharedPreferences userData;
    String LOCAL_APP_DATA = "userInformation";

    LinearLayout infoLayout;
    RelativeLayout infoProgress, postsProgress;
    LinearLayout noCompanyPosts, noBucketPosts;


    Button subscribeFromProfle, unsubscribeFromProfile;


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.fadein_scan,R.anim.fadeout_scan);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarCompany);
        setSupportActionBar(toolbar);
        initViews();

        if (getIntent().getExtras() == null) {return;}

        try {new GetCompanyInfo().execute(getIntent().getExtras().getString("companyID", "1"));  } catch (Exception e) {e.printStackTrace();}
        try {new GetCompanyPosts().execute(getIntent().getExtras().getString("companyID", "1")); } catch (Exception e) {e.printStackTrace();}
        try {new GetCompanyBucket().execute(getIntent().getExtras().getString("companyID", "1"));} catch (Exception e) {e.printStackTrace();}


    }

    public void initViews() {


        companyName         = (TextView) findViewById(R.id.companyName);
        companyTag          = (TextView) findViewById(R.id.companyTag);
        companyAbout        = (TextView) findViewById(R.id.aboutCompany);
        addressLine1        = (TextView) findViewById(R.id.addressLine1);
        addressOpp          = (TextView) findViewById(R.id.addressOpp);
        companyState        = (TextView) findViewById(R.id.addressState);
        companyPicture      = (NetworkImageView) findViewById(R.id.companyPicture);
        noBucketTitle       = (TextView) findViewById(R.id.noBucketTitle);
        noBucketSubtitle    = (TextView) findViewById(R.id.noBucketSubtitle);
        noPostsTitle        = (TextView) findViewById(R.id.noPostsTitle);
        noPostsSubtitle     = (TextView) findViewById(R.id.noPostsSubtitle);
        noCompanyPosts      = (LinearLayout) findViewById(R.id.noCompanyPosts);
        noBucketPosts       = (LinearLayout) findViewById(R.id.noCompanyBucket);
        companyBucket       = (RecyclerView) findViewById(R.id.companyBucketRV);
        companyBucket.setNestedScrollingEnabled(false);

        bucketProgress      = (RelativeLayout) findViewById(R.id.bucketProgress);

        subscribeFromProfle = (Button) findViewById(R.id.subscribeFProfile);
        unsubscribeFromProfile = (Button) findViewById(R.id.unsubscribeFProfile);

        subscribeFromProfle.setVisibility(View.VISIBLE);
        unsubscribeFromProfile.setVisibility(View.GONE);

        subscribeFromProfle.setOnClickListener(this);
        unsubscribeFromProfile.setOnClickListener(this);

//        callTV = (TextView) findViewById(R.id.callTV);
//        shareTV = (TextView) findViewById(R.id.shareTV);
        mailTV = (TextView) findViewById(R.id.mailTV);
//        visitTV = (TextView) findViewById(R.id.visitTV);
        adddressTV = (TextView) findViewById(R.id.addressTV);
        postsTV = (TextView) findViewById(R.id.postsTV);
        callCompany     = (ImageButton) findViewById(R.id.callFromProifleIB);
        visitCompany    = (ImageButton) findViewById(R.id.visitFromProfileIB);
        mailCompany     = (ImageButton) findViewById(R.id.mailFromProfileIB);

        shareDetails        = (LinearLayout) findViewById(R.id.shareDetails);
        infoLayout          = (LinearLayout) findViewById(R.id.infoLayout);
        infoProgress        = (RelativeLayout) findViewById(R.id.infoProgress);
        postsProgress       = (RelativeLayout) findViewById(R.id.postsProgress);
        companyPostsRv      = (RecyclerView) findViewById(R.id.companyPostsRV);

        companyPostsRv.setNestedScrollingEnabled(false);
        userData = getSharedPreferences(LOCAL_APP_DATA, 0);


        callCompany.setOnClickListener(this);
        mailCompany.setOnClickListener(this);
        visitCompany.setOnClickListener(this);
        shareDetails.setOnClickListener(this);


    }


    @Override
    public void onClick(View v) {

        userData = getSharedPreferences(LOCAL_APP_DATA, 0);
        String companyID = getIntent().getExtras().getString("companyID","1").trim();


        switch (v.getId()) {


            case R.id.subscribeFProfile:
                try{  new SubscriptionUtilities("sub").execute(companyID);
                    FirebaseMessaging.getInstance().subscribeToTopic(companyID);
                }catch (Exception e){e.printStackTrace();}

                break;



            case R.id.unsubscribeFProfile:
                try{  new SubscriptionUtilities("unsub").execute(companyID);
                    FirebaseMessaging.getInstance().unsubscribeFromTopic(companyID);
                }catch (Exception e){e.printStackTrace();}


                break;

            case R.id.callFromProifleIB:


                final Dialog dialog = new Dialog(CompanyProfile.this);
                Button cancelVerify, callVerify, iUnderStand;


                boolean verificationStatus;
                //setting Dialog View
                verificationStatus = userData.getBoolean("VERIFIED", false);
                if (verificationStatus) {
                    dialog.setContentView(R.layout.verified_user);
                    cancelVerify = (Button) dialog.findViewById(R.id.cancelVerified);
                    callVerify = (Button) dialog.findViewById(R.id.callVerified);

                    callVerify.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {


                            Intent intent = new Intent(Intent.ACTION_CALL);
                            intent.setData(Uri.parse("tel:" + companyPhoneS));

                            Log.e("TAG", "Ready to CAll " + companyTitleS);

                            if (companyPhoneS.length() == 10) {

                                try {
                                    if (ActivityCompat.checkSelfPermission(CompanyProfile.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                                        // TODO: Consider calling
                                        //    ActivityCompat#requestPermissions
                                        // here to request the missing permissions, and then overriding
                                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                        //                                          int[] grantResults)
                                        // to handle the case where the user grants the permission. See the documentation
                                        // for ActivityCompat#requestPermissions for more details.
                                        return;
                                    }
                                    CompanyProfile.this.startActivity(intent);
                                        }catch(Exception e){
                                            Toast.makeText(CompanyProfile.this, "Please wait while we are retrieving the information ", Toast.LENGTH_SHORT).show();

                                        }

                                    }else {
                                        Toast.makeText(CompanyProfile.this, "Please wait while we are retrieving the information ", Toast.LENGTH_SHORT).show();
                                    }


                                    if (ActivityCompat.checkSelfPermission(CompanyProfile.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                                        // TODO: Consider calling
                                        //    ActivityCompat#requestPermissions
                                        // here to request the missing permissions, and then overriding
                                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                        //                                          int[] grantResults)
                                        // to handle the case where the user grants the permission. See the documentation
                                        // for ActivityCompat#requestPermissions for more details.
                                        try{
                                            CallLogs callLogs = new CallLogs(CompanyProfile.this);
                                            callLogs.writer();
                                            callLogs.updateCompanyCall(companyTitleS,java.text.DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime()),call);
                                            callLogs.close();

                                        }catch (Exception e){
                                            Log.e("TAG",companyTitleS+" Not Added" + e.toString());

                                        }
                                        try {
                                            startActivity(new Intent(CompanyProfile.this, CallPermissions.class).putExtra("callIt",call) );

                                        }catch (Exception e){
                                            Toast.makeText(CompanyProfile.this, "Please Make Sure you have given Permissions to this app in Settings", Toast.LENGTH_SHORT).show();

                                        }
                                        return;

                                    }

                                    try{
                                        CallLogs callLogs = new CallLogs(CompanyProfile.this);
                                        callLogs.writer();
                                        callLogs.updateCompanyCall(companyTitleS,java.text.DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime()),call);
                                        callLogs.close();
                                        Log.e("TAG",companyTitleS+" added to call logs");

                                    }catch (Exception e){
                                        Log.e("TAG",companyTitleS+" Not Added" + e.toString());

                                    }

                                    dialog.cancel();
                                }
                            });

                            cancelVerify.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    dialog.cancel();
                                }
                            });




                        } else {
                            dialog.setContentView(R.layout.not_verified_user);
                            iUnderStand = (Button) dialog.findViewById(R.id.iUnderstand);
                            iUnderStand.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    dialog.cancel();
                                }
                            });

                        }



                        dialog.show();





                break;




            case R.id.mailFromProfileIB:
                final Dialog dialog1;

                dialog1 = new Dialog(CompanyProfile.this);
                verificationStatus = userData.getBoolean("VERIFIED",false);
                if (verificationStatus){

                    Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                            "mailto",companyEmailS, null));
                    emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Enter email subject here...");
                    emailIntent.putExtra(Intent.EXTRA_TEXT, "Enter Your feedback or query here...");
                    CompanyProfile.this.startActivity(Intent.createChooser(emailIntent, "Send an Email via..."));




                } else { dialog1.setContentView(R.layout.not_verified_user);
                    iUnderStand = (Button) dialog1.findViewById(R.id.iUnderstand);
                    iUnderStand.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog1.cancel();
                        }
                    });

                    dialog1.show();
                }




                break;






            case R.id.visitFromProfileIB:


                final Dialog dialog2 = new Dialog(CompanyProfile.this);
                verificationStatus = userData.getBoolean("VERIFIED",false);

                if (verificationStatus){

                    ConnectivityManager cManager = (ConnectivityManager) CompanyProfile.this.getSystemService(CONNECTIVITY_SERVICE);
                    NetworkInfo nInfo = cManager.getActiveNetworkInfo();

                    if(nInfo!=null && nInfo.isConnected()) {
//
//                        String url = addVisitArray.get(0).toString();
//                        Log.e("MYURL",""+addVisitArray.get(0).toString());
//                        Uri uri = Uri.parse(url);
//                        Intent intent = new Intent();
//                        intent.setData(uri);
//                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                        startActivity(intent);

                        // Intent i = new Intent(Intent.ACTION_VIEW);
                        // i.setData(Uri.parse(addVisitArray.get(0).toString()));
                        // startActivity(i);


                         String url = "http://"+addVisitS;
                        Intent i = new Intent(Intent.ACTION_VIEW);
                        i.setData(Uri.parse(url));


                        if (addVisitS.contains("please update your website here")){
                            Toast.makeText(CompanyProfile.this, "Website Not Updated Yet", Toast.LENGTH_SHORT).show();
                        }else {
                            startActivity(i);
                        }

                    }
                    else {
                        Snackbar.make(v,"No Internet Connection",Snackbar.LENGTH_SHORT).show();
                    }


                }else {
                    dialog2.setContentView(R.layout.not_verified_user);
                    iUnderStand = (Button) dialog2.findViewById(R.id.iUnderstand);
                    iUnderStand.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog2.cancel();
                        }
                    });

                    dialog2.show();
                }


                break;






            case R.id.shareDetails:

                final Dialog dialog3 = new Dialog(CompanyProfile.this);
                verificationStatus = userData.getBoolean("VERIFIED",false);
                if (verificationStatus){


                    String advert = "Shared via 1clickAway, Find Best Jobs, Experts and Offers from your City and State. Click on the below link to Download Now\n" +
                            "https://play.google.com/store/apps/details?id=corp.burenz.expertouch";
                    String firstString = "Hey there i recommend you to check out "+companyName.getText().toString()+"\n Contact information \n Email : "+companyEmailS +" \n Phone : "+companyPhoneS+"";
                    Intent shareIntent = new Intent();
                    shareIntent.setAction(Intent.ACTION_SEND);
                    shareIntent.setType("text/plain");
                    shareIntent.putExtra(Intent.EXTRA_TEXT, "" + firstString + "\n\n" + advert);
                    CompanyProfile.this.startActivity(Intent.createChooser(shareIntent, "Share via..."));




                } else { dialog3.setContentView(R.layout.not_verified_user);
                    iUnderStand = (Button) dialog3.findViewById(R.id.iUnderstand);
                    iUnderStand.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog3.cancel();
                        }
                    });

                    dialog3.show();
                }

                break;






        }


    }

    private  class GetCompanyInfo extends AsyncTask<String,String,String>{





        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            /* to prevent null pointer exception on clicks*/
            companyPhoneS           = "0000000000";
            companyEmailS           = "mail.1clickaway@gmail.com";
            addVisitS               = "http://1clickaway.in";

        }


        @Override
        protected String doInBackground(String... params) {

            StringBuilder builder               = new StringBuilder();

            try {

                HttpURLConnection httpURLConnection;
                URL url;

                url = new URL( getString(R.string.host)+"/profile/get_company_info_from_id.php?company_id=" + URLEncoder.encode(params[0],"UTF-8") +"&user_phone=" +new GuestInformation(CompanyProfile.this).getGuestNumber());
                httpURLConnection = (HttpURLConnection) url.openConnection();

                BufferedReader  bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
                String str = "";

                while ((str = bufferedReader.readLine()) != null){
                    builder.append(str);
                }


                JSONObject jsonObject = new JSONArray(builder.toString()).getJSONObject(0);

                isSubscrribed          =  jsonObject.getString("isSubscribed");

                companyBannerS          = jsonObject.getString("companyBanner");

                companyTitleS           = jsonObject.getString("companyTitle");
                companyTagLineS         = jsonObject.getString("companyTag");

                companyPhoneS           = jsonObject.getString("companyPhone");
                companyEmailS           = jsonObject.getString("companyEmail");
                addVisitS               = jsonObject.getString("companyVisit");

                companyAboutS           = jsonObject.getString("companyDiscription");

                companyCityS            = jsonObject.getString("companyCity");
                companyLandmarkS        = jsonObject.getString("companyLandmark");
                companyStateS           = jsonObject.getString("companyState");







            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }catch (Exception e ){

            }


            return builder.toString();





        }


        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);



            if(companyTagLineS == null||  companyAboutS == null || companyLandmarkS == null || companyCityS == null || addVisitS == null){

                Toast.makeText(CompanyProfile.this, "Connection too slow, Please Connect to networks and try again " + s, Toast.LENGTH_SHORT).show();
                 finish();
                 return;
            }


            ImageLoader imageLoader = MySingleton.getInstance(CompanyProfile.this).getImageLoader();
            companyPicture.setImageUrl((String) companyBannerS, imageLoader);

            companyName .setText(companyTitleS);
            companyTag  .setText(companyTagLineS.toLowerCase());


            companyAbout.setText(companyAboutS);

            addressLine1.setText(companyCityS);
            addressOpp  .setText(companyLandmarkS);
            companyState.setText(companyStateS);

            if ( isSubscrribed.contains("subscribed") ){
                subscribeFromProfle.setVisibility(View.GONE);
                unsubscribeFromProfile.setVisibility(View.VISIBLE);
            }else{
                subscribeFromProfle.setVisibility(View.VISIBLE);
                unsubscribeFromProfile.setVisibility(View.GONE);
            }






        }


    }

    private   class GetCompanyPosts extends AsyncTask< String, String, String>{




        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            companyPostsRv.setLayoutManager(new LinearLayoutManager(CompanyProfile.this));

            jobInfoArray            = new ArrayList<>();
            postDateArrayJ          = new ArrayList<>();
            jobPostIdArray          = new ArrayList<>();

            postsProgress.setVisibility(View.VISIBLE);
            companyPostsRv.setVisibility(View.GONE);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            Log.e("post_C",s);



            Log.e("myPosts", " Size of job posts " + jobPostIdArray.size());


            if (jobPostIdArray.size() == 0){
                noCompanyPosts.startAnimation(AnimationUtils.loadAnimation(CompanyProfile.this,R.anim.card_animation));
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        noCompanyPosts.setVisibility(View.VISIBLE);

                    }
                },500);
                postsProgress.setVisibility(View.GONE);
            }else if (jobPostIdArray.size() > 0 ){
                companyPostsAdapter = new PostsCompany(CompanyProfile.this,postDateArrayJ,jobInfoArray, jobPostIdArray);

                companyPostsRv.setAdapter(companyPostsAdapter);
                postsProgress.setVisibility(View.GONE);
                companyPostsRv.setVisibility(View.VISIBLE);
            }else {

                noPostsTitle.setText("Connection Slow");
                noPostsSubtitle.setText("We are having problem connecting to Our Server, Tay Again Later");

                noCompanyPosts.startAnimation(AnimationUtils.loadAnimation(CompanyProfile.this,R.anim.card_animation));
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        noCompanyPosts.setVisibility(View.VISIBLE);

                    }
                },500);


            }


        }

        @Override
        protected String doInBackground(String... params) {

            JSONObject  jsonObject;
            JSONArray   jsonArray;

            StringBuilder builder = new StringBuilder();
            List<NameValuePair> nameValuePairs = new ArrayList<>();
            nameValuePairs.add(new BasicNameValuePair("functionname","job_company_id"));
            nameValuePairs.add(new BasicNameValuePair("company_id",params[0]));


            try {

                HttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost(getString(R.string.host)+"/profile/get_company_posts_from.php");
                httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                HttpResponse httpResponse = (HttpResponse) httpClient.execute(httpPost);


                HttpURLConnection httpURLConnection;

                HttpEntity httpEntity = (HttpEntity)httpResponse.getEntity();

                BufferedReader  bufferedReader = new BufferedReader(new InputStreamReader(httpEntity.getContent()));
                String str = "";



                while ( (str = bufferedReader.readLine()) != null){
                    builder.append(str);
                }

                jsonArray = new JSONArray(builder.toString());


                for (int i = 0; i < jsonArray.length(); i++){
                    jsonObject = jsonArray.getJSONObject(i);
                    postDateArrayJ.add(jsonObject.getString("postDate"));
                    jobInfoArray.add(jsonObject.getString("jobInfo"));
                    jobPostIdArray.add(jsonObject.getString("postId"));

                }


            }catch (Exception e){
                e.printStackTrace();
                Log.e("myPosts","Exception raised "+ e.toString());
            }


            return builder.toString();

        }



    }

    private   class GetCompanyBucket extends AsyncTask< String, String, String>{


        JSONObject jsonObject;
        JSONArray jsonArray;


        StringBuilder builder = new StringBuilder();
        BufferedReader bufferedReader;
        List<NameValuePair> nameValuePairs = new ArrayList<>();


        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            saleTitleArray          = new ArrayList<String>();
            saleDiscriptionArray    = new ArrayList<String>();
            postDateArrayB          = new ArrayList<String>();
            saleID                  = new ArrayList<String>();

            companyBucket.setLayoutManager(new LinearLayoutManager(CompanyProfile.this));
            bucketProgress.setVisibility(View.VISIBLE);
            companyBucket.setVisibility(View.GONE);



        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);


            Log.e("myPosts", " Size of saleTitleArray" + saleTitleArray.size());

            if (saleTitleArray.size() == 0){
                noBucketPosts.startAnimation(AnimationUtils.loadAnimation(CompanyProfile.this,R.anim.card_animation));
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        noBucketPosts.setVisibility(View.VISIBLE);
                    }
                },500);
                bucketProgress.setVisibility(View.GONE);

            }else if (saleTitleArray.size() > 0 ){

                bucketAdapter = new BucketCompanyAdapter(saleTitleArray,saleDiscriptionArray,postDateArrayB,saleID);
                companyBucket.setAdapter(bucketAdapter);
                bucketProgress.setVisibility(View.GONE);
                companyBucket.setVisibility(View.VISIBLE);

            }else {

                noBucketTitle.setText("Connection Slow");
                noBucketSubtitle.setText("We are having problem connecting to Our Server, Tay Again Later");
                noBucketPosts.startAnimation(AnimationUtils.loadAnimation(CompanyProfile.this,R.anim.card_animation));
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        noBucketPosts.setVisibility(View.VISIBLE);

                    }
                },500);


            }


        }

        @Override
        protected String doInBackground(String... params) {

            nameValuePairs.add(new BasicNameValuePair("functionname","bucket_company_id"));
            nameValuePairs.add(new BasicNameValuePair("company_id",params[0]));

            try {

                HttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost(getString(R.string.host)+"/profile/get_company_posts_from.php");
                httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                HttpResponse httpResponse = (HttpResponse) httpClient.execute(httpPost);

                HttpEntity httpEntity = (HttpEntity)httpResponse.getEntity();

                bufferedReader = new BufferedReader(new InputStreamReader(httpEntity.getContent()));
                String str = "";


                while ( (str = bufferedReader.readLine()) != null){
                    builder.append(str);
                }
                jsonObject = new JSONObject();
                jsonArray = new JSONArray(builder.toString());


                int length = jsonArray.length();

                for (int i = 0; i < length; i++){

                    jsonObject = jsonArray.getJSONObject(i);

                    saleTitleArray.add(jsonObject.getString("saleTitle"));
                    saleDiscriptionArray.add(jsonObject.getString("saleDiscription"));
                    postDateArrayB.add(jsonObject.getString("saleDate"));
                    saleID.add(jsonObject.getString("saleId"));

                }





            }catch (Exception e){
                Log.e("myposts","Exception here " + e.toString());

            }


            return builder.toString();

        }



    }


    private    class SubscriptionUtilities extends AsyncTask< String, String, String >{
        /*sub*/
        String flag;

        public SubscriptionUtilities(String flag) {
            this.flag = flag;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            if (flag.equals("sub")){
                unsubscribeFromProfile.setVisibility(View.VISIBLE);
                subscribeFromProfle.setVisibility(View.GONE);
            }else {

                unsubscribeFromProfile.setVisibility(View.GONE);
                subscribeFromProfle.setVisibility(View.VISIBLE);

            }

        }



        @Override
        protected String doInBackground(String... strings) {

          /*  strings[0]  = whether subscribe or unsubscribe
            strings[1]  = companys id      */

            String          urlToHit            =   getString(R.string.host) + "/workshop/subscription_utils.php";
            StringBuilder   stringBuilder       =   new StringBuilder();


            HttpURLConnection httpURLConnection ;
            URL url;


            try {


                switch (flag.trim()){

                    case "sub":
                        url = new URL(urlToHit + "?functionname=" + URLEncoder.encode(flag, "UTF-8") + "&user_phone="+ URLEncoder.encode(new GuestInformation(CompanyProfile.this).getGuestNumber(), "UTF-8") +"&channel_id=" + URLEncoder.encode(strings[0],"UTF-8") );
                        Log.e("channel",urlToHit + "?functionname=" + URLEncoder.encode(flag, "UTF-8") + "&user_phone="+ URLEncoder.encode(new GuestInformation(CompanyProfile.this).getGuestNumber(), "UTF-8") +"&channel_id=" + URLEncoder.encode(strings[0],"UTF-8") );
                        break;

                    case "unsub":
                        url = new URL(urlToHit + "?functionname=" + URLEncoder.encode(flag, "UTF-8") + "&user_phone="+ URLEncoder.encode(new GuestInformation(CompanyProfile.this).getGuestNumber(), "UTF-8") +"&channel_id=" + URLEncoder.encode(strings[0],"UTF-8") );
                        Log.e("channel",urlToHit + "?functionname=" + URLEncoder.encode(flag, "UTF-8") + "&user_phone="+ URLEncoder.encode(new GuestInformation(CompanyProfile.this).getGuestNumber(), "UTF-8") +"&channel_id=" + URLEncoder.encode(strings[0],"UTF-8") );
                        break;

                    default:
                        url = new URL(urlToHit + "?functionname=" + URLEncoder.encode(flag, "UTF-8") + "&user_phone="+ URLEncoder.encode(new GuestInformation(CompanyProfile.this).getGuestNumber(), "UTF-8") +"&channel_id=" + URLEncoder.encode(strings[0],"UTF-8") );
                        Log.e("channel",urlToHit + "?functionname=" + URLEncoder.encode(flag, "UTF-8") + "&user_phone="+ URLEncoder.encode(new GuestInformation(CompanyProfile.this).getGuestNumber(), "UTF-8") +"&channel_id=" + URLEncoder.encode(strings[0],"UTF-8") );

                }






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
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            switch (s.trim()){

                case "success":
                    break;

                case "failure":

                    if (flag.equals("sub")){
                        unsubscribeFromProfile.setVisibility(View.GONE);
                        subscribeFromProfle.setVisibility(View.VISIBLE);
                    }else {
                        unsubscribeFromProfile.setVisibility(View.VISIBLE);
                        subscribeFromProfle.setVisibility(View.GONE);
                    }

                    break;











            }


        }



    }

}
