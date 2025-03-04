package com.example.ktinsta.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.ktinsta.adapter.StoryAdapter.ViewholderStory
import com.example.ktinsta.modal.Photo
import com.example.ktinsta.storyactivity.StoryViewActivity
import com.example.ktinsta.util.ImageDiffUtilCallback
import com.example.instagram.R
import com.example.instagram.databinding.StoryResBinding

class StoryAdapter(context: Context) :
    PagingDataAdapter<Photo, ViewholderStory>(ImageDiffUtilCallback) {
    private val context: Context

    init {
        this.context = context
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewholderStory {
        val storyResBinding = DataBindingUtil.inflate<StoryResBinding>(
            LayoutInflater.from(context),
            R.layout.story_res,
            parent,
            false
        )
        return ViewholderStory(storyResBinding)
    }

    override fun onBindViewHolder(holder: ViewholderStory, position: Int) {
        val photo = getItem(position)
        holder.storyResBinding.storyImage = photo
        holder.storyResBinding.executePendingBindings()
    }


    companion object {
        private val ImageDiffUtilCallback = object : DiffUtil.ItemCallback<Photo>() {
            override fun areItemsTheSame(oldItem: Photo, newItem: Photo): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Photo, newItem: Photo): Boolean =
                oldItem.url == newItem.url
        }
    }

    inner class ViewholderStory(var storyResBinding: StoryResBinding) : RecyclerView.ViewHolder(storyResBinding.root) {
        init {
            storyResBinding.root.setOnClickListener {
                val intent = Intent(
                    context,
                    StoryViewActivity::class.java
                )
                intent.putExtra("ContentURI", getItem(bindingAdapterPosition)?.src?.original)
                context.startActivity(intent)
            }
        }
    }
}
