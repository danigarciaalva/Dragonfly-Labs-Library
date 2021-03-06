package io.dflabs.lib.adapters;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
            if (this.items.get(this.items.size() - 1) == null){
                this.items.remove(this.items.size() - 1);
            }
        }
        if (appendBottom) {
            this.items.addAll(items);
        } else {
            this.items = items;
        }
        try {
            notifyDataSetChanged();
        } catch (Exception ignored) {

        }

    }

    @Override
    public int getItemViewType(int position) {
        if (items.get(position) == null) {
            return -1;
        }
        return getItemType(position);
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
            float scale = parent.getContext().getResources().getDisplayMetrics().density;
            int dp = (int) (8 * scale + 0.5f);
            progressBar.setPadding(dp, dp, dp, dp);
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

    protected abstract int getItemType(int position);

    protected abstract void onBindViewHolder(VH holder, int position, T item);

    protected abstract VH onCreateViewHolder(ViewGroup parent, int viewType, RecyclerView recyclerView);

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void startEndlessLoading() {
        Log.d("Endless Scroll", "Called");
        endlessScrollActive = true;
        try {
            items.add(null);
            notifyDataSetChanged();
        } catch (Exception ignored) {

        }
    }


}
