package postfix;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

class MyFrame extends JFrame{
	private JPanel panel;//panel 객체 선언
	private JTextField text;//text필드 객체 선언
	
	public MyFrame(){
		setTitle("KICCalculator");//프레임 타이틀
		setSize(300, 300);//프레임 크기
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//종료
		
		panel = new JPanel();//panel 객체 생성
		text = new JTextField("");//text필드 객체 생성
		panel.add(new JLabel("designed by 컴퓨터공학부 2011136004 강인창"));//panel안에 label객체 생성
		panel.setBackground(Color.lightGray);						//label 배경색 셋	
		add(text, BorderLayout.NORTH);//text필드 레이아웃 설정
		add(new ButtonsPanel(), BorderLayout.CENTER);	//따로 버튼을 위한 ButtonPanel객체 레이아웃 설정
		add(panel, BorderLayout.SOUTH);//panel 레이아웃 설정
		setResizable(false);//크기 조절 false
		setVisible(true);//가시적으로 생성
	}
	
	//내부 클래스 ButtonsPanel에서 text필드를 사용해주기 위해 get메소드 선언
	public JTextField getTextField(){
		return text;
	}
	
	//계산기 중간층 버튼들 표시
	private class ButtonsPanel extends JPanel{
		
		private JButton []button;		//버튼 배열 객체 선언
		private final int numberofbuttons = 18;	//버튼의 총 개수
		MyListener listener = new MyListener();	//버튼 이벤트 객체
		
		//계산기 구현을 위한 스택 객체 및 변수
		//버튼으로부터 숫자를 받는 스택(String)
		StringArrayStack temp = new StringArrayStack(100);	
		//temp의 요소들을 postfix 형태로 변환하는 스택(String)
		StringArrayStack postfix = new StringArrayStack(100);	
		//postfix형태 변환을 위해 operator 저장하는 스택(String)
		StringArrayStack op = new StringArrayStack(100);			
		//수식의 결과값을 계산하는 스택(Int)
		IntArrayStack result = new IntArrayStack(100);
		private String number;//operator or operand 구별
		private String operator1, operator2;//operator
		private int operand1, operand2;//operand
		private int operator1_priority, operator2_priority;	//operator 우선 순위
		//temp 스택에 들어가는 값을 넣어주기 위한 screen변수
		private String screen ="";
		
		//화면 표시를 위한 변수
		private String screen2 ="";
		private int cnt2 = 0, cnt3 = 0, cnt4 = 0, cnt5 = 0;
		
		//특수 버튼 처리
		private boolean emptyButton = false;	//button "C"
		private boolean resultButton = false;	//button "="
		
		public ButtonsPanel(){
			this.setLayout(new GridLayout(4,5,10,10));//그리드 레이아웃
			//버튼 동적할당 및 생성
			button = new JButton[numberofbuttons];	
			button[0] = new JButton("/");
			button[1] = new JButton("←");
			button[2] = new JButton("7");
			button[3] = new JButton("8");
			button[4] = new JButton("9");
			button[5] = new JButton("*");
			button[6] = new JButton("C");
			button[7] = new JButton("4");
			button[8] = new JButton("5");
			button[9] = new JButton("6");
			button[10] = new JButton("+");
			button[11] = new JButton("=");
			button[12] = new JButton("1");
			button[13] = new JButton("2");
			button[14] = new JButton("3");
			button[15] = new JButton("-");
			button[16] = new JButton(".");
			button[17] = new JButton("0");
			for(int i=0;i<numberofbuttons;i++){
				add(button[i]);//ButtonsPanel클래스에 button생성
                                        //버튼 이벤트 등록
				button[i].addActionListener(listener);
			}
		}
		
