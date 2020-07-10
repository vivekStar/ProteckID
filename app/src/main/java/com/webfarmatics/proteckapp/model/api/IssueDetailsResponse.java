package com.webfarmatics.proteckapp.model.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.webfarmatics.proteckapp.model.IssueDetails;

public class IssueDetailsResponse {

    @SerializedName("responseCode")
    @Expose
    private Integer responseCode;
    @SerializedName("responseMessage")
    @Expose
    private String responseMessage;


    @SerializedName("issue")
    @Expose
    private IssueDetails issue;

    public Integer getResponseCode() {
        return responseCode;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public IssueDetails getIssue() {
        return issue;
    }
}
