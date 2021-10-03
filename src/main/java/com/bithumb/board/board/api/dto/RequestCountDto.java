package com.bithumb.board.board.api.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@NoArgsConstructor
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)

public class RequestCountDto {
    @NotBlank(message = "recommend Empty")
    private String boardRecommend;

    public RequestCountDto(String boardRecommend) {
        this.boardRecommend = boardRecommend;
    }
}
