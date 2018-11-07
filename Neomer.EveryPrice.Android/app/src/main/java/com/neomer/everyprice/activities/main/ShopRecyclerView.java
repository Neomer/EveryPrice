package com.neomer.everyprice.activities.main;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import com.neomer.everyprice.api.models.Shop;
import com.neomer.everyprice.core.widgets.recyclerview.BaseRecyclerView;

public class ShopRecyclerView extends BaseRecyclerView<Shop> {

    public ShopRecyclerView(@NonNull Context context) {
        super(context);
    }

    public ShopRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ShopRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

}
