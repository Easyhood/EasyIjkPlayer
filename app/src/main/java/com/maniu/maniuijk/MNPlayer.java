package com.maniu.maniuijk;

import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.view.Surface;
import android.view.SurfaceView;
import android.widget.RelativeLayout;

public class MNPlayer {
    static {
        System.loadLibrary("maniuijk");
    }


    SurfaceView surfaceView;
    private AudioTrack audioTrack;
    public MNPlayer(SurfaceView surfaceView) {
        this.surfaceView = surfaceView;
    }
//没有问题  鲜花
    public native void play(String url, Surface surface);
//直播  rtm 协议 直播 万能
//    获取到视频的宽高
    public void onSizeChange(int width, int heigth) {
//        final int[] a = {0};
//        new Thread(){
//            @Override
//            public void run() {
//                a[0] = 20;
//            }
//        }.start();
        float ratio = width / (float) heigth;
        int screenWidth = surfaceView.getContext().getResources().getDisplayMetrics().widthPixels;
        int videoWidth = 0;
        int videoHeigth = 0;
        videoWidth = screenWidth;
        videoHeigth = (int) (screenWidth / ratio);
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(videoWidth, videoHeigth);
        surfaceView.setLayoutParams(lp); //调整surfaceview控件的大小
    }
//   采样频率 底层传给我的  底层 回调  初始化
    public void createTrack(int sampleRateInHz,int nb_channals) {

        int channaleConfig;//通道数
        if (nb_channals == 1) {
            channaleConfig = AudioFormat.CHANNEL_OUT_MONO;
        } else if (nb_channals == 2) {
            channaleConfig = AudioFormat.CHANNEL_OUT_STEREO;
        }else {
            channaleConfig = AudioFormat.CHANNEL_OUT_MONO;
        }
        int buffersize = AudioTrack.getMinBufferSize(sampleRateInHz,
                channaleConfig, AudioFormat.ENCODING_PCM_16BIT);
        audioTrack = new AudioTrack(AudioManager.STREAM_MUSIC,sampleRateInHz, channaleConfig, AudioFormat.ENCODING_PCM_16BIT, buffersize, AudioTrack.MODE_STREAM);
        audioTrack.play();
    }
//    byte[] buffer
//     uint8_t *outbuffer   是 1 不是2
//    响应数据  解码音频 数据
    public void playTrack(byte[] buffer, int lenth) {
        if (audioTrack != null && audioTrack.getPlayState() == AudioTrack.PLAYSTATE_PLAYING) {
//播放声音   audioTrack.write(0,0,1） 播放声音
            audioTrack.write(buffer, 0, lenth);
        }


    }
}
