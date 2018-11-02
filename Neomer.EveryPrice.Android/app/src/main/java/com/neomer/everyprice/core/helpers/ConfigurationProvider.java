package com.neomer.everyprice.core.helpers;

import android.content.Context;
import android.content.SharedPreferences;

import com.neomer.everyprice.core.IConfigurationChangeListener;

import java.util.ArrayList;
import java.util.List;

public class ConfigurationProvider {

    //region #Singleton

    private static ConfigurationProvider instance;

    private ConfigurationProvider() {
        listeners = new ArrayList<>();
    }

    public static ConfigurationProvider getInstance() {
        if (instance == null) {
            instance = new ConfigurationProvider();
        }
        return instance;
    }

    //endregion

    private List<IConfigurationChangeListener> listeners;

    public void registerConfigurationChangeListener(IConfigurationChangeListener listener) {
        listeners.add(listener);
    }

    private void emitChanges() {
        for (IConfigurationChangeListener item : listeners) {
            try {
                item.onConfigurationChange();
            }
            catch (Exception e) {}
        }
    }

    public final static String APP_CONFIGURATION = "EveryPriceConfiguration";

    public final static String CONFIGURATION_SERVER_IP = "ServerIP";

    private SharedPreferences preferences;

    private String serverIP;

    public void Load(Context context) {
        preferences = context.getSharedPreferences(ConfigurationProvider.APP_CONFIGURATION, Context.MODE_PRIVATE);

        serverIP = preferences.getString(ConfigurationProvider.CONFIGURATION_SERVER_IP, "");
    }

    public void Save() {
        SharedPreferences.Editor editor = preferences.edit();

        editor.putString(ConfigurationProvider.CONFIGURATION_SERVER_IP, serverIP);

        editor.apply();

        emitChanges();
    }

    public String getServerIP() {
        return serverIP;
    }

    public void setServerIP(String serverIP) {
        this.serverIP = serverIP;
    }
}
