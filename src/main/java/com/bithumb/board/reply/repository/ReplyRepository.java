package com.bithumb.board.reply.repository;


import com.bithumb.board.reply.domain.Reply;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReplyRepository extends JpaRepository<Reply, Long > {
}
