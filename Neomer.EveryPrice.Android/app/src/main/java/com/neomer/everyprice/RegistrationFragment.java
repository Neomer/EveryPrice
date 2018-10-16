package com.neomer.everyprice;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.neomer.everyprice.api.IWebApiCallback;
import com.neomer.everyprice.api.WebApiExceptionTranslator;
import com.neomer.everyprice.api.WebApiFacade;
import com.neomer.everyprice.api.commands.UserRegistrationCommand;
import com.neomer.everyprice.api.models.Token;
import com.neomer.everyprice.api.models.UserSignInModel;
import com.neomer.everyprice.api.models.WebApiException;
import com.neomer.everyprice.core.IBeforeExecuteListener;

public class RegistrationFragment extends Fragment {

    private View rootView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_registration_security, container, false);

        final EditText txtUsername = (EditText) rootView.findViewById(R.id.txtUsername);
        final EditText txtPassword = (EditText) rootView.findViewById(R.id.txtPassword);
        final EditText txtPasswordRetype = (EditText) rootView.findViewById(R.id.txtPasswordRetype);
        final TextView tvRegistrationError = (TextView) rootView.findViewById(R.id.tvRegistrationError);

        Button btnRegistration = (Button) rootView.findViewById(R.id.btnRegister);
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
        registrationCommand.setOnBeforeExecuteListener(new IBeforeExecuteListener() {
            @Override
            public boolean OnBeforeExecute() {
                if (!txtPassword.getText().toString().contentEquals(txtPasswordRetype.getText().toString())) {
                    DisplayErrorMessage(rootView.getResources().getText(R.string.password_not_match));
                    return false;
                }
                registrationCommand.setData(new UserSignInModel(txtUsername.getText().toString()));
                return true;
            }
        });
        registrationCommand.applyToViewClick(btnRegistration);

        return rootView;
    }

    private void moveToMainActivity() {
        startActivity(new Intent(rootView.getContext(), MainActivity.class));
        getActivity().finish();
    }

    public void DisplayErrorMessage(String message) {
        final TextView tvRegistrationError = (TextView) rootView.findViewById(R.id.tvRegistrationError);
        tvRegistrationError.setText(message);
    }


    public void DisplayErrorMessage(CharSequence message) {
        final TextView tvRegistrationError = (TextView) rootView.findViewById(R.id.tvRegistrationError);
        tvRegistrationError.setText(message);
    }
}
