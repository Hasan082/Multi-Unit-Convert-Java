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

public class AreaActivity extends AppCompatActivity {
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
        setContentView(R.layout.activity_area);
        setAppBarTitle(this);


        parentLayout = findViewById(R.id.lengthParentView);
        spinner = findViewById(R.id.spinnerUnits);
        editTextLengthValue = findViewById(R.id.editTextLengthValue);
        btnConvert = findViewById(R.id.btnConvert);
        btnClear = findViewById(R.id.btnClear);
        res = getResources();
        resourceUnits = res.getStringArray(R.array.area_units);

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
        double squareKm, squareMeter, squareCm, squareMm, squareInch, squareFoot, squareYard;

        if (spinnerText.contains("(km²)")) {
            squareKm = inputValue;
            squareMeter = inputValue * 1_000_000;
            squareCm = inputValue * 10_000_000_000.0;
            squareMm = inputValue * 1_000_000_000_000.0;
            squareInch = inputValue * 1_550_003.1;
            squareFoot = inputValue * 10_764;
            squareYard = inputValue * 1_195.99;
        } else if (spinnerText.contains("(m²)")) {
            squareKm = inputValue / 1_000_000;
            squareMeter = inputValue;
            squareCm = inputValue * 10_000;
            squareMm = inputValue * 1_000_000;
            squareInch = inputValue * 1550.0031;
            squareFoot = inputValue * 10.764;
            squareYard = inputValue * 1.19599;
        } else if (spinnerText.contains("(cm²)")) {
            squareKm = inputValue / 10_000_000_000.0;
            squareMeter = inputValue / 10_000;
            squareCm = inputValue;
            squareMm = inputValue * 100;
            squareInch = inputValue * 0.15500031;
            squareFoot = inputValue * 0.0010764;
            squareYard = inputValue * 0.000119599;
        } else if (spinnerText.contains("(mm²)")) {
            squareKm = inputValue / 1_000_000_000_000.0;
            squareMeter = inputValue / 1_000_000;
            squareCm = inputValue / 100;
            squareMm = inputValue;
            squareInch = inputValue * 0.00015500031;
            squareFoot = inputValue * 0.0000010764;
            squareYard = inputValue * 0.000000119599;
        } else if (spinnerText.contains("(in²)")) {
            squareKm = inputValue / 1_550_003.1;
            squareMeter = inputValue / 1550.0031;
            squareCm = inputValue / 0.15500031;
            squareMm = inputValue / 0.00015500031;
            squareInch = inputValue;
            squareFoot = inputValue / 144;
            squareYard = inputValue / 1296;
        } else if (spinnerText.contains("(ft²)")) {
            squareKm = inputValue / 10_764;
            squareMeter = inputValue / 10.764;
            squareCm = inputValue / 0.0010764;
            squareMm = inputValue / 0.0000010764;
            squareInch = inputValue * 144;
            squareFoot = inputValue;
            squareYard = inputValue / 9;
        } else if (spinnerText.contains("(yd²)")) {
            squareKm = inputValue / 1_195.99;
            squareMeter = inputValue / 1.19599;
            squareCm = inputValue / 0.000119599;
            squareMm = inputValue / 0.000000119599;
            squareInch = inputValue * 1296;
            squareFoot = inputValue * 9;
            squareYard = inputValue;
        } else {
            squareKm = squareMeter = squareCm = squareMm = squareInch = squareFoot = squareYard = 0.0;
        }

        return new double[]{squareKm, squareMeter, squareCm, squareMm, squareInch, squareFoot, squareYard};
    }





}