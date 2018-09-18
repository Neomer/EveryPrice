package com.neomer.everyprice;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.neomer.everyprice.api.WebApiCallback;
import com.neomer.everyprice.api.WebApiFacade;
import com.neomer.everyprice.api.models.Token;
import com.neomer.everyprice.api.models.UserSignInModel;

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

        btnRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!txtPassword.getText().toString().contentEquals(txtPasswordRetype.getText().toString())) {
                    tvRegistrationError.setText(rootView.getResources().getText(R.string.password_not_match));
                    return;
                }

                WebApiFacade.getInstance().Registration(
                        new UserSignInModel(txtUsername.getText().toString()),
                        new WebApiCallback<Token>() {
                            @Override
                            public void onSuccess(Token result) {
                                moveToMainActivity();
                            }

                            @Override
                            public void onFailure(Throwable t) {
                                tvRegistrationError.setText(t.getLocalizedMessage());
                            }
                        });

            }
        });

        return rootView;
    }

    private void moveToMainActivity() {
        startActivity(new Intent(rootView.getContext(), MainActivity.class));
        getActivity().finish();
    }
}
