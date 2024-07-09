package com.example.bagrutproject;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

public class GameActivity extends AppCompatActivity {

    MySurfaceView mySurfaceView;
    Thread thread;
    boolean userAskBack = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mySurfaceView = new MySurfaceView(this,this);
        thread = new Thread(mySurfaceView);
        thread.start();

        ViewGroup.LayoutParams layoutParams = new ActionBar.LayoutParams
                (ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        addContentView(mySurfaceView,layoutParams);
    }



    public void openDialog(){
        final Dialog dialog = new Dialog(this);
        Log.d("balls","second test");
        dialog.setContentView(R.layout.game_over_dialog);

        dialog.setCancelable(false);
        Button btnQuit = dialog.findViewById(R.id.btn_quit);


        btnQuit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view == btnQuit){
                    dialog.dismiss();
                    mySurfaceView.destroy();
                }
            }
        });
        dialog.show();
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
        mySurfaceView.stopPlaying();

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