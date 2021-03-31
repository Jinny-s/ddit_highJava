package ex;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import kr.or.ddit.util.JDBCUtil3;

public class E02_JDBCHotel {
	
	private Connection conn;
	private Statement stmt;
	private PreparedStatement pstmt;
	private ResultSet rs;
	
	private Scanner scan = new Scanner(System.in);
	
	
	// 시작 메서드
	// 1.체크인 2.체크아웃 3.객실현황 4.클로즈
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
	
	// 체크인
	private void checkIn() {
		System.out.println("어느 방에 체크인 하시겠습니까?");
		System.out.println("방 번호 입력 => ");
		int roomNum = scan.nextInt();
		
		if(checkRoom(roomNum)) {
			System.out.println(roomNum + "호는 이미 체크인된 객실입니다.");
			return;
		}
		
		System.out.println("투숙객 성명을 입력해주세요.");
		System.out.println("이름 입력 => ");
		String guestName = scan.next();
		
		try {
			conn = JDBCUtil3.getConnection();
			
			String sql = "insert into hotel_mng (room_num, guest_name)"
						+ " values (?, ?)";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, roomNum);
			pstmt.setString(2, guestName);
			
			int cnt = pstmt.executeUpdate();
			
			if(cnt > 0) {
				System.out.println(guestName + "님, " + roomNum + "호로 체크인이 완료되었습니다.");
			} else {
				System.out.println(guestName + "님, 체크인 중 오류가 발생하였습니다.");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JDBCUtil3.disConnect(conn, stmt, pstmt, rs);
		}
		
	}
	
	// 체크아웃
	private void checkOut() {
		System.out.println("체크아웃하려는 호실을 입력해주세요.");
		System.out.println("방 번호 입력 => ");
		int roomNum = scan.nextInt();
		
		try {
			conn = JDBCUtil3.getConnection();
			
			String sql = "delete from hotel_mng where room_num = ?";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, roomNum);
			
			int cnt = pstmt.executeUpdate();
			
			if(cnt > 0) {
				System.out.println(roomNum + "호 체크아웃이 완료되었습니다.");
				System.out.println("이용해주셔서 감사합니다");			
			} else {
				System.out.println(roomNum + "호는 체크인한 객실이 아닙니다.");
			} 
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			JDBCUtil3.disConnect(conn, stmt, pstmt, rs);
		}
	}
	
	// 객실현황(전체조회)
	private void status() {
		
		try {
			conn = JDBCUtil3.getConnection();
			
			stmt = conn.createStatement();
			
			rs = stmt.executeQuery("select * from hotel_mng");
			
			System.out.println("──────────────────────────────────────────────────────────");
			while(rs.next()) {
				int roomNum = rs.getInt("room_num");
				String guestName = rs.getString("guest_name");
				System.out.println("방번호 : " + roomNum + "\t투숙객 : " + guestName);
			}
		} catch (SQLException e) {
			e.printStackTrace();
 		} finally {
			JDBCUtil3.disConnect(conn, stmt, pstmt, rs);
		}
	}
	
	//객실번호로 객실 할당 여부 체크
	private boolean checkRoom(int roomNum) {
		
		boolean chk = false;
		
		try {
			conn = JDBCUtil3.getConnection();
			
			String sql = "select count(*) as cnt from hotel_mng where room_num = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, roomNum);
			
			rs = pstmt.executeQuery();
			
			int cnt = 0;
			while(rs.next()) {
				cnt = rs.getInt("cnt");
			}
			
			if(cnt > 0) {
				chk = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCUtil3.disConnect(conn, stmt, pstmt, rs);
		}
		return chk;
	}
		
	public static void main(String[] args) {
		E02_JDBCHotel hotelObj = new E02_JDBCHotel();
		hotelObj.hotelStart();
	}
}
