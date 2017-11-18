package corp.burenz.expertouch.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import corp.burenz.expertouch.R;

public class XpertRegistration extends AppCompatActivity  implements  View.OnClickListener{


    String MY_PROFILE_DATA = "myProfileInfo";
    SharedPreferences userData,myProfile;
    SharedPreferences.Editor editor,myProfileEditor;
    Animation cardAnimation;
    String LOCAL_APP_DATA = "userInformation";



    // Tricky Layout

    LinearLayout trickyLayout;
    TextView trickyTitle,trickySubtitle;
    Button iAccept;
    ViewFlipper trickyFlipper;

    ViewFlipper xpertWelcomeFlipper,xpertImagesFlipper,xpertButtonsFlipper,xpertSubtitleFlipper,xpertEditTextFlippers;
    EditText fullNameE, mainExpertiseE,myCityE,textStatusE,expertBioE,expertCallE ,expertMailE ,expertSkillsE;
    Spinner expertAgeE;
    Spinner expertStateE,experienceYears,experienceMonths,mainProfS,profSubS;

    Button fullNameB,mainExpertiseB,noticePeriodB,textStatusB,expertAgeB,expertBioB,expertCallB,expertMailB,expertSkillsB,expertExperienceB;
    Button genderB,statusB,expertStateB,mainProfB,profSubB,myCityB;

    RadioButton maleSelection,femaleSelection;
    RadioButton availableR,notAvailableR;
    RadioButton partTime,fullTime;
    String status,mainExpertise,noticePeriod,textStatus,expertAge,expertBio,expertCall,expertMail,experience,expertState;
    String gender = "1";






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xpert_registration);

        initViews();

        setBasics();

        final String[] experience = {
                "Select Your Experience", "sfvsfsf",
                "sfvsfsf",
                "sfvsfsf",
                "sfvsfsf",
                "sfvsfsf",
                "sfvsfsf",
                "sfvsfsf",
                "sfvsfsf",
                "sfvsfsf"
        };



        SpinnerAdapter spinnerAdapter = new corp.burenz.expertouch.adapters.MySpinnerAdapter(experience);
