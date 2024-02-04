package com.hasan.multiconvert.UnitActivity;

import static com.hasan.multiconvert.utils.AppBarUtil.setAppBarTitle;

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

public class EnergyActivity extends AppCompatActivity {

    LinearLayout parentLayout;
    Spinner spinner;
    EditText editTextLengthValue;
    Button btnConvert, btnClear;
    String[] resourceUnits;
    double inputValue;
    ReusableUiLogin reusableUiLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_energy);
        setAppBarTitle(this);

        parentLayout = findViewById(R.id.lengthParentView);
        spinner = findViewById(R.id.spinnerUnits);
        editTextLengthValue = findViewById(R.id.editTextLengthValue);
        btnConvert = findViewById(R.id.btnConvert);
        btnClear = findViewById(R.id.btnClear);
        resourceUnits = getResources().getStringArray(R.array.energy_units);
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
        double joule, kilojoule, calorie, kilocalorie, electronVolt, britishThermalUnit, footPound;

        if (spinnerText.contains("(J)")) {
            joule = inputValue;
            kilojoule = inputValue / 1000;
            calorie = inputValue / 4.184;
            kilocalorie = inputValue / 4184;
            electronVolt = inputValue * 6.242e+18;
            britishThermalUnit = inputValue * 0.000947817;
            footPound = inputValue * 0.737562;
        } else if (spinnerText.contains("(kJ)")) {
            joule = inputValue * 1000;
            kilojoule = inputValue;
            calorie = inputValue / 0.004184;
            kilocalorie = inputValue / 4.184;
            electronVolt = inputValue * 6.242e+21;
            britishThermalUnit = inputValue * 0.947817;
            footPound = inputValue * 737.562;
        } else if (spinnerText.contains("(cal)")) {
            joule = inputValue * 4.184;
            kilojoule = inputValue * 0.004184;
            calorie = inputValue;
            kilocalorie = inputValue / 1000;
            electronVolt = inputValue * 2.390e+19;
            britishThermalUnit = inputValue * 0.000252164;
            footPound = inputValue * 0.324048;
        } else if (spinnerText.contains("(kcal)")) {
            joule = inputValue * 4184;
            kilojoule = inputValue * 4.184;
            calorie = inputValue * 1000;
            kilocalorie = inputValue;
            electronVolt = inputValue * 2.390e+22;
            britishThermalUnit = inputValue * 252.164;
            footPound = inputValue * 324.048;
        } else if (spinnerText.contains("(eV)")) {
            joule = inputValue / 6.242e+18;
            kilojoule = inputValue / 6.242e+21;
            calorie = inputValue / 2.390e+19;
            kilocalorie = inputValue / 2.390e+22;
            electronVolt = inputValue;
            britishThermalUnit = inputValue * 9.4804e-19;
            footPound = inputValue * 7.3756e-19;
        } else if (spinnerText.contains("(BTU)")) {
            joule = inputValue / 0.000947817;
            kilojoule = inputValue / 0.947817;
            calorie = inputValue / 0.000252164;
            kilocalorie = inputValue / 252.164;
            electronVolt = inputValue / 9.4804e-19;
            britishThermalUnit = inputValue;
            footPound = inputValue * 778.169;
        } else if (spinnerText.contains("(ft-lb)")) {
            joule = inputValue / 0.737562;
            kilojoule = inputValue / 737.562;
            calorie = inputValue / 0.324048;
            kilocalorie = inputValue / 324.048;
            electronVolt = inputValue / 7.3756e-19;
            britishThermalUnit = inputValue / 778.169;
            footPound = inputValue;
        } else {
            joule = kilojoule = calorie = kilocalorie = electronVolt = britishThermalUnit = footPound = 0.0;
        }

        return new double[]{joule, kilojoule, calorie, kilocalorie, electronVolt, britishThermalUnit, footPound};
    }




}