package com.neomer.everyprice.core.helpers;

import android.content.Context;
import android.content.SharedPreferences;

public final class SecurityHelper {

    public final static String APP_PREFERENCES = "EveryPricePreferences";
    public final static String APP_PREFERENCES_TOKEN = "Token";

    /**
     * Удаляет информацию о сохраненном токене
     */
    public static void ClearSavedToken(Context context) {
        if (context == null) {
            return;
        }
        SharedPreferences preferences = context.getSharedPreferences(SecurityHelper.APP_PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(SecurityHelper.APP_PREFERENCES_TOKEN, null);
        editor.apply();
    }

}
