package com.bithumb.board.board.api;


import com.bithumb.board.board.api.dto.RequestCountDto;
import com.bithumb.board.board.api.dto.ResponseCountDto;
import com.bithumb.board.board.api.dto.RequestBoardDto;
import com.bithumb.board.board.api.dto.ResponseBoardDto;
import com.bithumb.board.board.application.S3Service;
import com.bithumb.board.comment.api.CommentController;
import com.bithumb.board.common.response.ApiResponse;
import com.bithumb.board.common.response.StatusCode;
import com.bithumb.board.common.response.SuccessCode;
import com.bithumb.board.board.application.BoardService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Api(tags = {"Board API"})
@RestController
@RequiredArgsConstructor
@CrossOrigin(origins="*", allowCredentials = "false")
@Slf4j
public class BoardController {
    private final BoardService boardService;
    private final S3Service s3Service;
    private static final Logger LOGGER = LoggerFactory.getLogger(BoardController.class);

    /* 게시글 조회 */
    @ApiOperation(value=" 게시글 조회", notes=" 단일 게시글 조회, comments로 응답되는 링크는 해당 게시글에 작성된 댓글리스트를 조회합니다. " +
            "\n /boards/{board-no}" +
            "\n comments로 조회되는거는 게시글에 달린 댓글들 조회할 수 있는 링크입니다 어떻게 쓰실지 몰라서 일단 달아놨어요")
    @GetMapping("/boards/{board-no}")
    public ResponseEntity retrieveBoard(
            @ApiParam(value = "boardNo", required = true, example = "12")
            @PathVariable(value ="board-no") long boardNo){
        ResponseBoardDto responseBoardDto = boardService.retrieveBoard(boardNo);


        //if(LOGGER.isDebugEnabled()) {
        // 로그레벨 조정하기
        //LOGGER.debug("조회한 게시물 넘버 {}.", boardNo);
        //    LOGGER.info("조회한 게시물 넘버 {}.", boardNo);
        //}

        EntityModel model =EntityModel.of(responseBoardDto)
                .add(WebMvcLinkBuilder.linkTo(methodOn(CommentController.class)
                        .retrieveCommentsList(boardNo)).withRel("comments"));
        ApiResponse apiResponse = ApiResponse.responseData(StatusCode.SUCCESS, SuccessCode.BOARD_FIND_SUCCESS.getMessage(),model);
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    /* 게시글 추천 */
    @ApiOperation(value="게시글 좋아요", notes="게시글 좋아요 버튼" +
            "\n 게시글id(게시글 넘버), 현재 좋아요 갯수 응답")
    @PostMapping("/boards/{board-no}/recommend")
    public ResponseEntity countingBoardRecommend(
            @ApiParam(value = "boardNo", required = true, example = "1")
            @PathVariable(value="board-no") long boardNo,
            @Valid @RequestBody RequestCountDto recommend) {
        ResponseCountDto countDto = boardService.updateRecommend(boardNo, recommend);
        ApiResponse apiResponse = ApiResponse.responseData(StatusCode.SUCCESS, SuccessCode.BOARD_RECOMMEND_SUCCESS.getMessage(),countDto);
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    /* 게시글 등록 */
    @ApiOperation(value=" 게시글 등록", notes=" 게시글 등록" +
            "/boards/{user-no}" +
            "\n 현재 로그인된 유저넘버")
    @ApiImplicitParam(
            name = "user-no"
            , value = "유저 넘버"
            , required = true
            , dataType = "long"
            , defaultValue = "None")
    @PostMapping("/boards/{user-no}")                                      // 유저 정보 확인 o
    public ResponseEntity createBoard(
            @Valid @RequestBody RequestBoardDto dto,
            @ApiParam(value = "userNo", required = true, example = "1")
            @PathVariable(value ="user-no") long userNo){
        ResponseBoardDto responseBoardDto = boardService.createBoard(dto, userNo);

        EntityModel model = EntityModel.of(responseBoardDto)
                .add(WebMvcLinkBuilder.linkTo(methodOn(BoardController.class)
                        .retrieveBoard(responseBoardDto.getBoardNo())).withRel("board"));

        ApiResponse apiResponse = ApiResponse.responseData(StatusCode.SUCCESS,
                SuccessCode.BOARD_REGISTER_SUCCESS.getMessage(),model);
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    /* 게시물 수정 */
    @ApiOperation(value="게시글 수정", notes="게시글 수정" +
            "\n /boards/{board-no}/{user-no}" +
            "\n 보드넘버, 현재 로그인된 유저넘버" +
            "\n 수정할때 이미지는 추가된이미지만 들어오는게 아니라 게시글에 있는 이미지 url 전부 들어와야합니다!")
    @PutMapping("/boards/{board-no}/{user-no}")                           // 유저 정보 확인 o
    public ResponseEntity updateBoard(@Valid @RequestBody RequestBoardDto dto,
                                      @ApiParam(value = "boardNo", required = true, example = "1")
                                      @PathVariable(value ="board-no") long boardNo,
                                      @ApiParam(value = "userNo", required = true, example = "1")
                                      @PathVariable(value ="user-no") long userNo){
        ResponseBoardDto responseBoardDto = boardService.updateBoard(dto, boardNo,userNo);
        ApiResponse apiResponse = ApiResponse.responseData(StatusCode.SUCCESS, SuccessCode.BOARD_UPDATE_SUCCESS.getMessage(),responseBoardDto);
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    /* 게시물 삭제 */
    @ApiOperation(value=" 게시글 삭제 ", notes="게시글 삭제" +
            "\n /boards/{board-no}/{user-no}" +
            "\n 게시글 넘버, 현재 로그인된 유저넘버")
    @DeleteMapping("/boards/{board-no}/{user-no}")                        // 유저 정보 확인 o
    public ResponseEntity deleteBoard(
            @ApiParam(value = "boardNo", required = true, example = "1")
            @PathVariable(value ="board-no") long boardNo,
            @ApiParam(value = "userNo", required = true, example = "1")
                                      @PathVariable(value ="user-no") long userNo) {
        s3Service.deleteObejct(userNo, boardNo);
        ApiResponse apiResponse = ApiResponse.responseData(StatusCode.SUCCESS, SuccessCode.BOARD_DELETE_SUCCESS.getMessage(),boardService.deleteBoard(boardNo,userNo));
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }
}
