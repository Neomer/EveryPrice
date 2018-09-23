package com.neomer.everyprice.core;

import org.apache.commons.math3.util.Precision;

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

    public String FormatLocation(double value) { return String.valueOf(Precision.round(value, 5)); }
}
