package com.example.kulinerkreasi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomnavigation.BottomNavigationView.OnNavigationItemSelectedListener;

public class MainActivityAdmin extends AppCompatActivity {
    public static Context contextOfApplication;
    public static Context getContextOfApplication()
    {
        return contextOfApplication;
    }

    BottomNavigationView bottomNavigation;
    private OnNavigationItemSelectedListener navigation = new BottomNavigationView.OnNavigationItemSelectedListener(){
        @Override
        public boolean onNavigationItemSelected( MenuItem item) {
            Fragment f = null;
            switch (item.getItemId()) {
                    case R.id.nav_dashboard:
                        f = new DashboardFragment();
                        break;
                    case R.id.nav_resep:
                        f = new ResepFragment();
                        break;
                    case R.id.nav_tambah:
                        f = new TambahFragment();
                        break;
                    case R.id.nav_berita:
                        f = new BeritaFragment();
                        break;
                    case R.id.nav_pengguna:
                        f = new PenggunaFragment();
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

        setContentView(R.layout.activity_main_admin);
        bottomNavigation = findViewById(R.id.bottom_nav);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new DashboardFragment()).commit();

        bottomNavigation.setOnNavigationItemSelectedListener(navigation);
        contextOfApplication = getApplicationContext();
    }


}
