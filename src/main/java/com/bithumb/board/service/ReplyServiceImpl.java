package com.board.api.boardMs.board.service;

import com.bithumb.board.domain.Reply;
import com.bithumb.board.repository.ReplyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ReplyServiceImpl implements ReplyService {
    @Autowired
    private final ReplyRepository replyRepository;

    @Override
    public Reply save(Reply reply) {
        return replyRepository.save(reply);
    }
    @Override
    public Optional<Reply> findById(long reply_no){
        return replyRepository.findById(reply_no);
    }
    @Override
    public void deleteById(long reply_no){
        replyRepository.deleteById(reply_no);
    }

}
