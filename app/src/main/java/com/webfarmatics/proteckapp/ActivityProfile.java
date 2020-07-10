package com.webfarmatics.proteckapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.webfarmatics.proteckapp.model.User;
import com.webfarmatics.proteckapp.utils.CommonUtil;
import com.webfarmatics.proteckapp.utils.GlobalData;
import com.webfarmatics.proteckapp.views.UserProfileIntr;
import com.webfarmatics.proteckapp.views.UserProfilePresenter;

public class ActivityProfile extends AppCompatActivity implements UserProfileIntr {

    private Context context;
    private RelativeLayout rlProfile;
    private ProgressBar pb;
    private TextView tvName, tvEmail, tvContactNo, tvCity, tvAddress, tvLocation;
    private ImageView imgEdit;
    private User user;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agent_profile);

        initialize();

        fetchProfileDetails();

        imgEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityProfile.this, ActivityUpdateProfile.class);
                intent.putExtra("user", user);
                startActivityForResult(intent, GlobalData.REQUEST_PROFILE_UPDATE);
            }
        });

    }

    private void initialize() {

        context = ActivityProfile.this;
        pb = findViewById(R.id.fbProgress);
        rlProfile = findViewById(R.id.rlProfile);
        tvName = findViewById(R.id.tvName);
        tvEmail = findViewById(R.id.tvEmail);
        tvContactNo = findViewById(R.id.tvContactNo);
        tvCity = findViewById(R.id.tvCity);
        tvAddress = findViewById(R.id.tvAddress);
        tvLocation = findViewById(R.id.tvLocation);
        imgEdit = findViewById(R.id.imgEdit);

    }

    @Override
    public void setUserProfileDetails(User user) {
        this.user = user;
        tvName.setText(user.getName());
        tvEmail.setText(user.getEmail());
        tvContactNo.setText(user.getContact());
        tvCity.setText(user.getCity());
        tvAddress.setText(user.getAdress());
        tvLocation.setText(user.getLocationName());

        pb.setVisibility(View.GONE);
        rlProfile.setVisibility(View.VISIBLE);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GlobalData.REQUEST_PROFILE_UPDATE && data != null) {
            String resp = data.getStringExtra(GlobalData.RESPONSE_CODE);
            if (resp.equalsIgnoreCase(GlobalData.RESPONSE_CODE_200)) {
                fetchProfileDetails();
            }
        }

    }

    private void fetchProfileDetails() {

        pb.setVisibility(View.VISIBLE);
        rlProfile.setVisibility(View.GONE);

        String userId = CommonUtil.getSharePreferenceString(context, GlobalData.USER_ID, "0");

        UserProfilePresenter presenter = new UserProfilePresenter(context, this);
        presenter.getUserProfile(userId);
    }
}
