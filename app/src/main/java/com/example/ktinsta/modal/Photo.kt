package com.example.ktinsta.modal

import android.util.Log
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.instagram.R
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Photo (private val type : String = ""){


    @SerializedName("id")
    @Expose
    var id: Int? = null

    @SerializedName("url")
    @Expose
    var url: String? = null

    @SerializedName("alt")
    @Expose
    var alt: String? = null

    @SerializedName("photographer")
    @Expose
    var photographer: String? = null

    @SerializedName("src")
    @Expose
    var src: Src? = null


    companion object {
        @JvmStatic
        @BindingAdapter("glide_image")
        fun loadImage(imageView: ImageView, url: String?) {
            Glide.with(imageView.context)
                .load(url)
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .error(R.drawable.connection_error)
                .into(imageView)

        }
    }
}
