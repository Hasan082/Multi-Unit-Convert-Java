package com.hasan.multiconvert.UnitActivity;

import static com.hasan.multiconvert.utils.AppBarUtil.setAppBarTitle;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.hasan.multiconvert.R;
import com.hasan.multiconvert.utils.HideKeyBoard;

import java.util.Locale;

public class SpeedActivity extends AppCompatActivity {
    LinearLayout parentLayout;
    String formattedValue;
    Spinner spinner;
    EditText editTextLengthValue;
    Button btnConvert, btnClear;
    Resources res;
    String[] resourceUnits;
    double inputValue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speed);
        setAppBarTitle(this);

        parentLayout = findViewById(R.id.lengthParentView);
        spinner = findViewById(R.id.spinnerUnits);
        editTextLengthValue = findViewById(R.id.editTextLengthValue);
        btnConvert = findViewById(R.id.btnConvert);
        btnClear = findViewById(R.id.btnClear);
        res = getResources();
        resourceUnits = res.getStringArray(R.array.speed_units);

        btnConvert.setOnClickListener(v -> {
            String edyTxtStr = editTextLengthValue.getText().toString().trim();

            if(!edyTxtStr.isEmpty()){
                inputValue = Double.parseDouble(edyTxtStr);
                String spinnerText = spinner.getSelectedItem().toString();
                double[] unitValArray = ConvertUnits(inputValue, spinnerText);

                for (int i = 0; i < resourceUnits.length; i++) {
                    View unitLayoutView = getLayoutInflater().inflate(R.layout.length_unit_layout, parentLayout, false);
                    TextView unitName = unitLayoutView.findViewById(R.id.unitMetric);
                    TextView unitVal = unitLayoutView.findViewById(R.id.unitValue);
                    String kmValue = resourceUnits[i];
                    String formattedText = kmValue + getString(R.string.unitIndicator) + " ";
                    unitName.setText(formattedText);
                    if (unitValArray[i] % 1 == 0) {
                        formattedValue = String.format(Locale.US, "%.0f", unitValArray[i]);
                    } else if (unitValArray[i] * 1000 % 1 == 0) {
                        String stringValue = String.format(Locale.US, "%.3f", unitValArray[i]);
                        formattedValue = stringValue.replaceAll("\\.?0*$", "");
                    } else {
                        String stringValue = String.format(Locale.US, "%.5f", unitValArray[i]);
                        formattedValue = stringValue.replaceAll("\\.?0*$", "");
                    }
                    unitVal.setText(formattedValue);

                    if (resourceUnits[i].equals(spinnerText)) {
                        unitLayoutView.setVisibility(View.GONE);
                    }

                    parentLayout.addView(unitLayoutView);
                }

                editTextLengthValue.clearFocus();
                HideKeyBoard.hideKeyboard(this, v);
                btnConvert.setVisibility(View.GONE);
                btnClear.setVisibility(View.VISIBLE);

            } else {
                HideKeyBoard.hideKeyboard(this, v);
                Toast.makeText(this, "Please input length", Toast.LENGTH_LONG).show();
            }
        });

        btnClear.setOnClickListener(v -> {
            editTextLengthValue.setText("");
            spinner.setSelection(0);
            parentLayout.removeAllViews();
            btnConvert.setVisibility(View.VISIBLE);
            btnClear.setVisibility(View.GONE);
        });




    }

    private double[] ConvertUnits(double inputValue, String spinnerText) {
        double meterPerSecond, kilometerPerHour, milesPerHour, knots, feetPerSecond, yardPerSecond, kilometerPerMinute;

        if (spinnerText.contains("(m/s)")) {
            meterPerSecond = inputValue;
            kilometerPerHour = inputValue * 3.6;
            milesPerHour = inputValue * 2.23694;
            knots = inputValue * 1.94384;
            feetPerSecond = inputValue * 3.28084;
            yardPerSecond = inputValue * 1.09361;
            kilometerPerMinute = inputValue * 0.06;
        } else if (spinnerText.contains("(km/h)")) {
            meterPerSecond = inputValue / 3.6;
            kilometerPerHour = inputValue;
            milesPerHour = inputValue * 0.621371;
            knots = inputValue * 0.539957;
            feetPerSecond = inputValue * 0.911344;
            yardPerSecond = inputValue * 0.3048;
            kilometerPerMinute = inputValue * 0.0166667;
        } else if (spinnerText.contains("(mph)")) {
            meterPerSecond = inputValue / 2.23694;
            kilometerPerHour = inputValue / 0.621371;
            milesPerHour = inputValue;
            knots = inputValue * 0.868976;
            feetPerSecond = inputValue * 1.46667;
            yardPerSecond = inputValue * 0.488889;
            kilometerPerMinute = inputValue * 0.0277778;
        } else if (spinnerText.contains("(kn)")) {
            meterPerSecond = inputValue / 1.94384;
            kilometerPerHour = inputValue / 0.539957;
            milesPerHour = inputValue / 0.868976;
            knots = inputValue;
            feetPerSecond = inputValue * 1.68781;
            yardPerSecond = inputValue * 0.562704;
            kilometerPerMinute = inputValue * 0.0299765;
        } else if (spinnerText.contains("(ft/s)")) {
            meterPerSecond = inputValue / 3.28084;
            kilometerPerHour = inputValue / 0.911344;
            milesPerHour = inputValue / 1.46667;
            knots = inputValue / 1.68781;
            feetPerSecond = inputValue;
            yardPerSecond = inputValue * 0.333333;
            kilometerPerMinute = inputValue * 0.018288;
        } else if (spinnerText.contains("(yd/s)")) {
            meterPerSecond = inputValue / 1.09361;
            kilometerPerHour = inputValue / 0.3048;
            milesPerHour = inputValue / 0.488889;
            knots = inputValue / 0.562704;
            feetPerSecond = inputValue / 0.333333;
            yardPerSecond = inputValue;
            kilometerPerMinute = inputValue * 0.0546807;
        } else if (spinnerText.contains("(km/min)")) {
            meterPerSecond = inputValue / 0.06;
            kilometerPerHour = inputValue / 0.0166667;
            milesPerHour = inputValue / 0.0277778;
            knots = inputValue / 0.0299765;
            feetPerSecond = inputValue / 0.018288;
            yardPerSecond = inputValue / 0.0546807;
            kilometerPerMinute = inputValue;
        } else {
            meterPerSecond = kilometerPerHour = milesPerHour = knots = feetPerSecond = yardPerSecond = kilometerPerMinute = 0.0;
        }

        return new double[]{meterPerSecond, kilometerPerHour, milesPerHour, knots, feetPerSecond, yardPerSecond, kilometerPerMinute};
    }


}