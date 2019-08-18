package com.example.a10.guideapplication.view;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.a10.guideapplication.R;
import com.example.a10.guideapplication.app.App;
import com.example.a10.guideapplication.model.User;
import com.example.a10.guideapplication.utils.PrefUtils;
import com.google.gson.Gson;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProfileFragmentActivity extends Fragment {
    @BindView(R.id.toolbar_back)
    Button back;
    @BindView(R.id.toolbar_edit)
    Button edit;
    @BindView(R.id.username)
    TextView usernameTextView;
    @BindView(R.id.job)
    TextView jobTextView;
    @BindView(R.id.name)
    TextView nameTextView;
    @BindView(R.id.location)
    TextView locationTextView;
    @BindView(R.id.mail)
    TextView mailTextView;
    @BindView(R.id.phone)
    TextView phoneTextView;
    @BindView(R.id.imageProfile)
    de.hdodenhof.circleimageview.CircleImageView profileImage;

    User user;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.setHasOptionsMenu(true);
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        ButterKnife.bind(this, view);

        edit.setVisibility(View.VISIBLE);

        final Gson gson = new Gson();
        user = gson.fromJson(PrefUtils.getKeys(App.getContext(), getString(R.string.user)), User.class);

        usernameTextView.setText(user.getUserName());
        nameTextView.setText(user.getUserName());
        mailTextView.setText(user.getMail());
        if(user.getPhone() != 0) {
            int phone = user.getPhone();
            phoneTextView.setText(String.valueOf(phone));
        }
        jobTextView.setText(user.getJob());
        locationTextView.setText(user.getAddress());

        if(user.getImage() != null) {
            byte[] decodedString = Base64.decode(user.getImage(), Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            profileImage.setImageBitmap(decodedByte);
            //profileImage.setImageBitmap(BitmapFactory.decodeByteArray(user.getImage(), 0, user.getImage().length));
        }

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(App.getContext(), EditProfileActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(1);
            }
        });

        return view;
    }
}
