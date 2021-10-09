package com.bithumb.board.board.api.dto;

import com.bithumb.board.board.domain.Board;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
public class ResponseLikeDto<T> {
    private List<ResponseBoardDto> boardList;
    private PageCustom page;

    public ResponseLikeDto (List<ResponseBoardDto> boardList, long size, long totalElements, long totalPages,long number){
        this.boardList = boardList;
        this.page = PageCustom.builder()
                .size(size)
                .totalElements(totalElements)
                .totalPages(totalPages)
                .number(number)
                .build();
    }

    public static ResponseLikeDto of(List<ResponseBoardDto> boardList, long size, long totalElements, long totalPages, long number){
        return new ResponseLikeDto(boardList, size, totalElements, totalPages, number);
    }

}
