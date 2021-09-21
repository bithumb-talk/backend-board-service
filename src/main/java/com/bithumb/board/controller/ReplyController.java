
package com.bithumb.board.controller;

import com.bithumb.board.domain.Board;
import com.bithumb.board.domain.Comment;

import com.bithumb.board.domain.Reply;
import com.bithumb.board.response.ApiResponse;
import com.bithumb.board.response.StatusCode;
import com.bithumb.board.response.SuccessCode;
import com.bithumb.board.service.ReplyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowCredentials = "false")
public class ReplyController {
    private ReplyService replyService;

    //대댓글 등록
    @PostMapping("/replies")
    public ResponseEntity createReply(@Valid @RequestBody Reply reply) {
        Reply savedReply = replyService.save(reply);

        ApiResponse apiResponse = ApiResponse.responseData(StatusCode.SUCCESS, SuccessCode.REPLY_REGISTER_SUCCESS.getMessage(),savedReply);
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }
    //대댓글 수정
    @PutMapping("/replies/{reply-no}")
    public ResponseEntity updateReply (@PathVariable("reply-no") long replyNo ,@RequestBody Reply srcReply ){
        Optional<Reply> destReply = replyService.findById(replyNo);
        if(!destReply.isPresent()){
            //에러처리
        }
        destReply.get().setReplyModifyDate(LocalDateTime.now().withNano(0));
        destReply.get().setReplyContent(srcReply.getReplyContent());
        destReply.get().setUserId(srcReply.getUserId());

        ApiResponse apiResponse = ApiResponse.responseData(StatusCode.SUCCESS, SuccessCode.REPLY_UPDATE_SUCCESS.getMessage(),destReply);
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);

    }
    // 대댓글 삭제
    @DeleteMapping("/replies/{reply-no}")
    public ResponseEntity<?> deleteBoard(@PathVariable long replyNo) {
        replyService.deleteById(replyNo);
        ApiResponse apiResponse = ApiResponse.responseData(StatusCode.SUCCESS, SuccessCode.REPLY_DELETE_SUCCESS.getMessage(),null);
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

}
