package com.neomer.everyprice;

import android.location.Location;

public interface ILocationUpdateEventListener {

    void onLocationReceived(Location location);

}
