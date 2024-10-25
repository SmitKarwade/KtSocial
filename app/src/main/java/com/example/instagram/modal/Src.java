package com.example.instagram.modal;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Src {

    @SerializedName("original")
    @Expose
    private String original;

    public String getOriginal() {
        return original;
    }

    public void setOriginal(String original) {
        this.original = original;
    }
}
