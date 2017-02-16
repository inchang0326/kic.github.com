package com.example.sens_user.assignment02;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    boolean isNumberInput = true;// EditText 란에 숫자가 입력 되었는지 안되었는지를 확인하기 위한 변수
    EditText numberOneInput;// first input EditText
    EditText numberTwoInput;// second input EditText
    String clickedOperator;// input operator
    String sNumberOneInput;// first input to string type
    String sNumberTwoInput;// second input to string type
    double dNumberOneInput;// string type of first input to double type
    double dNumberTweoInput;// string type of second input to double type
    double dResult;// double type of final output
    CharSequence csResult;// double type of final ouput to CharSequence
    TextView tv;// final output TextView

    class MyListenerClass implements View.OnClickListener {
        public void onClick(View view) {

            // EditText의 값을 getText()메소드를 사용하여 string 형태로 받아 온다.
            sNumberOneInput = numberOneInput.getText().toString();
            sNumberTwoInput = numberTwoInput.getText().toString();

            // 예외 1. EditText 란에 숫자 입력 없이 연산 버튼을 클릭했을 경우 처리
            try {
                dNumberOneInput = Double.parseDouble(sNumberOneInput);
                dNumberTweoInput = Double.parseDouble(sNumberTwoInput);
            } catch(NumberFormatException e) {// NumberFormatException 처리

                // Toast 메세지를 띄우고 isNumberInput = false일 설정 후 TextView에 출력할 최종 값을 ""로 초기화 한다.
                Toast.makeText(getApplicationContext(), "숫자를 입력해주세요!", Toast.LENGTH_LONG).show();
                isNumberInput = false;
                if(isNumberInput == false) tv.setText("");
            } finally{
                switch (view.getId()) {// 각 Button의 id를 식별
                    case R.id.button_add:// Button의 id가 button_add일 경우
                        dResult = dNumberOneInput + dNumberTweoInput;// 덧셈 연산
                        clickedOperator = "+";// operator = "+" 초기화
                        break;
                    case R.id.button_sub:// button의 id가 button_sub일 경우
                        dResult = dNumberOneInput - dNumberTweoInput;// 뺄셈 연산
                        clickedOperator = "-";// operator = "-" 초기화
                        break;
                    case R.id.button_mul:// button의 id가 button_mul일 경우
                        dResult = dNumberOneInput * dNumberTweoInput;// 곱셈 연산
                        clickedOperator = "×";// operator = "*" 초기화
                        break;
                    case R.id.button_div:// button의 id가 button_div일 경우
                        dResult = dNumberOneInput / dNumberTweoInput;// 나눗셈 연산

                        // 예외 2. 나눗셈 연산할 때 0으로 나누는 경우 처리
                        if (dNumberTweoInput == 0) {// 입력된 몫이 0일 경우

                            // 전체 결과를 0으로 처리하고 Toast 메세지를 띄운다.
                            dResult = 0.0;
                            Toast.makeText(getApplicationContext(), "0으로 나눌 수 없습니다! ex) 7÷0", Toast.LENGTH_LONG).show();
                        }
                        clickedOperator = "÷";//operator = "/"초기화
                        break;
                }
                csResult = String.format("%.2f", dResult);// Double 형태인 결과값 dResult를 소수점 둘째자리 까지 자르고 string 형태로 반환한다.

                if(isNumberInput == true) {

                    // 예외 3. EditText 란에 숫자 입력 시 소수점으로 시작했을 경우 처리
                    if (sNumberOneInput.charAt(0) == '.') {

                        // TextView 에서 연산되는 과정을 출력할 때 첫 번째 입력값 소수점 앞에 0과 함께 출력하고 Toast 메세지를 띄운다.
                        sNumberOneInput =  '0' + sNumberOneInput;
                        Toast.makeText(getApplicationContext(), "숫자 입력 시 소수점으로 시작하셨어요! ex) .7, .9", Toast.LENGTH_LONG).show();
                    }
                    if (sNumberTwoInput.charAt(0) == '.') {

                        // TextView 에서 연산되는 과정을 출력할 때 두 번째 입력값 소수점 앞에 0과 함께 출력하고 Toast 메세지를 띄운다.
                        sNumberTwoInput =  '0' + sNumberTwoInput;
                        Toast.makeText(getApplicationContext(), "숫자 입력 시 소수점으로 시작하셨어요! ex) .7, .9", Toast.LENGTH_LONG).show();
                    }

                    // 예외 4. EditText 란에 숫자 입력 시 소수점으로 끝났을 경우 처리
                    if (sNumberOneInput.charAt(sNumberOneInput.length() - 1) == '.') {

                        // TextView 에서 연산되는 과정을 출력할 때 첫 번째 입력값 마지막 소수점을 없애고 출력하고 Toast 메세지를 띄운다.
                        sNumberOneInput = sNumberOneInput.substring(0, sNumberOneInput.length() - 1);
                        Toast.makeText(getApplicationContext(), "숫자 입력 시 소수점으로 끝나셨어요! ex) 9., 7.", Toast.LENGTH_LONG).show();
                    }
                    if (sNumberTwoInput.charAt(sNumberTwoInput.length() - 1) == '.') {

                        // TextView 에서 연산되는 과정을 출력할 때 두 번째 입력값 마지막 소수점을 없애고 출력하고 Toast 메세지를 띄운다.
                        sNumberTwoInput = sNumberTwoInput.substring(0, sNumberTwoInput.length() - 1);
                        Toast.makeText(getApplicationContext(), "숫자 입력 시 소수점으로 끝나셨어요! ex) 9., 7.", Toast.LENGTH_LONG).show();
                    }

                    // TextView에 최종 연산 과정 및 결과값 출력
                    tv.setText(sNumberOneInput + " " + clickedOperator + " " + sNumberTwoInput + " " + "=" + " " + csResult);
                }

                //최종 연산 이후 첫 번째와 두 번째 EditText 란에 있는 숫자를 지워주기 위해 null 값을 지정해 주고 isNumberInput 값을 true로 재설정한다.
                numberOneInput.setText(null);
                numberTwoInput.setText(null);
                isNumberInput = true;
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        numberOneInput = (EditText)findViewById(R.id.number1);
        numberTwoInput = (EditText)findViewById(R.id.number2);
        tv = (TextView)findViewById(R.id.output);

        // EditText 란에 숫자 입력시 처음 0 두번 연속으로 입력하는 경우를 방지하기 위한 TextWatcher 클래스
        numberOneInput.addTextChangedListener(new TextWatcher() {

                String beforeText;

                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    beforeText = s.toString();// EditText에 값을 입력할 때 변하기 전 값을 의미한다.
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                }

                @Override
                public void afterTextChanged(Editable s) {

                    // 5. 처음 연속 0 입력
                    if(s.length() > 1){
                        if(s.charAt(0) == '0' && s.charAt(1) == '0'){// 처음 연속으로 0 입력될 경우

                            // Toast 메세지를 띄우고 EditText에 beforeText값을 출력하고 커서를 가장 오른쪽에 놓는다.
                            Toast.makeText(getApplicationContext(), "처음 연속으로 0 두번 입력하셨어요!", Toast.LENGTH_LONG).show();
                            numberOneInput.setText(beforeText);
                            numberOneInput.setSelection(numberOneInput.length());
                        }
                    }
                }
        });
        numberTwoInput.addTextChangedListener(new TextWatcher() {

            String beforeText;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                beforeText = s.toString();
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {

                // 예외 5. 처음 연속 0 입력
                if(s.length() > 1){
                    if(s.charAt(0) == '0' && s.charAt(1) == '0'){// 처음 연속으로 0 입력될 경우

                        // Toast 메세지를 띄우고 EditText에 beforeText값을 출력하고 커서를 가장 오른쪽에 놓는다.
                        Toast.makeText(getApplicationContext(), "처음 연속으로 0 두번 입력하셨어요!", Toast.LENGTH_LONG).show();
                        numberTwoInput.setText(beforeText);
                        numberTwoInput.setSelection(numberTwoInput.length());
                    }

                }
            }
        });

        // 각 Button 객체 생성
        Button addButton = (Button) findViewById(R.id.button_add);
        Button subButton = (Button) findViewById(R.id.button_sub);
        Button mulButton = (Button) findViewById(R.id.button_mul);
        Button divButton = (Button) findViewById(R.id.button_div);

        // buttonListener 클래스 생성 후 각 button을 위젯에 등록
        MyListenerClass buttonListener = new MyListenerClass();
        addButton.setOnClickListener(buttonListener);
        subButton.setOnClickListener(buttonListener);
        mulButton.setOnClickListener(buttonListener);
        divButton.setOnClickListener(buttonListener);
    }
}
