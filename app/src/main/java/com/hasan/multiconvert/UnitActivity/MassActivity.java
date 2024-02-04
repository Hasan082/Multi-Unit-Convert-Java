package com.hasan.multiconvert.UnitActivity;

import static com.hasan.multiconvert.utils.AppBarUtil.setAppBarTitle;

import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.hasan.multiconvert.R;
import com.hasan.multiconvert.ReusableUiLogic.ReusableClearBtn;
import com.hasan.multiconvert.ReusableUiLogic.ReusableUiLogin;
import com.hasan.multiconvert.utils.HideKeyBoard;

public class MassActivity extends AppCompatActivity {
    LinearLayout parentLayout;
    Spinner spinner;
    EditText editTextLengthValue;
    Button btnConvert, btnClear;
    Resources res;
    String[] resourceUnits;
    double inputValue;
    ReusableUiLogin reusableUiLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mass);
        setAppBarTitle(this);

        parentLayout = findViewById(R.id.lengthParentView);
        spinner = findViewById(R.id.spinnerUnits);
        editTextLengthValue = findViewById(R.id.editTextLengthValue);
        btnConvert = findViewById(R.id.btnConvert);
        btnClear = findViewById(R.id.btnClear);
        res = getResources();
        resourceUnits = res.getStringArray(R.array.mass_units);
        reusableUiLogin = new ReusableUiLogin(this);

        btnConvert.setOnClickListener(v -> {

            String edyTxtStr = editTextLengthValue.getText().toString().trim();

            if (!edyTxtStr.isEmpty()) {
                inputValue = Double.parseDouble(edyTxtStr);
                String spinnerText = spinner.getSelectedItem().toString();
                double[] unitValArray = ConvertUnits(inputValue, spinnerText);
                reusableUiLogin.updateUI(unitValArray, resourceUnits, spinnerText, parentLayout);
                editTextLengthValue.setEnabled(false);
                spinner.setEnabled(false);
                btnConvert.setVisibility(View.GONE);
                btnClear.setVisibility(View.VISIBLE);
                HideKeyBoard.hideKeyboard(this, v);
            } else {
                Toast.makeText(this, "Please Input Units", Toast.LENGTH_LONG).show();
            }

        });

        btnClear.setOnClickListener(v -> ReusableClearBtn.ClearBtn(editTextLengthValue, spinner,parentLayout,btnConvert, btnClear));

    }

    private double[] ConvertUnits(double inputValue, String spinnerText) {
        double kilogram, gram, milligram, metricTon, pound, ounce;

        if (spinnerText.contains("(kg)")) {
            kilogram = inputValue;
            gram = inputValue * 1000;
            milligram = inputValue * 1_000_000;
            metricTon = inputValue / 1000;
            pound = inputValue * 2.20462;
            ounce = inputValue * 35.274;
        } else if (spinnerText.contains("(g)")) {
            kilogram = inputValue / 1000;
            gram = inputValue;
            milligram = inputValue * 1000;
            metricTon = inputValue / 1_000_000;
            pound = inputValue * 0.00220462;
            ounce = inputValue * 0.035274;
        } else if (spinnerText.contains("(mg)")) {
            kilogram = inputValue / 1_000_000;
            gram = inputValue / 1000;
            milligram = inputValue;
            metricTon = inputValue / 1_000_000_000.0;
            pound = inputValue * 2.20462e-6;
            ounce = inputValue * 3.5274e-5;
        } else if (spinnerText.contains("(ton)")) {
            kilogram = inputValue * 1000;
            gram = inputValue * 1_000_000;
            milligram = inputValue * 1_000_000_000.0;
            metricTon = inputValue;
            pound = inputValue * 2204.62;
            ounce = inputValue * 35_273.96;
        } else if (spinnerText.contains("(lb)")) {
            kilogram = inputValue * 0.453592;
            gram = inputValue * 453.592;
            milligram = inputValue * 453_592;
            metricTon = inputValue * 0.000453592;
            pound = inputValue;
            ounce = inputValue * 16;
        } else if (spinnerText.contains("(oz)")) {
            kilogram = inputValue * 0.0283495;
            gram = inputValue * 28.3495;
            milligram = inputValue * 28_349.5;
            metricTon = inputValue * 2.83495e-5;
            pound = inputValue * 0.0625;
            ounce = inputValue;
        } else {
            kilogram = gram = milligram = metricTon = pound = ounce = 0.0;
        }

        return new double[]{kilogram, gram, milligram, metricTon, pound, ounce};
    }



}