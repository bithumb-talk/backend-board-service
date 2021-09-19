package com.bithumb.board.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Table(name = "user")
public class User {
    @Id
    @Column(name = "user_no")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "user_password")
    private String password;

    @Column(name = "user_nickname")
    private String nickname;

    @Enumerated(EnumType.STRING)
    @Column(name = "user_authority")
    private Authority authority;


    @OneToMany(mappedBy="user")
    private List<Board> boards;

}
