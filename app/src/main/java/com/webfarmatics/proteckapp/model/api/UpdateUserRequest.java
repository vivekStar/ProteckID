package com.webfarmatics.proteckapp.model.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.webfarmatics.proteckapp.model.User;

public class UpdateUserRequest {

    @SerializedName("user")
    private User user;


    public UpdateUserRequest(User user) {
        this.user = user;
    }
}
