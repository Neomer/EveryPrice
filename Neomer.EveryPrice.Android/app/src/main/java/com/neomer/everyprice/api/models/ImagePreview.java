package com.neomer.everyprice.api.models;

import android.graphics.Bitmap;
import android.os.Parcel;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.util.UUID;

public class ImagePreview extends Blob {

    public ImagePreview(UUID uid, Bitmap bitmap) {
        super();
        int channels = 0;
        switch (bitmap.getConfig()) {
            case ALPHA_8:   channels = 1; break;
            case RGB_565:
            case ARGB_4444: channels = 2; break;
            case ARGB_8888: channels = 4; break;
            default: assert false;
        }
        Buffer buffer =  ByteBuffer.wrap(new  byte[bitmap.getWidth() * bitmap.getHeight() * channels]);
        bitmap.copyPixelsToBuffer(buffer);

        setUid(uid);
        setData(((ByteBuffer) buffer).array());
    }

    public ImagePreview() {
        super();
    }

    protected ImagePreview(Parcel in) {
        super(in);
    }

}
