package com.example.myapplication.Home;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Contact.Contact;
import com.example.myapplication.R;

import java.util.ArrayList;

public class HomeContactAdapter extends RecyclerView.Adapter<HomeContactAdapter.ContactViewHolder>{
    private ArrayList<Contact> mList;
    private HomeContactAdapter.RecyclerViewClickListener listener;

    public HomeContactAdapter(ArrayList<Contact> list, HomeContactAdapter.RecyclerViewClickListener listener) {
        mList = list;
        this.listener = listener;
    }

    public interface RecyclerViewClickListener {
        void onClick(View v, int position);
    }

    public class ContactViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        protected TextView name;

        public ContactViewHolder(View view) {
            super(view);
            this.name = view.findViewById(R.id.contact_item_name);

            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            listener.onClick(v, getAbsoluteAdapterPosition());
        }
    }

    @NonNull
    @Override
    public HomeContactAdapter.ContactViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.home_contact_fragment, viewGroup, false);

        return new HomeContactAdapter.ContactViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeContactAdapter.ContactViewHolder holder, final int position) {
        holder.name.setText(mList.get(position).getName());
        holder.name.setGravity(Gravity.CENTER);
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }
}
