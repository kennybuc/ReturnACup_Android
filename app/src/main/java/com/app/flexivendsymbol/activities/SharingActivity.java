package com.app.flexivendsymbol.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.app.flexivendsymbol.R;
import com.app.flexivendsymbol.activities.base.BaseActivity;

public class SharingActivity extends BaseActivity implements View.OnClickListener {

    public static final String KEY_SYMBOL_CODE = "KEY_SYMBOL_CODE";

    private String code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sharing);

        code = getIntent().getStringExtra(KEY_SYMBOL_CODE);
        TextView tvCode = findViewById(R.id.tvCode);
        tvCode.setText(code);

        // Wrap event handlers to view elements.
        findViewById(R.id.btnPrint).setOnClickListener(this);
        findViewById(R.id.btnSendToEmail).setOnClickListener(this);
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

    }

    private void sendToEmail() {

    }

}
