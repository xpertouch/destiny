package corp.burenz.expertouch.activities;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
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


    @Override
    public void onBackPressed() {
        super.onBackPressed();

        overridePendingTransition(R.anim.fadein_scan,R.anim.fadeout_scan);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buket);

        TextView bucketTitle;

        Typeface logoTypeface;
        bucketTitle = (TextView) findViewById(R.id.bucketTitle);
        logoTypeface = Typeface.createFromAsset(Buket.this.getAssets(), "fonts/forte.ttf");
        bucketTitle.setTypeface(logoTypeface);

        mViewPager = (MaterialViewPager) findViewById(R.id.materialViewPager);

        assert mViewPager != null;
        mViewPager.getViewPager().setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {

            @Override
            public Fragment getItem(int position) {
                switch (position % 5) {

                    case 0:
                        return new Food();

                    case 1:
                        return new Offers();

                    case 2:
                        return new EducationBucket();

                    case 3:
                        return new Health();
                    
                    case 4:
                        return new Products();

                    default:
                        return new Products();


                }
            }

            @Override
            public int getCount() {
                return 5;
            }

            @Override
            public CharSequence getPageTitle(int position) {
                switch (position % 5) {
                    case 0:
                        return "Food Store";
                    case 1:
                        return "Fashion";
                    case 2:
                        return "Education";
                    case 3:
                        return "Health";
                    case 4:
                        return "New Releases";
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
                                getString(R.string.host)+"/other/sale.jpg");
                    case 2:
                        return HeaderDesign.fromColorResAndUrl(
                                R.color.cyan,
                                getString(R.string.host)+"/other/education.jpg");
                    case 3:
                        return HeaderDesign.fromColorResAndUrl(
                                R.color.cyan,
                                getString(R.string.host)+"/defaults/health.jpg");
                    case 4:
                        return HeaderDesign.fromColorResAndUrl(
                                R.color.green,
                                getString(R.string.host)+"/other/lifestyle.jpg");


                }

                //execute others actions if needed (ex : modify your header logo)

                return null;
            }
        });

        mViewPager.getViewPager().setOffscreenPageLimit(mViewPager.getViewPager().getAdapter().getCount());
        mViewPager.getPagerTitleStrip().setViewPager(mViewPager.getViewPager());
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
