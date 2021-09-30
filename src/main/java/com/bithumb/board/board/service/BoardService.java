package com.bithumb.board.board.service;

import com.bithumb.board.board.controller.dto.CountDto;
import com.bithumb.board.board.controller.dto.RequestBoardDto;
import com.bithumb.board.board.controller.dto.ResponseBoardDto;
import com.bithumb.board.board.domain.Board;
import com.bithumb.board.user.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
public interface BoardService {
    /* 게시글 조회 */
    ResponseBoardDto retrieveBoard(long boardNo);

    /* 게시글 추천 */
    CountDto updateRecommend(long boardNo);

    /* 게시글 등록 */
    ResponseBoardDto createBoard(RequestBoardDto boardRequestDto, long userNo);

    /* 게시글 수정 */
    ResponseBoardDto updateBoard(RequestBoardDto boardRequestDto, long userNo, long boardNo);



    Board getById(long board_no);
    Page<Board> findAll(Pageable pageable);
    Page<Board> findBoardByBoardCategory(String boardCategory, Pageable pageable);
    Page<Board> findBoardByUser(User user, Pageable pageable);
    boolean existsById(long id);
//    long count();
    void deleteById(long board_no);
//    void deleteAll();



}
