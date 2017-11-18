package corp.burenz.expertouch.activities;

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
import corp.burenz.expertouch.fragments.more.Address;
import corp.burenz.expertouch.fragments.more.Education;
import corp.burenz.expertouch.fragments.more.GeneralDetails;

public class MoreDetails extends AppCompatActivity   {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.design_register_company);


        //initViewPagerAndTabs();


    }




    private void initViewPagerAndTabs() {
        ViewPager viewPager = (ViewPager) findViewById(R.id.ourviewPagerForMoreDetails);
        PagerAdapter pagerAdapter = new PagerAdapter(getSupportFragmentManager());



        pagerAdapter.addFragment(new GeneralDetails(),"General");
        pagerAdapter.addFragment(new Address(),"Address");
        pagerAdapter.addFragment(new Education(),"Other");



        viewPager.setAdapter(pagerAdapter);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.maintabLayoutForMoreDetails);
        tabLayout.setupWithViewPager(viewPager);
    }



    static class PagerAdapter extends FragmentPagerAdapter {

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
