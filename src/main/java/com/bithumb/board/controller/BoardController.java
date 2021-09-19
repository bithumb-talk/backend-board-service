package com.bithumb.board.controller;


import com.bithumb.board.domain.Board;
import com.bithumb.board.exception.BoardNotFoundException;
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
    PagedResourcesAssembler<Board> pagedResourcesAssembler;

    @GetMapping("/boards")
    public ResponseEntity retrieveBoards(@PageableDefault(sort="boardCreatedDate", direction =Sort.Direction.DESC ) final Pageable pageable) {
        Page<Board> board = boardService.findAll(pageable);

        Map<String,Object> responseHs = new HashMap<>();

//        responseHs.put("content",board.getContent());
 //       responseHs.put("pageSize", board.getPageable().getPageSize()); //한 페이지에서 나타내는 게시물 수
//        responseHs.put("pageNumber", board.getPageable().getPageNumber()); // 현재 페이지 번호
//        responseHs.put("totalPages", board.getTotalPages()); //페이지로 제공되는 총 페이지의 수

        UriComponents next = UriComponentsBuilder.newInstance()
                .path("/boards")
                .queryParam("page", 1)
                .queryParam("size",board.getPageable().getPageSize())
                .build();
        URI locationNext = ServletUriComponentsBuilder.fromCurrentRequest()
                //.path("/{id}")
                .buildAndExpand(next)
                .toUri();
        System.out.println(ServletUriComponentsBuilder.fromCurrentRequest());
        System.out.println(next);
        System.out.println(locationNext);
        responseHs.put("nextLink", locationNext);
        System.out.println(responseHs.get("nextLink"));
//        int cur = 0;
//        if(board.getPageable().getPageNumber() != 0){
//            cur = board.getPageable().getPageSize()-1;
//        }
//        UriComponents pre = UriComponentsBuilder.newInstance()
//                .path("/boards")
//                .queryParam("size",board.getSize())
//                .queryParam("page", cur)
//                .build();
//
//        URI locationPre = ServletUriComponentsBuilder.fromCurrentRequest()
//                .buildAndExpand(pre)
//                .toUri();
//
//        responseHs.put("preLink",locationPre);
//
//        UriComponents last = UriComponentsBuilder.newInstance()
//                .path("/boards")
//                .queryParam("size",board.getSize())
//                .queryParam("page", board.getTotalPages()-1)
//                .build();
//        URI locationLast = ServletUriComponentsBuilder.fromCurrentRequest()
//                .buildAndExpand(last)
//                .toUri();
//        responseHs.put("lastLink",locationLast);
//
//        UriComponents first = UriComponentsBuilder.newInstance()
//                .path("/boards")
//                .queryParam("size",board.getSize())
//                .queryParam("page", 0)
//                .build();
//        URI locationFirst = ServletUriComponentsBuilder.fromCurrentRequest()
//                .buildAndExpand(first)
//                .toUri();
//        responseHs.put("firstLink",locationFirst);
//
//        System.out.println(first);
//        System.out.println(last);


//        responseHs.put("totalElements", board.getTotalElements()); // 모든 페이지에 존재하는 총 원소 수
 //       responseHs.put("last",board.getPageable().getPageSize());



        return new ResponseEntity<>(board, HttpStatus.OK);
    }


    @PostMapping("/boards")                          //글 등록
    public ResponseEntity<Board> createBoard(@Valid @RequestBody Board board){
        Board savedBoard = boardService.save(board);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{board_no}")
                .buildAndExpand(savedBoard.getBoardNo())
                .toUri();//uri로 변경
        return ResponseEntity.created(location).build();
    }

    @GetMapping("/boards/{board_no}")
    public EntityModel retrieveBoard(@PathVariable long board_no){
        Optional<Board> board = boardService.findById(board_no);

        if(!board.isPresent()){
            throw new BoardNotFoundException(String.format("boardNo {%d} not found",board_no));
        }
        //Map <String, Object> hs = new HashMap<>();
        //List<Comment> comments = board.get().getComments();
        //System.out.println(comments);
        //hs.put("comments",comments);
        EntityModel model =EntityModel.of(board)
                .add(linkTo(methodOn(CommentController.class)
                        .retrieveComments(board_no)).withRel("comments"));

        return model;
    }

    @PutMapping("/boards/{board_no}")
    public ResponseEntity<Map<String, Object>> changeBoard(@RequestBody Board board, @PathVariable long board_no){

        Optional<Board> updateBoard = boardService.findById(board_no);
        Map<String, Object> result = new HashMap<>();
        updateBoard.ifPresent(selectBoard->{
            result.put("status", new String("SUCCESS"));
            result.put("message", new String("UPDATE SUCCESS"));
            selectBoard.setBoardNo(updateBoard.get().getBoardNo());
            if(board.getBoardContent() != null)
                selectBoard.setBoardContent(board.getBoardContent());
            if(board.getBoardCategory() != null)
                selectBoard.setBoardCategory(board.getBoardCategory());
            if(board.getBoardTitle() != null)
                selectBoard.setBoardTitle(board.getBoardTitle());
            if(board.getBoardViews() != null)
                selectBoard.setBoardViews(board.getBoardViews());
            if(board.getBoardRecommend() != null)
                selectBoard.setBoardRecommend(board.getBoardRecommend());
            if(board.getBoardCreatedDate()!= null)
                selectBoard.setBoardCreatedDate(board.getBoardCreatedDate());

//            selectBoard.setCreatedDate(updateBoard.get().getCreatedDate());
            selectBoard.setBoardModifyDate(LocalDateTime.now());
            result.put("data",boardService.save(selectBoard));
        });

        return ResponseEntity.ok().body(result);
    }
    @DeleteMapping("/boards/{board_no}")
    public ResponseEntity<?> deleteBoard(@PathVariable long board_no) {
        boardService.deleteById(board_no);
        ApiResponse apiResponse = ApiResponse.responseData(StatusCode.SUCCESS, SuccessCode.BOARD_DELETE_SUCCESS.getMessage(),null);
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

}
