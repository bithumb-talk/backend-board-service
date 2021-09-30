package com.bithumb.board.board.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@EqualsAndHashCode(callSuper = false)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BoardModel extends RepresentationModel<BoardModel> {
    private long boardNo;
    private String boardTitle;
    private long boardViews;
    private String boardCategory;
    private String boardContent;
    private String nickname;
    private LocalDateTime boardCreatedDate;
    private LocalDateTime boardModifyDate;
    private long boardRecommend;

    public BoardModel(Board entity) {
        this.boardNo = entity.getBoardNo();
        this.boardTitle = entity.getBoardTitle();
        this.boardViews = entity.getBoardViews();
        this.boardCategory = entity.getBoardCategory();
        this.boardContent = entity.getBoardContent();
        this.boardCreatedDate = entity.getBoardCreatedDate();
        this.boardModifyDate = entity.getBoardModifyDate();
        this.boardRecommend = entity.getBoardRecommend();
        this.nickname = entity.getNickname();
    }
}