		public void paintComponent(Graphics g){
			super.paintComponent(g);
			//clearButton 실행
			if(emptyButton){
				while(!temp.isEmpty()){
					temp.delete();
				}
				screen2 ="";
			}
			//resultButton 실행
			if(resultButton){
				for(int i=0;i<=temp.currentsizeofstack();i++){
					number = temp.getArray(i);
					//요소가 숫자인지 아닌지 확인한다.
					if(number != "+" && number != "-" && number != "/" && number != "*"){//숫자일 경우
						postfix.push(number);
					} else{//숫자가 아닐 경우
						if(op.isEmpty()){//첫번째 op일 경우
							operator1 = number;
							op.push(operator1);
						} else{//두번째 op일 경우
							operator2 = number;
							//우선순위를 정한다.
							operator1_priority = getOpPriority(operator1);
							operator2_priority = getOpPriority(operator2);
							if(operator1_priority >= operator2_priority){//첫번째 op가 다음 op보다 우선순위가 높거나 같을 경우
								//operator1->postfix스택에 저장
								postfix.push(op.pop());
								//operator2->op스택에 저장
								op.push(operator2);
								operator1=operator2;
							} else{//첫번째 op가 다음 op의 우선순위보다 낮을 경우
								//우선순위가 높은 operator2->op스택에 저장
								op.push(operator2);
								operator1 = operator2;
							}
						}
					}
				}
				//screen의 식을 postfix형태로 변환
				while(!op.isEmpty()){
					postfix.push(op.pop());
				}
				System.out.print("Postfix"); postfix.display();
				//postfix형태를 계산하기 위한 for문
				for(int i=0;i<=postfix.currentsizeofstack();i++){
					number = postfix.getArray(i);
					//요소가 숫자인지 아닌지 확인한다.
					if(number != "+" && number != "-" && number != "/" && number != "*"){//숫자일 경우
						//숫자일 경우 String변수를 int형변환하여 result스택에 담는다.
						try{
							result.push(Integer.parseInt(number));
						}
						catch(Exception e){
						}
					} else{//숫자가 아닐 경우
						//result에 있는 operand 2개를 불러와 계산한다.
						operand1 = result.pop();
						operand2 = result.pop();	
						if(number == "+"){//+일 경우
							result.push(operand2 + operand1);
						}
						else if(number == "-"){//-일 경우
							result.push(operand2 - operand1);
						}
						else if(number == "*"){//*일 경우
							result.push(operand2 * operand1);
						}
						else{// /일 경우
							result.push(operand2 / operand1);
						}
					}
				}
				//result결과 값을 String형변환 하여 screen2에 pop()한다.
				screen2 = Integer.toString(result.pop());
				while(!postfix.isEmpty()){
					postfix.delete();
				}
				System.out.print("Result>>"); System.out.println(screen2);
			}
			//
			getTextField().setText(screen2);
			System.out.print("Stack"); temp.display();
			//특수 버튼 처리
			emptyButton = false;
			resultButton = false;
		}
		
		// 연산자 우선 순위
		private int getOpPriority(String operator){
			if(operator =="+" || operator =="-"){
				return 3;
			}
			else{
				return 7;
			}	
		}

		private class MyListener implements ActionListener{
			public void actionPerformed(ActionEvent e){
				if(e.getSource() == button[12]){
					screen += "1"; screen2 += "1";
				}
				if(e.getSource() == button[13]){
					screen += "2"; screen2 += "2";
				}
				if(e.getSource() == button[14]){
					screen += "3"; screen2 += "3";
				}
				if(e.getSource() == button[7]){
					screen += "4"; screen2 += "4";
				}
				if(e.getSource() == button[8]){
					screen += "5"; screen2 += "5";
				}
				if(e.getSource() == button[9]){
					screen += "6"; screen2 += "6";
				}
				if(e.getSource() == button[2]){
					screen += "7"; screen2 += "7";
				}
				if(e.getSource() == button[3]){
					screen += "8"; screen2 += "8";
				}
				if(e.getSource() == button[4]){
					screen += "9"; screen2 += "9";
				}
				if(e.getSource() == button[17]){
					screen += "0"; screen2 += "0";
				}
				if(e.getSource() == button[10]){
					cnt2++;
				}
				if(e.getSource() == button[15]){
					cnt3++;
				}
				if(e.getSource() == button[0]){
					cnt4++;
				}
				if(e.getSource() == button[5]){
					cnt5++;
				}
				//operator가 눌러졌을 경우
				if(cnt2 != 0 || cnt3 != 0 || cnt4 != 0 || cnt5 != 0){
				          //입력받았던 값들을 temp스택에 넣는다.
					temp.push(screen);
					if(cnt2 != 0){
						//입력된 연산자도 temp스택에 넣는다.
						screen += "+"; screen2 += "+";
						temp.push("+");
					}
					if(cnt3 != 0){
						screen += "-"; screen2 += "-";
						temp.push("-");
					}
					if(cnt4 != 0){
						screen += "/"; screen2 += "/";
						temp.push("/");
					}
					if(cnt5 != 0){
						screen += "*"; screen2 += "*";
						temp.push("*");
					}
					//temp스택에 넣을 screen은 refresh되어야 하므로 ""으로 초기화 한다.
					screen ="";
				}
				if(e.getSource() == button[11]){
					temp.push(screen);
					resultButton = true;
					screen="";
				}
				if(e.getSource() == button[6]){
					emptyButton = true;
				}

				cnt2 = 0; cnt3 = 0; cnt4 = 0; cnt5 = 0;
				repaint();
			}
		}
	}
}

public class postfix {

	public static void main(String[] args) {
		MyFrame test = new MyFrame();//메인함수에서 실행
	}
}


