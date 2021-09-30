package com.bithumb.board.board.repository;

import com.bithumb.board.board.domain.Board;
import com.bithumb.board.user.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {
    Page<Board> findBoardByBoardCategory(String boardCategory, Pageable pageable);
    Page<Board> findBoardByUser(User user, Pageable pageable);
    Optional<Board> findBoardByBoardNoAndUser(long boardNo, User user);
    long deleteBoardByBoardNoAndUser(long boardNo, User user);
}
