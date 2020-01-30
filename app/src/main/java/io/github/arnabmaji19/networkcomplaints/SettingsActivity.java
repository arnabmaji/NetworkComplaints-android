package io.github.arnabmaji19.networkcomplaints;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import io.github.arnabmaji19.networkcomplaints.util.LocalDataManager;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        //display settings fragment
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.settings, new SettingsFragment(SettingsActivity.this))
                .commit();

        //configure toolbar
        Toolbar toolbar = findViewById(R.id.settings_toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    public static class SettingsFragment extends PreferenceFragmentCompat {

        private Activity activity;

        public SettingsFragment(Activity activity) {
            this.activity = activity;
        }

        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource(R.xml.settings_preferences, rootKey);

            Preference clearLogInPref = findPreference("clear_log_in_pref_key");
            if (clearLogInPref != null) {
                clearLogInPref.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                    @Override
                    public boolean onPreferenceClick(Preference preference) {
                        //clear log in credentials
                        activity.getSharedPreferences(activity.getPackageName(), MODE_PRIVATE)
                                .edit()
                                .clear()
                                .apply();
                        Toast.makeText(activity.getBaseContext(), "Log In credentials cleared!", Toast.LENGTH_SHORT).show();
                        return true;
                    }
                });
            }

            Preference clearDeviceReportPref = findPreference("clear_device_report_pref_key");
            if (clearDeviceReportPref != null) {
                clearDeviceReportPref.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                    @Override
                    public boolean onPreferenceClick(Preference preference) {
                        //clear saved device report
                        LocalDataManager localDataManager = new LocalDataManager(activity);
                        String message;
                        if (localDataManager.clearLocalData()) {
                            message = "Device Report cleared!";
                        } else {
                            message = "Nothing to clear!";
                        }
                        Toast.makeText(activity.getBaseContext(), message, Toast.LENGTH_SHORT).show();
                        return true;
                    }
                });
            }
        }
    }
}
