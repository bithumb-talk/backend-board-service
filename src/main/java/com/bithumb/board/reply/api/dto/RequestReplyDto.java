package com.bithumb.board.reply.api.dto;

import com.bithumb.board.comment.domain.Comment;
import com.bithumb.board.reply.domain.Reply;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RequestReplyDto {
    @NotBlank(message = "reply nickname empty")
    private String nickname;

    @NotBlank(message = "reply Content empty")
    private String replyContent;

    private LocalDateTime replyCreatedDate;
    private LocalDateTime replyModifyDate;

    public void setreplyCreateDate(){
        this.replyCreatedDate = LocalDateTime.now().withNano(0);
    }
    public void setreplyModifyDate() { this.replyModifyDate = LocalDateTime.now().withNano(0);}

    public Reply toEntity(){
        return Reply.builder().replyContent(this.replyContent)
                .replyCreatedDate(this.replyCreatedDate)
                .replyModifyDate(this.replyModifyDate)
                .nickname(this.nickname)
                .build();
    }

}
