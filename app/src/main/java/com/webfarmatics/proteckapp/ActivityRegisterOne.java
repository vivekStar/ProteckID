package com.webfarmatics.proteckapp;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.material.snackbar.Snackbar;
import com.webfarmatics.proteckapp.adapters.StringSpinnerAdapter;
import com.webfarmatics.proteckapp.model.User;
import com.webfarmatics.proteckapp.utils.CommonUtil;
import com.webfarmatics.proteckapp.utils.Config;
import com.webfarmatics.proteckapp.utils.DataGenerator;
import com.webfarmatics.proteckapp.utils.GlobalData;
import com.webfarmatics.proteckapp.utils.Validator;
import com.webfarmatics.proteckapp.views.RegisterUserIntr;
import com.webfarmatics.proteckapp.views.RegisterUserPresenter;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class ActivityRegisterOne extends AppCompatActivity implements RegisterUserIntr {

    private static final String TAG = "RegisterActivity";

    private Context context;
    private EditText edtName, edtEmail, edtMobile, edtCity, edtZipCode, edtPassword, edtCPassword, edtAddress, edtMapLoc;
    private Button btnSave;
    private ProgressDialog pDialog;
    private Spinner spinnerState;
    private ImageView imgLocateMe;

    private String stateSel = "", email = "", password = "";
    private boolean stateSelected = false;

    private LocationManager locationManager;
    private Location updatedLocation;
    private boolean isGpsEnabled = false, isNetworkEnabled = false;

    private int MIN_TIME_BETWEEN_UPDATES = 1000 * 60 * 1;
    private int MIN_DISTANCE_BETWEEN_UPDATES = 10;

    private android.location.LocationListener locationListener1;
    private String lattitude, longitude;
    private RegisterUserPresenter presenter;
    private final static int REQUEST_CHECK_SETTINGS_GPS = 0x12;
    private String name;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_one);

        initialize();

        if (checkPermission()) {
            checkUserLocation();
        } else {
            Toast.makeText(getApplicationContext(), "Please grant location permission", Toast.LENGTH_SHORT).show();
            requestPermission();
        }

        imgLocateMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setUserLocation();
            }
        });


        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (validateData() && checkPermission()) {

                    String name = edtName.getText().toString();
                    String email = edtEmail.getText().toString();
                    String contact = edtMobile.getText().toString();
                    String city = edtCity.getText().toString();
                    String zipCode = edtZipCode.getText().toString();
                    String password = edtPassword.getText().toString();
                    String adress = edtAddress.getText().toString();
                    String locationName = edtMapLoc.getText().toString();

                    SharedPreferences pref = getApplicationContext().getSharedPreferences(Config.SHARED_PREF, 0);
                    String token = pref.getString(GlobalData.USER_TOKEN, "0");
                    Log.e(TAG, "onClick:Token  "+token );

                    User user = new User("", name, email, contact, adress, city, stateSel, zipCode, "", password, locationName, lattitude, longitude, token);
                    presenter.saveUserDetails(user);

                } else {
                    Snackbar.make(edtMapLoc, "To use this functionality, You need to grant all the permissions.", Snackbar.LENGTH_LONG).show();
                    requestPermission();
                }
            }
        });

    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("name", edtName.getText().toString());
        outState.putString("email", edtEmail.getText().toString());
        outState.putString("mobile", edtMobile.getText().toString());
        outState.putString("city", edtCity.getText().toString());
        outState.putString("zipcode", edtZipCode.getText().toString());
        outState.putString("password", edtPassword.getText().toString());
        outState.putString("address", edtAddress.getText().toString());
        outState.putString("maplocation", edtMapLoc.getText().toString());
    }


    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        edtName.setText(savedInstanceState.getString("name"));
        edtEmail.setText(savedInstanceState.getString("email"));
        edtMobile.setText(savedInstanceState.getString("mobile"));
        edtCity.setText(savedInstanceState.getString("city"));
        edtZipCode.setText(savedInstanceState.getString("zipcode"));
        edtPassword.setText(savedInstanceState.getString("password"));
        edtAddress.setText(savedInstanceState.getString("address"));
        edtMapLoc.setText(savedInstanceState.getString("maplocation"));
    }

    private void initialize() {


        context = ActivityRegisterOne.this;
        presenter = new RegisterUserPresenter(context, this);
        locationManager = (LocationManager) context.getSystemService(LOCATION_SERVICE);

        pDialog = new ProgressDialog(context);
        pDialog.setIndeterminate(true);
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(false);

        btnSave = findViewById(R.id.btnSave);

        edtName = findViewById(R.id.edtName);
        edtEmail = findViewById(R.id.edtEmail);
        edtMobile = findViewById(R.id.edtMobile);
        edtAddress = findViewById(R.id.edtAddress);

        edtCity = findViewById(R.id.edtCity);
        edtZipCode = findViewById(R.id.edtZipCode);
        edtPassword = findViewById(R.id.edtPassword);
        edtCPassword = findViewById(R.id.edtCPassword);

        edtMapLoc = findViewById(R.id.edtMapLoc);
        imgLocateMe = findViewById(R.id.imgLocateMe);

        spinnerState = findViewById(R.id.spinnerState);

        final List<String> states = DataGenerator.getStateList(context);
        StringSpinnerAdapter adapter = new StringSpinnerAdapter(context, states);
        spinnerState.setAdapter(adapter);

        locationListener1 = new android.location.LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                updatedLocation = location;
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };


        spinnerState.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (view != null) {
                    TextView textState = view.findViewById(R.id.tv_title);
                    textState.setTextColor(getResources().getColor(R.color.white));

                    stateSel = states.get(position);
                    if (stateSel.equals("State"))
                        stateSelected = false;
                    else
                        stateSelected = true;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


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
                checkUserLocation();
            } else {

                Snackbar.make(edtMapLoc, "To use this functionality, You need to grant all the permissions.", Snackbar.LENGTH_LONG).show();

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                    if (shouldShowRequestPermissionRationale(ACCESS_FINE_LOCATION)) {
                        showMessageOKCancel("You need to allow access to both the permissions",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                            requestPermissions(new String[]{ACCESS_FINE_LOCATION, CAMERA},
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

    private void checkUserLocation() {

        isGpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        Log.e(TAG, "setUserLocation: " + isNetworkEnabled);
        Log.e(TAG, "setUserLocation: " + isGpsEnabled);

        if (isGpsEnabled) {

            if (ActivityCompat.checkSelfPermission(this, ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }

            locationManager.requestLocationUpdates(
                    LocationManager.NETWORK_PROVIDER,
                    MIN_TIME_BETWEEN_UPDATES,
                    MIN_DISTANCE_BETWEEN_UPDATES, locationListener1);


            if (locationManager != null) {
                updatedLocation = locationManager
                        .getLastKnownLocation(LocationManager.GPS_PROVIDER);
                updateGPSCoordinates();
            }

            setUserLocation();

        } else if (isNetworkEnabled) {

            locationManager.requestLocationUpdates(
                    LocationManager.NETWORK_PROVIDER,
                    MIN_TIME_BETWEEN_UPDATES,
                    MIN_DISTANCE_BETWEEN_UPDATES, locationListener1);


            if (locationManager != null) {
                updatedLocation = locationManager
                        .getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                updateGPSCoordinates();
            }

            setUserLocation();

        } else {
            Toast.makeText(getApplicationContext(), "User location unavailable", Toast.LENGTH_SHORT).show();

        }
    }

    private void updateGPSCoordinates() {

        if (updatedLocation != null) {
            Log.e(TAG, "onLocationChanged: " + updatedLocation.getLatitude());
            Log.e(TAG, "onLocationChanged: " + updatedLocation.getLongitude());
        }

    }


    private boolean validateData() {

        if (edtName.getText().toString().isEmpty()) {
            edtName.setError("Required");
            Toast.makeText(context, "Enter Correct Name", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (edtEmail.getText().toString().isEmpty()) {
            edtEmail.setError("Required");
            Toast.makeText(context, "Enter Correct Email", Toast.LENGTH_SHORT).show();
            return false;
        }
        email = edtEmail.getText().toString();
        int valid = Validator.validateEmail(email);
        if (valid != Validator.VALID) {
            edtEmail.setText("");
            Toast.makeText(context, "Enter Correct Email", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (edtMobile.getText().toString().isEmpty()) {
            edtMobile.setError("Required");
            Toast.makeText(context, "Enter Correct Mobile No", Toast.LENGTH_SHORT).show();
            return false;
        }

        String phone = edtMobile.getText().toString();
        int result = Validator.validatePhoneNumber(phone);
        if (result != Validator.VALID) {
            edtMobile.setError("Required");
            Toast.makeText(context, "Enter Correct Mobile No", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (edtAddress.getText().toString().isEmpty()) {
            edtAddress.setError("Required");
            Toast.makeText(context, "Enter Correct Address", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (edtCity.getText().toString().isEmpty()) {
            edtCity.setError("Required");
            Toast.makeText(context, "Enter Correct City Name", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!stateSelected) {
            Toast.makeText(context, "Select Correct State", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (edtZipCode.getText().toString().isEmpty()) {
            edtZipCode.setError("Required");
            Toast.makeText(context, "Enter Correct ZipCode", Toast.LENGTH_SHORT).show();
            return false;
        }

        String zip = edtZipCode.getText().toString();
        int validZ = Validator.validateZipCode(zip);
        if (validZ != Validator.VALID) {
            edtZipCode.setError("Required");
            Toast.makeText(context, "Enter Correct ZipCode", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (edtPassword.getText().toString().isEmpty()) {
            edtPassword.setError("Required");
            Toast.makeText(context, "Enter Correct Password", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (edtCPassword.getText().toString().isEmpty()) {
            edtCPassword.setError("Required");
            Toast.makeText(context, "Enter Correct Password", Toast.LENGTH_SHORT).show();
            return false;
        }
        password = edtPassword.getText().toString();
        String p2 = edtCPassword.getText().toString();
        if (!password.equals(p2)) {
            Toast.makeText(context, "Password doesn't match..", Toast.LENGTH_SHORT).show();
            edtPassword.setText("");
            edtCPassword.setText("");
            return false;
        }


        valid = Validator.validateEditText(edtMapLoc);

        if (valid != Validator.VALID) {
            Toast.makeText(getApplicationContext(), "Please Enable ..", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private void setUserLocation() {

        if (isGpsEnabled || isNetworkEnabled) {

            if (updatedLocation == null) {
                Toast.makeText(getApplicationContext(), "User location unavailable", Toast.LENGTH_SHORT).show();
                return;
            }

            Geocoder geocoder = new Geocoder(context, Locale.getDefault());

            try {

                lattitude = "" + updatedLocation.getLatitude();
                longitude = "" + updatedLocation.getLongitude();

                List<Address> addressList = geocoder.getFromLocation(updatedLocation.getLatitude(), updatedLocation.getLongitude(), 3);
                String subLoc = "", fecName = "", addsLine = "";

                String preSubLoc = "N/A", preFecName = "N/A", preAddressLine = "N/A";

                if (addressList.size() > 0) {

                    for (int i = 0; i < addressList.size(); i++) {

                        Address address = addressList.get(i);
                        String subLocality = address.getSubLocality();
                        String featureName = address.getFeatureName();
                        String addressLine = address.getAddressLine(0);

                        if (subLocality != null) {
                            if (subLoc.length() < subLocality.length()) {
                                subLoc = subLocality;
                            }
                        }

                        if (featureName != null && featureName.length() > 5) {
                            if (fecName.length() < featureName.length()) {
                                fecName = featureName;
                            }
                        }

                        if (addressLine != null && addressLine.length() > 5) {
                            if (addsLine.length() < addressLine.length()) {
                                addsLine = addressLine;
                            }
                        }
                    }

                    if (!subLoc.equalsIgnoreCase(preSubLoc)) {
                        preSubLoc = subLoc;
                        preAddressLine = addsLine;
                    }

                    if (!addsLine.equalsIgnoreCase(preAddressLine)) {
                        preAddressLine = addsLine;
                    }

                }

                String address = preAddressLine + preSubLoc;
                edtMapLoc.setText(address);

            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }

    @Override
    public void handleResult(String userId) {

        CommonUtil.setSharePreferenceString(context, GlobalData.USER_ID, userId);
        Intent intent = new Intent(ActivityRegisterOne.this, ActivityLogin.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra(GlobalData.USER_ID, userId);
        startActivity(intent);

    }
}
