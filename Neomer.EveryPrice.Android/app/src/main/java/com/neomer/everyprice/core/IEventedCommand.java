package com.neomer.everyprice.core;

/**
 * Расширение для паттерна Команда для информирования о событиях
 */
public interface IEventedCommand extends ICommand {

    void setOnBeforeExecuteListener(IBeforeExecutionListener onBeforeExecuteListener);

    void setOnAfterExecuteListener(IAfterExecutionListener onAfterExecutionListener);

    void setOnAfterFailedExecutionListener(IAfterFailedExecutionListener onAfterFailedExecutionListener);

    void setOnAfterSuccessExecutionListener(IAfterSuccessExecutionListener onAfterSuccessExecutionListener);

}
