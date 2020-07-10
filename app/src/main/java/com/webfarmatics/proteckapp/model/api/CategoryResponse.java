package com.webfarmatics.proteckapp.model.api;

import com.google.gson.annotations.SerializedName;
import com.webfarmatics.proteckapp.model.Brand;
import com.webfarmatics.proteckapp.model.Category;

import java.util.ArrayList;

public class CategoryResponse {

    @SerializedName("responseCode")
    private String responseCode;
    @SerializedName("responseMessage")
    private String responseMessage;
    @SerializedName("categryList")
    private ArrayList<Category> categoryList;

    public String getResponseCode() {
        return responseCode;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public ArrayList<Category> getCategoryList() {
        return categoryList;
    }

}
