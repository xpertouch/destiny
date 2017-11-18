package corp.burenz.expertouch.fragments.registration;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

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
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import corp.burenz.expertouch.R;
import corp.burenz.expertouch.activities.HelpCenter;
import corp.burenz.expertouch.activities.Jobs;

/**
 * Created by Developer on 6/29/2016.
 */

public class LoginFragment extends Fragment implements View.OnClickListener {


    String userEmail,userPassword;
    EditText userEmailE, userPasswordE;
    Button loginUser,createNew;
    String newPassword;
    TextView iForgotMyPassword,loginTitle;
    ViewFlipper proceedLoginFlipper;
    InputMethodManager im;
    LinearLayout progerssLayout;
    TextView takeMetoHelpCenter;
    LinearLayout loginContainer;

    TextView progressTitleV,progressSubtitleV;
    String COMPANY_DETAILS = "myCompanyDetails";
    SharedPreferences myCompanyDetails;
    SharedPreferences.Editor editor;
    Typeface logoTypeface;

    String MY_PROFILE_DATA = "myProfileInfo";
    SharedPreferences myProfile;
    SharedPreferences.Editor myProfileEditor;
    ArrayList<String> userState,userName,profile;
    ArrayList<String> companyName,companyTag,companyPhone,companyEmail,companyVisit,companyDiscription,companyState,companyCity,companyLandark,companyBanner,verified;
    ArrayList<String> fullName,mainExpertise,status,textStatus,noticePeriod,call,email,shortBio,myCurrentState,myExperience,myAge,myExperienceMonths,gender,expertCity,expertSkills,expertProfType,expertProfession,expertPic,expertVisibilty;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){

        View v = inflater.inflate(R.layout.login_fragment_layout,container,false);
        inItViews(v);

        loginContainer.startAnimation(AnimationUtils.loadAnimation(getActivity(),R.anim.abc_fade_in));
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
             loginContainer.setVisibility(View.VISIBLE);
            }
        },300);


        logoTypeface = Typeface.createFromAsset(getActivity().getAssets(), "fonts/forte.ttf");


        takeMetoHelpCenter = (TextView) v.findViewById(R.id.takeMeToHelpCenter);
        takeMetoHelpCenter.setOnClickListener(this);
        loginTitle = (TextView) v.findViewById(R.id.loginTitle);

        loginTitle.setTypeface(logoTypeface);

        loginUser.setOnClickListener(this);
        createNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_server_frame_layout,new Register()).commit();
            }
        });

        return v;
    }



    public void inItViews(View v){

        loginContainer = (LinearLayout) v.findViewById(R.id.loginContainer);
        loginUser = (Button) v.findViewById(R.id.loginUser);
		im = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
		iForgotMyPassword = (TextView) v.findViewById(R.id.iForgotMyPassword);
		iForgotMyPassword.setOnClickListener(this);
        createNew = (Button) v.findViewById(R.id.createNew);
		progerssLayout = (LinearLayout) v.findViewById(R.id.progressLayout);
        proceedLoginFlipper = (ViewFlipper) v.findViewById(R.id.proceedLoginFlipper);
        userEmailE = (EditText) v.findViewById(R.id.enterEmail);
        userPasswordE = (EditText) v.findViewById(R.id.enterPassword);

        progressTitleV = (TextView) v.findViewById(R.id.progressTitleV);
        progressSubtitleV = (TextView)  v.findViewById(R.id.progressSubtitleV);

    }


    @Override
    public void onClick(View v) {

    	switch(v.getId()){



    		case R.id.loginUser:



    			userEmail = userEmailE.getText().toString();

    			if (userEmail.length() == 10){

                    if (userEmail.contains(" ")){
                        Toast.makeText(getActivity(), "Phone Number must not contain any Spaces", Toast.LENGTH_SHORT).show();
                    }else {
                        userPassword = userPasswordE.getText().toString();
                        if (userPassword.length() > 7){
                            im.hideSoftInputFromWindow(userEmailE.getWindowToken(),0);
                            new LoginUser().execute();
                        }else {
                            Toast.makeText(getActivity(), "Password length less than 8 Characters", Toast.LENGTH_SHORT).show();

                        }


                    }


                }else {
                    Toast.makeText(getActivity(), "Please Enter a Valid Phone Number Address", Toast.LENGTH_SHORT).show();

                }
                break;


    		case R.id.iForgotMyPassword:
    			

				getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_server_frame_layout,new ResetPassword())
						.commit();

    			break;	
    			
    		case R.id.submitOTP:
    			

    			break;



            case R.id.takeMeToHelpCenter:
                    startActivity(new Intent(getActivity(), HelpCenter.class));

            break;


    	}


    }

        // This class gathers guest Data details
        class LoginUser extends AsyncTask< String, String, String > {

    		StringBuilder builder = new StringBuilder();
    		BufferedReader bufferedReader;

    		List<NameValuePair> userNamePassword = new ArrayList<NameValuePair>();


        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            userEmailE.setEnabled(false);
            userPasswordE.setEnabled(false);

            proceedLoginFlipper.showNext();

        }

        protected String doInBackground(String... params){


    			userNamePassword.add(new BasicNameValuePair("userPhone",userEmail));
				userNamePassword.add(new BasicNameValuePair("userPassword",userPassword));

					try{


						HttpClient httpClient = new DefaultHttpClient();
						HttpPost httpPost = new HttpPost(getString(R.string.host)+"/registrations/login_user.php");

						httpPost.setEntity(new UrlEncodedFormEntity (userNamePassword));
						
						HttpResponse httpResponse = (HttpResponse) httpClient.execute(httpPost);
						
						HttpEntity httpEntity  = (HttpEntity) httpResponse.getEntity();
						
						bufferedReader = new BufferedReader( new InputStreamReader( httpEntity.getContent() ) );
						String str = "";

						while( (str  = bufferedReader.readLine()) != null ){
								
								builder.append(str);

						}

                    }catch (ClientProtocolException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (Exception e){

                }


					return builder.toString(); 


    		}


    		protected void onPostExecute( String s ){
    			super.onPostExecute(s);

                if( s.equals("0") ){

                      new CheckAvailability().execute();

                }else if ( s.equals("1") ){


                    new GetUserStuff().execute();



    			}else{
                    proceedLoginFlipper.showNext();
                    Toast.makeText(getActivity(), "We are Having Trouble Connecting to the Internet, Please Try again", Toast.LENGTH_SHORT).show();

                    userEmailE.setEnabled(true);
                    userPasswordE.setEnabled(true);
                }


    		}










    }
        //This class is used to check whether the email
        //Is registered with us or not only in case the email and password doesn't match
        class CheckAvailability extends AsyncTask< String, String, String>{


        StringBuilder builder = new StringBuilder();
        BufferedReader bufferedReader;

        List<NameValuePair> nameValuePairs = new ArrayList<>();
            String USER_EMAIL;

        @Override
        protected String doInBackground(String... params) {

            nameValuePairs.add(new BasicNameValuePair("userPhone",USER_EMAIL));

            try {


                HttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost =  new HttpPost(getString(R.string.host)+"/registrations/check_availibilty.php");
                httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                HttpResponse httpResponse = (HttpResponse) httpClient.execute(httpPost);
                HttpEntity httpEntity = httpResponse.getEntity();

                bufferedReader = new BufferedReader(new InputStreamReader(httpEntity.getContent()));
                String str = "";

                while ( (str = bufferedReader.readLine()) != null ){

                    builder.append(str);
                }


            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e){

            }


            return builder.toString();
        }


        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

      //      Toast.makeText(getContext(), s, Toast.LENGTH_LONG).show();
            Log.e("ERROR",s);

            if (s.equals("0")){

                userEmailE.startAnimation(AnimationUtils.loadAnimation(getActivity(),R.anim.shakeanim));
                Toast.makeText(getActivity(), "Phone Number Not Registered with us", Toast.LENGTH_SHORT).show();

            }else if (s.equals("1")){




                Toast.makeText(getActivity(), "Incorrect Password", Toast.LENGTH_SHORT).show();

                userPasswordE.startAnimation(AnimationUtils.loadAnimation(getActivity(),R.anim.shakeanim));
                iForgotMyPassword.startAnimation(AnimationUtils.loadAnimation(getActivity(),R.anim.card_animation));
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        iForgotMyPassword.setVisibility(View.VISIBLE);
                    }
                },300);
            }


            else if (s.equals("die")) {

                Toast.makeText(getActivity(), "Couldn't Connect To server Right Now" , Toast.LENGTH_SHORT).show();

            }else {

                Toast.makeText(getActivity(), "Network failure, Please try again" , Toast.LENGTH_SHORT).show();
            }

            userEmailE.setEnabled(true);
            userPasswordE.setEnabled(true);
            proceedLoginFlipper.showNext();
        }


            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                USER_EMAIL = userEmailE.getText().toString();
            }
        }
        //This identifies experts or companies
        class GetUserStuff extends AsyncTask< String, String, String > {




        StringBuilder builder = new StringBuilder();
        BufferedReader bufferedReader;

        List<NameValuePair> userNamePassword = new ArrayList<NameValuePair>();
        JSONObject jsonObject;
        JSONArray jsonArray;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();


            progerssLayout.startAnimation(AnimationUtils.loadAnimation(getActivity(),R.anim.abc_slide_in_top));

            userState = new ArrayList<String>();
            userName = new ArrayList<String>();
            profile = new ArrayList<String>();

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                    progerssLayout.setVisibility(View.VISIBLE);

                }
            },300);

        }

        protected String doInBackground(String... params){


            userNamePassword.add(new BasicNameValuePair("userPhone",userEmail));
            userNamePassword.add(new BasicNameValuePair("userPassword",userPassword));

            try{

                HttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost(getString(R.string.host)+"/registrations/get_guest.php");

                httpPost.setEntity(new UrlEncodedFormEntity (userNamePassword));

                HttpResponse httpResponse = (HttpResponse) httpClient.execute(httpPost);

                HttpEntity httpEntity  = (HttpEntity) httpResponse.getEntity();

                bufferedReader = new BufferedReader( new InputStreamReader( httpEntity.getContent() ) );
                String str = "";

                while( (str  = bufferedReader.readLine()) != null ){

                    builder.append(str);

                }

                jsonArray = new JSONArray(builder.toString());


                    jsonObject = jsonArray.getJSONObject(0);

                userState.add(jsonObject.getString("userState"));
                userName.add(jsonObject.getString("userName"));
                profile.add(jsonObject.getString("profile"));



            }catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e){

            }

            return builder.toString();


        }
            

            protected void onPostExecute( String s ){
            super.onPostExecute(s);

            String LOCAL_APP_DATA = "userInformation";
            final SharedPreferences userData;
            final SharedPreferences.Editor editor;

            userData = getActivity().getSharedPreferences(LOCAL_APP_DATA,0);
            editor = userData.edit();

            boolean isCompany,isExpert;

            isExpert = false;
            isCompany = false;

            if (profile.size() > 0 && userState.size() > 0 && userName.size() > 0){


                if (profile.get(0).equals("expert")){
                    isExpert = true;

                }else if (profile.get(0).equals("company")) {
                    isCompany = true;
                }else {
                    isExpert = false;
                    isCompany = false;
                }



                editor.putString("userEmail",userEmailE.getText().toString());
                editor.putString("userName", userName.get(0));
                editor.putString("userState", userState.get(0));
                editor.putBoolean("EXPERT",isExpert);
                editor.putBoolean("COMPANY",isCompany);
                editor.putString("userPassword",userPasswordE.getText().toString());
                editor.apply();



                if (isCompany){

                    new GetCompanyStuff().execute();



                    // get Company Details

                }else  if (isExpert){
                    new GetExpertStuff().execute();

             //       progerssLayout.startAnimation(AnimationUtils.loadAnimation(getActivity(),R.anim.abc_fade_out));
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            progerssLayout.setBackgroundColor(Color.argb(180,0,0,0));
                          //  progerssLayout.startAnimation(AnimationUtils.loadAnimation(getActivity(),R.anim.abc_fade_in));
//

                             progressTitleV.setText("Welcome Back "+ userData.getString("userName","Expert"));
                            progressSubtitleV.setText("Please wait while we Setup Your Expert Profile registered with us, This Process may take a while");




                        }
                    },300);

                }else{

                    // Take Guest To Jobs user as he is neither expert nor user
                    im = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    im.hideSoftInputFromWindow(userEmailE.getWindowToken(),0);





                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            progerssLayout.startAnimation(AnimationUtils.loadAnimation(getActivity(),R.anim.abc_fade_out));
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {

                                    progerssLayout.setVisibility(View.GONE);
                                    editor.putBoolean("VERIFIED",true);
                                    editor.putBoolean("LOGEDOUT",false);
                                    editor.putBoolean("LOGEDIN",true);
                                    editor.commit();

                                    startActivity(new Intent(getActivity(), Jobs.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                                    getActivity().finish();

                                }
                            },300);


                        }
                    },300);


                }










            }



        }













    }

        class GetCompanyStuff extends AsyncTask< String, String, String > {


        StringBuilder builder = new StringBuilder();
        BufferedReader bufferedReader;

        List<NameValuePair> userNamePassword = new ArrayList<NameValuePair>();
        JSONObject jsonObject;
        JSONArray jsonArray;


         @Override
         protected void onPreExecute() {
             super.onPreExecute();

             String LOCAL_APP_DATA = "userInformation";
             final SharedPreferences userData;
             final Animation fabOpen;
             fabOpen = AnimationUtils.loadAnimation(getActivity(),R.anim.abc_fade_in);

             userData = getActivity().getSharedPreferences(LOCAL_APP_DATA,0);


            //  progerssLayout.startAnimation(AnimationUtils.loadAnimation(getActivity(),R.anim.abc_fade_in));
             progressTitleV.startAnimation(fabOpen);
             progressSubtitleV.startAnimation(fabOpen);
             progressTitleV.setText("Welcome Back "+ userData.getString("userName","Expert"));
             progressSubtitleV.setText("Lets Pick Up Where You Left, We are Setting Up Your Company Profile, This Process may take a while");

//             progerssLayout.startAnimation(AnimationUtils.loadAnimation(getActivity(),R.anim.abc_fade_in));
//
//             new Handler().postDelayed(new Runnable() {
//                 @Override
//                 public void run() {
//                     progerssLayout.setVisibility(View.VISIBLE);
//                 }
//             },500);


             companyName = new ArrayList<String>();
             companyTag = new ArrayList<String>();
             companyPhone = new ArrayList<String>();
             companyEmail = new ArrayList<String>();
             companyVisit = new ArrayList<String>();
             companyDiscription = new ArrayList<String>();
             companyState = new ArrayList<String>();
             companyCity  = new ArrayList<String>();
             companyLandark = new ArrayList<String>();
             companyBanner = new ArrayList<String>();
             verified = new ArrayList<String>();
         }

         protected String doInBackground(String... params) {


            userNamePassword.add(new BasicNameValuePair("userPhone", userEmail));


            try {


                HttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost(getString(R.string.host)+"/registrations/get_company.php");

                httpPost.setEntity(new UrlEncodedFormEntity(userNamePassword));

                HttpResponse httpResponse = (HttpResponse) httpClient.execute(httpPost);

                HttpEntity httpEntity = (HttpEntity) httpResponse.getEntity();

                bufferedReader = new BufferedReader(new InputStreamReader(httpEntity.getContent()));
                String str = "";

                while ((str = bufferedReader.readLine()) != null) {

                    builder.append(str);

                }

                jsonArray = new JSONArray(builder.toString());


                jsonObject = jsonArray.getJSONObject(0);
                companyName.add(jsonObject.getString("companyTitle"));
                companyTag.add(jsonObject.getString("companyTag"));
                companyPhone.add(jsonObject.getString("companyPhone"));
                companyEmail.add(jsonObject.getString("companyEmail"));
                companyVisit.add(jsonObject.getString("companyVisit"));
                companyDiscription.add(jsonObject.getString("companyDiscription"));
                companyState.add(jsonObject.getString("companyState"));
                companyLandark.add(jsonObject.getString("companyLandmark"));
                companyCity.add(jsonObject.getString("companyCity"));
                companyBanner.add(jsonObject.getString("companyBanner"));
                verified.add(jsonObject.getString("verified"));




            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {

            }


             return builder.toString();


        }


        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            String LOCAL_APP_DATA = "userInformation";
            final SharedPreferences userData;
            final SharedPreferences.Editor userDataEditor;
            userData = getActivity().getSharedPreferences(LOCAL_APP_DATA,0);
            userDataEditor = userData.edit();

            myCompanyDetails = getActivity().getSharedPreferences(COMPANY_DETAILS,0);
            editor = myCompanyDetails.edit();

            if (companyName.size() > 0 && companyTag.size() > 0 && companyPhone.size()> 0 && companyEmail.size() > 0 && companyVisit.size() > 0 && companyDiscription.size() > 0 && companyState.size() > 0 && companyLandark.size() >0){


                // add CVERIFIED
                editor.putString("companyName", companyName.get(0));
                editor.putString("companyTag", companyTag.get(0));
                editor.putString("companyPhone", companyPhone.get(0));
                editor.putString("companyEmail", companyEmail.get(0));
                editor.putString("companyVisit", companyVisit.get(0));
                editor.putString("companyDiscription", companyDiscription.get(0));
                editor.putString("companyState", companyState.get(0));
                editor.putString("companyCity", companyCity.get(0));
                editor.putString("companyLandmark", companyLandark.get(0));
                editor.putString("companyBanner", companyBanner.get(0));
                Boolean vStatus;

                if (verified.get(0).equals("true")){
                    vStatus = true;
                }else {
                    vStatus = false;
                }
                editor.putBoolean("CVERIFIED",vStatus);
                editor.putString("COTP","0");

                editor.apply();



                // COMPANY ADMIN TO JOBS NOW
                im = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                im.hideSoftInputFromWindow(userEmailE.getWindowToken(),0);

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                       // progerssLayout.startAnimation(AnimationUtils.loadAnimation(getActivity(),R.anim.abc_slide_in_top));
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {

                                progerssLayout.setVisibility(View.GONE);
                                userDataEditor.putBoolean("VERIFIED",true);
                                userDataEditor.putBoolean("LOGEDOUT",false);
                                userDataEditor.putBoolean("LOGEDIN",true);
                                userDataEditor.apply();

                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {

                                        startActivity(new Intent(getActivity(), Jobs.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                                        getActivity().finish();
                                    }
                                },500);


                            }
                        },300);


                    }
                },300);


            }


        }








     }
        class GetExpertStuff extends AsyncTask< String, String, String > {



            StringBuilder builder = new StringBuilder();
        BufferedReader bufferedReader;

        List<NameValuePair> userNamePassword = new ArrayList<NameValuePair>();
        JSONObject jsonObject;
        JSONArray jsonArray;


         @Override
         protected void onPreExecute() {
             super.onPreExecute();

             String LOCAL_APP_DATA = "userInformation";
             final SharedPreferences userData;
             final Animation fabOpen;
             fabOpen = AnimationUtils.loadAnimation(getActivity(),R.anim.abc_fade_in);
             userData = getActivity().getSharedPreferences(LOCAL_APP_DATA,0);
             progressTitleV.startAnimation(fabOpen);
             progressSubtitleV.startAnimation(fabOpen);
             progressTitleV.setText("Welcome Back "+ userData.getString("userName","Expert"));
             progressSubtitleV.setText("Lets Pick Up Where You Left, We are Setting Up Your Profile, This Process may take a while");

//             progerssLayout.startAnimation(AnimationUtils.loadAnimation(getActivity(),R.anim.abc_fade_in));
//
//             new Handler().postDelayed(new Runnable() {
//                 @Override
//                 public void run() {
//                     progerssLayout.setVisibility(View.VISIBLE);
//                 }
//             },500);







             fullName = new ArrayList<String>();
             status = new ArrayList<String>();
             textStatus = new ArrayList<String>();
             noticePeriod = new ArrayList<String>();
             call = new ArrayList<String>();
             email = new ArrayList<String>();
             shortBio = new ArrayList<String>();
             myCurrentState = new ArrayList<String>();
             myExperience = new ArrayList<String>();
             mainExpertise = new ArrayList<String>();
             myExperienceMonths = new ArrayList<String>();
             myAge = new ArrayList<String>();
             gender = new ArrayList<String>();
             expertCity = new ArrayList<String>();
             expertSkills = new ArrayList<String>();
             expertProfType = new ArrayList<String>();
             expertProfession = new ArrayList<String>();
             expertPic = new ArrayList<String>();
             expertVisibilty = new ArrayList<String>();





         }

         protected String doInBackground(String... params){

            userNamePassword.add(new BasicNameValuePair("userPhone",userEmail));

            try{


                HttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost(getString(R.string.host)+"/registrations/get_expert.php");

                httpPost.setEntity(new UrlEncodedFormEntity (userNamePassword));

                HttpResponse httpResponse = (HttpResponse) httpClient.execute(httpPost);

                HttpEntity httpEntity  = (HttpEntity) httpResponse.getEntity();

                bufferedReader = new BufferedReader( new InputStreamReader( httpEntity.getContent() ) );
                String str = "";

                while( (str  = bufferedReader.readLine()) != null ){

                    builder.append(str);

                }

                jsonArray = new JSONArray(builder.toString());



                jsonObject = jsonArray.getJSONObject(0);

                fullName.add(jsonObject.getString("myFullName"));
                mainExpertise.add(jsonObject.getString("myMainExpertise"));
                status.add(jsonObject.getString("myStatus"));
                textStatus.add(jsonObject.getString("myTextStatus"));
                noticePeriod.add(jsonObject.getString("myNoticePeriod"));
                call.add(jsonObject.getString("myCall"));
                email.add(jsonObject.getString("myEmail"));
                shortBio.add(jsonObject.getString("myShortBio"));
                myCurrentState.add(jsonObject.getString("myState"));
                myExperience.add(jsonObject.getString("myExperience"));
                myAge.add(jsonObject.getString("myAge"));
                myExperienceMonths.add(jsonObject.getString("myMonthsExperience"));
                gender.add(jsonObject.getString("gender"));
                expertCity.add(jsonObject.getString("expertCity"));
                expertSkills.add(jsonObject.getString("expertSkills"));
                expertProfType.add(jsonObject.getString("expertProfType"));
                expertProfession.add(jsonObject.getString("expertProfession"));
                expertPic.add(jsonObject.getString("expertPic"));
                expertVisibilty.add(jsonObject.getString("visibility"));

                // getVisibility here
            }catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e){

            }


            return builder.toString();


        }


        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            String LOCAL_APP_DATA = "userInformation";
            final SharedPreferences userData;
            final SharedPreferences.Editor userDataEditor;
            userData = getActivity().getSharedPreferences(LOCAL_APP_DATA,0);
            userDataEditor = userData.edit();


            myProfile = getActivity().getSharedPreferences(MY_PROFILE_DATA,0);
            myProfileEditor = myProfile.edit();
            myProfileEditor.putString("myFullName", fullName.get(0));

