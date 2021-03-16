package kr.or.ddit.board;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import kr.or.ddit.board.VO.BoardVO;
import kr.or.ddit.board.service.BoardServiceImpl;
import kr.or.ddit.board.service.IBoardService;
import kr.or.ddit.util.JDBCUtil3;

public class BoardMain {
	
	private IBoardService boardService;
	
	public BoardMain() {
		boardService = new BoardServiceImpl();
	}
	
	private Scanner scan = new Scanner(System.in);

	/**
	 * 첫 화면을 출력하는 메서드
	 */
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
	
	/**
	 * 프로그램 시작메서드
	 */
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
	
	
	/**
	 * 메인 메뉴 출력 메서드
	 */
	public void listMenu() {
		System.out.println("====================== 원하는 작업 선택 =======================");
		System.out.println("\t1.새글작성 2.수정 3.삭제 4.검색 5.메인");
		System.out.println("---------------------------------------------------------------");
		
		int menuNum;
		do {
			menuNum = scan.nextInt();
			switch (menuNum) {
				case 1:
					write();
					break;
				case 2:
					edit();
					break;
				case 3:
					delete();
					break;
				case 4:
					search();
					break;
				case 5:					
					break;
				default:
					System.out.println("잘못 입력하셨습니다. 1 ~ 5 사이의 번호를 입력해주세요.");
			}
		} while (menuNum != 5);
	}
	

	/**
	 * 전체 목록 출력 메서드
	 */
	public void listAll() {
		System.out.println();
		System.out.println("===============================================================");
		System.out.println("글번호\t제목\t\t작성자\t작성일\t내용");
		System.out.println("---------------------------------------------------------------");
		
		List<BoardVO> boardList = boardService.getAllBoardList();
		
		for(BoardVO bv : boardList) {
			System.out.println(bv.getNo() + "\t"
							+ bv.getTitle()+ "\t"
							+ bv.getWriter()+ "\t"
							+ bv.getDate()+ "\t" + bv.getContent());
		}
		
			System.out.println("===============================================================");
			System.out.println("전체 게시글 목록 조회 완료!");
			System.out.println();
			listMenu();
	}
	
	// 2.새글작성 메서드
	public void write() {
		System.out.println("제목 입력 (7글자 이내) >");
		String title = scan.next();
		System.out.println("작성자명 입력 (3글자 이내) >");
		String writer = scan.next();
		System.out.println("내용 입력 >");
		String content = scan.next();
		
		BoardVO bv = new BoardVO();
		bv.setTitle(title);
		bv.setWriter(writer);
		bv.setContent(content);
			
		int cnt = boardService.insertBoard(bv);
		
		if(cnt > 0) {
			System.out.println(writer + "님, 게시글이 성공적으로 업로드 되었습니다.");
		} else {
			System.out.println(writer + "님, 게시글 업로드가 실패하였습니다.");
		}
		listAll();
	}

	// 3.수정 메서드
	public void edit() {
		
		boolean chk = false;
		int no = 0;
		
		do {
			System.out.println();
			System.out.println("수정할 게시글 번호를 입력하세요 >>");
			no = scan.nextInt();
			
			chk = boardService.checkBoard(no);
			
			if(chk == false) {
				System.out.println(no + "번은 존재하지 않는 게시물입니다.");
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

		BoardVO bv = new BoardVO();
		bv.setTitle(title);
		bv.setWriter(writer);
		bv.setContent(content);
		bv.setNo(no);
		
		int cnt = boardService.updateBoard(bv);
		
		if(cnt > 0 ) {
			System.out.println(writer + "님, 게시글 수정이 완료되었습니다.");
		} else {
			System.err.println(writer + "님, 게시글 수정이 실패하였습니다.");
		}
		listAll();
	}

	// 4.삭제 메서드
	public void delete() {
		
		System.out.println();
		System.out.println("삭제할 게시글 번호를 입력하세요 >");
		int no = scan.nextInt();
		
		int cnt = boardService.deleteBoard(no);
		
		if(cnt > 0) {
			System.out.println(no + "번 게시글 삭제 성공");
		} else {
			System.out.println(no + "번 게시글 삭제 실패");
		}
		listAll();
	}

	// 5.검색 메서드
	public void search() {
		scan.nextLine();
		System.out.println("검색할 게시글 정보를 입력하세요.");
		System.out.print("게시글 제목 >> ");
		String title = scan.nextLine().trim();
		
		System.out.print("게시글 작성자 >> ");
		String writer = scan.nextLine().trim();
		
		System.out.print("게시글 내용 >> ");
		String content = scan.nextLine().trim();
		
		BoardVO bv = new BoardVO();
		bv.setTitle(title);
		bv.setWriter(writer);
		bv.setContent(content);

		List<BoardVO> boardList = boardService.getSearchBoard(bv);
		
		System.out.println();
		System.out.println("===============================================================");
		System.out.println("글번호\t제목\t\t작성자\t작성일\t내용");
		System.out.println("---------------------------------------------------------------");
		
		for(BoardVO bv2 : boardList) {
			System.out.println(bv2.getNo() + "\t"
							+ bv2.getTitle() + "\t"
							+ bv2.getWriter() + "\t"
							+ bv2.getDate() + "\t" + bv2.getContent());
		}
		System.out.println("===============================================================");
		System.out.println("게시글 검색 완료!");
		listMenu();
	}
	
	public static void main(String[] args) {
		BoardMain boardObj = new BoardMain();
		boardObj.mainMenu();
	}
}

