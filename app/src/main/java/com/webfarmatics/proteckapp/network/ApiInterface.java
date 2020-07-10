package com.webfarmatics.proteckapp.network;


import com.webfarmatics.proteckapp.model.Category;
import com.webfarmatics.proteckapp.model.api.BrandRequest;
import com.webfarmatics.proteckapp.model.api.BrandResponse;
import com.webfarmatics.proteckapp.model.api.CategoryResponse;
import com.webfarmatics.proteckapp.model.api.IssueDetailsRequest;
import com.webfarmatics.proteckapp.model.api.IssueDetailsResponse;
import com.webfarmatics.proteckapp.model.api.IssueHistoryRequest;
import com.webfarmatics.proteckapp.model.api.IssueHistoryResponse;
import com.webfarmatics.proteckapp.model.api.LoginUserRequest;
import com.webfarmatics.proteckapp.model.api.LoginUserResponse;
import com.webfarmatics.proteckapp.model.api.ModelRequest;
import com.webfarmatics.proteckapp.model.api.ModelResponse;
import com.webfarmatics.proteckapp.model.api.RaiseIssueRequest;
import com.webfarmatics.proteckapp.model.api.RaiseIssueResponse;
import com.webfarmatics.proteckapp.model.api.SaveUserRequest;
import com.webfarmatics.proteckapp.model.api.SaveUserResponse;
import com.webfarmatics.proteckapp.model.api.StrIssueRequest;
import com.webfarmatics.proteckapp.model.api.StrIssueResponse;
import com.webfarmatics.proteckapp.model.api.UpdateUserRequest;
import com.webfarmatics.proteckapp.model.api.UpdateUserResponse;
import com.webfarmatics.proteckapp.model.api.UserProfileRequest;
import com.webfarmatics.proteckapp.model.api.UserProfileResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ApiInterface {

    @GET("service/Category/getCategoryList")
    Call<CategoryResponse> getCategoryList();

    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @POST("getBrandsList")
    Call<BrandResponse> getBrandList(@Body BrandRequest request);


    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @POST("getIssueList")
    Call<StrIssueResponse> getIssueList(@Body StrIssueRequest request);


    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @POST("getmodelList")
    Call<ModelResponse> getmodelList(@Body ModelRequest request);

    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @POST("saveUserDetails")
    Call<SaveUserResponse> saveUserDetails(@Body SaveUserRequest request);


    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @POST("getUserProfile")
    Call<UserProfileResponse> getUserProfile(@Body UserProfileRequest request);


    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @POST("updateUserProfile")
    Call<UpdateUserResponse> updateUserProfile(@Body UpdateUserRequest request);


    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @POST("loginUser")
    Call<LoginUserResponse> loginUser(@Body LoginUserRequest request);


    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @POST("raiseUserIssue")
    Call<RaiseIssueResponse> raiseUserIssue(@Body RaiseIssueRequest request);


    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @POST("getUserIssueHistory")
    Call<IssueHistoryResponse> getUserIssueHistory(@Body IssueHistoryRequest request);


    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @POST("getUserIssue")
    Call<IssueDetailsResponse> getIssueDetails(@Body IssueDetailsRequest request);


}
