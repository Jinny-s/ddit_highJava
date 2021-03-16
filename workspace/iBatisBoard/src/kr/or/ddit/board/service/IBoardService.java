package kr.or.ddit.board.service;

import java.util.List;

import kr.or.ddit.board.vo.BoardVO;

/**
 * 게시판 정보 처리를 수행하는 서비스
 *
 */
public interface IBoardService {
	
	/**
	 * 게시글 등록하는 메서드
	 * @param bv DB에 insert할 자료가 저장된 BoardVO객체
	 * @return DB작업이 성공하면 1 이상의 값이 반환되고, 실패하면 0이 반환된다. 
	 */
	public int insertBoard(BoardVO bv); 
	
	/**
	 * 주어진 게시글 번호가 존재하는지 여부를 알아내는 메서드
	 * @param boardNo 게시글 번호
	 * @return 해당 게시글 번호가 존재하면 true, 존재하지 않으면 false
	 */
	public boolean checkBoard(int boardNo);
	
	/**
	 * 전체 게시판 정보 조회 메서드
	 * @return 게시판 정보를 담고있는 List객체
	 */
	public List<BoardVO> getAllBoardList();
	
	/**
	 * 하나의 게시판 정보를 수정하는 메서드
	 * @param bv 게시판 정보 객체
	 * @return 작업성공: 1, 작업실패: 0
	 */
	public int updateBoard(BoardVO bv);
	
	/**
	 * 게시글을 삭제하는 메서드
	 * @param boardNo 삭제할 게시글 번호
	 * @return 작업성공: 1, 작업실패: 0
	 */
	public int deleteBoard(int boardNo);
	
	/**
	 * BoardVO 객체에 담긴 자료를 이용하여 게시글을 검색하는 메서드
	 * @param bv 검색할 자료가 들어있는 BoardVO 객체
	 * @return 검색된 결과를 담은 List
	 */
	public List<BoardVO> getSearchBoard(BoardVO bv);
	
}











