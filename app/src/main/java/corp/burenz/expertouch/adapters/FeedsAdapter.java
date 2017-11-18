package corp.burenz.expertouch.adapters;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.CardView;
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
import android.widget.ViewFlipper;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;

import java.util.ArrayList;
import java.util.Calendar;

import corp.burenz.expertouch.R;
import corp.burenz.expertouch.activities.Buket;
import corp.burenz.expertouch.activities.CompanyProfile;
import corp.burenz.expertouch.activities.Hire;
import corp.burenz.expertouch.activities.MyCompany;
import corp.burenz.expertouch.activities.MyFavouritesActivity;
import corp.burenz.expertouch.activities.OwnChoice;
import corp.burenz.expertouch.activities.Profile;
import corp.burenz.expertouch.activities.RegisterCompany;
import corp.burenz.expertouch.activities.XpertRegistration;
import corp.burenz.expertouch.databases.CallLogs;
import corp.burenz.expertouch.databases.Favourites;
import corp.burenz.expertouch.util.CallPermissions;
import corp.burenz.expertouch.util.MySingleton;

/**
 * Created by xperTouch on 7/11/2016.
 */

public class FeedsAdapter extends RecyclerView.Adapter<FeedsAdapter.FeedsViewHolder> {

    ArrayList<String> titleArray, subtitleArray, postDateArray, websiteArray, emailArray, banners;
    ArrayList<String> callArray;
    Context context;
    SharedPreferences userData;
    View myView;
    String LOCAL_APP_DATA = "userInformation";


    Intent companyProfileIntent;
    Animation animation;
    RecyclerView jobCountsR;
    RecyclerView.Adapter adapter;
    String[] finalCount;

    public FeedsAdapter(Context context, ArrayList<String> title, ArrayList<String> subtitle, ArrayList<String> postDate, ArrayList<String> callArray, ArrayList<String> websiteArray, ArrayList<String> emailArray,ArrayList<String> banners) {

        this.context = context;
        this.titleArray = title;
        this.subtitleArray = subtitle;
        this.postDateArray = postDate;
        this.callArray = callArray;
        this.websiteArray = websiteArray;
        this.emailArray = emailArray;
        this.banners = banners;

    }


    public static class FeedsViewHolder extends RecyclerView.ViewHolder {

        TextView titleTextView, subtitleTextView, textViewDate;
        ImageView callFeed, mailFeed, visitFeed, shareFeed,companyProfile,saveForOffline;
        NetworkImageView mainFrame;
        ViewFlipper newsFeedsFlpper, switchFeeds;
        CardView cardView;
        LinearLayout sliderPannelLL;
        RecyclerView jobCountsR;
        final ViewFlipper offlineFlipper;
        SliderLayout sliderShow;

