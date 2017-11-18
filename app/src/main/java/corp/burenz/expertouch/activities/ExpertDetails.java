package corp.burenz.expertouch.activities;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.LayoutInflaterCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.mikepenz.iconics.context.IconicsLayoutInflater;

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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import corp.burenz.expertouch.R;
import corp.burenz.expertouch.adapters.SkillsAdapter;
import corp.burenz.expertouch.databases.CallLogs;
import corp.burenz.expertouch.util.CallPermissions;
import corp.burenz.expertouch.util.MySingleton;

public class ExpertDetails extends AppCompatActivity {


    SharedPreferences userData;
    String LOCAL_APP_DATA = "userInformation";

    String expertNames;
    String EXPERT_ID;

    ArrayList<String> workPeriod;
    ArrayList<Integer> currentStatus;
    NetworkImageView expertProfilePic;
    ArrayList<String> expertState;
    ArrayList<Integer> expertAge;
    ArrayList<String> expertBio;
    ArrayList<String> callExpert;
    ArrayList<String> mailExpert;
    ArrayList<String> expertGender;
    ArrayList<String> expertExperienceArray;

    TextView expertName,expertExpertise,expertExperience,expertStatus,expertCityV;

    TextView workPeriodV,currentStatusV,expertStateV,expertAgeV,expertBioV,callExpertV,mailExpertV,shareInfoV,expertMonthsXP;

    ImageView statusSrc,genderSrc;

    String expertMail,expertPhone;

    RelativeLayout progressExpert;


    LinearLayout monthsExpertLL,yearsExpertLL,freshExpertLL;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        LayoutInflaterCompat.setFactory(getLayoutInflater(), new IconicsLayoutInflater(getDelegate()));

        super.onCreate(savedInstanceState);


        ConnectivityManager connectivityManager  = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        final NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        boolean isTrue = networkInfo!= null && networkInfo.isConnected();


        if (isTrue ){

            setContentView( R.layout.activity_expert_details);
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);

            Bundle bundle  = getIntent().getExtras();
            if (bundle == null){
                return;

            }

            setupVariables();


            expertNames = bundle.getString("expertName","Experts Name");
            expertName.setText(bundle.getString("expertName","Experts Name"));
            expertStatus.setText(bundle.getString("expertStatus","Hey There Call me if necessary"));
            expertCityV.setText(bundle.getString("expertCity","Kashmir"));
            expertExpertise.setText(bundle.getString("expertExpertise","Main Expertise"));
			EXPERT_ID = bundle.getString("expertId","noId");


            String skillsEntered;
            skillsEntered = bundle.getString("expertSkills");


            String[] skillArray = skillsEntered.split(",");

            RecyclerView recyclerView = (RecyclerView)findViewById(R.id.adapterViewForSkills);
            RecyclerView testRV = (RecyclerView)findViewById(R.id.testRV);
            testRV.setLayoutManager(new LinearLayoutManager(ExpertDetails.this));
            expertProfilePic = (NetworkImageView) findViewById(R.id.expertProfilePic);
            ImageLoader imageLoader = MySingleton.getInstance(ExpertDetails.this).getImageLoader();
            expertProfilePic.setImageUrl((String) bundle.getString("expertPic",""), imageLoader);


             userData = getSharedPreferences(LOCAL_APP_DATA,0);

             
            new GetExpertDetails().execute(userData.getString("userEmail","noEmail@example.com"));


            recyclerView.setLayoutManager(new LinearLayoutManager(ExpertDetails.this));
            RecyclerView.Adapter adapter = new SkillsAdapter(skillArray);

