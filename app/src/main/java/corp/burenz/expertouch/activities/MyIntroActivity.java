package corp.burenz.expertouch.activities;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.LayoutInflaterCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.mikepenz.iconics.context.IconicsLayoutInflater;
import com.mikepenz.iconics.view.IconicsImageView;

import corp.burenz.expertouch.R;
import corp.burenz.expertouch.fragments.intro.Fifth;
import corp.burenz.expertouch.fragments.intro.FifthSecond;
import corp.burenz.expertouch.fragments.intro.First;
import corp.burenz.expertouch.fragments.intro.Fourth;
import corp.burenz.expertouch.fragments.intro.Second;
import corp.burenz.expertouch.fragments.intro.Six;
import corp.burenz.expertouch.fragments.intro.Third;

public class MyIntroActivity extends AppCompatActivity {


    LinearLayout    layerOne,layerTwo,layerThree,layerFour,layerFive,layerSix,layerSeven,mDone;
    LinearLayout    upperHalfintro,lowerHalfIntro;
    Button          letsGetGoing,letsGoFinally;
    Animation       slide_up,slide_down,fab_open,fab_close,card_animation;
    Typeface        logoTypeface;
    TextView        nowAnythingTV,clickAwayTV,jobsLocalTV;

    final private int           REQUEST_CODE_ASK_PERMISSIONS = 123;
    private static final int    MY_CAMERA_REQUEST_CODE = 100;

    int PERMISSION_REQUEST_CODE = 1;




    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        LayoutInflaterCompat.setFactory(getLayoutInflater(), new IconicsLayoutInflater(getDelegate()));

        super.onCreate(savedInstanceState);
        setContentView(R.layout.intro_one);
        initViews();




