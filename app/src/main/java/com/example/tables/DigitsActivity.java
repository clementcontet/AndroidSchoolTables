package com.example.tables;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CheckBox;

public class DigitsActivity extends AppCompatActivity {
    private boolean[] mDigits = new boolean[]{false, false, false, false, false, false, false, false, false, false};
    private int mSelected = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_digits);

        findViewById(R.id.addition).setOnClickListener(v -> {
            computeSelected();
            if (mSelected > 0) {
                Intent newIntent = new Intent(this, ComputeActivity.class);
                newIntent.putExtra(ComputeActivity.EXTRA_IS_ADDITION, true);
                newIntent.putExtra(ComputeActivity.EXTRA_DIGITS, mDigits);
                startActivity(newIntent);
            }
        });

        findViewById(R.id.multiplication).setOnClickListener(v -> {
            computeSelected();
            if (mSelected > 0) {
                Intent newIntent = new Intent(this, ComputeActivity.class);
                newIntent.putExtra(ComputeActivity.EXTRA_IS_ADDITION, false);
                newIntent.putExtra(ComputeActivity.EXTRA_DIGITS, mDigits);
                startActivity(newIntent);
            }
        });

        ((CheckBox) findViewById(R.id.digit_1)).setOnCheckedChangeListener((buttonView, isChecked) -> mDigits[0] = isChecked);
        ((CheckBox) findViewById(R.id.digit_2)).setOnCheckedChangeListener((buttonView, isChecked) -> mDigits[1] = isChecked);
        ((CheckBox) findViewById(R.id.digit_3)).setOnCheckedChangeListener((buttonView, isChecked) -> mDigits[2] = isChecked);
        ((CheckBox) findViewById(R.id.digit_4)).setOnCheckedChangeListener((buttonView, isChecked) -> mDigits[3] = isChecked);
        ((CheckBox) findViewById(R.id.digit_5)).setOnCheckedChangeListener((buttonView, isChecked) -> mDigits[4] = isChecked);
        ((CheckBox) findViewById(R.id.digit_6)).setOnCheckedChangeListener((buttonView, isChecked) -> mDigits[5] = isChecked);
        ((CheckBox) findViewById(R.id.digit_7)).setOnCheckedChangeListener((buttonView, isChecked) -> mDigits[6] = isChecked);
        ((CheckBox) findViewById(R.id.digit_8)).setOnCheckedChangeListener((buttonView, isChecked) -> mDigits[7] = isChecked);
        ((CheckBox) findViewById(R.id.digit_9)).setOnCheckedChangeListener((buttonView, isChecked) -> mDigits[8] = isChecked);
        ((CheckBox) findViewById(R.id.digit_10)).setOnCheckedChangeListener((buttonView, isChecked) -> mDigits[9] = isChecked);
    }

    private void computeSelected() {
        mSelected = 0;
        for (boolean digit : mDigits) {
            if (digit) {
                mSelected++;
            }
        }
    }
}
