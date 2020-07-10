package com.webfarmatics.proteckapp.model.api;

import com.google.gson.annotations.SerializedName;

public class SaveUserResponse {

    @SerializedName("responseCode")
    private String responseCode;
    @SerializedName("responseMessage")
    private String responseMessage;
    @SerializedName("userId")
    private String userId;


    public String getResponseCode() {
        return responseCode;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public String getUserId() {
        return userId;
    }
}
