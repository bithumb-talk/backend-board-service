package com.bithumb.board.board.api;


import com.bithumb.board.board.application.BoardService;
import com.bithumb.board.board.assembler.BoardAssembler;
import com.bithumb.board.board.domain.Board;
import com.bithumb.board.board.domain.BoardModel;
import com.bithumb.board.board.repository.BoardRepository;
import com.bithumb.board.common.response.ApiResponse;
import com.bithumb.board.common.response.StatusCode;
import com.bithumb.board.common.response.SuccessCode;
import com.bithumb.board.user.application.UserService;
import com.bithumb.board.user.domain.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Api(tags = {"All-Board API"})
@RestController
@RequiredArgsConstructor
@CrossOrigin(origins="*", allowCredentials = "false")
@Slf4j
public class AllBoardController {
    private final BoardService boardService;
    private final UserService userService;

    @Autowired
    BoardAssembler boardAssembler;
    @Autowired
    PagedResourcesAssembler<Board> pagedResourcesAssembler;

    /* 게시판 조회 */
    @ApiOperation(value=" 게시판 조회", notes="default 0번 페이지부터 시작, 아무값도 입력하지 않으면 첫번째 페이지를 조회합니다." +
            "\n /all-boards (default 첫번째 페이지) 로 조회하면 알아서 링크 다 생성해줍니다" +
            "\n size = 현재 페이지에 조회할 최대 갯수 : default 16으로 해둠" +
            "\n page = 조회할 페이지" +
            "\n sort = 정렬방식, default :  boardCreatedDate, desc ")

    @GetMapping("/all-boards")
    public ResponseEntity BoardsListAll(@PageableDefault(size=16,sort="boardCreatedDate", direction =Sort.Direction.DESC ) final Pageable pageable) {
        Page<Board> board = boardService.BoardsListAll(pageable);
        PagedModel<BoardModel> collModel = pagedResourcesAssembler.toModel(board,boardAssembler);
        ApiResponse apiResponse = ApiResponse.responseData(StatusCode.SUCCESS, SuccessCode.BOARD_FIND_SUCCESS.getMessage(),collModel);
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    /* 유저기반 게시판 조회 */
    @ApiOperation(value=" 유저 기반 게시판 조회", notes=" 유저가 자신이 쓴 게시물 리스트를 조회합니다. " +
            "\n /all-boards/{user-no}" +
            "\n user-no: 현재 로그인한 유저넘버" +
            "\n size = 현재 페이지에 조회할 최대 갯수 : default 16으로 해둠" +
            "\n page = 조회할 페이지" +
            "\n sort = 정렬방식, default :  boardCreatedDate, desc ")
    @GetMapping("/all-boards/{user-no}")
    public ResponseEntity BoardsListUser(@PathVariable(value="user-no")
                        long userNo , @PageableDefault(size=16,sort="boardCreatedDate", direction =Sort.Direction.DESC ) final Pageable pageable) {
        User user = userService.findUser(userNo);
        Page<Board> board = boardService.findBoardByUser(user,pageable);
        PagedModel<BoardModel> collModel = pagedResourcesAssembler.toModel(board,boardAssembler);
        ApiResponse apiResponse = ApiResponse.responseData(StatusCode.SUCCESS, SuccessCode.BOARD_FIND_SUCCESS.getMessage(),collModel);
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    /* 카테고리 기반 게시판 조회 */
    @ApiOperation(value="카테고리 기반 게시판 조회", notes=" 카테고리로 게시판을 조회합니다. ")
    @GetMapping("/all-boards/category/{boardCategory}")
    public ResponseEntity BoardsListCategory(@PathVariable(value="boardCategory",required = false) String boardCategory, @PageableDefault(size=16,sort="boardCreatedDate", direction =Sort.Direction.DESC )final Pageable pageable) {

        Page<Board> board = boardService.findBoardByBoardCategory(boardCategory, pageable);
        PagedModel<BoardModel> collModel = pagedResourcesAssembler.toModel(board,boardAssembler);
        ApiResponse apiResponse = ApiResponse.responseData(StatusCode.SUCCESS, SuccessCode.BOARD_FIND_SUCCESS.getMessage(),collModel);
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    /* 베스트 인기글 4개 조회  */
    @ApiOperation(value="베스트 인기글 4개 조회", notes=" 좋아요 갯수로 인기글 최대 4개를 조회")
    @GetMapping("/all-boards/ranking")
    public ResponseEntity BoardsRanking(){
        List<Board> board = boardService.boardsRanking();
        CollectionModel<BoardModel> collModel = boardAssembler.toCollectionModel(board);
        ApiResponse apiResponse = ApiResponse.responseData(StatusCode.SUCCESS, SuccessCode.BOARD_FIND_SUCCESS.getMessage(), collModel);
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }
}
