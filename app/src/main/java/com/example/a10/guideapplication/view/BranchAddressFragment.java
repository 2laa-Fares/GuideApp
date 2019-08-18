package com.example.a10.guideapplication.view;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.a10.guideapplication.R;
import com.example.a10.guideapplication.app.App;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BranchAddressFragment extends Fragment {

    @BindView(R.id.addressTextView)
    TextView addressTextView;
    @BindView(R.id.phoneTextView)
    TextView phoneTextView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.branch_address, container, false);
        ButterKnife.bind(this, view);
        if (getArguments()!=null){
            String address = getArguments().getString(getString(R.string.branchAddress));
            String contact = getArguments().getString(getString(R.string.branchContact));
            final Double latitude = getArguments().getDouble(getString(R.string.latitude));
            final Double longitude = getArguments().getDouble(getString(R.string.longitude));
            if (address!=null){
                addressTextView.setText(address);
            }
            if (contact!=null){
                if (contact.contains(",")){
                    contact = contact.replace(",", "\n");
                }
                phoneTextView.setText(contact);
            }
            addressTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (latitude!=null&&longitude!=null) {
                        String uri = String.format(Locale.ENGLISH, "geo:%f,%f", latitude, longitude);
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                        App.getContext().startActivity(intent);
                    }
                }
            });
        }

        return view;
    }
}
