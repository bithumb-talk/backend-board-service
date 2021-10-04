package com.bithumb.board.comment.api;


import com.bithumb.board.board.domain.Board;
import com.bithumb.board.board.repository.BoardRepository;
import com.bithumb.board.comment.api.dto.RequestCountDto;
import com.bithumb.board.comment.api.dto.ResponseCountDto;
import com.bithumb.board.comment.api.dto.RequestCommentDto;
import com.bithumb.board.comment.api.dto.ResponseCommentDto;
import com.bithumb.board.comment.assembler.CommentAssembler;
import com.bithumb.board.comment.domain.Comment;
import com.bithumb.board.comment.domain.CommentModel;
import com.bithumb.board.comment.repository.CommentRepository;
import com.bithumb.board.common.response.ApiResponse;
import com.bithumb.board.common.response.ErrorCode;
import com.bithumb.board.common.response.StatusCode;
import com.bithumb.board.common.response.SuccessCode;
import com.bithumb.board.comment.application.CommentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

@Api(tags = {"Comment API"})
@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowCredentials = "false")
public class CommentController {
    private final CommentService commentService;
    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;

    @Autowired
    CommentAssembler commentAssembler;
    @Autowired
    PagedResourcesAssembler<Comment> pagedResourcesAssembler;

    /* 게시글 기반 댓글 리스트 조회 */
    @ApiOperation(value=" 게시글에 작성된 댓글 리스트 조회", notes=" 게시글 조회시 링크" +
            "\n 게시글 조회시 링크되는 API" +
            "\n /boards/{board-no}/comments" +
            "\n 보드넘버로 조회")
    @GetMapping("/boards/{board-no}/comments")
    public ResponseEntity retrieveCommentsList(
            @ApiParam(value = "boardNo", required = true, example = "1")
            @PathVariable(value = "board-no") long boardNo) {
        Pageable paging = PageRequest.of(0, 10, Sort.Direction.DESC, "commentCreatedDate");
        Page<Comment> comment = commentService.findCommentsByBoard(boardNo, paging);
        PagedModel<CommentModel> collModel = pagedResourcesAssembler.toModel(comment, commentAssembler);
        ApiResponse apiResponse = ApiResponse.responseData(StatusCode.SUCCESS, SuccessCode.COMMENT_FIND_SUCCESS.getMessage(), collModel);
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    /* 댓글 등록 */
    @ApiOperation(value=" 댓글 등록 ", notes=" 댓글 등록 ")
    @PostMapping("/boards/{board-no}/comments")
    public ResponseEntity createComment(@Valid @RequestBody RequestCommentDto requestCommentDto,
                                        @ApiParam(value = "boardNo", required = true, example = "1")
                                        @PathVariable(value = "board-no") long boardNo) {
        ResponseCommentDto responseCommentDto = commentService.createComment(requestCommentDto, boardNo);
        ApiResponse apiResponse = ApiResponse.responseData(StatusCode.SUCCESS, SuccessCode.COMMENT_REGISTER_SUCCESS.getMessage(), responseCommentDto);
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    /* 댓글 수정 */
    @ApiOperation(value=" 댓글 수정 ", notes=" 댓글 수정 ")
    @PutMapping("/boards/{board-no}/comments/{comment-no}")
    public ResponseEntity updateComment(@Valid @RequestBody RequestCommentDto requestCommentDto,
                                        @ApiParam(value = "boardNo", required = true, example = "1")
                                        @PathVariable(value = "board-no") long boardNo,
                                        @ApiParam(value = "commentNo", required = true, example = "1")
                                        @PathVariable(value = "comment-no") long commentNo) {
        ResponseCommentDto responseCommentDto = commentService.updateComment(requestCommentDto, boardNo, commentNo);
        ApiResponse apiResponse = ApiResponse.responseData(StatusCode.SUCCESS, SuccessCode.COMMENT_UPDATE_SUCCESS.getMessage(), responseCommentDto);
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    /* 댓글 삭제 */
    @ApiOperation(value=" 댓글 삭제 ", notes=" 댓글 삭제 ")
    @DeleteMapping("/boards/{board-no}/comments/{comment-no}")
    public ResponseEntity deleteComment(
            @ApiParam(value = "boardNo", required = false)
            @PathVariable(value = "board-no") long boardNo,
            @ApiParam(value = "commentNo", required = true, example = "1")
            @PathVariable(value = "comment-no") long commentNo) {
        commentService.deleteComment(commentNo);
        ApiResponse apiResponse = ApiResponse.responseData(StatusCode.SUCCESS, SuccessCode.COMMENT_DELETE_SUCCESS.getMessage(), null);
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    /* 댓글 추천 */
    @ApiOperation(value=" 댓글 추천 ", notes=" 댓글 추천 " +
            "\n 좋아요 버튼 누르면 댓글아이디(commentNo), 좋아요 몇개받았는지 카운팅갯수 응답" +
            "\n body에는 boolean타입 recommend => true 좋아요, flase 좋아요 취소 ")
    @PutMapping("/boards/{board-no}/comments/{comment-no}/recommend")
    public ResponseEntity commentRecommend(
            @ApiParam(value = "boardNo", required = true)
            @PathVariable(value = "board-no") long boardNo,
            @ApiParam(value = "commentNo", required = true)
            @PathVariable(value = "comment-no") long commentNo,
            @ApiParam(value = "좋아요 여부", required = true)
            @Valid @RequestBody RequestCountDto recommend) {
        ResponseCountDto countDto = commentService.updateRecommend(boardNo, commentNo, recommend);
        ApiResponse apiResponse = ApiResponse.responseData(StatusCode.SUCCESS, SuccessCode.COMMENT_UPDATE_SUCCESS.getMessage(), countDto);
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

}
