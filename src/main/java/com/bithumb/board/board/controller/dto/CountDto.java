package com.bithumb.board.board.controller.dto;

import com.bithumb.board.board.domain.Board;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class CountDto {
    private long boardRecommend;

    public CountDto(long boardRecommend) {
        this.boardRecommend = boardRecommend;
    }

    //setter
    public static CountDto from(Board board) {
        return new CountDto(board.getBoardRecommend());
    }

}
