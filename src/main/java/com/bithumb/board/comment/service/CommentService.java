package com.bithumb.board.comment.service;
import com.bithumb.board.board.domain.Board;
import com.bithumb.board.comment.domain.Comment;
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
    void deleteById(long comment_no);

}
