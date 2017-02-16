package com.example.sens_user.assignment03;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

/**
 * Created by SENS-user on 2016-11-22.
 */

public class MusicViewActivity extends AppCompatActivity{

    private static TextView textView;
    public static void setTextView(String st){
        textView.setText(st);
    }
    private ImageButton imageButton;

    private int positionOfMusic;//MainActivity와 MusicService에서 받는 positionOfMusic 인텐트 데이터를 받기 위한 변수

    private MusicService mService;
    private boolean mBound = false;

    // ServiceConnection 인터페이스를 구현한 ServiceConnection 객체 생성
    // onServiceConnected() 콜백 메소드와 onServiceDisconnected() 콜백 메소드를 구현해야 함
    private ServiceConnection mConnection = new ServiceConnection() {

        // Service에 연결(bound)되었을 때 호출되는 callback 메소드
        // Service의 onBind() 메소드에서 반환한 IBinder 객체를 받음 (두번째 인자)
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {

            // 두번째 인자로 넘어온 IBinder 객체를 MusicService 클래스에 정의된 LocalBinder 클래스 객체로 캐스팅
            MusicService.LocalBinder binder = (MusicService.LocalBinder)service;

            // MusicService 객체를 참조하기 위해 LocalBinder 객체의 getService() 메소드 호출
            mService = binder.getService();

            mBound = true;
        }

        // Service 연결 해제되었을 때 호출되는 callback 메소드
        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.d("MusicViewActivity", "onServiceDisconnected()");

            mBound = false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_musicview);
        getSupportActionBar().setTitle("MP3 Player");

        textView = (TextView)findViewById(R.id.musicInfo);
        imageButton = (ImageButton)findViewById(R.id.pause);

        //메인 화면에서 인텐트로 전달 받은 선택한 음악의 위치로 filepath 지정
        Intent intentFromMainActivity = getIntent();
        // getIntExtra always returns default value
        positionOfMusic = intentFromMainActivity.getIntExtra("positionOfMusic", 0);

        Intent intentFromMusicService = getIntent();
        // getIntExtra always returns default value
        positionOfMusic = intentFromMusicService.getIntExtra("positionOfMusic", 0);

        textView.setText(MainActivity.getFileByPos(positionOfMusic).getName());

        // 음악 재생이 시작되면 java 코드로 버튼의 이미지를 일시정지 이미지로 바꿔준다
        imageButton.setBackgroundResource(R.drawable.actions_pause_icon);
    }

    @Override
    protected void onStart() {
        super.onStart();

        // 연결할 Service를 위한 Intent 객체 생성
        Intent intentToMusicServiceFromMusicViewActivity = new Intent(this, MusicService.class);
        intentToMusicServiceFromMusicViewActivity.putExtra("positionOfMusic", positionOfMusic);

        // Service에 연결하기 위해 bindService 호출, 생성한 intent 객체와 구현한 ServiceConnection의 객체를 전달
        // boolean bindService(Intent service, ServiceConnection conn, int flags)
        bindService(intentToMusicServiceFromMusicViewActivity, mConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(mBound) {
            unbindService(mConnection);
            mBound = false;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        //엑션바에 종료 아이템 추가
        getMenuInflater().inflate(R.menu.action_bar_terminate, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()) {
            // 액션바에 있는 종료 아이템을 클릭할 경우
            case R.id.terminate:
                // MusicService stop
                Intent intentForMusicServiceStop = new Intent(MusicViewActivity.this, MusicService.class);
                stopService(intentForMusicServiceStop);
                // MainActivity start
                Intent intentForMainActivityStart = new Intent(MusicViewActivity.this, MainActivity.class);
                startActivity(intentForMainActivityStart);
                // MusicViewActivity finish
                finish();
                return true;
            default: return super.onOptionsItemSelected(item);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.previous:{
                mService.fastbackward();
                imageButton.setBackgroundResource(R.drawable.actions_pause_icon);
                break;
            }
            case R.id.pause: {
                if (MainActivity.getMediaPlayer().isPlaying()) {
                    mService.pause();
                    imageButton.setBackgroundResource(R.drawable.actions_start_icon);
                } else {
                    mService.restart();
                    imageButton.setBackgroundResource(R.drawable.actions_pause_icon);
                }
                break;
            }
            case R.id.next:
                mService.fastForward();
                imageButton.setBackgroundResource(R.drawable.actions_pause_icon);
                break;
            default: break;
        }
    }
}
