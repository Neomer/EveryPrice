package com.neomer.everyprice.core;

/**
 * Интерфейс для реализации паттерна "Команда".
 */
public interface ICommand {

    void setOnBeforeExecuteListener(IBeforeExecutionListener listener);

    void setOnAfterExecuteListener(IAfterExecutionListener listener);

    void execute();

}
