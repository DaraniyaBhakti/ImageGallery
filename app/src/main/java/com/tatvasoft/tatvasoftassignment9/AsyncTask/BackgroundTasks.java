package com.tatvasoft.tatvasoftassignment9.AsyncTask;

import android.os.Handler;
import android.os.Looper;

import java.io.File;
import java.util.ArrayList;

public class BackgroundTasks {
    protected Handler localHandler;
    protected Thread localThread;

    public void execute(File file){
        this.localHandler = new Handler(Looper.getMainLooper());
        this.onPreExecute();

        this.localThread = new Thread(new Runnable() {
            @Override
            public void run() {
                ArrayList<File> arrayList = BackgroundTasks.this.doInBackground(file);

                BackgroundTasks.this.localHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        BackgroundTasks.this.onPostExecute(arrayList);
                    }
                });

            }


        });
        this.localThread.start();
    }

    protected ArrayList<File> doInBackground(File file) {
        return null;

    }

    public void cancel(){
        if(this.localThread.isAlive()){
            this.localThread.interrupt();
        }
    }


    protected void onPostExecute(ArrayList arrayList) {
    }

    protected void onPreExecute() {
    }
}
