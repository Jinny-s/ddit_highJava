package kr.or.ddit.basic;

import javax.swing.JOptionPane;

/**
	컴퓨터와 가위바위보를 진행하는 프로그램을 작성하시오.
	
	컴퓨터의 가위바위보는 난수를 이용하여 구하고,
	사용자의 가위바위보는 showInputDialog()메서드를 이용하여 입력 받는다.
	
	입력시간은 5초로 제한하고 카운트다운을 진행한다.
	5초 안에 입력이 없으면 게임을 진 것으로 처리한다.
	
	5초 안에 입력이 완료되면 승패를 출력한다.
	
	결과예시)
 	========== 결과 ==========
	컴퓨터 : 가위
	당  신 : 바위
	결  과 : 당신이 이겼습니다.
	
 */
public class T07_ThreadGame {
	
	public static boolean inputCheck = false;
	
	public static void main(String[] args) {
		
		Thread th1 = new gbbGame();
		Thread th2 = new TimeLimit();
		
		th1.start();
		th2.start();
		
	}
}

class gbbGame extends Thread {
	@Override
	public void run() {
		String str;
		do {
			str = JOptionPane.showInputDialog("가위 바위 보를 입력하세요.");
		} while (!str.equals("가위") && !str.equals("바위") && !str.endsWith("보"));
		
		T07_ThreadGame.inputCheck = true;
		
		//////////////////////////////////////////////////////////////////
		String[] gbb = {"가위", "바위", "보"};
		String result;
		int index = (int) (Math.random() * 3);
		String com = gbb[index];
		
		System.out.println("========== 결과 ==========");
		System.out.println("컴퓨터 : " + com);
		System.out.println("사용자 : " + str);
		
		if (str.equals(com)) {
			result = "비겼습니다!";
		} else if ((str.equals("가위") && com.equals("바위"))
				|| (str.equals("바위") && com.equals("보"))
				|| (str.equals("보") && com.equals("가위"))) {
			result = "당신이 졌습니다!";
		} else {
			result = "당신이 이겼습니다!";
		}
		
		System.out.println("결  과 : " + result);
	}
}

class TimeLimit extends Thread {
	@Override
	public void run() {
		for (int i = 5; i > 0; i--) {
			if(T07_ThreadGame.inputCheck == true) {
				return;
			}
			
			System.out.println(i);
			
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println("시간초과로 패배하였습니다!");
	}
}