//        expertExperience.setAdapter(spinnerAdapter);
//        expertStateE.setAdapter(spinnerAdapter);
//        expertAgeE.setAdapter(spinnerAdapter);
            trickyFlipper.showNext();
            checkPrevious();


    }





    void sendDetails(View v){


        userData = getSharedPreferences(LOCAL_APP_DATA,0);

        editor = userData.edit();

        editor.putBoolean("EXPERT",true);
        editor.putBoolean("COMPANY",false);
        editor.commit();
        startActivity(new Intent(XpertRegistration.this,Profile.class));

    }

    public void initViews(){


        myProfile = getSharedPreferences(MY_PROFILE_DATA,0);

        myProfileEditor = myProfile.edit();
        cardAnimation = AnimationUtils.loadAnimation(XpertRegistration.this,R.anim.card_animation);

        // Flippers
        xpertWelcomeFlipper = (ViewFlipper)findViewById(R.id.xpertWelcomeFlipper);
        xpertImagesFlipper = (ViewFlipper)findViewById(R.id.expertImagesFlipper);
        xpertButtonsFlipper = (ViewFlipper)findViewById(R.id.expertButtonsFlipper);
        xpertSubtitleFlipper = (ViewFlipper)findViewById(R.id.expertSubtitleFlipper);
        xpertEditTextFlippers = (ViewFlipper)findViewById(R.id.expertEditTextFlipper);


        // Buttons
        fullNameB = (Button)findViewById(R.id.fullNameB);
        mainExpertiseB = (Button)findViewById(R.id.mainExpertiseB);
        mainProfB = (Button) findViewById(R.id.mainProfB);
        profSubB = (Button) findViewById(R.id.profSubtB);
        noticePeriodB = (Button)findViewById(R.id.noticePeriodB);
        genderB = (Button)findViewById(R.id.genderB);
        textStatusB = (Button)findViewById(R.id.textStatusB);
        statusB  = (Button)findViewById(R.id.statusB);
        expertAgeB  = (Button)findViewById(R.id.expertAgeB);
        expertBioB  = (Button)findViewById(R.id.expertBioB);
        expertCallB  = (Button)findViewById(R.id.expertCallB);
        expertMailB = (Button)findViewById(R.id.expertEmailB);
        expertSkillsB  = (Button)findViewById(R.id.expertSkillsB);
        expertStateB = (Button)findViewById(R.id.expertStateB);
        expertExperienceB = (Button)findViewById(R.id.expertExperienceB);
        myCityB = (Button) findViewById(R.id.cityB);
        mainProfS = (Spinner) findViewById(R.id.mainProfS);
        profSubS = (Spinner) findViewById(R.id.profSubS);




        SpinnerAdapter profAdapter;









        String []profTypes = {
                "Artisans",
                "Engineers",
                "Doctors",
                "Transporters",
                "Cabs",
                "Accountants",
                "Mechanics",
                "Fashion",
                "Lawyers",
                "Teachers",
                "Labours",
                "Software and Services",
                "Media",
                "Contractors",
                "Health",
                "Agents",
                "General Order Suppliers",
                "Others"

        };







        profAdapter= new ArrayAdapter<String>(XpertRegistration.this, R.layout.spinner_adapter, R.id.spinnerText,profTypes);
        mainProfS.setAdapter(profAdapter);

        //trickyLayouts
        trickyLayout = (LinearLayout) findViewById(R.id.trickyLayout);
        trickyTitle = (TextView)findViewById(R.id.trickyTitle);
        trickySubtitle = (TextView) findViewById(R.id.trickySubtitle);
        iAccept = (Button) findViewById(R.id.iAccept);
        trickyFlipper = (ViewFlipper)findViewById(R.id.trickyFlipper);


        // EditTexts
        fullNameE = (EditText)findViewById(R.id.fullNameE);
        myCityE = (EditText) findViewById(R.id.myCityE);
        mainExpertiseE = (EditText)findViewById(R.id.mainExpertiseE);
        textStatusE = (EditText)findViewById(R.id.textStatusE);
        expertAgeE  = (Spinner)findViewById(R.id.expertAgeE);
        expertBioE  = (EditText)findViewById(R.id.expertBioE);
        expertCallE  = (EditText)findViewById(R.id.expertCallE);
        expertMailE = (EditText)findViewById(R.id.expertEmailE);
        expertSkillsE = (EditText) findViewById(R.id.expertSkillsE);


        // Spinners
        expertStateE = (Spinner)findViewById(R.id.expertStateE);
        experienceYears = (Spinner)findViewById(R.id.experienceYears);
        experienceMonths = (Spinner)findViewById(R.id.experienceMonths);

        //Radios
        maleSelection = (RadioButton)findViewById(R.id.maleSelection);
        femaleSelection = (RadioButton)findViewById(R.id.femaleSelection);
        availableR = (RadioButton)findViewById(R.id.availableR);
        notAvailableR= (RadioButton)findViewById(R.id.notAvailableR);
        partTime = (RadioButton)findViewById(R.id.partTime);
        fullTime= (RadioButton)findViewById(R.id.fullTime);




        femaleSelection.setBackgroundResource(R.drawable.unchecked);
        notAvailableR.setBackgroundResource(R.drawable.unchecked);
        fullTime.setBackgroundResource(R.drawable.unchecked);



        // onClick Listners
        fullNameB.setOnClickListener(this);
        mainExpertiseB.setOnClickListener(this);
        noticePeriodB.setOnClickListener(this);
        mainProfB.setOnClickListener(this);
        profSubB.setOnClickListener(this);
        myCityB.setOnClickListener(this);
        genderB.setOnClickListener(this);
        textStatusB.setOnClickListener(this);
        statusB.setOnClickListener(this);
        expertAgeB.setOnClickListener(this);
        expertBioB.setOnClickListener(this);
        expertCallB .setOnClickListener(this);
        expertMailB.setOnClickListener(this);
        expertSkillsB .setOnClickListener(this);
        expertStateB.setOnClickListener(this);
        expertExperienceB.setOnClickListener(this);
        iAccept.setOnClickListener(this);
        maleSelection.setOnClickListener(this);
        femaleSelection.setOnClickListener(this);



        availableR.setOnClickListener(this);
        notAvailableR.setOnClickListener(this);


        partTime.setOnClickListener(this);
        fullTime.setOnClickListener(this);













    }




    void setBasics(){


        String myFullName,myMainExpertise,myNoticePeriod,myStatus,myTextStatus,myShortBio,myCall,myEmail,myState;
           String visibility;

        SharedPreferences myMetaprofile, userData;
        myMetaprofile = getSharedPreferences(MY_PROFILE_DATA,0);
        userData = getSharedPreferences(LOCAL_APP_DATA,0);
        SharedPreferences.Editor myMetaEditor;
        myMetaEditor = myMetaprofile.edit();

        myFullName = userData.getString("userName","Mr Expert");
        visibility = "1"; // visible for public

        // myMainExpertise override when you call my Profsbutype
        myNoticePeriod = "Part Time"; // visible
        myTextStatus = "Hello there, i just signed up for 1clickAway";
        myStatus = "1";
        myShortBio = "No Bio defined yet";
        myCall = userData.getString("userEmail","0000000000");
        myEmail = "Update Your Email Now";
        myState = userData.getString("userState","Jammu and Kashmir");



        myMetaEditor.putString("myFullName",myFullName);
        myMetaEditor.putString("visibility",visibility);
        myMetaEditor.putString("myNoticePeriod",myNoticePeriod);
        myMetaEditor.putString("myTextStatus",myTextStatus);
        myMetaEditor.putString("myStatus",myStatus);
        myMetaEditor.putString("myShortBio",myShortBio);
        myMetaEditor.putString("myCall",myCall);
        myMetaEditor.putString("myEmail",myEmail);
        myMetaEditor.putString("myState",myState);


        myMetaEditor.apply();












    }

    @Override
    public void onClick(View v) {


        switch (v.getId()) {

            case R.id.fullNameB:

                if (fullNameE.getText().toString().length() > 3){
                    myProfileEditor.putString("myFullName", fullNameE.getText().toString());
                    myProfileEditor.putString("visibility", "1");
                    xpertImagesFlipper.showNext();
                    xpertButtonsFlipper.showNext();
                    xpertSubtitleFlipper.showNext();
                    xpertEditTextFlippers.showNext();
                    myProfileEditor.apply();
                }else {
                    Toast.makeText(XpertRegistration.this, "Username must be at least 4 Characters Long", Toast.LENGTH_SHORT).show();
                }
                break;


            case R.id.mainExpertiseB:


                if (mainExpertiseE.getText().toString().length() > 5){
                    myProfileEditor.putString("myMainExpertise", mainExpertiseE.getText().toString());
                    xpertImagesFlipper.showNext();
                    xpertButtonsFlipper.showNext();
                    xpertSubtitleFlipper.showNext();
                    xpertEditTextFlippers.showNext();
                    myProfileEditor.apply();

                }else {
                    Toast.makeText(XpertRegistration.this, "Field Of Interest must be at least 6 Characters Long", Toast.LENGTH_SHORT).show();
                }


                break;


            case R.id.noticePeriodB:


                if (partTime.isChecked()) {
                    noticePeriod = "Part Time";
                } else {
                    noticePeriod = "Full Time";
                }

                myProfileEditor.putString("myNoticePeriod", noticePeriod);

                xpertImagesFlipper.showNext();
                xpertButtonsFlipper.showNext();
                xpertSubtitleFlipper.showNext();
                xpertEditTextFlippers.showNext();
                myProfileEditor.apply();

                break;



            case R.id.mainProfB:

                myProfileEditor.putString("myMainProf", mainProfS.getSelectedItem().toString());
                // tanslate subType Here by identifying using switch

                SpinnerAdapter typeAdapter;


                String []lawyerTypes = {

                        "All Categories"
                        ,"Employment Lawyer"
                        ,"Family Lawyer"
                        ,"General Practice Lawyer"
                        ,"Bankruptcy Lawyer"
                        ,"Commercial Bankruptcy Lawyer"
                        ,"Corporate Lawyer"
                        ,"Criminal Lawyer"
                        ,"Deportation Lawyer"
                        ,"DUI Lawyer"
                        ,"Inheritance Lawyer"
                        ,"Spousal Support Lawyer"
                        ,"Discrimination Lawyer"
                        ,"Wrongful Death Lawyer"
                        ,"Birth Injury Lawyer"
                        ,"Health Insurance Lawyer"
                        ,"Contract Lawyer"
                        ,"Accident Lawyer"
                        ,"Traffic Lawyer",
                        "Others"
                };

                String []artizensType = {

                        "All Categories"
                        ,"Welders",
                        "Electricians",
                        "Fitters",
                        "Turners",
                        "Millwrights",
                        "Sheetmetal Workers",
                        "Boilermakers",
                        "Mechatronics",
                        "Mechanics",
                        "Woodworks",
                        "Penlers",
                        "Toolmakers",
                        "Patternmakers",
                        "Bricklayers",
                        "Plumbers",
                        "Maison",
                        "Carpenters",
                        "Joiners",
                        "Shutterhands",
                        "Steel fixers",
                        "Glaziers",
                        "Plasterers",
                        "Tilers",
                        "Sound technicians"
                        ,"Painters",
                        "Instrumentation and electronics technicians",
                        "Others"

                };


                String []teacherTypes  = {

                        "All Categories"
                        ,"Science",
                        "Arts",
                        "Commerce",
                        "Physical Education",
                        "Islamic Studies",
                        "Others"
                };



                String []doctorsType = {
                        "All Categories"
                        ,"Audiologist"
                        ,"Allergist"
                        ,"Anesthesiologist"
                        ,"Cardiologist"
                        ,"Dentist"
                        ,"Dermatologist"
                        ,"Endocrinologist"
                        ,"Gynecologist"
                        ,"Immunologist"
                        ,"Infectious Disease Specialist"
                        ,"Internal Medicine Specialist"
                        ,"Medical Geneticist"
                        ,"Microbiologist"
                        ,"Neonatologist"
                        ,"Neurologist"
                        ,"Neurosurgeon"
                        ,"Obstetrician"
                        ,"Oncologist"
                        ,"Orthopedic Surgeon"
                        ,"ENT Specialist"
                        ,"Pediatrician"
                        ,"Physiologist"
                        ,"Plastic Surgeon"
                        ,"Podiatrist"
                        ,"Psychiatrist"
                        ,"Radiologist"
                        ,"Rheumatologist"
                        ,"Surgeon"
                        ,"Urologist",
                        "Others"

                };




                String []mediaTypes  = {

                        "All Categories"
                        ,"Journalists",
                        "Print Media",
                        "Electronic Media",
                        "News",
                        "Agents",
                        "Others"

                };


                String []healthTypes = {
                        "All Categories"
                        ,"Diagnostics",
                        "Clinics",
                        "Stores",
                        "Others"


                };


                String []agentTypes = {
                        "All Categories"
                        ,"Insurance",
                        "Travel",
                        "Automobile",
                        "Overseas",
                        "Placement",
                        "Haj and Umrah",
                        "Pilgrimage",
                        "Others"
                };

                String []contractorTypes = {
                        "All Categories"
                        ,"Government",
                        "Private",
                        "Others"
                };




                String []otherTypes = {
                        "All Categories",
                        "Wedding Planners",
                        "Cooks",
                        "Advisers",
                        "Tourist Guides",
                        "Others"






                };

                String []engineersType = {

                        "All Categories"
                        ,"Architectural & Building "
                        ,"Chemical Engineering"
                        ,"Civil Engineering"
                        ,"Electrical Engineering"
                        ,"Industrial Engineering"
                        ,"Mechatronics Engineering"
                        ,"Mechanical Engineering"
                        ,"Metallurgical Engineering"
                        ,"Computer Engineering"
                        ,"Software Engineering"
                        ,"Mathematical Engineering"
                        ,"Others"

                };

                String []fashion = {
                        "All Categories"
                        ,"Boutiques"
                        , "Bridal Make Up"
                        , "Mehandi expert"
                        , "Hair Stylist",
                        "Others"



                };

                String []allCategories = {
                        "All Categories"
                };






                String []labours = {
                        "All Categories"

                };

                String []mechanics= {
                        "All Categories"
                        ,"Mobile Phone",
                        "Laptops",
                        "Refrigerators",
                        "Television",
                        "Automobile",
                        "Other Electrical Appliances",


                };

                String []accountants= {

                        "All Categories"
                        ,"Chartered Accountants",
                        "Tax Consultants",
                        "Accountants",
                        "Others"


                };


                String []transporters = {

                        "All Categories"
                        ,"JCB's",
                        "Trucks",
                        "Load carriers",
                        "Tippers",
                        "Tractors",
                        "Buses",
                        "Matadors",
                        "Others"



                };

                String []localTransport = {

                        "All Categories"
                        ,"Rikshaws",
                        "Cabs",
                        "Others"

                };




                String []profTypes = {
                        "All Categories",
                        "Artisans",
                        "Engineers",
                        "Doctors",
                        "Transporters",
                        "Cabs",
                        "Accountants",
                        "Mechanics",
                        "Fashion",
                        "Lawyers",
                        "Teachers",
                        "Labours",
                        "Software and Services",
                        "Media",
                        "Contractors",
                        "Health",
                        "Agents",
                        "General Order Suppliers",
                        "Others"

                };


                String []orderSuppliesTypes= {

                        "All Categories"
                        ,"Stores",
                        "Malls",
                        "Agencies",
                        "Others"

                };





                switch (mainProfS.getSelectedItem().toString()){





                    case "Doctors":
                        typeAdapter = new ArrayAdapter<String>(XpertRegistration.this, R.layout.spinner_adapter_black, R.id.spinnerTextBlack,doctorsType);
                        profSubS.setAdapter(typeAdapter);
                        break;

                    case "Engineers":
                        typeAdapter = new ArrayAdapter<String>(XpertRegistration.this, R.layout.spinner_adapter_black, R.id.spinnerTextBlack,engineersType);
                        profSubS.setAdapter(typeAdapter);
                        break;




                    case "Cabs":
                        typeAdapter = new ArrayAdapter<String>(XpertRegistration.this, R.layout.spinner_adapter_black, R.id.spinnerTextBlack,localTransport);
                        profSubS.setAdapter(typeAdapter);

                        break;

                    case "Transporters":
                        typeAdapter = new ArrayAdapter<String>(XpertRegistration.this, R.layout.spinner_adapter_black, R.id.spinnerTextBlack,transporters);
                        profSubS.setAdapter(typeAdapter);

                        break;
                    case "Accountants":
                        typeAdapter = new ArrayAdapter<String>(XpertRegistration.this, R.layout.spinner_adapter_black, R.id.spinnerTextBlack,accountants);
                        profSubS.setAdapter(typeAdapter);

                        break;
                    case "Mechanics":
                        typeAdapter = new ArrayAdapter<String>(XpertRegistration.this, R.layout.spinner_adapter_black, R.id.spinnerTextBlack,mechanics);
                        profSubS.setAdapter(typeAdapter);

                        break;
                    case "Fashion":
                        typeAdapter = new ArrayAdapter<String>(XpertRegistration.this, R.layout.spinner_adapter_black, R.id.spinnerTextBlack,fashion);
                        profSubS.setAdapter(typeAdapter);

                        break;
                    case "Teachers":
                        typeAdapter = new ArrayAdapter<String>(XpertRegistration.this, R.layout.spinner_adapter_black, R.id.spinnerTextBlack,teacherTypes);
                        profSubS.setAdapter(typeAdapter);

                        break;

                    case "Labours":
                        typeAdapter = new ArrayAdapter<String>(XpertRegistration.this, R.layout.spinner_adapter_black, R.id.spinnerTextBlack,allCategories);
                        profSubS.setAdapter(typeAdapter);

                        break;


                    case "Software and Services":
                        typeAdapter = new ArrayAdapter<String>(XpertRegistration.this, R.layout.spinner_adapter_black, R.id.spinnerTextBlack,allCategories);
                        profSubS.setAdapter(typeAdapter);

                        break;
                    case "Lawyers":
                        typeAdapter = new ArrayAdapter<String>(XpertRegistration.this, R.layout.spinner_adapter_black, R.id.spinnerTextBlack,lawyerTypes);
                        profSubS.setAdapter(typeAdapter);

                        break;

                    case "Artisans":
                        typeAdapter = new ArrayAdapter<String>(XpertRegistration.this, R.layout.spinner_adapter_black, R.id.spinnerTextBlack,artizensType);
                        profSubS.setAdapter(typeAdapter);

                        break;



                    case "Media":
                        typeAdapter = new ArrayAdapter<String>(XpertRegistration.this, R.layout.spinner_adapter_black, R.id.spinnerTextBlack,mediaTypes);
                        profSubS.setAdapter(typeAdapter);

                        break;
                    case "Contractors":
                        typeAdapter = new ArrayAdapter<String>(XpertRegistration.this, R.layout.spinner_adapter_black, R.id.spinnerTextBlack,contractorTypes);
                        profSubS.setAdapter(typeAdapter);

                        break;

                    case "Health":
                        typeAdapter = new ArrayAdapter<String>(XpertRegistration.this, R.layout.spinner_adapter_black, R.id.spinnerTextBlack,healthTypes);
                        profSubS.setAdapter(typeAdapter);

                        break;


                    case "Agents":
                        typeAdapter = new ArrayAdapter<String>(XpertRegistration.this, R.layout.spinner_adapter_black, R.id.spinnerTextBlack,agentTypes);
                        profSubS.setAdapter(typeAdapter);

                        break;

                    case "Others":
                        typeAdapter = new ArrayAdapter<String>(XpertRegistration.this, R.layout.spinner_adapter_black, R.id.spinnerTextBlack,otherTypes);
                        profSubS.setAdapter(typeAdapter);

                        break;

                    case "General Order Suppliers":
                        typeAdapter = new ArrayAdapter<String>(XpertRegistration.this, R.layout.spinner_adapter_black, R.id.spinnerTextBlack,orderSuppliesTypes);
                        profSubS.setAdapter(typeAdapter);

                        break;






                }

                xpertImagesFlipper.showNext();
                xpertButtonsFlipper.showNext();
                xpertSubtitleFlipper.showNext();
                xpertEditTextFlippers.showNext();
                myProfileEditor.apply();

                break;


            case R.id.profSubtB:
                myProfileEditor.putString("expertProfType", profSubS.getSelectedItem().toString());
                myProfileEditor.putString("myMainExpertise", profSubS.getSelectedItem().toString());
                xpertImagesFlipper.showNext();
                xpertButtonsFlipper.showNext();
                xpertSubtitleFlipper.showNext();
                xpertEditTextFlippers.showNext();
                myProfileEditor.apply();


                break;



                    case R.id.cityB:

                        if (myCityE.getText().toString().length() > 8){
                            myProfileEditor.putString("myCurrentCity", myCityE.getText().toString());
                            xpertImagesFlipper.showNext();
                            xpertButtonsFlipper.showNext();
                            xpertSubtitleFlipper.showNext();
                            xpertEditTextFlippers.showNext();
                            myProfileEditor.apply();

                        }else {
                            Toast.makeText(XpertRegistration.this, "Please Mention your Area as well ,Your City must be greater than 10 Characters Long", Toast.LENGTH_SHORT).show();
                        }
                        break;



            case R.id.genderB:


                if (maleSelection.isChecked()) {
                    gender = "1";
                    myProfileEditor.putString("myProfilePic", getString(R.string.host)+"/defaults/male_default.jpg");
                    myProfileEditor.commit();
                 } else {
                    gender = "0";

                     myProfileEditor.putString("myProfilePic", getString(R.string.host)+"/defaults/female_default.png");
                     myProfileEditor.commit();
                }

                myProfileEditor.putString("myGender",gender);

                xpertImagesFlipper.showNext();
                xpertButtonsFlipper.showNext();
                xpertSubtitleFlipper.showNext();
                xpertEditTextFlippers.showNext();
                myProfileEditor.apply();
                break;

            case R.id.statusB:


                if (availableR.isChecked()) {
                    status = "1";
                } else {
                    status = "0";
                }

                myProfileEditor.putString("myStatus", status);


                xpertImagesFlipper.showNext();
                xpertButtonsFlipper.showNext();
                xpertSubtitleFlipper.showNext();
                xpertEditTextFlippers.showNext();
                myProfileEditor.apply();
                break;

            case R.id.textStatusB:

                textStatus = textStatusE.getText().toString();

                if (textStatus.length() > 10 && textStatus.length() < 70){
                    myProfileEditor.putString("myTextStatus", textStatus);
                    xpertImagesFlipper.showNext();
                    xpertButtonsFlipper.showNext();
                    xpertSubtitleFlipper.showNext();
                    xpertEditTextFlippers.showNext();
                    myProfileEditor.apply();

                }else {
                    Toast.makeText(XpertRegistration.this, "Status must be greater than 10 Characters but less than 70 characters", Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.expertAgeB:

                //  expertAge = expertAgeE.getText().toString();

                if (!expertAgeE.getSelectedItem().toString().equals("Select Age")){

                    xpertImagesFlipper.showNext();
                    xpertButtonsFlipper.showNext();
                    xpertSubtitleFlipper.showNext();
                    xpertEditTextFlippers.showNext();
                    myProfileEditor.putString("myAge", expertAgeE.getSelectedItem().toString());
                    myProfileEditor.apply();

                }else {
                    Toast.makeText(XpertRegistration.this, "Please Select Your Age", Toast.LENGTH_SHORT).show();

                }


                break;

            case R.id.expertBioB:

                expertBio = expertBioE.getText().toString();

                if (expertBio.length() > 5 && expertBio.length() < 40){

                    myProfileEditor.putString("myShortBio", expertBio);

                    xpertImagesFlipper.showNext();
                    xpertButtonsFlipper.showNext();
                    xpertSubtitleFlipper.showNext();
                    xpertEditTextFlippers.showNext();
                    myProfileEditor.apply();
                }else {
                    Toast.makeText(XpertRegistration.this, "Your Bio must be at least 5 Characters Long but less than 40 Characters", Toast.LENGTH_SHORT).show();
                }



                break;

            case R.id.expertCallB:


                expertCall = expertCallE.getText().toString();

                if (expertCall.length() == 10){

                    myProfileEditor.putString("myCall", expertCall);

                    xpertImagesFlipper.showNext();
                    xpertButtonsFlipper.showNext();
                    xpertSubtitleFlipper.showNext();
                    xpertEditTextFlippers.showNext();
                    myProfileEditor.apply();

                }else {
                    Toast.makeText(XpertRegistration.this, "Your Phone must be at exactly 10 Digits Long", Toast.LENGTH_SHORT).show();
                }


                break;

            case R.id.expertEmailB:

                expertMail = expertMailE.getText().toString();

                if (expertMail.contains("@") && expertMail.contains(".com")){

                    if (expertMail.contains(" ")){
                        Toast.makeText(XpertRegistration.this, "Email address must not contains Spaces", Toast.LENGTH_SHORT).show();
                    }else {
                        myProfileEditor.putString("myEmail", expertMail);
                        xpertImagesFlipper.showNext();
                        xpertButtonsFlipper.showNext();
                        xpertSubtitleFlipper.showNext();
                        xpertEditTextFlippers.showNext();
                        myProfileEditor.apply();
                    }


                }else {
                    Toast.makeText(XpertRegistration.this, "Please Enter a valid Email address", Toast.LENGTH_SHORT).show();
                }


                break;


            case R.id.expertStateB:

                if (expertStateE.getSelectedItem().toString().equals("Select Your State")){

                    Toast.makeText(XpertRegistration.this, "Please Enter a valid State", Toast.LENGTH_SHORT).show();

                }else{
                    myProfileEditor.putString("myState", expertStateE.getSelectedItem().toString());

                    xpertImagesFlipper.showNext();
                    xpertButtonsFlipper.showNext();
                    xpertSubtitleFlipper.showNext();
                    xpertEditTextFlippers.showNext();

                    myProfileEditor.apply();

                }


                break;

            case R.id.expertExperienceB:


                if (experienceYears.getSelectedItem().toString().equals("Select Years") || experienceMonths.getSelectedItem().toString().equals("Select Months")){
                    Toast.makeText(XpertRegistration.this, "Please Select a Valid Set", Toast.LENGTH_SHORT).show();
                    break;
                }else {

                    myProfileEditor.putString("myExperience", experienceYears.getSelectedItem().toString());
                    myProfileEditor.putString("myMonthsExperience", experienceMonths.getSelectedItem().toString());
                    xpertImagesFlipper.showNext();
                    xpertButtonsFlipper.showNext();
                    xpertSubtitleFlipper.showNext();
                    xpertEditTextFlippers.showNext();

                    myProfileEditor.apply();

                }

                break;


            case R.id.expertSkillsB:

                if (expertSkillsE.getText().toString().length() > 3){


                    myProfileEditor.putString("mySkills", expertSkillsE.getText().toString());
                    myProfileEditor.commit();
                    new RegisterExpert().execute();
                }else {
                    Toast.makeText(XpertRegistration.this, "Please mention at least one Skill", Toast.LENGTH_SHORT).show();
                }

                break;





            case R.id.maleSelection:
                femaleSelection.setBackgroundResource(R.drawable.unchecked);
                maleSelection.setBackgroundColor(Color.TRANSPARENT);

                gender = "1";
                femaleSelection.setChecked(false);
                maleSelection.setChecked(true);
                break;



            case R.id.iAccept:
                trickyFlipper.showPrevious();
                xpertWelcomeFlipper.showNext();
                xpertButtonsFlipper.showNext();
                break;


            case R.id.femaleSelection:

                maleSelection.setBackgroundResource(R.drawable.unchecked);
                femaleSelection.setBackgroundColor(Color.TRANSPARENT);

                gender = "0";
                myProfileEditor.putString("myGender",gender);
                femaleSelection.setChecked(true);
                maleSelection.setChecked(false);

                break;

            case R.id.availableR:

                notAvailableR.setBackgroundResource(R.drawable.unchecked);
                availableR.setBackgroundColor(Color.TRANSPARENT);


                availableR.setChecked(true);
                notAvailableR.setChecked(false);

                break;


            case R.id.notAvailableR:

                availableR.setBackgroundResource(R.drawable.unchecked);
                notAvailableR.setBackgroundColor(Color.TRANSPARENT);


                availableR.setChecked(false);
                notAvailableR.setChecked(true);

                break;


            case R.id.partTime:

                fullTime.setBackgroundResource(R.drawable.unchecked);
                partTime.setBackgroundColor(Color.TRANSPARENT);


                partTime.setChecked(true);
                fullTime.setChecked(false);

                break;


            case R.id.fullTime:

                partTime.setBackgroundResource(R.drawable.unchecked);
                fullTime.setBackgroundColor(Color.TRANSPARENT);

                partTime.setChecked(false);
                fullTime.setChecked(true);
                break;

        }

    }

    public void checkPrevious(){


        if (!myProfile.getString("myFullName","Enter Full Name").equals("Enter Full Name") ){

            fullNameE.setText(myProfile.getString("myFullName","Enter Here"));

        }else {

            fullNameE.setHint(myProfile.getString("myFullName","Enter Full Name Here"));

        }


        if (!myProfile.getString("myTextStatus","Enter Full Name").equals("Enter Full Name") ){

            textStatusE.setText(myProfile.getString("myTextStatus","Enter Here"));

        }else {

            textStatusE.setHint(myProfile.getString("myTextStatus","Enter Some Status For Callers"));

        }


        if (!myProfile.getString("myMainExpertise","Enter Full Name").equals("Enter Full Name") ){

            mainExpertiseE.setText(myProfile.getString("myMainExpertise","Enter Here"));

        }else {

            mainExpertiseE.setHint(myProfile.getString("myMainExpertise","Enter Your Main Expertise Here"));

        }



        if (!myProfile.getString("myShortBio","Enter Full Name").equals("Enter Full Name") ){

            expertBioE.setText(myProfile.getString("myShortBio","Enter Here"));

        }else {

            expertBioE.setHint(myProfile.getString("myShortBio","Enter a short bio here"));

        }


        if (!myProfile.getString("myCall","Enter Full Name").equals("Enter Full Name") ){

            expertCallE.setText(myProfile.getString("myCall","Enter Here"));

        }else {

            expertCallE.setHint(myProfile.getString("myCall","Enter Your Phone Number"));

        }

        if (!myProfile.getString("myEmail","Enter Full Name").equals("Enter Full Name") ){

            expertMailE.setText(myProfile.getString("myEmail","Enter Here"));

        }else {

            expertMailE.setHint(myProfile.getString("myEmail","Enter your Email Here"));

        }
        if (!myProfile.getString("mySkills","Enter Skills").equals("Enter Skills") ){

            expertSkillsE.setText(myProfile.getString("mySkills","Enter Here"));

        }else {

            expertSkillsE.setHint(myProfile.getString("mySkills","example skill1, skill2,skill3 etc"));

        }



    }


    public class RegisterExpert extends AsyncTask< String, String, String >{

        StringBuilder builder = new StringBuilder();
        BufferedReader bufferedReader;
        List<NameValuePair> nameValuePairs = new ArrayList<>();



        @Override
        protected String doInBackground(String... params) {

        userData = getSharedPreferences(LOCAL_APP_DATA,0);


            nameValuePairs.add(new BasicNameValuePair("fullName",myProfile.getString("myFullName","Enter Full Name")));
            nameValuePairs.add(new BasicNameValuePair("mainExpertise",myProfile.getString("myMainExpertise","Enter Full Name")));

            nameValuePairs.add(new BasicNameValuePair("gender",myProfile.getString("myGender","Enter Full Name")));
            nameValuePairs.add(new BasicNameValuePair("status",myProfile.getString("myStatus","Enter Full Name")));

            nameValuePairs.add(new BasicNameValuePair("textStatus",myProfile.getString("myTextStatus","Enter Full Name")));
            nameValuePairs.add(new BasicNameValuePair("noticePeriod",myProfile.getString("myNoticePeriod","Enter Full Name")));

            nameValuePairs.add(new BasicNameValuePair("expertCall",myProfile.getString("myCall","Enter Full Name")));
            nameValuePairs.add(new BasicNameValuePair("expertEmail",myProfile.getString("myEmail","Enter Full Name")));

            nameValuePairs.add(new BasicNameValuePair("shortBio",myProfile.getString("myShortBio","Enter Full Name")));
            nameValuePairs.add(new BasicNameValuePair("myCurrentState",myProfile.getString("myState","Enter Full Name")));

            nameValuePairs.add(new BasicNameValuePair("myExperience",myProfile.getString("myExperience","Enter Full Name")));
            nameValuePairs.add(new BasicNameValuePair("myAge",myProfile.getString("myAge","Enter Age Here")));

            nameValuePairs.add(new BasicNameValuePair("myExperienceMonths",myProfile.getString("myMonthsExperience","Enter Full Name")));
            //expert Pic

            nameValuePairs.add(new BasicNameValuePair("userPhone",userData.getString("userEmail","noEmail@gmail.com")));
            nameValuePairs.add(new BasicNameValuePair("expertCity",myProfile.getString("myCurrentCity","Enter Age Here")));

            nameValuePairs.add(new BasicNameValuePair("expertSkills",myProfile.getString("mySkills","mySkills")));
            //visibilty in php

            nameValuePairs.add(new BasicNameValuePair("expertProfType",myProfile.getString("expertProfType","Enter Age Here")));
            nameValuePairs.add(new BasicNameValuePair("expertProfession",myProfile.getString("myMainProf","Enter Full Name")));











//


            try{

                HttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost(getString(R.string.host)+"/profile/register_expert.php");

                httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                HttpResponse httpResponse = (HttpResponse) httpClient.execute(httpPost);
                HttpEntity httpEntity = (HttpEntity) httpResponse.getEntity();

                bufferedReader = new BufferedReader(new InputStreamReader(httpEntity.getContent()));
                String str = "";

                while ( (str = bufferedReader.readLine()) != null ){
                    builder.append(str);

                }

                } catch (ClientProtocolException e) {
                e.printStackTrace();
                } catch (IOException e) {
                e.printStackTrace();
                }catch ( Exception e ){
                e.printStackTrace();
            }


            return builder.toString();

        }


        @Override
        protected void onPreExecute() {
            super.onPreExecute();


            trickyTitle.setText("Hang On");
            trickySubtitle.setText("We are updating your Cool Profile to our  Servers, we are preparing your  profile to  be served to Copanies associated with Us, we wish you all the very Best");


            trickyFlipper.showNext();
            xpertWelcomeFlipper.showPrevious();

            xpertButtonsFlipper.showNext();



        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            SharedPreferences userData;
            SharedPreferences.Editor editor;

            userData  = getSharedPreferences(LOCAL_APP_DATA,0);
            editor = userData.edit();

            if (s.equals("1")){

                Log.e("TAG","Expert Registered Here");
                editor.putBoolean("EXPERT",true);
                editor.commit();

                startActivity(new Intent(XpertRegistration.this,Profile.class));
                XpertRegistration.this.finish();


            }else{



                trickyFlipper.showPrevious();
                xpertWelcomeFlipper.showNext();
                xpertButtonsFlipper.showPrevious();
                Log.e("TAG","Not Registered Here");

                Toast.makeText(XpertRegistration.this, "Poor Connection, Please try again ", Toast.LENGTH_SHORT).show();

            }



        }
    }




    public void deadEnd(View v){
        Toast.makeText(XpertRegistration.this, "Cant Go back any Further", Toast.LENGTH_SHORT).show();
    }


    public void goBack(View v){

        xpertImagesFlipper.showPrevious();
        xpertButtonsFlipper.showPrevious();
        xpertSubtitleFlipper.showPrevious();
        xpertEditTextFlippers.showPrevious();

    }

}

