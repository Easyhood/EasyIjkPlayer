package com.maniu.maniuijk;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.TextView;

import com.maniu.maniuijk.databinding.ActivityMainBinding;

import java.io.File;
import java.lang.ref.WeakReference;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class
//ffmepg  手写 自己ijkplayer

//   同步  渲染  万能格式
MainActivity extends AppCompatActivity {
    SurfaceView surfaceView;
    MNPlayer mnPlayer;
    public boolean checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
            }, 1);

        }
        return false;
    }

    Surface surface;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        checkPermission();

        surfaceView = (SurfaceView) findViewById(R.id.surface);
        surfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
//                底层 渲染 流程
                surface=holder.getSurface();

            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {

            }
        });
    }
//快播
//    小米 华为   rmvb
//

    //    kuaib
    public void play(View view) {
        String folderurl = new File(Environment.getExternalStorageDirectory(), "input.mp4").
                getAbsolutePath();
//        folderurl = "http://39.134.65.162/PLTV/88888888/224/3221225611/index.m3u8";
//        folderurl = "http://zhibo.hkstv.tv/livestream/mutfysrq/playlist.m3u8";
//        弱引用1  强应用  2
          mnPlayer = new MNPlayer(surfaceView);
        mnPlayer.play(folderurl, surface);
    }
}
