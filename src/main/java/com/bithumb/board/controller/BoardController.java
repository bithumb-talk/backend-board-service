package com.bithumb.board.controller;


import com.bithumb.board.domain.Board;
import com.bithumb.board.domain.BoardModel;
import com.bithumb.board.response.ApiResponse;
import com.bithumb.board.response.StatusCode;
import com.bithumb.board.response.SuccessCode;
import com.bithumb.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


@RestController
@RequiredArgsConstructor
@CrossOrigin(origins="*", allowCredentials = "false")
//@Slf4j
public class BoardController {
    private final BoardService boardService;

    @Autowired
    BoardAssembler boardAssembler;
    @Autowired
    PagedResourcesAssembler<Board> pagedResourcesAssembler;

    // 게시판 조회
    @GetMapping("/boards")
    public ResponseEntity retrieveBoards(@PageableDefault(sort="boardCreatedDate", direction =Sort.Direction.DESC ) final Pageable pageable) {
        Page<Board> board = boardService.findAll(pageable);
        PagedModel<BoardModel> collModel = pagedResourcesAssembler.toModel(board,boardAssembler);
        ApiResponse apiResponse = ApiResponse.responseData(StatusCode.SUCCESS, SuccessCode.BOARD_FIND_SUCCESS.getMessage(),collModel);
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    // 게시물 등록
    @PostMapping("/boards")
    public ResponseEntity<?> createBoard(@Valid @RequestBody Board board){
        Board savedBoard = boardService.save(board);
//        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
//                .path("/{board_no}")
//                .buildAndExpand(savedBoard.getBoardNo())
//                .toUri();//uri로 변경
        ApiResponse apiResponse = ApiResponse.responseData(StatusCode.SUCCESS, SuccessCode.BOARD_REGISTER_SUCCESS.getMessage(),savedBoard);
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    // 게시물 조회
    @GetMapping("/boards/{board_no}")
    public ResponseEntity<?> retrieveBoard(@PathVariable long board_no){
        Optional<Board> board = boardService.findById(board_no);

        if(!board.isPresent()){
            //에러처리
        }

        EntityModel model =EntityModel.of(board)
                .add(linkTo(methodOn(CommentController.class)
                        .retrieveComments(board_no)).withRel("comments"));

        ApiResponse apiResponse = ApiResponse.responseData(StatusCode.SUCCESS, SuccessCode.BOARD_FIND_SUCCESS.getMessage(),model);
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);

    }

    // 게시물 수정
    @PutMapping("/boards/{board_no}")
    public ResponseEntity<?> changeBoard(@RequestBody Board srcBoard, @PathVariable long board_no){
        //조회수 증가, 추천수 증가 따로처리
        Optional<Board> destBoard = boardService.findById(board_no);
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
    @DeleteMapping("/boards/{board_no}")
    public ResponseEntity<?> deleteBoard(@PathVariable long board_no) {
        boardService.deleteById(board_no);
        ApiResponse apiResponse = ApiResponse.responseData(StatusCode.SUCCESS, SuccessCode.BOARD_DELETE_SUCCESS.getMessage(),null);
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

}
