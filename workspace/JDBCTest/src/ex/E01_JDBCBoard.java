package ex;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import javax.rmi.ssl.SslRMIClientSocketFactory;

import kr.or.ddit.util.JDBCUtil3;

public class E01_JDBCBoard {
	
	private Connection conn;
	private Statement stmt;
	private PreparedStatement pstmt;
	private ResultSet rs;
	
	private Scanner scan = new Scanner(System.in);

	// 시작 메서드
	// 1.전체목록 2.글쓰기 3.검색 4.종료
	public void mainMenu() {
		int menuNum;
		do {
			startMenu();
			menuNum = scan.nextInt();
			switch (menuNum) {
				case 1:
					listAll();
					break;
				case 2:
					write();
					break;
				case 3:
					search();
					break;
				case 4:
					System.out.println("프로그램을 종료합니다.");
					break;
				default:
					System.out.println("잘못 입력하셨습니다. 1 ~ 4 사이의 번호를 입력해주세요.");
			}
		} while (menuNum != 4);
	}
	
	// 시작 메뉴 출력 메서드 (수정할거)
	// 1.전체목록출력 2.새글작성 3.검색 4.종료
	public void startMenu() {
		System.out.println("---------------------------------------------------------------");
		System.out.println("====================== 원하는 작업 선택 =======================");
		System.out.println("---------------------------------------------------------------");
		System.out.println("\t\t1. 전체 목록 보기");
		System.out.println("\t\t2. 새 글 작성");
		System.out.println("\t\t3. 검색하기");
		System.out.println("\t\t4. 프로그램 종료");
		System.out.println("---------------------------------------------------------------");
	}
	
	// 목록 조회 후 메뉴 출력 메서드
	// 1.새글작성 2.수정 3.삭제 4.검색 5.메인 6.종료
	public void listMenu() {
		System.out.println("====================== 원하는 작업 선택 =======================");
		System.out.println("\t1.새글작성 2.조회 3.수정 4.삭제 5.검색 6.메인");
		System.out.println("---------------------------------------------------------------");
		
		int menuNum;
		do {
			menuNum = scan.nextInt();
			switch (menuNum) {
				case 1:
					write();
					break;
				case 2:
					read();
					break;
				case 3:
					edit();
					break;
				case 4:
					delete();
					break;
				case 5:
					search();
					break;
				case 6:					
					break;
				default:
					System.out.println("잘못 입력하셨습니다. 1 ~ 6 사이의 번호를 입력해주세요.");
			}
		} while (menuNum != 6);
	}
	

	// 1.전체목록출력 메서드
	public void listAll() {
		System.out.println();
		System.out.println("===============================================================");
		System.out.println("글번호\t제목\t\t작성자\t작성일");
		System.out.println("---------------------------------------------------------------");
		
		try {
			conn = JDBCUtil3.getConnection();
			
			stmt = conn.createStatement();
			
			rs = stmt.executeQuery("select board_no, board_title,"
								+ " board_writer, board_date from jdbc_board");
		
			while(rs.next()) {
				int num = rs.getInt("board_no");
				String title = rs.getString("board_title");
				String writer = rs.getString("board_writer");
				String date = rs.getString("board_date");
				System.out.println(num + "\t" + title + "\t" + writer + "\t" + date);
			}
			System.out.println("===============================================================");
			System.out.println("전체 게시글 목록 조회 완료!");
			System.out.println();
			System.out.println();
			listMenu();
			
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			JDBCUtil3.disConnect(conn, stmt, pstmt, rs);
		}
	}
	
