package kr.or.ddit.basic;

import java.util.Arrays;

public class E01_HorseRacing_Ans {

	public static void main(String[] args) {
		Horse3[] horses = new Horse3[] { new Horse3("01번말"), new Horse3("02번말"), new Horse3("03번말"), new Horse3("04번말"),
				new Horse3("05번말"), new Horse3("06번말"), new Horse3("07번말"), new Horse3("08번말"), new Horse3("09번말"),
				new Horse3("10번말") };
		PlayState state = new PlayState(horses);
		for (Horse3 h : horses) {
			h.start();
		}
		state.start();
		for (Horse3 h : horses) {
			try {
				h.join();
			} catch (InterruptedException e) {
			}
		}
		try {
			state.join();
		} catch (InterruptedException e) {
		}
		System.out.println();
		System.out.println("경기 끝........");
		System.out.println();
		Arrays.sort(horses);
		System.out.println("경기 결과");
		for (Horse3 h : horses) {
			System.out.println(h);
		}
	}
}

class Horse3 extends Thread implements Comparable<Horse3> {
	public static int currentRank = 0;
	private String horseName;
	private int rank;
	private int location;

	public Horse3(String horseName) {
		this.horseName = horseName;
	}

	public String getHorseName() {
		return horseName;
	}

	public void setHorseName(String horseName) {
		this.horseName = horseName;
	}

	public int getRank() {
		return rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}

	public int getLocation() {
		return location;
	}

	public void setLocation(int location) {
		this.location = location;
	}

	@Override
	public String toString() {
		return "경주마 " + horseName + "는" + rank + "등 입니다.";
	}

	@Override
	public int compareTo(Horse3 h) {
		return Integer.compare(rank, h.getRank());
	}

	@Override
	public void run() {
		for (int i = 1; i <= 50; i++) {
			location = i;
			try {
				Thread.sleep((int) (Math.random() * 400));
			} catch (InterruptedException e) {
			}
		}
		rank = ++Horse3.currentRank;
	}
}

class PlayState extends Thread {
	private Horse3[] horses;

	public PlayState(Horse3[] horses) {
		this.horses = horses;
	}

	@Override
	public void run() {
		while (true) {
			if (Horse3.currentRank == horses.length) {
				break;
			}
			for (int i = 1; i <= 10; i++) {
				System.out.println();
			}
			for (int i = 0; i < horses.length; i++) {
				System.out.print(horses[i].getHorseName() + " : ");
				for (int j = 1; j <= 50; j++) {
					if (horses[i].getLocation() == j) {
						System.out.print(">");
					} else {
						System.out.print("-");
					}
				}
				System.out.println();
			}
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
			}
		}
	}
}
