package com.webfarmatics.proteckapp.model.api;

import com.google.gson.annotations.SerializedName;

public class LoginUserResponse {

    @SerializedName("responseCode")
    private String responseCode;
    @SerializedName("responseMessage")
    private String responseMessage;
    @SerializedName("userid")
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
