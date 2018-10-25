package com.neomer.everyprice.api.commands;

import android.location.Location;
import android.support.annotation.NonNull;

import com.neomer.everyprice.api.IWebApiCallback;
import com.neomer.everyprice.api.WebApiFacade;
import com.neomer.everyprice.api.models.Shop;
import com.neomer.everyprice.api.models.Tag;
import com.neomer.everyprice.core.GeoLocation;

import java.util.List;
import java.util.UUID;

import retrofit2.Call;

/**
 * WebAPI запрос для получения списка ближайших магазинов.
 * На вход передаются данные о текущем местоположении.
 * На выходе получаем список магазинов.
 */
public class GetNearShopsCommand extends AbstractWebApiWithTokenCommand<List<Shop>> {

    private GeoLocation location;
    private double distance;
    private Tag tag;

    public GetNearShopsCommand(@NonNull IWebApiCallback<List<Shop>> callback) throws NullPointerException {
        super(callback);

        location = new GeoLocation();
        distance = 0;
        tag = null;
    }

    @Override
    protected boolean beforeExecute() {
        return super.beforeExecute();
    }

    @Override
    protected Call<List<Shop>> getCall() {
        if (tag == null) {
            return getSecurityApi().GetNearShops(
                    WebApiFacade.getInstance().getToken().getToken(),
                    location.getLatitude(),
                    location.getLongitude(),
                    distance);
        } else {
            return getSecurityApi().GetNearShops(
                    WebApiFacade.getInstance().getToken().getToken(),
                    location.getLatitude(),
                    location.getLongitude(),
                    distance,
                    tag.getUid());
        }
    }

    public GeoLocation getLocation() {
        return location;
    }

    public void setLocation(GeoLocation location) {
        this.location = location;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public Tag getTag() {
        return tag;
    }

    public void setTag(Tag tagUid) {
        this.tag = tagUid;
    }
}
