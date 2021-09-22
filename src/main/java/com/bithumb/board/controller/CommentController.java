package com.bithumb.board.controller;


import com.bithumb.board.Assembler.BoardAssembler;
import com.bithumb.board.Assembler.CommentAssembler;
import com.bithumb.board.domain.Board;
import com.bithumb.board.domain.BoardModel;
import com.bithumb.board.domain.Comment;
import com.bithumb.board.domain.CommentModel;
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
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
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

    @Autowired
    CommentAssembler commentAssembler;
    @Autowired
    PagedResourcesAssembler<Comment> pagedResourcesAssembler;

    @GetMapping("/comments")
    public Page<Comment> retrieveComments(@PageableDefault final Pageable pageable) {
        Page<Comment> comment = commentService.findAll(pageable);

        return comment;
    }

    // board_no 인거 찾아서 pageing
    @GetMapping("/comments/{board-no}")
    public ResponseEntity retrieveComments(@PathVariable(value ="board-no") long boardNo) {
        Board board = boardService.getById(boardNo);
        if(board == null){
            //에러 처리
        }
        Pageable paging = PageRequest.of(0, 2, Sort.Direction.DESC,"commentCreatedDate");

        Page<Comment> comment = commentService.findCommentsByBoard(board,paging);

        PagedModel<CommentModel> collModel = pagedResourcesAssembler.toModel(comment,commentAssembler);
        ApiResponse apiResponse = ApiResponse.responseData(StatusCode.SUCCESS, SuccessCode.COMMENT_FIND_SUCCESS.getMessage(),collModel);
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);


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
    @PutMapping("/comments/{comment-no}")
    public ResponseEntity<?> changeComment(@RequestBody Comment srcComment, @PathVariable(value ="comment-no") long commentNo){
        Optional<Comment> destComment = commentService.findById(commentNo);
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
    @DeleteMapping("/comments/{comment-no}")
    public ResponseEntity<?> deleteComment(@PathVariable(value ="comment-no") long commentNo) {
        commentService.deleteById(commentNo);
        ApiResponse apiResponse = ApiResponse.responseData(StatusCode.SUCCESS, SuccessCode.COMMENT_DELETE_SUCCESS.getMessage(),null);
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

}
