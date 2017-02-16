package kr.ac.koreatech.swkang.ch7actionbar;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    //ListVeiw 객체 선언
    private ListView m_ListView;

    //ListView 객체를 리스트 형태로 저장하기 위한 ArrayAdapter<String> 객체 선언
    private ArrayAdapter<String> m_Adapter;

    //String 형태의 파일 제목을 리스트 형식으로 저장하기 위한 ArrayList<String> 객체 선언
    private ArrayList<String> values = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //액션바에 "Memo List" 텍스트 형성
        getSupportActionBar().setTitle("Memo List");

        //스트링 형태의 파일 제목 하나를 출력하는 레이아웃으로 어댑터 생성
        m_Adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, values);

        //ListView 객체 초기화
        m_ListView = (ListView) findViewById(R.id.list);

        //ListView와 Adapter연결
        m_ListView.setAdapter(m_Adapter);

        //fileList() 함수는 ContextWrapper의 멤버 함수로 동일 애플리케이션 패키지 내에서 내부 메모리에 저장 되어 있는 메모 제목들을 list 에 저장
        int i;
        String[] list = fileList();

        //반복문을 통해 메모 제목의 수반큼 어텝터에 입력
        for(i=1;i<list.length;i++) m_Adapter.add(list[i]);

        //ListView 아이템 터치 시 이벤트를 처리할 리스너 설정
        m_ListView.setOnItemClickListener(onClickListItem);
    }

    private AdapterView.OnItemClickListener onClickListItem = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            //현재 글 목록 화면에서 글 보기 화면으로 전환
            Intent intent = new Intent(MainActivity.this, MemoViewActivity.class);
            //사용자가 선택한 파일 제목에 데이터와 함께 인텐트를 전달, position은 Adapter에 입력된 데이터의 위치를 의미
            intent.putExtra("memoTitle", m_Adapter.getItem(position));
            startActivity(intent);
        }
    };
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        //엑션바에 메뉴 형성 및 아이템 추가
        getMenuInflater().inflate(R.menu.action_bar_create, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        //필요하다면, 액션바상에 형성된 아이템에 대한 이벤트 처리
        switch(item.getItemId()) {
            case R.id.action_create:
                Log.d("ActionBar", "create button");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //글 목록 화면에서 글 작성 화면으로 Intent 전달
    public void onClickForNewMemo(MenuItem item) {
        Intent intent = new Intent(MainActivity.this, MemoTextActivity.class);
        startActivity(intent);
    }
}
