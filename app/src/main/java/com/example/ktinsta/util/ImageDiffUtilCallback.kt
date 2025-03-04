package com.example.ktinsta.util

import androidx.recyclerview.widget.DiffUtil
import com.example.ktinsta.modal.Photo

class ImageDiffUtilCallback : DiffUtil.ItemCallback<Photo>() {
    override fun areItemsTheSame(oldItem: Photo, newItem: Photo): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Photo, newItem: Photo): Boolean {
        return oldItem == newItem
    }
}