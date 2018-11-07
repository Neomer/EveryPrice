package com.neomer.everyprice.api.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.neomer.everyprice.core.helpers.BlobHelper;

import java.util.UUID;

public class Blob implements Parcelable {

    private UUID Uid;
    private String DataEncoded;

    public Blob(UUID uid, String dataEncoded) {
        Uid = uid;
        DataEncoded = dataEncoded;
    }

    public Blob(UUID uid, byte[] data) {
        Uid = uid;
        DataEncoded = BlobHelper.Encode(data);
    }

    public Blob() {

    }

    protected Blob(Parcel in) {
        Uid = UUID.fromString(in.readString());
        DataEncoded = in.readString();
    }

    public static final Creator<Blob> CREATOR = new Creator<Blob>() {
        @Override
        public Blob createFromParcel(Parcel in) {
            return new Blob(in);
        }

        @Override
        public Blob[] newArray(int size) {
            return new Blob[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(Uid.toString());
        dest.writeString(DataEncoded);
    }

    public UUID getUid() {
        return Uid;
    }

    public void setUid(UUID uid) {
        Uid = uid;
    }

    public String getDataEncoded() {
        return DataEncoded;
    }

    public void setData(byte[] data) {
        DataEncoded = BlobHelper.Encode(data);
    }
}
