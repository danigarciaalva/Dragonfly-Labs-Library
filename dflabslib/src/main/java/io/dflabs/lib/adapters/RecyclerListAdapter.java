package io.dflabs.lib.adapters;

import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Daniel García Alvarado on 10/15/15.
 * DragonflyLabs Library Sample - danielgarcia
 */
public abstract class RecyclerListAdapter<T, VH extends RecyclerView.ViewHolder>
        extends RecyclerView.Adapter<VH> {

    public RecyclerListAdapter(){
        items = new ArrayList<>();
    }

    List<T> items;

    public void update(List<T> items, boolean append) {
        if (this.items == null) {
            this.items = new ArrayList<>();
        }
        if (append) {
            this.items.addAll(items);
        } else {
            this.items = items;
        }
    }

    @Override
    public void onBindViewHolder(VH holder, int position) {
        onBindViewHolder(holder, position, items.get(position));
    }

    protected abstract void onBindViewHolder(VH holder, int position, T item);

    @Override
    public int getItemCount() {
        return items.size();
    }
}
