package com.bithumb.board.comment.domain;

import com.bithumb.board.reply.domain.Reply;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@EqualsAndHashCode(callSuper = false)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CommentModel extends RepresentationModel<CommentModel> {
    private Long commentNo;
    private String commentContent;
    private LocalDateTime commentCreatedDate;
    private LocalDateTime commentModifyDate;
    private String userId;
    private List<Reply> replyList;

    public CommentModel(Comment entity) {
        this.commentNo = entity.getCommentNo();
        this.commentContent = entity.getCommentContent();
        this.commentCreatedDate = entity.getCommentCreatedDate();
        this.commentModifyDate = entity.getCommentModifyDate();
        this.userId = entity.getNickname();
        this.replyList = entity.getReply();
    }

}
