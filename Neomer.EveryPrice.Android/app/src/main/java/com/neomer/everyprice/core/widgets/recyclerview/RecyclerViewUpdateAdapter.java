package com.neomer.everyprice.core.widgets.recyclerview;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.neomer.everyprice.R;

public class RecyclerViewUpdateAdapter extends RecyclerView.Adapter<RecyclerViewUpdateAdapter.ViewHolder> {

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recyclerview_update_animation, viewGroup, false);
        RecyclerViewUpdateAdapter.ViewHolder viewHolder = new RecyclerViewUpdateAdapter.ViewHolder(view);
        return viewHolder;
   }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return 1;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }

    }
}
