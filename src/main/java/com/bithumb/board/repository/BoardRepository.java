package com.bithumb.board.repository;

import com.bithumb.board.domain.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long > {
//    Page<Board> findByWorkGroupNmContaining(final String search, final Pageable pageable);
//    @Query("select c from Comment AS c where c.boardNo = :boardNo")
//    Page<Comment> findByBoard(@Param("boardNo") long boardNo, Pageable pageable);
}
