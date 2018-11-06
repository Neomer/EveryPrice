package com.neomer.everyprice.activities.security;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.neomer.everyprice.R;
import com.neomer.everyprice.activities.settings.ApplicationSettingsActivity;
import com.neomer.everyprice.api.SecurityApi;
import com.neomer.everyprice.core.IAfterExecutionListener;
import com.neomer.everyprice.core.IBeforeExecutionListener;
import com.neomer.everyprice.core.ICommand;
import com.neomer.everyprice.core.helpers.ConfigurationProvider;

public class SecurityActivity extends AppCompatActivity {

    SecurityFragment signInFragment, registrationFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_security);

        ConfigurationProvider.getInstance().Load(this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        signInFragment = new SignInFragment();
        registrationFragment = new RegistrationFragment();

        SectionsPagerAdapter mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        ViewPager mViewPager = findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = findViewById(R.id.tabs);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.security_menu, menu);

        MenuItem settingsItem = menu.findItem(R.id.securityMenu_Setting);
        if (settingsItem != null) {
            settingsItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    moveToSettingsActivity();
                    return true;
                }
            });
        }

        MenuItem exitItem = menu.findItem(R.id.securityMenu_Exit);
        if (exitItem != null) {
            exitItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    finish();
                    return true;
                }
            });
        }

        return true;
    }

    private void moveToSettingsActivity() {
        startActivity(new Intent(SecurityActivity.this, ApplicationSettingsActivity.class));
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position)
            {
                case 0:
                    return signInFragment;

                case 1:
                    return registrationFragment;

            }

            return null;
        }

        @Override
        public int getCount() {
            return 2;
        }
    }
}
