package corp.burenz.expertouch.adapters;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ViewFlipper;

import corp.burenz.expertouch.R;

/**
 * Created by xperTouch on 7/15/2016.
 */
public class MyProfileAdapt extends RecyclerView.Adapter<MyProfileAdapt.ProfileViewholder> {


    Context context;
    ConnectivityManager connectivityManager;
    NetworkInfo networkInfo;
    boolean connectionStatus;

    public MyProfileAdapt(Context context){

        this.context = context;

    }

 //   Returen False when Valley Resumes Network in checkConnection



    @Override
    public ProfileViewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v  = LayoutInflater.from(parent.getContext()).inflate(R.layout.finally_profile,parent,false);
        ProfileViewholder  profileViewholder = new ProfileViewholder(v);
        return profileViewholder;
    }



    @Override
    public void onBindViewHolder(ProfileViewholder holder, int position) {

        ImageView editFlipInfo,editFlipStatus,editFlipLocation,editFlipExperience,editFlipBio,editFlipCall,editFlipemail,editFlipSkill;
        final ViewFlipper infoFlipper,statusFlipepr,locationFlipper,experienceFlipper,bioFlipper,callFlipper,emailFlipper,skillFlipper;

        final  ViewFlipper infoPannelFlipper,statusPannelFlipper,locationPannelFlipper,experiencePannelFlipper,bioPannelFlipper,callPannelFlipper,emailPannelFlipper,skillPannelFlipper;
        Button saveInfoBtn,saveStatusBtn,saveLocationBtn,saveExperienceBtn,saveBioBtn,saveCallBtn,saveEmailBtn,saveSkillButton;
        Button cancelInfoBtn,cancelStatusBtn,cancelLocationBtn,cancelExperienceBtn,cancelBioBtn,cancelCallBtn,cancelEmailBtn,cancelSkillButton;
        RecyclerView skilledRV;
        RecyclerView.Adapter adapter;


        String skillLists;
        skillLists = "skill1,skill2,skill3,skill4,skill5";
        final String skillArray[] = skillLists.split(",");


        connectivityManager = (ConnectivityManager)  context.getSystemService(Context.CONNECTIVITY_SERVICE);
        networkInfo = connectivityManager.getActiveNetworkInfo();

        infoFlipper = holder.infoFlipper;
        statusFlipepr = holder.statusFlipepr;
        locationFlipper = holder.locationFlipper;
        experienceFlipper = holder.experienceFlipper;
        bioFlipper = holder.bioFlipper;
        callFlipper = holder.callFlipper;
        emailFlipper = holder.emailFlipper;
        skillFlipper = holder.skillFlipper;
        // ImageViews

        editFlipInfo = holder.editFlipInfo;
        editFlipStatus = holder.editFlipStatus;
        editFlipLocation = holder.editFlipLocation;
        editFlipExperience = holder.editFlipExperience;
        editFlipBio = holder.editFlipBio;
        editFlipCall = holder.editFlipCall;
        editFlipemail = holder.editFlipemail;
        editFlipSkill = holder.editFlipSkill;


        //BottomFlliper

        infoPannelFlipper  = holder.infoPannelFlipper;
        statusPannelFlipper = holder.statusPannelFlipper;
        locationPannelFlipper = holder.locationPannelFlipper;
        experiencePannelFlipper = holder.experiencePannelFlipper;
        bioPannelFlipper = holder.bioPannelFlipper;
        callPannelFlipper = holder.callPannelFlipper;
        emailPannelFlipper = holder.emailPannelFlipper;
        skillPannelFlipper = holder.skillPannelFlipper;


        // Buttons
        //cancel Buttons

        cancelInfoBtn = holder.cancelInfoBtn;
        cancelStatusBtn = holder.cancelStatusBtn;
        cancelLocationBtn = holder.cancelLocationBtn;
        cancelExperienceBtn = holder.cancelExperienceBtn;
        cancelBioBtn = holder.cancelBioBtn;
        cancelCallBtn = holder.cancelCallBtn;
        cancelEmailBtn = holder.cancelEmailBtn;
        cancelSkillButton = holder.cancelSkillButton;
        // saveButtons

        saveInfoBtn = holder.saveInfoBtn;
        saveStatusBtn = holder.saveStatusBtn;
        saveLocationBtn = holder.saveLocationBtn;
        saveExperienceBtn = holder.saveExperienceBtn;
        saveBioBtn = holder.saveBioBtn;
        saveCallBtn = holder.saveCallBtn;
        saveEmailBtn = holder.saveEmailBtn;
        saveSkillButton = holder.saveSkillButton;
        skilledRV = holder.skilledRV;


        // recyclerView
 //       adapter = new SkillsAdapter(skillArray);
//        skilledRV.setLayoutManager(new LinearLayoutManager(context));

        // skilledRV.setAdapter(adapter);











        // EDIT IMAGE BUTTON LAYOUT FLIPPERS

        editFlipInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                infoFlipper.showNext();
                infoPannelFlipper.showNext();
            }
        });

        editFlipStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                statusPannelFlipper.showNext();
                statusFlipepr.showNext();
            }
        });

        editFlipLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                locationPannelFlipper.showNext();
                locationFlipper.showNext();
            }
        });
        editFlipExperience.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                experienceFlipper.showNext();
                experiencePannelFlipper.showNext();
            }
        });
        editFlipBio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bioPannelFlipper.showNext();
                bioFlipper.showNext();
            }
        });
        editFlipCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callPannelFlipper.showNext();
                callFlipper.showNext();
            }
        });
        editFlipemail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                emailPannelFlipper.showNext();
                emailFlipper.showNext();
            }
        });
        editFlipSkill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                skillPannelFlipper.showNext();
                skillFlipper.showNext();
            }
        });




        // CANCEL BUTTONS

        cancelInfoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                infoPannelFlipper.showNext();
                infoFlipper.showNext();
            }
        });

        cancelStatusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                statusPannelFlipper.showNext();
                statusFlipepr.showNext();
            }
        });

        cancelLocationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                locationPannelFlipper.showNext();
                locationFlipper.showNext();
            }
        });

        cancelExperienceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                experiencePannelFlipper.showNext();
                experienceFlipper.showNext();
            }
        });
        cancelBioBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bioPannelFlipper.showNext();
                bioFlipper.showNext();
            }
        });
        cancelCallBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callPannelFlipper.showNext();
                callFlipper.showNext();
            }
        });
        cancelEmailBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                emailPannelFlipper.showNext();
                emailFlipper.showNext();
            }
        });


        cancelInfoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                infoPannelFlipper.showNext();
                infoFlipper.showNext();
            }
        });

        cancelStatusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                statusPannelFlipper.showNext();
                statusFlipepr.showNext();
            }
        });

        cancelLocationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                locationPannelFlipper.showNext();
                locationFlipper.showNext();
            }
        });

        cancelExperienceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                experiencePannelFlipper.showNext();
                experienceFlipper.showNext();
            }
        });
        cancelBioBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bioPannelFlipper.showNext();
                bioFlipper.showNext();
            }
        });
        cancelCallBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callPannelFlipper.showNext();
                callFlipper.showNext();
            }
        });
        cancelEmailBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                emailPannelFlipper.showNext();
                emailFlipper.showNext();
            }
        });
        cancelSkillButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                skillPannelFlipper.showNext();
                skillFlipper.showNext();
            }
        });






        //SAVE BUTTONS

        saveInfoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                connectionStatus = checkConnection();

                if (connectionStatus){

                    infoPannelFlipper.showNext();
                    infoFlipper.showNext();
                }else {
                    Snackbar.make(v,"No Internet Connection Found",Snackbar.LENGTH_LONG).show();
                }

            }
        });

        saveStatusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                connectionStatus = checkConnection();

                if (connectionStatus){

                    statusFlipepr.showNext();
                    statusPannelFlipper.showNext();
                }else {
                    Snackbar.make(v,"No Internet Connection Found",Snackbar.LENGTH_LONG).show();
                }
            }
        });

        saveLocationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                connectionStatus = checkConnection();

                if (connectionStatus){

                    locationFlipper.showNext();
                    locationPannelFlipper.showNext();
                }else {
                    Snackbar.make(v,"No Internet Connection Found",Snackbar.LENGTH_LONG).show();
                }
            }
        });

        saveExperienceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                connectionStatus = checkConnection();

                if (connectionStatus){

                    experienceFlipper.showNext();
                    experiencePannelFlipper.showNext();
                }else {
                    Snackbar.make(v,"No Internet Connection Found",Snackbar.LENGTH_LONG).show();
                }
            }
        });

        saveBioBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                connectionStatus = checkConnection();

                if (connectionStatus){

                    bioFlipper.showNext();
                    bioPannelFlipper.showNext();
                }else {
                    Snackbar.make(v,"No Internet Connection Found",Snackbar.LENGTH_LONG).show();
                }
            }
        });

        saveCallBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                connectionStatus = checkConnection();

                if (connectionStatus){

                    callFlipper.showNext();
                    callPannelFlipper.showNext();
                }else {
                    Snackbar.make(v,"No Internet Connection Found",Snackbar.LENGTH_LONG).show();
                }
            }
        });

        saveEmailBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                connectionStatus = checkConnection();

                if (connectionStatus){

                    emailFlipper.showNext();
                    emailPannelFlipper.showNext();
                }else {
                    Snackbar.make(v,"No Internet Connection Found",Snackbar.LENGTH_LONG).show();
                }
            }
        });


        saveSkillButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                connectionStatus = checkConnection();

                if (connectionStatus){

                    skillFlipper.showNext();
                    skillPannelFlipper.showNext();

                } else {

                    Snackbar.make(v,"No Internet Connection Found",Snackbar.LENGTH_LONG).show();

                }
            }
        });


    }


    boolean checkConnection(){

        connectivityManager = (ConnectivityManager)  context.getSystemService(Context.CONNECTIVITY_SERVICE);
        networkInfo = connectivityManager.getActiveNetworkInfo();

        if(networkInfo !=null && networkInfo.isConnected()){
            return true;

        }
        // Returen False when Valley Resumes Network in checkConnection
        else return true;

    }

    @Override
    public int getItemCount() {
        return 1;
    }

    public static class ProfileViewholder extends RecyclerView.ViewHolder {

        ViewFlipper infoFlipper,statusFlipepr,locationFlipper,experienceFlipper,bioFlipper,callFlipper,emailFlipper,skillFlipper;
        ImageView editFlipInfo,editFlipStatus,editFlipLocation,editFlipExperience,editFlipBio,editFlipCall,editFlipemail,editFlipSkill;
        ViewFlipper infoPannelFlipper,statusPannelFlipper,locationPannelFlipper,experiencePannelFlipper,bioPannelFlipper,callPannelFlipper,emailPannelFlipper,skillPannelFlipper;
        Button saveInfoBtn,saveStatusBtn,saveLocationBtn,saveExperienceBtn,saveBioBtn,saveCallBtn,saveEmailBtn,saveSkillButton;
        Button cancelInfoBtn,cancelStatusBtn,cancelLocationBtn,cancelExperienceBtn,cancelBioBtn,cancelCallBtn,cancelEmailBtn,cancelSkillButton;
        RecyclerView skilledRV;


        public ProfileViewholder(View itemView) {
            super(itemView);

            // View Flippers

            infoFlipper = (ViewFlipper)itemView.findViewById(R.id.infoFlipper);
            statusFlipepr = (ViewFlipper)itemView.findViewById(R.id.statusFlipper);
            locationFlipper = (ViewFlipper)itemView.findViewById(R.id.locationFlipper);
            experienceFlipper  =(ViewFlipper)itemView.findViewById(R.id.experienceFlipper);
            bioFlipper = (ViewFlipper)itemView.findViewById(R.id.bioFlipper);
            callFlipper = (ViewFlipper)itemView.findViewById(R.id.callFlipper);
            emailFlipper = (ViewFlipper)itemView.findViewById(R.id.emailFlipper);
            skillFlipper  = (ViewFlipper)itemView.findViewById(R.id.skillFlipper);


            //ImageView

            editFlipInfo = (ImageView)itemView.findViewById(R.id.editFlipInfo);
            editFlipStatus = (ImageView)itemView.findViewById(R.id.editFlipStatus);
            editFlipLocation = (ImageView)itemView.findViewById(R.id.editFlipAddress);
            editFlipExperience = (ImageView)itemView.findViewById(R.id.editFlipExperience);
            editFlipBio = (ImageView)itemView.findViewById(R.id.editFlipBio);
            editFlipCall = (ImageView)itemView.findViewById(R.id.editFlipCall);
            editFlipemail = (ImageView)itemView.findViewById(R.id.editFlipEmail);
            editFlipSkill = (ImageView)itemView.findViewById(R.id.editFlipSkill);

            //PannelFlipper

            infoPannelFlipper = (ViewFlipper)itemView.findViewById(R.id.infoPannelFlipper);
            statusPannelFlipper = (ViewFlipper)itemView.findViewById(R.id.statusPannelFlipper);
            locationPannelFlipper = (ViewFlipper)itemView.findViewById(R.id.addressPannelFlipper);
            experiencePannelFlipper = (ViewFlipper)itemView.findViewById(R.id.experiencePannelFlipper);
            bioPannelFlipper = (ViewFlipper)itemView.findViewById(R.id.bioPannelFlipper);
            callPannelFlipper = (ViewFlipper)itemView.findViewById(R.id.callPannelFlipper);
            emailPannelFlipper = (ViewFlipper)itemView.findViewById(R.id.emailPannelFlipper);
            skillPannelFlipper = (ViewFlipper)itemView.findViewById(R.id.skillPannelFlipper);


            // save buttons

            saveInfoBtn = (Button)itemView.findViewById(R.id.saveInfoBT);
            saveStatusBtn = (Button)itemView.findViewById(R.id.saveStatusBtn);
            saveLocationBtn = (Button)itemView.findViewById(R.id.saveLocationBtn);
            saveExperienceBtn = (Button)itemView.findViewById(R.id.saveExperienceBtn);
            saveBioBtn = (Button)itemView.findViewById(R.id.saveBioBtn);
            saveCallBtn = (Button)itemView.findViewById(R.id.saveCallBtn);
            saveEmailBtn = (Button)itemView.findViewById(R.id.saveEmailBtn);
            saveSkillButton = (Button)itemView.findViewById(R.id.saveSkillButton);


            // cancel Buttons

            cancelInfoBtn = (Button)itemView.findViewById(R.id.cancelInfoBtn);
            cancelStatusBtn = (Button)itemView.findViewById(R.id.cancelStatusBtn);
            cancelLocationBtn = (Button)itemView.findViewById(R.id.cancelLocationBtn);
            cancelExperienceBtn = (Button)itemView.findViewById(R.id.cancelExperienceBtn);
            cancelBioBtn = (Button)itemView.findViewById(R.id.cancelBioBtn);
            cancelCallBtn = (Button)itemView.findViewById(R.id.cancelCallBtn);
            cancelEmailBtn = (Button)itemView.findViewById(R.id.cancelEmailBtn);
            cancelSkillButton = (Button)itemView.findViewById(R.id.cancelSkillButton);
            // Recycler View
            skilledRV = (RecyclerView)itemView.findViewById(R.id.skilledRV);

        }
    }
}
