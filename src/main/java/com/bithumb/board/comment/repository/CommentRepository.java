package com.bithumb.board.comment.repository;

import com.bithumb.board.board.domain.Board;
import com.bithumb.board.comment.domain.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CommentRepository extends JpaRepository<Comment, Long > {

    Page<Comment> findCommentsByBoard(Board board, Pageable pageable);

}
