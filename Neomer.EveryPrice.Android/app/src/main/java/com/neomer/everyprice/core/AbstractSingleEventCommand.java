package com.neomer.everyprice.core;

public abstract class AbstractSingleEventCommand implements IEventedCommand {

    protected IBeforeExecutionListener onBeforeExecuteListener;
    protected IAfterExecutionListener onAfterExecutionListener;
    protected IAfterFailedExecutionListener onAfterFailedExecutionListener;
    protected IAfterSuccessExecutionListener onAfterSuccessExecutionListener;


    public AbstractSingleEventCommand() {
        onBeforeExecuteListener = null;
        onAfterExecutionListener = null;
        onAfterFailedExecutionListener = null;
        onAfterSuccessExecutionListener = null;
    }

    @Override
    public void setOnBeforeExecuteListener(IBeforeExecutionListener onBeforeExecuteListener) {
        this.onBeforeExecuteListener = onBeforeExecuteListener;
    }

    @Override
    public void setOnAfterExecuteListener(IAfterExecutionListener onAfterExecutionListener) {
        this.onAfterExecutionListener = onAfterExecutionListener;
    }

    @Override
    public void setOnAfterFailedExecutionListener(IAfterFailedExecutionListener onAfterFailedExecutionListener) {
        this.onAfterFailedExecutionListener = onAfterFailedExecutionListener;
    }

    @Override
    public void setOnAfterSuccessExecutionListener(IAfterSuccessExecutionListener onAfterSuccessExecutionListener) {
        this.onAfterSuccessExecutionListener = onAfterSuccessExecutionListener;
    }
}
