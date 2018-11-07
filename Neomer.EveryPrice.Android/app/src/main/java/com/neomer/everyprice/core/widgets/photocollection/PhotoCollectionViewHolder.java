package com.neomer.everyprice.core.widgets.photocollection;

import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.widget.ImageView;

import com.neomer.everyprice.R;
import com.neomer.everyprice.core.models.ImagePreview;
import com.neomer.everyprice.core.widgets.recyclerview.AbstractRecyclerViewHolder;

public class PhotoCollectionViewHolder extends AbstractRecyclerViewHolder<ImagePreview> {

    private ImageView imageView;
    private ConstraintLayout constraintLayout;

    public PhotoCollectionViewHolder(@NonNull View itemView) {
        super(itemView);
    }

    @Override
    protected void setupViews(@NonNull View itemView) {
        imageView = itemView.findViewById(R.id.photoCollection_imageView);
        constraintLayout = itemView.findViewById(R.id.photoCollection_constraintLayout);
    }

    @Override
    protected View getRootContainerView() {
        return constraintLayout;
    }

    @Override
    protected void bind(ImagePreview imagePreview) {
        if (imagePreview == null) {
            return;
        }

        imageView.setImageURI(imagePreview.getUri());
    }

}
