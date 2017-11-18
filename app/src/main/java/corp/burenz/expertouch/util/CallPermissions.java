package corp.burenz.expertouch.util;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import corp.burenz.expertouch.R;

/**
 * Created by xperTouch on 8/11/2016.
 */
public class CallPermissions extends AppCompatActivity {


    public static final int READ_GALLERY_PERMISSIONS_REQUEST = 2;
    String callIt;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.workspcae);
        imagePermissionItem(getCurrentFocus());
        Bundle callBundle = getIntent().getExtras();

        if (callBundle == null){
            return;
        }else{
            callIt = callBundle.getString("callIt");
        }
    }



    public void imagePermissionItem(View view) {

        if (ContextCompat.checkSelfPermission(CallPermissions.this, Manifest.permission.CALL_PHONE)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(Manifest.permission.CALL_PHONE);
        }
    }




    // Called when the user is performing an action which requires the app to read the
    // user's gallery
    @TargetApi(23)
    public void requestPermissions(String... permission)
    {

        if(ContextCompat.checkSelfPermission(CallPermissions.this, permission[0]) != PackageManager.PERMISSION_GRANTED)
        {
            if(permission[0].equals(Manifest.permission.CALL_PHONE))
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

            if (ActivityCompat.shouldShowRequestPermissionRationale(CallPermissions.this,
                    Manifest.permission.CALL_PHONE)) {

            }
            if (grantResults.length == 1 &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:" + callIt ));
                startActivity(intent);
                finish();

            } else {
                Toast.makeText(CallPermissions.this, "You have restricted xperTouch app to access to Calling Feature", Toast.LENGTH_SHORT).show();

                android.support.v7.app.AlertDialog alertDialog = null;
                android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(CallPermissions.this,R.style.AppTheme);
                builder.setTitle("Want to Call Expert From Phone");
                builder.setMessage("Allow xperTouch app to Call experts from phone. This enables you to Call experts and companies directly from your phone. " +
                        "Go to Settings to turn on Call Access.\n\nTo enable this, click xpertouch App Settings below and activate Phone under the permissions menu.");

                builder.setPositiveButton("App Settings", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent();
                        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        Uri uri = Uri.fromParts("package", CallPermissions.this.getPackageName(), null);
                        intent.setData(uri);
                        startActivity(intent);
                        finish();
                    }
                });

                builder.setNegativeButton("Not Now", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        finish();
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














}


