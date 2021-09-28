package com.bithumb.board.board.service;

import com.bithumb.board.board.domain.Board;
import com.bithumb.board.user.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public interface BoardService {
//    List<Board> findAll();
    Optional<Board> findById(long board_no);
    Board getById(long board_no);
    Board save(Board board);
    Page<Board> findAll(Pageable pageable);
    Page<Board> findBoardByBoardCategory(String boardCategory, Pageable pageable);
    Page<Board> findBoardByUser(User user, Pageable pageable);
    boolean existsById(long id);
//    long count();
    void deleteById(long board_no);
//    void deleteAll();



}
