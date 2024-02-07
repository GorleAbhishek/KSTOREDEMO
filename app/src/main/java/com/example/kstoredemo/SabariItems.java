package com.example.kstoredemo;

import java.io.Serializable;

public class SabariItems implements Serializable {

    String ItemName,sabariItemQtyEnter,sabariItempriceEnter;
    public SabariItems(String ItemName)
    {
        this.ItemName = ItemName;
    }

    public String getItemName() {
        return ItemName;
    }

    public void setItemName(String itemName) {
        ItemName = itemName;
    }

    public String getSabariItemQtyEnter() {
        return sabariItemQtyEnter;
    }

    public void setSabariItemQtyEnter(String sabariItemQtyEnter) {
        this.sabariItemQtyEnter = sabariItemQtyEnter;
    }

    public String getSabariItempriceEnter() {
        return sabariItempriceEnter;
    }

    public void setSabariItempriceEnter(String sabariItempriceEnter) {
        this.sabariItempriceEnter = sabariItempriceEnter;
    }
}
