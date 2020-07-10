package com.webfarmatics.proteckapp;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;
import com.webfarmatics.proteckapp.model.IssueDetails;
import com.webfarmatics.proteckapp.utils.CommonUtil;
import com.webfarmatics.proteckapp.utils.GlobalData;
import com.webfarmatics.proteckapp.views.IssueDetailsIntr;
import com.webfarmatics.proteckapp.views.IssueDetailsPresenter;

public class ActivityIssueDetails extends AppCompatActivity implements IssueDetailsIntr {

    private Context context;
    private LinearLayout llPayDate, llPayTobe;
    private View vp;
    private ImageView imageView;
    private TextView tvCategory, tvModel, tvBrand, tvIssue, tvDescription, tvDate, tvPayStatus, tvPayDate, tvPayTobe;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_issue_details);

        context = ActivityIssueDetails.this;
        llPayDate = findViewById(R.id.llPayDate);
        llPayTobe = findViewById(R.id.llPayTobe);
        tvCategory = findViewById(R.id.tvCategory);
        vp = findViewById(R.id.vp);
        tvModel = findViewById(R.id.tvModel);
        tvBrand = findViewById(R.id.tvBrand);
        tvIssue = findViewById(R.id.tvIssue);
        tvDate = findViewById(R.id.tvDate);
        tvPayStatus = findViewById(R.id.tvPayStatus);
        tvPayTobe = findViewById(R.id.tvPayTobe);
        tvPayDate = findViewById(R.id.tvPayDate);
        tvDescription = findViewById(R.id.tvDescription);
        imageView = findViewById(R.id.imageView);

        String issueId = "" + getIntent().getIntExtra("issueId", 0);

        String userId = CommonUtil.getSharePreferenceString(context, GlobalData.USER_ID, "0");

        IssueDetailsPresenter presenter = new IssueDetailsPresenter(context, this);
        presenter.fetchIssueDetails(issueId, userId);

    }

    @Override
    public void setIssueDetails(IssueDetails issue) {
        tvModel.setText(issue.getModel_name());
        tvCategory.setText(issue.getBrand_name());
        tvBrand.setText(issue.getBrand_name());
        tvIssue.setText(issue.getIssue_name());
        tvDate.setText(CommonUtil.getDateToDisplay(issue.getCreated_date()));
        tvDescription.setText(issue.getDescription());

        if (issue.getPayment_date() == null) {
            llPayDate.setVisibility(View.GONE);
            vp.setVisibility(View.GONE);
        } else {
            tvPayDate.setText(CommonUtil.getDateToDisplay(issue.getPayment_date()));
        }


        if (issue.getGrand_total() == 0) {
            llPayTobe.setVisibility(View.GONE);
        } else {
            tvPayTobe.setText(CommonUtil.getDateToDisplay(issue.getPayment_date()));
        }

        tvPayStatus.setText(issue.getPayment_status());

        String image = issue.getWarranty_bill();
        int index = image.lastIndexOf("\\");
        String fileName = image.substring(index + 1);

        String imageurl = GlobalData.ATTACHMENT_PATH + fileName;

        Log.e("TAG", "imageArrList imageurl : " + imageurl);

        Picasso.get()
                .load(imageurl)
                .placeholder(R.drawable.ic_about_us)
                .error(R.drawable.ic_about_us)
                .into(imageView);


    }
}