//error was here


            myProfileEditor.putString("myMainExpertise", mainExpertise.get(0));
            myProfileEditor.putString("myStatus", status.get(0));
            myProfileEditor.putString("myTextStatus", textStatus.get(0));
            myProfileEditor.putString("myNoticePeriod", noticePeriod.get(0));
            myProfileEditor.putString("myCall", call.get(0));
            myProfileEditor.putString("myEmail", email.get(0));
            myProfileEditor.putString("myShortBio", shortBio.get(0));
            myProfileEditor.putString("myState", myCurrentState.get(0));
            myProfileEditor.putString("myExperience", myExperience.get(0));
            myProfileEditor.putString("myAge", myAge.get(0));
            myProfileEditor.putString("myMonthsExperience", myExperienceMonths.get(0));
            myProfileEditor.putString("myGender", gender.get(0));
            myProfileEditor.putString("myCurrentCity", expertCity.get(0));
            myProfileEditor.putString("mySkills", expertSkills.get(0));
            myProfileEditor.putString("expertProfType", expertProfType.get(0));
            myProfileEditor.putString("myMainProf", expertProfession.get(0));
            myProfileEditor.putString("myProfilePic", expertPic.get(0));

            if (expertVisibilty.get(0).equals("companies")){
                myProfileEditor.putString("visibility","0");

            }else {
                myProfileEditor.putString("visibility","1");

            }

            myProfileEditor.apply();



            // COMPANY ADMIN TO JOBS NOW
            im = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            im.hideSoftInputFromWindow(userEmailE.getWindowToken(),0);

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                    progerssLayout.startAnimation(AnimationUtils.loadAnimation(getActivity(),R.anim.abc_slide_out_top));
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            progerssLayout.setVisibility(View.GONE);
                            userDataEditor.putBoolean("VERIFIED",true);
                            userDataEditor.putBoolean("LOGEDOUT",false);
                            userDataEditor.putBoolean("LOGEDIN",true);
                            userDataEditor.apply();
                            startActivity(new Intent(getActivity(), Jobs.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                            getActivity().finish();
                        }
                    },500);


                }
            },300);


        }

        }






}









