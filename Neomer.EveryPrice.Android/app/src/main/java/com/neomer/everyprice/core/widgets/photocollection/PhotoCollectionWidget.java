package com.neomer.everyprice.core.widgets.photocollection;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.util.AttributeSet;

import com.neomer.everyprice.R;
import com.neomer.everyprice.core.models.ImagePreview;
import com.neomer.everyprice.core.widgets.recyclerview.AbstractRecycleViewAdatper;
import com.neomer.everyprice.core.widgets.recyclerview.BaseRecyclerView;
import com.neomer.everyprice.core.widgets.recyclerview.BaseRecyclerViewAdapter;

public class PhotoCollectionWidget extends BaseRecyclerView<ImagePreview> {

    private Context context;

    public PhotoCollectionWidget(@NonNull Context context) {
        super(context);
        init(context);
    }

    public PhotoCollectionWidget(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public PhotoCollectionWidget(@NonNull Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(@NonNull Context context) {
        this.context = context;
        AbstractRecycleViewAdatper<ImagePreview> imagePreviewAdapter = new BaseRecyclerViewAdapter(PhotoCollectionViewHolder.class, R.layout.photo_collection_listitem);
        setAdapter(imagePreviewAdapter);
        setLayoutManager(new LinearLayoutManager(context));
    }

}
