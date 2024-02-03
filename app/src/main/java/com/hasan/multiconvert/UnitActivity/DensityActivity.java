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

public class DensityActivity extends AppCompatActivity {
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
        setContentView(R.layout.activity_density);
        setAppBarTitle(this);

        parentLayout = findViewById(R.id.lengthParentView);
        spinner = findViewById(R.id.spinnerUnits);
        editTextLengthValue = findViewById(R.id.editTextLengthValue);
        btnConvert = findViewById(R.id.btnConvert);
        btnClear = findViewById(R.id.btnClear);
        res = getResources();
        resourceUnits = res.getStringArray(R.array.density_units);

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
        double kgPerCubicMeter, gPerCubicCentimeter, gPerLiter, lbPerCubicInch, lbPerCubicFoot, ozPerCubicInch;

        if (spinnerText.contains("(kg/m³)")) {
            kgPerCubicMeter = inputValue;
            gPerCubicCentimeter = inputValue / 1000;
            gPerLiter = inputValue / 1000;
            lbPerCubicInch = inputValue * 6.2428e-5;
            lbPerCubicFoot = inputValue * 0.0624279;
            ozPerCubicInch = inputValue * 0.0353147;
        } else if (spinnerText.contains("(g/cm³)")) {
            kgPerCubicMeter = inputValue * 1000;
            gPerCubicCentimeter = inputValue;
            gPerLiter = inputValue * 1000;
            lbPerCubicInch = inputValue * 0.0000353147;
            lbPerCubicFoot = inputValue * 0.0353147;
            ozPerCubicInch = inputValue * 0.035274;
        } else if (spinnerText.contains("(g/L)")) {
            kgPerCubicMeter = inputValue * 1000;
            gPerCubicCentimeter = inputValue / 1000;
            gPerLiter = inputValue;
            lbPerCubicInch = inputValue * 0.0000353147;
            lbPerCubicFoot = inputValue * 0.0353147;
            ozPerCubicInch = inputValue * 0.035274;
        } else if (spinnerText.contains("(lb/in³)")) {
            kgPerCubicMeter = inputValue * 16.0185;
            gPerCubicCentimeter = inputValue * 453.592;
            gPerLiter = inputValue * 453.592;
            lbPerCubicInch = inputValue;
            lbPerCubicFoot = inputValue * 1728;
            ozPerCubicInch = inputValue * 27.6799;
        } else if (spinnerText.contains("(lb/ft³)")) {
            kgPerCubicMeter = inputValue * 0.0160185;
            gPerCubicCentimeter = inputValue * 0.453592;
            gPerLiter = inputValue * 0.453592;
            lbPerCubicInch = inputValue * 0.000578704;
            lbPerCubicFoot = inputValue;
            ozPerCubicInch = inputValue * 0.000496507;
        } else if (spinnerText.contains("(oz/in³)")) {
            kgPerCubicMeter = inputValue * 0.453592;
            gPerCubicCentimeter = inputValue * 28.3495;
            gPerLiter = inputValue * 28.3495;
            lbPerCubicInch = inputValue * 0.0361273;
            lbPerCubicFoot = inputValue * 1.00115;
            ozPerCubicInch = inputValue;
        } else {
            kgPerCubicMeter = gPerCubicCentimeter = gPerLiter = lbPerCubicInch = lbPerCubicFoot = ozPerCubicInch = 0.0;
        }

        return new double[]{kgPerCubicMeter, gPerCubicCentimeter, gPerLiter, lbPerCubicInch, lbPerCubicFoot, ozPerCubicInch};
    }



}