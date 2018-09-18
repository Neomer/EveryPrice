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

import com.neomer.everyprice.api.WebApiCallback;
import com.neomer.everyprice.api.WebApiFacade;
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

        Toast.makeText(getContext(), "Here!", Toast.LENGTH_LONG).show();

        if (tvSignInError == null) {
            Toast.makeText(getContext(), "tvSignInError is null!", Toast.LENGTH_LONG).show();;
        }

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (txtUsername == null) {
                    Toast.makeText(getContext(), "txtUsername is null!", Toast.LENGTH_LONG).show();;
                    return;
                }

                WebApiFacade webApiFacade = WebApiFacade.getInstance();

                if (webApiFacade == null) {
                    Toast.makeText(getContext(), "webApiFacade is null!", Toast.LENGTH_LONG).show();;
                    return;
                }

                webApiFacade.SignIn(
                        new UserSignInModel(txtUsername.getText().toString()),
                        new WebApiCallback<Token>() {
                            @Override
                            public void onSuccess(Token result) {
                                moveToMainActivity();
                            }

                            @Override
                            public void onFailure(Throwable t) {
                                tvSignInError.setText(t.getMessage());
                            }
                        });

            }
        });

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
        //getActivity().finish();
    }


}
