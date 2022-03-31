package com.tatvasoft.tatvasoftassignment9.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.tatvasoft.tatvasoftassignment9.AsyncTask.ImageBackgroundTasks;
import com.tatvasoft.tatvasoftassignment9.R;
import com.tatvasoft.tatvasoftassignment9.Utils.Constant;
import com.tatvasoft.tatvasoftassignment9.databinding.FragmentImageBinding;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ImageFragment extends Fragment{

    List<String> imagePathList = new ArrayList<>() ;
    public static String folderName ;
    SharedPreferences imageFolderSharedPreferences;

    public static FragmentImageBinding binding;
    public ImageFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentImageBinding.inflate(inflater,container,false);
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).setTitle(R.string.gallery);
        setHasOptionsMenu(true);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        imageFolderSharedPreferences = requireContext().getSharedPreferences(Constant.SELECT_FOLDER_KEY, Context.MODE_PRIVATE);
        folderName = imageFolderSharedPreferences.getString(Constant.IMAGE_FOLDER_VALUE,"");
        imagePathList =new ArrayList<>();
        display();
    }


    public  void display() {
        imagePathList.clear();
        File file = new File(Environment.getExternalStorageDirectory(),"/" + folderName + "/");
        if(!folderName.isEmpty()) {
            ImageBackgroundTasks imageBackgroundTasks = new ImageBackgroundTasks(getContext());
            imageBackgroundTasks.execute(file);
        }
        else {
            binding.noImageTextView.setText(R.string.no_folder_selected_text_view);
            if(imagePathList.size() == 0)
            {
                binding.noImageTextView.setVisibility(View.VISIBLE);
            }else {
                binding.noImageTextView.setVisibility(View.GONE);

            }
        }
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.options_menu,menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        requireActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.container,new SettingFragment()).addToBackStack(null).commit();
        return super.onOptionsItemSelected(item);
    }



}