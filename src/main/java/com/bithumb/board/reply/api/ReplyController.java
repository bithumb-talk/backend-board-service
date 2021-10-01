
package com.bithumb.board.reply.api;

import com.bithumb.board.reply.api.dto.RequestReplyDto;
import com.bithumb.board.reply.api.dto.ResponseReplyDto;
import com.bithumb.board.reply.domain.Reply;
import com.bithumb.board.common.response.ApiResponse;
import com.bithumb.board.common.response.StatusCode;
import com.bithumb.board.common.response.SuccessCode;
import com.bithumb.board.reply.application.ReplyService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.Optional;

@Api(tags = {"Reply"})
@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowCredentials = "false")
public class ReplyController {

    private final ReplyService replyService;

    /* 대댓글 등록 */
    @PostMapping("/boards/{board-no}/comments/{comment-no}/replies")
    public ResponseEntity createReply(@Valid @RequestBody RequestReplyDto requestReplyDto, @PathVariable(value ="board-no") long boardNo
            ,@PathVariable(value= "comment-no") long commentNo ) {
        ResponseReplyDto responseReplyDto = replyService.createReply(requestReplyDto, commentNo);
        ApiResponse apiResponse = ApiResponse.responseData(StatusCode.SUCCESS, SuccessCode.REPLY_REGISTER_SUCCESS.getMessage(),responseReplyDto);
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    /* 대댓글 수정 */
    @PutMapping("/boards/{board-no}/comments/{comment-no}/replies/{reply-no}")
    public ResponseEntity updateReply (@Valid @RequestBody RequestReplyDto requestReplyDto, @PathVariable(value="board-no") long boardNo , @PathVariable (value="comment-no") long commentNo, @PathVariable(value ="reply-no") long replyNo){
        ResponseReplyDto responseReplyDto = replyService.updateReply(requestReplyDto, replyNo);
        ApiResponse apiResponse = ApiResponse.responseData(StatusCode.SUCCESS, SuccessCode.REPLY_UPDATE_SUCCESS.getMessage(),responseReplyDto);
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    /* 대댓글 삭제 */
    @DeleteMapping("/boards/{board-no}/comments/{comment-no}/replies/{reply-no}")
    public ResponseEntity deleteReply(@PathVariable(value ="reply-no") long replyNo) {
        replyService.deleteReply(replyNo);
        ApiResponse apiResponse = ApiResponse.responseData(StatusCode.SUCCESS, SuccessCode.REPLY_DELETE_SUCCESS.getMessage(),null);
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

}
