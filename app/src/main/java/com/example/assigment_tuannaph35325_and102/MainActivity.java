package com.example.assigment_tuannaph35325_and102;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.assigment_tuannaph35325_and102.fragment.AboutFragment;
import com.example.assigment_tuannaph35325_and102.fragment.LogoutFragment;
import com.example.assigment_tuannaph35325_and102.fragment.ProductFragment;
import com.example.assigment_tuannaph35325_and102.fragment.SettingFragment;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawerLayout = findViewById(R.id.drawerLayout);
        toolbar = findViewById(R.id.toolBar);
        navigationView = findViewById(R.id.navigationView);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu);

        //Set fragment mac dinh

        getSupportFragmentManager()
                .beginTransaction()
                        .replace(R.id.linearLayout, new ProductFragment())
                                .commit();

        //Nhan item navigation

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                Fragment fragment = null;
                if (item.getItemId() == R.id.mQLSP) {
                    fragment = new ProductFragment();
                } else if (item.getItemId() == R.id.mGioiThieu) {
                    fragment = new AboutFragment();
                } else if (item.getItemId() == R.id.mCaiDat) {
                    fragment = new SettingFragment();
                } else {
                    fragment = new LogoutFragment();
                }

                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.linearLayout, fragment)
                        .commit();

                getSupportActionBar().setTitle(item.getTitle());

                drawerLayout.closeDrawer(GravityCompat.START);

                return false;
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            drawerLayout.openDrawer(GravityCompat.START);
        }

        return super.onOptionsItemSelected(item);
    }
}