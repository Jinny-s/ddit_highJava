package kr.or.ddit.basic;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class T06_1_BaseballGame {

	public static void main(String[] args) {

		Set<Integer> intRnd = new HashSet<Integer>();
		
		while(intRnd.size() < 3) { // 데이터가 3개가 될 때까지 반복
			int num = (int)(Math.random() * 9) + 1;
			intRnd.add(num);
		}
		
		System.out.println(intRnd);
		
		List<Integer> list = new ArrayList<Integer>();
		list.addAll(intRnd);
		
		System.out.println(list);
		
		Scanner s = new Scanner(System.in);
		
		int count = 0;
		while (true) {
			System.out.println("3가지 숫자 입력 >");
			int input = Integer.parseInt(s.nextLine());
			int i3 = input % 10;
			input /= 10;
			int i2 = input % 10;
			input /= 10;
			int i1 = input % 10;
			
			int strike = 0;
			int ball = 0;
			int out = 0;
			
			if (list.get(0) == i1) strike++;
			if (list.get(1) == i2) strike++;
			if (list.get(2) == i3) strike++;
			
			if (list.get(0) == i2 || list.get(0) == i3) ball++;
			if (list.get(1) == i1 || list.get(1) == i3) ball++;
			if (list.get(2) == i1 || list.get(2) == i2) ball++;
			
			out = 3 - strike - ball;
			
			System.out.println(++count + "차 시도(" + i1 + i2 + i3 + ") : "
					+ strike + "S " + ball + "B " + out + "O");
			if (strike == 3) {
				System.out.println("정답입니다!");
				break;
			}
		}
	}
}
