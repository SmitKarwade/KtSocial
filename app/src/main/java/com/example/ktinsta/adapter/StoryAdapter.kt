package com.example.ktinsta.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.RecyclerView
import com.example.ktinsta.adapter.StoryAdapter.ViewholderStory
import com.example.ktinsta.modal.Photo
import com.example.ktinsta.storyactivity.StoryViewActivity
import com.example.ktinsta.util.ImageDiffUtilCallback
import com.example.instagram.R
import com.example.instagram.databinding.StoryResBinding

class StoryAdapter(context: Context) :
    RecyclerView.Adapter<ViewholderStory>() {
    private val context: Context

    init {
        this.context = context
    }

    private val differ = AsyncListDiffer(this, ImageDiffUtilCallback())

    fun submitList(listOfPhotos: List<Photo>?) = differ.submitList(listOfPhotos)

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
        val photo = differ.currentList[position]
        holder.storyResBinding.storyImage = photo
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    inner class ViewholderStory(var storyResBinding: StoryResBinding) :
        RecyclerView.ViewHolder(storyResBinding.root) {
        init {
            storyResBinding.root.setOnClickListener {
                val intent = Intent(
                    context,
                    StoryViewActivity::class.java
                )
                intent.putExtra("ContentURI", differ.currentList[adapterPosition].src?.original)
                context.startActivity(intent)
            }
        }
    }
}
