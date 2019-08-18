package com.example.a10.guideapplication.view;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a10.guideapplication.R;
import com.example.a10.guideapplication.app.App;
import com.example.a10.guideapplication.model.Doctor;
import com.example.a10.guideapplication.model.DoctorApi;
import com.example.a10.guideapplication.model.Images;
import com.example.a10.guideapplication.model.Item;
import com.example.a10.guideapplication.model.Place;
import com.example.a10.guideapplication.model.Section;
import com.example.a10.guideapplication.model.User;
import com.example.a10.guideapplication.presenter.DoctorPresenter;
import com.example.a10.guideapplication.presenter.PlacePresenter;
import com.example.a10.guideapplication.repository.repositoryImp.DoctorRepositoryImp;
import com.example.a10.guideapplication.repository.repositoryImp.PlacesRepositoryImp;
import com.example.a10.guideapplication.utils.PrefUtils;
import com.example.a10.guideapplication.utils.RoundedCornerImage;
import com.google.gson.Gson;
import com.jaiselrahman.filepicker.activity.FilePickerActivity;
import com.jaiselrahman.filepicker.config.Configurations;
import com.jaiselrahman.filepicker.model.MediaFile;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.blurry.Blurry;

public class EditPlaceActivity extends AppCompatActivity implements PlaceInterface, DoctorInterface {

    private static final String TAG = "TAG";
    @BindView(R.id.toolbar_back)
    Button toolbarBack;
    @BindView(R.id.imageProfile)
    ImageView imageProfile;
    @BindView(R.id.imageButton)
    FloatingActionButton imageButton;
    @BindView(R.id.nameTextView)
    TextView nameTextView;
    @BindView(R.id.name)
    EditText nameEditText;
    @BindView(R.id.categoryTextView)
    TextView categoryTextView;
    @BindView(R.id.categoryLinear)
    LinearLayout categoryLinear;
    @BindView(R.id.category)
    Spinner categorySpinner;
    @BindView(R.id.descriptionTextView)
    TextView descriptionTextView;
    @BindView(R.id.descriptionLinear)
    LinearLayout descriptionLinear;
    @BindView(R.id.description)
    EditText description;
    @BindView(R.id.menuTextView)
    TextView menuTextView;
    @BindView(R.id.noMenuTextView)
    TextView noMenuTextView;
    @BindView(R.id.menuRecyclerView)
    RecyclerView menuRecyclerView;
    @BindView(R.id.menuButton)
    ImageButton menuButton;
    @BindView(R.id.saveButton)
    Button saveButton;
    @BindView(R.id.cancleButton)
    Button cancleButton;

    private DoctorApi doctorApi = new DoctorApi();
    private Place place = new Place();
    private DoctorInterface doctorInterface;
    private DoctorPresenter doctorPresenter;
    private PlaceInterface placeInterface;
    private PlacePresenter placePresenter;
    private ProgressDialog progressBar;
    private String placeType;
    private Section section;
    private Doctor doctor;
    private Bundle bundle;
    private User user;
    String[] menus;

