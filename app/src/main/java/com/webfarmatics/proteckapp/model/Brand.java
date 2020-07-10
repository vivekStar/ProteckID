package com.webfarmatics.proteckapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Brand {

    @SerializedName("brand_Id")
    @Expose
    private int brandId;
    @SerializedName("brand_name")
    @Expose
    private String brandName;


    public int getBrandId() {
        return brandId;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandId(int brandId) {
        this.brandId = brandId;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }
}
