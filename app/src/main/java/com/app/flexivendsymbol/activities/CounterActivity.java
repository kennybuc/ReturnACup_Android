package com.app.flexivendsymbol.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.app.flexivendsymbol.R;
import com.app.flexivendsymbol.activities.base.BaseActivity;
import com.app.flexivendsymbol.helpers.AppUtils;

public class CounterActivity extends BaseActivity {

    private TextView tvCounterNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_counter);

        // Map view elements to class members.
        tvCounterNumber = findViewById(R.id.tvCounterNumber);

        // Wrap event handlers to view elements.
        findViewById(R.id.btnStartScan).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CounterActivity.this, ScanningActivity.class));
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        tvCounterNumber.setText(String.valueOf(AppUtils.getCounterNumber()));
    }

}
