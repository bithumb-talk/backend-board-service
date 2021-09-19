package com.bithumb.board.controller;


import com.bithumb.board.domain.Board;
import com.bithumb.board.domain.Comment;
import com.bithumb.board.service.BoardService;
import com.bithumb.board.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

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
        //Optional<Comment> comment = commentRepository.findById(board_no);
//        Specification<Board> joins = (board, query, cb)->{
//            Join<Board, Comment> boards = board.join("board_no");
//
//            return cb.and(board.equals(board.get("??")))
//
//        }
//        Sort.Order staffNameOrder = Sort.Order.desc("staffs.name");
        Board board = boardService.getById(board_no);

        Pageable paging = PageRequest.of(0, 2, Sort.Direction.DESC,"commentCreatedDate");
            Page<Comment> comment = commentService.findCommentsByBoard(board, paging);
        System.out.println(comment);
        return comment;
    }
    @PostMapping("/comments")
    public ResponseEntity createComment(@Valid @RequestBody Comment comment){
        Comment savedComment = commentService.save(comment);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{board_no}")
                .buildAndExpand(savedComment.getCommentNo())
                .toUri();//uri로 변경
        return ResponseEntity.created(location).build();
    }
}
