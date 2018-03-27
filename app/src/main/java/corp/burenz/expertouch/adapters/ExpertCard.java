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

import com.mikhaellopez.circularimageview.CircularImageView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.ArrayList;

import corp.burenz.expertouch.R;
import corp.burenz.expertouch.activities.ExpertDetails;
import corp.burenz.expertouch.databases.Favourites;
import corp.burenz.expertouch.util.MySingleton;

/**
 * Created by xperTouch on 7/16/2016.
 */
public class ExpertCard extends RecyclerView.Adapter<ExpertCard.ExpertVieweHolder> {


    private SharedPreferences userData;
    private String LOCAL_APP_DATA = "userInformation";
    private ArrayList<String> expertCityArray;
    private ArrayList<String> expertNames;
    private ArrayList<String> expertMainExpertise;
    private ArrayList<String> expertCallStatus;
    private ArrayList<String> expertSkillsArray;
    private ArrayList<String> expertPic;
    private ArrayList<String> expertId;
    private LinearLayout expertCard;
    private Animation cardAnimation;
    private ImageView expertAvtarV;
    private CircularImageView circularView;


    Context context;



    public ExpertCard(Context context,ArrayList<String> expertId,ArrayList<String> expertPic ,ArrayList<String> expertNames, ArrayList<String> expertMainExpertise,ArrayList<String> expertCityArray,ArrayList<String> expertCallStatus,ArrayList<String> expertSkills){
        this.context = context;

        this.expertNames            = expertNames;
        this.expertMainExpertise    = expertMainExpertise;
        this.expertCityArray        = expertCityArray;
        this.expertCallStatus       = expertCallStatus;
        this.expertSkillsArray      = expertSkills;
        this.expertPic              = expertPic;
        this.expertId               = expertId;

    }


    @Override
    public ExpertVieweHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.experts_adapter,parent,false);
        ExpertVieweHolder expertVieweHolder = new ExpertVieweHolder(v);
        return expertVieweHolder;





    }









    // C O D E  H E R E


    @Override
    public void onBindViewHolder(final ExpertVieweHolder holder, final int position) {
        userData = context.getSharedPreferences(LOCAL_APP_DATA,0);

        cardAnimation = AnimationUtils.loadAnimation(context,R.anim.fab_open);


        try{
            holder.expertName.setText(toTitleCase(expertNames.get(position)));
            holder.expertCityV.setText(toTitleCase(expertCityArray.get(position)));


        }catch (Exception e){
            holder.expertName.setText(expertNames.get(position));
            holder.expertExpertise.setText(expertMainExpertise.get(position));

        }


        holder.expertExpertise.setText(expertMainExpertise.get(position));
        holder.expertStatus.setText(expertCallStatus.get(position));
        holder.expertSkills.setText(expertSkillsArray.get(position));



        holder.addExpertOfffline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Favourites favourites = new Favourites(context);
                try{


                    holder.statusShutetrFlipper.showPrevious();
                    holder.shutterUp.setVisibility(View.VISIBLE);

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



        holder.imageLoader.displayImage(expertPic.get(position),holder.expertAvtarV);

        // OnClickListners
        holder.visitProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.statusShutetrFlipper .showPrevious();
                holder.shutterUp            .setVisibility(View.VISIBLE);
                holder.visitProfile.setClickable(false);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        holder.visitProfile.setClickable(true);
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


        holder.shutterUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.shutterDown.setVisibility(View.VISIBLE);
                holder.statusShutetrFlipper.showNext();
                holder.shutterUp.setVisibility(View.GONE);
                holder.expertAvtarV.setClickable(false);
                holder.expertName.setClickable(false);
                holder.expertExpertise.setClickable(false);
                holder.expertCityV.setClickable(false);
            }
        });

        holder.shutterDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.shutterDown.setVisibility(View.GONE);
                holder.statusShutetrFlipper.showPrevious();
                holder.shutterUp.setVisibility(View.VISIBLE);


                holder.expertAvtarV.setClickable(true);
                holder.expertName.setClickable(true);
                holder.expertExpertise.setClickable(true);
                holder.expertCityV.setClickable(true);

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


    public  class ExpertVieweHolder extends RecyclerView.ViewHolder {

        ViewFlipper statusShutetrFlipper;
        ImageView shutterUp,shutterDown;
        TextView visitProfile;
        ImageLoader imageLoader;

        // card textViews

        TextView expertName,expertExpertise,expertCityV,expertSkills,expertStatus,addExpertOfffline;
        ImageView expertAvtarV;
        LinearLayout expertCard;
        CircularImageView circularView;



        public ExpertVieweHolder(View itemView) {
            super(itemView);

            statusShutetrFlipper    = (ViewFlipper)itemView.findViewById(R.id.statusShutterFlipper);
            shutterUp               = (ImageView)itemView.findViewById(R.id.shutterUpButton);
            shutterDown             = (ImageView)itemView.findViewById(R.id.shutterDown);
            visitProfile            = (TextView)itemView.findViewById(R.id.visitProfile);
            expertName              = (TextView)itemView.findViewById(R.id.expertName);
            expertExpertise         = (TextView)itemView.findViewById(R.id.expertExpertise);
            expertCityV             = (TextView)itemView.findViewById(R.id.expertExperience);
            expertStatus            = (TextView)itemView.findViewById(R.id.expertStatus);
            expertSkills            = (TextView)itemView.findViewById(R.id.expertSkills);
            expertCard              = (LinearLayout) itemView.findViewById(R.id.expertCard);
            addExpertOfffline       = (TextView) itemView.findViewById(R.id.addExpertOffline);
            expertAvtarV            = (ImageView) itemView.findViewById(R.id.expertAvtarIV);
            circularView            = (CircularImageView) itemView.findViewById(R.id.circularView);

            ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context).build();
            com.nostra13.universalimageloader.core.ImageLoader.getInstance().init(config);
            imageLoader   = com.nostra13.universalimageloader.core.ImageLoader.getInstance(); // Get singleton instance

            circularView.setShadowRadius(50);
            circularView.setShadowColor(Color.RED);


        }
    }

    @Override
    public int getItemCount() {
        return expertId.size();
    }

}