        letsGoFinally.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (layerSeven.getVisibility() == View.VISIBLE){



                    letsGoFinally.setClickable(false);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mDone.startAnimation(AnimationUtils.loadAnimation(MyIntroActivity.this,R.anim.fab_close));
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    startActivity(new Intent(MyIntroActivity.this,Registrations.class));
                                    finish();
                                }
                            },300);

                        }
                    },500);

                }



            }
        });


        letsGetGoing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v){

                if((Build.VERSION.SDK_INT > 23)){
                    letsGetGoing.setText("Get Started");
                    Toast.makeText(MyIntroActivity.this, "below 23", Toast.LENGTH_SHORT).show();

                }else {
                    letsGetGoing.setText("Grant Permissions");
                    Toast.makeText(MyIntroActivity.this, "above 23", Toast.LENGTH_SHORT).show();

                }



                if ( letsGetGoing.getText().toString().contains("Grant") ){
                    requestForPermissions();
                    letsGetGoing.setText("Get Started");
                    Toast.makeText(MyIntroActivity.this, "inside Grant", Toast.LENGTH_SHORT).show();


                }else{

                    Toast.makeText(MyIntroActivity.this, "Outside grant", Toast.LENGTH_SHORT).show();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Snackbar.make(v,"Sit back and Relax While the App Introduces itself",Snackbar.LENGTH_SHORT).show();
                        }
                    },4000);

                    Animation slideup  = AnimationUtils.loadAnimation(MyIntroActivity.this,R.anim.abc_slide_out_top);

                    slideup.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    lowerHalfIntro.startAnimation(AnimationUtils.loadAnimation(MyIntroActivity.this,R.anim.md_styled_slide_down_slow));
                                    new Handler().postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            lowerHalfIntro.setVisibility(View.GONE);
                                        }
                                    },300);
                                }
                            },400);

                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {
                            upperHalfintro.setVisibility(View.INVISIBLE);
                            // call intro 2 function

                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    setIntroTwo();
                                }
                            },600);

                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {

                        }
                    });

                    upperHalfintro.startAnimation(slideup);


                }

            }
        });


    }


    void initViews(){



        layerOne            = (LinearLayout)    findViewById(R.id.layerOne);
        upperHalfintro      = (LinearLayout)    findViewById(R.id.upperHalfIntro);
        lowerHalfIntro      = (LinearLayout)    findViewById(R.id.lowerHalfIntro);
        layerTwo            = (LinearLayout)    findViewById(R.id.layerTwoIntro);
        layerThree          = (LinearLayout)    findViewById(R.id.layerThreeIntro);
        layerFour           = (LinearLayout)    findViewById(R.id.layerFour);
        layerFive           = (LinearLayout)    findViewById(R.id.layerFive);
        layerSix            = (LinearLayout)    findViewById(R.id.layerSix);
        layerSeven          = (LinearLayout)    findViewById(R.id.layerSeven);
        mDone               = (LinearLayout)    findViewById(R.id.mDone);
        letsGetGoing        = (Button)          findViewById(R.id.letsGetGoingOne);

        letsGoFinally       = (Button) findViewById(R.id.letsGoFinally);
        slide_up            = AnimationUtils.loadAnimation(MyIntroActivity.this,R.anim.md_styled_slide_up_slow);
        slide_down          = AnimationUtils.loadAnimation(MyIntroActivity.this,R.anim.md_styled_slide_down_slow);
        card_animation      = AnimationUtils.loadAnimation(MyIntroActivity.this,R.anim.card_animation);
        fab_open            = AnimationUtils.loadAnimation(MyIntroActivity.this,R.anim.fab_open);
        fab_close           = AnimationUtils.loadAnimation(MyIntroActivity.this,R.anim.fab_close);

        logoTypeface        = Typeface.createFromAsset(MyIntroActivity.this.getAssets(), "fonts/forte.ttf");

        nowAnythingTV       =  (TextView)       findViewById(R.id.now_anything_text_view);
        clickAwayTV         =  (TextView)       findViewById(R.id.clickaway_text_view);
        jobsLocalTV         =  (TextView)       findViewById(R.id.jobs_local_text_view);


        try{
            assert nowAnythingTV != null;
            assert clickAwayTV != null;
            assert jobsLocalTV != null;

            nowAnythingTV.setTypeface(logoTypeface);
            clickAwayTV.setTypeface(logoTypeface);
            jobsLocalTV.setTypeface(logoTypeface);

        }catch (Exception e){
            e.printStackTrace();
        }



    }


    void setIntroTwo(){
        final ImageView tabTwoImage;
        final TextView lookinForTV,dontWorryTV,usesLocationTV;

        lookinForTV = (TextView)findViewById(R.id.lokinForTV);
        dontWorryTV = (TextView) findViewById(R.id.dontWorryTV);
        usesLocationTV = (TextView) findViewById(R.id.usesLocationTV);
        tabTwoImage = (ImageView) findViewById(R.id.tabTwoImage);

        lookinForTV.startAnimation(AnimationUtils.loadAnimation(MyIntroActivity.this,R.anim.card_animation));

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
               lookinForTV.setVisibility(View.VISIBLE);
            }
        },300);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                dontWorryTV.startAnimation(AnimationUtils.loadAnimation(MyIntroActivity.this,R.anim.card_animation));
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                       dontWorryTV.setVisibility(View.VISIBLE);
                        tabTwoImage.startAnimation(AnimationUtils.loadAnimation(MyIntroActivity.this,R.anim.design_bottom_sheet_slide_in_scan));
                        usesLocationTV.startAnimation(AnimationUtils.loadAnimation(MyIntroActivity.this,R.anim.card_animation));
                       new Handler().postDelayed(new Runnable() {
                           @Override
                           public void run() {
                              usesLocationTV.setVisibility(View.VISIBLE);
                                tabTwoImage.setVisibility(View.VISIBLE);
                               new Handler().postDelayed(new Runnable() {
                                   @Override
                                   public void run() {

                                       layerTwo.startAnimation(AnimationUtils.loadAnimation(MyIntroActivity.this,R.anim.abc_fade_out));
                                       layerThree.startAnimation(AnimationUtils.loadAnimation(MyIntroActivity.this,R.anim.abc_slide_in_top));
                                       new Handler().postDelayed(new Runnable() {
                                           @Override
                                           public void run() {
                                               layerTwo.setVisibility(View.GONE);
                                               layerThree.setVisibility(View.VISIBLE);

                                               layerFour.startAnimation(AnimationUtils.loadAnimation(MyIntroActivity.this,R.anim.slide_left));
                                               new Handler().postDelayed(new Runnable() {
                                                   @Override
                                                   public void run() {
                                                   layerFour.setVisibility(View.VISIBLE);
                                                   }
                                               },300);
                                               // call function here...
                                                introThree();
                                           }
                                       },300);

                                   }
                               },4000);

                           }
                       },300);
                    }
                },300);

            }
        },100);




    }



    void introThree(){

    final TextView titleThree,subtitleThree,usesThreeTV;
    final ImageView tabThreePic;

    titleThree = (TextView) findViewById(R.id.titleThreeTV);
    subtitleThree = (TextView) findViewById(R.id.subTitileThreeTV);
    usesThreeTV = (TextView) findViewById(R.id.usesThreeTV);
    tabThreePic = (ImageView) findViewById(R.id.tabThreePic);


        titleThree.startAnimation(AnimationUtils.loadAnimation(MyIntroActivity.this,R.anim.card_animation));
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
               titleThree.setVisibility(View.VISIBLE);
                subtitleThree.startAnimation(AnimationUtils.loadAnimation(MyIntroActivity.this,R.anim.card_animation));
                tabThreePic.startAnimation(AnimationUtils.loadAnimation(MyIntroActivity.this,R.anim.design_bottom_sheet_slide_in_scan));

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                       subtitleThree.setVisibility(View.VISIBLE);
                       tabThreePic.setVisibility(View.VISIBLE);
                        usesThreeTV.startAnimation(AnimationUtils.loadAnimation(MyIntroActivity.this,R.anim.card_animation));

                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                               usesThreeTV.setVisibility(View.VISIBLE);

                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {

                                        layerThree.startAnimation(AnimationUtils.loadAnimation(MyIntroActivity.this,R.anim.abc_fade_out));
                                        layerFour.startAnimation(AnimationUtils.loadAnimation(MyIntroActivity.this,R.anim.abc_slide_in_top));
                                        new Handler().postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                         layerThree.setVisibility(View.GONE);
                                         layerFour.setVisibility(View.VISIBLE);
                                         // call new Frame here
                                        introFour();

                                        }
                                    },300);
                                    }
                                },4000);
                            }
                        },300);
                    }
                },300);
            }
        },300);





    }


    void introFour(){

        final TextView titleFourIntro,subTitleFourIntro,usesFourIntro;
        final ImageView tabFourIntro;

        titleFourIntro = (TextView) findViewById(R.id.titleFourIntro);
        subTitleFourIntro = (TextView) findViewById(R.id.subTitleFourIntro);
        usesFourIntro = (TextView) findViewById(R.id.usesFourIntro);
        tabFourIntro = (ImageView) findViewById(R.id.tabFourIntro);



        titleFourIntro.startAnimation(AnimationUtils.loadAnimation(MyIntroActivity.this,R.anim.card_animation));
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                titleFourIntro.setVisibility(View.VISIBLE);
                subTitleFourIntro.startAnimation(AnimationUtils.loadAnimation(MyIntroActivity.this,R.anim.card_animation));
                tabFourIntro.startAnimation(AnimationUtils.loadAnimation(MyIntroActivity.this,R.anim.design_bottom_sheet_slide_in_scan));

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        subTitleFourIntro.setVisibility(View.VISIBLE);
                        tabFourIntro.setVisibility(View.VISIBLE);
                        usesFourIntro.startAnimation(AnimationUtils.loadAnimation(MyIntroActivity.this,R.anim.card_animation));

                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                usesFourIntro.setVisibility(View.VISIBLE);

                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {

                                        layerFour.startAnimation(AnimationUtils.loadAnimation(MyIntroActivity.this,R.anim.abc_fade_out));
                                        layerFive.startAnimation(AnimationUtils.loadAnimation(MyIntroActivity.this,R.anim.abc_slide_in_top));
                                        new Handler().postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                layerFour.setVisibility(View.GONE);
                                                layerFive.setVisibility(View.VISIBLE);
                                                // call new Frame here

                                                introFive();
                                            }
                                        },300);
                                    }
                                },6000);
                            }
                        },300);
                    }
                },300);
            }
        },300);









    }



    void introFive(){

        final TextView titleFourIntro,subTitleFourIntro;
        final ImageView tabFourIntro;

        titleFourIntro = (TextView) findViewById(R.id.titleFiveIntro);
        subTitleFourIntro = (TextView) findViewById(R.id.subTitleFiveIntro);
        tabFourIntro = (ImageView) findViewById(R.id.tabFiveIntro);



        titleFourIntro.startAnimation(AnimationUtils.loadAnimation(MyIntroActivity.this,R.anim.card_animation));
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                titleFourIntro.setVisibility(View.VISIBLE);
                subTitleFourIntro.startAnimation(AnimationUtils.loadAnimation(MyIntroActivity.this,R.anim.card_animation));
                tabFourIntro.startAnimation(AnimationUtils.loadAnimation(MyIntroActivity.this,R.anim.design_bottom_sheet_slide_in_scan));

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        subTitleFourIntro.setVisibility(View.VISIBLE);
                        tabFourIntro.setVisibility(View.VISIBLE);

                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {

                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {

                                        layerFive.startAnimation(AnimationUtils.loadAnimation(MyIntroActivity.this,R.anim.abc_fade_out));
                                        layerSix.startAnimation(AnimationUtils.loadAnimation(MyIntroActivity.this,R.anim.abc_slide_in_top));
                                        new Handler().postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                layerFive.setVisibility(View.GONE);
                                                layerSix.setVisibility(View.VISIBLE);
                                                // call new Frame here

                                                introSix();

                                            }
                                        },300);
                                    }
                                },6000);
                            }
                        },300);
                    }
                },300);
            }
        },300);









    }

    void introSix(){

        final TextView titleFourIntro,subTitleFourIntro;
        final ImageView tabFourIntro;

        titleFourIntro = (TextView) findViewById(R.id.titleSixIntro);
        subTitleFourIntro = (TextView) findViewById(R.id.subTitleSixIntro);
        tabFourIntro = (ImageView) findViewById(R.id.tabSixIntro);



        titleFourIntro.startAnimation(AnimationUtils.loadAnimation(MyIntroActivity.this,R.anim.card_animation));
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                titleFourIntro.setVisibility(View.VISIBLE);
                subTitleFourIntro.startAnimation(AnimationUtils.loadAnimation(MyIntroActivity.this,R.anim.card_animation));
                tabFourIntro.startAnimation(AnimationUtils.loadAnimation(MyIntroActivity.this,R.anim.design_bottom_sheet_slide_in_scan));

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        subTitleFourIntro.setVisibility(View.VISIBLE);
                        tabFourIntro.setVisibility(View.VISIBLE);

                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {

                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {

                                        layerSix.startAnimation(AnimationUtils.loadAnimation(MyIntroActivity.this,R.anim.abc_fade_out));
                                        layerSeven.startAnimation(AnimationUtils.loadAnimation(MyIntroActivity.this,R.anim.abc_slide_in_top));
                                        new Handler().postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                layerSix.setVisibility(View.GONE);
                                                layerSeven.setVisibility(View.VISIBLE);
                                                // call new Frame here
                                                introSeven();
                                                letsGoFinally.setClickable(true);
                                            }
                                        },300);
                                    }
                                },6000);
                            }
                        },300);
                    }
                },300);
            }
        },300);







    }

    void introSeven(){

        final TextView titleFourIntro,subTitleFourIntro;
        final  IconicsImageView tabFourIntro;

        titleFourIntro = (TextView) findViewById(R.id.titleSevenIntro);
        subTitleFourIntro = (TextView) findViewById(R.id.subtitleSevenIntro);
        tabFourIntro = (IconicsImageView) findViewById(R.id.tabSevenIntro);

        titleFourIntro.startAnimation(AnimationUtils.loadAnimation(MyIntroActivity.this,R.anim.card_animation));

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                titleFourIntro.setVisibility(View.VISIBLE);
                subTitleFourIntro.startAnimation(AnimationUtils.loadAnimation(MyIntroActivity.this,R.anim.card_animation));
                tabFourIntro.startAnimation(AnimationUtils.loadAnimation(MyIntroActivity.this,R.anim.md_styled_zoom_in));

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        subTitleFourIntro.setVisibility(View.VISIBLE);
                        tabFourIntro.setVisibility(View.VISIBLE);

                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {

  /////////////////
                            }
                        },300);
                    }
                },300);
            }
        },300);









    }



    private void checkCameraPermissions(){


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.CAMERA)
                    != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.CAMERA},
                        MY_CAMERA_REQUEST_CODE);
            }
        }

    }

    private void requestForPermissions() {

        int hasContactPermission = ActivityCompat.checkSelfPermission(MyIntroActivity.this,Manifest.permission.RECEIVE_SMS);

        if(hasContactPermission != PackageManager.PERMISSION_GRANTED ) {
            ActivityCompat.requestPermissions(MyIntroActivity.this, new String[]   {Manifest.permission.RECEIVE_SMS,Manifest.permission.CAMERA,Manifest.permission.CALL_PHONE}, 123);

        }else {
            //Toast.makeText(AddContactsActivity.this, "Contact Permission is already granted", Toast.LENGTH_LONG).show();
            checkCameraPermissions();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_ASK_PERMISSIONS:
                // Check if the only required permission has been granted
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.i("Permission", "Contact permission has now been granted. Showing result.");
                    checkCameraPermissions();

                } else {
                    Log.i("Permission", "Contact permission was NOT granted.");
                    checkCameraPermissions();

                }
                break;

            case MY_CAMERA_REQUEST_CODE:

                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    Toast.makeText(this, "Camera Granted", Toast.LENGTH_SHORT).show();


                } else {
//                    Toast.makeText(this, "Camera Denied", Toast.LENGTH_SHORT).show();

                }

                break;



        }
    }


}

