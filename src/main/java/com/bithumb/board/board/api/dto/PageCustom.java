package com.bithumb.board.board.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
public class PageCustom {
    private long size;
    private long totalElements;
    private long totalPages;
    private long number;

    @Builder
    public PageCustom(long size, long totalElements, long totalPages, long number){
        this.size = size;
        this.totalElements = totalElements;
        this.totalPages = totalPages;
        this.number= number;
    }

}
