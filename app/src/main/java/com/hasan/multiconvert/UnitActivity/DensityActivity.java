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

public class DensityActivity extends AppCompatActivity {
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
        setContentView(R.layout.activity_density);
        setAppBarTitle(this);

        parentLayout = findViewById(R.id.lengthParentView);
        spinner = findViewById(R.id.spinnerUnits);
        editTextLengthValue = findViewById(R.id.editTextLengthValue);
        btnConvert = findViewById(R.id.btnConvert);
        btnClear = findViewById(R.id.btnClear);
        res = getResources();
        resourceUnits = res.getStringArray(R.array.density_units);
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