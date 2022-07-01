package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

public class TopFragment extends Fragment implements View.OnClickListener {
    ImageView HomeBtn;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.top_fragment, container, false);
        HomeBtn = view.findViewById(R.id.ic_house);

        HomeBtn.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        if(Common.btn == 0) return;
        Common.btn = 0;

        FragmentActivity activity = getActivity();
        if(activity == null) return;

        Intent intent = new Intent(activity, HomeActivity.class);
        startActivity(intent);
    }
}
