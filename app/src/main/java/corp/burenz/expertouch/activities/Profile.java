package corp.burenz.expertouch.activities;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import corp.burenz.expertouch.R;
import corp.burenz.expertouch.util.MySingleton;

public class Profile extends AppCompatActivity implements View.OnClickListener   {

    ViewFlipper infoFlipper,statusFlipepr,locationFlipper,experienceFlipper,bioFlipper,callFlipper,emailFlipper,skillFlipper;
    ImageView editFlipInfo,editFlipStatus,editFlipLocation,editFlipExperience,editFlipBio,editFlipCall,editFlipemail,editFlipSkill;
    ImageButton editFlipImage,saveImageButton;
    ViewFlipper infoPannelFlipper,statusPannelFlipper,locationPannelFlipper,experiencePannelFlipper,bioPannelFlipper,callPannelFlipper,emailPannelFlipper,skillPannelFlipper,imagePannelFlipper;
    Button saveInfoBtn,saveStatusBtn,saveLocationBtn,saveExperienceBtn,saveBioBtn,saveCallBtn,saveEmailBtn,saveSkillButton,cancelInfoBtn,cancelStatusBtn,cancelLocationBtn,cancelExperienceBtn,cancelBioBtn,cancelCallBtn,cancelEmailBtn,cancelSkillButton;
    ImageButton cancelImageButton;
    RecyclerView skilledRV;
    Animation fadeIn,fadeOut;

    ArrayList<String> lawyerTypes = new ArrayList<>();
    ArrayList<String> artizensType = new ArrayList<>();
    ArrayList<String> teacherTypes = new ArrayList<>();
    ArrayList<String> doctorsType = new ArrayList<>();
    ArrayList<String> mediaTypes = new ArrayList<>();
    ArrayList<String> healthTypes = new ArrayList<>();
    ArrayList<String> agentTypes = new ArrayList<>();
    ArrayList<String> contractorTypes = new ArrayList<>();
    ArrayList<String> otherTypes = new ArrayList<>();
    ArrayList<String> engineersType = new ArrayList<>();
    ArrayList<String> fashion = new ArrayList<>();
    ArrayList<String> mechanics = new ArrayList<>();
    ArrayList<String> accountants = new ArrayList<>();
    ArrayList<String> transporters = new ArrayList<>();
    ArrayList<String> localTransPort = new ArrayList<>();
    ArrayList<String> orderSuppliesTypes = new ArrayList<String>();

    ArrayList<String> statesArray = new ArrayList<String>();

    Switch availibilitySwitch;
    CardView fullNameCard,callStatusCard,addressCard,experienceCard,skillCard,bioCard,callCard,mailCard;
    RadioButton companiesRB,publicRB;
    NetworkImageView myProfilePic;
    ConnectivityManager connectivityManager;
    NetworkInfo networkInfo;
    boolean connectionStatus;
    String MY_PROFILE_DATA = "myProfileInfo";
    SharedPreferences myProfile;
    SharedPreferences.Editor updateEditor;
    Animation cardAnimation;

    //Image Cake
    public static final int GALLERY_PICTURE = 1;
    public static final int READ_GALLERY_PERMISSIONS_REQUEST = 2;
    public static final int  REQUEST_IMAGE_CROP = 3;
    Intent imageIntent;
    ImageButton selectFromGallery;
    ImageView imagePreview;
    String base64;
    LinearLayout imageProgress;

    // Profile First Cake
    TextView myFullNameV,myMainExpertiseV,myNoticePeiodV;
    ImageView genderSrc;
    EditText myFullNameE,myMainExpertiseE,myCurrentCityE;
    RadioButton myMaleRD,myFemaleRD;
    RadioButton partTimeRBP,fullTimeRBP;
    Spinner myProfTypeS,myGenderS;
    String gender;
    String noticePeriod;
    TextView myCurrentCityV;

    //Experience Cake

    TextView myYOB,myExperienceV,myExperienceMonths;
    Spinner myYOBE;
    Spinner myExperienceE;
    Spinner myExperienceMonthsE;



    // Profile Second cake // Availibility
    TextView myTextStatusV;
    TextView myStatusV;
    ImageView myStatusSrc;
    EditText myTextStatusE;
    String available;


    // Profile Third Cake // Curren State
    TextView myCurrentStateV;
    Spinner myCurrentStateS;
    String visibilty;

    //Profile Fourth Cake //  PENDING REMOVE CONFUSION



    //Profile Fifth Cake // Skills
    TextView myEnteredSkillsV;
    EditText myEnteredSkillsE;
    Bitmap myBitmap;

    // Profile sixth  Cake  // Bio
    TextView myShortBioV;
    EditText myShortBioE;


    // Profile Seventh Cake // call and Email
    TextView myCallV,myMailV;
    EditText myCallE,myMailE;




    // Verification Banner layout Variables

    


    SharedPreferences userData;
    String LOCAL_APP_DATA = "userInformation";
    String USER_EMAIL;



