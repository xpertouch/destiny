package corp.burenz.expertouch.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.util.ArrayList;

import corp.burenz.expertouch.R;
import corp.burenz.expertouch.activities.ExpertDetails;
import corp.burenz.expertouch.databases.Favourites;
import corp.burenz.expertouch.util.MySingleton;

/**
 * Created by xperTouch on 7/16/2016.
 */
public class ExpertCard extends RecyclerView.Adapter<ExpertCard.ExpertVieweHolder> {


    SharedPreferences userData;
    String LOCAL_APP_DATA = "userInformation";
    ArrayList<String> expertCityArray;
    ArrayList<String> expertNames;
    ArrayList<String> expertMainExpertise;
    ArrayList<String> expertCallStatus;
    ArrayList<String> expertSkillsArray;
    ArrayList<String> expertPic;
    ArrayList<String> expertId;
    LinearLayout expertCard;
    Animation cardAnimation;
    NetworkImageView expertAvtarV;
    CircularImageView circularView;


    Context context;



    public ExpertCard(Context context,ArrayList<String> expertId,ArrayList<String> expertPic ,ArrayList<String> expertNames, ArrayList<String> expertMainExpertise,ArrayList<String> expertCityArray,ArrayList<String> expertCallStatus,ArrayList<String> expertSkills){
        this.context = context;

        this.expertNames = expertNames;
        this.expertMainExpertise = expertMainExpertise;
        this.expertCityArray = expertCityArray;
        this.expertCallStatus = expertCallStatus;
        this.expertSkillsArray = expertSkills;
        this.expertPic = expertPic;
        this.expertId = expertId;

    }


    @Override
    public ExpertVieweHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.experts_adapter,parent,false);
        ExpertVieweHolder expertVieweHolder = new ExpertVieweHolder(v);
        return expertVieweHolder;





    }









    // C O D E  H E R E


    @Override
    public void onBindViewHolder(ExpertVieweHolder holder, final int position) {


        final ViewFlipper statusShutetrFlipper;
        final ImageView shutterUp,shutterDown;
        final TextView visitProfile,addExpertOfffline;

        LinearLayout expertCard;


        final TextView expertName,expertExpertise,expertCityV,expertStatus,expertSkills;




        userData = context.getSharedPreferences(LOCAL_APP_DATA,0);
        statusShutetrFlipper = holder.statusShutetrFlipper;
        shutterUp = holder.shutterUp;
        shutterDown = holder.shutterDown;
        visitProfile = holder.visitProfile;

        expertName = holder.expertName;
        expertExpertise = holder.expertExpertise;
        expertCityV = holder.expertExperience;
        expertStatus = holder.expertStatus;
        expertSkills =  holder.expertSkills;
        addExpertOfffline = holder.addExpertOfffline;
        expertAvtarV = holder.expertAvtarV;
        circularView = holder.circularView;
        expertCard = holder.expertCard;

        ImageLoader imageLoader = MySingleton.getInstance(context).getImageLoader();

        cardAnimation = AnimationUtils.loadAnimation(context,R.anim.fab_open);
  //      expertCard.startAnimation(cardAnimation);

        // setting Text


        try{
            expertName.setText(toTitleCase(expertNames.get(position)));
            expertCityV.setText(toTitleCase(expertCityArray.get(position)));


        }catch (Exception e){
            expertName.setText(expertNames.get(position));
            expertExpertise.setText(expertMainExpertise.get(position));

        }


        expertExpertise.setText(expertMainExpertise.get(position));
        expertStatus.setText(expertCallStatus.get(position));
        expertSkills.setText(expertSkillsArray.get(position));



        addExpertOfffline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Favourites favourites = new Favourites(context);
                try{


                    statusShutetrFlipper.showPrevious();
                    shutterUp.setVisibility(View.VISIBLE);

                    favourites.writer();

                    Boolean isAvail = favourites.checkExpertAvailibility(expertId.get(position));
                    if (isAvail){
                    Snackbar.make(v,"Expert Already Exists",Snackbar.LENGTH_LONG).show();
                    }else{
                        if (expertSkillsArray.get(position).length() == 0){
                            expertSkillsArray.add(position,"No skills mentioned");
                        }


                        favourites.insertExpert(expertId.get(position), toTitleCase(expertNames.get(position)), expertMainExpertise.get(position), expertCityArray.get(position), expertCallStatus.get(position), expertSkillsArray.get(position),expertPic.get(position));




                        Snackbar.make(v,"Successfully Added To My Favourites",Snackbar.LENGTH_LONG).show();
                    }

                    favourites.close();

                }catch (Exception e){
                    Snackbar.make(v,"Couldn't add to my Favourites \n" + e,Snackbar.LENGTH_LONG).show();
                }

            }
        });


        expertAvtarV.setImageUrl((String) expertPic.get(position), imageLoader);
        // OnClickListners
        visitProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                statusShutetrFlipper.showPrevious();
                shutterUp.setVisibility(View.VISIBLE);
                visitProfile.setClickable(false);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        visitProfile.setClickable(true);
                    }
                },500);

                final Intent intent = new Intent(context,ExpertDetails.class);
                intent.putExtra("expertName", toTitleCase(expertNames.get(position)));
                intent.putExtra("expertExpertise", expertMainExpertise.get(position));
                intent.putExtra("expertCity", toTitleCase(expertCityArray.get(position)));
                intent.putExtra("expertStatus", expertCallStatus.get(position));
                intent.putExtra("expertPic", expertPic.get(position));
                intent.putExtra("expertSkills", expertSkillsArray.get(position));
                intent.putExtra("expertId", expertId.get(position));

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                context.startActivity(intent);
            }
        },500);


            }
        });



