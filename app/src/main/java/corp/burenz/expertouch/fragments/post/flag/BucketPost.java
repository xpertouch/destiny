package corp.burenz.expertouch.fragments.post.flag;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
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
import java.util.ArrayList;
import java.util.List;

import corp.burenz.expertouch.R;
import corp.burenz.expertouch.activities.MyCompanyPosts;

/**
 * Created by xperTouch on 10/12/2016.
 */
public class BucketPost extends Fragment {

    String TITTLE,DISCRIBE;



    EditText promotionTitle,promotionDiscription;
    RadioButton offerRB,productRB,healthRB,educationRb;
    ViewFlipper promotionFlipper;
    LinearLayout flagPromotion;
    String type = "offer";
    String title,discription;

    String COMPANY_ADD  = "companyAdd";
    SharedPreferences companyAdd;
    SharedPreferences.Editor editor;

    public static final int GALLERY_PICTURE = 1;
    public static final int READ_GALLERY_PERMISSIONS_REQUEST = 2;
    public static final int  REQUEST_IMAGE_CROP = 3;
    Bitmap myBitmap;
    Intent imageIntent;

    ImageView bannerPreview;
    LinearLayout attachBanner;

    ImageButton selectFromGallery;
    String base64 = "EMPTY";
    LinearLayout imageProgress;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        View v = inflater.inflate(R.layout.post_bucket,container,false);

        promotionTitle = (EditText) v.findViewById(R.id.promotionTitle);
        promotionDiscription = (EditText) v.findViewById(R.id.promotionDiscription);
        offerRB = (RadioButton) v.findViewById(R.id.offerRB);
        productRB = (RadioButton) v.findViewById(R.id.productRB);
        flagPromotion = (LinearLayout) v.findViewById(R.id.flagPromotion);
        promotionFlipper = (ViewFlipper) v.findViewById(R.id.promotionFlipper);
        healthRB = (RadioButton) v.findViewById(R.id.healthRB);
        educationRb = (RadioButton) v.findViewById(R.id.educationRB);
        attachBanner = (LinearLayout) v.findViewById(R.id.attachBanner);
        bannerPreview = (ImageView) v.findViewById(R.id.bannerPreview);

        attachBanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imagePermissionItem(v);
            }
        });


        offerRB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                type = "offer";
                offerRB.setChecked(true);
                healthRB.setChecked(false);
                productRB.setChecked(false);
                educationRb.setChecked(false);


            }
        });

        productRB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                type = "product";
                productRB.setChecked(true);
                healthRB.setChecked(false);
                educationRb.setChecked(false);
                offerRB.setChecked(false);

            }
        });



        healthRB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                type = "health";
                healthRB.setChecked(true);
                productRB.setChecked(false);
                offerRB.setChecked(false);
                educationRb.setChecked(false);

            }
        });

        educationRb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                type = "education";
                educationRb.setChecked(true);
                productRB.setChecked(false);
                offerRB.setChecked(false);
                healthRB.setChecked(false);


            }
        });





        flagPromotion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                title = promotionTitle.getText().toString();
                discription = promotionDiscription.getText().toString();



                if (bannerPreview.getVisibility() == View.VISIBLE){

                    //add validation here

                    sendImage(v);


                }

                if (promotionTitle.getText().toString().length() > 5){

                    if (promotionDiscription.getText().toString().length() > 10){

                        TITTLE = promotionTitle.getText().toString();
                        DISCRIBE = promotionDiscription.getText().toString();

                        new SendBucket().execute();


                      }else {
                        Toast.makeText(getActivity(), "Promotion Description should be greater than 10 Characters", Toast.LENGTH_SHORT).show();

                    }


                }else {

                    Toast.makeText(getActivity(), "Promotion Title should be greater than 5 Characters", Toast.LENGTH_SHORT).show();
                }


            }
        });


        return v;


    }

    private class SendBucket extends AsyncTask<String , String ,String> {


        StringBuilder builder = new StringBuilder();
        BufferedReader bufferedReader;
        List<NameValuePair> nameValuePairList = new ArrayList<>();

        String COMPANY_DETAILS = "myCompanyDetails";
        SharedPreferences myCompanyDetails;


        @Override
        protected String doInBackground(String... params) {

            myCompanyDetails = getActivity().getSharedPreferences(COMPANY_DETAILS,0);
            companyAdd = getActivity().getSharedPreferences(COMPANY_ADD,0);


            nameValuePairList.add(new BasicNameValuePair("companyTitle",myCompanyDetails.getString("companyName","noTitle")));
            nameValuePairList.add(new BasicNameValuePair("saleTitle",TITTLE));
            nameValuePairList.add(new BasicNameValuePair("saleDiscription",DISCRIBE));
            nameValuePairList.add(new BasicNameValuePair("type",type));
            nameValuePairList.add(new BasicNameValuePair("base64",base64));
            Log.e("BASE64",base64);

            try {

                HttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost(getString(R.string.host)+"/bucket/post_bucket.php");
                httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairList));
                HttpResponse httpResponse = httpClient.execute(httpPost);
                HttpEntity httpEntity = (HttpEntity) httpResponse.getEntity();


                bufferedReader = new BufferedReader(new InputStreamReader(httpEntity.getContent()));
                String str = "";
                while ( (str = bufferedReader.readLine()  ) != null ){

                    builder.append(str);

                }



            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e){
                Toast.makeText(getActivity(), "Caught an Exception \n " + e, Toast.LENGTH_SHORT).show();
            }


            return builder.toString();

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            promotionFlipper.showNext();

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            Log.e("POSTER",s);

            if (s.contains("0")){

                Toast.makeText(getActivity(), "Network Failure, Please try Again", Toast.LENGTH_SHORT).show();
                promotionFlipper.showNext();

            }else if (s.contains("1")){

                promotionFlipper.startAnimation(AnimationUtils.loadAnimation(getActivity(),R.anim.md_styled_slide_down_slow));
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        promotionFlipper.setVisibility(View.GONE);

                    }
                },500);


                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        startActivity(new Intent(getActivity(),MyCompanyPosts.class));
                        getActivity().finish();

                    }
                },100);


            }else if (s.contains("exists")){

                Toast.makeText(getActivity(), "Promotion Already Exits", Toast.LENGTH_SHORT).show();
                promotionFlipper.showNext();

            }else if (s.contains("crook")){
                Toast.makeText(getActivity(), "Your Post Facility has been disabled, Please Contact our Customer Care ", Toast.LENGTH_SHORT).show();
                promotionFlipper.showNext();

            }else {

                if (s.contains("Large")){
                    Toast.makeText(getActivity(), "File Size is Too Large, file size should be approx. less than 1 MB", Toast.LENGTH_SHORT).show();

                }else {
                    Toast.makeText(getActivity(), "We are having trouble connecting to the internet, Please check your Connection ", Toast.LENGTH_SHORT).show();

                }

                promotionFlipper.showNext();

            }


        }
    }

    public void imagePermissionItem(View view) {
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE)
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
            if(resultCode == getActivity().RESULT_OK)
            {
                Image_Selecting_Task(data.getData());
            }
            else
            {
                return;
            }


        }else if (requestCode == REQUEST_IMAGE_CROP && resultCode == getActivity().RESULT_OK){
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap)extras.get("data");


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
            bannerPreview.setVisibility(View.VISIBLE);
            bannerPreview.setImageBitmap(myBitmap);

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
            Toast.makeText(getActivity(),"Failed to get image", Toast.LENGTH_LONG).show();
            return null;
        }

        // try to retrieve the image from the media store first
        // this will only work for images selected from gallery
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = getActivity().getContentResolver().query(uri, projection, null, null, null);
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

        if(ContextCompat.checkSelfPermission(getActivity(), permission[0]) != PackageManager.PERMISSION_GRANTED)
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

            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    Manifest.permission.READ_EXTERNAL_STORAGE)) {

            }
            if (grantResults.length == 1 &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                selectImage();

            } else {
                Toast.makeText(getActivity(), "You have restricted "+getString(R.string.app_name)+" app to access to gallery", Toast.LENGTH_SHORT).show();

                android.support.v7.app.AlertDialog alertDialog = null;
                android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(getActivity(),R.style.AppTheme);
                builder.setTitle("Want to select image from gallery");
                builder.setMessage("Allow "+getString(R.string.app_name)+" app to select images from gallery. This enables you to change your profile picture. " +
                        "Go to Settings to turn on Gallery Access.\n\nTo enable this, click "+getString(R.string.app_name)+" App Settings below and activate Storage under the permissions menu.");

                builder.setPositiveButton("App Settings", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent();
                        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        Uri uri = Uri.fromParts("package", getActivity().getPackageName(), null);
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

        else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }


    public void sendImage(View view) {

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        myBitmap.compress(Bitmap.CompressFormat.JPEG, 90, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream .toByteArray();
        base64= new String(Base64.encodeBase64(byteArray));

    }


}
