package com.webfarmatics.proteckapp.model.api;

import com.google.gson.annotations.SerializedName;
import com.webfarmatics.proteckapp.model.Brand;

import java.util.ArrayList;

public class BrandResponse {

    @SerializedName("responseCode")
    private String responseCode;
    @SerializedName("responseMessage")
    private String responseMessage;
    @SerializedName("brands")
    private ArrayList<Brand> brandList;

    public String getResponseCode() {
        return responseCode;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public ArrayList<Brand> getBrandList() {
        return brandList;
    }
}
