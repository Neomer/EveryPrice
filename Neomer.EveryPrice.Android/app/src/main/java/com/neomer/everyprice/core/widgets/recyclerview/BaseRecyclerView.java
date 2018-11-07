package com.neomer.everyprice.core.widgets.recyclerview;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import com.neomer.everyprice.core.IAfterExecutionListener;
import com.neomer.everyprice.core.IBeforeExecutionListener;
import com.neomer.everyprice.core.ICommand;
import com.neomer.everyprice.core.IEventedCommand;

/**
 * Базовый класс для реализации RecyclerView
 */
public class BaseRecyclerView<TViewModel> extends RecyclerView {

    private IEventedCommand updateCommand;
    private AbstractRecycleViewAdatper<TViewModel> adapter;
    private RecyclerViewUpdateAdapter updateAdapter;

    public BaseRecyclerView(@NonNull Context context) {
        super(context);
        updateAdapter = new RecyclerViewUpdateAdapter();
    }

    public BaseRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        updateAdapter = new RecyclerViewUpdateAdapter();
    }

    public BaseRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        updateAdapter = new RecyclerViewUpdateAdapter();
    }

    public void setAdapter(AbstractRecycleViewAdatper<TViewModel> adapter) {
        this.adapter = adapter;
        super.setAdapter(adapter);
    }

    public AbstractRecycleViewAdatper<TViewModel> getAdapter() {
        return adapter;
    }

    public void setUpdateCommand(IEventedCommand command) {
        updateCommand = command;

        // Отображаем прогресбар
        updateCommand.setOnBeforeExecuteListener(new IBeforeExecutionListener() {
            @Override
            public boolean OnBeforeExecute() {
                setAdapter(updateAdapter);
                return true;
            }
        });
        // Убираем прогресбар
        updateCommand.setOnAfterExecuteListener(new IAfterExecutionListener() {
            @Override
            public void OnAfterExecution() {
                setAdapter(adapter);
            }
        });
    }

    public ICommand getUpdateCommand() {
        return updateCommand;
    }

    public void update()
    {
        if (updateCommand != null) {
            updateCommand.execute();
        }
    }
}
