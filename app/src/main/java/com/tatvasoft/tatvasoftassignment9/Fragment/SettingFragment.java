package com.tatvasoft.tatvasoftassignment9.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.tatvasoft.tatvasoftassignment9.Activity.MainActivity;
import com.tatvasoft.tatvasoftassignment9.R;
import com.tatvasoft.tatvasoftassignment9.Utils.Constant;
import com.tatvasoft.tatvasoftassignment9.databinding.FragmentSettingBinding;

public class SettingFragment extends Fragment {


    SharedPreferences colorSharedPreference;
    SharedPreferences.Editor colorEditor;
    private boolean bar=true;

    String[] fileListArray ={"Android", "DCIM", "Download","Pictures"};
    ArrayAdapter<String> arrayAdapter;
    public static String selectedFile;

    public SettingFragment() {
        // Required empty public constructor
    }
    private FragmentSettingBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentSettingBinding.inflate(inflater,container,false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(R.string.settings);
        arrayAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_single_choice
                , fileListArray);
        binding.imageFileListView.setAdapter(arrayAdapter);

        binding.selectFolderLinearLayout.setOnClickListener(v -> {
            if(binding.imageFileListLinearLayout.getVisibility() == View.GONE){
                binding.imageFileListLinearLayout.setVisibility(View.VISIBLE);
                binding.imageLayoutArrow.setImageResource(R.drawable.ic_baseline_keyboard_arrow_up_24);
            }else if(binding.imageFileListLinearLayout.getVisibility() == View.VISIBLE){
                binding.imageFileListLinearLayout.setVisibility(View.GONE);
                binding.imageLayoutArrow.setImageResource(R.drawable.ic_baseline_keyboard_arrow_down_24);
            }
        });

        binding.selectColorLinearLayout.setOnClickListener(v -> {
            if(binding.colorSelectorLinearLayout.getVisibility() == View.GONE){
                binding.colorSelectorLinearLayout.setVisibility(View.VISIBLE);
                binding.colorLayoutArrow.setImageResource(R.drawable.ic_baseline_keyboard_arrow_up_24);
            }else if(binding.colorSelectorLinearLayout.getVisibility() == View.VISIBLE){
                binding.colorSelectorLinearLayout.setVisibility(View.GONE);
                binding.colorLayoutArrow.setImageResource(R.drawable.ic_baseline_keyboard_arrow_down_24);
            }
        });

