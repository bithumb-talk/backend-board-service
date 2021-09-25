package com.bithumb.board.controller;


import com.bithumb.board.Assembler.BoardAssembler;
import com.bithumb.board.controller.CommentController;
import com.bithumb.board.domain.Board;
import com.bithumb.board.domain.BoardModel;
import com.bithumb.board.domain.User;
import com.bithumb.board.response.ApiResponse;
import com.bithumb.board.response.StatusCode;
import com.bithumb.board.response.SuccessCode;
import com.bithumb.board.service.BoardService;
import com.bithumb.board.service.UserService;
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
import java.time.LocalDateTime;
import java.util.Optional;

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
    // 게시판 조회
    @GetMapping("/boards")
    public ResponseEntity retrieveBoardsList(@RequestParam(value="user",required = false) Long userNo ,final Pageable pageable) {

        Pageable paging = PageRequest.of(0, 16, Sort.Direction.DESC,"boardCreatedDate");

        if(userNo != null){        //유저 정보 확인 필요 o
            User user = userService.getById(userNo);
            if(user == null){
                //에러 처리
            }
            Page<Board> board = boardService.findBoardByUser(user,pageable);
            PagedModel<BoardModel> collModel = pagedResourcesAssembler.toModel(board,boardAssembler);
            ApiResponse apiResponse = ApiResponse.responseData(StatusCode.SUCCESS, SuccessCode.BOARD_FIND_SUCCESS.getMessage(),collModel);
            return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
        }
        Page<Board> board = boardService.findAll(pageable);         //유저 정보 확인 필요 x
        PagedModel<BoardModel> collModel = pagedResourcesAssembler.toModel(board,boardAssembler);
        ApiResponse apiResponse = ApiResponse.responseData(StatusCode.SUCCESS, SuccessCode.BOARD_FIND_SUCCESS.getMessage(),collModel);
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    // 게시물 등록
    @PostMapping("/boards")                                      // 유저 정보 확인 o
    public ResponseEntity<?> createBoard(@Valid @RequestBody Board board){
        Board savedBoard = boardService.save(board);
        EntityModel model =EntityModel.of(savedBoard)
                .add(WebMvcLinkBuilder.linkTo(methodOn(BoardController.class)
                        .retrieveBoard(savedBoard.getBoardNo())).withRel("board"));
//        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
//                .path("/{board_no}")
//                .buildAndExpand(savedBoard.getBoardNo())
//                .toUri();//uri로 변경
        ApiResponse apiResponse = ApiResponse.responseData(StatusCode.SUCCESS, SuccessCode.BOARD_REGISTER_SUCCESS.getMessage(),model);
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    // 게시물 조회
    @GetMapping("/boards/{board-no}")                            // 유저 정보 확인 x
    public ResponseEntity<?> retrieveBoard(@PathVariable(value ="board-no") long boardNo){
        Optional<Board> board = boardService.findById(boardNo);

        if(!board.isPresent()){
            //에러처리
        }

        //if(LOGGER.isDebugEnabled()) {
            // 로그레벨 조정하기
            //LOGGER.debug("조회한 게시물 넘버 {}.", boardNo);
        //    LOGGER.info("조회한 게시물 넘버 {}.", boardNo);
        //}



        EntityModel model =EntityModel.of(board)
                .add(WebMvcLinkBuilder.linkTo(methodOn(CommentController.class)
                        .retrieveComments(boardNo)).withRel("comments"));

        ApiResponse apiResponse = ApiResponse.responseData(StatusCode.SUCCESS, SuccessCode.BOARD_FIND_SUCCESS.getMessage(),model);
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);

    }

    // 게시물 수정
    @PutMapping("/boards/{board-no}")                           // 유저 정보 확인 o
    public ResponseEntity<?> changeBoard(@RequestBody Board srcBoard, @PathVariable(value ="board-no") long boardNo){
        //조회수 증가, 추천수 증가 따로처리
        Optional<Board> destBoard = boardService.findById(boardNo);
        if(!destBoard.isPresent()){
            //에러처리
        }
        if(srcBoard.getBoardContent() != null){
            destBoard.get().setBoardContent(srcBoard.getBoardContent());
        }
        if(srcBoard.getBoardCategory() != null ) {
            destBoard.get().setBoardCategory(srcBoard.getBoardCategory());
        }
        if(srcBoard.getBoardTitle() != null) {
            destBoard.get().setBoardTitle(srcBoard.getBoardTitle());
        }
        if(srcBoard.getBoardRecommend() != null) {
            destBoard.get().setBoardRecommend( destBoard.get().getBoardRecommend()+1 );
        }
        destBoard.get().setBoardModifyDate(LocalDateTime.now().withNano(0));

        ApiResponse apiResponse = ApiResponse.responseData(StatusCode.SUCCESS, SuccessCode.BOARD_UPDATE_SUCCESS.getMessage(),destBoard);
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    //게시물 삭제
    @DeleteMapping("/boards/{board-no}")                                // 유저 정보 확인 o
    public ResponseEntity<?> deleteBoard(@PathVariable(value ="board-no") long boardNo) {
        boardService.deleteById(boardNo);
        ApiResponse apiResponse = ApiResponse.responseData(StatusCode.SUCCESS, SuccessCode.BOARD_DELETE_SUCCESS.getMessage(),null);
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

}
