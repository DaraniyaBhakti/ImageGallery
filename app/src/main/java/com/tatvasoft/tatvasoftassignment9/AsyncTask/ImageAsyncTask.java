package com.tatvasoft.tatvasoftassignment9.AsyncTask;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.View;

import androidx.recyclerview.widget.GridLayoutManager;

import com.tatvasoft.tatvasoftassignment9.Activity.FullImageActivity;
import com.tatvasoft.tatvasoftassignment9.Adapter.ImageAdapter;
import com.tatvasoft.tatvasoftassignment9.Fragment.ImageFragment;
import com.tatvasoft.tatvasoftassignment9.R;
import com.tatvasoft.tatvasoftassignment9.Utils.Constant;

import java.io.File;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import static com.tatvasoft.tatvasoftassignment9.Fragment.ImageFragment.binding;

public class ImageAsyncTask extends AsyncTask<File,Void,ArrayList> implements ImageAdapter.onItemClickListener {


    private final WeakReference<Context> contextRef;
    ProgressDialog progressDialog;

    private ArrayList imageFile;
    ImageAdapter imageAdapter;
    List<String> imagePathList = new ArrayList<>() ;
    private final ArrayList<File> allImageFile = new ArrayList<>();
    static String[] imgExtension = {".jpg", ".jpeg", ".png"};

    public ImageAsyncTask(Context context) {
        contextRef = new WeakReference<>(context);
    }

    @Override
    protected void onPreExecute() {
        progressDialog = new ProgressDialog(contextRef.get());
        progressDialog.setTitle(contextRef.get().getString(R.string.loading_progressDialog));
        progressDialog.setMessage(contextRef.get().getString(R.string.pleaseWait_progressDialog));
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();
    }
    @Override
    protected ArrayList<File> doInBackground(File... files) {
        File[] fileList = files[0].listFiles();
        if(fileList != null && fileList.length > 0){
            for (File value : fileList) {
                if (value.isDirectory()) {
                    doInBackground(value);
                } else {
                    String name = value.getName().toLowerCase();
                    for (String extensions : imgExtension) {
                        if (name.endsWith(extensions)) {
                            allImageFile.add(value);
                            break;
                        }
                    }
                }
            }
        }
        imageFile = allImageFile;
        return allImageFile;
    }

    @Override
    protected void onPostExecute(ArrayList arrayList) {
        super.onPostExecute(arrayList);

        for (int j = 0; j < arrayList.size(); j++) {
            imagePathList.add(String.valueOf(arrayList.get(j)));
        }
        binding.noImageTextView.setText(String.format(contextRef.get().getString(R.string.noImageFolderText), ImageFragment.folderName));
        if (imagePathList.size() == 0) {
            binding.noImageTextView.setVisibility(View.VISIBLE);
        } else {
            binding.noImageTextView.setVisibility(View.GONE);
            imageAdapter = new ImageAdapter(imagePathList, this);
            binding.recyclerViewId.setAdapter(imageAdapter);
            binding.recyclerViewId.setLayoutManager(new GridLayoutManager(contextRef.get(), 3));
        }
        progressDialog.dismiss();

    }

    @Override
    public void onClick(int position) {
        Intent intent=new Intent(contextRef.get(), FullImageActivity.class);
        intent.putExtra(Constant.FULL_IMAGE_INTENT_KEY,String.valueOf(imageFile.get(position)));
        intent.putExtra(Constant.FULL_IMAGE_POSITION_KEY,position);
        intent.putExtra(Constant.FULL_IMAGE_PATH_LIST_KEY,imageFile);
        contextRef.get().startActivity(intent);
    }
}
