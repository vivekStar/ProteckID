package com.webfarmatics.proteckapp;

import android.app.Activity;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.webfarmatics.proteckapp.adapters.BrandsSpinnerAdapter;
import com.webfarmatics.proteckapp.adapters.CategorySpinnerAdapter;
import com.webfarmatics.proteckapp.adapters.ModelsSpinnerAdapter;
import com.webfarmatics.proteckapp.adapters.StrIssueSpinnerAdapter;
import com.webfarmatics.proteckapp.model.Brand;
import com.webfarmatics.proteckapp.model.Category;
import com.webfarmatics.proteckapp.model.CategoryId;
import com.webfarmatics.proteckapp.model.Issue;
import com.webfarmatics.proteckapp.model.Model;
import com.webfarmatics.proteckapp.model.StrIssue;
import com.webfarmatics.proteckapp.utils.Base64;
import com.webfarmatics.proteckapp.utils.CommonUtil;
import com.webfarmatics.proteckapp.utils.CropImage;
import com.webfarmatics.proteckapp.utils.GlobalData;
import com.webfarmatics.proteckapp.utils.InternalStorageContentProvider;
import com.webfarmatics.proteckapp.utils.Validator;
import com.webfarmatics.proteckapp.views.RaiseIssueIntr;
import com.webfarmatics.proteckapp.views.RaiseIssuePresenter;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

public class ActivityRaiseIssue extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener, RaiseIssueIntr {

    private Context context;
    private EditText et_desc;
    private TextView tvBill;
    private Spinner spBrand, spModel, spCat, spIssue;
    private LinearLayout llIntro, llCat, llBr, llNewBr, llMo, llNewMo, llIss, llDesc, llGuarantee;
    private RadioGroup rgG;
    private RadioButton rbYes, rbNo;
    private FrameLayout flBill;
    private ImageButton ib1;

