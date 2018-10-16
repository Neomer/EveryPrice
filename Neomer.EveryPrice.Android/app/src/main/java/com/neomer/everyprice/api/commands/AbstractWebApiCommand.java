package com.neomer.everyprice.api.commands;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import com.neomer.everyprice.api.IWebApiCallback;
import com.neomer.everyprice.api.SecurityApi;
import com.neomer.everyprice.api.WebApiFacade;
import com.neomer.everyprice.api.models.Token;
import com.neomer.everyprice.core.ICommand;
import com.neomer.everyprice.core.IBeforeExecuteListener;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Базовый класс для всех WebApi команд.
 *
 * @param <TCallbackResult> Тип возвращаемого объекта
 */
public abstract class AbstractWebApiCommand<TCallbackResult> implements ICommand {

    private IWebApiCallback<TCallbackResult> callback;
    private SecurityApi securityApi;

    private IBeforeExecuteListener onBeforeExecuteListener;

    protected abstract Call<TCallbackResult> getCall();

    public AbstractWebApiCommand(@NonNull IWebApiCallback<TCallbackResult> callback) throws NullPointerException {
        this.securityApi = WebApiFacade.getInstance().getSecurityApi();
        if (this.securityApi == null) {
            throw new NullPointerException();
        }
        this.callback = callback;
        this.onBeforeExecuteListener = null;
    }

    public final SecurityApi getSecurityApi() {
        return securityApi;
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
    protected void beforeFailureCallback(Throwable t) {

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
            if (!onBeforeExecuteListener.OnBeforeExecute())
            {
                return;
            }
        }

        if (!beforeExecute())
        {
            return;
        }

        Call<TCallbackResult> call = getCall();

        if (call == null) {
            throw new NullPointerException();
        }

        call.enqueue(new Callback<TCallbackResult>() {
            @Override
            public void onResponse(Call<TCallbackResult> call, Response<TCallbackResult> response) {
                if (getCallback() == null || response == null) {
                    throw new NullPointerException();
                }
                TCallbackResult result = response.body();

                beforeSuccessCallback(result);
                getCallback().onSuccess(result);
            }

            @Override
            public void onFailure(Call<TCallbackResult> call, Throwable t) {
                if (getCallback() == null) {
                    throw new NullPointerException();
                }
                beforeFailureCallback(t);
                getCallback().onFailure(t);
            }
        });
    }

    /**
     * Применяет команду к обработчику OnClick указанного представления
     * @param view Представление, к которому нужно привязать команду
     */
    public final void applyToViewClick(View view) {
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                execute();
            }
        });
    }

    public final void setOnBeforeExecuteListener(IBeforeExecuteListener onBeforeExecuteListener) {
        this.onBeforeExecuteListener = onBeforeExecuteListener;
    }
}