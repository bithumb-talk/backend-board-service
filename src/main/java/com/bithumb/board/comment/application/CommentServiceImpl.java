package com.bithumb.board.comment.application;


import com.bithumb.board.board.application.BoardService;
import com.bithumb.board.board.domain.Board;
import com.bithumb.board.board.repository.BoardRepository;
import com.bithumb.board.comment.api.dto.RequestCommentDto;
import com.bithumb.board.comment.api.dto.ResponseCommentDto;
import com.bithumb.board.comment.domain.Comment;
import com.bithumb.board.comment.repository.CommentRepository;
import com.bithumb.board.common.response.ErrorCode;
import com.bithumb.board.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@RequiredArgsConstructor
@Service

public class CommentServiceImpl implements CommentService {
    @Autowired
    private final CommentRepository commentRepository;
    @Autowired
    private final BoardRepository boardRepository;

    @Override
    public Page<Comment> commentsListAll(Pageable pageable){
        return commentRepository.findAll(pageable);
    }
    @Override
    public Optional<Comment> findById(long comment_no){
        return commentRepository.findById(comment_no);
    }


    @Override
    public ResponseCommentDto createComment(RequestCommentDto requestCommentDto, long boardNo) {
        requestCommentDto.setCommentCreateDate();
        Comment comment = requestCommentDto.toEntity();
        Board board = boardRepository.findById(boardNo).orElseThrow(()-> new NullPointerException(ErrorCode.BOARD_NOT_EXIST.getMessage()));
        comment.changeBoard(board);
        return ResponseCommentDto.of(commentRepository.save(comment));
    }

    @Override
    public Page<Comment> findCommentsByBoard(long boardNo, Pageable pageable){
        Board board = boardRepository.findById(boardNo).orElseThrow(() -> new NullPointerException(ErrorCode.BOARD_NOT_EXIST.getMessage()));
        return commentRepository.findCommentsByBoard(board,pageable);
    }

    @Override
    public ResponseCommentDto updateComment(RequestCommentDto requestCommentDto , long boardNo, long commentNo){
        Board board = boardRepository.findById(boardNo).orElseThrow(() -> new NullPointerException(ErrorCode.BOARD_NOT_EXIST.getMessage()));
        Comment comment = commentRepository.findCommentByBoardAndCommentNo(board, commentNo);
        comment.changeComment(requestCommentDto.getNickname(), requestCommentDto.getCommentContent(),LocalDateTime.now().withNano(0));
        comment.changeBoard(board);
        return ResponseCommentDto.of(commentRepository.save(comment));
    }

    @Override
    public void deleteComment(long comment_no){
        commentRepository.deleteById(comment_no);
    }
}
