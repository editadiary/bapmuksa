package com.example.myapplication.Recommend;

import static com.example.myapplication.Common.*;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.Common;
import com.example.myapplication.Contact.Contact;
import com.example.myapplication.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;
import java.util.stream.Collectors;

public class RecommendSecondActivity extends AppCompatActivity implements View.OnClickListener {
    private FoodSpinnerAdapter foodAdapter;
    private Spinner foodSpinner;
    private TextView[] tagTVs;
    private RelativeLayout profileRL;
    private ArrayList<String> foodTags;
    private ArrayList<Contact> filteredContacts;
    private ArrayList<Long> filteredDay;
    private TextView nameTV, dayTV, cannotFindTV;
    private Button searchBtn;
    private int min, max, selectedTagPosition, randomPosition;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommend_secondpage);

        initVariables();
        initTags();
        initViews();
        initListeners();
        setViews();
    }

    private void initVariables(){
        initBringItems();
        tagTVs = getTagTVs();
        foodTags = new ArrayList<>();
        filteredDay = new ArrayList<>();
        filteredContacts = filter();
        foodAdapter = new FoodSpinnerAdapter(this, foodTags);
        getRandom();
    }

    private void initTags() {
        final String[] foods = {"전체", "한식", "중식", "일식", "양식", "후식", "기타"};
        int sz = foods.length;

        foodTags.addAll(Arrays.asList(foods).subList(0, sz));
    }

    private TextView[] getTagTVs() {
        TextView[] retTVs = new TextView[6];
        for(int i = 0; i < 6; ++i)
            retTVs[i] = findViewById(getResources().getIdentifier(food_tags_id[i], "id", getPackageName()));

        return retTVs;
    }

    private void initBringItems() {
        min = 1;
        max = 100;
        selectedTagPosition = 0;
    }

    private void initViews() {
        cannotFindTV = findViewById(R.id.cannotFind);
        profileRL = findViewById(R.id.profileRL);
        searchBtn = findViewById(R.id.searchBtn);
        nameTV = findViewById(R.id.Name);
        dayTV = findViewById(R.id.Day);
        foodSpinner = findViewById(R.id.tagSpinner);
        foodSpinner.setAdapter(foodAdapter);
    }

    private void initListeners() {
        foodSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedTagPosition = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        searchBtn.setOnClickListener(this);
    }

    private void setViews() {
        if(filteredContacts.size() == 0) {
            profileRL.setVisibility(View.GONE);
            cannotFindTV.setVisibility(View.VISIBLE);
        }

        else {
            Contact selectedContact = filteredContacts.get(randomPosition);
            setTagsColor(tagTVs, selectedContact.getTags());

            String dayText = Long.toString(filteredDay.get(randomPosition));
            nameTV.setText(selectedContact.getName());
            dayTV.setText(dayText);
        }

    }

    private ArrayList<Contact> filter() {
        return filterByDay(allContacts);
        //        return filterByDay(filterByTag(allContacts));
    }

    /*
    @SuppressLint("NewApi")
    private ArrayList<Contact> filterByTag(ArrayList<Contact> contacts) {
        if(selectedTagPosition == -1) return contacts;

        return (ArrayList<Contact>) contacts.stream().filter(contact -> {
            String[] tags = contact.getTags().replaceAll("[\\[\\]]", "").split(", ");
            return tags[selectedTagPosition].equals("true");
        }).collect(Collectors.toList());
    }
     */

    @SuppressLint("NewApi")
    private ArrayList<Contact> filterByDay(ArrayList<Contact> contacts) {
        if (contacts == null) return new ArrayList<>();

        return (ArrayList<Contact>) contacts.stream().filter(contact -> {
            if( contact.getLastMeet() == null || contact.getLastMeet().equals("")) return false;
            try {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
                Date current = simpleDateFormat.parse(simpleDateFormat.format(new Date()));
                Date prev = simpleDateFormat.parse(contact.getLastMeet());

                assert current != null;
                assert prev != null;
                long days = (current.getTime() - prev.getTime()) / (24 * 60 * 60 * 1000);

                if(min <= days && days <= max) {
                    filteredDay.add(days);
                    return true;
                }
                return false;
            } catch (ParseException e) {
                e.printStackTrace();
                return false;
            }
        }).collect(Collectors.toList());
    }

    private void getRandom(){
        randomPosition = (int) (Math.random() * filteredContacts.size());
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.searchBtn) {
            goIntent(10);
            Intent intent = new Intent(this, RecommendThirdActivity.class);
            intent.putExtra("selectedTagPosition", selectedTagPosition);
            startActivity(intent);
        }
    }

    @Override
    public void onBackPressed() {
        toPrev(this);
    }
}
