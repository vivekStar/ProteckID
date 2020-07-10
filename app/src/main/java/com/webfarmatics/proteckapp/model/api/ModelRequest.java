package com.webfarmatics.proteckapp.model.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModelRequest {

    @SerializedName("brandId")
    @Expose
    private String brandId;

    public ModelRequest(String brandId) {
        this.brandId = brandId;
    }
}
