package kr.ac.koreatech.swkang.ch7actionbar;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class MemoViewActivity extends AppCompatActivity{

    //AlertDialog 메세지 변수
    private static final int DIALOG_YES_NO_MESSAGE = 1;

    //TextView 객체 선언
    private TextView textView;

    //TextFileManager 객체 선언
    private TextFileManager mFileMgr;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memoview);

        //글 목록 화면에서 사용자가 선택한 파일 제목에 관련한 인탠트 데이터를 받음
        Intent intent = getIntent();
        String data = intent.getStringExtra("memoTitle");

        //액션바에 텍스트 형성
        getSupportActionBar().setTitle(data);

        //TextView 객체 초기화
        textView = (TextView)findViewById(R.id.contentView);
        //TextFileManger 객체 초기화
        mFileMgr = new TextFileManager(this);

        //파일 제목을 글 목록에서 전달 받은 파일 제목으로 지정
        mFileMgr.FILE_NAME = data;

        //파일 제목에 따른 파일 내용을 load함
        textView.setText(mFileMgr.load());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        //엑션바에 메뉴 형성 및 아이템 추가
        getMenuInflater().inflate(R.menu.action_bar_view, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        //필요하다면, 액션바상에 형성된 아이템에 대한 엑티비티 처리
        switch(item.getItemId()) {
            case R.id.action_delete:
                Log.d("ActionBar", "delete button");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //액션바에서 글 삭제 아이콘 클릭 시
    public void onClickForMemoDelete(MenuItem item) {
        //AlertDialog 호출
        showDialog(DIALOG_YES_NO_MESSAGE);
    }

    //글 보기에서 글 목록 화면으로 Intent 전달
    public void onClickForGoBack(MenuItem item) {
        Intent intent = new Intent(MemoViewActivity.this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DIALOG_YES_NO_MESSAGE:
                //AlertDialog.Builder 클래스는 AlertDialog 생성에 필요한 API를 제공
                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
                //AlertDialog의 대화상자를 설정
                builder.setMessage("Would you like to remove it?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {//사용자가 "Yes" 클릭 시
                                mFileMgr.delete();//파일 데이터 삭제
                                //글 보기 화면에서 글 목록 화면으로 전환
                                Intent intent = new Intent(MemoViewActivity.this, MainActivity.class);
                                startActivity(intent);
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {//사용자가 "No" 클릭 시
                            @Override
                            //AlertDialog 해제
                            public void onClick(DialogInterface dialog, int which) {//사용자가 "No" 클릭 시
                                dialog.dismiss();//AlertDialog 해제
                            }
                        });
                //AlertDialog 객체 생성
                android.app.AlertDialog alert = builder.create();
                return alert;
        }
        return null;
    }
}