package com.bithumb.board.comment.api.dto;


import com.bithumb.board.comment.domain.Comment;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.tomcat.jni.Local;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RequestCommentDto {
    @NotBlank(message = "Nickname empty")
    private String nickname;

    @NotBlank(message = "Content empty")
    private String commentContent;

    private long commentRecommend=0;

    private ZonedDateTime commentCreateDate;
    private ZonedDateTime commentModifyDate;


    public void setCommentCreateDate(){
        this.commentCreateDate = ZonedDateTime.now(ZoneOffset.of("+09:00"));
    }
    public void setCommentModifyDate() { this.commentModifyDate = ZonedDateTime.now(ZoneOffset.of("+09:00"));}

    public Comment toEntity(){
        return Comment.builder().commentContent(this.commentContent)
                .commentCreatedDate(this.commentCreateDate)
                .commentModifyDate(this.commentModifyDate)
                .commentRecommend(this.commentRecommend)
                .nickname(this.nickname)
                .build();
    }
}
