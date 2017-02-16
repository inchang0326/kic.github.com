package com.example.sens_user.assignment03;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.Log;
import java.io.IOException;

/**
 * Created by SENS-user on 2016-11-23.
 */

public class MusicService extends Service {

    private int positionOfMusic;//MainActivity와 MusicViewActivity에서 받는 positionOfMusic 인텐트 데이터를 받기 위한 변수

    private Intent intentToMusicActivityFromMusicService;//Notification 클릭 시 넘길 인텐트 객체 변수

    // Binder 클래스를 상속 받는 클래스를 정의
    // getService() 메소드에서 현재 서비스 객체를 반환
    public class LocalBinder extends Binder {
        // 클라이언트가 호출할 수 있는 공개 메소드가 현재 Service 객체 반환
        MusicService getService() {
            return MusicService.this;
        }
    }

    // 위에서 정의한 Binder 클래스의 객체 생성
    // Binder 클래스는 Interface인 IBinder를 구현한 클래스
    private final IBinder mBinder = new LocalBinder();

    @Override
    public void onCreate() {
        // Service실행 시 단 한 번 실행 되는 메소드로 객체를 생성한다.
        MainActivity.setMediaPlayer();
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public int onStartCommand(Intent intentFromBothActivity, int flags, int startId) {
        // intent: startService() 호출 시 넘기는 intent 객체
        // flags: service start 요청에 대한 부가 정보. 0, START_FLAG_REDELIVERY, START_FLAG_RETRY
        // startId: start 요청을 나타내는 unique integer id

        // MainActivity에서 startService() 호출 시, MusicViewActiviy에서 bind service 시 넘겨 받은 intent 객체에 첨부된 데이터
        positionOfMusic = intentFromBothActivity.getIntExtra("positionOfMusic", 0);

        // MediaPlayer 객체 reset
        MainActivity.getMediaPlayer().reset();

        //***************************************
        // Service를 Foreground로 실행하기 위한 과정

        // 1. Notification 객체 생성
        // 1-1. Intent 객체 설정
        // Notification 클릭 시 현재 진행하는 음악 화면으로 돌아간다
        intentToMusicActivityFromMusicService = new Intent(MusicService.this, MusicViewActivity.class);
        // Notification 클릭 시 재생되는 음악 화면에 적절한 음악 파일 명을 표현하기 위해 현재 재생되는 음악의 위치를 넘긴다
        intentToMusicActivityFromMusicService.putExtra("positionOfMusic", positionOfMusic);
        // Notification 클릭 시 이전에 음악을 진행하던 액티비티를 제거한다
        intentToMusicActivityFromMusicService.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        // 1-2Intent 객체를 이용하여 PendingIntent 객체를 생성 - Activity를 실행하기 위한 PendingIntent
        PendingIntent pIntent = PendingIntent.getActivity(this, 0, intentToMusicActivityFromMusicService, PendingIntent.FLAG_UPDATE_CURRENT);

        // 1-3. Notification 객체 생성
        Notification noti = new Notification.Builder(this)
                .setContentTitle("Music service")
                .setContentText(MainActivity.getFileByPos(positionOfMusic).getName())
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentIntent(pIntent)
                .build();

        // 2. foregound service 설정 - startForeground() 메소드 호출, 위에서 생성한 nofication 객체 넘겨줌
        startForeground(123456, noti);
        //****************************************

        // mediaPlayer setDataSource
        try {
            MainActivity.getMediaPlayer().setDataSource(MainActivity.getFileByPos(positionOfMusic).getAbsolutePath());
            MainActivity.getMediaPlayer().prepare();
        } catch (IOException e) { e.printStackTrace(); }

        // mediaPlayer start
        MainActivity.getMediaPlayer().start();

        // mediaPlayer's automatic start of next music
        if(positionOfMusic != MainActivity.getFilesInMusicDir().length-1) {
            MainActivity.getMediaPlayer().setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                public void onCompletion(MediaPlayer arg0) {
                    fastForward();
                }
            });
        }

