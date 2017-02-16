package kr.ac.koreatech.swkang.ch7actionbar;

import android.content.Context;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class TextFileManager{
    protected static String FILE_NAME;//TextFileManger 객체의 파일 제목을 위한 변수, 동일 패지키내 다른 클래스에서 사용됨
    Context mContext = null;

    public TextFileManager(Context _context){
        mContext = _context;
    }

    //파일에 문자열 데이터를 쓰는 TextFileManager 객체의 멤버 함수
    public void save(String data) {
        /*
        if(data == null || data.isEmpty() == true) {//저장 할 내용이 없다면
            return;
        }
        */
        FileOutputStream fos = null;
        try {
            fos = mContext.openFileOutput(FILE_NAME, Context.MODE_PRIVATE);//파일의 제목과 파일 열기 모드를 매개변수로 설정
            fos.write(data.getBytes());//스트링 형태의 내용물을 바이트 단위로 쓰기
            fos.close();//파일닫기
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //파일에서 데이터를 읽고 문자열 데이터로 반환하는 TextFileManager 객체의 멤버 함수
    public String load() {
        try {
            FileInputStream fis = mContext.openFileInput(FILE_NAME);//FILE_NAME에 따라 파일 열기
            byte[] data = new byte[fis.available()];
            fis.read(data);//파일에 있는 데이터를 바이트 단위로 읽기
            fis.close();//파일닫기

            return new String(data);//문자열 형태로 리턴
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    //파일을 삭제하는 TextFileManager 객체의 멤버 함수
    public void delete(){
        mContext.deleteFile(FILE_NAME);//FILE_NAME에 따라 파일 삭제
    }
}
