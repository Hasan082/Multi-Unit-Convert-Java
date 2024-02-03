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
import android.widget.Toast;

import com.hasan.multiconvert.R;
import com.hasan.multiconvert.ReusableUiLogic.ReusableUiLogin;
import com.hasan.multiconvert.utils.HideKeyBoard;

public class FuelActivity extends AppCompatActivity {
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
        setContentView(R.layout.activity_fuel);
        setAppBarTitle(this);

        parentLayout = findViewById(R.id.lengthParentView);
        spinner = findViewById(R.id.spinnerUnits);
        editTextLengthValue = findViewById(R.id.editTextLengthValue);
        btnConvert = findViewById(R.id.btnConvert);
        btnClear = findViewById(R.id.btnClear);
        res = getResources();
        resourceUnits = res.getStringArray(R.array.fuel_units);
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


        btnClear.setOnClickListener(v -> {
            editTextLengthValue.setEnabled(true);
            editTextLengthValue.setText("");
            spinner.setSelection(0);
            spinner.setEnabled(true);
            parentLayout.removeAllViews();
            btnConvert.setVisibility(View.VISIBLE);
            btnClear.setVisibility(View.GONE);
        });


    }

    private double[] ConvertUnits(double inputValue, String spinnerText) {
        double usGallon, imperialGallon, liter, milliliter;

        if (spinnerText.contains("(gal)")) {
            usGallon = inputValue;
            imperialGallon = inputValue * 0.832674;
            liter = inputValue * 3.78541;
            milliliter = inputValue * 3785.41;
        } else if (spinnerText.contains("(imp gal)")) {
            usGallon = inputValue * 1.20095;
            imperialGallon = inputValue;
            liter = inputValue * 4.54609;
            milliliter = inputValue * 4546.09;
        } else if (spinnerText.contains("(L)")) {
            usGallon = inputValue * 0.264172;
            imperialGallon = inputValue * 0.219969;
            liter = inputValue;
            milliliter = inputValue * 1000;
        } else if (spinnerText.contains("(mL)")) {
            usGallon = inputValue * 0.000264172;
            imperialGallon = inputValue * 0.000219969;
            liter = inputValue * 0.001;
            milliliter = inputValue;
        } else {
            usGallon = imperialGallon = liter = milliliter = 0.0;
        }

        return new double[]{usGallon, imperialGallon, liter, milliliter};
    }




}