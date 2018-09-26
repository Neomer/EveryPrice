package com.neomer.everyprice.core;

import android.location.Location;

public interface ILocationUpdateEventListener {

    void onLocationReceived(Location location);

}
