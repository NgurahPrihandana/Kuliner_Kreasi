package com.example.kulinerkreasi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;


public class MainActivityUser extends AppCompatActivity {

    BottomNavigationView bottomNavigation;
    private BottomNavigationView.OnNavigationItemSelectedListener navigation = new BottomNavigationView.OnNavigationItemSelectedListener(){
        @Override
        public boolean onNavigationItemSelected( MenuItem item) {
            Fragment f = null;
            switch (item.getItemId()) {
                case R.id.nav_beranda:
                    f = new BerandaFragment();
                    break;
                case R.id.nav_cari:
                    f = new CariFragment();
                    break;
                case R.id.nav_resep_user:
                    f = new ResepUserFragment();
                    break;
                case R.id.nav_berita_user:
                    f = new BeritaUserFragment();
                    break;
                case R.id.nav_profil_user:
                    f = new ProfilUserFragment();
                    break;
            }

            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,f).commit();
            return true;
        }
    }
            ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_user);
        bottomNavigation = findViewById(R.id.bottom_nav_user);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new BerandaFragment()).commit();

        bottomNavigation.setOnNavigationItemSelectedListener(navigation);
    }
}