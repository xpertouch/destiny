package corp.burenz.expertouch.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import corp.burenz.expertouch.R;
import corp.burenz.expertouch.fragments.post.flagged.MyBucket;
import corp.burenz.expertouch.fragments.post.flagged.MyPosts;

public class MyCompanyPosts extends AppCompatActivity {

    String COMPANY_DETAILS = "myCompanyDetails";
    SharedPreferences myCompanyDetails;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_company_posts);

        initViewPagerAndTabs();


        myCompanyDetails = getSharedPreferences(COMPANY_DETAILS,0);



    }



    private void initViewPagerAndTabs() {
        ViewPager viewPager = (ViewPager) findViewById(R.id.myPostsViewPager);
        PagerAdapter pagerAdapter = new PagerAdapter(getSupportFragmentManager());


        pagerAdapter.addFragment(new MyPosts(),"MY POSTS");
        pagerAdapter.addFragment(new MyBucket(),"MY PROMOTIONS");

        viewPager.setAdapter(pagerAdapter);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.myPostsTabView);
        tabLayout.setupWithViewPager(viewPager);
    }

    class PagerAdapter extends FragmentPagerAdapter {

        private final List<Fragment> fragmentList = new ArrayList<>();
        private final List<String> fragmentTitleList = new ArrayList<>();

        public PagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        public void addFragment(Fragment fragment, String title) {
            fragmentList.add(fragment);
            fragmentTitleList.add(title);
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {

            return fragmentList.size();

        }
        @Override
        public CharSequence getPageTitle(int position) {
            return fragmentTitleList.get(position);
        }
    }






















}
