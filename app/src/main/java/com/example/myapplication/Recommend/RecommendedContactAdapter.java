package com.example.myapplication.Recommend;

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

public class RecommendedContactAdapter extends RecyclerView.Adapter<RecommendedContactAdapter.RecommendViewHolder> {
    private static ArrayList<Contact> mList;
    private RecommendedContactAdapter.RecyclerViewClickListener listener;

    public RecommendedContactAdapter(ArrayList<Contact> mList, RecommendedContactAdapter.RecyclerViewClickListener listener) {
        this.mList = mList;
        this.listener = listener;
    }

    public interface RecyclerViewClickListener {
        void onClick(View v, int position);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class RecommendViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        protected TextView name;
        // protected TextView phone;

        public RecommendViewHolder(View view) {
            super(view);
            this.name = view.findViewById(R.id.recommendTextName);
            // this.phone = view.findViewById(R.id.contact_item_phone);

            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            listener.onClick(v, getAbsoluteAdapterPosition());
        }
    }


    @NonNull
    @Override
    public RecommendedContactAdapter.RecommendViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.recommened_third_contact_fragment, viewGroup, false);

        return new RecommendedContactAdapter.RecommendViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull RecommendedContactAdapter.RecommendViewHolder holder, final int position) {
        holder.name.setText(mList.get(position).getName());
        // holder.phone.setText(mList.get(position).getPhone());

        holder.name.setGravity(Gravity.CENTER);
        // holder.phone.setGravity(Gravity.START);
    }

}
