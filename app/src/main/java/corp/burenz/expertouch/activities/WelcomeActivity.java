package corp.burenz.expertouch.activities;

import android.annotation.TargetApi;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Window;
import android.widget.TextView;

import corp.burenz.expertouch.R;

public class WelcomeActivity extends AppCompatActivity {


    String LOCAL_APP_DATA = "userInformation";
    SharedPreferences userData;
    Typeface logoTypeface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);


    //    lollipop();


        logoTypeface = Typeface.createFromAsset(WelcomeActivity.this.getAssets(), "fonts/forte.ttf");

        TextView xper = (TextView) findViewById(R.id.xperTwo);
       // TextView touch = (TextView) findViewById(R.id.touchTwo);


        xper.setTypeface(logoTypeface);
        //touch.setTypeface(logoTypeface);


        CountDownTimer countDownTimer = new CountDownTimer(1000,9000) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {

                userData = getSharedPreferences(LOCAL_APP_DATA,0);

                // set true
            if (userData.getBoolean("firstRun",true)){
                startActivity(new Intent(WelcomeActivity.this,MyIntroActivity.class));
                WelcomeActivity.this.finish();

            }else {
                if (userData.getBoolean("LOGEDIN",false)){

                    startActivity(new Intent(WelcomeActivity.this, Jobs.class));
                    WelcomeActivity.this.finish();

                }else if (userData.getBoolean("LOGEDOUT",false)){

                    startActivity(new Intent(WelcomeActivity.this, Registrations.class));
                    WelcomeActivity.this.finish();


                } else {


                    if (!userData.getBoolean("VERIFIED",false)){

                        if (userData.getBoolean("SKIPPEDVERI",false)){

                            startActivity(new Intent(WelcomeActivity.this,Jobs.class));
                            WelcomeActivity.this.finish();

                        }else {

                            startActivity(new Intent(WelcomeActivity.this, Registrations.class));
                            WelcomeActivity.this.finish();
                        }
                    }else{
                        startActivity(new Intent(WelcomeActivity.this,Jobs.class));
                        WelcomeActivity.this.finish();
                    }


                }


            }


            }



        };

        countDownTimer.start();






    }


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    void lollipop(){
        Window window;
        window = this.getWindow();
        window.setStatusBarColor(Color.BLACK);

    }

}
