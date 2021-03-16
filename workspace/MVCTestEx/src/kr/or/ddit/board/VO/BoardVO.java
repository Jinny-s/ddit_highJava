package kr.or.ddit.board.VO;

/**
 * DB 테이블에 있는 컬럼을 기준으로 데이터를 객체화한 클래스이다.
 * <p>
 * DB테이블의 '컬럼'이 이 클래스의 '멤버변수'가 된다 <br>
 * DB테이블의 컬럼과 클래스의 멤버변수를 매핑하는 역할을 수행한다.<br>
 * </p>
 */
public class BoardVO {
	
	private int no;
	private String title;
	private String writer;
	private String content;
	private String date;
	
	
	
	public int getNo() {
		return no;
	}
	public void setNo(int no) {
		this.no = no;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getWriter() {
		return writer;
	}
	public void setWriter(String writer) {
		this.writer = writer;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	
	@Override
	public String toString() {
		return "BoradVO [no=" + no + ", title=" + title + ", writer=" + writer + ", content=" + content + ", date="
				+ date + "]";
	}
	
}
