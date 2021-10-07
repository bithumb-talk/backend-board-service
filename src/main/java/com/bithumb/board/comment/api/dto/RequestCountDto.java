package com.bithumb.board.comment.api.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
@NoArgsConstructor
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)

public class RequestCountDto {
    @NotBlank(message = "recommend Empty")
    private String commentRecommend;

    public RequestCountDto(String commentRecommend) {
        this.commentRecommend = commentRecommend;
    }
}
