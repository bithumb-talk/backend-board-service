package com.bithumb.board.comment.api.dto;

import com.bithumb.board.board.domain.Board;
import com.bithumb.board.comment.domain.Comment;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class CountDto {
    private long commentRecommend;
    public CountDto(long commentRecommend) {
        this.commentRecommend = commentRecommend;
    }
    public static CountDto from(Comment comment) {
        return new CountDto(comment.getCommentRecommend());
    }
}
