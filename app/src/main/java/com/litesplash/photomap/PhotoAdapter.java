package com.litesplash.photomap;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by Eugene on 6/30/2015.
 */
public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.ViewHolder> {

    private static final String LOAD_PHOTO_TAG = "loadPhoto";
    private static final int[] GRADIENT_COLORS = {0xFFF5F5F5, 0xFFE0E0E0};

    private ArrayList<PhotoMarker> markers;
    private Context context;
    private OnItemClickListener listener;
    private int photoSize;
    private GradientDrawable placeholder;

    public PhotoAdapter(ArrayList<PhotoMarker> markers, Context context, OnItemClickListener listener) {
        this.markers = markers;
        this.context = context;
        this.listener = listener;
        photoSize = context.getResources().getDimensionPixelSize(R.dimen.photo_grid_item_size);
        placeholder = new GradientDrawable(GradientDrawable.Orientation.TL_BR, GRADIENT_COLORS);
    }

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cluster_list_item, parent, false);
        return new ViewHolder(v, listener);
    }

    public void onBindViewHolder(ViewHolder holder, int position) {
        File photoFile = markers.get(position).getFile();
        holder.filenameText.setText(photoFile.getName());

        Picasso.with(context)
                .load(photoFile)
                .placeholder(placeholder)
                .tag(LOAD_PHOTO_TAG)
                .resize(photoSize, photoSize)
                .centerCrop()
                .into(holder.photoView);
    }

    public int getItemCount() {
        return markers.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView photoView;
        TextView filenameText;
        OnItemClickListener listener;

        public ViewHolder(View itemView, OnItemClickListener listener) {
            super(itemView);
            this.listener = listener;
            photoView = (ImageView) itemView.findViewById(R.id.list_photo_view);
            filenameText = (TextView) itemView.findViewById(R.id.list_filename_text);
            itemView.setOnClickListener(this);
        }

        public void onClick(View v) {
            int position = getLayoutPosition();
            listener.onPhotoItemClick(v, position);
        }

    }

    public interface OnItemClickListener {
        void onPhotoItemClick(View view, int position);
    }
}
