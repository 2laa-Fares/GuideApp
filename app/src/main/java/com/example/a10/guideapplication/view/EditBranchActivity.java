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

public class EditBranchActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener, BranchInterface {

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
    Branch branch = new Branch();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_branch);
        ButterKnife.bind(this);
        App.setContext(this);
        branchInterface = this;
        RecyclerView.LayoutManager manager = new RtlGridLayoutManager(App.getContext(), 3);
        photosRecyclerView.setLayoutManager(manager);
        Intent intent = getIntent();
        if (intent!=null){
                branch = intent.getParcelableExtra(getString(R.string.branch));
                if (branch!=null){
                    if (branch.getName()!=null){
                        branchNameET.setText(branch.getName());
                    }
                    if (branch.getAddress()!=null){
                        branchAddressET.setText(branch.getAddress());
                    }
                    if (branch.getContact()!=null){
                        if (branch.getContact().contains(","))
                        {
                            String[] contacts = branch.getContact().split(",");
                            phone1ET.setText(contacts[0]);
                            phone2ET.setText(contacts[1]);
                        }else {
                            phone1ET.setText(branch.getContact());
                        }
                    }
                    if (branch.getTimes()!=null){
                        if (branch.getTimes().contains(" - ")){
                            String[] time = branch.getTimes().split(" - ");
                            fromTimeTV.setText(time[1]);
                            toTimeTV.setText(time[0]);
                        }
                    }
                    if (branch.getPhotos()!=null){
                        String[] photos;
                        if (branch.getPhotos().contains(",")){
                            photos = branch.getPhotos().split(",");
                        }else {
                            photos = new String[1];
                            photos[0] = branch.getPhotos();
                        }
                        MenuAdapter adapter = new MenuAdapter("Branch", branch.getID(), photos, new MenuAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(ImageView imageView) {

                            }
                        });
                        photosRecyclerView.setAdapter(adapter);
                    }
                }

        }

        presenter = new BranchPresenter(branchInterface, new BranchRepositoryImp());
        takePhotosImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EditBranchActivity.this, FilePickerActivity.class);
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
                    startActivityForResult(builder.build(EditBranchActivity.this), PLACE_PICKER_REQUEST);
                } catch (GooglePlayServicesRepairableException | GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                }
            }
        });
        mGoogleApiClient = new GoogleApiClient
                .Builder(App.getContext())
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .enableAutoManage(EditBranchActivity.this, this)
                .build();

        forwardImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Utils.isOnline(EditBranchActivity.this)){
                    progressBar = new ProgressDialog(App.getContext());
                    progressBar.setCancelable(false);
                    progressBar.setMessage("جارى التحميل ...");
                    progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    progressBar.show();
                    BranchApi branchApi = new BranchApi();
                    branchApi.setID(branch.getID());
                    branchApi.setSectionID(branch.getSectionID());
                    branchApi.setType(branch.getType());
                    branchApi.setName(branchNameET.getText().toString());
                    branchApi.setAddress(branchAddressET.getText().toString());
                    if (phone2ET.getText().toString().equals(""))
                    {
                        branchApi.setContact(phone1ET.getText().toString());
                    }else {
                        branchApi.setContact(phone1ET.getText().toString().concat(",").concat(phone2ET.getText().toString()));
                    }
                    if (imagesNames.equals("")){
                        branchApi.setPhotos(branch.getPhotos());
                    }else {
                        branchApi.setPhotos(imagesNames);
                        branchApi.setImages(branchImages);
                    }
                    if (latitude!=null&&longitude!=null){
                        branchApi.setLocationX(latitude);
                        branchApi.setLocationY(longitude);
                    }else {
                        branchApi.setLocationX(branch.getLocationX());
                        branchApi.setLocationY(branch.getLocationY());
                    }
                    branchApi.setTimes(toTimeTV.getText().toString().concat(" - ").concat(fromTimeTV.getText().toString()));
                    branchApi.setCreateUser(branch.getCreateUser());
                    String user = PrefUtils.getKeys(App.getContext(), getString(R.string.user));
                    Gson gson = new Gson();
                    User user1 = gson.fromJson(user, User.class);
                    branchApi.setModifiedUser(user1.getID());
                    presenter.editBranch(branchApi);
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
                if (imagesNames.endsWith(",")){
                    imagesNames = imagesNames.substring(0, imagesNames.length()-1);
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

    }

    @Override
    public void message(Boolean response) {
        progressBar.dismiss();
        if (response){
            Snackbar.make(locationTextView, "تم تعديل الفرع بنجاح", Snackbar.LENGTH_LONG).show();
        }else {
            Snackbar.make(locationTextView, "فشل تعديل الفرع", Snackbar.LENGTH_LONG).show();
        }
    }

    @Override
    public void branches(List<Branch> branches) {

    }
}
