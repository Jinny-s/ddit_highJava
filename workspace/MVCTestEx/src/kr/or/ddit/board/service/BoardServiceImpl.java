package kr.or.ddit.board.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import kr.or.ddit.board.dao.IBoardDao;
import kr.or.ddit.board.VO.BoardVO;
import kr.or.ddit.board.dao.BoardDaoImpl;
import kr.or.ddit.util.JDBCUtil3;

public class BoardServiceImpl implements IBoardService{

	// 사용할 DAO의 객체변수를 선언한다.
	private IBoardDao boardDao;
	private Connection conn;
	
	public BoardServiceImpl() {
		boardDao = new BoardDaoImpl();
	}
	
	@Override
	public int insertBoard(BoardVO bv) {
		
		int cnt = 0;
		
		try {
			conn = JDBCUtil3.getConnection();
			cnt = boardDao.insertBoard(conn, bv);
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCUtil3.disConnect(conn, null, null, null);
		}
		return cnt;
	}

	@Override
	public boolean checkBoard(int no) {
		
		boolean chk = false;
		
		try {
			conn = JDBCUtil3.getConnection();
			chk = boardDao.checkBoard(conn, no);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCUtil3.disConnect(conn, null, null, null);
		}
		return chk;
	}

	@Override
	public List<BoardVO> getAllBoardList() {
		
		List<BoardVO> boardList = new ArrayList<>();
		
		try {
			conn = JDBCUtil3.getConnection();
			boardList = boardDao.getAllBoardList(conn);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCUtil3.disConnect(conn, null, null, null);
		}
		return boardList;
	}

	@Override
	public int updateBoard(BoardVO bv) {
		
		int cnt = 0;
		
		try {
			conn = JDBCUtil3.getConnection();
			cnt = boardDao.updateBoard(conn, bv);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCUtil3.disConnect(conn, null, null, null);
		}
		
		return cnt;
	}

	@Override
	public int deleteBoard(int no) {
		int cnt = 0;
		
		try {
			conn = JDBCUtil3.getConnection();
			cnt = boardDao.deleteBoard(conn, no);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCUtil3.disConnect(conn, null, null, null);
		}
		
		return cnt;
	}
	
	@Override
	public List<BoardVO> getSearchBoard(BoardVO bv) {
		
		List<BoardVO> boardList = new ArrayList<>();
		
		try {
			conn = JDBCUtil3.getConnection();
			boardList = boardDao.getSearchBoard(conn, bv);
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			JDBCUtil3.disConnect(conn, null, null, null);
		}
		return boardList;
	}

}

