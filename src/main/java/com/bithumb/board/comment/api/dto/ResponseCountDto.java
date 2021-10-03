package com.bithumb.board.comment.api.dto;

import com.bithumb.board.comment.domain.Comment;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class ResponseCountDto {
    private long commentNo;
    private long commentRecommend;
    public ResponseCountDto(long commentNo, long commentRecommend) {
        this.commentNo = commentNo;
        this.commentRecommend = commentRecommend;
    }
    public static ResponseCountDto from(Comment comment) {

        return new ResponseCountDto(comment.getCommentNo(),comment.getCommentRecommend());
    }
}
