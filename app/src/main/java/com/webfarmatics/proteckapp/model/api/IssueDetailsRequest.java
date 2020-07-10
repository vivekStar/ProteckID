package com.webfarmatics.proteckapp.model.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class IssueDetailsRequest {

    @SerializedName("issueid")
    @Expose
    private int issueid;

    @SerializedName("userid")
    @Expose
    private int userid;

    public IssueDetailsRequest(int issueid, int userid) {
        this.issueid = issueid;
        this.userid = userid;
    }
}
