package com.neomer.everyprice.activities.settings;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;

import com.neomer.everyprice.R;
import com.neomer.everyprice.core.helpers.ConfigurationProvider;

public class ApplicationSettingsActivity extends AppCompatActivity {

    public final static String TAG = "ApplicationSettingsActivity";

    private EditText editIP;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        editIP = findViewById(R.id.activitySettings_editIP);
    }

    @Override
    protected void onPause() {
        super.onPause();

        ConfigurationProvider.getInstance().setServerIP(editIP.getText().toString());

        ConfigurationProvider.getInstance().Save();
    }

    @Override
    protected void onResume() {
        super.onResume();

        ConfigurationProvider.getInstance().Load(ApplicationSettingsActivity.this);

        editIP.setText(ConfigurationProvider.getInstance().getServerIP());
    }

}
