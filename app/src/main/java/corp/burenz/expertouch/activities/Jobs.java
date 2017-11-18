package corp.burenz.expertouch.activities;

import android.annotation.TargetApi;
import android.app.SearchManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.LayoutInflaterCompat;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ActionMenuView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.google.firebase.messaging.FirebaseMessaging;
import com.mikepenz.iconics.Iconics;
import com.mikepenz.iconics.context.IconicsContextWrapper;
import com.mikepenz.iconics.context.IconicsLayoutInflater;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.HttpHostConnectException;
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
import corp.burenz.expertouch.adapters.FeedsAdapter;
import corp.burenz.expertouch.util.Config;
import corp.burenz.expertouch.util.NotificationUtils;
import jp.co.recruit_lifestyle.android.widget.WaveSwipeRefreshLayout;
import uk.co.samuelwall.materialtaptargetprompt.MaterialTapTargetPrompt;

import static android.content.Context.SEARCH_SERVICE;

public class Jobs extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    SharedPreferences introduceMe;
    String INTRODUCE_ME = "introduction";
    String APP_VERSION = "appVersion";
    SharedPreferences appVersion;


    String currentAppVersion = "silicon";
    RecyclerView whatsNewRecycler;

    RecyclerView.Adapter whatsNewRecyclerAdapter;
    RecyclerView.Adapter filterRecyclerAdapter;
    Typeface logoTypeface;
    SharedPreferences userData;
    String LOCAL_APP_DATA = "userInformation";
    SharedPreferences feedsFilter;
    TextView postAddView,viewMyPostView;
    String CURRENT_FILTER = "feedsFilter";
    ViewFlipper switchBulbF;


    LinearLayout searchJobOppurtunities,noConnectionLL;

    Window window;
    LinearLayout subOne,subTwo;
    SharedPreferences.Editor editor;
    Boolean isFabOpen = false;
    SharedPreferences companyAdds;
    InputMethodManager im;
    Boolean isFabVisible;
    ArrayList<String> companyTitles ;
    ArrayList<String> postDate;
    ArrayList<String> callArray;
    ArrayList<String> emailArray;
    ArrayList<String> websiteArray;
    ArrayList<String> jobInfo;
    ArrayList<String> banners;
    ArrayList<String> addState;
    ArrayList<String> addCatagory;
    ArrayList<String> addType;
    TextView dontShowAgain,helpCenter;
    ViewFlipper verificationBulbFl;
    LinearLayout smartBottom;
    String COMPANY_DETAILS = "myCompanyDetails";
    SharedPreferences myCompanyDetails;


    LinearLayout newVersionFoundLL;
    LinearLayout switchUserLL;
    FeedsLoader feedsLoader;

    SharedPreferences updateInfo;
    String UPDATE_INFO = "updateInfo";
    String TAG = "Development";
    LinearLayout whyVerify;
    ViewFlipper showTimeFlipper;
    ArrayList<String> companyTitlesNew ;
    ArrayList<String> postDateNew;
    ArrayList<String> callArrayNew;
    ArrayList<String> emailArrayNew;
    ArrayList<String> websiteArrayNew;
    ArrayList<String> jobInfoNew;
    ArrayList<String> bannersNew;
    private BroadcastReceiver mRegistrationBroadcastReceiver;

    LinearLayout noAdverts;

    ProgressBar progressBar;
    android.widget.SearchView searchViewJobs;
    TextView pdStyle;

    // Bottom Banner
    LinearLayout bottomBanner,bannerRoom;
    ViewFlipper verifyNowFlipper;
    TextView noThanksLverify,verifyNow;
    LinearLayout jobsLayout;
    RelativeLayout loadProgress;
    Animation animation;
    TextView textView638;
    WaveSwipeRefreshLayout mWaveSwipeRefreshLayout;



    ImageButton smartHireLL,smartProfileLL,smartFavouritesLL,smartBucketLL;




    Animation fab_open, fab_close, rotate_forward, rotate_backward;
    FloatingActionButton fab,fab1,fab2;

    final  int VERIFY_BANNER_TIMEOUT = 6000;



    RecyclerView recyclerView;
    RecyclerView.Adapter adaptert;


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    void lollipop(Toolbar toolbar){
      //  toolbar.setElevation(0);
        Window window;
     //   window = this.getWindow();
      //  window.setStatusBarColor(Color.BLACK);

    }


    void initViews(){

        fab               = (FloatingActionButton) findViewById(R.id.postAddFab);
        fab1              = (FloatingActionButton) findViewById(R.id.fab1);
        fab2              = (FloatingActionButton) findViewById(R.id.fab2);
        postAddView       = (TextView) findViewById(R.id.postAddView);
        viewMyPostView    = (TextView) findViewById(R.id.myPostsView);
        noConnectionLL    = (LinearLayout) findViewById(R.id.noConnectionLinear);
        jobsLayout        = (LinearLayout) findViewById(R.id.jobsLayout);
        noAdverts         = (LinearLayout)findViewById(R.id.noAdverts);
        loadProgress      = (RelativeLayout)findViewById(R.id.loadProgress);
        fab1              = (FloatingActionButton) findViewById(R.id.fab1);
        fab2              = (FloatingActionButton) findViewById(R.id.fab2);
        subOne            = (LinearLayout) findViewById(R.id.subOne);
        bottomBanner      = (LinearLayout) findViewById(R.id.bottomBanner);
        subTwo            = (LinearLayout) findViewById(R.id.subTwo);
        switchUserLL      = (LinearLayout) findViewById(R.id.switchUserLL);

        newVersionFoundLL = (LinearLayout) findViewById(R.id.newVersionFoundLL);
        smartBucketLL     = (ImageButton) findViewById(R.id.smartBucketLL);
        smartFavouritesLL = (ImageButton) findViewById(R.id.smartFavouritesLL);
        smartHireLL       =  (ImageButton) findViewById(R.id.smartHireLL);
        smartProfileLL    = (ImageButton) findViewById(R.id.smartProfileLL);
        smartBottom       = (LinearLayout) findViewById(R.id.smartBottom);

        searchJobOppurtunities = (LinearLayout) findViewById(R.id.searchJobOppurtunities);
        verificationBulbFl = (ViewFlipper) findViewById(R.id.verificationBulbF);


        mWaveSwipeRefreshLayout = (WaveSwipeRefreshLayout) findViewById(R.id.main_swipe);
        mWaveSwipeRefreshLayout.setColorSchemeColors(Color.WHITE, Color.WHITE);
        mWaveSwipeRefreshLayout.setWaveARGBColor(255,3,35,46);







    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(IconicsContextWrapper.wrap(newBase));
    }



    private void displayFirebaseRegId() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences(Config.SHARED_PREF, 0);
        String regId = pref.getString("regId", null);

        Log.e(TAG, "Firebase reg id: " + regId);
        FirebaseMessaging.getInstance().subscribeToTopic("/topics/news");

        Log.e("subscribe","inside subscribe topic");

       /* if (!TextUtils.isEmpty(regId))
            Toast.makeText(this, "Firebase Reg Id : "+regId, Toast.LENGTH_SHORT).show();
        else
        Toast.makeText(this, "Firebase Reg Id is not received yet!", Toast.LENGTH_SHORT).show();*/
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        LayoutInflaterCompat.setFactory(getLayoutInflater(), new IconicsLayoutInflater(getDelegate()));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jobs);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_jobs);
        setSupportActionBar(toolbar);
        toolbar.setTitle("");
        lollipop(toolbar);
        Iconics.init(Jobs.this);
        initViews();



        im = (InputMethodManager)Jobs.this.getSystemService(INPUT_METHOD_SERVICE);
        TextView xper = (TextView) findViewById(R.id.xperJobs);
        TextView touch = (TextView) findViewById(R.id.touchJobs);

        logoTypeface = Typeface.createFromAsset(Jobs.this.getAssets(), "fonts/forte.ttf");



        pdStyle = (TextView) findViewById(R.id.pdStyle);
        pdStyle.setTypeface(logoTypeface);
        progressBar = (ProgressBar)findViewById(R.id.testBar);
        toolbarTypeFace();




        smartBucketLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(Jobs.this,Buket.class));
            }
        });

