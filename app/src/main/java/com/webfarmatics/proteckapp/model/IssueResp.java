package com.webfarmatics.proteckapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class IssueResp {

    @SerializedName("issue_id")
    @Expose
    private Integer issueId;
    @SerializedName("issue_name")
    @Expose
    private String issueName;
    @SerializedName("brand_id")
    @Expose
    private Integer brandId;
    @SerializedName("model_id")
    @Expose
    private Integer modelId;
    @SerializedName("issue_master_id")
    @Expose
    private Integer issueMasterId;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("user_login_id")
    @Expose
    private Integer userLoginId;
    @SerializedName("created_date")
    @Expose
    private String createdDate;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("technician_id")
    @Expose
    private Integer technicianId;
    @SerializedName("issue_assign")
    @Expose
    private String issueAssign;
    @SerializedName("issue_assign_date")
    @Expose
    private String issueAssignDate;
    @SerializedName("brand_name")
    @Expose
    private String brandName;
    @SerializedName("model_name")
    @Expose
    private String modelName;
    @SerializedName("category_id")
    @Expose
    private String categoryId;


    public Integer getIssueId() {
        return issueId;
    }

    public String getIssueName() {
        return issueName;
    }

    public Integer getBrandId() {
        return brandId;
    }

    public Integer getModelId() {
        return modelId;
    }

    public Integer getIssueMasterId() {
        return issueMasterId;
    }

    public String getDescription() {
        return description;
    }

    public Integer getUserLoginId() {
        return userLoginId;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public String getStatus() {
        return status;
    }

    public Integer getTechnicianId() {
        return technicianId;
    }

    public String getIssueAssign() {
        return issueAssign;
    }

    public String getIssueAssignDate() {
        return issueAssignDate;
    }

    public String getBrandName() {
        return brandName;
    }

    public String getModelName() {
        return modelName;
    }

    public String getCategoryId() {
        return categoryId;
    }
}
