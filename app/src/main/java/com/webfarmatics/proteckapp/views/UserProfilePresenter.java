package com.webfarmatics.proteckapp.views;

import android.content.Context;
import android.util.Log;

import com.webfarmatics.proteckapp.R;
import com.webfarmatics.proteckapp.model.api.UserProfileRequest;
import com.webfarmatics.proteckapp.model.api.UserProfileResponse;
import com.webfarmatics.proteckapp.network.ApiClient;
import com.webfarmatics.proteckapp.network.ApiInterface;
import com.webfarmatics.proteckapp.utils.GlobalData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserProfilePresenter {

    private Context context;
    private UserProfileIntr view;


    public UserProfilePresenter(Context context, UserProfileIntr view) {
        this.context = context;
        this.view = view;
    }


    public void getUserProfile(String userId) {

        final UserProfileRequest request = new UserProfileRequest(userId);

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<UserProfileResponse> call = apiService.getUserProfile(request);

        GlobalData.setProgressDialog(context);
        GlobalData.showDialog();

        call.enqueue(new Callback<UserProfileResponse>() {
            @Override
            public void onResponse(Call<UserProfileResponse> call, Response<UserProfileResponse> response) {

                GlobalData.hideDialog();

                if (response.body() == null) {
                    GlobalData.toastMessage(context, context.getResources().getString(R.string.dia_sv_nt_msg));
                    return;
                }

                String CODE = response.body().getResponseCode();
                if (CODE == null) {
                    GlobalData.toastMessage(context, "Internal Server Error..!");
                    return;
                }

                if (CODE.equalsIgnoreCase(GlobalData.RESPONSE_CODE_200)) {

//                    GlobalData.toastMessage(context, response.body().getResponseMessage());
                    view.setUserProfileDetails(response.body().getUser());

                } else {
                    GlobalData.toastMessage(context, response.body().getResponseMessage());
                }

            }

            @Override
            public void onFailure(Call<UserProfileResponse> call, Throwable t) {
                GlobalData.toastMessage(context, context.getResources().getString(R.string.dia_sv_nt_msg));
                GlobalData.hideDialog();
                t.printStackTrace();
                Log.e("tag", "" + t.getMessage());
            }
        });


    }

}