    void addStates(){
        statesArray.add("Select Your State");
        statesArray.add("Andhra Pradesh");
        statesArray.add("Arunachal Pradesh");
        statesArray.add("Assam");
        statesArray.add("Bihar");
        statesArray.add("Chandigarh");
        statesArray.add("Chhattisgarh");
        statesArray.add("Dadra and Nagar Haveli");
        statesArray.add("Daman and Diu");
        statesArray.add("Delhi");
        statesArray.add("Goa");
        statesArray.add("Gujarat");
        statesArray.add("Haryana");
        statesArray.add("Himachal Pradesh");
        statesArray.add("Jammu and Kashmir");
        statesArray.add("Jharkhand");
        statesArray.add("Karnataka");
        statesArray.add("Kerala");
        statesArray.add("Lakshadweep");
        statesArray.add("Madhya Pradesh");
        statesArray.add("Maharashtra");
        statesArray.add("Manipur");
        statesArray.add("Meghalaya");
        statesArray.add("Mizoram");
        statesArray.add("Nagaland");
        statesArray.add("Odisha");
        statesArray.add("Puducherry");
        statesArray.add("Punjab");
        statesArray.add("Rajasthan");
        statesArray.add("Sikkim");
        statesArray.add("Tamil Nadu");
        statesArray.add("Telengana");
        statesArray.add("Tripura");
        statesArray.add("Uttar Pradesh");
        statesArray.add("Uttarakhand");
        statesArray.add("West Bengal");
initArrays();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        addStates();

        userData = getSharedPreferences(LOCAL_APP_DATA,0);
        USER_EMAIL = userData.getString("userEmail","noEmail");

        String skillLists;
        skillLists = "skill1,skill2,skill3,skill4,skill5";
        final String skillArray[] = skillLists.split(",");

        fadeIn = AnimationUtils.loadAnimation(Profile.this,R.anim.abc_fade_in);
        fadeOut = AnimationUtils.loadAnimation(Profile.this,R.anim.abc_fade_out);




        connectivityManager = (ConnectivityManager)  Profile.this.getSystemService(CONNECTIVITY_SERVICE);
        networkInfo = connectivityManager.getActiveNetworkInfo();

        myProfile = getSharedPreferences(MY_PROFILE_DATA,0);
        final ImageLoader imageLoader = MySingleton.getInstance(Profile.this).getImageLoader();

        visitProfileSetters();
        visitVaraiables();
        editingProfile();

            setExperience();
            setProfileTexts();
            setAvailibility();
            setState();
            setSkills();
            setBio();
            setCall();
            setEmail();
            setVisibility();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                  setImage();

                }
            },1000);

    }


    void setExperience() {

        LinearLayout monthsLayout,yearsContainer,fresherContainer;
        TextView yeasrText;

        monthsLayout = (LinearLayout) findViewById(R.id.monthsContainer);
        yearsContainer = (LinearLayout) findViewById(R.id.yearsContainer);
        fresherContainer = (LinearLayout) findViewById(R.id.fresherContainer);
        yeasrText = (TextView) findViewById(R.id.yearsText);
        myProfile = getSharedPreferences(MY_PROFILE_DATA,0);

        if (myProfile.getString("myExperience","1").equals("0")  && myProfile.getString("myMonthsExperience","1").equals("0")){
            fresherContainer.setVisibility(View.VISIBLE);
        }else{
            fresherContainer.setVisibility(View.GONE);
        }

        if (myProfile.getString("myAge","1990").equals("Below 20")){
            myYOB.setTextSize(20);
        }else{
            myYOB.setTextSize(40);
        }

        myYOB.setText(myProfile.getString("myAge","1990"));

        if (myProfile.getString("myExperience","1").equals("0")){
            yearsContainer.setVisibility(View.GONE);
        }else{
            yearsContainer.setVisibility(View.VISIBLE);
        }

//        if (myProfile.getString("myExperience","1").equals("Fresher")){
//            myExperienceV.setTextSize(20);
//            yeasrText.setVisibility(View.GONE);
//        }else{
//            myExperienceV.setTextSize(40);
//            yeasrText.setVisibility(View.VISIBLE);
//        }

        if (myProfile.getString("myMonthsExperience","0").equals("0")){
            monthsLayout.setVisibility(View.GONE);
            //yeasrText.setVisibility(View.GONE);
        }else{
            monthsLayout.setVisibility(View.VISIBLE);
            myExperienceMonths.setText(myProfile.getString("myMonthsExperience","0"));
            yeasrText.setVisibility(View.VISIBLE);
        }

        myExperienceV.setText(myProfile.getString("myExperience","1"));




    }


    void editingProfile(){


        // EDIT IMAGE BUTTON LAYOUT FLIPPERS

        editFlipInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                infoFlipper.showNext();
                infoPannelFlipper.showNext();
            }
        });

        editFlipStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                statusPannelFlipper.showNext();
                statusFlipepr.showNext();
            }
        });

        editFlipLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                locationPannelFlipper.showNext();
                locationFlipper.showNext();
            }
        });
        editFlipExperience.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                experienceFlipper.showNext();
                experiencePannelFlipper.showNext();
            }
        });
        editFlipBio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bioPannelFlipper.showNext();
                bioFlipper.showNext();
            }
        });
        editFlipCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callPannelFlipper.showNext();
                callFlipper.showNext();
            }
        });
        editFlipemail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                emailPannelFlipper.showNext();
                emailFlipper.showNext();
            }
        });
        editFlipSkill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                skillPannelFlipper.showNext();
                skillFlipper.showNext();
            }
        });
        editFlipImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imagePannelFlipper.showNext();
            }
        });





        // CANCEL BUTTONS

        cancelInfoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                infoPannelFlipper.showPrevious();
                infoFlipper.showNext();
                setProfileTexts();
            }
        });

        cancelStatusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                statusPannelFlipper.showPrevious();
                statusFlipepr.showNext();
                setAvailibility();
            }
        });

        cancelLocationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                locationPannelFlipper.showPrevious();
                locationFlipper.showNext();
                setState();
            }
        });

        cancelExperienceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                experiencePannelFlipper.showPrevious();
                experienceFlipper.showNext();
                setExperience();
            }
        });
        cancelBioBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bioPannelFlipper.showPrevious();;
                bioFlipper.showNext();
                setBio();
            }
        });
        cancelCallBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callPannelFlipper.showPrevious();
                callFlipper.showNext();
                setCall();
            }
        });
        cancelEmailBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                emailPannelFlipper.showPrevious();
                emailFlipper.showNext();
                setEmail();
            }
        });

        cancelSkillButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                skillPannelFlipper.showPrevious();
                skillFlipper.showNext();
                setSkills();
            }
        });

        cancelImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (imagePreview.getVisibility() == View.VISIBLE){

                    imagePreview.startAnimation(fadeOut);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            imagePreview.setVisibility(View.GONE);
                        }
                    },300);
                }
                imagePannelFlipper.showPrevious();

            }




        });





        //SAVE BUTTONS

        saveInfoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                connectionStatus = checkConnection();

                if (connectionStatus){


                    if (myFullNameE.getText().toString().length() > 3 || myMainExpertiseE.getText().toString().length() > 5){

                        new UpdateFirstCake().execute();

                    }else {

                        if (myFullNameE.getText().toString().length() < 3){
                            Toast.makeText(Profile.this, "Username must be at least 4 Characters Long", Toast.LENGTH_SHORT).show();

                        }else {
                            Toast.makeText(Profile.this, "Field Of Interest must be at least 6 Characters Long", Toast.LENGTH_SHORT).show();

                        }
                    }

                }else {
                    Snackbar.make(v,"No Internet Connection Found",Snackbar.LENGTH_LONG).show();
                }

            }
        });

        saveStatusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                connectionStatus = checkConnection();

                if (connectionStatus){

                    if (myTextStatusE.getText().toString().length() > 10 && myTextStatusE.getText().toString().length()< 70){
                        new UpdateStatus().execute();
                    }else {
                        Toast.makeText(Profile.this, "Status must be greater than 10 Characters but less than 70 characters", Toast.LENGTH_SHORT).show();

                    }


                }else {
                    Snackbar.make(v,"No Internet Connection Found",Snackbar.LENGTH_LONG).show();
                }
            }
        });

        saveLocationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                connectionStatus = checkConnection();

                if (connectionStatus){

                    if (myCurrentCityE.getText().toString().length() < 8 || myCurrentStateS.getSelectedItem().toString().equals("Select Your State")){
                        if (myCurrentCityE.getText().toString().length() < 8 ){
                            Toast.makeText(Profile.this, "Your City Name must be at least 8 Characters Long", Toast.LENGTH_SHORT).show();

                        }else {
                            Toast.makeText(Profile.this, "Please Select a valid State", Toast.LENGTH_SHORT).show();
                        }

                    }else {
                        new UpdateAddress().execute();
                    }

                }else {
                    Snackbar.make(v,"No Internet Connection Found",Snackbar.LENGTH_LONG).show();
                }
            }
        });

        saveExperienceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                connectionStatus = checkConnection();

                if (connectionStatus){

                    if (myYOBE.getSelectedItem().toString().equals("Select Age")|| myExperienceE.getSelectedItem().toString().equals("Select Years") || myExperienceMonthsE.getSelectedItem().toString().equals("Select Months")){
                        Snackbar.make(v,"Please Select a Valid Set",Snackbar.LENGTH_SHORT).show();

                    }else{
                        new UpdateExperience().execute();
                    }


                }else {
                    Snackbar.make(v,"No Internet Connection Found",Snackbar.LENGTH_LONG).show();
                }
            }
        });

        saveBioBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                connectionStatus = checkConnection();

                if (connectionStatus){

                    if (myShortBioE.getText().length() > 5 && myShortBioE.getText().length() < 40){

                        new UpdateBio().execute();
                    }else {
                        Toast.makeText(Profile.this, "Your Bio must be at least 5 Characters Long but less than 40 Characters", Toast.LENGTH_SHORT).show();
                    }


                }else {
                    Snackbar.make(v,"No Internet Connection Found",Snackbar.LENGTH_LONG).show();
                }
            }
        });

        saveCallBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                connectionStatus = checkConnection();

                if (connectionStatus){

                    if (myCallE.getText().toString().length() == 10){

                        new UpdatePhone().execute();

                    }else {
                        Toast.makeText(Profile.this, "Please enter 10 digits exactly", Toast.LENGTH_SHORT).show();

                    }




                }else {
                    Snackbar.make(v,"No Internet Connection Found",Snackbar.LENGTH_LONG).show();
                }
            }
        });

        saveEmailBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                connectionStatus = checkConnection();

                if (connectionStatus){
                    String newEmail = myMailE.getText().toString();
                    if (newEmail.contains("@") && newEmail.contains(".com")){

                        if (newEmail.contains(" ")){
                            Toast.makeText(Profile.this, "Email Address must not contain any Spaces", Toast.LENGTH_SHORT).show();
                        }else{
                            new UpdateEmail().execute();
                        }

                    }else{
                        Toast.makeText(Profile.this, "Please Enter a valid Email", Toast.LENGTH_SHORT).show();
                    }

                }else {
                    Snackbar.make(v,"No Internet Connection Found",Snackbar.LENGTH_LONG).show();
                }
            }
        });


        saveSkillButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                connectionStatus = checkConnection();

                if (connectionStatus){

                   new UpdateSkills().execute();

                } else {

                    Snackbar.make(v,"No Internet Connection Found",Snackbar.LENGTH_LONG).show();

                }
            }
        });

        saveImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                connectionStatus = checkConnection();

                if (connectionStatus){


                    if (imagePreview.getVisibility() == View.VISIBLE){
                        sendImage(v);
                    }else{
                        Snackbar.make(v,"No Image Selected",Snackbar.LENGTH_LONG).show();
                    }


                } else {

                    Snackbar.make(v,"No Internet Connection Found",Snackbar.LENGTH_LONG).show();

                }
            }
        });


    }

    void visitVaraiables(){

        cardAnimation = AnimationUtils.loadAnimation(Profile.this,R.anim.card_animation);

        myProfilePic = (NetworkImageView) findViewById(R.id.myProfilePic);

        //Cards
        fullNameCard = (CardView)findViewById(R.id.fullNameCard);
        callStatusCard = (CardView)findViewById(R.id.callStatusCard);
        addressCard = (CardView)findViewById(R.id.addressCard);
        experienceCard = (CardView)findViewById(R.id.experienceCard);
        skillCard = (CardView)findViewById(R.id.skillCard);
        bioCard = (CardView)findViewById(R.id.bioCard);
        callCard = (CardView)findViewById(R.id.callCard);
        mailCard = (CardView)findViewById(R.id.mailCard);


        fullNameCard.startAnimation(cardAnimation);
        callStatusCard.startAnimation(cardAnimation);
        addressCard.startAnimation(cardAnimation);
        experienceCard.startAnimation(cardAnimation);
        skillCard.startAnimation(cardAnimation);
        bioCard.startAnimation(cardAnimation);
        callCard.startAnimation(cardAnimation);
        mailCard.startAnimation(cardAnimation);



        // TextViews
        myExperienceMonths = (TextView) findViewById(R.id.myExperienceMonths);
        myYOB = (TextView) findViewById(R.id.myYOBV);
        myExperienceV = (TextView) findViewById(R.id.myExperienceV);
        myYOBE = (Spinner) findViewById(R.id.myYOBE);
        myExperienceE = (Spinner) findViewById(R.id.myExperienceE);
        myExperienceMonthsE = (Spinner) findViewById(R.id.myMonthsExperienceE);

        // View Flippers

        infoFlipper = (ViewFlipper)findViewById(R.id.infoFlipper);
        statusFlipepr = (ViewFlipper)findViewById(R.id.statusFlipper);
        locationFlipper = (ViewFlipper)findViewById(R.id.locationFlipper);
        experienceFlipper  =(ViewFlipper)findViewById(R.id.experienceFlipper);
        bioFlipper = (ViewFlipper)findViewById(R.id.bioFlipper);
        callFlipper = (ViewFlipper)findViewById(R.id.callFlipper);
        emailFlipper = (ViewFlipper)findViewById(R.id.emailFlipper);
        skillFlipper  = (ViewFlipper)findViewById(R.id.skillFlipper);


        //ImageView

        editFlipInfo = (ImageView)findViewById(R.id.editFlipInfo);
        editFlipStatus = (ImageView)findViewById(R.id.editFlipStatus);
        editFlipLocation = (ImageView)findViewById(R.id.editFlipAddress);
        editFlipExperience = (ImageView)findViewById(R.id.editFlipExperience);
        editFlipBio = (ImageView)findViewById(R.id.editFlipBio);
        editFlipCall = (ImageView)findViewById(R.id.editFlipCall);
        editFlipemail = (ImageView)findViewById(R.id.editFlipEmail);
        editFlipSkill = (ImageView)findViewById(R.id.editFlipSkill);
        editFlipImage = (ImageButton)findViewById(R.id.editFlipImage);
        imagePreview = (ImageView) findViewById(R.id.imagePreview);
        imagePreview.setVisibility(View.GONE);




        //PannelFlipper

        infoPannelFlipper = (ViewFlipper)findViewById(R.id.infoPannelFlipper);
        statusPannelFlipper = (ViewFlipper)findViewById(R.id.statusPannelFlipper);
        locationPannelFlipper = (ViewFlipper)findViewById(R.id.addressPannelFlipper);
        experiencePannelFlipper = (ViewFlipper)findViewById(R.id.experiencePannelFlipper);
        bioPannelFlipper = (ViewFlipper)findViewById(R.id.bioPannelFlipper);
        callPannelFlipper = (ViewFlipper)findViewById(R.id.callPannelFlipper);
        emailPannelFlipper = (ViewFlipper)findViewById(R.id.emailPannelFlipper);
        skillPannelFlipper = (ViewFlipper)findViewById(R.id.skillPannelFlipper);
        imagePannelFlipper = (ViewFlipper)findViewById(R.id.imagePannelFlipper);


        // save buttons

        saveInfoBtn = (Button)findViewById(R.id.saveInfoBT);
        saveStatusBtn = (Button)findViewById(R.id.saveStatusBtn);
        saveLocationBtn = (Button)findViewById(R.id.saveLocationBtn);
        saveExperienceBtn = (Button)findViewById(R.id.saveExperienceBtn);
        saveBioBtn = (Button)findViewById(R.id.saveBioBtn);
        saveCallBtn = (Button)findViewById(R.id.saveCallBtn);
        saveEmailBtn = (Button)findViewById(R.id.saveEmailBtn);
        saveSkillButton = (Button)findViewById(R.id.saveSkillButton);



        //LinearLayouts


        saveImageButton= (ImageButton)findViewById(R.id.saveImageButton);
        cancelImageButton = (ImageButton)findViewById(R.id.cancelImageButton);
        selectFromGallery = (ImageButton) findViewById(R.id.selectFromGallery);


        selectFromGallery.setOnClickListener(this);

        // cancel Buttons

        cancelInfoBtn = (Button)findViewById(R.id.cancelInfoBtn);
        cancelStatusBtn = (Button)findViewById(R.id.cancelStatusBtn);
        cancelLocationBtn = (Button)findViewById(R.id.cancelLocationBtn);
        cancelExperienceBtn = (Button)findViewById(R.id.cancelExperienceBtn);
        cancelBioBtn = (Button)findViewById(R.id.cancelBioBtn);
        cancelCallBtn = (Button)findViewById(R.id.cancelCallBtn);
        cancelEmailBtn = (Button)findViewById(R.id.cancelEmailBtn);
        cancelSkillButton = (Button)findViewById(R.id.cancelSkillButton);

        // Recycler View
        skilledRV = (RecyclerView)findViewById(R.id.skilledRV);

        //RadioButtons

        companiesRB = (RadioButton)findViewById(R.id.companiesRB);
        publicRB = (RadioButton) findViewById(R.id.publicRB);

        companiesRB.setOnClickListener(this);
        publicRB.setOnClickListener(this);

         gender = myProfile.getString("myGender","0");
         noticePeriod = myProfile.getString("myNoticePeriod","0");
         available  = myProfile.getString("available","0");

        //Switches

        availibilitySwitch = (Switch)findViewById(R.id.availableSwitch);
        myGenderS = (Spinner) findViewById(R.id.myGenderS);


    }



    void setImage(){


        myProfile = getSharedPreferences(MY_PROFILE_DATA,0);
        ImageLoader imageLoader = MySingleton.getInstance(Profile.this).getImageLoader();
        myProfilePic.setImageUrl(myProfile.getString("myProfilePic",getString(R.string.host)+"/defaults/expert_default.jpg"), imageLoader);
        Log.e("myProfilePic",myProfile.getString("myProfilePic",getString(R.string.host)+"/defaults/expert_default.jpg"));
    }



    void initArrays(){
        lawyerTypes.add("All Categories");
        lawyerTypes.add("Employment Lawyer");
        lawyerTypes.add("Family Lawyer");
        lawyerTypes.add("General Practice Lawyer");
        lawyerTypes.add("Bankruptcy Lawyer");
        lawyerTypes.add("Commercial Bankruptcy Lawyer");
        lawyerTypes.add("Corporate Lawyer");
        lawyerTypes.add("Criminal Lawyer");
        lawyerTypes.add("Deportation Lawyer");
        lawyerTypes.add("DUI Lawyer");
        lawyerTypes.add("Inheritance Lawyer");
        lawyerTypes.add("Spousal Support Lawyer");
        lawyerTypes.add("Discrimination Lawyer");
        lawyerTypes.add("Wrongful Death Lawyer");
        lawyerTypes.add("Birth Injury Lawyer");
        lawyerTypes.add("Health Insurance Lawyer");
        lawyerTypes.add("Contract Lawyer");
        lawyerTypes.add("Accident Lawyer");
        lawyerTypes.add("Traffic Lawyer");
        lawyerTypes.add("Others");


        artizensType.add("All Categories");
        artizensType.add("Welders");
        artizensType.add("Electricians");
        artizensType.add("Fitters");
        artizensType.add("Turners");
        artizensType.add("Millwrights");
        artizensType.add("Sheetmetal Workers");
        artizensType.add("Boilermakers");
        artizensType.add("Mechatronics");
        artizensType.add("Mechanics");
        artizensType.add("Woodworks");
        artizensType.add("Penlers");
        artizensType.add("Toolmakers");
        artizensType.add("Patternmakers");
        artizensType.add("Bricklayers");
        artizensType.add("Plumbers");
        artizensType.add("Maison");
        artizensType.add("Carpenters");
        artizensType.add("Joiners");
        artizensType.add("Shutterhands");
        artizensType.add("Steel fixers");
        artizensType.add("Glaziers");
        artizensType.add("Plasterers");
        artizensType.add("Tilers");
        artizensType.add("Sound technicians");
        artizensType.add("Painters");
        artizensType.add("Instrumentation and electronics technicians");
        artizensType.add("Others");



        teacherTypes.add("All Categories");
        teacherTypes.add("Science");
        teacherTypes.add("Arts");
        teacherTypes.add("Commerce");
        teacherTypes.add("Physical Education");
        teacherTypes.add("Islamic Studies");
        teacherTypes.add("Others");



        doctorsType.add("All Categories");
        doctorsType.add("Audiologist");
        doctorsType.add("Allergist");
        doctorsType.add("Anesthesiologist");
        doctorsType.add("Cardiologist");
        doctorsType.add("Dentist");
        doctorsType.add("Dermatologist");
        doctorsType.add("Endocrinologist");
        doctorsType.add("Gynecologist");
        doctorsType.add("Immunologist");
        doctorsType.add("Infectious Disease Specialist");
        doctorsType.add("Internal Medicine Specialist");
        doctorsType.add("Medical Geneticist");
        doctorsType.add("Microbiologist");
        doctorsType.add("Neonatologist");
        doctorsType.add("Neurologist");
        doctorsType.add("Neurosurgeon");
        doctorsType.add("Obstetrician");
        doctorsType.add("Oncologist");
        doctorsType.add("Orthopedic Surgeon");
        doctorsType.add("ENT Specialist");
        doctorsType.add("Pediatrician");
        doctorsType.add("Physiologist");
        doctorsType.add("Plastic Surgeon");
        doctorsType.add("Podiatrist");
        doctorsType.add("Psychiatrist");
        doctorsType.add("Radiologist");
        doctorsType.add("Rheumatologist");
        doctorsType.add("Surgeon");
        doctorsType.add("Urologist");
        doctorsType.add("Others");





        mediaTypes.add("All Categories");
        mediaTypes.add("Journalists");
        mediaTypes.add("Print Media");
        mediaTypes.add("Electronic Media");
        mediaTypes.add("News");
        mediaTypes.add("Agents");
        mediaTypes.add("Others");





        healthTypes.add("All Categories");
        healthTypes.add("Diagnostics");
        healthTypes.add("Clinics");
        healthTypes.add("Stores");
        healthTypes.add("Others");






        agentTypes.add("All Categories");
        agentTypes.add("Insurance");
        agentTypes.add("Travel");
        agentTypes.add("Automobile");
        agentTypes.add("Overseas");
        agentTypes.add("Placement");
        agentTypes.add("Haj and Umrah");
        agentTypes.add("Pilgrimage");
        agentTypes.add("Others");




        contractorTypes.add("All Categories");
        contractorTypes.add("Government");
        contractorTypes.add("Private");
        contractorTypes.add("Others");




        otherTypes.add("All Categories");
        otherTypes.add("Wedding Planners");
        otherTypes.add("Cooks");
        otherTypes.add("Advisers");
        otherTypes.add("Tourist Guides");
        otherTypes.add("Others");








        engineersType.add("All Categories");
        engineersType.add("Architectural & Building");
        engineersType.add("Chemical Engineering");
        engineersType.add("Civil Engineering");
        engineersType.add("Electrical Engineering");
        engineersType.add("Industrial Engineering");
        engineersType.add("Mechatronics Engineering");
        engineersType.add("Mechanical Engineering");
        engineersType.add("Metallurgical Engineering");
        engineersType.add("Computer Engineering");
        engineersType.add("Software Engineering");
        engineersType.add("Mathematical Engineering");
        engineersType.add("Others");






        fashion.add("All Categories");
        fashion.add("Boutiques");
        fashion.add("Bridal Make Up");
        fashion.add("Mehandi expert");
        fashion.add("Hair Stylist");
        fashion.add("Others");





        mechanics.add("All Categories");
        mechanics.add("Mobile Phone");
        mechanics.add("Laptops");
        mechanics.add("Refrigerators");
        mechanics.add("Television");
        mechanics.add("Automobile");
        mechanics.add("Others");


        accountants.add("All Categories");
        accountants.add("Chartered Accountants");
        accountants.add("Tax Consultants");
        accountants.add("Accountants");
        accountants.add("Others");



        transporters.add("All Categories");
        transporters.add("JCB's");
        transporters.add("Trucks");
        transporters.add("Load carriers");
        transporters.add("Tippers");
        transporters.add("Tractors");
        transporters.add("Buses");
        transporters.add("Matadors");
        transporters.add("Others");


        localTransPort.add("All Categories");
        localTransPort.add("Rikshaws");
        localTransPort.add("Cabs");
        localTransPort.add("Others");

        orderSuppliesTypes.add("All Categories");
        orderSuppliesTypes.add("Stores");
        orderSuppliesTypes.add("Malls");
        orderSuppliesTypes.add("Agencies");
        orderSuppliesTypes.add("Others");

    }





    public void setProfileTexts(){
        myProfile = getSharedPreferences(MY_PROFILE_DATA,0);


        String []allCategories = {
                "All Categories"
        };






        String []labours = {
                "All Categories"

        };









        myFullNameV.setText(myProfile.getString("myFullName","Update Your Name "));
        myMainExpertiseV.setText(myProfile.getString("myMainExpertise","Please Update Your Main Expertise"));
        myNoticePeiodV.setText(myProfile.getString("myNoticePeriod","Update Your Notice Period"));

        myFullNameE.setText(myProfile.getString("myFullName","Update Your Name "));
        myMainExpertiseE.setText(myProfile.getString("myMainExpertise","Please Update Your Main Expertise"));


        ArrayAdapter<String> typeAdapter;

        switch (myProfile.getString("myMainProf","Artisans")){


            case "Doctors":
                typeAdapter = new ArrayAdapter<String>(Profile.this, R.layout.spinner_adapter_black, R.id.spinnerTextBlack,doctorsType);
                myProfTypeS.setAdapter(typeAdapter);

                try {
                    myProfTypeS.setSelection(doctorsType.indexOf(myProfile.getString("expertProfType","Artisans")));
                }catch (Exception e){
                    e.printStackTrace();
                }

                break;

            case "Engineers":
                typeAdapter = new ArrayAdapter<String>(Profile.this, R.layout.spinner_adapter_black, R.id.spinnerTextBlack,engineersType);
                myProfTypeS.setAdapter(typeAdapter);

                try {
                    myProfTypeS.setSelection(engineersType.indexOf(myProfile.getString("expertProfType","Artisans")));
                }catch (Exception e){
                    e.printStackTrace();
                }

                break;




            case "Cabs":
                typeAdapter = new ArrayAdapter<String>(Profile.this, R.layout.spinner_adapter_black, R.id.spinnerTextBlack,localTransPort);
                myProfTypeS.setAdapter(typeAdapter);

                try {
                    myProfTypeS.setSelection(localTransPort.indexOf(myProfile.getString("expertProfType","Artisans")));
                }catch (Exception e){
                    e.printStackTrace();
                }



                break;

            case "Transporters":
                typeAdapter = new ArrayAdapter<String>(Profile.this, R.layout.spinner_adapter_black, R.id.spinnerTextBlack,transporters);
                myProfTypeS.setAdapter(typeAdapter);

                try {
                    myProfTypeS.setSelection(transporters.indexOf(myProfile.getString("expertProfType","Artisans")));
                }catch (Exception e){
                    e.printStackTrace();
                }


                break;
            case "Accountants":
                typeAdapter = new ArrayAdapter<String>(Profile.this, R.layout.spinner_adapter_black, R.id.spinnerTextBlack,accountants);
                myProfTypeS.setAdapter(typeAdapter);

                try {
                    myProfTypeS.setSelection(accountants.indexOf(myProfile.getString("expertProfType","Artisans")));
                }catch (Exception e){
                    e.printStackTrace();
                }


                break;
            case "Mechanics":
                typeAdapter = new ArrayAdapter<String>(Profile.this, R.layout.spinner_adapter_black, R.id.spinnerTextBlack,mechanics);
                myProfTypeS.setAdapter(typeAdapter);

                try {
                    myProfTypeS.setSelection(mechanics.indexOf(myProfile.getString("expertProfType","Artisans")));
                }catch (Exception e){
                    e.printStackTrace();
                }


                break;
            case "Fashion":
                typeAdapter = new ArrayAdapter<String>(Profile.this, R.layout.spinner_adapter_black, R.id.spinnerTextBlack,fashion);
                myProfTypeS.setAdapter(typeAdapter);

                try {
                    myProfTypeS.setSelection(fashion.indexOf(myProfile.getString("expertProfType","Artisans")));
                }catch (Exception e){
                    e.printStackTrace();
                }


                break;
            case "Teachers":
                typeAdapter = new ArrayAdapter<String>(Profile.this, R.layout.spinner_adapter_black, R.id.spinnerTextBlack,teacherTypes);
                myProfTypeS.setAdapter(typeAdapter);

                try {
                    myProfTypeS.setSelection(teacherTypes.indexOf(myProfile.getString("expertProfType","Artisans")));
                }catch (Exception e){
                    e.printStackTrace();
                }


                break;

            case "Labours":
                typeAdapter = new ArrayAdapter<String>(Profile.this, R.layout.spinner_adapter_black, R.id.spinnerTextBlack,allCategories);
                myProfTypeS.setAdapter(typeAdapter);

                break;


            case "Software and Services":
                typeAdapter = new ArrayAdapter<String>(Profile.this, R.layout.spinner_adapter_black, R.id.spinnerTextBlack,allCategories);

                myProfTypeS.setAdapter(typeAdapter);

                break;
            case "Lawyers":
                typeAdapter = new ArrayAdapter<String>(Profile.this, R.layout.spinner_adapter_black, R.id.spinnerTextBlack,lawyerTypes);
                myProfTypeS.setAdapter(typeAdapter);

                try {
                    myProfTypeS.setSelection(lawyerTypes.indexOf(myProfile.getString("expertProfType","Artisans")));
                }catch (Exception e){
                    e.printStackTrace();
                }


                break;

            case "Artisans":
                typeAdapter = new ArrayAdapter<String>(Profile.this, R.layout.spinner_adapter_black, R.id.spinnerTextBlack,artizensType);
                myProfTypeS.setAdapter(typeAdapter);

                try {
                    myProfTypeS.setSelection(artizensType.indexOf(myProfile.getString("expertProfType","Artisans")));
                    Log.e("SUCCESS","M HERE "+myProfile.getString("expertProfType","Artisans"));

                }catch (Exception e){
                    e.printStackTrace();
                }


                break;



            case "Media":
                typeAdapter = new ArrayAdapter<String>(Profile.this, R.layout.spinner_adapter_black, R.id.spinnerTextBlack,mediaTypes);
                myProfTypeS.setAdapter(typeAdapter);

                try {
                    myProfTypeS.setSelection(mediaTypes.indexOf(myProfile.getString("expertProfType","Artisans")));
                }catch (Exception e){
                    e.printStackTrace();
                }


                break;
            case "Contractors":
                typeAdapter = new ArrayAdapter<String>(Profile.this, R.layout.spinner_adapter_black, R.id.spinnerTextBlack,contractorTypes);
                myProfTypeS.setAdapter(typeAdapter);

                try {
                    myProfTypeS.setSelection(contractorTypes.indexOf(myProfile.getString("expertProfType","Artisans")));
                }catch (Exception e){
                    e.printStackTrace();
                }


                break;

            case "Health":
                typeAdapter = new ArrayAdapter<String>(Profile.this, R.layout.spinner_adapter_black, R.id.spinnerTextBlack,healthTypes);
                myProfTypeS.setAdapter(typeAdapter);

                try {
                    myProfTypeS.setSelection(healthTypes.indexOf(myProfile.getString("expertProfType","Artisans")));
                }catch (Exception e){
                    e.printStackTrace();
                }


                break;


            case "Agents":
                typeAdapter = new ArrayAdapter<String>(Profile.this, R.layout.spinner_adapter_black, R.id.spinnerTextBlack,agentTypes);
                myProfTypeS.setAdapter(typeAdapter);

                try {
                    myProfTypeS.setSelection(agentTypes.indexOf(myProfile.getString("expertProfType","Artisans")));
                }catch (Exception e){
                    e.printStackTrace();
                }


                break;

            case "Others":
                typeAdapter = new ArrayAdapter<String>(Profile.this, R.layout.spinner_adapter_black, R.id.spinnerTextBlack,otherTypes);
                myProfTypeS.setAdapter(typeAdapter);

                try {
                    myProfTypeS.setSelection(otherTypes.indexOf(myProfile.getString("expertProfType","Artisans")));
                }catch (Exception e){
                    e.printStackTrace();
                }


                break;

            case "General Order Suppliers":
                typeAdapter = new ArrayAdapter<String>(Profile.this, R.layout.spinner_adapter_black, R.id.spinnerTextBlack,orderSuppliesTypes);
                myProfTypeS.setAdapter(typeAdapter);

                try {
                    myProfTypeS.setSelection(orderSuppliesTypes.indexOf(myProfile.getString("expertProfType","Artisans")));
                }catch (Exception e){
                    e.printStackTrace();
                }

                break;






        }
        

        
        
        
        
        
        if (myProfile.getString("expertGender","Update Your Name ").equals("1")){
            myMaleRD.setChecked(true);
            myFemaleRD.setChecked(false);
            genderSrc.setImageResource(R.drawable.male);
            }else{
            myMaleRD.setChecked(false);
            myFemaleRD.setChecked(true);
            genderSrc.setImageResource(R.drawable.female);

        }

        if (myProfile.getString("myNoticePeriod","Part Time").equals("Part Time")){
            partTimeRBP.setChecked(true);
            fullTimeRBP.setChecked(false);
            myNoticePeiodV.setText("Part Time");

        }else{

            partTimeRBP.setChecked(false);
            fullTimeRBP.setChecked(true);
            myNoticePeiodV.setText("Full Time");

        }

    }

    public void setAvailibility(){

        available = myProfile.getString("myStatus","0");

        myTextStatusV.setText(myProfile.getString("myTextStatus","Enter Some Status Here For Intreseted Companies"));

        myTextStatusE.setText(myProfile.getString("myTextStatus","Enter Some Status Here For Intreseted Companies"));

        if (myProfile.getString("myStatus","9").equals("1")){
            myStatusSrc.setImageResource(R.drawable.available);
            myStatusV.setText("Available");
            availibilitySwitch.setChecked(true);
        }else{
            myStatusV.setText("Not Available");
            myStatusSrc.setImageResource(R.drawable.not_available_now);
            availibilitySwitch.setChecked(false);
        }


        availibilitySwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    available = "1";
                }else {
                    available = "0";
                }

            }
        });



    }

    public  void setState(){

        myProfile = getSharedPreferences(MY_PROFILE_DATA,0);
        myCurrentStateV.setText(myProfile.getString("myState","Select a State Here..."));
        myCurrentCityV.setText(myProfile.getString("myCurrentCity","Enter Your City"));
        myCurrentCityE.setText(myProfile.getString("myCurrentCity","Enter City here..."));
        myCurrentStateS.setSelection(statesArray.indexOf(myProfile.getString("myState","Jammu and Kashmir")));




    }

    public void setSkills(){


        String[] myEnteredSkills  = myProfile.getString("mySkills","No Skills Mentioned Yet").split(",");
        myEnteredSkillsV.setText(myProfile.getString("mySkills","Please mention Your Skills Here.."));
        myEnteredSkillsE.setText(myProfile.getString("mySkills","Please mention Your Skills Here.."));


    }

    public void setBio(){

        myShortBioV.setText(myProfile.getString("myShortBio","PLease Enter Some Cool Bio Of Yours"));
        myShortBioE.setText(myProfile.getString("myShortBio","PLease Enter Some Cool Bio Of Yours"));


    }

    public void setCall(){

        myCallV.setText(myProfile.getString("myCall","Enter Your Number"));
        myCallE.setText(myProfile.getString("myCall","Enter Your Number"));
    }

    public void setEmail(){

        myMailV.setText(myProfile.getString("myEmail","Enter Your Email"));
        myMailE.setText(myProfile.getString("myEmail","Enter Your Email"));
    }

    void setVisibility(){



        myProfile = getSharedPreferences(MY_PROFILE_DATA,0);

        if (myProfile.getString("visibility","1").equals("1")){

            publicRB.setChecked(true);
            companiesRB.setChecked(false);

        }else if (myProfile.getString("visibility","1").equals("0")){

            publicRB.setChecked(false);
            companiesRB.setChecked(true);


        }




    }


    public void visitProfileSetters(){


           // profile NAMEEXPERTISE TAGS

        myFullNameV = (TextView) findViewById(R.id.myFullNameV);
        myMainExpertiseV = (TextView)findViewById(R.id.myMainExpertiseV);
        myNoticePeiodV = (TextView)findViewById(R.id.myNoticePeriodV);
        genderSrc =  (ImageView)findViewById(R.id.myGenderSrc);

        myFullNameE = (EditText) findViewById(R.id.myFullNameE);
        myMainExpertiseE = (EditText) findViewById(R.id.myMainExpertiseE);
        myCurrentCityE  =(EditText) findViewById(R.id.myCurrentCityE);
        myCurrentCityV = (TextView) findViewById(R.id.myCurrentCityV);
        imageProgress = (LinearLayout)findViewById(R.id.imageProgress);
        myProfTypeS = (Spinner)findViewById(R.id.myNoticePeriodS);


        myMaleRD = (RadioButton) findViewById(R.id.myMaleRD);
        myFemaleRD = (RadioButton) findViewById(R.id.myFemaleRD);

        partTimeRBP = (RadioButton) findViewById(R.id.partTimeRBP);
        fullTimeRBP = (RadioButton) findViewById(R.id.fullTimeRBP);

        partTimeRBP.setOnClickListener(this);
        fullTimeRBP.setOnClickListener(this);


        myMaleRD.setOnClickListener(this);
        myFemaleRD.setOnClickListener(this);

        // Availibility


//        TextView myTextStatusV,myStatusV,myStatusSrc;
//        EditText myTextStatusE;

            myTextStatusV = (TextView)findViewById(R.id.myTextStatusV);
            myStatusV = (TextView)findViewById(R.id.myStatusV);
            myStatusSrc = (ImageView)findViewById(R.id.statusSrc);
            myTextStatusE = (EditText)findViewById(R.id.myTextStatusE);


        // Current State
            myCurrentStateS  = (Spinner) findViewById(R.id.myCurrentAddressS);
            ArrayAdapter<String> statesType;
            statesType = new ArrayAdapter<String>(Profile.this, R.layout.spinner_adapter_black, R.id.spinnerTextBlack,statesArray);
            myCurrentStateS.setAdapter(statesType);
            myCurrentStateV = (TextView)findViewById(R.id.myCurrentStateV);


            // Skills

        myEnteredSkillsE = (EditText)findViewById(R.id.myEnteredSkillsE);
        myEnteredSkillsV = (TextView)findViewById(R.id.myEnteredSkillsV);




        // Bios

        myShortBioV = (TextView)findViewById(R.id.myShortBioV);
        myShortBioE = (EditText)findViewById(R.id.myShortBioE);


        // Emails and calls

        myCallV = (TextView)findViewById(R.id.myCallV);
        myCallE = (EditText) findViewById(R.id.myCallE);



        myMailV = (TextView)findViewById(R.id.myEmailV);
        myMailE = (EditText)findViewById(R.id.myEmailE);





    }

    boolean checkConnection(){

        connectivityManager = (ConnectivityManager)  Profile.this.getSystemService(CONNECTIVITY_SERVICE);
        networkInfo = connectivityManager.getActiveNetworkInfo();

        if(networkInfo !=null && networkInfo.isConnected()){
            return true;

        } else {
            return false;
        }

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {


                case   R.id.selectFromGallery:
                imagePermissionItem(v);
                break;


            case R.id.companiesRB:
                visibilty = "0";
                publicRB.setChecked(false);
                companiesRB.setChecked(true);
                new UpdateVisibility().execute();
                break;

            case R.id.publicRB:
                publicRB.setChecked(true);
                companiesRB.setChecked(false);
                visibilty = "1";
                new UpdateVisibility().execute();

                break;

            case R.id.myMaleRD:
                myMaleRD.setChecked(true);
                myFemaleRD.setChecked(false);
                gender = "1";

                break;

            case R.id.myFemaleRD:
                myMaleRD.setChecked(false);
                myFemaleRD.setChecked(true);
                gender = "0";

                break;



            case R.id.partTimeRBP:
                partTimeRBP.setChecked(true);
                fullTimeRBP.setChecked(false);
                noticePeriod = "Part Time";

                break;

            case R.id.fullTimeRBP:
                partTimeRBP.setChecked(false);
                fullTimeRBP.setChecked(true);
                noticePeriod = "Full Time";
                break;






        }

    }



    class UpdateFirstCake extends AsyncTask<String , String, String>{


        StringBuilder builder = new StringBuilder();
        List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>();
        BufferedReader bufferedReader;
        String PROF_SUBTYPE,FULLNAME,MAINEXPERTISE;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();


            infoPannelFlipper.showNext();
            myFullNameE.setEnabled(false);
            myMainExpertiseE.setEnabled(false);
            myProfTypeS.setEnabled(false);
            myGenderS.setEnabled(false);
            PROF_SUBTYPE = myProfTypeS.getSelectedItem().toString();
            FULLNAME = myFullNameE.getText().toString();
            MAINEXPERTISE =  myMainExpertiseE.getText().toString();


        }



        protected String doInBackground(String... params){

            nameValuePair.add(new BasicNameValuePair("userPhone",USER_EMAIL));
            nameValuePair.add(new BasicNameValuePair("expertProfType",PROF_SUBTYPE));
            Log.e("Failure",gender);
            nameValuePair.add(new BasicNameValuePair("expertGender",gender));
            nameValuePair.add(new BasicNameValuePair("expertName",FULLNAME));
            nameValuePair.add(new BasicNameValuePair("expertExpertise",MAINEXPERTISE));
            nameValuePair.add(new BasicNameValuePair("myNoticePeriod",noticePeriod));


            try{


                HttpClient  httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost(getString(R.string.host)+"/myprofile/updatename_tag.php");

                httpPost.setEntity(new UrlEncodedFormEntity(nameValuePair));

                HttpResponse httpResponse = (HttpResponse) httpClient.execute(httpPost);

                HttpEntity httpEntity = (HttpEntity) httpResponse.getEntity();

                bufferedReader = new BufferedReader(new InputStreamReader(httpEntity.getContent()));
                String str = "";

                while( (str = bufferedReader.readLine()) != null ){

                    builder.append(str);

                }



            }catch(Exception e){

            }


            return builder.toString();

        }


        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            updateEditor = myProfile.edit();