    private static final int FILE_REQUEST_CODE = 1000;
    private int GALLERY = 1, CAMERA = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_place);
        ButterKnife.bind(this);
        App.setContext(this);

        final Gson gson = new Gson();
        user = gson.fromJson(PrefUtils.getKeys(App.getContext(), getString(R.string.user)), User.class);


        bundle = getIntent().getExtras();
        placeType = bundle.getString(getString(R.string.placeType));

        if (placeType.equals(getString(R.string.doctor))) {
            doctorInterface = this;
            doctorPresenter = new DoctorPresenter(doctorInterface, new DoctorRepositoryImp());
        } else {
            placeInterface = this;
            placePresenter = new PlacePresenter(new PlacesRepositoryImp(), placeInterface);
        }

        setData();

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPictureDialog();
            }
        });

        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addMenu();
            }
        });

        toolbarBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                back();
            }
        });

        cancleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                back();
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar = new ProgressDialog(EditPlaceActivity.this);
                progressBar.setMessage("جارى حفظ البيانات...");
                progressBar.setCanceledOnTouchOutside(true);
                progressBar.show();
                save();
            }
        });

    }

    private void setData() {

        if (placeType.equals(getString(R.string.coffee))) {
            section = bundle.getParcelable(getString(R.string.place));

            menuTextView.setVisibility(View.VISIBLE);
            menuButton.setVisibility(View.VISIBLE);

            // Begin the transaction
            if (section != null && section.getMenu() != null) {
                menuRecyclerView.setVisibility(View.VISIBLE);
                if (section.getMenu().contains(",")) {
                    menus = section.getMenu().split(",");
                } else {
                    menus = new String[1];
                    menus[0] = section.getMenu();
                }
                RecyclerView.LayoutManager manager = new GridLayoutManager(App.getContext(), 3);
                menuRecyclerView.setLayoutManager(manager);
                MenuAdapter adapter = new MenuAdapter("section", section.getID(), menus, null);
                menuRecyclerView.setAdapter(adapter);
            } else {
                noMenuTextView.setVisibility(View.VISIBLE);
            }


            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                    R.array.coffees_category_array, android.R.layout.simple_spinner_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            categorySpinner.setAdapter(adapter);

            if (section != null) {
                if (section.getImage() != null) {
                    try {
                        byte[] decodedString = Base64.decode(section.getImage(), Base64.DEFAULT);
                        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                        Blurry.with(App.getContext()).from(RoundedCornerImage.getRoundedCornerBitmap(decodedByte)).into(imageProfile);
                    } catch (Exception e) {
                        Log.e(TAG, e.getMessage());
                    }
                }
                if (section.getPlaceName() != null) nameEditText.setText(section.getPlaceName());
                if (section.getCategory() != null)
                    categorySpinner.setSelection(adapter.getPosition(section.getCategory()));
                if (section.getDescription() != null) description.setText(section.getDescription());
            }

        } else if (placeType.equals(getString(R.string.hotel))) {
            section = bundle.getParcelable(getString(R.string.place));

            categoryTextView.setText("تقييم الفندق الرسمى");

            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                    R.array.hotel_category_array, android.R.layout.simple_spinner_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            categorySpinner.setAdapter(adapter);

            if (section != null) {
                if (section.getImage() != null) {
                    try {
                        byte[] decodedString = Base64.decode(section.getImage(), Base64.DEFAULT);
                        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                        Blurry.with(App.getContext()).from(RoundedCornerImage.getRoundedCornerBitmap(decodedByte)).into(imageProfile);
                    } catch (Exception e) {
                        Log.e(TAG, e.getMessage());
                    }
                }
                if (section.getPlaceName() != null) nameEditText.setText(section.getPlaceName());
                if (section.getCategory() != null)
                    categorySpinner.setSelection(adapter.getPosition(section.getCategory()));
                if (section.getDescription() != null) description.setText(section.getDescription());
            }

        } else if (placeType.equals(getString(R.string.doctor))) {
            doctor = bundle.getParcelable(getString(R.string.place));

            descriptionTextView.setVisibility(View.GONE);
            descriptionLinear.setVisibility(View.GONE);

            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                    R.array.doctors_category_array, android.R.layout.simple_spinner_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            categorySpinner.setAdapter(adapter);

            if (doctor != null) {
                if (doctor.getImage() != null) {
                    try {
                        byte[] decodedString = Base64.decode(doctor.getImage(), Base64.DEFAULT);
                        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                        Blurry.with(App.getContext()).from(RoundedCornerImage.getRoundedCornerBitmap(decodedByte)).into(imageProfile);
                    } catch (Exception e) {
                        Log.e(TAG, e.getMessage());
                    }
                }
                if (doctor.getName() != null) nameEditText.setText(doctor.getName());
                if (doctor.getSpecialization() != null)
                    categorySpinner.setSelection(adapter.getPosition(doctor.getSpecialization()));
            }

        } else if (placeType.equals(getString(R.string.store))) {
            section = bundle.getParcelable(getString(R.string.place));

            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                    R.array.store_category_array, android.R.layout.simple_spinner_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            categorySpinner.setAdapter(adapter);

            if (section != null) {
                if (section.getImage() != null) {
                    try {
                        byte[] decodedString = Base64.decode(section.getImage(), Base64.DEFAULT);
                        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                        Blurry.with(App.getContext()).from(RoundedCornerImage.getRoundedCornerBitmap(decodedByte)).into(imageProfile);
                    } catch (Exception e) {
                        Log.e(TAG, e.getMessage());
                    }
                }
                if (section.getPlaceName() != null) nameEditText.setText(section.getPlaceName());
                if (section.getCategory() != null)
                    categorySpinner.setSelection(adapter.getPosition(section.getCategory()));
                if (section.getDescription() != null) description.setText(section.getDescription());
            }
        }
    }

    private void back() {
        Intent mainIntent = new Intent(EditPlaceActivity.this, PlacesActivity.class);
        mainIntent.putExtra(getString(R.string.placeType), placeType);
        startActivity(mainIntent);
        finish();
    }

    private void save() {
        saveButton.setEnabled(false);
        if (valid()) {
            if (placeType.equals(getString(R.string.doctor))) {
                doctorApi.setName(nameEditText.getText().toString());
                doctorApi.setRate(0);
                doctorApi.setCreateUser(user.getID());
                doctorApi.setSpecialization(categorySpinner.getSelectedItem().toString());
                if (doctor == null) doctorPresenter.setDoctor(doctorApi);
                else {
                    doctorApi.setID(doctor.getID());
                    doctorApi.setModifiedUser(user.getID());
                    doctorPresenter.editDoctor(doctorApi);
                }
            } else {
                place.setPlaceName(nameEditText.getText().toString());
                place.setDescription(description.getText().toString());
                place.setRate(0);
                place.setCreateUser(user.getID());
                place.setCategory(categorySpinner.getSelectedItem().toString());
                place.setType(placeType);
                if (section == null) placePresenter.addPlace(place);
                else {
                    place.setID(section.getID());
                    place.setModifiedUser(user.getID());
                    placePresenter.updatePlace(place);
                }
            }
        } else {
            saveButton.setEnabled(true);
            progressBar.dismiss();
        }

    }

    private boolean valid() {
        boolean valid = true;

        if (nameEditText.getText().toString() == null || nameEditText.getText().toString().equals("")) {
            nameEditText.setError("من فضلك ادخل الاسم");
            valid = false;
        }

        if (categorySpinner.getSelectedItem().equals("")) {
            if (!placeType.equals(getString(R.string.hotel)))
                Toast.makeText(this, "من فضلك اختر تقييم الفندق", Toast.LENGTH_LONG).show();
            else Toast.makeText(this, "من فضلك اختر التخصص", Toast.LENGTH_LONG).show();
            valid = false;
        }

        if (!placeType.equals(getString(R.string.doctor))) {
            if (description.getText().toString() == null || description.getText().toString().equals("")) {
                description.setError("من فضلك ادخل وصف للمكان");
                valid = false;
            }

            if (place == null || place.getImage() == null || place.getImage().equals("")) {
                if (section == null || section.getImage() == null || section.getImage().equals("")) {
                    Toast.makeText(this, "من فضلك ادخل صورة المكان", Toast.LENGTH_LONG).show();
                    valid = false;
                } else {
                    place.setImage(section.getImage());
                }
            }
        } else {
            if (doctorApi == null || doctorApi.getImage() == null || doctorApi.getImage().equals("")) {
                if (doctor == null || doctor.getImage() == null || doctor.getImage().equals("")) {
                    Toast.makeText(this, "من فضلك ادخل صورة الطبيب", Toast.LENGTH_LONG).show();
                    valid = false;
                } else {
                    doctorApi.setImage(doctor.getImage());
                }
            }
        }

        if (placeType.equals(getString(R.string.coffee))) {
            if (place.getMenuImages() == null) {
                if (section == null || section.getMenu() == null) {
                    Toast.makeText(this, "من فضلك ادخل القائمة الخاصه بالاطعمه او المشروبات", Toast.LENGTH_LONG).show();
                    valid = false;
                } else {
                    place.setMenu(section.getMenu());
                }
            }
        }

        return valid;
    }

    private void showPictureDialog() {
        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(this);

        final Item[] items = {
                new Item("اختر صورة من البوم الصور", R.drawable.gallery),
                new Item("التقط صورة من الكاميرا", R.drawable.camera)};

        ListAdapter adapter = new ArrayAdapter<Item>(
                this,
                android.R.layout.select_dialog_item,
                android.R.id.text1,
                items) {
            public View getView(final int position, View convertView, ViewGroup parent) {
                //Use super class to create the View
                View v = super.getView(position, convertView, parent);
                TextView tv = (TextView) v.findViewById(android.R.id.text1);

                //Put the image on the TextView
                tv.setText(items[position].text);
                tv.setCompoundDrawablesWithIntrinsicBounds(items[position].icon, 0, 0, 0);

                //Add margin between image and text (support various screen densities)
                int dp5 = (int) (5 * getResources().getDisplayMetrics().density + 0.5f);
                tv.setCompoundDrawablePadding(dp5);
                return v;
            }
        };
        pictureDialog.setAdapter(adapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                switch (i) {
                    case 0:
                        choosePhotoFromGallary();
                        break;
                    case 1:
                        takePhotoFromCamera();
                        break;
                }
            }
        });
        pictureDialog.show();
    }

    private void takePhotoFromCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAMERA);
    }

    private void choosePhotoFromGallary() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(galleryIntent, GALLERY);
    }

    private void addMenu() {
        Intent intent = new Intent(this, FilePickerActivity.class);
        intent.putExtra(FilePickerActivity.CONFIGS, new Configurations.Builder()
                .setCheckPermission(true)
                .setShowImages(true)
                .enableImageCapture(true)
                .setSkipZeroSizeFiles(true)
                .build());
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        startActivityForResult(intent, FILE_REQUEST_CODE);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == this.RESULT_CANCELED) {
            return;
        }
        if (requestCode == GALLERY) {
            if (data != null) {
                Uri contentURI = data.getData();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), contentURI);
                    imageProfile.setImageBitmap(bitmap);
                    ByteArrayOutputStream blob = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG, 0 /* Ignored for PNGs */, blob);
                    String base64 = Base64.encodeToString(blob.toByteArray(), Base64.DEFAULT);
                    if (placeType.equals(getString(R.string.doctor))) doctorApi.setImage(base64);
                    else place.setImage(base64);
                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(EditPlaceActivity.this, "فشل تحميل الصورة!", Toast.LENGTH_SHORT).show();
                }
            }

        } else if (requestCode == CAMERA) {
            Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
            imageProfile.setImageBitmap(thumbnail);
            ByteArrayOutputStream blob = new ByteArrayOutputStream();
            thumbnail.compress(Bitmap.CompressFormat.PNG, 0 /* Ignored for PNGs */, blob);
            String base64 = Base64.encodeToString(blob.toByteArray(), Base64.DEFAULT);
            if (placeType.equals(getString(R.string.doctor))) doctorApi.setImage(base64);
            else place.setImage(base64);
        }

        if (requestCode == FILE_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            String imageNames = "";
            ArrayList<MediaFile> files = data.getParcelableArrayListExtra(FilePickerActivity.MEDIA_FILES);
            List<Images> result = new ArrayList<>();
            for (int i = 0; i < files.size(); i++) {
                File source = new File(files.get(i).getPath());
                Images menuImage = new Images();
                menuImage.setName(source.getName());
                imageNames = imageNames + source.getName() + ",";
                if (source.exists() && source.length() > 0) {
                    Bitmap bm = BitmapFactory.decodeFile(files.get(i).getPath());
                    ByteArrayOutputStream bOut = new ByteArrayOutputStream();
                    bm.compress(Bitmap.CompressFormat.JPEG, 100, bOut);
                    String base64Image = Base64.encodeToString(bOut.toByteArray(), Base64.DEFAULT);
                    menuImage.setData(base64Image);
                }
                result.add(menuImage);
            }
            if (imageNames != null || !imageNames.equals("")) {
                place.setMenu(imageNames);
                place.setMenuImages(result);

                menuRecyclerView.setVisibility(View.VISIBLE);
                noMenuTextView.setVisibility(View.GONE);
                menuRecyclerView.removeAllViews();
                RecyclerView.LayoutManager manager = new GridLayoutManager(App.getContext(), 3);
                menuRecyclerView.setLayoutManager(manager);
                EditMenuAdapter editMenuAdapter = new EditMenuAdapter(result);
                menuRecyclerView.setAdapter(editMenuAdapter);
            }
        }
    }

    @Override
    public void places(List<Section> sections) {

    }

    @Override
    public void isPlaceAdded(Section section) {
        progressBar.dismiss();
        if (section != null) {
            Toast.makeText(this, "لقد تم أضافة البيانات بنجاح", Toast.LENGTH_LONG).show();
            back();
        } else {
            Toast.makeText(this, "برجاء المحاولة فى وقت آخرى", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void isPlaceUpdated(boolean b) {
        progressBar.dismiss();
        if (b == true) {
            Toast.makeText(this, "لقد تم تعديل البيانات بنجاح", Toast.LENGTH_LONG).show();
            back();
        } else {
            Toast.makeText(this, "برجاء المحاولة فى وقت آخرى", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void isPlaceDeleted(boolean b) {

    }

    @Override
    public void topRated(List sections) {

    }

    @Override
    public void searchDoctor(List<Doctor> doctor) {

    }

    @Override
    public void searchSection(List<Section> section) {

    }

    @Override
    public void doctor(Doctor doctor) {
        progressBar.dismiss();
        if (doctor != null) {
            Toast.makeText(this, "لقد تم أضافة البيانات بنجاح", Toast.LENGTH_LONG).show();
            back();
        } else {
            Toast.makeText(this, "برجاء المحاولة فى وقت آخرى", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void doctorMessage(Boolean response) {
        progressBar.dismiss();
        if (response == true) {
            Toast.makeText(this, "لقد تم تعديل البيانات بنجاح", Toast.LENGTH_LONG).show();
            back();
        } else {
            Toast.makeText(this, "برجاء المحاولة فى وقت آخرى", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void doctors(List<Doctor> doctors) {

    }
}
