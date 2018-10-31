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
import com.neomer.everyprice.api.models.WebApiException;
import com.neomer.everyprice.core.IAfterExecutionListener;
import com.neomer.everyprice.core.IBeforeExecutionListener;
import com.neomer.everyprice.api.commands.SignInCommand;
import com.neomer.everyprice.api.models.Token;
import com.neomer.everyprice.api.models.UserSignInModel;

public class SignInFragment extends SecurityFragment {

    @Override
    public int getLayoutResource() {
        return R.layout.fragment_signin_security;
    }

    private View progressView = null;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        final EditText txtUsername = getRootView().findViewById(R.id.txtUsername);
        final EditText txtPassword = getRootView().findViewById(R.id.txtPassword);

        Button btnSignIn = getRootView().findViewById(R.id.btnSignIn);
        final TextView tvSignInError = getRootView().findViewById(R.id.tvSignInError);

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
                    tvSignInError.setText(t.getMessage());
                }
            }
        });
        signInCommand.setOnBeforeExecuteListener(new IBeforeExecutionListener() {
            @Override
            public boolean OnBeforeExecute() {
                getRootView().findViewById(R.id.FragmentSignIn_FormLayout).setVisibility(View.INVISIBLE);
                getRootView().findViewById(R.id.FragmentSignIn_progressBar).setVisibility(View.VISIBLE);
                signInCommand.setData(
                        new UserSignInModel(
                                txtUsername.getText().toString(),
                                txtPassword.getText().toString()));
                return true;
            }
        });
        signInCommand.setOnAfterExecuteListener(new IAfterExecutionListener() {
            @Override
            public void OnAfterExecution() {
                getRootView().findViewById(R.id.FragmentSignIn_FormLayout).setVisibility(View.VISIBLE);
                getRootView().findViewById(R.id.FragmentSignIn_progressBar).setVisibility(View.INVISIBLE);
            }
        });

        signInCommand.applyToViewClick(btnSignIn);

        return getRootView();
    }

}
