package io.dflabs.sample;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import io.dflabs.lib.mvp.RecyclerFragment;

/**
 * Created by Daniel García Alvarado on 10/13/15.
 * DragonflyLabs Library Sample - danielgarcia
 */
public class FruitAdapter<T extends CharSequence> extends android.support.v7.widget.RecyclerView.Adapter implements RecyclerFragment.SuperRecyclerAdapter<T>{


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return null;

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    @Override
    public void addAll(List<T> items) {

    }
}
