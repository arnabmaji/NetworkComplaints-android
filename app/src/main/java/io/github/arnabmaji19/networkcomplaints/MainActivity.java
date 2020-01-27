package io.github.arnabmaji19.networkcomplaints;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.google.android.material.navigation.NavigationView;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;
import io.github.arnabmaji19.networkcomplaints.util.Session;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

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

        //code for development use

        RequestParams params = new RequestParams("user_id", Session.getInstance().getUserId());
        AsyncHttpClient client = new AsyncHttpClient();
        client.post("http://34.203.204.120:3000/android/requests", params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                Log.d(TAG, "onSuccess: " + response);
            }
        });

        //end of code for development use

        setupNavigationDrawer(); //configure navigation drawer
        //set navigation view menu selected listener
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Fragment selectedFragment = null;

                //determine the selected fragment
                switch (menuItem.getItemId()) {
                    case R.id.menu_dashboard:
                        selectedFragment = new DashboardFragment(MainActivity.this);
                        break;

                    case R.id.menu_my_requests:
                        selectedFragment = new RequestsFragment(MainActivity.this);
                        break;
                    case R.id.menu_settings:
                        startActivity(new Intent(MainActivity.this, SettingsActivity.class));
                        return true;
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
        setFragment(new DashboardFragment(MainActivity.this));
        navigationView.setCheckedItem(R.id.menu_dashboard);

        setUserDetailsInNavDrawer(); //update user details in nav drawer

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

    private void setUserDetailsInNavDrawer() {
        Session session = Session.getInstance();
        //if session is available only then set user details in nav drawer header
        if (session.isSessionAvailable()) {
            Log.d(TAG, "setUserDetailsInNavDrawer: " + session.getUsername());
            View headerView = navigationView.getHeaderView(0);
            TextView usernameTextView = headerView.findViewById(R.id.navHeaderUsernameTextView);
            TextView userEmailTextView = headerView.findViewById(R.id.navHeaderUserEmailTextView);
            usernameTextView.setText(session.getUsername());
            userEmailTextView.setText(session.getEmail());
        }
    }
}
