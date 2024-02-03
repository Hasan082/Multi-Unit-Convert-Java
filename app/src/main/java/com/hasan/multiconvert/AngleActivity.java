package com.hasan.multiconvert;

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

import com.hasan.multiconvert.ReusableUiLogic.ReusableUiLogin;
import com.hasan.multiconvert.utils.HideKeyBoard;

public class AngleActivity extends AppCompatActivity {
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
        setContentView(R.layout.activity_angle);
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
        double degree, radian, grad, minuteOfArc, secondOfArc;

        if (spinnerText.contains("(°)")) {
            degree = inputValue;
            radian = Math.toRadians(inputValue);
            grad = inputValue * 10 / 9;
            minuteOfArc = inputValue * 60;
            secondOfArc = inputValue * 3600;
        } else if (spinnerText.contains("(rad)")) {
            degree = Math.toDegrees(inputValue);
            radian = inputValue;
            grad = Math.toDegrees(inputValue) * 10 / 9;
            minuteOfArc = Math.toDegrees(inputValue) * 60;
            secondOfArc = Math.toDegrees(inputValue) * 3600;
        } else if (spinnerText.contains("(grad)")) {
            degree = inputValue * 9 / 10;
            radian = Math.toRadians(inputValue * 9 / 10);
            grad = inputValue;
            minuteOfArc = inputValue * 54 / 5;
            secondOfArc = inputValue * 3240 / 5;
        } else if (spinnerText.contains("(,)")) {
            degree = inputValue / 60;
            radian = Math.toRadians(inputValue / 60);
            grad = inputValue / 54;
            minuteOfArc = inputValue;
            secondOfArc = inputValue * 60;
        } else if (spinnerText.contains("(,,)")) {
            degree = inputValue / 3600;
            radian = Math.toRadians(inputValue / 3600);
            grad = inputValue / 3240;
            minuteOfArc = inputValue / 60;
            secondOfArc = inputValue;
        } else {
            degree = radian = grad = minuteOfArc = secondOfArc = 0.0;
        }

        return new double[]{degree, radian, grad, minuteOfArc, secondOfArc};
    }


}