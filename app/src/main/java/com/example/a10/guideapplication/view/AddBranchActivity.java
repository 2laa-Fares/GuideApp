package com.example.a10.guideapplication.view;

import android.app.Activity;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.a10.guideapplication.R;
import com.example.a10.guideapplication.app.App;
import com.example.a10.guideapplication.model.Branch;
import com.example.a10.guideapplication.model.BranchApi;
import com.example.a10.guideapplication.model.Images;
import com.example.a10.guideapplication.model.User;
import com.example.a10.guideapplication.presenter.BranchPresenter;
import com.example.a10.guideapplication.repository.repositoryImp.BranchRepositoryImp;
import com.example.a10.guideapplication.utils.PrefUtils;
import com.example.a10.guideapplication.utils.RtlGridLayoutManager;
import com.example.a10.guideapplication.utils.Utils;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.gson.Gson;
import com.jaiselrahman.filepicker.activity.FilePickerActivity;
import com.jaiselrahman.filepicker.config.Configurations;
import com.jaiselrahman.filepicker.model.MediaFile;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddBranchActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener, BranchInterface {

    private static final int FILE_REQUEST_CODE = 1000;
    @BindView(R.id.branchNameET)
    EditText branchNameET;
    @BindView(R.id.branchAddressET)
    EditText branchAddressET;
    @BindView(R.id.phone1ET)
    EditText phone1ET;
    @BindView(R.id.phone2ET)
    EditText phone2ET;
    @BindView(R.id.takePhotosImageView)
    ImageView takePhotosImageView;
    @BindView(R.id.photosRecyclerView)
    RecyclerView photosRecyclerView;
    @BindView(R.id.fromTimeTV)
    TextView fromTimeTV;
    @BindView(R.id.toTimeTV)
    TextView toTimeTV;
    @BindView(R.id.forwardImageView)
    ImageView forwardImageView;
    @BindView(R.id.locationTextView)
    TextView locationTextView;
    List<Images> branchImages;
    String imagesNames = "";
    Double latitude, longitude;
    private GoogleApiClient mGoogleApiClient;
    private int PLACE_PICKER_REQUEST = 1;
    BranchInterface branchInterface;
    BranchPresenter presenter;
    private ProgressDialog progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_branch);
        App.setContext(this);
        ButterKnife.bind(this);
        branchInterface = this;
        RecyclerView.LayoutManager manager = new RtlGridLayoutManager(App.getContext(), 3);
        photosRecyclerView.setLayoutManager(manager);
        presenter = new BranchPresenter(branchInterface, new BranchRepositoryImp());
        takePhotosImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(App.getContext(), FilePickerActivity.class);
                intent.putExtra(FilePickerActivity.CONFIGS, new Configurations.Builder()
                        .setCheckPermission(true)
                        .setShowImages(true)
                        .enableImageCapture(true)
                        .setMaxSelection(15)
                        .setSkipZeroSizeFiles(true)
                        .build());
                StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
                StrictMode.setVmPolicy(builder.build());
                startActivityForResult(intent, FILE_REQUEST_CODE);
            }
        });
        fromTimeTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setTime(fromTimeTV);
            }
        });
        toTimeTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setTime(toTimeTV);
            }
        });
        locationTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
                try {
                    startActivityForResult(builder.build((Activity) App.getContext()), PLACE_PICKER_REQUEST);
                } catch (GooglePlayServicesRepairableException | GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                }
            }
        });
        mGoogleApiClient = new GoogleApiClient
                .Builder(App.getContext())
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .enableAutoManage((FragmentActivity) App.getContext(), this)
                .build();

        forwardImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!branchNameET.getText().toString().equals("")&&!branchAddressET.getText().toString().equals("")&&
                        !fromTimeTV.getText().toString().equals("")&&!toTimeTV.getText().toString().equals("")&&
                        latitude!=null&&longitude!=null){
                    if (phone1ET!=null||phone2ET!=null) {
                        if (Utils.isOnline(App.getContext())) {
                            progressBar = new ProgressDialog(App.getContext());
                            progressBar.setCancelable(false);
                            progressBar.setMessage("جارى التحميل ...");
                            progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                            progressBar.show();
                            BranchApi branch = new BranchApi();
                            branch.setSectionID(getIntent().getIntExtra(getString(R.string.placeId), -1));
                            branch.setType(getIntent().getIntExtra(getString(R.string.placeType), -1));
                            branch.setName(branchNameET.getText().toString());
                            branch.setLocationX(latitude);
                            branch.setLocationY(longitude);
                            branch.setAddress(branchAddressET.getText().toString());
                            if (phone2ET.getText() != null) {
                                branch.setContact(phone1ET.getText().toString().concat(",").concat(
                                        phone2ET.getText().toString()));
                            } else {
                                branch.setContact(phone1ET.getText().toString());
                            }
                            if (!imagesNames.equals("")) {
                                if (imagesNames.endsWith(",")) {
                                    imagesNames = imagesNames.substring(0, imagesNames.length() - 2);
                                }
                                branch.setPhotos(imagesNames);
                            }
                            branch.setTimes(toTimeTV.getText().toString().concat(" - ").concat(fromTimeTV.getText().toString()));
                            String userString = PrefUtils.getKeys(App.getContext(), getString(R.string.user));
                            Gson gson = new Gson();
                            User user = gson.fromJson(userString, User.class);
                            branch.setCreateUser(user.getID());
                            if (branchImages != null) {
                                branch.setImages(branchImages);
                            }
                            presenter.setBranch(branch);

                        } else {
                            Snackbar.make(locationTextView, "لا يوجد اتصال بالانترنت", Snackbar.LENGTH_LONG).show();
                        }
                    }
                }else {
                    if (branchNameET.getText().toString().equals("")){
                        branchNameET.setError("برجاء ادخال اسم الفرع");
                    }
                    if (branchAddressET.getText().toString().equals("")){
                        branchAddressET.setError("برجاء ادخال عنوان الفرع");
                    }
                    if (phone1ET.getText().toString().equals("")&&phone2ET.toString().equals("")){
                        phone1ET.setError("برجاء ادخال رقم هاتف واحد على الاقل");
                    }
                    if (fromTimeTV.getText().toString().equals("")){
                        fromTimeTV.setError("برجاء اختيار الوقت");
                    }
                    if (toTimeTV.getText().toString().equals("")){
                        toTimeTV.setError("برجاء اختيار الوقت");
                    }
                    if (latitude==null&&longitude==null)
                    {
                        locationTextView.setError("برجاء اختيار الموقع");
                    }
                }
            }
        });
    }

    private void setTime(final TextView timeTV) {
        Calendar mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);
        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(App.getContext(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                timeTV.setText( selectedHour + ":" + selectedMinute);
            }
        }, hour, minute, true);//Yes 24 hour time
        mTimePicker.setTitle("اختر الوقت");
        mTimePicker.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == FILE_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            ArrayList<MediaFile> files = data.getParcelableArrayListExtra(FilePickerActivity.MEDIA_FILES);
            if (files!=null){
                branchImages = new ArrayList<>();
                for (int i = 0; i<files.size(); i++){
                    Images image = new Images();
                    image.setName(files.get(i).getName()+".png");
                    imagesNames = imagesNames.concat(files.get(i).getName()+",");
                    Uri path = Uri.fromFile(new File(files.get(i).getPath()));
                    try {
                        Bitmap bitmap = BitmapFactory.decodeStream(App.getContext().getContentResolver().openInputStream(path));
                        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                        byte[] byteArray = byteArrayOutputStream .toByteArray();
                        String encoded = Base64.encodeToString(byteArray, Base64.DEFAULT);
                        image.setData(encoded);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    branchImages.add(image);
                    BranchImagesAdapter adapter = new BranchImagesAdapter(branchImages);
                    photosRecyclerView.setAdapter(adapter);
                }
            }
        }else if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {
                Place place = PlacePicker.getPlace(data, App.getContext());
                StringBuilder stBuilder = new StringBuilder();
                String placename = String.format("%s", place.getName());
                latitude = place.getLatLng().latitude;
                longitude = place.getLatLng().longitude;
                String address = String.format("%s", place.getAddress());
                stBuilder.append("Name: ");
                stBuilder.append(placename);
                stBuilder.append("\n");
                stBuilder.append("Latitude: ");
                stBuilder.append(latitude);
                stBuilder.append("\n");
                stBuilder.append("Logitude: ");
                stBuilder.append(longitude);
                stBuilder.append("\n");
                stBuilder.append("Address: ");
                stBuilder.append(address);
                locationTextView.setText(stBuilder.toString());
            }
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Snackbar.make(locationTextView, connectionResult.getErrorMessage() + "", Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void branch(Branch branch) {
        progressBar.dismiss();
        if (branch!=null){
            Snackbar.make(locationTextView, "تم اضافة الفرع بنجاح", Snackbar.LENGTH_LONG).show();
        }else {
            Snackbar.make(locationTextView, "فشل اضافة الفرع", Snackbar.LENGTH_LONG).show();
        }
    }

    @Override
    public void message(Boolean response) {

    }

    @Override
    public void branches(List<Branch> branches) {

    }
}
