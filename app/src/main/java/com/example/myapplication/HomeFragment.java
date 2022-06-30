package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

public class HomeFragment extends Fragment implements View.OnClickListener {
    ImageView HomeBtn;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.top_fragment, container, false);
        HomeBtn = (ImageView) view.findViewById(R.id.ic_house);

        HomeBtn.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        if(MainActivity.btn == 0) return;
        MainActivity.btn = 0;
        Intent intent = new Intent(getActivity(), MainActivity.class);
        startActivity(intent);
    }
}
