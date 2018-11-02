package com.neomer.everyprice.core;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.List;

public abstract class AbstractRecycleViewAdatper<TViewModel> extends RecyclerView.Adapter<AbstractRecyclerViewHolder<TViewModel>> {

    private final int resource;
    private List<TViewModel> modelList;

    private IRecyclerViewElementClickListener<TViewModel> onElementClickListener;

    public AbstractRecycleViewAdatper(@NonNull int resource, @Nullable List<TViewModel> modelList) {
        this.resource = resource;
        this.modelList = modelList;
        onElementClickListener = null;
    }

    public AbstractRecycleViewAdatper(int resource) {
        this.resource = resource;
        this.modelList = null;
        onElementClickListener = null;
    }

    @Override
    public void onBindViewHolder(@NonNull AbstractRecyclerViewHolder<TViewModel> viewHolder, int i) {
        TViewModel selectedElement = null;
        if (modelList != null) {
            selectedElement = modelList.get(i);
            viewHolder.bind(selectedElement);
        }
        final TViewModel finalSelectedElement = selectedElement;
        viewHolder.getRootContainerView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onElementClickListener != null) {
                    onElementClickListener.OnClick(finalSelectedElement);
                }
            }
        });
    }

    @Override
    public final int getItemCount() {
        return modelList == null ? 0 : modelList.size();
    }

    public void setModel(List<TViewModel> modelList) {
        this.modelList = modelList;
        notifyDataSetChanged();
    }

    public void setModel(List<TViewModel> modelList, boolean needNotify) {
        this.modelList = modelList;
        if (needNotify) {
            notifyDataSetChanged();
        }
    }

    public List<TViewModel> getModel() {
        return modelList;
    }

    public final void setOnElementClickListener(IRecyclerViewElementClickListener<TViewModel> onElementClickListener) {
        this.onElementClickListener = onElementClickListener;
    }

    public int getResource() {
        return resource;
    }
}
