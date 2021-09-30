package com.bithumb.board.board.api;


import com.bithumb.board.board.assembler.BoardAssembler;
import com.bithumb.board.board.api.dto.CountDto;
import com.bithumb.board.board.api.dto.RequestBoardDto;
import com.bithumb.board.board.api.dto.ResponseBoardDto;
import com.bithumb.board.board.domain.Board;
import com.bithumb.board.board.domain.BoardModel;
import com.bithumb.board.common.response.ApiResponse;
import com.bithumb.board.common.response.StatusCode;
import com.bithumb.board.common.response.SuccessCode;
import com.bithumb.board.board.application.BoardService;
import com.bithumb.board.user.domain.User;
import com.bithumb.board.user.application.UserService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Api(tags = {"Board"})
@RestController
@RequiredArgsConstructor
@CrossOrigin(origins="*", allowCredentials = "false")
@Slf4j



public class BoardController {
    private final BoardService boardService;

    private static final Logger LOGGER = LoggerFactory.getLogger(BoardController.class);

    /* 게시글 조회 */
    @GetMapping("/boards/{board-no}")
    public ResponseEntity retrieveBoard(@PathVariable(value ="board-no") long boardNo){
        ResponseBoardDto responseBoardDto = boardService.retrieveBoard(boardNo);


        //if(LOGGER.isDebugEnabled()) {
        // 로그레벨 조정하기
        //LOGGER.debug("조회한 게시물 넘버 {}.", boardNo);
        //    LOGGER.info("조회한 게시물 넘버 {}.", boardNo);
        //}

        // 링크추가
//        EntityModel model =EntityModel.of(boardRetrieveResponseDto)
//                .add(WebMvcLinkBuilder.linkTo(methodOn(CommentController.class)
//                        .retrieveCommentsList(boardNo)).withRel("comments"));
        //응답
        ApiResponse apiResponse = ApiResponse.responseData(StatusCode.SUCCESS, SuccessCode.BOARD_FIND_SUCCESS.getMessage(),responseBoardDto);
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    /* 게시글 추천 */
    @GetMapping("/boards/{board-no}/recommend")
    public ResponseEntity countingBoardRecommend(@PathVariable(value="board-no") long boardNo) {
        CountDto countDto = boardService.updateRecommend(boardNo);
        ApiResponse apiResponse = ApiResponse.responseData(StatusCode.SUCCESS, SuccessCode.BOARD_RECOMMEND_SUCCESS.getMessage(),countDto);
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    /* 게시글 등록 */
    @PostMapping("/boards/{user-no}")                                      // 유저 정보 확인 o
    public ResponseEntity createBoard(@Valid @RequestBody RequestBoardDto dto, @PathVariable(value ="user-no") long userNo){
        ResponseBoardDto responseBoardDto = boardService.createBoard(dto, userNo);

        EntityModel model =EntityModel.of(responseBoardDto)
                .add(WebMvcLinkBuilder.linkTo(methodOn(BoardController.class)
                        .retrieveBoard(responseBoardDto.getBoardNo())).withRel("board"));

        ApiResponse apiResponse = ApiResponse.responseData(StatusCode.SUCCESS,
                SuccessCode.BOARD_REGISTER_SUCCESS.getMessage(),responseBoardDto);
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    /* 게시물 수정 */
    @PutMapping("/boards/{board-no}/{user-no}")                           // 유저 정보 확인 o
    public ResponseEntity updateBoard(@Valid @RequestBody RequestBoardDto dto, @PathVariable(value ="board-no") long boardNo, @PathVariable(value ="user-no") long userNo){
        ResponseBoardDto responseBoardDto = boardService.updateBoard(dto, boardNo,userNo);
        ApiResponse apiResponse = ApiResponse.responseData(StatusCode.SUCCESS, SuccessCode.BOARD_UPDATE_SUCCESS.getMessage(),responseBoardDto);
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    /* 게시물 삭제 */
    @DeleteMapping("/boards/{board-no}/{user-no}")                        // 유저 정보 확인 o
    public ResponseEntity deleteBoard(@PathVariable(value ="board-no") long boardNo, @PathVariable(value ="user-no") long userNo) {
        ApiResponse apiResponse = ApiResponse.responseData(StatusCode.SUCCESS, SuccessCode.BOARD_DELETE_SUCCESS.getMessage(),boardService.deleteBoard(boardNo,userNo));
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }
}
