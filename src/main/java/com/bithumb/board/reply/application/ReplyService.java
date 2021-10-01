package com.bithumb.board.reply.application;

import com.bithumb.board.reply.api.dto.RequestReplyDto;
import com.bithumb.board.reply.api.dto.ResponseReplyDto;
import com.bithumb.board.reply.domain.Reply;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public interface ReplyService {
    /* 대댓글 등록 */
    ResponseReplyDto createReply(RequestReplyDto requestReplyDto, long commentNo);
    ResponseReplyDto updateReply(RequestReplyDto requestReplyDto, long replyNo);
    void deleteReply(long reply_no);
}