    private EditText et_model, et_brand;
    private Button btnSubmit;
    private ArrayList<Brand> brandsList;
    private ArrayList<Model> modelsList;
    private ArrayList<StrIssue> issueList;
    private ArrayList<Category> categoryList;
    private BrandsSpinnerAdapter brandAdapter;
    private int catId = 0, brandId = 0, modelId = 0, issueId = 0;
    private String catName = "", brandName = "", modelName = "", issueName = "", underGuarantee = "";
    private boolean otherBSele = false, otherMSel = false, modelSelected = false, brandSelected = false,
            catSelected = false, issueSel = false;
    private RaiseIssuePresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_raise_issue);

        initialize();

        (findViewById(R.id.bt_submit)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (validateData()) {

                    String description = et_desc.getText().toString();
                    String userId = CommonUtil.getSharePreferenceString(context, GlobalData.USER_ID, "0");
                    int usId = Integer.parseInt(userId);
                    if (otherBSele) {
                        brandName = et_brand.getText().toString();
                    }

                    if (otherMSel) {
                        modelName = et_model.getText().toString();
                    }

                    CategoryId categoryId = new CategoryId(catId);
                    String attachment1 = ba1_base64;

                    Issue issue = new Issue(issueName, issueId, "" + brandId, "" + modelId, description, usId, brandName, modelName, categoryId, underGuarantee, attachment1);
                    presenter.raiseIssue(issue);
                }
            }
        });


        spBrand.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Brand brand = brandsList.get(position);
                brandName = brand.getBrandName();
                if (brandName.equals("Other")) {
                    otherBSele = true;
                    otherMSel = true;
                    brandId = 0;
                    modelId = 0;
                    llMo.setVisibility(View.GONE);
                    llNewBr.setVisibility(View.VISIBLE);
                    llNewMo.setVisibility(View.VISIBLE);
                } else {
                    brandSelected = true;
                    otherBSele = false;
                    brandId = brand.getBrandId();
                    presenter.fetchModelsList(brand.getBrandId());
                    llNewBr.setVisibility(View.GONE);
                    llNewMo.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        spModel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                Model model = modelsList.get(position);
                modelName = model.getModelName();
                llIss.setVisibility(View.VISIBLE);
                llDesc.setVisibility(View.VISIBLE);
                llGuarantee.setVisibility(View.VISIBLE);
                if (modelName.equals("Other")) {
                    otherMSel = true;
                    modelId = 0;
                    llNewMo.setVisibility(View.VISIBLE);
                } else {
                    modelSelected = true;
                    otherMSel = false;
                    modelId = model.getModelId();
                    llNewMo.setVisibility(View.GONE);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        spCat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                llIntro.setVisibility(View.GONE);
                Category category = categoryList.get(position);
                catName = category.getCategoryName();

                if (catName.equals("Select")) {
                    llIntro.setVisibility(View.VISIBLE);
                    llCat.setVisibility(View.VISIBLE);
                    llBr.setVisibility(View.GONE);
                    llNewBr.setVisibility(View.GONE);
                    llMo.setVisibility(View.GONE);
                    llNewMo.setVisibility(View.GONE);
                    llIss.setVisibility(View.GONE);
                    llDesc.setVisibility(View.GONE);
                } else {

                    catSelected = true;
                    catId = category.getCategoryId();
                    presenter.fetchBrandsList(catId);
                    presenter.fetchStrIssueList(catId);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        spIssue.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                issueSel = true;
                StrIssue issue = issueList.get(position);
                issueName = issue.getIssueName();
                issueId = issue.getIssueId();
                btnSubmit.setVisibility(View.VISIBLE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        rgG.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.rbYes) {
                    tvBill.setVisibility(View.VISIBLE);
                    flBill.setVisibility(View.VISIBLE);
                } else if (checkedId == R.id.rbNo) {
                    tvBill.setVisibility(View.GONE);
                    flBill.setVisibility(View.GONE);
                }
            }
        });


        presenter.fetchCategoryList();


    }

    private void initialize() {

        context = ActivityRaiseIssue.this;
        presenter = new RaiseIssuePresenter(context, this);

        et_desc = findViewById(R.id.et_desc);

        rgG = findViewById(R.id.rgG);
        spCat = findViewById(R.id.spCat);
        spBrand = findViewById(R.id.spBrand);
        spModel = findViewById(R.id.spModel);
        spIssue = findViewById(R.id.spIssue);

        llIntro = findViewById(R.id.llIntro);
        llCat = findViewById(R.id.llCat);
        llBr = findViewById(R.id.llBr);
        llNewBr = findViewById(R.id.llNewBr);
        llMo = findViewById(R.id.llMo);
        llNewMo = findViewById(R.id.llNewMo);
        llIss = findViewById(R.id.llIss);
        llDesc = findViewById(R.id.llDesc);
        llGuarantee = findViewById(R.id.llGuarantee);

        et_model = findViewById(R.id.et_model);
        et_brand = findViewById(R.id.et_brand);

        tvBill = findViewById(R.id.tvBill);
        flBill = findViewById(R.id.flBill);

        rbYes = findViewById(R.id.rbYes);
        rbNo = findViewById(R.id.rbNo);
        ib1 = findViewById(R.id.ib1);


        registerForContextMenu(ib1);

        ib1.setOnClickListener(imgClick);

        ib1.setTag(1);

        btnSubmit = findViewById(R.id.bt_submit);

        BottomNavigationView navigationBar = findViewById(R.id.navigation);
        navigationBar.setOnNavigationItemSelectedListener(this);

    }

    String ba1_base64 = "";

    private boolean validateData() {

        int valid;

        if (!catSelected) {
            Toast.makeText(getApplicationContext(), "Please Select Category..", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (otherBSele) {

            valid = Validator.validateEditText(et_brand);

            if (valid != Validator.VALID) {
                Toast.makeText(getApplicationContext(), "Please Enter Brand..", Toast.LENGTH_SHORT).show();
                return false;
            }

        }

        if (otherMSel) {

            valid = Validator.validateEditText(et_model);

            if (valid != Validator.VALID) {
                Toast.makeText(getApplicationContext(), "Please Enter Model..", Toast.LENGTH_SHORT).show();
                return false;
            }

        }

        if (!otherBSele && !brandSelected) {
            Toast.makeText(getApplicationContext(), "Please Select Brand..", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (!otherMSel && !modelSelected) {
            Toast.makeText(getApplicationContext(), "Please Select Model..", Toast.LENGTH_SHORT).show();
            return false;
        }


        if (!issueSel) {
            Toast.makeText(getApplicationContext(), "Please Select Issue..", Toast.LENGTH_SHORT).show();
            return false;
        }

        valid = Validator.validateEditText(et_desc);

        if (valid != Validator.VALID) {
            Toast.makeText(getApplicationContext(), "Please Enter Description..", Toast.LENGTH_SHORT).show();
            return false;
        }

        int rbId = rgG.getCheckedRadioButtonId();

        if (rbId == R.id.rbYes) {
            underGuarantee = "Yes";

            if (imgBitmap[0] != null) {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                imgBitmap[0].compress(Bitmap.CompressFormat.JPEG, 50, baos);
                byte[] ba = baos.toByteArray();
                ba1_base64 = Base64.encodeBytes(ba);
            }

        } else if (rbId == R.id.rbNo) {
            underGuarantee = "No";
        } else {
            Toast.makeText(getApplicationContext(), "Please select guarantee..", Toast.LENGTH_SHORT).show();
            return false;
        }


        return true;

    }

    public void showSuccessDialog() {

        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
        dialog.setContentView(R.layout.dialog_successful);
        dialog.setCancelable(true);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

        TextView tvMessage = dialog.findViewById(R.id.tvMessage);
        tvMessage.setText("Issue details saved..");

        ((Button) dialog.findViewById(R.id.bt_close)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
        dialog.getWindow().setAttributes(lp);
    }


    private static final String TAG = "ActivityRaiseIssue";


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.navigation_exit:
                showExitDialog();
                break;

            case R.id.navigation_profile:
                Intent intent = new Intent(context, ActivityProfile.class);
                startActivity(intent);
                break;

            case R.id.navigation_history:
                Intent intent1 = new Intent(context, ActivityHistory.class);
                startActivity(intent1);
                break;

            case R.id.navigation_aboutus:
                Intent intent2 = new Intent(context, ActivityAboutUs.class);
                startActivity(intent2);
                break;

        }
        return false;
    }

    private void showExitDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Do you want to exit app?");
        builder.setPositiveButton(R.string.nav_exit, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                CommonUtil.setSharePreferenceString(context, GlobalData.USER_ID, "0");
                Intent intent = new Intent(context, ActivityLogin.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        });
        builder.setNegativeButton(R.string.nav_cancel, null);
        builder.show();

    }


    @Override
    public void setBrands(ArrayList<Brand> brandList) {

        this.brandsList = brandList;
        Brand b = new Brand();
        b.setBrandId(0);
        b.setBrandName("Other");
        brandList.add(b);
        brandAdapter = new BrandsSpinnerAdapter(context, brandsList);
        spBrand.setAdapter(brandAdapter);

        llBr.setVisibility(View.VISIBLE);


    }

    @Override
    public void setBrandListError() {
        llMo.setVisibility(View.GONE);
        llNewMo.setVisibility(View.GONE);
        llIss.setVisibility(View.GONE);
        llDesc.setVisibility(View.GONE);
    }

    @Override
    public void setModels(ArrayList<Model> modelList) {

        this.modelsList = modelList;
        Model b = new Model();
        b.setBrandId(0);
        b.setModelId(0);
        b.setModelName("Other");
        modelList.add(b);

        ModelsSpinnerAdapter adapterFrom = new ModelsSpinnerAdapter(context, modelsList);
        spModel.setAdapter(adapterFrom);

        llMo.setVisibility(View.VISIBLE);

    }

    @Override
    public void setModelListError() {
        llIss.setVisibility(View.GONE);
        llDesc.setVisibility(View.GONE);
    }

    @Override
    public void setIssues(ArrayList<StrIssue> issueList) {

        if (issueList != null && issueList.size() > 0) {
            this.issueList = issueList;
            StrIssueSpinnerAdapter adapter = new StrIssueSpinnerAdapter(context, issueList);
            spIssue.setAdapter(adapter);
        }

    }

    @Override
    public void setIssueListError() {
        llDesc.setVisibility(View.GONE);
    }

    @Override
    public void setCategories(ArrayList<Category> categoryList) {

        this.categoryList = categoryList;
        Category category = new Category(0, "Select");
        categoryList.add(0, category);
        CategorySpinnerAdapter adapter = new CategorySpinnerAdapter(context, categoryList);
        spCat.setAdapter(adapter);

    }

    @Override
    public void setCategoryError() {
        llIntro.setVisibility(View.VISIBLE);
        llCat.setVisibility(View.VISIBLE);
        llBr.setVisibility(View.GONE);
        llNewBr.setVisibility(View.GONE);
        llMo.setVisibility(View.GONE);
        llNewMo.setVisibility(View.GONE);
        llIss.setVisibility(View.GONE);
        llDesc.setVisibility(View.GONE);
        llGuarantee.setVisibility(View.GONE);
    }

    @Override
    public void onIssueSubmit() {

        brandSelected = false;
        modelSelected = false;
        otherBSele = false;
        otherMSel = false;

        brandId = 0;
        modelId = 0;
        spCat.setSelection(0);

        et_desc.setText("");
        et_brand.setText("");
        et_model.setText("");
        et_desc.setText("");
        btnSubmit.setVisibility(View.GONE);
        llGuarantee.setVisibility(View.GONE);
        ib1.setImageResource(R.drawable.button_camera);
        imgBitmap[0] = null;

        rbNo.setChecked(true);

        showSuccessDialog();

    }


    //----------------------Raise Issue Image Logic Start----------------------------------


    private static final int FRAGMENT_GROUPID = 30;

    private Bitmap imgBitmap[] = new Bitmap[2];
    private static final int BUTTON1_CAMERA = 1;
    private static final int BUTTON2_CAMERA = 2;
    private static final int BUTTON3_CAMERA = 3;
    private static final int BUTTON4_CAMERA = 4;

    private static final int BUTTON1_GALARY = 5;
    private static final int BUTTON2_GALARY = 6;
    private static final int BUTTON3_GALARY = 7;
    private static final int BUTTON4_GALARY = 8;

    private static final int CROP_FROM_CAMERA1 = 9;
    private static final int CROP_FROM_CAMERA2 = 10;
    private static final int CROP_FROM_CAMERA3 = 11;

    private File mFileTemp;
    private int buttonClick;

    View.OnClickListener imgClick = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            buttonClick = Integer.parseInt(v.getTag().toString());
            v.showContextMenu();
        }
    };

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.setHeaderTitle("Please Select");
        menu.add(FRAGMENT_GROUPID, v.getId(), 0, "Camera");
        if (buttonClick != 4) {
            menu.add(FRAGMENT_GROUPID, v.getId(), 0, "Gallery");
        }
        menu.add(FRAGMENT_GROUPID, v.getId(), 0, "Delete");
    }

    Uri imageUri;
    private static int PICK_IMAGE;
    private static int PICK_Camera_IMAGE;

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        String state = Environment.getExternalStorageState();

        if (Environment.MEDIA_MOUNTED.equals(state)) {
            mFileTemp = new File(Environment.getExternalStorageDirectory(), GlobalData.TEMP_PHOTO_FILE_NAME);
        } else {
            mFileTemp = new File(context.getFilesDir(), GlobalData.TEMP_PHOTO_FILE_NAME);
        }

        if (item.getGroupId() == FRAGMENT_GROUPID) {
            if (item.getTitle() == "Camera") {
                if (buttonClick == 4) {
                    String fileName = "video.mp4";
                    // create parameters for Intent with filename
                    ContentValues values = new ContentValues();
                    values.put(MediaStore.Images.Media.TITLE, fileName);
                    values.put(MediaStore.Images.Media.DESCRIPTION,
                            "Video captured by camera");
                    // imageUri is the current activity attribute, define and
                    // save
                    // it
                    // for later usage (also in onSaveInstanceState)


//                    imageUri =
//                            getContentResolver()
//                                    .insert(MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
//                                            values);


                    imageUri = FileProvider.getUriForFile(context, context.getApplicationContext().getPackageName() + ".provider", mFileTemp);

                    PICK_Camera_IMAGE = BUTTON4_CAMERA;

                    /*
                     * Intent intent = new
                     * Intent(MediaStore.ACTION_VIDEO_CAPTURE); // Add
                     * (optional) extra to save video to our file
                     * intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri); //
                     * Optional extra to set video quality
                     * intent.putExtra(MediaStore.EXTRA_DURATION_LIMIT, 5);
                     * intent.putExtra(MediaStore.EXTRA_SIZE_LIMIT, 2);
                     * intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 0);
                     * startActivityForResult(intent, PICK_Camera_IMAGE);
                     */

                    Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);

                    int size = 3;
                    intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 0);//0 for low, 1 higth
                    intent.putExtra(MediaStore.EXTRA_DURATION_LIMIT, 30); //10seq
                    intent.putExtra(MediaStore.EXTRA_SIZE_LIMIT, size * 1048576L);//X mb *1024*1024
                    intent.putExtra(MediaStore.EXTRA_SHOW_ACTION_ICONS, true);
                    intent.putExtra(
                            MediaStore.INTENT_ACTION_STILL_IMAGE_CAMERA, true);
                    startActivityForResult(intent, PICK_Camera_IMAGE);

                } else {

                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                    try {
                        if (Environment.MEDIA_MOUNTED.equals(state)) {
//                            imageUri = Uri.fromFile(mFileTemp);
                            imageUri = FileProvider.getUriForFile(context, context.getApplicationContext().getPackageName() + ".provider", mFileTemp);

                            Log.e("tag", "CameraPermission 1 imgUri : " + imageUri.toString());
                        } else {
                            /*
                             * The solution is taken from here:
                             * http://stackoverflow
                             * .com/questions/10042695/how-to
                             * -get-camera-result-as-a-uri-in-data-folder
                             */
                            imageUri = InternalStorageContentProvider.CONTENT_URI;
                            Log.e("tag", "CameraPermission 1 imgUri : " + imageUri.toString());
                        }
                        intent.putExtra(
                                MediaStore.EXTRA_OUTPUT,
                                imageUri);
                        intent.putExtra("return-data", true);
                        switch (buttonClick) {
                            case 1:
                                PICK_Camera_IMAGE = BUTTON1_CAMERA;
                                break;
                            case 2:
                                PICK_Camera_IMAGE = BUTTON2_CAMERA;
                                break;
                            case 3:
                                PICK_Camera_IMAGE = BUTTON3_CAMERA;
                                break;
                        }

                        startActivityForResult(intent, PICK_Camera_IMAGE);

                    } catch (ActivityNotFoundException e) {

                    }

                }
                return true;
            } else if (item.getTitle() == "Gallery") {
                if (buttonClick == 4) {
                    //  openGalleryVideo();
                } else {
                    try {
                        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                        photoPickerIntent.setType("image/*");
                        // photoPickerIntent.setAction(Intent.ACTION_GET_CONTENT);
                        switch (buttonClick) {
                            case 1:
                                PICK_IMAGE = BUTTON1_GALARY;
                                break;
                            case 2:
                                PICK_IMAGE = BUTTON2_GALARY;
                                break;
                            case 3:
                                PICK_IMAGE = BUTTON3_GALARY;
                                break;
                            case 4:
                                PICK_IMAGE = BUTTON4_GALARY;
                                break;
                        }
                        startActivityForResult(Intent.createChooser(
                                photoPickerIntent, "Select Image"),
                                PICK_IMAGE);

                        //                        startActivityForResult(intent,requestcode);

                    } catch (Exception e) {

                    }
                }
                return true;
            } else if (item.getTitle() == "Delete") {
                try {
                    switch (buttonClick) {
                        case 1:
                            ib1.setImageResource(R.drawable.button_camera);
                            imgBitmap[0] = null;
                            break;

                    }
                } catch (Exception e) {

                }
                return true;
            }
        }
        return super.onContextItemSelected(item);
    }

    public static final int REQUEST_CODE_CROP_IMAGE = 0x3;

    private void startCropImage(int REQUEST_ID) {

        Intent intent = new Intent(ActivityRaiseIssue.this, CropImage.class);
        intent.putExtra(CropImage.IMAGE_PATH, mFileTemp.getPath());
        intent.putExtra(CropImage.SCALE, false);

        intent.putExtra(CropImage.ASPECT_X, 3);
        intent.putExtra(CropImage.ASPECT_Y, 3);

        startActivityForResult(intent, REQUEST_ID);
    }

    // rounded corner by Ashish

    public Bitmap roundCornerImage(Bitmap src, float round) {
        // Source image size
        int width = src.getWidth();
        int height = src.getHeight();
        // create result bitmap output
        Bitmap result = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        // set canvas for painting
        Canvas canvas = new Canvas(result);
        canvas.drawARGB(0, 0, 0, 0);

        // configure paint
        final Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.BLACK);

        // configure rectangle for embedding
        final Rect rect = new Rect(0, 0, width, height);
        final RectF rectF = new RectF(rect);

        // draw Round rectangle to canvas
        canvas.drawRoundRect(rectF, round, round, paint);

        // create Xfer mode
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        // draw source image to canvas
        canvas.drawBitmap(src, rect, rect, paint);

        // return final image
        return result;
    }

    public static void copyStream(InputStream input, OutputStream output)
            throws IOException {

        byte[] buffer = new byte[1024];
        int bytesRead;
        while ((bytesRead = input.read(buffer)) != -1) {
            output.write(buffer, 0, bytesRead);
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Uri selectedImageUri = null;

        switch (requestCode) {
            case BUTTON1_GALARY:
            case BUTTON2_GALARY:
            case BUTTON3_GALARY:
                if (resultCode == Activity.RESULT_OK) {
                    selectedImageUri = data.getData();
                    if (selectedImageUri != null) {
                        try {
                            // OI FILE Manager

                            InputStream inputStream = context.getContentResolver()
                                    .openInputStream(data.getData());
                            FileOutputStream fileOutputStream = new FileOutputStream(
                                    mFileTemp);
                            copyStream(inputStream, fileOutputStream);
                            fileOutputStream.close();
                            inputStream.close();

                            if (mFileTemp != null) {
                                switch (requestCode) {

                                    case BUTTON1_GALARY:
                                        startCropImage(CROP_FROM_CAMERA1);
                                        break;

                                    case BUTTON2_GALARY:
                                        startCropImage(CROP_FROM_CAMERA2);
                                        break;

                                    case BUTTON3_GALARY:
                                        startCropImage(CROP_FROM_CAMERA3);
                                        break;
                                }
                            }

                        } catch (Exception e) {
                            Toast.makeText(context, "Internal error",
                                    Toast.LENGTH_LONG).show();

                        }
                    }
                }
                break;
            case BUTTON4_GALARY:
                if (resultCode == Activity.RESULT_OK) {
                    //  getVideoPath(data.getData());
                    return;
                }
                break;
            case BUTTON1_CAMERA:
                Log.e("tag", "BUTTON1_CAMERA result : " + resultCode);
                if (data != null) {
                    Log.e("tag", "BUTTON1_CAMERA result not null ");
                }
                if (resultCode == RESULT_OK) {
                    // use imageUri here to access the image
                    selectedImageUri = imageUri;
                    startCropImage(CROP_FROM_CAMERA1);

                } else if (resultCode == RESULT_CANCELED) {
                    Log.e("tag", " RESULT_CANCELED ");
                } else {
                    Log.e("tag", " ELSE ");
                }
                break;
            case BUTTON2_CAMERA:
                if (resultCode == RESULT_OK) {
                    // use imageUri here to access the image
                    selectedImageUri = imageUri;
                    startCropImage(CROP_FROM_CAMERA2);

                }
                break;
            case BUTTON3_CAMERA:
                if (resultCode == RESULT_OK) {
                    // use imageUri here to access the image
                    selectedImageUri = imageUri;
                    startCropImage(CROP_FROM_CAMERA3);
                }
                break;
            case BUTTON4_CAMERA:
                if (data != null) {
                    if (resultCode == Activity.RESULT_OK) {
                        //   getVideoPath(data.getData());
                        return;
                    }
                }
                break;

            case CROP_FROM_CAMERA1:
                if (data != null) {

                    String path = data.getStringExtra(CropImage.IMAGE_PATH);
                    if (path == null) {
                        return;
                    }

                    imgBitmap[0] = BitmapFactory.decodeFile(mFileTemp.getPath());
                    //  ib1.setImageBitmap(imgBitmap[0]);
                    ib1.setImageBitmap(roundCornerImage(imgBitmap[0], 35));
                    // ib2.setEnabled(true);
                    // fib2.setVisibility(View.VISIBLE);

                }
                break;
        }
    }

    /*-----------Raise Issue Image Logic End-----------------------*/


}
