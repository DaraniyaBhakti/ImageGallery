package com.tatvasoft.tatvasoftassignment9.Activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.tatvasoft.tatvasoftassignment9.Fragment.ImageFragment;
import com.tatvasoft.tatvasoftassignment9.R;
import com.tatvasoft.tatvasoftassignment9.Utils.Constant;
import com.tatvasoft.tatvasoftassignment9.databinding.ActivityMainBinding;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    public static ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setTitle(getString(R.string.gallery));
        actionBar = getSupportActionBar();


        getSupportFragmentManager().beginTransaction().add(R.id.container,new ImageFragment())
                .commit();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onResume() {
        super.onResume();

        SharedPreferences sharedPreferences = getSharedPreferences(Constant.COLOR_KEY, Context.MODE_PRIVATE);
        int actionBarRed=sharedPreferences.getInt(Constant.ACTION_BAR_COLOR_RED,0);
        int actionBarGreen=sharedPreferences.getInt(Constant.ACTION_BAR_COLOR_GREEN,0);
        int actionBarBlue=sharedPreferences.getInt(Constant.ACTION_BAR_COLOR_BLUE,0);

        Objects.requireNonNull(getSupportActionBar()).setBackgroundDrawable(
                new ColorDrawable(Color.rgb(actionBarRed,actionBarGreen,actionBarBlue)));

        int statusBarRed=sharedPreferences.getInt(Constant.STATUS_BAR_COLOR_RED,0);
        int statusBarGreen=sharedPreferences.getInt(Constant.STATUS_BAR_COLOR_GREEN,0);
        int statusBarBlue=sharedPreferences.getInt(Constant.STATUS_BAR_COLOR_BLUE,0);

        Window window = getWindow();
        // clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        // finally change the color
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //    window.setStatusBarColor(Color.BLUE);
            window.setStatusBarColor(Color.rgb(statusBarRed,statusBarGreen,statusBarBlue));
        }
    }

}