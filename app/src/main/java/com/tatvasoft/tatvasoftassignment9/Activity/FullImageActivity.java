package com.tatvasoft.tatvasoftassignment9.Activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.tatvasoft.tatvasoftassignment9.R;
import com.tatvasoft.tatvasoftassignment9.Utils.Constant;
import com.tatvasoft.tatvasoftassignment9.databinding.ActivityFullImageBinding;

import java.util.ArrayList;

import static com.tatvasoft.tatvasoftassignment9.Activity.MainActivity.actionBar;

public class FullImageActivity extends AppCompatActivity {

    int position;
    String imageLink;
    ArrayList myImage;

    private ActivityFullImageBinding binding;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.gallery);
        setContentView(R.layout.activity_full_image);
        binding = ActivityFullImageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        colorSet();
        Bundle bundle= getIntent().getExtras();
        if (bundle !=null){
            myImage = bundle.getParcelableArrayList(Constant.FULL_IMAGE_PATH_LIST_KEY);
            position=   bundle.getInt(Constant.FULL_IMAGE_POSITION_KEY,0);
            imageLink=  bundle.getString(Constant.FULL_IMAGE_INTENT_KEY);

        }

        binding.fullImageViewId.setImageURI(Uri.parse(imageLink));

        binding.previousFab.setOnClickListener(v -> {
            position=((position -1 )<0)? (myImage.size()-1) :(position-1);
            binding.fullImageViewId.setImageURI(Uri.parse(myImage.get(position).toString()));
        });

        binding.nextFab.setOnClickListener(v -> {
            position = ((position + 1) % myImage.size());
            binding.fullImageViewId.setImageURI(Uri.parse(myImage.get(position).toString()));
        });


    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void colorSet(){

        SharedPreferences sharedPreferences = getSharedPreferences(Constant.COLOR_KEY, Context.MODE_PRIVATE);
        int actionBarRed=sharedPreferences.getInt(Constant.ACTION_BAR_COLOR_RED,0);
        int actionBarGreen=sharedPreferences.getInt(Constant.ACTION_BAR_COLOR_GREEN,0);
        int actionBarBlue=sharedPreferences.getInt(Constant.ACTION_BAR_COLOR_BLUE,0);

        getSupportActionBar().setBackgroundDrawable(
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