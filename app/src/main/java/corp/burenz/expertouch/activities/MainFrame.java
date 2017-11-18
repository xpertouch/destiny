package corp.burenz.expertouch.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import corp.burenz.expertouch.R;
import corp.burenz.expertouch.fragments.mainframes.Catagories;
import corp.burenz.expertouch.fragments.mainframes.MyProfile;
import corp.burenz.expertouch.fragments.mainframes.WhatNew;

public class MainFrame extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    String LOCAL_APP_DATA = "userInformation";
    SharedPreferences userData;
    boolean verificationStatus;
    Typeface logoTypeface;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_frame);
        Toolbar mToolbar = (Toolbar) findViewById(R.id.maintoolbarForFeeds);
        setSupportActionBar(mToolbar);

        TextView xper = (TextView) findViewById(R.id.xper);
        TextView touch = (TextView) findViewById(R.id.touch);


        logoTypeface = Typeface.createFromAsset(MainFrame.this.getAssets(), "fonts/forte.ttf");

        xper.setTypeface(logoTypeface);
        touch.setTypeface(logoTypeface);

        initViewPagerAndTabs();





        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        toggle.setDrawerIndicatorEnabled(true);
        toggle.isDrawerIndicatorEnabled();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        userData = this.getSharedPreferences(LOCAL_APP_DATA,MODE_PRIVATE);

        verificationStatus = userData.getBoolean("VERIFIED",false);
        if (!verificationStatus){
          //  Toast.makeText(MainFrame.this, "YOur Verification Status is Still pendin", Toast.LENGTH_SHORT).show();
        }


    }






    private void initViewPagerAndTabs() {
        ViewPager viewPager = (ViewPager) findViewById(R.id.ourviewPagerForFeeds);
        PagerAdapter pagerAdapter = new PagerAdapter(getSupportFragmentManager());



        pagerAdapter.addFragment(new WhatNew(),"WHATS NEW");
     pagerAdapter.addFragment(new Catagories(),"CATAGORIES");
        pagerAdapter.addFragment(new MyProfile(),"PROFILE");


        viewPager.setAdapter(pagerAdapter);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.maintabLayoutForFeeds);
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



    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_frame, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action

            startActivity(new Intent(MainFrame.this,ExpertDetails.class));

        } else if (id == R.id.nav_gallery) {


            startActivity(new Intent(MainFrame.this,SkilledExperts.class));


        } else if (id == R.id.nav_slideshow) {


        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }





}
