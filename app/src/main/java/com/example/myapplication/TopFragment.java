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

public class TopFragment extends Fragment implements View.OnClickListener {
    private ImageView HomeBtn;
    // private ImageView PrevBtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.top_fragment, container, false);

        HomeBtn = view.findViewById(R.id.ic_house);
        // PrevBtn = view.findViewById(R.id.ic_left_arrow);

        HomeBtn.setOnClickListener(this);
        // PrevBtn.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        FragmentActivity activity = getActivity();

        if(id == R.id.ic_house) {
            Intent intent;

            if(stack_page.peek() == 0) return;
            stack_page.push(0); currentTab = 0;

            if(activity == null) return;
            intent = new Intent(activity, HomeActivity.class);
            startActivity(intent);
        }

        /*
        else if(id == R.id.ic_left_arrow) {
            if(stack_page.size() == 0) System.exit(0);

            stack_page.pop();
            activity.finish();
        }

         */
    }
}
