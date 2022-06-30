package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

public class TabFragment extends Fragment implements View.OnClickListener {
    Button PhoneBtn, GalleryBtn;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab_fragment, container, false);
        PhoneBtn = (Button)view.findViewById(R.id.phone_button);
        PhoneBtn.setOnClickListener(this);

        GalleryBtn = (Button)view.findViewById(R.id.gallery_button);
        GalleryBtn.setOnClickListener(this);

        return view;
    }

    public void onClick(View view) {
        Intent intent;
        switch(view.getId()) {
            case R.id.phone_button:
                if(MainActivity.btn == 1) break;
                MainActivity.btn = 1;
                intent = new Intent(getActivity(), ContactActivity.class);
                startActivity(intent);
                break;
            case R.id.gallery_button:
                if(MainActivity.btn == 2) break;
                MainActivity.btn = 2;
                intent = new Intent(getActivity(), GalleryMainActivity.class);
                startActivity(intent);
        }
    }
}
