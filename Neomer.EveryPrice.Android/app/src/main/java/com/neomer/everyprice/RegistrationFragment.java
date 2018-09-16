package com.neomer.everyprice;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.neomer.everyprice.api.WebApiFacade;
import com.neomer.everyprice.api.models.UserSignInModel;

public class RegistrationFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_registration_security, container, false);

        final EditText txtUsername = (EditText) rootView.findViewById(R.id.txtUsername);
        final EditText txtPassword = (EditText) rootView.findViewById(R.id.txtPassword);
        final EditText txtPasswordRetype = (EditText) rootView.findViewById(R.id.txtPasswordRetype);

        Button btnRegistration = (Button) rootView.findViewById(R.id.btnRegister);

        btnRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (txtPassword.getText().toString() != txtPasswordRetype.getText().toString()) {
                    Toast.makeText(rootView.getContext(), rootView.getResources().getText(R.string.password_not_match), Toast.LENGTH_LONG);
                    return;
                }

                WebApiFacade.getInstance().Registration(new UserSignInModel(txtUsername.getText().toString()));
            }
        });

        return rootView;
    }
}
