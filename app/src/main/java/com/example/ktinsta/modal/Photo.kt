package com.example.ktinsta.modal

import android.util.Log
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Photo (){


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
            if (url != null && !url.isEmpty()) {
                Glide.with(imageView.context).load(url).into(imageView)
            } else {
                Log.d("GlideLoad", "Failed")
            }
        }
    }
}
