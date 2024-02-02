package com.hasan.multiconvert;


import static com.hasan.multiconvert.utils.AppBarUtil.setAppBarTitle;

import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.hasan.multiconvert.utils.HideKeyBoard;

import java.util.Locale;


public class LengthActivity extends AppCompatActivity {
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
        setContentView(R.layout.activity_length);
        setAppBarTitle(this);

        parentLayout = findViewById(R.id.lengthParentView);
        spinner = findViewById(R.id.spinnerUnits);
        editTextLengthValue = findViewById(R.id.editTextLengthValue);
        btnConvert = findViewById(R.id.btnConvert);
        btnClear = findViewById(R.id.btnClear);
        res = getResources();
        resourceUnits = res.getStringArray(R.array.length_units);

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

        double km, m, cm, mm, inch, foot, yard;

        if (spinnerText.contains("(km)")) {
            km = inputValue;
            m = inputValue * 1000;
            cm = inputValue * 100000;
            mm = inputValue * 1000000;
            inch = inputValue * 39370.1;
            foot = inputValue * 3280.84;
            yard = inputValue * 1093.61;
        } else if (spinnerText.contains("(m)")) {
            km = inputValue / 1000;
            m = inputValue;
            cm = inputValue * 100;
            mm = inputValue * 1000;
            inch = inputValue * 39.3701;
            foot = inputValue * 3.28084;
            yard = inputValue * 1.09361;
        } else if (spinnerText.contains("(cm)")) {
            km = inputValue / 100000;
            m = inputValue / 100;
            cm = inputValue;
            mm = inputValue * 10;
            inch = inputValue / 2.54;
            foot = inputValue / 30.48;
            yard = inputValue / 91.44;
        } else if (spinnerText.contains("(mm)")) {
            km = inputValue / 1000000;
            m = inputValue / 1000;
            cm = inputValue / 10;
            mm = inputValue;
            inch = inputValue / 25.4;
            foot = inputValue / 304.8;
            yard = inputValue / 914.4;
        } else if (spinnerText.contains("(in)")) {
            km = inputValue / 39370.1;
            m = inputValue / 39.3701;
            cm = inputValue * 2.54;
            mm = inputValue * 25.4;
            inch = inputValue;
            foot = inputValue / 12;
            yard = inputValue / 36;
        } else if (spinnerText.contains("(ft)")) {
            km = inputValue / 3280.84;
            m = inputValue / 3.28084;
            cm = inputValue * 30.48;
            mm = inputValue * 304.8;
            inch = inputValue * 12;
            foot = inputValue;
            yard = inputValue / 3;
        } else if (spinnerText.contains("(yd)")) {
            km = inputValue / 1093.61;
            m = inputValue / 1.09361;
            cm = inputValue * 91.44;
            mm = inputValue * 914.4;
            inch = inputValue * 36;
            foot = inputValue * 3;
            yard = inputValue;
        } else {
            km = m = cm = mm = inch = foot = yard = 0.0;
        }

        return new double[]{km, m, cm, mm, inch, foot, yard};
    }


}