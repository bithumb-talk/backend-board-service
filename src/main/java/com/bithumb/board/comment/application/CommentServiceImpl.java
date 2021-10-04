package com.bithumb.board.comment.application;


import com.bithumb.board.board.domain.Board;
import com.bithumb.board.board.repository.BoardRepository;
import com.bithumb.board.comment.api.dto.RequestCountDto;
import com.bithumb.board.comment.api.dto.ResponseCountDto;
import com.bithumb.board.comment.api.dto.RequestCommentDto;
import com.bithumb.board.comment.api.dto.ResponseCommentDto;
import com.bithumb.board.comment.domain.Comment;
import com.bithumb.board.comment.repository.CommentRepository;
import com.bithumb.board.common.response.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;

    @Override
    public Page<Comment> commentsListAll(Pageable pageable){
        return commentRepository.findAll(pageable);
    }

    /* 게시글 기반 댓글 리스트 조회 */
    @Override
    public Page<Comment> findCommentsByBoard(long boardNo, Pageable pageable){
        System.out.println("----");
        System.out.println(boardNo);
        System.out.println(pageable);
        Board board = boardRepository.findById(boardNo).orElseThrow(() -> new NullPointerException(ErrorCode.BOARD_NOT_EXIST.getMessage()));
        System.out.println("----");
        System.out.println(board);
        System.out.println("------");
        return commentRepository.findCommentsByBoard(board,pageable);
    }

    /* 댓글 등록 */
    @Override
    public ResponseCommentDto createComment(RequestCommentDto requestCommentDto, long boardNo) {
        requestCommentDto.setCommentCreateDate();
        Comment comment = requestCommentDto.toEntity();
        Board board = boardRepository.findById(boardNo).orElseThrow(()-> new NullPointerException(ErrorCode.BOARD_NOT_EXIST.getMessage()));
        comment.changeBoard(board);
        return ResponseCommentDto.of(commentRepository.save(comment));
    }

    /* 댓글 수정 */
    @Override
    public ResponseCommentDto updateComment(RequestCommentDto requestCommentDto , long boardNo, long commentNo){
        Board board = boardRepository.findById(boardNo).orElseThrow(() -> new NullPointerException(ErrorCode.BOARD_NOT_EXIST.getMessage()));
        Comment comment = commentRepository.findCommentByBoardAndCommentNo(board, commentNo);
        requestCommentDto.setCommentModifyDate();
        comment.changeComment(requestCommentDto.getNickname(), requestCommentDto.getCommentContent(), requestCommentDto.getCommentModifyDate());
        comment.changeBoard(board);
        return ResponseCommentDto.of(commentRepository.save(comment));
    }

    /* 댓글 삭제 */
    @Override
    public void deleteComment(long comment_no){
        commentRepository.deleteById(comment_no);
    }

    /* 댓글 추천 */
    public ResponseCountDto updateRecommend(long boardNo, long commentNo, RequestCountDto recommend){
        Comment comment = commentRepository.findById(commentNo).orElseThrow(() -> new NullPointerException(ErrorCode.COMMENT_NOT_EXIST.getMessage()));
        long countingRecommend = comment.getCommentRecommend();
        if(recommend.getCommentRecommend().equals("true"))
            comment.changeCommentRecommend(countingRecommend+1);
        else
            comment.changeCommentRecommend(Math.max(countingRecommend-1,0));
        return ResponseCountDto.from(commentRepository.save(comment));
    }
}

