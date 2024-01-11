package com.example.bagrutproject;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;

public class GameActivity extends AppCompatActivity {

    MySurfaceView mySurfaceView;
    Thread thread;
    boolean userAskBack = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mySurfaceView = new MySurfaceView(this);
        thread = new Thread(mySurfaceView);
        thread.start();

        ViewGroup.LayoutParams layoutParams = new ActionBar.LayoutParams
                (ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        addContentView(mySurfaceView,layoutParams);
    }
    @Override
    public void finish() {
        super.finish();
        userAskBack = true;
        mySurfaceView.threadRunning = false;
        while(true){
            try {
                thread.join();

            }
            catch (Exception e){
                e.printStackTrace();
            }
            break;
        }


    }

    @Override
    protected void onResume() {
        super.onResume();
        if(mySurfaceView !=null){
            mySurfaceView.resume();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        Log.d("data", "pause");
        if(userAskBack){
            Log.d("data", "user ask back");
        }
        else if(mySurfaceView != null){
            mySurfaceView.pause();
        }
    }
}