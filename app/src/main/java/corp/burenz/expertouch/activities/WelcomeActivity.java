package corp.burenz.expertouch.activities;

import android.annotation.TargetApi;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import corp.burenz.expertouch.R;
import corp.burenz.expertouch.butter.GuestInformation;
import corp.burenz.expertouch.util.MyBounceInterpolator;

public class WelcomeActivity extends AppCompatActivity {


    String LOCAL_APP_DATA = "userInformation";
    SharedPreferences       userData;
    Typeface                logoTypeface;
    TextView                nowAnythingTV,t1clickawayTV,jobsLSTV;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().setFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
            );
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        }


        nowAnythingTV = (TextView) findViewById(R.id.nowAnythingTV);
        t1clickawayTV = (TextView) findViewById(R.id.t1clickawayTV);
        jobsLSTV      = (TextView) findViewById(R.id.jobsLSTV);

        Typeface welcomeType = Typeface.createFromAsset(WelcomeActivity.this.getAssets(), "fonts/fjalla.otf");

        nowAnythingTV.setTypeface(welcomeType);
        t1clickawayTV.setTypeface(welcomeType);
        jobsLSTV.setTypeface(welcomeType);


//        try{
//            Window window = WelcomeActivity.this.getWindow();
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                window.setStatusBarColor(ContextCompat.getColor(WelcomeActivity.this,R.color.colorPrimary));
//                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//            }
//        }catch (Exception e ){
//            e.printStackTrace();
//        }


        Animation animation = AnimationUtils.loadAnimation(WelcomeActivity.this,R.anim.bounce);
        MyBounceInterpolator myBounceInterpolator = new MyBounceInterpolator(0.2,20);
        animation.setInterpolator(myBounceInterpolator);


        logoTypeface = Typeface.createFromAsset(WelcomeActivity.this.getAssets(), "fonts/forte.ttf");

        final TextView xper = (TextView) findViewById(R.id.xperTwo);
//        xper.startAnimation(animation);
        /*setting greetings Message*/

        userData = getSharedPreferences(LOCAL_APP_DATA,0);
        if (userData.getBoolean("LOGEDIN",false)){
            if(new GuestInformation(WelcomeActivity.this).getGuestName().split(" ")[0].length() != 0){
                xper.setText( "Welcome back " +  new GuestInformation(WelcomeActivity.this).getGuestName().split(" ")[0]);
                logoTypeface = Typeface.createFromAsset(WelcomeActivity.this.getAssets(), "fonts/roboto.ttf");
            }

        }

        final LinearLayout locationGif;

        locationGif = (LinearLayout) findViewById(R.id.lcoationGifOnWelcome);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                locationGif.startAnimation(AnimationUtils.loadAnimation(WelcomeActivity.this ,R.anim.fadeout_scan));
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        locationGif.setVisibility(View.GONE);

                    }
                },300);


            }
        },3900);

        xper.setTypeface(logoTypeface);



        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doTheChecks();
            }
        },4000);






    }


    void doTheChecks(){
        userData = getSharedPreferences(LOCAL_APP_DATA,0);

        // set true
        if (userData.getBoolean("firstRun",true)){
            startActivity(new Intent(WelcomeActivity.this,MyIntroActivity.class));
            WelcomeActivity.this.finish();
            overridePendingTransition(R.anim.fadein_scan,R.anim.fadein_scan);

        }else {
            if (userData.getBoolean("LOGEDIN",false)){

                startActivity(new Intent(WelcomeActivity.this, Jobs.class));
                WelcomeActivity.this.finish();
                overridePendingTransition(R.anim.fadein_scan,R.anim.fadein_scan);


            }else if (userData.getBoolean("LOGEDOUT",false)){

                startActivity(new Intent(WelcomeActivity.this, Registrations.class));
                WelcomeActivity.this.finish();
                overridePendingTransition(R.anim.fadein_scan,R.anim.fadein_scan);


            } else {


                if (!userData.getBoolean("VERIFIED",false)){

                    if (userData.getBoolean("SKIPPEDVERI",false)){

                        startActivity(new Intent(WelcomeActivity.this,Jobs.class));

                        WelcomeActivity.this.finish();

                    }else {
                        startActivity(new Intent(WelcomeActivity.this, Registrations.class));
                        WelcomeActivity.this.finish();
                        overridePendingTransition(R.anim.fadein_scan,R.anim.fadein_scan);

                    }
                }else{
                    startActivity(new Intent(WelcomeActivity.this,Jobs.class));
                    WelcomeActivity.this.finish();
                    overridePendingTransition(R.anim.fadein_scan,R.anim.fadein_scan);

                }


            }


        }


    }




    public void presentActivity(View view) {
        ActivityOptionsCompat options = ActivityOptionsCompat.
                makeSceneTransitionAnimation(this, view, "transition");
        int revealX = (int) (view.getX() + view.getWidth() / 2);
        int revealY = (int) (view.getY() + view.getHeight() / 2);

//        Intent intent = new Intent(this, SecondActivity.class);
//        intent.putExtra(SecondActivity.EXTRA_CIRCULAR_REVEAL_X, revealX);
//        intent.putExtra(SecondActivity.EXTRA_CIRCULAR_REVEAL_Y, revealY);

//        ActivityCompat.startActivity(this, intent, options.toBundle());
    }



    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    void lollipop(){
        Window window;
        window = this.getWindow();
        window.setStatusBarColor(Color.BLACK);

    }

}
