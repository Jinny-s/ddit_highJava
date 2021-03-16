package ex;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class E02_Lotto {

	public static void main(String[] args) {
		E02_Lotto lotto = new E02_Lotto();
		
		while(true) {
			lotto.startLotto();
		}
	}
	
	Scanner sc = new Scanner(System.in);
	
	public void startLotto() {
		System.out.println("=============================================");
		System.out.println("　　　　　　　 Lotto 프로그램");
		System.out.println("---------------------------------------------");
		System.out.println("　　　　1.Lotto 구입");
		System.out.println("　　　　2.프로그램 종료");
		System.out.println("=============================================");
		System.out.println("메뉴선택 >");
		int input = sc.nextInt();
		
		switch (input) {
		case 1: 
			pay();
			break;

		case 2:
			System.out.println("감사합니다.");
			System.exit(0);
		}
	}
	
	public void pay() {
		System.out.println("　　　　　　　 Lotto 구입시작");
		System.out.println();
		System.out.println("　　　(1,000원에 로또 번호 하나입니다.)");
		System.out.println("금액입력 >");
		System.out.println();
		int input = sc.nextInt();
		
		int count = input / 1000;
		int change = input - (count * 1000);
		
		Set<Integer> intRnd = new HashSet<Integer>();
		
		for (int i = 0; i < count; i++) {
			while(intRnd.size() < 6) {
				int num = (int)(Math.random() * 45 + 1);
				intRnd.add(num);
			}
			List numList = new ArrayList(intRnd);
			Collections.sort(numList);
			System.out.println("로또번호" + (i + 1) + " : " + numList);
			intRnd.clear();
		}
		System.out.println();
		System.out.println("받은 금액은 " + input + "원이고, 거스름돈은 " + change + "원입니다.");
	}
}