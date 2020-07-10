package com.webfarmatics.proteckapp.model.api;

import com.google.gson.annotations.SerializedName;
import com.webfarmatics.proteckapp.model.Brand;
import com.webfarmatics.proteckapp.model.StrIssue;

import java.util.ArrayList;

public class StrIssueResponse {

    @SerializedName("responseCode")
    private String responseCode;
    @SerializedName("responseMessage")
    private String responseMessage;
    @SerializedName("issueList")
    private ArrayList<StrIssue> issueList;

    public String getResponseCode() {
        return responseCode;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public ArrayList<StrIssue> getIssueList() {
        return issueList;
    }

}
