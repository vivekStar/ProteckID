package com.webfarmatics.proteckapp.views;

import android.content.Context;
import android.util.Log;

import com.webfarmatics.proteckapp.R;
import com.webfarmatics.proteckapp.model.api.IssueDetailsRequest;
import com.webfarmatics.proteckapp.model.api.IssueDetailsResponse;
import com.webfarmatics.proteckapp.model.api.LoginUserRequest;
import com.webfarmatics.proteckapp.model.api.LoginUserResponse;
import com.webfarmatics.proteckapp.network.ApiClient;
import com.webfarmatics.proteckapp.network.ApiInterface;
import com.webfarmatics.proteckapp.utils.GlobalData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class IssueDetailsPresenter {


    private Context context;
    private IssueDetailsIntr view;


    public IssueDetailsPresenter(Context context, IssueDetailsIntr view) {
        this.context = context;
        this.view = view;
    }

    public void fetchIssueDetails(String issueid, String userid) {

        int isId = Integer.parseInt(issueid);
        int usId = Integer.parseInt(userid);

        final IssueDetailsRequest request = new IssueDetailsRequest(isId, usId);

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<IssueDetailsResponse> call = apiService.getIssueDetails(request);

        GlobalData.setProgressDialog(context);
        GlobalData.showDialog();

        call.enqueue(new Callback<IssueDetailsResponse>() {
            @Override
            public void onResponse(Call<IssueDetailsResponse> call, Response<IssueDetailsResponse> response) {

                GlobalData.hideDialog();

                if (response.body() == null) {
                    GlobalData.toastMessage(context, context.getResources().getString(R.string.dia_sv_nt_msg));
                    return;
                }

                int CODE = response.body().getResponseCode();
                if (CODE == 0) {
                    GlobalData.toastMessage(context, "Internal Server Error..!");
                    return;
                }

                if (CODE == 200) {

//                    GlobalData.toastMessage(context, response.body().getResponseMessage());
                    view.setIssueDetails(response.body().getIssue());

                } else {
                    GlobalData.toastMessage(context, response.body().getResponseMessage());
                }

            }

            @Override
            public void onFailure(Call<IssueDetailsResponse> call, Throwable t) {
                GlobalData.toastMessage(context, context.getResources().getString(R.string.dia_sv_nt_msg));
                GlobalData.hideDialog();
                t.printStackTrace();
                Log.e("tag", "" + t.getMessage());
            }
        });


    }

}
