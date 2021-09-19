package com.bithumb.board.service;
import com.bithumb.board.domain.Board;
import com.bithumb.board.domain.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public interface CommentService {
    Page<Comment> findAll(Pageable pageable);
    Optional<Comment> findById(long comment_no);
//    Page<Comment> findByBoard(Pageable pageable, long board_no);

//    Page<Comment> findCommentsByBoard(long board_no, Pageable pageable);
    Page<Comment> findCommentsByBoard(Board board, Pageable pageable );
    Comment save(Comment comment);

}
