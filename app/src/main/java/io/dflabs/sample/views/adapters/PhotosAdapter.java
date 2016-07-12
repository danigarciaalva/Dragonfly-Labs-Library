package io.dflabs.sample.views.adapters;

import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;

import io.dflabs.sample.R;

/**
 * Created by danielgarcia on 6/28/16.
 * DFLabs
 */
public class PhotosAdapter extends RecyclerView.Adapter<PhotosAdapter.PhotosViewHolder> {

    ArrayList<Bitmap> photos;

    public PhotosAdapter() {
        photos = new ArrayList<>();
    }

    @Override
    public PhotosViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_photo, parent, false);
        return new PhotosViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PhotosViewHolder holder, int position) {
        holder.imageView.setImageBitmap(photos.get(position));
    }

    @Override
    public int getItemCount() {
        return photos.size();
    }

    public void update(ArrayList<Bitmap> photos) {
        this.photos.addAll(photos);
        notifyDataSetChanged();
    }

    public class PhotosViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;

        public PhotosViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.item_photo_image);
        }
    }
}
