package com.webfarmatics.proteckapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.webfarmatics.proteckapp.adapters.IssueListAdapter;
import com.webfarmatics.proteckapp.model.Issue;
import com.webfarmatics.proteckapp.model.IssueResp;
import com.webfarmatics.proteckapp.model.api.IssueHistoryRequest;
import com.webfarmatics.proteckapp.model.api.IssueHistoryResponse;
import com.webfarmatics.proteckapp.model.api.ModelRequest;
import com.webfarmatics.proteckapp.model.api.ModelResponse;
import com.webfarmatics.proteckapp.model.api.UserProfileRequest;
import com.webfarmatics.proteckapp.model.api.UserProfileResponse;
import com.webfarmatics.proteckapp.network.ApiClient;
import com.webfarmatics.proteckapp.network.ApiInterface;
import com.webfarmatics.proteckapp.utils.CommonUtil;
import com.webfarmatics.proteckapp.utils.GlobalData;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityHistory extends AppCompatActivity {

    private Context context;
    private ListView llIssues;
    private List<IssueResp> issueList = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        context = ActivityHistory.this;
        llIssues = findViewById(R.id.llIssues);
        String userId = CommonUtil.getSharePreferenceString(context, GlobalData.USER_ID, "0");
        getUserIssueHistory(userId);


        llIssues.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int issueId = issueList.get(position).getIssueId();

                Intent intent = new Intent(ActivityHistory.this, ActivityIssueDetails.class);
                intent.putExtra("issueId", issueId);
                startActivity(intent);
            }
        });



    }


    public void getUserIssueHistory(String userId) {

        final IssueHistoryRequest request = new IssueHistoryRequest(userId);

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<IssueHistoryResponse> call = apiService.getUserIssueHistory(request);

        GlobalData.setProgressDialog(context);
        GlobalData.showDialog();

        call.enqueue(new Callback<IssueHistoryResponse>() {
            @Override
            public void onResponse(Call<IssueHistoryResponse> call, Response<IssueHistoryResponse> response) {

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

                    issueList = response.body().getIssueList();
                    IssueListAdapter adapter = new IssueListAdapter(context, issueList);
                    llIssues.setAdapter(adapter);


                } else {
                    GlobalData.toastMessage(context, response.body().getResponseMessage());
                }

            }

            @Override
            public void onFailure(Call<IssueHistoryResponse> call, Throwable t) {
                GlobalData.toastMessage(context, context.getResources().getString(R.string.dia_sv_nt_msg));
                GlobalData.hideDialog();
                t.printStackTrace();
                Log.e("tag", "" + t.getMessage());
            }
        });


    }


}
