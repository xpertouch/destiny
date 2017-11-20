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
import corp.burenz.expertouch.fragments.bucket.Health;
import corp.burenz.expertouch.fragments.bucket.Offers;
import corp.burenz.expertouch.fragments.bucket.Products;

public class Buket extends AppCompatActivity {

    private MaterialViewPager mViewPager;

    

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

        mViewPager.getViewPager().setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {

            @Override
            public Fragment getItem(int position) {
                switch (position % 4) {

                    case 0:
                        return new Offers();

                    case 1:
                        return new EducationBucket();

                    case 2:
                        return new Health();

                    case 3:
                        return new Products();
                    
                    default:
                        return new Products();

                }
            }

            @Override
            public int getCount() {
                return 4;
            }

            @Override
            public CharSequence getPageTitle(int position) {
                switch (position % 4) {
                    case 0:
                        return "Offers";
                    case 1:
                        return "Education";
                    case 2:
                        return "Health";
                    case 3:
                        return "New Releases";
                }
                return "";
            }
        });

        mViewPager.setMaterialViewPagerListener(new MaterialViewPager.Listener() {
            @Override
            public HeaderDesign getHeaderDesign(int page) {
                switch (page) {
                    case 0:
                        return HeaderDesign.fromColorResAndUrl(
                                R.color.blue,
                                getString(R.string.host)+"/other/sale.jpg");
                    case 1:
                        return HeaderDesign.fromColorResAndUrl(
                                R.color.blue,
                                getString(R.string.host)+"/other/education.jpg");
                    case 2:
                        return HeaderDesign.fromColorResAndUrl(
                                R.color.cyan,
                                getString(R.string.host)+"/other/health.jpg");
                    case 3:
                        return HeaderDesign.fromColorResAndUrl(
                                R.color.red,
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
