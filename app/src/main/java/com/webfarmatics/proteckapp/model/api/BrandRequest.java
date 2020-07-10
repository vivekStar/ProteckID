package com.webfarmatics.proteckapp.model.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BrandRequest {

    @SerializedName("category_id")
    @Expose
    private String categoryId;

    public BrandRequest(String categoryId) {
        this.categoryId = categoryId;
    }

}
