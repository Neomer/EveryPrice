package com.neomer.everyprice.core.models;

import android.net.Uri;

import com.neomer.everyprice.core.helpers.ConfigurationProvider;

import java.util.UUID;

public class ImagePreview {

    private UUID uid;

    public ImagePreview() {

    }

    public ImagePreview(UUID uid) {
        this.uid = uid;
    }

    public Uri getUri() {
        return Uri.parse("http://" + ConfigurationProvider.getInstance().getServerIP() + "/ImagePreview/" + uid.toString());
    }

}
