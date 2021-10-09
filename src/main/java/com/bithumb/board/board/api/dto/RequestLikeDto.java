package com.bithumb.board.board.api.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
//@Getter

public class RequestLikeDto {
    private List<Long> contentIdList;
    public List<Long> getContentIdList(){
        return this.contentIdList;
    }
    public void setContentIdList(List<Long> contentIdList) {
        this.contentIdList = contentIdList;
    }
}
