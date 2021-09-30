package com.bithumb.board.reply.application;

import com.bithumb.board.reply.domain.Reply;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public interface ReplyService {
    Reply save(Reply reply);
    Optional<Reply> findById(long reply_no);
    void deleteById(long reply_no);
}
