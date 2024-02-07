package com.example.kstoredemo;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MemberAdapter extends RecyclerView.Adapter<MemberAdapter.MyViewHolder> {
    @NonNull
    RecyclerView recyclerView;
    private int selectedPosition = RecyclerView.NO_POSITION;
    private int preSelectedItemPosition = -1;
    private final Context context;
    Button scanButton;
    ArrayList<Members> membersArrayList = new ArrayList<>();

    public MemberAdapter(ArrayList<Members> membersArrayList, Context context, Button scanButton) {
        this.membersArrayList = membersArrayList;
        this.scanButton = scanButton;
        this.context = context;
    }

    @Override
    public MemberAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_memberview, parent, false);
        return new MyViewHolder(view);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull MemberAdapter.MyViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        Members members = membersArrayList.get(position);
        holder.names.setText(members.getName());
        holder.card.setText(members.getCardNumber());

        if (position == selectedPosition){
            holder.itemView.setBackgroundColor(ContextCompat.getColor(context,R.color.gray));
        }
        else {
            holder.itemView.setBackgroundColor(ContextCompat.getColor(context, android.R.color.white));
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int previousSelectedPosition = selectedPosition;
                selectedPosition = position;

                // Notify item changes for both the previous and new selections
                notifyItemChanged(previousSelectedPosition);
                notifyItemChanged(selectedPosition);
                notifyItemChanged(position);

            }
        });
    }

    @Override
    public int getItemCount() {
        return membersArrayList.size();

    }

    public void setPreSelectedItem(int position) {
        preSelectedItemPosition = position;
        notifyDataSetChanged();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private final TextView names;
        private final TextView card;
        Button scanFpButton;
        private final CardView cardView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            names = itemView.findViewById(R.id.memberName);
            card = itemView.findViewById(R.id.memberCard);
            cardView = itemView.findViewById(R.id.membercardView);
        }
    }
}
