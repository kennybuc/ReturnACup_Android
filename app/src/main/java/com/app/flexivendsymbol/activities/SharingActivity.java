package com.app.flexivendsymbol.activities;

import android.content.Intent;
import android.hardware.usb.UsbDevice;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import com.app.flexivendsymbol.R;
import com.app.flexivendsymbol.activities.base.BaseActivity;
import com.app.flexivendsymbol.api.APIClient;
import com.app.flexivendsymbol.helpers.AppUtils;
import com.app.flexivendsymbol.helpers.UIUtils;
import com.er.ERusbsdk.UsbController;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;

public class SharingActivity extends BaseActivity implements View.OnClickListener {

    public static final String KEY_SYMBOL_CODE = "KEY_SYMBOL_CODE";

    private String code;

    private UsbController usbController;
    private UsbDevice usbDevice;
    private Handler usbHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case UsbController.USB_CONNECTED:
                    break;
                default:
                    break;
            }
            return true;
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sharing);

        // Setup usb controller.
        usbController = new UsbController(this, usbHandler);
        connectPrinter();

        code = getIntent().getStringExtra(KEY_SYMBOL_CODE);

        // Wrap event handlers to view elements.
        findViewById(R.id.btnPrint).setOnClickListener(this);
        findViewById(R.id.btnSendToEmail).setOnClickListener(this);

        submitToken();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnPrint) {
            printToken();
        } else if (v.getId() == R.id.btnSendToEmail) {
            sendToEmail();
        }
    }

    private void printToken() {
        String token = getString(R.string.PRINT_STRING);
        if (checkUsbPermission()) {
            byte printStatus = usbController.revByte(usbDevice);
            if (printStatus == 0x38) {
                UIUtils.showMessage(this, R.string.paper_unavailable);
                return;
            }

            byte[] cmd_resume = new byte[4];
            cmd_resume[0] = 0x1B;
            cmd_resume[1] = 0x40; // reset command
            usbController.sendByte(cmd_resume, usbDevice);
            usbController.sendByte(new byte[]{0x1D, 0x21, 0x22}, usbDevice);
            usbController.sendMsg(token, "GBK", usbDevice);

            closeAndShowThankYou();
        } else {
            UIUtils.showMessage(this, R.string.printer_unavailable);
            connectPrinter();
        }
    }

    private void sendToEmail() {
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setType("plain/text");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{""});
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Return-A-Cup Token");
        emailIntent.putExtra(Intent.EXTRA_TEXT, code);
        startActivity(Intent.createChooser(emailIntent, "Send mail..."));
    }

    private void connectPrinter() {
        usbController.close();
        usbDevice = usbController.getUsbDev();

        if (usbDevice != null) {
            if (!(usbController.isHasPermission(usbDevice))) {
                usbController.getPermission(usbDevice);
            }
        }
    }

    //check access permission?
    private boolean checkUsbPermission() {
        if (usbDevice != null) {
            if (usbController.isHasPermission(usbDevice)) {
                return true;
            }
        }
        return false;
    }

    private void submitToken() {
        String apiKey = getString(R.string.API_KEY);
        APIClient.getAPIService().registerNumber(apiKey, code).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call,
                                   @NonNull retrofit2.Response<ResponseBody> response) {
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                t.printStackTrace();
            }
        });

        AppUtils.incrementCounterNumber();
    }

    private void closeAndShowThankYou() {
        startActivity(new Intent(this, ThankyouActivity.class));
        finish();
    }

}
