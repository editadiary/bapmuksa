package com.example.myapplication.Contact;

import android.content.Intent;
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
    private static ArrayList<Contact> mList;
    private RecyclerViewClickListener listener;

    public ContactAdapter(ArrayList<Contact> list, RecyclerViewClickListener listener) {
        mList = list;
        this.listener = listener;
    }

    public interface RecyclerViewClickListener {
        void onClick(View v, int position);
    }

    public class ContactViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        protected TextView name;
        protected TextView phone;

        public ContactViewHolder(View view) {
            super(view);
            this.name = view.findViewById(R.id.contact_item_name);
            this.phone = view.findViewById(R.id.contact_item_phone);

            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            listener.onClick(v, getAdapterPosition());
        }
    }

    @NonNull
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
        holder.phone.setGravity(Gravity.START);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
}