            recyclerView.setAdapter(adapter);






        }else{
           Toast.makeText(ExpertDetails.this, "Please Review Your Internet Connection", Toast.LENGTH_SHORT).show();
            setContentView(R.layout.no_connectivity);
            Button button;


            button = (Button) findViewById(R.id.reCreate);


            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ConnectivityManager connectivityManager1  = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
                    NetworkInfo networkInfo1 = connectivityManager1.getActiveNetworkInfo();

                    boolean isTrue1 = networkInfo1!= null && networkInfo1.isConnected();
                    recreate();
//                    if (isTrue1){
//
//                    }else{
//                        Toast.makeText(ExpertDetails.this, "Please Connect to networks and try again", Toast.LENGTH_SHORT).show();
//                    }



                }
            });




        }





    }



    void setupVariables(){


        monthsExpertLL = (LinearLayout) findViewById(R.id.monthsExpertLL);
        yearsExpertLL = (LinearLayout) findViewById(R.id.yearsExpertLL);
        freshExpertLL = (LinearLayout) findViewById(R.id.freshExpertLL);

        expertName = (TextView)findViewById(R.id.expertNameV);
        expertExpertise = (TextView)findViewById(R.id.mainExpertiseV);
        expertExperience = (TextView)findViewById(R.id.expertExperienceV);
        expertStatus = (TextView) findViewById(R.id.expertStatusV);

        expertCityV = (TextView) findViewById(R.id.expertCityV);
        workPeriodV = (TextView)findViewById(R.id.workPeriodV);
        currentStatusV = (TextView)findViewById(R.id.currentStatusV);
        expertStateV = (TextView)findViewById(R.id.expertStateV);
        expertAgeV = (TextView)findViewById(R.id.expertAgeV);
        expertBioV = (TextView)findViewById(R.id.expertBioV);
        callExpertV = (TextView)findViewById(R.id.callExpertV);
        mailExpertV = (TextView)findViewById(R.id.mailExpertV);

        genderSrc  = (ImageView)findViewById(R.id.genderSrc);
        statusSrc = (ImageView)findViewById(R.id.statusSrc);

        progressExpert  = (RelativeLayout)findViewById(R.id.progressExpert);
        shareInfoV = (Button) findViewById(R.id.shareInfoV);
        expertMonthsXP = (TextView) findViewById(R.id.expertMonthsEXP);


        userData = getSharedPreferences(LOCAL_APP_DATA,0);




        callExpertV.setOnClickListener(new View.OnClickListener() {


            Dialog dialog = new Dialog(ExpertDetails.this);
            Button cancelVerify, callVerify, iUnderStand;




            @Override
            public void onClick(View v) {

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
                            intent.setData(Uri.parse("tel:" + expertPhone));
                            if (ActivityCompat.checkSelfPermission(ExpertDetails.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                                // TODO: Consider calling
                                //    ActivityCompat#requestPermissions
                                // here to request the missing permissions, and then overriding
                                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                //                                          int[] grantResults)
                                // to handle the case where the user grants the permission. See the documentation
                                // for ActivityCompat#requestPermissions for more details.
                                try{
                                    CallLogs callLogs = new CallLogs(ExpertDetails.this);
                                    callLogs.writer();
                                    callLogs.updateExpertCalls(expertNames,java.text.DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime()),expertPhone);
                                    callLogs.close();
                                }catch (Exception e){
                                e.printStackTrace();
                                }


                                startActivity(new Intent(ExpertDetails.this,CallPermissions.class).putExtra("callIt",expertPhone));
                                return;
                            }
                            try{
                                CallLogs callLogs = new CallLogs(ExpertDetails.this);
                                callLogs.writer();
                                callLogs.updateExpertCalls(expertNames,java.text.DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime()),expertPhone);
                                callLogs.close();
                            }catch (Exception e){
                                e.printStackTrace();
                            }

                            startActivity(intent);
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
//
//                boolean verificationStatus;
//                verificationStatus = userData.getBoolean("VERIFIED",false);
//                verifiedImage.setImageResource(R.drawable.verified_user);
//                verifiedTitle.setText("You are a Verified User");
//                verifiedSubtitle.setText("You are authorised to call,share,visit and mail this advertiser");
//                cancelVerify.setText("CANCEL");
//                callVerify.setText("CALL");
//
//                callVerify.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        Toast.makeText(context, "Calling Man please Wait", Toast.LENGTH_SHORT).show();
//                    }
//                });
//                cancelVerify.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        dialog.cancel();
//                    }
//                });
//
//
//
//                dialog.show();

            }


        });




        mailExpertV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean verificationStatus;

                final Dialog dialog = new Dialog(ExpertDetails.this);
                Button iUnderStand;
                verificationStatus = userData.getBoolean("VERIFIED",false);
                if (verificationStatus){

                    if (expertMail.contains("Update Your Email Now")){

                        Toast.makeText(ExpertDetails.this, "Expert hasn't updated its email yet", Toast.LENGTH_SHORT).show();
                        return;
                    }


                    Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                            "mailto",expertMail, null));
                    emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Profile Feedback");
                    emailIntent.putExtra(Intent.EXTRA_TEXT, "Hi there, i am sending this email with reference to your profile on 1clickAway as i wanted to know that... ");
                    startActivity(Intent.createChooser(emailIntent, "Send an Email via..."));




                } else { dialog.setContentView(R.layout.not_verified_user);
                    iUnderStand = (Button) dialog.findViewById(R.id.iUnderstand);
                    iUnderStand.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.cancel();
                        }
                    });

                    dialog.show();
                }



            }
        });

        shareInfoV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean verificationStatus;
                Button iUnderStand;
                final Dialog dialog = new Dialog(ExpertDetails.this);
                verificationStatus = userData.getBoolean("VERIFIED",false);
                if (verificationStatus){


                    String advert = "Shared via 1clickAway, Find Best Jobs, Experts and Offers from your City and State. Click on the below link to Download Now\nhttps://play.google.com/store/apps/details?id=corp.burenz.expertouch";
                    String firstString = "Hey i am sharing with you an Expert Profile Of "+expertName.getText().toString()+" from "+expertCityV.getText().toString()+" whose Field Of Interest is "+expertExpertise.getText().toString()+". Expert Phone Number : "+expertPhone+" ";
                    Intent shareIntent = new Intent();
                    shareIntent.setAction(Intent.ACTION_SEND);
                    shareIntent.setType("text/plain");
                    shareIntent.putExtra(Intent.EXTRA_TEXT, "" + firstString + "\n\n" + advert);
                    startActivity(Intent.createChooser(shareIntent, "Share via..."));




                } else { dialog.setContentView(R.layout.not_verified_user);
                    iUnderStand = (Button) dialog.findViewById(R.id.iUnderstand);
                    iUnderStand.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.cancel();
                        }
                    });

                    dialog.show();
                }



            }
        });







    }


    public class GetExpertDetails extends AsyncTask< String, String, String > {

        JSONObject jsonObject;
        JSONArray jsonArray;


        StringBuilder builder = new StringBuilder();
        BufferedReader bufferedReader;
        List<NameValuePair> nameValuePairs = new ArrayList<>();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            //     progressExpert.setVisibility(View.VISIBLE);
            workPeriod = new ArrayList<>();
            ;
            currentStatus = new ArrayList<>();
            expertState = new ArrayList<>();
            expertAge = new ArrayList<>();
            expertBio = new ArrayList<>();
            callExpert = new ArrayList<>();
            mailExpert = new ArrayList<>();
            expertExperienceArray = new ArrayList<>();
            expertGender = new ArrayList<>();

        }


        @Override
        protected String doInBackground(String... params) {


            nameValuePairs.add(new BasicNameValuePair("expertId", EXPERT_ID));
            nameValuePairs.add(new BasicNameValuePair("userPhone", params[0]));

            //initTemp();

            try {

                HttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost(getString(R.string.host)+"/hire/expert_profiles.php");
                httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                HttpResponse httpResponse = (HttpResponse) httpClient.execute(httpPost);
                HttpEntity httpEntity = (HttpEntity) httpResponse.getEntity();


                bufferedReader = new BufferedReader(new InputStreamReader(httpEntity.getContent()));
                String str = "";

                while ((str = bufferedReader.readLine()) != null) {

                    builder.append(str);

                }


                jsonObject = new JSONObject();
                jsonArray = new JSONArray(builder.toString());

                int length = jsonArray.length();


                for (int i = 0; i < length; i++) {

                    workPeriod = new ArrayList<>();
                    currentStatus = new ArrayList<>();
                    expertState = new ArrayList<>();
                    expertAge = new ArrayList<>();
                    expertBio = new ArrayList<>();
                    callExpert = new ArrayList<>();
                    mailExpert = new ArrayList<>();

                    jsonObject = jsonArray.getJSONObject(i);

                    workPeriod.add(jsonObject.getString("noticePeriod"));
                    currentStatus.add(Integer.parseInt(jsonObject.getString("status")));
                    expertState.add(jsonObject.getString("myCurrentState"));
                    expertAge.add(Integer.parseInt(jsonObject.getString("myAge")));
                    expertBio.add(jsonObject.getString("shortBio"));
                    callExpert.add(jsonObject.getString("expertCall"));
                    mailExpert.add(jsonObject.getString("email"));
                    expertGender.add(jsonObject.getString("gender"));
                    expertExperienceArray.add(jsonObject.getString("expertExperience"));

                }


            } catch (ClientProtocolException | JSONException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            if (workPeriod.size() == 0){
                Toast.makeText(ExpertDetails.this, "We are having Trouble Connecting to Internet, Please Try again Later"+s, Toast.LENGTH_SHORT).show();
            }else{


                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        setters();
                    }
                },1000);


            }


        }


        void initTemp() {

            workPeriod.add("1");
            currentStatus.add(1);
            expertState.add("Jammu and Kashmir");
            expertAge.add(21);
            expertBio.add("well Cool guys i am cool here is my cool about");
            callExpert.add("9797080059");
            mailExpert.add("mailMe@gmail.com");
            expertGender.add("1");
            expertExperienceArray.add("03xt37");

        }


        void setters() {
            Animation cardAnimate;
            cardAnimate = AnimationUtils.loadAnimation(ExpertDetails.this,R.anim.abc_fade_in);



            workPeriodV.startAnimation(cardAnimate);
            expertStateV.startAnimation(cardAnimate);
            expertStateV.startAnimation(cardAnimate);
            expertAgeV.startAnimation(cardAnimate);
            expertBioV.startAnimation(cardAnimate);


            workPeriodV.setText(workPeriod.get(0).toString());
            expertStateV.setText(expertState.get(0).toString());
            expertStateV.setText(expertState.get(0).toString());
            expertAgeV.setText(expertAge.get(0).toString());
            expertBioV.setText(expertBio.get(0).toString());



            String []experience =  expertExperienceArray.get(0).toString().split("3xt3");

            if (experience[0].equals("0") && experience[1].equals("0")){
                freshExpertLL.setVisibility(View.VISIBLE);

            }else if (experience[0].equals("0") && experience[1] != "0"){
                monthsExpertLL.setVisibility(View.VISIBLE);
                yearsExpertLL.setVisibility(View.GONE);

            }else if (experience[1].equals("0") && experience[0] != "0"){
                monthsExpertLL.setVisibility(View.GONE);
                yearsExpertLL.setVisibility(View.VISIBLE);
            }





            expertExperience.startAnimation(cardAnimate);
            expertMonthsXP.startAnimation(cardAnimate);

            expertExperience.setText(experience[0]);
            expertMonthsXP.setText(experience[1]);


            expertPhone = callExpert.get(0).toString();
            expertMail = mailExpert.get(0).toString();
            progressExpert.setVisibility(View.GONE);


            if (workPeriod.get(0).toString().equals(1)){


                workPeriodV.startAnimation(cardAnimate);

                workPeriodV.setText("FullTime");

            }else{
                workPeriodV.startAnimation(cardAnimate);
                workPeriodV.setText("Part Time");
            }


            if (expertGender.get(0).toString().equals("1")){

                genderSrc.startAnimation(cardAnimate);
                genderSrc.setImageResource(R.drawable.male);
            }else {
                genderSrc.startAnimation(cardAnimate);
                genderSrc.setImageResource(R.drawable.female);
            }


            if (currentStatus.get(0).toString().equals("1")){


                statusSrc.startAnimation(cardAnimate);
                statusSrc.setImageResource(R.drawable.available);

                currentStatusV.startAnimation(cardAnimate);
                currentStatusV.setText("Available");

                currentStatusV.setTextColor(Color.argb(255,0,153,204));




            }else {

                statusSrc.startAnimation(cardAnimate);
                currentStatusV.startAnimation(cardAnimate);

                statusSrc.setImageResource(R.drawable.not_available_now);
                currentStatusV.setText("Not Available");
                currentStatusV.setTextColor(Color.RED);


            }

        }







    }


    }



