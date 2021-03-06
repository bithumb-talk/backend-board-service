package com.bithumb.board.board.api.dto;

import com.bithumb.board.board.domain.Board;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
/* 게시글 등록 Dto */
public class RequestBoardDto {
    @NotBlank(message = "Input Your Nickname")
    private String nickname;

    @NotBlank(message = "Title empty")
    private String boardTitle;

    @NotBlank(message = "Content empty")
    private String boardContent;

    @NotBlank(message = "Category empty")
    private String boardCategory;

    private ZonedDateTime boardCreatedDate;
    private long boardViews = 0;
    private long boardRecommend = 0;
    private List<String> boardImg;

    public void setBoardCreateDate(){
        this.boardCreatedDate = ZonedDateTime.now(ZoneOffset.of("+09:00"));
    }

    /* 이미지 url To 이미지 String */
    public String listToString(){
        if(this.boardImg == null){
            return "";
        }
        List<String> boardImg = this.boardImg;
        String url = "";
        for(String imgUrl : boardImg){
            url += imgUrl;
            url += "\n";
        }
        return url;
    }

    public String setListToStringUrl(){
        return listToString();
    }
    public Board toEntity() {
        return Board.builder()
                .nickname(this.nickname)
                .boardTitle(this.boardTitle)
                .boardViews(this.boardViews)
                .boardCategory(this.boardCategory)
                .boardContent(this.boardContent)
                .boardCreatedDate(this.boardCreatedDate)
                .boardRecommend(this.boardRecommend)
                .boardImg(listToString())
                .build();
    }
}
