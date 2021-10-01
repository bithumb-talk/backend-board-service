
package com.bithumb.board.reply.application;
import com.bithumb.board.comment.domain.Comment;
import com.bithumb.board.comment.repository.CommentRepository;
import com.bithumb.board.common.response.ErrorCode;
import com.bithumb.board.reply.api.dto.RequestReplyDto;
import com.bithumb.board.reply.api.dto.ResponseReplyDto;
import com.bithumb.board.reply.domain.Reply;


import com.bithumb.board.reply.repository.ReplyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ReplyServiceImpl implements ReplyService {
    private final ReplyRepository replyRepository;
    private final CommentRepository commentRepository;

    @Override
    public ResponseReplyDto createReply(RequestReplyDto requestReplyDto, long commentNo) {
        requestReplyDto.setreplyCreateDate();
        Reply reply = requestReplyDto.toEntity();
        Comment comment = commentRepository.findById(commentNo).orElseThrow(() -> new NullPointerException(ErrorCode.COMMENT_NOT_EXIST.getMessage()));
        reply.changeComment(comment);
        return ResponseReplyDto.of(replyRepository.save(reply));
    }
    @Override
    public ResponseReplyDto updateReply(RequestReplyDto requestReplyDto, long replyNo){
        Reply reply = replyRepository.findById(replyNo).orElseThrow(() -> new NullPointerException(ErrorCode.REPLY_NOT_EXIST.getMessage()));
        requestReplyDto.setreplyModifyDate();
        reply.changeReply(requestReplyDto.getNickname(),requestReplyDto.getReplyContent(), requestReplyDto.getReplyModifyDate());
        return ResponseReplyDto.of(replyRepository.save(reply));
    }

    @Override
    public void deleteReply(long reply_no) {
        replyRepository.deleteById(reply_no);
    }

}