//        firebase code starts here

        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {



                // checking for type intent filter
                if (intent.getAction().equals(Config.REGISTRATION_COMPLETE)) {
                    // gcm successfully registered
                    // now subscribe to `global` topic to receive app wide notifications
                    FirebaseMessaging.getInstance().subscribeToTopic(Config.TOPIC_GLOBAL);
                Toast.makeText(context, "inside registeration complete", Toast.LENGTH_SHORT).show();

                } else if (intent.getAction().equals(Config.PUSH_NOTIFICATION)) {
                    // new push notification is received

                    String message = intent.getStringExtra("message");

                    Toast.makeText(getApplicationContext(), "Push notification: " + message, Toast.LENGTH_LONG).show();


                }
            }
        };


//    firebase code ends here







        introduceMe = getSharedPreferences(INTRODUCE_ME,0);




        smartFavouritesLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Jobs.this,MyFavouritesActivity.class));
            }
        });

        smartHireLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(Jobs.this,Hire.class));
            }
        });

        smartProfileLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

          userData = getSharedPreferences(LOCAL_APP_DATA,0);

                if (userData.getBoolean("VERIFIED",false)){


                    if(userData.getBoolean("EXPERT",false)){

                        startActivity(new Intent(Jobs.this,Profile.class));

                    }else if (userData.getBoolean("COMPANY",false)){

                        startActivity(new Intent(Jobs.this,MyCompany.class));

                    }else{

                        startActivity(new Intent(Jobs.this,OwnChoice.class));
                    }

                }
                else {

                    startActivity(new Intent(Jobs.this,OwnChoice.class));
                }



            }
        });



        fab_open = AnimationUtils.loadAnimation(this, R.anim.fab_open);
        fab_close = AnimationUtils.loadAnimation(this, R.anim.fab_close);
        rotate_forward = AnimationUtils.loadAnimation(this, R.anim.rotate_forward);
        rotate_backward = AnimationUtils.loadAnimation(this, R.anim.rotate_backward);

        whyVerify  = (LinearLayout) findViewById(R.id.whyVerifyLL);


        fab.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor;
                introduceMe = getSharedPreferences(INTRODUCE_ME,0);
                editor = introduceMe.edit();
                editor.putBoolean("tappedPostFab",true);
                editor.apply();


                        animateFAB();


            }
        });
        fabVisibility();

        xper.setTypeface(logoTypeface);
        touch.setTypeface(logoTypeface);



        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Jobs.this,MyCompanyPosts.class));
            }
        });

        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String COMPANY_DETAILS = "myCompanyDetails";
                SharedPreferences myCompanyDetails;
                myCompanyDetails = getSharedPreferences(COMPANY_DETAILS,0);
                if (myCompanyDetails.getBoolean("CVERIFIED",false)){
                    startActivity(new Intent(Jobs.this,PostAdd.class));
                }else{
                    startActivity(new Intent(Jobs.this,VerifyCompany.class));
                }
            }
        });

        postAddView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String COMPANY_DETAILS = "myCompanyDetails";
                SharedPreferences myCompanyDetails;

                myCompanyDetails = getSharedPreferences(COMPANY_DETAILS,0);
                if (myCompanyDetails.getBoolean("CVERIFIED",false)){
                    startActivity(new Intent(Jobs.this,PostAdd.class));
                }else{
                    startActivity(new Intent(Jobs.this,VerifyCompany.class));
                }

            }
        });




        whatsNewRecycler = (RecyclerView)findViewById(R.id.jobsRv);
        whatsNewRecycler.setLayoutManager(new LinearLayoutManager(Jobs.this));
        animation = AnimationUtils.loadAnimation(Jobs.this,R.anim.card_animation);

        noAdverts.startAnimation(animation);
        feedsFilter = getSharedPreferences(CURRENT_FILTER,0);

        new FeedsLoader(Jobs.this,whatsNewRecycler).execute();

        searchView();

        bannerScanner();

        viewMyPostView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Jobs.this,MyCompanyPosts.class));
            }
        });

        updateInfo = getSharedPreferences(UPDATE_INFO,0);


        if (updateInfo.getBoolean("updateAvailable",false)){


            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                    if (bottomBanner.getVisibility() != View.VISIBLE){
                        newVersionBanner();
                    }

                }
            },10000);


        }else {

            if (updateInfo.getBoolean("check",false)){
                new GetCurrentVersion().execute();

            }

        }


        mWaveSwipeRefreshLayout.setOnRefreshListener(new WaveSwipeRefreshLayout.OnRefreshListener() {
            @Override public void onRefresh() {
                // Do work to refresh the list here.
                new FeedsLoader(Jobs.this,whatsNewRecycler).execute();

            }
        });




        myCompanyDetails = Jobs.this.getSharedPreferences(COMPANY_DETAILS,0);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close){

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);

                InputMethodManager im = (InputMethodManager)Jobs.this.getSystemService(INPUT_METHOD_SERVICE);
                im.hideSoftInputFromWindow(searchViewJobs.getWindowToken(),0);
                Log.e("Drawer","Drawer opened");

             }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };

        drawer.setDrawerListener(toggle);
        toggle.syncState();



        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        displayFirebaseRegId();



    }





    void fabVisibility(){


        SharedPreferences userData;
        String LOCAL_APP_DATA = "userInformation";

        userData = getSharedPreferences(LOCAL_APP_DATA,0);
        Boolean isCompany =  userData.getBoolean("COMPANY",false);

        if (fab.getVisibility() == View.VISIBLE){
            return;
        }


        if (isCompany){

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    fab.startAnimation(AnimationUtils.loadAnimation(Jobs.this,R.anim.card_animation));
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            fab.setVisibility(View.VISIBLE);
                            fab.setClickable(true);
                        }
                    },1000);
                    fab.setVisibility(View.VISIBLE);
                    fab.setClickable(true);
                }
            },1000);

            introduceMe = getSharedPreferences(INTRODUCE_ME,0);
            if (!introduceMe.getBoolean("tappedPostFab",false)){
                new MaterialTapTargetPrompt.Builder(Jobs.this)
                        .setTarget(findViewById(R.id.postAddFab))
                        .setAutoDismiss(false)
                        .setPrimaryText("Did you notice this feature")
                        .setSecondaryText("Tap the icon to start exploring your Business account")
                        .setOnHidePromptListener(new MaterialTapTargetPrompt.OnHidePromptListener()
                        {
                            @Override
                            public void onHidePrompt(MotionEvent event, boolean tappedTarget)
                            {
                                //Do something such as storing a value so that this prompt is never shown again
                                if (tappedTarget){

                                    new MaterialTapTargetPrompt.Builder(Jobs.this)
                                            .setTarget(findViewById(R.id.fab2))
                                            .setAutoDismiss(false)
                                            .setPrimaryText("Post your first Advertisement")
                                            .setSecondaryText("Tap the icon to start composing your first Promotion")
                                            .setOnHidePromptListener(new MaterialTapTargetPrompt.OnHidePromptListener()
                                            {
                                                @Override
                                                public void onHidePrompt(MotionEvent event, boolean tappedTarget)
                                                {
                                                    //Do something such as storing a value so that this prompt is never shown again
                                                }

                                                @Override
                                                public void onHidePromptComplete()
                                                {

                                                }
                                            })
                                            .show();


                                }
                            }

                            @Override
                            public void onHidePromptComplete()
                            {

                            }
                        })
                        .show();

            }


        }

    }



    public void animateFAB() {

        LinearLayout fabForeground;

        if (this.isFabOpen) {
            jobsLayout.setClickable(true);
            fab.startAnimation(rotate_backward);
            fab1.startAnimation(fab_close);
            fab2.startAnimation(fab_close);
            subOne.startAnimation(fab_close);
            subTwo.startAnimation(fab_close);
            fab1.setClickable(false);
            fab2.setClickable(false);
            postAddView.setClickable(false);
            viewMyPostView.setClickable(false);
            this.isFabOpen = false;

        } else {

            jobsLayout.setClickable(false);
            fab.startAnimation(rotate_forward);

            subOne.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(),R.anim.card_animation));
            subTwo.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(),R.anim.card_animation));

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                   subOne.setVisibility(View.VISIBLE);
                    subTwo.setVisibility(View.VISIBLE);
                }
            },200);

            fab1.startAnimation(fab_open);
            fab2.startAnimation(fab_open);
            fab1.setClickable(true);
            fab2.setClickable(true);

            postAddView.setClickable(true);
            viewMyPostView.setClickable(true);
            this.isFabOpen = true;
        }
    }


    public void bottomBannerCheck(){

        fab.setVisibility(View.GONE);
        whyVerify.setVisibility(View.VISIBLE);
        verifyNowFlipper = (ViewFlipper) findViewById(R.id.verifyNowFlipper);
        showTimeFlipper = (ViewFlipper)  findViewById(R.id.showTimeFlipper);
        noThanksLverify = (TextView) findViewById(R.id.noThanksLverify);
        verifyNow = (TextView) findViewById(R.id.verifyNow);
        bannerRoom = (LinearLayout) findViewById(R.id.bannerRoom);
        bottomBanner.setVisibility(View.VISIBLE);


        verifyNowFlipper.showNext();


        showTimeFlipper.setFlipInterval(3000);
        showTimeFlipper.startFlipping();
        dontShowAgain = (TextView) findViewById(R.id.dontShowAgain);
        helpCenter = (TextView) findViewById(R.id.helpCenter);

        smartBottom.startAnimation(AnimationUtils.loadAnimation(Jobs.this,R.anim.md_styled_slide_down_slow));
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                smartBottom.setClickable(false);
                smartBottom.setVisibility(View.GONE);
            }
        },500);


        dontShowAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                bannerRoom.setBackgroundColor(Color.TRANSPARENT);
                editor = userData.edit();
                editor.putBoolean("IGOTIT",true);
                editor.apply();

                bottomBanner.startAnimation(AnimationUtils.loadAnimation(Jobs.this,R.anim.md_styled_slide_down_slow));
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        bottomBanner.setVisibility(View.GONE);

                        smartBottom.startAnimation(AnimationUtils.loadAnimation(Jobs.this,R.anim.md_styled_slide_up_slow));
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                smartBottom.setClickable(true);
                                smartBottom.setVisibility(View.VISIBLE);
                            }
                        },500);


                    }
                },500);




            }
        });


        helpCenter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                bottomBanner.startAnimation(AnimationUtils.loadAnimation(Jobs.this,R.anim.md_styled_slide_down_slow));
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        startActivity(new Intent(Jobs.this,HelpCenter.class).putExtra("from","verify"));
                        bottomBanner.setVisibility(View.GONE);

                        smartBottom.startAnimation(AnimationUtils.loadAnimation(Jobs.this,R.anim.md_styled_slide_up_slow));
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                smartBottom.setClickable(true);
                                smartBottom.setVisibility(View.VISIBLE);
                            }
                        },500);


                    }
                },500);

            }
        });


        verificationBulbFl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomBanner.startAnimation(AnimationUtils.loadAnimation(Jobs.this,R.anim.md_styled_slide_down_slow));
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        startActivity(new Intent(Jobs.this,HelpCenter.class).putExtra("from","verify"));
                        bottomBanner.setVisibility(View.GONE);

                        smartBottom.startAnimation(AnimationUtils.loadAnimation(Jobs.this,R.anim.md_styled_slide_up_slow));
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                smartBottom.setClickable(true);
                                smartBottom.setVisibility(View.VISIBLE);
                            }
                        },500);


                    }
                },500);

            }
        });

        verifyNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                bottomBanner.startAnimation(AnimationUtils.loadAnimation(Jobs.this,R.anim.md_styled_slide_down_slow));
                new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {




                            //startActivity(new Intent(Jobs.this,OwnChoice.class));


                            userData = getSharedPreferences(LOCAL_APP_DATA,0);
                            if (userData.getBoolean("VERIFIED",false)){

                                if(userData.getBoolean("EXPERT",false)){

                                    startActivity(new Intent(Jobs.this,Profile.class));

                                }else if (userData.getBoolean("COMPANY",false)){

                                    startActivity(new Intent(Jobs.this,MyCompany.class));

                                }else{

                                    startActivity(new Intent(Jobs.this,OwnChoice.class));
                                }

                            }
                            else {

                                startActivity(new Intent(Jobs.this,OwnChoice.class));
                            }





                            bottomBanner.setVisibility(View.GONE);

                            smartBottom.startAnimation(AnimationUtils.loadAnimation(Jobs.this,R.anim.md_styled_slide_up_slow));
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    smartBottom.setClickable(true);
                                    smartBottom.setVisibility(View.VISIBLE);
                                }
                            },500);


                        }
                    },500);


            }
        });



        noThanksLverify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    bannerRoom.setBackgroundColor(Color.TRANSPARENT);
                    editor = userData.edit();
                    editor.putBoolean("IGOTIT",true);
