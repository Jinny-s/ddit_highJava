package kr.or.ddit.basic;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class E02_Hotel {
	private Scanner scan;
	private Map<Integer, Hotel> hotelManage;
	
	public E02_Hotel() {
		scan = new Scanner(System.in);
		hotelManage = new HashMap<Integer, Hotel>();
	}
	
	// 시작 메서드
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
	
	// 호텔 오픈
	private void open() {
		System.out.println("──────────────────────────────────────────────────────────");
		System.out.println("　　　　　　　　D　D　I　T　H　O　T　E　L");
		System.out.println("　　　　　　　　　　영업을 시작합니다.");
		System.out.println("──────────────────────────────────────────────────────────");
		
		// 입력용 스트림 객체 생성
		try {
			
			ObjectInputStream ois = 
					new ObjectInputStream(
							new BufferedInputStream(
									new FileInputStream("d:/D_Other/hotelObj.bin")));
			
			Object obj = null;
			
			while((obj = ois.readObject()) != null) {
				Hotel hotel = (Hotel) obj;
				
				// 읽어온 내용 출력
				System.out.println("방번호 : " + hotel.getRoomNum());
				System.out.println("고객이름: " + hotel.getName());
				System.out.println("----------------------------------------------------------");
				
				hotelManage.put(hotel.getRoomNum(), hotel);
			}
			ois.close();
			
		} catch (FileNotFoundException e){
			System.out.println("저장된 데이터가 없습니다.");
		} catch(EOFException ex) {
			System.out.println("데이터 불러오기 완료!");
		} catch(IOException|ClassNotFoundException ex) {
			ex.printStackTrace();
		}
	}
	
	// 호텔 클로즈
	private void close() {
		System.out.println("──────────────────────────────────────────────────────────");
		System.out.println("　　　　　　　　D　D　I　T　H　O　T　E　L");
		System.out.println("　　　　　　　　　　영업을 종료합니다.");
		System.out.println("──────────────────────────────────────────────────────────");
		System.out.println();
		
		try {
			// 출력용 스트림 객체 생성
			ObjectOutputStream oos = 
					new ObjectOutputStream(
							new BufferedOutputStream(
									new FileOutputStream("d:/D_Other/hotelObj.bin")));
			
			Collection<Hotel> values = hotelManage.values();
			for(Hotel hotel: values) {
				oos.writeObject(hotel); // 직렬화
			}
			
			oos.close();
			System.out.println("데이터 저장 완료!");
			
		} catch(IOException ex) {
			ex.printStackTrace();
		}
			
		System.exit(0);
	}
	
	// 메뉴 출력
	private void displayMenu() {
		System.out.println();
		System.out.println("──────────────────────────────────────────────────────────");
		System.out.println("　　　　　　　　어떤 업무를 하시겠습니까?");
		System.out.println("　　1.체크인　　2.체크아웃　　3.객실상태　　4.업무종료");
		System.out.println("──────────────────────────────────────────────────────────");
		System.out.println("메뉴선택 => ");
	}
	
	// 체크인
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
	
	// 체크아웃
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
	
	// 객실현황
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
		new E02_Hotel().hotelStart();
	}

}

//호텔 투숙 상황을 저장할 수 있는 VO 클래스
class Hotel implements Serializable {
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