        return START_STICKY;
        //return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        // notification dismiss
        NotificationManager nm = (NotificationManager)getSystemService(this.NOTIFICATION_SERVICE);
        nm.cancel(123456);

        // meidaPlayer release
        MainActivity.destroyMediaPlayer();
        Log.i("MobileProgramming", "onDestory()");
    }

    @Nullable
    @Override
    // 위에서 생성한 LocalBinder 객체를 반환
    public IBinder onBind(Intent intent) { return mBinder; }

    //뒤로 가기
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void fastbackward(){

        MainActivity.getMediaPlayer().reset();

        // fastbackward 버튼 클릭 시 파일의 위치를 -1 만큼 움직이고 초기화
        try{
            MainActivity.getMediaPlayer().setDataSource(MainActivity.getFileByPos(positionOfMusic-1).getAbsolutePath());
            MainActivity.getMediaPlayer().prepare();
            positionOfMusic = positionOfMusic - 1;
        } catch (Exception e){// bound exception을 가정한다면
            try {
                // 현재의 음악 트랙에 머무르기
                MainActivity.getMediaPlayer().setDataSource(MainActivity.getFileByPos(positionOfMusic).getAbsolutePath());
                MainActivity.getMediaPlayer().prepare();
            } catch (IOException e2) { e2.printStackTrace(); }
            e.printStackTrace();
        }
        resetNotification(positionOfMusic);//현재 올라가 있는 Nofication의 속성을 재설정하는 메소드
        MusicViewActivity.setTextView(MainActivity.getFileByPos(positionOfMusic).getName());//현재 재생중인 화면의 음악 파일 명을 이전 곡으로 재설정
        MainActivity.getMediaPlayer().start();
    }


    //앞으로 가기
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void fastForward(){

        MainActivity.getMediaPlayer().reset();

        // fastbackward 버튼 클릭 시 파일의 위치를 +1 만큼 움직이고 초기화
        try{
            MainActivity.getMediaPlayer().setDataSource(MainActivity.getFileByPos(positionOfMusic+1).getAbsolutePath());
            MainActivity.getMediaPlayer().prepare();
            positionOfMusic = positionOfMusic + 1;
        } catch (Exception e){// bound exception을 가정한다면
            try {
                // 현재의 음악 트랙에 머무르기
                MainActivity.getMediaPlayer().setDataSource(MainActivity.getFileByPos(positionOfMusic).getAbsolutePath());
                MainActivity.getMediaPlayer().prepare();
            } catch (IOException e1) { e1.printStackTrace(); }
            e.printStackTrace();
        }
        resetNotification(positionOfMusic);//현재 올라가 있는 Nofication의 속성을 재설정하는 메소드
        MusicViewActivity.setTextView(MainActivity.getFileByPos(positionOfMusic).getName());//현재 재생중인 화면의 음악 파일 명을 다음 곡으로 재설정
        MainActivity.getMediaPlayer().start();// mediaPlayer start
    }

    //일시멈춤
    public void pause() {
        MainActivity.getMediaPlayer().pause();
    }

    //다시재생
    public void restart(){
        MainActivity.getMediaPlayer().start();
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void resetNotification(int MusicPos){
        intentToMusicActivityFromMusicService.putExtra("positionOfMusic", MusicPos);
        // Intent 객체를 이용하여 PendingIntent 객체를 생성 - Activity를 실행하기 위한 PendingIntent
        PendingIntent pIntent = PendingIntent.getActivity(this, 0, intentToMusicActivityFromMusicService, PendingIntent.FLAG_UPDATE_CURRENT);

        Notification noti = new Notification.Builder(this)
                .setContentTitle("Music service")
                .setContentText(MainActivity.getFileByPos(MusicPos).getName())
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentIntent(pIntent)
                .build();

        // 2. foregound service 설정 - startForeground() 메소드 호출, 위에서 생성한 nofication 객체 넘겨줌
        startForeground(123456, noti);
    }
}
