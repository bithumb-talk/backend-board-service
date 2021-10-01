package com.bithumb.board.comment.api;


import com.bithumb.board.board.api.dto.ResponseBoardDto;
import com.bithumb.board.comment.api.dto.RequestCommentDto;
import com.bithumb.board.comment.api.dto.ResponseCommentDto;
import com.bithumb.board.comment.assembler.CommentAssembler;
import com.bithumb.board.comment.domain.Comment;
import com.bithumb.board.comment.domain.CommentModel;
import com.bithumb.board.common.response.ApiResponse;
import com.bithumb.board.common.response.StatusCode;
import com.bithumb.board.common.response.SuccessCode;
import com.bithumb.board.board.application.BoardService;
import com.bithumb.board.comment.application.CommentService;
import io.swagger.annotations.Api;
import io.swagger.models.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.Optional;

@Api(tags = {"Comment"})
@RestController
@RequiredArgsConstructor
@CrossOrigin(origins="*", allowCredentials = "false")
public class CommentController {
    private final CommentService commentService;
    private final BoardService boardService;

    @Autowired
    CommentAssembler commentAssembler;
    @Autowired
    PagedResourcesAssembler<Comment> pagedResourcesAssembler;

    @GetMapping("/comments")
    public Page<Comment> commentsListAll(@PageableDefault final Pageable pageable) {
        Page<Comment> comment = commentService.commentsListAll(pageable);
        return comment;
    }

    /* 게시글 기반 댓글 리스트 조회 */
    @GetMapping("/boards/{board-no}/comments")
    public ResponseEntity retrieveCommentsList(@PathVariable(value ="board-no") long boardNo) {
        Pageable paging = PageRequest.of(0, 10, Sort.Direction.DESC,"commentCreatedDate");
        Page<Comment> comment = commentService.findCommentsByBoard(boardNo,paging);
        PagedModel<CommentModel> collModel = pagedResourcesAssembler.toModel(comment,commentAssembler);
        ApiResponse apiResponse = ApiResponse.responseData(StatusCode.SUCCESS, SuccessCode.COMMENT_FIND_SUCCESS.getMessage(),collModel);
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    /* 댓글 등록 */
    @PostMapping("/boards/{board-no}/comments")
    public ResponseEntity createComment(@Valid @RequestBody RequestCommentDto requestCommentDto, @PathVariable(value ="board-no") long boardNo){
        ResponseCommentDto responseCommentDto = commentService.createComment(requestCommentDto, boardNo);
        ApiResponse apiResponse = ApiResponse.responseData(StatusCode.SUCCESS, SuccessCode.COMMENT_REGISTER_SUCCESS.getMessage(),responseCommentDto);
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    /* 댓글 수정 */
    @PutMapping("/boards/{board-no}/comments/{comment-no}")
    public ResponseEntity updateComment(@Valid @RequestBody RequestCommentDto requestCommentDto, @PathVariable(value= "board-no") long boardNo ,@PathVariable(value ="comment-no") long commentNo){
        ResponseCommentDto responseCommentDto = commentService.updateComment(requestCommentDto, boardNo, commentNo);
        ApiResponse apiResponse = ApiResponse.responseData(StatusCode.SUCCESS, SuccessCode.COMMENT_UPDATE_SUCCESS.getMessage(),responseCommentDto);
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    /* 댓글 삭제 */
    @DeleteMapping("/boards/{board-no}/comments/{comment-no}")
    public ResponseEntity deleteComment(@PathVariable(value = "board-no") long boardNo,@PathVariable(value ="comment-no") long commentNo) {
        commentService.deleteComment(commentNo);
        ApiResponse apiResponse = ApiResponse.responseData(StatusCode.SUCCESS, SuccessCode.COMMENT_DELETE_SUCCESS.getMessage(),null);
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }
}
