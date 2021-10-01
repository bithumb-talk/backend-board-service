package com.bithumb.board.board.api.dto;

import com.bithumb.board.board.domain.Board;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class CountDto {
    private long boardRecommend;

    public CountDto(long boardRecommend) {
        this.boardRecommend = boardRecommend;
    }
    public static CountDto from(Board board) {
        return new CountDto(board.getBoardRecommend());
    }
}
