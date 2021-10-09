package com.bithumb.board.comment.api.dto;

import com.bithumb.board.board.api.dto.ResponseBoardDto;
import com.bithumb.board.board.domain.Board;
import com.bithumb.board.comment.domain.Comment;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;

@NoArgsConstructor
@Getter
public class ResponseCommentDto {
    private long commentNo;
    private String nickname;
    private String commentContent;
    private long commentRecommend;
    private ZonedDateTime commentCreatedDate;
    private ZonedDateTime commentModifyDate;
    public ResponseCommentDto(long commentNo, String nickname, String commentContent, long commentRecommend,
                              ZonedDateTime commentCreatedDate, ZonedDateTime commentModifyDate) {
        this.commentNo = commentNo;
        this.nickname = nickname;
        this.commentContent = commentContent;
        this.commentRecommend = commentRecommend;
        this.commentCreatedDate = commentCreatedDate;
        this.commentModifyDate = commentModifyDate;
    }
    public static ResponseCommentDto of(Comment comment) {
        return new ResponseCommentDto(comment.getCommentNo(), comment.getNickname(), comment.getCommentContent(),
                comment.getCommentRecommend(), comment.getCommentCreatedDate(), comment.getCommentModifyDate());
    }
}
