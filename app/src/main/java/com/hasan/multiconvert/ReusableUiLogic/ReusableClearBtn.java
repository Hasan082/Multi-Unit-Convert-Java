package com.hasan.multiconvert.ReusableUiLogic;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

public class ReusableClearBtn {

    public static void ClearBtn(EditText edText, Spinner spin, LinearLayout llOut, Button btnCrt, Button BtnClr){
        edText.setEnabled(true);
        edText.setText("");
        spin.setSelection(0);
        spin.setEnabled(true);
        llOut.removeAllViews();
        btnCrt.setVisibility(View.VISIBLE);
        BtnClr.setVisibility(View.GONE);
    }

}
