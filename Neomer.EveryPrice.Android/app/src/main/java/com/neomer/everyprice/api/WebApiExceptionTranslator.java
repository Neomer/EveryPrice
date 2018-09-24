package com.neomer.everyprice.api;

import android.content.res.Resources;

import com.neomer.everyprice.R;
import com.neomer.everyprice.api.models.WebApiException;

public class WebApiExceptionTranslator {

    public static String getMessage(WebApiException exception, Resources resources) {
        switch (exception.getExceptionType())
        {
            case "Neomer.EveryPrice.SDK.Exceptions.Security.SignInFailedException":
                return resources.getString(R.string.webapi_error_wrong_username_or_password);

            default:
                return exception.getMessage();
        }
    }

}
