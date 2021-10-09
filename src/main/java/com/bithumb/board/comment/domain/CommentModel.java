package com.bithumb.board.comment.domain;

import com.bithumb.board.reply.domain.Reply;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.List;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@EqualsAndHashCode(callSuper = false)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CommentModel extends RepresentationModel<CommentModel> {
    private Long commentNo;
    private String commentContent;
    private ZonedDateTime commentCreatedDate;
    private ZonedDateTime commentModifyDate;
    private long commentRecommend;
    private String nickname;
    private List<Reply> replyList;
    public void changeModel(Comment entity) {
        this.commentNo = entity.getCommentNo();
        this.commentContent = entity.getCommentContent();
        this.commentCreatedDate = entity.getCommentCreatedDate();
        this.commentModifyDate = entity.getCommentModifyDate();
        this.commentRecommend = entity.getCommentRecommend();
        this.nickname = entity.getNickname();
        this.replyList = entity.getReply();
    }
    public CommentModel of(Comment comment){
        return new CommentModel(comment.getCommentNo(), comment.getCommentContent(),
                comment.getCommentCreatedDate(), comment.getCommentModifyDate(),
                comment.getCommentRecommend(), comment.getNickname(), comment.getReply());
    }
}
