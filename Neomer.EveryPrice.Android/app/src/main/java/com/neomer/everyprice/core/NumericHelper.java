package com.neomer.everyprice.core;

public final class NumericHelper {

    private static NumericHelper instance;

    private NumericHelper() {

    }

    public static NumericHelper getInstance() {
        if (instance == null) {
            instance = new NumericHelper();
        }
        return instance;
    }

    public String FormatToMoney(double value) {
        return String.valueOf(value);
    }
}
