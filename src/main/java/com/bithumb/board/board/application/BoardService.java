package com.bithumb.board.board.application;

import com.bithumb.board.board.api.dto.*;
import com.bithumb.board.board.domain.Board;
import com.bithumb.board.user.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface BoardService {
    /* 게시판 전체 조회 */
    Page<Board> BoardsListAll(Pageable pageable);

    /* 유저로 전체 조회  */
    Page<Board> findBoardByUser(User user, Pageable pageable);

    /* 카테고리로 전체 조회 */
    Page<Board> findBoardByBoardCategory(String boardCategory, Pageable pageable);

    /* 베스트 인기글 4개 조회 */
    List<Board> boardsRanking();

    /* 마이페이지 게시글 좋아요 기록 게시글 리스트 조회 */
    ResponseLikeDto pagingCustomBoardList(RequestLikeDto requestLikeDto, long page);


    /* 게시글 조회 */
    ResponseBoardDto retrieveBoard(long boardNo);

    /* 게시글 추천 */
    ResponseCountDto updateRecommend(long boardNo, RequestCountDto recommend);

    /* 게시글 등록 */
    ResponseBoardDto createBoard(RequestBoardDto boardRequestDto, long userNo);

    /* 게시글 수정 */
    ResponseBoardDto updateBoard(RequestBoardDto boardRequestDto, long userNo, long boardNo);

    /* 게시글 삭제 */
    long deleteBoard(long boardNo, long userNo);


    boolean existsById(long id);
    void deleteById(long board_no);
}
