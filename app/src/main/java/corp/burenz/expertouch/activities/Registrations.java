package corp.burenz.expertouch.activities;

import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import corp.burenz.expertouch.R;
import corp.burenz.expertouch.fragments.registration.HomeScreen;

public class Registrations extends AppCompatActivity {


    SharedPreferences exitCount;
    String COUNT = "exitCount";
    SharedPreferences.Editor editor;
    int count = 0;

    SharedPreferences userData;
    String LOCAL_APP_DATA = "userInformation";



    boolean exit = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Registrations.this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);


        userData = getSharedPreferences(LOCAL_APP_DATA,0);

        SharedPreferences.Editor editor;
        editor = userData.edit();

        editor.putBoolean("firstRun",false);
        editor.apply();

        // add the HomeScreen Fragment


            MediaPlayer ourSplasSound =  MediaPlayer.create(Registrations.this,R.raw.upbeat);


        //    ourSplasSound.start();
            ourSplasSound.isPlaying();
            ourSplasSound.setLooping(true);

        if (savedInstanceState == null) {

            getSupportFragmentManager().beginTransaction().add(R.id.main_server_frame_layout, new HomeScreen())
                    .commit();
            exitCount = getSharedPreferences(COUNT, 0);
            editor = exitCount.edit();
            editor.putBoolean("isHome", true);
            editor.putInt("count",1);
            editor.commit();

        }


    }

        @Override
        public void onBackPressed () {
            //   super.onBackPressed();


            exitCount = getSharedPreferences(COUNT, 0);
            editor = exitCount.edit();
            if (exitCount.getBoolean("isHome", true) ) {

                backExitStyle();

            } else if ( exitCount.getInt("count",1) == 0 ){

                    exitCount = getSharedPreferences(COUNT, 0);
                    editor = exitCount.edit();
                    editor.putInt("count",1);
                    editor.apply();


            }else  if (exitCount.getInt("count",1) == 1){

                backExitStyle();


            }



            getSupportFragmentManager().beginTransaction().replace(R.id.main_server_frame_layout, new HomeScreen()).addToBackStack(null)
                    .commit();


            //  Toast.makeText(Registrations.this, "OnBackPressed", Toast.LENGTH_SHORT).show();
        }





    public void backExitStyle(){


        if (exit){
            finish();
        }else{
            Toast.makeText(Registrations.this, "Press Back again To Exit", Toast.LENGTH_SHORT).show();
            exit = true;

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    exit = false;
                }
            },3000);


        }




    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        Log.e("onDestroyCalled","onDestroy Called");
            exitCount = getSharedPreferences(COUNT,0);
            editor = exitCount.edit();
            editor.putBoolean("isHome",true);
            editor.apply();


    }




}

