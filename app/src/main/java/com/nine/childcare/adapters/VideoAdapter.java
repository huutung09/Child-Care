package com.nine.childcare.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.nine.childcare.R;
import com.nine.childcare.interfaces.VideoAdapterListener;
import com.nine.childcare.model.ItemYoutube;

import java.util.ArrayList;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.ViewHolder> {
    private ArrayList<ItemYoutube> items;
    private VideoAdapterListener listener;

    public void setListener(VideoAdapterListener listener) {
        this.listener = listener;
    }

    public VideoAdapter(ArrayList<ItemYoutube> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.video_row_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ItemYoutube i = items.get(position);
        holder.itemRowTitle.setText(i.getSnippet().getTitle());
        holder.itemRowTime.setText(i.getSnippet().getPublishedAt());
        Glide.with(holder.itemView.getContext())
                .load(i.getSnippet().getThumbnails().getMedium().getUrl())
                .into(holder.itemRowImg);
        holder.currentItem = i;
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout itemRowContainer;
        public ImageView itemRowImg;
        public TextView itemRowTitle, itemRowTime;
        public ItemYoutube currentItem;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemRowContainer = itemView.findViewById(R.id.row_item_container);
            itemRowImg = itemView.findViewById(R.id.row_item_img);
            itemRowTitle = itemView.findViewById(R.id.row_item_tittle);
            itemRowTime = itemView.findViewById(R.id.row_item_time);
            itemRowContainer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        listener.onClick(currentItem);
                    }
                }
            });
        }
    }
}
