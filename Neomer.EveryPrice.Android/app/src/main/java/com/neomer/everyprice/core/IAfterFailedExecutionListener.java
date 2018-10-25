package com.neomer.everyprice.core;

/**
 * Интерфейс для выполнения действий после успешного выполнения команды
 */
public interface IAfterFailedExecutionListener {

    void OnAfterFailedExecution(Throwable t);

}
