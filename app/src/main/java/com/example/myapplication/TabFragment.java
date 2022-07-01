package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.example.myapplication.Contact.ContactActivity;
import com.example.myapplication.Gallery.GalleryMainActivity;

public class TabFragment extends Fragment implements View.OnClickListener {
    private Button PhoneBtn, GalleryBtn;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab_fragment, container, false);
        PhoneBtn = view.findViewById(R.id.phone_button);
        PhoneBtn.setOnClickListener(this);

        GalleryBtn = view.findViewById(R.id.gallery_button);
        GalleryBtn.setOnClickListener(this);

        return view;
    }

    public void onClick(View view) {
        int id = view.getId();
        boolean flag = true;
        Intent intent = null;
        FragmentActivity activity = getActivity();
        if(activity == null) return;

        if(id == R.id.phone_button && Common.btn != 1) {
            Common.btn = 1; flag = false;
            PhoneBtn.setBackgroundResource(R.color.purple_500);
            intent = new Intent(activity, ContactActivity.class);
        }
        else if (id == R.id.gallery_button && Common.btn != 2) {
            Common.btn = 2; flag = false;
            GalleryBtn.setBackgroundResource(R.color.purple_500);
            intent = new Intent(activity, GalleryMainActivity.class);
        }

        if(!flag){
            startActivity(intent);
        }
    }
}
