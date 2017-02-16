package kr.ac.koreatech.swkang.ch7actionbar;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MemoTextActivity extends AppCompatActivity {

    //EditText 객체 선언
    private EditText title = null;
    private EditText content = null;

    //Button 객체 선언
    private Button write;
    private Button cancle;

    //TextFileManager 객체 선언
    private TextFileManager mFileMgr= new TextFileManager(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memotext);

        //액션바에 텍스트 형성
        getSupportActionBar().setTitle("New Memo");

        //EditText 객체 초기화
        title = (EditText) findViewById(R.id.titleText);
        content = (EditText) findViewById(R.id.contentText);

        //Button 객체 초기화
        write = (Button) findViewById(R.id.write);
        cancle = (Button) findViewById(R.id.cancle);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        //엑션바에 메뉴 형성 및 아이템 추가
        getMenuInflater().inflate(R.menu.action_bar_back, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        //필요하다면, 액션바상에 형성된 아이템에 대한 이벤트 처리
        switch (item.getItemId()) {
            case R.id.action_back:
                Log.v("ActionBar", "back button");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void onClickForSaveOrCancle(View v) {
        String titleData = title.getText().toString();
        String contentData = content.getText().toString();

        switch (v.getId()) {
            case R.id.write: {
                //TextFileNanager 객체의 FILE_NAME에 파일 제목 저장
                mFileMgr.FILE_NAME = titleData + ".txt";

                //작성한 파일 내용을 받아온 후 파일 형식으로 저장
                mFileMgr.save(contentData);

                //저장 후 제목과 내용 초기화
                title.setText("");
                content.setText("");

                //저장에 관련한 토스트 메세지
                Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show();

                //현재 글 작성 액티비티에서 글 목록 액티비티로 화면을 전환
                Intent intent = new Intent(MemoTextActivity.this, MainActivity.class);
                startActivity(intent);
                break;
            }

            case R.id.cancle: {

                //글 작성 취소시 글 제목 초기화
                title.setText("");

                //글 작성 취소시 글 내용 초기화
                content.setText("");

                //토스트 메세지
                Toast.makeText(this, "Cancled", Toast.LENGTH_SHORT).show();
                break;
            }
        }
    }

    //액션바에 있는 메뉴 클릭 시 현재 글 작성 화면에서 글 목록 화면으로 전환
    public void onClickForGoBack(MenuItem item) {
        Intent intent = new Intent(MemoTextActivity.this, MainActivity.class);
        startActivity(intent);
    }
}