package com.example.myapplication.Gallery;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

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

        CardView cardView = view.findViewById(R.id.cardView);

        TextView tvName = view.findViewById(R.id.tvName);
        ImageView imageView = view.findViewById(R.id.imageView);

        tvName.setText(items.get(i).getName());
        imageView.setImageResource(items.get(i).getImage());
        int tag = items.get(i).getTag();
        switch (tag) {
            case 0:
                cardView.setBackgroundResource(R.color.korean);
                break;
            case 1:
                cardView.setBackgroundResource(R.color.chinese);
                break;
            case 2:
                cardView.setBackgroundResource(R.color.japanese);
                break;
            case 3:
                cardView.setBackgroundResource(R.color.italian);
                break;
            case 4:
                cardView.setBackgroundResource(R.color.dessert);
                break;
            default:
                cardView.setBackgroundResource(R.color.etc);
                break;

        }

        return view;
    }

    public void deleteItem(int i) {
        items.remove(i);
        this.notifyDataSetChanged();
    }

}
