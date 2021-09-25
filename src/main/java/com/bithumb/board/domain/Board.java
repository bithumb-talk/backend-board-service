package com.bithumb.board.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@Table(name="board")
public class Board {

    @Id
    @GeneratedValue
    @Column(name="board_no")
    private Long boardNo;

    @Column(name = "user_nickname")
    private String nickname;

    @NotNull
    @Column(name="board_title", length=100)
    //@NotBlank() //입ㅂ력이나 스페이스바 같은거 오류발생
    private String boardTitle;

    @Column(name="board_views")
    private Long boardViews;

    @Column(name="board_category", length=20)
    private String boardCategory;

    @NotNull
    @Column(columnDefinition = "LONGTEXT")
    private String boardContent;

    //board img는 나중에

    @Column(name="board_created_date")
    private LocalDateTime boardCreatedDate;

    @Column(name="board_modify_date")
    private LocalDateTime boardModifyDate;

    @Column(name="board_recommend")
    private Long boardRecommend;

    @PrePersist     //insert 연산할때 같이실행
    public void prePersist(){
        this.boardCreatedDate = LocalDateTime.now().withNano(0);
        this.boardViews = this.boardViews == null ? 0 : this.boardViews;
        this.boardRecommend = this.boardRecommend == null ? 0 : this.boardRecommend;
    }
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_no")
    @JsonIgnore
    private User user;

    @OneToMany(mappedBy="board", cascade = CascadeType.REMOVE)
    @JsonIgnore
    private List<Comment> comments;

}
