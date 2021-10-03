package com.bithumb.board.board.api.dto;

import com.bithumb.board.board.domain.Board;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class ResponseCountDto {
    private long boardNo;
    private long boardRecommend;

    public ResponseCountDto(long boardNo , long boardRecommend) {
        this.boardNo = boardNo;
        this.boardRecommend = boardRecommend;
    }
    public static ResponseCountDto from(Board board) {
        return new ResponseCountDto( board.getBoardNo(),board.getBoardRecommend());
    }
}
