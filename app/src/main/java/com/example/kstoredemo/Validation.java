package com.example.kstoredemo;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class Validation extends AppCompatActivity {
    Button authentication,backButton;
    int aadhaarflag = 0;
    RadioGroup radioGroup;
    String aadhaar;
    RadioButton rationcardButton,aadhaarcardButton;
    EditText entercardNumber;
    int flag = 0;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_validation);
        entercardNumber = findViewById(R.id.cardNumberEnter);
        radioGroup = findViewById(R.id.radioGroup);
        authentication = findViewById(R.id.authenticate);
        backButton = findViewById(R.id.back);
        rationcardButton = findViewById(R.id.rationcardRadiobutton);
        aadhaarcardButton = findViewById(R.id.aadhaarcardradiobutton);
        rationcardButton.setEnabled(false);

        if (rationcardButton.isChecked()) {
//            entercardNumber.setHint("Enter Ration Card Number");
            entercardNumber.setHint("Use Aadhaar. RC Disable");
        } else if (aadhaarcardButton.isChecked()) {
            entercardNumber.setHint("Enter Aadhaar Card Number");
        }
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Validation.this, Services.class);
                startActivity(intent);
                finish();
            }
        });
        authentication.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rationcardButton.isChecked()) {
                    if (entercardNumber.getText().toString().length() != 10) {
                        Toast.makeText(Validation.this, "Please Enter Valid RC Number", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Intent intent = new Intent(Validation.this, MemberDetails.class);
                        intent.putExtra("rationcardNumber", entercardNumber.getText().toString());
                        startActivity(intent);

                    }
                } else if (aadhaarcardButton.isChecked()) {
                        aadhaar = entercardNumber.getText().toString();
                        boolean validate = VerhoeffAlgorithm.validateVerhoeff(aadhaar);
                        String vadilation = String.valueOf(validate);
                        if (vadilation == "true" && entercardNumber.getText().toString().length() == 12) {
                            Toast.makeText(Validation.this, "Aadhaar verified", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(Validation.this, MemberDetails.class);
                            intent.putExtra("aadhaarNumber", entercardNumber.getText().toString());
                            startActivity(intent);

                        } else {
                            Toast.makeText(Validation.this, "Please Enter Valid Aadhaar Number", Toast.LENGTH_SHORT).show();
                        }
                }
            }
        });
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.rationcardRadiobutton) {
                    entercardNumber.setHint("Enter Ration Card Number");
                    entercardNumber.setText("");
                    entercardNumber.setInputType(EditorInfo.TYPE_CLASS_NUMBER);
                } else if (checkedId == R.id.aadhaarcardradiobutton) {
                    entercardNumber.setHint("Enter Aadhaar Card Number");
                    entercardNumber.setText("");
                    entercardNumber.setInputType(EditorInfo.TYPE_CLASS_NUMBER);
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(Validation.this, Services.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}