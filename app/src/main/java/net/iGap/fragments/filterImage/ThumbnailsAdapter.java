/*
 * This is the source code of iGap for Android
 * It is licensed under GNU AGPL v3.0
 * You should have received a copy of the license in this archive (see LICENSE).
 * Copyright © 2017 , iGap - www.iGap.net
 * iGap Messenger | Free, Fast and Secure instant messaging application
 * The idea of the Kianiranian Company - www.kianiranian.com
 * All rights reserved.
 */

package net.iGap.fragments.filterImage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.zomato.photofilters.imageprocessors.Filter;
import com.zomato.photofilters.utils.ThumbnailItem;

import net.iGap.R;

import java.util.List;


public class ThumbnailsAdapter extends RecyclerView.Adapter<ThumbnailsAdapter.MyViewHolder> {

    private List<ThumbnailItem> thumbnailItemList;
    private ThumbnailsAdapterListener listener;
    private Context mContext;
    private int selectedIndex = 0;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private de.hdodenhof.circleimageview.CircleImageView thumbnail;
        private TextView filterName;

        public MyViewHolder(View view) {
            super(view);
            thumbnail = view.findViewById(R.id.imgFilter);
            filterName = view.findViewById(R.id.txtName);
        }
    }


    public ThumbnailsAdapter(Context context, List<ThumbnailItem> thumbnailItemList, ThumbnailsAdapterListener listener) {
        mContext = context;
        this.thumbnailItemList = thumbnailItemList;
        this.listener = listener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.option_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final ThumbnailItem thumbnailItem = thumbnailItemList.get(position);

        holder.thumbnail.setImageBitmap(thumbnailItem.image);

        holder.thumbnail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onFilterSelected(thumbnailItem.filter);
                selectedIndex = position;
                notifyDataSetChanged();
            }
        });

        holder.filterName.setText(thumbnailItem.filterName);
        holder.filterName.setSelected(selectedIndex == position);
    }

    @Override
    public int getItemCount() {
        return thumbnailItemList.size();
    }

    public interface ThumbnailsAdapterListener {
        void onFilterSelected(Filter filter);
    }
}
