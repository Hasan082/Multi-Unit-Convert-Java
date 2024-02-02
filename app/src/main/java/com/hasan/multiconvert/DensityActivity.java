package com.hasan.multiconvert;

import static com.hasan.multiconvert.utils.AppBarUtil.setAppBarTitle;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class DensityActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_density);
        setAppBarTitle(this);
    }
}