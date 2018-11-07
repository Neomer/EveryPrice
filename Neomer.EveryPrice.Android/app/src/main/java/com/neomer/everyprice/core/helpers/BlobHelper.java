package com.neomer.everyprice.core.helpers;

import android.util.Base64;

public final class BlobHelper {

    public static String Encode(byte[] data) {
        return Base64.encodeToString(data, Base64.URL_SAFE);
    }

    public static byte[] Decode(String encodedData) {
        return Base64.decode(encodedData, Base64.URL_SAFE);
    }

}
