package com.webfarmatics.proteckapp.model.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.webfarmatics.proteckapp.model.Issue;

import java.util.List;

public class IssueHistoryRequest {

    @SerializedName("userid")
    @Expose
    private String userId;

    public IssueHistoryRequest(String userId) {
        this.userId = userId;
    }

}
