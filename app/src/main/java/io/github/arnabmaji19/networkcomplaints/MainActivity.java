package io.github.arnabmaji19.networkcomplaints;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Link views
        drawerLayout = findViewById(R.id.drawer_layout);
        toolbar = findViewById(R.id.toolbar);
        navigationView = findViewById(R.id.navigation_view);

        setupNavigationDrawer(); //configure navigation drawer

        //set navigation view menu selected listener
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Fragment selectedFragment = null;

                //determine the selected fragment
                switch (menuItem.getItemId()) {
                    case R.id.menu_dashboard:
                        selectedFragment = new DashboardFragment();
                        break;
                }

                if (selectedFragment != null) {
                    setFragment(selectedFragment);
                    drawerLayout.closeDrawer(GravityCompat.START); //close the navigation drawer
                    return true;
                }

                return false;
            }
        });

        //set dashboard fragment as default fragment
        setFragment(new DashboardFragment());
        navigationView.setCheckedItem(R.id.menu_dashboard);
    }

    private void setupNavigationDrawer() {
        setSupportActionBar(toolbar);
        //define action bar drawer toggle
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,
                drawerLayout,
                toolbar, R.string.open, R.string.close);
        //attach the toggle to drawer listener
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }

    private void setFragment(Fragment fragment) {
        //change current fragment in the frame layout
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frame_layout, fragment)
                .commit();
    }
}
