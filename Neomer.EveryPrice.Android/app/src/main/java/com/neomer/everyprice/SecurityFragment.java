package com.neomer.everyprice;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.neomer.everyprice.activities.main.MainActivity;
import com.neomer.everyprice.api.WebApiFacade;
import com.neomer.everyprice.core.helpers.SecurityHelper;

public abstract class SecurityFragment extends Fragment {

    private View rootView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(getLayoutResource(), container, false);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    public final View getRootView() {
        return rootView;
    }

    public abstract int getLayoutResource();

    /**
     * Сохраняем полученный токен в файл и переходим на основную активность
     */
    protected final void moveToMainActivity() {
        Context context = getContext();
        if (context == null) {
            return;
        }
        SharedPreferences preferences = context.getSharedPreferences(SecurityHelper.APP_PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(SecurityHelper.APP_PREFERENCES_TOKEN, WebApiFacade.getInstance().getToken().getToken().toString());
        editor.apply();

        if (rootView != null) {
            startActivity(new Intent(rootView.getContext(), MainActivity.class));
        }
        getActivity().finish();
    }

}
