package com.hasan.multiconvert;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private Map<Integer, Class<?>> buttonMap = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupButton(R.id.length, LengthActivity.class);
        setupButton(R.id.area, AreaActivity.class);
        setupButton(R.id.mass, MassActivity.class);
        setupButton(R.id.volume, VolumeActivity.class);
        setupButton(R.id.times, TimesActivity.class);
        setupButton(R.id.speed, SpeedActivity.class);
        setupButton(R.id.density, DensityActivity.class);
        setupButton(R.id.temp, TemperatureActivity.class);
        setupButton(R.id.weight, WeightActivity.class);
        setupButton(R.id.energy, EnergyActivity.class);

    }

    private void setupButton(int buttonId, Class<?> activityClass) {
        LinearLayout button = findViewById(buttonId);
        if (button != null) {
            button.setOnClickListener(v -> {
                Intent intent = new Intent(MainActivity.this, activityClass);
                startActivity(intent);
            });
            buttonMap.put(buttonId, activityClass);
        }
    }


}