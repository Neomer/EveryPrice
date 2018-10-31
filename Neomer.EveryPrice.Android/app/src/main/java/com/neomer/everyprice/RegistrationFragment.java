package com.neomer.everyprice;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.neomer.everyprice.api.IWebApiCallback;
import com.neomer.everyprice.api.WebApiExceptionTranslator;
import com.neomer.everyprice.api.commands.UserRegistrationCommand;
import com.neomer.everyprice.api.models.Token;
import com.neomer.everyprice.api.models.UserSignInModel;
import com.neomer.everyprice.api.models.WebApiException;
import com.neomer.everyprice.core.IAfterExecutionListener;
import com.neomer.everyprice.core.IBeforeExecutionListener;

public class RegistrationFragment extends SecurityFragment {

    @Override
    public int getLayoutResource() {
        return R.layout.fragment_registration_security;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        final EditText txtUsername = getRootView().findViewById(R.id.txtUsername);
        final EditText txtPassword = getRootView().findViewById(R.id.txtPassword);
        final EditText txtPasswordRetype = getRootView().findViewById(R.id.txtPasswordRetype);
        final TextView tvRegistrationError = getRootView().findViewById(R.id.tvRegistrationError);

        Button btnRegistration = getRootView().findViewById(R.id.btnRegister);
        final UserRegistrationCommand registrationCommand = new UserRegistrationCommand(new IWebApiCallback<Token>() {
            @Override
            public void onSuccess(Token result) {
                moveToMainActivity();
            }

            @Override
            public void onFailure(Throwable t) {
                if (t instanceof WebApiException) {
                    tvRegistrationError.setText(WebApiExceptionTranslator.getMessage((WebApiException)t, getResources()));
                } else {
                    tvRegistrationError.setText(t.getLocalizedMessage());
                }
            }
        });
        registrationCommand.setOnBeforeExecuteListener(new IBeforeExecutionListener() {
            @Override
            public boolean OnBeforeExecute() {
                if (!txtPassword.getText().toString().contentEquals(txtPasswordRetype.getText().toString())) {
                    DisplayErrorMessage(getRootView().getResources().getText(R.string.password_not_match));
                    return false;
                }
                getRootView().findViewById(R.id.FragmentRegistration_formLayout).setVisibility(View.INVISIBLE);
                getRootView().findViewById(R.id.FragmentRegistration_progressBar).setVisibility(View.VISIBLE);
                registrationCommand.setData(
                        new UserSignInModel(
                                txtUsername.getText().toString(),
                                txtPassword.getText().toString()));
                return true;
            }
        });
        registrationCommand.setOnAfterExecuteListener(new IAfterExecutionListener() {
            @Override
            public void OnAfterExecution() {
                getRootView().findViewById(R.id.FragmentRegistration_formLayout).setVisibility(View.VISIBLE);
                getRootView().findViewById(R.id.FragmentRegistration_progressBar).setVisibility(View.INVISIBLE);
            }
        });
        registrationCommand.applyToViewClick(btnRegistration);

        return getRootView();
    }

    public void DisplayErrorMessage(CharSequence message) {
        final TextView tvRegistrationError = getRootView().findViewById(R.id.tvRegistrationError);
        tvRegistrationError.setText(message);
    }
}
