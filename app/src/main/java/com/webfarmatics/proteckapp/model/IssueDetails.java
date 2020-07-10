package com.webfarmatics.proteckapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class IssueDetails {
    @SerializedName("issue_id")
    @Expose
    private int issue_id;
    @SerializedName("issue_name")
    @Expose
    private String issue_name;
    @SerializedName("brand_id")
    @Expose
    private int brand_id;
    @SerializedName("model_id")
    @Expose
    private int model_id;
    @SerializedName("issue_master_id")
    @Expose
    private int issue_master_id;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("user_login_id")
    @Expose
    private int user_login_id;
    @SerializedName("customer_name")
    @Expose
    private String customer_name;
    @SerializedName("contact")
    @Expose
    private String contact;
    @SerializedName("created_date")
    @Expose
    private String created_date;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("technician_id")
    @Expose
    private int technician_id;
    @SerializedName("issue_assign")
    @Expose
    private String issue_assign;
    @SerializedName("issue_assign_date")
    @Expose
    private String issue_assign_date;
    @SerializedName("brand_name")
    @Expose
    private String brand_name;
    @SerializedName("model_name")
    @Expose
    private String model_name;
    @SerializedName("category_id")
    @Expose
    private String category_id;
    @SerializedName("inventory_assigned")
    @Expose
    private String inventory_assigned;
    @SerializedName("total_amount")
    @Expose
    private int total_amount;
    @SerializedName("service_charge")
    @Expose
    private int service_charge;
    @SerializedName("grand_total")
    @Expose
    private int grand_total;
    @SerializedName("under_warranty")
    @Expose
    private String under_warranty;
    @SerializedName("warranty_bill")
    @Expose
    private String warranty_bill;
    @SerializedName("bill_name")
    @Expose
    private String bill_name;
    @SerializedName("payment_date")
    @Expose
    private String payment_date;
    @SerializedName("payment_status")
    @Expose
    private String payment_status;


    public int getIssue_id() {
        return issue_id;
    }

    public void setIssue_id(int issue_id) {
        this.issue_id = issue_id;
    }

    public String getIssue_name() {
        return issue_name;
    }

    public void setIssue_name(String issue_name) {
        this.issue_name = issue_name;
    }

    public int getBrand_id() {
        return brand_id;
    }

    public void setBrand_id(int brand_id) {
        this.brand_id = brand_id;
    }

    public int getModel_id() {
        return model_id;
    }

    public void setModel_id(int model_id) {
        this.model_id = model_id;
    }

    public int getIssue_master_id() {
        return issue_master_id;
    }

    public void setIssue_master_id(int issue_master_id) {
        this.issue_master_id = issue_master_id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getUser_login_id() {
        return user_login_id;
    }

    public void setUser_login_id(int user_login_id) {
        this.user_login_id = user_login_id;
    }

    public String getCustomer_name() {
        return customer_name;
    }

    public void setCustomer_name(String customer_name) {
        this.customer_name = customer_name;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getCreated_date() {
        return created_date;
    }

    public void setCreated_date(String created_date) {
        this.created_date = created_date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getTechnician_id() {
        return technician_id;
    }

    public void setTechnician_id(int technician_id) {
        this.technician_id = technician_id;
    }

    public String getIssue_assign() {
        return issue_assign;
    }

    public void setIssue_assign(String issue_assign) {
        this.issue_assign = issue_assign;
    }

    public String getIssue_assign_date() {
        return issue_assign_date;
    }

    public void setIssue_assign_date(String issue_assign_date) {
        this.issue_assign_date = issue_assign_date;
    }

    public String getBrand_name() {
        return brand_name;
    }

    public void setBrand_name(String brand_name) {
        this.brand_name = brand_name;
    }

    public String getModel_name() {
        return model_name;
    }

    public void setModel_name(String model_name) {
        this.model_name = model_name;
    }

    public String getCategory_id() {
        return category_id;
    }

    public void setCategory_id(String category_id) {
        this.category_id = category_id;
    }

    public String getInventory_assigned() {
        return inventory_assigned;
    }

    public void setInventory_assigned(String inventory_assigned) {
        this.inventory_assigned = inventory_assigned;
    }

    public int getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(int total_amount) {
        this.total_amount = total_amount;
    }

    public int getService_charge() {
        return service_charge;
    }

    public void setService_charge(int service_charge) {
        this.service_charge = service_charge;
    }

    public int getGrand_total() {
        return grand_total;
    }

    public void setGrand_total(int grand_total) {
        this.grand_total = grand_total;
    }

    public String getUnder_warranty() {
        return under_warranty;
    }

    public void setUnder_warranty(String under_warranty) {
        this.under_warranty = under_warranty;
    }

    public String getWarranty_bill() {
        return warranty_bill;
    }

    public void setWarranty_bill(String warranty_bill) {
        this.warranty_bill = warranty_bill;
    }

    public String getBill_name() {
        return bill_name;
    }

    public void setBill_name(String bill_name) {
        this.bill_name = bill_name;
    }

    public String getPayment_date() {
        return payment_date;
    }

    public void setPayment_date(String payment_date) {
        this.payment_date = payment_date;
    }

    public String getPayment_status() {
        return payment_status;
    }

    public void setPayment_status(String payment_status) {
        this.payment_status = payment_status;
    }
}
