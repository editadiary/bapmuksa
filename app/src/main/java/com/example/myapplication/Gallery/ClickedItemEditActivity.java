package com.example.myapplication.Gallery;

import static com.example.myapplication.Common.galleryAdapter;
import static com.example.myapplication.Common.mGalleryList;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.myapplication.R;

public class ClickedItemEditActivity extends AppCompatActivity implements View.OnClickListener{

    ClickedItemActivity clickedItemActivity = (ClickedItemActivity)ClickedItemActivity.clickedItemActivity;

    private Spinner spinner;

    ImageButton CheckBtn;

    ImageView imageView;
    EditText editName;

    int selectedImage = -1;
    int idx = -1;
    int tag = -1;
    String tagName = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clicked_item_edit);

        clickedItemActivity.finish();

        spinner = findViewById(R.id.spinnerTag);

        imageView = findViewById(R.id.imageView);
        editName = findViewById(R.id.tvNameEdit);

        String selectedName = "";

        Intent intent = getIntent();
        if(intent.getExtras() != null){
            idx = intent.getIntExtra("index", 0);
            selectedName = mGalleryList.get(idx).getName();
            selectedImage = mGalleryList.get(idx).getImage();
            tag = mGalleryList.get(idx).getTag();

            imageView.setImageResource(selectedImage);
        }else{
            finish();
        }

        spinner.setSelection(tag);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                tag = i;
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        CheckBtn = findViewById(R.id.ic_check);
        CheckBtn.setOnClickListener(this);

        editName.setText(selectedName);
    }

    public void onClick(View v) {
        String name = editName.getText().toString();

        ImageFile image = mGalleryList.get(idx);
        image.setName(name);
        image.setTag(tag);

        mGalleryList.set(idx, image);
        galleryAdapter.notifyDataSetChanged();

        startActivity(new Intent(ClickedItemEditActivity.this,
                ClickedItemActivity.class).putExtra("index", idx));

        finish();

    }
}