package com.bithumb.board.comment.api.dto;


import com.bithumb.board.comment.domain.Comment;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.tomcat.jni.Local;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
/*
public class RequestBoardDto {
    @NotBlank(message = "Input Your Nickname")
    private String nickname;

    @NotBlank(message = "Title empty")
    private String boardTitle;

    @NotBlank(message = "Content empty")
    private String boardContent;

    private LocalDateTime boardCreatedDate;
    private long boardViews = 0;
    private long boardRecommend = 0;
    private List<String> boardImg;

    public void setBoardCreateDate(){
        this.boardCreatedDate = LocalDateTime.now().withNano(0);
    }
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
        .boardRecommend(this.boardRecommend)
        .boardTitle(this.boardTitle)
        .boardContent(this.boardContent)
        .boardCreatedDate(this.boardCreatedDate)
        .boardViews(this.boardViews)
        .boardImg(listToString())
        .build();
        }
        }
 */

public class RequestCommentDto {
    @NotBlank(message = "Nickname empty")
    private String nickname;

    @NotBlank(message = "Content empty")
    private String commentContent;

    private long commentRecommend=0;

    private LocalDateTime commentCreateDate;
    private LocalDateTime commentModifyDate;


    public void setCommentCreateDate(){
        this.commentCreateDate = LocalDateTime.now().withNano(0);
    }
    public void setCommentModifyDate() { this.commentModifyDate = LocalDateTime.now().withNano(0);}

    public Comment toEntity(){
        return Comment.builder().commentContent(this.commentContent)
                .commentCreatedDate(this.commentCreateDate)
                .commentModifyDate(this.commentModifyDate)
                .commentRecommend(this.commentRecommend)
                .nickname(this.nickname)
                .build();
    }
}
