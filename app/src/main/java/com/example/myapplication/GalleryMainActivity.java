package com.example.myapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class GalleryMainActivity extends AppCompatActivity {

    GridView gridView;

    ArrayList<ImageFile> items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery_main);

        gridView = findViewById(R.id.gridView);

        items = new ArrayList<>();
        items.add(new ImageFile("image1", R.drawable.image1));
        items.add(new ImageFile("image2", R.drawable.image2));
        items.add(new ImageFile("image3", R.drawable.image3));
        items.add(new ImageFile("image4", R.drawable.image4));
        items.add(new ImageFile("image5", R.drawable.image5));
        items.add(new ImageFile("image6", R.drawable.image6));
        items.add(new ImageFile("image7", R.drawable.image7));
        items.add(new ImageFile("image8", R.drawable.image8));
        items.add(new ImageFile("image9", R.drawable.image9));
        items.add(new ImageFile("image10", R.drawable.image10));
        items.add(new ImageFile("image11", R.drawable.image11));
        items.add(new ImageFile("image12", R.drawable.image12));
        items.add(new ImageFile("image13", R.drawable.image13));
        items.add(new ImageFile("image14", R.drawable.image14));
        items.add(new ImageFile("image15", R.drawable.image15));
        items.add(new ImageFile("image16", R.drawable.image16));


        GalleryMainActivity.CustomAdapter customAdapter = new GalleryMainActivity.CustomAdapter(items, this);

        gridView.setAdapter(customAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                String selectedName = items.get(i).name;
                int selectedImage = items.get(i).image;

                startActivity(new Intent(GalleryMainActivity.this, ClickedItemActivity.class).putExtra("name", selectedName).putExtra("image", selectedImage));
            }
        });

        gridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {


                AlertDialog.Builder builder = new AlertDialog.Builder(GalleryMainActivity.this);
                builder.setCancelable(true);
                builder.setTitle("Gallery");
                builder.setMessage("Delete "+items.get(i).name+"?");
                builder.setPositiveButton("Confirm",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int j) {
                                items.remove(i);
                                customAdapter.notifyDataSetChanged();
                                Log.d("t", "pos");
                            }
                        });
                builder.setNegativeButton(R.string.cancle, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Log.d("t","neg");
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();

                return true;
            }
        });

    }

    public class CustomAdapter extends BaseAdapter {

        ArrayList<ImageFile> items;
        Context context;

        private LayoutInflater layoutInflater;

        public CustomAdapter(ArrayList<ImageFile> items, Context context) {
            this.items = items;
            this.context = context;
            this.layoutInflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return items.size();
        }

        @Override
        public Object getItem(int i) {
            return items.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {

            if(view == null){
                view = layoutInflater.inflate(R.layout.row_items, viewGroup, false);
            }

            TextView tvName = view.findViewById(R.id.tvName);
            ImageView imageView = view.findViewById(R.id.imageView);

            tvName.setText(items.get(i).name);
            imageView.setImageResource(items.get(i).image);

            return view;
        }
    }

    public class ImageFile {
        String name;
        int image;

        ImageFile(String name, int image){
            this.name = name;
            this.image = image;
        }
    }
}


