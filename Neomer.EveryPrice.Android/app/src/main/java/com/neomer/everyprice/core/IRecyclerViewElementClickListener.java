package com.neomer.everyprice.core;

import android.support.annotation.Nullable;

public interface IRecyclerViewElementClickListener<TViewModel> {

    public void OnClick(@Nullable TViewModel model);

}
