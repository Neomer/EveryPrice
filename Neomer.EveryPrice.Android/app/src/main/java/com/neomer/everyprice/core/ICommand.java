package com.neomer.everyprice.core;

/**
 * Интерфейс для реализации паттерна "Команда".
 */
public interface ICommand {

    void setOnBeforeExecuteListener(IBeforeExecuteListener listener);

    void execute();

}
