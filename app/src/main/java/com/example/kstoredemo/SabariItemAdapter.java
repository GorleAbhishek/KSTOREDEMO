package com.example.kstoredemo;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SabariItemAdapter extends RecyclerView.Adapter<SabariItemAdapter.MyViewHolder> {
    private final ArrayList<SabariItems> sabariItemsArrayList;
    SabariItems sabariItems;
    private final Runnable updateTotalCallback;

    public SabariItemAdapter(ArrayList<SabariItems> sabariItemsArrayList, Runnable updateTotalCallback) {
        this.updateTotalCallback = updateTotalCallback;
        this.sabariItemsArrayList = sabariItemsArrayList;
    }

    @NonNull
    @Override
    public SabariItemAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sabaricardview, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SabariItemAdapter.MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        SabariItems sabariItems = sabariItemsArrayList.get(position);
        holder.itemNameView.setText(sabariItems.getItemName());
        holder.sabariQuantityEnter.setText(sabariItems.getSabariItemQtyEnter());
        holder.sabariPriceEnter.setText(sabariItems.getSabariItempriceEnter());
    }

    @Override
    public int getItemCount() {
        return sabariItemsArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView itemNameView, sabaritotalPrice;
        EditText sabariQuantityEnter;
        EditText sabariPriceEnter;
        Button scanFPbutton;
        private Button printItemsButton;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            itemNameView = itemView.findViewById(R.id.sabariItemName);
            sabariQuantityEnter = itemView.findViewById(R.id.sabariQtyEnter);
            sabariPriceEnter = itemView.findViewById(R.id.sabariPriceEnter);
            sabaritotalPrice = itemView.findViewById(R.id.sabariTotalPrice);
            sabariPriceEnter.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    sabariItemsArrayList.get(getAdapterPosition()).setSabariItempriceEnter(s.toString());
                    updateProductTextView(getAdapterPosition());
                    updateTotalCallback.run();
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
            sabariQuantityEnter.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    sabariItemsArrayList.get(getAdapterPosition()).setSabariItemQtyEnter(s.toString());
                    updateProductTextView(getAdapterPosition());
                    updateTotalCallback.run();
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
        }
        private void updateProductTextView(int position) {
            SabariItems item = sabariItemsArrayList.get(position);
            try {
                int input1 = Integer.parseInt(item.getSabariItemQtyEnter());
                int input2 = Integer.parseInt(item.getSabariItempriceEnter());
                int product = input1 * input2;
                sabaritotalPrice.setText(String.valueOf(product));
            } catch (NumberFormatException e) {
                sabaritotalPrice.setText("0");
            }
        }
    }
}




