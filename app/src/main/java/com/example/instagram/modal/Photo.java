package com.example.instagram.modal;

import android.util.Log;
import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Photo {

    private final String MediaType = "image";

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("alt")
    @Expose
    private String alt;
    @SerializedName("photographer")
    @Expose
    private String photographer;

    @SerializedName("src")
    @Expose
    private Src src;

    public Src getSrc() {
        return src;
    }

    public void setSrc(Src src) {
        this.src = src;
    }

    @BindingAdapter("glide_image")
    public static void loadImage(ImageView imageView, String url){
        if (url != null && !url.isEmpty()){
            Glide.with(imageView.getContext()).load(url).into(imageView);
        }else {
            Log.d("GlideLoad", "Failed");
        }

    }

    public String getAlt() {
        return alt;
    }

    public void setAlt(String alt) {
        this.alt = alt;
    }

    public String getPhotographer() {
        return photographer;
    }

    public void setPhotographer(String photographer) {
        this.photographer = photographer;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}
