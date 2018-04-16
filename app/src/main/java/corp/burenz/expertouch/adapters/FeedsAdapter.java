package corp.burenz.expertouch.adapters;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.android.volley.toolbox.NetworkImageView;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import corp.burenz.expertouch.R;
import corp.burenz.expertouch.activities.Buket;
import corp.burenz.expertouch.activities.ChannelSearchView;
import corp.burenz.expertouch.activities.CompanyProfile;
import corp.burenz.expertouch.activities.Hire;
import corp.burenz.expertouch.activities.MyCompany;
import corp.burenz.expertouch.activities.MyFavouritesActivity;
import corp.burenz.expertouch.activities.OwnChoice;
import corp.burenz.expertouch.activities.Profile;
import corp.burenz.expertouch.activities.RegisterCompany;
import corp.burenz.expertouch.activities.XpertRegistration;
import corp.burenz.expertouch.butter.MySharedConfig;
import corp.burenz.expertouch.databases.CallLogs;
import corp.burenz.expertouch.databases.Favourites;
import corp.burenz.expertouch.util.BannerUtils;
import corp.burenz.expertouch.util.CallPermissions;
import corp.burenz.expertouch.util.MySingleton;

/**
 * Created by xperTouch on 7/11/2016.
 */

public class FeedsAdapter extends RecyclerView.Adapter<FeedsAdapter.FeedsViewHolder> {

    private ArrayList<String> titleArray, subtitleArray, postDateArray, websiteArray, emailArray, banners;
    private ArrayList<String> callArray, slidingBannerURLS;
    private ArrayList<String> postId, companyIDArray;
    private Context context;
    private SharedPreferences userData;
    private View myView;
    private String LOCAL_APP_DATA = "userInformation";

    private SliderLayout sliderLayout;


    private Animation animation;
    private RecyclerView jobCountsR;
    private RecyclerView.Adapter adapter;
    private String[] finalCount;

    public FeedsAdapter(Context context, ArrayList<String> title, ArrayList<String> subtitle, ArrayList<String> postDate, ArrayList<String> callArray, ArrayList<String> websiteArray, ArrayList<String> emailArray,ArrayList<String> banners, ArrayList<String> postId, ArrayList<String> companyIDArray) {

        this.context            = context;
        this.titleArray         = title;
        this.subtitleArray      = subtitle;
        this.postDateArray      = postDate;
        this.callArray          = callArray;
        this.websiteArray       = websiteArray;
        this.emailArray         = emailArray;
        this.banners            = banners;
        this.postId             = postId;
        this.companyIDArray     = companyIDArray;

    }





    public  class FeedsViewHolder extends RecyclerView.ViewHolder {

        TextView            titleTextView, subtitleTextView, textViewDate,showMeMoreTV;
        ImageView           callFeed, mailFeed, visitFeed, shareFeed,companyProfile,saveForOffline;
        ImageView           mainFrame;
        ViewFlipper         newsFeedsFlpper, switchFeeds;
        CardView            cardView, recommendedCapsulrInjection,hirePannelIALL;
        LinearLayout        sliderPannelLL, bottomPannelHolder;
        RecyclerView        jobCountsR;
        final ViewFlipper   offlineFlipper;
        SliderLayout        sliderShow;
        ImageLoader         imageLoader;
        RecyclerView        hireprofessionlsRV;
        HireProfessionalsAdapter hireProfessionalsAdapter;

        DefaultSliderView hireSlide, bucketSlide, offlineUsageSlide, registerCompanySide, registerExpertSlide, slide6;

