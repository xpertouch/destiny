package corp.burenz.expertouch.activities;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.github.florent37.materialviewpager.MaterialViewPager;
import com.github.florent37.materialviewpager.header.HeaderDesign;

import corp.burenz.expertouch.R;
import corp.burenz.expertouch.fragments.bucket.EducationBucket;
import corp.burenz.expertouch.fragments.bucket.Food;
import corp.burenz.expertouch.fragments.bucket.Health;
import corp.burenz.expertouch.fragments.bucket.Offers;
import corp.burenz.expertouch.fragments.bucket.Products;

public class Buket extends AppCompatActivity {

    private MaterialViewPager mViewPager;
    Boolean fromNotifications  = false;
    String fromType = "food";


    @Override
    public void onBackPressed() {
        super.onBackPressed();

        overridePendingTransition(R.anim.fadein_scan,R.anim.fadeout_scan);

    }


    public void checkForNotification(final MaterialViewPager mViewPager, boolean fromNotifications, final String fromType){

        if (!fromNotifications){ return;}

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mViewPager.getViewPager().setCurrentItem(resolveNotificationPosition(fromType));
            }
        },500);
    }


    /*returns the page to be set*/
    public int resolveNotificationPosition(String offerType){

        switch (offerType.trim()){

            case "food":
                return 0;

            case "offer":
                return 1;

            case "travel":
                return 2;

            case "health":
                return 3;

            case "education":
                return 4;

            case "depart":
                return 5;

            case "activity":
                return 6;

            default:
                return 0;
        }

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buket);

        TextView bucketTitle;
        Bundle bundle = getIntent().getExtras();

        if ( bundle != null ){fromNotifications = true;
        fromType = getIntent().getExtras().getString("type");
        }

        Typeface logoTypeface;
        bucketTitle     = (TextView) findViewById(R.id.bucketTitle);
        logoTypeface    =  Typeface.createFromAsset(Buket.this.getAssets(), "fonts/forte.ttf");
        bucketTitle.setTypeface(logoTypeface);

        mViewPager = (MaterialViewPager) findViewById(R.id.materialViewPager);


        assert mViewPager != null;
        mViewPager.getViewPager().setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {


            @Override
            public Fragment getItem(int position) {
                switch (position % 7) {

                    case 0:
                        Bundle agrs0 = new Bundle();
                        Fragment foodFragment = new Food();
                        agrs0.putString("from","food");
                        foodFragment.setArguments(agrs0);
                        return foodFragment;

                    case 1:
                        Bundle agrs1 = new Bundle();
                        Fragment offferStore = new Food();
                        agrs1.putString("from","offer");
                        offferStore.setArguments(agrs1);
                        return offferStore;


                    case 2:
                        Bundle agrs5 = new Bundle();
                        Fragment travelBucket = new Food();
                        agrs5.putString("from","travel");
                        travelBucket.setArguments(agrs5);
                        return travelBucket;


                    case 3:
                        Bundle agrs2 = new Bundle();
                        Fragment educationbucket= new Food();
                        agrs2.putString("from","health");
                        educationbucket.setArguments(agrs2);
                        return educationbucket;


                    case 4:
                        Bundle agrs3 = new Bundle();

                        Fragment healthBucket= new Food();
                        agrs3.putString("from","education");
                        healthBucket.setArguments(agrs3);
                        return healthBucket;

                    case 5:
                        Bundle agrs4 = new Bundle();
                        Fragment departBucket = new Food();
                        agrs4.putString("from","depart");
                        departBucket.setArguments(agrs4);
                        return departBucket;

                    case 6:
                        Bundle agrs6 = new Bundle();
                        Fragment activityBucket = new Food();
                        agrs6.putString("from","activity");
                        activityBucket.setArguments(agrs6);
                        return activityBucket;

                        default:
                        return new Products();

                }
            }

            @Override
            public int getCount() {
                return 7;
            }

            @Override
            public CharSequence getPageTitle(int position) {
                switch (position % 7) {
                    case 0:
                        return "Food";

                    case 1:
                        return "Clothes";

                    case 2:
                        return "Travel";

                    case 3:
                        return "Health";

                    case 4:
                        return "Education";

                    case 6:
                        return "Activity";

                    case 5:
                        return "Departmental Store";

                    default:
                        return "New Releases";
                }
            }
        });

        mViewPager.setMaterialViewPagerListener(new MaterialViewPager.Listener() {
            @Override
            public HeaderDesign getHeaderDesign(int page) {
                switch (page) {
                    case 0:
                        return HeaderDesign.fromColorResAndUrl(
                                R.color.red,
                                getString(R.string.host)+"/defaults/food.jpg");

                    case 1:
                        return HeaderDesign.fromColorResAndUrl(
                                R.color.blue,
                                "https://theweekendedition.com.au/wp-content/uploads/sites/6/2017/05/TWe-clothes-market-700x350-c-default.jpg");
                    case 2:
                        return HeaderDesign.fromColorResAndUrl(
                                R.color.cyan,
                                "https://ak6.picdn.net/shutterstock/videos/30467896/thumb/1.jpg");
                    case 3:
                        return HeaderDesign.fromColorResAndUrl(
                                R.color.cyan,
                                getString(R.string.host)+"/defaults/health.jpg");
                    case 4:
                        return HeaderDesign.fromColorResAndUrl(
                                R.color.green,
                                getString(R.string.host)+"/other/lifestyle.jpg");

                    case 5:
                        return HeaderDesign.fromColorResAndUrl(
                                R.color.red,
                                "http://ak8.picdn.net/shutterstock/videos/15199678/thumb/1.jpg");
                    case 6:
                        return HeaderDesign.fromColorResAndUrl(
                                R.color.red,
                                "http://dtsavvy.com/wp-content/uploads/2017/06/notifications-011.png");




                }

                //execute others actions if needed (ex : modify your header logo)

                return null;
            }
        });

        mViewPager.getViewPager().setOffscreenPageLimit(mViewPager.getViewPager().getAdapter().getCount());
        mViewPager.getPagerTitleStrip().setViewPager(mViewPager.getViewPager());

        checkForNotification(mViewPager, fromNotifications, fromType);

        mViewPager.getPagerTitleStrip().setTextColor(Color.WHITE);
        mViewPager.getToolbar().setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishThis(v);
            }
        });



    }



    public void finishThis(View v){
        finish();
    }




}
