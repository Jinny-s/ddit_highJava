package kr.or.ddit.basic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class E01_HorseRacing {
/**
    10마리 말들이 경주하는 경마 프로그램
    클래스 : Horse
    멤버변수 : 말이름(String), 등수(int)
    등수를 오름차순으로 처리할 수 있는 기능(Comparable 인터페이스)
    
    경기 구간 1~50구간

    경기 중 중간중간 각 말들의 위치는 > 로 표시
    경기 끝나면 등수를 기준으로 정렬하여 출력
 */
	static int intRank = 1;
	
	public static void main(String[] args) {
		
		// 말 list 생성
		List<Horse> horse = new ArrayList<Horse>();
		for(int i = 1; i <= 10; i++) {
			if (i < 10) horse.add(new Horse("🐴 0" + i + "번마"));
			else horse.add(new Horse("🐴 " + i + "번마"));
		}
		
		// 말 Thread 실행
		for (Horse h : horse) {
			h.start();
		}
		
		// join
		for (Horse h : horse) {
			try {
				h.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println();
		System.out.println("경기 종료!");
		System.out.println();
		System.out.println("============== 결과 발표 ==============");
		
		Collections.sort(horse);
		
		for(Horse h : horse) {
			System.out.println(h.getRank() + "등 : " + h.gethName());
		}
		System.out.println("=======================================");
		
		
	}
	
	
	
	
}

class Horse extends Thread implements Comparable<Horse>{
	
	private String hName;
	private int rank;

	public Horse(String hName) {
		this.hName = hName;
	}

	public String gethName() {
		return hName;
	}

	public void sethName(String hName) {
		this.hName = hName;
	}

	public int getRank() {
		return rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}
	
	@Override
	public void run() {
		for(int i = 1; i < 50; i++) {
			System.out.print("\n" + hName + " : ");
			
			for(int j = 0; j < i; j++) {
				System.out.print("-");
			}
			
			System.out.print(">");
			
			for(int j = 49; j > i; j--) {
				System.out.print("-");
			}
			
			try {
				Thread.sleep((int)(Math.random() * 301 + 200));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		System.out.print("\n" + hName + "도착");
		
		setRank(E01_HorseRacing.intRank);
		E01_HorseRacing.intRank++;
	}
	
	@Override
	public int compareTo(Horse h) {
		return Integer.compare(rank, h.getRank());
	}
}




























