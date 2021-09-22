package com.bithumb.board.Assembler;


import com.bithumb.board.controller.BoardController;
import com.bithumb.board.controller.CommentController;
import com.bithumb.board.domain.Board;
import com.bithumb.board.domain.BoardModel;
import com.bithumb.board.domain.Comment;
import com.bithumb.board.domain.CommentModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class CommentAssembler extends RepresentationModelAssemblerSupport<Comment, CommentModel> {
    public CommentAssembler(){
        super(CommentController.class, CommentModel.class);
    }

    @Override
    public CommentModel toModel(Comment entity) {
        CommentModel model = instantiateModel(entity);
        model.setCommentNo(entity.getCommentNo());

        model.setCommentContent(entity.getCommentContent());
        model.setCommentCreatedDate(entity.getCommentCreatedDate());
        model.setCommentModifyDate(entity.getCommentModifyDate());
        model.add(linkTo(methodOn(BoardController.class).retrieveBoard(entity.getBoard().getBoardNo())).withRel("board"));

        return model;
    }
}
