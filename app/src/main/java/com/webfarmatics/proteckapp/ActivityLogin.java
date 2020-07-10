package com.webfarmatics.proteckapp;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.webfarmatics.proteckapp.utils.CommonUtil;
import com.webfarmatics.proteckapp.utils.GlobalData;
import com.webfarmatics.proteckapp.views.LoginUserPresenter;
import com.webfarmatics.proteckapp.views.UpdateUserIntr;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;


public class ActivityLogin extends AppCompatActivity implements UpdateUserIntr {

    private Context context;
    private GoogleSignInClient mGoogleSignInClient;
    private EditText edtLEmail, edtLPassword;
    private String emailID;
    private LoginUserPresenter presenter;
    private boolean allPermissionsGranted = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_one);

        context = ActivityLogin.this;
        presenter = new LoginUserPresenter(context, this);
        edtLEmail = findViewById(R.id.edtLEmail);
        edtLPassword = findViewById(R.id.edtLPassword);

        GlobalData.setProgressDialog(context);

        if (checkPermission()) {
            allPermissionsGranted = true;
        } else {
            Toast.makeText(getApplicationContext(), "Please grant location permission", Toast.LENGTH_SHORT).show();
            requestPermission();
        }


    }

    private boolean checkPermission() {

        int result = ContextCompat.checkSelfPermission(getApplicationContext(), ACCESS_FINE_LOCATION);
        int result1 = ContextCompat.checkSelfPermission(getApplicationContext(), ACCESS_COARSE_LOCATION);
        int result2 = ContextCompat.checkSelfPermission(getApplicationContext(), CAMERA);
        int result3 = ContextCompat.checkSelfPermission(getApplicationContext(), WRITE_EXTERNAL_STORAGE);
        return result == PackageManager.PERMISSION_GRANTED && result1 == PackageManager.PERMISSION_GRANTED
                && result2 == PackageManager.PERMISSION_GRANTED && result3 == PackageManager.PERMISSION_GRANTED;

    }


    private static final int PERMISSIONS_REQUEST_CAMERA = 10;

    private void requestPermission() {
        ActivityCompat.requestPermissions(this, new String[]{ACCESS_FINE_LOCATION, ACCESS_COARSE_LOCATION, CAMERA, WRITE_EXTERNAL_STORAGE}, PERMISSIONS_REQUEST_CAMERA);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {


        if (grantResults.length > 0) {

            boolean locationFAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
            boolean locationCAccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED;
            boolean cameraAccepted = grantResults[2] == PackageManager.PERMISSION_GRANTED;
            boolean extStorageAccepted = grantResults[3] == PackageManager.PERMISSION_GRANTED;


            if (locationFAccepted && locationCAccepted && cameraAccepted && extStorageAccepted) {
                allPermissionsGranted = true;
            } else {

                Snackbar.make(edtLEmail, "To use this functionality, You need to grant all the permissions.", Snackbar.LENGTH_LONG).show();

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (shouldShowRequestPermissionRationale(ACCESS_FINE_LOCATION)) {
                        showMessageOKCancel("You need to allow access to both the permissions",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                            requestPermissions(new String[]{ACCESS_FINE_LOCATION, CAMERA, WRITE_EXTERNAL_STORAGE},
                                                    PERMISSIONS_REQUEST_CAMERA);
                                        }
                                    }
                                });
                        return;
                    }
                }

            }

        }


    }


    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(context)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    public void login(View view) {

        final String email = CommonUtil.getSharePreferenceString(context, GlobalData.EMAIL, "0");
        final String password = CommonUtil.getSharePreferenceString(context, GlobalData.PASSWORD, "0");

        final String e = edtLEmail.getText().toString();
        final String p = edtLPassword.getText().toString();

        if (allPermissionsGranted) {
            presenter.userLogin(e, p);
        }else{
            Snackbar.make(edtLEmail, "To use this apps functionality, You need to grant all the permissions.", Snackbar.LENGTH_LONG).show();
        }

    }


    private static final int RC_SIGN_IN = 007;

    public void signInGoogle(View view) {

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(context, gso);

        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);

    }

    private static final String TAG = "ActivityLogin";


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {

            Log.e(TAG, "onActivityResult: requestCode == RC_SIGN_IN");
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        } else {
            Log.e(TAG, "onActivityResult: !!!!!!!! requestCode == RC_SIGN_IN");
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {

        Log.e(TAG, "handleSignInResult: completedTask");

        try {

            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            String personName = account.getDisplayName();
            emailID = account.getEmail();

            Log.e(TAG, "Person name : " + personName + "Email : " + emailID);

//            makeGoogleSignInRequest(personName, "", emailID);

            // Signed in successfully, show authenticated UI.
//            updateUI(account);
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());
//            updateUI(null);
        }

    }


    public void register(View view) {
        Intent intent = new Intent(ActivityLogin.this, ActivityRegisterOne.class);
        intent.putExtra("email", emailID);
        startActivity(intent);
    }

    public void mainActivity(View view) {

//        Intent intent = new Intent(ActivityLogin.this, MainActivity.class);
//        startActivity(intent);

    }

    @Override
    public void handleResult(String userId) {
        CommonUtil.setSharePreferenceString(context, GlobalData.USER_ID, userId);
        Intent intent = new Intent(ActivityLogin.this, ActivityRaiseIssue.class);
        startActivity(intent);
        finish();
    }

    public void forgotPassword(View view) {
        Intent intent = new Intent(ActivityLogin.this, ActivityForgotPassword.class);
        startActivity(intent);
    }
}