Log.e("RESULt","ompost"+s);
            if (s.equals("1")){

                infoPannelFlipper.showNext();
                infoFlipper.showNext();
                myFullNameE.setEnabled(true);
                myMainExpertiseE.setEnabled(true);
                myProfTypeS.setEnabled(true);
                myGenderS.setEnabled(true);

                updateEditor.putString("expertGender",gender);
                Log.e("RESULt",gender);

                updateEditor.putString("myFullName",myFullNameE.getText().toString());
                updateEditor.putString("expertProfType",myProfTypeS.getSelectedItem().toString());
                updateEditor.putString("myMainExpertise",myMainExpertiseE.getText().toString());
                updateEditor.putString("myNoticePeriod",noticePeriod);
                updateEditor.commit();

                setProfileTexts();


            }else{
                Toast.makeText(Profile.this, "We are having trouble connecting to the Internet, Please Try again Later", Toast.LENGTH_SHORT).show();
                infoPannelFlipper.showPrevious();
                myFullNameE.setEnabled(true);
                myMainExpertiseE.setEnabled(true);
                myProfTypeS.setEnabled(true);
                myGenderS.setEnabled(true);


            }









        }
    }
    class UpdatePhone extends AsyncTask< String, String, String >{


        StringBuilder builder = new StringBuilder();
        BufferedReader bufferedReader;
        List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>();
        String CALL_PHONE;


        @Override 
        public void onPreExecute() {
            super.onPreExecute();


            callPannelFlipper.showNext();
            myCallE.setEnabled(false);
            CALL_PHONE = myCallE.getText().toString();

        }



        @Override 
        public void onPostExecute( String s ){
            super.onPostExecute( s );

            updateEditor = myProfile.edit();

            if (s.equals("1")){

                callFlipper.showNext();
                callPannelFlipper.showNext();
                myCallE.setEnabled(true);
                updateEditor.putString("myCall",myCallE.getText().toString());
                updateEditor.commit();
                setCall();


            }else {
                Toast.makeText(Profile.this, "We are having trouble Connecting to the Internet, Please try again", Toast.LENGTH_SHORT).show();
                callPannelFlipper.showPrevious();
                myCallE.setEnabled(true);

            }




        }


        @Override 
        protected String doInBackground( String... params ){

            nameValuePair.add(new BasicNameValuePair("userPhone",USER_EMAIL));
            nameValuePair.add(new BasicNameValuePair("myCall",CALL_PHONE));


                try{


                    HttpClient  httpClient = new DefaultHttpClient();
                    HttpPost httpPost = new HttpPost(getString(R.string.host)+"/myprofile/update_phone.php");

                    httpPost.setEntity(new UrlEncodedFormEntity(nameValuePair));

                    HttpResponse httpResponse = (HttpResponse) httpClient.execute(httpPost);

                    HttpEntity httpEntity = (HttpEntity) httpResponse.getEntity();

                    bufferedReader = new BufferedReader(new InputStreamReader(httpEntity.getContent()));
                    String str = "";

                    while( (str = bufferedReader.readLine()) != null ){

                        builder.append(str);

                    }



                }catch(Exception e){

                }


                    return builder.toString();

        }

    }
    class UpdateEmail extends AsyncTask< String, String, String > {


        StringBuilder builder = new StringBuilder();
        BufferedReader bufferedReader;
        List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>();
        String MY_MAIL;



        @Override 
        public void onPreExecute(){
            super.onPreExecute();


            emailPannelFlipper.showNext();
            myMailE.setEnabled(false);
            MY_MAIL = myMailE.getText().toString();

        }



        @Override 
        public void onPostExecute( String s ){
            super.onPostExecute( s );

            updateEditor = myProfile.edit();

            if (s.equals("1")){

                emailFlipper.showNext();
                emailPannelFlipper.showNext();
                myMailE.setEnabled(true);
                updateEditor.putString("myEmail",myMailE.getText().toString());
                updateEditor.commit();
                setEmail();


            }else {
                Toast.makeText(Profile.this, "We are having trouble Connecting to the Internet, Please try again", Toast.LENGTH_SHORT).show();
                emailPannelFlipper.showPrevious();
                myMailE.setEnabled(true);

            }




        }

        
        @Override 
        protected String doInBackground( String... params ){


            nameValuePair.add(new BasicNameValuePair("userPhone",USER_EMAIL));
            nameValuePair.add(new BasicNameValuePair("expertEmail",MY_MAIL));


                try{


                    HttpClient  httpClient = new DefaultHttpClient();
                    HttpPost httpPost = new HttpPost(getString(R.string.host)+"/myprofile/update_email.php");

                    httpPost.setEntity(new UrlEncodedFormEntity(nameValuePair));

                    HttpResponse httpResponse = (HttpResponse) httpClient.execute(httpPost);

                    HttpEntity httpEntity = (HttpEntity) httpResponse.getEntity();

                    bufferedReader = new BufferedReader(new InputStreamReader(httpEntity.getContent()));
                    String str = "";

                    while( (str = bufferedReader.readLine()) != null ){

                        builder.append(str);

                    }



                }catch(Exception e){

                }


                    return builder.toString();

        }

    }
    class UpdateBio extends AsyncTask< String, String, String >{


        StringBuilder builder = new StringBuilder();
        BufferedReader bufferedReader;
        List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>();
        String MY_BIO;


        @Override 
        public void onPreExecute(){
            super.onPreExecute();


            bioPannelFlipper.showNext();
            myShortBioE.setEnabled(false);
            MY_BIO = myShortBioE.getText().toString();


        }



        @Override 
        public void onPostExecute( String s ){
            super.onPostExecute( s );
            updateEditor = myProfile.edit();

            if (s.equals("1")){
                bioPannelFlipper.showNext();
                bioFlipper.showNext();
                myShortBioE.setEnabled(true);
                updateEditor.putString("myShortBio",myShortBioE.getText().toString());
                updateEditor.apply();
                setBio();

            }else{
                Toast.makeText(Profile.this, "We are having trouble Connecting to the Internet, Please try again", Toast.LENGTH_SHORT).show();
                bioPannelFlipper.showPrevious();
                myShortBioE.setEnabled(true);
            }




        }

        
        @Override 
        protected String doInBackground( String... params ){


            nameValuePair.add(new BasicNameValuePair("userPhone",USER_EMAIL));
            nameValuePair.add(new BasicNameValuePair("expertBio",MY_BIO));


                try{


                    HttpClient httpClient = new DefaultHttpClient();
                    HttpPost httpPost = new HttpPost(getString(R.string.host)+"/myprofile/update_bio.php");

                    httpPost.setEntity(new UrlEncodedFormEntity(nameValuePair));

                    HttpResponse httpResponse = (HttpResponse) httpClient.execute(httpPost);

                    HttpEntity httpEntity = (HttpEntity) httpResponse.getEntity();

                    bufferedReader = new BufferedReader(new InputStreamReader(httpEntity.getContent()));
                    String str = "";

                    while( (str = bufferedReader.readLine()) != null ){

                        builder.append(str);

                    }



                }catch(Exception e){

                }


                    return builder.toString();



        }




    }
    class UpdateSkills extends AsyncTask< String, String, String >{


        StringBuilder builder = new StringBuilder();
        BufferedReader bufferedReader;
        List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>();
        String MY_SKILLS;


        @Override 
        public void onPreExecute(){
            super.onPreExecute();

            myEnteredSkillsE.setEnabled(false);
            skillPannelFlipper.showNext();
            MY_SKILLS = myEnteredSkillsE.getText().toString();

        }



        @Override 
        public void onPostExecute( String s ){
            super.onPostExecute( s );
            updateEditor = myProfile.edit();

            if (s.equals("1")){

                myEnteredSkillsE.setEnabled(true);
                skillPannelFlipper.showNext();
                skillFlipper.showNext();
                updateEditor.putString("mySkills",myEnteredSkillsE.getText().toString());
                updateEditor.commit();
                setSkills();


            }else {

                Toast.makeText(Profile.this, "We are having trouble Connecting to the Internet, Please try again", Toast.LENGTH_SHORT).show();
                skillPannelFlipper.showPrevious();
                myEnteredSkillsE.setEnabled(true);

            }

        }

        
        @Override 
        protected String doInBackground( String... params ){


            nameValuePair.add(new BasicNameValuePair("userPhone",USER_EMAIL));
            nameValuePair.add(new BasicNameValuePair("expertSkills",MY_SKILLS));


                try{


                    HttpClient  httpClient = new DefaultHttpClient();

                    HttpPost httpPost = new HttpPost(getString(R.string.host)+"/myprofile/update_skills.php");
                    httpPost.setEntity(new UrlEncodedFormEntity(nameValuePair));

                    HttpResponse httpResponse = (HttpResponse) httpClient.execute(httpPost);

                    HttpEntity httpEntity = (HttpEntity) httpResponse.getEntity();

                    bufferedReader = new BufferedReader(new InputStreamReader(httpEntity.getContent()));
                    String str = "";

                    while( (str = bufferedReader.readLine()) != null ){

                        builder.append(str);

                    }



                }catch(Exception e){

                }


                    return builder.toString();

        }

    }
    class UpdateAddress extends AsyncTask< String, String, String >{


        StringBuilder builder = new StringBuilder();
        BufferedReader bufferedReader;
        List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>();
        String MY_CITY,MY_ADDRESS;


        @Override 
        public void onPreExecute(){
            super.onPreExecute();


            locationPannelFlipper.showNext();
            myCurrentStateS.setEnabled(false);
            MY_CITY = myCurrentCityE.getText().toString();
            MY_ADDRESS = myCurrentStateS.getSelectedItem().toString();


        }



        @Override 
        public void onPostExecute( String s ){
            super.onPostExecute( s );
            updateEditor = myProfile.edit();
            if (s.equals("1")){
                locationFlipper.showNext();
                locationPannelFlipper.showNext();
                updateEditor.putString("myState",myCurrentStateS.getSelectedItem().toString());
                updateEditor.putString("myCurrentCity",myCurrentCityE.getText().toString());
                updateEditor.commit();
                myCurrentStateS.setEnabled(true);

            }else{
                Toast.makeText(Profile.this, "We are having trouble Connecting to the Internet, Please try again", Toast.LENGTH_SHORT).show();
                myCurrentStateS.setEnabled(true);
                locationPannelFlipper.showPrevious();
            }

            try {


            }catch (Exception e){
                e.printStackTrace();
            }
            setState();

        }

        
        @Override 
        protected String doInBackground( String... params ){


            nameValuePair.add(new BasicNameValuePair("userPhone",USER_EMAIL));
            nameValuePair.add(new BasicNameValuePair("expertCity",MY_CITY));
            nameValuePair.add(new BasicNameValuePair("expertAddress",MY_ADDRESS));


                try{


                    HttpClient  httpClient = new DefaultHttpClient();
                    HttpPost httpPost = new HttpPost(getString(R.string.host)+"/myprofile/update_address.php");

                    httpPost.setEntity(new UrlEncodedFormEntity(nameValuePair));

                    HttpResponse httpResponse = (HttpResponse) httpClient.execute(httpPost);

                    HttpEntity httpEntity = (HttpEntity) httpResponse.getEntity();


                    bufferedReader = new BufferedReader(new InputStreamReader(httpEntity.getContent()));
                    String str = "";

                    while( (str = bufferedReader.readLine()) != null ){

                        builder.append(str);

                    }



                }catch(Exception e){

                }


                    return builder.toString();

        }




    }
    class UpdateStatus extends AsyncTask< String, String, String >{


        StringBuilder builder = new StringBuilder();
        BufferedReader bufferedReader;
        List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>();
        String TEXT_STATUS;

        protected String doInBackground( String... params){

            nameValuePair.add(new BasicNameValuePair("userPhone",USER_EMAIL));
            nameValuePair.add(new BasicNameValuePair("myTextStatus",TEXT_STATUS));
            nameValuePair.add(new BasicNameValuePair("myStatus",available));


            try{

                HttpClient httpClient  = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost(getString(R.string.host)+"/myprofile/update_status.php");
                httpPost.setEntity(new UrlEncodedFormEntity(nameValuePair));

                HttpResponse httpResponse = (HttpResponse) httpClient.execute(httpPost);

                HttpEntity httpEntity = httpResponse.getEntity();


                bufferedReader = new BufferedReader(new InputStreamReader(httpEntity.getContent()));
                String str = "";

                while( (str = bufferedReader.readLine()) != null ){

                    builder.append(str);

                }







            }catch(Exception e){

            }



            return builder.toString();
        }


        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            statusPannelFlipper.showNext();
            myTextStatusE.setEnabled(false);
            availibilitySwitch.setEnabled(false);
            TEXT_STATUS = myTextStatusE.getText().toString();

        }

        public void onPostExecute(String s){
            super.onPostExecute(s);

            updateEditor = myProfile.edit();
            if (s.equals("1")){

                statusFlipepr.showNext();
                statusPannelFlipper.showNext();
                myTextStatusE.setEnabled(true);
                availibilitySwitch.setEnabled(true);
                updateEditor.putString("myStatus",available);
                updateEditor.putString("myTextStatus",myTextStatusE.getText().toString());
                updateEditor.commit();
                setAvailibility();

            }else{
                Toast.makeText(Profile.this, "We are having trouble Connecting to the Internet, Please try again", Toast.LENGTH_SHORT).show();
                statusPannelFlipper.showPrevious();
                myTextStatusE.setEnabled(true);
                availibilitySwitch.setEnabled(true);

            }





        }



    } 
    class UpdateVisibility extends AsyncTask<String,String,String> {


        StringBuilder builder = new StringBuilder();
        List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>();
        BufferedReader bufferedReader;



        protected String doInBackground(String... params){

            nameValuePair.add(new BasicNameValuePair("userPhone",USER_EMAIL));
            nameValuePair.add(new BasicNameValuePair("visibility",visibilty));


            try{

                HttpClient httpClient  = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost(getString(R.string.host)+"/myprofile/update_visibility.php");
                httpPost.setEntity(new UrlEncodedFormEntity(nameValuePair));

                HttpResponse httpResponse = (HttpResponse) httpClient.execute(httpPost);

                HttpEntity httpEntity = httpResponse.getEntity();


                bufferedReader = new BufferedReader(new InputStreamReader(httpEntity.getContent()));
                String str = "";

                while( (str = bufferedReader.readLine()) != null ){

                    builder.append(str);

                }


            }catch(Exception e){

            }



            return builder.toString();


        }


        protected void onPreExecute(){
            super.onPreExecute();



        }


        protected void onPostExecute(String s){
            super.onPostExecute(s);

            updateEditor = myProfile.edit();

            if (s.contains("1")){

                if (visibilty.equals("1")){

                    updateEditor.putString("visibility","1");
                    updateEditor.apply();

                    publicRB.setChecked(true);
                    companiesRB.setChecked(false);

                }else if (visibilty.equals("0") ){

                    updateEditor.putString("visibility","0");
                    updateEditor.apply();
                    publicRB.setChecked(false);
                    companiesRB.setChecked(true);

                }

            }else{
                Toast.makeText(Profile.this, "We are having trouble Connecting to the Internet, Please try again Later"+s, Toast.LENGTH_SHORT).show();

            }
            // need to reset visibilty here
            setVisibility();


        }






    }
    class UpdateExperience extends AsyncTask< String, String, String >{


        StringBuilder builder = new StringBuilder();
        BufferedReader bufferedReader;
        List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>();
        String EXPY,EXPM,YOBE;


        @Override
        public void onPreExecute(){
            super.onPreExecute();


            experiencePannelFlipper.showNext();
            myYOBE.setEnabled(false);
            myExperienceMonthsE.setEnabled(false);
            myExperienceE.setEnabled(false);
            EXPM = myExperienceMonthsE.getSelectedItem().toString();
            EXPY = myExperienceE.getSelectedItem().toString();
            YOBE = myYOBE.getSelectedItem().toString();

        }



        @Override
        public void onPostExecute( String s ){
            super.onPostExecute( s );
                updateEditor = myProfile.edit();

            if (s.equals("1")){
                experienceFlipper.showNext();
                experiencePannelFlipper.showNext();
                updateEditor.putString("myAge",myYOBE.getSelectedItem().toString());
                updateEditor.putString("myExperience",myExperienceE.getSelectedItem().toString());
                updateEditor.putString("myMonthsExperience",myExperienceMonthsE.getSelectedItem().toString());
                updateEditor.commit();
                myYOBE.setEnabled(true);
                myExperienceMonthsE.setEnabled(true);
                myExperienceE.setEnabled(true);

                setExperience();

            }else{
                experiencePannelFlipper.showPrevious();
                Toast.makeText(Profile.this, "Connection Slow Please try Again", Toast.LENGTH_SHORT).show();
                myYOBE.setEnabled(true);
                myExperienceMonthsE.setEnabled(true);
                myExperienceE.setEnabled(true);

            }


        }


        @Override
        protected String doInBackground( String... params ){

            nameValuePair.add(new BasicNameValuePair("userPhone",USER_EMAIL));
            nameValuePair.add(new BasicNameValuePair("myAge",YOBE));
            nameValuePair.add(new BasicNameValuePair("myExperience",EXPY));
            nameValuePair.add(new BasicNameValuePair("myExperienceMonths",EXPM));


            try{


                HttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost(getString(R.string.host)+"/myprofile/update_experience.php");
                httpPost.setEntity(new UrlEncodedFormEntity(nameValuePair));

                HttpResponse httpResponse = (HttpResponse) httpClient.execute(httpPost);

                HttpEntity httpEntity = (HttpEntity) httpResponse.getEntity();

                bufferedReader = new BufferedReader(new InputStreamReader(httpEntity.getContent()));
                String str = "";

                while( (str = bufferedReader.readLine()) != null ){

                    builder.append(str);

                }



            }catch(Exception e){

            }


            return builder.toString();



        }




    }







    public void imagePermissionItem(View view) {
        if (ContextCompat.checkSelfPermission(Profile.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(Manifest.permission.READ_EXTERNAL_STORAGE);
        }
        else {
//            if(isInternetPresent) {
            selectImage();
//            }else
//            {
//                Toast.makeText(getActivity(), "You are not connected to internet", Toast.LENGTH_SHORT).show();
//                return;
//            }
        }
    }
    public void selectImage() {
        if(Build.VERSION.SDK_INT < 18)
        {
            imageIntent = new Intent(Intent. ACTION_GET_CONTENT , android.provider.MediaStore.Images.Media. EXTERNAL_CONTENT_URI);
            imageIntent.setType("image/*");
            imageIntent.putExtra("return-data", true);
            startActivityForResult(Intent.createChooser(imageIntent,"Choose image"), GALLERY_PICTURE);
        }
        else if (Build.VERSION.SDK_INT < 19){
            imageIntent = new Intent(Intent.ACTION_PICK_ACTIVITY);
            imageIntent.setType("image/*");
            imageIntent.putExtra("return-data", true);
            startActivityForResult(Intent.createChooser(imageIntent,"Choose image"), GALLERY_PICTURE);
        } else {
            imageIntent = new Intent();
            imageIntent.setType("image/*");
            imageIntent.setAction(Intent.ACTION_PICK);
            startActivityForResult(Intent.createChooser(imageIntent,
                    "Choose image"), GALLERY_PICTURE);
            //  startActivityForResult(Intent.createChooser(com.xpertouchsoftware.app.xpertouch.Utility.pictureActionIntent,"Choose image"), com.xpertouchsoftware.app.xpertouch.Utility.GALLERY_PICTURE);
        }


    }

    // After the selection of image you will retun on the main activity with bitmap image
    ////////////////////////////////////////////////////////////////////////////////////////////////
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GALLERY_PICTURE) {
            // calling function for saving gallery image on xpertouch data folder on users device
            if(resultCode == RESULT_OK)
            {
                Image_Selecting_Task(data.getData());
            }
            else
            {
                return;
            }


        }else if (requestCode == REQUEST_IMAGE_CROP && resultCode == RESULT_OK){
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap)extras.get("data");
            imagePreview.setVisibility(View.VISIBLE);
            imagePreview.setImageBitmap(imageBitmap);
            myBitmap = imageBitmap;

        }

    }

    //take image from gallery and crop
    ////////////////////////////////////////////////////////////////////////////////////////////////
    public void Image_Selecting_Task(Uri data) {
        if(data == null)
        {
            return;
        }
        Uri selectedImageUri = data;



        try {

            String selectedImagePath = getPath(selectedImageUri);

            myBitmap = BitmapFactory.decodeFile(selectedImagePath);
            imagePreview.setVisibility(View.VISIBLE);
            ImageView image = (ImageView) findViewById(R.id.imagePreview);
            image.setImageBitmap(myBitmap);
        }
        catch(Exception e) {
            Log.e("StatusBitmap", e.toString());
        }

//        try {
//
//            Intent cropIntent = new Intent("com.android.camera.action.CROP");
//
//            cropIntent.setDataAndType(selectedImageUri, "image/*");
//            cropIntent.putExtra("crop", "true");
//            cropIntent.putExtra("aspectX", 1);
//            cropIntent.putExtra("aspectY", 1);
//            cropIntent.putExtra("outputX", 640);
//            cropIntent.putExtra("outputY", 640);
//            cropIntent.putExtra("return-data", true);
//            startActivityForResult(cropIntent, REQUEST_IMAGE_CROP);
//        }
//        // respond to users whose devices do not support the crop action
//        catch (ActivityNotFoundException anfe) {
//            // display an error message
//            String errorMessage = "Whoops - your device doesn't support the crop action!";
//            Toast toast = Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT);
//            toast.show();
//
//            imagePreview.setVisibility(View.VISIBLE);
//            ImageView image = (ImageView) findViewById(R.id.imagePreview);
//            image.setImageBitmap(myBitmap);
//        }



    }
    /**
     * helper to retrieve the path of an image URI
     */

    public String getPath(Uri uri) {

        // just some safety built in
        if( uri == null ) {
            // perform some logging or show user feedback
            Toast.makeText(Profile.this,"Failed to get image", Toast.LENGTH_LONG).show();
            return null;
        }

        // try to retrieve the image from the media store first
        // this will only work for images selected from gallery
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = Profile.this.getContentResolver().query(uri, projection, null, null, null);
        if( cursor != null ){
            int column_index = cursor
                    .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        }
        // this is our fallback here, thanks to the answer from @mad indicating this is needed for
        // working code based on images selected using other file managers
        return uri.getPath();
    }

    // Called when the user is performing an action which requires the app to read the
    // user's gallery
    @TargetApi(23)
    public void requestPermissions(String... permission){

        if(ContextCompat.checkSelfPermission(Profile.this, permission[0]) != PackageManager.PERMISSION_GRANTED)
        {
            if(permission[0].equals(Manifest.permission.READ_EXTERNAL_STORAGE))
            {
                requestPermissions(new String[]{permission[0]}, READ_GALLERY_PERMISSIONS_REQUEST);
            }

        }
    }

    // Callback with the request from calling requestPermissions(...)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults)
    {

        if(requestCode == READ_GALLERY_PERMISSIONS_REQUEST) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(Profile.this,
                    Manifest.permission.READ_EXTERNAL_STORAGE)) {

            }
            if (grantResults.length == 1 &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                selectImage();

            } else {
                Toast.makeText(Profile.this, "You have restricted "+getString(R.string.app_name)+" app to access to gallery", Toast.LENGTH_SHORT).show();

                android.support.v7.app.AlertDialog alertDialog = null;
                android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(Profile.this,R.style.AppTheme);
                builder.setTitle("Want to select image from gallery");
                builder.setMessage("Allow "+getString(R.string.app_name)+" app to select images from gallery. This enables you to change your profile picture. " +
                        "Go to Settings to turn on Gallery Access.\n\nTo enable this, click "+getString(R.string.app_name)+" App Settings below and activate Storage under the permissions menu.");

                builder.setPositiveButton("App Settings", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent();
                        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        Uri uri = Uri.fromParts("package", Profile.this.getPackageName(), null);
                        intent.setData(uri);
                        startActivity(intent);
                    }
                });

                builder.setNegativeButton("Not Now", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                alertDialog = builder.create();
                alertDialog.show();

            }
        }

        else
        {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    public void sendImage(View view) {

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        myBitmap.compress(Bitmap.CompressFormat.JPEG, 90, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream .toByteArray();
        base64= new String(Base64.encodeBase64(byteArray));

        new UpdateImage().execute();

    }

    class UpdateImage extends AsyncTask<String,String,String>{

        StringBuilder builder = new StringBuilder();
        BufferedReader bufferedReader;
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            imageProgress.startAnimation(fadeIn);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                   imageProgress.setVisibility(View.VISIBLE);
                }
            },300);
            imagePannelFlipper.showNext();

        }

        @Override
        protected void onPostExecute(final String s) {
            super.onPostExecute(s);



            if (s.contains("expert")){

                imageProgress.startAnimation(fadeOut);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        imageProgress.setVisibility(View.GONE);
                    }
                },300);
                // reloading the image here #code...
                myProfile = getSharedPreferences(MY_PROFILE_DATA,0);

                updateEditor = myProfile.edit();
                // play some great sound here
                final MediaPlayer ourSplasSound =  MediaPlayer.create(Profile.this,R.raw.notific);
                ourSplasSound.start();



                NotificationCompat.Builder builder = new NotificationCompat.Builder(Profile.this);
                builder.setContentTitle("Profile Picture updated");
                builder.setContentText("Your new Profile Picture has been successfully updated");
                builder.setSmallIcon(R.drawable.verified_user);
                builder.setTicker("Profile Picture updated Successfully");
                builder.setAutoCancel(true);

                Notification notification = builder.build();
                NotificationManager notificationManager = (NotificationManager) Profile.this.getSystemService(NOTIFICATION_SERVICE);
                try{
                    notificationManager.notify(1234,notification);
                }catch (Exception e){
                    e.printStackTrace();
                }

                updateEditor.putString("myProfilePic",s);
                updateEditor.apply();
                setImage();
                imagePannelFlipper.showNext();
            }else if (s.contains("no64")){

                imageProgress.startAnimation(fadeOut);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        imageProgress.setVisibility(View.GONE);
                    }
                },500);
                Toast.makeText(Profile.this, "We are having Trouble Uploading your image, Please try again later", Toast.LENGTH_SHORT).show();
                imagePannelFlipper.showPrevious();

            }else {
                new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                imageProgress.startAnimation(fadeOut);
                new Handler().postDelayed(new Runnable() {

                    @Override
            public void run() {
                imageProgress.setVisibility(View.GONE);
                }
        },300);


                    if (s.contains("Large")){
                        Toast.makeText(Profile.this, "File Size is Too Large, file size should be approx. less than 1 MB", Toast.LENGTH_SHORT).show();

                    }else {
                        Toast.makeText(Profile.this, "We are having trouble connecting to the internet, Please check your Connection ", Toast.LENGTH_SHORT).show();

                    }



                }
             },1000);
            }
        }




        @Override
        protected String doInBackground(String... params) {


            nameValuePairs.add(new BasicNameValuePair("userPhone",USER_EMAIL));
            nameValuePairs.add(new BasicNameValuePair("base64",base64));


            try{

                HttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost(getString(R.string.host)+"/myprofile/update_pic.php");
                httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                HttpResponse httpResponse = (HttpResponse) httpClient.execute(httpPost);
                HttpEntity httpEntity = (HttpEntity)httpResponse.getEntity();
                bufferedReader = new BufferedReader(new InputStreamReader(httpEntity.getContent()));
                String str = "";

                while( (str = bufferedReader.readLine()) != null ){
                    builder.append(str);
                }
            }catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }catch (Exception e){

            }


            return builder.toString();

        }










    }


}







