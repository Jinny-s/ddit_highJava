package ex;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class E01_ListSort {

	public static void main(String[] args) {
		
		List<Student> sList = new ArrayList<Student>();
		sList.add(new Student("40303", "강건우", getScore(), getScore(), getScore()));
		sList.add(new Student("40305", "김두환", getScore(), getScore(), getScore()));
		sList.add(new Student("40301", "김이현", getScore(), getScore(), getScore()));
		sList.add(new Student("40302", "정영인", getScore(), getScore(), getScore()));
		sList.add(new Student("40304", "정유진", getScore(), getScore(), getScore()));
		
		for(Student student1: sList) {
			for(Student student2: sList) {
				if(student1.getSum() < student2.getSum()) {
					student1.addRank();
				}
			}
		}
		
		System.out.println("정렬 전");
		System.out.println();
		System.out.println("학번\t이름\t국어\t영어\t수학\t총점\t석차");
		for(Student student: sList) {
			System.out.println(student);
		}
		System.out.println("────────────────────────────────────────────────────────────────────────");
		
		Collections.sort(sList);
		
		System.out.println("학번의 오름차순으로 정렬 후");
		System.out.println();
		System.out.println("학번\t이름\t국어\t영어\t수학\t총점\t석차");
		for(Student student: sList) {
			System.out.println(student);
		}
		System.out.println("────────────────────────────────────────────────────────────────────────");
	
		Collections.sort(sList, new SortSumDesc());
		System.out.println("총점의 내림차순으로 정렬 후 동점 시 학점 내림차순 정렬");
		System.out.println();
		System.out.println("학번\t이름\t국어\t영어\t수학\t총점\t석차");
		for(Student student: sList) {
			System.out.println(student);
		}
		System.out.println("────────────────────────────────────────────────────────────────────────");
	
	
	}
	
	public static int getScore() {
		return (int)(Math.random() * 101);
	}
}

class SortSumDesc implements Comparator<Student> {

	@Override
	public int compare(Student s1, Student s2) {
		int compareNum = new Integer(s1.getSum()).compareTo(s2.getSum()) * -1;
		if (compareNum == 0) {
			return new String(s1.getNum()).compareTo(s2.getNum()) * -1;
		}
		return compareNum;
	}
}

class Student implements Comparable<Student> {
	
	private String num;
	private String name;
	private int kr;
	private int eng;
	private int math;
	private int sum;
	private int rank;
	
	public Student(String num, String name, int kr, int eng, int math) {
		super();
		this.num = num;
		this.name = name;
		this.kr = kr;
		this.eng = eng;
		this.math = math;
		this.sum = kr + eng + math;
		this.rank = 1;
	}

	public void addRank() {
		this.rank++;
	}

	public String getNum() {
		return num;
	}

	public void setNum(String num) {
		this.num = num;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getKr() {
		return kr;
	}

	public void setKr(int kr) {
		this.kr = kr;
	}

	public int getEng() {
		return eng;
	}

	public void setEng(int eng) {
		this.eng = eng;
	}

	public int getMath() {
		return math;
	}

	public void setMath(int math) {
		this.math = math;
	}

	public int getSum() {
		return sum;
	}

	public void setSum(int sum) {
		this.sum = sum;
	}

	public int getRank() {
		return rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}

	@Override
	public String toString() {
		return num + "\t" + name + "\t" + kr + "\t" + eng + "\t" + math + "\t"
				+ sum + "\t" + rank;
	}

	@Override
	public int compareTo(Student student) {
		return this.getNum().compareTo(student.getNum());
	}
	
}




