package com.example.kstoredemo;

import java.io.Serializable;

public class Members implements Serializable {
    public String name, cardNumber;
    private boolean isSelected;

    public Members(String name, String cardNumber) {
        this.name = name;
        this.cardNumber = cardNumber;
        this.isSelected = false;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
