package com.neomer.everyprice.core;

import android.content.res.Resources;

import com.neomer.everyprice.R;

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

    public String FormatDistance(double value, Resources resources) {
        if (value > 1000) {
            return String.valueOf(Precision.round(value * 0.001, 1)) + ' ' + resources.getString(R.string.kilometer_short);
        } else {
            return String.valueOf(Precision.round(value, 0)) + ' ' + resources.getString(R.string.meter_short);
        }
    }

    public static double ToMoney(double value) { return Precision.round(value, 2); }
}
