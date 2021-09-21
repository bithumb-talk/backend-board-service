package com.bithumb.board.repository;


import com.bithumb.board.domain.Reply;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReplyRepository extends JpaRepository<Reply, Long > {
}
