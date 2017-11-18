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
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
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

public class MyCompany extends AppCompatActivity implements View.OnClickListener {


    String COMPANY_DETAILS = "myCompanyDetails";
    SharedPreferences myCompanyDetails;
    SharedPreferences.Editor editor;


    ArrayList<String> companyName;
    ArrayList<String> companyTag;
    ImageLoader imageLoader;
    ImageView editCompanyImage,companyPicPreview;
    ViewFlipper companyImageFlipper;
    ImageButton selectCFromGallery;
    ImageButton cancelCFrom;
    ImageButton uploadCfrom;
    public static final int  REQUEST_IMAGE_CROP = 3;

    //image cake
    public static final int GALLERY_PICTURE = 1;
    public static final int READ_GALLERY_PERMISSIONS_REQUEST = 2;
    Intent imageIntent;
    LinearLayout selectFromGallery,takeSnap;
//    ImageView imagePreview;
    String base64;
    LinearLayout imageProgress;
    Bitmap myBitmap;

    NetworkImageView myCompanyAvtar;

    Animation cardAnimation;

    // NAMETAG PANNEL
    TextView myCompanyTitleV,myCompanyTagV;
    EditText myCompanyTitleE,myCompanyTagE;

    ArrayList<String> statesArray = new ArrayList<>();

    // Company Contacts

    TextView myCompanyCallV,myCompanyMailV,myCompanyVisitV;
    EditText myCompanyCallE,myCompanyMailE,myCompanyVisitE;

    // Company About
    TextView myCompanyAboutV;
    EditText myCompanyAboutE;

    // Company Address
    TextView myCompanyCityV,mycompanyLandmarkV,myCompanyStateV;
    EditText myCompanyCityE,mycompanyLandmarkE;
    Spinner myCompanyStateS;
    CardView titlesCard,contactCard,aboutCard,addresssCard;