//        expertName.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                shutterDown.setVisibility(View.VISIBLE);
//                statusShutetrFlipper.showNext();
//                shutterUp.setVisibility(View.GONE);
//
//                expertAvtarV.setClickable(false);
//                expertName.setClickable(false);
//                expertExpertise.setClickable(false);
//                expertCityV.setClickable(false);
//            }
//        });
//
//
//
//        expertExpertise.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                shutterDown.setVisibility(View.VISIBLE);
//                statusShutetrFlipper.showNext();
//                shutterUp.setVisibility(View.GONE);
//
//                expertAvtarV.setClickable(false);
//                expertName.setClickable(false);
//                expertExpertise.setClickable(false);
//                expertCityV.setClickable(false);
//            }
//        });
//
//
//        expertCityV.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                shutterDown.setVisibility(View.VISIBLE);
//                statusShutetrFlipper.showNext();
//                shutterUp.setVisibility(View.GONE);
//
//                expertAvtarV.setClickable(false);
//                expertName.setClickable(false);
//                expertExpertise.setClickable(false);
//                expertCityV.setClickable(false);
//            }
//        });
//
//
//
//
//        expertAvtarV.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                shutterDown.setVisibility(View.VISIBLE);
//                statusShutetrFlipper.showNext();
//                shutterUp.setVisibility(View.GONE);
//
//                expertAvtarV.setClickable(false);
//                expertName.setClickable(false);
//                expertExpertise.setClickable(false);
//                expertCityV.setClickable(false);
//
//            }
//        });

        shutterUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shutterDown.setVisibility(View.VISIBLE);
                statusShutetrFlipper.showNext();
                shutterUp.setVisibility(View.GONE);
                expertAvtarV.setClickable(false);
                expertName.setClickable(false);
                expertExpertise.setClickable(false);
                expertCityV.setClickable(false);






            }
        });

        shutterDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shutterDown.setVisibility(View.GONE);
                statusShutetrFlipper.showPrevious();
                shutterUp.setVisibility(View.VISIBLE);


                expertAvtarV.setClickable(true);
                expertName.setClickable(true);
                expertExpertise.setClickable(true);
                expertCityV.setClickable(true);

                Log.e("IDBUG","Expert Id inn expert Card is :" + expertId.get(position));

            }
        });



    }

    public static String toTitleCase(String str) {

        if (str == null) {
            return null;
        }

        boolean space = true;
        StringBuilder builder = new StringBuilder(str);
        final int len = builder.length();

        for (int i = 0; i < len; ++i) {
            char c = builder.charAt(i);
            if (space) {
                if (!Character.isWhitespace(c)) {
                    // Convert to title case and switch out of whitespace mode.
                    builder.setCharAt(i, Character.toTitleCase(c));
                    space = false;
                }
            } else if (Character.isWhitespace(c)) {
                space = true;
            } else {
                builder.setCharAt(i, Character.toLowerCase(c));
            }
        }

        return builder.toString();
    }



    // V I E W S      H E R E


    public static class ExpertVieweHolder extends RecyclerView.ViewHolder {

        ViewFlipper statusShutetrFlipper;
        ImageView shutterUp,shutterDown;
        TextView visitProfile;

        // card textViews

        TextView expertName,expertExpertise,expertExperience,expertSkills,expertStatus,addExpertOfffline;
        NetworkImageView expertAvtarV;
        LinearLayout expertCard;
        CircularImageView circularView;


        public ExpertVieweHolder(View itemView) {
            super(itemView);

            statusShutetrFlipper = (ViewFlipper)itemView.findViewById(R.id.statusShutterFlipper);
            shutterUp = (ImageView)itemView.findViewById(R.id.shutterUpButton);
            shutterDown = (ImageView)itemView.findViewById(R.id.shutterDown);
            visitProfile = (TextView)itemView.findViewById(R.id.visitProfile);
            expertName = (TextView)itemView.findViewById(R.id.expertName);
            expertExpertise = (TextView)itemView.findViewById(R.id.expertExpertise);
            expertExperience = (TextView)itemView.findViewById(R.id.expertExperience);
            expertStatus = (TextView)itemView.findViewById(R.id.expertStatus);
            expertSkills = (TextView)itemView.findViewById(R.id.expertSkills);
            expertCard = (LinearLayout) itemView.findViewById(R.id.expertCard);
            addExpertOfffline = (TextView) itemView.findViewById(R.id.addExpertOffline);
            expertAvtarV = (NetworkImageView) itemView.findViewById(R.id.expertAvtarIV);
            circularView = (CircularImageView) itemView.findViewById(R.id.circularView);

  //          circularView.setBorderColor(R.color.newVector);
    //        circularView.setBorderWidth(10);
//            // Add Shadow with default aram
//            circularView.addShadow();
//            // or with custom param
//            circularView.setShadowRadius(30);
//            circularView.setShadowColor(Color.WHITE);
    circularView.setShadowRadius(50);
            circularView.setShadowColor(Color.RED);


        }
    }

    @Override
    public int getItemCount() {
        return expertId.size();
    }




}
