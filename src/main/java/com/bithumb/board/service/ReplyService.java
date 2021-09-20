package com.bithumb.board.service;

import com.bithumb.board.domain.Comment;
import com.bithumb.board.domain.Reply;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public interface ReplyService {
    Reply save(Reply reply);
    Optional<Reply> findById(long reply_no);
    void deleteById(long reply_no);
}
