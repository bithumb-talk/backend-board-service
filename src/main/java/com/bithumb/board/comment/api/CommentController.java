package com.bithumb.board.comment.api;


import com.bithumb.board.board.api.BoardController;
import com.bithumb.board.board.api.dto.RequestLikeDto;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public class CommentController {
    private static final Logger LOGGER = LoggerFactory.getLogger(CommentController.class);
    private final CommentService commentService;

    @Autowired
    CommentAssembler commentAssembler;
    @Autowired
    PagedResourcesAssembler<Comment> pagedResourcesAssembler;

    /* ????????? ?????? ?????? ????????? ?????? */
    @ApiOperation(value=" ???????????? ????????? ?????? ????????? ??????", notes=" ????????? ????????? ??????" +
            "\n ????????? ????????? ???????????? API" +
            "\n /boards/{board-no}/comments" +
            "\n ??????????????? ??????")
    @GetMapping("/boards/{board-no}/comments")
    public ResponseEntity retrieveCommentsList(
            @ApiParam(value = "boardNo", required = true, example = "1")
            @PathVariable(value = "board-no") long boardNo) {
        Pageable paging = PageRequest.of(0, 10, Sort.Direction.ASC, "commentCreatedDate");
        Page<Comment> comment = commentService.findCommentsByBoard(boardNo, paging);
        PagedModel<CommentModel> collModel = pagedResourcesAssembler.toModel(comment, commentAssembler);
        ApiResponse apiResponse = ApiResponse.responseData(StatusCode.SUCCESS, SuccessCode.COMMENT_FIND_SUCCESS.getMessage(), collModel);
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    /* ?????? ?????? */
    @ApiOperation(value=" ?????? ?????? ", notes=" ?????? ?????? " +
            "{\n" +
            "  \"commentContent\": \"?????? post test1\",\n" +
            "  \"nickname\": \"user1\"\n" +
            "\n}" +
            "=> commentContent, nickname??? ???????????????, commentCreateDate??? ?????? ???????????? ???????????? ??????" +
            "\n commentModifyDate??? Put?????? ?????? ???????????? ???????????? ??????" +
            "\n Parameters?????? ???????????? commentContend, nickname??? ????????? ???????????? ????????? default?????? ????????????.")
    @PostMapping("/boards/{board-no}/comments/auth")//
    public ResponseEntity createComment(@Valid @RequestBody RequestCommentDto requestCommentDto,
                                        @ApiParam(value = "boardNo", required = true, example = "1")
                                        @PathVariable(value = "board-no") long boardNo) {
        ResponseCommentDto responseCommentDto = commentService.createComment(requestCommentDto, boardNo);
        ApiResponse apiResponse = ApiResponse.responseData(StatusCode.SUCCESS, SuccessCode.COMMENT_REGISTER_SUCCESS.getMessage(), responseCommentDto);
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    /* ?????? ?????? */
    @ApiOperation(value=" ?????? ?????? ", notes=" ?????? ?????? " +
            "\n ?????????????????? ?????? ????????? ????????????.")
    @PutMapping("/boards/{board-no}/comments/{comment-no}/auth")//
    public ResponseEntity updateComment(@Valid @RequestBody RequestCommentDto requestCommentDto,
                                        @ApiParam(value = "boardNo", required = true, example = "1")
                                        @PathVariable(value = "board-no") long boardNo,
                                        @ApiParam(value = "commentNo", required = true, example = "1")
                                        @PathVariable(value = "comment-no") long commentNo) {
        ResponseCommentDto responseCommentDto = commentService.updateComment(requestCommentDto, boardNo, commentNo);
        ApiResponse apiResponse = ApiResponse.responseData(StatusCode.SUCCESS, SuccessCode.COMMENT_UPDATE_SUCCESS.getMessage(), responseCommentDto);
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    /* ?????? ?????? */
    @ApiOperation(value=" ?????? ?????? ", notes=" ?????? ?????? ")
    @DeleteMapping("/boards/{board-no}/comments/{comment-no}/auth") //
    public ResponseEntity deleteComment(
            @ApiParam(value = "boardNo", required = true)
            @PathVariable(value = "board-no") long boardNo,
            @ApiParam(value = "commentNo", required = true, example = "1")
            @PathVariable(value = "comment-no") long commentNo) {
        commentService.deleteComment(commentNo);
        ApiResponse apiResponse = ApiResponse.responseData(StatusCode.SUCCESS, SuccessCode.COMMENT_DELETE_SUCCESS.getMessage(), null);
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    /* ?????? ?????? */
    @ApiOperation(value=" ?????? ?????? ", notes=" ?????? ?????? " +
            "\n ???????????? API????????? ????????????" +
            "\n ????????? ?????? ????????? ???????????????(commentNo), ????????? ?????????????????? ??????????????? ??????" +
            "\n body?????? recommend => true ?????????, flase ????????? ?????? " +
            "\n boardNo??? API ????????? ????????? ????????????, ?????? ???????????? ?????? ?????? ???????????? ??????????????????! ")
    @PostMapping("/boards/{board-no}/comments/{comment-no}/recommend/auth") //
    public ResponseEntity commentRecommend(
            @ApiParam(value = "boardNo", required = true)
            @PathVariable(value = "board-no") long boardNo,
            @ApiParam(value = "commentNo", required = true)
            @PathVariable(value = "comment-no") long commentNo,
            @ApiParam(value = "????????? ??????", required = true)
            @Valid @RequestBody RequestCountDto recommend) {
        ResponseCountDto countDto = commentService.updateRecommend(commentNo, recommend);
        ApiResponse apiResponse = ApiResponse.responseData(StatusCode.SUCCESS, SuccessCode.COMMENT_RECOMMEND_SUCCESS.getMessage(), countDto);
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }
}
