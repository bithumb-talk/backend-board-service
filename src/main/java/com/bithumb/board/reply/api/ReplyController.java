
package com.bithumb.board.reply.api;

import com.bithumb.board.reply.api.dto.RequestReplyDto;
import com.bithumb.board.reply.api.dto.ResponseReplyDto;
import com.bithumb.board.reply.domain.Reply;
import com.bithumb.board.common.response.ApiResponse;
import com.bithumb.board.common.response.StatusCode;
import com.bithumb.board.common.response.SuccessCode;
import com.bithumb.board.reply.application.ReplyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.Optional;

@Api(tags = {"Reply API"})
@RestController
@RequiredArgsConstructor
public class ReplyController {

    private final ReplyService replyService;

    /* 대댓글 등록 */
    @ApiOperation(value=" 대댓글 등록 ", notes="board-no의 경우 위의 API들과 통일성때문에 넣었습니다." +
            "\n /boards/{board-no}/comments/{comment-no}/replies")
    @PostMapping("/boards/{board-no}/comments/{comment-no}/replies/auth") //
    public ResponseEntity createReply( @Valid @RequestBody RequestReplyDto requestReplyDto,
                                       @ApiParam(value = "boardNo", required = false)
                                       @PathVariable(value ="board-no") long boardNo,
                                       @ApiParam(value = "commentNo", required = true)
                                           @PathVariable(value= "comment-no") long commentNo ) {
        ResponseReplyDto responseReplyDto = replyService.createReply(requestReplyDto, commentNo);
        ApiResponse apiResponse = ApiResponse.responseData(StatusCode.SUCCESS, SuccessCode.REPLY_REGISTER_SUCCESS.getMessage(),responseReplyDto);
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    /* 대댓글 수정 */
    @ApiOperation(value=" 대댓글 수정 ", notes="board-no의 경우 위의 API들과 통일성때문에 넣었습니다.")
    @PutMapping("/boards/{board-no}/comments/{comment-no}/replies/{reply-no}/auth") //
    public ResponseEntity updateReply (@Valid @RequestBody RequestReplyDto requestReplyDto,
                                       @ApiParam(value = "boardNo", required = false)
                                       @PathVariable(value="board-no") long boardNo,
                                       @ApiParam(value = "commentNo", required = true)
                                           @PathVariable (value="comment-no") long commentNo,
                                       @ApiParam(value = "replyNo", required = true)
                                           @PathVariable(value ="reply-no") long replyNo){
        ResponseReplyDto responseReplyDto = replyService.updateReply(requestReplyDto, replyNo);
        ApiResponse apiResponse = ApiResponse.responseData(StatusCode.SUCCESS, SuccessCode.REPLY_UPDATE_SUCCESS.getMessage(),responseReplyDto);
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    /* 대댓글 삭제 */
    @ApiOperation(value=" 대댓글 삭제 ", notes="board-no / comment-no 의 경우 위의 API들과 통일성때문에 넣었습니다.")
    @DeleteMapping("/boards/{board-no}/comments/{comment-no}/replies/{reply-no}/auth") //
    public ResponseEntity deleteReply( @ApiParam(value = "boardNo", required = false)
                                           @PathVariable(value="board-no") long boardNo,
                                       @ApiParam(value = "commentNo", required = false)
                                       @PathVariable (value="comment-no") long commentNo,
                                       @ApiParam(value = "commentNo", required = true)
                                       @PathVariable(value ="reply-no") long replyNo) {
        replyService.deleteReply(replyNo);
        ApiResponse apiResponse = ApiResponse.responseData(StatusCode.SUCCESS, SuccessCode.REPLY_DELETE_SUCCESS.getMessage(),null);
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

}
