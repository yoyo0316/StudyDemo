package com.github.yoyozhangh.studydemo.supportlibrary;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.github.yoyozhangh.studydemo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yoyozhangh on 2018/12/21.
 */

public class DesignActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private TabLayout tabLayout;
    private ViewPager mViewPager;

    private List<String> listTitles;
    private List<Fragment> fragments;
    private List<TextView> listTextViews;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.design_activity_main_layout);
        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        tabLayout = (TabLayout) findViewById(R.id.tab_layout);

        initData();

//        findViewById(R.id.snackBar).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Snackbar.make(v,"here is SnackBar",Snackbar.LENGTH_LONG).setAction("yes", new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//
//                    }
//                }).show();
//            }
//        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
//                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
//        drawer.addDrawerListener(toggle);
//        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void initData() {
        listTitles = new ArrayList<>();
        fragments = new ArrayList<>();
        listTextViews = new ArrayList<>();

        listTitles.add("推荐");
        listTitles.add("热点");
        listTitles.add("视频");
        listTitles.add("社会");
        listTitles.add("娱乐");
        listTitles.add("财经");
        listTitles.add("科技");

        for (int i = 0; i < listTitles.size(); i++) {
            ContentFragment fragment = ContentFragment.newInstance(listTitles.get(i));
            fragments.add(fragment);
        }

        for (int i = 0; i < listTitles.size(); i++) {
            tabLayout.addTab(tabLayout.newTab().setText(listTitles.get(i)));
        }
        FragmentPagerAdapter adapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragments.get(position);
            }

            @Override
            public int getCount() {
                return fragments.size();
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return listTitles.get(position);
            }
        };
        mViewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(mViewPager);
        tabLayout.setTabsFromPagerAdapter(adapter);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

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
