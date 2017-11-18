package corp.burenz.expertouch.activities;

import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.LayoutInflaterCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mikepenz.iconics.Iconics;
import com.mikepenz.iconics.context.IconicsLayoutInflater;

import corp.burenz.expertouch.R;
import corp.burenz.expertouch.adapters.HelpCenterAdapter;

public class HelpCenter extends AppCompatActivity {

    LinearLayout swapSheild;
    ImageView swapSheildButton;
    LinearLayout basicHelpL,hireExpertsHelp,profileHelp,bucketHelp,businessHelp,otherHelp;
    ImageView swapImage;
    TextView swapTitle,swapSubtitle;
    RecyclerView helpCenterRV;
    RecyclerView.Adapter adapter;
    MediaPlayer ourSplasSound;


    SharedPreferences narrate;
    String NARRATION = "narrate";
    Boolean NARRATE_FLAG = false;
    void setFeilds(int src,String title,String subtitle){

        swapImage.setImageResource(src);
        swapTitle.setText(title);
        swapSubtitle.setText(subtitle);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        LayoutInflaterCompat.setFactory(getLayoutInflater(), new IconicsLayoutInflater(getDelegate()));
        super.onCreate(savedInstanceState);

//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
//
        setContentView(R.layout.activity_help_center);




        ourSplasSound =  MediaPlayer.create(HelpCenter.this,R.raw.guide);
        swapSheildButton  = (ImageView) findViewById(R.id.swapSheildButton);
        swapSheild = (LinearLayout) findViewById(R.id.swapSheild);
        basicHelpL = (LinearLayout) findViewById(R.id.basicHelpL);
        swapImage = (ImageView) findViewById(R.id.swapImage);
        swapTitle = (TextView) findViewById(R.id.swapTitle);
        swapSubtitle  = (TextView) findViewById(R.id.swapSubtitle);
        helpCenterRV = (RecyclerView) findViewById(R.id.helpCenterRV);
        helpCenterRV.setLayoutManager(new LinearLayoutManager(HelpCenter.this));
        hireExpertsHelp = (LinearLayout) findViewById(R.id.hireExpertsHelp);
        profileHelp = (LinearLayout) findViewById(R.id.profileHelp);
        bucketHelp = (LinearLayout) findViewById(R.id.bucketHelp);
        businessHelp = (LinearLayout) findViewById(R.id.businessHelp);
        otherHelp = (LinearLayout) findViewById(R.id.otherHelp);



        Iconics.init(HelpCenter.this);

        narrate = getSharedPreferences(NARRATION,0);

        NARRATE_FLAG = narrate.getBoolean("narrate",true);



        Bundle bundle = getIntent().getExtras();


        if (bundle != null){

            final String tep = bundle.getString("from","home");

            if (!tep.equals("home")){

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {


                        switch (tep){

                            case "verify":
                                callBasicHelp();
                                break;

                            case "switch":
                                othersHelp();





                        }


                    }
                },200);

            }

        }





        basicHelpL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                callBasicHelp();



            }
        });



        swapSheildButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                swapSheild.startAnimation(AnimationUtils.loadAnimation(HelpCenter.this,R.anim.abc_slide_out_top));



                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swapSheild.setClickable(false);
                        swapSheild.setVisibility(View.INVISIBLE);
                    }
                },400);

            }
        });


        hireExpertsHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hireHelp();
            }
        });

        profileHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                profileHelp();
            }
        });
        bucketHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bucketHelp();
            }
        });
        businessHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                businessHelp();
            }
        });
        otherHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                othersHelp();
            }
        });



    }


    void callBasicHelp(){


        String questions[] = {

                "What this app all about?",
                "Who is an expert?",
                "Who can register as business?",
                "Why is verification important?",
                "Is expert of business account necessary?"

        };



        String solutions[] = {
                "The app lets you hire people fro your state, it also helps you grow your business and it get you every single job oppurtunity in your state",
                "An expert is an individual who is skilled in any field",
                "Anyone who runs a firm or owns a store or owns a company can register with us including govt. Departments",
                "verification helps us know who is calling companies and experts from our application",
                "No, you be our guest and enjoy all the services provided by the application for free"
        };

        if (NARRATE_FLAG){

            ourSplasSound =  MediaPlayer.create(HelpCenter.this,R.raw.guide);
            ourSplasSound.start();

        }


        adapter = new HelpCenterAdapter(HelpCenter.this,questions,solutions);
        helpCenterRV.setAdapter(adapter);
        setFeilds(R.drawable.laptop_experts,"Basics","What we Offer");
        swapSheild.startAnimation(AnimationUtils.loadAnimation(HelpCenter.this,R.anim.abc_slide_in_top));

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                swapSheild.setClickable(true);
                swapSheild.setVisibility(View.VISIBLE);
            }
        },400);


    }

    void profileHelp(){


        String questions[] = {

                "Who can see my profile?",
                "What if i don't have any experience?",
                "Do i get any updates about my profile?",
                "Can i update my profile?",
                "I am a Doctor, Why should i register?"

        };



        String solutions[] = {
                "it depends on your choice whether you want to keep your profile visibility to the companies or to the public",
                "If you have no experience then you are automatically labelled as a Fresher in your profile",
                "Whenever someone sees your profile in our application, you will be notified with the persons name in your registered email address",
                "Yes you can update your profile anytime you wish",
                "if you belong to this profession  the you can keep your visibility companies where private and govt hospitals can see your profile and hire you according to their interest"
        };



        if (NARRATE_FLAG){
            ourSplasSound.stop();
            ourSplasSound =  MediaPlayer.create(HelpCenter.this,R.raw.profile);
            ourSplasSound.start();

        }


        adapter = new HelpCenterAdapter(HelpCenter.this,questions,solutions);
        helpCenterRV.setAdapter(adapter);
        setFeilds(R.drawable.labours,"Profile","How Can We Help");
        swapSheild.startAnimation(AnimationUtils.loadAnimation(HelpCenter.this,R.anim.abc_slide_in_top));

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                swapSheild.setClickable(true);
                swapSheild.setVisibility(View.VISIBLE);
            }
        },400);


    }
    void businessHelp(){


        String questions[] = {
                "What is a business Account?",
                "Why should i upload my Registration Certificate?",
                "How can i register a Govt. Department?",
                "Where do i get my business account activation code?"

        };

        String solutions[] = {
                "A business account lets you post job vacancies or your business promotions in our application which helps your business reach to people in your state",
                "We want to make sure that the company or business being promoted in our application is 100% genuine",
                "You have to mention Govt. followed by your Department name and you don't have to upload any certificate for verification","After you upload your Registration Certificate  our analysts analyze your certificate and upon successful verification you will get a call from Our Customer care who will give you the activation code"
        };



        if (NARRATE_FLAG){
            ourSplasSound.stop();
            ourSplasSound =  MediaPlayer.create(HelpCenter.this,R.raw.business);
            ourSplasSound.start();

        }


        adapter = new HelpCenterAdapter(HelpCenter.this,questions,solutions);
        helpCenterRV.setAdapter(adapter);
        setFeilds(R.drawable.consultants_vector,"Business Account","Expand You Business");
        swapSheild.startAnimation(AnimationUtils.loadAnimation(HelpCenter.this,R.anim.abc_slide_in_top));

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                swapSheild.setClickable(true);
                swapSheild.setVisibility(View.VISIBLE);
            }
        },400);


    }
    void bucketHelp(){

        String questions[] = {
                "What is bucket store?",
                "How can i post in bucket store?",
                "I don't see my favourite store promotions?"
        };

        String solutions[] = {
                "Its a place where you get all the offers in various fields from your state posted by your favourite malls and hotels",
                "You need to register a business account where we will enable you to post in jobs and bucket store",
                "If you favourite Store is not listed in our application then you can suggest this application to your store."

        };


        if (NARRATE_FLAG){
            ourSplasSound.stop();
            ourSplasSound =  MediaPlayer.create(HelpCenter.this,R.raw.bucketstore);
            ourSplasSound.start();

        }


        adapter = new HelpCenterAdapter(HelpCenter.this,questions,solutions);
        helpCenterRV.setAdapter(adapter);
        setFeilds(R.drawable.hot_w,"Bucket Store","Saves Your Money and Time");
        swapSheild.startAnimation(AnimationUtils.loadAnimation(HelpCenter.this,R.anim.abc_slide_in_top));

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                swapSheild.setClickable(true);
                swapSheild.setVisibility(View.VISIBLE);
            }
        },400);


    }
    void othersHelp(){


        String questions[] = {
                "How is switch account Different?",
                "Where do these expert profile come from?",
                "I am a carpenter, How can i register?",
                "Where does the app gets Govt. jobs?"
        };


        String solutions[] = {
                "Switching account is a bit different from logout, while you switch an account the jobs and experts saved in your application are not erased",
                "The profiles are added both online in our app and offline through forms as well",
                "Don't worry, if you cannot manage to register online then you just get the offline application form our store and our marketing manager will help you fill the form and getting you registered",
                "We have a Sharp job analyst who keeps an eye to every single govt. job oppurtunity in the state and posts it right away, for corporate jobs business owners post vacancies on their owners"
        };



        if (NARRATE_FLAG){
            ourSplasSound.stop();
            ourSplasSound =  MediaPlayer.create(HelpCenter.this,R.raw.other);
            ourSplasSound.start();

        }


        adapter = new HelpCenterAdapter(HelpCenter.this,questions,solutions);
        helpCenterRV.setAdapter(adapter);
        setFeilds(R.drawable.other_experts,"Others","How we work");
        swapSheild.startAnimation(AnimationUtils.loadAnimation(HelpCenter.this,R.anim.abc_slide_in_top));

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                swapSheild.setClickable(true);
                swapSheild.setVisibility(View.VISIBLE);
            }
        },400);


    }
    void hireHelp(){


        String questions[] = {
                "How can i hire someone?",
                "How do report an expert?",
                "How can i rate an expert?"
        };

        String solutions[] = {
                "Select your Service and place a call directly to your expert",
                "Drop the expert name and reason for report in our feedback section, and we will let you know about any further progress",
                "Rating an expert will be included in further versions of this application, stay tuned"
        };


        if (NARRATE_FLAG){
            ourSplasSound.stop();
            ourSplasSound =  MediaPlayer.create(HelpCenter.this,R.raw.hire);
            ourSplasSound.start();

        }


        adapter = new HelpCenterAdapter(HelpCenter.this,questions,solutions);
        helpCenterRV.setAdapter(adapter);
        setFeilds(R.drawable.engineers_experts,"Hire","How do i Hire");
        swapSheild.startAnimation(AnimationUtils.loadAnimation(HelpCenter.this,R.anim.abc_slide_in_top));

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                swapSheild.setClickable(true);
                swapSheild.setVisibility(View.VISIBLE);
            }
        },400);


    }









    @Override
    public void onBackPressed() {


        if (swapSheild.getVisibility() == View.VISIBLE){
            swapSheild.startAnimation(AnimationUtils.loadAnimation(HelpCenter.this,R.anim.abc_slide_out_top));
            ourSplasSound.stop();



            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    swapSheild.setClickable(false);
                    swapSheild.setVisibility(View.INVISIBLE);
                }
            },400);
        }else{
            super.onBackPressed();

        }


    }

    @Override
    protected void onPause() {
        super.onPause();

        ourSplasSound.pause();

    }


}