        setColor();
        return binding.getRoot();

    }

    @Override
    public void onResume() {
        super.onResume();

        SharedPreferences sharedPreferences = requireContext().getSharedPreferences(Constant.SELECT_FOLDER_KEY,Context.MODE_PRIVATE);
        String name = sharedPreferences.getString(Constant.IMAGE_FOLDER_VALUE,"");
        for(int i = 0 ; i < binding.imageFileListView.getCount() ; i++) {
            if (binding.imageFileListView.getItemAtPosition(i).equals(name)) {
                binding.imageFileListView.setItemChecked(i,true);
            }
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        for(int i = 0 ; i < binding.imageFileListView.getCount() ; i++) {
            if (binding.imageFileListView.isItemChecked(i)) {
                selectedFile=String.valueOf(binding.imageFileListView.getItemAtPosition(i));
            }
        }
        SharedPreferences sharedPreferences =requireContext().getSharedPreferences(Constant.SELECT_FOLDER_KEY,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor= sharedPreferences.edit();

        editor.putString(Constant.IMAGE_FOLDER_VALUE,selectedFile);
        editor.apply();
    }
    public void setColor(){
        RadioButton rbActionBar = (RadioButton) binding.radioGroup.getChildAt(0);
        rbActionBar.setChecked(true);

        RadioButton rbStatusBar = (RadioButton) binding.radioGroup.getChildAt(1);
        rbStatusBar.setVisibility(View.GONE);
        colorSharedPreference =requireContext().getSharedPreferences(Constant.COLOR_KEY, Context.MODE_PRIVATE);
        colorEditor=colorSharedPreference.edit();
        getColor();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            rbStatusBar.setVisibility(View.VISIBLE);
            initStatusBarColor();
        }
        binding.radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            switch(checkedId){
                case R.id.rbActionBar:
                    // do operations specific to this selection
                    bar=true;
                    getColor();
                    break;
                case R.id.rbStatusBar:
                    // do operations specific to this selection
                    bar=false;
                    getColor();
                    break;
            }
        });

        binding.seekBarRed.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                binding.textR.setText(String.format("= %s", progress));
                putClr();
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        binding.seekBarGreen.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                binding.textG.setText(String.format("= %s", progress));
                putClr();
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        binding.seekBarBlue.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                binding.textB.setText(String.format("= %s", progress));
                putClr();
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
        colorEditor.apply();
    }

    public void actionBarColor(){
        MainActivity.actionBar.setBackgroundDrawable(
                new ColorDrawable(Color.rgb(binding.seekBarRed.getProgress()
                        ,binding.seekBarGreen.getProgress(),binding.seekBarBlue.getProgress())));
    }

    public void statusBarColor(){
        Window window = requireActivity().getWindow();
        // clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        // finally change the color
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //    window.setStatusBarColor(Color.BLUE);

            window.setStatusBarColor(Color.rgb(binding.seekBarRed.getProgress()
                    ,binding.seekBarGreen.getProgress(),binding.seekBarBlue.getProgress()));
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void initStatusBarColor(){

        int statusBarRed=colorSharedPreference.getInt(Constant.STATUS_BAR_COLOR_RED,0);
        int statusBarGreen=colorSharedPreference.getInt(Constant.STATUS_BAR_COLOR_GREEN,0);
        int statusBarBlue=colorSharedPreference.getInt(Constant.STATUS_BAR_COLOR_BLUE,0);

        Window window =requireActivity().getWindow();
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
    private void getColor() {

        if(bar) {
            int actionBarRed=colorSharedPreference.getInt(Constant.ACTION_BAR_COLOR_RED,0);
            int actionBarGreen=colorSharedPreference.getInt(Constant.ACTION_BAR_COLOR_GREEN,0);
            int actionBarBlue=colorSharedPreference.getInt(Constant.ACTION_BAR_COLOR_BLUE,0);

            binding.seekBarRed.setProgress(actionBarRed);
            binding.seekBarGreen.setProgress(actionBarGreen);
            binding.seekBarBlue.setProgress(actionBarBlue);

            binding.textR.setText(String.format("= %s", actionBarRed));
            binding.textG.setText(String.format("= %s", actionBarGreen));
            binding.textB.setText(String.format("= %s", actionBarBlue));
            actionBarColor();
        }else{
            int statusBarRed=colorSharedPreference.getInt(Constant.STATUS_BAR_COLOR_RED,0);
            int statusBarGreen=colorSharedPreference.getInt(Constant.STATUS_BAR_COLOR_GREEN,0);
            int statusBarBlue=colorSharedPreference.getInt(Constant.STATUS_BAR_COLOR_BLUE,0);

            binding.seekBarRed.setProgress(statusBarRed);
            binding.seekBarGreen.setProgress(statusBarGreen);
            binding.seekBarBlue.setProgress(statusBarBlue);

            binding.textR.setText(String.format("= %s", statusBarRed));
            binding.textG.setText(String.format("= %s", statusBarGreen));
            binding.textB.setText(String.format("= %s", statusBarBlue));
            statusBarColor();
        }
    }

    public void putClr(){
        if (bar){
            colorEditor.putInt(Constant.ACTION_BAR_COLOR_RED,binding.seekBarRed.getProgress());
            colorEditor.putInt(Constant.ACTION_BAR_COLOR_GREEN,binding.seekBarGreen.getProgress());
            colorEditor.putInt(Constant.ACTION_BAR_COLOR_BLUE,binding.seekBarBlue.getProgress());
            colorEditor.apply();
            actionBarColor();
        }else{
            colorEditor.putInt(Constant.STATUS_BAR_COLOR_RED,binding.seekBarRed.getProgress());
            colorEditor.putInt(Constant.STATUS_BAR_COLOR_GREEN,binding.seekBarGreen.getProgress());
            colorEditor.putInt(Constant.STATUS_BAR_COLOR_BLUE,binding.seekBarBlue.getProgress());
            colorEditor.apply();
            statusBarColor();
        }
    }
}