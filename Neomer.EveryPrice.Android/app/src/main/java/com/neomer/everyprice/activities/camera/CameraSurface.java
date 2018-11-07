package com.neomer.everyprice.activities.camera;

import android.content.Context;
import android.hardware.Camera;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.IOException;

public class CameraSurface implements SurfaceHolder.Callback {

    private Camera camera;

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        if (camera == null) {
            return;
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }

    public void setCamera(Camera camera) {
        this.camera = camera;
    }
}
