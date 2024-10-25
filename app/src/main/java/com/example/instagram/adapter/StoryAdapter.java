package com.example.instagram.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.instagram.R;
import com.example.instagram.storyactivity.StoryViewActivity;
import com.example.instagram.databinding.StoryResBinding;
import com.example.instagram.modal.Photo;

import java.util.ArrayList;
import java.util.List;

public class StoryAdapter extends RecyclerView.Adapter<StoryAdapter.ViewholderStory> {
    private List<Photo> photoList = new ArrayList<>();
    private Context context;

    public StoryAdapter(List<Photo> photoList, Context context) {
        this.photoList = photoList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewholderStory onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        StoryResBinding storyResBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.story_res, parent, false);
        return new ViewholderStory(storyResBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewholderStory holder, int position) {
        Photo photo = photoList.get(position);
        holder.storyResBinding.setStoryImage(photo);
    }

    @Override
    public int getItemCount() {
        return photoList.size();
    }

    public class ViewholderStory extends RecyclerView.ViewHolder{
        StoryResBinding storyResBinding;


        public ViewholderStory(@NonNull StoryResBinding storyResBinding) {
            super(storyResBinding.getRoot());
            this.storyResBinding = storyResBinding;

            storyResBinding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, StoryViewActivity.class);
                    intent.putExtra("ContentURI", photoList.get(getAbsoluteAdapterPosition()).getSrc().getOriginal());
                    context.startActivity(intent);
                }
            });
        }
    }
}
