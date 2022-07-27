package com.example.moneyshare;

import android.content.Intent;
import android.os.Bundle;

import androidx.core.view.GravityCompat;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.moneyshare.ui.gallery.GalleryFragment;
import com.example.moneyshare.ui.home.HomeFragment;
import com.example.moneyshare.ui.slideshow.SlideshowFragment;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

public class MainActivity extends AppCompatActivity implements
        HomeFragment.OnFragmentInteractionListener,
        GalleryFragment.OnFragmentInteractionListener,
        SlideshowFragment.OnFragmentInteractionListener,
        NavigationView.OnNavigationItemSelectedListener {


    DrawerLayout drawer;

    // fragments
    HomeFragment homeFragment;
    GalleryFragment galleryFragment;
    SlideshowFragment slideshowFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        String user_id = intent.getStringExtra("user_id");

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);

        homeFragment = HomeFragment.newInstance(user_id,null);
        galleryFragment = GalleryFragment.newInstance(null, null);
        slideshowFragment = SlideshowFragment.newInstance(null, null);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        //NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ///default fragment
        navigationView.setCheckedItem(R.id.nav_home);

        androidx.fragment.app.FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.mainFrame,new HomeFragment());

        ft.commit();


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        Log.e("Mainactivity","onbackpressed");
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            int count = getFragmentManager().getBackStackEntryCount();
            if (count == 0) {
                super.onBackPressed();
                //change, if error occur
            } else {
                getFragmentManager().popBackStack();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_logout) {
            FirebaseAuth.getInstance().signOut();
            Intent loginIntent = new Intent(this, LoginActivity.class);
            startActivity(loginIntent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        androidx.fragment.app.Fragment fragment =null;
        if (id == R.id.nav_home) {
            Log.e("Mainactivity","navhome");
            fragment = homeFragment;
        } else if (id == R.id.nav_gallery) {
            Log.e("Mainactivity","navgallery");
            fragment = galleryFragment;
        } else if (id == R.id.nav_slideshow) {
            Log.e("Mainactivity","slideshow");
            fragment = slideshowFragment;
        }

        if(fragment!=null){
            androidx.fragment.app.FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.mainFrame, fragment);
            ft.addToBackStack(null);
            ft.commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onFragmentInteraction(String st) {
        getSupportActionBar().setTitle(st);
    }
}