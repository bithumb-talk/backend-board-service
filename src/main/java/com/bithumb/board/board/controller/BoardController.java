package com.bithumb.board.board.controller;


import com.bithumb.board.board.assembler.BoardAssembler;
import com.bithumb.board.board.controller.dto.CountDto;
import com.bithumb.board.board.controller.dto.RequestBoardDto;
import com.bithumb.board.board.controller.dto.ResponseBoardDto;
import com.bithumb.board.board.domain.Board;
import com.bithumb.board.board.domain.BoardModel;
import com.bithumb.board.common.response.ApiResponse;
import com.bithumb.board.common.response.ErrorCode;
import com.bithumb.board.common.response.StatusCode;
import com.bithumb.board.common.response.SuccessCode;
import com.bithumb.board.board.service.BoardService;
import com.bithumb.board.user.domain.User;
import com.bithumb.board.user.service.UserService;
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
    private final UserService userService;
    @Autowired
    BoardAssembler boardAssembler;
    @Autowired
    PagedResourcesAssembler<Board> pagedResourcesAssembler;


    private static final Logger LOGGER = LoggerFactory.getLogger(BoardController.class);
    // 게시글 리스트 조회, 유저 기반 게시글 리스트 조회
    // 전체조회, 유저기반 전체조회 분리하기
    @GetMapping("/all-boards")
    public ResponseEntity retrieveBoardsList(final Pageable pageable) {
        Pageable paging = PageRequest.of(0, 16, Sort.Direction.DESC,"boardCreatedDate");
        Page<Board> board = boardService.findAll(paging);         //유저 정보 확인 필요 x
        PagedModel<BoardModel> collModel = pagedResourcesAssembler.toModel(board,boardAssembler);
        ApiResponse apiResponse = ApiResponse.responseData(StatusCode.SUCCESS, SuccessCode.BOARD_FIND_SUCCESS.getMessage(),collModel);
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    @GetMapping("/all-boards/{user-no}")
    public ResponseEntity retrieveBoardsList(@PathVariable(value="user-no",required = false) long userNo ,final Pageable pageable) {
        Pageable paging = PageRequest.of(0, 16, Sort.Direction.DESC,"boardCreatedDate");
        User user = userService.getById(userNo);
        Page<Board> board = boardService.findBoardByUser(user,paging);
        PagedModel<BoardModel> collModel = pagedResourcesAssembler.toModel(board,boardAssembler);
        ApiResponse apiResponse = ApiResponse.responseData(StatusCode.SUCCESS, SuccessCode.BOARD_FIND_SUCCESS.getMessage(),collModel);
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    /* 게시글 조회 */
    @GetMapping("/boards/{board-no}")                            // 유저 정보 확인 x
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
        System.out.println(dto.getBoardImg());
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
    @DeleteMapping("/boards/{board-no}")                                // 유저 정보 확인 o
    public ResponseEntity deleteBoard(@PathVariable(value ="board-no") long boardNo) {
        boardService.deleteById(boardNo);
        ApiResponse apiResponse = ApiResponse.responseData(StatusCode.SUCCESS, SuccessCode.BOARD_DELETE_SUCCESS.getMessage(),null);
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }


}
