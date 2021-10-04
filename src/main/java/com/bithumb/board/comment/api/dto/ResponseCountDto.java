package com.bithumb.board.comment.api.dto;

import com.bithumb.board.comment.domain.Comment;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class ResponseCountDto {
    private long commentRecommend;
    public ResponseCountDto( long commentRecommend) {
        this.commentRecommend = commentRecommend;
    }
    public static ResponseCountDto from(Comment comment) {
        return new ResponseCountDto(comment.getCommentRecommend());
    }
}
