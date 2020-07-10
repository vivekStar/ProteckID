package com.webfarmatics.proteckapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class User implements Serializable {

    @SerializedName("userId")
    @Expose
    private String userId;

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("contact")
    @Expose
    private String contact;
    @SerializedName("adress")
    @Expose
    private String adress;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("state_Name")
    @Expose
    private String stateName;
    @SerializedName("zipCode")
    @Expose
    private String zipCode;
    @SerializedName("userName")
    @Expose
    private String userName;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("locationName")
    @Expose
    private String locationName;
    @SerializedName("lattitude")
    @Expose
    private String lattitude;
    @SerializedName("longitude")
    @Expose
    private String longitude;

    @SerializedName("userToken")
    @Expose
    private String userToken;

    public User(String userId, String name, String email, String contact, String adress,
                String city, String stateName, String zipCode, String userName, String password,
                String locationName, String lattitude, String longitude, String userToken) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.contact = contact;
        this.adress = adress;
        this.city = city;
        this.stateName = stateName;
        this.zipCode = zipCode;
        this.userName = userName;
        this.password = password;
        this.locationName = locationName;
        this.lattitude = lattitude;
        this.longitude = longitude;
        this.userToken = userToken;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getContact() {
        return contact;
    }

    public String getAdress() {
        return adress;
    }

    public String getCity() {
        return city;
    }

    public String getStateName() {
        return stateName;
    }

    public String getZipCode() {
        return zipCode;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public String getLocationName() {
        return locationName;
    }

    public String getLattitude() {
        return lattitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public String getUserId() {
        return userId;
    }

    public String getUserToken() {
        return userToken;
    }
}
