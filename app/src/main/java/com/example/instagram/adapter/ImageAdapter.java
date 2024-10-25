package com.example.instagram.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.instagram.R;
import com.example.instagram.databinding.FragmentHomeBinding;
import com.example.instagram.databinding.PostItemsBinding;
import com.example.instagram.modal.Photo;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.MyViewHolder> {

    private List<Photo> list = new ArrayList<>();
    private Context context;

    public ImageAdapter(List<Photo> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        PostItemsBinding postItemsBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.post_items, parent, false);
        return new MyViewHolder(postItemsBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Photo item = list.get(position);
        holder.postItemsBinding.setPhotos(item);
        holder.postItemsBinding.executePendingBindings();

        holder.postItemsBinding.likeIcon.setOnClickListener(new View.OnClickListener() {
            int like = 0;
            @Override
            public void onClick(View v) {
                if (like == 0){
                    holder.postItemsBinding.likeIcon.animate().scaleX(0.8f).scaleY(0.8f).setDuration(200).withEndAction(() -> {
                        holder.postItemsBinding.likeIcon.setIcon(ContextCompat.getDrawable(context, R.drawable.like_filled_svg));

                        holder.postItemsBinding.likeIcon.animate().scaleX(1.0f).scaleY(1.0f).setDuration(200).start();
                    });
                    like = 1;
                }else {
                    holder.postItemsBinding.likeIcon.animate().scaleX(0.8f).scaleY(0.8f).setDuration(200).withEndAction(() -> {
                        holder.postItemsBinding.likeIcon.setIcon(ContextCompat.getDrawable(context, R.drawable.like_svg));

                        holder.postItemsBinding.likeIcon.animate().scaleX(1.0f).scaleY(1.0f).setDuration(200).start();
                    });
                    like = 0;
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        PostItemsBinding postItemsBinding;
        MaterialButton like_icon;

        public MyViewHolder(@NonNull PostItemsBinding postItemsBinding) {
            super(postItemsBinding.getRoot());
            this.postItemsBinding = postItemsBinding;
            like_icon = postItemsBinding.likeIcon;
        }



    }
}
