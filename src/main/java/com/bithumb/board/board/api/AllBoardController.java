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
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

@Api(tags = {"All-Board"})
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
    @GetMapping("/all-boards")
    public ResponseEntity BoardsListAll(final Pageable pageable) {
        Pageable paging = PageRequest.of(0, 16, Sort.Direction.DESC,"boardCreatedDate");
        Page<Board> board = boardService.BoardsListAll(paging);
        PagedModel<BoardModel> collModel = pagedResourcesAssembler.toModel(board,boardAssembler);
        ApiResponse apiResponse = ApiResponse.responseData(StatusCode.SUCCESS, SuccessCode.BOARD_FIND_SUCCESS.getMessage(),collModel);
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    /* 유저기반 게시판 조회 */
    @GetMapping("/all-boards/{user-no}")
    public ResponseEntity BoardsListUser(@PathVariable(value="user-no",required = false) long userNo , final Pageable pageable) {
        Pageable paging = PageRequest.of(0, 16, Sort.Direction.DESC,"boardCreatedDate");
        User user = userService.findUser(userNo);
        Page<Board> board = boardService.findBoardByUser(user,paging);
        PagedModel<BoardModel> collModel = pagedResourcesAssembler.toModel(board,boardAssembler);
        ApiResponse apiResponse = ApiResponse.responseData(StatusCode.SUCCESS, SuccessCode.BOARD_FIND_SUCCESS.getMessage(),collModel);
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    /* 카테고리 기반 게시판 조회 */
    @GetMapping("/all-boards/category/{category-no}")
    public ResponseEntity BoardsListCategory(@PathVariable(value="category-no",required = false) long categoryNo,final Pageable pageable) {
        ApiResponse apiResponse = ApiResponse.responseData(StatusCode.SUCCESS, SuccessCode.BOARD_FIND_SUCCESS.getMessage(),null);
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

}
