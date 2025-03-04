package com.example.ktinsta.paging

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.instagram.R
import com.example.instagram.databinding.PostItemsBinding
import com.example.ktinsta.modal.Photo

class ImagePagingAdapter(private val context: Context)
    : PagingDataAdapter<Photo, ImagePagingAdapter.MyViewHolder>(MOVIE_COMPARATOR) {

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
        val item = getItem(position)
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

        holder.postItemsBinding.commentIcon.setOnClickListener{
            Toast.makeText(context, "Comment", Toast.LENGTH_SHORT)
        }

        holder.postItemsBinding.sendIcon.setOnClickListener{
            Toast.makeText(context, "Send", Toast.LENGTH_SHORT)
        }

        holder.postItemsBinding.saveIcon.setOnClickListener{
            Toast.makeText(context, "Save", Toast.LENGTH_SHORT)
        }

    }

    class MyViewHolder(var postItemsBinding: PostItemsBinding)
        : RecyclerView.ViewHolder(postItemsBinding.root)

    companion object {
        private val MOVIE_COMPARATOR = object : DiffUtil.ItemCallback<Photo>() {
            override fun areItemsTheSame(oldItem: Photo, newItem: Photo): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Photo, newItem: Photo): Boolean =
                oldItem.url == newItem.url
        }
    }
}
