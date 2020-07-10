package com.webfarmatics.proteckapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CategoryId {

    @SerializedName("category_id")
    @Expose
    private Integer categoryId;

    public CategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }
}
