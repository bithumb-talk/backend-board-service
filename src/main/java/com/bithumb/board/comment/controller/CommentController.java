package com.bithumb.board.comment.controller;


import com.bithumb.board.comment.assembler.CommentAssembler;
import com.bithumb.board.board.domain.Board;
import com.bithumb.board.comment.domain.Comment;
import com.bithumb.board.comment.domain.CommentModel;
import com.bithumb.board.response.ApiResponse;
import com.bithumb.board.response.StatusCode;
import com.bithumb.board.response.SuccessCode;
import com.bithumb.board.board.service.BoardService;
import com.bithumb.board.comment.service.CommentService;
import io.swagger.annotations.Api;
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

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.Optional;

@Api(tags = {"Comment"})
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
        Optional<Board> board = boardService.findById(boardNo);
        if (!board.isPresent()) {
            //return null;
            //에러처리 => 리팩토링하면서 바꾸기
            //Optional<Board> board = ...;
            //return board.orElseThrow(() -> new NoSuchElementException());
        }



        Pageable paging = PageRequest.of(0, 16, Sort.Direction.DESC,"commentCreatedDate");
        Page<Comment> comment = commentService.findCommentsByBoard(board.get(),paging);
        PagedModel<CommentModel> collModel = pagedResourcesAssembler.toModel(comment,commentAssembler);
        ApiResponse apiResponse = ApiResponse.responseData(StatusCode.SUCCESS, SuccessCode.COMMENT_FIND_SUCCESS.getMessage(),collModel);
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);

    }
    // 댓글 등록
    // 댓글 post 테스트 x => 보드넘버 추가안됨
    @PostMapping("/boards/{board-no}/comments")
    public ResponseEntity createComment(@Valid @RequestBody Comment comment, @PathVariable(value ="board-no") long boardNo){
        // 게시글 있는지 확인하고
        Optional<Board> board = boardService.findById(boardNo);
        if(board.isPresent()){
            comment.setBoard(board.get());
        }else{
            //에러처리
        }
        Comment savedComment = commentService.save(comment);
        ApiResponse apiResponse = ApiResponse.responseData(StatusCode.SUCCESS, SuccessCode.COMMENT_REGISTER_SUCCESS.getMessage(),savedComment);
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }
    // 댓글 수정

    @PutMapping("/boards/{board-no}/comments/{comment-no}")
    public ResponseEntity<?> changeComment(@RequestBody Comment srcComment,@PathVariable(value= "board-no") long boardNo ,@PathVariable(value ="comment-no") long commentNo){
        Optional<Comment> destComment = commentService.findById(commentNo);
        if(!destComment.isPresent()){
            //에러처리
        }
        destComment.get().setCommentModifyDate(LocalDateTime.now().withNano(0));
        destComment.get().setCommentContent(srcComment.getCommentContent());
        destComment.get().setNickname(srcComment.getNickname());

        ApiResponse apiResponse = ApiResponse.responseData(StatusCode.SUCCESS, SuccessCode.COMMENT_UPDATE_SUCCESS.getMessage(),destComment);
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }


    // 댓글 삭제
    @DeleteMapping("/boards/{board-no}/comments/{comment-no}")
    public ResponseEntity<?> deleteComment(@PathVariable(value = "board-no") long boardNo,@PathVariable(value ="comment-no") long commentNo) {
        commentService.deleteById(commentNo);
        ApiResponse apiResponse = ApiResponse.responseData(StatusCode.SUCCESS, SuccessCode.COMMENT_DELETE_SUCCESS.getMessage(),null);
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

}
