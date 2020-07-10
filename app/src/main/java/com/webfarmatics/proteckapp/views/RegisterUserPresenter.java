package com.webfarmatics.proteckapp.views;

import android.content.Context;
import android.util.Log;

import com.webfarmatics.proteckapp.R;
import com.webfarmatics.proteckapp.model.User;
import com.webfarmatics.proteckapp.model.api.SaveUserRequest;
import com.webfarmatics.proteckapp.model.api.SaveUserResponse;
import com.webfarmatics.proteckapp.network.ApiClient;
import com.webfarmatics.proteckapp.network.ApiInterface;
import com.webfarmatics.proteckapp.utils.GlobalData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterUserPresenter {

    private Context context;
    private RegisterUserIntr view;


    public RegisterUserPresenter(Context context, RegisterUserIntr view) {
        this.context = context;
        this.view = view;
    }


    public void saveUserDetails(User user) {

        final SaveUserRequest request = new SaveUserRequest();
        request.setUser(user);

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<SaveUserResponse> call = apiService.saveUserDetails(request);

        GlobalData.setProgressDialog(context);
        GlobalData.showDialog();

        call.enqueue(new Callback<SaveUserResponse>() {
            @Override
            public void onResponse(Call<SaveUserResponse> call, Response<SaveUserResponse> response) {

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

                    GlobalData.toastMessage(context, response.body().getResponseMessage());
                    view.handleResult(response.body().getUserId());

                } else {
                    GlobalData.toastMessage(context, response.body().getResponseMessage());
                }

            }

            @Override
            public void onFailure(Call<SaveUserResponse> call, Throwable t) {
                GlobalData.toastMessage(context, context.getResources().getString(R.string.dia_sv_nt_msg));
                GlobalData.hideDialog();
                t.printStackTrace();
                Log.e("tag", "" + t.getMessage());
            }
        });


    }

}
