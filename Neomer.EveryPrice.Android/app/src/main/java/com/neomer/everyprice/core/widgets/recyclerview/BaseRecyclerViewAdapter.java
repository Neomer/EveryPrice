package com.neomer.everyprice.core.widgets.recyclerview;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

public class BaseRecyclerViewAdapter<TViewModel, TViewHolder extends AbstractRecyclerViewHolder<TViewModel>> extends AbstractRecycleViewAdatper<TViewModel> {

    private final Class<TViewHolder> viewHolderClass;

    public BaseRecyclerViewAdapter(Class<TViewHolder> viewHolderClass,@NonNull int resource, @Nullable List<TViewModel> modelList) {
        super(resource, modelList);
        this.viewHolderClass = viewHolderClass;
    }

    public BaseRecyclerViewAdapter(Class<TViewHolder> viewHolderClass, @NonNull int resource) {
        super(resource, null);
        this.viewHolderClass = viewHolderClass;
    }

    @NonNull
    @Override
    public AbstractRecyclerViewHolder<TViewModel> onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(getResource(), viewGroup, false);
        AbstractRecyclerViewHolder viewHolder = null;
        try {
            viewHolder = viewHolderClass.getConstructor(View.class).newInstance(view);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return viewHolder;
    }
}
