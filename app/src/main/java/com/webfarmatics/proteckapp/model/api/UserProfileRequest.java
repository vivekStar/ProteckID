package com.webfarmatics.proteckapp.model.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserProfileRequest {

    @SerializedName("userId")
    @Expose
    private String userId;

    public UserProfileRequest(String userId) {
        this.userId = userId;
    }
}