//                    editor.apply();
                bottomBanner.startAnimation(AnimationUtils.loadAnimation(Jobs.this,R.anim.md_styled_slide_down_slow));
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        bottomBanner.setVisibility(View.GONE);

                        smartBottom.startAnimation(AnimationUtils.loadAnimation(Jobs.this,R.anim.md_styled_slide_up_slow));
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                smartBottom.setClickable(true);
                                smartBottom.setVisibility(View.VISIBLE);
                            }
                        },500);

                    }
                },500);



            }
        });


    }

    Boolean leaveApp =  false;



    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {

            if (leaveApp){
                finish();
            }else{


                if (bottomBanner.getVisibility() != View.VISIBLE && switchUserLL.getVisibility() != View.VISIBLE && newVersionFoundLL.getVisibility() != View.VISIBLE){

                    Toast.makeText(Jobs.this, "Press Back again To Exit", Toast.LENGTH_SHORT).show();
                    leaveApp = true;

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            leaveApp = false;
                        }
                    },3000);

                }else {



                    if (bottomBanner.getVisibility() ==View.VISIBLE){

                        bottomBanner.startAnimation(AnimationUtils.loadAnimation(Jobs.this,R.anim.md_styled_slide_down_slow));
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                bottomBanner.setVisibility(View.GONE);

                                smartBottom.startAnimation(AnimationUtils.loadAnimation(Jobs.this,R.anim.md_styled_slide_up_slow));
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        smartBottom.setClickable(true);
                                        smartBottom.setVisibility(View.VISIBLE);
                                    }
                                },500);

                            }
                        },500);



                    }else if(newVersionFoundLL.getVisibility() ==View.VISIBLE){
                        newVersionFoundLL.startAnimation(AnimationUtils.loadAnimation(Jobs.this,R.anim.md_styled_slide_down_slow));

                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                newVersionFoundLL.setVisibility(View.GONE);

                                smartBottom.startAnimation(AnimationUtils.loadAnimation(Jobs.this,R.anim.md_styled_slide_up_slow));
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        smartBottom.setClickable(true);
                                        smartBottom.setVisibility(View.VISIBLE);
                                    }
                                },500);


                            }
                        },450);





                    }else if (switchUserLL.getVisibility() ==View.VISIBLE){

                        switchUserLL.startAnimation(AnimationUtils.loadAnimation(Jobs.this,R.anim.md_styled_slide_down_slow));

                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                switchUserLL.setVisibility(View.INVISIBLE);

                                jobsLayout.setEnabled(true);
                                if (isFabVisible){
                                    fab.startAnimation(AnimationUtils.loadAnimation(Jobs.this,R.anim.card_animation));
                                    fab.setVisibility(View.VISIBLE);
                                    fab.setClickable(true);
                                }else{

                                    fab.setVisibility(View.INVISIBLE);
                                }

                                smartBottom.startAnimation(AnimationUtils.loadAnimation(Jobs.this,R.anim.md_styled_slide_up_slow));
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        smartBottom.setClickable(true);
                                        smartBottom.setVisibility(View.VISIBLE);
                                    }
                                },500);



                            }
                        },500);


                    }




                }



            }

        }

    }


    void setters(){

        TextView userName,userEmail;
        ImageView  statusImage;
        userData = getSharedPreferences(LOCAL_APP_DATA,0);
        userEmail = (TextView)findViewById(R.id.userEmailJobs);
        userName =  (TextView)findViewById(R.id.userNameJobs);
        try {
            userEmail.setText(userData.getString("userEmail","email@example.com"));
            userName.setText(userData.getString("userName","userName"));

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.jobs, menu);

        setters();

        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        userData = getSharedPreferences(LOCAL_APP_DATA,0);



        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.my_filter) {

            if (companyTitles.size() > 0){
                startActivity(new Intent(Jobs.this,Filter.class));
            }else{
                Toast.makeText(Jobs.this, "Cannot Filter Empty List", Toast.LENGTH_SHORT).show();
            }

        }

        else if(id == R.id.settings){

            startActivity(new Intent(Jobs.this,SettingsActivity.class));


        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.


        userData = getSharedPreferences(LOCAL_APP_DATA,0);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        final int id = item.getItemId();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                if (id == R.id.app_bucket) {


                    startActivity(new Intent(Jobs.this,Buket.class));
                    // Handle the camera action
                } else if (id == R.id.hire_experts) {


                    startActivity(new Intent(Jobs.this,Hire.class));
                    // Handle the camera action
                } else if (id == R.id.profile) {

                    if (userData.getBoolean("VERIFIED",false)){


                        if(userData.getBoolean("EXPERT",false)){

                            startActivity(new Intent(Jobs.this,Profile.class));

                        }else if (userData.getBoolean("COMPANY",false)){

                            startActivity(new Intent(Jobs.this,MyCompany.class));

                        }else{

                            startActivity(new Intent(Jobs.this,OwnChoice.class));
                        }

                    }
                    else {

                        startActivity(new Intent(Jobs.this,OwnChoice.class));
                    }




                }else if (id == R.id.exit_app) {

                    Jobs.this.finish();

                }else if (id == R.id.add_bussiness) {

                    startActivity(new Intent(Jobs.this,AddMissingExpertise.class));

                } else if (id == R.id.rate_app) {
                    Uri uri = Uri.parse("market://details?id="+getPackageName());
                    startActivity(new Intent(Intent.ACTION_VIEW,uri));

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(Jobs.this, "Your 5 stars will encourage us to serve you better", Toast.LENGTH_SHORT).show();
                        }
                    },7000);


                }
                else if (id == R.id.my_favourites) {

                    startActivity(new Intent(Jobs.this,MyFavouritesActivity.class));


                }  else if (id == R.id.report_company) {



                    if (userData.getBoolean("VERIFIED",false)){

                        startActivity(new Intent(Jobs.this,ReportCompany.class));

                        }else{

                        Toast.makeText(Jobs.this, "Please Verify Your Account First", Toast.LENGTH_SHORT).show();
                        }


                    }

                else if (id == R.id.myCalls) {

                    startActivity(new Intent(Jobs.this,MyCalls.class));

                }else if (id == R.id.help_center) {

                    startActivity(new Intent(Jobs.this,HelpCenter.class));


                }

                else if (id == R.id.send_a_feedback) {

                    startActivity(new Intent(Jobs.this,FeedbackActivity.class));


                } else if (id == R.id.update_latest) {

                    startActivity(new Intent(Jobs.this,UpdateApp.class));

                } else if (id == R.id.log_out) {
                    switchUser();

                }else if (id == R.id.terms) {

                    startActivity(new Intent(Jobs.this,TermsOfService.class));


                }
                else if (id == R.id.share_app) {

                    String advert = "Check out 1clickAway, an app that helps you find Best Jobs, Experts and Offers from your City and State. Click on the below link to Download Now\n" +
                            "https://play.google.com/store/apps/details?id=corp.burenz.expertouch";
                    Intent shareIntent = new Intent();
                    shareIntent.setAction(Intent.ACTION_SEND);
                    shareIntent.setType("text/plain");
                    shareIntent.putExtra(Intent.EXTRA_TEXT,  advert);
                    startActivity(Intent.createChooser(shareIntent, "Share via..."));

                }








            }
        },300);




        return true;


    }

    public class FeedsLoader extends AsyncTask<String,String,String>{



        Context context;
        JSONObject jsonObject;
        JSONArray jsonArray;


        StringBuilder line = new StringBuilder();
        BufferedReader bufferedReader;

        RecyclerView recyclerView;
        RecyclerView.Adapter adapter;

        public FeedsLoader(Context context, RecyclerView recyclerView){
            this.context = context;
            this.recyclerView = recyclerView;
        }




        @Override
        protected void onPreExecute() {
            super.onPreExecute();


            ConnectivityManager connectivityManager  = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

            boolean isTrue = networkInfo!= null && networkInfo.isConnected();

            if (!isTrue ) {

                noConnectionLL.startAnimation(AnimationUtils.loadAnimation(Jobs.this,R.anim.shakeanim));

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        noConnectionLL.setVisibility(View.VISIBLE);

                    }
                },300);


            }else {

                noConnectionLL.startAnimation(AnimationUtils.loadAnimation(Jobs.this,R.anim.fab_close));

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        noConnectionLL.setVisibility(View.GONE);
                    }
                },300);


            }




                noAdverts.setVisibility(View.GONE);
            mWaveSwipeRefreshLayout.setRefreshing(true);
            loadProgress.setVisibility(View.VISIBLE);

            companyTitles = new ArrayList<>();
            postDate = new ArrayList<>();
            callArray = new ArrayList<>();
            emailArray = new ArrayList<>();
            websiteArray = new ArrayList<>();
            jobInfo = new ArrayList<>();
            banners = new ArrayList<>();
            addState = new ArrayList<>();
            addCatagory = new ArrayList<>();
            addType = new ArrayList<>();

          // loadProgress.setVisibility(View.VISIBLE);
          //  whatsNewRecycler.setVisibility(View.GONE);

        }

        @Override
        protected String doInBackground(String... params) {

//            doForSome();

        try {

            HttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(getString(R.string.host)+"/jobs/get_jobs.php");
            HttpResponse httpResponse = (HttpResponse)httpClient.execute(httpPost);

            HttpEntity httpEntity = httpResponse.getEntity();
            bufferedReader = new BufferedReader(new InputStreamReader(httpEntity.getContent()));
            String str = "";



            while ((str = bufferedReader.readLine()) != null){
                line.append(str);
            }


            jsonObject = new JSONObject();
            jsonArray = new JSONArray(line.toString());

            int length = jsonArray.length();

            for (int i = 0; i < length; i++){

                jsonObject = jsonArray.getJSONObject(i);

                companyTitles.  add(jsonObject.getString("companyTitle"));
                postDate.       add(jsonObject.getString("postDate"));
                callArray.      add(jsonObject.getString("companyPhone"));
                emailArray.     add(jsonObject.getString("companyEmail"));
                jobInfo.        add(jsonObject.getString("jobInfo"));
                banners.        add(jsonObject.getString("companyBanner"));
                addState.       add(jsonObject.getString("companyState"));
                addCatagory.    add(jsonObject.getString("addCatagory"));
                addType.        add(jsonObject.getString("addType"));

            }

        } catch (HttpHostConnectException e)
        {
      //      Toast.makeText(context, "Error While connecting to the server", Toast.LENGTH_SHORT).show();
        }
        catch (Exception e) {
       //     Toast.makeText(context, "Something Went Wrong"+e.toString(), Toast.LENGTH_SHORT).show();

        }
            return line.toString();
        }


        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            if (feedsFilter.getString("currentState","All States").equals("All States") && feedsFilter.getString("currentCatagory","All Categories").equals("All Categories") && feedsFilter.getString("currentType","All Types").equals("All Types")){

                if (companyTitles.size() == 0){
                    mWaveSwipeRefreshLayout.setRefreshing(false);
                    loadProgress.setVisibility(View.GONE);
                    whatsNewRecycler.setVisibility(View.GONE);
                    noAdverts.setVisibility(View.VISIBLE);
                    Log.e(TAG,"ORIGINAL LIST 0 CHECK , ENABLE AT DEPLOYMENT");
                    return;
                }else {
                        whatsNewRecycler.setVisibility(View.VISIBLE);
                        whatsNewRecycler.hasFixedSize();
//                        textView638.setVisibility(View.VISIBLE);

                        noAdverts.setVisibility(View.GONE);


                        try{

                    whatsNewRecyclerAdapter = new FeedsAdapter(Jobs.this,companyTitles,jobInfo,postDate,callArray,addState,emailArray,banners);
                    whatsNewRecycler.setAdapter(whatsNewRecyclerAdapter);
                        }catch(Exception e){
                            e.printStackTrace();
                        }
                    Log.e(TAG,"Loading onPostExcute Adapter");

                }


            }else {

                try{
                    onResume();
                }catch (Exception e){
                e.printStackTrace();
                }


            }



            mWaveSwipeRefreshLayout.setRefreshing(false);

            loadProgress.setVisibility(View.GONE);
            whatsNewRecycler.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
        super.onPause();
    }


    @Override
    protected void onResume() {
        super.onResume();
        displayFirebaseRegId();

//      fireabse code starts here
        // register GCM registration complete receiver
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(Config.REGISTRATION_COMPLETE));

        // register new push message receiver
        // by doing this, the activity will be notified each time a new message arrives
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(Config.PUSH_NOTIFICATION));

        // clear the notification area when the app is opened
        NotificationUtils.clearNotifications(getApplicationContext());


