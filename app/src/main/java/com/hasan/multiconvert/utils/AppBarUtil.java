package com.hasan.multiconvert.utils;

import android.app.Activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.hasan.multiconvert.R;


public class AppBarUtil {
    public static void setAppBarTitle(AppCompatActivity activity) {

        Toolbar toolbar = activity.findViewById(R.id.appBar);
        activity.setSupportActionBar(toolbar);

        ActionBar actionBar = ((AppCompatActivity) activity).getSupportActionBar();
        if (actionBar != null) {
            String activityTitle = getActivityTitle(activity);
            actionBar.setTitle(activityTitle);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    private static String getActivityTitle(Activity activity) {
        String activityName = activity.getClass().getSimpleName();
        return activityName.replaceAll("Activity$", " Convert");
    }
}
