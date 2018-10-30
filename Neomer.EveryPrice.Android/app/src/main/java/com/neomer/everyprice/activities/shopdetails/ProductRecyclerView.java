package com.neomer.everyprice.activities.shopdetails;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import com.neomer.everyprice.api.models.Product;
import com.neomer.everyprice.core.BaseRecyclerView;
import com.neomer.everyprice.core.ICommand;

public class ProductRecyclerView extends BaseRecyclerView<Product> {

    public ProductRecyclerView(@NonNull Context context) {
        super(context);
    }

    public ProductRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ProductRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }
}