//        firebase code ends here



        feedsFilter = getSharedPreferences(CURRENT_FILTER,0);

        fabVisibility();


        if (feedsFilter.getString("currentState","All States").equals("All States") && feedsFilter.getString("currentCatagory","All Categories").equals("All Categories") && feedsFilter.getString("currentType","All Types").equals("All Types") ){

            // Keep The same Filter
            Log.e(TAG,"VOILA LOADIND ALL OF EM ");


            if (companyTitles.size() == 0){

                noAdverts.setVisibility(View.VISIBLE);
                whatsNewRecycler.setVisibility(View.GONE);
//                Log.e(TAG," TRAP #1 Set Visibilty jin Here , Count = "+companyTitlesNew.size());

            }else{

                noAdverts.setVisibility(View.GONE);
                whatsNewRecycler.setVisibility(View.VISIBLE);

            }



             try{
            whatsNewRecyclerAdapter = new FeedsAdapter(Jobs.this,companyTitles,jobInfo,postDate,callArray,addState,emailArray,banners);
            whatsNewRecycler.setAdapter(whatsNewRecyclerAdapter);
            }catch (Exception e){
                e.printStackTrace();
                }

            


        }else if (!feedsFilter.getString("currentState","All States").equals("All States") && feedsFilter.getString("currentCatagory","All Categories").equals("All Categories") && feedsFilter.getString("currentType","All Types").equals("All Types") ){

            companyTitlesNew = new ArrayList<>();
            postDateNew = new ArrayList<>();
            callArrayNew = new ArrayList<>();
            emailArrayNew = new ArrayList<>();
            websiteArrayNew = new ArrayList<>();
            jobInfoNew = new ArrayList<>();
            bannersNew = new ArrayList<>();

            clearList();


            for (int i = 0; i < companyTitles.size(); i++){

                if ( addState.get(i).contains(feedsFilter.getString("currentState","a")  ) ){

                    companyTitlesNew.add(companyTitles.get(i));
                    postDateNew.add(postDate.get(i));
                    jobInfoNew.add(jobInfo.get(i));
                    callArrayNew.add(callArray.get(i));
                    emailArrayNew.add(emailArray.get(i));
                    websiteArrayNew.add(addState.get(i));
                    bannersNew.add(banners.get(i));
                }


            }




            if (companyTitlesNew.size() == 0){

                noAdverts.setVisibility(View.VISIBLE);
                whatsNewRecycler.setVisibility(View.GONE);
                Log.e(TAG," TRAP #1 Set Visibilty Here , Count = "+companyTitlesNew.size());

            }else{

                noAdverts.setVisibility(View.GONE);
                whatsNewRecycler.setVisibility(View.VISIBLE);

            }



            Log.e(TAG,"Filtering State , Rest ALL");
            try{

            whatsNewRecyclerAdapter = new FeedsAdapter(Jobs.this,companyTitlesNew,jobInfoNew,postDateNew,callArrayNew,websiteArrayNew,emailArrayNew,bannersNew);
            whatsNewRecycler.setAdapter(whatsNewRecyclerAdapter);

            }catch(Exception e){
                e.printStackTrace();
            }

        }else if (feedsFilter.getString("currentState","All States").equals("All States") && !feedsFilter.getString("currentCatagory","All Categories").equals("All Categories") && feedsFilter.getString("currentType","All Types").equals("All Types") ){

            companyTitlesNew = new ArrayList<>();
            postDateNew = new ArrayList<>();
            callArrayNew = new ArrayList<>();
            emailArrayNew = new ArrayList<>();
            websiteArrayNew = new ArrayList<>();
            jobInfoNew = new ArrayList<>();
            bannersNew = new ArrayList<>();

            clearList();

            for (int i = 0; i < companyTitles.size(); i++){

                if ( addCatagory.get(i).contains(feedsFilter.getString("currentCatagory","a")  ) ){
                    companyTitlesNew.add(companyTitles.get(i));
                    postDateNew.add(postDate.get(i));
                    jobInfoNew.add(jobInfo.get(i));
                    callArrayNew.add(callArray.get(i));
                    emailArrayNew.add(emailArray.get(i));
                    websiteArrayNew.add(addState.get(i));
                    bannersNew.add(banners.get(i));

                }


            }


            if (companyTitlesNew.size() == 0){

                noAdverts.setVisibility(View.VISIBLE);
                whatsNewRecycler.setVisibility(View.GONE);
                Log.e(TAG," TRAP #1 Set Visibilty Here , Count = "+companyTitlesNew.size());

            }else{

                noAdverts.setVisibility(View.GONE);
                whatsNewRecycler.setVisibility(View.VISIBLE);

            }

            Log.e(TAG,"Filtering  Catagory , Rest ALL");

            try{

            whatsNewRecyclerAdapter = new FeedsAdapter(Jobs.this,companyTitlesNew,jobInfoNew,postDateNew,callArrayNew,websiteArrayNew,emailArrayNew,bannersNew);
            whatsNewRecycler.setAdapter(whatsNewRecyclerAdapter);

            }catch(Exception e){
                e.printStackTrace();
            }
        }

        else if (!feedsFilter.getString("currentState","All States").equals("All States") && !feedsFilter.getString("currentCatagory","All Categories").equals("All Categories") && feedsFilter.getString("currentType","All Types").equals("All Types") ){

            companyTitlesNew = new ArrayList<>();
            postDateNew = new ArrayList<>();
            callArrayNew = new ArrayList<>();
            emailArrayNew = new ArrayList<>();
            websiteArrayNew = new ArrayList<>();
            jobInfoNew = new ArrayList<>();
            bannersNew = new ArrayList<>();

            clearList();

            for (int i = 0; i < companyTitles.size(); i++){

                if ( addState.get(i).contains(feedsFilter.getString("currentState","a")  ) ){


                    if (addCatagory.get(i).contains(feedsFilter.getString("currentCatagory","a"))){

                        companyTitlesNew.add(companyTitles.get(i));
                        postDateNew.add(postDate.get(i));
                        jobInfoNew.add(jobInfo.get(i));
                        callArrayNew.add(callArray.get(i));
                        emailArrayNew.add(emailArray.get(i));
                        websiteArrayNew.add(addState.get(i));
                        bannersNew.add(banners.get(i));


                    }





                }






            }


            if (companyTitlesNew.size() == 0){

                noAdverts.setVisibility(View.VISIBLE);
                whatsNewRecycler.setVisibility(View.GONE);
                Log.e(TAG," TRAP #1 Set Visibilty Here , Count = "+companyTitlesNew.size());

            }else{

                noAdverts.setVisibility(View.GONE);
                whatsNewRecycler.setVisibility(View.VISIBLE);

            }

        

            try{

            Log.e(TAG,"STACATA , COOL MAN");
            whatsNewRecyclerAdapter = new FeedsAdapter(Jobs.this,companyTitlesNew,jobInfoNew,postDateNew,callArrayNew,websiteArrayNew,emailArrayNew,bannersNew);
            whatsNewRecycler.setAdapter(whatsNewRecyclerAdapter);
        
            }catch(Exception e){
            e.printStackTrace();
            }
            


        }else if (feedsFilter.getString("currentState","All States").equals("All States") && !feedsFilter.getString("currentCatagory","All Categories").equals("All Categories") && !feedsFilter.getString("currentType","All Types").equals("All Types") ){

            companyTitlesNew = new ArrayList<>();
            postDateNew = new ArrayList<>();
            callArrayNew = new ArrayList<>();
            emailArrayNew = new ArrayList<>();
            websiteArrayNew = new ArrayList<>();
            jobInfoNew = new ArrayList<>();
            bannersNew = new ArrayList<>();

            clearList();

            for (int i = 0; i < companyTitles.size(); i++){

                if ( addCatagory.get(i).contains(feedsFilter.getString("currentCatagory","a")  ) ){

                    if (addType.get(i).contains(feedsFilter.getString("currentType","a"))){


                        companyTitlesNew.add(companyTitles.get(i));
                        postDateNew.add(postDate.get(i));
                        jobInfoNew.add(jobInfo.get(i));
                        callArrayNew.add(callArray.get(i));
                        emailArrayNew.add(emailArray.get(i));
                        websiteArrayNew.add(addState.get(i));
                        bannersNew.add(banners.get(i));


                    }





                }






            }



            if (companyTitlesNew.size() == 0){

                noAdverts.setVisibility(View.VISIBLE);
                whatsNewRecycler.setVisibility(View.GONE);
                Log.e(TAG," TRAP #1 Set Visibilty Here , Count = "+companyTitlesNew.size());

            }else{

                noAdverts.setVisibility(View.GONE);
                whatsNewRecycler.setVisibility(View.VISIBLE);

            }
            Log.e(TAG,"STAITM , COOL MAN");
            
            try{

            whatsNewRecyclerAdapter = new FeedsAdapter(Jobs.this,companyTitlesNew,jobInfoNew,postDateNew,callArrayNew,websiteArrayNew,emailArrayNew,bannersNew);
            whatsNewRecycler.setAdapter(whatsNewRecyclerAdapter);
        
            }catch(Exception e){
                e.printStackTrace();
            }

        }
        else if (!feedsFilter.getString("currentState","All States").equals("All States") && !feedsFilter.getString("currentCatagory","All Categories").equals("All Categories") && !feedsFilter.getString("currentType","All Types").equals("All Types") ){

            companyTitlesNew = new ArrayList<>();
            postDateNew = new ArrayList<>();
            callArrayNew = new ArrayList<>();
            emailArrayNew = new ArrayList<>();
            websiteArrayNew = new ArrayList<>();
            jobInfoNew = new ArrayList<>();
            bannersNew = new ArrayList<>();



            clearList();

            for (int i = 0; i < companyTitles.size(); i++){

                if ( addCatagory.get(i).contains(feedsFilter.getString("currentCatagory","a")  ) ){


                    if (addCatagory.get(i).contains(feedsFilter.getString("currentCatagory","a"))){


                        if ( addType.get(i).contains(feedsFilter.getString("currentType","a"))  ){
                            companyTitlesNew.add(companyTitles.get(i));
                            postDateNew.add(postDate.get(i));
                            jobInfoNew.add(jobInfo.get(i));
                            callArrayNew.add(callArray.get(i));
                            emailArrayNew.add(emailArray.get(i));
                            websiteArrayNew.add(addState.get(i));
                            bannersNew.add(banners.get(i));


                        }


                    }





                }






            }

            if (companyTitlesNew.size() == 0){

                noAdverts.setVisibility(View.VISIBLE);
                whatsNewRecycler.setVisibility(View.GONE);
                Log.e(TAG," TRAP #1 Set Visibilty Here , Count = "+companyTitlesNew.size());

            }else{

                noAdverts.setVisibility(View.GONE);
                whatsNewRecycler.setVisibility(View.VISIBLE);

            }

            Log.e(TAG,"ITMSTACATA , FIRE IN THE HOLE");
            try{

            whatsNewRecyclerAdapter = new FeedsAdapter(Jobs.this,companyTitlesNew,jobInfoNew,postDateNew,callArrayNew,websiteArrayNew,emailArrayNew,bannersNew);
            whatsNewRecycler.setAdapter(whatsNewRecyclerAdapter);
           
        }catch(Exception e){
                e.printStackTrace();
        }

        }




    }

    void clearList(){



        companyTitlesNew.clear();
        postDateNew.clear();
        callArrayNew.clear();
        emailArrayNew.clear();
        websiteArrayNew.clear();
        jobInfoNew.clear();
        bannersNew.clear();





    }

    void doForSome(){

        companyAdds = getSharedPreferences("companyAdds",0);

        companyTitles.add("Hotel Victoria Palace");
        postDate.add("November 24 2016");
        callArray.add("121");
        emailArray.add("email@example.com");
        jobInfo.add(companyAdds.getString("add","Nothing Yet"));
        addState.add("Jammu and Kashmir");
        addCatagory.add("Engineers");
        addType.add("IT");
        banners.add("http://xpertouch.pe.hu/defaults/company_default.jpg");

        banners.add("http://xpertouch.pe.hu/defaults/company_default.jpg");
        banners.add("http://xpertouch.pe.hu/defaults/company_default.jpg");
        banners.add("http://xpertouch.pe.hu/defaults/company_default.jpg");
        banners.add("http://xpertouch.pe.hu/defaults/company_default.jpg");
        banners.add("http://xpertouch.pe.hu/defaults/company_default.jpg");

        companyTitles.add("IBM");
        companyTitles.add("Microsoft");
        companyTitles.add("hp");
        companyTitles.add("UClap");
        companyTitles.add("TCS");


        postDate.add("July 24 2016");
        postDate.add("March 24 2016");
        postDate.add("April 24 2016");
        postDate.add("May 24 2016");
        postDate.add("August 24 2016");


        callArray.add("1234");
        callArray.add("1234");
        callArray.add("1234");
        callArray.add("1234");
        callArray.add("1234");


        emailArray.add("email@example.com");
        emailArray.add("email@example.com");
        emailArray.add("email@example.com");
        emailArray.add("email@example.com");
        emailArray.add("email@example.com");


        String noOfPosts = "6",gender0 = "male",postPosition = "Junior Assistant",experienceNeeded = "2 Years",skillsNeeded = "Teaching and Java Programming";
        jobInfo.add("Posts : " + noOfPosts + " Gender : " + gender0 +"(s)  Position :" + postPosition + " Experience : "+ experienceNeeded +" Prescribed Qualification : " + skillsNeeded + "");
        jobInfo.add("Vacancy Of "+noOfPosts+" ");
        jobInfo.add("Can someome suggest me a good 3xt3 interior designer, i just need it for my new office!!,");
        jobInfo.add("Can someome suggest me 3xt3 a good interior designer, i just need it for my new office!!,3xt3" +
                "Can someome suggest me a good interior designer, i just need it for my new office!!3xt3");
        jobInfo.add("Can someome suggest 3xt3 me a good interior designer, 3xt3 i just need it for my new office!!," +
                "Can someome suggest me a good interior designer, i just need it for my new office!!");


        addState.add("Jammu and Kashmir");
        addState.add("Karnatka");
        addState.add("Madhya Pardesh");
        addState.add("Magaliya");
        addState.add("Swizerland");


        addCatagory.add("Doctors");
        addCatagory.add("Transporters");
        addCatagory.add("Cabs");
        addCatagory.add("Painters");
        addCatagory.add("Musicians");

        addType.add("Software");
        addType.add("Civil");
        addType.add("Mechanical");
        addType.add("Electronics");
        addType.add("Electrical");
    }

    void toolbarTypeFace(){


    }

    public void searchView(){

        TextView searchViewTint;

        feedsFilter = getSharedPreferences(CURRENT_FILTER,0);
        searchViewTint = (TextView) findViewById(R.id.searchViewTint);
        searchViewTint.setTextColor(Color.GRAY);
        searchViewTint.setTextSize(13);

        final SearchManager searchManager = (SearchManager)Jobs.this.getSystemService(SEARCH_SERVICE);
        searchViewJobs = (android.widget.SearchView)findViewById(R.id.searchViewJobs);
        searchViewJobs.setSearchableInfo(searchManager.getSearchableInfo(Jobs.this.getComponentName()));
        searchViewJobs.setQueryRefinementEnabled(true);



        searchJobOppurtunities.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchJobOppurtunities.startAnimation(AnimationUtils.loadAnimation(Jobs.this,R.anim.abc_fade_out));
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        searchJobOppurtunities.setVisibility(View.GONE);

                    }
                },300);

                searchViewJobs.setIconifiedByDefault(true);
                searchViewJobs.setFocusable(true);
                searchViewJobs.setIconified(false);
                searchViewJobs.requestFocusFromTouch();
                searchViewJobs.onActionViewExpanded();
            }
        });


        int id = searchViewJobs.getContext().getResources().getIdentifier("android:id/search_src_text", null, null);
        TextView textView = (TextView) searchViewJobs.findViewById(id);
        textView.setTextColor(Color.BLACK);
        textView.setHintTextColor(Color.GRAY);
        textView.setTextSize(13);
        searchViewJobs.setQueryHint("Search For companies, jobs and more");
        //searchViewJobs.setFocusable(true);
      //  searchViewJobs.setIconified(false);
     //   searchViewJobs.clearFocus();

