package com.example.myapplication.Gallery;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.R;

import java.util.ArrayList;

public class GalleryAdapter extends BaseAdapter {

    private ArrayList<ImageFile> items;
    Context context;

    private LayoutInflater layoutInflater;

    public GalleryAdapter(ArrayList<ImageFile> items, Context context) {
        this.items = items;
        this.context = context;
        this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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

        tvName.setText(items.get(i).getName());
        imageView.setImageResource(items.get(i).getImage());

        return view;
    }

    public void deleteItem(int i) {
        items.remove(i);
        this.notifyDataSetChanged();
    }

}
