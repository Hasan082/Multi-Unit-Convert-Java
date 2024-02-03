package com.hasan.multiconvert.ReusableUiLogic;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.hasan.multiconvert.R;

import java.util.Locale;

public class ReusableUiLogin {
    private final Context context;

    public ReusableUiLogin(Context context) {
        this.context = context;
    }

    public void updateUI(double[] unitValArray, String[] resourceUnits, String spinnerText, LinearLayout parentLayout) {
        parentLayout.removeAllViews();

        for (int i = 0; i < resourceUnits.length; i++) {
            View unitLayoutView = LayoutInflater.from(context).inflate(R.layout.length_unit_layout, parentLayout, false);
            TextView unitName = unitLayoutView.findViewById(R.id.unitMetric);
            TextView unitVal = unitLayoutView.findViewById(R.id.unitValue);
            String kmValue = resourceUnits[i];
            String formattedText = kmValue + context.getString(R.string.unitIndicator) + " ";
            unitName.setText(formattedText);

            String formattedValue;
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
    }
}
