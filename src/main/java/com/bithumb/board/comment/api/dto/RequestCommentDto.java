package com.bithumb.board.comment.api.dto;


import com.bithumb.board.comment.domain.Comment;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.tomcat.jni.Local;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

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

    private LocalDateTime commentCreateDate;
    private LocalDateTime commentModifyDate;


    public void setCommentCreateDate(){
        this.commentCreateDate = LocalDateTime.now().withNano(0);
    }
    public void setCommentModifyDate() { this.commentModifyDate = LocalDateTime.now().withNano(0);}

    public Comment toEntity(){
        return Comment.builder().commentContent(this.commentContent)
                .commentCreatedDate(this.commentCreateDate)
                .commentModifyDate(this.commentModifyDate)
                .commentRecommend(this.commentRecommend)
                .nickname(this.nickname)
                .build();
    }
}
