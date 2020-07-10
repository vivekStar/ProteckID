package com.webfarmatics.proteckapp.model.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.webfarmatics.proteckapp.model.Issue;

public class RaiseIssueRequest {

    @SerializedName("issue")
    private Issue status;

    public RaiseIssueRequest(Issue status) {
        this.status = status;
    }
}
