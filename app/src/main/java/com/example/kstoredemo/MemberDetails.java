package com.example.kstoredemo;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import java.io.StringReader;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilderFactory;

public class MemberDetails extends AppCompatActivity {
    RecyclerView recyclerView;
    MemberAdapter memberAdapter;
    private Context context;
    public String aadhaarNumber, rcNumber;
    TextView cardNumberView;
    private ProgressDialog dialog;
    private Button scanButton, backButton;
    TextView cardName;
    EditText cardEntereditText;
    ArrayList<Members> membersArrayList;
    String certificateIdentifier, dataType, dc, dpId, encHmac, mc, mid, rdId, rdVer, secure_pid, sessionKey, aadharNumber, deviceId;
    String errInfo;
    private static final int REQ_INFO = 12;
    int checkDevice;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_details);
        String timeout = "30000";
        final String pid_Options = "<PidOptions ver=\"1.0\">"
                + "<Opts fCount=\"1\" fType=\"0\" env=\"P\" format=\"0\" pidVer=\"2.0\" "
                + "timeout=\"" + timeout + "\" otp=\"\" wadh=\"\"/>"
                + "<Demo></Demo><CustOpts></CustOpts><Bios></Bios></PidOptions>\n";

        membersArrayList = new ArrayList<>();
        scanButton = findViewById(R.id.scanfpbtn);
        backButton = findViewById(R.id.backcd);
        cardNumberView = findViewById(R.id.cardNumberView);
        cardName = findViewById(R.id.cardName);
        cardEntereditText = findViewById(R.id.cardNumberEnter);
        aadhaarNumber = getIntent().getStringExtra("aadhaarNumber");
        rcNumber = getIntent().getStringExtra("rationcardNumber");
        if (rcNumber != null) {
            System.out.println("Rc Number: " + rcNumber);
            cardName.setText("RC:");
            cardNumberView.setText(rcNumber);
        }
        else if (aadhaarNumber != null) {
            System.out.println("Aadhaar Number: " + aadhaarNumber);
            cardName.setText("AADHAAR:");
            String trimAadhaar = aadhaarNumber.substring(8);
            cardNumberView.setText("XXXXXXXX" + trimAadhaar);
        }
        recyclerView = findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        membersArrayList.add(new Members("Abhishek ", "XXXXXXXX9874"));
        membersArrayList.add(new Members("Ganapathi ", "XXXXXXXX1234"));
        membersArrayList.add(new Members("Swaminath ", "XXXXXXXX8998"));
        membersArrayList.add(new Members("Srinivas ", "XXXXXXXX3696"));
        membersArrayList.add(new Members("Praveen ", "XXXXXXXX4078"));
        if (!membersArrayList.isEmpty()) {
            membersArrayList.get(0).isSelected();
        }
        memberAdapter = new MemberAdapter(membersArrayList, this, scanButton);
        recyclerView.setAdapter(memberAdapter);
        int preSelectedPosition = 2;
        memberAdapter.setPreSelectedItem(preSelectedPosition);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MemberDetails.this, Validation.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
        scanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (NetworkUtil.isInternetAvailable(MemberDetails.this)) {
                    makeReqValuesNull();
                    checkDevice = 0;
//                for demo
                    showToast("Capture Success");


//                    ******
//                    use when scanner available
//                    Use FingerScan
//                    go with below url its gov.Document
//                    https://uidai.gov.in/images/resource/aadhaar_registered_devices_2_0_09112016.pdf

//                    check letest uidai requrest url and try
//                    Intent intent = new Intent("in.gov.uidai.rdservice.fp.CAPTURE");
//                    intent.putExtra("PID_OPTIONS", pid_Options);
//                    startActivityForResult(intent, 2);
                } else {
                    showNoInternetDialog();
                    System.out.println("No internet");
                }
            }
        });
    }

    private void showNoInternetDialog() {
        new androidx.appcompat.app.AlertDialog.Builder(MemberDetails.this)
                .setTitle("No Internet Connection")
                .setCancelable(false)
                .setMessage("Please check your internet connection and try again.")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .show();
    }

    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case 1:
                if (resultCode == Activity.RESULT_OK) {
                    try {
                        if (data != null) {
                            String result = data.getStringExtra("DEVICE_INFO");
                            String rdService = data.getStringExtra("RD_SERVICE_INFO");
                            String display = "";
                            if (rdService != null) {
                                display = "RD Service Info :\n" + rdService + "\n\n";
                            }
                            if (result != null) {
                                display += "Device Info :\n" + result;
                                System.out.println("Display..." + display);
//                              showToast(display);
                            }
                        }
                    } catch (Exception e) {
                        Log.e("Error", "Error while deserialze device info", e);
                    }
                }
                break;
            case 2:
                if (resultCode == Activity.RESULT_OK) {
                    try {
                        if (data != null) {
                            String result = data.getStringExtra("PID_DATA");
                            System.out.println("result : " + result);
                            readParse(result);
                            if (result != null) {
                                /*pidData = serializer.read(PidData.class, result);
                                setText(result);*/
                                //System.out.println("RESULT..." + result);
                                //writeToFile(result);
                            }
                        }
                    } catch (Exception e) {
                        Log.e("Error", "Error while deserialze pid data", e);
                    }
                }
                break;
            case REQ_INFO:
                if (resultCode == Activity.RESULT_OK) {
                    String deviceInfo = data.getStringExtra("DEVICE_INFO");
                    if (deviceInfo != null) {
                        if (deviceInfo.isEmpty() || deviceInfo.equals("")) {
                            showToast("Get Device Info failed!");
                        } else {
                            try {
                                Toast.makeText(getApplicationContext(), deviceInfo, Toast.LENGTH_LONG).show();

                            } catch (Exception e) {
                                Toast.makeText(getApplicationContext(), "Process failed!", Toast.LENGTH_LONG).show();
                            }
                        }
                        return;
                    } else {
                        showToast("Can't open device. Please check plugin device  !");
                    }
                }
                break;
        }
    }

    public void showToast(final String toast) {
        AlertDialog.Builder alert = new AlertDialog.Builder(MemberDetails.this);
        alert.setTitle("Response");
        alert.setCancelable(false)
        .setIcon(R.drawable.keralaapplogo);
        alert.setMessage(toast);
        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                if (NetworkUtil.isInternetAvailable(MemberDetails.this)){
                    Intent intent = new Intent(MemberDetails.this, ProductsList.class);
                    intent.putExtra("aadhaarNumber",aadhaarNumber);
                    intent.putExtra("rcNumber",rcNumber);
                    startActivity(intent);
                    finish();
                }

            }
        });
        alert.show();
    }

    public void readParse(String str) {
        try {
            //aadharNumber = edtAadhar.getText().toString();
            DocumentBuilderFactory db =
                    DocumentBuilderFactory.newInstance();

            Document
                    inputDocument =
                    db.newDocumentBuilder().parse(new InputSource(new
                            StringReader(str)));


            NodeList nodes =
                    inputDocument.getElementsByTagName("PidData");
            if (nodes != null) {

                NodeList respNode =
                        inputDocument.getElementsByTagName("DeviceInfo");

                NodeList respNode1 =
                        inputDocument.getElementsByTagName("Hmac");

                NodeList respNode2 =
                        inputDocument.getElementsByTagName("Skey");

                if (respNode != null) {
                    Element elementValues = (Element) respNode.item(0);

                    Element certIdentifier = (Element) respNode2.item(0);

                    try {
                        System.out.println("loop started");
                        for (int i = 0; i <= nodes.getLength(); i++) {

                            Node nNode = nodes.item(i);
                            System.out.println("\nCurrent Element :" + nNode.getNodeName());
                            if (nNode.getNodeType() == Node.ELEMENT_NODE) {

                                Element eElement = (Element) nNode;
                                secure_pid = eElement.getElementsByTagName("Data").item(0).getTextContent();
                                sessionKey = eElement.getElementsByTagName("Skey").item(0).getTextContent();
                                encHmac = eElement.getElementsByTagName("Hmac").item(0).getTextContent();

                                dc = elementValues.getAttribute("dc");
                                dpId = elementValues.getAttribute("dpId");
                                mc = elementValues.getAttribute("mc");
                                mid = elementValues.getAttribute("mi");
                                rdId = elementValues.getAttribute("rdsId");
                                rdVer = elementValues.getAttribute("rdsVer");
                                deviceId = elementValues.getAttribute("did");
                                certificateIdentifier = certIdentifier.getAttribute("ci");
                                System.out.println("Ci....." + certificateIdentifier);
                                System.out.println("secure pid " + secure_pid.length() + "   " + secure_pid);
                            }
                        }
                    } catch (NullPointerException npe) {
                    }
                    if (certificateIdentifier != null & dc != null & dpId != null & encHmac != null & mc != null & mid != null & rdId != null & rdVer != null & secure_pid != null & sessionKey != null & deviceId != null) {
                        showToast("Capture Success");
                        Intent intent = new Intent(MemberDetails.this, ProductsList.class);
                        intent.putExtra("aadhaarNumber",aadhaarNumber);
                        intent.putExtra("rcNumber",rcNumber);
                        startActivity(intent);
                        finish();
                    } else {
                        showToast("Capture failed");
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("XML Pasing Excpetion = " + e);
        }
    }

    public void makeReqValuesNull() {
//        its a demo purpose
        certificateIdentifier = null;
        dataType = null;
        dc = null;
        dpId = null;
        encHmac = null;
        mc = null;
        mid = null;
        rdId = null;
        rdVer = null;
        secure_pid = null;
        sessionKey = null;
        aadharNumber = null;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(MemberDetails.this, Validation.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}