        public FeedsViewHolder(View itemView) {
            super(itemView);
            sliderPannelLL = (LinearLayout) itemView.findViewById(R.id.sliderPannleLL);
            titleTextView = (TextView) itemView.findViewById(R.id.textViewTitle);
            mainFrame = (NetworkImageView) itemView.findViewById(R.id.imageViewBirthday);
            newsFeedsFlpper = (ViewFlipper) itemView.findViewById(R.id.newsFeedsFlipper);
            switchFeeds = (ViewFlipper) itemView.findViewById(R.id.switchFeeds);
            callFeed = (ImageView) itemView.findViewById(R.id.callFeed);
            mailFeed = (ImageView) itemView.findViewById(R.id.mailFeed);
            visitFeed = (ImageView) itemView.findViewById(R.id.visitFeed);
            shareFeed = (ImageView) itemView.findViewById(R.id.shareFeed);
            textViewDate = (TextView) itemView.findViewById(R.id.textViewDate);
            companyProfile = (ImageView) itemView.findViewById(R.id.takeMeToProfile);
            cardView =(CardView)itemView.findViewById(R.id.cardView);
            jobCountsR = (RecyclerView)itemView.findViewById(R.id.jobCountsR);
            saveForOffline = (ImageView) itemView.findViewById(R.id.saveForOffline);
            offlineFlipper = (ViewFlipper)itemView.findViewById(R.id.offlineFlipper);
            sliderShow = (SliderLayout) itemView.findViewById(R.id.slider);
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
        NetworkImageView mainFrame;
        final Dialog dialog = new Dialog(context);
        final ViewFlipper newsFeedsFlpper, switchFeeds;
        final TextView verifiedTitle, verifiedSubtitle;
        CardView cardView;
        final ListView jobCounts;
        ImageView saveForOffline;
        SliderLayout sliderShow;


        ImageLoader imageLoader = MySingleton.getInstance(context).getImageLoader();

        final ViewFlipper offlineFlipper;

    try {
        String add = subtitleArray.get(holder.getAdapterPosition()).toString();
        finalCount = add.split("3xt3");

    }catch (IndexOutOfBoundsException e){
        Log.e("INDEX ","STUCK OUT OF BOUND ");
    }



        animation = AnimationUtils.loadAnimation(context,R.anim.card_animation);


        userData = context.getSharedPreferences(LOCAL_APP_DATA, 0);


        titleTextView = holder.titleTextView;
        subtitleTextView = holder.subtitleTextView;
        mainFrame = holder.mainFrame;
        switchFeeds = holder.switchFeeds;
        newsFeedsFlpper = holder.newsFeedsFlpper;
        companyProfile = holder.companyProfile;
        sliderPannelLL = holder.sliderPannelLL;
        jobCountsR = holder.jobCountsR;
        callFeed = holder.callFeed;
        shareFeed = holder.shareFeed;
        visitFeed = holder.visitFeed;
        mailFeed = holder.mailFeed;
        cardView = holder.cardView;
        postDateTextView = holder.textViewDate;
        saveForOffline = holder.saveForOffline;
        offlineFlipper = holder.offlineFlipper;
        sliderShow = holder.sliderShow;

        companyProfileIntent = new Intent(context,CompanyProfile.class);

        cardView.startAnimation(animation);

        adapter = new AddCounts(finalCount);
        jobCountsR.setLayoutManager(new LinearLayoutManager(context));
        jobCountsR.hasFixedSize();
        jobCountsR.setAdapter(adapter);

        if (titleArray.get(position).toString().equals("Microsoft")){
            offlineFlipper.showNext();


        }



        if (position == 0){
            sliderPannelLL.setVisibility(View.VISIBLE);

            DefaultSliderView hireSlide = new DefaultSliderView(context);
            hireSlide
                    .image(R.raw.banner_one);

            sliderShow.addSlider(hireSlide);




            DefaultSliderView bucketSlide = new DefaultSliderView(context);
            bucketSlide
                    .image(R.raw.banner_two);
            sliderShow.addSlider(bucketSlide);

            DefaultSliderView offlineUsageSlide = new DefaultSliderView(context);
            offlineUsageSlide
                    .image(R.raw.banner_three);


            sliderShow.addSlider(offlineUsageSlide);


            DefaultSliderView registerCompanySide = new DefaultSliderView(context);
            registerCompanySide
                    .image(R.raw.banner_four);


            sliderShow.addSlider(registerCompanySide);


            DefaultSliderView registerExpertSlide = new DefaultSliderView(context);
            registerExpertSlide
                    .image(R.raw.banner_five);


            sliderShow.addSlider(registerExpertSlide);


            DefaultSliderView slide6 = new DefaultSliderView(context);
            slide6
                    .image(R.raw.banner_six);


            sliderShow.addSlider(slide6);



            sliderShow.setPresetTransformer(SliderLayout.Transformer.ZoomOutSlide);
            sliderShow.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);



            hireSlide.setOnSliderClickListener(new BaseSliderView.OnSliderClickListener() {
                @Override
                public void onSliderClick(BaseSliderView slider) {
                    context.startActivity(new Intent(context,Hire.class));
                }
            });


            bucketSlide.setOnSliderClickListener(new BaseSliderView.OnSliderClickListener() {
                @Override
                public void onSliderClick(BaseSliderView slider) {
                    context.startActivity(new Intent(context,Buket.class));
                }
            });




            offlineUsageSlide.setOnSliderClickListener(new BaseSliderView.OnSliderClickListener() {
                @Override
                public void onSliderClick(BaseSliderView slider) {
                    context.startActivity(new Intent(context,MyFavouritesActivity.class));
                }
            });



            registerCompanySide.setOnSliderClickListener(new BaseSliderView.OnSliderClickListener() {
                @Override
                public void onSliderClick(BaseSliderView slider) {


                    userData = context.getSharedPreferences(LOCAL_APP_DATA,0);

                    if (userData.getBoolean("VERIFIED",false)){


                        if(userData.getBoolean("EXPERT",false)){

                            context.startActivity(new Intent(context,Profile.class));

                        }else if (userData.getBoolean("COMPANY",false)){

                            context.startActivity(new Intent(context,MyCompany.class));

                        }else{

                            context.startActivity(new Intent(context,RegisterCompany.class));
                        }

                    }
                    else {

                        context.startActivity(new Intent(context,OwnChoice.class));
                    }


                }
            });


            registerExpertSlide.setOnSliderClickListener(new BaseSliderView.OnSliderClickListener() {
                @Override
                public void onSliderClick(BaseSliderView slider) {


                    userData = context.getSharedPreferences(LOCAL_APP_DATA,0);

                    if (userData.getBoolean("VERIFIED",false)){


                        if(userData.getBoolean("EXPERT",false)){

                            context.startActivity(new Intent(context,Profile.class));

                        }else if (userData.getBoolean("COMPANY",false)){

                            context.startActivity(new Intent(context,MyCompany.class));

                        }else{

                            context.startActivity(new Intent(context,XpertRegistration.class));
                        }

                    }
                    else {

                        context.startActivity(new Intent(context,OwnChoice.class));
                    }

                }
            });

//            offlineUsageSlide.setOnSliderClickListener(new BaseSliderView.OnSliderClickListener() {
//                @Override
//                public void onSliderClick(BaseSliderView slider) {
//                    context.startActivity(new Intent(context,MyFavouritesActivity.class));
//                }
//            });







        }else{
            sliderPannelLL.setVisibility(View.GONE);

        }

