package com.webfarmatics.proteckapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Issue {

    @SerializedName("issue_name")
    @Expose
    private String issueName;
    @SerializedName("issue_master_id")
    @Expose
    private Integer issueMasterId;
    @SerializedName("brand_id")
    @Expose
    private String brandId;
    @SerializedName("model_id")
    @Expose
    private String modelId;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("user_login_id")
    @Expose
    private Integer userLoginId;
    @SerializedName("brand_name")
    @Expose
    private String brandName;
    @SerializedName("model_name")
    @Expose
    private String modelName;
    @SerializedName("category_id")
    @Expose
    private CategoryId categoryId;

    @SerializedName("under_warranty")
    @Expose
    private String underWarranty;

    @SerializedName("warranty_bill")
    @Expose
    private String warrantyBill;

    public Issue(String issueName, Integer issueMasterId, String brandId, String modelId, String description, Integer userLoginId, String brandName, String modelName, CategoryId categoryId, String underWarranty, String warrantyBill) {
        this.issueName = issueName;
        this.issueMasterId = issueMasterId;
        this.brandId = brandId;
        this.modelId = modelId;
        this.description = description;
        this.userLoginId = userLoginId;
        this.brandName = brandName;
        this.modelName = modelName;
        this.categoryId = categoryId;
        this.underWarranty = underWarranty;
        this.warrantyBill = warrantyBill;
    }
}
