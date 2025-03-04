package com.example.ktinsta.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.RecyclerView
import com.example.ktinsta.adapter.ImageAdapter.MyViewHolder
import com.example.ktinsta.modal.Photo
import com.example.ktinsta.util.ImageDiffUtilCallback
import com.example.instagram.R
import com.example.instagram.databinding.PostItemsBinding
import com.google.android.material.button.MaterialButton

class ImageAdapter(context: Context) :
    RecyclerView.Adapter<MyViewHolder>() {
    private val context: Context

    init {
        this.context = context
    }

    private val AsyncListDiffer = AsyncListDiffer(this, ImageDiffUtilCallback())

    fun submitList(listOfPhotos: List<Photo>?) = AsyncListDiffer.submitList(listOfPhotos)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val postItemsBinding = DataBindingUtil.inflate<PostItemsBinding>(
            LayoutInflater.from(context),
            R.layout.post_items,
            parent,
            false
        )
        return MyViewHolder(postItemsBinding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = AsyncListDiffer.currentList[position]
        holder.postItemsBinding.photos = item
        holder.postItemsBinding.executePendingBindings()

        holder.postItemsBinding.likeIcon.setOnClickListener(object : View.OnClickListener {
            var like: Int = 0
            override fun onClick(v: View) {
                if (like == 0) {
                    holder.postItemsBinding.likeIcon.animate().scaleX(0.8f).scaleY(0.8f)
                        .setDuration(200).withEndAction {
                            holder.postItemsBinding.likeIcon.icon = ContextCompat.getDrawable(
                                context,
                                R.drawable.like_filled_svg
                            )
                            holder.postItemsBinding.likeIcon.animate().scaleX(1.0f).scaleY(1.0f)
                                .setDuration(200).start()
                        }
                    like = 1
                } else {
                    holder.postItemsBinding.likeIcon.animate().scaleX(0.8f).scaleY(0.8f)
                        .setDuration(200).withEndAction {
                            holder.postItemsBinding.likeIcon.icon = ContextCompat.getDrawable(
                                context,
                                R.drawable.like_svg
                            )
                            holder.postItemsBinding.likeIcon.animate().scaleX(1.0f).scaleY(1.0f)
                                .setDuration(200).start()
                        }
                    like = 0
                }
            }
        })
    }

    override fun getItemCount(): Int {
        return AsyncListDiffer.currentList.size
    }

    class MyViewHolder(var postItemsBinding: PostItemsBinding) : RecyclerView.ViewHolder(postItemsBinding.root) {
        var likeIcon: MaterialButton = postItemsBinding.likeIcon
    }
}
