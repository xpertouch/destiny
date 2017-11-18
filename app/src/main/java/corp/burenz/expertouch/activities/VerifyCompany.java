package corp.burenz.expertouch.activities;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
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
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

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

public class VerifyCompany extends AppCompatActivity {


    String COMPANY_DETAILS = "myCompanyDetails";
    SharedPreferences myCompanyDetails;

    String COMPANY_NAME,COMPANY_PHONE;

    LinearLayout placeCall2Company, resendCode;
    ViewFlipper companyVerificationFlipper;

    ViewFlipper sendGreenFlagFlipper;
    ImageButton senddTruebtn;


    SharedPreferences userData;
    String LOCAL_APP_DATA = "userInformation";


    EditText accessCodeET;

    Animation fadeIn,fadeOut;
    View myView;

    public static final int GALLERY_PICTURE = 1;
    public static final int READ_GALLERY_PERMISSIONS_REQUEST = 2;
    public static final int  REQUEST_IMAGE_CROP = 3;
    Bitmap myBitmap;

    Intent imageIntent;
    ImageButton selectFromGallery;
    String base64;
    LinearLayout imageProgress;



    // verifyPhaseOne
    LinearLayout verifyPhaseOneLL;
    TextView learnMoreTV;
    Button uploadRC;
    Button skipUpload;
    TextView signedInAs;


    // verifyPhase2
    ImageView certificatePreview;
    LinearLayout progressCertificateLL;
    Button initiateUploadLL;
    LinearLayout verifyPhaseTwo;

    //verifyPhaseThree;
    LinearLayout verifyPhaseThree;
    String COMPANY_ADMIN;









    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_company);


        setViews();



        myCompanyDetails = getSharedPreferences(COMPANY_DETAILS,0);
        if (myCompanyDetails.getString("COTP","O").length() == 4){
            companyVerificationFlipper.showNext();
        }else {
            new SendAccessCode().execute();
        }



        signedInAs.setText("Currently Signed in as "+COMPANY_NAME +" Administrator");



        uploadRC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imagePermissionItem(v);
            }
        });


        initiateUploadLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendImage(v);
            }
        });


        skipUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ConnectivityManager connectivityManager  = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
                final NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();


                if(networkInfo!= null && networkInfo.isConnected() ){


                    verifyPhaseThree.startAnimation(AnimationUtils.loadAnimation(VerifyCompany.this,R.anim.abc_slide_in_top));

                    verifyPhaseOneLL.setVisibility(View.GONE);
                    verifyPhaseTwo.setVisibility(View.GONE);
                    verifyPhaseThree.setVisibility(View.VISIBLE);


                }else{
                    Toast.makeText(VerifyCompany.this, "Please Connect to internet and try again", Toast.LENGTH_SHORT).show();
                }



            }
        });


        resendCode.setEnabled(true);
        resendCode.setVisibility(View.VISIBLE);
        resendCode.setClickable(true);

        senddTruebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (accessCodeET.getText().toString().length() == 4){


                    if (accessCodeET.getText().toString().equals(myCompanyDetails.getString("COTP","O"))){

                        new SendTrue().execute();

                    }else {
                        Toast.makeText(VerifyCompany.this, "Activation Code Doesn't Match", Toast.LENGTH_SHORT).show();
                    }
                }else {

                    Toast.makeText(VerifyCompany.this, "Please Enter a 4 digit Activation Code", Toast.LENGTH_SHORT).show();
                }


