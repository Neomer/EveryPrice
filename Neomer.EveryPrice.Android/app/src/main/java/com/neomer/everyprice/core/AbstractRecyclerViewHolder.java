package com.neomer.everyprice.core;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public abstract class AbstractRecyclerViewHolder<TViewModel> extends RecyclerView.ViewHolder {

    private Context context;

    public AbstractRecyclerViewHolder(@NonNull View itemView) {
        super(itemView);

        context = itemView.getContext();

        setupViews(itemView);
    }

    /**
     * Метод установки элементов формы
     */
    protected abstract void setupViews(@NonNull View itemView);

    /**
     * Возвращает родительское представление, внутри которого находятся все остальные элементы.
     * По клику на него будет вешаться обработчик OnClick()
     * @return
     */
    protected abstract View getRootContainerView();

    /**
     * Установка значений на форме
     * @param viewModel
     */
    protected abstract void bind(TViewModel viewModel);

    public Context getContext() {
        return context;
    }
}
