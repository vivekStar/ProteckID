package com.webfarmatics.proteckapp.views;

import android.content.Context;
import android.util.Log;

import com.webfarmatics.proteckapp.R;
import com.webfarmatics.proteckapp.model.api.LoginUserRequest;
import com.webfarmatics.proteckapp.model.api.LoginUserResponse;
import com.webfarmatics.proteckapp.model.api.UpdateUserRequest;
import com.webfarmatics.proteckapp.model.api.UpdateUserResponse;
import com.webfarmatics.proteckapp.network.ApiClient;
import com.webfarmatics.proteckapp.network.ApiInterface;
import com.webfarmatics.proteckapp.utils.GlobalData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginUserPresenter {

    private Context context;
    private UpdateUserIntr view;


    public LoginUserPresenter(Context context, UpdateUserIntr view) {
        this.context = context;
        this.view = view;
    }


    public void userLogin(String email, String password) {

        final LoginUserRequest request = new LoginUserRequest(email, password);

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<LoginUserResponse> call = apiService.loginUser(request);

        GlobalData.setProgressDialog(context);
        GlobalData.showDialog();

        call.enqueue(new Callback<LoginUserResponse>() {
            @Override
            public void onResponse(Call<LoginUserResponse> call, Response<LoginUserResponse> response) {

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
                    view.handleResult(response.body().getUserId());

                } else {
                    GlobalData.toastMessage(context, response.body().getResponseMessage());
                }

            }

            @Override
            public void onFailure(Call<LoginUserResponse> call, Throwable t) {
                GlobalData.toastMessage(context, context.getResources().getString(R.string.dia_sv_nt_msg));
                GlobalData.hideDialog();
                t.printStackTrace();
                Log.e("tag", "" + t.getMessage());
            }
        });


    }

}
