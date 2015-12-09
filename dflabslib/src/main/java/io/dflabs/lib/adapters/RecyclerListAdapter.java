package io.dflabs.lib.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Daniel García Alvarado on 10/15/15.
 * DragonflyLabs Library Sample - danielgarcia
 */
@SuppressWarnings("unchecked")
public abstract class RecyclerListAdapter<T, VH extends RecyclerView.ViewHolder>
        extends RecyclerView.Adapter<VH> {

    private boolean endlessScrollActive;

    public RecyclerListAdapter() {
        items = new ArrayList<>();
        endlessScrollActive = false;
    }

    protected List<T> items;

    public void update(List<T> items, boolean appendBottom) {
        if (this.items == null) {
            this.items = new ArrayList<>();
        }
        if (endlessScrollActive && this.items.size() > 0) {
            endlessScrollActive = false;
            this.items.remove(this.items.size() - 1);
        }
        if (appendBottom) {
            this.items.addAll(items);
        } else {
            this.items = items;
        }
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        if (items.get(position) == null) {
            return -1;
        }
        return super.getItemViewType(position);
    }

    @Override
    public void onBindViewHolder(VH holder, int position) {
        if (items.get(position) == null) {
            ProgressViewHolder progressViewHolder = (ProgressViewHolder) holder;
            progressViewHolder.progressBar.setIndeterminate(true);
        } else {
            onBindViewHolder(holder, position, items.get(position));
        }
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == -1) {
            ProgressBar progressBar = new ProgressBar(parent.getContext(), null,
                    android.R.attr.progressBarStyleSmall);
            progressBar.setLayoutParams(
                    new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT));
            return (VH) new ProgressViewHolder(progressBar);
        } else {
            return onCreateViewHolder(parent, viewType, null);
        }
    }

    public class ProgressViewHolder extends RecyclerView.ViewHolder {

        ProgressBar progressBar;

        public ProgressViewHolder(View itemView) {
            super(itemView);
            progressBar = (ProgressBar) itemView;
        }
    }

    protected abstract void onBindViewHolder(VH holder, int position, T item);

    protected abstract VH onCreateViewHolder(ViewGroup parent, int viewType, RecyclerView recyclerView);

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void startEndlessLoading() {
        endlessScrollActive = true;
        items.add(null);
        notifyDataSetChanged();
    }


}
