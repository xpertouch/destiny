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
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import corp.burenz.expertouch.R;
import corp.burenz.expertouch.adapters.BucketCompanyAdapter;
import corp.burenz.expertouch.adapters.PostsCompany;

import corp.burenz.expertouch.databases.CallLogs;
import corp.burenz.expertouch.util.CallPermissions;
import corp.burenz.expertouch.util.MySingleton;

public class CompanyProfile extends AppCompatActivity implements View.OnClickListener{


    RecyclerView companyPostsRv;
    RecyclerView.Adapter companyPostsAdapter;

    ArrayList<String> postsCompany;
    ArrayList<String> postDate;
    ArrayList<String> tagLine;
    ArrayList<String> companyLandmark;
    ArrayList<String> aboutCompany;
    ArrayList<String> companyCity;
    ArrayList<String> addVisitArray;
    ArrayList<String> companyPhone,companyEmail;
    ArrayList<String> saleTitleArray,saleDiscriptionArray,postDateArray,saleID;
    NetworkImageView companyPicture;
    RecyclerView companyBucket;
    RecyclerView.Adapter bucketAdapter;
    RelativeLayout bucketProgress;

    TextView companyName,companyTag,companyAbout,addressLine1,addressOpp,companyState;
    LinearLayout callCompany,visitCompany,mailCompany,shareDetails;
    TextView callTV,shareTV,mailTV,visitTV,adddressTV,postsTV;

    TextView noPostsTitle,noPostsSubtitle,noBucketTitle,noBucketSubtitle;


    String company,tag,address,Opp,state,email,call,picture;

    SharedPreferences userData;
    String LOCAL_APP_DATA = "userInformation";

    LinearLayout infoLayout;
    RelativeLayout infoProgress,postsProgress;
    Bundle bundle;

