package com.bithumb.board.board.controller.dto;

import com.bithumb.board.board.domain.Board;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
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

    private LocalDateTime boardCreatedDate = LocalDateTime.now().withNano(0);
    private long boardViews = 0;
    private long boardRecommend = 0;
    private List<String> boardImg;

    public void setBoardCreateDate(){
        this.boardCreatedDate = LocalDateTime.now().withNano(0);
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
    public Board toEntity(){
        return Board.builder()
                .nickname(this.nickname)
                .boardRecommend(this.boardRecommend)
                .boardTitle(this.boardTitle)
                .boardContent(this.boardContent)
                .boardCreatedDate(this.boardCreatedDate)
                .boardViews(this.boardViews)
                .boardImg(listToString())
                .build();
    }


}
