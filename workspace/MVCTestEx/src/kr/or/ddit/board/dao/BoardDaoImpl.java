package kr.or.ddit.board.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import kr.or.ddit.board.VO.BoardVO;
import kr.or.ddit.util.JDBCUtil3;

public class BoardDaoImpl implements IBoardDao{

	private Statement stmt;
	private PreparedStatement pstmt;
	private ResultSet rs;
	
	@Override
	public int insertBoard(Connection conn, BoardVO bv) throws SQLException {
		
		int cnt = 0;
		
		try {
			
			String sql = "insert into jdbc_board (board_no, board_title, board_writer, board_date, board_content)"
					+ " values (board_seq.nextVal, ?, ?, SYSDATE,?)";
		
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, bv.getTitle());
			pstmt.setString(2, bv.getWriter());
			pstmt.setString(3, bv.getContent());
			
			cnt = pstmt.executeUpdate();
			
		} finally {
			JDBCUtil3.disConnect(null, null, pstmt, null);
		}
		return cnt;
	}

	@Override
	public boolean checkBoard(Connection conn, int no) throws SQLException {
		
		boolean chk = false;
		
		try {
			
			String sql = "select count(*) as cnt from jdbc_board where board_no = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, no);
			
			rs = pstmt.executeQuery();
			
			int cnt = 0;
			while(rs.next()) {
				cnt = rs.getInt("cnt");
			}
			
			if(cnt > 0) {
				chk = true;
			}
		} finally {
			JDBCUtil3.disConnect(null, pstmt, pstmt, rs);
		}
		return chk;
	}

	@Override
	public List<BoardVO> getAllBoardList(Connection conn) throws SQLException {
		
		List<BoardVO> boardList = new ArrayList<BoardVO>();
		
		try {
			stmt = conn.createStatement();
			
			rs = stmt.executeQuery("select * from jdbc_board");
			
			while(rs.next()) {
				BoardVO bv = new BoardVO();
				
				int no = rs.getInt("board_no");
				String title = rs.getString("board_title");
				String writer = rs.getString("board_writer");
				String date = rs.getString("board_date");
				String content = rs.getString("board_content");
				
				bv.setNo(no);
				bv.setTitle(title);
				bv.setWriter(writer);
				bv.setDate(date);
				bv.setContent(content);
				
				boardList.add(bv);
			}
		} catch (Exception e) {
			JDBCUtil3.disConnect(null, stmt, pstmt, rs);
		}
		return boardList;
	}

	@Override
	public int updateBoard(Connection conn, BoardVO bv) throws SQLException {
		
		int cnt = 0;
		
		try {
			String sql = "update jdbc_board"
					+ " set board_title = ?, board_writer = ?, board_content = ?"
					+ " where board_no = ?";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, bv.getTitle());
			pstmt.setString(2, bv.getWriter());
			pstmt.setString(3, bv.getContent());
			pstmt.setInt(4, bv.getNo());
			
			cnt = pstmt.executeUpdate();
			
		} finally {
			JDBCUtil3.disConnect(null, null, pstmt, null);
		}
		return cnt;
	}

	@Override
	public int deleteBoard(Connection conn, int no) throws SQLException {
		
		int cnt = 0;
		
		try {
			String sql = "delete from jdbc_board where board_no = ?";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, no);
			
			cnt = pstmt.executeUpdate();
			
		} finally {
			JDBCUtil3.disConnect(null, null, pstmt, null);
		}
		return cnt;
	}
	
	@Override
	public List<BoardVO> getSearchBoard(Connection conn, BoardVO bv) throws SQLException {
		
		List<BoardVO> boardList = new ArrayList<>();
		
		try {
			String sql = "select * from jdbc_board where 1=1 ";
			if(bv.getTitle() != null && !bv.getTitle().equals("")) {
				sql += " and board_title like '%' || ? || '%' ";
			}
			if(bv.getWriter() != null && !bv.getWriter().equals("")) {
				sql += " and board_writer like '%' || ? || '%' ";
			}
			if(bv.getContent() != null && !bv.getContent().equals("")) {
				sql += " and board_content like '%' || ? || '%' ";
			}
			
			pstmt = conn.prepareStatement(sql);
			
			int index = 1;
			
			if(bv.getTitle() != null && !bv.getTitle().equals("")) {
				pstmt.setString(index++, bv.getTitle());
			}
			if(bv.getWriter() != null && !bv.getWriter().equals("")) {
				pstmt.setString(index++, bv.getWriter());
			}
			if(bv.getContent() != null && !bv.getContent().equals("")) {
				pstmt.setString(index++, bv.getContent());
			}
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				BoardVO bv2 = new BoardVO();
				bv2.setNo(rs.getInt("board_no"));
				bv2.setTitle(rs.getString("board_title"));
				bv2.setWriter(rs.getString("board_writer"));
				bv2.setDate(rs.getString("board_date"));
				bv2.setContent(rs.getString("board_content"));
				
				boardList.add(bv2);
			}
			
		} finally {
			JDBCUtil3.disConnect(null, null, pstmt, rs);
		}
		return boardList;
	}
	
}
