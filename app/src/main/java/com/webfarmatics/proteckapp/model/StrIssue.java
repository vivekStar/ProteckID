package com.webfarmatics.proteckapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StrIssue {

    @SerializedName("issue_master_id")
    @Expose
    private int issueId;
    @SerializedName("issue")
    @Expose
    private String issueName;

    public int getIssueId() {
        return issueId;
    }

    public String getIssueName() {
        return issueName;
    }
}
