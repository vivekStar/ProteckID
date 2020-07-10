package com.webfarmatics.proteckapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Model {


    @SerializedName("brandId")
    @Expose
    private int brandId;
    @SerializedName("modelId")
    @Expose
    private int modelId;
    @SerializedName("modelName")
    @Expose
    private String modelName;


    public int getBrandId() {
        return brandId;
    }

    public int getModelId() {
        return modelId;
    }

    public String getModelName() {
        return modelName;
    }

    public void setBrandId(int brandId) {
        this.brandId = brandId;
    }

    public void setModelId(int modelId) {
        this.modelId = modelId;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }
}
