package com.webfarmatics.proteckapp.model.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.webfarmatics.proteckapp.model.User;

public class SaveUserRequest {

    @SerializedName("user")
    @Expose
    private User user;

    public void setUser(User user) {
        this.user = user;
    }


}
