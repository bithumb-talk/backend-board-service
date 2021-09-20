package com.bithumb.board.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@Table(name="comment")
public class Comment {
    @Id
    @GeneratedValue
    @Column(name="comment_no")
    private Long commentNo;

    @NotNull
    @Column(name="comment_content")
    private String commentContent;

    @Column(name="comment_created_date")
    private LocalDateTime commentCreatedDate;

    @Column(name="comment_modify_date")
    private LocalDateTime commentModifyDate;

    @Column(name="user_id")
    private String userId;

    @PrePersist     //insert 연산할때 같이실행
    public void prePersist(){
        this.commentCreatedDate = LocalDateTime.now().withNano(0);
    }
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="board_no")
    @JsonIgnore
    private Board board;

    @OneToMany(mappedBy="comment",cascade = CascadeType.REMOVE)

    private List<Reply> reply;

}
