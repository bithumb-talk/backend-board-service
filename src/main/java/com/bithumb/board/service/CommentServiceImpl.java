package com.bithumb.board.service;


import com.bithumb.board.domain.Board;
import com.bithumb.board.domain.Comment;
import com.bithumb.board.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service

public class CommentServiceImpl implements CommentService {
    @Autowired
    private final CommentRepository commentRepository;

    @Override
    public Page<Comment> findAll(Pageable pageable){
        return commentRepository.findAll(pageable);
    }
    @Override
    public Optional<Comment> findById(long comment_no){
        return commentRepository.findById(comment_no);
    }
//    @Override
//    public Page<Comment> findByBoard(Pageable pageable, long board_no){
//
//    }
    @Override
    public Comment save(Comment comment) {
        return commentRepository.save(comment);
    }

    @Override
    public Page<Comment> findCommentsByBoard(Board board, Pageable pageable){
        return commentRepository.findCommentsByBoard(board,pageable);

    }




}