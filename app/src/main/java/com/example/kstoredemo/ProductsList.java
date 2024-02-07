package com.example.kstoredemo;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.cloudpos.DeviceException;
import com.cloudpos.POSTerminal;
import com.cloudpos.printer.Format;
import com.cloudpos.printer.PrinterDevice;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class ProductsList extends AppCompatActivity {
    PrinterDevice printerDevice;
    Format format;
    Bitmap bitmap;
    RecyclerView recyclerView;
    Button backButton, sabariPrint;
    private SabariItemAdapter sabariItemAdapter;
    ArrayList<SabariItems> sabariItemsArrayList = new ArrayList<>();
    private Context context;
    String aadhaarNumber, rcNumber;
    EditText editText1, editText2, totalEditText, adharNumber;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products_list);
        printerDevice = (PrinterDevice) POSTerminal.getInstance(getApplicationContext()).getDevice("cloudpos.device.printer");
        recyclerView = findViewById(R.id.sabarilistrecyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        editText1 = findViewById(R.id.sabariPriceEnter);
        editText2 = findViewById(R.id.sabariQtyEnter);
        aadhaarNumber = getIntent().getStringExtra("aadhaarNumber");
        rcNumber = getIntent().getStringExtra("rcNumber");
        System.out.println("AadhaarNumber:" + aadhaarNumber);
        System.out.println("Ration Card::" + rcNumber);
        totalEditText = findViewById(R.id.sabariItemTotal);
        adharNumber = findViewById(R.id.cardNumberEnter);
        backButton = findViewById(R.id.sabariBack);
        sabariPrint = findViewById(R.id.sabariPrint);
        sabariItemsArrayList.add(new SabariItems("Supreme(L) tea 1kg"));
        sabariItemsArrayList.add(new SabariItems("Supreme 100gm pkt"));
        sabariItemsArrayList.add(new SabariItems("Supreme 250gm pkt"));
        sabariItemsArrayList.add(new SabariItems("Supreme 500gm pkt"));
        sabariItemsArrayList.add(new SabariItems("Hotel Blend 1kg"));
        sabariItemsArrayList.add(new SabariItems("Hotel Blend 1/2kg"));
        sabariItemsArrayList.add(new SabariItems("Gold Tea 500gm"));
        sabariItemsArrayList.add(new SabariItems("Gold Tea 250gm"));
        sabariItemsArrayList.add(new SabariItems("Gold Tea 100gm"));
        sabariItemsArrayList.add(new SabariItems("SFD Tea 500gm"));
        sabariItemsArrayList.add(new SabariItems("SFD Tea 250gm"));
        sabariItemsArrayList.add(new SabariItems("SFD Tea 100gm"));
        sabariItemsArrayList.add(new SabariItems("Gold Jar 250gm"));
        sabariItemsArrayList.add(new SabariItems("Xtra Fresh 250gm"));
        sabariItemsArrayList.add(new SabariItems("Green Tea 100gm"));
        sabariItemsArrayList.add(new SabariItems("Leaf Tea 250gm"));
        sabariItemsArrayList.add(new SabariItems("Dip Tea bag 25 Nos"));
        sabariItemsArrayList.add(new SabariItems("Dip Tea bag 50 Nos"));
        sabariItemsArrayList.add(new SabariItems("prm Blk Tea 200gm"));
        sabariItemsArrayList.add(new SabariItems("prm Blk Tea 400gm"));
        sabariItemsArrayList.add(new SabariItems("C.nut Oil 1L Pouch"));
        sabariItemsArrayList.add(new SabariItems("Chilly pwdr 100gm"));
        sabariItemsArrayList.add(new SabariItems("Chilly pwdr 200gm"));
        sabariItemsArrayList.add(new SabariItems("Chilly pwdr 500gm"));
        sabariItemsArrayList.add(new SabariItems("Ksh chly pwr 100gm"));
        sabariItemsArrayList.add(new SabariItems("Corinder pwr 100gm"));
        sabariItemsArrayList.add(new SabariItems("Corinder Pwr 200gm"));
        sabariItemsArrayList.add(new SabariItems("Turmeric pwr 100gm"));
        sabariItemsArrayList.add(new SabariItems("Turmeric pwr 200gm"));
        sabariItemsArrayList.add(new SabariItems("Black Pepper 50gm"));
        sabariItemsArrayList.add(new SabariItems("Chickn masala 100g"));
        sabariItemsArrayList.add(new SabariItems("Meat Masala 100g"));
        sabariItemsArrayList.add(new SabariItems("Fish Masala 100g"));
        sabariItemsArrayList.add(new SabariItems("Garam Masala 50g"));
        sabariItemsArrayList.add(new SabariItems("Samber Powder 100g"));
        sabariItemsArrayList.add(new SabariItems("Rasam Powder 100g"));
        sabariItemsArrayList.add(new SabariItems("Cumin Seeds 100gm"));
        sabariItemsArrayList.add(new SabariItems("Fennel Seeds 100gm"));
        sabariItemsArrayList.add(new SabariItems("Fenugreek Seeds 100gm"));
        sabariItemsArrayList.add(new SabariItems("Mustard Seeds 100gm"));
        sabariItemsArrayList.add(new SabariItems("Coffee pwder 100gm"));
        sabariItemsArrayList.add(new SabariItems("Coffee Pwder 200gm"));
        sabariItemsArrayList.add(new SabariItems("Comp Aftd pwr 50g"));
        sabariItemsArrayList.add(new SabariItems("Comp Aftd pwr 100g"));
        sabariItemsArrayList.add(new SabariItems("Comp Aftd Cake 50g"));
        sabariItemsArrayList.add(new SabariItems("Comp Aftd Cake 100g"));
        sabariItemsArrayList.add(new SabariItems("Washing Soap 200gm"));
        sabariItemsArrayList.add(new SabariItems("Washing Soap 400gm"));
        sabariItemsArrayList.add(new SabariItems("Washing Soap 800gm"));
        sabariItemsArrayList.add(new SabariItems("Glmr T.Soap 100gm"));
        sabariItemsArrayList.add(new SabariItems("P.Chki Fr Atta 1kg"));
        sabariItemAdapter = new SabariItemAdapter(sabariItemsArrayList, this::updateTotal);
        recyclerView.setAdapter(sabariItemAdapter);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProductsList.this, MemberDetails.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
        sabariPrint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String a = totalEditText.getText().toString();
                int b = Integer.parseInt(a);
                System.out.println("the value of str:" + b);
                if (b == 0) {
                    new AlertDialog.Builder(ProductsList.this)
                            .setTitle("Error!")
                            .setMessage("Please Select atleast one Item")
                            .setCancelable(false)
                            .setIcon(R.drawable.keralaapplogo)
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            })
                            .show();
                } else {
                    new AlertDialog.Builder(ProductsList.this)
                            .setTitle("Success!")
                            .setMessage("Printing")
                            .setCancelable(false)
                            .setIcon(R.drawable.keralaapplogo)
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                    Intent intent1 = new Intent(ProductsList.this, Validation.class);
                                    startActivity(intent1);
                                }
                            })
                            .show();


                    int ifPrinterNotAva = 0;
                    if (ifPrinterNotAva == 1) {
//                        thermal printer divice dont have so now i stop
                        try {
                            AMisc miscobj = new AMisc();
                            String receiptNum = miscobj.DeviceId();
                            format = new Format();
                            printerDevice.open();
                            bitmap = BitmapFactory.decodeStream(getApplicationContext().getResources().getAssets().open("image.png"));
                            printerDevice.printBitmap(bitmap);
                            format.clear();
                            format.setParameter("bold", "true");
                            format.setParameter("size", "medium");
                            format.setParameter("align", "center");
                            printerDevice.printlnText(format, "GOVT OF KERALA");
                            printerDevice.printlnText(format, "CIVIL SUPPLIES DPT");
                            printerDevice.printlnText(format, "CASH BILL");
                            format.setParameter("size", "medium");
                            printerDevice.printlnText(format, String.format("Date:%s    Time:%s", new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date()), new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date())));
                            format.setParameter("bold", "true");
                            format.setParameter("size", "medium");
                            printerDevice.printlnText(format, "--------------------------------");
                            format.clear();
                            format.setParameter("size", "medium");
                            format.setParameter("align", "left");
                            format.clear();
                            printerDevice.printlnText(format, String.format("Receipt No.%s", receiptNum));
                            if (rcNumber != null) {
                                System.out.println("Rc Number: " + rcNumber);
                                printerDevice.printlnText(format, String.format("Rc No:%s", rcNumber));
                            } else if (aadhaarNumber != null) {
                                System.out.println("Aadhaar Number: " + aadhaarNumber);
                                String trimAadhaar = aadhaarNumber.substring(8);
                                printerDevice.printlnText(format, String.format("Aadhaar No:XXXXXXXX%s", trimAadhaar));
                            }
                            format.clear();
                            format.setParameter("bold", "true");
                            format.setParameter("size", "medium");
                            printerDevice.printlnText(format, "--------------------------------");
                            printerDevice.printlnText("ITEM NAME        QTY  MRP  TOTAL");
                            printerDevice.printlnText(format, "--------------------------------");
                            for (SabariItems item : sabariItemsArrayList) {
                                String itemName = item.getItemName();
                                int qty = 0;
                                String qtyStr = item.getSabariItemQtyEnter();
                                if (qtyStr != null && !qtyStr.isEmpty()) {
                                    try {
                                        qty = Integer.parseInt(qtyStr);
                                    } catch (NumberFormatException e) {
                                        e.printStackTrace();
                                    }
                                }
                                int price = 0;
                                String priceStr = item.getSabariItempriceEnter();
                                if (priceStr != null && !priceStr.isEmpty()) {
                                    try {
                                        price = Integer.parseInt(priceStr);
                                    } catch (NumberFormatException e) {
                                        e.printStackTrace();
                                    }
                                }
                                int total = qty * price;
                                if (total > 0) {
                                    String itemLine = String.format("%-15s %3d %3d %3d", itemName, qty, price, total);
                                    printerDevice.printlnText(itemLine);
                                }
                            }
                            printerDevice.printlnText(format, "--------------------------------");
                            format.clear();
                            format.setParameter("bold", "true");
                            format.setParameter("size", "medium");
                            format.setParameter("align", "center");
//                        String totalText = String.format("Total Amt:\u20B9%s", totalEditText.getText().toString());
//                        Bitmap textBitmap = textToBitmap(totalText, 400, 100); // Adjust width and height as needed
//

                            printerDevice.printlnText(format, String.format("Total Amt:%s", totalEditText.getText().toString()));
                            printerDevice.printlnText(format, "--------------------------------");
                            printerDevice.printlnText("\n\n\n\n\n\n\n");
                            printerDevice.close();
                            Intent intent1 = new Intent(ProductsList.this, Validation.class);
                            startActivity(intent1);


                        } catch (DeviceException e) {
                            throw new RuntimeException(e);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            }
        });
    }

    private void updateTotal() {
        int total = 0;
        for (SabariItems item : sabariItemsArrayList) {
            try {
                int input1 = Integer.parseInt(item.getSabariItemQtyEnter());
                int input2 = Integer.parseInt(item.getSabariItempriceEnter());
                total += (input1 * input2);
                System.out.println("input1:" + input1);
                System.out.println("input1:" + input2);
                System.out.println("total:" + total);
            } catch (NumberFormatException ignored) {
            }
        }
        totalEditText.setText(String.valueOf(total));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(ProductsList.this, MemberDetails.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}