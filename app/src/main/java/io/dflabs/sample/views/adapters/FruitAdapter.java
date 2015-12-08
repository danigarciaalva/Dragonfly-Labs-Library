package io.dflabs.sample.views.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import io.dflabs.lib.adapters.RecyclerListAdapter;
import io.dflabs.sample.R;
import io.dflabs.sample.models.pojos.Fruit;

/**
 * Created by Daniel García Alvarado on 10/13/15.
 * DragonflyLabs Library Sample - danielgarcia
 */
public class FruitAdapter extends RecyclerListAdapter<Fruit, FruitAdapter.FruitViewHolder> {

    @Override
    public FruitViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_fruit, parent, false);
        return new FruitViewHolder(v);
    }

    @Override
    protected void onBindViewHolder(FruitViewHolder holder, int position, Fruit item) {
        holder.nameTextView.setText(item.getName());
    }

    public class FruitViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView;
        public FruitViewHolder(View itemView) {
            super(itemView);
            nameTextView = (TextView)itemView.findViewById(R.id.item_fruit_name);
        }
    }
}