        public FeedsViewHolder(View itemView) {
            super(itemView);
            sliderPannelLL  = (LinearLayout)        itemView.findViewById(R.id.sliderPannleLL);
            titleTextView   = (TextView)            itemView.findViewById(R.id.textViewTitle);
            mainFrame       = (ImageView)           itemView.findViewById(R.id.imageViewBirthday);
            newsFeedsFlpper = (ViewFlipper)         itemView.findViewById(R.id.newsFeedsFlipper);
            switchFeeds     = (ViewFlipper)         itemView.findViewById(R.id.switchFeeds);
            callFeed        = (ImageView)           itemView.findViewById(R.id.callFeed);
            mailFeed        = (ImageView)           itemView.findViewById(R.id.mailFeed);
            visitFeed       = (ImageView)           itemView.findViewById(R.id.visitFeed);
            shareFeed       = (ImageView)           itemView.findViewById(R.id.shareFeed);
            textViewDate    = (TextView)            itemView.findViewById(R.id.textViewDate);
            companyProfile  = (ImageView)           itemView.findViewById(R.id.takeMeToProfile);
            cardView        = (CardView)            itemView.findViewById(R.id.cardView);
            jobCountsR      = (RecyclerView)        itemView.findViewById(R.id.jobCountsR);
            saveForOffline  = (ImageView)           itemView.findViewById(R.id.saveForOffline);
            offlineFlipper  = (ViewFlipper)         itemView.findViewById(R.id.offlineFlipper);
            sliderShow      = (SliderLayout)        itemView.findViewById(R.id.slider);
            hirePannelIALL  = (CardView)            itemView.findViewById(R.id.hirePannelIALL);
            hireprofessionlsRV = (RecyclerView)     itemView.findViewById(R.id.hireprofessionlsRV);
            bottomPannelHolder = (LinearLayout)     itemView.findViewById(R.id.bottomPannelHolder);
//            jobContainerCard   = (CardView)         itemView.findViewById(R.id.jobContainerCard);


            hireSlide             = new DefaultSliderView(context);
            bucketSlide           = new DefaultSliderView(context);
            offlineUsageSlide     = new DefaultSliderView(context);
            registerCompanySide   = new DefaultSliderView(context);
            registerExpertSlide   = new DefaultSliderView(context);
            slide6                = new DefaultSliderView(context);


            hireProfessionalsAdapter = new HireProfessionalsAdapter(context);
            hireprofessionlsRV.setLayoutManager(new GridLayoutManager(context, 14));
            hireprofessionlsRV.setAdapter(hireProfessionalsAdapter);
            ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context).build();
            com.nostra13.universalimageloader.core.ImageLoader.getInstance().init(config);
            imageLoader   = com.nostra13.universalimageloader.core.ImageLoader.getInstance(); // Get singleton instance

