package com.tatvasoft.tatvasoftassignment9.Adapter;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tatvasoft.tatvasoftassignment9.databinding.ImageFileBinding;

import java.util.List;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageViewHolder> {

    List<String> mList;
    onItemClickListener listener;
    ImageFileBinding imageFileBinding;

    public ImageAdapter(List<String> mList, onItemClickListener listener) {

        this.mList = mList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        imageFileBinding = ImageFileBinding.inflate(inflater,parent,false);
        return new ImageViewHolder(imageFileBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        if(!mList.isEmpty()) {
            holder.imageFileBinding.imageView.setImageURI(Uri.parse(mList.get(position)));
            imageFileBinding.imageView.setOnClickListener(v -> listener.onClick(holder.getAdapterPosition()));
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public static class ImageViewHolder extends RecyclerView.ViewHolder {
        ImageFileBinding imageFileBinding;
        public ImageViewHolder(@NonNull ImageFileBinding imageFileBinding) {
            super(imageFileBinding.getRoot());
            this.imageFileBinding = imageFileBinding;
        }
    }
    public interface onItemClickListener {
        void onClick(int position);

    }

}

