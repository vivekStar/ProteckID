package com.webfarmatics.proteckapp.views;

import android.content.Context;
import android.util.Log;

import com.webfarmatics.proteckapp.R;
import com.webfarmatics.proteckapp.model.Issue;
import com.webfarmatics.proteckapp.model.api.BrandRequest;
import com.webfarmatics.proteckapp.model.api.BrandResponse;
import com.webfarmatics.proteckapp.model.api.CategoryResponse;
import com.webfarmatics.proteckapp.model.api.ModelRequest;
import com.webfarmatics.proteckapp.model.api.ModelResponse;
import com.webfarmatics.proteckapp.model.api.RaiseIssueRequest;
import com.webfarmatics.proteckapp.model.api.RaiseIssueResponse;
import com.webfarmatics.proteckapp.model.api.StrIssueRequest;
import com.webfarmatics.proteckapp.model.api.StrIssueResponse;
import com.webfarmatics.proteckapp.network.ApiClient;
import com.webfarmatics.proteckapp.network.ApiInterface;
import com.webfarmatics.proteckapp.utils.GlobalData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RaiseIssuePresenter {

    private Context context;
    private RaiseIssueIntr view;

    public RaiseIssuePresenter(Context context, RaiseIssueIntr view) {
        this.context = context;
        this.view = view;
    }


    public void fetchCategoryList() {

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<CategoryResponse> call = apiService.getCategoryList();

        GlobalData.setProgressDialog(context);
        GlobalData.showDialog();

        call.enqueue(new Callback<CategoryResponse>() {
            @Override
            public void onResponse(Call<CategoryResponse> call, Response<CategoryResponse> response) {

                GlobalData.hideDialog();

                if (response.body() == null) {
                    GlobalData.toastMessage(context, context.getResources().getString(R.string.dia_sv_nt_msg));
                    return;
                }

                String CODE = response.body().getResponseCode();
                if (CODE == null) {
                    GlobalData.toastMessage(context, "Category List error");
                    return;
                }

                if (CODE.equalsIgnoreCase(GlobalData.RESPONSE_CODE_200)) {

                    view.setCategories(response.body().getCategoryList());

                } else {
                    view.setCategoryError();
                    GlobalData.toastMessage(context, "No Categories available.!");
                    Log.e("code", "--------" + response.body().getResponseMessage());
                    Log.e("code", "--------" + response.body().getResponseCode());
                }

            }

            @Override
            public void onFailure(Call<CategoryResponse> call, Throwable t) {
                GlobalData.toastMessage(context, context.getResources().getString(R.string.dia_sv_nt_msg));
                GlobalData.hideDialog();
                Log.e("code", "400--------");
                Log.e("TAG", t.toString());
                t.printStackTrace();
            }
        });

    }

    public void fetchBrandsList(int catId) {

        BrandRequest request = new BrandRequest("" + catId);

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<BrandResponse> call = apiService.getBrandList(request);

        GlobalData.setProgressDialog(context);
        GlobalData.showDialog();

        call.enqueue(new Callback<BrandResponse>() {
            @Override
            public void onResponse(Call<BrandResponse> call, Response<BrandResponse> response) {

                GlobalData.hideDialog();

                if (response.body() == null) {
                    GlobalData.toastMessage(context, context.getResources().getString(R.string.dia_sv_nt_msg));
                    return;
                }

                String CODE = response.body().getResponseCode();
                if (CODE == null) {
                    GlobalData.toastMessage(context, "Brand List error");
                    return;
                }

                if (CODE.equalsIgnoreCase(GlobalData.RESPONSE_CODE_200)) {

                    view.setBrands(response.body().getBrandList());

                } else {
                    view.setBrandListError();
                    GlobalData.toastMessage(context, "No Brands available.!");
                    Log.e("code", "--------" + response.body().getResponseMessage());
                    Log.e("code", "--------" + response.body().getResponseCode());
                }

            }

            @Override
            public void onFailure(Call<BrandResponse> call, Throwable t) {
                GlobalData.toastMessage(context, context.getResources().getString(R.string.dia_sv_nt_msg));
                GlobalData.hideDialog();
                Log.e("code", "400--------");
                Log.e("TAG", t.toString());
                t.printStackTrace();
            }
        });

    }


    public void fetchStrIssueList(int catId) {

        StrIssueRequest request = new StrIssueRequest("" + catId);

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<StrIssueResponse> call = apiService.getIssueList(request);

        call.enqueue(new Callback<StrIssueResponse>() {
            @Override
            public void onResponse(Call<StrIssueResponse> call, Response<StrIssueResponse> response) {

                if (response.body() == null) {
                    GlobalData.toastMessage(context, context.getResources().getString(R.string.dia_sv_nt_msg));
                    return;
                }

                String CODE = response.body().getResponseCode();
                if (CODE == null) {
                    GlobalData.toastMessage(context, "Issue List error");
                    return;
                }

                if (CODE.equalsIgnoreCase(GlobalData.RESPONSE_CODE_200)) {

                    view.setIssues(response.body().getIssueList());

                } else {
                    view.setBrandListError();
                    GlobalData.toastMessage(context, "No Issues available.!");
                    Log.e("code", "--------" + response.body().getResponseMessage());
                    Log.e("code", "--------" + response.body().getResponseCode());
                }

            }

            @Override
            public void onFailure(Call<StrIssueResponse> call, Throwable t) {
                GlobalData.toastMessage(context, context.getResources().getString(R.string.dia_sv_nt_msg));
                Log.e("code", "400--------");
                Log.e("TAG", t.toString());
                t.printStackTrace();
            }
        });

    }

    public void fetchModelsList(int brandId) {

        ModelRequest request = new ModelRequest("" + brandId);

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<ModelResponse> call = apiService.getmodelList(request);

        GlobalData.setProgressDialog(context);
        GlobalData.showDialog();

        call.enqueue(new Callback<ModelResponse>() {
            @Override
            public void onResponse(Call<ModelResponse> call, Response<ModelResponse> response) {

                GlobalData.hideDialog();

                if (response.body() == null) {
                    GlobalData.toastMessage(context, context.getResources().getString(R.string.dia_sv_nt_msg));
                    return;
                }

                String CODE = response.body().getResponseCode();
                if (CODE == null) {
                    GlobalData.toastMessage(context, "Models List error");
                    return;
                }

                if (CODE.equalsIgnoreCase(GlobalData.RESPONSE_CODE_200)) {

                    view.setModels(response.body().getModelList());

                } else {
                    view.setModelListError();
                    GlobalData.toastMessage(context, "No Models available.!");
                    Log.e("code", "--------" + response.body().getResponseMessage());
                    Log.e("code", "--------" + response.body().getResponseCode());
                }

            }

            @Override
            public void onFailure(Call<ModelResponse> call, Throwable t) {
                GlobalData.toastMessage(context, context.getResources().getString(R.string.dia_sv_nt_msg));
                GlobalData.hideDialog();
                Log.e("code", "400--------");
                Log.e("TAG", t.toString());
                t.printStackTrace();
            }
        });

    }

    public void raiseIssue(Issue issue) {

        RaiseIssueRequest request = new RaiseIssueRequest(issue);
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<RaiseIssueResponse> call = apiService.raiseUserIssue(request);

        GlobalData.setProgressDialog(context);
        GlobalData.showDialog();

        call.enqueue(new Callback<RaiseIssueResponse>() {
            @Override
            public void onResponse(Call<RaiseIssueResponse> call, Response<RaiseIssueResponse> response) {

                GlobalData.hideDialog();

                if (response.body() == null) {
                    GlobalData.toastMessage(context, context.getResources().getString(R.string.dia_sv_nt_msg));
                    return;
                }

                String CODE = response.body().getResponseCode();
                if (CODE == null) {
                    GlobalData.toastMessage(context, "Server Error..!!");
                    return;
                }

                if (CODE.equalsIgnoreCase(GlobalData.RESPONSE_CODE_200)) {

                    view.onIssueSubmit();

                } else {
                    GlobalData.toastMessage(context, "No Brands available.!");
                }

            }

            @Override
            public void onFailure(Call<RaiseIssueResponse> call, Throwable t) {
                GlobalData.toastMessage(context, context.getResources().getString(R.string.dia_sv_nt_msg));
                GlobalData.hideDialog();
                Log.e("code", "400--------");
                Log.e("TAG", t.toString());
                t.printStackTrace();
            }
        });


    }


}
