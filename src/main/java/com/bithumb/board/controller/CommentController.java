package com.bithumb.board.controller;


import com.bithumb.board.domain.Board;
import com.bithumb.board.domain.Comment;
import com.bithumb.board.response.ApiResponse;
import com.bithumb.board.response.StatusCode;
import com.bithumb.board.response.SuccessCode;
import com.bithumb.board.service.BoardService;
import com.bithumb.board.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins="*", allowCredentials = "false")
public class CommentController {
    private final CommentService commentService;
    private final BoardService boardService;


    @GetMapping("/comments")
    public Page<Comment> retrieveComments(@PageableDefault final Pageable pageable) {
        Page<Comment> comment = commentService.findAll(pageable);

        return comment;
    }

    // board_no 인거 찾아서 pageing
    @GetMapping("/comments/{board_no}")
    public Page<?> retrieveComments(@PathVariable long board_no) {

        Board board = boardService.getById(board_no);

        Pageable paging = PageRequest.of(0, 2, Sort.Direction.DESC,"commentCreatedDate");
            Page<Comment> comment = commentService.findCommentsByBoard(board, paging);
        System.out.println(comment);
        return comment;
    }
    // 댓글 등록
    @PostMapping("/comments")
    public ResponseEntity createComment(@Valid @RequestBody Comment comment){
        Comment savedComment = commentService.save(comment);
//        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
//                .path("/{board_no}")
//                .buildAndExpand(savedComment.getCommentNo())
//                .toUri();//uri로 변경
        ApiResponse apiResponse = ApiResponse.responseData(StatusCode.SUCCESS, SuccessCode.COMMENT_REGISTER_SUCCESS.getMessage(),savedComment);
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }
    // 댓글 수정
    @PutMapping("/comments/{comment_no}")
    public ResponseEntity<?> changeBoard(@RequestBody Comment srcComment, @PathVariable long comment_no){
        Optional<Comment> destComment = commentService.findById(comment_no);
        if(!destComment.isPresent()){
            //에러처리
        }
        destComment.get().setCommentModifyDate(LocalDateTime.now().withNano(0));
        destComment.get().setCommentContent(srcComment.getCommentContent());
        destComment.get().setUserId(srcComment.getUserId());

        ApiResponse apiResponse = ApiResponse.responseData(StatusCode.SUCCESS, SuccessCode.COMMENT_UPDATE_SUCCESS.getMessage(),destComment);
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }


    // 댓글 삭제
    @DeleteMapping("/comments/{comment_no}")
    public ResponseEntity<?> deleteBoard(@PathVariable long comment_no) {
        commentService.deleteById(comment_no);
        ApiResponse apiResponse = ApiResponse.responseData(StatusCode.SUCCESS, SuccessCode.COMMENT_DELETE_SUCCESS.getMessage(),null);
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

}