    String LOCAL_APP_DATA = "userInformation";
    SharedPreferences userData;
    String USER_EMAIL;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_company);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        userData = getSharedPreferences(LOCAL_APP_DATA,0);
        USER_EMAIL = userData.getString("userEmail","noEmail@example.com");

        addStates();
        inItViews();
        setNameTag();
        setAddress();
        setCompanyAbout();
        setContactText();

    }



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
    }


    ViewFlipper titleFlipper,contactFlipper,aboutFlipper,addressCFlipper;
    ViewFlipper titlePannelFlipper,contactPannelFlipper,aboutPannelFlipper,addressPannelFlipper;
    ImageView editFlipTitle,editFlipContact,editFlipAbout,editFlipAddressc;
    Button saveTitle,saveContact,saveAbout,saveAddress;
    Button cancelTitle,cancelContact,cancelAbout,cancelAddress;












    public void inItViews(){

        cardAnimation = AnimationUtils.loadAnimation(MyCompany.this,R.anim.card_animation);


        aboutCard = (CardView) findViewById(R.id.aboutCard);
        addresssCard = (CardView) findViewById(R.id.addressCard);
        contactCard = (CardView) findViewById(R.id.contactCard);
        titlesCard = (CardView) findViewById(R.id.titlesCard);

        titlesCard.startAnimation(cardAnimation);
        contactCard.startAnimation(cardAnimation);
        aboutCard.startAnimation(cardAnimation);
        addresssCard.startAnimation(cardAnimation);

        // Company Title Pannel


//
//        ImageView editCompanyImage,companyProfilePic;
//        ViewFlipper companyImageFlipper;
//        LinearLayout selectCFromGallery,cancelCFrom,UploadCfrom;


        
        editCompanyImage = (ImageView) findViewById(R.id.editCompanyImage);
        companyPicPreview = (ImageView) findViewById(R.id.companyPicPreview);
        companyImageFlipper = (ViewFlipper) findViewById(R.id.companyImageFlipper);
        selectCFromGallery = (ImageButton) findViewById(R.id.selectCFromGallery);
        cancelCFrom = (ImageButton) findViewById(R.id.cancelCFrom);
        uploadCfrom = (ImageButton) findViewById(R.id.uploadCfrom);
        
        editCompanyImage.setOnClickListener(this);
        selectCFromGallery.setOnClickListener(this);
        cancelCFrom.setOnClickListener(this);
        uploadCfrom.setOnClickListener(this);

        myCompanyDetails = getSharedPreferences(COMPANY_DETAILS,0);


        myCompanyAvtar = (NetworkImageView) findViewById(R.id.myCompanyAvtar);

        imageLoader = MySingleton.getInstance(MyCompany.this).getImageLoader();

        myCompanyAvtar.setImageUrl((String) myCompanyDetails.getString("companyBanner",getString(R.string.host)+"/mycompany/defaults/company_default.jpg"), imageLoader);
        companyPicPreview = (ImageView) findViewById(R.id.companyPicPreview);
        imageProgress = (LinearLayout) findViewById(R.id.companyImageProgress);


        // EditTexts
        myCompanyTagE = (EditText)findViewById(R.id.myCompanyTagE);
        myCompanyTitleE = (EditText)findViewById(R.id.myCompanyTitleE);
        myCompanyTitleE.setEnabled(false);

        // TextViews
        myCompanyTagV = (TextView)findViewById(R.id.myCompanyTagV);
        myCompanyTitleV = (TextView) findViewById(R.id.myCompanyTitleV);



        // Company Contact Pannel
        myCompanyCallV = (TextView)findViewById(R.id.myCompanyCallV);
        myCompanyVisitV = (TextView)findViewById(R.id.myCompanyVisitV);
        myCompanyMailV = (TextView)findViewById(R.id.myCompanyMailV);

        myCompanyCallE = (EditText)findViewById(R.id.myCompanyCallE);
        myCompanyVisitE = (EditText)findViewById(R.id.myCompanyVisitE);
        myCompanyMailE = (EditText)findViewById(R.id.myCompanyMailE);





        // CompanyA About
        myCompanyAboutE = (EditText)findViewById(R.id.myCompanyAboutE);
        myCompanyAboutV = (TextView)findViewById(R.id.myCompanyAboutV);



        // Company Address
        myCompanyCityV = (TextView)findViewById(R.id.myCompanyCityV);
        mycompanyLandmarkV = (TextView) findViewById(R.id.myCompanyLandmarkV);
        myCompanyStateV = (TextView)findViewById(R.id.myCompanyStateV);

        myCompanyCityE = (EditText) findViewById(R.id.myCompanyCityE);
        mycompanyLandmarkE = (EditText) findViewById(R.id.myCompanyLandmarkE);
        myCompanyStateS = (Spinner)findViewById(R.id.myCompanyStateS);





        // Body Flippers
        titleFlipper = (ViewFlipper)findViewById(R.id.titleFlipper);
        contactFlipper = (ViewFlipper)findViewById(R.id.contactFlipper);
        aboutFlipper = (ViewFlipper)findViewById(R.id.aboutFlipper);
        addressCFlipper = (ViewFlipper)findViewById(R.id.companyAddressFlipper);


        // PannelFlippers
        titlePannelFlipper = (ViewFlipper)findViewById(R.id.titlePannelFlipper);
        contactPannelFlipper = (ViewFlipper)findViewById(R.id.contactPannelFlipper);
        aboutPannelFlipper = (ViewFlipper)findViewById(R.id.aboutPannelFlipper);
        addressPannelFlipper = (ViewFlipper)findViewById(R.id.addressCPannelFlipper);

        // editFlipButtons
        editFlipTitle = (ImageView) findViewById(R.id.editFlipTitle);
        editFlipAbout = (ImageView) findViewById(R.id.editFlipAbout);
        editFlipContact = (ImageView) findViewById(R.id.exitFlipContact);
        editFlipAddressc = (ImageView) findViewById(R.id.editFlipAddressC);









        saveTitle = (Button)findViewById(R.id.saveTitle);
        saveContact = (Button)findViewById(R.id.saveContact);
        saveAbout = (Button)findViewById(R.id.saveAbout);
        saveAddress = (Button)findViewById(R.id.saveAddressC);

        cancelTitle = (Button)findViewById(R.id.cancelTitle);
        cancelContact = (Button)findViewById(R.id.cancelContact);
        cancelAbout = (Button)findViewById(R.id.cancelAbout);
        cancelAddress = (Button)findViewById(R.id.cancelAddressC);





        editFlipTitle.setOnClickListener(this);
        editFlipContact.setOnClickListener(this);
        editFlipAddressc.setOnClickListener(this);
        editFlipAbout.setOnClickListener(this);


        saveTitle.setOnClickListener(this);
        saveAddress.setOnClickListener(this);
        saveContact.setOnClickListener(this);
        saveAbout.setOnClickListener(this);

        cancelAbout.setOnClickListener(this);
        cancelContact.setOnClickListener(this);
        cancelTitle.setOnClickListener(this);
        cancelAddress.setOnClickListener(this);




    }

    public void setAddress(){


        myCompanyCityV.setText(myCompanyDetails.getString("companyCity","No City Set, Update Your City Now "));
        mycompanyLandmarkV.setText(myCompanyDetails.getString("companyLandmark","No landmark Set, Update Landmark Now"));
        myCompanyStateV.setText(myCompanyDetails.getString("companyState","No State Set, Update Now"));


        myCompanyCityE.setText(myCompanyDetails.getString("companyCity","No City Set, Update Your City Now "));
        mycompanyLandmarkE.setText(myCompanyDetails.getString("companyLandmark","No landmark Set, Update Landmark Now"));
        ArrayAdapter<String> typeAdapter;
        typeAdapter = new ArrayAdapter<String>(MyCompany.this, R.layout.spinner_adapter_black, R.id.spinnerTextBlack,statesArray);
        myCompanyStateS.setAdapter(typeAdapter);
        myCompanyStateS.setSelection(statesArray.indexOf(myCompanyStateV.getText().toString()));




    }

    public void setCompanyAbout(){

        myCompanyAboutV.setText(myCompanyDetails.getString("companyDiscription","No Discription Found , lets Start putting up a nice Company Discription"));
        myCompanyAboutE.setText(myCompanyDetails.getString("companyDiscription","No Discription Found , lets Start putting up a nice Company Discription"));

    }

    public  void setContactText(){

        myCompanyCallV.setText(myCompanyDetails.getString("companyPhone","No Number Found, Update Phone Number Now"));
        myCompanyVisitV.setText(myCompanyDetails.getString("companyVisit","No Website Found, Update Phone Website Now"));
        myCompanyMailV.setText(myCompanyDetails.getString("companyEmail","No Email Found, Update Email Now"));



        myCompanyCallE.setText(myCompanyDetails.getString("companyPhone","No Number Found, Update Phone Number Now"));
        myCompanyVisitE.setText(myCompanyDetails.getString("companyVisit","No Website Found, Update Phone Website Now"));
        myCompanyMailE.setText(myCompanyDetails.getString("companyEmail","No Email Found, Update Email Now"));


    }

    public void setNameTag(){

        myCompanyDetails = getSharedPreferences(COMPANY_DETAILS,0);

        myCompanyTitleE.setText(myCompanyDetails.getString("companyName","Something Went Wrong"));
        myCompanyTagE.setText(myCompanyDetails.getString("companyTag","Something Went Wrong"));

        myCompanyTitleV.setText(myCompanyDetails.getString("companyName","Something Went Wrong"));
        myCompanyTagV.setText(myCompanyDetails.getString("companyTag","Something Went Wrong"));



    }

    boolean checkConnection(){

       ConnectivityManager connectivityManager = (ConnectivityManager)  MyCompany.this.getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if(networkInfo !=null && networkInfo.isConnected()){
            return true;

        } else {
            return false;
        }

    }





    @Override
    public void onClick(View v) {


        switch (v.getId()){


            case R.id.selectCFromGallery:
                imagePermissionItem(v);
                    break;
            
            case R.id.editCompanyImage:
                    companyImageFlipper.showNext();
                    break;
            case R.id.cancelCFrom:

                if (companyPicPreview.getVisibility() == View.VISIBLE){

                    companyPicPreview.startAnimation(AnimationUtils.loadAnimation(MyCompany.this,R.anim.abc_fade_out));
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                           companyPicPreview.setVisibility(View.GONE);
                        }
                    },400);
                }

                companyImageFlipper.showPrevious();    
                break;
            case R.id.uploadCfrom:

                
                if (companyPicPreview.getVisibility() != View.VISIBLE){
                    Toast.makeText(MyCompany.this, "No Image Selected", Toast.LENGTH_SHORT).show();
                }else {
                    sendImage(v);
                }
                
                    break;
            
            


            case R.id.editFlipTitle:

                titleFlipper.showNext();
                titlePannelFlipper.showNext();

                break;

            case R.id.editFlipAddressC:

                addressCFlipper.showNext();
                addressPannelFlipper.showNext();

                break;

            case R.id.exitFlipContact:

                contactFlipper.showNext();
                contactPannelFlipper.showNext();

                break;

            case R.id.editFlipAbout:

                aboutFlipper.showNext();
                aboutPannelFlipper.showNext();

                break;



            case R.id.cancelContact:


                contactFlipper.showNext();
                contactPannelFlipper.showPrevious();

                break;

            case R.id.cancelAddressC:

                addressCFlipper.showNext();
                addressPannelFlipper.showPrevious();

                break;

            case R.id.cancelAbout:

                aboutFlipper.showNext();
                aboutPannelFlipper.showPrevious();

                break;

            case R.id.cancelTitle:

                titleFlipper.showNext();
                titlePannelFlipper.showPrevious();

                break;

            case R.id.saveContact:

                if (checkConnection()){



                    if (myCompanyCallE.getText().toString().length() == 10){

                        new UpdateContact().execute();

                    }else {

                        Toast.makeText(MyCompany.this, "Please Enter 10 digits exaclty", Toast.LENGTH_SHORT).show();
                    }


                }else {
                    Snackbar.make(v,"No Internet Connectivity",Snackbar.LENGTH_LONG).show();
                }



                break;

            case R.id.saveAddressC:

                if (checkConnection()){


                    if (myCompanyStateS.getSelectedItem().toString().equals("Select Your State") || myCompanyCityE.getText().toString().length() < 5 || mycompanyLandmarkE.getText().toString().length() < 8){

                        if (myCompanyStateS.getSelectedItem().toString().equals("Select Your State")){
                            Toast.makeText(MyCompany.this, "Please Select a valid State", Toast.LENGTH_SHORT).show();
                        }else if (myCompanyCityE.getText().toString().length() < 5){
                            Toast.makeText(MyCompany.this, "Company City Name must be at least 6 characters long", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(MyCompany.this, "Company landmark  must be at least 9 characters long", Toast.LENGTH_SHORT).show();
                        }

                    }else {
                        new UpdateAddress().execute();

                    }



                }else {
                    Snackbar.make(v,"No Internet Connectivity",Snackbar.LENGTH_LONG).show();
                }

                break;

            case R.id.saveAbout:

                if (checkConnection()){


                    if (myCompanyAboutE.getText().toString().length() > 10){
                        new UpdateAbout().execute();

                    }else {
                        Toast.makeText(MyCompany.this, "Please describe your company in more than 10 Characters but less than 40 Characters", Toast.LENGTH_SHORT).show();

                    }


                }else {
                    Snackbar.make(v,"No Internet Connectivity",Snackbar.LENGTH_LONG).show();
                }


                break;

            case R.id.saveTitle:

                if (checkConnection()){


                    if(myCompanyTagE.getText().toString().length() > 4){

                        new UpdateCompanyNameTag().execute();

                    }else {
                        Toast.makeText(MyCompany.this, "Company Tag must be at least 5 Characters Long", Toast.LENGTH_SHORT).show();

                    }


                }else {
                    Snackbar.make(v,"No Internet Connectivity",Snackbar.LENGTH_LONG).show();
                }


                break;



















        }





    }

    private class UpdateCompanyNameTag extends AsyncTask< String, String, String>{


        StringBuilder builder = new StringBuilder();
        BufferedReader bufferedReader;
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        String COMPANY_TAG;


        @Override
        protected String doInBackground(String... params) {

         nameValuePairs.add(new BasicNameValuePair("userPhone",USER_EMAIL));
            nameValuePairs.add(new BasicNameValuePair("companyTag",COMPANY_TAG));



            try{
                HttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost(getString(R.string.host)+"/mycompany/nametag_up.php");

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
            } catch (Exception e ) {

            }

            return builder.toString();
        }


        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            titlePannelFlipper.showNext();
            myCompanyTitleE.setEnabled(false);
            myCompanyTagE.setEnabled(false);
            COMPANY_TAG =  myCompanyTagE.getText().toString();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            myCompanyDetails = getSharedPreferences(COMPANY_DETAILS,0);

            editor = myCompanyDetails.edit();

            if (s.contains("1")){

                titlePannelFlipper.showNext();
                titleFlipper.showNext();
                editor.putString("companyTag",myCompanyTagE.getText().toString());
                editor.commit();
                myCompanyTagV.setText(myCompanyTagE.getText().toString());

            }else {

                titlePannelFlipper.showPrevious();

                Toast.makeText(MyCompany.this, "We Couldn't Update Your Details", Toast.LENGTH_SHORT).show();
//titleFlipper.showNext();
//                titlePannelFlipper.showNext();
//                titleFlipper.showNext();
//
//                editor.putString("companyName",myCompanyTitleE.getText().toString());
//                editor.putString("companyTag",myCompanyTagE.getText().toString());
//                editor.commit();
//
//                myCompanyTitleV.setText(myCompanyTitleE.getText().toString());
//                myCompanyTagV.setText(myCompanyTagE.getText().toString());
            }


            myCompanyTagE.setEnabled(true);

        }

    }

    private class UpdateContact extends AsyncTask<String , String ,String>{

        StringBuilder builder = new StringBuilder();
        BufferedReader bufferedReader;
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        String COMPANY_CALL,COMPANYeMAIL,COMPANYvISIT;


        protected String doInBackground(String... params){

            nameValuePairs.add(new BasicNameValuePair("userPhone",USER_EMAIL));
            nameValuePairs.add(new BasicNameValuePair("companyPhone",COMPANY_CALL));
            nameValuePairs.add(new BasicNameValuePair("companyEmail",COMPANYeMAIL));
            nameValuePairs.add(new BasicNameValuePair("companyVisit",COMPANYvISIT));


            try {

                HttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost(getString(R.string.host)+"/mycompany/contact_up.php");

                httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                HttpResponse httpResponse = (HttpResponse) httpClient.execute(httpPost);


                HttpEntity httpEntity = (HttpEntity) httpResponse.getEntity();


                bufferedReader = new BufferedReader(new InputStreamReader(httpEntity.getContent()));
                String str = "";

                while ( (str = bufferedReader.readLine()) != null ){
                    builder.append(str);
                }






            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch ( Exception e ){

            }


            return builder.toString();

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            contactPannelFlipper.showNext();
            myCompanyMailE.setEnabled(false);
            myCompanyCallE.setEnabled(false);
            myCompanyVisitE.setEnabled(false);
            COMPANY_CALL = myCompanyCallE.getText().toString();
            COMPANYeMAIL = myCompanyMailE.getText().toString();
            COMPANYvISIT = myCompanyVisitE.getText().toString();

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            editor = myCompanyDetails.edit();

            if (s.contains("1")){

                contactFlipper.showNext();
                contactPannelFlipper.showNext();

                editor.putString("companyPhone",myCompanyCallE.getText().toString());
                editor.putString("companyEmail",myCompanyMailE.getText().toString());
                editor.putString("companyVisit",myCompanyVisitE.getText().toString());

                editor.commit();

                myCompanyCallV.setText(myCompanyCallE.getText().toString());
                myCompanyMailV.setText(myCompanyMailE.getText().toString());
                myCompanyVisitV.setText(myCompanyVisitE.getText().toString());



            }else{

                contactPannelFlipper.showPrevious();
                Toast.makeText(MyCompany.this, "We are having Trouble Connecting To Internet", Toast.LENGTH_SHORT).show();

            }



            myCompanyMailE.setEnabled(true);
            myCompanyCallE.setEnabled(true);
            myCompanyVisitE.setEnabled(true);

        }
    }

    private class UpdateAbout extends AsyncTask<String ,String , String>{


        StringBuilder builder = new StringBuilder();
        BufferedReader bufferedReader;
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        String COMPANY_ABOUT;


        protected String doInBackground(String... params){

            nameValuePairs.add(new BasicNameValuePair("userPhone",USER_EMAIL));
            nameValuePairs.add(new BasicNameValuePair("companyDiscription",COMPANY_ABOUT));

            try {

                HttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost(getString(R.string.host)+"/mycompany/about_up.php");

                httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                HttpResponse httpResponse = (HttpResponse) httpClient.execute(httpPost);


                HttpEntity httpEntity = (HttpEntity) httpResponse.getEntity();


                bufferedReader = new BufferedReader(new InputStreamReader(httpEntity.getContent()));
                String str = "";

                while ( (str = bufferedReader.readLine()) != null ){
                    builder.append(str);
                }


            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch( Exception e ){

            }


            return builder.toString();

        }


        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            aboutPannelFlipper.showNext();
            myCompanyAboutE.setEnabled(false);
            COMPANY_ABOUT = myCompanyAboutE.getText().toString();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            editor = myCompanyDetails.edit();

            if (s.equals("1")){
                aboutFlipper.showNext();
                editor.putString("companyDiscription",myCompanyAboutE.getText().toString());
                editor.commit();

                myCompanyAboutV.setText(myCompanyAboutE.getText().toString());
                aboutPannelFlipper.showNext();

            }else{

                aboutPannelFlipper.showPrevious();
                Toast.makeText(MyCompany.this, "We are having Trouble Connecting To Internet, PLease Try Again" + s, Toast.LENGTH_SHORT).show();

            }
            Log.e("COMOUTPUT",s);
            myCompanyAboutE.setEnabled(true);
        }


    }

    private class UpdateAddress extends AsyncTask<String , String, String>{




        StringBuilder builder = new StringBuilder();
        BufferedReader bufferedReader;
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        String STATE,CITY,LANDMARK;



        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            addressPannelFlipper.showNext();
            myCompanyCityE.setEnabled(false);
            mycompanyLandmarkE.setEnabled(false);
            myCompanyStateS.setEnabled(false);


            STATE = myCompanyStateS.getSelectedItem().toString();
            CITY = myCompanyCityE.getText().toString();
            LANDMARK = mycompanyLandmarkE.getText().toString();


        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            editor = myCompanyDetails.edit();

            if(s.equals("1")){

                addressCFlipper.showNext();
                addressPannelFlipper.showNext();
                editor.putString("companyState",myCompanyStateS.getSelectedItem().toString());
                editor.putString("companyCity",myCompanyCityE.getText().toString());
                editor.putString("companyLandmark",mycompanyLandmarkE.getText().toString());

                editor.commit();

                myCompanyStateV.setText(myCompanyStateS.getSelectedItem().toString());
                myCompanyCityV.setText(myCompanyCityE.getText().toString());
                mycompanyLandmarkV.setText(mycompanyLandmarkE.getText().toString());

                myCompanyCityE.setEnabled(true);
                mycompanyLandmarkE.setEnabled(true);
                myCompanyStateS.setEnabled(true);

            }else{
                myCompanyCityE.setEnabled(true);
                mycompanyLandmarkE.setEnabled(true);
                myCompanyStateS.setEnabled(true);
                addressPannelFlipper.showPrevious();
                Toast.makeText(MyCompany.this, "We are having Trouble Connecting To Internet, PLease Try Again", Toast.LENGTH_SHORT).show();

            }
            myCompanyStateS.setSelection(statesArray.indexOf(myCompanyStateV.getText().toString()));
            setAddress();


        }


        @Override
        protected String doInBackground(String... params) {

            nameValuePairs.add(new BasicNameValuePair("userPhone",USER_EMAIL));
            nameValuePairs.add(new BasicNameValuePair("companyState",STATE));
            nameValuePairs.add(new BasicNameValuePair("companyCity",CITY));
            nameValuePairs.add(new BasicNameValuePair("companyLandmark",LANDMARK));

            try {

                HttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost(getString(R.string.host)+"/mycompany/address_up.php");

                httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                HttpResponse httpResponse = (HttpResponse) httpClient.execute(httpPost);


                HttpEntity httpEntity = (HttpEntity) httpResponse.getEntity();


                bufferedReader = new BufferedReader(new InputStreamReader(httpEntity.getContent()));
                String str = "";

                while ( (str = bufferedReader.readLine()) != null ){
                    builder.append(str);
                }


            } catch (UnsupportedEncodingException e) {
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





    public void imagePermissionItem(View view) {
        if (ContextCompat.checkSelfPermission(MyCompany.this, Manifest.permission.READ_EXTERNAL_STORAGE)
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
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GALLERY_PICTURE)
        {
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
            myBitmap = (Bitmap)extras.getParcelable("data");
            companyPicPreview.setVisibility(View.VISIBLE);
            companyPicPreview.setImageBitmap(myBitmap);
        }


    }

    //take image from gallery and crop
    ////////////////////////////////////////////////////////////////////////////////////////////////
    public void Image_Selecting_Task(Uri data)
    {
        if(data == null)
        {
            return;
        }
        Uri selectedImageUri = data;

        try {

            String selectedImagePath = getPath(selectedImageUri);
            myBitmap = BitmapFactory.decodeFile(selectedImagePath);
            companyPicPreview.setVisibility(View.VISIBLE);
            ImageView image = (ImageView) findViewById(R.id.companyPicPreview);
            image.setImageBitmap(myBitmap);

        }
        catch(Exception e)
        {
            Log.e("StatusBitmap", e.toString());
        }

//        try {
//
//            Intent cropIntent = new Intent("com.android.camera.action.CROP");
//
//            cropIntent.setDataAndType(selectedImageUri, "image/*");
//            cropIntent.putExtra("crop", true);
//            cropIntent.putExtra("aspectX", 0);
//            cropIntent.putExtra("aspectY", 0);
//            cropIntent.putExtra("outputX", 200);
//            cropIntent.putExtra("outputY", 150);
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
//            companyPicPreview.setVisibility(View.VISIBLE);
//            ImageView image = (ImageView) findViewById(R.id.companyPicPreview);
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
            Toast.makeText(MyCompany.this,"Failed to get image", Toast.LENGTH_LONG).show();
            return null;
        }

        // try to retrieve the image from the media store first
        // this will only work for images selected from gallery
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = MyCompany.this.getContentResolver().query(uri, projection, null, null, null);
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
    public void requestPermissions(String... permission)
    {

        if(ContextCompat.checkSelfPermission(MyCompany.this, permission[0]) != PackageManager.PERMISSION_GRANTED)
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

            if (ActivityCompat.shouldShowRequestPermissionRationale(MyCompany.this,
                    Manifest.permission.READ_EXTERNAL_STORAGE)) {

            }
            if (grantResults.length == 1 &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                selectImage();

            } else {
                Toast.makeText(MyCompany.this, "You have restricted "+getString(R.string.app_name)+" app to access to gallery", Toast.LENGTH_SHORT).show();

                android.support.v7.app.AlertDialog alertDialog = null;
                android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(MyCompany.this,R.style.AppTheme);
                builder.setTitle("Want to select image from gallery");
                builder.setMessage("Allow "+getString(R.string.app_name)+" app to select images from gallery. This enables you to change your profile picture. " +
                        "Go to Settings to turn on Gallery Access.\n\nTo enable this, click "+getString(R.string.app_name)+" App Settings below and activate Storage under the permissions menu.");

                builder.setPositiveButton("App Settings", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent();
                        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        Uri uri = Uri.fromParts("package", MyCompany.this.getPackageName(), null);
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

            imageProgress.startAnimation(AnimationUtils.loadAnimation(MyCompany.this,R.anim.abc_fade_in));
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    imageProgress.setVisibility(View.VISIBLE);
                }
            },300);
            companyImageFlipper.showNext();

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);


            if (s.contains("comp")){

                imageProgress.startAnimation(AnimationUtils.loadAnimation(MyCompany.this,R.anim.abc_fade_out));
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        imageProgress.setVisibility(View.GONE);

                    }
                },300);
                companyPicPreview.setVisibility(View.VISIBLE);
                editor = myCompanyDetails.edit();

                editor.putString("companyBanner",s);
                editor.apply();
                companyImageFlipper.showNext();
                final MediaPlayer ourSplasSound =  MediaPlayer.create(MyCompany.this,R.raw.notific);
                ourSplasSound.start();




                NotificationCompat.Builder builder = new NotificationCompat.Builder(MyCompany.this);
                builder.setContentTitle("Profile Picture Updated");
                builder.setContentText("Your new company Banner has been successfully updated");
                builder.setSmallIcon(R.drawable.verified_user);
                builder.setTicker("Profile Picture updated Successfully");
                builder.setAutoCancel(true);

                myCompanyAvtar.setImageUrl((String) myCompanyDetails.getString("companyBanner",getString(R.string.host)+"/mycompany/defaults/company_default.jpg"), imageLoader);

                Notification notification = builder.build();
                NotificationManager notificationManager = (NotificationManager) MyCompany.this.getSystemService(NOTIFICATION_SERVICE);
                try{
                    notificationManager.notify(1234,notification);
                }catch (Exception e){
                    e.printStackTrace();
                }




            }else if (s.contains("no64")){

                imageProgress.startAnimation(AnimationUtils.loadAnimation(MyCompany.this,R.anim.abc_fade_out));
                new Handler().postDelayed(new Runnable() {
                     @Override
                     public void run() {
                         imageProgress.setVisibility(View.GONE);

                     }
                 },300);
                Toast.makeText(MyCompany.this, "Couldn't upload Image due to slow Connection, Please try again", Toast.LENGTH_SHORT).show();
                companyImageFlipper.showPrevious();

            }else{

                imageProgress.startAnimation(AnimationUtils.loadAnimation(MyCompany.this,R.anim.abc_fade_out));
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        imageProgress.setVisibility(View.GONE);

                    }
                },300);

                if (s.contains("Large")){
                    Toast.makeText(MyCompany.this, "File Size is Too Large, file size should be approx. less than 1 MB", Toast.LENGTH_SHORT).show();

                }else {
                    Toast.makeText(MyCompany.this, "We are having trouble connecting to the internet, Please check your Connection ", Toast.LENGTH_SHORT).show();

                }

                companyImageFlipper.showPrevious();

            }

            Log.e("MYCOMPANYBANNER",s);

        }

        @Override
        protected String doInBackground(String... params) {

            nameValuePairs.add(new BasicNameValuePair("userPhone",USER_EMAIL));
            nameValuePairs.add(new BasicNameValuePair("base64",base64));



            try{

                HttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost(getString(R.string.host)+"/mycompany/update_pic.php");
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
