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

public class VolumeActivity extends AppCompatActivity {
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
        setContentView(R.layout.activity_volume);
        setAppBarTitle(this);

        parentLayout = findViewById(R.id.lengthParentView);
        spinner = findViewById(R.id.spinnerUnits);
        editTextLengthValue = findViewById(R.id.editTextLengthValue);
        btnConvert = findViewById(R.id.btnConvert);
        btnClear = findViewById(R.id.btnClear);
        res = getResources();
        resourceUnits = res.getStringArray(R.array.volume_units);

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
        double liter, milliliter, cubicMeter, cubicCentimeter, cubicMillimeter, usGallon, usQuart, usFluidOunce;

        if (spinnerText.contains("(L)")) {
            liter = inputValue;
            milliliter = inputValue * 1000;
            cubicMeter = inputValue * 0.001;
            cubicCentimeter = inputValue * 1000;
            cubicMillimeter = inputValue * 1_000_000;
            usGallon = inputValue * 0.264172;
            usQuart = inputValue * 1.05669;
            usFluidOunce = inputValue * 33.814;
        } else if (spinnerText.contains("(mL)")) {
            liter = inputValue * 0.001;
            milliliter = inputValue;
            cubicMeter = inputValue * 1e-6;
            cubicCentimeter = inputValue;
            cubicMillimeter = inputValue * 1000;
            usGallon = inputValue * 0.000264172;
            usQuart = inputValue * 0.00105669;
            usFluidOunce = inputValue * 0.033814;
        } else if (spinnerText.contains("(m³)")) {
            liter = inputValue * 1000;
            milliliter = inputValue * 1e+6;
            cubicMeter = inputValue;
            cubicCentimeter = inputValue * 1e+6;
            cubicMillimeter = inputValue * 1e+9;
            usGallon = inputValue * 264.172;
            usQuart = inputValue * 1056.69;
            usFluidOunce = inputValue * 33814;
        } else if (spinnerText.contains("(cm³)")) {
            liter = inputValue * 0.001;
            milliliter = inputValue;
            cubicMeter = inputValue * 1e-6;
            cubicCentimeter = inputValue;
            cubicMillimeter = inputValue * 1000;
            usGallon = inputValue * 0.000264172;
            usQuart = inputValue * 0.00105669;
            usFluidOunce = inputValue * 0.033814;
        } else if (spinnerText.contains("(mm³)")) {
            liter = inputValue * 1e-6;
            milliliter = inputValue * 0.001;
            cubicMeter = inputValue * 1e-9;
            cubicCentimeter = inputValue * 0.001;
            cubicMillimeter = inputValue;
            usGallon = inputValue * 2.64172e-7;
            usQuart = inputValue * 1.05669e-6;
            usFluidOunce = inputValue * 3.3814e-5;
        } else if (spinnerText.contains("(gal)")) {
            liter = inputValue * 3.78541;
            milliliter = inputValue * 3785.41;
            cubicMeter = inputValue * 0.00378541;
            cubicCentimeter = inputValue * 3785.41;
            cubicMillimeter = inputValue * 3.78541e+6;
            usGallon = inputValue;
            usQuart = inputValue * 4;
            usFluidOunce = inputValue * 128;
        } else if (spinnerText.contains("(qt)")) {
            liter = inputValue * 0.946353;
            milliliter = inputValue * 946.353;
            cubicMeter = inputValue * 0.000946353;
            cubicCentimeter = inputValue * 946.353;
            cubicMillimeter = inputValue * 946353;
            usGallon = inputValue * 0.25;
            usQuart = inputValue;
            usFluidOunce = inputValue * 32;
        } else if (spinnerText.contains("(fl oz)")) {
            liter = inputValue * 0.0295735;
            milliliter = inputValue * 29.5735;
            cubicMeter = inputValue * 2.95735e-5;
            cubicCentimeter = inputValue * 29.5735;
            cubicMillimeter = inputValue * 29573.5;
            usGallon = inputValue * 0.0078125;
            usQuart = inputValue * 0.03125;
            usFluidOunce = inputValue;
        } else {
            liter = milliliter = cubicMeter = cubicCentimeter = cubicMillimeter = usGallon = usQuart = usFluidOunce = 0.0;
        }

        return new double[]{liter, milliliter, cubicMeter, cubicCentimeter, cubicMillimeter, usGallon, usQuart, usFluidOunce};
    }



}