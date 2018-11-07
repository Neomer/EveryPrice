package com.neomer.everyprice.api.commands;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.neomer.everyprice.api.IWebApiCallback;
import com.neomer.everyprice.api.SecurityApi;
import com.neomer.everyprice.api.WebApiFacade;
import com.neomer.everyprice.api.models.WebApiException;
import com.neomer.everyprice.core.AbstractMultipleEventedCommand;
import com.neomer.everyprice.core.IAfterExecutionListener;
import com.neomer.everyprice.core.IAfterFailedExecutionListener;
import com.neomer.everyprice.core.IAfterSuccessExecutionListener;
import com.neomer.everyprice.core.IBeforeExecutionListener;
import com.neomer.everyprice.core.ICommand;
import com.neomer.everyprice.core.IEventedCommand;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Базовый класс для всех WebApi команд.
 *
 * @param <TCallbackResult> Тип возвращаемого объекта
 */
public abstract class AbstractWebApiCommand<TCallbackResult> extends AbstractMultipleEventedCommand {

    private IWebApiCallback<TCallbackResult> callback;
    protected abstract Call<TCallbackResult> getCall();

    AbstractWebApiCommand(@NonNull IWebApiCallback<TCallbackResult> callback) throws NullPointerException {
        this.callback = callback;
    }

    public final SecurityApi getSecurityApi() {
        return WebApiFacade.getInstance().getSecurityApi();
    }

    public final IWebApiCallback<TCallbackResult> getCallback() {
        return callback;
    }

    public final void setCallback(IWebApiCallback<TCallbackResult> callback) {
        this.callback = callback;
    }

    /**
     * Вызывается перед тем, как отдать результат на внешний Callback
     * @param result Результат WebAPI запроса
     */
    protected void beforeSuccessCallback(@Nullable TCallbackResult result) {

    }

    /**
     * Вызывается перед тем, как отдать исключение на внешний Callback
     * @param t Исключение при выполнении WebAPI запроса
     */
    protected Throwable beforeFailureCallback(@SuppressWarnings("unused") Throwable t) {
        return t;
    }

    /**
     * Вызывается перед тем, как выполнить WebAPI запрос, но после того, как выполнится BeforeExecuteListener.
     * Используется для проверки корректности переданных данных.
     *
     * @return Если возвращается false, то выполнение execute() прерывается
     */
    protected boolean beforeExecute() {
        return true;
    }

    @Override
    public synchronized final void execute() throws NullPointerException {

        if (onBeforeExecuteListener != null) {
            for (IBeforeExecutionListener i : onBeforeExecuteListener) {
                if (!i.OnBeforeExecute()) {
                    return;
                }
            }
        }

        if (!beforeExecute()) {
            return;
        }

        Call<TCallbackResult> call = getCall();

        if (call == null) {
            throw new NullPointerException();
        }

        call.enqueue(new Callback<TCallbackResult>() {
            @Override
            public void onResponse(@NonNull Call<TCallbackResult> call, @NonNull Response<TCallbackResult> response) {
                if (getCallback() == null) {
                    throw new NullPointerException();
                }

                if (onAfterExecutionListener != null) {
                    for (IAfterExecutionListener i : onAfterExecutionListener) {
                        i.OnAfterExecution();
                    }
                }

                if (response.code() != 200) {
                    Gson gson = new GsonBuilder().create();
                    if (response.errorBody() != null) {
                        try {
                            WebApiException exception = gson.fromJson(response.errorBody().string(), WebApiException.class);
                            onFailure(call, exception);
                        }
                        catch (Exception ex) {
                            onFailure(call, ex);
                        }
                    }
                    return;
                }

                if (onAfterSuccessExecutionListener != null) {
                    for (IAfterSuccessExecutionListener i : onAfterSuccessExecutionListener) {
                        i.OnAfterSuccessExecution();
                    }
                }

                TCallbackResult result = response.body();

                    beforeSuccessCallback(result);
                getCallback().onSuccess(result);
            }

            @Override
            public void onFailure(@NonNull Call<TCallbackResult> call, @NonNull Throwable t) {
                if (getCallback() == null) {
                    throw new NullPointerException();
                }
                if (onAfterExecutionListener != null) {
                    for (IAfterExecutionListener i : onAfterExecutionListener) {
                        i.OnAfterExecution();
                    }
                }

                if (onAfterFailedExecutionListener != null) {
                    for (IAfterFailedExecutionListener i : onAfterFailedExecutionListener) {
                        i.OnAfterFailedExecution(t);
                    }
                }
                t = beforeFailureCallback(t);
                getCallback().onFailure(t);
            }
        });
    }

    /**
     * Применяет команду к обработчику OnClick указанного представления
     * @param view Представление, к которому нужно привязать команду
     */
    public final void applyToViewClick(View view) {
        if (view != null) {
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    execute();
                }
            });
        }
    }
}
