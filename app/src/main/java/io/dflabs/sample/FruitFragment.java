package io.dflabs.sample;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import io.dflabs.lib.mvp.RecyclerFragment;

/**
 * Created by Daniel García Alvarado on 10/13/15.
 * DragonflyLabs Library Sample - danielgarcia
 */
public class FruitFragment extends RecyclerFragment{


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setAdapter(new FruitAdapter());
        startRefreshing();
    }

    @Override
    public void onRefresh() {

    }
}