//                resendCode.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        myView = v;
//                        new PingCare().execute();
//
//
//                    }
//                });







            }
        });


    }


    public void setViews() {

      //  placeCall2Company = (LinearLayout) findViewById(R.id.placeCall2Company);
        resendCode = (LinearLayout) findViewById(R.id.requestCallLL);
        myCompanyDetails = getSharedPreferences(COMPANY_DETAILS,0);
        companyVerificationFlipper = (ViewFlipper) findViewById(R.id.companyVerificationFlipper);
        sendGreenFlagFlipper = (ViewFlipper) findViewById(R.id.sendGreenFlagFlipper);
        senddTruebtn = (ImageButton) findViewById(R.id.sendTrueBtn);
        accessCodeET = (EditText) findViewById(R.id.accessCodeET);






        verifyPhaseOneLL = (LinearLayout) findViewById(R.id.verifyPhaseOne);
        certificatePreview = (ImageView) findViewById(R.id.certificatePreview);
        progressCertificateLL = (LinearLayout) findViewById(R.id.progressCertificate);
        initiateUploadLL  = (Button) findViewById(R.id.uploadRC);
        verifyPhaseTwo = (LinearLayout) findViewById(R.id.verifyPhaseTwo);
        verifyPhaseThree = (LinearLayout) findViewById(R.id.verifyPhaseThree);
        uploadRC = (Button) findViewById(R.id.acceptUpload);
        skipUpload = (Button) findViewById(R.id.alreadyDid);
        signedInAs = (TextView)findViewById(R.id.signedInAs);

        fadeIn = AnimationUtils.loadAnimation(VerifyCompany.this,R.anim.abc_fade_in);
        fadeOut = AnimationUtils.loadAnimation(VerifyCompany.this,R.anim.abc_fade_out);


        COMPANY_NAME = myCompanyDetails.getString("companyName","companyName");
        COMPANY_PHONE = myCompanyDetails.getString("companyPhone","companyPhone");


    }










    class SendTrue extends AsyncTask<String,String,String>{


        StringBuilder builder = new StringBuilder();
        BufferedReader bufferedReader;

        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            sendGreenFlagFlipper.showNext();

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            SharedPreferences.Editor editor;
            editor = myCompanyDetails.edit();
           if (s.equals("1")){
                editor.putBoolean("CVERIFIED",true);
                editor.commit();
                startActivity(new Intent(VerifyCompany.this,PostAdd.class));
                VerifyCompany.this.finish();
            }else{
                Toast.makeText(VerifyCompany.this, "Connection Poor , Please try again Later", Toast.LENGTH_SHORT).show();
                sendGreenFlagFlipper.showNext();
            }


        }

        @Override
        protected String doInBackground(String... params) {


            nameValuePairs.add(new BasicNameValuePair("companyName",COMPANY_NAME));

            try {


                HttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost(getString(R.string.host)+"/verify/flag_company.php");
                httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                HttpResponse httpResponse = (HttpResponse) httpClient.execute(httpPost);
                HttpEntity httpEntity = httpResponse.getEntity();

                bufferedReader = new BufferedReader(new InputStreamReader(httpEntity.getContent()));
                String str = "";

                while ((str = bufferedReader.readLine()) != null) {

                    builder.append(str);
                }


            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {

            }


            return builder.toString();
        }

    }

    class SendAccessCode extends AsyncTask<String, String, String> {


        StringBuilder builder = new StringBuilder();
        BufferedReader bufferedReader;

        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            SharedPreferences.Editor editor;
            editor = myCompanyDetails.edit();


            if (s.length() == 4){

                editor.putString("COTP",s);
                editor.commit();
                companyVerificationFlipper.showNext();
            }else {

                Toast.makeText(VerifyCompany.this, "Connection Slow", Toast.LENGTH_SHORT).show();
            }

        }


        @Override
        protected String doInBackground(String... params) {


            nameValuePairs.add(new BasicNameValuePair("companyTitle",COMPANY_NAME));
            nameValuePairs.add(new BasicNameValuePair("companyNumber",COMPANY_PHONE));

            try {


                HttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost(getString(R.string.host)+"/verify/request_call.php");
                httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                HttpResponse httpResponse = (HttpResponse) httpClient.execute(httpPost);
                HttpEntity httpEntity = httpResponse.getEntity();

                bufferedReader = new BufferedReader(new InputStreamReader(httpEntity.getContent()));
                String str = "";

                while ((str = bufferedReader.readLine()) != null) {

                    builder.append(str);
                }


            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {

            }


            return builder.toString();
        }

    }

    class PingCare extends AsyncTask<String, String, String> {


        StringBuilder builder = new StringBuilder();
        BufferedReader bufferedReader;

        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            resendCode.startAnimation(AnimationUtils.loadAnimation(VerifyCompany.this,R.anim.fab_close));
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    resendCode.setVisibility(View.INVISIBLE);
                }
            },500);


        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            SharedPreferences.Editor editor;
            editor = myCompanyDetails.edit();

            if (s.length() == 4){

                editor.putString("COTP",s);
                editor.commit();

                Snackbar snackbar = Snackbar.make(myView, "Request Completed Successfully, You would recieve a call from our Team soon", Snackbar.LENGTH_LONG)
                        .setAction("Action", null);
                View sbView = snackbar.getView();
                TextView tv = (TextView)sbView.findViewById(android.support.design.R.id.snackbar_text);
                tv.setGravity(Gravity.CENTER);
                tv.setTextColor(Color.argb(255,255,136,0));
                sbView.setBackgroundColor(Color.BLACK);
                snackbar.show();




            }else {

                Snackbar snackbar = Snackbar.make(myView, "Connection Slow, Please Review your Internet Connection", Snackbar.LENGTH_LONG)
                        .setAction("Action", null);
                View sbView = snackbar.getView();
                TextView tv = (TextView)sbView.findViewById(android.support.design.R.id.snackbar_text);
                tv.setGravity(Gravity.CENTER);
                tv.setTextColor(Color.WHITE);
                sbView.setBackgroundColor(Color.RED);
                snackbar.show();


            }


            resendCode.startAnimation(AnimationUtils.loadAnimation(VerifyCompany.this,R.anim.card_animation));
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    resendCode.setVisibility(View.VISIBLE);
                }
            },500);

        }

        @Override
        protected String doInBackground(String... params) {


            nameValuePairs.add(new BasicNameValuePair("companyName",COMPANY_NAME));
            nameValuePairs.add(new BasicNameValuePair("companyNumber",COMPANY_PHONE));


            try {

                HttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost(getString(R.string.host)+"/verify/ping_care.php");

                httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                HttpResponse httpResponse = (HttpResponse) httpClient.execute(httpPost);
                HttpEntity httpEntity = httpResponse.getEntity();
                bufferedReader = new BufferedReader(new InputStreamReader(httpEntity.getContent()));
                String str = "";

                while ((str = bufferedReader.readLine()) != null) {

                    builder.append(str);
                }


            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {

            }

            return builder.toString();
        }

    }






    public void imagePermissionItem(View view) {
        if (ContextCompat.checkSelfPermission(VerifyCompany.this, Manifest.permission.READ_EXTERNAL_STORAGE)
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
            else {
                return;
            }

        }else if (requestCode == REQUEST_IMAGE_CROP && resultCode == RESULT_OK){
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap)extras.get("data");

            verifyPhaseOneLL.setVisibility(View.GONE);
            verifyPhaseTwo.setVisibility(View.VISIBLE);
            certificatePreview.setImageBitmap(imageBitmap);
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
            verifyPhaseOneLL.setVisibility(View.GONE);
            verifyPhaseTwo.setVisibility(View.VISIBLE);
            certificatePreview.setImageBitmap(myBitmap);

        }
        catch(Exception e) {
            Log.e("StatusBitmap", e.toString());
        }

        // crop feature disabled here