            /*recommendedCapsulrInjection =   (CardView) itemView.findViewById(R.id.recommended_capsule_injection);
            showMeMoreTV    =   (TextView)itemView.findViewById(R.id.show_me_more_textview);
*/
        }
    }


    @Override
    public FeedsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.newfeeds_layout, parent, false);
        FeedsViewHolder feedsViewHolder = new FeedsViewHolder(v);
        return feedsViewHolder;
    }

    @Override
    public void onBindViewHolder(final FeedsViewHolder holder, final int position) {
        TextView titleTextView, subtitleTextView, postDateTextView;
        final ImageView  callFeed, mailFeed, visitFeed, shareFeed, verifiedImage,companyProfile;
        LinearLayout sliderPannelLL;
        ImageView mainFrame;

        final Dialog dialog = new Dialog(context);
        final ViewFlipper newsFeedsFlpper, switchFeeds;
        final TextView verifiedTitle, verifiedSubtitle;
        CardView cardView;
        final ListView jobCounts;
        ImageView saveForOffline;
        SliderLayout sliderShow;



        final ViewFlipper offlineFlipper;

        try {
            String add = subtitleArray.get(holder.getAdapterPosition());
            finalCount = add.split("3xt3");

        }catch (IndexOutOfBoundsException e){
            Log.e("INDEX ","STUCK OUT OF BOUND ");
        }



        animation = AnimationUtils.loadAnimation(context,R.anim.card_animation);


        userData = context.getSharedPreferences(LOCAL_APP_DATA, 0);


        titleTextView       = holder.titleTextView;
        subtitleTextView    = holder.subtitleTextView;
        mainFrame           = holder.mainFrame;
        switchFeeds         = holder.switchFeeds;
        newsFeedsFlpper     = holder.newsFeedsFlpper;
        companyProfile      = holder.companyProfile;
        sliderPannelLL      = holder.sliderPannelLL;
        jobCountsR          = holder.jobCountsR;
        callFeed            = holder.callFeed;
        shareFeed           = holder.shareFeed;
        visitFeed           = holder.visitFeed;
        mailFeed            = holder.mailFeed;
        cardView            = holder.cardView;
        postDateTextView    = holder.textViewDate;
        saveForOffline      = holder.saveForOffline;
        offlineFlipper      = holder.offlineFlipper;
        sliderShow          = holder.sliderShow;
        this.sliderLayout   = sliderShow;


        cardView.startAnimation(animation);

//        adapter = new AddCounts(context,finalCount,true,);
        adapter =   new AddCounts(context, finalCount, true, postId,postId.get(position));

        jobCountsR.setLayoutManager(new LinearLayoutManager(context));
        jobCountsR.hasFixedSize();
        jobCountsR.setAdapter(adapter);


         /*seeting the capsule injection visibility to visible at place 3*/


 /*        if(titleArray.size() > 4){
             if(position == 2){

                 cardView.startAnimation(animation);
                 holder.recommendedCapsulrInjection.setVisibility(View.VISIBLE);
                 holder.recommendedCapsulrInjection.startAnimation(animation);

             }else{
                 holder.recommendedCapsulrInjection.setVisibility(View.GONE);

             }

         }
*/

      /*   holder.showMeMoreTV.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 context.startActivity(new Intent(context, ChannelSearchView.class));
             }
         });*/



        /*set company banners inside*/

/*
        if (position == 3){
            subtitleArray.add(3,"https://images.milled.com/2018-02-16/EUyiLA3xiY7bQON4/7lWo8lKi-7v0.jpgsetThis3x2x1");
        }
*/

        if (subtitleArray.get(position).contains("setthis3x2x1")){

                 try {
                     Picasso.with(context).load(subtitleArray.get(position).split("setthis3x2x1")[1].toLowerCase()).into(holder.mainFrame);
                     Log.e("bannerrr",subtitleArray.get(position).split("setthis3x2x1")[1]);
                     holder.bottomPannelHolder.setVisibility(View.GONE);

                 }catch (Exception e){
                     e.printStackTrace();
                     holder.cardView.setVisibility(View.GONE);
                     holder.bottomPannelHolder.setVisibility(View.GONE);

                 }


        }else {

            holder.cardView.setVisibility(View.VISIBLE);
            Picasso.with(context).load(banners.get(position)).into(holder.mainFrame);
            holder.bottomPannelHolder.setVisibility(View.VISIBLE);

        }

        /*only banner s and nothing else else*/


        final Activity activity = (Activity) context;
        if (position == 0){
            sliderPannelLL.setVisibility(View.VISIBLE);
            holder.hirePannelIALL.setVisibility(View.VISIBLE);


            SharedPreferences sharedPreferences = context.getSharedPreferences(MySharedConfig.BannerPrefs.BANNER_PREF,0);
            if (sharedPreferences.getBoolean(MySharedConfig.BannerPrefs.IS_CUSTOM_BOOLEAN,false)){

                BannerUtils.BannerMethods bannerUtils = new BannerUtils(). new BannerMethods(context);

                holder.hireSlide           .image(bannerUtils.getBanner1());
                holder.bucketSlide         .image(bannerUtils.getBanner2());
                holder.offlineUsageSlide   .image(bannerUtils.getBanner3());
                holder.registerCompanySide .image(bannerUtils.getBanner4());
//                holder.registerExpertSlide .image(bannerUtils.getBanner5());
//                holder.slide6              .image(bannerUtils.getBanner6());


                Log.e("Banners",  bannerUtils.getBanner1() + bannerUtils.getBanner2() + bannerUtils.getBanner3() + bannerUtils.getBanner4() + bannerUtils.getBanner5());

            }else{

                Log.e("Banner","inside deaafult banner");

                holder.hireSlide           .image(R.raw.banner_one);
                holder.bucketSlide         .image(R.raw.banner_two);
                holder.offlineUsageSlide   .image(R.raw.banner_three);
                holder.registerCompanySide .image(R.raw.banner_four);
//                holder.registerExpertSlide .image(R.raw.banner_five);
//                holder.slide6              .image(R.raw.banner_six);



                holder.hireSlide.setOnSliderClickListener(new BaseSliderView.OnSliderClickListener() {
                    @Override
                    public void onSliderClick(BaseSliderView slider) {
                        activity.startActivity(new Intent(context,Buket.class).putExtra("type","food"));
                        activity.overridePendingTransition(R.anim.fadein_scan,R.anim.fadeout_scan);

                    }
                });


                holder.bucketSlide.setOnSliderClickListener(new BaseSliderView.OnSliderClickListener() {
                    @Override
                    public void onSliderClick(BaseSliderView slider) {
                        activity.startActivity(new Intent(context,Buket.class).putExtra("type","offer"));
                        activity.overridePendingTransition(R.anim.fadein_scan,R.anim.fadeout_scan);

                    }
                });




                holder.offlineUsageSlide.setOnSliderClickListener(new BaseSliderView.OnSliderClickListener() {
                    @Override
                    public void onSliderClick(BaseSliderView slider) {
                        activity.startActivity(new Intent(context,Buket.class).putExtra("type","education"));
                        activity.overridePendingTransition(R.anim.fadein_scan,R.anim.fadeout_scan);

                    }
                });



                holder.registerCompanySide.setOnSliderClickListener(new BaseSliderView.OnSliderClickListener() {
                    @Override
                    public void onSliderClick(BaseSliderView slider) {


                        activity.startActivity(new Intent(context,Buket.class).putExtra("type","depart"));

                        activity.overridePendingTransition(R.anim.fadein_scan,R.anim.fadeout_scan);


                    /*    userData = context.getSharedPreferences(LOCAL_APP_DATA,0);

                        if (userData.getBoolean("VERIFIED",false)){


                            if(userData.getBoolean("EXPERT",false)){

                                activity.startActivity(new Intent(context,Profile.class));
                                activity.overridePendingTransition(R.anim.fadein_scan,R.anim.fadeout_scan);


                            }else if (userData.getBoolean("COMPANY",false)){

                                activity.startActivity(new Intent(context,MyCompany.class));
                                activity.overridePendingTransition(R.anim.fadein_scan,R.anim.fadeout_scan);


                            }else{

                                activity.startActivity(new Intent(context,RegisterCompany.class));
                                activity.overridePendingTransition(R.anim.fadein_scan,R.anim.fadeout_scan);

                            }

                        }
                        else {

                            activity.startActivity(new Intent(context,OwnChoice.class));
                            activity.overridePendingTransition(R.anim.fadein_scan,R.anim.fadeout_scan);

                        }*/


                    }
                });


                holder.registerExpertSlide.setOnSliderClickListener(new BaseSliderView.OnSliderClickListener() {
                    @Override
                    public void onSliderClick(BaseSliderView slider) {
                       /* userData = context.getSharedPreferences(LOCAL_APP_DATA,0);

                        if (userData.getBoolean("VERIFIED",false)){


                            if(userData.getBoolean("EXPERT",false)){

                                activity.startActivity(new Intent(context,Profile.class));
                                activity.overridePendingTransition(R.anim.fadein_scan,R.anim.fadeout_scan);


                            }else if (userData.getBoolean("COMPANY",false)){

                                activity.startActivity(new Intent(context,MyCompany.class));
                                activity.overridePendingTransition(R.anim.fadein_scan,R.anim.fadeout_scan);


                            }else{

                                activity.startActivity(new Intent(context,XpertRegistration.class));
                                activity.overridePendingTransition(R.anim.fadein_scan,R.anim.fadeout_scan);

                            }

                        }
                        else {

                            activity.startActivity(new Intent(context,OwnChoice.class));
                            activity.overridePendingTransition(R.anim.fadein_scan,R.anim.fadeout_scan);

                        }
*/

                        activity.startActivity(new Intent(context,Buket.class).putExtra("type",""));
                        activity.overridePendingTransition(R.anim.fadein_scan,R.anim.fadeout_scan);

                    }
                });





            }

            sliderShow.addSlider(holder.hireSlide);
            sliderShow.addSlider(holder.bucketSlide);
            sliderShow.addSlider(holder.offlineUsageSlide);
            sliderShow.addSlider(holder.registerCompanySide);
//            sliderShow.addSlider(holder.registerExpertSlide);
//            sliderShow.addSlider(holder.slide6);



            sliderShow.setPresetTransformer(SliderLayout.Transformer.ZoomOutSlide);
            sliderShow.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);









        }else{
            sliderPannelLL.setVisibility(View.GONE);
            holder.hirePannelIALL.setVisibility(View.GONE);
        }

        final int sharePosition = holder.getAdapterPosition();
        companyProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.startActivity(new Intent(context, CompanyProfile.class).putExtra("companyID",companyIDArray.get(position)));
                activity.overridePendingTransition(R.anim.fadein_scan,R.anim.fadeout_scan);

            }
        });

        callFeed.setOnClickListener(new View.OnClickListener() {


            Dialog dialog = new Dialog(context);
            Button cancelVerify, callVerify, iUnderStand;

            @Override
            public void onClick(View v) {

                boolean verificationStatus;
                //setting Dialog View
                verificationStatus = userData.getBoolean("VERIFIED", false);
                if (verificationStatus) {
//                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setContentView(R.layout.verified_user);
                    cancelVerify = (Button) dialog.findViewById(R.id.cancelVerified);
                    callVerify = (Button)dialog.findViewById(R.id.callVerified);

                    callVerify.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(Intent.ACTION_CALL);
                            intent.setData(Uri.parse("tel:" + callArray.get(position).toString()));
                            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                                // TODO: Consider calling
                                //    ActivityCompat#requestPermissions
                                // here to request the missing permissions, and then overriding
                                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                //                                          int[] grantResults)
                                // to handle the case where the user grants the permission. See the documentation
                                // for ActivityCompat#requestPermissions for more details.
                                context.startActivity(new Intent(context,CallPermissions.class).putExtra("callIt",callArray.get(position).toString()));
                                return;
                            }
                            context.startActivity(intent);

                            CallLogs callLogs = new CallLogs(context);
                            try{
                                callLogs.writer();
                                callLogs.updateCompanyCall(titleArray.get(position).toString(),java.text.DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime()),callArray.get(position).toString());
                                callLogs.close();

                            }catch (Exception e){
                                e.printStackTrace();
                            }
                            dialog.cancel();



                        }
                    });

                    cancelVerify.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.cancel();
                        }
                    });




                } else {
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setContentView(R.layout.not_verified_user);
                    iUnderStand = (Button) dialog.findViewById(R.id.iUnderstand);
                    iUnderStand.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.cancel();
                        }
                    });

                }


                dialog.show();
