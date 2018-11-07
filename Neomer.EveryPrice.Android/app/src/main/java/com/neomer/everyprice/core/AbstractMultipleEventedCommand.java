package com.neomer.everyprice.core;

import java.util.ArrayList;
import java.util.List;

/**
 * Реализация IEventedCommand с возможностью указания нескольких обработчиков событий
 */
public abstract class AbstractMultipleEventedCommand implements IEventedCommand {

    protected List<IBeforeExecutionListener> onBeforeExecuteListener;
    protected List<IAfterExecutionListener> onAfterExecutionListener;
    protected List<IAfterFailedExecutionListener> onAfterFailedExecutionListener;
    protected List<IAfterSuccessExecutionListener> onAfterSuccessExecutionListener;

    public AbstractMultipleEventedCommand() {
        onBeforeExecuteListener = new ArrayList<>();
        onAfterExecutionListener = new ArrayList<>();
        onAfterFailedExecutionListener = new ArrayList<>();
        onAfterSuccessExecutionListener = new ArrayList<>();
    }

    @Override
    public void setOnBeforeExecuteListener(IBeforeExecutionListener onBeforeExecuteListener) {
        this.onBeforeExecuteListener.add(onBeforeExecuteListener);
    }

    @Override
    public void setOnAfterExecuteListener(IAfterExecutionListener onAfterExecutionListener) {
        this.onAfterExecutionListener.add(onAfterExecutionListener);
    }

    @Override
    public void setOnAfterSuccessExecutionListener(IAfterSuccessExecutionListener onAfterSuccessExecutionListener) {
        this.onAfterSuccessExecutionListener.add(onAfterSuccessExecutionListener);
    }

    @Override
    public void setOnAfterFailedExecutionListener(IAfterFailedExecutionListener onAfterFailedExecutionListener) {
        this.onAfterFailedExecutionListener.add(onAfterFailedExecutionListener);
    }
}
