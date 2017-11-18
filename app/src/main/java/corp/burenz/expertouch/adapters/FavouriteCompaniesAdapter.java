package corp.burenz.expertouch.adapters;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

import java.util.Calendar;

import corp.burenz.expertouch.R;
import corp.burenz.expertouch.activities.CompanyProfile;
import corp.burenz.expertouch.databases.CallLogs;
import corp.burenz.expertouch.databases.Favourites;
import corp.burenz.expertouch.util.CallPermissions;

/**
 * Created by xperTouch on 8/12/2016.
 */
public class FavouriteCompaniesAdapter extends RecyclerView.Adapter<FavouriteCompaniesAdapter.MyFavourteAdds> {

    Context context;

    String []companyTitles;
    String []postDate;
    String []jobInfo;
    String []companyPhone;
    String []companyVisit;
    String []companyMail;
    String []companyBanner;



    public FavouriteCompaniesAdapter(Context context,String[] companyTitles,String[] postDate,String[] jobInfo,String[] companyPhone,String[] companyVisit, String[] companyMail, String[] companyBanner){

        this.companyTitles = companyTitles;
        this.postDate = postDate;
        this.jobInfo = jobInfo;
        this.companyPhone = companyPhone;
        this.companyVisit = companyVisit;
        this.companyMail = companyMail;
        this.context = context;
        this.companyBanner = companyBanner;

    }

    @Override
    public MyFavourteAdds onCreateViewHolder(ViewGroup parent, int viewType) {
        View v  = LayoutInflater.from(context).inflate(R.layout.favourite_companies_adapter,parent,false);
        MyFavourteAdds favourteAdds = new MyFavourteAdds(v);
        return favourteAdds;
    }

