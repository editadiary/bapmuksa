package com.example.myapplication;

import static com.example.myapplication.Common.stack_page;
import static com.example.myapplication.Common.currentTab;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.example.myapplication.Contact.ContactActivity;
import com.example.myapplication.Gallery.GalleryMainActivity;

public class TabFragment extends Fragment implements View.OnClickListener {
    private ImageView ContactBtn, GalleryBtn;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab_fragment, container, false);
        ContactBtn = view.findViewById(R.id.ic_person_square);
        ContactBtn.setOnClickListener(this);
        if(currentTab == 1) ContactBtn.setBackgroundResource(R.color.color1);
        else ContactBtn.setBackgroundResource(R.color.white);

        GalleryBtn = view.findViewById(R.id.ic_picture);
        GalleryBtn.setOnClickListener(this);
        if(currentTab == 2) GalleryBtn.setBackgroundResource(R.color.color1);
        else GalleryBtn.setBackgroundResource(R.color.white);

        return view;
    }

    public void onClick(View view) {
        int id = view.getId();
        boolean flag = true;
        Intent intent = null;
        FragmentActivity activity = getActivity();
        if(activity == null) return;

        if(id == R.id.ic_person_square && stack_page.peek() != 1) {
            currentTab = 1;
            stack_page.push(1); flag = false;
            intent = new Intent(activity, ContactActivity.class);
        }
        else if (id == R.id.ic_picture && stack_page.peek() != 2) {
            currentTab = 2;
            stack_page.push(2); flag = false;
            intent = new Intent(activity, GalleryMainActivity.class);
        }

        if(!flag){
            startActivity(intent);
        }
    }
}