//        searchViewJobs.setIconifiedByDefault(true);
        searchViewJobs.setOnQueryTextListener(new android.widget.SearchView.OnQueryTextListener() {



            @Override
            public boolean onQueryTextSubmit(String query){

                return false;

            }

            @Override
            public boolean onQueryTextChange(String searchQuery) {



                ArrayList<String> finalCompanyTitlesNew = new ArrayList<>();
                ArrayList<String> finalPostDateNew = new ArrayList<>();
                ArrayList<String> finalJobInfoNew = new ArrayList<>();
                ArrayList<String> finalEmailArrayNew = new ArrayList<>();
                ArrayList<String> finalWebsiteArrayNew = new ArrayList<>();
                ArrayList<String> finalCallArrayNew = new ArrayList<>();
                ArrayList<String> finalBannerArrayNew = new ArrayList<>();

                if (feedsFilter.getString("currentState","All States").equals("All States") && feedsFilter.getString("currentCatagory","All Categories").equals("All Categories") && feedsFilter.getString("currentType","All Types").equals("All Types") ) {

                    searchQuery = searchQuery.toLowerCase();
                    String[] searchData = searchQuery.split(" ");


                    for (String query: searchData){

                        for (int i = 0; i < companyTitles.size(); i++){


                            if (companyTitles.get(i).toLowerCase().contains(query)
                                    || addCatagory.get(i).toLowerCase().contains(query)
                                    || jobInfo.get(i).toLowerCase().contains(query)
                                    || postDate.get(i).toLowerCase().contains(query)){

                                finalCompanyTitlesNew.add(companyTitles.get(i));
                                finalPostDateNew.add(postDate.get(i));
                                finalJobInfoNew.add(jobInfo.get(i));
                                finalCallArrayNew.add(callArray.get(i));
                                finalEmailArrayNew.add(emailArray.get(i));
                                finalWebsiteArrayNew.add(addState.get(i));
                                finalBannerArrayNew.add(banners.get(i));

                            }




                        }




                    }

                    if (finalCompanyTitlesNew.size() == 0){

                        noAdverts.setVisibility(View.VISIBLE);
                        whatsNewRecycler.setVisibility(View.GONE);
  //                      Log.e(TAG," TRAP #1 Set Visibilty Here , Count = "+companyTitlesNew.size());

                    }else{

                        noAdverts.setVisibility(View.GONE);
                        whatsNewRecycler.setVisibility(View.VISIBLE);

                    }


                }

                else{

                    searchQuery = searchQuery.toLowerCase();
                    String[] searchData = searchQuery.split(" ");


                    for (String query: searchData){

                        int companyTitlesNewSize = 0;

                        if (companyTitlesNew != null){
                           companyTitlesNewSize = companyTitlesNew.size();
                        }


                        for (int i = 0; i < companyTitlesNewSize; i++){


                            if (companyTitlesNew.get(i).toLowerCase().contains(query)
                                    || addCatagory.get(i).toLowerCase().contains(query)
                                    || jobInfo.get(i).toLowerCase().contains(query)
                                    || postDate.get(i).toLowerCase().contains(query)){

                                finalCompanyTitlesNew.add(companyTitles.get(i));
                                finalPostDateNew.add(postDate.get(i));
                                finalJobInfoNew.add(jobInfo.get(i));
                                finalCallArrayNew.add(callArray.get(i));
                                finalEmailArrayNew.add(emailArray.get(i));
                                finalWebsiteArrayNew.add(addState.get(i));
                                finalBannerArrayNew.add(banners.get(i));

                            }




                        }






                    }
                }

                if (finalCompanyTitlesNew.size() == 0){

                    noAdverts.setVisibility(View.VISIBLE);
                    whatsNewRecycler.setVisibility(View.GONE);
//                    Log.e(TAG," TRAP #1 Set Visibilty Here , Count = "+companyTitlesNew.size());

                }else{

                    noAdverts.setVisibility(View.GONE);
                    whatsNewRecycler.setVisibility(View.VISIBLE);

                }


                try{

                whatsNewRecyclerAdapter = new FeedsAdapter(Jobs.this,finalCompanyTitlesNew,finalJobInfoNew,finalPostDateNew,finalCallArrayNew,finalWebsiteArrayNew,finalEmailArrayNew,finalBannerArrayNew);
                whatsNewRecycler.setAdapter(whatsNewRecyclerAdapter);
                whatsNewRecyclerAdapter.notifyDataSetChanged();

                }catch(Exception e){
                    e.printStackTrace();
                return true;
                }
                return true;


            }
        });




    }

    void bannerScanner(){

        userData = Jobs.this.getSharedPreferences(LOCAL_APP_DATA, Context.MODE_PRIVATE);


        if (!userData.getBoolean("VERIFIED",false)){
            if (!userData.getBoolean("IGOTIT",false)){

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        bottomBannerCheck();
                    }
                },VERIFY_BANNER_TIMEOUT);


            }
        }




    }

    class GetCurrentVersion extends AsyncTask< String , String, String >{

        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        StringBuilder builder = new StringBuilder();
        BufferedReader bufferedReader;



        @Override
        protected String doInBackground(String... params) {

            nameValuePairs.add(new BasicNameValuePair("version","silicon"));

            try {
                HttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost(getString(R.string.host)+"/jobs/app_version.php");
                httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                HttpResponse httpResponse =  (HttpResponse) httpClient.execute(httpPost);

                HttpEntity httpEntity = (HttpEntity) httpResponse.getEntity();

                bufferedReader = new BufferedReader(new InputStreamReader(httpEntity.getContent()));
                String str = "";

                while (  (str = bufferedReader.readLine())  != null ){

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
            SharedPreferences.Editor editor;
            updateInfo = getSharedPreferences(UPDATE_INFO,0);
            editor = updateInfo.edit();

            if (s.equals("1")){
                editor.putBoolean("updateAvailable",true);
                editor.apply();
                newVersionBanner();
            }else {
                Log.e("Version", "You are currently Using the latest Version");
            }

        }
    }

//    class GetCompanyStatus extends AsyncTask< String , String, String >{
//
//        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
//        StringBuilder builder = new StringBuilder();
//        BufferedReader bufferedReader;
//
//
//        @Override
//        protected String doInBackground(String... params) {
//
//
//            String COMPANY_DETAILS = "myCompanyDetails";
//            SharedPreferences myCompanyDetails;
//            myCompanyDetails = getSharedPreferences(COMPANY_DETAILS,0);
//
//            nameValuePairs.add(new BasicNameValuePair("companyTitle",myCompanyDetails.getString("companyTitle","nothingHere")));
//
//            try {
//
//
//                HttpClient httpClient = new DefaultHttpClient();
//                HttpPost httpPost = new HttpPost("check_app_version.php");
//
//                HttpResponse httpResponse =  (HttpResponse) httpClient.execute(httpPost);
//
//                HttpEntity httpEntity = (HttpEntity) httpResponse.getEntity();
//
//
//
//                bufferedReader = new BufferedReader(new InputStreamReader(httpEntity.getContent()));
//                String str = "";
//
//                while (  (str = bufferedReader.readLine())  != null ){
//
//                    builder.append(str);
//
//                }
//
//
//            } catch (ClientProtocolException e) {
//                e.printStackTrace();
//            } catch (IOException e) {
//                e.printStackTrace();
//            } catch (Exception e){
//
//            }
//
//
//            return builder.toString();
//
//
//
//        }
//
//
//        @Override
//        protected void onPostExecute(String s) {
//            super.onPostExecute(s);
//            String COMPANY_DETAILS = "myCompanyDetails";
//            SharedPreferences myCompanyDetails;
//            SharedPreferences.Editor editor;
//
//            myCompanyDetails = getSharedPreferences(COMPANY_DETAILS,0);
//            if (s.equals("false")){
//                editor = myCompanyDetails.edit();
//                editor.putBoolean("CVERIFIED",false);editor.apply();
//                editor.apply();
//            }
//
//        }
//    }

    void newVersionBanner(){

        LinearLayout updateAppNow;
        LinearLayout remindMELater;

        if (switchUserLL.getVisibility() == View.VISIBLE || bottomBanner.getVisibility() == View.VISIBLE){

            return;

        }


        smartBottom.startAnimation(AnimationUtils.loadAnimation(Jobs.this,R.anim.md_styled_slide_down_slow));
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
               smartBottom.setClickable(false);
               smartBottom.setVisibility(View.GONE);
            }
        },500);

        if (whyVerify.getVisibility() == View.VISIBLE){
            whyVerify.setVisibility(View.GONE);
        }

        newVersionFoundLL.startAnimation(AnimationUtils.loadAnimation(Jobs.this,R.anim.md_styled_slide_up_slow));
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                newVersionFoundLL.setVisibility(View.VISIBLE);
            }
        },450);

        remindMELater =(LinearLayout) findViewById(R.id.remindMELater);
        updateAppNow = (LinearLayout) findViewById(R.id.updateAppNow);

        remindMELater.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                newVersionFoundLL.startAnimation(AnimationUtils.loadAnimation(Jobs.this,R.anim.md_styled_slide_down_slow));

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        newVersionFoundLL.setVisibility(View.GONE);

                        smartBottom.startAnimation(AnimationUtils.loadAnimation(Jobs.this,R.anim.md_styled_slide_up_slow));
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                smartBottom.setClickable(true);
                                smartBottom.setVisibility(View.VISIBLE);
                            }
                        },500);


                    }
                },450);






            }
        });


        updateAppNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                newVersionFoundLL.startAnimation(AnimationUtils.loadAnimation(Jobs.this,R.anim.md_styled_slide_down_slow));

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        newVersionFoundLL.setVisibility(View.GONE);
                        Uri uri = Uri.parse("market://details?id="+getPackageName());
                        startActivity(new Intent(Intent.ACTION_VIEW,uri));

                        smartBottom.startAnimation(AnimationUtils.loadAnimation(Jobs.this,R.anim.md_styled_slide_up_slow));
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                smartBottom.setClickable(true);
                                smartBottom.setVisibility(View.VISIBLE);
                            }
                        },500);


                    }
                },450);


            }
        });


    }



    void switchUser(){

        userData = getSharedPreferences(LOCAL_APP_DATA,0);
        final TextView switchDescribeTop,switchDescribeBottom,cLoggedInAs;
        TextView logOutAccount;
        TextView cancelSwitch;
        TextView learnMore;



        switchDescribeTop = (TextView) findViewById(R.id.switchDescribeTop);
        switchDescribeBottom = (TextView) findViewById(R.id.switchDescribeBottom);
        cLoggedInAs = (TextView) findViewById(R.id.cLoggedInAs);
        logOutAccount = (TextView) findViewById(R.id.logOutAccount);
        cancelSwitch = (TextView) findViewById(R.id.cancelSwitch);
        learnMore = (TextView) findViewById(R.id.learnMore);
        switchUserLL.startAnimation(AnimationUtils.loadAnimation(Jobs.this,R.anim.md_styled_slide_up_slow));
        smartBottom.startAnimation(AnimationUtils.loadAnimation(Jobs.this,R.anim.md_styled_slide_down_slow));
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                smartBottom.setClickable(false);
                smartBottom.setVisibility(View.GONE);
            }
        },500);

        if (bottomBanner.getVisibility() == View.VISIBLE){
            bottomBanner.startAnimation(AnimationUtils.loadAnimation(Jobs.this,R.anim.md_styled_slide_down_slow));
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                    bottomBanner.setVisibility(View.GONE);
                    smartBottom.startAnimation(AnimationUtils.loadAnimation(Jobs.this,R.anim.md_styled_slide_up_slow));

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            smartBottom.setClickable(true);
                            smartBottom.setVisibility(View.VISIBLE);
                        }
                    },500);

                }
            },500);

        }

        if (newVersionFoundLL.getVisibility() == View.VISIBLE){

            newVersionFoundLL.startAnimation(AnimationUtils.loadAnimation(Jobs.this,R.anim.md_styled_slide_down_slow));

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    newVersionFoundLL.setVisibility(View.GONE);

                    smartBottom.startAnimation(AnimationUtils.loadAnimation(Jobs.this,R.anim.md_styled_slide_up_slow));
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            smartBottom.setClickable(true);
                            smartBottom.setVisibility(View.VISIBLE);
                        }
                    },500);


                }
            },450);



        }



        if (fab.getVisibility() == View.VISIBLE){
            isFabVisible  = true;

            if (isFabOpen){
                animateFAB();

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        fab.startAnimation(AnimationUtils.loadAnimation(Jobs.this,R.anim.fab_close));
                        fab.setClickable(false);

                    }
                },500);
            }else{

                fab.startAnimation(AnimationUtils.loadAnimation(Jobs.this,R.anim.fab_close));
                fab.setClickable(false);

            }

        }else {
            isFabVisible = false;
        }

        cLoggedInAs.setText("Currently Logged in as "+ userData.getString("userName",userData.getString("userEmail","Guest")));

        if (userData.getBoolean("VERIFIED",false)){
            switchDescribeTop.setText("Switching account is similer to logging out your account, after you logout your account you will not lose any amount of data stored locally in your phone.");
            switchDescribeBottom.setText("Local data like call logs and favourites saved on your device will not be lost");
            }else{
            switchDescribeTop.setText("Since your account is not verified therefore after you logout your account you cannot log in this account from any device");
            switchDescribeBottom.setText("Your account will only be included if you verify your Email address");

        }




        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
               switchUserLL.setVisibility(View.VISIBLE);

            }
        },500);



        logOutAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String COMPANY_DETAILS = "myCompanyDetails";
                SharedPreferences myCompanyDetails;
                final SharedPreferences.Editor myCompanyEditor;
                myCompanyDetails = getSharedPreferences(COMPANY_DETAILS,0);
                myCompanyEditor = myCompanyDetails.edit();


                switchUserLL.startAnimation(AnimationUtils.loadAnimation(Jobs.this,R.anim.md_styled_slide_down_slow));

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        switchUserLL.setVisibility(View.INVISIBLE);
                        editor = userData.edit();
                        editor.putBoolean("LOGEDOUT",true);
                        editor.putBoolean("EXPERT",false);
                        editor.putBoolean("COMPANY",false);
                        editor.putBoolean("IGOTIT",false);
                        editor.putBoolean("LOGEDIN",false);
                        editor.putBoolean("VERIFIED",false);
                        myCompanyEditor.putBoolean("CVERIFIED",false);
                        myCompanyEditor.putString("COTP","0");

                        myCompanyEditor.apply();
                        editor.apply();
                        startActivity(new Intent(Jobs.this,Registrations.class));
                        Jobs.this.finish();

                    }
                },500);


            }
        });

        cancelSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // cancel Switch

                switchUserLL.startAnimation(AnimationUtils.loadAnimation(Jobs.this,R.anim.md_styled_slide_down_slow));

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                       switchUserLL.setVisibility(View.INVISIBLE);

                        jobsLayout.setEnabled(true);
                        if (isFabVisible){
                            fab.startAnimation(AnimationUtils.loadAnimation(Jobs.this,R.anim.card_animation));
                            fab.setVisibility(View.VISIBLE);
                            fab.setClickable(true);
                        }else{

                            fab.setVisibility(View.INVISIBLE);
                        }

                        smartBottom.startAnimation(AnimationUtils.loadAnimation(Jobs.this,R.anim.md_styled_slide_up_slow));
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                smartBottom.setClickable(true);
                                smartBottom.setVisibility(View.VISIBLE);
                            }
                        },500);



                    }
                },500);
            }
        });



        learnMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                switchOnClick();


            }
        });





        switchBulbF = (ViewFlipper) findViewById(R.id.switchBulbF);

        switchBulbF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchOnClick();
            }
        });






    }


    void switchOnClick(){

        switchUserLL.startAnimation(AnimationUtils.loadAnimation(Jobs.this,R.anim.md_styled_slide_down_slow));

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                switchUserLL.setVisibility(View.INVISIBLE);

                if (isFabVisible){
                    fab.startAnimation(AnimationUtils.loadAnimation(Jobs.this,R.anim.card_animation));
                    fab.setVisibility(View.VISIBLE);
                    fab.setClickable(true);

                }else{

                    fab.setVisibility(View.INVISIBLE);
                }

                smartBottom.startAnimation(AnimationUtils.loadAnimation(Jobs.this,R.anim.md_styled_slide_up_slow));
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        smartBottom.setClickable(true);
                        smartBottom.setVisibility(View.VISIBLE);
                    }
                },500);


                startActivity(new Intent(Jobs.this,HelpCenter.class).putExtra("from","switch"));
            }
        },500);


    }


}
