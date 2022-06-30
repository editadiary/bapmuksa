package com.example.myapplication;

import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.sql.Array;
import java.util.ArrayList;

public class Contact_Adapter extends RecyclerView.Adapter<Contact_Adapter.ContactViewHolder> {
    private ArrayList<Contact> mList;

    public Contact_Adapter(ArrayList<Contact> list) {
        this.mList = list;
    }

    public static class ContactViewHolder extends RecyclerView.ViewHolder {
        protected TextView name;
        protected TextView phone;

        public ContactViewHolder(View view) {
            super(view);
            this.name = (TextView)view.findViewById(R.id.contact_item_name);
            this.phone = (TextView)view.findViewById(R.id.contact_item_phone);
        }
    }

    @Override
    public ContactViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.recycler_item, viewGroup, false);

        return new ContactViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactViewHolder holder, final int position) {
        holder.name.setText(mList.get(position).getName());
        holder.phone.setText(mList.get(position).getPhone());

        holder.name.setGravity(Gravity.CENTER);
        holder.phone.setGravity(Gravity.LEFT);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
}
