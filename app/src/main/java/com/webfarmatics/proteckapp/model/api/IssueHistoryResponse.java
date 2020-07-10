package com.webfarmatics.proteckapp.model.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.webfarmatics.proteckapp.model.Issue;
import com.webfarmatics.proteckapp.model.IssueResp;

import java.util.List;

public class IssueHistoryResponse {

    @SerializedName("responseCode")
    @Expose
    private Integer responseCode;
    @SerializedName("responseMessage")
    @Expose
    private String responseMessage;
    @SerializedName("issueList")
    @Expose
    private List<IssueResp> issueList = null;


    public Integer getResponseCode() {
        return responseCode;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public List<IssueResp> getIssueList() {
        return issueList;
    }

}
