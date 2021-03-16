package ex;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class E03_Hotel {
	private Scanner scan;
	private Map<Integer, Hotel> hotelManage;
	
	public E03_Hotel() {
		scan = new Scanner(System.in);
		hotelManage = new HashMap<Integer, Hotel>();
	}
	
	// 호텔 오픈
	public void open() {
		System.out.println("──────────────────────────────────────────────────────────");
		System.out.println("　　　　　　　　D　D　I　T　H　O　T　E　L");
		System.out.println("　　　　　　　　　　영업을 시작합니다.");
		System.out.println("──────────────────────────────────────────────────────────");
	}
	
	// 호텔 클로즈
	public void close() {
		System.out.println("──────────────────────────────────────────────────────────");
		System.out.println("　　　　　　　　D　D　I　T　H　O　T　E　L");
		System.out.println("　　　　　　　　　　영업을 종료합니다.");
		System.out.println("──────────────────────────────────────────────────────────");
		System.out.println();
		System.exit(0);
	}
	
	// 메뉴 출력
	public void displayMenu() {
		System.out.println();
		System.out.println("──────────────────────────────────────────────────────────");
		System.out.println("　　　　　　　　어떤 업무를 하시겠습니까?");
		System.out.println("　　1.체크인　　2.체크아웃　　3.객실상태　　4.업무종료");
		System.out.println("──────────────────────────────────────────────────────────");
		System.out.println("메뉴선택 => ");
	}
	
	public void hotelStart() {
		
		open();
		
		while(true) {
			
			displayMenu();
			
			int menuNum = scan.nextInt();
			
			switch(menuNum) {
			case 1 : checkIn();
				break;
			case 2 : checkOut();
				break;
			case 3 : status();
				break;
			case 4 : close();
				break;
			default :
				System.out.println("잘못 입력하셨습니다. 다시 입력해주세요.");
			}
		}
	}
	
	private void checkIn() {
		System.out.println("어느 방에 체크인 하시겠습니까?");
		System.out.println("방 번호 입력 => ");
		int roomNum = scan.nextInt();
		
		if(hotelManage.get(roomNum) != null) {
			System.out.println(roomNum + "호는 이미 체크인된 객실입니다.");
			return;
		}
		
		System.out.println("투숙객 성명을 입력해주세요.");
		System.out.println("이름 입력 => ");
		String name = scan.next();
		
		hotelManage.put(roomNum, new Hotel(name, roomNum));
		System.out.println(name + "님, " + roomNum + "호로 체크인이 완료되었습니다.");
	}
	
	private void checkOut() {
		System.out.println("체크아웃하려는 호실을 입력해주세요.");
		System.out.println("방 번호 입력 => ");
		
		int roomNum = scan.nextInt();
		
		if(hotelManage.remove(roomNum) == null) {
			System.out.println(roomNum + "호는 체크인한 객실이 아닙니다.");
		} else {
			System.out.println(roomNum + "호 체크아웃이 완료되었습니다.");
			System.out.println("이용해주셔서 감사합니다");			
		}
		
		
	}
	
	private void status() {
		
		Set<Integer> keySet = hotelManage.keySet();
		
		System.out.println("──────────────────────────────────────────────────────────");
		
		if(keySet.size() == 0) {
			System.out.println("체크인된 객실이 없습니다.");
		} else {
			Iterator<Integer> it = keySet.iterator();
			while(it.hasNext()) {
				int roomNum = it.next();
				Hotel h = hotelManage.get(roomNum);
				System.out.println("방번호 : " + h.getRoomNum() + "\t투숙객 : " + h.getName());
			}
		}
	}
	
	
	public static void main(String[] args) {
		new E03_Hotel().hotelStart();
	}

}

//호텔 투숙 상황을 저장할 수 있는 VO 클래스
class Hotel {
	private String name;
	private int roomNum;
	
	public Hotel(String name, int roomNum) {
		super();
		this.name = name;
		this.roomNum = roomNum;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getRoomNum() {
		return roomNum;
	}

	public void setRoomNum(int roomNum) {
		this.roomNum = roomNum;
	}

	@Override
	public String toString() {
		return "Hotel [name=" + name + ", roomNum=" + roomNum + "]";
	}
	
}