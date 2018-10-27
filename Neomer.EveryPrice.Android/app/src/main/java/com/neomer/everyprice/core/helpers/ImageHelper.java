package com.neomer.everyprice.core.helpers;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.DrawableWrapper;
import android.support.v4.content.ContextCompat;

public final class ImageHelper {

    public static Bitmap VectorToBitmap(Context context, int resource) {
        Drawable vectorImage = ContextCompat.getDrawable(context, resource);
        int w = vectorImage.getIntrinsicWidth() * 2;
        int h = vectorImage.getIntrinsicHeight() * 2;
        vectorImage.setBounds(0, 0, w, h);
        Bitmap bm = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bm);
        vectorImage.draw(canvas);
        return bm;
    }


}
