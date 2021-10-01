package com.bithumb.board.comment.application;
import com.bithumb.board.board.domain.Board;
import com.bithumb.board.comment.api.dto.RequestCommentDto;
import com.bithumb.board.comment.api.dto.ResponseCommentDto;
import com.bithumb.board.comment.domain.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public interface CommentService {
    /* 게시글 기반 댓글 리스트 조회 */
    Page<Comment> findCommentsByBoard(long board_no, Pageable pageable);

    /* 댓글 등록 */
    ResponseCommentDto createComment(RequestCommentDto requestCommentDto, long boardNo);

    /* 댓글 수정 */
    ResponseCommentDto updateComment(RequestCommentDto requestCommentDto ,long boardNo, long commentNo);

    /* 댓글 삭제 */
    void deleteComment(long comment_no);

    Page<Comment> commentsListAll(Pageable pageable);
    Optional<Comment> findById(long comment_no);
//    Page<Comment> findByBoard(Pageable pageable, long board_no);
//
//    Page<Comment> findCommentByBoardAndCommentNo(Board board, long commentNo, Pageable pageable);



}
