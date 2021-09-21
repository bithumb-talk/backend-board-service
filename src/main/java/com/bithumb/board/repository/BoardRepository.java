package com.bithumb.board.repository;

import com.bithumb.board.domain.Board;
import com.bithumb.board.domain.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long > {
    Page<Board> findBoardByBoardCategory(String boardCategory, Pageable pageable);
}
