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

import com.neomer.everyprice.api.WebApiCallback;
import com.neomer.everyprice.api.WebApiFacade;
import com.neomer.everyprice.api.models.UserSignInModel;

public class SignInFragment extends Fragment {

    private View rootView = null;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_signin_security, container, false);

        final EditText txtUsername = (EditText) rootView.findViewById(R.id.txtUsername);
        Button btnSignIn = (Button) rootView.findViewById(R.id.btnSignIn);
        final TextView tvSignInError = (TextView) rootView.findViewById(R.id.tvSignInError);

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WebApiFacade.getInstance().SignIn(
                        new UserSignInModel(txtUsername.getText().toString()),
                        new WebApiCallback() {
                            @Override
                            public void onSuccess() {
                                moveToMainActivity();
                            }

                            @Override
                            public void onFailure(Throwable t) {
                                tvSignInError.setText(rootView.getResources().getText(R.string.error_wrong_username_or_password));
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
