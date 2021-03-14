package com.shugaban.shugaban.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.shugaban.shugaban.Fragments.CinemaFragment;
import com.shugaban.shugaban.Fragments.LiveFragment;
import com.shugaban.shugaban.Fragments.MoviesFragment;
import com.shugaban.shugaban.Fragments.PurchasesFragment;
import com.shugaban.shugaban.Fragments.SearchFragment;
import com.shugaban.shugaban.Inc.Util;
import com.shugaban.shugaban.R;


import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigation;
    public static ViewPager mFragmentsHolderViewPager;
    private ImageView m_login_imageview;
    private MyPageAdapter pageAdapter;
    private int currentMenuItemSelected = Util.MOVIES_FRAMENT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        m_login_imageview = findViewById(R.id.login);
        mFragmentsHolderViewPager = findViewById(R.id.activity_main_viewpager);
        bottomNavigation = findViewById(R.id.bottom_navigation);

        bottomNavigation.setOnNavigationItemSelectedListener(navigationItemSelectedListener);
        bottomNavigation.setSelectedItemId(R.id.navigation_movies);

        //SETTING A PAGE/FRAGMENT CHANGE LISTENER FOR THE VIEWPAGER
        mFragmentsHolderViewPager.addOnPageChangeListener(view_paper_listener);

        List<Fragment> fragmentsList = getFragments();

        pageAdapter = new MyPageAdapter(getSupportFragmentManager(), fragmentsList);
        mFragmentsHolderViewPager.setAdapter(pageAdapter);
        mFragmentsHolderViewPager.setCurrentItem(0);

        m_login_imageview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                startActivity(intent);
            }
        });
        //Util.open_fragment(getSupportFragmentManager(),R.id.container, TodayFragment.newInstance("", ""), "TodayFragment", 0);

    }

    private List<Fragment> getFragments(){
        List<Fragment> fList = new ArrayList<Fragment>();

        fList.add(MoviesFragment.newInstance("", ""));
        fList.add(SearchFragment.newInstance("", ""));
        fList.add(LiveFragment.newInstance("", ""));
        fList.add(CinemaFragment.newInstance("", ""));
        fList.add(PurchasesFragment.newInstance("", ""));
        return fList;
    }

    // THE FRAGMENT ADAPTER
    private class MyPageAdapter extends FragmentPagerAdapter {
        private List<Fragment> fragmentList;

        public MyPageAdapter(FragmentManager fragmentManager, List<Fragment> fragmentsList ) {
            super(fragmentManager);
            this.fragmentList = fragmentsList;
        }

        @Override
        public Fragment getItem(int position) {
            return this.fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return this.fragmentList.size();
        }
    }


    ViewPager.OnPageChangeListener view_paper_listener = new ViewPager.OnPageChangeListener(){

        @Override
        public void onPageScrolled(int i, float v, int i1) {

        }

        @Override
        public void onPageSelected(int i) {
            currentMenuItemSelected = i;
            if(i == Util.MOVIES_FRAMENT){
                bottomNavigation.setSelectedItemId(R.id.navigation_movies);
            } else if(i == Util.SEARCH_FRAMENT){
                bottomNavigation.setSelectedItemId(R.id.navigation_search);
            } else if(i == Util.LIVE_FRAMENT){
                bottomNavigation.setSelectedItemId(R.id.navigation_live);
            } else if(i == Util.CINEMA_FRAMENT){
                bottomNavigation.setSelectedItemId(R.id.navigation_cinema);
            } else if(i == Util.PURCHASED_FRAMENT){
                bottomNavigation.setSelectedItemId(R.id.navigation_witness);
            }


        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };


    BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.navigation_movies:
                            currentMenuItemSelected = Util.MOVIES_FRAMENT;
                            mFragmentsHolderViewPager.setCurrentItem(0);
                            return true;
                        case R.id.navigation_search:
                            currentMenuItemSelected = Util.SEARCH_FRAMENT;
                            mFragmentsHolderViewPager.setCurrentItem(1);
                            return true;
                        case R.id.navigation_live:
                            currentMenuItemSelected = Util.LIVE_FRAMENT;
                            mFragmentsHolderViewPager.setCurrentItem(2);
                            return true;
                        case R.id.navigation_cinema:
                            currentMenuItemSelected = Util.CINEMA_FRAMENT;
                            mFragmentsHolderViewPager.setCurrentItem(3);
                            return true;
                        case R.id.navigation_witness:
                            currentMenuItemSelected = Util.PURCHASED_FRAMENT;
                            mFragmentsHolderViewPager.setCurrentItem(4);
                            return true;
                    }
                    return false;
                }
            };

    @Override
    public void onBackPressed() {

        //GETTING THE FRAGMENT MANAGER OF ALL FRAGMENTS OPEN ON THE MAIN ACTIVITY
        int count = getSupportFragmentManager().getBackStackEntryCount();
        if(count == 0){
            if(mFragmentsHolderViewPager.getCurrentItem() == 0){
                //super.onBackPressed();
                finish();
                // finishAffinity();
            } else {
                mFragmentsHolderViewPager.setCurrentItem(0);
            }
        } else {
            //super.onBackPressed();
            finish();
            // finishAffinity();
        }

    }


}