//        try {
//
//            Intent cropIntent = new Intent("com.android.camera.action.CROP");
//            cropIntent.setDataAndType(selectedImageUri, "image/*");
//            cropIntent.putExtra("crop", "true");
//            cropIntent.putExtra("aspectX", 1);
//            cropIntent.putExtra("aspectY", 1);
//            cropIntent.putExtra("outputX", 128);
//            cropIntent.putExtra("outputY", 128);
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
//
//            verifyPhaseOneLL.setVisibility(View.GONE);
//            verifyPhaseTwo.setVisibility(View.VISIBLE);
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
            Toast.makeText(VerifyCompany.this,"Failed to get image", Toast.LENGTH_LONG).show();
            return null;
        }

        // try to retrieve the image from the media store first
        // this will only work for images selected from gallery
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = VerifyCompany.this.getContentResolver().query(uri, projection, null, null, null);
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

        if(ContextCompat.checkSelfPermission(VerifyCompany.this, permission[0]) != PackageManager.PERMISSION_GRANTED)
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

            if (ActivityCompat.shouldShowRequestPermissionRationale(VerifyCompany.this,
                    Manifest.permission.READ_EXTERNAL_STORAGE)) {

            }
            if (grantResults.length == 1 &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                selectImage();

            } else {
                Toast.makeText(VerifyCompany.this, "You have restricted "+getString(R.string.app_name)+" app to access to gallery", Toast.LENGTH_SHORT).show();

                android.support.v7.app.AlertDialog alertDialog = null;
                android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(VerifyCompany.this,R.style.AppTheme);
                builder.setTitle("Want to select image from gallery");
                builder.setMessage("Allow "+getString(R.string.app_name)+" app to select images from gallery. This enables you to change your profile picture. " +
                        "Go to Settings to turn on Gallery Access.\n\nTo enable this, click "+getString(R.string.app_name)+" App Settings below and activate Storage under the permissions menu.");

                builder.setPositiveButton("App Settings", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent();
                        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        Uri uri = Uri.fromParts("package", VerifyCompany.this.getPackageName(), null);
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

        ConnectivityManager connectivityManager = (ConnectivityManager) VerifyCompany.this.getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected() ){

            new UpdateImage().execute();
        }else {
            Toast.makeText(VerifyCompany.this, "Please Connect to Internet and try again", Toast.LENGTH_SHORT).show();
        }
    }

    class UpdateImage extends AsyncTask<String,String,String>{

        StringBuilder builder = new StringBuilder();
        BufferedReader bufferedReader;
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressCertificateLL.startAnimation(fadeIn);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    progressCertificateLL.setVisibility(View.VISIBLE);
                }
            },300);
        }

        @Override
        protected void onPostExecute(final String s) {
            super.onPostExecute(s);

Log.e("MYRC",s);

            if (s.contains("rc_")){

                progressCertificateLL.startAnimation(fadeOut);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        progressCertificateLL.setVisibility(View.GONE);
                    }
                },300);

                verifyPhaseTwo.startAnimation(AnimationUtils.loadAnimation(VerifyCompany.this,R.anim.abc_slide_out_top));
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                    verifyPhaseTwo.setVisibility(View.GONE);
                    }
                },300);
                verifyPhaseOneLL.setVisibility(View.GONE);
                verifyPhaseThree.setVisibility(View.VISIBLE);

                // reloading the image here #code...

            }else if (s.contains("no64")){

                progressCertificateLL.startAnimation(fadeOut);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        progressCertificateLL.setVisibility(View.GONE);
                    }
                },500);
                Toast.makeText(VerifyCompany.this, "We are having Trouble Uploading your image, Please try again later", Toast.LENGTH_SHORT).show();

            }else {



                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        progressCertificateLL.startAnimation(fadeOut);
                        new Handler().postDelayed(new Runnable() {

                            @Override
                            public void run() {
                                progressCertificateLL.setVisibility(View.GONE);
                            }
                        },300);

                       if (s.contains("Large")){
                           Toast.makeText(VerifyCompany.this, "File Size is Too Large, file size should be approx. less than 1 MB", Toast.LENGTH_SHORT).show();

                       }else {
                           Toast.makeText(VerifyCompany.this, "We are having trouble connecting to the internet, Please check your Connection ", Toast.LENGTH_SHORT).show();

                       }


                    }
                },1000);
            }
        }




        @Override
        protected String doInBackground(String... params) {
        userData = getSharedPreferences(LOCAL_APP_DATA,0);

            nameValuePairs.add(new BasicNameValuePair("userPhone",userData.getString("userEmail","noEmail@gmail.com")));
            nameValuePairs.add(new BasicNameValuePair("base64",base64));


            try{

                HttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost(getString(R.string.host)+"/mycompany/upload_rc.php");
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


    public void tellMeTermsCom(View v){

        startActivity(new Intent(VerifyCompany.this,TermsOfService.class));


    }





    public void pingCust(View v){
        myView = v;
        new PingCare().execute();

    }

}
