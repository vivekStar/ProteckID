package com.webfarmatics.proteckapp.model.api;

import com.google.gson.annotations.SerializedName;

public class RaiseIssueResponse {

    @SerializedName("responseCode")
    private String responseCode;
    @SerializedName("responseMessage")
    private String responseMessage;
    @SerializedName("issueId")
    private int issueId;

    public String getResponseCode() {
        return responseCode;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public int getUserId() {
        return issueId;
    }
}