    LinearLayout noCompanyPosts,noBucketPosts;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarCompany);
        setSupportActionBar(toolbar);




        bundle = getIntent().getExtras();

        if (bundle == null){

            return;

        }

        initViews();
        getExtras();
        settingViews();
        onClickListners();
        ImageLoader imageLoader = MySingleton.getInstance(CompanyProfile.this).getImageLoader();
        companyPicture.setImageUrl((String) picture, imageLoader);


        new GetCompanyInfo().execute();
        new GetCompanyPosts().execute();
        new GetCompanyBucket().execute();


    }

    public  void initViews(){


        companyName = (TextView)findViewById(R.id.companyName);
        companyTag = (TextView)findViewById(R.id.companyTag);
        companyAbout = (TextView)findViewById(R.id.aboutCompany);
        addressLine1 = (TextView)findViewById(R.id.addressLine1);
        addressOpp = (TextView)findViewById(R.id.addressOpp);
        companyState = (TextView)findViewById(R.id.addressState);
        companyPicture = (NetworkImageView) findViewById(R.id.companyPicture);
        noBucketTitle = (TextView) findViewById(R.id.noBucketTitle);
        noBucketSubtitle = (TextView) findViewById(R.id.noBucketSubtitle);
        noPostsTitle = (TextView) findViewById(R.id.noPostsTitle);
        noPostsSubtitle = (TextView) findViewById(R.id.noPostsSubtitle);
        noCompanyPosts = (LinearLayout) findViewById(R.id.noCompanyPosts);
        noBucketPosts = (LinearLayout) findViewById(R.id.noCompanyBucket);
        companyBucket = (RecyclerView) findViewById(R.id.companyBucketRV);
        bucketProgress = (RelativeLayout) findViewById(R.id.bucketProgress);

        callTV = (TextView)findViewById(R.id.callTV);
        shareTV = (TextView)findViewById(R.id.shareTV);
        mailTV = (TextView)findViewById(R.id.mailTV);
        visitTV = (TextView)findViewById(R.id.visitTV);
        adddressTV = (TextView)findViewById(R.id.addressTV);
        postsTV = (TextView)findViewById(R.id.postsTV);
        callCompany = (LinearLayout)findViewById(R.id.callCompany);
        visitCompany = (LinearLayout)findViewById(R.id.visitCompany);
        mailCompany = (LinearLayout)findViewById(R.id.mailCompany);
        shareDetails = (LinearLayout)findViewById(R.id.shareDetails);
        infoLayout = (LinearLayout)findViewById(R.id.infoLayout);
        infoProgress = (RelativeLayout)findViewById(R.id.infoProgress);
        postsProgress = (RelativeLayout)findViewById(R.id.postsProgress);
        companyPostsRv = (RecyclerView)findViewById(R.id.companyPostsRV);


    }

    public void settingViews(){


        userData = getSharedPreferences(LOCAL_APP_DATA,0);
        companyName.setText(company);



    }

    public void getExtras(){

        company = bundle.getString("companyName");
        picture = bundle.getString("companyBanner");
        state  = bundle.getString("companyState");

    }

    public void onClickListners(){

        callCompany.setOnClickListener(this);
        mailCompany.setOnClickListener(this);
        visitCompany.setOnClickListener(this);
        shareDetails.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        userData = getSharedPreferences(LOCAL_APP_DATA,0);


        switch (v.getId()){


            case R.id.callCompany:



                final Dialog dialog = new Dialog(CompanyProfile.this);
                    Button cancelVerify, callVerify, iUnderStand;


                        boolean verificationStatus;
                        //setting Dialog View
                        verificationStatus = userData.getBoolean("VERIFIED", false);
                        if (verificationStatus) {
                            dialog.setContentView(R.layout.verified_user);
                            cancelVerify = (Button) dialog.findViewById(R.id.cancelVerified);
                            callVerify = (Button)dialog.findViewById(R.id.callVerified);

                            callVerify.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {


                                    Intent intent = new Intent(Intent.ACTION_CALL);
                                    intent.setData(Uri.parse("tel:" + companyPhone.get(0)));

                                    Log.e("TAG","Ready to CAll "+company);

                                    if (companyPhone.get(0).length() == 10){

                                        try{
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
                                            callLogs.updateCompanyCall(company,java.text.DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime()),call);
                                            callLogs.close();

                                        }catch (Exception e){
                                            Log.e("TAG",company+" Not Added" + e.toString());

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
                                        callLogs.updateCompanyCall(company,java.text.DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime()),call);
                                        callLogs.close();
                                        Log.e("TAG",company+" added to call logs");

                                    }catch (Exception e){
                                        Log.e("TAG",company+" Not Added" + e.toString());

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




            case R.id.mailCompany:
                final Dialog dialog1;

                dialog1 = new Dialog(CompanyProfile.this);
                verificationStatus = userData.getBoolean("VERIFIED",false);
                if (verificationStatus){

                    Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                            "mailto",companyEmail.get(0), null));
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






            case R.id.visitCompany:


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


                         String url = "http://"+addVisitArray.get(0);
                        Intent i = new Intent(Intent.ACTION_VIEW);
                        i.setData(Uri.parse(url));


                        if (addVisitArray.contains("please update your website here")){
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
                    String firstString = "Hey there i recommend you to check out "+companyName.getText().toString()+"\n Contact information \n Email : "+companyEmail.get(0)+" \n Phone : "+companyPhone.get(0)+"";
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

    private   class GetCompanyInfo extends AsyncTask<String,String,String>{


        JSONObject jsonObject;
        JSONArray jsonArray;

        StringBuilder builder = new StringBuilder();
        BufferedReader bufferedReader;

        List<NameValuePair> nameValuePairs = new ArrayList<>();


        @Override
        protected void onPreExecute() {
            super.onPreExecute();

//            infoProgress.setVisibility(View.VISIBLE);
//            infoLayout.setVisibility(View.GONE);

            postsCompany = new ArrayList<>();
            postDate =  new  ArrayList<>();
            tagLine = new ArrayList<>();;
            companyLandmark = new ArrayList<>();;
            addVisitArray = new ArrayList<>();
            companyCity = new ArrayList<>();
            aboutCompany = new ArrayList<>();
            companyPhone = new ArrayList<>();
            companyEmail  = new ArrayList<>();






        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);




            if(tagLine.size() == 0 ||  aboutCompany.size() == 0 || companyLandmark.size() == 0 || companyCity.size() == 0 || addVisitArray.size() ==0){

                Toast.makeText(CompanyProfile.this, "Connection Slow, Please Connect to networks and try again", Toast.LENGTH_SHORT).show();
                tagLine.add("----");
                aboutCompany.add("----");
                companyLandmark.add("----");
                companyCity.add("-----");
                addVisitArray.add("-----");
            }

            companyTag.setText(tagLine.get(0).toString().toLowerCase());
            companyAbout.setText(aboutCompany.get(0).toString());
            addressLine1.setText(companyCity.get(0).toString());
            addressOpp.setText(companyLandmark.get(0).toString());
            companyState.setText(state);



        }

        @Override
        protected String doInBackground(String... params) {

            nameValuePairs.add(new BasicNameValuePair("companyTitle",company));

            try {

            HttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(getString(R.string.host)+"/profile/company_profiles.php");
            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            HttpResponse httpResponse = (HttpResponse) httpClient.execute(httpPost);
            HttpEntity  httpEntity = (HttpEntity) httpResponse.getEntity();


            bufferedReader = new BufferedReader(new InputStreamReader(httpEntity.getContent()));
            String str = "";

            while ((str = bufferedReader.readLine()) != null){
                builder.append(str);
            }


            jsonObject = new JSONObject();
            jsonArray = new JSONArray(builder.toString());

            int len = jsonArray.length();

            for(int u = 0; u < len; u++){

                jsonObject = jsonArray.getJSONObject(u);
                tagLine.add(jsonObject.getString("companyTag"));
                aboutCompany.add(jsonObject.getString("aboutCompany"));
                companyLandmark.add(jsonObject.getString("companyLandmark"));
                companyCity.add(jsonObject.getString("companyCity"));
                addVisitArray.add(jsonObject.getString("companyVisit"));
                companyEmail.add(jsonObject.getString("companyEmail"));
                companyPhone.add(jsonObject.getString("companyPhone"));

            }

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







    }
    private   class GetCompanyPosts extends AsyncTask< String, String, String>{


        Context context;
        JSONObject jsonObject;
        JSONArray jsonArray;


        StringBuilder builder = new StringBuilder();
        BufferedReader bufferedReader;
        List<NameValuePair> nameValuePairs = new ArrayList<>();


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            companyPostsRv.setLayoutManager(new LinearLayoutManager(CompanyProfile.this));
            postsCompany = new ArrayList<>();
            postDate = new ArrayList<>();

            postsProgress.setVisibility(View.VISIBLE);
            companyPostsRv.setVisibility(View.GONE);

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            if (aboutCompany.size() > 0){

                if (postsCompany.size() == 0){
                    noCompanyPosts.startAnimation(AnimationUtils.loadAnimation(CompanyProfile.this,R.anim.card_animation));
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                           noCompanyPosts.setVisibility(View.VISIBLE);

                        }
                    },500);
                    postsProgress.setVisibility(View.GONE);
                }else if (postsCompany.size() > 0 ){
                    companyPostsAdapter = new PostsCompany(CompanyProfile.this,postDate,postsCompany);
                    companyPostsRv.setAdapter(companyPostsAdapter);
                    postsProgress.setVisibility(View.GONE);
                    companyPostsRv.setVisibility(View.VISIBLE);
                }

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

            nameValuePairs.add(new BasicNameValuePair("companyTitle",company));

            try {

                HttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost(getString(R.string.host)+"/profile/get_company_posts.php");
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

                    postDate.add(jsonObject.getString("postDate"));
                    postsCompany.add(jsonObject.getString("jobInfo"));


                }


            }catch (Exception e){

            }


            return builder.toString();

        }



    }
    private   class GetCompanyBucket extends AsyncTask< String, String, String>{


        Context context;
        JSONObject jsonObject;
        JSONArray jsonArray;


        StringBuilder builder = new StringBuilder();
        BufferedReader bufferedReader;
        List<NameValuePair> nameValuePairs = new ArrayList<>();


        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            saleTitleArray = new ArrayList<String>();
            saleDiscriptionArray = new ArrayList<String>();
            postDateArray = new ArrayList<String>();
            saleID = new ArrayList<String>();
            companyBucket.setLayoutManager(new LinearLayoutManager(CompanyProfile.this));
            bucketProgress.setVisibility(View.VISIBLE);
            companyBucket.setVisibility(View.GONE);



        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);



            if (aboutCompany.size() > 0){

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

                    bucketAdapter = new BucketCompanyAdapter(saleTitleArray,saleDiscriptionArray,postDateArray,saleID);
                    companyBucket.setAdapter(bucketAdapter);
                    bucketProgress.setVisibility(View.GONE);
                    companyBucket.setVisibility(View.VISIBLE);

                }

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

            nameValuePairs.add(new BasicNameValuePair("companyTitle",company));

            try {

                HttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost(getString(R.string.host)+"/bucket/get_company_sales.php");
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
                    postDateArray.add(jsonObject.getString("saleDate"));
                    saleID.add(jsonObject.getString("saleId"));
                }





            }catch (Exception e){

            }


            return builder.toString();

        }



    }

}
