package com.webfarmatics.proteckapp.model.api;

import com.google.gson.annotations.SerializedName;
import com.webfarmatics.proteckapp.model.Brand;
import com.webfarmatics.proteckapp.model.Model;

import java.util.ArrayList;

public class ModelResponse {

    @SerializedName("responseCode")
    private String responseCode;
    @SerializedName("responseMessage")
    private String responseMessage;
    @SerializedName("models")
    private ArrayList<Model> modelList;

    public String getResponseCode() {
        return responseCode;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public ArrayList<Model> getModelList() {
        return modelList;
    }
}