	// 개별 게시글 조회 메서드
	public void read() {
		boolean chk = false;
		int num = 0;
		
		do {
			System.out.println();
			System.out.println("조회할 게시글 번호를 입력하세요 >>");
			num = scan.nextInt();
			
			chk = checkNum(num);
			
			if(chk == false) {
				System.out.println(num + "번은 존재하지 않는 게시물입니다.");
				System.out.println("다시 입력해주세요.");
			}
		} while (chk == false);
		
		try {
			conn = JDBCUtil3.getConnection();
			
			String sql = "select * from jdbc_board where board_no = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, num);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				int numb = rs.getInt("board_no");
				String title = rs.getString("board_title");
				String writer = rs.getString("board_writer");
				String date = rs.getString("board_date");
				String content = rs.getString("board_content");
				
				
				System.out.println("글번호 : " + numb + "\t\t제목 : " + title);
				System.out.println("작성자 : " + writer + "\t\t작성일 : " + date);
				System.out.println("---------------------------------------------------------------");
				System.out.println("글 내용 :");
				System.out.println();
				System.out.println(content);
				System.out.println("---------------------------------------------------------------");
				System.out.println("1.목록으로\t2.수정\t3.삭제");
				int input = scan.nextInt();
				switch(input){
				case 1 : listAll(); break;
				case 2 : edit(); break;
				case 3 : delete(); break;
				}
			}
			
		} catch (SQLException ex) {
				ex.printStackTrace();
		} finally {
			JDBCUtil3.disConnect(conn, stmt, pstmt, rs);
		}
		
	}

	// 2.새글작성 메서드
	public void write() {
		System.out.println("제목 입력 (7글자 이내) >");
		String title = scan.next();
		System.out.println("작성자명 입력 (3글자 이내) >");
		String writer = scan.next();
		System.out.println("내용 입력 >");
		String content = scan.next();
		
		try {
			conn = JDBCUtil3.getConnection();
			
			String sql = "insert into jdbc_board (board_no, board_title, board_writer, board_date, board_content)"
						+ " values (board_seq.nextVal, ?, ?, SYSDATE,?)";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, title);
			pstmt.setString(2, writer);
			pstmt.setString(3, content);
			
			int cnt = pstmt.executeUpdate();
			
			if(cnt > 0) {
				System.out.println(writer + "님, 게시글이 성공적으로 업로드 되었습니다.");
			} else {
				System.out.println(writer + "님, 게시글 업로드가 실패하였습니다.");
			}
			
			listAll();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JDBCUtil3.disConnect(conn, stmt, pstmt, rs);
		}
	}

	// 3.수정 메서드
	public void edit() {
		
		boolean chk = false;
		int num = 0;
		
		do {
			System.out.println();
			System.out.println("수정할 게시글 번호를 입력하세요 >>");
			num = scan.nextInt();
			
			chk = checkNum(num);
			
			if(chk == false) {
				System.out.println(num + "번은 존재하지 않는 게시물입니다.");
				System.out.println("다시 입력해주세요.");
			}
		} while (chk == false);
		
		System.out.println("------------------------- 게시글 수정 -------------------------");
		System.out.println("제목 입력 (7글자 이내) >");
		String title = scan.next();
		System.out.println("작성자명 입력 (3글자 이내) >");
		String writer = scan.next();
		System.out.println("내용 입력 >");
		String content = scan.next();
		
		try {
			conn = JDBCUtil3.getConnection();
			
			String sql = "update jdbc_board"
					+ " set board_title = ?, board_writer = ?, board_content = ?"
					+ " where board_no = ?";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, title);
			pstmt.setString(2, writer);
			pstmt.setString(3, content);
			pstmt.setInt(4, num);
			
			int cnt = pstmt.executeUpdate();
			
			if(cnt > 0 ) {
				System.out.println(writer + "님, 게시글 수정이 완료되었습니다.");
			} else {
				System.err.println(writer + "님, 게시글 수정이 실패하였습니다.");
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			JDBCUtil3.disConnect(conn, stmt, pstmt, rs);
		}
	}

	// 4.삭제 메서드
	public void delete() {
		
		System.out.println();
		System.out.println("삭제할 게시글 번호를 입력하세요 >");
		int num = scan.nextInt();
		
		try {
			conn = JDBCUtil3.getConnection();
			
			String sql = "delete from jdbc_board where board_no = ?";
				
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, num);
			
			int cnt = pstmt.executeUpdate();
			
			if(cnt > 0) {
				System.out.println(num + "번 게시글 삭제 성공");
			} else {
				System.out.println(num + "번 게시글 삭제 실패");
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			JDBCUtil3.disConnect(conn, stmt, pstmt, rs);
		}
		
	}

	// 5.검색 메서드 (아직안함!!!!!!!!!!)
	public void search() {
		System.out.println();
		System.out.println("검색할 게시글 제목을 입력하세요 >>");
		String search = scan.next();
		
		try {
			conn = JDBCUtil3.getConnection();
			
			String sql = "select board_no, board_title, board_writer, board_date"
						+ " from jdbc_board"
						+ " where board_title like '%' || ? || '%'";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, search);
			
			rs = pstmt.executeQuery();
			
			System.out.println();
			System.out.println("===============================================================");
			System.out.println("글번호\t제목\t\t작성자\t작성일");
			System.out.println("---------------------------------------------------------------");
			
			while(rs.next()) {
				int num = rs.getInt("board_no");
				String title = rs.getString("board_title");
				String writer = rs.getString("board_writer");
				String date = rs.getString("board_date");
				
				System.out.println(num + "\t" + title + "\t" + writer + "\t" + date);
			}
			System.out.println("===============================================================");
			System.out.println("게시글 검색 완료!");
			listMenu();
			
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			JDBCUtil3.disConnect(conn, stmt, pstmt, rs);
		}
	}

	// 게시글 번호로 게시글 유무 체크
	private boolean checkNum(int num) {
		
		boolean chk = false;
		
		try {
			conn = JDBCUtil3.getConnection();
			
			String sql = "select count(*) as cnt from jdbc_board where board_no = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, num);
			
			rs = pstmt.executeQuery();
			
			int cnt = 0;
			while(rs.next()) {
				cnt = rs.getInt("cnt");
			}
			
			if(cnt > 0) {
				chk = true;
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			JDBCUtil3.disConnect(conn, stmt, pstmt, rs);
		}
		
		return chk;
		
	}
	
	public static void main(String[] args) {
		E01_JDBCBoard boardObj = new E01_JDBCBoard();
		boardObj.mainMenu();
	}
}






















