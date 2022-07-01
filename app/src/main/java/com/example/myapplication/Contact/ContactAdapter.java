package com.example.myapplication.Contact;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;

import java.util.ArrayList;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactViewHolder> {
    private ArrayList<Contact> mList;

    public ContactAdapter(ArrayList<Contact> list) {
        this.mList = list;
    }

    public static class ContactViewHolder extends RecyclerView.ViewHolder {
        protected TextView name;
        protected TextView phone;

        public ContactViewHolder(View view) {
            super(view);
            this.name = view.findViewById(R.id.contact_item_name);
            this.phone = view.findViewById(R.id.contact_item_phone);
        }
    }

    @Override
    public ContactViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.contact_fragment, viewGroup, false);

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