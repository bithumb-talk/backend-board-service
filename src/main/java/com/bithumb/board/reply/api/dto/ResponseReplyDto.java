package com.bithumb.board.reply.api.dto;

import com.bithumb.board.reply.domain.Reply;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
public class ResponseReplyDto {
    private long replyNo;
    private String nickname;
    private String replyContent;
    private LocalDateTime replyCreatedDate;
    private LocalDateTime replyModifyDate;

    public ResponseReplyDto(long replyNo, String nickname, String replyContent
        ,LocalDateTime replyCreatedDate, LocalDateTime replyModifyDate){
        this.replyNo = replyNo;
        this.nickname = nickname;
        this.replyContent = replyContent;
        this.replyCreatedDate = replyCreatedDate;
        this.replyModifyDate = replyModifyDate;
    }
    public static ResponseReplyDto of (Reply reply){
        return new ResponseReplyDto(reply.getReplyNo(), reply.getNickname(), reply.getReplyContent() ,reply.getReplyCreatedDate(), reply.getReplyModifyDate());
    }
}
