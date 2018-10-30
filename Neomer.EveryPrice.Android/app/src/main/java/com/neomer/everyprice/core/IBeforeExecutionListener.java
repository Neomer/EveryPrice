package com.neomer.everyprice.core;

/**
 * Интерфейс для выполнения действий до выполнения команды
 */
public interface IBeforeExecutionListener {

    /**
     * Вызывается перед выполнением команды
     * @return Если возвращается false, то выполнение Execute прерывается
     */
    boolean OnBeforeExecute();

}