        final int sharePosition = holder.getAdapterPosition();
        companyProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                companyProfileIntent.putExtra("companyName",titleArray.get(sharePosition).toString());
                companyProfileIntent.putExtra("companyState",websiteArray.get(sharePosition).toString());
                companyProfileIntent.putExtra("companyBanner",banners.get(holder.getAdapterPosition()).toString());
                context.startActivity(companyProfileIntent);
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
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
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

                        Snackbar snackbar = Snackbar.make(v, "Add Already exists in My Favourites", Snackbar.LENGTH_LONG)
                                .setAction("Action", null);
                        View sbView = snackbar.getView();
                        TextView tv = (TextView)sbView.findViewById(android.support.design.R.id.snackbar_text);
                        tv.setGravity(Gravity.CENTER);
                        tv.setTextColor(Color.WHITE);
                        sbView.setBackgroundColor(Color.RED);
                        snackbar.show();

                    }else {

                        favourites.insertCompany(titleArray.get(position).toString(),postDateArray.get(position).toString(),subtitleArray.get(position).toString(),callArray.get(position).toString(),websiteArray.get(position).toString(),emailArray.get(position).toString(),banners.get(holder.getAdapterPosition()).toString());

                        Snackbar.make(v,"Successfully Added to My Favourites",Snackbar.LENGTH_LONG).show();
//                        Snackbar snackbar = Snackbar.make(v, "Successfully Added To My Favourites", Snackbar.LENGTH_LONG)
//                                .setAction("Action", null);
//                        View sbView = snackbar.getView();
//                        TextView tv = (TextView)sbView.findViewById(android.support.design.R.id.snackbar_text);
//                        tv.setGravity(Gravity.CENTER);
//                        tv.setTextColor(Color.WHITE);
//                        sbView.setBackgroundColor(Color.GREEN);
//                        snackbar.show();

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
                        Snackbar.make(v,"Couldn't add to My Favourites",Snackbar.LENGTH_LONG).show();
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
                        Snackbar.make(v,"No Internet Connection",Snackbar.LENGTH_SHORT).show();
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
                                " \n"+ titleArray.get(position).toString() +" posted on "+postDateArray.get(position).toString()+"" +
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


        titleTextView.setText(titleArray.get(position).toString());

        mainFrame.setImageUrl((String) banners.get(position), imageLoader);
        postDateTextView.setText(postDateArray.get(position).toString());

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