    @Override
    public void onBindViewHolder(final MyFavourteAdds holder, final int position) {

        final TextView companyFTitle,companyFDate;
        final RecyclerView jobCountFc;
        final RecyclerView.Adapter infoCount;
        String LOCAL_APP_DATA = "userInformation";


        ImageView takeMeToProfile;

        final ImageView openCase,closeCase,removeFromCompany,callFeedFav,mailFeedFav,shareFeedFav;
        final CardView addCardView;
        final ViewFlipper switchFeedsFC,favouritesFlipper;
        final LinearLayout nothingHere;
        final SharedPreferences userData;
        userData = context.getSharedPreferences(LOCAL_APP_DATA,0);



        companyFTitle = holder.companyFTitle;
        companyFDate = holder.companyFDate;
        jobCountFc = holder.jobCountFc;
        openCase = holder.openCase;
        closeCase = holder.closeCase;
        addCardView = holder.addCardView;
        switchFeedsFC = holder.switchFeedsFC;
        favouritesFlipper = holder.favouritesFlipper;
        removeFromCompany= holder.removeFromCompany;
        nothingHere = holder.nothingHere;
        callFeedFav = holder.callFeedFav;
        mailFeedFav = holder.mailFeedFav;
        shareFeedFav = holder.shareFeedFav;
        takeMeToProfile = holder.takeMeToProfile;


        int count;
        if (companyTitles[0].equals("")){
            count = 0;
        }else{
            count  = 1;
            nothingHere.setVisibility(View.GONE);
        }
        if (count == 0){

            addCardView.setVisibility(View.GONE);
            nothingHere.setVisibility(View.VISIBLE);
        }


        openCase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                favouritesFlipper.showNext();
                switchFeedsFC.showNext();

            }
        });

        closeCase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                favouritesFlipper.showPrevious();
                switchFeedsFC.showNext();
            }
        });
        String[] add = jobInfo[position].split("3xt3");
        infoCount = new AddCounts(add);



        takeMeToProfile.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent companyProfileIntent = new Intent(context, CompanyProfile.class);
                companyProfileIntent.putExtra("companyName",companyTitles[holder.getAdapterPosition()]);
                companyProfileIntent.putExtra("companyEmail",companyMail[holder.getAdapterPosition()]);
                companyProfileIntent.putExtra("companyPhone",companyPhone[holder.getAdapterPosition()]);
                companyProfileIntent.putExtra("companyState",companyVisit[holder.getAdapterPosition()]);
                companyProfileIntent.putExtra("companyBanner",companyBanner[holder.getAdapterPosition()]);
                context.startActivity(companyProfileIntent);
            }
        });


        mailFeedFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean verificationStatus;

                final Dialog dialog = new Dialog(context);
                Button iUnderStand;
                verificationStatus = userData.getBoolean("VERIFIED",false);
                if (verificationStatus){

                    Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                            "mailto",companyMail[position], null));
                    emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Advertisement Feedback");
                    emailIntent.putExtra(Intent.EXTRA_TEXT, "Hi there, i am sending this email with reference to an advertisement posted on xpertouch as i wanted to know that... ");
                    context.startActivity(Intent.createChooser(emailIntent, "Send an Email via..."));


                } else { dialog.setContentView(R.layout.not_verified_user);
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




        callFeedFav.setOnClickListener(new View.OnClickListener() {


            Dialog dialog = new Dialog(context);
            Button cancelVerify, callVerify, iUnderStand;




            @Override
            public void onClick(View v) {

                boolean verificationStatus;
                //setting Dialog View
                verificationStatus = userData.getBoolean("VERIFIED", false);
                if (verificationStatus) {
                    dialog.setContentView(R.layout.verified_user);
                    cancelVerify = (Button) dialog.findViewById(R.id.cancelVerified);
                    callVerify = (Button)dialog.findViewById(R.id.callVerified);

                    callVerify.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(Intent.ACTION_CALL);
                            intent.setData(Uri.parse("tel:" + companyPhone[position]));
                            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                                // TODO: Consider calling
                                //    ActivityCompat#requestPermissions
                                // here to request the missing permissions, and then overriding
                                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                //                                          int[] grantResults)
                                // to handle the case where the user grants the permission. See the documentation
                                // for ActivityCompat#requestPermissions for more details.
                                context.startActivity(new Intent(context,CallPermissions.class).putExtra("callIt",companyPhone[position]));
                                return;
                            }
                            context.startActivity(intent);

                            CallLogs callLogs = new CallLogs(context);
                            try{
                                callLogs.writer();
                                callLogs.updateCompanyCall(companyTitles[position],java.text.DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime()),companyPhone[position]);
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


            }



        });


        removeFromCompany.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                try {

                    Favourites favourites = new Favourites(context);
                    favourites.writer();
                    favourites.deletePost(jobInfo[position]);
                    String test[] = favourites.getCompanyTitle().split("\n");
                    favourites.close();

                    addCardView.startAnimation(AnimationUtils.loadAnimation(context,R.anim.fab_close));

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            addCardView.setVisibility(View.GONE);

                        }
                    },350);

                    if (test[0].equals("")){
                        addCardView.setVisibility(View.GONE);
                        nothingHere.setVisibility(View.VISIBLE);
                    }

                    Snackbar.make(v,"Advertisement removed from My Favourites",Snackbar.LENGTH_LONG).show();

                }catch (Exception e){

                    Snackbar.make(v,"Something went Wrong ",Snackbar.LENGTH_LONG).show();

                }




            }
        });


        companyFTitle.setText(companyTitles[position]);
        companyFDate.setText(postDate[position]);






        jobCountFc.setLayoutManager(new LinearLayoutManager(context));

        jobCountFc.setAdapter(infoCount);






        shareFeedFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean verificationStatus;
                Button iUnderStand;
                final Dialog dialog = new Dialog(context);
                verificationStatus = userData.getBoolean("VERIFIED",false);
                if (verificationStatus){

                    String advert = "Shared via xperTouch, hire and get Hired ";
                    String firstString = "Hey i am sharing with you an advertisement from" +
                            " \n"+ companyTitles[position] +" posted on "+postDate[position]+"" +
                            " where they mentioned "+jobInfo[position].replace("3xt3","\n")+"." +
                            " Here are their contact Details\nPhone:"+companyPhone[position]+"\nEmail:"+companyMail[position]+"";
                    Intent shareIntent = new Intent();
                    shareIntent.setAction(Intent.ACTION_SEND);
                    shareIntent.setType("text/plain");
                    shareIntent.putExtra(Intent.EXTRA_TEXT, "" + firstString + "\n\n" + advert);
                    context.startActivity(Intent.createChooser(shareIntent, "Share via..."));




                } else { dialog.setContentView(R.layout.not_verified_user);
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




    }

    @Override
    public int getItemCount() {
        return companyTitles.length;

    }


    public static class MyFavourteAdds extends RecyclerView.ViewHolder {



        TextView companyFTitle,companyFDate;
        RecyclerView jobCountFc;
        ImageView openCase,closeCase,removeFromCompany,callFeedFav,mailFeedFav,shareFeedFav;
        ViewFlipper switchFeedsFC,favouritesFlipper;
        CardView addCardView;
        LinearLayout nothingHere;

        ImageView takeMeToProfile;

        public MyFavourteAdds(View v) {
            super(v);



            companyFTitle = (TextView) v.findViewById(R.id.companyFTitle);
            companyFDate = (TextView) v.findViewById(R.id.companyFDate);

            jobCountFc = (RecyclerView) v.findViewById(R.id.jobCountFc);

            openCase = (ImageView)v.findViewById(R.id.openCase);
            closeCase = (ImageView) v.findViewById(R.id.closeCase);

            switchFeedsFC = (ViewFlipper) v.findViewById(R.id.switchFeedsFC);
            favouritesFlipper = (ViewFlipper) v.findViewById(R.id.favouritesFlipper);

            takeMeToProfile = (ImageView) v.findViewById(R.id.takeMeToProfile);

            removeFromCompany = (ImageView)v.findViewById(R.id.removeFromCompany);
            addCardView = (CardView)v.findViewById(R.id.addCardView);

            nothingHere = (LinearLayout)v.findViewById(R.id.nothingHere);


            callFeedFav = (ImageView) v.findViewById(R.id.callFeedFav);
            mailFeedFav = (ImageView) v.findViewById(R.id.mailFeedFav);
            shareFeedFav = (ImageView) v.findViewById(R.id.shareFeedFav);




        }


    }
}
