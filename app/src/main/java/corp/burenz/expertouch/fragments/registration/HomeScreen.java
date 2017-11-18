package corp.burenz.expertouch.fragments.registration;

import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

import corp.burenz.expertouch.R;
import corp.burenz.expertouch.util.GifView;

/**
 * Created by Developer on 6/29/2016.
 */
public class HomeScreen extends Fragment implements View.OnClickListener {

    ViewFlipper textInfoFlipper;
    Button signup,login;
    Typeface logoTypeface;
    GifView gifView;

    String COUNT = "exitCount";
    SharedPreferences isHome;
    SharedPreferences.Editor editor;
    LinearLayout appTitleBanner;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.home_screen,container,false);



        setupVariables(v);

        appTitleBanner.startAnimation(AnimationUtils.loadAnimation(getActivity(),R.anim.abc_fade_in));

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                appTitleBanner.setVisibility(View.VISIBLE);
            }
        },300);
        logoTypeface = Typeface.createFromAsset(getActivity().getAssets(), "fonts/forte.ttf");

        TextView xper = (TextView) v.findViewById(R.id.xperThree);
        TextView touch = (TextView) v.findViewById(R.id.touchThree);

        isHome = getActivity().getSharedPreferences(COUNT,0);
        editor = isHome.edit();

        xper.setTypeface(logoTypeface);
        touch.setTypeface(logoTypeface);


        textInfoFlipper.setInAnimation(getContext(),R.anim.fab_open);
        textInfoFlipper.setFlipInterval(3000);
        textInfoFlipper.startFlipping();


        return v;

    }



    void setupVariables(View v){
        appTitleBanner = (LinearLayout) v.findViewById(R.id.appTitleBanner);

        textInfoFlipper = (ViewFlipper)v.findViewById(R.id.textInfoFlipper);
        signup = (Button)v.findViewById(R.id.registerButton);
        login = (Button)v.findViewById(R.id.loginButton);

        signup.setOnClickListener(this);
        login.setOnClickListener(this);

    }





    @Override
    public void onClick(View v) {

        switch (v.getId()){


            case R.id.registerButton:

                getFragmentManager().beginTransaction().replace(R.id.main_server_frame_layout,new Register()).setCustomAnimations(R.anim.fab_open,R.anim.fab_open).commit();
                editor.putBoolean("isHome",false);
                editor.putInt("count",0);
                editor.apply();

                break;



            case R.id.loginButton:
                getFragmentManager().beginTransaction().replace(R.id.main_server_frame_layout,new LoginFragment())
                        .commit();
                editor.putBoolean("isHome",false);
                editor.putInt("count",0);
                editor.apply();

                break;

        }


    }
}