//
//                boolean verificationStatus;
//                verificationStatus = userData.getBoolean("VERIFIED",false);
//                verifiedImage.setImageResource(R.drawable.verified_user);
//                verifiedTitle.setText("You are a Verified User");
//                verifiedSubtitle.setText("You are authorised to call,share,visit and mail this advertiser");
//                cancelVerify.setText("CANCEL");
//                callVerify.setText("CALL");
//
//                callVerify.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        Toast.makeText(context, "Calling Man please Wait", Toast.LENGTH_SHORT).show();
//                    }
//                });
//                cancelVerify.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        dialog.cancel();
//                    }
//                });
//
//
//
//                dialog.show();

            }



        });

        saveForOffline.setOnClickListener(new View.OnClickListener() {



            boolean didItWork;
            String error = "";
            @Override
            public void onClick(View v) {
                myView = v;
                Favourites favourites  = new Favourites(context);
                try {
                        favourites.writer();

                    boolean isAvailable =  favourites.checkAvailibility(subtitleArray.get(position).toString());

                    if (isAvailable){

                        Toast.makeText(context, "Add Already exists in My Favourites", Toast.LENGTH_SHORT).show();

                    }else {
                        String current_date = "Saved on : " + DateFormat.getDateTimeInstance().format(new Date());


                        favourites.insertCompany(titleArray.get(position).toString(),current_date,subtitleArray.get(position).toString(),callArray.get(position).toString(),websiteArray.get(position).toString(),emailArray.get(position).toString(),banners.get(holder.getAdapterPosition()).toString());

                        Toast.makeText(context, "Successfully Added to My Favourites", Toast.LENGTH_SHORT).show();

                    }
                        favourites.close();
                        didItWork = true;
                }catch (Exception e){

                    didItWork = false;
                    error = error.toString();

                }finally {
                    if (didItWork){

                        offlineFlipper.showNext();
                    }else{
                        Toast.makeText(context, "Couldn't add to My Favourites", Toast.LENGTH_SHORT).show();
                    }


                }


            }
        });



        mailFeed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean verificationStatus;

                final Dialog dialog = new Dialog(context);
                Button iUnderStand;
                verificationStatus = userData.getBoolean("VERIFIED",false);
                if (verificationStatus){

                    Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                            "mailto",emailArray.get(position).toString(), null));
                    emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Advertisement Feedback");
                    emailIntent.putExtra(Intent.EXTRA_TEXT, "Hi there, i am sending this email with reference to an advertisement posted on 1clickAway as i wanted to know that... ");
                    context.startActivity(Intent.createChooser(emailIntent, "Send an Email via..."));


                } else {
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setContentView(R.layout.not_verified_user);
                    iUnderStand = (Button) dialog.findViewById(R.id.iUnderstand);
                    iUnderStand.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.cancel();
                        }
                    });


                    dialog.show();

                }



            }
        });

        visitFeed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean verificationStatus;
                Button iUnderStand;
                final Dialog dialog = new Dialog(context);
                verificationStatus = userData.getBoolean("VERIFIED",false);

                if (verificationStatus){

                    ConnectivityManager cManager = (ConnectivityManager) context.getSystemService(context.CONNECTIVITY_SERVICE);
                    NetworkInfo nInfo = cManager.getActiveNetworkInfo();
                    if(nInfo!=null && nInfo.isConnected()) {
                        Intent i = new Intent(Intent.ACTION_VIEW);
                        i.setData(Uri.parse(websiteArray.get(position).toString()));
                        context.startActivity(i);
                    }
                    else {
                        Toast.makeText(context, "No Internet Connection", Toast.LENGTH_SHORT).show();
                    }


                }else {
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setContentView(R.layout.not_verified_user);
                    iUnderStand = (Button) dialog.findViewById(R.id.iUnderstand);
                    iUnderStand.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.cancel();
                        }
                    });

                    dialog.show();
                }


            }
        });

        shareFeed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean verificationStatus;
                Button iUnderStand;
                final Dialog dialog = new Dialog(context);
                verificationStatus = userData.getBoolean("VERIFIED",false);
                if (verificationStatus){


                    String advert = "Shared via 1clickAway, Find Best Jobs, Experts and Offers from your City and State. Click on the below link to Download Now\nhttps://play.google.com/store/apps/details?id=corp.burenz.expertouch";
                    String firstString = "Hey i am sharing with you an advertisement from" +
                                " \n"+ titleArray.get(position).toString() +" posted "+postDateArray.get(position).toString()+"" +
                                " where they mentioned "+subtitleArray.get(position).toString().replace("3xt3","\n")+"." +
                                " Here are their contact Details\nPhone:"+callArray.get(position).toString()+"\nEmail:"+emailArray.get(position).toString()+"";
                        Intent shareIntent = new Intent();
                        shareIntent.setAction(Intent.ACTION_SEND);
                        shareIntent.setType("text/plain");
                        shareIntent.putExtra(Intent.EXTRA_TEXT, "" + firstString + "\n\n" + advert);
                        context.startActivity(Intent.createChooser(shareIntent, "Share via..."));




                } else {
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setContentView(R.layout.not_verified_user);
                    iUnderStand = (Button) dialog.findViewById(R.id.iUnderstand);
                    iUnderStand.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.cancel();
                        }
                    });

                    dialog.show();
                }



            }
        });


        titleTextView.setText(titleArray.get(position));
        postDateTextView.setText(postDateArray.get(position));



        switchFeeds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                newsFeedsFlpper.showNext();
                switchFeeds.showNext();

            }
        });

    }

    @Override
    public int getItemCount() {

        return titleArray.size();
    }



}