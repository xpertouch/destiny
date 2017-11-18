package corp.burenz.expertouch.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import corp.burenz.expertouch.R;
import corp.burenz.expertouch.activities.ExpertDetails;
import corp.burenz.expertouch.databases.Favourites;
import corp.burenz.expertouch.util.MySingleton;

/**
 * Created by xperTouch on 8/15/2016.
 */
public class ExpertFavAdapter extends RecyclerView.Adapter<ExpertFavAdapter.MyExpertFavHolder> {

    Context context;

    String[] expertName,expertExpertise,expertStatus,expertSkills,expertExperience,expertPic,expertId;
    public ExpertFavAdapter(Context context,String[] expertId,String[] expertName,String[] expertExpertise,String[] expertExperience,String []expertStatus,String []expertSkills,String []expertPic){

        this.context = context;
        this.expertName = expertName;
        this.expertExpertise = expertExpertise;
        this.expertStatus = expertStatus;
        this.expertSkills = expertSkills;
        this.expertExperience = expertExperience;
        this.expertPic = expertPic;
        this.expertId = expertId;
    }

    @Override
    public MyExpertFavHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.experts_fav_adapter,parent,false);
        MyExpertFavHolder myExpertFavHolder = new MyExpertFavHolder(v);
        return  myExpertFavHolder;

    }

    @Override
    public void onBindViewHolder(MyExpertFavHolder holder, final int position) {

        final TextView favExpertName,favExpertExpertise,favExpertStatus,favExpertSkills,favExpertExperience;
        final TextView deleteFavExpert,visitFavExpert;
        final ImageView favShutterUp,favShutterDown;
        final CardView favEXpertCard;
        NetworkImageView offlineAvtarIV;

        final ViewFlipper favPannnelFlipper;

        final LinearLayout emptyExperts;

        favExpertName = holder.favExpertName;
        favExpertExperience = holder.favExpertExperience;
        favExpertExpertise = holder.favExpertExpertise;
        favExpertStatus = holder.favExpertStatus;
        favExpertSkills = holder.favExpertSkills;

        deleteFavExpert = holder.deleteFavExpert;
        visitFavExpert = holder.visitFavExpert;
        favShutterDown = holder.favShutterDown;
        favShutterUp = holder.favShutterUp;
        favEXpertCard = holder.favExpertCard;
        favPannnelFlipper = holder.favPannnelFlipper;
        emptyExperts = holder.emptyExperts;

        offlineAvtarIV = holder.offlineAvtarIV;



        ImageLoader imageLoader = MySingleton.getInstance(context).getImageLoader();
        offlineAvtarIV.setImageUrl((String) expertPic[position], imageLoader);


        favExpertName.setText(toTitleCase(expertName[position]));
        favExpertExpertise.setText(expertExpertise[position]);
        favExpertExperience.setText(toTitleCase(expertExperience[position]));
        favExpertSkills.setText(expertSkills[position]);
        favExpertStatus.setText(expertStatus[position]);

        if(expertName[0].equals("")){
            emptyExperts.setVisibility(View.VISIBLE);
            favEXpertCard.setVisibility(View.GONE);
        }

//        else{
//            emptyExperts.setVisibility(View.GONE);
 //            favEXpertCard.setVisibility(View.VISIBLE);
//
//        }




        deleteFavExpert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {

                try {

                    Favourites favourites = new Favourites(context);
                    favourites.writer();
                    favourites.deleteExpert(expertId[position]);
                    String[] check  = favourites.getExpertName().split(" \n");
                    favourites.close();


                    favPannnelFlipper.showPrevious();

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            favEXpertCard.startAnimation(AnimationUtils.loadAnimation(context,R.anim.fab_close));
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {

                                    favEXpertCard.setVisibility(View.GONE);

                                }
                            },350);

                            Snackbar.make(v,"Expert Removed Successfully",Snackbar.LENGTH_LONG).show();

                        }
                    },500);

                    favourites.close();

                    if(check[0].equals("")){

                        favEXpertCard.setVisibility(View.GONE);

                        emptyExperts.startAnimation(AnimationUtils.loadAnimation(context,R.anim.card_animation));
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                emptyExperts.setVisibility(View.VISIBLE);
                            }
                        },350);

                    }

                }catch (Exception e){

                    Snackbar.make(v,"Something went Wrong" + e,Snackbar.LENGTH_INDEFINITE).show();
                    Log.e("wqwq",e.toString());

                }






            }
        });

        final Intent intent = new Intent(context,ExpertDetails.class);

        visitFavExpert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                favShutterUp.setVisibility(View.VISIBLE);
                favShutterDown.setVisibility(View.GONE);
                favPannnelFlipper.showPrevious();
                visitFavExpert.setClickable(false);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        visitFavExpert.setClickable(true);
                    }
                },500);


                intent.putExtra("expertName",toTitleCase(expertName[position]));
                intent.putExtra("expertExpertise",expertExpertise[position]);
                intent.putExtra("expertCity",toTitleCase(expertExperience[position]));
                intent.putExtra("expertStatus",expertStatus[position]);
                intent.putExtra("expertPic",expertPic[position]);
                intent.putExtra("expertSkills",expertSkills[position]);
				intent.putExtra("expertId",expertId[position]);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        context.startActivity(intent);

                    }
                },500);



            }




        });







        favShutterUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                favShutterUp.setVisibility(View.GONE);
                favShutterDown.setVisibility(View.VISIBLE);
                favPannnelFlipper.showNext();

                Log.e("IDBUG"," Expert id here : "+expertId[position]);


            }
        });


        favShutterDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                favShutterUp.setVisibility(View.VISIBLE);
                favShutterDown.setVisibility(View.GONE);
                favPannnelFlipper.showPrevious();

                Log.e("IDBUG"," Expert id here : "+expertId[position]);

            }
        });





    }

    @Override
    public int getItemCount() {
        return expertName.length;
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



    public static class MyExpertFavHolder extends RecyclerView.ViewHolder {

        TextView favExpertName,favExpertExpertise,favExpertStatus,favExpertSkills,favExpertExperience;
        TextView deleteFavExpert,visitFavExpert;
        ImageView favShutterUp,favShutterDown;
        CardView favExpertCard;
        ViewFlipper favPannnelFlipper;
        LinearLayout emptyExperts;
        NetworkImageView offlineAvtarIV;
        public MyExpertFavHolder(View v) {
            super(v);

            favExpertName = (TextView)v.findViewById(R.id.favExpertName);
            favExpertExpertise = (TextView) v.findViewById(R.id.favEXpertExpertise);

            offlineAvtarIV = (NetworkImageView)v.findViewById(R.id.offlineAvtarIV);

            favExpertExperience = (TextView)v.findViewById(R.id.favExpertExperience);

            favExpertStatus = (TextView) v.findViewById(R.id.favExpertStatus);
            favExpertSkills = (TextView) v.findViewById(R.id.favExpertSkills);

            deleteFavExpert = (TextView) v.findViewById(R.id.deleteFavExpert);
            visitFavExpert = (TextView) v.findViewById(R.id.visitFavExpert);

            favShutterUp = (ImageView)v.findViewById(R.id.favShutterUp);
            favShutterDown  = (ImageView) v.findViewById(R.id.favShutterDown);

            favPannnelFlipper = (ViewFlipper)v.findViewById(R.id.favPannelFlipper);

            favExpertCard = (CardView)v.findViewById(R.id.favExpertCard);


            emptyExperts = (LinearLayout)v.findViewById(R.id.emptyExperts);


        }
    }
}
