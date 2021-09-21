package com.bithumb.board.service;

import com.bithumb.board.domain.Board;
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
    boolean existsById(long id);
//    long count();
    void deleteById(long board_no);
//    void deleteAll();



}
