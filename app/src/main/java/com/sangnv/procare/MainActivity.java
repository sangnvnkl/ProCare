package com.sangnv.procare;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;

import android.util.Log;
import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sangnv.procare.Model.ItemRow;
import com.sangnv.procare.utils.AppState;
import com.sangnv.procare.utils.SharedPrefs;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.view.Menu;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        ItemFragment.OnListFragmentInteractionListener {
    public static final String TAG = MainActivity.class.getName();

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    public TabLayout tabLayout;
    public String key_danh_gia;
    public String key_tra_cuu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        key_danh_gia = getResources().getString(R.string.key_danh_gia);
        key_tra_cuu = getResources().getString(R.string.key_tra_cuu);

        //set viewpager
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        // TODO: Xử lý chọn option
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent itent = new Intent(this, SettingActivity.class);
            startActivity(itent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        // TODO: chọn item trong menu (Đánh giá, Tra cứu, About)
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_danh_gia) {
            setTitle(R.string.menu_danh_gia);
            AppState.getInstance().setmType(key_danh_gia);
            mViewPager.removeAllViews();
            tabLayout.removeAllTabs();
            mViewPager.getAdapter().notifyDataSetChanged();
            tabLayout.setupWithViewPager(mViewPager);
        } else if (id == R.id.nav_tra_cuu) {
            setTitle(R.string.menu_tra_cuu);
            AppState.getInstance().setmType(key_tra_cuu);
            mViewPager.removeAllViews();
            tabLayout.removeAllTabs();
            mViewPager.getAdapter().notifyDataSetChanged();
            tabLayout.setupWithViewPager(mViewPager);
        } else if (id == R.id.nav_settings) {
            Intent itent = new Intent(this, SettingActivity.class);
            startActivity(itent);
        } else if (id == R.id.nav_about) {

        }
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    public void onListFragmentInteraction(ItemRow item) {

    }

    @Override
    public void onRefreshSwipe() {
        mViewPager.removeAllViews();
        tabLayout.removeAllTabs();
        mViewPager.getAdapter().notifyDataSetChanged();
        tabLayout.setupWithViewPager(mViewPager);
    }

    @Override
    public void onItemChange() {

    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentStatePagerAdapter {
        private Context mContext;
        private FragmentManager mFragmentManager;

        public SectionsPagerAdapter(Context context, FragmentManager fm) {
            super(fm);
            mFragmentManager = fm;
            mContext = context;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            return super.instantiateItem(container, position);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            return ItemFragment.newInstance(AppState.getInstance().getDanhSachBang().get(position));
        }

        @Override
        public int getItemPosition(@NonNull Object object) {
            // refresh all fragments when data set changed
            return POSITION_NONE;
        }


        @Override
        public int getCount() {
            return AppState.getInstance().getDanhSachBang().size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return AppState.getInstance().getDanhSachBang() == null ? "Pro Care" : AppState.getInstance().getDanhSachBang().get(position);
        }
    }
}
