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

public class TimesActivity extends AppCompatActivity {
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
        setContentView(R.layout.activity_times);
        setAppBarTitle(this);


        parentLayout = findViewById(R.id.lengthParentView);
        spinner = findViewById(R.id.spinnerUnits);
        editTextLengthValue = findViewById(R.id.editTextLengthValue);
        btnConvert = findViewById(R.id.btnConvert);
        btnClear = findViewById(R.id.btnClear);
        res = getResources();
        resourceUnits = res.getStringArray(R.array.time_units);
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
        double second, millisecond, microsecond, minute, hour, day, week, month, year;

        if (spinnerText.contains("(s)")) {
            second = inputValue;
            millisecond = inputValue * 1000;
            microsecond = inputValue * 1_000_000;
            minute = inputValue / 60;
            hour = inputValue / 3600;
            day = inputValue / 86_400;
            week = inputValue / 604_800;
            month = inputValue / 2_629_746;
            year = inputValue / 31_556_952;
        } else if (spinnerText.contains("(ms)")) {
            second = inputValue / 1000;
            millisecond = inputValue;
            microsecond = inputValue * 1000;
            minute = inputValue / 60_000;
            hour = inputValue / 3_600_000;
            day = inputValue / 86_400_000;
            week = inputValue / 604_800_000;
            month = inputValue / 2_629_746_000.0;
            year = inputValue / 31_556_952_000.0;
        } else if (spinnerText.contains("(μs)")) {
            second = inputValue / 1_000_000;
            millisecond = inputValue / 1000;
            microsecond = inputValue;
            minute = inputValue / 60_000_000;
            hour = inputValue / 3_600_000_000.0;
            day = inputValue / 86_400_000_000.0;
            week = inputValue / 604_800_000_000.0;
            month = inputValue / 2_629_746_000_000.0;
            year = inputValue / 31_556_952_000_000.0;
        } else if (spinnerText.contains("(min)")) {
            second = inputValue * 60;
            millisecond = inputValue * 60_000;
            microsecond = inputValue * 60_000_000;
            minute = inputValue;
            hour = inputValue / 60;
            day = inputValue / 1_440;
            week = inputValue / 10_080;
            month = inputValue / 43_829.4;
            year = inputValue / 525_952.8;
        } else if (spinnerText.contains("(hr)")) {
            second = inputValue * 3600;
            millisecond = inputValue * 3_600_000;
            microsecond = inputValue * 3_600_000_000.0;
            minute = inputValue * 60;
            hour = inputValue;
            day = inputValue / 24;
            week = inputValue / 168;
            month = inputValue / 730.485;
            year = inputValue / 8_765.82;
        } else if (spinnerText.contains("(day)")) {
            second = inputValue * 86_400;
            millisecond = inputValue * 86_400_000;
            microsecond = inputValue * 86_400_000_000.0;
            minute = inputValue * 1_440;
            hour = inputValue * 24;
            day = inputValue;
            week = inputValue / 7;
            month = inputValue / 30.4369;
            year = inputValue / 365.242;
        } else if (spinnerText.contains("(week)")) {
            second = inputValue * 604_800;
            millisecond = inputValue * 604_800_000;
            microsecond = inputValue * 604_800_000_000.0;
            minute = inputValue * 10_080;
            hour = inputValue * 168;
            day = inputValue * 7;
            week = inputValue;
            month = inputValue / 4.34812;
            year = inputValue / 52.1775;
        } else if (spinnerText.contains("(month)")) {
            second = inputValue * 2_629_746;
            millisecond = inputValue * 2_629_746_000.0;
            microsecond = inputValue * 2_629_746_000_000.0;
            minute = inputValue * 43_829.4;
            hour = inputValue * 730.485;
            day = inputValue * 30.4369;
            week = inputValue * 4.34812;
            month = inputValue;
            year = inputValue / 12;
        } else if (spinnerText.contains("(year)")) {
            second = inputValue * 31_556_952;
            millisecond = inputValue * 31_556_952_000.0;
            microsecond = inputValue * 31_556_952_000_000.0;
            minute = inputValue * 525_952.8;
            hour = inputValue * 8_765.82;
            day = inputValue * 365.242;
            week = inputValue * 52.1775;
            month = inputValue * 12;
            year = inputValue;
        } else {
            second = millisecond = microsecond = minute = hour = day = week = month = year = 0.0;
        }

        return new double[]{second, millisecond, microsecond, minute, hour, day, week, month, year};
    }



}