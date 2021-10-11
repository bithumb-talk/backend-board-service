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
    @ApiOperation(value=" 댓글 등록 ", notes=" 댓글 등록 " +
            "{\n" +
            "  \"commentContent\": \"댓글 post test1\",\n" +
            "  \"nickname\": \"user1\"\n" +
            "\n}" +
            "=> commentContent, nickname은 필수데이터, commentCreateDate은 댓글 등록할때 내부에서 생성" +
            "\n commentModifyDate은 Put으로 댓글 수정할때 내부에서 생성" +
            "\n Parameters에서 조회되는 commentContend, nickname을 제외한 나머지는 내부에 default값이 있습니다.")
    @PostMapping("/boards/{board-no}/comments/auth")//
    public ResponseEntity createComment(@Valid @RequestBody RequestCommentDto requestCommentDto,
                                        @ApiParam(value = "boardNo", required = true, example = "1")
                                        @PathVariable(value = "board-no") long boardNo) {
        ResponseCommentDto responseCommentDto = commentService.createComment(requestCommentDto, boardNo);
        ApiResponse apiResponse = ApiResponse.responseData(StatusCode.SUCCESS, SuccessCode.COMMENT_REGISTER_SUCCESS.getMessage(), responseCommentDto);
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    /* 댓글 수정 */
    @ApiOperation(value=" 댓글 수정 ", notes=" 댓글 수정 " +
            "\n 필수데이터는 댓글 등록과 같습니다.")
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

    /* 댓글 삭제 */
    @ApiOperation(value=" 댓글 삭제 ", notes=" 댓글 삭제 ")
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

    /* 댓글 추천 */
    @ApiOperation(value=" 댓글 추천 ", notes=" 댓글 추천 " +
            "\n 백에서는 API받으면 카운팅만" +
            "\n 좋아요 버튼 누르면 댓글아이디(commentNo), 좋아요 몇개받았는지 카운팅갯수 응답" +
            "\n body에는 recommend => true 좋아요, flase 좋아요 취소 " +
            "\n boardNo는 API 통일성 때문에 추가했고, 현재 보드넘버 또는 어떤 숫자라도 넣어야합니다! ")
    @PostMapping("/boards/{board-no}/comments/{comment-no}/recommend/auth") //
    public ResponseEntity commentRecommend(
            @ApiParam(value = "boardNo", required = true)
            @PathVariable(value = "board-no") long boardNo,
            @ApiParam(value = "commentNo", required = true)
            @PathVariable(value = "comment-no") long commentNo,
            @ApiParam(value = "좋아요 여부", required = true)
            @Valid @RequestBody RequestCountDto recommend) {
        ResponseCountDto countDto = commentService.updateRecommend(commentNo, recommend);
        ApiResponse apiResponse = ApiResponse.responseData(StatusCode.SUCCESS, SuccessCode.COMMENT_RECOMMEND_SUCCESS.getMessage(), countDto);
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }
}
