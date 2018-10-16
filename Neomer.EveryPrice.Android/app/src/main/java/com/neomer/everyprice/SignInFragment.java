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
import android.widget.Toast;

import com.neomer.everyprice.api.IWebApiCallback;
import com.neomer.everyprice.api.WebApiExceptionTranslator;
import com.neomer.everyprice.api.models.WebApiException;
import com.neomer.everyprice.core.IBeforeExecuteListener;
import com.neomer.everyprice.api.commands.SignInCommand;
import com.neomer.everyprice.api.models.Token;
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

        final SignInCommand signInCommand = new SignInCommand(new IWebApiCallback<Token>() {
            @Override
            public void onSuccess(@Nullable Token result) {
                moveToMainActivity();
            }

            @Override
            public void onFailure(Throwable t) {
                if (t instanceof WebApiException) {
                    tvSignInError.setText(WebApiExceptionTranslator.getMessage((WebApiException)t, getResources()));
                } else {
                    tvSignInError.setText(t.getLocalizedMessage());
                }
            }
        });
        signInCommand.setOnBeforeExecuteListener(new IBeforeExecuteListener() {
            @Override
            public boolean OnBeforeExecute() {
                signInCommand.setData(new UserSignInModel(txtUsername.getText().toString()));
                return true;
            }
        });
        signInCommand.applyToViewClick(btnSignIn);

        return rootView;
    }

    private void moveToMainActivity() {
        if (rootView != null) {
            try {
                startActivity(new Intent(rootView.getContext(), MainActivity.class));
            }
            catch (Exception ex) {
                Toast.makeText(getContext(), ex.getMessage(), Toast.LENGTH_LONG);
            }
        }
        getActivity().finish();
    }


}
