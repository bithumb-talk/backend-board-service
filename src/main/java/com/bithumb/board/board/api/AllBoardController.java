package com.bithumb.board.board.api;


import com.bithumb.board.board.application.BoardService;
import com.bithumb.board.board.assembler.BoardAssembler;
import com.bithumb.board.board.domain.Board;
import com.bithumb.board.board.domain.BoardModel;
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
            "/all-boards")

    @GetMapping("/all-boards")
    public ResponseEntity BoardsListAll(@PageableDefault(size=16,sort="boardCreatedDate", direction =Sort.Direction.DESC ) final Pageable pageable) {
        Page<Board> board = boardService.BoardsListAll(pageable);
        PagedModel<BoardModel> collModel = pagedResourcesAssembler.toModel(board,boardAssembler);
        System.out.println(collModel.getMetadata());

        ApiResponse apiResponse = ApiResponse.responseData(StatusCode.SUCCESS, SuccessCode.BOARD_FIND_SUCCESS.getMessage(),collModel);
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    /* 유저기반 게시판 조회 */
    @ApiOperation(value=" 유저 기반 게시판 조회", notes=" 유저가 자신이 쓴 게시물 리스트를 조회합니다. " +
            "/all-boards/{user-no}")
    @GetMapping("/all-boards/{user-no}")
    public ResponseEntity BoardsListUser(@PathVariable(value="user-no")
                        long userNo , @PageableDefault(size=16,sort="boardCreatedDate", direction =Sort.Direction.DESC ) final Pageable pageable) {
        User user = userService.findUser(userNo);
        Page<Board> board = boardService.findBoardByUser(user,pageable);
        PagedModel<BoardModel> collModel = pagedResourcesAssembler.toModel(board,boardAssembler);
        ApiResponse apiResponse = ApiResponse.responseData(StatusCode.SUCCESS, SuccessCode.BOARD_FIND_SUCCESS.getMessage(),collModel);
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    @ApiOperation(value="카테고리 기반 게시판 조회", notes=" 카테고리로 게시판을 조회합니다. ")
    /* 카테고리 기반 게시판 조회 */
    @GetMapping("/all-boards/category/{category-no}")
    public ResponseEntity BoardsListCategory(@PathVariable(value="category-no",required = false) long categoryNo, @PageableDefault(size=16,sort="boardCreatedDate", direction =Sort.Direction.DESC )final Pageable pageable) {
        ApiResponse apiResponse = ApiResponse.responseData(StatusCode.SUCCESS, SuccessCode.BOARD_FIND_SUCCESS.getMessage(),null);
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }
}
