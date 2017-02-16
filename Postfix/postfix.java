package postfix;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

class MyFrame extends JFrame{
	private JPanel panel;//panel ��ü ����
	private JTextField text;//text�ʵ� ��ü ����
	
	public MyFrame(){
		setTitle("KICCalculator");//������ Ÿ��Ʋ
		setSize(300, 300);//������ ũ��
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//����
		
		panel = new JPanel();//panel ��ü ����
		text = new JTextField("");//text�ʵ� ��ü ����
		panel.add(new JLabel("designed by ��ǻ�Ͱ��к� 2011136004 ����â"));//panel�ȿ� label��ü ����
		panel.setBackground(Color.lightGray);						//label ���� ��	
		add(text, BorderLayout.NORTH);//text�ʵ� ���̾ƿ� ����
		add(new ButtonsPanel(), BorderLayout.CENTER);	//���� ��ư�� ���� ButtonPanel��ü ���̾ƿ� ����
		add(panel, BorderLayout.SOUTH);//panel ���̾ƿ� ����
		setResizable(false);//ũ�� ���� false
		setVisible(true);//���������� ����
	}
	
	//���� Ŭ���� ButtonsPanel���� text�ʵ带 ������ֱ� ���� get�޼ҵ� ����
	public JTextField getTextField(){
		return text;
	}
	
	//���� �߰��� ��ư�� ǥ��
	private class ButtonsPanel extends JPanel{
		
		private JButton []button;		//��ư �迭 ��ü ����
		private final int numberofbuttons = 18;	//��ư�� �� ����
		MyListener listener = new MyListener();	//��ư �̺�Ʈ ��ü
		
		//���� ������ ���� ���� ��ü �� ����
		//��ư���κ��� ���ڸ� �޴� ����(String)
		StringArrayStack temp = new StringArrayStack(100);	
		//temp�� ��ҵ��� postfix ���·� ��ȯ�ϴ� ����(String)
		StringArrayStack postfix = new StringArrayStack(100);	
		//postfix���� ��ȯ�� ���� operator �����ϴ� ����(String)
		StringArrayStack op = new StringArrayStack(100);			
		//������ ������� ����ϴ� ����(Int)
		IntArrayStack result = new IntArrayStack(100);
		private String number;//operator or operand ����
		private String operator1, operator2;//operator
		private int operand1, operand2;//operand
		private int operator1_priority, operator2_priority;	//operator �켱 ����
		//temp ���ÿ� ���� ���� �־��ֱ� ���� screen����
		private String screen ="";
		
		//ȭ�� ǥ�ø� ���� ����
		private String screen2 ="";
		private int cnt2 = 0, cnt3 = 0, cnt4 = 0, cnt5 = 0;
		
		//Ư�� ��ư ó��
		private boolean emptyButton = false;	//button "C"
		private boolean resultButton = false;	//button "="
		
		public ButtonsPanel(){
			this.setLayout(new GridLayout(4,5,10,10));//�׸��� ���̾ƿ�
			//��ư �����Ҵ� �� ����
			button = new JButton[numberofbuttons];	
			button[0] = new JButton("/");
			button[1] = new JButton("��");
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
				add(button[i]);//ButtonsPanelŬ������ button����
                                        //��ư �̺�Ʈ ���
				button[i].addActionListener(listener);
			}
		}
		
		public void paintComponent(Graphics g){
			super.paintComponent(g);
			//clearButton ����
			if(emptyButton){
				while(!temp.isEmpty()){
					temp.delete();
				}
				screen2 ="";
			}
			//resultButton ����
			if(resultButton){
				for(int i=0;i<=temp.currentsizeofstack();i++){
					number = temp.getArray(i);
					//��Ұ� �������� �ƴ��� Ȯ���Ѵ�.
					if(number != "+" && number != "-" && number != "/" && number != "*"){//������ ���
						postfix.push(number);
					} else{//���ڰ� �ƴ� ���
						if(op.isEmpty()){//ù��° op�� ���
							operator1 = number;
							op.push(operator1);
						} else{//�ι�° op�� ���
							operator2 = number;
							//�켱������ ���Ѵ�.
							operator1_priority = getOpPriority(operator1);
							operator2_priority = getOpPriority(operator2);
							if(operator1_priority >= operator2_priority){//ù��° op�� ���� op���� �켱������ ���ų� ���� ���
								//operator1->postfix���ÿ� ����
								postfix.push(op.pop());
								//operator2->op���ÿ� ����
								op.push(operator2);
								operator1=operator2;
							} else{//ù��° op�� ���� op�� �켱�������� ���� ���
								//�켱������ ���� operator2->op���ÿ� ����
								op.push(operator2);
								operator1 = operator2;
							}
						}
					}
				}
				//screen�� ���� postfix���·� ��ȯ
				while(!op.isEmpty()){
					postfix.push(op.pop());
				}
				System.out.print("Postfix"); postfix.display();
				//postfix���¸� ����ϱ� ���� for��
				for(int i=0;i<=postfix.currentsizeofstack();i++){
					number = postfix.getArray(i);
					//��Ұ� �������� �ƴ��� Ȯ���Ѵ�.
					if(number != "+" && number != "-" && number != "/" && number != "*"){//������ ���
						//������ ��� String������ int����ȯ�Ͽ� result���ÿ� ��´�.
						try{
							result.push(Integer.parseInt(number));
						}
						catch(Exception e){
						}
					} else{//���ڰ� �ƴ� ���
						//result�� �ִ� operand 2���� �ҷ��� ����Ѵ�.
						operand1 = result.pop();
						operand2 = result.pop();	
						if(number == "+"){//+�� ���
							result.push(operand2 + operand1);
						}
						else if(number == "-"){//-�� ���
							result.push(operand2 - operand1);
						}
						else if(number == "*"){//*�� ���
							result.push(operand2 * operand1);
						}
						else{// /�� ���
							result.push(operand2 / operand1);
						}
					}
				}
				//result��� ���� String����ȯ �Ͽ� screen2�� pop()�Ѵ�.
				screen2 = Integer.toString(result.pop());
				while(!postfix.isEmpty()){
					postfix.delete();
				}
				System.out.print("Result>>"); System.out.println(screen2);
			}
			//
			getTextField().setText(screen2);
			System.out.print("Stack"); temp.display();
			//Ư�� ��ư ó��
			emptyButton = false;
			resultButton = false;
		}
		
		// ������ �켱 ����
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
				//operator�� �������� ���
				if(cnt2 != 0 || cnt3 != 0 || cnt4 != 0 || cnt5 != 0){
				          //�Է¹޾Ҵ� ������ temp���ÿ� �ִ´�.
					temp.push(screen);
					if(cnt2 != 0){
						//�Էµ� �����ڵ� temp���ÿ� �ִ´�.
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
					//temp���ÿ� ���� screen�� refresh�Ǿ�� �ϹǷ� ""���� �ʱ�ȭ �Ѵ�.
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
		MyFrame test = new MyFrame();//�����Լ����� ����
	}
}


